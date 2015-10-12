package uk.ac.kcl.cch.diamm.search;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.DIAMMQueryMaker;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Item;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.diamm.ui.DisplayItem;
import uk.ac.kcl.cch.diamm.ui.PageNavigation;
import uk.ac.kcl.cch.diamm.ui.SourceResult;
import uk.ac.kcl.cch.facet.QueryMaker;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 14/02/11
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class SearchManager {

    private SearchItem[] currentCriteria;
    private boolean imageOnly = false;
    private int resultType;
    private int resultCount;
    private ArrayList<Source> sourceResults;
    private ArrayList<Item> itemResults;
    public static final int criteriaMax = 5;
    public static final String resultTypeParam = "resultType";
    public static final String imageOnlyParam = "imageOnly";
    public static final int resultsPerPage = 30;
    public static final String searchPageNumberSessionName = "searchPage";

    public SearchManager() {
        resultCount = 0;
        currentCriteria = new SearchItem[criteriaMax];
        resultType=2;
    }

    public void setOtherRequestVariables(HttpServletRequest request) {
        String t = request.getParameter(resultTypeParam);
        String i = request.getParameter(imageOnlyParam);
        boolean reset = false;
        if (t != null) {
            resultType = Integer.parseInt(t);
            reset = true;
        }
        if (i != null) {
            imageOnly = (i.equalsIgnoreCase("1") ? true : false);
            reset = true;
        }
        if (request.getParameter("pageNum") != null) {
            setCurrentPage(Integer.parseInt(request.getParameter("pageNum")), request);
        }
        if (reset) {
            setCurrentPage(1, request);
        }
    }

    public void setRequestVariables(HttpServletRequest request) {
        setOtherRequestVariables(request);
        //todo:  Create new criteria from request
        SearchItem[] criteria = getCurrentCriteria();
        for (int i = 1; i <= criteriaMax; i++) {
            String v = request.getParameter("criteriaInput" + i);
            String op = request.getParameter("op" + i);
            String t = request.getParameter("criteriaType" + i);
            String label = request.getParameter("criteriaInput" + i);
            /* if (SearchItemType.valueOf(t).equals(SearchItemType.DESCRIPTION) || SearchItemType.valueOf(t).equals(SearchItemType.TEXT)) {
                v = label;
            }*/
            if (v != null && v.length() > 0) {
                SearchItem item = initItemByType(SearchItemType.valueOf(t));
                item.setLabel(label);
                item.setFromRequest(request);
                if (v.matches(".*[a-z]+.*")) {
                    /*if (item.equals(SearchItemType.LAYOUT)) {
                        String c1 = request.getParameter("century1" + i);
                        String c2 = request.getParameter("century2" + i);
                        item.setTextValue(c1 + ":" + c2);
                    }*/
                    item.setTextValue(v);
                } else {
                    item.setValue(Integer.parseInt(v));
                }
                if (op != null && !op.equalsIgnoreCase("0")) {
                    item.setOperand(Integer.parseInt(op));
                }
                criteria[i] = item;
            }
        }
        /*for (int i = 0; i < criteria.length; i++) {
            SearchItem searchItem = criteria[i];
            searchItem.addRequestVariables(request);
            addOtherRequestVariables(request);
        }    */

    }

    public void addOtherRequestVariables(HttpServletRequest request) {
        request.setAttribute(imageOnlyParam, (imageOnly ? "1" : "0"));
        if (resultCount > 0) {
            int currentPage = getCurrentPage(request);
            PageNavigation pn = new PageNavigation(resultsPerPage, getResultCount(), Constants.defaulPageRange, currentPage);
            request.setAttribute("pageNav", pn);
        }
        request.setAttribute(resultTypeParam, resultType);
        request.setAttribute("searchTypes", SearchItemType.values());
        request.setAttribute("maxCriteria", criteriaMax);
    }

    public void setPagiantion(HttpServletRequest request) {
        int pageNumber = getCurrentPage(request);
        PageNavigation pn = new PageNavigation(resultsPerPage, getResultCount(), Constants.defaulPageRange, pageNumber);
        request.setAttribute(Constants.paginationAttrName, pn);
        setCurrentPage(pageNumber, request);
    }

    public int getCurrentPage(HttpServletRequest request) {
        int pageNumber = 1;
        String pnum = null;
        if (request.getParameter("pageNum") != null) {
            pnum = request.getParameter("pageNum");
        } else {
            pnum = (String) request.getSession().getAttribute(searchPageNumberSessionName);
        }
        if (pnum != null) {
            pageNumber = Integer.parseInt(pnum);
        }
        return pageNumber;
    }

    public void setCurrentPage(int page, HttpServletRequest request) {
        request.getSession().setAttribute(searchPageNumberSessionName, page + "");
    }

    public void addAutoComplete(HttpServletRequest request) {
        String iid = request.getParameter("iid");
        String type = request.getParameter("itemType");
        if (iid != null) {
            int cid = Integer.parseInt(iid);
            SearchItem item = initItemByType(SearchItemType.valueOf(type));
            if (item != null) {
                ArrayList<AutoCompleteResult> results = item.getAutoCompleteResults(request);
                if (results != null) {
                    request.setAttribute("autoCompleteResults", results);
                }
            }
        }
    }

    public String runSearch(HttpServletRequest request) {
        SearchItem[] criteria = getCurrentCriteria();
        DIAMMQueryMaker q = new DIAMMQueryMaker();
        String fullHql = "";
        for (int i = 1; i < criteria.length; i++) {
            SearchItem searchItem = criteria[i];
            if (searchItem != null) {
                searchItem.addSearchCriteria(q);
            }
        }

        ArrayList<Integer> itemKeys = q.getInClauseValues("item.itemkey");
        if (itemKeys.size() == 0) {
            //Keys are empty, nothing found
            if (resultType == 1) {
                request.setAttribute("itemResults", new ArrayList<DisplayItem>());
                setResultCount(0);
            } else {
                request.setAttribute("sourceResults", new ArrayList<SourceResult>());
                setResultCount(0);
            }
            return "searchresults.jsp";
        }

        if (resultType == 1) {
            //countHQL = "SELECT count(DISTINCT item) from Item as item " + fullHql;
            /*fullHql = "SELECT DISTINCT item from Item as item " + fullHql;
            if (q.getInClauseValues("item.itemkey").size()>0){
               fullHql += " WHERE item.itemkey IN (" + DIAMMFacetManager.serializeKeys(itemKeys) + ")";
            }
            Query query = HibernateUtil.getSession().createQuery(fullHql);

            if (getCurrentPage(request) > 1) {
                //Apply Pagination
                query.setFirstResult(resultsPerPage * (getCurrentPage(request) - 1));
            }
            query.setMaxResults(resultsPerPage);*/
            int firstResult = resultsPerPage * (getCurrentPage(request) - 1);
            int lastResult = (firstResult + resultsPerPage) > itemKeys.size()? itemKeys.size():(firstResult + resultsPerPage) ;
            /*if (lastResult>itemKeys.size()){
                lastResult=itemKeys.size();
            }
            List<Integer> subKeys= itemKeys.subList(firstResult, lastResult);*/
            Criteria c = HibernateUtil.getSession().createCriteria(Item.class)
                    .setProjection(Projections.distinct(Projections.id()))
                    .add(Restrictions.in("itemkey", itemKeys));
            if (isImageOnly()) {
                c.add(Restrictions.eq("hasImages", 1));
            }
            //Add ordering
            c.createCriteria("sourceBySourcekey").addOrder(Order.asc("sortorder"));
            ArrayList<DisplayItem> items = new ArrayList<DisplayItem>();
            ArrayList<Integer> resultKeys = (ArrayList<Integer>) c.list();
            ArrayList<Item> itemResults = (ArrayList<Item>) HibernateUtil.getSession().createCriteria(Item.class).add(Restrictions.in("itemkey", resultKeys.subList(firstResult, lastResult))).list();
            for (int i = 0; i < itemResults.size(); i++) {
                Item item = itemResults.get(i);
                DisplayItem di = new DisplayItem(item);
                di.addDetailData();
                items.add(di);
            }

            /*itemResults = (ArrayList<Item>) c.list();
            
            for (int i = 0; i < itemResults.size(); i++) {
                Item item = itemResults.get(i);
                DisplayItem di = new DisplayItem(item);
                di.addDetailData();
                items.add(di);
            }*/
            request.setAttribute("itemResults", items);
            setResultCount(resultKeys.size());
        } else if (resultType == 2) {
            /*Criteria c = HibernateUtil.getSession().createCriteria(Source.class)
                    //.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setProjection(Projections.projectionList()
                            //.add(Projections.rowCount(), "sourcekey")
                            .add(Projections.countDistinct("sourcekey"))
                    )
                    .addOrder(Order.asc("sourcekey"))
                    .createCriteria("itemsBySourcekey")
                    .add(Restrictions.in("itemkey", itemKeys));
            ArrayList counts=(ArrayList) c.list();
            if (counts!=null&&counts.size()>0){
                Integer count=(Integer) counts.get(0);
                setResultCount(count);
            }*/
            /*Integer sCount = (Integer) c.uniqueResult();
            */
            Criteria c = HibernateUtil.getSession().createCriteria(Source.class)
                    //.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    // .add(Restrictions.sqlRestriction("group by Source.sourceKey"))
                    .setProjection(Projections.distinct(Projections.id()))
                    .addOrder(Order.asc("sortorder"))
                    .createCriteria("itemsBySourcekey").add(Restrictions.in("itemkey", itemKeys));
            if (isImageOnly()) {
                c.add(Restrictions.eq("hasImages", 1));
            }
            int start = 0;
            if (getCurrentPage(request) > 1) {
                //Apply Pagination
                start = resultsPerPage * (getCurrentPage(request) - 1);
            }
            int end = (start + resultsPerPage);
            ArrayList<Integer> sKeys = (ArrayList<Integer>) c.list();
            if (end > sKeys.size()) {
                end = sKeys.size();
            }
            ArrayList<SourceResult> sources = new ArrayList<SourceResult>();
            for (int i = start; i < end; i++) {
                //Source source = sourceResults.get(i);
                Integer sKey = sKeys.get(i);
                sources.add(new SourceResult(sKey));
            }
            if (sKeys.size() > 0) {
                setResultCount(sKeys.size());
            }
            request.setAttribute("sourceResults", sources);

        }
        return "searchresults.jsp";
    }

    public static void addItemKeyConstraint(ArrayList<Integer> keys, QueryMaker q, int operand) {
        addKeyConstraint("item.itemkey", keys, q, operand);
    }

    public static void addSourceKeyConstraint(ArrayList<Integer> keys, QueryMaker q, int operand) {
        addKeyConstraint("source.sourcekey", keys, q, operand);
    }

    public static void addKeyConstraint(String key, ArrayList<Integer> keys, QueryMaker q, int operand) {
        ArrayList<Integer> oldkeys = q.getInClauseValues(key);
        if (operand == 0 && oldkeys != null && oldkeys.size() > 0) {
            keys = DIAMMFacetManager.mergeKeyLists(keys, oldkeys);
        } else if (operand == 1) {
            keys = DIAMMFacetManager.appendKeyLists(keys, oldkeys);
        }
        if (keys != null && keys.size() > 0) {
            q.resetInClause(key);
            q.setInClause(key, keys);
        }
    }

    public SearchItem initItemByType(SearchItemType type) {
        switch (type) {
            case COUNTRY:
                return new SourceSearchItem(SearchItemType.COUNTRY);
            case CITY:
                return new SourceSearchItem(SearchItemType.CITY);
            case ARCHIVE:
                return new SourceSearchItem(SearchItemType.ARCHIVE);
            case MANUSCRIPT:
                return new SourceSearchItem(SearchItemType.MANUSCRIPT);
            case AUTHOR:
                return new BibliographySearchItem();
            case LANGUAGE:
                return new ListSearchItem(SearchItemType.LANGUAGE);
            case DATE:
                return new SourceSearchItem(SearchItemType.DATE);
            case PROVENANCE:
                return new ListSearchItem(SearchItemType.PROVENANCE);
            case NOTATION:
                return new ListSearchItem(SearchItemType.NOTATION);
            case TAGS:
                return new SourceSearchItem(SearchItemType.TAGS);
            /*case LAYOUT:
                return new SourceSearchItem(SearchItemType.LAYOUT);
            case SCRIBE:
                return new SourceSearchItem(SearchItemType.SCRIBE);*/
            case PERSON:
                return new ListSearchItem(SearchItemType.PERSON);
            case DESCRIPTION:
                return new SourceSearchItem(SearchItemType.DESCRIPTION);
            case GENRE:
                return new ListSearchItem(SearchItemType.GENRE);
            case TITLE:
                return new ListSearchItem(SearchItemType.TITLE);
            case TEXT:
                return new TextSearchItem();
            case VOICE:
                return new ListSearchItem(SearchItemType.VOICE);
            case COPYIST:
                return new ListSearchItem(SearchItemType.COPYIST);
            //Deactivated due to time constraints EH 20/11/2012
            /*case CLEF:
                return new ListSearchItem(SearchItemType.CLEF);
            case MENSURATION:
                return new ListSearchItem(SearchItemType.MENSURATION);*/
            case SET:
                return new ListSearchItem(SearchItemType.SET);
            case COMPOSER:
                return new ListSearchItem(SearchItemType.COMPOSER);

        }
        return null;
    }

    public String addCriteria(HttpServletRequest request) {
        SearchItem[] items = getCurrentCriteria();
        String[] values = new String[criteriaMax];
        String[] labels = new String[criteriaMax];
        String[] criteriaTypes = new String[criteriaMax];
        for (int i = 1; i < items.length; i++) {
            SearchItem item = items[i];
            if (item != null) {
                if (item.getValue() > 0) {
                    values[i] = item.getValue() + "";
                } else if (item.getTextValue().length() > 0) {
                    values[i] = item.getTextValue();
                }
                labels[i] = item.getLabel();
                criteriaTypes[i] = item.getType().toString();
            }
        }
        request.setAttribute("criteriaTypes", criteriaTypes);
        request.setAttribute("values", values);
        request.setAttribute("labels", labels);
        return "";
    }

    public String setCriteriaValue(HttpServletRequest request) {

        return "";
    }


    public SearchItem[] getCurrentCriteria() {
        return currentCriteria;
    }

    public void setCurrentCriteria(SearchItem[] currentCriteria) {
        this.currentCriteria = currentCriteria;
    }

    public boolean isImageOnly() {
        return imageOnly;
    }

    public void setImageOnly(boolean imageOnly) {
        this.imageOnly = imageOnly;
    }

    public ArrayList<Source> getSourceResults() {
        return sourceResults;
    }

    public void setSourceResults(ArrayList<Source> sourceResults) {
        this.sourceResults = sourceResults;
    }

    public ArrayList<Item> getItemResults() {
        return itemResults;
    }

    public void setItemResults(ArrayList<Item> itemResults) {
        this.itemResults = itemResults;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}

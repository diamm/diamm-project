package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.facet.type.*;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.diamm.ui.GroupType;
import uk.ac.kcl.cch.diamm.ui.ItemGroup;
import uk.ac.kcl.cch.facet.*;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 10-May-2010
 * Time: 12:58:45
 * To change this template use File | Settings | File Templates.
 */
public class DIAMMFacetManager extends uk.ac.kcl.cch.facet.FacetManager {
    protected boolean updateItemView = false;

    public Long getResultCount(HttpServletRequest request) {
        Long c = (Long) request.getSession().getAttribute(Constants.currentResultCount);
        if (c != null) {
            return c;
        }
        return new Long(0);
    }

    public void setResultCount(Long resultCount, HttpServletRequest request) {
        request.getSession().setAttribute(Constants.currentResultCount, resultCount);
    }

    public static FacetCriterion getInitialCriterion(int key, List<FacetCriterion> values) {
        if (values == null) {
            return null;
        }
        for (int i = 0; i < values.size(); i++) {
            FacetCriterion facetCriterion = values.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion;
            }
        }
        return null;
    }


    public void setRowsPerPage(HttpServletRequest request) {
        int pageNumber = 1;
        if (request.getParameter(Constants.rowsPerPageParameter) != null) {
            pageNumber = Integer.parseInt(request.getParameter(Constants.rowsPerPageParameter));
            request.getSession().setAttribute(Constants.currentResultsPerPage, pageNumber + "");
            setUpdateItemView(true);
        }
    }

    public int getRowsPerPage(HttpServletRequest request) {
        String per = (String) request.getSession().getAttribute(Constants.currentResultsPerPage);
        if (per != null) {
            return Integer.parseInt(per);
        }
        return Constants.defaultResultsPerPage;
    }

    public void setCurrentPageFromRequest(HttpServletRequest request) {
        int pageNumber = 1;
        if (request.getParameter(Constants.pageNumberParameter) != null) {
            pageNumber = Integer.parseInt(request.getParameter(Constants.pageNumberParameter));
            setCurrentPage(pageNumber, request);
        }
    }

    public void setCurrentPage(int page, HttpServletRequest request) {
        if (page > 0) {
            request.getSession().setAttribute(Constants.pageNumberSessionName, page + "");
            setUpdateItemView(true);
        }
    }

    public int getCurrentPage(HttpServletRequest request) {
        int pageNumber = 1;
        String pnum = (String) request.getSession().getAttribute(Constants.pageNumberSessionName);
        if (pnum != null) {
            pageNumber = Integer.parseInt(pnum);
        }
        return pageNumber;
    }

    public int getResultsPerPage(HttpServletRequest request) {
        int pageNumber = 0;
        String pnum = (String) request.getSession().getAttribute(Constants.currentResultsPerPage);
        if (pnum != null) {
            pageNumber = Integer.parseInt(pnum);
        } else {
            pageNumber = Constants.defaultResultsPerPage;
        }
        return pageNumber;
    }

    public Facet getNewFacet(FacetType ft) {
        if (SourceFacetType.class == ft.getClass()) {
            return new SourceFacet(this);
        } else if (ft.getClass() == ComposerFacetType.class) {
            return new ComposerFacet(this);
        } else if (ft.getClass() == GenreFacetType.class) {
            return new GenreFacet(this);
        } else if (ft.getClass() == TextFacetType.class) {
            return new TextFacet(this);
        } else if (ft.getClass() == SecondaryFacetType.class) {
            return new SecondaryFacet(this);
        }
        return null;
    }


    public boolean updateAllFacetsFromRequest(HttpServletRequest request) {
        boolean changed = false;
        Iterator i = getFacets().values().iterator();
        while (i.hasNext()) {
            Facet f = (Facet) i.next();
            boolean c = f.updateConstraintsFromRequest(request);
            if (c) {
                changed = true;
            }
        }
        return changed;
    }

    public Facet getFacetByType(FacetType ft) {
        Facet f = null;//(Facet) getFacets().get(ft);
        Enumeration types = getFacets().keys();
        while (types.hasMoreElements()) {
            //Build a common set of Item keys for all facets
            FacetType type = (FacetType) types.nextElement();
            if (ft.getTypeName().equalsIgnoreCase(type.getTypeName())) {
                f = (Facet) getFacets().get(type);
            }
        }
        return f;
    }

    public Facet getFacetByType(String type) {
        for (int i = 0; i < types.size(); i++) {
            FacetType facetType = types.get(i);
            if (facetType.getTypeName().compareToIgnoreCase(type) == 0) {
                return getFacetByType(facetType);
            }
        }

        return null;
    }


    protected void addGroupOrdering(DetachedCriteria c, HttpServletRequest request) {
        GroupType group = getGroupType(request);
        switch (group) {
            case SOURCE:
                c.createCriteria("sourceBySourcekey").addOrder(Order.asc("sortorder"));
                break;
            case COMPOSER:
                c.createCriteria("compositionByCompositionkey").createCriteria("compositioncomposersByCompositionkey")
                        .createCriteria("composerByComposerkey").addOrder(Order.asc("lastname"));
                break;
            case PROVENANCE:
                c.createCriteria("sourceBySourcekey")
                        .createCriteria("sourceprovenancesBySourcekey")
                        .createCriteria("alprovenanceByAlprovenancekey").addOrder(Order.asc("country"));
                break;
            case GENRE:
                c.createCriteria("compositionByCompositionkey")
                        .createCriteria("compositiongenresByCompositionkey")
                        .createCriteria("algenreByAlgenrekey").addOrder(Order.asc("genre"));
                break;
            case DATE:
                c.createCriteria("sourceBySourcekey").addOrder(Order.asc("startdate"));
                break;
        }
    }

    /**
     * Orders results based on group type
     * for accurate grouping with pagiantion.
     *
     * @return
     */

    protected void addGrouping(QueryMaker q, HttpServletRequest request) {
        GroupType group = getGroupType(request);

        switch (group) {
            case SOURCE:
                q.addFromString("join item.sourceBySourcekey as source");
                q.addOrderByFieldName("source.sortorder,item.orderno");
                break;
            case COMPOSER:
                q.addFromString(" join item.compositionByCompositionkey as composition ");
                q.addFromString(" join composition.compositioncomposersByCompositionkey as compositioncomposer ");
                q.addFromString(" join compositioncomposer.composerByComposerkey as composer ");
                q.addOrderByFieldName("composer.composercomplete");
                break;
            case PROVENANCE:
                q.addFromString("join item.sourceBySourcekey as source");
                q.addFromString(" join source.sourceprovenancesBySourcekey as sourceprovenance");
                q.addFromString(" join sourceprovenance.alprovenanceByAlprovenancekey prov");
                q.addOrderByFieldName("prov.country");
                break;
            case GENRE:
                q.addFromString(" join item.compositionByCompositionkey as composition ");
                q.addFromString(" join composition.compositiongenresByCompositionkey as compositiongenre");
                q.addFromString(" join compositiongenre.algenreByAlgenrekey as genre");
                q.addOrderByFieldName("genre.genre");
                break;
            case DATE:
                q.addFromString("join item.sourceBySourcekey as source");
                q.addOrderByFieldName("source.startdate");
                break;
        }
    }


    public void setImageOnly(HttpServletRequest request) {
        if (request.getParameter(Constants.imageOnlyParameter) != null) {
            String t = request.getParameter(Constants.imageOnlyParameter);
            setCurrentPage(1, request);
            setImageOnly(Integer.parseInt(t), request);
        }
    }

    public void setImageOnly(int t, HttpServletRequest request) {
        request.getSession().setAttribute(Constants.imageOnlyToggleSessionName, t);
        setUpdateItemView(true);
    }


    /**
     * Takes the item list and splits it into groups depending
     * on the GroupType selected.
     *
     * @param items   all items
     * @param request
     * @return list of grouped items
     */
    protected List<ItemGroup> groupItems(List<Item> items, HttpServletRequest request) {
        GroupType group = getGroupType(request);
        //The label of the group i.e. Source.sourceName when grouped by source
        String label = "";
        //Key for the object that we're grouping by, in case we need it later
        int key = 0;
        List<ItemGroup> groups = new Vector<ItemGroup>();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            switch (group) {
                case SOURCE:
                    groupBySource(groups, item);
                    break;
                case COMPOSER:
                    groupByComposer(groups, item);
                    break;
                case PROVENANCE:
                    groupByProvenance(groups, item);
                    break;
                case GENRE:
                    groupByGenre(groups, item);
                    break;
                case DATE:
                    groupByDate(groups, item);
                    break;
            }

        }
        return groups;
    }

    protected void groupByProvenance(List<ItemGroup> groups, Item item) {
        Source s = item.getSourceBySourcekey();
        if (s != null) {
            Iterator<Sourceprovenance> sprovs = s.getSourceprovenancesBySourcekey().iterator();
            if (sprovs != null) {
                while (sprovs.hasNext()) {
                    Sourceprovenance sp = sprovs.next();
                    Alprovenance prov = sp.getAlprovenanceByAlprovenancekey();
                    String label = prov.getCountry();
                    int key = prov.getAlprovenancekey();
                    addToGroup(groups, GroupType.PROVENANCE, item, label, key);
                }
            }
        }
    }

    protected void groupByGenre(List<ItemGroup> groups, Item item) {
        if (item.getCompositionByCompositionkey().getComposers() != null) {
            Iterator<Compositiongenre> composers = item.getCompositionByCompositionkey().getCompositiongenresByCompositionkey().iterator();
            if (composers != null && composers.hasNext()) {
                while (composers.hasNext()) {
                    //One item may have multiple composers, add to all relevant groups
                    Compositiongenre cg = composers.next();
                    Algenre genre = cg.getAlgenreByAlgenrekey();
                    String label = genre.getGenre();
                    int key = genre.getAlgenrekey();
                    addToGroup(groups, GroupType.GENRE, item, label, key);
                }
            }

        }
    }

    protected void groupByDate(List<ItemGroup> groups, Item item) {
        Source s = item.getSourceBySourcekey();
        if (s != null) {
            if (s.getStartdate() != null && s.getStartdate().length() > 0) {
                String label = s.getStartdate() + "th century";
                int key = Integer.parseInt(s.getStartdate());
                addToGroup(groups, GroupType.DATE, item, label, key);
            }
        }
    }

    protected void groupBySource(List<ItemGroup> groups, Item item) {
        Source s = item.getSourceBySourcekey();
        String label = s.getArchiveByArchivekey().getSiglum() + " " + s.getShelfmark()+
        "&nbsp;<a href=\"Descriptions?op=SOURCE&amp;sourceKey="+s.getSourcekey()+"\" class=\"t9 m1 mssTrigger\">View Record</a>";
        int key = s.getSourcekey();
        addToGroup(groups, GroupType.SOURCE, item, label, key);
    }

    protected void groupByComposer(List<ItemGroup> groups, Item item) {
        if (item.getCompositionByCompositionkey().getComposers() != null) {
            Iterator<Composer> composers = item.getCompositionByCompositionkey().getComposers().iterator();
            if (composers != null && composers.hasNext()) {
                while (composers.hasNext()) {
                    //One item may have multiple composers, add to all relevant groups
                    Composer composer = composers.next();
                    String label = composer.buildComposerLabel();
                    int key = composer.getComposerkey();
                    addToGroup(groups, GroupType.COMPOSER, item, label, key);
                }
            }

        }
    }

    /**
     * Add item to appropriate groupitem
     *
     * @param groups all groups
     * @param type   group type
     * @param item   item to add
     * @param label  group header
     * @param key    group primary key
     */
    protected void addToGroup(List<ItemGroup> groups, GroupType type, Item item, String label, int key) {

        ItemGroup g = getGroupByKey(groups, key);
        if (g == null) {
            //new group
            g = new ItemGroup(type, key, label);
            groups.add(g);
        }
        g.addToItemList(item);
    }

    protected ItemGroup getGroupByKey(List<ItemGroup> groups, int key) {
        if (groups != null && groups.size() > 0) {
            for (int i = 0; i < groups.size(); i++) {
                ItemGroup itemGroup = groups.get(i);
                if (itemGroup.getKey() == key) {
                    return itemGroup;
                }
            }
        }
        return null;
    }

    public void setGroupType(GroupType g, HttpServletRequest request) {
        request.getSession().setAttribute(Constants.groupTypeSessionName, g);
        setUpdateItemView(true);
    }


    /**
     * Checks all the other facets and sees if any of them have data.
     * if none other active, we don't need to refer to keylist in main query
     * and can run faster queries only inside the facet.
     *
     * @param
     * @return if others are not empty
     */
    public boolean isOnlyFacetActive(Facet target) {
        Iterator i = getFacets().values().iterator();
        while (i.hasNext()) {
            Facet f = (Facet) i.next();
            if (!f.equals(target) && f.getState() == uk.ac.kcl.cch.facet.FacetState.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public GroupType getGroupType(HttpServletRequest request) {
        GroupType g = (GroupType) request.getSession().getAttribute(Constants.groupTypeSessionName);
        if (request.getParameter(Constants.groupTypeParameter) != null) {
            String gt = request.getParameter(Constants.groupTypeParameter);
            if (g != GroupType.valueOf(gt)) {
                g = GroupType.valueOf(gt);
                //Group changed, reset pagination
                setCurrentPage(1, request);
                setGroupType(g, request);
            }
        } else if (request.getSession() == null) {
            return Constants.defaultGroupType;
        }
        if (g == null) {
            //Go to default
            g = Constants.defaultGroupType;
            setGroupType(g, request);
        }
        return g;
    }

    public List<ItemGroup> getGroupedItems(HttpServletRequest request) {
        /*String pnum = (String) request.getSession().getAttribute(Constants.pageNumberSessionName);
        if (pnum == null) {
            pnum = "1";
        }*/
        List<Item> itemList = getItemList(request);
        List<ItemGroup> grouped = groupItems(itemList, request);
        return grouped;
    }

    /**
     * Utility function to extract keys from result set into array
     *
     * @param items item objects
     * @return ArrayList of item keys
     */
    public static ArrayList<Integer> getKeyList(List<Item> items) {
        ArrayList<Integer> itemKeys = new ArrayList<Integer>();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Integer itemKey = item.getItemkey();
            if (!itemKeys.contains(itemKey)) {
                itemKeys.add(itemKey);
            }
        }
        return itemKeys;
    }

    public static String serializeKeys(ArrayList<Integer> keys) {
        String keyS = "";
        for (int i = 0; i < keys.size(); i++) {
            Integer integer = keys.get(i);
            if (keyS.length() > 0) {
                keyS += ",";
            }
            keyS += "" + integer;
        }
        return keyS;
    }


    public int getImageOnly(HttpServletRequest request) {
        Integer toggle = (Integer) request.getSession().getAttribute(Constants.imageOnlyToggleSessionName);
        if (toggle == null) {
            return 0;
        }
        return toggle.intValue();
    }


    /**
     * Get a combined array of all master key constraints
     * currently held in facet.
     *
     * @param exclude facet to ignore (so keys can be used for GUI operations)
     * @return
     */
    public ArrayList<Integer> getMasterKeyList(FacetType exclude) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        Enumeration types = getFacets().keys();
        while (types.hasMoreElements()) {
            //Build a common set of Item keys for all facets
            FacetType type = (FacetType) types.nextElement();
            if (type != exclude) {
                Facet f = getFacetByType(type);
                if (f.getResultSetKeys() != null && f.getResultSetKeys().size() > 0) {
                    if (keys.size() == 0) {
                        //Initial set
                        keys = f.getResultSetKeys();
                    } else {
                        keys = FacetManager.mergeKeyLists(keys, f.getResultSetKeys());
                    }
                }
            }
        }

        return keys;
    }


    /**
     * Merges two lists of keys, keeping ONLY
     * keys common to both sets.
     *
     * @param l1
     * @param l2
     * @return list of keys in both
     */
    public static ArrayList<Integer> mergeKeyLists(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        ArrayList<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < l1.size(); i++) {
            Integer integer = l1.get(i);
            if (l2.contains(integer)) {
                newList.add(integer);
            }
        }
        return newList;
    }

    public static ArrayList<Integer> appendKeyLists(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        ArrayList<Integer> newList = l1;
        for (int i = 0; i < l2.size(); i++) {
            Integer integer = l2.get(i);
            if (!newList.contains(integer)) {
                newList.add(integer);
            }
        }
        return newList;
    }


    /**
     * Sets all facets that have been updated with this request
     * to selected.
     */
    public void resetFacetStates() {

        for (int i = 0; i < types.size(); i++) {
            FacetType type = types.get(i);
            Facet f = getFacetByType(type);
            if (f != null && f.getState() == FacetState.UPDATED) {
                f.setState(FacetState.SELECTED);
            }
        }
    }


    public String getMasterKeyString(FacetType exclude) {
        ArrayList<Integer> keys = getMasterKeyList(exclude);
        if (keys != null && keys.size() > 0) {
            return serializeKeys(keys);
        }
        return "";
    }

    public String getMasterKeyString() {
        ArrayList<Integer> keys = getMasterKeyList();
        if (keys != null && keys.size() > 0) {
            return serializeKeys(keys);
        }
        return "";
    }

    protected void initTypes() {

        ArrayList<FacetType> types = new ArrayList<FacetType>();
        types.add(new SourceFacetType());
        types.add(new GenreFacetType());
        types.add(new ComposerFacetType());
        types.add(new TextFacetType());
        types.add(new SecondaryFacetType());
        setTypes(types);
    }

    /**
     * Initialize all Facets and their GUI wrapper objects
     */
    protected void initFacets() {
        ArrayList<FacetType> types = getTypes();
        if (types == null) {
            initTypes();
            types = getTypes();
        }
        this.setFacets(new Hashtable((types.size() + 1)));
        /*for (int i = 0; i < types.size(); i++) {
            FacetType type = types.get(i);
            Facet f = getNewFacet(type);
            this.getFacets().put(type, f);
        } */
    }

    public DIAMMFacetManager() {
        setMasterKeyName("item.itemkey");
        initTypes();
        initFacets();
        initQueryMaker();
    }

    protected void initQueryMaker() {
        //if (this.qMaker == null) {
        this.qMaker = new DIAMMQueryMaker();
        //}
    }

    protected void populateUiToDbMappings() {
//To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param q
     * @return
     */
    protected List<Item> doItemQuery(QueryMaker q, HttpServletRequest request) {
        String wheres = q.generateWheres();
        String joins = q.generateFroms();
        String order = q.generateOrderByFieldNames();
        String fullHql = "";
        if (joins.length() > 0) {
            fullHql += " " + joins;
        }
        if (wheres.length() > 0) {
            fullHql += " WHERE " + wheres;
        }
        if (order.length() > 0) {
            fullHql += " ORDER BY " + order;
        }        /*
         join item.compositionByCompositionkey as composition
         join composition.compositioncomposersByCompositionkey as compositioncomposer
          join compositioncomposer.composerByComposerkey as composer
         join item.itemimagesByItemkey as itemimage  join itemimage.imageByImagekey as image
        */
        ArrayList<Integer> keys = q.getInClauseValues(getMasterKeyName());
        if (keys.size() > 0) {
            List<Item> test = null;
            DetachedCriteria baseCriteria = DetachedCriteria.forClass(Item.class)
                    .add(Restrictions.in("itemkey", keys));
            int imageonly = getImageOnly(request);
            if (imageonly == 1) {
                //Only return items with images
                baseCriteria.add(Restrictions.eq("hasImages", 1));
            }
            addGroupOrdering(baseCriteria, request);

            Criteria c = baseCriteria.getExecutableCriteria(HibernateUtil.getSession());

            if (getCurrentPage(request) > 1) {
                //Apply Pagination
                c.setFirstResult(getResultsPerPage(request) * (getCurrentPage(request) - 1));
            }
            c.setMaxResults(getResultsPerPage(request));
            c.addOrder(Order.asc("orderno"));
            //addGroupOrdering(c, request);
            test = c.list();
            if (getCurrentPage(request) == 1) {
                Integer count = (Integer) c.setMaxResults(100000).setProjection(Projections.rowCount()).uniqueResult();
                if (count != null) {
                    setResultCount(new Long(count), request);
                }
            }
            /*Integer count = (Integer) c.setProjection(Projections.projectionList().add(Projections.rowCount()))
            .setFirstResult(1).setMaxResults(100000).uniqueResult();*/

            if (test != null) {
                return test;
            }
        }
        Query query = HibernateUtil.getSession().createQuery("SELECT DISTINCT item from Item as item " + fullHql);
        Long count = (Long) HibernateUtil.getSession().createQuery("SELECT count(DISTINCT item) from Item as item " + fullHql).uniqueResult();
        setResultCount(count, request);
        if (getCurrentPage(request) > 1) {
            //Apply Pagination
            query.setFirstResult(getResultsPerPage(request) * (getCurrentPage(request) - 1));
        }
        query.setMaxResults(getResultsPerPage(request));
        return query.list();
    }

    public List<Item> getItemList(HttpServletRequest request) {

        initQueryMaker();
        addSQLOrdering(request);
        addOtherConstraints(request);
        //Reset key list
        qMaker.removeInsFromWheres();
        qMaker.resetInClause(getMasterKeyName());
        if (getMasterKeyList() != null && getMasterKeyList().size() > 0) {
            qMaker.setInClause(getMasterKeyName(), getMasterKeyList());
        }
        return doItemQuery(qMaker, request);

    }

    public ArrayList<Object> retrieveDisplayResults(int pageNumber) {
        return null;
    }


    public void addOtherConstraints(HttpServletRequest request) {
        int imageonly = getImageOnly(request);
        if (imageonly > 0) {
            qMaker.addFromString("join item.itemimagesByItemkey as itemimage");
            qMaker.addFromString("join itemimage.imageByImagekey as image");
            //qMaker.addWhereString("image.filename NOT NULL");
            qMaker.addWhereString("image.filename not in ('lost','applytolibrary','notphotographed','librarydigitized')");
            qMaker.addWhereString("image.availwebsite='Y'");
            //applytolibrary
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addSQLGrouping(HttpServletRequest request) {

    }

    public void addSQLOrdering(HttpServletRequest request) {
        addGrouping(qMaker, request);
    }


    public Hashtable getFacets() {
        return facets;
    }

    public void setFacets(Hashtable facets) {
        this.facets = facets;
    }

    public void addFacet(Facet f) {
        if (this.facets == null) {
            this.facets = new Hashtable<String, Facet>();
        }
        getFacets().put(f.getType(), f);
    }

    public boolean isUpdateItemView() {
        return updateItemView;
    }

    public void setUpdateItemView(boolean updateItemView) {
        this.updateItemView = updateItemView;
    }

    public static String getPreviousUrl(HttpServletRequest request) {
        String url = (String) request.getSession().getAttribute("DIAMM.previousurl");
        if (url != null) {
            return url;
        }
        return "";
    }

    public static void setPreviousUrl(HttpServletRequest request, String url) {
        request.getSession().setAttribute("DIAMM.previousurl", url);
    }

}

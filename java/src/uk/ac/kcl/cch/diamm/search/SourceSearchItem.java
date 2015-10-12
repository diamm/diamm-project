package uk.ac.kcl.cch.diamm.search;

import org.hibernate.Query;
import org.hibernate.criterion.*;
import uk.ac.kcl.cch.diamm.facet.SourceFacet;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alcity;
import uk.ac.kcl.cch.diamm.model.Alcountry;
import uk.ac.kcl.cch.diamm.model.Archive;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.facet.QueryMaker;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 16/02/11
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
public class SourceSearchItem extends SearchItem {
    SearchItemType locationType;

    public SourceSearchItem(SearchItemType type) {
        locationType = type;
        setType(type);
        setId(this.hashCode());
        setOrder(0);
        setPage("sourcesearch.jsp");
        setOperand(0);
    }

    @Override
    public void addSearchCriteria(QueryMaker q) {
        String hql = " join item.sourceBySourcekey as source ";
        ArrayList<Integer> itemKeys = null;
        switch (locationType) {
            case COUNTRY:
                hql += " join source.archiveByArchivekey as archive";
                hql += " join archive.alcityByAlcitykey as alcity";
                hql += " join alcity.alcountryByAlcountrykey as alcountry";
                hql += " WHERE alcountry.country like '%" + getTextValue()+"%'";
               // itemKeys = SourceFacet.getCountryValue(getValue()).getKeys();
                break;
            case CITY:
                hql += " join source.archiveByArchivekey as archive";
                hql += " join archive.alcityByAlcitykey as alcity";
                hql += " WHERE alcity.city='" + getTextValue()+"'";
               // itemKeys = SourceFacet.getCityValue(getValue()).getKeys();
                break;
            case MANUSCRIPT:
                //shelfmark, olim and MS name
                hql+=" WHERE source.shelfmark like '%"+ getTextValue()+"%' OR "+
                        " source.olim like '%"+ getTextValue()+"%' OR "+
                        " source.sourcename like '%"+ getTextValue()+"%' ";
                break;
            case ARCHIVE:
                hql += " join source.archiveByArchivekey as archive";
                hql += " WHERE archive.archivename like '%" + getTextValue()+"%'";
                //itemKeys = SourceFacet.getArchiveValue(getValue()).getKeys();
                break;
            case DESCRIPTION:
                hql += " WHERE source.description like '%" + getTextValue() + "%'";
                break;
            case TAGS:
                if (getTextValue() != null && getTextValue().length() > 0) {
                    String pat = getTextValue().replaceAll(" ", "\\|");
                    hql += " WHERE source.tags regexp '" + pat + "'";
                }
                break;
            case DATE:
                String[] dates = getTextValue().split(":");
                if (dates != null) {
                    int c1 = Integer.parseInt(dates[0]);
                    int c2 = 0;
                    if (dates[1] != null) {
                        c2 = Integer.parseInt(dates[1]);
                    }
                    hql += " WHERE source.startdate>=" + dates[0];
                    if (c2 > c1) {
                        hql += " and source.enddate<=" + dates[1];
                    }
                    /*if (dates[0] != null) {
                        hql += " WHERE source.dateOfSource regexp '^.*" + dates[0] + "th.*$'";
                    }
                    hql += " AND source.dateOfSource regexp '^.*" + dates[1] + "th.*$'";*/
                }
                break;
            /*case LAYOUT:
                hql += " WHERE item.layout regexp '^" + getTextValue() + ".*$'";
                break;*/
        }

        // hql = "SELECT DISTINCT source.sourcekey from Source as source " + hql+" ORDER BY source.sourcekey";
        if (itemKeys != null && itemKeys.size() > 0) {
            SearchManager.addItemKeyConstraint(itemKeys, q, getOperand());
        } else {
            hql = "SELECT DISTINCT item.itemkey from Item as item " + hql + " ORDER BY item.itemkey";
            Query query = HibernateUtil.getSession().createQuery(hql);
            ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
            if (keys != null && keys.size() > 0) {
                SearchManager.addItemKeyConstraint(keys, q, getOperand());
            }
        }
    }


    @Override
    public void setFromRequest(HttpServletRequest request) {

    }

    @Override
    public ArrayList<AutoCompleteResult> getAutoCompleteResults(HttpServletRequest request) {
        String search = request.getParameter("search");
        switch (locationType) {
            case COUNTRY:
                return countrySearch(search);
            case CITY:
                return citySearch(search);
            case ARCHIVE:
                return archiveSearch(search);
            case DESCRIPTION:
                return descriptionSearch(search);
            case TAGS:
                return tagSearch(search);
            case MANUSCRIPT:
                return manuscriptSearch(search);

        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

private ArrayList<AutoCompleteResult> manuscriptSearch(String search) {
    Criterion shelfmark=Restrictions.ilike("shelfmark",'%'+ search + '%');
    Criterion olim=Restrictions.ilike("olim",'%'+ search + '%');
    Criterion sourcename=Restrictions.ilike("sourcename",'%'+ search + '%');
    Junction conditionGroup = Restrictions.disjunction();
    conditionGroup.add(shelfmark).add(olim).add(sourcename);
        List<Source> sources = (List<Source>) HibernateUtil.getSession().createCriteria(Source.class)
                .add(conditionGroup)
                .addOrder(Property.forName("sortorder").asc()).list();
        ArrayList<AutoCompleteResult> autos = new ArrayList<AutoCompleteResult>();
        for (int i = 0; i < sources.size(); i++) {
            Source source = sources.get(i);
            String label=source.getShelfmark();
            AutoCompleteResult a = new AutoCompleteResult(source.getSourcekey(), label);
            autos.add(a);
        }
        return autos;
    }


    private ArrayList<AutoCompleteResult> tagSearch(String search) {
        List<Source> sources = (List<Source>) HibernateUtil.getSession().createCriteria(Source.class).add(Restrictions.eq("tags", "%" + search + "%")).addOrder(Property.forName("sortorder").asc()).list();
        ArrayList<AutoCompleteResult> autos = new ArrayList<AutoCompleteResult>();
        for (int i = 0; i < sources.size(); i++) {
            Source source = sources.get(i);
            AutoCompleteResult a = new AutoCompleteResult(source.getSourcekey(), source.getDescription());
            autos.add(a);
        }
        return autos;
    }

    private ArrayList<AutoCompleteResult> descriptionSearch(String search) {
        List<Source> sources = (List<Source>) HibernateUtil.getSession().createCriteria(Source.class).add(Restrictions.ilike("description", "%" + search + "%"))
                .setProjection(Projections.groupProperty("description"))
                .addOrder(Property.forName("sortorder").asc()).list();
        ArrayList<AutoCompleteResult> autos = new ArrayList<AutoCompleteResult>();
        for (int i = 0; i < sources.size(); i++) {
            Source source = sources.get(i);
            AutoCompleteResult a = new AutoCompleteResult(source.getSourcekey(), source.getDescription());
            autos.add(a);
        }
        return autos;
    }

    private ArrayList<AutoCompleteResult> countrySearch(String search) {
        List<FacetCriterion> cities = null;
        ArrayList<AutoCompleteResult> results = new ArrayList<AutoCompleteResult>();
        if (SourceFacet.countryValues != null) {
            cities = SourceFacet.countryValues;
            for (int i = 0; i < cities.size(); i++) {
                FacetCriterion fc = cities.get(i);
                if (fc.getLabel().toLowerCase().contains(search)) {
                    AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                    results.add(r);
                }
            }
        } else {
            List<Alcountry> countries = HibernateUtil.getSession().createCriteria(Alcountry.class).add(Restrictions.ilike("country", '%' + search + '%')).addOrder(Order.asc("country")).list();
            for (int i = 0; i < countries.size(); i++) {
                Alcountry alc = countries.get(i);
                AutoCompleteResult r = new AutoCompleteResult(alc.getAlcountrykey(), alc.getCountry());
                results.add(r);
            }
        }

        return results;
    }

    private ArrayList<AutoCompleteResult> archiveSearch(String search) {
        ArrayList<AutoCompleteResult> results = new ArrayList<AutoCompleteResult>();
        if (SourceFacet.archiveValues != null) {
            List<FacetCriterion> cities = SourceFacet.archiveValues;
            for (int i = 0; i < cities.size(); i++) {
                FacetCriterion fc = cities.get(i);
                if (fc.getLabel().toLowerCase().contains(search)) {
                    AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                    results.add(r);
                }
            }
        }else{
            List<String> countries = HibernateUtil.getSession().createCriteria(Archive.class).add(Restrictions.ilike("archivename", '%' + search + '%')).addOrder(Order.asc("archivename"))
                    .setProjection(Projections.groupProperty("archivename"))
                    .list();
            for (int i = 0; i < countries.size(); i++) {
                String s =  countries.get(i);
                AutoCompleteResult r = new AutoCompleteResult(0,s);
                results.add(r);
            }
            //.setResultTransformer(Criteria.)
            /*for (int i = 0; i < countries.size(); i++) {
                Archive alc = countries.get(i);
                AutoCompleteResult r = new AutoCompleteResult(alc.getArchivekey(), alc.getArchivename());
                results.add(r);
            }*/
        }
        return results;
    }

    private ArrayList<AutoCompleteResult> citySearch(String search) {
        ArrayList<AutoCompleteResult> results = new ArrayList<AutoCompleteResult>();
        if (SourceFacet.cityValues!=null){
            List<FacetCriterion> cities = SourceFacet.cityValues;
            for (int i = 0; i < cities.size(); i++) {
                FacetCriterion fc = cities.get(i);
                if (fc.getLabel().toLowerCase().contains(search)) {
                    AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                    results.add(r);
                }
            }
        } else{
            List<Alcity> countries = HibernateUtil.getSession().createCriteria(Alcity.class)
                    .add(Restrictions.ilike("city", '%' + search + '%')).addOrder(Order.asc("city")).list();
            for (int i = 0; i < countries.size(); i++) {
                Alcity alc = countries.get(i);
                AutoCompleteResult r = new AutoCompleteResult(alc.getAlcitykey(), alc.getCity());
                results.add(r);
            }
        }
        return results;
        //HibernateUtil.getSession().createCriteria(Alcity.class).add( Restrictions.eq("city", "%"+search+ "%") ).addOrder( Property.forName("city").asc() );
    }

    @Override
    public void addRequestVariables(HttpServletRequest request) {

        if (getValue() > 0) {
            request.setAttribute("sourceSelected", getValue());
        }
    }
}

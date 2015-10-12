package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import org.hibernate.criterion.Property;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.ArchiveFacetType;
import uk.ac.kcl.cch.diamm.facet.type.CityFacetType;
import uk.ac.kcl.cch.diamm.facet.type.CountryFacetType;
import uk.ac.kcl.cch.diamm.facet.type.SourceFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 05-May-2010
 * Time: 16:35:40
 * To change this template use File | Settings | File Templates.
 */
public class SourceFacet extends Facet {

    public static List<FacetCriterion> countryValues;
    public static List<FacetCriterion> cityValues;
    public static List<FacetCriterion> archiveValues;
    public static List<FacetCriterion> sourceValues;


    public SourceFacet(FacetManager manager) {
        super(new SourceFacetType(), manager);
        if (countryValues == null) {
            countryValues = FacetTools.getCachedFacetCriteria(new CountryFacetType());
            if (countryValues == null || countryValues.size() == 0) {
                getCountries();
                if (countryValues != null) {
                    getCountryCounts();
                }
            }
            cityValues = FacetTools.getCachedFacetCriteria(new CityFacetType());
            if (cityValues == null || cityValues.size() == 0) {
                getCities();
                getCityCounts();
            }
            archiveValues = FacetTools.getCachedFacetCriteria(new ArchiveFacetType());
            if (archiveValues == null || archiveValues.size() == 0) {
                getArchives();
                getArchiveCounts();
            }
            sourceValues = new Vector<FacetCriterion>();
            //getSources();
        }
    }

    public static List<FacetCriterion> getSourceValues() {
        return sourceValues;
    }

    public static ArrayList<Integer> getInitialvalues(int key, List<FacetCriterion> values) {
        for (int i = 0; i < values.size(); i++) {
            FacetCriterion facetCriterion = values.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion.getKeys();
            }
        }
        return null;
    }

    public static ArrayList<Integer> getInitialSourceValues(int key) {
        ArrayList<Integer> keys = getInitialvalues(key, sourceValues);
        if (keys == null) {
            FacetCriterion cf = newSourceValue(key);
            keys = cf.getKeys();
            sourceValues.add(cf);
        }
        return keys;
    }

    public static FacetCriterion newSourceValue(int key) {
        ArrayList<Integer> keys = getInitialSourceCount(key);
        Source s = (Source) HibernateUtil.getSession().load(Source.class, new Integer(key));
        FacetCriterion cf = new FacetCriterion(keys, new SourceFacetType(), s.getSourcekey(), s.getSourcename());
        return cf;
    }

    public static FacetCriterion getSourceValue(int key) {
        FacetCriterion fc = DIAMMFacetManager.getInitialCriterion(key, sourceValues);
        if (fc == null) {
            fc = newSourceValue(key);
            sourceValues.add(fc);
        }
        return fc;
    }

    public static FacetCriterion getCountryValue(int key) {
        FacetCriterion fc=null;
        if (countryValues!=null){
            fc = DIAMMFacetManager.getInitialCriterion(key, countryValues);
        }
        if (fc == null) {
            fc = FacetTools.getCachedFacetCriteria(new CountryFacetType(), key);
        }
        return fc;
    }

    public static FacetCriterion getCityValue(int key) {
        FacetCriterion fc = null;
        if (cityValues != null) {
            fc = DIAMMFacetManager.getInitialCriterion(key, cityValues);
        }
        if (fc == null) {
            fc = FacetTools.getCachedFacetCriteria(new CityFacetType(), key);
        }
        return fc;
    }

    public static FacetCriterion getArchiveValue(int key) {
        FacetCriterion fc = null;
        if (archiveValues != null) {
            fc = DIAMMFacetManager.getInitialCriterion(key, archiveValues);

        }
        if (fc == null) {
            fc = FacetTools.getCachedFacetCriteria(new ArchiveFacetType(), key);
        }
        return fc;
    }


    protected static void getCountries() {
        System.out.println("Building Country count cache");
        List<Alcountry> alcountries = HibernateUtil.getSession().createCriteria(Alcountry.class).list();
        List<FacetCriterion> countries = new Vector<FacetCriterion>();
        for (int i = 0; i < alcountries.size(); i++) {
            Alcountry alcountry = alcountries.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>();//getInitialCountryCount(alcountry.getAlcountrykey());
            FacetCriterion cf = new FacetCriterion(keys, new SourceFacetType(), alcountry.getAlcountrykey(), alcountry.getCountry());
            countries.add(cf);
        }
        System.out.println("Building Country count cache FINISHED");
        countryValues = countries;
    }

    protected static void getCities() {
        System.out.println("Building City count cache");
        List<Alcity> alcities = HibernateUtil.getSession().createCriteria(Alcity.class).list();
        List<FacetCriterion> cities = new Vector<FacetCriterion>();
        for (int i = 0; i < alcities.size(); i++) {
            Alcity city = alcities.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>();//getInitialCityCount(city.getAlcitykey());
            FacetCriterion cf = new FacetCriterion(keys, new SourceFacetType(), city.getAlcitykey(), city.getCity());
            cities.add(cf);
        }
        System.out.println("Building City count cache FINISHED");
        cityValues = cities;
    }

    protected static void getArchives() {
        System.out.println("Building ARCHIVE count cache");
        List<Archive> archives = HibernateUtil.getSession().createCriteria(Archive.class).list();
        List<FacetCriterion> arc = new Vector<FacetCriterion>();
        for (int i = 0; i < archives.size(); i++) {
            Archive a = archives.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>(); //getInitialArchiveCount(a.getArchivekey());
            FacetCriterion cf = new FacetCriterion(keys, new SourceFacetType(), a.getArchivekey(), a.getArchivename());
            arc.add(cf);
        }
        System.out.println("Building ARCHIVE count cache FINISHED");
        archiveValues = arc;
    }

    protected static void getSources() {
        System.out.println("Building Source count cache");
        //.addOrder(Property.forName("itemkey").asc())
        /*
                .add(Restrictions.sqlRestriction(" exists (select * from Source where Source.sourceKey=Item.sourceKey)"))
                .addOrder(Property.forName("sourcekey").asc()).list();*/
        List<Source> alcountries = HibernateUtil.getSession().createCriteria(Source.class).addOrder(Property.forName("sourcekey").asc()).list();
        List<FacetCriterion> countries = new Vector<FacetCriterion>();
        for (int i = 0; i < alcountries.size(); i++) {
            Source s = alcountries.get(i);
            //Get count
            Iterator<Item> items = s.getItemsBySourcekey().iterator();
            ArrayList<Integer> keys = new ArrayList<Integer>();
            while (items.hasNext()) {
                Item item = items.next();
                keys.add(item.getItemkey());
            }
            FacetCriterion cf = new FacetCriterion(keys, new SourceFacetType(), s.getSourcekey(), s.getShelfmark());
            //DIAMMFacetManager.getInitialCriterion(item.getSourcekey(), countries);
            countries.add(cf);
            /*if (cf==null){
                ArrayList<Integer> keys=new ArrayList<Integer>();


            }
            cf.getKeys().add(item.getItemkey());
             = getInitialSourceCount(s.getSourcekey());
            FacetCriterion
            ;*/
        }
        System.out.println("Building Source count cache FINISHED");
        sourceValues = countries;
    }

    /*
    protected static ArrayList<Integer> getInitialCountryCount(int key) {
        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.archiveByArchivekey as archive";
        fullHql += " join archive.alcityByAlcitykey as alcity";
        fullHql += " join alcity.alcountryByAlcountrykey as alcountry";
        fullHql += " WHERE alcountry.alcountrykey=" + key;
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected static ArrayList<Integer> getInitialCityCount(int key) {
        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.archiveByArchivekey as archive";
        fullHql += " join archive.alcityByAlcitykey as alcity";
        fullHql += " WHERE alcity.id=" + key;
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected static ArrayList<Integer> getInitialArchiveCount(int key) {
        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.archiveByArchivekey as archive";
        fullHql += " WHERE archive.id=" + key;
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    } */


    protected void getCityCounts() {
        String fullHql = "SELECT item.itemkey,alcity.id from Item as item "
         + " join item.sourceBySourcekey as source"
         + " join source.archiveByArchivekey as archive"
         + " join archive.alcityByAlcitykey as alcity "
                //+" where alcity.id=194"
        + " order by alcity.id";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Object[]> items = query.list();
        for (int i = 0; i < items.size(); i++) {
            Object[] fields = items.get(i);
            String iKey = fields[0] + "";
            String cKey = fields[1] + "";

            FacetCriterion fc = getCriterionByKey(Integer.parseInt(cKey), 2);
            if (fc != null) {
                fc.getKeys().add(Integer.parseInt(iKey));
            }
        }
        //Cull criteria with no items
        for (int i = 0; i < cityValues.size(); i++) {
            FacetCriterion fc = cityValues.get(i);
            if (fc.getKey()==194){
                int stop=0;
            }
            if (fc.getKeys().size() == 0) {
                cityValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
            }
            //TEMPORARILY CHANGE TYPE
            fc.setFacetType(new CityFacetType());
        }
        FacetTools.insertCachedFacetCriteria(cityValues);
        //Change back
        for (int i = 0; i < cityValues.size(); i++) {
            FacetCriterion fc = cityValues.get(i);
            if (fc.getKey()==194){
                int stop=0;
            }
            fc.setFacetType(new SourceFacetType());
        }

    }





    protected void getCountryCounts() {
        String fullHql = "SELECT item.itemkey,alcountry.id from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.archiveByArchivekey as archive";
        fullHql += " join archive.alcityByAlcitykey as alcity";
        fullHql += " join alcity.alcountryByAlcountrykey as alcountry";
        fullHql += " order by alcountry.id";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Object[]> items = query.list();
        for (int i = 0; i < items.size(); i++) {
            Object[] fields = items.get(i);
            String iKey = fields[0] + "";
            String cKey = fields[1] + "";
            FacetCriterion fc = getCriterionByKey(Integer.parseInt(cKey), 1);
            if (fc != null) {
                fc.getKeys().add(Integer.parseInt(iKey));
            }
        }
        //Cull criteria with no items
        for (int i = 0; i < countryValues.size(); i++) {
            FacetCriterion fc = countryValues.get(i);
            if (fc.getKeys().size() == 0) {
                countryValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
            }
            //TEMPORARILY CHANGE TYPE
            fc.setFacetType(new CountryFacetType());
        }
        FacetTools.insertCachedFacetCriteria(countryValues);
        //Change back
        for (int i = 0; i < countryValues.size(); i++) {
            FacetCriterion fc = countryValues.get(i);
            fc.setFacetType(new SourceFacetType());
        }

    }


    protected void getArchiveCounts() {
        String fullHql = "SELECT item.itemkey,archive.archivekey,archive.alcitykey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.archiveByArchivekey as archive";
        fullHql += " order by archive.archivekey";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Object[]> items = query.list();
        for (int i = 0; i < items.size(); i++) {
            Object[] fields = items.get(i);
            String iKey = fields[0] + "";
            String cKey = fields[1] + "";
            String cityKey = fields[2] + "";
            /*FacetCriterion city = getCriterionByKey(Integer.parseInt(cityKey), 2);
            if (city != null) {
                city.getKeys().add(Integer.parseInt(iKey));
            }*/
            FacetCriterion fc = getCriterionByKey(Integer.parseInt(cKey), 3);
            if (fc != null) {
                fc.getKeys().add(Integer.parseInt(iKey));
            }
        }
        //Cull criteria with no items
        for (int i = 0; i < archiveValues.size(); i++) {
            FacetCriterion fc = archiveValues.get(i);
            if (fc.getKeys().size() == 0) {
                archiveValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
            }
            //TEMPORARILY CHANGE TYPE
            fc.setFacetType(new ArchiveFacetType());
        }
        FacetTools.insertCachedFacetCriteria(archiveValues);
        for (int i = 0; i < archiveValues.size(); i++) {
            FacetCriterion fc = archiveValues.get(i);
            fc.setFacetType(new SourceFacetType());
        }

        /*for (int i = 0; i < cityValues.size(); i++) {
            FacetCriterion fc = cityValues.get(i);
            if (fc.getKeys().size() == 0) {
                cityValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
            }
            fc.setFacetType(new CityFacetType());
        }
        FacetTools.insertCachedFacetCriteria(cityValues);
        for (int i = 0; i < cityValues.size(); i++) {
            FacetCriterion fc = cityValues.get(i);
            fc.setFacetType(new SourceFacetType());
        }*/
    }


    protected static ArrayList<Integer> getInitialSourceCount(int key) {
        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " WHERE source.id=" + key;
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    public String[] valueParameters = {"alCountryKey", "alCityKey", "archiveKey", "sourceKey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }


    protected void populateStringConstraintFields() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    protected int level;
    protected String constraintString;
    protected int value;
    protected ArrayList<Integer> itemKeys;

    public ArrayList<Integer> getItemKeys() {
        /*if (getState() != FacetState.EMPTY && itemKeys == null) {

        }*/
        return itemKeys;
    }

    public void setItemKeys(ArrayList<Integer> itemKeys) {
        this.itemKeys = itemKeys;
    }


    public String getConstraintString() {
        return constraintString;
    }

    public void setConstraintString(String constraintString) {
        this.constraintString = constraintString;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /* public Boolean setConstraint(QueryMaker q) {
        return setConstraint(q, getValue(), getConstraintString(), getLevel());
    }*/

    /**
     * <p>Queries the database for results using SQL generated by the <code>QueryMaker</code> (@see
     * QueryMaker) and within this method itself.</p>
     * <p/>
     * <p>Invoked only if the <code>Facet</code> is the target of the user's request - that is to say,
     * that the user wishes to <em>retrieve</em> the values of this <code>Facet</code>, not use a value
     * from it to act as a constraint on some other range of values.</p>
     *
     * @param q                A <code>QueryMaker</code>, used to store the SQL clauses given to it by
     *                         constraint <code>Facet</codes>s, and to generate the text of the final SQL query.
     * @param constraintString The string (from, for example, a letterpicker or text input box) acting
     *                         as a constraint upon the values returned, if any.
     * @param level            In hierarchical facets, the level of the results to be returned; otherwise, 1.
     * @return whether the clauses added will have any effect upon the results of the query. <code>True
     *         </code> except under very specialized circumstances.
     */
    /*public Boolean setConstraint(QueryMaker q, int value, String constraintString, int level) {
        updateConstraints(value, constraintString, level);
        boolean changed = buildItemKeys();
        if (getItemKeys() != null) {
            q.setInClause(DIAMMFacetManager.getMasterKeyName(), getItemKeys());
        }
        return changed;
    }
*/

    /**
     * Runs a query based on current constraint settings
     * and populates the item key list
     *
     * @return
     */
    protected boolean buildItemKeys() {
        boolean changed = false;
        if ((this.getState() == FacetState.UPDATED) || (this.getState() == FacetState.REDRAW)) {
            setItemKeys(null);
            switch (level) {
                case 1:
                    changed = true;
                    setCountryConstraint(value, constraintString, level);
                    break;
                case 2:
                    changed = true;
                    setCityConstraint(value, constraintString, level);
                    break;
                case 3:
                    changed = true;
                    setArchiveConstraint(value, constraintString, level);
                    break;
                case 4:
                    changed = true;
                    setSourceConstraint(value, constraintString, level);
                    break;
            }
        }
        return changed;
    }


    protected void updateConstraints(int value, String constraintString, int level) {
        if (level != this.level) {
            setLevel(level);
            setUpdated();
        }
        if (value != getValue()) {
            setValue(value);
            setUpdated();
        }
        if (constraintString != null && !constraintString.equalsIgnoreCase(constraintString)) {
            setConstraintString(constraintString);
            setUpdated();
        }
    }

    public FacetCriterion getCriterionByKey(int key, int level) {
        switch (level) {
            case 1:
                return getCountryValue(key);
            case 2:
                return getCityValue(key);
            case 3:
                return getArchiveValue(key);
            case 4:
                return getSourceValue(key);
        }
        return null;
    }

    public void resetFacet() {
        setLevel(0);
        setValue(0);
        setItemKeys(null);
        setConstraintString("");
        setState(FacetState.EMPTY);
    }

    protected boolean setCountryConstraint(int value, String matchString, int level) {
        /*ArrayList<Integer> itemKeys;
        String fullHql = "SELECT DISTINCT item from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.archiveByArchivekey as archive";
        fullHql += " join archive.alcityByAlcitykey as alcity";
        fullHql += " join alcity.alcountryByAlcountrykey as alcountry";
        fullHql += " WHERE alcountry.alcountrykey=" + value;
        *//*ArrayList<Integer> queryItemKeys = q.getInClauseValues(Constants.itemClauseName);
        if (queryItemKeys != null && queryItemKeys.size() > 0) {
            fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + queryItemKeys.toString() + ")";
        }*//*
        //fullHql = "SELECT DISTINCT item from Item as item limit 10";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Item> items = query.list();
        itemKeys = DIAMMFacetManager.getKeyList(items);
        if (itemKeys != null && itemKeys.size() > 0) {
            setItemKeys(itemKeys);
        }*/
        ArrayList<Integer> itemKeys = getInitialvalues(value, countryValues);
        setItemKeys(itemKeys);
        return true;
    }

    /*
   SELECT DISTINCT item from Item as item
   join item.sourceBySourcekey as source
   join source.archiveByArchivekey as archive
   join archive.alcityByAlcitykey as alcity
   join alcity.alcountryByAlcountrykey as alcountry
   WHERE alcountry.alcountrykey=1
    */
    public ArrayList<Integer> getResultSetKeys() {
        if (this.getState() == FacetState.UPDATED) {
            buildItemKeys();
        }
        return getItemKeys();
    }

    protected boolean setCityConstraint(int value, String matchString, int level) {
        ArrayList<Integer> itemKeys = getInitialvalues(value, cityValues);
        setItemKeys(itemKeys);
        return true;
    }

    protected boolean setArchiveConstraint(int value, String matchString, int level) {
        ArrayList<Integer> itemKeys = getInitialvalues(value, archiveValues);
        setItemKeys(itemKeys);
        return true;
    }

    protected boolean setSourceConstraint(int value, String matchString, int level) {
        ArrayList<Integer> itemKeys = getInitialSourceValues(value);
        setItemKeys(itemKeys);
        return true;
    }

    public boolean updateConstraintsFromRequest(HttpServletRequest request) {
        boolean changed = false;
        String[] params = getValueParameters();
        for (int i = 0; i < params.length; i++) {
            String valueParameter = params[i];
            if (request.getParameter(valueParameter) != null && request.getParameter(valueParameter).length() > 0) {
                String param = request.getParameter(valueParameter);
                int val = 0;
                if (param.matches("^\\d+$")) {
                    try {
                        val = Integer.parseInt(param);
                    } catch (NumberFormatException e) {

                    }
                }
                if (val > 0) {
                    updateConstraints(val, "", (i + 1));
                } else if (param != null && param.length() > 0) {
                    updateConstraints(0, param, (i + 1));
                    //Set to Empty
                    //setState(FacetState.EMPTY);
                }
                changed = true;
            }
        }
        return changed;
    }
}

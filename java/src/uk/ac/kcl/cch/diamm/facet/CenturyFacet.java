package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.facet.type.CenturyFacetType;
import uk.ac.kcl.cch.diamm.facet.type.SourceFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Item;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 15-Nov-2010
 * Time: 11:27:01
 * To change this template use File | Settings | File Templates.
 */
public class CenturyFacet extends Facet {
    public static List<FacetCriterion> centuryValues;
    protected int level;
    protected String constraintString;
    protected int value;
    protected int century1;
    protected int century2;

    public static final Integer[] centuries = {10,11, 12, 13, 14, 15, 16, 17};

    public String[] valueParameters = {"century1", "century2"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }


    public CenturyFacet(FacetManager manager) {
        super(new CenturyFacetType(), manager);
        century1 = 0;
        century2 = 0;
        /*if (centuryValues == null) {
            centuryValues = getCenturys();
        }*/
    }

    protected ArrayList<Integer> getInitialCenturyCount(int key) {
        Criteria c = HibernateUtil.getSession().createCriteria(Item.class);
        c.createCriteria("sourceBySourcekey").add(Restrictions.sqlRestriction("dateOfSource regexp '^.*" + key + "th century.*$'"));
        List<Item> items = (List<Item>) c.list();
        ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        /*String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " ";
        fullHql += " WHERE source.dateofsource regexp '^.*" + key + "th century.*$'";
        *//*if (constraintKeys != null && constraintKeys.size() > 0) {
           fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
       } *//*
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        //List<Item> items = query.list();
        //ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();*/
        return keys;
    }

    protected List<FacetCriterion> sortCenturies(List<FacetCriterion> cents) {
        Criteria c = HibernateUtil.getSession().createCriteria(Source.class)
                .add(Restrictions.sqlRestriction("dateOfSource IS NOT NULL"));
        List<Source> sources = (List<Source>) c.list();
        SourceFacet sf=(SourceFacet) getManager().getFacetByType(new SourceFacetType());
        for (int i = 0; i < cents.size(); i++) {
            FacetCriterion cent = cents.get(i);
            for (int j = 0; j < sources.size(); j++) {
                Source s = sources.get(j);
                if (s.getDateofsource().matches(".*"+cent.getKey() + "\\d+.*") || s.getDateofsource().indexOf(cent.getKey() + "th")>-1) {
                    ArrayList<Integer> sKeys=sf.getInitialSourceValues(s.getSourcekey());
                    if (sKeys!=null){
                        if (cent.getKeys().size()==0){
                            cent.setKeys(sKeys);
                        }else{
                            cent.getKeys().addAll(sKeys);
                        }
                    }
                }
            }
        }
        return cents;
    }

    protected List<FacetCriterion> getCenturys() {
        System.out.println("Building Centuries count cache");
        List<FacetCriterion> cents = new ArrayList<FacetCriterion>();
        for (int i = 0; i < centuries.length; i++) {
            Integer century = centuries[i];
            ArrayList<Integer> keys = new ArrayList<Integer>();//getInitialCenturyCount(century);

                FacetCriterion cf = new FacetCriterion(keys, new CenturyFacetType(), century, century + "th century");
                cents.add(cf);
            

        }
        cents=sortCenturies(cents);
        System.out.println("Building Centuries count cache FINISHED");
        return cents;
    }

    public FacetCriterion getCriterionByKey(int key, int level) {
        if (value>0){
            int count=getResultSetKeys().size();
            String label=value+"th";
            if (century2>0){
                label=value+"th-"+century2+"th";
            }
            FacetCriterion fc=new FacetCriterion(count,getType(),value,label);
            return fc;
        }
        /*if (centuryValues != null && centuryValues.size() > 0) {
            for (int i = 0; i < centuryValues.size(); i++) {
                FacetCriterion facetCriterion = centuryValues.get(i);
                if (facetCriterion.getKey() == key) {
                    return facetCriterion;
                }
            }
        }*/
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getLevel() {
        return level;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ArrayList<Integer> getResultSetKeys() {
        if (this.getState() == FacetState.UPDATED) {
            buildItemKeys();
            setState(FacetState.SELECTED);
        }
        return getItemKeys();
    }

    protected ArrayList<Integer> getCenturyKeys(int c1,int c2){
        ArrayList<Integer> itemKeys=new ArrayList<Integer>();
         String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";

        fullHql += " WHERE source.startdate>=" + c1;
        if (c2>0){
            fullHql+=" and source.enddate<="+c2;
        }
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        itemKeys = (ArrayList<Integer>) query.list();
        return itemKeys;
    }


    protected boolean buildItemKeys() {
        boolean changed = false;
        if ((this.getState() == FacetState.UPDATED) || (this.getState() == FacetState.REDRAW)) {
            if (century1 == 11 && century2 == 17) {
                //Full range, ignore
                setItemKeys(new ArrayList<Integer>());
            } else {
                changed = true;
                ArrayList<Integer> itemKeys=getCenturyKeys(century1,century2);
                /*ArrayList<Integer> itemKeys = getInitialCenturyCount(value);
                if (century2 > 0 && century1 != century2) {
                    for (int i = century1; i < century2; i++) {
                        ArrayList<Integer> moreKeys = getInitialCenturyCount(i);
                        for (int j = 0; j < moreKeys.size(); j++) {
                            Integer integer = moreKeys.get(j);
                            if (!itemKeys.contains(integer)) {
                                itemKeys.add(integer);
                            }
                        }

                    }
                }*/
                setItemKeys(itemKeys);
            }
            setState(FacetState.SELECTED);
        }
        return changed;
    }

    protected ArrayList<Integer> itemKeys;

    public ArrayList<Integer> getItemKeys() {

        return itemKeys;
    }

    public void setItemKeys(ArrayList<Integer> itemKeys) {
        this.itemKeys = itemKeys;
    }

    public int getValue() {
        return value;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void resetFacet() {
        setLevel(0);
        setValue(0);
        setItemKeys(null);
        setConstraintString("");
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

    public String getConstraintString() {
        return constraintString;
    }

    public void setConstraintString(String constraintString) {
        this.constraintString = constraintString;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean updateConstraintsFromRequest(HttpServletRequest request) {
        boolean changed = false;
        String[] params = getValueParameters();
        String c1 = request.getParameter(params[0]);
        String c2 = request.getParameter(params[1]);
        if (c1 != null&&c1.length()>0) {
            century1 = Integer.parseInt(c1);
            setValue(century1);
            setUpdated();
            changed=true;
        }
        if (c2 != null&&c2.length()>0) {
            century2 = Integer.parseInt(c2);
            setUpdated();
            changed=true;
        }
        /*for (int i = 0; i < params.length; i++) {
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
        }*/
        return changed;
    }

    public int getCentury1() {
        return century1;
    }

    public void setCentury1(int century1) {
        this.century1 = century1;
    }



    public int getCentury2() {
        return century2;
    }

    public void setCentury2(int century2) {
        this.century2 = century2;
    }

    public static List<FacetCriterion> getCenturyValues() {
        return centuryValues;
    }

    public static void setCenturyValues(List<FacetCriterion> centuryValues) {
        CenturyFacet.centuryValues = centuryValues;
    }
}

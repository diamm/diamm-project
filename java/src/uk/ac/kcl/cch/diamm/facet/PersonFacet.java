package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import org.hibernate.criterion.Property;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.PersonFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alperson;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 15-Nov-2010
 * Time: 11:27:01
 * To change this template use File | Settings | File Templates.
 */
public class PersonFacet extends Facet {
    public static List<FacetCriterion> personValues;
    protected int level;
    protected String constraintString;
    protected int value;


    public String[] valueParameters = {"alpersonkey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }

    public PersonFacet(FacetManager manager) {
        super(new PersonFacetType(), manager);
        if (personValues == null) {
            if (!FacetTools.criteriaCached(new PersonFacetType())){
                personValues = getPersons();
                if (personValues != null) {
                    addCounts();
                }
                FacetTools.insertCachedFacetCriteria(personValues);
            }
        }
    }

    protected ArrayList<Integer> getInitialPersonCount(int key) {

        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.sourcealpersonsBySourcekey as sourceperson";
        fullHql += " join sourceperson.alpersonByAlpersonkey as person";
        fullHql += " ";
        fullHql += " WHERE person.alpersonkey=" + key;
        /*if (constraintKeys != null && constraintKeys.size() > 0) {
            fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
        } */
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        //List<Item> items = query.list();
        //ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected static FacetCriterion getCritByKey(int key,List<FacetCriterion> values){
        if (values!=null){
            for (int i = 0; i < values.size(); i++) {
                FacetCriterion fCrit =  values.get(i);
                if (fCrit.getKey()==key){
                    return fCrit;
                }
            }
        }
        return null;
    }

    protected void addCounts() {
        String fullHql = "SELECT DISTINCT item.itemkey,sp.alpersonkey from Item as item"
                //addJoin("join photodata.intermedium as intermedium ");
                + " join item.sourceBySourcekey as source"
                + " join source.sourcealpersonsBySourcekey as sp"
                + " order by sp.alpersonkey";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Object[]> items = query.list();
        for (int i = 0; i < items.size(); i++) {
            Object[] fields = items.get(i);
            String iKey = fields[0] + "";
            String cKey = fields[1] + "";
            FacetCriterion fc = FacetTools.getCritByKey(Integer.parseInt(cKey),personValues); //getCriterionByKey(Integer.parseInt(cKey), 0);
            if (fc != null) {
                fc.getKeys().add(Integer.parseInt(iKey));
            }
        }
        //Cull criteria with no items
        ArrayList<FacetCriterion> newList = new ArrayList<FacetCriterion>();
        for (int i = 0; i < personValues.size(); i++) {
            FacetCriterion fc = personValues.get(i);
            if (fc.getKeys().size() <= 0) {
                personValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
                newList.add(fc);
            }
        }
        personValues = newList;
    }


    protected List<FacetCriterion> getPersons() {
        System.out.println("Building Person count cache");
        List<Alperson> composers = HibernateUtil.getSession().createCriteria(Alperson.class).addOrder(Property.forName("fullname").asc()).list();
        List<FacetCriterion> comps = new Vector<FacetCriterion>();
        for (int i = 0; i < composers.size(); i++) {
            Alperson c = composers.get(i);
            if (c.getFullname() != null && c.getFullname().length() > 0) {
                //Get count
                ArrayList<Integer> keys = new ArrayList<Integer>();//getInitialPersonCount(c.getAlpersonkey());
                FacetCriterion cf = new FacetCriterion(keys, new PersonFacetType(), c.getAlpersonkey(), c.getFullname() != null && c.getFullname().length() > 30 ? c.getFullname().substring(0, 30) + "..." : c.getFullname());
                comps.add(cf);
            }
        }
        System.out.println("Building Person count cache FINISHED");
        return comps;
    }


    public FacetCriterion getCriterionByKey(int key, int level) {
        return FacetTools.getCachedFacetCriteria(new PersonFacetType(), key);
        /*for (int i = 0; i < personValues.size(); i++) {
            FacetCriterion facetCriterion = personValues.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion;
            }
        }
        return null;  */
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

    protected boolean buildItemKeys() {
        boolean changed = false;
        if ((this.getState() == FacetState.UPDATED) || (this.getState() == FacetState.REDRAW)) {
            changed = true;
            ArrayList<Integer> itemKeys = (getCriterionByKey(value, 1) != null ? getCriterionByKey(value, 1).getKeys() : null); //getInitialPersonCount(value);
            setItemKeys(itemKeys);
            //setComposerConstraint(value, constraintString, level);
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

    public void setLevel(int level) {
        this.level = level;
    }


    public void setValue(int value) {
        this.value = value;
    }


}
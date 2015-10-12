package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.ClefFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alclef;
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
public class ClefFacet extends Facet {
    public static List<FacetCriterion> clefValues;
    protected int level;
    protected String constraintString;
    protected int value;

    public String[] valueParameters = {"alClefKey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }

    public ClefFacet(FacetManager manager) {
        super(new ClefFacetType(), manager);
        if (clefValues == null) {
            if (!FacetTools.criteriaCached(new ClefFacetType())) {
                clefValues = getSets();
                if (clefValues != null) {
                    addCounts();
                }
                FacetTools.insertCachedFacetCriteria(clefValues);
            }
        }
    }

    protected ArrayList<Integer> getInitialSetCount(int key) {

        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        fullHql += " join item.textsByItemkey as texts";
        fullHql += " join texts.alclefbyalclefkey as clef";
        fullHql += " WHERE clef.alclefkey=" + key;

        Query query = HibernateUtil.getSession().createQuery(fullHql);
        //List<Item> items = query.list();
        //ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected List<FacetCriterion> getSets() {
        System.out.println("Building Set count cache");
        List<FacetCriterion> crits = new ArrayList<FacetCriterion>();
        List<Alclef> sets = HibernateUtil.getSession().createCriteria(Alclef.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        for (int i = 0; i < sets.size(); i++) {
            Alclef clef = sets.get(i);
            ArrayList<Integer> keys = new ArrayList<Integer>();//getInitialSetCount(set.getSetkey());
            // if (keys != null && keys.size() > 0) {
            String label = clef.getClef();//set.getDescription()!=null?set.getDescription()+" ":"";
            FacetCriterion cf = new FacetCriterion(keys, new ClefFacetType(), clef.getAlclefkey(), label);
            crits.add(cf);
            // }
        }
        System.out.println("Building Set count cache FINISHED");
        return crits;
    }

    protected void addCounts() {
        String fullHql = "SELECT DISTINCT item.itemkey,clef.id from Item as item" +
                " join item.textsByItemkey as texts" +
                " join texts.alclefbyalclefkey as clef" +
                " order by clef.id";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Object[]> items = query.list();
        for (int i = 0; i < items.size(); i++) {
            Object[] fields = items.get(i);
            String iKey = fields[0] + "";
            String cKey = fields[1] + "";
            FacetCriterion fc = FacetTools.getCritByKey(Integer.parseInt(cKey), clefValues); //getCriterionByKey(Integer.parseInt(cKey), 0);
            if (fc != null) {
                fc.getKeys().add(Integer.parseInt(iKey));
            }
        }
        //Cull criteria with no items
        ArrayList<FacetCriterion> newList = new ArrayList<FacetCriterion>();
        for (int i = 0; i < clefValues.size(); i++) {
            FacetCriterion fc = clefValues.get(i);
            if (fc.getKeys().size() <= 0) {
                clefValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
                newList.add(fc);
            }
        }
        clefValues = newList;
    }

    public FacetCriterion getCriterionByKey(int key, int level) {
        return FacetTools.getCachedFacetCriteria(new ClefFacetType(), key);
        /*if (clefValues != null && clefValues.size() > 0) {
            for (int i = 0; i < clefValues.size(); i++) {
                FacetCriterion facetCriterion = clefValues.get(i);
                if (facetCriterion.getKey() == key) {
                    return facetCriterion;
                }
            }
        }
        return null;*/
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
            ArrayList<Integer> itemKeys = (getCriterionByKey(value, 1) != null ? getCriterionByKey(value, 1).getKeys() : null);
            setItemKeys(itemKeys);
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
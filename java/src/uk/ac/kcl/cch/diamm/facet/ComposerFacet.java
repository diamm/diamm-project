package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import org.hibernate.criterion.Property;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.ComposerFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Composer;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ComposerFacet extends Facet {
    public static List<FacetCriterion> composerValues;

    public ComposerFacet(FacetManager manager) {
        super(new ComposerFacetType(), manager);
        initVars();
    }

    public void initVars(){
            ComposerFacet.composerValues = FacetTools.getCachedFacetCriteria(new ComposerFacetType());
            if (ComposerFacet.composerValues == null || ComposerFacet.composerValues.size() == 0) {
                ComposerFacet.composerValues = getComposers();
                if (composerValues != null) {
                    addCounts();
                }
                FacetTools.insertCachedFacetCriteria(ComposerFacet.composerValues);
            }
    }

    public void resetFacet() {
        setLevel(0);
        setValue(0);
        setItemKeys(null);
        setConstraintString("");
    }

    public String[] valueParameters = {"composerKey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
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


    /**
     * Runs a query based on current constraint settings
     * and populates the item key list
     *
     * @return
     */
    protected boolean buildItemKeys() {
        boolean changed = false;
        if ((this.getState() == FacetState.UPDATED) || (this.getState() == FacetState.REDRAW)) {
            changed = true;
            setComposerConstraint(value, constraintString, level);
        }
        return changed;
    }

    /*private void setUpdated() {
        if (getState() != FacetState.UPDATED || getState() != FacetState.REDRAW) {
            setState(FacetState.UPDATED);
        }
    }*/

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

    /*protected ArrayList<Integer> getInitialComposerCount(int key) {

        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        fullHql += " join item.compositionByCompositionkey as composition";
        fullHql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
        fullHql += " join compositioncomposer.composerByComposerkey as composer";
        fullHql += " WHERE composer.composerkey=" + key;
        *//*if (constraintKeys != null && constraintKeys.size() > 0) {
            fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
        } *//*
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        //List<Item> items = query.list();
        //ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }*/

    /**
     * Get the initial item counts for every composer in the db.
     */
    protected List<FacetCriterion> getComposers() {
        System.out.println("Building Composer count cache");
        List<Composer> composers = HibernateUtil.getSession().createCriteria(Composer.class).addOrder(Property.forName("firstname").asc()).list();
        List<FacetCriterion> comps = new Vector<FacetCriterion>();
        for (int i = 0; i < composers.size(); i++) {
            Composer c = composers.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>(); //getInitialComposerCount(c.getComposerkey());
            //if (keys!=null&&keys.size()>0){
            FacetCriterion cf = new FacetCriterion(keys, new ComposerFacetType(), c.getComposerkey(), c.getLastname() + (c.getFirstname() != null && c.getFirstname().length() > 0 ? ", " + c.getFirstname() : ""));
            comps.add(cf);
            //}                             
        }
        System.out.println("Building Composer count cache FINISHED");
        return comps;
    }

    protected void addCounts() {
        String fullHql = "SELECT item.itemkey,comp.composerkey from Item as item,Compositioncomposer as comp";
        fullHql += " WHERE item.compositionkey=comp.compositionkey";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Object[]> items = query.list();
        for (int i = 0; i < items.size(); i++) {
            Object[] fields = items.get(i);
            String iKey = fields[0] + "";
            String cKey = fields[1] + "";
            FacetCriterion fc = getCriterionByKey(Integer.parseInt(cKey), 0);
            if (fc != null) {
                fc.getKeys().add(Integer.parseInt(iKey));
            }
        }
        //Cull criteria with no items
        for (int i = 0; i < composerValues.size(); i++) {
            FacetCriterion fc = composerValues.get(i);
            if (fc.getKeys().size() <= 0) {
                composerValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
            }
        }

    }


    protected boolean setComposerConstraint(int value, String matchString, int level) {
        ArrayList<Integer> itemKeys = new ArrayList<Integer>();
        /*String fullHql = "SELECT DISTINCT item from Item as item";
        fullHql += " join item.compositionByCompositionkey as composition";
        fullHql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
        fullHql += " join compositioncomposer.composerByComposerkey as composer";
        fullHql += " WHERE composer.composerkey=" + value;
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Item> items = query.list();
        itemKeys = DIAMMFacetManager.getKeyList(items);*/
        if (value > 0) {
            itemKeys = (getCriterionByKey(value, 1) != null ? getCriterionByKey(value, 1).getKeys() : null); //getInitialComposerCount(value);
        }
        if (itemKeys != null && itemKeys.size() > 0) {
            setItemKeys(itemKeys);
        }
        return true;
    }

    public FacetCriterion getCriterionByKey(int key, int level) {
        for (int i = 0; i < composerValues.size(); i++) {
            FacetCriterion facetCriterion = composerValues.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion;
            }
        }
        return null;
    }

    public ArrayList<Integer> getResultSetKeys() {
        if (this.getState() == FacetState.UPDATED) {
            buildItemKeys();
            setState(FacetState.SELECTED);
        }
        return getItemKeys();
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

    public List<FacetCriterion> getComposerValues() {
         if (composerValues == null) {
             initVars();
         }
        return composerValues;
    }

    public void setComposerValues(List<FacetCriterion> composerValues) {
        ComposerFacet.composerValues = composerValues;
    }
}

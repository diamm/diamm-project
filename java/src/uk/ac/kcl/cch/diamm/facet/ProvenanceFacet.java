package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.ProvenanceFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alprovenance;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.FacetType;
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
public class ProvenanceFacet extends Facet {
    public static List<FacetCriterion> provenanceValues;
    protected int level;
    protected String constraintString;
    protected int value;

    public String[] valueParameters = {"alprovenancekey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }

    public ProvenanceFacet(FacetManager manager) {
        super(new ProvenanceFacetType(), manager);
        /*if (provenanceValues == null) {
            provenanceValues = FacetTools.getCachedFacetCriteria(new ProvenanceFacetType());
            if (provenanceValues == null||provenanceValues.size()==0) {
                provenanceValues = getProvenances();
                if (provenanceValues != null) {
                    addCounts();
                }
                FacetTools.insertCachedFacetCriteria(provenanceValues);
            }
        }*/
    }

    protected ArrayList<Integer> getInitialProvenanceCount(int key) {

        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.sourceprovenancesBySourcekey as sourceprovenance";
        fullHql += " join sourceprovenance.alprovenanceByAlprovenancekey prov";
        fullHql += " ";
        fullHql += " WHERE prov.alprovenancekey=" + key;
        /*if (constraintKeys != null && constraintKeys.size() > 0) {
            fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
        } */
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        //List<Item> items = query.list();
        //ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected List<FacetCriterion> getProvenances() {
        System.out.println("Building Provenance count cache");
        List<Alprovenance> composers = HibernateUtil.getSession().createCriteria(Alprovenance.class).list();
        List<FacetCriterion> comps = new Vector<FacetCriterion>();
        for (int i = 0; i < composers.size(); i++) {
            Alprovenance c = composers.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>(); //getInitialProvenanceCount(c.getAlprovenancekey());

            //if (keys != null && keys.size() > 0) {
            //todo: check description
            if (c.getCountry()!=null&&c.getCountry().length()>0){
                FacetCriterion cf = new FacetCriterion(keys, new ProvenanceFacetType(), c.getAlprovenancekey(), c.getCountry());
                comps.add(cf);
            }

            //}
        }
        System.out.println("Building Provenance count cache FINISHED");
        return comps;
    }


    protected void addCounts() {
        String fullHql = "SELECT DISTINCT item.itemkey, prov.alprovenancekey from Item as item";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.sourceprovenancesBySourcekey as sourceprovenance";
        fullHql += " join sourceprovenance.alprovenanceByAlprovenancekey prov";
        fullHql += " order by  prov.alprovenancekey";
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
        ArrayList<FacetCriterion> newList = new ArrayList<FacetCriterion>();
        for (int i = 0; i < provenanceValues.size(); i++) {
            FacetCriterion fc = provenanceValues.get(i);
            if (fc.getKeys().size() <= 0) {
                provenanceValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
                newList.add(fc);
            }
        }
        provenanceValues = newList;
    }

    public FacetCriterion getCriterionByKey(int key, int level) {
        return FacetTools.getCachedFacetCriteria(new ProvenanceFacetType(),key);
       /* for (int i = 0; i < provenanceValues.size(); i++) {
            FacetCriterion facetCriterion = provenanceValues.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion;
            }
        }*/
        //return null;  //To change body of implemented methods use File | Settings | File Templates.
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
            ArrayList<Integer> itemKeys = (getCriterionByKey(value, 1) != null ? getCriterionByKey(value, 1).getKeys() : null);//getInitialProvenanceCount(value);
            setItemKeys(itemKeys);
            //setComposerConstraint(value, constraintString, level);
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
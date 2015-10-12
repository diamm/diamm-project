package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import uk.ac.kcl.cch.diamm.facet.type.NotationFacetType;
import uk.ac.kcl.cch.diamm.facet.type.LanguageFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alnotationtype;
import uk.ac.kcl.cch.diamm.model.Allanguage;
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
public class NotationFacet extends Facet {
    public static List<FacetCriterion> notationValues;
    protected int level;
    protected String constraintString;
    protected int value;

    public String[] valueParameters = {"alnotationtypekey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }

    public NotationFacet(FacetManager manager) {
        super(new NotationFacetType(), manager);
        if (notationValues==null){
            notationValues=getNotations();
            if (notationValues!=null){
                addCounts();
            }
        }

    }

    protected ArrayList<Integer> getInitialNotationCount(int key) {

        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";

        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.alnotationtypes as notation";
        fullHql += " WHERE notation.alnotationtypekey=" + key;

        Query query = HibernateUtil.getSession().createQuery(fullHql);

        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected List<FacetCriterion> getNotations() {
        System.out.println("Building Notations count cache");
        List<FacetCriterion> crits = new ArrayList<FacetCriterion>();
        List<Alnotationtype> notes = HibernateUtil.getSession().createCriteria(Alnotationtype.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        for (int i = 0; i < notes.size(); i++) {
            Alnotationtype note = notes.get(i);
            ArrayList<Integer> keys = new ArrayList<Integer>();//getInitialNotationCount(note.getAlnotationtypekey());
            //if (keys != null && keys.size() > 0) {
                //todo: check description
                FacetCriterion cf = new FacetCriterion(keys, new NotationFacetType(), note.getAlnotationtypekey(), (note.getNotationType()!=null&&note.getNotationType().length()>30?note.getNotationType().substring(0,30):note.getNotationType()));
                crits.add(cf);
            //}

        }
        System.out.println("Building Notations count cache FINISHED");
        return crits;
    }


    protected void addCounts() {
            String fullHql = "SELECT DISTINCT item.itemkey,nsources.alnotationtypekey from Item as item";

        fullHql += " join item.sourceBySourcekey as source";
        fullHql += " join source.notationsources as nsources";
       // fullHql += " join nsources.alnotationtype as notation";
        fullHql+=" WHERE nsources.alnotationtypekey>0";
        fullHql+=" order by nsources.alnotationtypekey";
            Query query = HibernateUtil.getSession().createQuery(fullHql);
            List<Object[]> items = query.list();
            for (int i = 0; i < items.size(); i++) {
                Object[] fields = items.get(i);
                String iKey=fields[0]+"";
                String cKey=fields[1]+"";
                FacetCriterion fc = getCriterionByKey(Integer.parseInt(cKey), 0);
                if (fc != null) {
                    fc.getKeys().add(Integer.parseInt(iKey));
                }
            }
            //Cull criteria with no items
        ArrayList<FacetCriterion> newList=new ArrayList<FacetCriterion>();
            for (int i = 0; i < notationValues.size(); i++) {
                FacetCriterion fc = notationValues.get(i);
                if (fc.getKeys().size()<=0){
                    notationValues.remove(fc);
                }else{
                    fc.setCount(fc.getKeys().size());
                    newList.add(fc);
                }
            }
        notationValues=newList;
        }




    public FacetCriterion getCriterionByKey(int key, int level) {
        if (notationValues != null && notationValues.size() > 0) {
            for (int i = 0; i < notationValues.size(); i++) {
                FacetCriterion facetCriterion = notationValues.get(i);
                if (facetCriterion.getKey() == key) {
                    return facetCriterion;
                }
            }
        }
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

    protected boolean buildItemKeys() {
        boolean changed = false;
        if ((this.getState() == FacetState.UPDATED) || (this.getState() == FacetState.REDRAW)) {
            changed = true;
            ArrayList<Integer> itemKeys =(getCriterionByKey(value,1)!=null?getCriterionByKey(value,1).getKeys():null); // getInitialNotationCount(value);
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
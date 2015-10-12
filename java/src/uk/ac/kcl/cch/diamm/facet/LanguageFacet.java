package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import org.hibernate.criterion.Property;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.LanguageFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
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
public class LanguageFacet extends Facet {
    public static List<FacetCriterion> languageValues;
    protected int level;
    protected String constraintString;
    protected int value;

    public LanguageFacet(FacetManager manager) {
        super(new LanguageFacetType(), manager);
        if (languageValues == null) {
            if (!FacetTools.criteriaCached(new LanguageFacetType())) {
                languageValues = getlanguages();
                if (languageValues != null) {
                    addCounts();
                }
                FacetTools.insertCachedFacetCriteria(languageValues);
            }
        }
    }

    public String[] valueParameters = {"allanguagekey"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }


    protected ArrayList<Integer> getInitialLanguageCount(int key) {

        String fullHql = "SELECT DISTINCT text.itemkey from Text as text";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join text.textlanguagesByTextkey as textlang";
        fullHql += " join textlang.allanguageByAllanguagekey as lang";

        fullHql += " ";
        fullHql += " WHERE lang.allangaugekey=" + key;
        /*if (constraintKeys != null && constraintKeys.size() > 0) {
           fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
       } */
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        //List<Item> items = query.list();
        //ArrayList<Integer> keys = DIAMMFacetManager.getKeyList(items);
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    protected void addCounts() {
        String fullHql = "SELECT DISTINCT text.itemkey,lang.allangaugekey from Text as text";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join text.textlanguagesbytextkey as textlang";
        fullHql += " join textlang.allanguagebyallanguagekey as lang";
        fullHql += " order by lang.allangaugekey";
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
        for (int i = 0; i < languageValues.size(); i++) {
            FacetCriterion fc = languageValues.get(i);
            if (fc.getKeys().size() <= 0) {
                languageValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
                newList.add(fc);
            }
        }
        languageValues = newList;
    }

    protected List<FacetCriterion> getlanguages() {
        System.out.println("Building Language count cache");
        List<Allanguage> composers = HibernateUtil.getSession().createCriteria(Allanguage.class).addOrder(Property.forName("allangaugekey").asc()).list();
        List<FacetCriterion> comps = new Vector<FacetCriterion>();
        for (int i = 0; i < composers.size(); i++) {
            Allanguage c = composers.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>(); //getInitialLanguageCount(c.getAllangaugekey());

            //if (keys != null && keys.size() > 0) {
            //todo: check description
            FacetCriterion cf = new FacetCriterion(keys, new LanguageFacetType(), c.getAllangaugekey(), c.getLanguage());
            comps.add(cf);
            //}
        }
        System.out.println("Building Language count cache FINISHED");
        return comps;
    }


    public FacetCriterion getCriterionByKey(int key, int level) {
        if (languageValues != null) {
            for (int i = 0; i < languageValues.size(); i++) {
                FacetCriterion facetCriterion = languageValues.get(i);
                if (facetCriterion.getKey() == key) {
                    return facetCriterion;
                }
            }
        }else{
           return FacetTools.getCachedFacetCriteria(getType(),key);
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
            ArrayList<Integer> itemKeys = (getCriterionByKey(value, 1) != null ? getCriterionByKey(value, 1).getKeys() : null); //getInitialLanguageCount(value);
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
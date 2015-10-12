package uk.ac.kcl.cch.diamm.facet;

import org.hibernate.Query;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.type.GenreFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Algenre;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GenreFacet extends Facet {
    public static List<FacetCriterion> genreValues;

    public GenreFacet(FacetManager manager) {
        super(new GenreFacetType(), manager);
        if (GenreFacet.genreValues == null) {
            if (!FacetTools.criteriaCached(new GenreFacetType())) {
                if (GenreFacet.genreValues == null || genreValues.size() == 0) {
                    GenreFacet.genreValues = getGenres();
                }
                if (genreValues != null) {
                    getGenreCounts();
                }
                FacetTools.insertCachedFacetCriteria(genreValues);
            }else{
                genreValues= FacetTools.getCachedFacetCriteria(new GenreFacetType());
            }
        }
    }

    public void resetFacet() {
        setLevel(0);
        setValue(0);
        setItemKeys(null);
        setConstraintString("");
        setState(FacetState.EMPTY);
    }

    public String[] valueParameters = {"alGenreKey"};

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
        ArrayList<Integer> itemKeys = new ArrayList<Integer>();
        if ((this.getState() == FacetState.UPDATED) || (this.getState() == FacetState.REDRAW)) {
            changed = true;
            if (value > 0) {
                itemKeys = (getCriterionByKey(value, 1) != null ? getCriterionByKey(value, 1).getKeys() : null); //getInitialComposerCount(value);
            }
            //ArrayList<Integer> itemKeys = getInitialGenreCount(value);
            setItemKeys(itemKeys);
            //setComposerConstraint(value, constraintString, level);
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

    protected ArrayList<Integer> getInitialGenreCount(int key) {

        String fullHql = "SELECT DISTINCT item.itemkey from Item as item";
        fullHql += " join item.compositionByCompositionkey as composition";
        fullHql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
        fullHql += " join compositioncomposer.composerByComposerkey as composer";
        fullHql += " join composition.compositiongenresByCompositionkey as compositiongenre";
        fullHql += " join compositiongenre.algenreByAlgenrekey as genre";
        fullHql += " WHERE genre.algenrekey=" + key;

        Query query = HibernateUtil.getSession().createQuery(fullHql);

        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        return keys;
    }

    /**
     * Get the initial item counts for every composer in the db.
     */
    protected List<FacetCriterion> getGenres() {
        System.out.println("Building Genre count cache");
        List<Algenre> genres = HibernateUtil.getSession().createCriteria(Algenre.class).list();
        List<FacetCriterion> genreCrits = new Vector<FacetCriterion>();
        for (int i = 0; i < genres.size(); i++) {
            Algenre g = genres.get(i);
            //Get count
            ArrayList<Integer> keys = new ArrayList<Integer>();// getInitialGenreCount(g.getAlgenrekey());
            //if (keys != null && keys.size() > 0) {
            FacetCriterion cf = new FacetCriterion(keys, getType(), g.getAlgenrekey(), g.getGenre());
            genreCrits.add(cf);
            //}
        }
        System.out.println("Building Genre count cache FINISHED");
        return genreCrits;
    }

    protected void getGenreCounts() {
        String fullHql = "SELECT DISTINCT item.itemkey,genre.id from Item as item";
        fullHql += " join item.compositionByCompositionkey as composition";
        fullHql += " join composition.compositiongenresByCompositionkey as compositiongenre";
        fullHql += " join compositiongenre.algenreByAlgenrekey as genre";
        fullHql += " where length(genre.genre)>1";
        fullHql += " order by genre.id";
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
        for (int i = 0; i < genreValues.size(); i++) {
            FacetCriterion fc = genreValues.get(i);
            if (fc.getKeys().size() == 0) {
                genreValues.remove(fc);
            } else {
                fc.setCount(fc.getKeys().size());
            }
        }


    }

    /*protected boolean setComposerConstraint(int value, String matchString, int level) {
        ArrayList<Integer> itemKeys;
        String fullHql = "SELECT DISTINCT item from Item as item";
        fullHql += " join item.compositionByCompositionkey as composition";
        fullHql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
        fullHql += " join compositioncomposer.composerByComposerkey as composer";
        fullHql += " WHERE composer.composerkey=" + value;
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Item> items = query.list();
        itemKeys = DIAMMFacetManager.getKeyList(items);
        if (itemKeys != null && itemKeys.size() > 0) {
            setItemKeys(itemKeys);
        }
        return true;
    }*/

    public FacetCriterion getCriterionByKey(int key, int level) {
        for (int i = 0; i < genreValues.size(); i++) {
            FacetCriterion facetCriterion = genreValues.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion;
            }
        }
        return null;
    }

    public ArrayList<Integer> getResultSetKeys() {
        if (this.getState() == FacetState.UPDATED) {
            buildItemKeys();
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
}
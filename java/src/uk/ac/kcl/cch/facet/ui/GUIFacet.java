package uk.ac.kcl.cch.facet.ui;


import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 23-Jun-2010
 * Time: 15:31:28
 * To change this template use File | Settings | File Templates.
 */
public abstract class GUIFacet  {

    protected Facet facet; //The facet that this object displays
    protected GUIFacetManager GUIManager;
    protected GUIFacetState GUIstate;

    public GUIFacetState getGUIstate() {
        return GUIstate;
    }

    public void setGUIstate(GUIFacetState GUIstate) {
        this.GUIstate = GUIstate;
    }

    public GUIFacet(Facet f,GUIFacetManager g){
        setGUIManager(g);
        setFacet(f);
        setGUIstate(GUIFacetState.CLOSED);
    }

    public Facet getFacet() {
        return facet;
    }

    public void setFacet(Facet facet) {
        this.facet = facet;
    }

    /**
     * Adds JSTL attributes to request that are necessary to
     * display this facet
     * @param request
     */
    protected abstract void addFacetData(HttpServletRequest request);

    /**
     * A pass-through function so the GUIFacet will know if the underlying
     * facet has been update, and update itself if necessary
     * @param request
     */
    public abstract boolean updateFacetFromRequest(HttpServletRequest request);


    protected List<FacetCriterion> getDynamicCriteria(List<FacetCriterion> criteria) {
        //Applies the constraints of other facets to counts
        List<FacetCriterion> newCrit=criteria;
        ArrayList<Integer> constraintKeys = getFacet().getManager().getMasterKeyList(getFacet().getType());
        if (constraintKeys != null && constraintKeys.size() > 0 && criteria != null && criteria.size() > 0) {
            newCrit= new ArrayList<FacetCriterion>();
            for (int i = 0; i < criteria.size(); i++) {
                FacetCriterion fc = criteria.get(i);
                //Get modified counts based on key list
                //from other facets
                ArrayList<Integer> l1 = fc.getKeys();
                ArrayList<Integer> newList = FacetManager.mergeKeyLists(l1, constraintKeys);
                FacetCriterion c = new uk.ac.kcl.cch.facet.ui.FacetCriterion(newList, getFacet().getType(), fc.getKey(), fc.getLabel());
                if (c.getCount() > 0) {
                    newCrit.add(c);
                }
            }
        }
        return newCrit;
    }

    /**
     * Returns a string of text to display when facet
     * is collapsed.
     * @return
     */
    public abstract String getDescription();

    public abstract String getFacetType();


    public String getFrameTarget() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns a string of the criteria selected so they can be
     * selectively removed.
     * @return
     */
    public abstract String getLabel();

    public int getLevel() {
        return getFacet().getLevel();
    }

    public void setDBId(int pk) {
//To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDescription(String shortDesc) {
//To change body of implemented methods use File | Settings | File Templates.
    }

    public GUIFacetManager getGUIManager() {
        return GUIManager;
    }

    public void setGUIManager(GUIFacetManager GUIManager) {
        this.GUIManager = GUIManager;
    }
}
package uk.ac.kcl.cch.diamm.facet;

import uk.ac.kcl.cch.diamm.facet.type.SecondaryFacetType;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 15-Nov-2010
 * Time: 11:23:35
 * To change this template use File | Settings | File Templates.
 */
public class SecondaryFacet extends Facet {
    private ArrayList<Facet> seconds;

    public SecondaryFacet(FacetManager manager) {
        super(new SecondaryFacetType(), manager);
        initSecondaryFacets();
    }

    protected void initSecondaryFacets() {
        seconds = new ArrayList<Facet>();
        seconds.add(new PersonFacet(getManager()));
        seconds.add(new CenturyFacet(getManager()));
        seconds.add(new ProvenanceFacet(getManager()));
        //seconds.add(new SetFacet(getManager()));
        //seconds.add(new NotationFacet(getManager()));
        seconds.add(new ClefFacet(getManager()));
        seconds.add(new LanguageFacet(getManager()));
    }


    public FacetCriterion getCriterionByKey(int key, int level) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getLevel() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ArrayList<Integer> getResultSetKeys() {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i = 0; i < seconds.size(); i++) {
            Facet facet = seconds.get(i);
            if (facet.getResultSetKeys() != null && (facet.getValue() > 0)) {
                if (keys.size() == 0) {
                    keys = facet.getResultSetKeys();
                } else {
                    keys = FacetManager.mergeKeyLists(keys, facet.getResultSetKeys());
                }
            }
        }
        return keys;
    }

    public int getValue() {
        if (getState() != FacetState.EMPTY) {
            return 1;
        } else {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public void resetFacet() {
        for (int i = 0; i < seconds.size(); i++) {
            Facet facet = seconds.get(i);
            facet.resetFacet();
        }

    }

    protected void updateConstraints(int value, String constraintString, int level) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean updateConstraintsFromRequest(HttpServletRequest request) {
        boolean changed = false;
        setState(FacetState.EMPTY);
        for (int i = 0; i < seconds.size(); i++) {
            Facet facet = seconds.get(i);
            Boolean c = facet.updateConstraintsFromRequest(request);
            if (c) {
                //Changing any means whole facet changed
                changed = true;
            }
            if (facet.getValue()>0){
                setUpdated();
            }
        }
        return changed;
    }

    public ArrayList<Facet> getSeconds() {
        return seconds;
    }

    public void setSeconds(ArrayList<Facet> seconds) {
        this.seconds = seconds;
    }

    protected void setUpdated() {
        if (getState() != FacetState.UPDATED || getState() != FacetState.REDRAW) {
            setState(FacetState.UPDATED);
        }
    }


}

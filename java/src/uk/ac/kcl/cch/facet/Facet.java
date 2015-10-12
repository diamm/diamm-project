package uk.ac.kcl.cch.facet;

/**
 *<p>
 * An abstract controlling object for a faceted search.  Facet manages all of that facet's
 * interaction with the database and main query.
 *
 * Inputs are handled through httprequest object.
 * Primary output is ArrayList<Integer> of keys to be used as a constraint in the main query.
 * </p>
 * @author: ehall, based thill's earlier facet object
 * @version: 0.5 2010.11.4
 *
 */


import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public abstract class Facet {

    /**
     * The Facet's current state
     */
    protected FacetState state = FacetState.EMPTY;
    protected JoinType joinType = JoinType.AND;
    private FacetManager manager;
    private FacetType type;

    public String[] valueParameters;

    public String[] getValueParameters() {
        return this.valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }


    /**
     * Current Facet level
     *
     * @return
     */
    public abstract int getLevel();

    /**
     * The Current Value(primary key) of the selected item
     *
     * @return
     */
    public abstract int getValue();

    protected abstract void updateConstraints(int value, String constraintString, int level);

    public abstract boolean updateConstraintsFromRequest(HttpServletRequest request) ;

    /**
     * Returns an array of the primary keys of whatever the results
     * of the internal queries are.
     *
     * @return primary keys
     */
    public abstract ArrayList<Integer> getResultSetKeys();





    /**
     * Constructor
     */

    public Facet(FacetType t,FacetManager manager) {
        setManager(manager);
        this.type = t;
    }

    public Facet() {

    }

    /**
     * Reset the facet to its initial state
     */
    abstract public void resetFacet();

    /* getters and setters */


    public FacetState getState() {
        return state;
    }

    public void setState(FacetState state) {
        this.state = state;
    }


    /**
     * Retrieves one of the cached values in context.
     *
     * @param key   primary key for criterion
     * @param level which set of criterion to look in
     * @return
     */
    public abstract FacetCriterion getCriterionByKey(int key, int level);

    /**
     * For simple facets with only one criterion
     *
     * @return
     */
    public FacetCriterion getCurrentCriterion() {
        return getCriterionByKey(getValue(), getLevel());
    }

    public FacetCriterion getCurrentCriterion(int level) {
        return getCriterionByKey(getValue(), level);
    }

    /**
     * Type of join to other facets.
     * Default is AND.
     *
     * @return
     */
    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public FacetType getType() {
        return type;
    }

    public FacetManager getManager() {
        return manager;
    }

    public void setManager(FacetManager manager) {
        this.manager = manager;
    }

    protected void setUpdated() {
        if (getState() != FacetState.UPDATED || getState() != FacetState.REDRAW) {
            setState(FacetState.UPDATED);
        }
    }
}
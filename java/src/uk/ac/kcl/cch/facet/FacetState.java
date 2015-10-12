package uk.ac.kcl.cch.facet;

/**
 * An enumeration of a facet's different possible states.
 * Used to see if a Facet has data that affects the main query, has
 * been update in the latest request, and needs to be redrawn.
 *
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 08-Jun-2010
 * Time: 16:11:29
 * To change this template use File | Settings | File Templates.
 */
public enum FacetState {
     EMPTY,     //Just initialized, ignore
    SELECTED,   //Criterion selected
    UPDATED,    //Updated in last request
    REDRAW      //Update requires facet redraw
}
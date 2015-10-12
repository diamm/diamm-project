package uk.ac.kcl.cch.facet;

import uk.ac.kcl.cch.facet.ui.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>
 * Coordinates the relationship among <code>Facet</code>s, passing its <code>QueryMaker</code>
 * to all the constraint <code>Facet</code>s in turn before handing it over to the target
 * <code>Facet</code>, where the query is executed and the results processed.
 * </p>
 *
 * @author: thill
 * @version: 1.1 2010.04.26
 * @see uk.ac.kcl.cch.facet.QueryMaker
 * @see uk.ac.kcl.cch.facet.Facet
 */


public abstract class FacetManager {

    protected Hashtable<String, Facet> facets;
    protected ArrayList<FacetType> types; //All types of facets managed by this class.
    protected String masterKeyName;
    protected Constants c;



    /**
     * Stores the SQL clauses contributed by the <code>Facet</code>s and generates
     * a SQL query from them.
     */

    protected QueryMaker qMaker;


    /**
     * Returns an instance of the <code>Facet</code> class
     */
    public abstract Facet getNewFacet(FacetType ft);


    /**
     * Constructor
     */
    public FacetManager(Constants c) {
        this.c=c;
        initQueryMaker();
        initFacets();
    }

    public FacetManager() {        
        initQueryMaker();
        initFacets();
    }

    /**
     * Initialize the arrayList of facet types
     */
    protected abstract void initTypes();
    /**
     * Initialize all Facets and their GUI wrapper objects
     */
    protected abstract void initFacets();

    /**
     * Sets all facets that have been updated with this request
     * to selected.
     */
    public abstract void resetFacetStates();


    /**
     * Creates instance of an extension of <code>QueryMaker</code> and assigns it to <code>qMaker</code>.
     *
     * @see uk.ac.kcl.cch.facet.QueryMaker
     */
    abstract protected void initQueryMaker();

    /**
     * Perform the main query if not done/changed and return a subset of the results.
     *
     * @param pageNumber
     * @return
     */

    abstract public ArrayList<Object> retrieveDisplayResults(int pageNumber);


    /**
     * Add ordering joins, constraints, order text to querymaker based
     * on current facet options.
      * @param request current state
     *
     */
    abstract public void addSQLOrdering(HttpServletRequest request);

    abstract public void addSQLGrouping(HttpServletRequest request);

    /**
     * Add constraints based on constants, special session or context variables, etc.
     * @param request
     */
    abstract public void addOtherConstraints(HttpServletRequest request);


    //abstract public ArrayList<Object> getGroupedDisplayResults(int pageNumber);

    public abstract Facet getFacetByType(FacetType ft);

    public Facet getFacetByType(String type) {
        FacetType ft=getFacetType(type);
        if (ft==null){ return null;}
        return getFacetByType(ft);

    }

    public FacetType getFacetType(String typeName){
        for (int i = 0; i < types.size(); i++) {
            FacetType ft =  types.get(i);
            if (ft.getTypeName().equalsIgnoreCase(typeName)){
                return ft;
            }
        }

        return null;
    }

    public ArrayList<Object> retrieveDisplayResults(HttpServletRequest request) {
        addSQLOrdering(request);
        addSQLGrouping(request);
        addOtherConstraints(request);
        return retrieveDisplayResults(getCurrentPage(request));
    }




    public ArrayList<Integer> getMasterKeyList() {
        return getMasterKeyList(null);
    }

    public String getMasterKeyString(FacetType exclude) {
        ArrayList<Integer> keys = getMasterKeyList(exclude);
        if (keys != null && keys.size() > 0) {
            return serializeKeys(keys);
        }
        return "";
    }

    public String getMasterKeyString() {
        ArrayList<Integer> keys = getMasterKeyList();
        if (keys != null && keys.size() > 0) {
            return serializeKeys(keys);
        }
        return "";
    }

    /**
     * Utility function to extract keys from result set into array
     */
    //public abstract ArrayList<Integer> getKeyList(List<Item> items) ;
    public static String serializeKeys(ArrayList<Integer> keys) {
        String keyS = "";
        for (int i = 0; i < keys.size(); i++) {
            Integer integer = keys.get(i);
            if (keyS.length() > 0) {
                keyS += ",";
            }
            keyS += "" + integer;
        }
        return keyS;
    }


    public void setRowsPerPage(HttpServletRequest request) {
        int pageNumber = 1;
        if (request.getParameter(Constants.rowsPerPageParameter) != null) {
            pageNumber = Integer.parseInt(request.getParameter(Constants.rowsPerPageParameter));
            request.getSession().setAttribute(Constants.currentResultsPerPage, pageNumber + "");
        }
    }

    public int getRowsPerPage(HttpServletRequest request) {
        String per = (String) request.getSession().getAttribute(Constants.currentResultsPerPage);
        if (per != null) {
            return Integer.parseInt(per);
        }
        return Constants.defaultResultsPerPage;
    }

    public void setCurrentPageFromRequest(HttpServletRequest request) {
        int pageNumber = 1;
        if (request.getParameter(Constants.pageNumberParameter) != null) {
            pageNumber = Integer.parseInt(request.getParameter(Constants.pageNumberParameter));
            setCurrentPage(pageNumber, request);
        }
    }

    public void setCurrentPage(int page, HttpServletRequest request) {
        if (page > 0) {
            request.getSession().setAttribute(Constants.pageNumberSessionName, page + "");
        }
    }

    public int getCurrentPage(HttpServletRequest request) {
        int pageNumber = 1;
        String pnum = (String) request.getSession().getAttribute(Constants.pageNumberSessionName);
        if (pnum != null) {
            pageNumber = Integer.parseInt(pnum);
        }
        return pageNumber;
    }

    public int getResultsPerPage(HttpServletRequest request) {
        int pageNumber = 0;
        String pnum = (String) request.getSession().getAttribute(Constants.currentResultsPerPage);
        if (pnum != null) {
            pageNumber = Integer.parseInt(pnum);
        } else {
            pageNumber = Constants.defaultResultsPerPage;
        }
        return pageNumber;
    }

    /**
     * Stores the fully-qualified name of the master key used
     * to communicate between facets.  Key should be written as it would be used in
     * queries, not necessarily proper name.
     */

    public String getMasterKeyName() {
        return masterKeyName;
    }

    public void setMasterKeyName(String masterKeyName) {
        this.masterKeyName = masterKeyName;
    }

    /**
     * Get a combined array of all master key constraints
     * currently held in facet.
     *
     * @param exclude facet to ignore (so keys can be used for GUI operations)
     * @return
     */
    public ArrayList<Integer> getMasterKeyList(FacetType exclude) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        Enumeration types = getFacets().keys();
        while (types.hasMoreElements()) {
            //Build a common set of Item keys for all facets
            FacetType type = (FacetType) types.nextElement();
            if (!type.equals(exclude)) {
                Facet f = getFacetByType(type);
                if (f.getResultSetKeys() != null && f.getResultSetKeys().size() > 0) {
                    
                    keys.addAll(f.getResultSetKeys());
                }
            }
        }

        return keys;
    }


    public Hashtable getFacets() {
        return facets;
    }

    public void setFacets(Hashtable facets) {
        this.facets = facets;
    }

    /**
     * Merges two lists of keys, keeping ONLY
     * keys common to both sets.
     *
     * @param l1
     * @param l2
     * @return list of keys in both
     */
    public static ArrayList<Integer> mergeKeyLists(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        ArrayList<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < l1.size(); i++) {
            Integer integer = l1.get(i);
            if (l2.contains(integer)) {
                newList.add(integer);
            }
        }
        return newList;
    }

    public ArrayList<FacetType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<FacetType> types) {
        this.types = types;
    }


}
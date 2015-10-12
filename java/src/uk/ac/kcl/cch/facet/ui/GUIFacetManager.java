package uk.ac.kcl.cch.facet.ui;


import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetType;
import uk.ac.kcl.cch.facet.FacetManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 30-Jun-2010
 * Time: 18:32:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class GUIFacetManager {

       /**
     * GUI wrapper objects for facets
     */
    protected Hashtable<String, GUIFacet> GUIfacets;
    private String jspPage;
    private Constants constants;
    /**
         * The facets that this manager is displaying
         */
        protected FacetManager manager;


    public abstract GUIFacet getGUIFacet(String type);

    public abstract GUIFacet getGUIFacet(FacetType ft);

    public abstract void applyAttributes(HttpServletRequest request);


    public GUIFacetManager(FacetManager manager, String jspPage,Constants c) {
         this.manager = manager;
        this.jspPage=jspPage;
        this.constants=c;
        this.initGUIFacets();
    }

    public abstract void initGUIFacets();

    /** This is the synchronous version (it calls the more specific version.)  Applies all attributes
     * in available facets and returns initial page.
     *
     * @return   initial JSP page name
     */
    public abstract String getView();

    /**
         * Populates the request variable with all variables
         * necessary for display of facets and initial list
         * of items.
         *
         * @param request
         * @return
         */
        public String instantiateCurrentView(HttpServletRequest request) {
            //Apply all necessary facet data
            applyFacetData(request);
            applyDescriptionString(request);
            setOpenFacet(request);
            return getJspPage();
        }


    /**
     * Get only the view of a single facet.  Will be used
     * for Ajax.
     * @param ft
     * @return
     */
    public abstract String getView(FacetType ft);

    /**Iterates through all guifacets and adds relevant attributes
         *
         * @param request
         */
        public void applyFacetData(HttpServletRequest request) {
            Enumeration<String> keys=getGUIfacets().keys();
            while (keys.hasMoreElements()) {
                String s =  keys.nextElement();
                GUIFacet guiFacet=getGUIfacets().get(s);
               // if (guiFacet.getGUIstate() == GUIFacetState.OPEN ) {
                        guiFacet.addFacetData(request);

                //}
            }
        }

    public void applyDescriptionString(HttpServletRequest request) {
            ArrayList<FacetType> types=getManager().getTypes();
            String desc="";
        for (int i = 0; i < types.size(); i++) {
            FacetType type =  types.get(i);

                //TODO: Should facet buttons appear in a particular order?
                desc+=getGUIFacetByType(type).getDescription();
            }
            request.setAttribute(constants.searchDescriptionAttrName,desc);
        }

    public  FacetType getOpenFacet (HttpServletRequest request) {
        FacetType ft=(FacetType) request.getSession().getAttribute(constants.openFacet);
        return ft;
    }

     public  void setOpenFacet (FacetType ft,HttpServletRequest request) {
         request.getSession().setAttribute(constants.openFacet,ft);
     }

    public  void setOpenFacet (HttpServletRequest request) {
        FacetType ft=null;
        if (request.getParameter(constants.facetTypeParameter)!=null&&!request.getParameter(constants.facetTypeParameter).equalsIgnoreCase("ITEM")){
            String typeName=(String)request.getParameter(constants.facetTypeParameter);
            FacetManager m=getManager();
            ft=m.getFacetType(typeName);
            if (m.getFacetByType(ft)==null){
                //Facet has not been instantiated, create before open
                Facet f=m.getNewFacet(ft);
                Hashtable facets=m.getFacets();
                facets.put(ft,f);
                setManager(m);
            }

            if (ft==null){
                getManager().getFacetByType(typeName);
            }
            if (getGUIFacetByType(ft)!=null&&getGUIFacetByType(ft).getGUIstate()!=null&&getGUIFacetByType(ft).getGUIstate()==GUIFacetState.OPEN){
                setOpenFacet(ft,request);
            }
        }else{
            ft=getOpenFacet(request);
        }
        if (ft!=null&&getGUIFacetByType(ft).getGUIstate()==GUIFacetState.OPEN){
            request.setAttribute(constants.openFacet,ft.getTypeName());
        }
    }

    public String getJspPage() {
        return jspPage;
    }

    public void setJspPage(String jspPage) {
        this.jspPage = jspPage;
    }

    public GUIFacet getGUIFacetByType(FacetType f){
         if (getGUIfacets()!=null){
             return (getGUIfacets().get(f.getTypeName()));
         }
        return null;
    }

    public FacetManager getManager() {
        return manager;
    }

    public void setManager(FacetManager manager) {
        this.manager = manager;
    }

    public Hashtable<String, GUIFacet> getGUIfacets() {
        return GUIfacets;
    }

    public void setGUIfacets(Hashtable<String, GUIFacet> GUIfacets) {
        this.GUIfacets = GUIfacets;
    }
}
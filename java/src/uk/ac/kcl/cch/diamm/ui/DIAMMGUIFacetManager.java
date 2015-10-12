package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.type.*;
import uk.ac.kcl.cch.diamm.servlet.DIAMMFacetServlet;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.ui.GUIFacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.FacetType;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 06-Jul-2010
 * Time: 13:40:02
 * To change this template use File | Settings | File Templates.
 */
public class DIAMMGUIFacetManager extends GUIFacetManager {

    public DIAMMGUIFacetManager(FacetManager manager, String jspPage) {
        super(manager, jspPage,new Constants());
    }

    public void initGUIFacets() {
        if (GUIfacets == null) {
            GUIfacets = new Hashtable<String, GUIFacet>();
        }
        ArrayList<FacetType> types = getManager().getTypes();
        for (int i = 0; i < types.size(); i++) {
            FacetType type =  types.get(i);
            DIAMMFacetManager m = (DIAMMFacetManager) getManager();
            Facet facet = m.getFacetByType(type);
            if (facet != null) {
                GUIfacets.put(type.getTypeName(), getNewGUIFacet(facet));
            }
        }

    }

    public void addGuiFacet(GUIFacet f) {
         if (this.GUIfacets==null){
             this.GUIfacets=new Hashtable<String, GUIFacet>();
         }
        this.GUIfacets.put(f.getFacet().getType().getTypeName(),f);
    }

    public GUIFacet getNewGUIFacet(Facet f) {
        FacetType ft = f.getType();
        if (SourceFacetType.class==ft.getClass()) {
            return new SourceGUIFacet(f,this);
        } else if (ft.getClass() == ComposerFacetType.class) {
            return new ComposerGUIFacet(f,this);
        }else if (ft.getClass() == GenreFacetType.class) {
            return new GenreGUIFacet(f,this);
        }else if (ft.getClass() == TextFacetType.class) {
            return new TextGUIFacet(f,this);
        } else if (ft.getClass() == SecondaryFacetType.class) {
            return new SecondaryGUIFacet(f,this);
        }

        return null;
    }




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

    public void closeAllFacets(){
        ArrayList<FacetType> types=getManager().getTypes();
        for (int i = 0; i < types.size(); i++) {
            FacetType type =  types.get(i);
            GUIFacet gf=getGUIFacetByType(type);
            if (gf!=null&&gf.getGUIstate()== GUIFacetState.OPEN){
                gf.setGUIstate(GUIFacetState.CLOSED);
            }
        }
    }

    /*protected static FacetType getOpenFacet (HttpServletRequest request) {
        FacetType ft=(FacetType) request.getSession().getAttribute(Constants.openFacet);
        return ft;
    }

     protected void setOpenFacet (FacetType ft,HttpServletRequest request) {
         request.getSession().setAttribute(Constants.openFacet,ft);
     }

    protected  void setOpenFacet (HttpServletRequest request) {
        FacetType ft=null;
        if (request.getParameter(Constants.facetTypeParameter)!=null){
            ft=manager.getFacetType((String)request.getParameter(Constants.facetTypeParameter));
            setOpenFacet(ft,request);
        }else{
            ft=getOpenFacet(request);
        }
        if (ft!=null){
            request.setAttribute(Constants.openFacet,ft.name());
        }
    }  */

    public void applyDescriptionString(HttpServletRequest request) {
        String desc="";
        ArrayList<FacetType> types=manager.getTypes();
        for (int i = 0; i < types.size(); i++) {
            FacetType type =  types.get(i);
            if (getGUIFacetByType(type)!=null){
                desc+=getGUIFacetByType(type).getDescription();
            }
        }
        request.setAttribute(Constants.searchDescriptionAttrName,desc);
    }

    public void applyAttributes(HttpServletRequest request) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public GUIFacet getGUIFacet(FacetType ft) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public GUIFacet getGUIFacet(String type) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getView() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getView(FacetType ft) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

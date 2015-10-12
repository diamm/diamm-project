package uk.ac.kcl.cch.facet.ui;

import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.util.Iterator;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 04-Nov-2010
 * Time: 15:54:38
 * To change this template use File | Settings | File Templates.
 */
public abstract class FacetServlet extends HttpServlet {

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response);

    public abstract void updateOtherOptions(HttpServletRequest request);

    public abstract void resetAllOptions(HttpServletRequest request);

    protected abstract String initWindow(HttpServletRequest request) ;

    public void resetAllFacets(HttpServletRequest request) {
        Iterator i = getManager(request).getFacets().values().iterator();
        while (i.hasNext()) {
            Facet f = (Facet) i.next();
            f.resetFacet();
        }
    }



    public void resetWindow(HttpServletRequest request) {
        //Reset facets
        resetAllFacets(request);
        //Other options
        resetAllOptions(request);
        //Reset grouping and ordering
        resetOrderGroup(request);
        //Reset Pagination
        getManager(request).setCurrentPage(1,request);
    }

    public abstract void resetOrderGroup(HttpServletRequest request) ;

     public FacetManager getManager(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (session.getAttribute(Constants.facetManagerSessionName) != null) {
                return (FacetManager) session.getAttribute(Constants.facetManagerSessionName);
            } else {
                DIAMMFacetManager m = new DIAMMFacetManager();
                setManager(request, m);
                return m;
            }
        }
        return null;
    }

    public abstract FacetManager newFacetManager();

    public abstract GUIFacetManager getGUIManager(HttpServletRequest request);

    public abstract void setManager(HttpServletRequest request, FacetManager m) ;

    public abstract void setGUIManager(HttpServletRequest request, GUIFacetManager m) ;


    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.HibernateException;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.search.SearchItem;
import uk.ac.kcl.cch.diamm.search.SearchManager;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.facet.Facet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 15/02/11
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class SearchManagerServlet extends HttpServlet {

    //Operation Types
    public static final int getCriteriaOp = 1;
    public static final int getAutoComplete = 2;
    public static final int setCriteriaValue = 3;
    public static final int runSearch = 4;
    public static final int clearSearch = 5;
    public static final int setSearchVariables = 6;
    public static final int getCurrentSearch = 7;
    public static final int changeResultVariables = 8;
    public static final int resetSearch = 9;

    public static final String autopage = "autocomplete.jsp";

    public static int sourceSearchType = 1;
    public static int itemSearchType = 2;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void resetFacetManager(HttpServletRequest request) {
        DIAMMFacetManager facetM = (DIAMMFacetManager) request.getSession().getAttribute(Constants.facetManagerSessionName);
        if (facetM != null) {
            Iterator i = facetM.getFacets().values().iterator();
            while (i.hasNext()) {
                Facet f = (Facet) i.next();
                f.resetFacet();
            }
            facetM.setCurrentPage(1, request);
        }
    }

    protected void process(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        String page = "";
        String o = request.getParameter("op");
        int op = 7;
        if (o != null) {
            op = Integer.parseInt(o);
        }
        SearchManager m = getCurrentSearch(request);
        try {
            HibernateUtil.beginTransaction();
            switch (op) {
                case (getCurrentSearch):
                    resetFacetManager(request);
                    m.addCriteria(request);
                    m.addOtherRequestVariables(request);
                    page = "search.jsp";
                    break;
                case (getAutoComplete):
                    page = getAutoComplete(request, m);
                    break;
                case (getCriteriaOp):
                    page = m.addCriteria(request);
                    break;
                case (setCriteriaValue):
                    page = m.setCriteriaValue(request);
                    break;
                case (runSearch):
                    //Reset Criteria
                    m.setCurrentCriteria(new SearchItem[SearchManager.criteriaMax]);
                    m.setRequestVariables(request);
                    page = m.runSearch(request);
                    m.addOtherRequestVariables(request);
                    break;
                case (setSearchVariables):
                    break;
                case resetSearch:
                    m = new SearchManager();
                    request.getSession().setAttribute("DIAMM.getCurrentSearch", m);
                    m.addCriteria(request);
                    m.addOtherRequestVariables(request);
                    page = "search.jsp";
                    break;
                case (changeResultVariables):
                    m.setOtherRequestVariables(request);
                    page = m.runSearch(request);
                    m.addOtherRequestVariables(request);
                    break;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
        }
        setCurrentSearch(request, m);
        String searchType = request.getParameter("searchType");
        DIAMMFacetServlet.addMenu(request, "facetmanager");
        DIAMMFacetManager.setPreviousUrl(request, "SearchManager");
        DIAMMFacetManager.setPreviousUrl(request, "SearchManager?op=8");
        RequestDispatcher reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);
        reqDisp.forward(request, resp);
    }

    public String getAutoComplete(HttpServletRequest request, SearchManager m) {
        m.addAutoComplete(request);

        /*String sid = request.getParameter("sId");
        if (sid!=null){
            SearchItem item=m.getCriteriaByCode(Integer.parseInt(sid));
            ArrayList<AutoCompleteResult> results= item.getAutoCompleteResults(request);
            if (results!=null){
                request.setAttribute("autoCompleteResults",results);
            }
        }*/
        return autopage;
    }


    public static SearchManager getCurrentSearch(HttpServletRequest request) {
        SearchManager m = (SearchManager) request.getSession().getAttribute("DIAMM.getCurrentSearch");
        if (m == null) {
            m = new SearchManager();
        }
        return m;
    }

    public static void setCurrentSearch(HttpServletRequest request, SearchManager m) {
        request.getSession().setAttribute("DIAMM.getCurrentSearch", m);
    }
}

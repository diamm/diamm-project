package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.HibernateException;
import uk.ac.kcl.cch.diamm.dao.BibliographyDAO;
import uk.ac.kcl.cch.diamm.dao.BibliographyDTO;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Bibliography;
import uk.ac.kcl.cch.diamm.ui.BibliographyDisplay;
import uk.ac.kcl.cch.facet.ui.RecordNavigator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DIAMMBibliographyDetailServlet extends HttpServlet {

    //Define params relating to parent
    private static final String PARENT = "parent";
    private static final String PARENT_BIB = "bib";
    private static final String PARENT_OTHER = "other";

    //Define input request params - used when parent is Bibliography
    private static final String TOTAL_RECORD_COUNT = "totalRecordCount";
    private static final String BIB_ORIGINATING_PAGE = "bibOriginatingPage";
    private static final String BIB_POSITION_IN_PAGE = "bibPositionInPage";
    private static final String RECORD_NAV = "recordNav";
    private static final String PREV_RECORD = "prevRecord";
    private static final String NEXT_RECORD = "nextRecord";

    //Define input request params - used when parent is not Bibliography
    private static final String BIB_ID = "bibId";

    //Define output params
    private static final String BIB = "bib";
    private static final String BIB_DETAIL_VIEW = "bibliographyDetail.jsp";

    //Get BibliographyDAO
    private DAOFactory factory = DAOFactory.getFactory();
    private BibliographyDAO bibDAO = factory.getBibliographyDAO();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher reqDisp=null;
        try {
            HibernateUtil.getSession().beginTransaction();
            if (req.getParameter(PARENT) != null && req.getParameter(PARENT).equals(PARENT_BIB)) {
                doWithBibParent(req);
            } else {
                doWithOtherParent(req);
            }
            DIAMMFacetServlet.addMenu(req, "Bibliography");
            //Forward request to view

             reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + BIB_DETAIL_VIEW);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
        }
        if (reqDisp!=null){
            reqDisp.forward(req, resp);
        }
    }

    private void doWithBibParent(HttpServletRequest req) {
        //Get originating search params
        BibliographyDTO bibDTO = new BibliographyDTO();
        bibDTO.setTitle(req.getParameter(DIAMMBibliographyServlet.TITLE));
        bibDTO.setAuthor(req.getParameter(DIAMMBibliographyServlet.AUTHOR));
        bibDTO.setYear(req.getParameter(DIAMMBibliographyServlet.YEAR));
        bibDTO.setPublisher(req.getParameter(DIAMMBibliographyServlet.PUBLISHER));
        bibDTO.setAuthoreditor(req.getParameter(DIAMMBibliographyServlet.AUTHOREDITOR));

        //Create record navigatior object
        RecordNavigator recordNav;
        Integer totalRecordCount = Integer.valueOf(req.getParameter(TOTAL_RECORD_COUNT));
        Integer originatingPage = Integer.valueOf(req.getParameter(BIB_ORIGINATING_PAGE));

        if (req.getParameter(PREV_RECORD) != null || req.getParameter(NEXT_RECORD) != null) {
            Integer selectedRecord = (req.getParameter(PREV_RECORD) != null ? Integer.valueOf(req.getParameter(PREV_RECORD)) :
                    Integer.valueOf(req.getParameter(NEXT_RECORD)));
            recordNav = new RecordNavigator(totalRecordCount, originatingPage, selectedRecord);
        } else {
            Integer bibPositionInPage = Integer.valueOf(req.getParameter(BIB_POSITION_IN_PAGE));
            Integer rowsPerPage = Integer.valueOf(getServletContext().getInitParameter(DIAMMBibliographyServlet.BIB_DEFAULT_ROWS_PER_PAGE));
            recordNav = new RecordNavigator(totalRecordCount, originatingPage, bibPositionInPage, rowsPerPage);
        }

        //Retrieve Bibliography object based on its position
        //bibDAO.beginTransaction();
        BibliographyDisplay bib = null;

        bib = new BibliographyDisplay(bibDAO.getBibliographyList(bibDTO, recordNav.getSelectedRecord(), 1).get(0));
        bib.addVars();
        //bibDAO.commitTransaction();

        //Set output values
        req.setAttribute(PARENT, PARENT_BIB);
        req.setAttribute(BIB, bib);
        req.setAttribute(DIAMMBibliographyServlet.BIB_SEARCH, bibDTO);
        req.setAttribute(RECORD_NAV, recordNav);
    }

    private void doWithOtherParent(HttpServletRequest req) {
        //Retrieve Bibliography object based on its position
        //bibDAO.beginTransaction();
        Bibliography bibliography = bibDAO.findByPrimaryKey(Integer.parseInt(req.getParameter(BIB_ID)));
       // bibDAO.commitTransaction();
        BibliographyDisplay bib = new BibliographyDisplay(bibliography);
        bib.addVars();
        //Set output values
        req.setAttribute(PARENT, PARENT_OTHER);
        req.setAttribute("refererUrl", req.getHeader("referer"));
        req.setAttribute(BIB, bib);
    }

}

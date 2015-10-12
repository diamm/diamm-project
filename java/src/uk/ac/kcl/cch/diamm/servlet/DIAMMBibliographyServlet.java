package uk.ac.kcl.cch.diamm.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.kcl.cch.diamm.dao.BibliographyDAO;
import uk.ac.kcl.cch.diamm.dao.BibliographyDTO;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.model.Bibliography;
import uk.ac.kcl.cch.facet.ui.PageNavigator;

public class DIAMMBibliographyServlet extends HttpServlet
{
	//Define init params as constants
	public static final String BIB_DEFAULT_ROWS_PER_PAGE = "bibDefaultRowsPerPage";
	public static final String BIB_DEFAULT_PAGE_RANGE    = "bibDefaultPageRange";
	
	//Define input request params as constants (search related)
	public static final String TITLE     = "title";
	public static final String AUTHOR    = "author";
	public static final String YEAR      = "year";
	public static final String PUBLISHER = "publisher";
	public static final String AUTHOREDITOR = "authoreditor";
	//Define input request params as constants (other)
	public static final String NEXT_PAGE     = "nextPage";
	
	//Define output params as constants
	public static final String BIB_SEARCH  = "bibSearch";
	public static final String BIB_LIST    = "bibList";
	public static final String PAGE_NAV    = "pageNav";
	public static final String BIB_VIEW    = "bibliography.jsp";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{
		process(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{
		process(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {	
		//Perform search
		if (req.getParameter("search") != null)
		{ 
			//Get search params
			BibliographyDTO bibDTO = new BibliographyDTO();
			bibDTO.setTitle    (req.getParameter(TITLE));
			bibDTO.setAuthor   (req.getParameter(AUTHOR));
			bibDTO.setYear     (req.getParameter(YEAR));
			bibDTO.setPublisher(req.getParameter(PUBLISHER));
			bibDTO.setAuthoreditor(req.getParameter(AUTHOREDITOR));
			if (!bibDTO.isEmpty())
			{
				//Start transaction
				DAOFactory factory = DAOFactory.getFactory();
				BibliographyDAO bibDAO = factory.getBibliographyDAO();
				bibDAO.beginTransaction();
				
				//Create page navigatior object
				Integer rowsPerPage      = Integer.valueOf(getServletContext().getInitParameter(BIB_DEFAULT_ROWS_PER_PAGE));
				Integer totalRecordCount = bibDAO.getBibliographyCount(bibDTO);
				Integer selectedPage     = (req.getParameter(NEXT_PAGE) != null ? Integer.valueOf(req.getParameter(NEXT_PAGE)):1);
				Integer pageRange        = Integer.valueOf(getServletContext().getInitParameter(BIB_DEFAULT_PAGE_RANGE));
				PageNavigator pageNav    = new PageNavigator(rowsPerPage, totalRecordCount, pageRange, selectedPage); 
	            
	            //Perform search restricted by start row and max results
				List<Bibliography> bibList = bibDAO.getBibliographyList(bibDTO, pageNav.getFirstRecordInPage(), rowsPerPage); 
				bibDAO.commitTransaction();
				
				//Set output values
				req.setAttribute(BIB_SEARCH,  bibDTO);
	            req.setAttribute(BIB_LIST, bibList);
	            req.setAttribute(PAGE_NAV, pageNav);
			}
		}
		
		//Reset search params
		else if (req.getParameter("reset") != null)
		{
			BibliographyDTO bibDTO = new BibliographyDTO();
			req.setAttribute(BIB_SEARCH,  bibDTO);
		}
		  DIAMMFacetServlet.addMenu(req, "Bibliography");
		//Forward request to view
    	String view = BIB_VIEW;
		RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") +  view);
	    reqDisp.forward(req, resp); 
    }
}

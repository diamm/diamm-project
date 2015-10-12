package uk.ac.kcl.cch.diamm.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.kcl.cch.diamm.dao.CollectionDTO;
import uk.ac.kcl.cch.diamm.dao.Transaction;
import uk.ac.kcl.cch.diamm.dao.TransactionFactory;
import uk.ac.kcl.cch.diamm.model.Collection;
import uk.ac.kcl.cch.diamm.model.CollectionAction;

public class CollectionLinkServlet extends HttpServlet
{
	//Define constants
	private final String SELF_PARENT              = "selfParent";
	private final String ACTION_CREATE            = "create";
	private final String ACTION_LINK              = "link";
	private final String ACTION_NONE              = "none";
	private final String COLL_ID_TO_LINK          = "collIdToLink";
	private final String LINK_ID                  = "linkId";
	private final String LINK_TYPE                = "linkType";
	private final String CREATED_COLL_TITLE       = "createdCollTitle";
	private final String CREATED_COLL_DESCRIPTION = "createdCollDescription";
	private final String VIEW                     = "collectionLink.jsp";
	private final String MESSAGES                 = "messages";
	private final String COLL_DETAILS             = "collDetails";
	private final String COLL_LIST                = "collList";

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
		CollectionDTO collectionDetails = getCollectionParams(req);
		
		//Link a source, item or image to an existing collection
		if (collectionDetails.getAction().equals(ACTION_LINK))
		{
			linkToCollection(collectionDetails, req);
		}
		//Create a new collection
		else if (collectionDetails.getAction().equals(ACTION_CREATE))
		{
			Collection c=createCollection(collectionDetails, req);
            if (c!=null&&req.getParameter("linkId")!=null&&req.getParameter("linkType")!=null){
                //Link to new collection
                collectionDetails.setAction(ACTION_LINK);
                collectionDetails.setCollIdToLink(c.getId());
                linkToCollection(collectionDetails, req);
            }
		}
		
		//Set output attributes
		setOutputAttributes(collectionDetails, req);
		DIAMMFacetServlet.addMenu(req, "facetmanager");
		//Forward request to appropriate view
		RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + VIEW);
	    reqDisp.forward(req, resp); 
    }
	
	private void linkToCollection(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		CollectionAction colAction = new CollectionAction();
		colAction.link(collectionDetails);
		req.setAttribute(MESSAGES, colAction.getMessages());
		collectionDetails.setDefaultCollId(collectionDetails.getCollIdToLink());
		transaction.commit();
	}
	
	private Collection createCollection(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		CollectionAction colAction = new CollectionAction();
		Collection collection = colAction.create(collectionDetails);
		req.setAttribute(MESSAGES, colAction.getMessages());
		
		if (!colAction.hasError())
		{
			collectionDetails.setDefaultCollId(collection.getId());	
			collectionDetails.setCreatedCollTitle(null);
			collectionDetails.setCreatedCollDescription(null);
			transaction.commit();
            return collection;
		}
		else
		{
			transaction.rollback();
		}
        return null;
	}
	
	private void setOutputAttributes(CollectionDTO collectionDetails, HttpServletRequest req)
	{	
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		req.setAttribute(COLL_DETAILS,collectionDetails);
		req.setAttribute(COLL_LIST, (new CollectionAction()).getCollectionList(collectionDetails.getUsername()));
		
		if (collectionDetails.getSelfParent() == null) {
			req.setAttribute("refererUrl", req.getHeader("referer"));	
		}
		else {
			req.setAttribute("refererUrl", req.getParameter("referer"));	
		}
		transaction.commit();
	}

	private CollectionDTO getCollectionParams(HttpServletRequest req)
	{
		CollectionDTO collectionDetails = new CollectionDTO();
		
		if (req.getParameter(ACTION_LINK) != null)
		{
			collectionDetails.setAction(ACTION_LINK);	
		}
		else if (req.getParameter(ACTION_CREATE) != null)
		{
			collectionDetails.setAction(ACTION_CREATE);	
		}
		else 
		{
			collectionDetails.setAction(ACTION_NONE);	
		}
		
		collectionDetails.setSelfParent(req.getParameter(SELF_PARENT));
		collectionDetails.setUsername(req.getRemoteUser());
		collectionDetails.setCollIdToLink(req.getParameter(COLL_ID_TO_LINK) == null ? null : Integer.valueOf(req.getParameter(COLL_ID_TO_LINK)));
		collectionDetails.setCreatedCollTitle(req.getParameter(CREATED_COLL_TITLE));
		collectionDetails.setCreatedCollDescription(req.getParameter(CREATED_COLL_DESCRIPTION));
		collectionDetails.setLinkId(req.getParameter(LINK_ID) == null ? null : Integer.valueOf(req.getParameter(LINK_ID)));
		collectionDetails.setLinkType(req.getParameter(LINK_TYPE));
		return collectionDetails;
	}
}

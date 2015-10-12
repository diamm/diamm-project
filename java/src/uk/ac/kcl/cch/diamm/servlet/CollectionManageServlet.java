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
import uk.ac.kcl.cch.diamm.ui.CollectionDisplay;

public class CollectionManageServlet extends HttpServlet
{
	//Define constants
	private final String MODE                     = "mode";
	private final String MODE_CREATE              = "modeCreate";
	private final String MODE_EDIT                = "modeEdit";
	private final String ACTION_CREATE            = "create";
	private final String ACTION_EDIT              = "edit";
	private final String ACTION_SAVE              = "save";
	private final String ACTION_DELETE            = "delete";
	private final String ACTION_UNLINK            = "unlink";
	private final String ACTION_VIEW_FULL_DETAILS = "fullDetails";
	private final String ACTION_NONE              = "none";
	private final String CREATED_COLL_TITLE       = "createdCollTitle";
	private final String CREATED_COLL_DESCRIPTION = "createdCollDescription";
	private final String ORIG_COLL_ID             = "origCollId";
	private final String EDITED_COLL_TITLE        = "editedCollTitle";
	private final String EDITED_COLL_DESCRIPTION  = "editedCollDescription";
	private final String UNLINK_ID                = "unlinkId";
	private final String UNLINK_TYPE              = "unlinkType";
	private final String ORIG_COLL                = "origColl";
	private final String VIEW_MANAGE              = "collectionManage.jsp";
	private final String VIEW_FULL_DETAIL         = "collectionDetail.jsp";
	private final String MESSAGES                 = "messages";
	private final String COLL_DETAILS             = "collDetails";
	public final String COLL_LIST                = "collList";

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
		
		//Create a new collection
		if (collectionDetails.getAction().equals(ACTION_CREATE))
		{
			createCollection(collectionDetails, req);
		}
		//Edit a collection
		else if (collectionDetails.getAction().equals(ACTION_EDIT))
		{
			editCollection(collectionDetails, req);
		}
		//Save changes to collection
		else if (collectionDetails.getAction().equals(ACTION_SAVE))
		{
			saveCollectionChanges(collectionDetails, req);
		}
		//Delete an existing collection
		else if (collectionDetails.getAction().equals(ACTION_DELETE))
		{
			deleteCollection(collectionDetails, req);
		}
		//Unlink a source, item or image from an existing collection
		else if (collectionDetails.getAction().equals(ACTION_UNLINK))
		{
			unlinkFromCollection(collectionDetails, req);
		}	
		//View a collection's full details
		else if (collectionDetails.getAction().equals(ACTION_VIEW_FULL_DETAILS))
		{
			viewFullDetails(collectionDetails, req);
		}
		
		//Set output attributes
		setCommonOutputAttributes(collectionDetails, req);
		
		//Forward request to appropriate view
		RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + 
				                                             (collectionDetails.getAction().equals(ACTION_VIEW_FULL_DETAILS) ? VIEW_FULL_DETAIL: VIEW_MANAGE));
        DIAMMFacetServlet.addMenu(req, "facetmanager");
	    reqDisp.forward(req, resp); 
    }
	
	private void createCollection(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		CollectionAction colAction = new CollectionAction();
		Collection collection = colAction.create(collectionDetails);
		req.setAttribute(MESSAGES, colAction.getMessages());
		req.setAttribute(MODE, MODE_CREATE);
		
		if (!colAction.hasError())
		{
			collectionDetails.setDefaultCollId(collection.getId());	
			collectionDetails.setCreatedCollTitle(null);
			collectionDetails.setCreatedCollDescription(null);
			transaction.commit();
		}
		else
		{
			transaction.rollback();
		}
	}
	
	private void editCollection(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		Collection collection = new CollectionAction().find(collectionDetails.getOrigCollId());
        CollectionDisplay cd=new CollectionDisplay(collection);
        cd.addItems();
        cd.addSources();
		req.setAttribute(ORIG_COLL, cd);
		collectionDetails.setEditedCollTitle(collection.getTitle());
		collectionDetails.setEditedCollDescription(collection.getDescription());
		req.setAttribute(MODE, MODE_EDIT);
		transaction.commit();
	}
	
	private void saveCollectionChanges(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		CollectionAction colAction = new CollectionAction();
	    colAction.saveChanges(collectionDetails);
		req.setAttribute(MESSAGES, colAction.getMessages());
		req.setAttribute(MODE, MODE_EDIT);
		
		if (colAction.hasError())
		{
			Collection collection = new CollectionAction().find(collectionDetails.getOrigCollId());
			req.setAttribute(ORIG_COLL, collection);
			transaction.rollback();
		}
		else
		{
			transaction.commit();
			editCollection(collectionDetails, req);	
		}
	}
	
	private void deleteCollection(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		CollectionAction colAction = new CollectionAction();
		colAction.delete(collectionDetails);
		req.setAttribute(MODE, MODE_CREATE);
		transaction.commit();
	}
	
	private void unlinkFromCollection(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		(new CollectionAction()).unlink(collectionDetails);
		transaction.commit();
		editCollection(collectionDetails, req);
	}
	
	private void viewFullDetails(CollectionDTO collectionDetails, HttpServletRequest req)
	{
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		Collection collection = new CollectionAction().find(collectionDetails.getOrigCollId());
        CollectionDisplay cd=new CollectionDisplay(collection);
		req.setAttribute("refererUrl", req.getHeader("referer"));
        cd.addItems();
        cd.addSources();
		req.setAttribute(ORIG_COLL, cd);
		transaction.commit();
	}
	
	private void setCommonOutputAttributes(CollectionDTO collectionDetails, HttpServletRequest req)
	{	
		Transaction transaction = TransactionFactory.getTransaction();
		transaction.begin();
		req.setAttribute(COLL_DETAILS,collectionDetails);
		req.setAttribute(COLL_LIST, (new CollectionAction()).getCollectionList(collectionDetails.getUsername()));
		transaction.commit();
	}
	
	private CollectionDTO getCollectionParams(HttpServletRequest req)
	{
		CollectionDTO collectionDetails = new CollectionDTO();
		
		if (req.getParameter(ACTION_CREATE) != null)
		{
			collectionDetails.setAction(ACTION_CREATE);	
		}
		else if (req.getParameter(ACTION_EDIT) != null)
		{
			collectionDetails.setAction(ACTION_EDIT);	
		}
		else if (req.getParameter(ACTION_SAVE) != null)
		{
			collectionDetails.setAction(ACTION_SAVE);	
		}
		else if (req.getParameter(ACTION_DELETE) != null)
		{
			collectionDetails.setAction(ACTION_DELETE);	
		}
		else if (req.getParameter(ACTION_UNLINK) != null)
		{
			collectionDetails.setAction(ACTION_UNLINK);	
		}
		else if (req.getParameter(ACTION_VIEW_FULL_DETAILS) != null)
		{
			collectionDetails.setAction(ACTION_VIEW_FULL_DETAILS);	
		}
		else 
		{
			collectionDetails.setAction(ACTION_NONE);	
		}
		
		collectionDetails.setUsername(req.getRemoteUser());
		collectionDetails.setCreatedCollTitle(req.getParameter(CREATED_COLL_TITLE));
		collectionDetails.setCreatedCollDescription(req.getParameter(CREATED_COLL_DESCRIPTION));
		collectionDetails.setOrigCollId(req.getParameter(ORIG_COLL_ID) == null ? null : Integer.valueOf(req.getParameter(ORIG_COLL_ID)));
		collectionDetails.setEditedCollTitle(req.getParameter(EDITED_COLL_TITLE));
		collectionDetails.setEditedCollDescription(req.getParameter(EDITED_COLL_DESCRIPTION));
		collectionDetails.setUnlinkId(req.getParameter(UNLINK_ID) == null ? null : Integer.valueOf(req.getParameter(UNLINK_ID)));
		collectionDetails.setUnlinkType(req.getParameter(UNLINK_TYPE));
		return collectionDetails;
	}
}

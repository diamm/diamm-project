package uk.ac.kcl.cch.diamm.hibernate;

import uk.ac.kcl.cch.diamm.dao.BibliographyDAO;
import uk.ac.kcl.cch.diamm.dao.CollectionDAO;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.dao.ImageDAO;
import uk.ac.kcl.cch.diamm.dao.ItemDAO;
import uk.ac.kcl.cch.diamm.dao.NoteDAO;
import uk.ac.kcl.cch.diamm.dao.NoteVisibilityDAO;
import uk.ac.kcl.cch.diamm.dao.NotetypeDAO;
import uk.ac.kcl.cch.diamm.dao.SourceDAO;
import uk.ac.kcl.cch.diamm.dao.UserRoleDAO;

public class HibernateDAOFactory extends DAOFactory
{
	public BibliographyDAO getBibliographyDAO() 
	{
		return new HibernateBibliographyDAO();
	}
	
	public DiammUserDAO getDiammUserDAO() 
	{
		return new HibernateDiammUserDAO();
	}	
	
	public UserRoleDAO getUserRoleDAO() 
	{
		return new HibernateUserRoleDAO();
	}

	public CollectionDAO getCollectionDAO() 
	{
		return new HibernateCollectionDAO();
	}	
	
	public SourceDAO getSourceDAO() 
	{
		return new HibernateSourceDAO();
	}
	
	public ItemDAO getItemDAO() 
	{
		return new HibernateItemDAO();
	}
	
	public ImageDAO getImageDAO() 
	{
		return new HibernateImageDAO();
	}
	
	public NoteDAO getNoteDAO() 
	{
		return new HibernateNoteDAO();
	}
	
	public NotetypeDAO getNotetypeDAO() 
	{
		return new HibernateNotetypeDAO();
	}
	
	public NoteVisibilityDAO getNoteVisibilityDAO() 
	{
		return new HibernateNoteVisibilityDAO();
	}
} 

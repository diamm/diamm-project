package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.hibernate.HibernateDAOFactory;

public abstract class DAOFactory
{
	@SuppressWarnings("unchecked")
	private static final Class FACTORY_CLASS = HibernateDAOFactory.class;

    public static DAOFactory getFactory() 
    {
        try 
        {
            return (DAOFactory)FACTORY_CLASS.newInstance();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Couldn't create Factory");
        }
    }
    
    public abstract BibliographyDAO   getBibliographyDAO();
    public abstract DiammUserDAO      getDiammUserDAO();
    public abstract UserRoleDAO       getUserRoleDAO();
    public abstract CollectionDAO     getCollectionDAO();
    public abstract SourceDAO         getSourceDAO();
    public abstract ItemDAO           getItemDAO();
    public abstract ImageDAO          getImageDAO();
    public abstract NoteDAO           getNoteDAO();
    public abstract NotetypeDAO       getNotetypeDAO();
    public abstract NoteVisibilityDAO getNoteVisibilityDAO();
}
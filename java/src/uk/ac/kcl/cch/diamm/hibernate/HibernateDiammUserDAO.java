package uk.ac.kcl.cch.diamm.hibernate;

import java.util.List;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.model.DiammUser;

public class HibernateDiammUserDAO extends HibernateDAO <DiammUser, Integer> implements DiammUserDAO
{	
    public HibernateDiammUserDAO() 
    {
	    super(DiammUser.class);
    }
    
	@SuppressWarnings("unchecked")
	public DiammUser findByUsername(String username) 
    {
    	DiammUser diammUser = null;
        Criteria crit = HibernateUtil.getSession().createCriteria(DiammUser.class);
	    crit.add(Restrictions.eq("username", username));
	    
	    List <DiammUser> list = crit.list();
	    
	    if (!list.isEmpty())
	    {
	    	diammUser = list.get(0);
	    	
	    	//pre-load collection list
	    	diammUser.getCollectionList().size();
	    }
	    
	    return diammUser;
    }
	
	@SuppressWarnings("unchecked")
	public DiammUser findByEmail(String email) 
    {
    	DiammUser diammUser = null;
        Criteria crit = HibernateUtil.getSession().createCriteria(DiammUser.class);
	    crit.add(Restrictions.eq("email", email));
	    
	    List <DiammUser> list = crit.list();
	    
	    if (!list.isEmpty())
	    {
	    	diammUser = list.get(0);
	    }
	    
	    return diammUser;
    }
}
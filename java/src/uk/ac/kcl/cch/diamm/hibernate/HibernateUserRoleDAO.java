package uk.ac.kcl.cch.diamm.hibernate;

import uk.ac.kcl.cch.diamm.dao.UserRoleDAO;
import uk.ac.kcl.cch.diamm.model.UserRole;

public class HibernateUserRoleDAO extends HibernateDAO <UserRole, Integer> implements UserRoleDAO
{	
    public HibernateUserRoleDAO() 
    {
	    super(UserRole.class);
    }
}
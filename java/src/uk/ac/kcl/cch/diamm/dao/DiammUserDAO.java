package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.model.DiammUser;

public interface DiammUserDAO extends GenericDAO <DiammUser, Integer> 
{
	public DiammUser findByUsername(String username);	
	public DiammUser findByEmail(String email);	
}

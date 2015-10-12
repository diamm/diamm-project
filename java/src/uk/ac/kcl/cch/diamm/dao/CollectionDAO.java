package uk.ac.kcl.cch.diamm.dao;

import java.util.List;

import uk.ac.kcl.cch.diamm.model.Collection;

public interface CollectionDAO extends GenericDAO <Collection, Integer> 
{
	List<Collection> find(String username, String title, Integer excludeCollectionId);	
	List<Collection> find(String username, String title);	
}

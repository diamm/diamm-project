package uk.ac.kcl.cch.diamm.dao;

import java.util.List;
import uk.ac.kcl.cch.diamm.model.Bibliography;

public interface BibliographyDAO extends GenericDAO <Bibliography, Integer> 
{
	public Integer getBibliographyCount(BibliographyDTO bibDTO);
    List<Bibliography> getBibliographyList(BibliographyDTO bibDTO, Integer firstResult, Integer maxResults);	
}

package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.model.Source;

public interface SourceDAO extends GenericDAO <Source, Integer> 
{
    public Source findByImage(Integer imageId);	
}

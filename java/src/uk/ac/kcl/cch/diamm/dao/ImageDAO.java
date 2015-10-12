package uk.ac.kcl.cch.diamm.dao;

import java.util.List;
import uk.ac.kcl.cch.diamm.model.Image;

public interface ImageDAO extends GenericDAO <Image, Integer> 
{
	public List<Image> findBySource(Integer sourceId);
}

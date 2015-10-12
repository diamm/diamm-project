package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.model.Notetype;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;

public interface NotetypeDAO extends GenericDAO <Notetype, Integer> 
{
	Notetype findByCode(NoteTypeEnum type);
}

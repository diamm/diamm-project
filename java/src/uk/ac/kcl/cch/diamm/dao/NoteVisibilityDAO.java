package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.model.NoteVisibility;
import uk.ac.kcl.cch.diamm.model.NoteVisibility.NoteVisibilityEnum;

public interface NoteVisibilityDAO extends GenericDAO <NoteVisibility, Integer> 
{
	NoteVisibility findByCode(NoteVisibilityEnum type);
}

package uk.ac.kcl.cch.diamm.dao;

import java.util.List;
import uk.ac.kcl.cch.diamm.model.Note;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;
import uk.ac.kcl.cch.diamm.ui.NoteDisplayItem;

public interface NoteDAO extends GenericDAO <Note, Integer> 
{
    List<NoteDisplayItem> findRecentPublicImageNotes(int numnotes);
	List<Note> findPublicImageNotes(Integer imageId, NoteTypeEnum type);
	List<Note> findPrivateImageNotes(Integer imageId, NoteTypeEnum type, Integer userId);
	List<Note> findPublicSourceNotes(Integer sourceId, NoteTypeEnum type);
	List<Note> findPrivateSourceNotes(Integer sourceId, NoteTypeEnum type, Integer userId);
}

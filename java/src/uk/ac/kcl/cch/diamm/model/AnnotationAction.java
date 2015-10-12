package uk.ac.kcl.cch.diamm.model;

import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.NoteDAO;
import uk.ac.kcl.cch.diamm.dao.NoteDTO;

public class AnnotationAction {
    public Note createNote(NoteDTO noteDetails) {
        DAOFactory factory = DAOFactory.getFactory();
        NoteDAO noteDao = factory.getNoteDAO();
        Note note = null;

        if ((noteDetails.getNoteText() != null && noteDetails.getNoteText().length() > 0) &&
                (noteDetails.getSourceKey() != null || noteDetails.getImageKey() != null)) {

            //set common note fields
            note = new Note();
            note.setNotetext(noteDetails.getNoteText());
            note.setType(factory.getNotetypeDAO().findByCode(noteDetails.getNoteType()));
            note.setNoteVisibility(factory.getNoteVisibilityDAO().findByCode(noteDetails.getNoteVisibility()));
            note.setUser(noteDetails.getUser());
            note.setUserkey(noteDetails.getUser().getId());
            //either set the image for the note
            if (noteDetails.getImageKey() != null) {
                note.setNoteImage(factory.getImageDAO().findByPrimaryKey(noteDetails.getImageKey()));
            }
            //or set the source for the note
            else if (noteDetails.getSourceKey() != null) {
                note.setNoteSource(factory.getSourceDAO().findByPrimaryKey(noteDetails.getSourceKey()));
            }
            note = noteDao.save(note);
        }
        return note;
    }

    public Note updateNote(NoteDTO noteDetails) {
        DAOFactory factory = DAOFactory.getFactory();
        NoteDAO noteDao = factory.getNoteDAO();
        Note note = noteDao.findByPrimaryKey(noteDetails.getNoteKey());

        //perform delete if note exists and the user originally created it
        if (note != null && note.getUser().getUsername().equals(noteDetails.getUser().getUsername())) {
            if ((noteDetails.getNoteText() != null && noteDetails.getNoteText().length() > 0) ) {
                note.setNotetext(noteDetails.getNoteText());
            }
            if (noteDetails.getNoteVisibility()!=null ) {
                note.setNoteVisibility(factory.getNoteVisibilityDAO().findByCode(noteDetails.getNoteVisibility()));
            }
            note = noteDao.save(note);
        }
        return note;
    }

    public void deleteNote(NoteDTO noteDetails) {
        DAOFactory factory = DAOFactory.getFactory();
        NoteDAO noteDao = factory.getNoteDAO();
        Note note = noteDao.findByPrimaryKey(noteDetails.getNoteKey());

        //perform delete if note exists and the user originally created it
        if (note != null && note.getUser().getUsername().equals(noteDetails.getUser().getUsername())) {
            noteDao.delete(note);
        }
    }
}

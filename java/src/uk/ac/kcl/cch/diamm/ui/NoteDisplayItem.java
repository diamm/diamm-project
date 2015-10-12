package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 11/02/11
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class NoteDisplayItem {
    private Note note;
    private Notetype notetype;
    private DiammUser user;
    private int imagekey;
    private String notetext;
    private String attachmentText;

    public NoteDisplayItem(){

        }

    private void buildAttachmentText(){
        Source s=null;
        if (note.getNoteSource()!=null){
            setAttachmentText(note.getNoteSource().getShelfmark());
        }else{
            setAttachmentText(note.getNoteImage().getSourceBySourcekey().getShelfmark()+" > "+ note.getNoteImage().getFolio());
        }
    }

    public NoteDisplayItem(Note n,int iKey){
        setNote(n);
        setImagekey(iKey);
        buildNoteText();
        buildAttachmentText();
    }

    public void buildNoteText(){
        String text=getNote().getNotetext();
        text=text.replaceAll("&nbsp;","").replaceAll("<br>","<br/>");
        notetext=text;
    }

    public NoteDisplayItem(Note n, Notetype type, DiammUser user){
        setNote(n);
        setNotetype(type);
        setUser(user);
        buildNoteText();
    }
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Notetype getNotetype() {
        return notetype;
    }

    public void setNotetype(Notetype notetype) {
        this.notetype = notetype;
    }

    public DiammUser getUser() {
        return user;
    }

    public void setUser(DiammUser user) {
        this.user = user;
    }

    public int getImagekey() {
        return imagekey;
    }

    public void setImagekey(int imagekey) {
        this.imagekey = imagekey;
    }


    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext;
    }

    public String getAttachmentText() {
        return attachmentText;
    }

    public void setAttachmentText(String attachmentText) {
        this.attachmentText = attachmentText;
    }
}

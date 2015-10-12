package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 09/02/11
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "NoteSource", catalog = "diamm_ess")
@Entity
public class NoteSource {
    private int notesourcekey;

    @javax.persistence.Column(name = "noteSourceKey")
    @Id
    public int getNotesourcekey() {
        return notesourcekey;
    }

    public void setNotesourcekey(int notesourcekey) {
        this.notesourcekey = notesourcekey;
    }

    private int notekey;

    @javax.persistence.Column(name = "noteKey",insertable = false,updatable = false)
    @Basic
    public int getNotekey() {
        return notekey;
    }

    public void setNotekey(int notekey) {
        this.notekey = notekey;
    }

    private int sourcekey;

    @javax.persistence.Column(name = "sourceKey",insertable = false,updatable = false)
    @Basic
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteSource that = (NoteSource) o;

        if (notekey != that.notekey) return false;
        if (notesourcekey != that.notesourcekey) return false;
        if (sourcekey != that.sourcekey) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = notesourcekey;
        result = 31 * result + notekey;
        result = 31 * result + sourcekey;
        return result;
    }

    private Note note;

    @ManyToOne
    @JoinColumn(name = "noteKey", referencedColumnName = "noteKey", nullable = false)
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }


    private Source source;

    @ManyToOne
    @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false)
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    /*
    private Set setBySetkey;

    @ManyToOne
    @JoinColumn(name = "setKey", referencedColumnName = "setKey", nullable = false)
    public Set getSetBySetkey() {
        return setBySetkey;
    }

    public void setSetBySetkey(Set setBySetkey) {
        this.setBySetkey = setBySetkey;
    }

    private Source sourceBySourcekey;

    @ManyToOne
    @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false)
    public Source getSourceBySourcekey() {
        return sourceBySourcekey;
    }

    public void setSourceBySourcekey(Source sourceBySourcekey) {
        this.sourceBySourcekey = sourceBySourcekey;
    }
     */
}

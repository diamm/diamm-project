package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 09/02/11
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "NoteImage", catalog = "diamm_ess")
@Entity
public class NoteImage {
    private int noteimagekey;

    @javax.persistence.Column(name = "noteImageKey")
    @Id
    public int getNoteimagekey() {
        return noteimagekey;
    }

    public void setNoteimagekey(int noteimagekey) {
        this.noteimagekey = noteimagekey;
    }

    private int notekey;

    @javax.persistence.Column(name = "noteKey")
    @Basic
    public int getNotekey() {
        return notekey;
    }

    public void setNotekey(int notekey) {
        this.notekey = notekey;
    }

    private int imagekey;

    @javax.persistence.Column(name = "imageKey",insertable = false,updatable = false)
    @Basic
    public int getImagekey() {
        return imagekey;
    }

    public void setImagekey(int imagekey) {
        this.imagekey = imagekey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteImage that = (NoteImage) o;

        if (imagekey != that.imagekey) return false;
        if (noteimagekey != that.noteimagekey) return false;
        if (notekey != that.notekey) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = noteimagekey;
        result = 31 * result + notekey;
        result = 31 * result + imagekey;
        return result;
    }


}

package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 08-Dec-2010
 * Time: 14:33:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "UserNote")
public class Usernote {
    private int usernotekey;

    @Id
    @Column(name = "userNoteKey", nullable = false, length = 10)
    public int getUsernotekey() {
        return usernotekey;
    }

    public void setUsernotekey(int usernotekey) {
        this.usernotekey = usernotekey;
    }

    private int notekey;

    @Basic
    @Column(name = "noteKey", nullable = false, length = 10)
    public int getNotekey() {
        return notekey;
    }

    public void setNotekey(int notekey) {
        this.notekey = notekey;
    }

    private int userkey;

    @Basic
    @Column(name = "userKey", nullable = false, length = 10)
    public int getUserkey() {
        return userkey;
    }

    public void setUserkey(int userkey) {
        this.userkey = userkey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usernote usernote = (Usernote) o;

        if (notekey != usernote.notekey) return false;
        if (userkey != usernote.userkey) return false;
        if (usernotekey != usernote.usernotekey) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = usernotekey;
        result = 31 * result + notekey;
        result = 31 * result + userkey;
        return result;
    }
}

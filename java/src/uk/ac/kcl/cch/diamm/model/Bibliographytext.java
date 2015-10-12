package uk.ac.kcl.cch.diamm.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 15/03/11
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "BibliographyText", catalog = "diamm_ess")
@Entity
public class Bibliographytext {
    private int bibliographytextkey;

    @javax.persistence.Column(name = "bibliographyTextKey")
    @Id
    public int getBibliographytextkey() {
        return bibliographytextkey;
    }

    public void setBibliographytextkey(int bibliographytextkey) {
        this.bibliographytextkey = bibliographytextkey;
    }

    private int bibliographykey;

    @javax.persistence.Column(name = "bibliographyKey")
    @Basic
    public int getBibliographykey() {
        return bibliographykey;
    }

    public void setBibliographykey(int bibliographykey) {
        this.bibliographykey = bibliographykey;
    }

    private int textkey;

    @javax.persistence.Column(name = "textKey")
    @Basic
    public int getTextkey() {
        return textkey;
    }

    public void setTextkey(int textkey) {
        this.textkey = textkey;
    }

    private String notes;

   @Basic
    @Column(name = "notes", length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bibliographytext that = (Bibliographytext) o;

        if (bibliographykey != that.bibliographykey) return false;
        if (bibliographytextkey != that.bibliographytextkey) return false;
        if (textkey != that.textkey) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bibliographytextkey;
        result = 31 * result + bibliographykey;
        result = 31 * result + textkey;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}

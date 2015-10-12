package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 15/03/11
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "BibliographyItem", catalog = "diamm_ess")
@Entity
public class Bibliographyitem {
    private int bibliographyitemkey;

    @javax.persistence.Column(name = "bibliographyItemKey")
    @Id
    public int getBibliographyitemkey() {
        return bibliographyitemkey;
    }

    public void setBibliographyitemkey(int bibliographyitemkey) {
        this.bibliographyitemkey = bibliographyitemkey;
    }

    private int bibliographykey;

    @javax.persistence.Column(name = "bibliographyKey",updatable = false,insertable = false)
    @Basic
    public int getBibliographykey() {
        return bibliographykey;
    }

    public void setBibliographykey(int bibliographykey) {
        this.bibliographykey = bibliographykey;
    }

    private int itemkey;

    @javax.persistence.Column(name = "itemKey",updatable = false,insertable = false)
    @Basic
    public int getItemkey() {
        return itemkey;
    }

    public void setItemkey(int itemkey) {
        this.itemkey = itemkey;
    }


    private Item itemByItemkey;

    @ManyToOne
    @JoinColumn(name = "itemKey", referencedColumnName = "itemKey", nullable = false)
    public Item getItemByItemkey() {
        return itemByItemkey;
    }

    public void setItemByItemkey(Item itemByItemkey) {
        this.itemByItemkey = itemByItemkey;
    }


    private Bibliography bibliography;

    @ManyToOne
    @JoinColumn(name = "bibliographyKey", referencedColumnName = "bibliographyKey")
    public Bibliography getBibliography() {
        return bibliography;
    }

    public void setBibliography(Bibliography bibliography) {
        this.bibliography = bibliography;
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

        Bibliographyitem that = (Bibliographyitem) o;

        if (bibliographyitemkey != that.bibliographyitemkey) return false;
        if (bibliographykey != that.bibliographykey) return false;
        if (itemkey != that.itemkey) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bibliographyitemkey;
        result = 31 * result + bibliographykey;
        result = 31 * result + itemkey;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}

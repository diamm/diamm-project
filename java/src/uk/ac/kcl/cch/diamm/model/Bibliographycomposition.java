package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 15/03/11
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "BibliographyComposition", catalog = "diamm_ess")
@Entity
public class Bibliographycomposition {
    private int bibliographycompositionkey;

    @javax.persistence.Column(name = "bibliographyCompositionKey")
    @Id
    public int getBibliographycompositionkey() {
        return bibliographycompositionkey;
    }

    public void setBibliographycompositionkey(int bibliographycompositionkey) {
        this.bibliographycompositionkey = bibliographycompositionkey;
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

    private int compositionkey;

    @javax.persistence.Column(name = "compositionKey",insertable = false,updatable = false)
    @Basic
    public int getCompositionkey() {
        return compositionkey;
    }

    public void setCompositionkey(int compositionkey) {
        this.compositionkey = compositionkey;
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

        Bibliographycomposition that = (Bibliographycomposition) o;

        if (bibliographycompositionkey != that.bibliographycompositionkey) return false;
        if (bibliographykey != that.bibliographykey) return false;
        if (compositionkey != that.compositionkey) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bibliographycompositionkey;
        result = 31 * result + bibliographykey;
        result = 31 * result + compositionkey;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    /*
    private Item itemByItemkey;

    @ManyToOne
    @JoinColumn(name = "itemKey", referencedColumnName = "itemKey", nullable = false)
    public Item getItemByItemkey() {
        return itemByItemkey;
    }

    public void setItemByItemkey(Item itemByItemkey) {
        this.itemByItemkey = itemByItemkey;
    }
     */

    private Composition composition;
    @ManyToOne
    @JoinColumn(name = "compositionKey", referencedColumnName = "compositionKey", nullable = false)
    public Composition getComposition() {
        return composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }
}

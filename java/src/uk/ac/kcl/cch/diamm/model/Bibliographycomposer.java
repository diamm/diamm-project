package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 15/03/11
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "BibliographyComposer", catalog = "diamm_ess")
@Entity
public class Bibliographycomposer {
    private int bibliographycomposerkey;

    @Column(name = "bibliographyComposerKey")
    @Id
    public int getBibliographycompositionkey() {
        return bibliographycomposerkey;
    }

    public void setBibliographycompositionkey(int bibliographycompositionkey) {
        this.bibliographycomposerkey = bibliographycompositionkey;
    }

    private int bibliographykey;

    @Column(name = "bibliographyKey")
    @Basic
    public int getBibliographykey() {
        return bibliographykey;
    }

    public void setBibliographykey(int bibliographykey) {
        this.bibliographykey = bibliographykey;
    }

    private int composerkey;

    @Column(name = "composerkey",insertable = false,updatable = false)
    @Basic
    public int getComposerkey() {
        return composerkey;
    }

    public void setComposerkey(int composerkey) {
        this.composerkey = composerkey;
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

        Bibliographycomposer that = (Bibliographycomposer) o;

        if (bibliographycomposerkey != that.bibliographycomposerkey) return false;
        if (bibliographykey != that.bibliographykey) return false;
        if (composerkey != that.composerkey) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bibliographycomposerkey;
        result = 31 * result + bibliographykey;
        result = 31 * result + composerkey;
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

    private Composer composer;
    @ManyToOne
    @JoinColumn(name = "composerKey", referencedColumnName = "composerKey", nullable = false)
    public Composer getComposer() {
        return composer;
    }

    public void setComposer(Composer composer) {
        this.composer = composer;
    }
}

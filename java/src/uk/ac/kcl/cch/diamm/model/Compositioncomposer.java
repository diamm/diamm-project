package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "CompositionComposer")
public class Compositioncomposer {
    private int compositioncomposerkey;

    @Id
    @Column(name = "compositionComposerKey", nullable = false, length = 10)
    public int getCompositioncomposerkey() {
        return compositioncomposerkey;
    }

    public void setCompositioncomposerkey(int compositioncomposerkey) {
        this.compositioncomposerkey = compositioncomposerkey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compositioncomposer that = (Compositioncomposer) o;

        if (compositioncomposerkey != that.compositioncomposerkey) return false;

        return true;
    }

    public int hashCode() {
        return compositioncomposerkey;
    }

    private String attributionuncertain;

    @Basic
    @Column(name="attribution_uncertain", length = 2147483647)
    public String getAttributionuncertain() {
        return attributionuncertain;
    }

    public void setAttributionuncertain(String attributionuncertain) {
        this.attributionuncertain = attributionuncertain;
    }

    private Composition compositionByCompositionkey;

    @ManyToOne
    @JoinColumn(name = "compositionKey", referencedColumnName = "compositionKey", nullable = false)
    public Composition getCompositionByCompositionkey() {
        return compositionByCompositionkey;
    }

    public void setCompositionByCompositionkey(Composition compositionByCompositionkey) {
        this.compositionByCompositionkey = compositionByCompositionkey;
    }

    private Composer composerByComposerkey;

    @ManyToOne
    @JoinColumn(name = "composerKey", referencedColumnName = "composerKey", nullable = false)
    public Composer getComposerByComposerkey() {
        return composerByComposerkey;
    }

    public void setComposerByComposerkey(Composer composerByComposerkey) {
        this.composerByComposerkey = composerByComposerkey;
    }


    private int composerkey;
    @Basic
    @Column(name = "composerKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getComposerkey() {
        return composerkey;
    }

    public void setComposerkey(int composerkey) {
        this.composerkey = composerkey;
    }

    private int compositionkey;
    @Basic
     @Column(name = "compositionKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getCompositionkey() {
        return compositionkey;
    }

    public void setCompositionkey(int compositionkey) {
        this.compositionkey = compositionkey;
    }
}

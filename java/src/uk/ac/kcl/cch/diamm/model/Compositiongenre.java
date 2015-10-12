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
@Table(catalog = "diamm_ess", name = "CompositionGenre")
public class Compositiongenre {
    private int compositiongenrekey;

    @Id
    @Column(name = "compositionGenreKey", nullable = false, length = 10)
    public int getCompositiongenrekey() {
        return compositiongenrekey;
    }

    public void setCompositiongenrekey(int compositiongenrekey) {
        this.compositiongenrekey = compositiongenrekey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compositiongenre that = (Compositiongenre) o;

        if (compositiongenrekey != that.compositiongenrekey) return false;

        return true;
    }

    public int hashCode() {
        return compositiongenrekey;
    }

    private Composition compositionByCompositionkey;

    @ManyToOne
    @JoinColumn(name = "compositionKey", referencedColumnName = "compositionKey")
    public Composition getCompositionByCompositionkey() {
        return compositionByCompositionkey;
    }

    public void setCompositionByCompositionkey(Composition compositionByCompositionkey) {
        this.compositionByCompositionkey = compositionByCompositionkey;
    }

    private int algenrekey;

    @Basic
    @Column(name = "alGenreKey", nullable = false, length = 10)
    public int getAlgenrekey() {
        return algenrekey;
    }

    public void setAlgenrekey(int algenrekey) {
        this.algenrekey = algenrekey;
    }

    private Algenre algenreByAlgenrekey;

    @ManyToOne
    @JoinColumn(name = "alGenreKey", referencedColumnName = "alGenreKey",insertable = false,updatable = false)
    public Algenre getAlgenreByAlgenrekey() {
        return algenreByAlgenrekey;
    }

    public void setAlgenreByAlgenrekey(Algenre algenreByAlgenrekey) {
        this.algenreByAlgenrekey = algenreByAlgenrekey;
    }
}

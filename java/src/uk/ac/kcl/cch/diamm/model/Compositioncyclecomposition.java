package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 27/05/11
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess",name = "CompositionCycleComposition")
public class Compositioncyclecomposition {
    private int compositioncyclecompositionkey;

    @javax.persistence.Column(name = "compositionCycleCompositionKey")
    @Id
    public int getCompositioncyclecompositionkey() {
        return compositioncyclecompositionkey;
    }

    public void setCompositioncyclecompositionkey(int compositioncyclecompositionkey) {
        this.compositioncyclecompositionkey = compositioncyclecompositionkey;
    }

    private Composition compositionByCompositionkey;

    @ManyToOne
    @JoinColumn(name = "compositionKey", referencedColumnName = "compositionKey", nullable = false,insertable = false,updatable = false)
    public Composition getCompositionByCompositionkey() {
        return compositionByCompositionkey;
    }

    public void setCompositionByCompositionkey(Composition compositionByCompositionkey) {
        this.compositionByCompositionkey = compositionByCompositionkey;
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


    private Compositioncycle compositioncycle;

    @ManyToOne
    @JoinColumn(name = "compositionCycleKey", referencedColumnName = "compositionCycleKey", nullable = false,insertable = false,updatable = false)
    public Compositioncycle getCompositioncycle() {
        return compositioncycle;
    }

    public void setCompositioncycle(Compositioncycle compositioncycle) {
        this.compositioncycle = compositioncycle;
    }

    private int compositioncyclekey;

    @javax.persistence.Column(name = "compositionCycleKey",insertable = false,updatable = false)
    @Basic
    public int getCompositioncyclekey() {
        return compositioncyclekey;
    }

    public void setCompositioncyclekey(int compositioncyclekey) {
        this.compositioncyclekey = compositioncyclekey;
    }

    private int orderno;

    @javax.persistence.Column(name = "orderNo")
    @Basic
    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    private String positiontitle;

    @javax.persistence.Column(name = "positionTitle", length = 2147483647)
    @Basic
    public String getPositiontitle() {
        return positiontitle;
    }

    public void setPositiontitle(String positiontitle) {
        this.positiontitle = positiontitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compositioncyclecomposition that = (Compositioncyclecomposition) o;

        if (compositioncyclecompositionkey != that.compositioncyclecompositionkey) return false;
        if (compositioncyclekey != that.compositioncyclekey) return false;
        if (compositionkey != that.compositionkey) return false;
        if (orderno != that.orderno) return false;
        if (positiontitle != null ? !positiontitle.equals(that.positiontitle) : that.positiontitle != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = compositioncyclecompositionkey;
        result = 31 * result + compositionkey;
        result = 31 * result + compositioncyclekey;
        result = 31 * result + orderno;
        result = 31 * result + (positiontitle != null ? positiontitle.hashCode() : 0);
        return result;
    }
}

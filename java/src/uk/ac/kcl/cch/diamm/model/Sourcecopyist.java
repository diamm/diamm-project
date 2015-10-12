package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:29
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "SourceCopyist")
public class Sourcecopyist {
    private int sourcecopyistkey;

    @Id
    @Column(name = "sourceCopyistKey", nullable = false, length = 10)
    public int getSourcecopyistkey() {
        return sourcecopyistkey;
    }

    public void setSourcecopyistkey(int sourcecopyistkey) {
        this.sourcecopyistkey = sourcecopyistkey;
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



    private int sourcekey;

    @Basic
    @Column(name = "sourceKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }


    private String attributionUncertain;

    @Basic
    @Column(name = "attribution_uncertain", length = 2147483647)
    public String getAttributionUncertain() {
        return attributionUncertain;
    }

    public void setAttributionUncertain(String attributionUncertain) {
        this.attributionUncertain = attributionUncertain;
    }



    private Alcopyist alcopyist;

    @ManyToOne
    @JoinColumn(name = "alcopyistKey",referencedColumnName = "alcopyistKey",nullable = false)
    public Alcopyist getAlcopyist() {
        return alcopyist;
    }

    public void setAlcopyist(Alcopyist alcopyist) {
        this.alcopyist = alcopyist;
    }

    private int alcopyistkey;

    @Basic
    @Column(name = "alcopyistKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlcopyistkey() {
        return alcopyistkey;
    }

    public void setAlcopyistkey(int alcopyistkey) {
        this.alcopyistkey = alcopyistkey;
    }


    private Alcopyisttype alcopyisttype;

    @ManyToOne
    @JoinColumn(name = "alcopyistTypeKey",referencedColumnName = "alcopyistTypeKey",nullable = false)
    public Alcopyisttype getAlcopyisttype() {
        return alcopyisttype;
    }

    public void setAlcopyisttype(Alcopyisttype alcopyisttype) {
        this.alcopyisttype = alcopyisttype;
    }

    private int alcopyisttypekey;

    @Basic
    @Column(name = "alcopyistTypeKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlcopyisttypekey() {
        return alcopyisttypekey;
    }

    public void setAlcopyisttypekey(int alcopyisttypekey) {
        this.alcopyisttypekey = alcopyisttypekey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sourcecopyist that = (Sourcecopyist) o;

        if (alcopyistkey != that.alcopyistkey) return false;
        if (alcopyisttypekey != that.alcopyisttypekey) return false;
        if (sourcecopyistkey != that.sourcecopyistkey) return false;
        if (sourcekey != that.sourcekey) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = sourcecopyistkey;
        result = 31 * result + sourcekey;
        result = 31 * result + alcopyistkey;
        result = 31 * result + alcopyisttypekey;
        return result;
    }
}

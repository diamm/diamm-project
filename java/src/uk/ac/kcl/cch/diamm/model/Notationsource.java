package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "NotationSource")
public class Notationsource {
    private int notationsourcekey;

    @Id
    @Column(name = "notationSourceKey", nullable = false, length = 10)
    public int getNotationsourcekey() {
        return notationsourcekey;
    }

    public void setNotationsourcekey(int notationsourcekey) {
        this.notationsourcekey = notationsourcekey;
    }

    private int alnotationtypekey;

    @Basic
    @Column(name = "alnotationtypekey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlnotationtypekey() {
        return alnotationtypekey;
    }

    public void setAlnotationtypekey(int alnotationtypekey) {
        this.alnotationtypekey = alnotationtypekey;
    }

    private int sourcekey;

    @Basic
    @Column(name = "sourceKey", nullable = false, length = 10)
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notationsource that = (Notationsource) o;

        if (alnotationtypekey != that.alnotationtypekey) return false;
        if (notationsourcekey != that.notationsourcekey) return false;
        if (sourcekey != that.sourcekey) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = notationsourcekey;
        result = 31 * result + alnotationtypekey;
        result = 31 * result + sourcekey;
        return result;
    }

    private Alnotationtype alnotationtype;

    @OneToOne
    @JoinColumn(name = "alnotationtypekey", referencedColumnName = "alnotationtypekey", nullable = false,insertable = false,updatable = false)
    public Alnotationtype getAlnotationtype() {
        return alnotationtype;
    }

    public void setAlnotationtype(Alnotationtype alnotationtype) {
        this.alnotationtype = alnotationtype;
    }

    private Source source;

    @ManyToOne
    @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false,insertable = false,updatable = false)
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}

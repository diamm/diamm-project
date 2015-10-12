package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alCopyistType")
public class Alcopyisttype {
    private int alcopyisttypekey;

    @Id
    @Column(name = "alcopyistTypeKey", nullable = false, length = 10)
    public int getAlcopyisttypekey() {
        return alcopyisttypekey;
    }

    public void setAlcopyisttypekey(int alcopyisttypekey) {
        this.alcopyisttypekey = alcopyisttypekey;
    }

    private String copyisttype;

    @Basic
    @Column(name = "copyistType", nullable = false, length = 2147483647)
    public String getCopyisttype() {
        return copyisttype;
    }

    public void setCopyisttype(String copyisttype) {
        this.copyisttype = copyisttype;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alcopyisttype that = (Alcopyisttype) o;

        if (alcopyisttypekey != that.alcopyisttypekey) return false;
        if (copyisttype != null ? !copyisttype.equals(that.copyisttype) : that.copyisttype != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alcopyisttypekey;
        result = 31 * result + (copyisttype != null ? copyisttype.hashCode() : 0);
        return result;
    }
}

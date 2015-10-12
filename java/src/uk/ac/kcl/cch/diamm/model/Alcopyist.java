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
@Table(catalog = "diamm_ess", name = "alCopyist")
public class Alcopyist {
    private int alcopyistkey;

    @Id
    @Column(name = "alcopyistKey", nullable = false, length = 10)
    public int getAlcopyistkey() {
        return alcopyistkey;
    }

    public void setAlcopyistkey(int alcopyistkey) {
        this.alcopyistkey = alcopyistkey;
    }

    private String copyistname;

    @Basic
    @Column(name = "copyistName", length = 2147483647)
    public String getCopyistname() {
        return copyistname;
    }

    public void setCopyistname(String copyistname) {
        this.copyistname = copyistname;
    }

    private int alaffiliationkey;

    @Basic
    @Column(name = "alaffiliationKey", nullable = false, length = 10)
    public int getAlaffiliationkey() {
        return alaffiliationkey;
    }

    public void setAlaffiliationkey(int alaffiliationkey) {
        this.alaffiliationkey = alaffiliationkey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alcopyist alcopyist = (Alcopyist) o;

        if (alaffiliationkey != alcopyist.alaffiliationkey) return false;
        if (alcopyistkey != alcopyist.alcopyistkey) return false;
        if (copyistname != null ? !copyistname.equals(alcopyist.copyistname) : alcopyist.copyistname != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alcopyistkey;
        result = 31 * result + (copyistname != null ? copyistname.hashCode() : 0);
        result = 31 * result + alaffiliationkey;
        return result;
    }
}

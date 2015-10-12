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
@Table(catalog = "diamm_ess", name = "alAffiliation")
public class Alaffiliation {
    private int alaffiliationkey;

    @Id
    @Column(name = "alaffiliationKey", nullable = false, length = 10)
    public int getAlaffiliationkey() {
        return alaffiliationkey;
    }

    public void setAlaffiliationkey(int alaffiliationkey) {
        this.alaffiliationkey = alaffiliationkey;
    }

    private String affiliation;

    @Basic
    @Column(name = "affiliation", length = 2147483647)
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alaffiliation that = (Alaffiliation) o;

        if (alaffiliationkey != that.alaffiliationkey) return false;
        if (affiliation != null ? !affiliation.equals(that.affiliation) : that.affiliation != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alaffiliationkey;
        result = 31 * result + (affiliation != null ? affiliation.hashCode() : 0);
        return result;
    }



    
}

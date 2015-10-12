package uk.ac.kcl.cch.diamm.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 27/05/11
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess",name = "alCycleType")
public class Alcycletype {
    private int alcycletypekey;

    @javax.persistence.Column(name = "alCycleTypeKey")
    @Id
    public int getAlcycletypekey() {
        return alcycletypekey;
    }

    public void setAlcycletypekey(int alcycletypekey) {
        this.alcycletypekey = alcycletypekey;
    }

    private String cycletype;

    @javax.persistence.Column(name = "cycleType", length = 2147483647)
    @Basic
    public String getCycletype() {
        return cycletype;
    }

    public void setCycletype(String cycletype) {
        this.cycletype = cycletype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alcycletype that = (Alcycletype) o;

        if (alcycletypekey != that.alcycletypekey) return false;
        if (cycletype != null ? !cycletype.equals(that.cycletype) : that.cycletype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = alcycletypekey;
        result = 31 * result + (cycletype != null ? cycletype.hashCode() : 0);
        return result;
    }
}

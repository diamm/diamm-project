package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alSetType")
public class Alsettype {
    private int alsettypekey;

    @Id
    @Column(name = "alsetTypeKey", nullable = false, length = 10)
    public int getAlsettypekey() {
        return alsettypekey;
    }

    public void setAlsettypekey(int alsettypekey) {
        this.alsettypekey = alsettypekey;
    }

    private String description;

    @Basic
    @Column(name = "description", length = 2147483647)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alsettype alsettype = (Alsettype) o;

        if (alsettypekey != alsettype.alsettypekey) return false;
        if (description != null ? !description.equals(alsettype.description) : alsettype.description != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alsettypekey;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}

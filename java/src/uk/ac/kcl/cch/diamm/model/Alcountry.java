package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alCountry")
public class Alcountry {
    private int alcountrykey;

    @Id
    @Column(name = "alCountryKey", nullable = false, length = 10)
    public int getAlcountrykey() {
        return alcountrykey;
    }

    public void setAlcountrykey(int alcountrykey) {
        this.alcountrykey = alcountrykey;
    }

    private String country;

    @Basic
    @Column(name = "country", nullable = false, length = 2147483647)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alcountry alcountry = (Alcountry) o;

        if (alcountrykey != alcountry.alcountrykey) return false;
        if (country != null ? !country.equals(alcountry.country) : alcountry.country != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alcountrykey;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    private Collection<Alcity> alcitiesByAlcountrykey;

    @OneToMany(mappedBy = "alcountryByAlcountrykey")
    @OrderBy("city")
    public Collection<Alcity> getAlcitiesByAlcountrykey() {
        return alcitiesByAlcountrykey;
    }

    public void setAlcitiesByAlcountrykey(Collection<Alcity> alcitiesByAlcountrykey) {
        this.alcitiesByAlcountrykey = alcitiesByAlcountrykey;
    }
}

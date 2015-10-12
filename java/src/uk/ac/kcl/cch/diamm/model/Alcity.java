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
@Table(catalog = "diamm_ess", name = "alCity")
public class Alcity {
    private int alcitykey;

    @Id
    @Column(name = "alCityKey", nullable = false, length = 10)
    public int getAlcitykey() {
        return alcitykey;
    }

    public void setAlcitykey(int alcitykey) {
        this.alcitykey = alcitykey;
    }

    private String city;

    @Basic
    @Column(name = "city", nullable = false, length = 2147483647)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /*private int countryAlcountrykey;

    @Basic
    @Column(name = "alCountryKey", nullable = false, length = 10)
    public int getCountryAlcountrykey() {
        return countryAlcountrykey;
    }

    public void setCountryAlcountrykey(int countryAlcountrykey) {
        this.countryAlcountrykey = countryAlcountrykey;
    }*/

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alcity alcity = (Alcity) o;

        if (alcitykey != alcity.alcitykey) return false;
        //if (countryAlcountrykey != alcity.countryAlcountrykey) return false;
        if (city != null ? !city.equals(alcity.city) : alcity.city != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alcitykey;
        result = 31 * result + (city != null ? city.hashCode() : 0);
       // result = 31 * result + countryAlcountrykey;
        return result;
    }

    private Collection<Archive> archivesByAlcitykey;

    @OneToMany(mappedBy = "alcityByAlcitykey")
    @OrderBy("archivename")
    public Collection<Archive> getArchivesByAlcitykey() {
        return archivesByAlcitykey;
    }

    public void setArchivesByAlcitykey(Collection<Archive> archivesByAlcitykey) {
        this.archivesByAlcitykey = archivesByAlcitykey;
    }

    private Alcountry alcountryByAlcountrykey;

    @ManyToOne
    @JoinColumn(name = "alCountryKey", referencedColumnName = "alCountryKey", nullable = false)
    public Alcountry getAlcountryByAlcountrykey() {
        return alcountryByAlcountrykey;
    }

    public void setAlcountryByAlcountrykey(Alcountry alcountryByAlcountrykey) {
        this.alcountryByAlcountrykey = alcountryByAlcountrykey;
    }
}

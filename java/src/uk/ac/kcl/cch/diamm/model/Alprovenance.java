package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alProvenance")
public class Alprovenance {
    private int alprovenancekey;

    @Id
    @Column(name = "alProvenanceKey", nullable = false, length = 10)
    public int getAlprovenancekey() {
        return alprovenancekey;
    }

    public void setAlprovenancekey(int alprovenancekey) {
        this.alprovenancekey = alprovenancekey;
    }

    private String provenance;

    @Basic
    @Column(name = "provenance", length = 2147483647)
    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    private String city;

    @Basic
    @Column(name = "city", length = 2147483647)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String country;

    @Basic
    @Column(name = "country", length = 2147483647)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String region;

    @Basic
    @Column(name = "region", length = 2147483647)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String protectorate;

    @Basic
    @Column(name = "protectorate", length = 2147483647)
    public String getProtectorate() {
        return protectorate;
    }

    public void setProtectorate(String protectorate) {
        this.protectorate = protectorate;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alprovenance that = (Alprovenance) o;

        if (alprovenancekey != that.alprovenancekey) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (protectorate != null ? !protectorate.equals(that.protectorate) : that.protectorate != null) return false;
        if (provenance != null ? !provenance.equals(that.provenance) : that.provenance != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alprovenancekey;
        result = 31 * result + (provenance != null ? provenance.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (protectorate != null ? protectorate.hashCode() : 0);
        return result;
    }

    private Collection<Sourceprovenance> sourceprovenancesByAlprovenancekey;

    @OneToMany(mappedBy = "alprovenanceByAlprovenancekey")
    public Collection<Sourceprovenance> getSourceprovenancesByAlprovenancekey() {
        return sourceprovenancesByAlprovenancekey;
    }

    public void setSourceprovenancesByAlprovenancekey(Collection<Sourceprovenance> sourceprovenancesByAlprovenancekey) {
        this.sourceprovenancesByAlprovenancekey = sourceprovenancesByAlprovenancekey;
    }

    private Collection<Source> sources;

    @ManyToMany(mappedBy = "alprovenances")
    public Collection<Source> getSources() {
        return sources;
    }

    public void setSources(Collection<Source> sources) {
        this.sources = sources;
    }
}

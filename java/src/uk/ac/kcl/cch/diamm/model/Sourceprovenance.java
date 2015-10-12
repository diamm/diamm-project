package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "SourceProvenance")
public class Sourceprovenance {
    private int sourceprovenancekey;

    @Id
    @Column(name = "sourceProvenanceKey", nullable = false, length = 10)
    public int getSourceprovenancekey() {
        return sourceprovenancekey;
    }

    public void setSourceprovenancekey(int sourceprovenancekey) {
        this.sourceprovenancekey = sourceprovenancekey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sourceprovenance that = (Sourceprovenance) o;

        if (sourceprovenancekey != that.sourceprovenancekey) return false;

        return true;
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



    private String institution;

    @Basic
    @Column(name = "institution", length = 2147483647)
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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

    private String region;

    @Basic
    @Column(name = "region", length = 2147483647)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    private String uncertain;

    @Basic
    @Column(name = "uncertain", length = 2147483647)
    public String getUncertain() {
        return uncertain;
    }

    public void setUncertain(String uncertain) {
        this.uncertain = uncertain;
    }

    public int hashCode() {
        return sourceprovenancekey;
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

    private Alprovenance alprovenanceByAlprovenancekey;

    @ManyToOne
    @JoinColumn(name = "alProvenanceKey", referencedColumnName = "alProvenanceKey", nullable = false)
    public Alprovenance getAlprovenanceByAlprovenancekey() {
        return alprovenanceByAlprovenancekey;
    }

    public void setAlprovenanceByAlprovenancekey(Alprovenance alprovenanceByAlprovenancekey) {
        this.alprovenanceByAlprovenancekey = alprovenanceByAlprovenancekey;
    }
}

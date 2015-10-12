package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "SourceAlPerson")
public class Sourcealperson {
    private int sourcealpersonkey;

    @Id
    @Column(name = "sourceAlPersonKey", nullable = false, length = 10)
    public int getSourcealpersonkey() {
        return sourcealpersonkey;
    }

    public void setSourcealpersonkey(int sourcealpersonkey) {
        this.sourcealpersonkey = sourcealpersonkey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sourcealperson that = (Sourcealperson) o;

        if (sourcealpersonkey != that.sourcealpersonkey) return false;

        return true;
    }

    public int hashCode() {
        return sourcealpersonkey;
    }


    private String attributionUncertain;

    @Basic
    @Column(name = "attribution_uncertain", length = 2147483647)
    public String getAttributionUncertain() {
        return attributionUncertain;
    }

    public void setAttributionUncertain(String attributionUncertain) {
        this.attributionUncertain = attributionUncertain;
    }

    private int alpersonkey;
    @Column(name = "alPersonKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlpersonkey() {
        return alpersonkey;
    }

    public void setAlpersonkey(int alpersonkey) {
        this.alpersonkey = alpersonkey;
    }

    private Alperson alpersonByAlpersonkey;

    @ManyToOne
    @JoinColumn(name = "alPersonKey", referencedColumnName = "alPersonKey", nullable = false)
    public Alperson getAlpersonByAlpersonkey() {
        return alpersonByAlpersonkey;
    }

    public void setAlpersonByAlpersonkey(Alperson alpersonByAlpersonkey) {
        this.alpersonByAlpersonkey = alpersonByAlpersonkey;
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

    private Collection<Source> sources;

    @ManyToMany(mappedBy = "persons")
    public Collection<Source> getSources() {
        return sources;
    }

    public void setSources(Collection<Source> sources) {
        this.sources = sources;
    }

    private AlPersonRelationship alpersonrelationship;

    @ManyToOne
    @JoinColumn(name = "alPersonRelationshipKey", nullable = false)
    public AlPersonRelationship getAlpersonrelationship() {
        return alpersonrelationship;
    }

    public void setAlpersonrelationship(AlPersonRelationship alpersonrelationship) {
        this.alpersonrelationship = alpersonrelationship;
    }
}

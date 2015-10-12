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
@Table(catalog = "diamm_ess", name = "SourceSet")
public class Sourceset {
    private int sourcesetkey;

    @Id
    @Column(name = "sourceSetKey", nullable = false, length = 10)
    public int getSourcesetkey() {
        return sourcesetkey;
    }

    public void setSourcesetkey(int sourcesetkey) {
        this.sourcesetkey = sourcesetkey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sourceset sourceset = (Sourceset) o;

        if (sourcesetkey != sourceset.sourcesetkey) return false;

        return true;
    }

    public int hashCode() {
        return sourcesetkey;
    }

    private Set setBySetkey;

    @ManyToOne
    @JoinColumn(name = "setKey", referencedColumnName = "setKey", nullable = false)
    public Set getSetBySetkey() {
        return setBySetkey;
    }

    public void setSetBySetkey(Set setBySetkey) {
        this.setBySetkey = setBySetkey;
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
}

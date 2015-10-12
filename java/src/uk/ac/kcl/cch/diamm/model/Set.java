package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:56
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Set {
    private int setkey;

    @Id
    @Column(name = "setKey", nullable = false, length = 10)
    public int getSetkey() {
        return setkey;
    }

    public void setSetkey(int setkey) {
        this.setkey = setkey;
    }

    private int settypekey;

    @Basic
    @Column(name = "setTypeKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getSettypekey() {
        return settypekey;
    }

    public void setSettypekey(int settypekey) {
        this.settypekey = settypekey;
    }


    private Alsettype alsettype;

    @OneToOne
    @JoinColumn(name = "setTypeKey", referencedColumnName = "alsetTypeKey", nullable = false)
    public Alsettype getAlsettype() {
        return alsettype;
    }

    public void setAlsettype(Alsettype alsettype) {
        this.alsettype = alsettype;
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


    private String clustershelfmark;

    @Basic
    @Column(name = "clusterShelfMark", length = 2147483647)
    public String getClustershelfmark() {
        return clustershelfmark;
    }

    public void setClustershelfmark(String clustershelfmark) {
        this.clustershelfmark = clustershelfmark;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Set set = (Set) o;

        if (setkey != set.setkey) return false;
        if (settypekey != set.settypekey) return false;
        if (description != null ? !description.equals(set.description) : set.description != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = setkey;
        result = 31 * result + settypekey;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    private Collection<Sourceset> sourcesetsBySetkey;

    @OneToMany(mappedBy = "setBySetkey")
    public Collection<Sourceset> getSourcesetsBySetkey() {
        return sourcesetsBySetkey;
    }

    public void setSourcesetsBySetkey(Collection<Sourceset> sourcesetsBySetkey) {
        this.sourcesetsBySetkey = sourcesetsBySetkey;
    }

    private Source source;

    @ManyToOne
    @JoinTable(catalog = "diamm_ess", name = "SourceSet", joinColumns = @JoinColumn(name = "setKey", referencedColumnName = "setKey", nullable = false), inverseJoinColumns = @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false))
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    private Collection<Source> sources;

    @ManyToMany
    @JoinTable(catalog = "diamm_ess", name = "SourceSet", joinColumns = @JoinColumn(name = "setKey", referencedColumnName = "setKey", nullable = false), inverseJoinColumns = @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false))
    public Collection<Source> getSources() {
        return sources;
    }

    public void setSources(Collection<Source> sources) {
        this.sources = sources;
    }
}

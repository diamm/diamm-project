package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alNotationType")
public class Alnotationtype {
    private int alnotationtypekey;

    @Id
    @Column(name = "alnotationtypekey", nullable = false, length = 10)
    public int getAlnotationtypekey() {
        return alnotationtypekey;
    }

    public void setAlnotationtypekey(int alnotationtypekey) {
        this.alnotationtypekey = alnotationtypekey;
    }

    private String musicnotation;

    @Basic
    @Column(name = "musicnotation", length = 2147483647)
    public String getMusicnotation() {
        return musicnotation;
    }

    public void setMusicnotation(String musicnotation) {
        this.musicnotation = musicnotation;
    }

    private String notationType;

    @Basic
    @Column(name = "notation_type", length = 2147483647)
    public String getNotationType() {
        return notationType;
    }

    public void setNotationType(String notationType) {
        this.notationType = notationType;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alnotationtype that = (Alnotationtype) o;

        if (alnotationtypekey != that.alnotationtypekey) return false;
        if (musicnotation != null ? !musicnotation.equals(that.musicnotation) : that.musicnotation != null)
            return false;
        if (notationType != null ? !notationType.equals(that.notationType) : that.notationType != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alnotationtypekey;
        result = 31 * result + (musicnotation != null ? musicnotation.hashCode() : 0);
        result = 31 * result + (notationType != null ? notationType.hashCode() : 0);
        return result;
    }

    private Collection<Source> sources;

    @ManyToMany(mappedBy = "alnotationtypes")
    public Collection<Source> getSources() {
        return sources;
    }

    public void setSources(Collection<Source> sources) {
        this.sources = sources;
    }
}

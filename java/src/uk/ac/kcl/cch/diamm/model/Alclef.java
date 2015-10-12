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
@Table(catalog = "diamm_ess", name = "alClef")
public class Alclef {
    private int alclefkey;

    @Id
    @Column(name = "alClefKey", nullable = false, length = 10)
    public int getAlclefkey() {
        return alclefkey;
    }

    public void setAlclefkey(int alclefkey) {
        this.alclefkey = alclefkey;
    }

    private String clef;

    @Basic
    @Column(name = "clef",length = 2147483647)
    public String getClef() {
        return clef;
    }

    public void setClef(String clef) {
        this.clef = clef;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alclef alclef = (Alclef) o;

        if (alclefkey != alclef.alclefkey) return false;
        if (clef != null ? !clef.equals(alclef.clef) : alclef.clef != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alclefkey;
        result = 31 * result + (clef != null ? clef.hashCode() : 0);
        return result;
    }

    private Collection<Text> textsByAlclefkey;

    @OneToMany(mappedBy = "alclefbyalclefkey")
    public Collection<Text> getTextsByAlclefkey() {
        return textsByAlclefkey;
    }

    public void setTextsByAlclefkey(Collection<Text> textsByAlclefkey) {
        this.textsByAlclefkey = textsByAlclefkey;
    }
}

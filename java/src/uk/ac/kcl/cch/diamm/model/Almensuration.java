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
@Table(catalog = "diamm_ess", name = "alMensuration")
public class Almensuration {
    private int almensurationkey;

    @Id
    @Column(name = "alMensurationKey", nullable = false, length = 10)
    public int getAlmensurationkey() {
        return almensurationkey;
    }

    public void setAlmensurationkey(int almensurationkey) {
        this.almensurationkey = almensurationkey;
    }

    private String mensurationsign;

    @Basic
    @Column(name = "mensurationSign", length = 2147483647)
    public String getMensurationsign() {
        return mensurationsign;
    }

    public void setMensurationsign(String mensurationsign) {
        this.mensurationsign = mensurationsign;
    }

    private String mensurationtext;

    @Basic
    @Column(name = "mensurationText", length = 2147483647)
    public String getMensurationtext() {
        return mensurationtext;
    }

    public void setMensurationtext(String mensurationtext) {
        this.mensurationtext = mensurationtext;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Almensuration that = (Almensuration) o;

        if (almensurationkey != that.almensurationkey) return false;
        if (mensurationsign != null ? !mensurationsign.equals(that.mensurationsign) : that.mensurationsign != null)
            return false;
        if (mensurationtext != null ? !mensurationtext.equals(that.mensurationtext) : that.mensurationtext != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = almensurationkey;
        result = 31 * result + (mensurationsign != null ? mensurationsign.hashCode() : 0);
        result = 31 * result + (mensurationtext != null ? mensurationtext.hashCode() : 0);
        return result;
    }

    private Collection<Text> textsByAlmensurationkey;

    @OneToMany(mappedBy = "almensurationByAlmensurationkey")
    public Collection<Text> getTextsByAlmensurationkey() {
        return textsByAlmensurationkey;
    }

    public void setTextsByAlmensurationkey(Collection<Text> textsByAlmensurationkey) {
        this.textsByAlmensurationkey = textsByAlmensurationkey;
    }
}

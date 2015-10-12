package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alTextNotationType")
public class Altextnotationtype {
    private int altextnotationtypekey;

    @Id
    @Column(name = "alTextNotationTypeKey", nullable = false, length = 10)
    public int getAltextnotationtypekey() {
        return altextnotationtypekey;
    }

    public void setAltextnotationtypekey(int altextnotationtypekey) {
        this.altextnotationtypekey = altextnotationtypekey;
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

    private String textnotation;

    @Basic
    @Column(name = "textnotation", length = 2147483647)
    public String getTextnotation() {
        return textnotation;
    }

    public void setTextnotation(String textnotation) {
        this.textnotation = textnotation;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Altextnotationtype that = (Altextnotationtype) o;

        if (altextnotationtypekey != that.altextnotationtypekey) return false;
        if (notationType != null ? !notationType.equals(that.notationType) : that.notationType != null) return false;
        if (textnotation != null ? !textnotation.equals(that.textnotation) : that.textnotation != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = altextnotationtypekey;
        result = 31 * result + (notationType != null ? notationType.hashCode() : 0);
        result = 31 * result + (textnotation != null ? textnotation.hashCode() : 0);
        return result;
    }
}

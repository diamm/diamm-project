package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:29
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "TextNotationItem")
public class Textnotationitem {
    private int textnotationitemkey;

    @Id
    @Column(name = "textNotationItemKey", nullable = false, length = 10)
    public int getTextnotationitemkey() {
        return textnotationitemkey;
    }

    public void setTextnotationitemkey(int textnotationitemkey) {
        this.textnotationitemkey = textnotationitemkey;
    }

    private int altextnotationtypekey;

    @Basic
    @Column(name = "alTextNotationTypeKey", nullable = false, length = 10)
    public int getAltextnotationtypekey() {
        return altextnotationtypekey;
    }

    public void setAltextnotationtypekey(int altextnotationtypekey) {
        this.altextnotationtypekey = altextnotationtypekey;
    }

    private int itemkey;

    @Basic
    @Column(name = "itemKey", nullable = false, length = 10)
    public int getItemkey() {
        return itemkey;
    }

    public void setItemkey(int itemkey) {
        this.itemkey = itemkey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Textnotationitem that = (Textnotationitem) o;

        if (altextnotationtypekey != that.altextnotationtypekey) return false;
        if (itemkey != that.itemkey) return false;
        if (textnotationitemkey != that.textnotationitemkey) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = textnotationitemkey;
        result = 31 * result + altextnotationtypekey;
        result = 31 * result + itemkey;
        return result;
    }
}

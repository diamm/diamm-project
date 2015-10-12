package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 04-Nov-2010
 * Time: 10:23:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "BibliographySource")
public class Bibliographysource {
    private int bibliographysourcekey;

    @Id
    @Column(name = "bibliographySourceKey", nullable = false, length = 10)
    public int getBibliographysourcekey() {
        return bibliographysourcekey;
    }

    public void setBibliographysourcekey(int bibliographysourcekey) {
        this.bibliographysourcekey = bibliographysourcekey;
    }

    private Bibliography bibliography;

    @ManyToOne
    @JoinColumn(name = "bibliographyKey", referencedColumnName = "bibliographyKey")
    public Bibliography getBibliography() {
        return bibliography;
    }

    public void setBibliography(Bibliography bibliography) {
        this.bibliography = bibliography;
    }

    private Source source;

    @ManyToOne
    @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey")
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    private int sourcekey;

    @Basic
    @Column(name = "sourceKey", length = 10,insertable = false,updatable = false)
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }

    private int bibliographykey;

    @Basic
    @Column(name = "bibliographyKey", length = 10,insertable = false,updatable = false)
    public int getBibliographykey() {
        return bibliographykey;
    }

    public void setBibliographykey(int bibliographykey) {
        this.bibliographykey = bibliographykey;
    }

    private String notes;

    @Basic
    @Column(name = "notes", length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String page;

    @Basic
    @Column(name = "page", length = 2147483647)
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }




    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bibliographysource that = (Bibliographysource) o;

        if (bibliographykey != that.bibliographykey) return false;
        if (bibliographysourcekey != that.bibliographysourcekey) return false;
        if (sourcekey != that.sourcekey) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = bibliographysourcekey;
        result = 31 * result + sourcekey;
        result = 31 * result + bibliographykey;
        return result;
    }
}

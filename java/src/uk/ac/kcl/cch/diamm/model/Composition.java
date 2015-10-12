package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Composition {
    private int compositionkey;

    @Id
    @Column(name = "compositionKey", nullable = false, length = 10)
    public int getCompositionkey() {
        return compositionkey;
    }

    public void setCompositionkey(int compositionkey) {
        this.compositionkey = compositionkey;
    }

    private String compositionName;

    @Basic
    @Column(name = "composition_name", length = 2147483647)
    public String getCompositionName() {
        return compositionName;
    }

    public void setCompositionName(String compositionName) {
        this.compositionName = compositionName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composition that = (Composition) o;

        if (compositionkey != that.compositionkey) return false;
        if (compositionName != null ? !compositionName.equals(that.compositionName) : that.compositionName != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = compositionkey;
        result = 31 * result + (compositionName != null ? compositionName.hashCode() : 0);
        return result;
    }


    private Collection<Compositioncomposer> compositioncomposersByCompositionkey;

    @OneToMany(mappedBy = "compositionByCompositionkey")
    public Collection<Compositioncomposer> getCompositioncomposersByCompositionkey() {
        return compositioncomposersByCompositionkey;
    }

    public void setCompositioncomposersByCompositionkey(Collection<Compositioncomposer> compositioncomposersByCompositionkey) {
        this.compositioncomposersByCompositionkey = compositioncomposersByCompositionkey;
    }

    private Collection<Compositiongenre> compositiongenresByCompositionkey;

    @OneToMany(mappedBy = "compositionByCompositionkey")
    public Collection<Compositiongenre> getCompositiongenresByCompositionkey() {
        return compositiongenresByCompositionkey;
    }

    public void setCompositiongenresByCompositionkey(Collection<Compositiongenre> compositiongenresByCompositionkey) {
        this.compositiongenresByCompositionkey = compositiongenresByCompositionkey;
    }

    private Collection<Algenre> genres;

    @ManyToMany
    @JoinTable(name = "CompositionGenre",
    		   joinColumns = {@JoinColumn(name = "compositionKey")},
    		   inverseJoinColumns = {@JoinColumn(name = "alGenreKey")})
    public Collection<Algenre> getGenres() {
        return genres;
    }



    public void setGenres(Collection<Algenre> genres) {
        this.genres = genres;
    }

    private Collection<Item> itemsByCompositionkey;

    @OneToMany(mappedBy = "compositionByCompositionkey")
    public Collection<Item> getItemsByCompositionkey() {
        return itemsByCompositionkey;
    }

    public void setItemsByCompositionkey(Collection<Item> itemsByCompositionkey) {
        this.itemsByCompositionkey = itemsByCompositionkey;
    }

    private Collection<Composer> composers;

    @ManyToMany(mappedBy = "compositions")
    public Collection<Composer> getComposers() {
        return composers;
    }

    public void setComposers(Collection<Composer> composers) {
        this.composers = composers;
    }


    private String attributionAuthority;

    @Basic
    @Column(name = "attribution_authority", length = 2147483647)
    public String getAttributionAuthority() {
        return attributionAuthority;
    }

    public void setAttributionAuthority(String attributionAuthority) {
        this.attributionAuthority = attributionAuthority;
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

    private String maxnumberofvoices;

    @Basic
    @Column(name = "max_number_of_voices", length = 2147483647)
    public String getMaxnumberofvoices() {
        return maxnumberofvoices;
    }

    public void setMaxnumberofvoices(String maxnumberofvoices) {
        this.maxnumberofvoices = maxnumberofvoices;
    }

    private String notesconcordances;

    @Basic
    @Column(name = "notesconcordances", length = 2147483647)
    public String getNotesconcordances() {
        return notesconcordances;
    }

    public void setNotesconcordances(String notesconcordances) {
        this.notesconcordances = notesconcordances;
    }

     private List<Bibliography> bibliographyList = new ArrayList<Bibliography>();

    @ManyToMany
    @JoinTable(name = "BibliographyComposition",
    		   joinColumns = {@JoinColumn(name = "bibliographyKey")},
    		   inverseJoinColumns = {@JoinColumn(name = "compositionKey")})
    public List<Bibliography> getBibliographyList() {
        return bibliographyList;
    }

    public void setBibliographyList(List<Bibliography> bibliographyList) {
        this.bibliographyList = bibliographyList;
    }

    private Collection<Bibliographycomposition> bibliographycompositions;

    @OneToMany(mappedBy = "composition")
    public Collection<Bibliographycomposition> getBibliographycompositions() {
        return bibliographycompositions;
    }

    public void setBibliographycompositions(Collection<Bibliographycomposition> bibliographycompositions) {
        this.bibliographycompositions = bibliographycompositions;
    }
}

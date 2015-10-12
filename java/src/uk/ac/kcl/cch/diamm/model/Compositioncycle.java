package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 27/05/11
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess",name = "CompositionCycle")
public class Compositioncycle {
    private int compositioncyclekey;

    @javax.persistence.Column(name = "compositionCycleKey")
    @Id
    public int getCompositioncyclekey() {
        return compositioncyclekey;
    }

    public void setCompositioncyclekey(int compositioncyclekey) {
        this.compositioncyclekey = compositioncyclekey;
    }


    private Alcycletype alcycletype;

    @ManyToOne
    @JoinColumn(name = "alCycleTypeKey", referencedColumnName = "alCycleTypeKey", nullable = false,insertable = false,updatable = false)
    public Alcycletype getAlcycletype() {
        return alcycletype;
    }

    public void setAlcycletype(Alcycletype alcycletype) {
        this.alcycletype = alcycletype;
    }



    private int alcycletypekey;

    @javax.persistence.Column(name = "alCycleTypeKey",nullable = false,insertable = false,updatable = false)
    @Basic
    public int getAlcycletypekey() {
        return alcycletypekey;
    }

    public void setAlcycletypekey(int alcycletypekey) {
        this.alcycletypekey = alcycletypekey;
    }

    private int composerkey;

    @javax.persistence.Column(name = "composerKey")
    @Basic
    public int getComposerkey() {
        return composerkey;
    }

    public void setComposerkey(int composerkey) {
        this.composerkey = composerkey;
    }

    private String title;

    @javax.persistence.Column(name = "title", length = 2147483647)
    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compositioncycle that = (Compositioncycle) o;

        if (alcycletypekey != that.alcycletypekey) return false;
        if (composerkey != that.composerkey) return false;
        if (compositioncyclekey != that.compositioncyclekey) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = compositioncyclekey;
        result = 31 * result + alcycletypekey;
        result = 31 * result + composerkey;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}

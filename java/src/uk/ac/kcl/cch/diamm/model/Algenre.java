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
@Table(catalog = "diamm_ess", name = "alGenre")
public class Algenre {
    private int algenrekey;

    @Id
    @Column(name = "alGenreKey", nullable = false, length = 10)
    public int getAlgenrekey() {
        return algenrekey;
    }

    public void setAlgenrekey(int algenrekey) {
        this.algenrekey = algenrekey;
    }

    private String genre;

    @Basic
    @Column(name = "genre", length = 2147483647)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Algenre algenre = (Algenre) o;

        if (algenrekey != algenre.algenrekey) return false;
        if (genre != null ? !genre.equals(algenre.genre) : algenre.genre != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = algenrekey;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

    private Collection<Compositiongenre> compositiongenresByAlgenrekey;

    @OneToMany(mappedBy = "algenreByAlgenrekey")
    public Collection<Compositiongenre> getCompositiongenresByAlgenrekey() {
        return compositiongenresByAlgenrekey;
    }

    public void setCompositiongenresByAlgenrekey(Collection<Compositiongenre> compositiongenresByAlgenrekey) {
        this.compositiongenresByAlgenrekey = compositiongenresByAlgenrekey;
    }
}

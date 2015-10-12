package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alLanguage")
public class Allanguage {
    private int allangaugekey;

    @Id
    @Column(name = "alLangaugeKey", nullable = false, length = 10)
    public int getAllangaugekey() {
        return allangaugekey;
    }

    public void setAllangaugekey(int allangaugekey) {
        this.allangaugekey = allangaugekey;
    }

    private String language;

    @Basic
    @Column(name = "language", nullable = false, length = 2147483647)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Allanguage that = (Allanguage) o;

        if (allangaugekey != that.allangaugekey) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = allangaugekey;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    private List<Text> texts;
    @ManyToMany(mappedBy = "languages")       		   
    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }

    private Collection<Textlanguage> textlanguagesByAllangaugekey;

    @OneToMany(mappedBy = "allanguagebyallanguagekey")
    public Collection<Textlanguage> getTextlanguagesByAllangaugekey() {
        return textlanguagesByAllangaugekey;
    }

    public void setTextlanguagesByAllangaugekey(Collection<Textlanguage> textlanguagesByAllangaugekey) {
        this.textlanguagesByAllangaugekey = textlanguagesByAllangaugekey;
    }
}

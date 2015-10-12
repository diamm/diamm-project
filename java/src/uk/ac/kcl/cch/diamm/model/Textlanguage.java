package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "TextLanguage")
public class Textlanguage {
    private int textlanguagekey;

    @Id
    @Column(name = "textLanguageKey", nullable = false, length = 10)
    public int getTextlanguagekey() {
        return textlanguagekey;
    }

    public void setTextlanguagekey(int textlanguagekey) {
        this.textlanguagekey = textlanguagekey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Textlanguage that = (Textlanguage) o;

        if (textlanguagekey != that.textlanguagekey) return false;

        return true;
    }

    public int hashCode() {
        return textlanguagekey;
    }

    private Allanguage allanguagebyallanguagekey;
    @ManyToOne
    @JoinColumn(name = "alLanguageKey", referencedColumnName = "alLangaugeKey", nullable = false)
    public Allanguage getAllanguagebyallanguagekey() {
        return allanguagebyallanguagekey;
    }

    public void setAllanguagebyallanguagekey(Allanguage allanguagebyallanguagekey) {
        this.allanguagebyallanguagekey = allanguagebyallanguagekey;
    }




    private Text textByTextkey;

    @ManyToOne
    @JoinColumn(name = "textKey", referencedColumnName = "textKey", nullable = false)
    public Text getTextByTextkey() {
        return textByTextkey;
    }

    public void setTextByTextkey(Text textByTextkey) {
        this.textByTextkey = textByTextkey;
    }
}

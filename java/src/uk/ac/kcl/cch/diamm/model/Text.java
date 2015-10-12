package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Text {
    private int textkey;

    @Id
    @Column(name = "textKey", nullable = false, length = 10)
    public int getTextkey() {
        return textkey;
    }

    public void setTextkey(int textkey) {
        this.textkey = textkey;
    }

    private String fulltermtext;

    @Basic
    @Column(name = "fulltermText", length = 2147483647)
    public String getFulltermtext() {
        return fulltermtext;
    }

    public void setFulltermtext(String fulltermtext) {
        this.fulltermtext = fulltermtext;
    }

    private int orderno;

    @Basic
    @Column(name = "orderNo", length = 10)
    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Text text = (Text) o;

        if (orderno != text.orderno) return false;
        if (textkey != text.textkey) return false;
        if (fulltermtext != null ? !fulltermtext.equals(text.fulltermtext) : text.fulltermtext != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = textkey;
        result = 31 * result + (fulltermtext != null ? fulltermtext.hashCode() : 0);
        result = 31 * result + orderno;
        return result;
    }

    private Alclef alclefbyalclefkey;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "alClefKey", referencedColumnName = "alClefKey", nullable = false)
    public Alclef getAlclefbyalclefkey() {
        return alclefbyalclefkey;
    }

    public void setAlclefbyalclefkey(Alclef alclefbyalclefkey) {
        this.alclefbyalclefkey = alclefbyalclefkey;
    }

    private Item itemByItemkey;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "itemKey", referencedColumnName = "itemKey", nullable = false)
    public Item getItemByItemkey() {
        return itemByItemkey;
    }

    public void setItemByItemkey(Item itemByItemkey) {
        this.itemByItemkey = itemByItemkey;
    }

    private Alvoice alvoicebyalvoicekey;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "alVoiceKey", referencedColumnName = "alVoiceKey", nullable = false)
    public Alvoice getAlvoicebyalvoicekey() {
        return alvoicebyalvoicekey;
    }

    public void setAlvoicebyalvoicekey(Alvoice alvoicebyalvoicekey) {
        this.alvoicebyalvoicekey = alvoicebyalvoicekey;
    }

    private Almensuration almensurationByAlmensurationkey;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "alMensurationKey", referencedColumnName = "alMensurationKey", nullable = false)
    public Almensuration getAlmensurationByAlmensurationkey() {
        return almensurationByAlmensurationkey;
    }

    public void setAlmensurationByAlmensurationkey(Almensuration almensurationByAlmensurationkey) {
        this.almensurationByAlmensurationkey = almensurationByAlmensurationkey;
    }

    private List<Allanguage> languages;
     @ManyToMany
    @JoinTable(name = "TextLanguage",
    		   joinColumns = {@JoinColumn(name = "textKey", nullable = false)},
    		   inverseJoinColumns = {@JoinColumn(name = "alLanguageKey",referencedColumnName = "alLangaugeKey", nullable = false)})
    public List<Allanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Allanguage> languages) {
        this.languages = languages;
    }

    private Collection<Textlanguage> textlanguagesbytextkey;

    @OneToMany(mappedBy = "textByTextkey",fetch=FetchType.LAZY)
    public Collection<Textlanguage> getTextlanguagesbytextkey() {
        return textlanguagesbytextkey;
    }

    public void setTextlanguagesbytextkey(Collection<Textlanguage> textlanguagesbytextkey) {
        this.textlanguagesbytextkey = textlanguagesbytextkey;
    }



    private int itemkey;

    @Basic
    @Column(name = "itemKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getItemkey() {
        return itemkey;
    }

    public void setItemkey(int itemkey) {
        this.itemkey = itemkey;
    }

    private int almensurationkey;

    @Basic
    @Column(name = "alMensurationKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlmensurationkey() {
        return almensurationkey;
    }

    public void setAlmensurationkey(int almensurationkey) {
        this.almensurationkey = almensurationkey;
    }

    private int alvoicekey;

    @Basic
    @Column(name = "alVoiceKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlvoicekey() {
        return alvoicekey;
    }

    public void setAlvoicekey(int alvoicekey) {
        this.alvoicekey = alvoicekey;
    }

    private int alclefkey;

    @Basic
    @Column(name = "alClefKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlclefkey() {
        return alclefkey;
    }

    public void setAlclefkey(int alclefkey) {
        this.alclefkey = alclefkey;
    }

    private String fulltermtextAuthority;

    @Basic
    @Column(name = "fulltermText_authority", length = 2147483647)
    public String getFulltermtextAuthority() {
        return fulltermtextAuthority;
    }

    public void setFulltermtextAuthority(String fulltermtextAuthority) {
        this.fulltermtextAuthority = fulltermtextAuthority;
    }


    private String global;

    @Basic
    @Column(name = "Global", length = 2147483647)
    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }


    private String standardspellingfulltext;

    @Basic
    @Column(name = "standardSpellingFullText", length = 2147483647)
    public String getStandardspellingfulltext() {
        return standardspellingfulltext;
    }

    public void setStandardspellingfulltext(String standardspellingfulltext) {
        this.standardspellingfulltext = standardspellingfulltext;
    }

    
    private String standardspellingincipit;

    @Basic
    @Column(name = "standardSpellingIncipit", length = 2147483647)
    public String getStandardspellingincipit() {
        return standardspellingincipit;
    }

    public void setStandardspellingincipit(String standardspellingincipit) {
        this.standardspellingincipit = standardspellingincipit;
    }

    private String textincipit;

    @Basic
    @Column(name = "textincipit", length = 2147483647)
    public String getTextincipit() {
        return textincipit;
    }

    public void setTextincipit(String textincipit) {
        this.textincipit = textincipit;
    }

    private String voicepart;

    @Basic
    @Column(name = "voicepart", length = 2147483647)
    public String getVoicepart() {
        return voicepart;
    }

    public void setVoicepart(String voicepart) {
        this.voicepart = voicepart;
    }
}

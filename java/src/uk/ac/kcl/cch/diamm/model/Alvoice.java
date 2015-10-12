package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alVoice")
public class Alvoice {
    private int alvoicekey;

    @Id
    @Column(name = "alVoiceKey", nullable = false, length = 10)
    public int getAlvoicekey() {
        return alvoicekey;
    }

    public void setAlvoicekey(int alvoicekey) {
        this.alvoicekey = alvoicekey;
    }

    private String voice;

    @Basic
    @Column(name = "voice", length = 2147483647)
    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alvoice alvoice = (Alvoice) o;

        if (alvoicekey != alvoice.alvoicekey) return false;
        if (voice != null ? !voice.equals(alvoice.voice) : alvoice.voice != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alvoicekey;
        result = 31 * result + (voice != null ? voice.hashCode() : 0);
        return result;
    }

    private Collection<Text> textsByAlvoicekey;

    @OneToMany(mappedBy = "alvoicebyalvoicekey")
    public Collection<Text> getTextsByAlvoicekey() {
        return textsByAlvoicekey;
    }

    public void setTextsByAlvoicekey(Collection<Text> textsByAlvoicekey) {
        this.textsByAlvoicekey = textsByAlvoicekey;
    }
}

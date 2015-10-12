package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 15-Sep-2010
 * Time: 11:50:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Image {
    private int imagekey;

    @Id
    @Column(name = "imageKey", nullable = false, length = 10)
    public int getImagekey() {
        return imagekey;
    }

    public void setImagekey(int imagekey) {
        this.imagekey = imagekey;
    }


    private Source sourceBySourcekey;

    @ManyToOne
    @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false,insertable = false, updatable = false)
    public Source getSourceBySourcekey() {
        return sourceBySourcekey;
    }

    public void setSourceBySourcekey(Source sourceBySourcekey) {
        this.sourceBySourcekey = sourceBySourcekey;
    }

    private int sourcekey;

    @Basic
    @Column(name = "sourceKey", length = 10)
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }

    private String bibliography;

    @Basic
    @Column(name = "bibliography", length = 2147483647)
    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    private String copyrightstatement;

    @Basic
    @Column(name = "copyrightstatement", length = 2147483647)
    public String getCopyrightstatement() {
        return copyrightstatement;
    }

    public void setCopyrightstatement(String copyrightstatement) {
        this.copyrightstatement = copyrightstatement;
    }

    private String filename;

    @Basic
    @Column(name = "filename", length = 2147483647)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String imagetype;

    @Basic
    @Column(name = "imageType", length = 2147483647)
    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    private String folio;

    @Basic
    @Column(name = "folio", length = 2147483647)
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
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

    private int orderno;

    @Basic
    @Column(name = "orderNo", length = 10)
    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    private String digitised;

    @Basic
    @Column(name = "digitised", length = 2147483647)
    public String getDigitised() {
        return digitised;
    }

    public void setDigitised(String digitised) {
        this.digitised = digitised;
    }

    private String availwebsite;

    @Basic
    @Column(name = "availwebsite", length = 200)
    public String getAvailwebsite() {
        return availwebsite;
    }

    public void setAvailwebsite(String availwebsite) {
        this.availwebsite = availwebsite;
    }


    private Collection<Itemimage> itemimagesByImagekey;

    @OneToMany(mappedBy = "imageByImagekey")
    public Collection<Itemimage> getItemimagesByImagekey() {
        return itemimagesByImagekey;
    }

    public void setItemimagesByImagekey(Collection<Itemimage> itemimagesByImagekey) {
        this.itemimagesByImagekey = itemimagesByImagekey;
    }

    private Collection<Secondaryimage> secondaryImages;

    @OneToMany(mappedBy = "imageByImagekey")
    public Collection<Secondaryimage> getSecondaryImages() {
        return secondaryImages;
    }

    public void setSecondaryImages(Collection<Secondaryimage> secondaryImages) {
        this.secondaryImages = secondaryImages;
    }
    
    private List<Note> noteList = new ArrayList<Note>();

    @OneToMany(mappedBy = "noteImage")
    public List<Note> getNoteList() {
        return noteList;
    }
    
    public void setNoteList(List<Note> noteList) {
    	this.noteList = noteList;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (availwebsite != image.availwebsite) return false;
        if (imagekey != image.imagekey) return false;
        if (orderno != image.orderno) return false;
        if (sourcekey != image.sourcekey) return false;
        if (bibliography != null ? !bibliography.equals(image.bibliography) : image.bibliography != null) return false;
        if (copyrightstatement != null ? !copyrightstatement.equals(image.copyrightstatement) : image.copyrightstatement != null)
            return false;
        if (digitised != null ? !digitised.equals(image.digitised) : image.digitised != null) return false;
        if (filename != null ? !filename.equals(image.filename) : image.filename != null) return false;
        if (folio != null ? !folio.equals(image.folio) : image.folio != null) return false;
        if (imagetype != null ? !imagetype.equals(image.imagetype) : image.imagetype != null) return false;
        if (notes != null ? !notes.equals(image.notes) : image.notes != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = imagekey;
        result = 31 * result + sourcekey;
        result = 31 * result + (bibliography != null ? bibliography.hashCode() : 0);
        result = 31 * result + (copyrightstatement != null ? copyrightstatement.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (imagetype != null ? imagetype.hashCode() : 0);
        result = 31 * result + (folio != null ? folio.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + orderno;
        result = 31 * result + (digitised != null ? digitised.hashCode() : 0);
        
        return result;
    }
}

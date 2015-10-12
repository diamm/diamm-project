package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 16-Nov-2010
 * Time: 15:22:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "SecondaryImage")
public class Secondaryimage {
    private int secondaryimagekey;

    @Id
    @Column(name = "secondaryImageKey", nullable = false, length = 10)
    public int getSecondaryimagekey() {
        return secondaryimagekey;
    }

    public void setSecondaryimagekey(int secondaryimagekey) {
        this.secondaryimagekey = secondaryimagekey;
    }

    private int imagekey;

    @Basic
    @Column(name = "imageKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getImagekey() {
        return imagekey;
    }

    public void setImagekey(int imagekey) {
        this.imagekey = imagekey;
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

    private String notes;

    @Basic
    @Column(name = "notes", length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String caption;

    @Basic
    @Column(name = "caption", length = 2147483647)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    private String archivefilename;

    @Basic
    @Column(name = "archivefilename", length = 2147483647)
    public String getArchivefilename() {
        return archivefilename;
    }

    public void setArchivefilename(String archivefilename) {
        this.archivefilename = archivefilename;
    }

    private String datemodified;

    @Basic
    @Column(name = "datemodified", length = 2147483647)
    public String getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(String datemodified) {
        this.datemodified = datemodified;
    }

    private Image imageByImagekey;

    @ManyToOne
    @JoinColumn(name = "imageKey", referencedColumnName = "imageKey", nullable = false)
    public Image getImageByImagekey() {
        return imageByImagekey;
    }

    public void setImageByImagekey(Image imageByImagekey) {
        this.imageByImagekey = imageByImagekey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Secondaryimage that = (Secondaryimage) o;

        if (imagekey != that.imagekey) return false;
        if (secondaryimagekey != that.secondaryimagekey) return false;
        if (archivefilename != null ? !archivefilename.equals(that.archivefilename) : that.archivefilename != null)
            return false;
        if (caption != null ? !caption.equals(that.caption) : that.caption != null) return false;
        if (datemodified != null ? !datemodified.equals(that.datemodified) : that.datemodified != null) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (imagetype != null ? !imagetype.equals(that.imagetype) : that.imagetype != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = secondaryimagekey;
        result = 31 * result + imagekey;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (imagetype != null ? imagetype.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (caption != null ? caption.hashCode() : 0);
        result = 31 * result + (archivefilename != null ? archivefilename.hashCode() : 0);
        result = 31 * result + (datemodified != null ? datemodified.hashCode() : 0);
        return result;
    }
}

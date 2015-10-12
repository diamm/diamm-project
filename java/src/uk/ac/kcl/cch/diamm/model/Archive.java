package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Archive {
    private int archivekey;

    @Id
    @Column(name = "archiveKey", nullable = false, length = 10)
    public int getArchivekey() {
        return archivekey;
    }

    public void setArchivekey(int archivekey) {
        this.archivekey = archivekey;
    }

    private String archivename;

    @Basic
    @Column(name = "archiveName", length = 2147483647)
    public String getArchivename() {
        return archivename;
    }

    public void setArchivename(String archivename) {
        this.archivename = archivename;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Archive archive = (Archive) o;

        if (archivekey != archive.archivekey) return false;
        if (archivename != null ? !archivename.equals(archive.archivename) : archive.archivename != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = archivekey;
        result = 31 * result + (archivename != null ? archivename.hashCode() : 0);
        return result;
    }
    private int alcitykey;

    @Basic
    @Column(name = "alCityKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlcitykey() {
        return alcitykey;
    }

    public void setAlcitykey(int alcitykey) {
        this.alcitykey = alcitykey;
    }

    private Alcity alcityByAlcitykey;

    @ManyToOne
    @JoinColumn(name = "alCityKey", referencedColumnName = "alCityKey", nullable = false)
    public Alcity getAlcityByAlcitykey() {
        return alcityByAlcitykey;
    }

    public void setAlcityByAlcitykey(Alcity alcityByAlcitykey) {
        this.alcityByAlcitykey = alcityByAlcitykey;
    }

    private Collection<Source> sourcesByArchivekey;

    @OneToMany(mappedBy = "archiveByArchivekey")
    @OrderBy("sortorder")
    public Collection<Source> getSourcesByArchivekey() {
        return sourcesByArchivekey;
    }

    public void setSourcesByArchivekey(Collection<Source> sourcesByArchivekey) {
        this.sourcesByArchivekey = sourcesByArchivekey;
    }

    private String address;

    @Basic
    @Column(name = "address", length = 2147483647)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    private String copyrightholder;

    @Basic
    @Column(name = "copyrightholder", length = 2147483647)
    public String getCopyrightholder() {
        return copyrightholder;
    }

    public void setCopyrightholder(String copyrightholder) {
        this.copyrightholder = copyrightholder;
    }

    private String email;

    @Basic
    @Column(name = "email", length = 2147483647)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String fax;

    @Basic
    @Column(name = "fax", length = 2147483647)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    private String librariana;

    @Basic
    @Column(name = "librarianA", length = 2147483647)
    public String getLibrariana() {
        return librariana;
    }

    public void setLibrariana(String librariana) {
        this.librariana = librariana;
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

    private String siglum;

    @Basic
    @Column(name = "siglum", length = 2147483647)
    public String getSiglum() {
        return siglum;
    }

    public void setSiglum(String siglum) {
        this.siglum = siglum;
    }

    private String phone;

    @Basic
    @Column(name = "telephone", length = 2147483647)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

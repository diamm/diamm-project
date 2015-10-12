package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:56
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Source {
    private int sourcekey;

    @Id
    @Column(name = "sourceKey", nullable = false, length = 10)
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }

    private String altNumberingSource;

    @Basic
    @Column(name = "alt_numbering_source", length = 2147483647)
    public String getAltNumberingSource() {
        return altNumberingSource;
    }

    public void setAltNumberingSource(String altNumberingSource) {
        this.altNumberingSource = altNumberingSource;
    }

    private String altrismabbrev;

    @Basic
    @Column(name = "altRISMabbrev", length = 2147483647)
    public String getAltrismabbrev() {
        return altrismabbrev;
    }

    public void setAltrismabbrev(String altrismabbrev) {
        this.altrismabbrev = altrismabbrev;
    }

    private String authority;

    @Basic
    @Column(name = "authority", length = 2147483647)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    private String ccmabbrev;

    @Basic
    @Column(name = "CCMabbrev", length = 2147483647)
    public String getCcmabbrev() {
        return ccmabbrev;
    }

    public void setCcmabbrev(String ccmabbrev) {
        this.ccmabbrev = ccmabbrev;
    }

    private String ccmimagefilename1;

    @Basic
    @Column(name = "CCMimagefilename1", length = 2147483647)
    public String getCcmimagefilename1() {
        return ccmimagefilename1;
    }

    public void setCcmimagefilename1(String ccmimagefilename1) {
        this.ccmimagefilename1 = ccmimagefilename1;
    }

    private String ccmimagefilename2;

    @Basic
    @Column(name = "CCMimagefilename2", length = 2147483647)
    public String getCcmimagefilename2() {
        return ccmimagefilename2;
    }

    public void setCcmimagefilename2(String ccmimagefilename2) {
        this.ccmimagefilename2 = ccmimagefilename2;
    }

    private String ccmimagefilename3;

    @Basic
    @Column(name = "CCMimagefilename3", length = 2147483647)
    public String getCcmimagefilename3() {
        return ccmimagefilename3;
    }

    public void setCcmimagefilename3(String ccmimagefilename3) {
        this.ccmimagefilename3 = ccmimagefilename3;
    }

    private String cmmeId;

    @Basic
    @Column(name = "CMME_id", length = 2147483647)
    public String getCmmeId() {
        return cmmeId;
    }

    public void setCmmeId(String cmmeId) {
        this.cmmeId = cmmeId;
    }

    private String completems;

    @Basic
    @Column(name = "completeMS", length = 2147483647)
    public String getCompletems() {
        return completems;
    }

    public void setCompletems(String completems) {
        this.completems = completems;
    }

    private String datecomments;

    @Basic
    @Column(name = "dateComments", length = 2147483647)
    public String getDatecomments() {
        return datecomments;
    }

    public void setDatecomments(String datecomments) {
        this.datecomments = datecomments;
    }

    private String dateofsource;

    @Basic
    @Column(name = "dateOfSource", length = 2147483647)
    public String getDateofsource() {
        return dateofsource;
    }

    public void setDateofsource(String dateofsource) {
        this.dateofsource = dateofsource;
    }

    private String dedicationtext;

    @Basic
    @Column(name = "dedicationText", length = 2147483647)
    public String getDedicationtext() {
        return dedicationtext;
    }

    public void setDedicationtext(String dedicationtext) {
        this.dedicationtext = dedicationtext;
    }

    private String description;

    @Basic
    @Column(name = "description", length = 2147483647)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String diammDescription;

    @Basic
    @Column(name = "DIAMM_description", length = 2147483647)
    public String getDiammDescription() {
        return diammDescription;
    }

    public void setDiammDescription(String diammDescription) {
        this.diammDescription = diammDescription;
    }

    private String enddate;

    @Basic
    @Column(name = "enddate", length = 2147483647)
    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }


    private String inventory;

    @Basic
    @Column(name = "inventory", length = 2147483647)
    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    private String liminarytext;

    @Basic
    @Column(name = "liminaryText", length = 2147483647)
    public String getLiminarytext() {
        return liminarytext;
    }

    public void setLiminarytext(String liminarytext) {
        this.liminarytext = liminarytext;
    }

    private String notation;

    @Basic
    @Column(name = "notation", length = 2147483647)
    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
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

    private String numberingSource;

    @Basic
    @Column(name = "numbering_source", length = 2147483647)
    public String getNumberingSource() {
        return numberingSource;
    }

    public void setNumberingSource(String numberingSource) {
        this.numberingSource = numberingSource;
    }

    private String olim;

    @Basic
    @Column(name = "olim", length = 2147483647)
    public String getOlim() {
        return olim;
    }

    public void setOlim(String olim) {
        this.olim = olim;
    }

    private String pagemeasurements;

    @Basic
    @Column(name = "pageMeasurements", length = 2147483647)
    public String getPagemeasurements() {
        return pagemeasurements;
    }

    public void setPagemeasurements(String pagemeasurements) {
        this.pagemeasurements = pagemeasurements;
    }


    private String provenancecitation;

    @Basic
    @Column(name = "provenanceCitation", length = 2147483647)
    public String getProvenancecitation() {
        return provenancecitation;
    }

    public void setProvenancecitation(String provenancecitation) {
        this.provenancecitation = provenancecitation;
    }

    private String provenancecomment;

    @Basic
    @Column(name = "provenanceComment", length = 2147483647)
    public String getProvenancecomment() {
        return provenancecomment;
    }

    public void setProvenancecomment(String provenancecomment) {
        this.provenancecomment = provenancecomment;
    }

    private String provenancecountry;

    @Basic
    @Column(name = "provenanceCountry", length = 2147483647)
    public String getProvenancecountry() {
        return provenancecountry;
    }

    public void setProvenancecountry(String provenancecountry) {
        this.provenancecountry = provenancecountry;
    }

    private String provenanceregion;

    @Basic
    @Column(name = "provenanceRegion", length = 2147483647)
    public String getProvenanceregion() {
        return provenanceregion;
    }

    public void setProvenanceregion(String provenanceregion) {
        this.provenanceregion = provenanceregion;
    }

    private String rismabbrev;

    @Basic
    @Column(name = "RISMabbrev", length = 2147483647)
    public String getRismabbrev() {
        return rismabbrev;
    }

    public void setRismabbrev(String rismabbrev) {
        this.rismabbrev = rismabbrev;
    }

    private String rismccm;

    @Basic
    @Column(name = "RISMCCM", length = 2147483647)
    public String getRismccm() {
        return rismccm;
    }

    public void setRismccm(String rismccm) {
        this.rismccm = rismccm;
    }

    private String rismimagefilename1;

    @Basic
    @Column(name = "RISMimagefilename1", length = 2147483647)
    public String getRismimagefilename1() {
        return rismimagefilename1;
    }

    public void setRismimagefilename1(String rismimagefilename1) {
        this.rismimagefilename1 = rismimagefilename1;
    }

    private String rismimagefilename2;

    @Basic
    @Column(name = "RISMimagefilename2", length = 2147483647)
    public String getRismimagefilename2() {
        return rismimagefilename2;
    }

    public void setRismimagefilename2(String rismimagefilename2) {
        this.rismimagefilename2 = rismimagefilename2;
    }

    private String rismimagefilename3;

    @Basic
    @Column(name = "RISMimagefilename3", length = 2147483647)
    public String getRismimagefilename3() {
        return rismimagefilename3;
    }

    public void setRismimagefilename3(String rismimagefilename3) {
        this.rismimagefilename3 = rismimagefilename3;
    }

    private String shelfmark;

    @Basic
    @Column(name = "shelfmark", length = 2147483647)
    public String getShelfmark() {
        return shelfmark;
    }

    public void setShelfmark(String shelfmark) {
        this.shelfmark = shelfmark;
    }

    private int sortorder;

    @Basic
    @Column(name = "sortorder", length = 10)
    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    private String sourcename;

    @Basic
    @Column(name = "sourceName", length = 2147483647)
    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    private String sourcetype;

    @Basic
    @Column(name = "sourceType", length = 2147483647)
    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    private String startdate;

    @Basic
    @Column(name = "startdate", length = 2147483647)
    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }


    private String stavegauge;

    @Basic
    @Column(name = "staveGauge", length = 2147483647)
    public String getStavegauge() {
        return stavegauge;
    }

    public void setStavegauge(String stavegauge) {
        this.stavegauge = stavegauge;
    }

    private String surface;

    @Basic
    @Column(name = "surface", length = 2147483647)
    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    private String tags;

    @Basic
    @Column(name = "Tags", length = 2147483647)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    private String watermark;

    @Basic
    @Column(name = "watermark", length = 2147483647)
    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    private String webpermission;

    @Basic
    @Column(name = "webpermission", length = 200)
    public String getWebpermission() {
        return webpermission;
    }

    public void setWebpermission(String webpermission) {
        this.webpermission = webpermission;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (sortorder != source.sortorder) return false;
        if (sourcekey != source.sourcekey) return false;
        if (webpermission != source.webpermission) return false;
        if (altNumberingSource != null ? !altNumberingSource.equals(source.altNumberingSource) : source.altNumberingSource != null)
            return false;
        if (altrismabbrev != null ? !altrismabbrev.equals(source.altrismabbrev) : source.altrismabbrev != null)
            return false;
        if (authority != null ? !authority.equals(source.authority) : source.authority != null) return false;
        if (ccmabbrev != null ? !ccmabbrev.equals(source.ccmabbrev) : source.ccmabbrev != null) return false;
        if (ccmimagefilename1 != null ? !ccmimagefilename1.equals(source.ccmimagefilename1) : source.ccmimagefilename1 != null)
            return false;
        if (ccmimagefilename2 != null ? !ccmimagefilename2.equals(source.ccmimagefilename2) : source.ccmimagefilename2 != null)
            return false;
        if (ccmimagefilename3 != null ? !ccmimagefilename3.equals(source.ccmimagefilename3) : source.ccmimagefilename3 != null)
            return false;
        if (cmmeId != null ? !cmmeId.equals(source.cmmeId) : source.cmmeId != null) return false;
        if (completems != null ? !completems.equals(source.completems) : source.completems != null) return false;
        if (datecomments != null ? !datecomments.equals(source.datecomments) : source.datecomments != null)
            return false;
        if (dateofsource != null ? !dateofsource.equals(source.dateofsource) : source.dateofsource != null)
            return false;
        if (dedicationtext != null ? !dedicationtext.equals(source.dedicationtext) : source.dedicationtext != null)
            return false;
        if (description != null ? !description.equals(source.description) : source.description != null) return false;
        if (diammDescription != null ? !diammDescription.equals(source.diammDescription) : source.diammDescription != null)
            return false;
        if (enddate != null ? !enddate.equals(source.enddate) : source.enddate != null) return false;

        if (inventory != null ? !inventory.equals(source.inventory) : source.inventory != null) return false;
        if (liminarytext != null ? !liminarytext.equals(source.liminarytext) : source.liminarytext != null)
            return false;
        if (notation != null ? !notation.equals(source.notation) : source.notation != null) return false;
        if (notes != null ? !notes.equals(source.notes) : source.notes != null) return false;
        if (numberingSource != null ? !numberingSource.equals(source.numberingSource) : source.numberingSource != null)
            return false;
        if (olim != null ? !olim.equals(source.olim) : source.olim != null) return false;
        if (pagemeasurements != null ? !pagemeasurements.equals(source.pagemeasurements) : source.pagemeasurements != null)
            return false;

        if (provenancecitation != null ? !provenancecitation.equals(source.provenancecitation) : source.provenancecitation != null)
            return false;
        if (provenancecomment != null ? !provenancecomment.equals(source.provenancecomment) : source.provenancecomment != null)
            return false;
        if (provenancecountry != null ? !provenancecountry.equals(source.provenancecountry) : source.provenancecountry != null)
            return false;
        if (provenanceregion != null ? !provenanceregion.equals(source.provenanceregion) : source.provenanceregion != null)
            return false;
        if (rismabbrev != null ? !rismabbrev.equals(source.rismabbrev) : source.rismabbrev != null) return false;
        if (rismccm != null ? !rismccm.equals(source.rismccm) : source.rismccm != null) return false;
        if (rismimagefilename1 != null ? !rismimagefilename1.equals(source.rismimagefilename1) : source.rismimagefilename1 != null)
            return false;
        if (rismimagefilename2 != null ? !rismimagefilename2.equals(source.rismimagefilename2) : source.rismimagefilename2 != null)
            return false;
        if (rismimagefilename3 != null ? !rismimagefilename3.equals(source.rismimagefilename3) : source.rismimagefilename3 != null)
            return false;
        if (shelfmark != null ? !shelfmark.equals(source.shelfmark) : source.shelfmark != null) return false;
        if (sourcename != null ? !sourcename.equals(source.sourcename) : source.sourcename != null) return false;
        if (sourcetype != null ? !sourcetype.equals(source.sourcetype) : source.sourcetype != null) return false;
        if (startdate != null ? !startdate.equals(source.startdate) : source.startdate != null) return false;

        if (stavegauge != null ? !stavegauge.equals(source.stavegauge) : source.stavegauge != null) return false;
        if (surface != null ? !surface.equals(source.surface) : source.surface != null) return false;
        if (tags != null ? !tags.equals(source.tags) : source.tags != null) return false;
        if (watermark != null ? !watermark.equals(source.watermark) : source.watermark != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = sourcekey;
        result = 31 * result + (altNumberingSource != null ? altNumberingSource.hashCode() : 0);
        result = 31 * result + (altrismabbrev != null ? altrismabbrev.hashCode() : 0);
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        result = 31 * result + (ccmabbrev != null ? ccmabbrev.hashCode() : 0);
        result = 31 * result + (ccmimagefilename1 != null ? ccmimagefilename1.hashCode() : 0);
        result = 31 * result + (ccmimagefilename2 != null ? ccmimagefilename2.hashCode() : 0);
        result = 31 * result + (ccmimagefilename3 != null ? ccmimagefilename3.hashCode() : 0);
        result = 31 * result + (cmmeId != null ? cmmeId.hashCode() : 0);
        result = 31 * result + (completems != null ? completems.hashCode() : 0);
        result = 31 * result + (datecomments != null ? datecomments.hashCode() : 0);
        result = 31 * result + (dateofsource != null ? dateofsource.hashCode() : 0);
        result = 31 * result + (dedicationtext != null ? dedicationtext.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (diammDescription != null ? diammDescription.hashCode() : 0);
        result = 31 * result + (enddate != null ? enddate.hashCode() : 0);

        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        result = 31 * result + (liminarytext != null ? liminarytext.hashCode() : 0);
        result = 31 * result + (notation != null ? notation.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (numberingSource != null ? numberingSource.hashCode() : 0);
        result = 31 * result + (olim != null ? olim.hashCode() : 0);
        result = 31 * result + (pagemeasurements != null ? pagemeasurements.hashCode() : 0);

        result = 31 * result + (provenancecitation != null ? provenancecitation.hashCode() : 0);
        result = 31 * result + (provenancecomment != null ? provenancecomment.hashCode() : 0);
        result = 31 * result + (provenancecountry != null ? provenancecountry.hashCode() : 0);
        result = 31 * result + (provenanceregion != null ? provenanceregion.hashCode() : 0);
        result = 31 * result + (rismabbrev != null ? rismabbrev.hashCode() : 0);
        result = 31 * result + (rismccm != null ? rismccm.hashCode() : 0);
        result = 31 * result + (rismimagefilename1 != null ? rismimagefilename1.hashCode() : 0);
        result = 31 * result + (rismimagefilename2 != null ? rismimagefilename2.hashCode() : 0);
        result = 31 * result + (rismimagefilename3 != null ? rismimagefilename3.hashCode() : 0);
        result = 31 * result + (shelfmark != null ? shelfmark.hashCode() : 0);
        result = 31 * result + sortorder;
        result = 31 * result + (sourcename != null ? sourcename.hashCode() : 0);
        result = 31 * result + (sourcetype != null ? sourcetype.hashCode() : 0);
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);

        result = 31 * result + (stavegauge != null ? stavegauge.hashCode() : 0);
        result = 31 * result + (surface != null ? surface.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (watermark != null ? watermark.hashCode() : 0);
        result = 31 * result + (sourcename != null ? sourcename.hashCode() : 0);
        return result;
    }

    private Collection<Bibliographysource> bibliographysources;


    @OneToMany(mappedBy = "source")
    public Collection<Bibliographysource> getBibliographysources() {
        return bibliographysources;
    }

    public void setBibliographysources(Collection<Bibliographysource> bibliographysources) {
        this.bibliographysources = bibliographysources;
    }


    private Collection<Item> itemsBySourcekey;

    @OneToMany(mappedBy = "sourceBySourcekey")
    @OrderBy("orderno")
    public Collection<Item> getItemsBySourcekey() {
        return itemsBySourcekey;
    }

    public void setItemsBySourcekey(Collection<Item> itemsBySourcekey) {
        this.itemsBySourcekey = itemsBySourcekey;
    }

    private Archive archiveByArchivekey;

    @ManyToOne
    @JoinColumn(name = "archiveKey", referencedColumnName = "archiveKey", nullable = false)
    public Archive getArchiveByArchivekey() {
        return archiveByArchivekey;
    }

    public void setArchiveByArchivekey(Archive archiveByArchivekey) {
        this.archiveByArchivekey = archiveByArchivekey;
    }

    private Collection<Sourcealperson> sourcealpersonsBySourcekey;

    @OneToMany(mappedBy = "sourceBySourcekey")
    public Collection<Sourcealperson> getSourcealpersonsBySourcekey() {
        return sourcealpersonsBySourcekey;
    }

    public void setSourcealpersonsBySourcekey(Collection<Sourcealperson> sourcealpersonsBySourcekey) {
        this.sourcealpersonsBySourcekey = sourcealpersonsBySourcekey;
    }

    private Collection<Sourcecopyist> sourcecopyists;

    @OneToMany(mappedBy = "sourceBySourcekey")
    public Collection<Sourcecopyist> getSourcecopyists() {
        return sourcecopyists;
    }

    public void setSourcecopyists(Collection<Sourcecopyist> sourcecopyists) {
        this.sourcecopyists = sourcecopyists;
    }


    private Collection<Sourceprovenance> sourceprovenancesBySourcekey;

    @OneToMany(mappedBy = "sourceBySourcekey")
    public Collection<Sourceprovenance> getSourceprovenancesBySourcekey() {
        return sourceprovenancesBySourcekey;
    }

    public void setSourceprovenancesBySourcekey(Collection<Sourceprovenance> sourceprovenancesBySourcekey) {
        this.sourceprovenancesBySourcekey = sourceprovenancesBySourcekey;
    }

    private Collection<Sourceset> sourcesetsBySourcekey;

    @OneToMany(mappedBy = "sourceBySourcekey")
    public Collection<Sourceset> getSourcesetsBySourcekey() {
        return sourcesetsBySourcekey;
    }

    public void setSourcesetsBySourcekey(Collection<Sourceset> sourcesetsBySourcekey) {
        this.sourcesetsBySourcekey = sourcesetsBySourcekey;
    }

    private Collection<Set> sets;

    @ManyToMany(targetEntity = Set.class)
    @JoinTable(catalog = "diamm_ess", name = "SourceSet",
            joinColumns = @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "setKey", referencedColumnName = "setKey", nullable = false))
    public Collection<Set> getSets() {
        return sets;
    }

    public void setSets(Collection<Set> sets) {
        this.sets = sets;
    }

    private Collection<Alperson> persons;

    @ManyToMany(targetEntity = Alperson.class)
    @JoinTable(catalog = "diamm_ess", name = "SourceAlPerson",
            joinColumns = @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "alPersonKey", referencedColumnName = "alPersonKey", nullable = false))
    public Collection<Alperson> getPersons() {
        return persons;
    }

    public void setPersons(Collection<Alperson> persons) {
        this.persons = persons;
    }


    private Collection<Alprovenance> alprovenances;

    @ManyToMany
    @JoinTable(catalog = "diamm_ess", name = "SourceProvenance", joinColumns = @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false), inverseJoinColumns = @JoinColumn(name = "alProvenanceKey", referencedColumnName = "alProvenanceKey", nullable = false))
    public Collection<Alprovenance> getAlprovenances() {
        return alprovenances;
    }

    public void setAlprovenances(Collection<Alprovenance> alprovenances) {
        this.alprovenances = alprovenances;
    }


    private int archivekey;

    @Basic
    @Column(name = "archiveKey", nullable = false, length = 10, insertable = false, updatable = false)
    public int getArchivekey() {
        return archivekey;
    }

    public void setArchivekey(int archivekey) {
        this.archivekey = archivekey;
    }


    /*
    private Collection<Sourceprovenance> sourceprovenancesBySourcekey;

    @OneToMany(mappedBy = "sourceBySourcekey")
    public Collection<Sourceprovenance> getSourceprovenancesBySourcekey() {
        return sourceprovenancesBySourcekey;
    }

    public void setSourceprovenancesBySourcekey(Collection<Sourceprovenance> sourceprovenancesBySourcekey) {
        this.sourceprovenancesBySourcekey = sourceprovenancesBySourcekey;
    }
     */


    private Collection<Notationsource> notationsources;

    @OneToMany(mappedBy = "source")
    public Collection<Notationsource> getNotationsources() {
        return notationsources;
    }

    public void setNotationsources(Collection<Notationsource> notationsources) {
        this.notationsources = notationsources;
    }

    private Collection<Alnotationtype> alnotationtypes;

    @ManyToMany
    @JoinTable(catalog = "diamm_ess", name = "NotationSource", joinColumns = @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false), inverseJoinColumns = @JoinColumn(name = "alnotationtypekey", referencedColumnName = "alnotationtypekey", nullable = false))
    public Collection<Alnotationtype> getAlnotationtypes() {
        return alnotationtypes;
    }

    public void setAlnotationtypes(Collection<Alnotationtype> alnotationtypes) {
        this.alnotationtypes = alnotationtypes;
    }

    private List<Note> noteList = new ArrayList<Note>();

    @OneToMany
    @JoinTable(name = "NoteSource",
            joinColumns = {@JoinColumn(name = "sourceKey")},
            inverseJoinColumns = {@JoinColumn(name = "noteKey")})
    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    //Convenience method for obtaining all images of a source
    @Transient
    public List<Image> getImageList() {
        List<Image> imageList = new ArrayList<Image>();

        for (Item item : itemsBySourcekey) {
            imageList.addAll(item.getImageList());
        }

        return imageList;
    }
}

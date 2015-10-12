package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Item {
    private int itemkey;

    @Id
    @Column(name = "itemKey", nullable = false, length = 10)
    public int getItemkey() {
        return itemkey;
    }

    public void setItemkey(int itemkey) {
        this.itemkey = itemkey;
    }

    private int sourcekey;
    @Basic
    @Column(name = "sourceKey", length = 10, nullable = false,insertable = false,updatable = false)
    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }




    /*private String label;

    @Basic
    @Column(name = "label", length = 2147483647)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }*/

    private String abbrevposn;

    @Basic
    @Column(name = "abbrevPosn", length = 2147483647)
    public String getAbbrevposn() {
        return abbrevposn;
    }

    public void setAbbrevposn(String abbrevposn) {
        this.abbrevposn = abbrevposn;
    }


    private String copyingstyle;

    @Basic
    @Column(name = "copyingstyle", length = 2147483647)
    public String getCopyingstyle() {
        return copyingstyle;
    }

    public void setCopyingstyle(String copyingstyle) {
        this.copyingstyle = copyingstyle;
    }

    private String corrections;

    @Basic
    @Column(name = "corrections", length = 2147483647)
    public String getCorrections() {
        return corrections;
    }

    public void setCorrections(String corrections) {
        this.corrections = corrections;
    }

    private String edition;

    @Basic
    @Column(name = "edition", length = 2147483647)
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    private String foliationpagination;

    @Basic
    @Column(name = "foliationPagination", length = 2147483647)
    public String getFoliationpagination() {
        return foliationpagination;
    }

    public void setFoliationpagination(String foliationpagination) {
        this.foliationpagination = foliationpagination;
    }

    private String folioEnd;

    @Basic
    @Column(name = "folio_end", length = 2147483647)
    public String getFolioEnd() {
        return folioEnd;
    }

    public void setFolioEnd(String folioEnd) {
        this.folioEnd = folioEnd;
    }

    private String layout;

    @Basic
    @Column(name = "layout", length = 2147483647)
    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    private String localAttribution;

    @Basic
    @Column(name = "local_attribution", length = 2147483647)
    public String getLocalAttribution() {
        return localAttribution;
    }

    public void setLocalAttribution(String localAttribution) {
        this.localAttribution = localAttribution;
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

    private String positionms;

    @Basic
    @Column(name = "positionMS", length = 2147483647)
    public String getPositionms() {
        return positionms;
    }

    public void setPositionms(String positionms) {
        this.positionms = positionms;
    }

    private String positionpage;

    @Basic
    @Column(name = "positionPage", length = 2147483647)
    public String getPositionpage() {
        return positionpage;
    }

    public void setPositionpage(String positionpage) {
        this.positionpage = positionpage;
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

    private String folioStart;

    @Basic
    @Column(name = "folio_start", length = 2147483647)
    public String getFolioStart() {
        return folioStart;
    }

    public void setFolioStart(String folioStart) {
        this.folioStart = folioStart;
    }

    private String folioStartAlt;

    @Basic
    @Column(name = "folio_start_alt", length = 2147483647)
    public String getFolioStartAlt() {
        return folioStartAlt;
    }

    public void setFolioStartAlt(String folioStartAlt) {
        this.folioStartAlt = folioStartAlt;
    }

    private String folioEndAlt;

    @Basic
    @Column(name = "folio_end_alt", length = 2147483647)
    public String getFolioEndAlt() {
        return folioEndAlt;
    }

    public void setFolioEndAlt(String folioEndAlt) {
        this.folioEndAlt = folioEndAlt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (folioEnd != item.folioEnd) return false;
        if (folioStart != item.folioStart) return false;
        if (itemkey != item.itemkey) return false;
        if (orderno != item.orderno) return false;
        if (abbrevposn != null ? !abbrevposn.equals(item.abbrevposn) : item.abbrevposn != null) return false;

        if (copyingstyle != null ? !copyingstyle.equals(item.copyingstyle) : item.copyingstyle != null) return false;
        if (corrections != null ? !corrections.equals(item.corrections) : item.corrections != null) return false;
        if (edition != null ? !edition.equals(item.edition) : item.edition != null) return false;
        if (foliationpagination != null ? !foliationpagination.equals(item.foliationpagination) : item.foliationpagination != null)
            return false;
        if (folioEndAlt != null ? !folioEndAlt.equals(item.folioEndAlt) : item.folioEndAlt != null) return false;
        if (folioStartAlt != null ? !folioStartAlt.equals(item.folioStartAlt) : item.folioStartAlt != null)
            return false;

        if (layout != null ? !layout.equals(item.layout) : item.layout != null) return false;
        if (localAttribution != null ? !localAttribution.equals(item.localAttribution) : item.localAttribution != null)
            return false;
        if (notes != null ? !notes.equals(item.notes) : item.notes != null) return false;
        if (positionms != null ? !positionms.equals(item.positionms) : item.positionms != null) return false;
        if (positionpage != null ? !positionpage.equals(item.positionpage) : item.positionpage != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = itemkey;
        
        result = 31 * result + (abbrevposn != null ? abbrevposn.hashCode() : 0);

        result = 31 * result + (copyingstyle != null ? copyingstyle.hashCode() : 0);
        result = 31 * result + (corrections != null ? corrections.hashCode() : 0);
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (foliationpagination != null ? foliationpagination.hashCode() : 0);
        //result = 31 * result + folioEnd;
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        result = 31 * result + (localAttribution != null ? localAttribution.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (positionms != null ? positionms.hashCode() : 0);
        result = 31 * result + (positionpage != null ? positionpage.hashCode() : 0);
        result = 31 * result + orderno;
        //result = 31 * result + folioStart;
        result = 31 * result + (folioStartAlt != null ? folioStartAlt.hashCode() : 0);
        result = 31 * result + (folioEndAlt != null ? folioEndAlt.hashCode() : 0);
        return result;
    }

    private Composition compositionByCompositionkey;

    @ManyToOne
    @JoinColumn(name = "compositionKey", referencedColumnName = "compositionKey", nullable = false,insertable = false,updatable = false)
    public Composition getCompositionByCompositionkey() {
        return compositionByCompositionkey;
    }

    public void setCompositionByCompositionkey(Composition compositionByCompositionkey) {
        this.compositionByCompositionkey = compositionByCompositionkey;
    }

    private Source sourceBySourcekey;

    @ManyToOne
    @JoinColumn(name = "sourceKey", referencedColumnName = "sourceKey", nullable = false)
    public Source getSourceBySourcekey() {
        return sourceBySourcekey;
    }

    public void setSourceBySourcekey(Source sourceBySourcekey) {
        this.sourceBySourcekey = sourceBySourcekey;
    }

    private Collection<Itemimage> itemimagesByItemkey;

    @OneToMany(mappedBy = "itemByItemkey",fetch = FetchType.LAZY)
    public Collection<Itemimage> getItemimagesByItemkey() {
        return itemimagesByItemkey;
    }

    public void setItemimagesByItemkey(Collection<Itemimage> itemimagesByItemkey) {
        this.itemimagesByItemkey = itemimagesByItemkey;
    }


    private Collection<Text> textsByItemkey;

    @OneToMany(mappedBy = "itemByItemkey",fetch = FetchType.LAZY)
    @OrderBy("orderno")
    public Collection<Text> getTextsByItemkey() {
        return textsByItemkey;
    }

    public void setTextsByItemkey(Collection<Text> textsByItemkey) {
        this.textsByItemkey = textsByItemkey;
    }

    private int compositionkey;

    @Basic
    @Column(name = "compositionKey", nullable = false, length = 10)
    public int getCompositionkey() {
        return compositionkey;
    }

    public void setCompositionkey(int compositionkey) {
        this.compositionkey = compositionkey;
    }

    private int hasImages;

    @Basic
    @Column(name = "hasImages", length = 10, nullable = false,insertable = false,updatable = false)
    public int getHasImages() {
        return hasImages;
    }

    public void setHasImages(int hasImages) {
        this.hasImages = hasImages;
    }




    private String restligatureconfig;

    @Basic
    @Column(name = "restligatureconfig", length = 2147483647)
    public String getRestligatureconfig() {
        return restligatureconfig;
    }

    public void setRestligatureconfig(String restligatureconfig) {
        this.restligatureconfig = restligatureconfig;
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

    private String scribalhabit;

    @Basic
    @Column(name = "scribalhabit", length = 2147483647)
    public String getScribalhabit() {
        return scribalhabit;
    }

    public void setScribalhabit(String scribalhabit) {
        this.scribalhabit = scribalhabit;
    }

    private String stdtextsource;

    @Basic
    @Column(name = "stdTextSource", length = 2147483647)
    public String getStdtextsource() {
        return stdtextsource;
    }

    public void setStdtextsource(String stdtextsource) {
        this.stdtextsource = stdtextsource;
    }

    private String textincipitsource;

    @Basic
    @Column(name = "textIncipitSource", length = 2147483647)
    public String getTextincipitsource() {
        return textincipitsource;
    }

    public void setTextincipitsource(String textincipitsource) {
        this.textincipitsource = textincipitsource;
    }

    private String textincipitstandard;

    @Basic
    @Column(name = "textIncipitStandard", length = 2147483647)
    public String getTextincipitstandard() {
        return textincipitstandard;
    }

    public void setTextincipitstandard(String textincipitstandard) {
        this.textincipitstandard = textincipitstandard;
    }

    private String textnotationstylekey;

    @Basic
    @Column(name = "textNotationStyleKey", length = 2147483647)
    public String getTextnotationstylekey() {
        return textnotationstylekey;
    }

    public void setTextnotationstylekey(String textnotationstylekey) {
        this.textnotationstylekey = textnotationstylekey;
    }

    private String textnotationcolour;

    @Basic
    @Column(name = "textNotationColour", length = 2147483647)
    public String getTextnotationcolour() {
        return textnotationcolour;
    }

    public void setTextnotationcolour(String textnotationcolour) {
        this.textnotationcolour = textnotationcolour;
    }

    private String texttype;

    @Basic
    @Column(name = "textType", length = 2147483647)
    public String getTexttype() {
        return texttype;
    }

    public void setTexttype(String texttype) {
        this.texttype = texttype;
    }

    private String datecomposed;

    @Basic
    @Column(name = "dateComposed", length = 2147483647)
    public String getDatecomposed() {
        return datecomposed;
    }

    public void setDatecomposed(String datecomposed) {
        this.datecomposed = datecomposed;
    }

    private String datecopied;

    @Basic
    @Column(name = "dateCopied", length = 2147483647)
    public String getDatecopied() {
        return datecopied;
    }

    public void setDatecopied(String datecopied) {
        this.datecopied = datecopied;
    }

    private String incipitfilename;

    @Basic
    @Column(name = "incipitFilename", length = 2147483647)
    public String getIncipitfilename() {
        return incipitfilename;
    }

    public void setIncipitfilename(String incipitfilename) {
        this.incipitfilename = incipitfilename;
    }

    private String incipittranscription;

    @Basic
    @Column(name = "incipitTranscription", length = 2147483647)
    public String getIncipittranscription() {
        return incipittranscription;
    }

    public void setIncipittranscription(String incipittranscription) {
        this.incipittranscription = incipittranscription;
    }

    private String motetincipitstandard;

    @Basic
    @Column(name = "motetIncipitStandard", length = 2147483647)
    public String getMotetincipitstandard() {
        return motetincipitstandard;
    }

    public void setMotetincipitstandard(String motetincipitstandard) {
        this.motetincipitstandard = motetincipitstandard;
    }

    private String motetorder;

    @Basic
    @Column(name = "motetOrder", length = 2147483647)
    public String getMotetorder() {
        return motetorder;
    }

    public void setMotetorder(String motetorder) {
        this.motetorder = motetorder;
    }

    private String motetcomments;

    @Basic
    @Column(name = "motetComments", length = 2147483647)
    public String getMotetcomments() {
        return motetcomments;
    }

    public void setMotetcomments(String motetcomments) {
        this.motetcomments = motetcomments;
    }

    private String altincipitfilename;

    @Basic
    @Column(name = "altIncipitFilename", length = 2147483647)
    public String getAltincipitfilename() {
        return altincipitfilename;
    }

    public void setAltincipitfilename(String altincipitfilename) {
        this.altincipitfilename = altincipitfilename;
    }

    private String motetincipitfilename;

    @Basic
    @Column(name = "motetIncipitFilename", length = 2147483647)
    public String getMotetincipitfilename() {
        return motetincipitfilename;
    }

    public void setMotetincipitfilename(String motetincipitfilename) {
        this.motetincipitfilename = motetincipitfilename;
    }

    private String motetsourcecomments;

    @Basic
    @Column(name = "motetSourceComments", length = 2147483647)
    public String getMotetsourcecomments() {
        return motetsourcecomments;
    }

    public void setMotetsourcecomments(String motetsourcecomments) {
        this.motetsourcecomments = motetsourcecomments;
    }

    private String musicalgenre;

    @Basic
    @Column(name = "musicalGenre", length = 2147483647)
    public String getMusicalgenre() {
        return musicalgenre;
    }

    public void setMusicalgenre(String musicalgenre) {
        this.musicalgenre = musicalgenre;
    }

    private String musicalincipitscore;

    @Basic
    @Column(name = "musicalIncipitScore", length = 2147483647)
    public String getMusicalincipitscore() {
        return musicalincipitscore;
    }

    public void setMusicalincipitscore(String musicalincipitscore) {
        this.musicalincipitscore = musicalincipitscore;
    }

    private String musiccolour;

    @Basic
    @Column(name = "musiccolour", length = 2147483647)
    public String getMusiccolour() {
        return musiccolour;
    }

    public void setMusiccolour(String musiccolour) {
        this.musiccolour = musiccolour;
    }

    private String musicnotationstylekey;

    @Basic
    @Column(name = "musicNotationStyleKey", length = 2147483647)
    public String getMusicnotationstylekey() {
        return musicnotationstylekey;
    }

    public void setMusicnotationstylekey(String musicnotationstylekey) {
        this.musicnotationstylekey = musicnotationstylekey;
    }

    private String pars;

    @Basic
    @Column(name = "pars", length = 2147483647)
    public String getPars() {
        return pars;
    }

    public void setPars(String pars) {
        this.pars = pars;
    }

    private String piecenumber;

    @Basic
    @Column(name = "piecenumber", length = 2147483647)
    public String getPiecenumber() {
        return piecenumber;
    }

    public void setPiecenumber(String piecenumber) {
        this.piecenumber = piecenumber;
    }
    
  //Convenience method for obtaining all images of an item
    @Transient
    public List<Image> getImageList()
    {
    	List<Image> imageList = new ArrayList<Image>();
    	
    	for (Itemimage itemimage : itemimagesByItemkey)
    	{
    		imageList.add(itemimage.getImageByImagekey());
    	}
    	
    	return imageList;
    }

private Collection<Bibliographyitem> bibliographyItems;

    @OneToMany(mappedBy = "itemByItemkey")
    public Collection<Bibliographyitem> getBibliographyItems() {
        return bibliographyItems;
    }

    public void setBibliographyItems(Collection<Bibliographyitem> bibliographyItems) {
        this.bibliographyItems = bibliographyItems;
    }



}

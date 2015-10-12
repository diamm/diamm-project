package uk.ac.kcl.cch.diamm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.*;
import uk.ac.kcl.cch.diamm.model.Authorbibliography.BibliographyPerson;
import uk.ac.kcl.cch.diamm.ui.BibliographyIntersectionDisplay;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 04-Nov-2010
 * Time: 10:23:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Bibliography {
    private int bibliographykey;

    @Id
    @Column(name = "bibliographyKey", nullable = false, length = 10)
    public int getBibliographykey() {
        return bibliographykey;
    }

    public void setBibliographykey(int bibliographykey) {
        this.bibliographykey = bibliographykey;
    }

    private String articletitle;

    @Basic
    @Column(name = "articleTitle", length = 2147483647)
    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }

    private String biblabbrev;

    @Basic
    @Column(name = "biblAbbrev", length = 2147483647)
    public String getBiblabbrev() {
        return biblabbrev;
    }

    public void setBiblabbrev(String biblabbrev) {
        this.biblabbrev = biblabbrev;
    }

    private String booktitle;

    @Basic
    @Column(name = "bookTitle", length = 2147483647)
    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    private String chapter;

    @Basic
    @Column(name = "chapter", length = 2147483647)
    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    private String degree;

    @Basic
    @Column(name = "degree", length = 2147483647)
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    private String dissertation;

    @Basic
    @Column(name = "dissertation", length = 2147483647)
    public String getDissertation() {
        return dissertation;
    }

    public void setDissertation(String dissertation) {
        this.dissertation = dissertation;
    }

    private String festschrift;

    @Basic
    @Column(name = "festschrift", length = 2147483647)
    public String getFestschrift() {
        return festschrift;
    }

    public void setFestschrift(String festschrift) {
        this.festschrift = festschrift;
    }

    private String fulltextcalculated;

    @Basic
    @Column(name = "fullTextCalculated", length = 2147483647)
    public String getFulltextcalculated() {
        return fulltextcalculated;
    }

    public void setFulltextcalculated(String fulltextcalculated) {
        this.fulltextcalculated = fulltextcalculated;
    }

    private String informationsource;

    @Basic
    @Column(name = "informationSource", length = 2147483647)
    public String getInformationsource() {
        return informationsource;
    }

    public void setInformationsource(String informationsource) {
        this.informationsource = informationsource;
    }

    private String journal;

    @Basic
    @Column(name = "journal", length = 2147483647)
    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }



    private String novolumes;

    @Basic
    @Column(name = "noVolumes", length = 2147483647)
    public String getNovolumes() {
        return novolumes;
    }

    public void setNovolumes(String novolumes) {
        this.novolumes = novolumes;
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



    private String page;

    @Basic
    @Column(name = "page", length = 2147483647)
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private String placepublication;

    @Basic
    @Column(name = "placePublication", length = 2147483647)
    public String getPlacepublication() {
        return placepublication;
    }

    public void setPlacepublication(String placepublication) {
        this.placepublication = placepublication;
    }

    private String publisher;

    @Basic
    @Column(name = "publisher", length = 2147483647)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private String seriestitle;

    @Basic
    @Column(name = "seriesTitle", length = 2147483647)
    public String getSeriestitle() {
        return seriestitle;
    }

    public void setSeriestitle(String seriestitle) {
        this.seriestitle = seriestitle;
    }

    private String university;

    @Basic
    @Column(name = "university", length = 2147483647)
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    private String vol;

    @Basic
    @Column(name = "vol", length = 2147483647)
    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    private String volno;

    @Basic
    @Column(name = "volNo", length = 2147483647)
    public String getVolno() {
        return volno;
    }

    public void setVolno(String volno) {
        this.volno = volno;
    }

    private String year;

    @Basic
    @Column(name = "year", length = 2147483647)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    private List<Source> sourceList = new ArrayList<Source>();
    
    @ManyToMany
    @JoinTable(name = "BibliographySource",
    		   joinColumns = {@JoinColumn(name = "bibliographyKey")},
    		   inverseJoinColumns = {@JoinColumn(name = "sourceKey")})
    public List<Source> getSourceList(){
    	return sourceList;
    }
    
    public void setSourceList(List<Source> sourceList){
    	this.sourceList = sourceList;
    }
    
    private List<Alauthor> authorMixedList = new ArrayList<Alauthor>();
    
    @ManyToMany
    @JoinTable(name = "AuthorBibliography",
    		   joinColumns = {@JoinColumn(name = "bibliographyKey")},
    		   inverseJoinColumns = {@JoinColumn(name = "alAuthorKey")})
    public List<Alauthor> getAuthorMixedList(){
    	return authorMixedList;
    }
    
    public void setAuthorMixedList(List<Alauthor> authorMixedList){
    	this.authorMixedList = authorMixedList;
    }
    
    private List<Authorbibliography> authorBibliographyList;
    
    @OneToMany(mappedBy="bibliography",
			   targetEntity=Authorbibliography.class)
	public List<Authorbibliography> getAuthorBibliographyList() {
		return authorBibliographyList;
	}
 
	public void setAuthorBibliographyList(List<Authorbibliography> authorBibliographyList) {
		this.authorBibliographyList = authorBibliographyList;
	}
    
    //Convenience method to get a list of strictly authors


    
    /*@Transient
    //Convenience method to get a list of strictly authors as a String
    public String getAuthorStrictListAsString(){
    	return getString(getAuthorStrictList());
    }
    
    @Transient
    //Convenience method to get a list of strictly editors as a String
    public String getEditorStrictListAsString(){
    	return getString(getEditorStrictList());
    }*/
    
    @Transient
    //Convenience method to get a list of sources grouped by archive 
    public List<ArchiveToSource> getArchiveToSourceList(){
    	List<Source> orderedSourceList = new ArrayList<Source>(sourceList);
    	Collections.sort(orderedSourceList, new SourceSortByDefault()); 
    	
    	int lastArchiveKey = 0;
    	ArchiveToSource ats = new ArchiveToSource();
    	List<ArchiveToSource> archiveToSourceList = new ArrayList<ArchiveToSource>();
    	List<Source> specificSourceList = new ArrayList<Source>();
    	
    	for (Source source: orderedSourceList) {
    		Archive archive = source.getArchiveByArchivekey();
    		
    		if (archive.getArchivekey() != lastArchiveKey) {		
    			if (!specificSourceList.isEmpty()) {
    				ats.setSourceList(specificSourceList);	
    				archiveToSourceList.add(ats);
    			}
    			
    			ats = new ArchiveToSource();
    			ats.setArchive(archive);
    			lastArchiveKey = archive.getArchivekey();
    			specificSourceList = new ArrayList<Source>();
    		}
    		specificSourceList.add(source);
    	}
    	
    	if (!specificSourceList.isEmpty()) {
			ats.setSourceList(specificSourceList);	
			archiveToSourceList.add(ats);
		}
    	
    	return archiveToSourceList;
    }


    @Transient
    //Convenience method to get the size of the source list
    public Boolean getHasSource()
    {
    	return (sourceList.size() > 0);
    }
    
    //Comparator class for default ordering of the source list
    private class SourceSortByDefault implements Comparator<Source> {    
		public int compare(Source s1, Source s2) {
			
			//1. Order by country name
			int order = s1.getArchiveByArchivekey().getAlcityByAlcitykey().getAlcountryByAlcountrykey().getCountry().compareTo(
			            s2.getArchiveByArchivekey().getAlcityByAlcitykey().getAlcountryByAlcountrykey().getCountry());
			            
			//2. Order by city name
			if (order == 0) {
				order = s1.getArchiveByArchivekey().getAlcityByAlcitykey().getCity().compareTo(
						s2.getArchiveByArchivekey().getAlcityByAlcitykey().getCity());
				
		    //3. Order by archive name
				if (order == 0) {
					order = s1.getArchiveByArchivekey().getArchivename().compareTo(
							s2.getArchiveByArchivekey().getArchivename());
				}
			}
			
			return order;    
		}
	}
    
    private String getString(List<Alauthor> authorMixedList){
    	final String NAME_DELIM   = ", ";
    	final String AUTHOR_MIXED_DELIM = "; ";
    	
    	StringBuffer authorMixedListString = new StringBuffer();
    	
    	Integer listSize = authorMixedList.size();
    	Integer listRow = 0;
    	
    	for (Alauthor authorMixed: authorMixedList){
    	    authorMixedListString.append(authorMixed.getLastname()).append(NAME_DELIM).append(authorMixed.getFirstname());
    	    listRow++;
    	    
    	    if (listRow < listSize){
    	    	authorMixedListString.append(AUTHOR_MIXED_DELIM);
    	    }
    	}
    	return authorMixedListString.toString();
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

        Bibliography that = (Bibliography) o;

        if (bibliographykey != that.bibliographykey) return false;
        if (articletitle != null ? !articletitle.equals(that.articletitle) : that.articletitle != null) return false;
        if (biblabbrev != null ? !biblabbrev.equals(that.biblabbrev) : that.biblabbrev != null) return false;
        if (booktitle != null ? !booktitle.equals(that.booktitle) : that.booktitle != null) return false;
        if (chapter != null ? !chapter.equals(that.chapter) : that.chapter != null) return false;
        if (degree != null ? !degree.equals(that.degree) : that.degree != null) return false;
        if (dissertation != null ? !dissertation.equals(that.dissertation) : that.dissertation != null) return false;
        if (festschrift != null ? !festschrift.equals(that.festschrift) : that.festschrift != null) return false;
        if (fulltextcalculated != null ? !fulltextcalculated.equals(that.fulltextcalculated) : that.fulltextcalculated != null)
            return false;
        if (informationsource != null ? !informationsource.equals(that.informationsource) : that.informationsource != null)
            return false;
        if (journal != null ? !journal.equals(that.journal) : that.journal != null) return false;

        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (novolumes != null ? !novolumes.equals(that.novolumes) : that.novolumes != null) return false;

        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (placepublication != null ? !placepublication.equals(that.placepublication) : that.placepublication != null)
            return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (seriestitle != null ? !seriestitle.equals(that.seriestitle) : that.seriestitle != null) return false;
        if (university != null ? !university.equals(that.university) : that.university != null) return false;
        if (vol != null ? !vol.equals(that.vol) : that.vol != null) return false;
        if (volno != null ? !volno.equals(that.volno) : that.volno != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = bibliographykey;
        result = 31 * result + (articletitle != null ? articletitle.hashCode() : 0);
        result = 31 * result + (biblabbrev != null ? biblabbrev.hashCode() : 0);
        result = 31 * result + (booktitle != null ? booktitle.hashCode() : 0);
        result = 31 * result + (chapter != null ? chapter.hashCode() : 0);
        result = 31 * result + (degree != null ? degree.hashCode() : 0);
        result = 31 * result + (dissertation != null ? dissertation.hashCode() : 0);
        result = 31 * result + (festschrift != null ? festschrift.hashCode() : 0);
        result = 31 * result + (fulltextcalculated != null ? fulltextcalculated.hashCode() : 0);
        result = 31 * result + (informationsource != null ? informationsource.hashCode() : 0);
        result = 31 * result + (journal != null ? journal.hashCode() : 0);

        result = 31 * result + (novolumes != null ? novolumes.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);

        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (placepublication != null ? placepublication.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (seriestitle != null ? seriestitle.hashCode() : 0);
        result = 31 * result + (university != null ? university.hashCode() : 0);
        result = 31 * result + (vol != null ? vol.hashCode() : 0);
        result = 31 * result + (volno != null ? volno.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }
}

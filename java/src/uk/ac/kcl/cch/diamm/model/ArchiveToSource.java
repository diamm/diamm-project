package uk.ac.kcl.cch.diamm.model;

import java.util.List;

public class ArchiveToSource {
	private Archive archive;
	private List<Source> sourceList;
	
	public Archive getArchive() {
		return archive;
	}
	
	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	
	public List<Source> getSourceList() {
		return sourceList;
	}
	
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}
	
	public String getArchiveDetails() {
		return (archive.getArchivename() + ", " + 
				archive.getAlcityByAlcitykey().getCity() + ", " +
				archive.getAlcityByAlcitykey().getAlcountryByAlcountrykey().getCountry());
	}
}
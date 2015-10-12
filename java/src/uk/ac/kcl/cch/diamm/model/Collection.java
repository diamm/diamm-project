package uk.ac.kcl.cch.diamm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(catalog = "diamm_ess")
public class Collection {
	//Definitions for map keys 
	public static final String MAP_KEY_TITLE       = "title";
	public static final String MAP_KEY_DESCRIPTION = "description";
	
	//Definitions for error messages
	public static final String MSG_REQUIRED   = "required";
	public static final String MSG_MAX_LENGTH = "maximum length is %len character(s)";
	
    private int id;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        int TITLE_MAX_LENGTH = 30;
    	
        if (title == null || title.trim().length() == 0) {
    		errorMessages.put(MAP_KEY_TITLE, MSG_REQUIRED);
    	}
    	else if (title.length() > TITLE_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_TITLE, MSG_MAX_LENGTH.replace("%len", String.valueOf(TITLE_MAX_LENGTH)));
    	}
        else {
    		this.title = title;
    	} 
    }

    private String description;

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    	int DESCRIPTION_MAX_LENGTH = 100;
    	
        if (description != null && description.length() > DESCRIPTION_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_DESCRIPTION, MSG_MAX_LENGTH.replace("%len", String.valueOf(DESCRIPTION_MAX_LENGTH)));
    	}
        else {
    		this.description = description;
    	} 
    }
    
    private DiammUser diammUser;
    
    @ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	public DiammUser getDiammUser() {
		return diammUser;
	}
    
    public void setDiammUser(DiammUser diammUser) {
      this.diammUser = diammUser;
    }
    
    private List<Source> sourceList = new ArrayList<Source>();
    
    @ManyToMany
    @JoinTable(name = "CollectionSource",
    		   joinColumns = {@JoinColumn(name = "collectionId")},
    		   inverseJoinColumns = {@JoinColumn(name = "sourceId")})
    public List<Source> getSourceList(){
    	return sourceList;
    }
    
    public void setSourceList(List<Source> sourceList){
    	this.sourceList = sourceList;
    }
    
    private List<Item> itemList = new ArrayList<Item>();
    
    @ManyToMany
    @JoinTable(name = "CollectionItem",
    		   joinColumns = {@JoinColumn(name = "collectionId")},
    		   inverseJoinColumns = {@JoinColumn(name = "itemId")})
    public List<Item> getItemList(){
    	return itemList;
    }
    
    public void setItemList(List<Item> itemList){
    	this.itemList = itemList;
    }
    
    private List<Image> imageList = new ArrayList<Image>();
    
    @ManyToMany
    @JoinTable(name = "CollectionImage",
    		   joinColumns = {@JoinColumn(name = "collectionId")},
    		   inverseJoinColumns = {@JoinColumn(name = "imageId")})
    public List<Image> getImageList(){
    	return imageList;
    }
    
    public void setImageList(List<Image> imageList){
    	this.imageList = imageList;
    }
    
    private Map<String, String> errorMessages = new HashMap<String, String>();
	
	@Transient
	public Map<String, String> getErrorMessages()
	{
		return errorMessages;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((diammUser == null) ? 0 : diammUser.hashCode());
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collection other = (Collection) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (diammUser == null) {
			if (other.diammUser != null)
				return false;
		} else if (!diammUser.equals(other.diammUser))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}

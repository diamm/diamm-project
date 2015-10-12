package uk.ac.kcl.cch.diamm.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.*;

@Entity
@Table(catalog = "diamm_ess")
public class Note {
    private int notekey;

    @Id
    @GeneratedValue
    @Column(name = "noteKey", nullable = false, length = 10)
    public int getNotekey() {
        return notekey;
    }

    public void setNotekey(int notekey) {
        this.notekey = notekey;
    }

    private Notetype type;
    
    @OneToOne
    @JoinColumn(name = "noteTypeKey", referencedColumnName = "noteTypeKey", nullable = false)
    public Notetype getType() {
        return type;
    }

    public void setType(Notetype type) {
        this.type = type;
    }

    private String notetext;

    @Basic
    @Column(name = "noteText", nullable = false,length = 2147483647)
    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext;
    }


    private int userkey;

    @Basic
    @Column(name = "userKey", nullable = false,length = 10)
    public int getUserkey() {
        return userkey;
    }

    public void setUserkey(int userkey) {
        this.userkey = userkey;
    }

    private DiammUser user;

    @ManyToOne
    @JoinColumn(name = "userKey", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public DiammUser getUser() {
        return user;
    }

    public void setUser(DiammUser user) {
        this.user = user;
    }
    
    private NoteVisibility noteVisibility;
    
    @ManyToOne
    @JoinColumn(name = "visibilityId", referencedColumnName = "id", nullable = false)
    public NoteVisibility getNoteVisibility() {
        return noteVisibility;    	
    }
    
    public void setNoteVisibility(NoteVisibility noteVisibility) {
    	this.noteVisibility = noteVisibility;
    }
    
    private Timestamp dateModified;
    
    @Basic
    @Column(name = "dateModified")
    public Timestamp getDateModified() {
        return dateModified;    	
    }
    
    public void setDateModified(Timestamp dateModified) {
    	this.dateModified = dateModified;
    }
    
    private Source noteSource;

    @ManyToOne
    @JoinTable(name = "NoteSource",joinColumns =@JoinColumn(name="noteKey"),inverseJoinColumns = @JoinColumn(name="sourceKey"))
    public Source getNoteSource() {
        return noteSource;
    }

    public void setNoteSource(Source noteSource) {
        this.noteSource = noteSource;
    }

    private Image noteImage;

    @ManyToOne
    @JoinTable(name = "NoteImage",joinColumns =@JoinColumn(name="noteKey"),inverseJoinColumns = @JoinColumn(name="imageKey"))
    public Image getNoteImage() {
        return noteImage;
    }

    public void setNoteImage(Image noteImage) {
        this.noteImage = noteImage;
    }
    
    //convenience method for obtaining the date in a human readable format
    @Transient
    public String getDateDisplay() {
    	return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(getDateModified()); 
    }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateModified == null) ? 0 : dateModified.hashCode());
		result = prime * result
				+ ((noteImage == null) ? 0 : noteImage.hashCode());
		result = prime * result
				+ ((noteSource == null) ? 0 : noteSource.hashCode());
		result = prime * result
				+ ((noteVisibility == null) ? 0 : noteVisibility.hashCode());
		result = prime * result + notekey;
		result = prime * result
				+ ((notetext == null) ? 0 : notetext.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (dateModified == null) {
			if (other.dateModified != null)
				return false;
		} else if (!dateModified.equals(other.dateModified))
			return false;
		if (noteImage == null) {
			if (other.noteImage != null)
				return false;
		} else if (!noteImage.equals(other.noteImage))
			return false;
		if (noteSource == null) {
			if (other.noteSource != null)
				return false;
		} else if (!noteSource.equals(other.noteSource))
			return false;
		if (noteVisibility == null) {
			if (other.noteVisibility != null)
				return false;
		} else if (!noteVisibility.equals(other.noteVisibility))
			return false;
		if (notekey != other.notekey)
			return false;
		if (notetext == null) {
			if (other.notetext != null)
				return false;
		} else if (!notetext.equals(other.notetext))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}

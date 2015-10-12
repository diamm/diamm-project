package uk.ac.kcl.cch.diamm.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(catalog = "diamm_ess", name = "NoteVisibility")
public class NoteVisibility {
	public static enum NoteVisibilityEnum {PV, PB}
	
    private int id;

    @Id
    @Column(name = "id", nullable = false, length = 10)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String code;

    @Basic
    @Column(name = "code", nullable = false, length = 2, columnDefinition = "char")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    private String description;

    @Basic
    @Column(name = "description", nullable = false, length = 10)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    private List<Note> noteList = new ArrayList<Note>();
	
	@OneToMany(mappedBy="noteVisibility",
			   targetEntity=Note.class)
	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteVisibility other = (NoteVisibility) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

    
}

package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 08-Dec-2010
 * Time: 14:33:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "NoteType")
public class Notetype {
	public static enum NoteTypeEnum {COM, TRA}
	
    private int notetypekey;

    @Id
    @Column(name = "noteTypeKey", nullable = false, length = 10)
    public int getNotetypekey() {
        return notetypekey;
    }

    public void setNotetypekey(int notetypekey) {
        this.notetypekey = notetypekey;
    }

    private String code;

    @Basic
    @Column(name = "code", nullable = false, length = 3, columnDefinition = "char")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    private String description;

    @Basic
    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + notetypekey;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notetype other = (Notetype) obj;
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
		if (notetypekey != other.notetypekey)
			return false;
		return true;
	}
}

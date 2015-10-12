package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

@Entity
@Table(catalog = "diamm_ess", name = "CollectionSource")
public class CollectionSource {
    private int id;

    @Id
    @Column(name = "id", nullable = false, length = 10)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int collectionId;

    @Basic
    @Column(name = "collectionId", length = 10)
    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    private int sourceId;

    @Basic
    @Column(name = "sourceId", length = 10)
    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + collectionId;
		result = prime * result + id;
		result = prime * result + sourceId;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectionSource other = (CollectionSource) obj;
		if (collectionId != other.collectionId)
			return false;
		if (id != other.id)
			return false;
		if (sourceId != other.sourceId)
			return false;
		return true;
	}
}

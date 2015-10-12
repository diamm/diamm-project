package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

@Entity
@Table(catalog = "diamm_ess", name = "CollectionItem")
public class CollectionImage {
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

    private int itemId;

    @Basic
    @Column(name = "itemId", length = 10)
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + collectionId;
		result = prime * result + id;
		result = prime * result + itemId;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectionImage other = (CollectionImage) obj;
		if (collectionId != other.collectionId)
			return false;
		if (id != other.id)
			return false;
		if (itemId != other.itemId)
			return false;
		return true;
	}
}

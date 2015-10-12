package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:12:55
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "ItemImage")
public class Itemimage {
    private int itemimagekey;

    @Id
    @Column(name = "itemImageKey", nullable = false, length = 10)
    public int getItemimagekey() {
        return itemimagekey;
    }

    public void setItemimagekey(int itemimagekey) {
        this.itemimagekey = itemimagekey;
    }

    private int itemkey;
    @Column(name = "itemKey", nullable = false, length = 10, insertable = false,updatable = false)
    public int getItemKey() {
        return itemkey;
    }

    public void setItemKey(int itemimagekey) {
        this.itemkey = itemimagekey;
    }





    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Itemimage itemimage = (Itemimage) o;

        if (itemimagekey != itemimage.itemimagekey) return false;


        return true;
    }

    public int hashCode() {
        int result;
        result = itemimagekey;

        return result;
    }

    private Item itemByItemkey;

    @ManyToOne
    @JoinColumn(name = "itemKey", referencedColumnName = "itemKey", nullable = false)
    public Item getItemByItemkey() {
        return itemByItemkey;
    }

    public void setItemByItemkey(Item itemByItemkey) {
        this.itemByItemkey = itemByItemkey;
    }

    private Image imageByImagekey;

    @ManyToOne
    @JoinColumn(name = "imageKey", referencedColumnName = "imageKey", nullable = false)
    public Image getImageByImagekey() {
        return imageByImagekey;
    }

    public void setImageByImagekey(Image imageByImagekey) {
        this.imageByImagekey = imageByImagekey;
    }
}

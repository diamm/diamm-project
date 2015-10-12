package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Item;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * A convenience class for organizing items into groups for display.
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 31-Aug-2010
 * Time: 18:38:15
 * To change this template use File | Settings | File Templates.
 */
public class ItemGroup implements Serializable{
    private String label;
    private int key;
    private GroupType groupType;
    private List<DisplayItem> itemList;

    public ItemGroup(GroupType groupType, int key, String label) {
        this.groupType = groupType;
        this.key = key;
        this.label = label;
        this.itemList=new ArrayList<DisplayItem>();
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public void addToItemList(DisplayItem i){
        itemList.add(i);
    }

    public void addToItemList(Item i){
        itemList.add(new DisplayItem(i));
    }

    public List<DisplayItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<DisplayItem> itemList) {
        this.itemList = itemList;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}

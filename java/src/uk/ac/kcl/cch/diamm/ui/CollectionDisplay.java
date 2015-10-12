package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Collection;
import uk.ac.kcl.cch.diamm.model.Item;
import uk.ac.kcl.cch.diamm.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 12/08/11
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class CollectionDisplay {
    private Collection coll;
    private ArrayList<DisplayItem> itemList;
    private ArrayList<SourceDisplay> sourceList;

    public CollectionDisplay(Collection c) {
        coll = c;
    }

    public void addItems() {
        List<Item> items =  coll.getItemList();
        if (items != null && items.size() > 0) {
            itemList = new ArrayList<DisplayItem>();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                DisplayItem di = new DisplayItem(item);
                itemList.add(di);
            }
        }
    }

    public void addSources() {
        List<Source> sources =  coll.getSourceList();
        if (sources != null && sources.size() > 0) {
            sourceList = new ArrayList<SourceDisplay>();
            for (int i = 0; i < sources.size(); i++) {
                Source source = sources.get(i);
                SourceDisplay sd=new SourceDisplay(source);
                sourceList.add(sd);
            }
        }
    }

    public Collection getColl() {
        return coll;
    }

    public void setColl(Collection coll) {
        this.coll = coll;
    }

    public ArrayList<DisplayItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<DisplayItem> itemList) {
        this.itemList = itemList;
    }

    public ArrayList<SourceDisplay> getSourceList() {
        return sourceList;
    }

    public void setSourceList(ArrayList<SourceDisplay> sourceList) {
        this.sourceList = sourceList;
    }
}

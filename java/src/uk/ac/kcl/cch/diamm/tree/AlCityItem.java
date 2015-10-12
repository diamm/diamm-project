package uk.ac.kcl.cch.diamm.tree;

import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.SourceFacet;
import uk.ac.kcl.cch.diamm.model.Alcity;
import uk.ac.kcl.cch.diamm.model.Archive;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.diamm.ui.SourceGUIFacet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 28-Jul-2010
 * Time: 11:04:05
 * To change this template use File | Settings | File Templates.
 */
public class AlCityItem implements uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem {
    private Alcity city;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AlCityItem(Alcity c) {
        this.city = c;
    }

    public AlCityItem(Alcity c, int count) {
        this.city = c;
        this.count = count;
    }


    private String cssclass;

    private IXHtmlHierarchyItem[] kids;


    public Vector<IXHtmlHierarchyItem> getAncestors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem[] getChildren() {
        if (kids != null) {
            return kids;
        }
        List<Archive> archives = (List<Archive>) city.getArchivesByAlcitykey();
        ArrayList<ArchiveItem> items = new ArrayList<ArchiveItem>();
        for (int i = 0; i < archives.size(); i++) {
            Archive arc = archives.get(i);
            ArrayList<Integer> cKeys = SourceFacet.getInitialvalues(arc.getArchivekey(), SourceFacet.archiveValues);
            if (cKeys != null && cKeys.size() > 0) {
                int count = 0;
                if (constraintKeys == null || constraintKeys.size() == 0) {
                    count =cKeys.size(); //SourceGUIFacet.getArchiveCount(arc.getArchivekey());
                } else {
                    count = DIAMMFacetManager.mergeKeyLists(cKeys, this.constraintKeys).size();
                }
                ArchiveItem item = new ArchiveItem(arc, count);
                if (constraintKeys != null && constraintKeys.size() > 0) {
                    item.setConstraintKeys(constraintKeys);
                }
                if (count > 0) {
                    items.add(item);
                }
            }
        }
        kids = items.toArray(new IXHtmlHierarchyItem[0]);
        return kids;
    }

    public String getChildrenContainerClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Vector<IXHtmlHierarchyItem> getDescendants() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFullID() {
        return "Alcity_" + getIDNumb();
    }

    public int getIDNumb() {
        return city.getAlcitykey();
    }

    public String getItemClass() {
        return cssclass;
    }

    public String getLabel() {
        return "<label><a href=\"FacetManager?op=" + (Constants.sync ? "1" : "2") + "&FacetType=SOURCEFACET&alCityKey=" +
                city.getAlcitykey() + "\" title=\"Set as criteria\"\n" +
                "  >" + city.getCity() + "</a></label> <dfn title=\""+getCount()+" items\">" + getCount() +
                "</dfn>";
    }

    public int getNumbChildren() {
        if (kids == null) {
            getChildren();
        }
        return kids.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem getParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasChildren() {
        if (kids == null) {
            getChildren();
        }
        return kids.length > 0;//To change body of implemented methods use File | Settings | File Templates.
    }

    public void setItemClass(String className) {
        cssclass = className;
    }

    private ArrayList<Integer> constraintKeys;

    public ArrayList<Integer> getConstraintKeys() {
        return constraintKeys;
    }

    public void setConstraintKeys(ArrayList<Integer> constraintKeys) {
        this.constraintKeys = constraintKeys;
    }
}

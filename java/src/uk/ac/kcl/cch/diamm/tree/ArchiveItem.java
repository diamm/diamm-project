package uk.ac.kcl.cch.diamm.tree;

import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;
import uk.ac.kcl.cch.diamm.model.Archive;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.diamm.ui.SourceGUIFacet;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.SourceFacet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 28-Jul-2010
 * Time: 11:04:24
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveItem implements uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem {
    private Archive archive;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArchiveItem(Archive c) {
        this.archive = c;
    }

    public ArchiveItem(Archive c, int count) {
        this.archive = c;
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
        List<Source> sources = (List<Source>) archive.getSourcesByArchivekey();
        ArrayList<SourceItem> items = new ArrayList<SourceItem>();
        for (int i = 0; i < sources.size(); i++) {
            Source s = sources.get(i);
            int count=0;
            if (constraintKeys==null||constraintKeys.size()==0){
                count=SourceGUIFacet.getSourceCount(s.getSourcekey());
            }  else{
                count= DIAMMFacetManager.mergeKeyLists(SourceFacet.getInitialSourceValues(s.getSourcekey()),this.constraintKeys).size();
            }
            SourceItem item=new SourceItem(s, count);            
            items.add(item);
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
        return "Archive_" + getIDNumb();
    }

    public int getIDNumb() {
        return archive.getArchivekey();
    }

    public String getItemClass() {
        return cssclass;
    }

    public String getLabel() {
         return "<label><a href=\"FacetManager?op=" + (Constants.sync ? "1" : "2") + "&FacetType=SOURCEFACET&archiveKey=" +
                archive.getArchivekey() + "\" title=\"Set as criteria\"\n" +
                "  >" + archive.getArchivename()  + "</a></label> <dfn title=\""+getCount()+" items\">" + getCount() +
                "</dfn><a href=\"Descriptions?op=ARCHIVE&summary=1&archiveKey="+getIDNumb()+"\" class=\"t9 m1\">View Record</a>";
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

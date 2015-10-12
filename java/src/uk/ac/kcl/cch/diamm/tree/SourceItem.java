package uk.ac.kcl.cch.diamm.tree;

import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.diamm.ui.Constants;

import java.util.Vector;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 28-Jul-2010
 * Time: 11:04:52
 * To change this template use File | Settings | File Templates.
 */
public class SourceItem implements uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem{

    private Source source;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SourceItem(Source c) {
        this.source = c;
    }

    public SourceItem(Source c, int count) {
        this.source = c;
        this.count = count;
    }


    private String cssclass;

    private IXHtmlHierarchyItem[] kids;


    public Vector<IXHtmlHierarchyItem> getAncestors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem[] getChildren() {
        return new IXHtmlHierarchyItem[0];
    }

    public String getChildrenContainerClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Vector<IXHtmlHierarchyItem> getDescendants() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFullID() {
        return "SourceItem_" + getIDNumb();
    }

    public int getIDNumb() {
        return source.getSourcekey();
    }

    public String getItemClass() {
        return cssclass;
    }

    public String getLabel() {
        String label=source.getShelfmark() ;
        return "<label><a href=\"FacetManager?op=" + (Constants.sync ? "1" : "2") + "&FacetType=SOURCEFACET&sourceKey=" +
                getIDNumb() + "\" title=\"Set as criteria\"\n" +
                "  >" + label  + "</a></label> <dfn title=\""+getCount()+" items\">" + getCount() +
                "</dfn><a href=\"Descriptions?op=SOURCE&summary=1&sourceKey="+getIDNumb()+"\" class=\"t9 m1\">View Record</a>";
                //"<d:userLoggedIn><a href=\"CollectionLink?linkId="+getIDNumb()+"&linkType=source\" class=\"t9 m2\">Add to a Collection</a></d:userLoggedIn>";
        /*label="<a href=\"FacetManager?op=1&facetType=SOURCEFACET&sourceKey="+getIDNumb()+"\">"+label+"</a>"+ " <dfn>(" + getCount() + ")</dfn>";
        label+="&nbsp;<a class=\"g5\" href=\"Descriptions?op=SOURCE&sourceKey="+getIDNumb()+"\"></a>";*/
    }

    

    public IXHtmlHierarchyItem getParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasChildren() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getNumbChildren() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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

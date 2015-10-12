package uk.ac.kcl.cch.XHtmlHierarchy;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 31-Jan-2008
 * Time: 12:31:53
 * To change this template use File | Settings | File Templates.
 */
public interface IXHtmlHierarchyItem {
   public int getIDNumb();
   public String getLabel();
   public IXHtmlHierarchyItem[] getChildren();
   public boolean hasChildren();
   public int getNumbChildren();
   public String getFullID();
   public IXHtmlHierarchyItem getParent();
   public Vector<IXHtmlHierarchyItem> getAncestors();
   public Vector<IXHtmlHierarchyItem> getDescendants();
   public String getChildrenContainerClass();
    public String getItemClass();
    public void setItemClass(String className);
}
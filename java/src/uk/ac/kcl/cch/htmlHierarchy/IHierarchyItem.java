/*
 * Created on 25-Nov-2004
 *
 * Created by: John Bradley
 */
package uk.ac.kcl.cch.htmlHierarchy;

/**
 * @author Bradley
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface IHierarchyItem {
   public int getIDNumb();
   public String getLabel();
   public IHierarchyItem[] getChildren();
   public boolean hasChildren();
   public int getNumbChildren();
}

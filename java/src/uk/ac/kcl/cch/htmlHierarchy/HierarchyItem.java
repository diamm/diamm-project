/*
 * Created on 02-Dec-2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package uk.ac.kcl.cch.htmlHierarchy;

import java.util.Vector;

/**
 * @author bradley
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HierarchyItem extends HierarchyItemBase implements IHierarchyItem {

    private Vector cvector;
    private IHierarchyItem[] children = null;

	public HierarchyItem(){
		super();
		cvector = new Vector();
	}


    public HierarchyItem(int idNumb, String label){
    	super(idNumb, label);
    	cvector = new Vector();
    }

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#getChildren()
	 */
	public IHierarchyItem[] getChildren() {
		if(children == null)children = (IHierarchyItem[])cvector.toArray(new IHierarchyItem[0]);
		return children;
	}

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#hasChildren()
	 */
	public boolean hasChildren() {
		if(children == null)children = (IHierarchyItem[])cvector.toArray(new IHierarchyItem[0]);
		return children.length > 0;
	}
	
	public void addChild(IHierarchyItem item){
		cvector.add(item);
		children = null;
	}


	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#getNumbChildren()
	 */
	public int getNumbChildren() {
		if(children == null)children = (IHierarchyItem[])cvector.toArray();
		return children.length;
	}

}

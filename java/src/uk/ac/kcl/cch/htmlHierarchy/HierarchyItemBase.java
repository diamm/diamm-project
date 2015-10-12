/*
 * Created on 03-Dec-2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package uk.ac.kcl.cch.htmlHierarchy;

/**
 * @author bradley
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HierarchyItemBase implements IHierarchyItem {
	private int idNumb;
	private String label;
	
	public HierarchyItemBase(){
		idNumb = 0;
		label = "";
	}
	
	public HierarchyItemBase(int idNumb, String label){
		this.idNumb = idNumb;
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#getIDNumb()
	 */
	public int getIDNumb() {
		return idNumb;
	}
	
	public void setIDNumb(int idNumb){
		this.idNumb = idNumb;
	}

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#getLabel()
	 */
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#getChildren()
	 */
	public IHierarchyItem[] getChildren() {
		return new IHierarchyItem[0];
	}

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#hasChildren()
	 */
	public boolean hasChildren() {
		return false;
	}

	/* (non-Javadoc)
	 * @see uk.ac.kcl.cch.htmlHierarchy.IHierarchyItem#getNumbChildren()
	 */
	public int getNumbChildren() {
		return 0;
	}

}

/*
 * Created on 25-Nov-2004
 *
 * Created by: John Bradley
 */
package uk.ac.kcl.cch.htmlHierarchy;
import org.json.*;

/**
 * @author Bradley
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HierarchyLevel {
   private IHierarchyItem[] items;
   private HierarchyManager manager;
   private int indent = 0;

    public HierarchyLevel() {
    }

    public HierarchyLevel(IHierarchyItem item, HierarchyManager manager, int indent){
   	  items = item.getChildren();
   	  this.manager = manager;
   	  this.indent = indent;
   }
   
   public String display(){
	  StringBuffer buf = new StringBuffer();
	  String itemTextTemplate = new String(manager.getRsltTemplate());
	  itemTextTemplate = itemTextTemplate.replaceAll("&indent;",indent+"");
	  if(manager.getClassName() == null)itemTextTemplate = itemTextTemplate.replaceAll("&class;","");
	  else {
	    String className = manager.getClassName();
	    if(manager.getShowLevelInClass())className += ""+indent;
		itemTextTemplate = itemTextTemplate.replaceAll("&class;",className);
	  }
   	  for(int i = 0; i < items.length; i++){
   	  	 String itemText = new String(itemTextTemplate);
		 itemText = itemText.replaceAll("&label;",items[i].getLabel());
		 String contents = null;
		 if(items[i].hasChildren()){
		 	if(manager.itemIsOpen(items[i])){
				itemText = itemText.replaceAll("&navicon;",manager.getOpenedIcon());
				HierarchyLevel hl = new HierarchyLevel(items[i],manager,indent+1);
				contents = hl.display();
		    }else
			    itemText = itemText.replaceAll("&navicon;",manager.getClosedIcon());
			itemText = itemText.replaceAll("&itemKey;",""+items[i].getIDNumb());
			itemText = itemText.replaceAll("&openstr;",manager.getOpenString());
		 } else {
			itemText = itemText.replaceAll("&navicon;",manager.getEmptyIcon());
		 }
		 buf.append(itemText+"\n");
		 if(contents != null)buf.append(contents);
   	  }
   	  return new String(buf);
   }

    
}

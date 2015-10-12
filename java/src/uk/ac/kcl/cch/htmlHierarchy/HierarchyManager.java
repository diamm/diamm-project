/*
 * Created on 25-Nov-2004
 *
 * Created by: John Bradley
 */
package uk.ac.kcl.cch.htmlHierarchy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bradley
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HierarchyManager {
	private String openedIconTemplate = "<A HREF=\"&navURL;?opened=&openstr;&excld=&itemKey;#i&itemKey;\" NAME=\"i&itemKey;\"><IMG SRC=\"&urlPrefix;&closeIcon;\" BORDER=\"0\"></A>";
	private String closedIconTemplate = "<A HREF=\"&navURL;?opened=&openstr;&incld=&itemKey;#i&itemKey;\" NAME=\"i&itemKey;\"><IMG SRC=\"&urlPrefix;&openIcon;\" BORDER=\"0\"></A>";
	private String emptyIconTemplate = "<IMG SRC=\"&urlPrefix;&emptyIcon;\" BORDER=\"0\">";
	private String rsltTemplate = "<DIV style=\"margin-left: &indent;cm\"&class;>&navicon;&nbsp;&label;</DIV>";

    public String getRsltTemplate(){return this.rsltTemplate;}
    public void setRsltTemplate(String rt){rsltTemplate=rt;}
    
    private String openedIcon = null;
    private String closedIcon = null;
    private String emptyIcon = null;
    
    private String navURL = "navigator.jsp";
    private String urlPrefix = "";
    private String openImg = "Assets/i/open.png";
	private String closeImg = "Assets/i/closed.png";
	private String emptyImg = "Assets/i/open.png";
	
	private String openedItemsString = null;
	public String getOpenString(){return openedItemsString;}
    private Set openedItems = null;
    
    private HierarchyLevel root = null;
    public void setTopItem(IHierarchyItem item){
    	root = new HierarchyLevel(item, this, 0);
    }
    
    private String className=null;
    public String getClassName(){return className;}
    public void setClassName(String className){
    	this.className = className;
    }
    
    private boolean showLevelInClass = false;
    public boolean getShowLevelInClass(){return showLevelInClass;}
    public void setShowLevelInClass(boolean showLevelInClass){
    	this.showLevelInClass = showLevelInClass;
    }
    public HierarchyManager (){

    }
    
    public HierarchyManager (String jspName){
    	navURL = jspName;
    }
    
    public HierarchyManager (String jspName, HttpServletRequest request){
    	navURL = jspName;
		setOpenedItems(request);
    }
    
    public HierarchyManager (String jspName, HttpServletRequest request, IHierarchyItem item){
		navURL = jspName;
		setOpenedItems(request);
		setTopItem(item);
    }
    
    public void setNavURL(String val){
    	navURL = val;
    	openedIcon = null;
    	closedIcon = null;
    	emptyIcon = null;
    }
    
	public void setUrlPrefix(String val){
		urlPrefix = val;
		openedIcon = null;
		closedIcon = null;
		emptyIcon = null;
	}
    
	public void setOpenImg(String val){
		openImg = val;
		openedIcon = null;
	}
    
	public void setCloseImg(String val){
		closeImg = val;
		closedIcon = null;
	}
    
	public void setEmptyImg(String val){
		emptyImg = val;
		emptyIcon = null;
	}
    
    private String buildFromTemplate(String template){
    	String rslt = template.replaceAll("&navURL;",navURL);
    	rslt = rslt.replaceAll("&urlPrefix;", urlPrefix);
		rslt = rslt.replaceAll("&openIcon;", openImg);
		rslt = rslt.replaceAll("&closeIcon;", closeImg);
		rslt = rslt.replaceAll("&emptyIcon;", emptyImg);
		return rslt;
    }
    
    public String getOpenedIcon(){
    	if(openedIcon == null)openedIcon = buildFromTemplate(openedIconTemplate);
    	return openedIcon;
    }
    
	public String getClosedIcon(){
		if(closedIcon == null)closedIcon = buildFromTemplate(closedIconTemplate);
		return closedIcon;
	}
    
	public String getEmptyIcon(){
		if(emptyIcon == null){
			if(emptyImg == null) emptyIcon = "";
			else emptyIcon = buildFromTemplate(emptyIconTemplate);
		}
		return emptyIcon;
	}

	public void setOpenedItems(String opened, String incld, String excld){
		openedItems = new HashSet();
		Integer excldItem = new Integer(-1);
		if(excld != null) excldItem = new Integer(excld);
		if((opened != null) && (!opened.equals(""))){
		   String[] items = opened.split(",");
		   for(int i = 0; i < items.length; i++){
			   Integer num = new Integer(items[i]);
			   if(num.intValue() != excldItem.intValue())openedItems.add(num);
		   }
		}
		if(incld != null) openedItems.add(new Integer(incld));
		StringBuffer buf = new StringBuffer();
		String connector = "";
		Iterator it = openedItems.iterator();
		while(it.hasNext()){
			Integer itm = (Integer)it.next();
			buf.append(connector);
			connector = ",";
			buf.append(itm);
		}
		openedItemsString = new String(buf);
	}
	
	public void setOpenedItems(HttpServletRequest request){
		String opened = request.getParameter("opened");
		String incld = request.getParameter("incld");
		String excld = request.getParameter("excld");
		setOpenedItems(opened, incld, excld);
	}

	public boolean itemIsOpen(IHierarchyItem item){
        return openedItems.contains(new Integer(item.getIDNumb()));
	}
	
	public String display(){
		if(root == null)return "";
		return root.display();
	}

      
}

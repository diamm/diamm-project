package uk.ac.kcl.cch.XHtmlHierarchy;

import uk.ac.kcl.cch.htmlHierarchy.HierarchyManager;
import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;
//import uk.ac.kcl.cch.ocve2.Init;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.lang.reflect.Constructor;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 30-Jan-2008
 * Time: 12:13:22
 * This is an extension of JB's Html Hierarchy.  It uses the same interfaces, but builds the tree with xml elements
 * rather than strings
 */
public class XHtmlHierarchyManager extends HierarchyManager {

    public static String noIcon = "NOICON";

    protected String navURL = "navigator.jsp";
    protected String urlPrefix = "";
    protected String openImg = noIcon;
    protected String closeImg = noIcon;
    protected String emptyImg = noIcon;
    protected String triggerOpenClass = "t9 m1";
    protected String triggerClosedClass = "t9 m1";
    protected String rootElementName = "ul";
    protected String levelElementName = "ul";
    protected String itemElementName = "li";
    protected String rootClass = "nvh";
    protected String levelClass = "";
    protected String iteratorPrefix = "i";
    protected boolean useRoot=true;



    protected String itemClassUnloaded = "";
    protected String itemClassNoChildren = "";
    protected String itemClassHasChildrenOpen = "s2";
    protected String itemClassHasChildrenClosed = "s1";

    protected String triggerChild = "";
    protected String triggerTextOpen = "Close";
    protected String triggerTextClosed = "Open";
    protected String triggerTitleOpen = "click to close";
    protected String triggerTitleClosed = "click to open";
    protected String itemWrapper;
     protected String itemWrapperClass;
    protected String moreHref = "";

    protected String rsltTemplate = "&navicon;&nbsp;&label;";
    protected XHtmlHierarchyLevel root = null;
    protected IXHtmlHierarchyItem rootItem;
    protected Document doc;

    protected DocumentBuilderFactory dbfac;
    protected DocumentBuilder docBuilder;
    protected boolean showIcons=true;
    protected boolean async=false;
    protected String packString;

    public XHtmlHierarchyManager() {

    }

    public XHtmlHierarchyManager(String jspName) {
        super(jspName);
    }

    public XHtmlHierarchyManager(String jspName, HttpServletRequest request) {
        super(jspName, request);
    }

   

    /**
     * An asynchronouos implementation of the tree.  The tree includes the key of an item
     * in its link as the root, so that JSON branches of its children can be generated.
     * @param jspName jsp to link to
     * @param request
     * @param packString package of objects in tree i.e. uk.ac.kcl.cch.cvma
     * @throws XHtmlHierarchyException
     * @throws ParserConfigurationException
     */
    public XHtmlHierarchyManager(String jspName, HttpServletRequest request,String packString) throws XHtmlHierarchyException,ParserConfigurationException{
        super(jspName, request);
        async=true;
        this.packString=packString;
        navURL = jspName;
        /*setOpenedItems(request);
        setTopItem(item);*/
        dbfac = DocumentBuilderFactory.newInstance();
        //this.doc = doc;
        docBuilder = null;
        try {
            docBuilder = dbfac.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw e;//Init.logger.error(e); //To change body of catch statement use File | Settings | File Templates.
        }
        doc = docBuilder.newDocument();
        setOpenedItems(request);
        if (request.getParameter("root")!=null){
            IXHtmlHierarchyItem root=getIXHtmlHierarchyItemByID(this.packString,request.getParameter("root"));
            setTopItem(root);
        }  else{
            throw new XHtmlHierarchyException("Root ID not Passed");
        }
    }

    public XHtmlHierarchyManager(String jspName, HttpServletRequest request, IXHtmlHierarchyItem item) {
        super(jspName);
        navURL = jspName;
        /*setOpenedItems(request);
        setTopItem(item);*/
        dbfac = DocumentBuilderFactory.newInstance();
        //this.doc = doc;
        docBuilder = null;
        try {
            docBuilder = dbfac.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            //Init.logger.error(e); //To change body of catch statement use File | Settings | File Templates.
        }
        doc = docBuilder.newDocument();
        setOpenedItems(request);
        setTopItem(item);
    }

    public boolean isShowIcons() {
        return showIcons;
    }

    public void setShowIcons(boolean showIcons) {
        this.showIcons = showIcons;
    }
    //private static String

    public String getOpenTriggerClass() {
        return triggerOpenClass;
    }

    public void setOpenTriggerClass(String triggerClass) {
        this.triggerOpenClass = triggerClass;
    }

    public String getTriggerClass() {
        return triggerClosedClass;
    }

    public void setTriggerClass(String triggerClass) {
        this.triggerClosedClass = triggerClass;
    }

    public String getItemClassHasChildrenOpen() {
        return itemClassHasChildrenOpen;
    }

    public void setItemClassHasChildrenOpen(String itemClassHasChildrenOpen) {
        this.itemClassHasChildrenOpen = itemClassHasChildrenOpen;
    }

    public String getItemClassHasChildrenClosed() {
        return itemClassHasChildrenClosed;
    }

    public void setItemClassHasChildrenClosed(String itemClassHasChildrenClosed) {
        this.itemClassHasChildrenClosed = itemClassHasChildrenClosed;
    }

    public String getItemClassNoChildren() {
        return itemClassNoChildren;
    }

    public void setItemClassNoChildren(String itemClassNoChildren) {
        this.itemClassNoChildren = itemClassNoChildren;
    }

    protected String openedItemsString = null;

    public String getOpenString() {
        return openedItemsString;
    }

    protected Set openedItems = null;

    public String getRootClass() {
        return rootClass;
    }

    public void setRootClass(String rootClass) {
        this.rootClass = rootClass;
    }

    public String getLevelClass() {
        return levelClass;
    }

    public void setLevelClass(String levelClass) {
        this.levelClass = levelClass;
    }

    public String getRootElementName() {
        return rootElementName;
    }

    public void setRootElementName(String rootElementName) {
        this.rootElementName = rootElementName;
    }

    public String getLevelElementName() {
        return levelElementName;
    }

    public void setLevelElementName(String levelElementName) {
        this.levelElementName = levelElementName;
    }

    public String getItemElementName() {
        return itemElementName;
    }

    public void setItemElementName(String itemElementName) {
        this.itemElementName = itemElementName;
    }

    public String getTriggerTextOpen() {
        return triggerTextOpen;
    }

    public void setTriggerTextOpen(String triggerTextOpen) {
        this.triggerTextOpen = triggerTextOpen;
    }

    public String getTriggerTextClosed() {
        return triggerTextClosed;
    }

    public void setTriggerTextClosed(String triggerTextClosed) {
        this.triggerTextClosed = triggerTextClosed;
    }

    public String getTriggerTitleClosed() {
        return triggerTitleClosed;
    }

    public void setTriggerTitleClosed(String triggerTitleClosed) {
        this.triggerTitleClosed = triggerTitleClosed;
    }

    public String getTriggerTitleOpen() {
        return triggerTitleOpen;
    }

    public void setTriggerTitleOpen(String triggerTitleOpen) {
        this.triggerTitleOpen = triggerTitleOpen;
    }

    public String getTriggerChild() {
        return triggerChild;
    }

    public void setTriggerChild(String triggerChild) {
        this.triggerChild = triggerChild;
    }

    public String display() {
        if (root == null) return "";
        return root.display();               
    }

    public String getOpenImg() {
        return openImg;
    }

    public void setOpenImg(String openImg) {
        this.openImg = openImg;
    }

    public String getCloseImg() {
        return closeImg;
    }

    public void setCloseImg(String closeImg) {
        this.closeImg = closeImg;
    }

    public String getEmptyImg() {
        return emptyImg;
    }

    public void setEmptyImg(String emptyImg) {
        this.emptyImg = emptyImg;
    }

    public void  setTopItem(IXHtmlHierarchyItem item) {
        if (doc!=null){doc = docBuilder.newDocument();}
        rootItem=item;
        root = new XHtmlHierarchyLevel(item, this, 0, doc);
        root.setUseRoot(this.useRoot);
        if (itemWrapper!=null){root.setItemWrapper(itemWrapper);}
        if (itemWrapperClass!=null){root.setItemWrapperClass(itemWrapperClass);}
    }



    public void setOpenedItems(String opened, String incld, String excld) {
        openedItems = new HashSet();
        //Integer excldItem = new Integer(-1);
        if (excld == null) excld = "";
        if (opened == null) {
            opened = "";
        }
        if (!opened.equals("")) {
            String[] items = opened.split(",");
            for (int i = 0; i < items.length; i++) {
                if (items[i].compareTo(excld) != 0) openedItems.add(items[i]);
            }
        }
        if (incld != null) openedItems.add(incld);
        StringBuffer buf = new StringBuffer();
        String connector = "";
        Iterator it = openedItems.iterator();
        while (it.hasNext()) {
            String itm = (String) it.next();
            buf.append(connector);
            connector = ",";
            buf.append(itm);
        }
        openedItemsString = new String(buf);
    }

    public void setOpenedItems(HttpServletRequest request) {
        String opened = request.getParameter("opened");
        String incld = request.getParameter("incld");
        String excld = request.getParameter("excld");
        setOpenedItems(opened, incld, excld);
    }

    public boolean itemIsOpen(IXHtmlHierarchyItem item) {
        if (openedItems.contains("ALL")) {
            return true;
        }
        return openedItems.contains(item.getFullID());
    }

    public static IXHtmlHierarchyItem getIXHtmlHierarchyItemByID(String packString, String id) {
        String[] parts = id.split("_");
        Class<?> c = null;
        try {
            c = Class.forName(packString + "." + parts[0]);
            Constructor init = c.getConstructor(new Class[]{int.class});
            IXHtmlHierarchyItem I = (IXHtmlHierarchyItem) init.newInstance(Integer.parseInt(parts[1]));
            return I;
        } catch (Exception e) {
            //Init.logger.error(e);  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    protected Node getNavIcon(IXHtmlHierarchyItem item) {
        //Added by EH on 9/12/2008 to allow making icons invisible
        if (!isShowIcons()){return null;}
        Element link = doc.createElement("a");
        Element img = doc.createElement("img");
        String action = "";
        String id = item.getFullID();
        //add link attributes
        link.setAttribute("id", id);
        if (itemIsOpen(item)) {
           action = "&excld=" + id;
            link.setAttribute("class", triggerOpenClass);
                link.setAttribute("title", triggerTitleOpen);
        } else{
           action = "&incld=" + id;
            link.setAttribute("class", triggerClosedClass);
            link.setAttribute("title", triggerTitleClosed);
        }
        if (async){
            //Add item as root parameter
            // to only return its branch
            action+="&root="+item.getFullID();
        }        
        if (item.hasChildren()) {
            link.setAttribute("href", navURL + "?opened=" + getOpenString() + action + moreHref + "#" + id);
        } else{
            return null;
        }
        //Create icon
        if (closeImg != noIcon) {
            //Use images as clickable icons
            if (itemIsOpen(item)) {

                img.setAttribute("src", openImg);

            } else {
                img.setAttribute("src", closeImg);
            }
            img.setAttribute("border", "0");
            if (!item.hasChildren()) {
                img.setAttribute("src", emptyImg);
                if (emptyImg != noIcon) {
                    link = img;
                } else {
                    link = null;
                }
            } else {
//            img.setAttribute("class", triggerClass);
                if (img.getAttribute("src") != noIcon) {
                    //Check to see if icon is wanted
                    link.appendChild(img);
                }
            }
        } else {
            //The xMod case, where images are not used for icons
            Element linkChild = null;
            if (triggerChild.length()>0){
                linkChild=doc.createElement(triggerChild);
            }
            if (itemIsOpen(item)) {
                link.setAttribute("class", triggerOpenClass);
                link.setAttribute("title", triggerTitleOpen);
                if (linkChild!=null){
                    linkChild.appendChild(doc.createTextNode(triggerTextOpen));
                } else{
                    link.appendChild(doc.createTextNode(triggerTextOpen));
                }
            } else {
                link.setAttribute("class", triggerClosedClass);
                link.setAttribute("title", triggerTitleClosed);
                if (linkChild!=null){
                    linkChild.appendChild(doc.createTextNode(triggerTextClosed));
                }       else{
                    link.appendChild(doc.createTextNode(triggerTextClosed));
                }
            }
            if (linkChild!=null){
                link.appendChild(linkChild);
            }
        }
        return link;
    }

    protected String getFullID(IXHtmlHierarchyItem item) {
        String className = item.getClass().getName();
        className = className.substring(className.lastIndexOf(".") + 1);
        return className + "_" + item.getIDNumb();
    }

    public String displayASJSON() {
        if (root == null) return "";
        return root.displayAsJSON();
    }

    public String getMoreHref() {
        return moreHref;
    }

    /**
     * Add more get variables to be included in tree links.
     *
     * @param moreHref additional GET variables
     */
    public void setMoreHref(String moreHref) {
        this.moreHref = "&" + moreHref;
    }

    /**
     * Write the tree to a session, with attribute name
     *
     * @param session  user session
     * @param attrName the attribte name
     */
    public void serialize(String attrName, HttpSession session) {
        if (session != null) {
            session.setAttribute(attrName, this);
        }
    }

    public static XHtmlHierarchyManager loadFromSession(String attrName, HttpSession session, HttpServletRequest request) {
        if (session != null) {
            XHtmlHierarchyManager m = (XHtmlHierarchyManager) session.getAttribute(attrName);
            if (m != null) {
                m.setOpenedItems(request);
                return m;
            }
        }
        return null;
    }

    public IXHtmlHierarchyItem getRootItem() {
        return rootItem;
    }

    /*public void setRootItem(IXHtmlHierarchyItem rootItem) {
        this.rootItem = rootItem;
    }*/

    public String getItemWrapper() {
        return itemWrapper;
    }

    public void setItemWrapper(String itemWrapper) {
        this.itemWrapper = itemWrapper;
    }

    public String getItemWrapperClass() {
        return itemWrapperClass;
    }

    public void setItemWrapperClass(String itemWrapperClass) {
        this.itemWrapperClass = itemWrapperClass;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public String getPackString() {
        return packString;
    }

    public void setPackString(String packString) {
        this.packString = packString;
    }

    /**
     * This function returns a new manager object
     * open to display the node with fullID.
     * @param fullID id of leaf to which larger branch belongs
     * @return
     */
    public XHtmlHierarchyManager getBranch(String fullID) throws CloneNotSupportedException{
        XHtmlHierarchyManager newM=(XHtmlHierarchyManager) this.clone();
        IXHtmlHierarchyItem leaf=getIXHtmlHierarchyItemByID(packString,fullID);
        //If Item is already open, return clone of manager
        if (newM.itemIsOpen(leaf)){return newM;}
        IXHtmlHierarchyItem parent=leaf.getParent();
        return newM;
    }

    public boolean isUseRoot() {
        return useRoot;
    }

    public void setUseRoot(boolean useRoot) {
        this.useRoot = useRoot;
    }

    /* private String buildFromTemplate(String template) {
  String rslt = template.replaceAll("&navURL;", super.navURL);
  rslt = rslt.replaceAll("&urlPrefix;", urlPrefix);
  rslt = rslt.replaceAll("&openIcon;", openImg);
  rslt = rslt.replaceAll("&closeIcon;", closeImg);
  rslt = rslt.replaceAll("&emptyIcon;", emptyImg);
  return rslt;
}

public String getOpenedIcon() {
  if (openedIcon == null) openedIcon = buildFromTemplate(openedIconTemplate);
  return openedIcon;
}

public String getClosedIcon() {
  if (closedIcon == null) closedIcon = buildFromTemplate(closedIconTemplate);
  return closedIcon;
}

public String getEmptyIcon() {
  if (emptyIcon == null) {
      if (emptyImg == null) emptyIcon = "";
      else emptyIcon = buildFromTemplate(emptyIconTemplate);
  }
  return emptyIcon;
}     */

}

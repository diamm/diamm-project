package uk.ac.kcl.cch.XHtmlHierarchy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import uk.ac.kcl.cch.htmlHierarchy.HierarchyLevel;


import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 30-Jan-2008
 * Time: 12:22:28
 * To change this template use File | Settings | File Templates.
 */
public class XHtmlHierarchyLevel extends HierarchyLevel {
    private boolean useRoot;
    private Document doc;
    private IXHtmlHierarchyItem[] items;
    private XHtmlHierarchyManager manager;
    private int iterator;
    private int depth;
    private String itemWrapper;
    protected String itemWrapperClass;

    public int getDepth() {
        return depth;
    }

    /**
     * Sets the number of levels the tree should descend.
     * Default value is all.
     *
     * @param depth
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Include the root element in the iteration
     */
    public boolean isUseRoot() {
        return useRoot;
    }

    public void setUseRoot(boolean useRoot) {
        this.useRoot = useRoot;
    }

    public XHtmlHierarchyLevel(IXHtmlHierarchyItem item, XHtmlHierarchyManager manager, int iterator, Document doc) {
        items = item.getChildren();

        this.manager = manager;
        this.iterator = iterator;

        this.doc = doc;
        depth = -1;
    }


    public XHtmlHierarchyLevel(IXHtmlHierarchyItem item, XHtmlHierarchyManager manager, int iterator) {
        items = item.getChildren();
        this.manager = manager;
        this.iterator = iterator;

        depth = -1;
    }

    protected Element buildTree() {
        Element level = doc.createElement(manager.getLevelElementName());
        setLevelClass(level);
        if (isUseRoot()) {
            Element root = doc.createElement(manager.getRootElementName());
            root.appendChild(level);
            setRootClass(root);
            level=root;
        }

        for (int i = 0; i < items.length; i++) {
            Element item = doc.createElement(manager.getItemElementName());
            Element wrap=null;
            //Populate item with data.
            applyItemClass(item, items[i], i);
            item.setAttribute("id", items[i].getFullID());
            if (itemWrapper!=null){
                wrap=doc.createElement(itemWrapper);
                if (itemWrapperClass!=null){
                     wrap.setAttribute("class",itemWrapperClass);
                }
                item.appendChild(wrap);
            }
            Node icon = manager.getNavIcon(items[i]);
            if (icon != null) {
                if (wrap!=null){wrap.appendChild(icon);}
                else{item.appendChild(icon);}
            }
            if (wrap!=null){wrap.appendChild(buildItemContents(items[i]));}

            else{item.appendChild(buildItemContents(items[i]));}
            //Text text = doc.createTextNode(buildItemText(items[i]));
            //item.appendChild(text);
            //Parse Children
            if (items[i].hasChildren() && iterator > depth) {
                if (manager.itemIsOpen(items[i])) {
                    XHtmlHierarchyLevel hl = new XHtmlHierarchyLevel(items[i], manager, iterator + 1, doc);
                    if (itemWrapper!=null){
                        hl.setItemWrapper(itemWrapper);
                        if (itemWrapperClass!=null){hl.setItemWrapperClass(itemWrapperClass);}
                    }
                    Element children = hl.buildTree();
                    if (wrap!=null){wrap.appendChild(children);}
                    else{item.appendChild(children);}
                }
            }
                level.appendChild(item);
        }
        return level;
    }

    private Node buildItemContents(IXHtmlHierarchyItem item) {

        Text text = doc.createTextNode(item.getLabel());
        return text;
    }

    /*private String buildItemText(IXHtmlHierarchyItem item) {
        StringBuffer buf = new StringBuffer();
        String itemText = new String(manager.getRsltTemplate());
        itemText = itemText.replaceAll("&label;", item.getLabel());
        String contents = null;
        if (item.hasChildren()) {
            if (manager.itemIsOpen(item)) {
                itemText = itemText.replaceAll("&navicon;", manager.getOpenedIcon());
                //HierarchyLevel hl = new HierarchyLevel(item, manager, iterator + 1);
                //contents = hl.display();
            } else {
                itemText = itemText.replaceAll("&navicon;", manager.getClosedIcon());
            }
            itemText = itemText.replaceAll("&itemKey;", "" + item.getIDNumb());
            itemText = itemText.replaceAll("&openstr;", manager.getOpenString());
        } else {
            itemText = itemText.replaceAll("&navicon;", manager.getEmptyIcon());
        }
        buf.append(itemText);
        return new String(buf);
    }*/

    private void setRootClass(Element root) {
        if (manager.getRootClass().length() > 0) {
            root.setAttribute("class", manager.getRootClass());
        }
    }

    //    private void setLevelClass(Element level,boolean children,int position) {
    private void setLevelClass(Element level) {
        String levelClass="";
        if (manager.getLevelClass().length() > 0) {
            levelClass+=manager.getLevelClass();
        }
        if (items!=null&&items.length>0){
            if (items[0].getChildrenContainerClass()!=null&&items[0].getChildrenContainerClass().length()>0){
                 levelClass+=" "+items[0].getChildrenContainerClass();
            }
        }
        
        level.setAttribute("class", levelClass);
    }

    /**
     * Applies a css class to the item based on its position, whether it has
     * children, and whether it's open.  The different states are based on
     * xMod 1.3
     *
     * @param e        XHTML element
     * @param item
     * @param position item's relative position
     */
    private void applyItemClass(Element e, IXHtmlHierarchyItem item, int position) {
        String className = "";
        if (item.hasChildren()) {
            if (manager.itemIsOpen(item)) {
                className = manager.getItemClassHasChildrenOpen();
            } else {
                className = manager.getItemClassHasChildrenClosed();
            }
        } else {
            className = manager.getItemClassNoChildren();
        }
        if (item.getItemClass()!=null&&item.getItemClass().length()>0){
            if (className.length()>0){className=className+" ";}
            className=className+item.getItemClass();
        }
        if (className.length() > 0) {
            e.setAttribute("class", className);
        }
    }

    /**
     * Extra method added by Elliott Hall to return the tree as a JSON object.
     * Used for Ajax calls.
     *
     * @return
     */
    public String displayAsJSON() {
        JSONArray JSONS = new JSONArray();
        for (int i = 0; i < items.length; i++) {
            try {
                JSONObject result = new JSONObject();
                result.put("label", items[i].getLabel());
                result.put("key", items[i].getIDNumb());
                result.put("fullID", items[i].getFullID());
                result.put("hasChildren", items[i].hasChildren());
                JSONS.put(result);
                //Assume single level return only?
                /* if(items[i].hasChildren()){

                 }  */
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return JSONS.toString();
    }

    @Override
    public String display() {
        Element e = buildTree();
        doc.appendChild(e);
        String xmlString = "";
        try {
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            xmlString = sw.toString();
        } catch (TransformerException e1) {
            //Init.logger.error(e1);  //To change body of catch statement use File | Settings | File Templates.
        }
        xmlString = xmlString.replaceAll("&lt;", "<");
        xmlString = xmlString.replaceAll("&gt;", ">");
        xmlString = xmlString.replaceAll("&amp;", "&");
        return xmlString;
    }

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
}

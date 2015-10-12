package uk.ac.kcl.cch.diamm.tree;

import uk.ac.kcl.cch.XHtmlHierarchy.XHtmlHierarchyManager;
import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 31-Aug-2010
 * Time: 15:12:46
 * To change this template use File | Settings | File Templates.
 */
public class SourceFacetTreeManager extends XHtmlHierarchyManager {
    public SourceFacetTreeManager (String jspName, HttpServletRequest request, IXHtmlHierarchyItem item) {
        super(jspName);

        navURL = jspName;
        /*setOpenedItems(request);
        setTopItem(item);*/
        dbfac = DocumentBuilderFactory.newInstance();
        //this.doc = doc;
        //Set default html variables
       // this.itemWrapper="h5";
        this.useRoot=false;
        this.triggerOpenClass="t9 m0 s7";
        this.triggerClosedClass="t9 m0 s6";
        this.itemClassHasChildrenClosed="";
        this.itemClassHasChildrenOpen="";
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
}

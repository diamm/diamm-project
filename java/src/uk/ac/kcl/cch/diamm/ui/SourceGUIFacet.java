package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.XHtmlHierarchy.XHtmlHierarchyManager;
import uk.ac.kcl.cch.diamm.facet.SourceFacet;
import uk.ac.kcl.cch.diamm.facet.type.SourceFacetType;
import uk.ac.kcl.cch.diamm.tree.SourceFacetTreeManager;
import uk.ac.kcl.cch.diamm.tree.TreeRoot;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.ui.GUIFacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 05-Jul-2010
 * Time: 13:14:22
 * To change this template use File | Settings | File Templates.
 */
public class SourceGUIFacet extends GUIFacet {

    private static String currentIncldSessionName = "sourceIncld";
    private static String currentExcldSessionName = "sourceExcld";
    private static String currentOpenedSessionName = "sourceOpened";
    private static String sourceTreeSessionName = "sourceTree";

    public SourceGUIFacet(Facet f, GUIFacetManager g) {
        super(f, g);
    }

    /**
     * Add all countries that
     *
     * @param request
     */
    protected void addTree(HttpServletRequest request) {
        /*ArrayList<Integer> constraintKeys=DIAMMFacetServlet.getManager(request).getMasterKeyList(FacetType.valueOf(getFacetType()));
        String fullHql = "SELECT DISTINCT alcountry from Alcountry as alcountry";
        //addJoin("join photodata.intermedium as intermedium ");
        fullHql += " join alcountry.alcitiesByAlcountrykey as alcity";
        fullHql += " join alcity.archivesByAlcitykey as archive";
        fullHql += " join archive.sourcesByArchivekey as source";
        fullHql += " join source.itemsBySourcekey as item";
        //fullHql += " WHERE alcountry.alcountrykey=" + value;
        if (constraintKeys != null && constraintKeys.size() > 0) {
            fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
        }
        //fullHql = "SELECT DISTINCT item from Item as item limit 10";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Alcountry> countries = query.list();*/
        //TODO:  Order authority lists?
        //Applies the constraints of other facets to counts
        XHtmlHierarchyManager sourceTree = loadSourceTree(request);
        applyRequestVariables(request, sourceTree);
        request.setAttribute(Constants.sourceTreeAttrName, sourceTree.display());

    }
    /*
    if (request.getParameter("opened")==null&&(session.getAttribute(Constants.currentIncldSessionName)!=null||session.getAttribute(Constants.currentOpenedSessionName)!=null)){
            //Set tree from session
            String opened= session.getAttribute(Constants.currentOpenedSessionName)!=null? (String)session.getAttribute(Constants.currentOpenedSessionName):"";
            String incld= session.getAttribute(Constants.currentIncldSessionName)!=null? (String)session.getAttribute(Constants.currentIncldSessionName):"";
            String excld= session.getAttribute(Constants.currentExcldSessionName)!=null? (String)session.getAttribute(Constants.currentExcldSessionName):"";
            tree = new OCVETreeManager("tree.jsp", request, root,opened,incld,excld);
            //tree.setOpenedItems(opened,incld,excld);
        }else{
            session.setAttribute("OCVETree",tree);
            if (request.getParameter("opened")!=null){
                session.setAttribute(Constants.currentOpenedSessionName,(String)request.getParameter("opened"));
            }
            if (request.getParameter("incld")!=null){
                session.setAttribute(Constants.currentIncldSessionName,(String)request.getParameter("incld"));
            }
            if (request.getParameter("excld")!=null){
                session.setAttribute(Constants.currentExcldSessionName,(String)request.getParameter("excld"));
            }
        }
     */


    public boolean applyRequestVariables(HttpServletRequest request, XHtmlHierarchyManager tree) {
        HttpSession session = request.getSession();
        boolean changed = false;
        if (request.getParameter("opened") != null) {
            session.setAttribute(currentOpenedSessionName, (String) request.getParameter("opened"));
            changed = true;
        }
        if (request.getParameter("incld") != null) {
            session.setAttribute(currentIncldSessionName, (String) request.getParameter("incld"));
            changed = true;
        }
        if (request.getParameter("excld") != null) {
            session.setAttribute(currentExcldSessionName, (String) request.getParameter("excld"));
            changed = true;
        }
        if (request.getParameter("resetGuiFacet") != null && request.getParameter("resetGuiFacet").equals("COMPOSERFACET")) {
            //Reset
            session.removeAttribute(currentExcldSessionName);
            session.removeAttribute(currentOpenedSessionName);
            session.removeAttribute(currentIncldSessionName);

            //a search string was used for composer
        } else if (changed) {
            tree.setOpenedItems(request);
        } else
        if (session.getAttribute(currentIncldSessionName) != null || session.getAttribute(currentOpenedSessionName) != null) {
            String opened = "";
            String incld = "";
            String excld = "";
            if (session.getAttribute(currentOpenedSessionName) != null) {
                opened = (String) session.getAttribute(currentOpenedSessionName);
            }
            if (session.getAttribute(currentIncldSessionName) != null) {
                incld = (String) session.getAttribute(currentIncldSessionName);
            }
            if (session.getAttribute(currentExcldSessionName) != null) {
                excld = (String) session.getAttribute(currentExcldSessionName);
            }
            tree.setOpenedItems(opened, incld, excld);
        }
        if (changed) {
            //todo:Bad Workaround?
            getGUIManager().setOpenFacet(new SourceFacetType(), request);
        }
        return changed;
    }

    /**
     * Clears teh session and loads a new tree
     *
     * @param request
     */
    public void resetSourceTree(HttpServletRequest request) {
        //Clear session variables
        HttpSession session = request.getSession();
        session.removeAttribute(currentExcldSessionName);
        session.removeAttribute(currentIncldSessionName);
        session.removeAttribute(currentOpenedSessionName);
        session.removeAttribute(sourceTreeSessionName);
    }

    /**
     * Returns a source tree in the session or a new one if none exists.
     *
     * @param request
     * @return
     */
    public XHtmlHierarchyManager loadSourceTree(HttpServletRequest request) {
        XHtmlHierarchyManager sourceTree = null;
        /* if (request.getSession().getAttribute(sourceTreeSessionName) != null) {
          sourceTree=(XHtmlHierarchyManager) request.getSession().getAttribute(sourceTreeSessionName);
      } else {*/
        //Instantiate new tree
        ArrayList<Integer> constraintKeys = getFacet().getManager().getMasterKeyList(getFacet().getType());
        sourceTree = new SourceFacetTreeManager("FacetManager", request, new TreeRoot(constraintKeys));
        /*sourceTree.setCloseImg();
        sourceTree.setOpenImg();
        */
        //}
        return sourceTree;
    }

    public void saveSourceTree(HttpServletRequest request, XHtmlHierarchyManager tree) {
        if (request.getSession() != null) {
            request.getSession().setAttribute(sourceTreeSessionName, tree);
        }
    }


    protected void addFacetData(HttpServletRequest request) {
        if (getGUIstate() == GUIFacetState.OPEN) {
            addTree(request);
        }
        if (getLabel() != null && getLabel().length() > 0) {
            request.setAttribute("sourceLabel", getLabel());
        }
        //If the facet is selected, add the key to request
        if (getFacet().getState() != FacetState.EMPTY) {
            request.setAttribute("SOURCEFACETSELECTED", getFacet().getValue());
        }
    }

    public boolean updateFacetFromRequest(HttpServletRequest request) {
        boolean changed = getFacet().updateConstraintsFromRequest(request);

        return changed;
    }

    public String getDescription() {

        if (getFacet().getValue() > 0) {

            switch (getLevel()) {
                case 1:
                    //city
                    return getDescription("Country");
                case 2:
                    //city
                    return getDescription("City");

                case 3:
                    //archive
                    return getDescription("ARCHIVE");
                case 4:
                    //source
                    return getDescription("Source");
            }
        }
        return "";
    }

    private String getDescription(String label) {
        //Country selected, acts like normal facet at top level
        uk.ac.kcl.cch.facet.ui.FacetCriterion fc = getFacet().getCurrentCriterion();

        return " <li> <label> " + label +":"+fc.getLabel()+ "</label>\n"+
                "<a class=\"t9 m3\" href=\"FacetManager?op=4&FacetType=SOURCEFACET\">Remove</a></li>";

        //"<span>" + fc.getLabel() + "<a href=\"FacetManager?op=4&FacetType=SOURCEFACET\">X</a></span>";
    }


    public String getFacetType() {
        return new SourceFacetType().getTypeName();
    }

    public String getLabel() {
        FacetCriterion fc = getFacet().getCurrentCriterion();
        String l = "";
        if (fc != null) {
            l = fc.getLabel();
        }
        return l;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static int getCountryCount(int key) {
        return getCountByKey(key, SourceFacet.countryValues);
    }

    public static int getCityCount(int key) {
        return getCountByKey(key, SourceFacet.cityValues);
    }

    public static int getArchiveCount(int key) {
        return getCountByKey(key, SourceFacet.archiveValues);
    }

    public static int getSourceCount(int key) {
        if (SourceFacet.getSourceValue(key) != null) {
            return SourceFacet.getSourceValue(key).getCount();
        }
        return 0;
    }

    public static int getCountByKey(int key, List<FacetCriterion> values) {
        for (int i = 0; i < values.size(); i++) {
            FacetCriterion facetCriterion = values.get(i);
            if (facetCriterion.getKey() == key) {
                return facetCriterion.getCount();
            }
        }
        return 0;
    }
}

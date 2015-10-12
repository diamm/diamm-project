package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Property;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.diamm.ui.*;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.FacetType;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 07-Jun-2010
 * Time: 16:23:56
 * To change this template use File | Settings | File Templates.
 * INSERT INTO Item (itemKey,sourceKey,compositionKey,label) VALUES (3,830,1,'Test 3');
 * INSERT INTO alCity (alCityKey,alCountryKey,city) VALUES (3,10,'Blah 1')
 * INSERT INTO ARCHIVE (archiveKey,alCityKey,archiveName) VALUES (4,3,'D')
 * <p/>
 * DELETE FROM diamm_ess.alAffiliation;
 * DELETE FROM diamm_ess.alAuthor;
 * DELETE FROM diamm_ess.alCity;
 * DELETE FROM diamm_ess.alClef;
 * DELETE FROM diamm_ess.alCopyist;
 * DELETE FROM diamm_ess.alCopyistType;
 * DELETE FROM diamm_ess.alCountry;
 * DELETE FROM diamm_ess.alGenre;
 * DELETE FROM diamm_ess.alLanguage;
 * DELETE FROM diamm_ess.alMensuration;
 * DELETE FROM diamm_ess.alNotationType;
 * DELETE FROM diamm_ess.alPerson;
 * DELETE FROM diamm_ess.alProvenance;
 * DELETE FROM diamm_ess.alSetType;
 * DELETE FROM diamm_ess.alTextNotationType;
 * DELETE FROM diamm_ess.alVoice;
 * DELETE FROM diamm_ess.Composition;
 * DELETE FROM diamm_ess.Composer;
 * DELETE FROM diamm_ess.CompositionModel;
 * DELETE FROM diamm_ess.Bibliography;
 * DELETE FROM diamm_ess.CatalogueIndex;
 * DELETE FROM diamm_ess.ARCHIVE;
 * DELETE FROM diamm_ess.Source;
 * DELETE FROM diamm_ess.Item;
 * DELETE FROM diamm_ess.Image;
 * DELETE FROM diamm_ess.AuthorBibliography;
 * DELETE FROM diamm_ess.CompositionComposer;
 * DELETE FROM diamm_ess.CompositionGenre;
 * DELETE FROM diamm_ess.ItemImage;
 * DELETE FROM diamm_ess.NotationSource;
 * DELETE FROM diamm_ess.NoteImage;
 * DELETE FROM diamm_ess.NoteSource;
 * DELETE FROM diamm_ess.SourceAlPerson;
 * DELETE FROM diamm_ess.SourceCopyist;
 * DELETE FROM diamm_ess.SourceProvenance;
 * DELETE FROM diamm_ess.SourceSet;
 * DELETE FROM diamm_ess.`Text`;
 * DELETE FROM diamm_ess.TextLanguage;
 * DELETE FROM diamm_ess.TextModel;
 * DELETE FROM diamm_ess.TextNotationItem;
 * <p/>
 * Reset hasImages:
 * update diamm_ess.Item set hasImages=1 where exists (select Image.* from Image,ItemImage where Image.availWebsite='Y' and Image.imageKey=ItemImage.imageKey and ItemImage.itemKey=Item.itemKey)
 * <p/>
 * EMmpty alist values:
 * INSERT INTO diamm_ess.alAffiliation VALUES (0,'none');
 * <p/>
 * Fix strange denmar/bangor problem
 * update diamm_ess.FacetCriterionEntity set facetType=13 where label='Copenhagen';
 * update diamm_ess.FacetCriterionEntity set facetType=13 where label='Bangor'
 */
public class DIAMMFacetServlet extends HttpServlet {



    /**
     * One-time function to fix spaces in old usernames
     */
    public static void fixUsernames() {
        try {
            HibernateUtil.beginTransaction();
            ArrayList<DiammUser> user = (ArrayList<DiammUser>) HibernateUtil.getSession().createCriteria(DiammUser.class).list();
            for (int i = 0; i < user.size(); i++) {
                DiammUser u = user.get(i);
                u.getUsername();

            }
        } finally {

            HibernateUtil.commitTransaction();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter(Constants.opParameter);
        String page = "";
        int operation = 0;
        //verifyImages();
        //fixUsernames();
        if (op != null && op.length() > 0) {
            try {
                operation = Integer.parseInt(op);
            } catch (NumberFormatException ne) {

            }
        } else {
            //default
            operation = 1;
        }

        try {
            HibernateUtil.beginTransaction();
            //dbAnalysis();
            //operation=0;
            //Text t= (Text) HibernateUtil.getSession().get(Text.class,25376);
            switch (operation) {
                case ServletOpTypes.INIT:
                    page = initWindow(request);
                    break;
                case ServletOpTypes.UPDATEFACET:
                    page = updateFacetFromRequest(request);
                    break;
                case ServletOpTypes.CURRENTITEMLIST:
                    page = getCurrentItemView(request);
                    break;
                case ServletOpTypes.RESETFACET:
                    String fType = request.getParameter(Constants.facetTypeParameter);
                    if (fType != null) {
                        getManager(request).getFacetByType(fType).resetFacet();
                    } else {
                        resetWindow(request);
                        // page = initWindow(request);
                    }
                    //Reset Pagination
                    getManager(request).setCurrentPage(1, request);
                    break;
                case ServletOpTypes.DETAILPAGE:
                    setDetailPage(request);
                    break;
                case ServletOpTypes.OPTIONS:
                    updateOtherOptions(request);
                case ServletOpTypes.TOGGLESTATE:
                    fType = request.getParameter(Constants.facetTypeParameter);
                    FacetType type = getManager(request).getFacetType(fType);
                    if (getManager(request).getFacetByType(type) == null) {
                        addFacet(request, type);
                    }
                    GUIFacet gf = getGUIManager(request).getGUIFacetByType(getManager(request).getFacetType(fType));
                    if (gf.getGUIstate() == GUIFacetState.OPEN) {
                        gf.setGUIstate(GUIFacetState.CLOSED);
                        request.getSession().removeAttribute(Constants.openFacet);
                    } else {
                        getGUIManager(request).closeAllFacets();
                        gf.setGUIstate(GUIFacetState.OPEN);
                    }
                    break;
            }
            if (Constants.sync && operation != ServletOpTypes.INIT && operation != ServletOpTypes.SOURCEPAGE) {
                page = initWindow(request);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
        }
        if (operation != ServletOpTypes.SOURCEPAGE) {
            getManager(request).resetFacetStates();
        }
        //Add the dynamic menu from Cocoon
        addMenu(request, "facetmanager");
        response.setContentType("text/html");
        RequestDispatcher reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);
        reqDisp.include(request, response);
        DIAMMFacetManager.setPreviousUrl(request, "FacetManager");

    }

    public Facet addFacet(HttpServletRequest request, FacetType type) {
        DIAMMFacetManager m = getManager(request);
        Facet f = m.getNewFacet(type);
        m.addFacet(f);
        GUIFacet gf = getGUIManager(request).getNewGUIFacet(f);
        getGUIManager(request).addGuiFacet(gf);
        setManager(request, m);
        return f;
    }

    public void dbAnalysis() {
        //sourceDiagnostic();
        // itemDiagnostic();
        // personDiagnostic();
        textDiagnostic();
    }

    protected static String getMenu(HttpServletRequest request, String page) {
        String menu = "";
        try {
            String newURL = "http://localhost:" + request.getServerPort() + "" + request.getContextPath() + "/_xmod/menu?filedir=jsp&filename=" + page;
            //System.out.print(newURL);
            URL url = new URL(newURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                menu += line;
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return menu;
    }

    protected static void addMenu(HttpServletRequest request, String page) {
        String menu = getMenu(request, page);
        request.setAttribute("menu", menu);
    }

    public void sourceDiagnostic() {
        String sql = "select max(source.sourceKey) from Source as source";
        Query maxQ = HibernateUtil.getSession().createSQLQuery(sql);
        List l = maxQ.list();
        Integer max = (Integer) l.get(0);
        for (int i = 0; i < max; i++) {
            Source s = (Source) HibernateUtil.getSession().get(Source.class, i);
            if (s != null) {
                System.out.println("Source " + s.getSourcekey());
            }
        }
    }

    public void itemDiagnostic() {
        String sql = "select max(item.itemKey) from Item as item";
        Query maxQ = HibernateUtil.getSession().createSQLQuery(sql);
        List l = maxQ.list();
        Integer max = (Integer) l.get(0);
        for (int i = 33000; i < max; i++) {
            Item s = (Item) HibernateUtil.getSession().get(Item.class, i);
            if (s != null) {
                System.out.println("Item " + i);
            }
            s = null;
        }
    }

    public void textDiagnostic() {
        String sql = "select max(text.textKey) from Text as text";
        Query maxQ = HibernateUtil.getSession().createSQLQuery(sql);
        List l = maxQ.list();
        Integer max = (Integer) l.get(0);
        for (int i = 20000; i < max; i++) {
            Text s = (Text) HibernateUtil.getSession().get(Text.class, i);
            if (s != null) {
                System.out.println("Text " + i);
            }
            s = null;
        }
    }

    public void personDiagnostic() {
        //Test affiliation
        List<Alaffiliation> affs = HibernateUtil.getSession().createCriteria(Alaffiliation.class).addOrder(Property.forName("alaffiliationkey").asc()).list();
        for (int i = 0; i < affs.size(); i++) {
            Alaffiliation alaffiliation = affs.get(i);
            System.out.println("affiliation " + alaffiliation.getAlaffiliationkey());
        }
        String sql = "select max(alperson.alPersonKey) from alPerson as alperson";
        Query maxQ = HibernateUtil.getSession().createSQLQuery(sql);
        List l = maxQ.list();
        Integer max = (Integer) l.get(0);
        for (int i = 0; i < max; i++) {
            Alperson s = (Alperson) HibernateUtil.getSession().get(Alperson.class, i);
            if (s != null) {
                System.out.println("AlPerson " + i);
            }
            s = null;
        }
    }

    public void updateOtherOptions(HttpServletRequest request) {
        getManager(request).setImageOnly(request);
    }


    public void resetWindow(HttpServletRequest request) {
        //Reset facets
        resetAllFacets(request);
        //Other options
        resetAllOptions(request);
        //Reset grouping
        getManager(request).setGroupType(Constants.defaultGroupType, request);
        //Reset Pagination
        getManager(request).setCurrentPage(1, request);
    }

    /**
     * Find item key passed in request
     * and add it as DisplayItem to vars
     *
     * @param request
     */
    public void setDetailPage(HttpServletRequest request) {
        String ik = request.getParameter(Constants.detailItemParameter);
        if (ik != null) {
            Item i = (Item) HibernateUtil.getSession().load(Item.class, new Integer(ik));
            if (i != null) {
                DisplayItem di = new DisplayItem(i);
                di.addDetailData();
                di.addFullItemData();
                request.setAttribute(Constants.detailItemName, di);
            }
        }
        if (request.getParameter(Constants.currentDetailTabName) != null) {
            //Set currently open tab
            String tabid = request.getParameter(Constants.currentDetailTabName);
            request.setAttribute(Constants.currentDetailTabSessionName, tabid);
        }
    }

    public void resetAllOptions(HttpServletRequest request) {
        getManager(request).setImageOnly(0, request);
    }

    public void resetAllFacets(HttpServletRequest request) {
        Iterator i = getManager(request).getFacets().values().iterator();
        while (i.hasNext()) {
            Facet f = (Facet) i.next();
            f.resetFacet();
        }
    }

    /**
     * Returns the item view with the current list as defined by facets
     *
     * @param request
     * @return
     */
    public String getCurrentItemView(HttpServletRequest request) {
        DIAMMFacetManager m = getManager(request);
        m.setCurrentPageFromRequest(request);
        m.setRowsPerPage(request);
        List<ItemGroup> groups = null;
        if (getManager(request).isUpdateItemView() || request.getSession().getAttribute(Constants.groupItemsSessionName) == null) {
            groups = m.getGroupedItems(request);
            getManager(request).setUpdateItemView(false);
            //Instantiate needed objects
            for (int i = 0; i < groups.size(); i++) {
                ItemGroup itemGroup = groups.get(i);
                List<DisplayItem> items = itemGroup.getItemList();
                for (int j = 0; j < items.size(); j++) {
                    DisplayItem displayItem = items.get(j);
                    displayItem.addDetailData();
                }
            }
            request.getSession().setAttribute(Constants.groupItemsSessionName, groups);
        } else {
            groups = (List<ItemGroup>) request.getSession().getAttribute(Constants.groupItemsSessionName);
        }

        request.setAttribute(Constants.itemCountAttrName, m.getResultCount(request));
        request.setAttribute(Constants.itemAttrName, groups);
        request.setAttribute(Constants.groupAttrName, getManager(request).getGroupType(request).toString());
        setPagiantion(request);
        return Constants.itemFacetPage;
    }

    public void setPagiantion(HttpServletRequest request) {
        DIAMMFacetManager m = getManager(request);
        int pageNumber = m.getCurrentPage(request);
        Integer total = new Integer(m.getResultCount(request).intValue());
        PageNavigation pn = new PageNavigation(m.getResultsPerPage(request), total, Constants.defaulPageRange, pageNumber);
        request.setAttribute(Constants.paginationAttrName, pn);
    }


    public void setAllOpen(HttpServletRequest request) {
        ArrayList<FacetType> types = getManager(request).getTypes();
        for (int i = 0; i < types.size(); i++) {
            FacetType facetType = types.get(i);
            getGUIManager(request).getGUIFacetByType(facetType).setGUIstate(GUIFacetState.OPEN);
        }
    }

    /**
     * A full initialization of the faceted search.
     *
     * @param request
     * @return page
     */
    protected String initWindow(HttpServletRequest request) {
        //DEBUG only
        //setAllOpen(request);
        //Parse any request variables in the inital GET string
        boolean changed = getManager(request).updateAllFacetsFromRequest(request);
        updateOtherOptions(request);
        if (changed || request.getParameter(Constants.groupTypeParameter) != null) {
            //reset pagination
            getManager(request).setCurrentPage(1, request);
            getManager(request).setUpdateItemView(true);
        }
        String page = getGUIManager(request).instantiateCurrentView(request);
        //Apply current result set
        Enumeration facets = getManager(request).getFacets().elements();
        boolean addItems = false;
        while (facets.hasMoreElements()) {
            Facet f = (Facet) facets.nextElement();
            if (f.getState() != FacetState.EMPTY && (f.getValue() > 0)) {
                addItems = true;
            } else {
                int stop = 0;
            }
        }
        if (addItems) {
            getCurrentItemView(request);
        } else {
            //Necessary blank elements
            request.setAttribute(Constants.itemCountAttrName, 0);
            request.setAttribute(Constants.groupAttrName, getManager(request).getGroupType(request).toString());
            /*PageNavigation pn = new PageNavigation(0, 0, 0, 0);
            request.setAttribute(Constants.paginationAttrName, pn);*/
        }
        //Add other option toggles
        //addOtherParams(request);
        return page;
    }


    public DIAMMFacetManager getManager(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (session.getAttribute(Constants.facetManagerSessionName) != null) {
                return (DIAMMFacetManager) session.getAttribute(Constants.facetManagerSessionName);
            } else {
                DIAMMFacetManager m = null;
                //Ensure persistence by writing to servlet context
                /* if (session.getServletContext().getAttribute("tetheredManager")!=null){
                m=(DIAMMFacetManager) session.getServletContext().getAttribute("tetheredManager");
            }else{
                m=new DIAMMFacetManager();
                session.getServletContext().setAttribute("tetheredManager",m);
            }    */
                m = new DIAMMFacetManager();
                setManager(request, m);
                return m;
            }
        }
        return null;
    }

    public DIAMMGUIFacetManager getGUIManager(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (session.getAttribute(Constants.GUIManagerSessionName) != null) {
                return (DIAMMGUIFacetManager) session.getAttribute(Constants.GUIManagerSessionName);
            } else {
                DIAMMFacetManager m = getManager(request);
                DIAMMGUIFacetManager gm = new DIAMMGUIFacetManager(m, Constants.windowPage);
                session.setAttribute(Constants.GUIManagerSessionName, gm);
                return gm;
            }
        }
        return null;
    }

    public static void setManager(HttpServletRequest request, DIAMMFacetManager m) {
        HttpSession session = request.getSession();
        if (session != null && m != null) {
            session.setAttribute(Constants.facetManagerSessionName, m);
        }
    }

    /**
     * Update a Facet with variables from request
     *
     * @param request
     * @return
     */
    protected String updateAllFacetsFromRequest(HttpServletRequest request) {
        String page = "";
        DIAMMFacetManager m = getManager(request);
        m.updateAllFacetsFromRequest(request);
        return page;
    }

    /**
     * Checks the request for redraw requests from client side
     *
     * @param request
     * @return facets to be redrawn
     */
    protected ArrayList<FacetType> getReDrawFacetRequests(HttpServletRequest request) {
        String[] rds = request.getParameterValues(Constants.redrawParameter);
        if (rds != null && rds.length > 0) {
            ArrayList<FacetType> redraws = new ArrayList<FacetType>(rds.length);
            for (int i = 0; i < rds.length; i++) {
                String rd = rds[i];
                FacetType ft = getManager(request).getFacetType(rd);
                if (rd != null) {
                    redraws.add(ft);
                }
            }
            return redraws;
        }
        return null;
    }

    /**
     * Update a Facet with variables from request
     *
     * @param request
     * @return
     */
    protected String updateFacetFromRequest(HttpServletRequest request) {
        String page = "";
        DIAMMFacetManager m = getManager(request);
        String fType = request.getParameter(Constants.facetTypeParameter);
        boolean updated = false;
        Facet f = null;
        if (fType != null) {
            f = m.getFacetByType(fType);
            if (f != null) {
                updated = f.updateConstraintsFromRequest(request);
            }
        }
        if (updated) {
            //Reset Pagination
            getManager(request).setCurrentPage(1, request);

        } else {

        }

        return page;
    }

    /**
     * Update all Facets from request.
     *
     * @param request
     */
    protected void updateFacetsFromRequest(HttpServletRequest request) {
        String opType = request.getParameter(Constants.facetTypeParameter);

    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

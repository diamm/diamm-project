package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.NoteDAO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Image;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.diamm.ui.NoteDisplayItem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 08/04/11
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class DIAMMPublishingServlet extends HttpServlet {
    public static final int maxRecentImages = 5;
    public static final int maxRecentComments = 5;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    protected void process(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        String op = request.getParameter(Constants.opParameter);
        String page = "";
        int operation = 0;
        if (op != null && op.length() > 0) {
            operation = Integer.valueOf(op);
        }
        try {
            HibernateUtil.beginTransaction();
            switch (operation) {
                case 1:
                    //Recent images
                    page = getRecentImages(request);
                    break;
                case 2:
                    //Recent comments
                    page = getRecentComments(request);
                    break;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
        }
        DIAMMFacetServlet.addMenu(request, "facetmanager");
        RequestDispatcher reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);
        reqDisp.forward(request, resp);
    }

    public String getRecentImages(HttpServletRequest request) {
        Integer[] keyArray={57,136,151,158,153};
        List<Image> images = HibernateUtil.getSession().createCriteria(Image.class).add(Restrictions.eq("availwebsite", "Y"))
                .add(Restrictions.in("imagekey",keyArray))
                .addOrder(Property.forName("orderno").desc()).setMaxResults(maxRecentImages).list();

        for (int i = 0; i < images.size(); i++) {
            //Populate objects
            Image image =  images.get(i);
            image.getSourceBySourcekey().getArchiveByArchivekey();
        }
        if (images != null && images.size() > 0) {
            request.setAttribute("recentImages", images);
        }
        return "publish.jsp";
    }

    public String getRecentComments(HttpServletRequest request) {
        //most recent public notes
       DAOFactory factory = DAOFactory.getFactory();
	    NoteDAO noteDao = factory.getNoteDAO();
        List<NoteDisplayItem> recentComments=noteDao.findRecentPublicImageNotes(maxRecentComments);
        if (recentComments!=null&&recentComments.size()>0){
            request.setAttribute("recentComments",recentComments);
        }
        return "publish.jsp";
    }
}

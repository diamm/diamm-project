package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.DiammUser;
import uk.ac.kcl.cch.diamm.model.Note;
import uk.ac.kcl.cch.diamm.ui.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 28/11/11
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class MyDIAMMServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            //transaction.begin();
            String page = "myDiamm.jsp";
             DiammUser user = UserAccessServlet.getCurrentUser(req.getRemoteUser());
            HibernateUtil.beginTransaction();
            DIAMMFacetServlet.addMenu(req, "facetmanager");
            ArrayList<Note> pbNotes = new ArrayList<Note>();
            ArrayList<Note> pvNotes = new ArrayList<Note>();
            if (user != null) {
                ArrayList<Note> notes = (ArrayList<Note>) HibernateUtil.getSession().createCriteria(Note.class)
                .add(Restrictions.eq("user", user)).list();
                for (int i = 0; i < notes.size(); i++) {
                    Note note = notes.get(i);
                    //Initialize links
                    note.getNoteImage();
                    note.getNoteSource();
                    note.getNoteVisibility();
                    if (note.getNoteSource()!=null){
                        note.getNoteSource().getArchiveByArchivekey().getAlcityByAlcitykey();
                    }
                    /*if (note.getNoteVisibility().getId() == 1) {
                        pvNotes.add(note);
                    } else {
                        pbNotes.add(note);
                    }*/
                }
                /*req.setAttribute("pbCommList",pbNotes);
                req.setAttribute("pvCommList",pvNotes);*/
                req.setAttribute("commList", notes);

            }
            RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);

            HibernateUtil.commitTransaction();
            reqDisp.forward(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            //transaction.rollback();
            HibernateUtil.rollbackTransaction();
        }
    }

}

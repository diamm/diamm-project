package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.action.*;
import uk.ac.kcl.cch.diamm.ImageSearch;
import uk.ac.kcl.cch.diamm.dao.*;
import uk.ac.kcl.cch.diamm.hibernate.HibernateNoteDAO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.diamm.model.CollectionAction;
import uk.ac.kcl.cch.diamm.model.NoteVisibility.NoteVisibilityEnum;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;
import uk.ac.kcl.cch.diamm.ui.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DIAMMAnnotationServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    /**Any extra parsing for text.  Right now used to convert URLs
     *
     * @param noteDetails
     */
    private void notePreProcess(NoteDTO noteDetails){
        if (noteDetails.getNoteText()!=null&&noteDetails.getNoteText().indexOf("http://")>-1){
            //Convert to link
            noteDetails.setNoteText(noteDetails.getNoteText().replaceAll(".*[^\\\"](http:\\/\\/.+?)[^\\:\\?\\=\\&\\w\\.\\/].*","<a href=\"$1\">$1</a>"));
            /*Pattern p = Pattern.compile(".*(http:\\/\\/.+?)\\s*.*");
    	    Matcher m = p.matcher(noteDetails.getNoteText());
            if (m.matches()){
                String url=m.group(1);
                   noteDetails.getNoteText().replaceAll(url,);
            }*/

        }
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
       // Transaction transaction = TransactionFactory.getTransaction();
        try {
            //transaction.begin();
            HibernateUtil.beginTransaction();
            DAOFactory factory = DAOFactory.getFactory();
            String page = "imageViewer.jsp";
            NoteDTO noteDetails = getNoteParams(req);
            if (noteDetails.getOp() != null && noteDetails.getOp().equalsIgnoreCase("updateNote")) {
                notePreProcess(noteDetails);
                updateNote(noteDetails);
            }
            if (noteDetails.getOp() != null && noteDetails.getOp().equalsIgnoreCase("createNote")) {
                notePreProcess(noteDetails);
                createNote(noteDetails);
            } else if (noteDetails.getOp() != null && noteDetails.getOp().equalsIgnoreCase("deleteNote")) {
                deleteNote(noteDetails);
            }

            if (noteDetails.getImageKey() != null) {
                addImageRequestVariables(noteDetails, req);
            } else{
                //Source note, change redirect
                page="Descriptions?op=SOURCE&sourceKey="+noteDetails.getSourceKey();
            }
            DIAMMFacetServlet.addMenu(req, "facetmanager");
            RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);
            //transaction.commit();
            HibernateUtil.commitTransaction();
            reqDisp.forward(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            //transaction.rollback();
            HibernateUtil.rollbackTransaction();
        }
    }

    private static void createNote(NoteDTO noteDetails) {
        AnnotationAction action = new AnnotationAction();
        action.createNote(noteDetails);
    }

    private static void updateNote(NoteDTO noteDetails) {
        AnnotationAction action = new AnnotationAction();
        action.updateNote(noteDetails);
    }

    private static void deleteNote(NoteDTO noteDetails) {
        AnnotationAction action = new AnnotationAction();
        action.deleteNote(noteDetails);
    }

    private static  NoteDTO getNoteParams(HttpServletRequest req) {
        NoteDTO noteDetails = new NoteDTO();
        noteDetails.setOp(req.getParameter(Constants.opParameter));
        noteDetails.setNoteKey(req.getParameter("noteKey") == null ? null : Integer.valueOf(req.getParameter("noteKey")));
        //key from inplace editor requires some filtering
        if (req.getParameter("noteid") != null) {
            noteDetails.setNoteKey(req.getParameter("noteid") == null ? null : Integer.valueOf(req.getParameter("noteid").replaceAll("note_", "")));
        } else if (req.getParameter("typeid") != null) {
            //And for the select
            noteDetails.setNoteKey(req.getParameter("typeid") == null ? null : Integer.valueOf(req.getParameter("typeid").replaceAll("type_", "")));
        }
        noteDetails.setNoteText(req.getParameter("noteText"));
        noteDetails.setNoteType(req.getParameter("noteType") == null ? null : NoteTypeEnum.valueOf(req.getParameter("noteType")));
        noteDetails.setNoteVisibility(req.getParameter("noteVis") == null ? null : NoteVisibilityEnum.valueOf(req.getParameter("noteVis")));
        noteDetails.setUser(UserAccessServlet.getCurrentUser(req.getRemoteUser()));
        HibernateUtil.beginTransaction();
        noteDetails.setImageKey(req.getParameter("imageKey") == null ? null : Integer.valueOf(req.getParameter("imageKey")));
        noteDetails.setSourceKey(req.getParameter("sourceKey") == null ? null : Integer.valueOf(req.getParameter("sourceKey")));
        return noteDetails;
    }


    private void addImageRequestVariables(NoteDTO noteDetails, HttpServletRequest req) {
        DAOFactory factory = DAOFactory.getFactory();
        NoteDAO noteDao = factory.getNoteDAO();
        //add user to output attributes
        if (noteDetails.getUser() != null) {
            req.setAttribute("user", noteDetails.getUser());
        }

        //add the image as an output attribute
        Image i = factory.getImageDAO().findByPrimaryKey(noteDetails.getImageKey());
        if (i == null) {
            i = (Image) req.getSession().getAttribute(Constants.ImageSessionName);
        } else {
            req.getSession().setAttribute(Constants.ImageSessionName, i);
        }
        if (i != null) {
            req.setAttribute("image", i);

        }

        //add secondary images as attribute
        ArrayList<Secondaryimage> seconds = new ArrayList<Secondaryimage>();
        Iterator<Secondaryimage> se = i.getSecondaryImages().iterator();
        while (se.hasNext()) {
            Secondaryimage next = se.next();
            String imageurl = "http://" + req.getServerName() + ":" + req.getServerPort() + "/" + req.getContextPath() + "/jsp/ImageProxy/" + next.getFilename() + "/ImageProperties.xml";
            try {
                URL url = new URL(imageurl);
                url.openConnection();
                seconds.add(next);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if (seconds != null && seconds.size() > 0) {
            req.setAttribute("seconds", seconds);
        }

        //get the source relating to the image and add as an output attribute
        Source s = (Source) HibernateUtil.getSession().load(Source.class,i.getSourcekey()); //factory.getSourceDAO().findByImage(i.getImagekey());  //i.getSourcekey()
                req.setAttribute("source", s);
        //Add the whole source path for dispaly
        req.setAttribute("archive", s.getArchiveByArchivekey());
        req.setAttribute("city", s.getArchiveByArchivekey().getAlcityByAlcitykey());
        //get the image list for that source and add as an output attribute
        List<Image> images = factory.getImageDAO().findBySource(s.getSourcekey());
        req.setAttribute("sourceImages", images);

        //add prev and next images and add as output attributes
        for (Image image : images) {
            if (image.getOrderno() == (i.getOrderno() - 1)) {
                req.setAttribute("prev", image);
            } else if (image.getOrderno() == (i.getOrderno() + 1)) {
                req.setAttribute("next", image);
            }
        }

        if (noteDetails.getUser() != null) {
            //add private image notes as output attributes
            req.setAttribute("pvCommList", noteDao.findPrivateImageNotes(i.getImagekey(), NoteTypeEnum.COM, noteDetails.getUser().getId()));
            req.setAttribute("pvTranList", noteDao.findPrivateImageNotes(i.getImagekey(), NoteTypeEnum.TRA, noteDetails.getUser().getId()));
            //Add collections for user
            CollectionAction ca= new CollectionAction();
            req.setAttribute("collList", ca.getCollectionList(req.getRemoteUser()));
            ArrayList<Collection> attached=ca.getCollectionsAttachedToImage(i.getImagekey(),req.getRemoteUser());
            //Collection(s) owned by this user that are attached to this image
            if (attached!=null){
                //Instantiate objects for display
                for (int j = 0; j < attached.size(); j++) {
                    Collection collection =  attached.get(j);
                    List<Image> collImages=collection.getImageList();
                    for (int k = 0; k < collImages.size(); k++) {
                        Image image =  collImages.get(k);
                        image.getSourceBySourcekey();
                    }
                }
                req.setAttribute("attachedCollections",attached);
            }
            //Add other images user has commented on
            ArrayList<Image> commented=ImageSearch.getCommentedImagesByUser(req);
            if (commented!=null){
                for (int j = 0; j < commented.size(); j++) {
                    Image image = commented.get(j);
                    image.getSourceBySourcekey();
                }
                req.setAttribute("commented",commented);
            }
        }
        //add public image notes as output attributes
        ArrayList<Note> pbList=(ArrayList<Note>) noteDao.findPublicImageNotes(i.getImagekey(), NoteTypeEnum.COM);
        for (int j = 0; j < pbList.size(); j++) {
            Note note = pbList.get(j);
            note.getUser();
        }
        req.setAttribute("pbCommList", pbList);
        req.setAttribute("pbTranList", noteDao.findPublicImageNotes(i.getImagekey(), NoteTypeEnum.TRA));
    }
}


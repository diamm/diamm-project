package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.NoteDAO;
import uk.ac.kcl.cch.diamm.dao.NoteDTO;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.diamm.model.NoteVisibility.NoteVisibilityEnum;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;
import uk.ac.kcl.cch.diamm.ui.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Dec 3, 2010
 * Time: 9:49:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class DIAMMDescriptionServlet extends HttpServlet {
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
        DescriptionTypes type = null;
        RequestDispatcher reqDisp = null;
        if (op != null && op.length() > 0) {
            type = DescriptionTypes.valueOf(op);

        }
        if (type != null) {

            try {
                HibernateUtil.beginTransaction();
                //dbAnalysis();
                //operation=0;
                //Text t= (Text) HibernateUtil.getSession().get(Text.class,25376);
                switch (type) {
                    case SOURCE:
                        page = loadSourceDescription(request);
                        break;
                    case COMPOSER:
                        page = loadComposerDescription(request);
                        break;
                    case GENRE:
                        page = loadGenreDescription(request);
                        break;
                    case ARCHIVE:
                        page = loadArchiveDescription(request);
                        break;
                    case ITEM:
                        page = loadItemDescription(request);
                        break;
                    case COMPOSITION:
                        page = loadCompositionDescription(request);
                    case SET:
                        page = loadSetDescription(request);
                        break;
                }
                DIAMMFacetServlet.addMenu(request, "facetmanager");
                request.setAttribute("refererUrl", DIAMMFacetManager.getPreviousUrl(request));
                reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                HibernateUtil.commitTransaction();
            }
        }
        if (reqDisp != null) {
            reqDisp.forward(request, resp);
        }

    }

    public String loadCompositionDescription(HttpServletRequest request) {
        String cKey = request.getParameter("compositionKey");
        if (cKey != null) {
            CompositionDisplay cd = new CompositionDisplay(Integer.parseInt(cKey));
            request.setAttribute("composition", cd);
            //addComposers(request,cd.getComposers());
            //addSources(request,cd.getSources());
            //addCompositions(request, s.getCompositions());
            //addBibliographies(request,cd.getBibs());
        }
        return "description.jsp";
    }

    public String loadItemDescription(HttpServletRequest request) {
        String iKey = request.getParameter("itemKey");
        if (iKey != null) {
            Item item = (Item) HibernateUtil.getSession().load(Item.class, Integer.parseInt(iKey));
            DisplayItem d = new DisplayItem(item);
            d.addDetailData();
            d.addFullItemData();
            request.setAttribute("detailItem", d);
        }
        return "itemdescription.jsp";
    }

    public String loadComposerDescription(HttpServletRequest request) {
        String sKey = request.getParameter("composerKey");
        String summary = request.getParameter("summary");
        if (sKey != null) {
            int cKey = Integer.parseInt(sKey);
            ComposerDisplay c = new ComposerDisplay(cKey);
            request.setAttribute("composer", c);
            if (summary == null) {
                c.buildVars();
            } else {
                //Only return summary
                return "popups/composerSummary.jsp";
            }
        }
        return "description.jsp";
    }

    public String loadGenreDescription(HttpServletRequest request) {
        String sKey = request.getParameter("alGenreKey");
        if (sKey != null) {
            int cKey = Integer.parseInt(sKey);
            GenreDisplay c = new GenreDisplay(cKey);
            request.setAttribute("genre", c);
            addComposers(request, c.getComposers());
            addSources(request, c.getSources());
            //addCompositions(request, s.getCompositions());
            //addBibliographies(request,s.getBibs());
        }
        return "description.jsp";
    }

    public String loadArchiveDescription(HttpServletRequest request) {
        String sKey = request.getParameter("archiveKey");
        String summary = request.getParameter("summary");
        if (sKey != null) {
            int aKey = Integer.parseInt(sKey);
            ArchiveDisplay c = new ArchiveDisplay(aKey);
            request.setAttribute("archive", c);
            if (summary == null) {
                addSources(request, c.getSources());
            }
            if (summary != null) {
                //Only return summary
                return "popups/archiveSummary.jsp";
            }
        }
        return "description.jsp";
    }

    public String loadSetDescription(HttpServletRequest request) {
        String sKey = request.getParameter("setKey");
        if (sKey != null) {
            int aKey = Integer.parseInt(sKey);
            SetDisplay sd = new SetDisplay(aKey);
            request.setAttribute("set", sd);
            addSources(request, sd.getSet().getSources().iterator());
        }
        return "description.jsp";
    }

    public String loadSourceDescription(HttpServletRequest request) {
        String sKey = request.getParameter("sourceKey");
        String summary = request.getParameter("summary");
        if (sKey != null) {
            int sourceKey = Integer.parseInt(sKey);
            SourceDisplay s = new SourceDisplay(sourceKey);
            //Add source annotations
            Iterator i = s.getS().getNoteList().iterator();
            ArrayList<Note> pbNotes = new ArrayList<Note>();
            ArrayList<Note> pvNotes = new ArrayList<Note>();
            //DiammUser currentUser= UserAccessServlet.getCurrentUser(request.getRemoteUser());
            //HibernateUtil.beginTransaction();
            while (i.hasNext()) {
                Note note = (Note) i.next();
                if (note.getNoteVisibility().getId() == 1) {
                    if (request.getRemoteUser()!=null&&note.getUser().getUsername().equalsIgnoreCase(request.getRemoteUser())) {
                        pvNotes.add(note);
                    }
                } else {
                    pbNotes.add(note);
                }
            }
            if (pbNotes.size() > 0) {
                request.setAttribute("publicSourceNotes", pbNotes);
            }
            if (pvNotes.size() > 0) {
                request.setAttribute("privateSourceNotes", pvNotes);
            }
            if (summary == null) {
                //Build full description
                s.buildVars();
                s.addLinkedSets();
            }
            request.setAttribute("source", s);

        }
        if (summary != null) {
            //Only return summary
            return "popups/sourceSummary.jsp";
        }
        return "sourcedetail.jsp";
    }

    public void addSets(HttpServletRequest request, Source s) {
        Criteria ss = HibernateUtil.getSession().createCriteria(Sourceset.class)
                .add(Restrictions.eq("sourceBySourcekey", s));
        ArrayList<Sourceset> ssets = (ArrayList<Sourceset>) ss.list();
        ArrayList<Set> sets = new ArrayList<Set>();
        for (int i = 0; i < ssets.size(); i++) {
            Sourceset sourceset = ssets.get(i);
            sets.add(sourceset.getSetBySetkey());
        }
        request.setAttribute("sets", sets);
    }

    public void addCompositions(HttpServletRequest request, ArrayList<Composition> c) {
        request.setAttribute("compositions", c);
    }

    public void addComposers(HttpServletRequest request, ArrayList<Composer> c) {
        request.setAttribute("composers", c);
    }

    public void addItems(HttpServletRequest request, ArrayList<Item> c) {
        request.setAttribute("items", c);
    }

    public void addSources(HttpServletRequest request, Iterator<Source> c) {
        ArrayList<Source> sources = new ArrayList<Source>();
        while (c.hasNext()) {
            Source next = c.next();
            sources.add(next);
        }
        addSources(request, sources);
    }

    public void addSources(HttpServletRequest request, ArrayList<Source> c) {
        request.setAttribute("sources", c);
    }

    public void addBibliographies(HttpServletRequest request, ArrayList<BibliographyIntersectionDisplay> c) {
        request.setAttribute("bibs", c);
    }

    public void addGenres(HttpServletRequest request, ArrayList<Algenre> c) {
        request.setAttribute("genres", c);
    }

    private void doNotes(HttpServletRequest req) {
        NoteDTO noteDetails = getNoteParams(req);

        if (noteDetails.getOp() != null && noteDetails.getOp().equalsIgnoreCase("createNote")) {
            createNote(noteDetails);
        } else if (noteDetails.getOp() != null && noteDetails.getOp().equalsIgnoreCase("deleteNote")) {
            deleteNote(noteDetails);
        }

        if (noteDetails.getSourceKey() != null) {
            addSourceAttributes(noteDetails, req);
        }
    }

    private NoteDTO getNoteParams(HttpServletRequest req) {
        NoteDTO noteDetails = new NoteDTO();
        noteDetails.setOp(req.getParameter("action"));
        noteDetails.setNoteKey(req.getParameter("noteKey") == null ? null : Integer.valueOf(req.getParameter("noteKey")));
        noteDetails.setNoteText(req.getParameter("noteText"));
        noteDetails.setNoteType(req.getParameter("noteType") == null ? null : NoteTypeEnum.valueOf(req.getParameter("noteType")));
        noteDetails.setNoteVisibility(req.getParameter("noteVis") == null ? null : NoteVisibilityEnum.valueOf(req.getParameter("noteVis")));
        noteDetails.setUser(req.getRemoteUser() == null ? null : DAOFactory.getFactory().getDiammUserDAO().findByUsername(req.getRemoteUser()));
        noteDetails.setSourceKey(req.getParameter("sourceKey") == null ? null : Integer.valueOf(req.getParameter("sourceKey")));
        return noteDetails;
    }

    private void createNote(NoteDTO noteDetails) {
        AnnotationAction action = new AnnotationAction();
        action.createNote(noteDetails);
    }

    private void deleteNote(NoteDTO noteDetails) {
        AnnotationAction action = new AnnotationAction();
        action.deleteNote(noteDetails);
    }

    private void addSourceAttributes(NoteDTO noteDetails, HttpServletRequest req) {
        NoteDAO noteDao = DAOFactory.getFactory().getNoteDAO();

        //add private source notes as output attribute
        if (noteDetails.getUser() != null) {
            req.setAttribute("pvCommList", noteDao.findPrivateSourceNotes(noteDetails.getSourceKey(), NoteTypeEnum.COM, noteDetails.getUser().getId()));
        }
        //add public source notes as output attribute
        req.setAttribute("pbCommList", noteDao.findPublicSourceNotes(noteDetails.getSourceKey(), NoteTypeEnum.COM));
    }
}

package uk.ac.kcl.cch.diamm.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.NoteDAO;
import uk.ac.kcl.cch.diamm.model.DiammUser;
import uk.ac.kcl.cch.diamm.model.Image;
import uk.ac.kcl.cch.diamm.model.Note;
import uk.ac.kcl.cch.diamm.model.NoteVisibility.NoteVisibilityEnum;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;
import uk.ac.kcl.cch.diamm.ui.NoteDisplayItem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HibernateNoteDAO extends HibernateDAO<Note, Integer> implements NoteDAO {
    public HibernateNoteDAO() {
        super(Note.class);
    }

    public Note save(Note note) {
        note.setDateModified(new Timestamp((new Date()).getTime()));
        super.save(note);
        return note;
    }

    public List<NoteDisplayItem> findRecentPublicImageNotes(int numnotes) {
        Criteria criteria = HibernateUtil.getSession().createCriteria(Note.class);
        criteria.createAlias("type", "noteType");
        criteria.createAlias("noteVisibility", "noteVis");
        criteria.createAlias("noteImage", "ni");
        //Criterion crLinkId = Restrictions.eq("ni.notekey", Property.forName("Note.id"));
        Criterion crType = Restrictions.eq("noteType.code", NoteTypeEnum.COM.name());
        Criterion crVisPB = Restrictions.eq("noteVis.code", NoteVisibilityEnum.PB.toString());
        //Criterion users = Restrictions.gt("userkey", 9);
        Conjunction conjunction = Restrictions.conjunction();
        //conjunction.add(crLinkId);
        conjunction.add(crType);
        conjunction.add(crVisPB);
        //conjunction.add(users);
        criteria.add(conjunction);
        criteria.addOrder(Order.desc("dateModified"));
        criteria.setMaxResults(numnotes);
        ArrayList<Note> notes = (ArrayList<Note>) criteria.list();
        ArrayList<NoteDisplayItem> nd = new ArrayList<NoteDisplayItem>();
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getUser() != null && note.getUser().getUsername() != null) {
                Image ni = note.getNoteImage();
                if (ni != null) {
                    nd.add(new NoteDisplayItem(note, ni.getImagekey()));
                }
            }
            /*NoteImage ni=(NoteImage) HibernateUtil.getSession().createCriteria(NoteImage.class).add(Restrictions.eq("notekey",note.getNotekey())).uniqueResult();
            if (ni!=null){
                nd.add(new NoteDisplayItem(note,ni.getImagekey()));
            }*/
        }
        return nd;
    }

    public List<Note> findPublicImageNotes(Integer imageId, NoteTypeEnum type) {
        return findPublicNotes(imageId, type, NoteLinkType.IMAGE);
    }

    public List<Note> findPrivateImageNotes(Integer imageId, NoteTypeEnum type, Integer userId) {
        return findPrivateNotes(imageId, type, NoteLinkType.IMAGE, userId);
    }

    public List<Note> findPublicSourceNotes(Integer sourceId, NoteTypeEnum type) {
        return findPublicNotes(sourceId, type, NoteLinkType.SOURCE);
    }

    public List<Note> findPrivateSourceNotes(Integer sourceId, NoteTypeEnum type, Integer userId) {
        return findPrivateNotes(sourceId, type, NoteLinkType.SOURCE, userId);
    }

    @SuppressWarnings("unchecked")
    private List<Note> findPublicNotes(Integer linkId, NoteTypeEnum type, NoteLinkType linkType) {
        Criteria criteria = HibernateUtil.getSession().createCriteria(Note.class);
        criteria.createAlias("type", "noteType");
        criteria.createAlias("noteVisibility", "noteVis");
        Criterion crLinkId = Restrictions.eq((linkType.equals(NoteLinkType.IMAGE) ? "noteImage.imagekey" : "noteSource.sourcekey"), linkId);
        Criterion crType = Restrictions.eq("noteType.code", type.toString());
        Criterion crVisPB = Restrictions.eq("noteVis.code", NoteVisibilityEnum.PB.toString());

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(crLinkId);
        conjunction.add(crType);
        conjunction.add(crVisPB);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("dateModified"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    private List<Note> findPrivateNotes(Integer linkId, NoteTypeEnum type, NoteLinkType linkType, Integer userId) {
        Criteria criteria = HibernateUtil.getSession().createCriteria(Note.class);
        criteria.createAlias("type", "noteType");
        criteria.createAlias("noteVisibility", "noteVis");
        Criterion crLinkId = Restrictions.eq((linkType.equals(NoteLinkType.IMAGE) ? "noteImage.imagekey" : "noteSource.sourcekey"), linkId);
        Criterion crType = Restrictions.eq("noteType.code", type.toString());
        Criterion crUser = Restrictions.eq("user.id", userId);
        Criterion crVisPV = Restrictions.eq("noteVis.code", NoteVisibilityEnum.PV.toString());


        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(crLinkId);
        conjunction.add(crType);
        conjunction.add(crUser);
        conjunction.add(crVisPV);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("dateModified"));
        return criteria.list();
    }

    private enum NoteLinkType {SOURCE, IMAGE}
}
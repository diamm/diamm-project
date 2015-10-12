package uk.ac.kcl.cch.diamm.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.NoteVisibilityDAO;
import uk.ac.kcl.cch.diamm.model.NoteVisibility;
import uk.ac.kcl.cch.diamm.model.NoteVisibility.NoteVisibilityEnum;

public class HibernateNoteVisibilityDAO extends HibernateDAO <NoteVisibility, Integer> implements NoteVisibilityDAO
{	
    public HibernateNoteVisibilityDAO() 
    {
	    super(NoteVisibility.class);
    }

	public NoteVisibility findByCode(NoteVisibilityEnum vis) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(NoteVisibility.class);
		Criterion crVis = Restrictions.eq("code", vis.toString());
		criteria.add(crVis);
		return (NoteVisibility) criteria.uniqueResult();
	}
}
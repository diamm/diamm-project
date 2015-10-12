package uk.ac.kcl.cch.diamm.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.NotetypeDAO;
import uk.ac.kcl.cch.diamm.model.Notetype;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;

public class HibernateNotetypeDAO extends HibernateDAO <Notetype, Integer> implements NotetypeDAO
{	
    public HibernateNotetypeDAO() 
    {
	    super(Notetype.class);
    }

	public Notetype findByCode(NoteTypeEnum type) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(Notetype.class);
		Criterion crType = Restrictions.eq("code", type.toString());
		criteria.add(crType);
		return (Notetype) criteria.uniqueResult();
	}
}
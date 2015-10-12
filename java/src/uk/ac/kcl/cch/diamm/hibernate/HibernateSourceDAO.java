package uk.ac.kcl.cch.diamm.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.SourceDAO;
import uk.ac.kcl.cch.diamm.model.Source;

public class HibernateSourceDAO extends HibernateDAO <Source, Integer> implements SourceDAO
{	
    public HibernateSourceDAO() 
    {
	    super(Source.class);
    }

	public Source findByImage(Integer imageId) 
	{	
//        //perform using criteria
//		Criteria criteria = HibernateUtil.getSession().createCriteria(Source.class);
//		criteria.createAlias("itemsBySourcekey", "item");
//		criteria.createAlias("item.itemimagesByItemkey", "itemimage");
//		criteria.createAlias("itemimage.imageByImagekey", "image");
//		criteria.add(Restrictions.eq("image.id", imageId));
//		return (Source) criteria.uniqueResult();
		
		//perform using hql
		String fullHql = "SELECT source from Source as source " +
                         " join source.itemsBySourcekey as item" +
                         " join item.itemimagesByItemkey as itemimage" +
                         " join itemimage.imageByImagekey as image" +
                         " WHERE image.id = :imageId";
		
		Query query = HibernateUtil.getSession().createQuery(fullHql);
	    query.setInteger("imageId", imageId);
	    return (Source) query.uniqueResult();
	}
}
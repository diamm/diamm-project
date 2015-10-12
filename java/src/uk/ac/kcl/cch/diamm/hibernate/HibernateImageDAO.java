package uk.ac.kcl.cch.diamm.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.ImageSearch;
import uk.ac.kcl.cch.diamm.dao.ImageDAO;
import uk.ac.kcl.cch.diamm.model.Image;

public class HibernateImageDAO extends HibernateDAO <Image, Integer> implements ImageDAO
{	
    public HibernateImageDAO() 
    {
	    super(Image.class);
    }

	
	@SuppressWarnings("unchecked")
	public List<Image> findBySource(Integer sourceId) 
	{
//		//perform using criteria
//		Criteria criteria = HibernateUtil.getSession().createCriteria(Image.class);
//		criteria.createAlias("itemimagesByImagekey", "itemimage");
//		criteria.createAlias("itemimage.itemByItemkey", "item");
//		criteria.createAlias("item.sourceBySourcekey", "source");
//		criteria.add(Restrictions.eq("source.sourcekey", sourceId));
//		criteria.addOrder(Order.asc("orderno"));
//		return criteria.list();
		
		//perform using hql
		/*String fullHql = "SELECT distinct image from Image as image " +
                         " join image.itemimagesByImagekey as itemimage" +
                         " join itemimage.itemByItemkey as item" +
                         " join item.sourceBySourcekey as source" +
                         " WHERE source.sourcekey = :sourceId" +
                         " order by image.orderno";
		
		Query query = HibernateUtil.getSession().createQuery(fullHql);
	    query.setInteger("sourceId", sourceId);
	    return query.list();*/
        return ImageSearch.getImagesBySourceKey(sourceId);
	}
}
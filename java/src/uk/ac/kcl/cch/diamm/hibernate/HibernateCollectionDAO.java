package uk.ac.kcl.cch.diamm.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.CollectionDAO;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.model.Collection;
import uk.ac.kcl.cch.diamm.model.Item;
import uk.ac.kcl.cch.diamm.model.Itemimage;
import uk.ac.kcl.cch.diamm.model.Source;

public class HibernateCollectionDAO extends HibernateDAO <Collection, Integer> implements CollectionDAO
{	
    public HibernateCollectionDAO() 
    {
	    super(Collection.class);
    }
    
    public Collection findByPrimaryKey(Integer id)
    {
    	Collection col = super.findByPrimaryKey(id);
    	
    	//pre-load the lazy lists
    	preload(col);
    	
    	return col;
    }
    
    private void preload(Collection col)
	{
    	List<Source> sourceList = col.getSourceList();
    	for(Source source: sourceList)
    	{
    		java.util.Collection<Item> itemList = source.getItemsBySourcekey();
    		for (Item item: itemList)
    		{
    			java.util.Collection<Itemimage> itemimageList = item.getItemimagesByItemkey();
    			itemimageList.size();
    		}
    	} 
    	
    	List<Item> itemList = col.getItemList();
    	for(Item item: itemList)
    	{
    		item.getItemimagesByItemkey().size();
    	}
    	
    	col.getImageList().size();
	}

	@SuppressWarnings("unchecked")
	public List<Collection> find(String username, String collectionTitle, Integer excludeCollectionId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria(Collection.class);
		Criterion user  = Restrictions.eq("diammUser.id", DAOFactory.getFactory().getDiammUserDAO().findByUsername(username).getId());
    	Criterion title = Restrictions.eq("title", collectionTitle);
    	Criterion id    = Restrictions.ne("id", excludeCollectionId);
    	Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(user);
        conjunction.add(title);
        conjunction.add(id);
        criteria.add(conjunction);
    	return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Collection> find(String username, String collectionTitle)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria(Collection.class);
		Criterion user  = Restrictions.eq("diammUser.id", DAOFactory.getFactory().getDiammUserDAO().findByUsername(username).getId());
    	Criterion title = Restrictions.eq("title", collectionTitle);
    	Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(user);
        conjunction.add(title);
        criteria.add(conjunction);
    	return criteria.list();
	}
}
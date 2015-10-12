package uk.ac.kcl.cch.diamm.hibernate;

import uk.ac.kcl.cch.diamm.dao.ItemDAO;
import uk.ac.kcl.cch.diamm.model.Item;

public class HibernateItemDAO extends HibernateDAO <Item, Integer> implements ItemDAO
{	
    public HibernateItemDAO() 
    {
	    super(Item.class);
    }
}
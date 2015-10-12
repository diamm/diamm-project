package uk.ac.kcl.cch.diamm.hibernate;

import java.io.*; 
import java.util.*;
import org.hibernate.*; 
import org.hibernate.criterion.*;
import uk.ac.kcl.cch.diamm.dao.GenericDAO;

public class HibernateDAO <T,ID extends Serializable> implements GenericDAO <T,ID>
{
    protected Class<T> persistentClass;
    
    public HibernateDAO(Class<T> c) 
    {
        persistentClass = c;
    }
    
    @SuppressWarnings("unchecked")
	public T findByPrimaryKey(ID id) 
    {
        return (T) HibernateUtil.getSession().get(persistentClass, id);
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findAll()
    {
        Criteria crit = HibernateUtil.getSession().createCriteria(persistentClass);
        return crit.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findAll(String orderBy)
    {
        Criteria crit = HibernateUtil.getSession().createCriteria(persistentClass);
        crit.addOrder(Order.asc(orderBy));
        return crit.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findAll(Integer startIndex, Integer maxSize, String orderBy)
    {
        Criteria crit = HibernateUtil.getSession().createCriteria(persistentClass);
        crit.addOrder(Order.asc(orderBy));
        crit.setFirstResult(startIndex);
        crit.setMaxResults(maxSize);
        return crit.list();
    }
    
    public T save(T entity) 
    {
        HibernateUtil.getSession().saveOrUpdate(entity);
        return entity;
    }
    
    public void delete(T entity) 
    {
        HibernateUtil.getSession().delete(entity);

    }
    
    public void beginTransaction() 
	{
		HibernateUtil.beginTransaction();
	}

	public void commitTransaction() 
	{
		HibernateUtil.commitTransaction();
	}

	public void rollbackTransaction() 
	{
		HibernateUtil.rollbackTransaction();
	}
}
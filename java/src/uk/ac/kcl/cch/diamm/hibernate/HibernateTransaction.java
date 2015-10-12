package uk.ac.kcl.cch.diamm.hibernate;

import uk.ac.kcl.cch.diamm.dao.Transaction;

public class HibernateTransaction implements Transaction {
	public void begin() 
	{
		HibernateUtil.beginTransaction();
	}

	public void commit() 
	{
		HibernateUtil.commitTransaction();
	}

	public void rollback() 
	{
		HibernateUtil.rollbackTransaction();
	}
}

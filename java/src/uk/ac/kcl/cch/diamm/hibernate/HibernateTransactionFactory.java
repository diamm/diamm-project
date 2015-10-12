package uk.ac.kcl.cch.diamm.hibernate;

import uk.ac.kcl.cch.diamm.dao.Transaction;
import uk.ac.kcl.cch.diamm.dao.TransactionFactory;

public class HibernateTransactionFactory extends TransactionFactory
{
	public static Transaction getTransaction() 
	{
		return new HibernateTransaction();
	}
} 

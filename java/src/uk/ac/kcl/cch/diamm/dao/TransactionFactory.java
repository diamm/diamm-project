package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.hibernate.HibernateTransaction;

public abstract class TransactionFactory
{
	@SuppressWarnings("unchecked")
	private static final Class TRANSACTION_CLASS = HibernateTransaction.class;

    public static Transaction getTransaction() 
    {
        try 
        {
            return (Transaction)TRANSACTION_CLASS.newInstance();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Couldn't create Transaction");
        }
    }
}
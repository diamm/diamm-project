package uk.ac.kcl.cch.diamm.dao;

public interface Transaction {
	public void begin();
    public void commit();
    public void rollback(); 
}

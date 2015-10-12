package uk.ac.kcl.cch.diamm.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T, ID extends Serializable>
{
    public T findByPrimaryKey(ID id);
    public List<T> findAll();
    public List<T> findAll(String orderBy);
    public List<T> findAll(Integer startIndex, Integer fetchSize, String orderBy);
    public T save(T entity);
    public void delete(T entity);
    public void beginTransaction();
    public void commitTransaction();
    public void rollbackTransaction(); 
}

package cl.gob.scj.sgdp.dao;

import java.util.List;

public interface GenericDao<T> {
	
	void insert(T t);
	
	void delete(T t);
	
	T find(Object id);
	
	List<T> getAll(final Class<T> type);

}

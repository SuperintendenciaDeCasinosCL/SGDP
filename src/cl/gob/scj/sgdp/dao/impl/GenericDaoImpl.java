package cl.gob.scj.sgdp.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.GenericDao;

@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> type;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GenericDaoImpl() {
		super();
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType)t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	public void insert(T t) {
		getSession().save(t);
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);

	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(Object id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(type, (Serializable) id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(final Class<T> type) {
	      final Criteria crit = getSession().createCriteria(type);
	      return crit.list();
	}

}

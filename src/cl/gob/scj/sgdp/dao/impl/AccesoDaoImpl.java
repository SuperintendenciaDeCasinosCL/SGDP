package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.AccesoDao;
import cl.gob.scj.sgdp.model.Acceso;

@Repository
public class AccesoDaoImpl implements AccesoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Acceso> getTodosLosAccesos() {
		Query query = getSession().getNamedQuery("Acceso.findAll");				
		return query.list();
	}
	

}
package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.SuperProcesoDao;
import cl.gob.scj.sgdp.model.SuperProceso;

@Repository
public class SuperProcesoDaoImpl implements SuperProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SuperProceso> getTodosLosSuperProcesosByIDMacroProceso(Long idMacroProceso) {
		Query query = getSession().getNamedQuery("SuperProceso.findAllByMacroProceso");				
		query.setLong("idMacroProceso", idMacroProceso);
		return query.list();
	}
	
	
}

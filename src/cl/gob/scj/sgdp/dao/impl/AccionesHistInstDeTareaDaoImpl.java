package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.AccionesHistInstDeTareaDao;
import cl.gob.scj.sgdp.model.AccionesHistInstDeTarea;

@Repository
public class AccionesHistInstDeTareaDaoImpl implements
		AccionesHistInstDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public AccionesHistInstDeTarea getAccionHistInstDeTareaPorId(
			long idAccionHistoricoInstDeTarea) {
		return (AccionesHistInstDeTarea) getSession().get(AccionesHistInstDeTarea.class, idAccionHistoricoInstDeTarea);
	}
	

}

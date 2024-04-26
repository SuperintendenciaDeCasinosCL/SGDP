package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ResponsabilidadDao;
import cl.gob.scj.sgdp.model.Responsabilidad;

@Repository
public class ReferenciaDeTareaDaoImpl implements ResponsabilidadDao {

	@Autowired
	private SessionFactory sessionFactory;	
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Responsabilidad> getAllByProcessId(long processId) {
		Query query = getSession().getNamedQuery("Responsabilidad.findAllByProcessID");	
		query.setLong("processid", processId);
		System.out.println(processId);
		List<Responsabilidad> list = query.list();
		System.out.println(list);
		return list;
	}

	@Override
	public Long guardar(Responsabilidad r, Session session) {
		if (session == null ) {
			session = getSession();
		}
		return (Long) session.save(r);
	}
	
	

}

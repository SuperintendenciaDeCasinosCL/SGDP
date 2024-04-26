package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ReferenciaDeTareaDao;
import cl.gob.scj.sgdp.model.ReferenciaDeTarea;


@Repository
public class ResponsabilidadDaoImpl implements ReferenciaDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;	
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}	
	

	@Override
	public Long guardar(ReferenciaDeTarea ref, Session session) {
		if (session == null ) {
			session = getSession();
		}
		return (Long) session.save(ref);
	}


	

}

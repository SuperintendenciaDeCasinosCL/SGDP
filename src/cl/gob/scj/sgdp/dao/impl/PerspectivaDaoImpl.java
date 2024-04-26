package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.PerspectivaDao;
import cl.gob.scj.sgdp.model.Perspectiva;

@Repository
public class PerspectivaDaoImpl implements PerspectivaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perspectiva> getTodasLasPerspectivas() {
		// return getSession().createQuery("from MacroProceso").list();
		Query query = getSession().getNamedQuery("Perspectiva.findAll");
		return query.list();
	}

}

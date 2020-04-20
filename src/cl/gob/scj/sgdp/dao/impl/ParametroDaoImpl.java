package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroDao;
import cl.gob.scj.sgdp.model.Parametro;

@Repository
@Transactional(rollbackFor = Throwable.class)
public class ParametroDaoImpl extends GenericDaoImpl<Parametro> implements ParametroDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Parametro> getTodosLosParametros() {		
		return getSession().createQuery("from Parametro").list();
	}

}

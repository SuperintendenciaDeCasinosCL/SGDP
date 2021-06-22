package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TipoGestionDao;
import cl.gob.scj.sgdp.model.Tipo;

@Repository
public class TipoGestionDaoImpl implements TipoGestionDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipo> getAll() {

		return getSession().createQuery("SELECT p FROM Tipo p order by p.idTipo asc").list();
	}
	

}

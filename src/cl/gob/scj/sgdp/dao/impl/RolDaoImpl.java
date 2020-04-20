package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.RolDao;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.Rol;

@Repository
public class RolDaoImpl implements RolDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getAllRoles() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Rol").list();
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public Rol getRolPorIdRol(long idRol) {
		return (Rol) getSession().get(Rol.class, idRol);
	}
	

}

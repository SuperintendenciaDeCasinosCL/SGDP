package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.model.Autor;

@Repository
public class AutorDaoImpl extends GenericDaoImpl<Autor> implements AutorDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> getTodosLosAutores() {
		//return getSession().createQuery("from Autor").list();
		Query query = getSession().getNamedQuery("Autor.findAll");				
		return query.list();
	}
	
	@Override
	public Autor getAutorPorIdAutor(long idAutor) {
		return (Autor) getSession().get(Autor.class, idAutor);	
	}	

}
package cl.gob.scj.sgdp.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.CometarioDeArchivoDao;
import cl.gob.scj.sgdp.model.CometarioDeArchivo;

@Repository
public class CometarioDeArchivoDaoImpl implements CometarioDeArchivoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertCometarioDeArchivo(CometarioDeArchivo cometarioDeArchivo,
			Usuario usuario) {
		getSession().save(cometarioDeArchivo);
	}	
	
	public CometarioDeArchivo getCometarioDeArchivoPorNombreArchivo(String nombreArchivo, Usuario usuario) {
		Query query = getSession().createQuery(
				"SELECT c from CometarioDeArchivo c where c.nombreArchivo = :nombreArchivo");	
		query.setString("nombreArchivo", nombreArchivo);		
		return (CometarioDeArchivo) query.uniqueResult();
	}

}

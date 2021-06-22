package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ParametroArchivoNacionalDao;
import cl.gob.scj.sgdp.model.ParametroArchivoNacional;

@Repository
public class ParametroArchivoNacionalDaoImpl implements ParametroArchivoNacionalDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ParametroArchivoNacional getParametroArchivoNacionalByCodigo(String codigo) {
		Query query = getSession().createQuery(
				"SELECT t from ParametroArchivoNacional t where t.nombreParametro = :nombreParametro");
		query.setString("nombreParametro", codigo);
		return (ParametroArchivoNacional) query.uniqueResult();
	}

	@Override
	public void insertarParametroArchivoNacional(ParametroArchivoNacional entity) {
		getSession().save(entity);		
	}

	


}

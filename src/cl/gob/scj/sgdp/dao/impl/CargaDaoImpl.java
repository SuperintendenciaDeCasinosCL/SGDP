package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.CargaDao;
import cl.gob.scj.sgdp.model.Carga;

@Repository
public class CargaDaoImpl implements CargaDao {
	private static final Logger log = Logger.getLogger(CargaDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public void guardarCargaArchivoNacional(Carga entity) {
		log.info("Inicio guardarCargaArchivoNacional");
		getSession().save(entity);
		log.info("Fin guardarCargaArchivoNacional");
	}
	@Override
	public List<Carga> getResultadoBusquedaEstadoTransferencia() {
		Query query = getSession().getNamedQuery("Carga.findAll");		
		return query.list();	
	}

}

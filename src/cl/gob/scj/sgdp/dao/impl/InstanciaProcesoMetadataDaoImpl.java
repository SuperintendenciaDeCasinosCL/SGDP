package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.InstanciaProcesoMetadataDao;
import cl.gob.scj.sgdp.model.InstanciaProcesoMetadata;


@Repository
public class InstanciaProcesoMetadataDaoImpl implements InstanciaProcesoMetadataDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertInstanciaProcesoMetadata(InstanciaProcesoMetadata instanciaProcesoMetadata) {
		getSession().save(instanciaProcesoMetadata);
	}

		
}

package cl.gob.scj.sgdp.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaMetadataDao;
import cl.gob.scj.sgdp.model.ArchivosInstDeTareaMetadata;

@Repository
public class ArchivosInstDeTareaMetadataDaoImpl implements ArchivosInstDeTareaMetadataDao {

	private static final Logger log = Logger.getLogger(ArchivosInstDeTareaMetadataDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertaArchivosInstDeTareaMetada(ArchivosInstDeTareaMetadata archivosInstDeTarea) {
		getSession().save(archivosInstDeTarea);
	}
	
	@Override
	public void updateArchivosInstDeTareaMetada(ArchivosInstDeTareaMetadata archivosInstDeTarea) {
		getSession().update(archivosInstDeTarea);
	}
	
	
	
	
	
}

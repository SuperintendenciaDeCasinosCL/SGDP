package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ArchivosSolCreaExpDao;
import cl.gob.scj.sgdp.dto.ArchivosSolCreaExpDTO;
import cl.gob.scj.sgdp.dto.SolicitudCreacionExpDTO;
import cl.gob.scj.sgdp.model.ArchivosSolCreaExp;
import cl.gob.scj.sgdp.model.SolicitudCreacionExp;

@Repository
public class ArchivosSolCreaExpDaoImpl extends GenericDaoImpl<ArchivosSolCreaExp> implements ArchivosSolCreaExpDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<ArchivosSolCreaExp> getArchivosCrearExp(ArchivosSolCreaExpDTO archivosSolCreaExpDTO) {
		Query query = getSession().getNamedQuery("ArchivosSolCreaExp.findByIdSol");
		query.setLong("idSolicitudCreacionExp", archivosSolCreaExpDTO.getIdSolicitudCreacionExp());
		query.setFirstResult(archivosSolCreaExpDTO.getPagina()).setMaxResults(archivosSolCreaExpDTO.getTamanoPagina());
		List<ArchivosSolCreaExp> listaArchivo = (List<ArchivosSolCreaExp>) query.list();		
		return  listaArchivo;
	}

	
}

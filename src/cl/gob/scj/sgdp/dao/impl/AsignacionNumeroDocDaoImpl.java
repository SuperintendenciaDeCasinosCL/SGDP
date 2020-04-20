package cl.gob.scj.sgdp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.control.BandejaDeEntradaControl;
import cl.gob.scj.sgdp.dao.AsignacionNumeroDocDao;
import cl.gob.scj.sgdp.dto.rest.AsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.model.AsignacionesNumerosDoc;

@Repository
public class AsignacionNumeroDocDaoImpl implements AsignacionNumeroDocDao {	
	
	private static final Logger log = Logger.getLogger(BandejaDeEntradaControl.class);	

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public AsignacionesNumerosDoc getAsignacionesNumerosDocPorId(long idAsignacionNumeroDoc) {	
		return (AsignacionesNumerosDoc) getSession().get(AsignacionesNumerosDoc.class, idAsignacionNumeroDoc);
	}
	
	@Override
	public String guardarAsignacionDocumento(
			AsignacionesNumerosDoc asignacionesNumerosDoc) {
		
		try {
			getSession().save(asignacionesNumerosDoc);
			return "Ok";
		} catch (Exception e) {
			log.error(" GuardarAsignacionDocumento " + e.getMessage());

			return "Error";
		}
	        	
	}

	@Override
	public AsignacionesNumerosDoc buscarIdNumeroDocumentoReservadoNoUtilizado(
			AsignacionesNumerosDocDto asignacionesNumerosDocDto) {
			
		
		Query query = getSession().getNamedQuery("AsignacionesNumerosDoc.BuscarIdNumeroDocumentoReservadoNoUtilizado");
		query.setLong("tipoDeDocumento", asignacionesNumerosDocDto.getTipoDeDocumento());
		query.setString("anio", asignacionesNumerosDocDto.getAnio());
		
		return (AsignacionesNumerosDoc) query.uniqueResult();
		
	}

	@Override
	public AsignacionesNumerosDoc buscarIdNumeroDocumentoFirmado(
			AsignacionesNumerosDocDto asignacionesNumerosDocDto) {
			
		List<String> estados = new ArrayList<String>(1);
		estados.add(Constantes.NOMBRE_ESTADO_RESERVADO);
		estados.add(Constantes.NOMBRE_ESTADO_FIRMADO);
	  //  String[] estados = new String[2];
	//	estados[0] = Constantes.NOMBRE_ESTADO_RESERVADO;
	//	estados[1] = Constantes.NOMBRE_ESTADO_FIRMADO;
		
		
		Query query = getSession().getNamedQuery("AsignacionesNumerosDoc.BuscarIdNumeroDocumentoFirmado");
		query.setLong("tipoDeDocumento", asignacionesNumerosDocDto.getTipoDeDocumento());
		query.setString("anio", asignacionesNumerosDocDto.getAnio());
		query.setParameterList("estados", estados);
		
		return (AsignacionesNumerosDoc) query.uniqueResult();
		
	}

	@Override
	public AsignacionesNumerosDoc buscarAsignacionDocumentoPorId(
			AsignacionesNumerosDocDto asignacionesNumerosDocDto) {
		Query query = getSession().createQuery(
				"from AsignacionesNumerosDoc e where e.idAsignacionNumeroDoc =:idAsignacionNumeroDocEntada ");
		query.setLong("idAsignacionNumeroDocEntada", asignacionesNumerosDocDto.getIdAsignacionNumeroDocEntada());
		return (AsignacionesNumerosDoc) query.uniqueResult();	
	}

	




}

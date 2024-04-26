package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dto.TipoActividadBitacoraDTO;
import cl.gob.scj.sgdp.model.BitacoraSubTareas;
import cl.gob.scj.sgdp.model.TipoActividadBitacora;
import cl.gob.scj.sgdp.service.TipoActividadBitacoraService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class TipoActividadBitacoraServiceImpl implements TipoActividadBitacoraService {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<TipoActividadBitacoraDTO> getAll() {
		List<TipoActividadBitacoraDTO> tipos = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<TipoActividadBitacora> tbds = getSession().createCriteria(TipoActividadBitacora.class).list();
		
		for(TipoActividadBitacora tbd : tbds) {
				TipoActividadBitacoraDTO tipo = new TipoActividadBitacoraDTO();
				tipo.setId(tbd.getIdActividad());
				tipo.setNombre(tbd.getNombreActividad());
				tipo.setActivo(tbd.getActivo());
				tipos.add(tipo);
		}
		
		return tipos;
	}


	@Override
	public List<TipoActividadBitacoraDTO> getAllActive() {
		List<TipoActividadBitacoraDTO> tipos = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<TipoActividadBitacora> tbds = getSession().createCriteria(TipoActividadBitacora.class).list();
		
		for(TipoActividadBitacora tbd : tbds) {
			if(tbd.getActivo()) {
				TipoActividadBitacoraDTO tipo = new TipoActividadBitacoraDTO();
				tipo.setId(tbd.getIdActividad());
				tipo.setNombre(tbd.getNombreActividad());
				tipos.add(tipo);
			}
		}
		
		return tipos;
	}

	@Override
	public Boolean save(TipoActividadBitacoraDTO tipo) {
		TipoActividadBitacora tipoModel = new TipoActividadBitacora();
		tipoModel.setNombreActividad(tipo.getNombre());
		tipoModel.setActivo(true);
		try {
			getSession().save(tipoModel);
			return true;
		} catch (Exception e ) {
			return false;
		}

	}

	@Override
	public Boolean update(TipoActividadBitacoraDTO tipo) {
		TipoActividadBitacora tipoModel = new TipoActividadBitacora();
		tipoModel.setNombreActividad(tipo.getNombre());
		tipoModel.setIdActividad(tipo.getId());
		try {
			getSession().update(tipoModel);
			return true;
		} catch (Exception e ) {
			return false;
		}

	}
	
	@Override
	public Boolean remove(Long idActividad) {
		Query query = getSession().getNamedQuery("bitacoraTipoActividad.elimina");
		query.setLong("idActividad", idActividad);
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e ) {
			return false;
		}

	}

	@Override
	public TipoActividadBitacoraDTO getById(Long id) {
		TipoActividadBitacora tipoModel = new TipoActividadBitacora();
		TipoActividadBitacoraDTO tDto = new TipoActividadBitacoraDTO();
				;
		tipoModel.setIdActividad(id);
		try {
			tipoModel = (TipoActividadBitacora) getSession().get(tipoModel.getClass(), id);
			tDto.setId(tipoModel.getIdActividad());
			tDto.setNombre(tipoModel.getNombreActividad());
			tDto.setEstado("OK");
		} catch(Exception e ) {
			tDto.setEstado("ERROR");
			tDto.setGlosa(e.getMessage());
			
		}
		
		return tDto;
		
	}
	
	
}

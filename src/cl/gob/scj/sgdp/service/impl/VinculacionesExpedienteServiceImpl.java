package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.HistoricoVinculacionExpDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.VinculacionExpDao;
import cl.gob.scj.sgdp.dto.VinculacionExpedienteDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.HistoricoVinculacionExp;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.VinculacionExp;
import cl.gob.scj.sgdp.service.VinculacionesExpedienteService;
import cl.gob.scj.sgdp.tipos.AccionType;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class VinculacionesExpedienteServiceImpl implements VinculacionesExpedienteService {
	
	private static final Logger log = Logger.getLogger(VinculacionesExpedienteServiceImpl.class);
			
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private VinculacionExpDao vinculacionExpDao;
	
	@Autowired
	private HistoricoVinculacionExpDao historicoVinculacionExpDao;
	
	@Autowired
	private GestorMetadataCMSService gestorMetadataCMSService;
	
	@Override
	public VinculacionExpedienteDTO getVinculacionExpedienteDTO(String idExpediente) {
		VinculacionExpedienteDTO vinculacionExpedienteDTO = new VinculacionExpedienteDTO();
		List<VinculacionExpedienteDTO> expedientesAntecesores = new ArrayList<VinculacionExpedienteDTO>();
		List<VinculacionExpedienteDTO> expedientesSucesores = new ArrayList<VinculacionExpedienteDTO>();
		InstanciaDeProceso instanciaDeProcesoActual = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(idExpediente);		
		vinculacionExpedienteDTO.setNombreExpediente(instanciaDeProcesoActual.getNombreExpediente());
		vinculacionExpedienteDTO.setNombreProceso(instanciaDeProcesoActual.getProceso().getNombreProceso());
		List<VinculacionExp> vinculacionesExp = instanciaDeProcesoActual.getVinculacionesDeExp();
		List<VinculacionExp> vinculacionesExpSucesores = vinculacionExpDao.getVinculacionExpSucesoresPorIdInstProcAntecesor(instanciaDeProcesoActual.getIdInstanciaDeProceso());
		log.info(instanciaDeProcesoActual.toString());
		log.info("vinculacionesExp.size(): " + vinculacionesExp.size());
		List<String> comentarios = new ArrayList<String>();
		StringBuilder comentarioVinculacionExpedienteAntecesor = new StringBuilder();
		StringBuilder comentarioVinculacionExpedienteSucesor = new StringBuilder();
		for (VinculacionExp vinculacionExp: vinculacionesExp) {
			log.info("Vinculaciones del expediente: " + instanciaDeProcesoActual.getNombreExpediente());
			log.info(vinculacionExp.toString());			
			InstanciaDeProceso instanciaDeProcesoAntecesor = vinculacionExp.getInstanciaDeProcesoAntecesor();			
			VinculacionExpedienteDTO vinculacionExpedienteAntecesorDTO = new VinculacionExpedienteDTO();			
			vinculacionExpedienteAntecesorDTO.setIdUsuario(vinculacionExp.getIdUsuario());
			vinculacionExpedienteAntecesorDTO.setFechaVinculacion(vinculacionExp.getFechaVinculacion());
			vinculacionExpedienteAntecesorDTO.setNombreExpediente(instanciaDeProcesoAntecesor.getNombreExpediente());
			vinculacionExpedienteAntecesorDTO.setNombreProceso(instanciaDeProcesoAntecesor.getProceso().getNombreProceso());				
			List<HistoricoVinculacionExp> historicoVinculacionesExp = 
					historicoVinculacionExpDao.getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor(instanciaDeProcesoActual.getIdInstanciaDeProceso(), instanciaDeProcesoAntecesor.getIdInstanciaDeProceso());
			int contador = 1;
			for (HistoricoVinculacionExp historicoVinculacionExp : historicoVinculacionesExp) {
				comentarios.add(historicoVinculacionExp.getComentario());
				comentarioVinculacionExpedienteAntecesor.append(historicoVinculacionExp.getComentario()).append(" (").append(historicoVinculacionExp.getIdUsuario()).append(")");				
				if (contador<historicoVinculacionesExp.size()) {
					comentarioVinculacionExpedienteAntecesor.append("; ");
				}					
				contador ++;
			}
			vinculacionExpedienteAntecesorDTO.setComentario(comentarioVinculacionExpedienteAntecesor.toString());
			vinculacionExpedienteAntecesorDTO.setComentarios(comentarios);
			expedientesAntecesores.add(vinculacionExpedienteAntecesorDTO);
			comentarioVinculacionExpedienteAntecesor.setLength(0);
						
		}
		for (VinculacionExp vinculacionExpSucesor: vinculacionesExpSucesores) {
			log.info("Expedientes Sucesores del expediente: " + instanciaDeProcesoActual.getNombreExpediente());
			log.info(vinculacionExpSucesor.toString());
			VinculacionExpedienteDTO vinculacionExpedienteSucesorDTO = new VinculacionExpedienteDTO();
			InstanciaDeProceso instanciaDeProcesoSucesor = vinculacionExpSucesor.getInstanciaDeProceso();		
			vinculacionExpedienteSucesorDTO.setIdUsuario(vinculacionExpSucesor.getIdUsuario());
			vinculacionExpedienteSucesorDTO.setFechaVinculacion(vinculacionExpSucesor.getFechaVinculacion());
			vinculacionExpedienteSucesorDTO.setNombreExpediente(instanciaDeProcesoSucesor.getNombreExpediente());
			vinculacionExpedienteSucesorDTO.setNombreProceso(instanciaDeProcesoSucesor.getProceso().getNombreProceso());		
			List<HistoricoVinculacionExp> historicoVinculacionesExp = 
					historicoVinculacionExpDao.getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor(instanciaDeProcesoSucesor.getIdInstanciaDeProceso(), instanciaDeProcesoActual.getIdInstanciaDeProceso());
			int contador = 1;
			for (HistoricoVinculacionExp historicoVinculacionExp : historicoVinculacionesExp) {
				comentarios.add(historicoVinculacionExp.getComentario());
				comentarioVinculacionExpedienteSucesor.append(historicoVinculacionExp.getComentario()).append(" (").append(historicoVinculacionExp.getIdUsuario()).append(")");				
				if (contador<historicoVinculacionesExp.size()) {
					comentarioVinculacionExpedienteSucesor.append("; ");
				}					
				contador ++;
			}
			vinculacionExpedienteSucesorDTO.setComentario(comentarioVinculacionExpedienteSucesor.toString());
			vinculacionExpedienteSucesorDTO.setComentarios(comentarios);
			expedientesSucesores.add(vinculacionExpedienteSucesorDTO);	
			comentarioVinculacionExpedienteSucesor.setLength(0);
		}		
		vinculacionExpedienteDTO.setComentarios(comentarios);
		Collections.sort(expedientesAntecesores);
		Collections.sort(expedientesSucesores);
		vinculacionExpedienteDTO.setExpedientesAntecesores(expedientesAntecesores);
		vinculacionExpedienteDTO.setExpedientesSucesores(expedientesSucesores);
		return vinculacionExpedienteDTO;
	}

	@Override
	public void vincularExp(Usuario usuario, VinculacionExpedienteDTO vinculacionExpedienteDTO) throws Exception {
		String validaDatos = vinculacionExpedienteDTO.getValidaDatos();		
		if (validaDatos!=null && !validaDatos.isEmpty()) {
			throw new SgdpException(validaDatos, Level.WARN, true);
		}		
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(vinculacionExpedienteDTO.getNombreExpediente());
		InstanciaDeProceso instanciaDeProcesoAntecesor = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(vinculacionExpedienteDTO.getNombreExpAntecesor());		
		String tipoAccion;		
		if (instanciaDeProcesoAntecesor == null) {
			throw new SgdpException("No fue posible realizar la vinculaci\u00f3n ya que el expediente ingresado no existe", Level.WARN, true);
		}
		if (instanciaDeProcesoAntecesor.getFechaInicio().compareTo(instanciaDeProceso.getFechaInicio()) > 0) {
			throw new SgdpException("No fue posible realizar la vinculaci\u00f3n ya que el expediente antecesor " + instanciaDeProcesoAntecesor.getNombreExpediente() + " fue creado con posterioridad al expediente " + instanciaDeProceso.getNombreExpediente(), Level.WARN, true);
		}
		VinculacionExp vinculacioneExp = vinculacionExpDao.getVinculacionExpPorIdInstProcIdInstProcAntecesor(instanciaDeProceso.getIdInstanciaDeProceso(), 
				instanciaDeProcesoAntecesor.getIdInstanciaDeProceso());
		if (vinculacioneExp==null) {			
			VinculacionExp vinculacionExp = new VinculacionExp();
			vinculacionExp.setComentario(vinculacionExpedienteDTO.getComentario());
			vinculacionExp.setFechaVinculacion(new Date());
			vinculacionExp.setIdUsuario(vinculacionExpedienteDTO.getIdUsuario());
			vinculacionExp.setInstanciaDeProceso(instanciaDeProceso);
			vinculacionExp.setInstanciaDeProcesoAntecesor(instanciaDeProcesoAntecesor);			
			vinculacionExpDao.insert(vinculacionExp);
			tipoAccion = AccionType.INSERTA.getNombreAccion();
			vinculacionExpedienteDTO.setRespuestaVinculacion("Vinculaci\u00f3n realizada de manera exitosa");
		} else {
			vinculacioneExp.setComentario(vinculacionExpedienteDTO.getComentario());
			vinculacioneExp.setFechaVinculacion(new Date());
			vinculacioneExp.setIdUsuario(vinculacionExpedienteDTO.getIdUsuario());
			tipoAccion = AccionType.ACTUALIZA.getNombreAccion();			
			vinculacionExpedienteDTO.setRespuestaVinculacion("Vinculaci\u00f3n ya realizada, a la que se agreg\u00f3 el comentario");
		}
		HistoricoVinculacionExp historicoVinculacionExp = new HistoricoVinculacionExp();
		historicoVinculacionExp.setComentario(vinculacionExpedienteDTO.getComentario());
		historicoVinculacionExp.setFecha(new Date());
		historicoVinculacionExp.setIdUsuario(vinculacionExpedienteDTO.getIdUsuario());
		historicoVinculacionExp.setInstanciaDeProceso(instanciaDeProceso);
		historicoVinculacionExp.setInstanciaDeProcesoAntecesor(instanciaDeProcesoAntecesor);
		historicoVinculacionExp.setTipoAccion(tipoAccion);
		historicoVinculacionExp.setVigente(true);
		historicoVinculacionExpDao.insert(historicoVinculacionExp);
		try {
			if (tipoAccion.equals(AccionType.INSERTA.getNombreAccion())) {
				gestorMetadataCMSService.actualizaExpedientesAntecesores(usuario, instanciaDeProceso.getIdExpediente(), vinculacionExpedienteDTO.getNombreExpAntecesor());
			}			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void desVincularExp(Usuario usuario, VinculacionExpedienteDTO vinculacionExpedienteDTO) throws Exception {
		String validaDatos = vinculacionExpedienteDTO.getValidaDatos();		
		if (validaDatos!=null && !validaDatos.isEmpty()) {
			throw new SgdpException(validaDatos, Level.WARN, true);
		}		
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(vinculacionExpedienteDTO.getNombreExpediente());
		InstanciaDeProceso instanciaDeProcesoAntecesor = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(vinculacionExpedienteDTO.getNombreExpAntecesor());		
		if (instanciaDeProcesoAntecesor == null) {
			throw new SgdpException("No fue posible realizar la desvinculaci\u00f3n ya que el expediente ingresado no existe", Level.WARN, true);
		}
		VinculacionExp vinculacioneExp = vinculacionExpDao.getVinculacionExpPorIdInstProcIdInstProcAntecesor(instanciaDeProceso.getIdInstanciaDeProceso(), 
				instanciaDeProcesoAntecesor.getIdInstanciaDeProceso());
		List<HistoricoVinculacionExp> historicoVinculaciones = historicoVinculacionExpDao.
				getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor(instanciaDeProceso.getIdInstanciaDeProceso(), instanciaDeProcesoAntecesor.getIdInstanciaDeProceso());
		for (HistoricoVinculacionExp HistoricoVinculacionExp : historicoVinculaciones) {
			HistoricoVinculacionExp.setVigente(false);
		}
		HistoricoVinculacionExp historicoVinculacionExp = new HistoricoVinculacionExp();
		historicoVinculacionExp.setComentario(vinculacionExpedienteDTO.getComentario());
		historicoVinculacionExp.setFecha(new Date());
		historicoVinculacionExp.setIdUsuario(vinculacionExpedienteDTO.getIdUsuario());
		historicoVinculacionExp.setInstanciaDeProceso(instanciaDeProceso);
		historicoVinculacionExp.setInstanciaDeProcesoAntecesor(instanciaDeProcesoAntecesor);
		historicoVinculacionExp.setTipoAccion(AccionType.BORRA.getNombreAccion());
		historicoVinculacionExp.setVigente(false);
		historicoVinculacionExpDao.insert(historicoVinculacionExp);
		vinculacionExpDao.delete(vinculacioneExp);
		try {
			gestorMetadataCMSService.remueveExpedientesAntecesor(usuario, instanciaDeProceso.getIdExpediente(), vinculacionExpedienteDTO.getNombreExpAntecesor());
		} catch (Exception e) {
			throw e;
		}
	}

}

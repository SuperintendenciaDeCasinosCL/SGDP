package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ComplejidadExpedienteDao;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.ParametroRelacionTareaDao;
import cl.gob.scj.sgdp.dao.SeguimientoIntanciaProcesoDao;
import cl.gob.scj.sgdp.dto.ComplejidadExpedienteDTO;
import cl.gob.scj.sgdp.dto.ElementoResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.FechaEstadoInstanciaProcesoDTO;
import cl.gob.scj.sgdp.dto.HistorialProcesoDTO;
import cl.gob.scj.sgdp.dto.InfoProcesoExternoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.RespuestaActualizaMetaDataExpedienteDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.model.EtapaDeInstanciaDeProceso;
import cl.gob.scj.sgdp.model.HistorialProceso;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoSeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.ParametroRelacionTarea;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProcesoPK;
import cl.gob.scj.sgdp.model.SolicitudCreacionExp;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.tipos.AccionType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class InstanciaDeProcesoServiceImpl implements InstanciaDeProcesoService {

	private static final Logger log = Logger.getLogger(InstanciaDeProcesoServiceImpl.class);
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private SeguimientoIntanciaProcesoDao seguimientoIntanciaProcesoDao;
	
	@Autowired
	private ParametroRelacionTareaDao parametroRelacionTareaDao;
	
	@Autowired
	private ComplejidadExpedienteDao complejidadExpedienteDao;
	
	@Autowired
	private GestorMetadataCMSService gestorMetadataCMSService;
	
	@Override
	public List<HistorialProcesoDTO> getHistorialDelProceso(String idExpediente) {
		List<HistorialProceso> historialProcesoList = instanciaDeProcesoDao.getHistorialDelProceso(idExpediente);
		List<HistorialProcesoDTO> historialProcesoDTOList = new ArrayList<HistorialProcesoDTO>();
		for (HistorialProceso historialProceso : historialProcesoList) {
			HistorialProcesoDTO historialProcesoDTO = new HistorialProcesoDTO();
			BeanUtils.copyProperties(historialProceso, historialProcesoDTO);
			historialProcesoDTOList.add(historialProcesoDTO);
		}		
		return historialProcesoDTOList;
	}
	
	@Override
	public List<EtapaDeInstanciaDeProcesoDTO> getEtapasDeInstanciaDeProcesoPorIdExpediente(String idExpediente) {
		List<EtapaDeInstanciaDeProceso> etapasDeInstanciaDeProceso = instanciaDeProcesoDao.getEtapasDeInstanciaDeProcesoPorIdExpediente(idExpediente);
		List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO = new ArrayList<EtapaDeInstanciaDeProcesoDTO>();		
		Iterator<EtapaDeInstanciaDeProceso> it = etapasDeInstanciaDeProceso.iterator();
		while(it.hasNext()) {
			EtapaDeInstanciaDeProceso etapaDeInstanciaDeProceso = it.next();
			EtapaDeInstanciaDeProcesoDTO etapaDeInstanciaDeProcesoDTO = new EtapaDeInstanciaDeProcesoDTO();
			BeanUtils.copyProperties(etapaDeInstanciaDeProceso, etapaDeInstanciaDeProcesoDTO);			
			agregaEtapaDeInstanciaDeProcesoDTO(etapaDeInstanciaDeProcesoDTO, etapasDeInstanciaDeProcesoDTO);
		}		
		return etapasDeInstanciaDeProcesoDTO;
	}
	
	private void agregaEtapaDeInstanciaDeProcesoDTO(EtapaDeInstanciaDeProcesoDTO etapaDeInstanciaDeProcesoDTO, 
			List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO) {		
		ListIterator<EtapaDeInstanciaDeProcesoDTO> it = etapasDeInstanciaDeProcesoDTO.listIterator();
		boolean agrega = true;
		if(it.hasNext() == false) {
			log.debug("Cargo el primero");
			etapasDeInstanciaDeProcesoDTO.add(etapaDeInstanciaDeProcesoDTO);
		} else {
			while(it.hasNext()) {
				EtapaDeInstanciaDeProcesoDTO etapaDeInstanciaDeProcesoDTOF = (EtapaDeInstanciaDeProcesoDTO) it.next();
				if (etapaDeInstanciaDeProcesoDTO.getNombreEtapa().equals(etapaDeInstanciaDeProcesoDTOF.getNombreEtapa())
						&& etapaDeInstanciaDeProcesoDTO.getCodigoEstadoDeTarea() == 2){
					it.remove();
					it.add(etapaDeInstanciaDeProcesoDTO);
					agrega = false;
					log.debug("Removio y agrego");
				} else if (etapaDeInstanciaDeProcesoDTO.getNombreEtapa().equals(etapaDeInstanciaDeProcesoDTOF.getNombreEtapa())
						&& etapaDeInstanciaDeProcesoDTOF.getCodigoEstadoDeTarea() == 2) {
					agrega = false;
				}
		    }
			if (agrega == true) {
				log.debug("Solo agrego");
				etapasDeInstanciaDeProcesoDTO.add(etapaDeInstanciaDeProcesoDTO);
			}
		}		
	}
	
	@Override
	public List<HistorialProcesoDTO> getHistoricoDeInstDeTareaPorIdExpediente(String idExpediente) {
		List<HistoricoDeInstDeTarea> historicoDeInstDeTareaList = historicoDeInstDeTareaDao.getHistoricoDeInstDeTareaPorIdExpediente(idExpediente);
		List<HistorialProcesoDTO> historialProcesoDTOList = new ArrayList<HistorialProcesoDTO>();
		for (HistoricoDeInstDeTarea historicoDeInstDeTarea :historicoDeInstDeTareaList ) {
			HistorialProcesoDTO historialProcesoDTO = new HistorialProcesoDTO();
			historialProcesoDTO.setFechaMovimiento(historicoDeInstDeTarea.getFechaMovimiento());
			historialProcesoDTO.setIdDeUsuario(historicoDeInstDeTarea.getIdUsuarioOrigen());
			historialProcesoDTO.setComentario(historicoDeInstDeTarea.getComentario());
			historialProcesoDTO.setIdInstanciaDeTarea(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea());
			historialProcesoDTO.setNombreDeTarea(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getTarea().getNombreTarea());
			historialProcesoDTO.setAccion(historicoDeInstDeTarea.getAccionHistInstDeTarea().getNombreAccion());
			historialProcesoDTO.setNombreDelProceso(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getInstanciaDeProceso().getProceso().getNombreProceso());
			historialProcesoDTOList.add(historialProcesoDTO);
		}
		return historialProcesoDTOList;
	}
	
	@Override
	public void cargaInstanciaDeProcesoDTOPorIdExpediente(String idExpediente, InstanciaDeProcesoDTO instanciaDeProcesoDTO){
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(idExpediente);	
		SolicitudCreacionExp solicitudCreacionExp = instanciaDeProceso.getSolicitudCreacionExp();
		instanciaDeProcesoDTO.setIdProceso(instanciaDeProceso.getProceso().getIdProceso());
		List<ParametroRelacionTarea> parametrosRelacionTarea = parametroRelacionTareaDao.getParamTareaPorIdProc(instanciaDeProcesoDTO.getIdProceso());
		if (parametrosRelacionTarea!=null && parametrosRelacionTarea.size()>0) {
			instanciaDeProcesoDTO.setTieneParametroPorTarea(true);
		}
		if (solicitudCreacionExp!=null && solicitudCreacionExp.getComentario()!=null && !solicitudCreacionExp.getComentario().isEmpty()) {
			instanciaDeProcesoDTO.setComentarioSolicitudCreacionExpediente(solicitudCreacionExp.getComentario());
		}
		instanciaDeProcesoDTO.setNombreDeProceso(instanciaDeProceso.getProceso().getNombreProceso());
		BeanUtils.copyProperties(instanciaDeProceso, instanciaDeProcesoDTO);		
		instanciaDeProcesoDTO.setDiasHabilesMaxDuracion(instanciaDeProceso.getProceso().getDiasHabilesMaxDuracion());		
	}
	
	@Override
	public long buscaSiTieneSeguimiento(String idUsuario,
			long idInstanciaDeProceso) {
		log.debug("idUsuario: " + idUsuario + " -- idInstanciaDeProceso: " + idInstanciaDeProceso);
		return seguimientoIntanciaProcesoDao.buscaSiTieneSeguimiento(idUsuario, idInstanciaDeProceso);
	}

	@Override
	public void guardarSeguimiento(
			InstanciaDeProcesoDTO instanciaDeProcesoDTO,
			MensajeJson mensajeJson, String idUsuarioQueSigue, Usuario usuario, String nombreTipoNotificacion) {

		try {
			
			long tieneSeguimiento = buscaSiTieneSeguimiento(idUsuarioQueSigue, instanciaDeProcesoDTO.getIdInstanciaDeProceso());
			log.debug("tieneSeguimiento: " + tieneSeguimiento);
			
			if (tieneSeguimiento>0) {
				mensajeJson.setMensaje("OK");
				return;
			}
			
			InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdInstanciaDeProceso(instanciaDeProcesoDTO.getIdInstanciaDeProceso());
			
			SeguimientoIntanciaProceso seguimientoIntanciaProceso = new SeguimientoIntanciaProceso();
			
			SeguimientoIntanciaProcesoPK seguimientoIntanciaProcesoPK = new SeguimientoIntanciaProcesoPK();
			seguimientoIntanciaProcesoPK.setInstanciaDeProceso(instanciaDeProceso);
			seguimientoIntanciaProcesoPK.setIdUsuario(idUsuarioQueSigue);
					
			seguimientoIntanciaProceso.setId(seguimientoIntanciaProcesoPK);
			seguimientoIntanciaProceso.setTipoDeNotificacion(nombreTipoNotificacion);
			
			HistoricoSeguimientoIntanciaProceso historicoSeguimientoIntanciaProceso = new HistoricoSeguimientoIntanciaProceso();
			
			historicoSeguimientoIntanciaProceso.setIdInstanciaProceso(instanciaDeProcesoDTO.getIdInstanciaDeProceso());
			historicoSeguimientoIntanciaProceso.setAccion(AccionType.INSERTA.getNombreAccion());
			historicoSeguimientoIntanciaProceso.setFechaAccion(new Date());
			historicoSeguimientoIntanciaProceso.setIdUsuario(idUsuarioQueSigue);
			historicoSeguimientoIntanciaProceso.setIdUsuarioAccion(usuario.getIdUsuario());
			historicoSeguimientoIntanciaProceso.setTipoDeNotificacion(nombreTipoNotificacion);
			
			seguimientoIntanciaProcesoDao.guardarSeguimiento(seguimientoIntanciaProceso);
			seguimientoIntanciaProcesoDao.guardarSeguimientoHistorico(historicoSeguimientoIntanciaProceso);
			
			mensajeJson.setMensaje("OK");
			
		} catch(Exception e) {
			log.error(e);
			mensajeJson.setMensaje("ERROR");
		}
		
	}

	@Override
	public void dejarDeSeguimiento(
			InstanciaDeProcesoDTO instanciaDeProcesoDTO/*SeguimientoIntanciaProceso seguimientoIntanciaProceso*/,
			MensajeJson mensajeJson, String idUsuarioQueDejaDeSeguir, Usuario usuario) {
			
		log.debug("Entro al metodo SeguimientoIntanciaProceso");
		
		try {
			
			InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdInstanciaDeProceso(instanciaDeProcesoDTO.getIdInstanciaDeProceso());
						
			SeguimientoIntanciaProceso seguimientoIntanciaProceso = new SeguimientoIntanciaProceso();
			
			SeguimientoIntanciaProcesoPK seguimientoIntanciaProcesoPK = new SeguimientoIntanciaProcesoPK();
			seguimientoIntanciaProcesoPK.setInstanciaDeProceso(instanciaDeProceso);
			seguimientoIntanciaProcesoPK.setIdUsuario(usuario.getIdUsuario());
					
			seguimientoIntanciaProceso.setId(seguimientoIntanciaProcesoPK);
			
			// Guadar en historico de seguimiento
			HistoricoSeguimientoIntanciaProceso historicoSeguimientoIntanciaProceso = new HistoricoSeguimientoIntanciaProceso();
			historicoSeguimientoIntanciaProceso.setIdInstanciaProceso(instanciaDeProcesoDTO.getIdInstanciaDeProceso());
			historicoSeguimientoIntanciaProceso.setAccion(AccionType.BORRA.getNombreAccion());
			historicoSeguimientoIntanciaProceso.setFechaAccion(new Date());
			historicoSeguimientoIntanciaProceso.setIdUsuario(idUsuarioQueDejaDeSeguir);
			historicoSeguimientoIntanciaProceso.setIdUsuarioAccion(usuario.getIdUsuario());
			historicoSeguimientoIntanciaProceso.setTipoDeNotificacion(seguimientoIntanciaProceso.getTipoDeNotificacion());
			
			seguimientoIntanciaProcesoDao.guardarSeguimientoHistorico(historicoSeguimientoIntanciaProceso);
			
			// Borrar el seguimiento
			
			seguimientoIntanciaProcesoDao.borrarSeguimiento(seguimientoIntanciaProceso);
			
			log.debug("Se elimino el segumiento del proceso");
			
			mensajeJson.setMensaje("OK");

		} catch (Exception e) {
			log.error(e);
			mensajeJson.setMensaje("ERROR");
		}		
	}

	
	@Override
	public void buscaInstanciaDeProcesoDTOPorIdExpediente(ResultadoBusquedaDTO resultadoBusquedaDTO) {
	List<ElementoResultadoBusquedaDTO> listaElementoResultadoBusquedaDTO = new ArrayList<ElementoResultadoBusquedaDTO>();		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");		  
		for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO : resultadoBusquedaDTO.getElementosResultadoBusquedaDTO()) {	
			FechaEstadoInstanciaProcesoDTO fechaEstadoInstanciaProcesoDTO = new FechaEstadoInstanciaProcesoDTO();
		
			// Complejidad de expediente o instancia de proceso
			if (elementoResultadoBusquedaDTO.getComplejidad() == null || elementoResultadoBusquedaDTO.getComplejidad().isEmpty()) {
				ComplejidadExpedienteDTO complejidad = complejidadExpedienteDao.getLastByNombreExpediente(elementoResultadoBusquedaDTO.getNombreDeObjeto(), new ComplejidadExpedienteDTO());	
				elementoResultadoBusquedaDTO.setComplejidad(complejidad.getComplejidad());
				elementoResultadoBusquedaDTO.setJustificacionComplejidad(complejidad.getMotivoComplejidad());
			}// fin complejidad

			
			if (elementoResultadoBusquedaDTO.getTipoObjeto().equals("Documento")) {				
				fechaEstadoInstanciaProcesoDTO = instanciaDeProcesoDao.getFechaEstadoInstanciaDeProcesoPorIdExpediente(elementoResultadoBusquedaDTO.getIdExpedienteQueLoContiene());			
			} else {
				fechaEstadoInstanciaProcesoDTO = instanciaDeProcesoDao.getFechaEstadoInstanciaDeProcesoPorIdExpediente(elementoResultadoBusquedaDTO.getIdObjeto());
			}	
			if (fechaEstadoInstanciaProcesoDTO != null) {	
				try {
					if (fechaEstadoInstanciaProcesoDTO.getFechaInicio() != null) {
						elementoResultadoBusquedaDTO.setFechaInicioInstanciaDeProceso(formatter.parse(fechaEstadoInstanciaProcesoDTO.getFechaInicio()));
					}
				} catch (ParseException e) {
					elementoResultadoBusquedaDTO.setFechaInicioInstanciaDeProceso(null);
				}	
				try {
					if (fechaEstadoInstanciaProcesoDTO.getFechaFin() != null) {
						elementoResultadoBusquedaDTO.setFechaFinInstanciaDeProceso(formatter.parse(fechaEstadoInstanciaProcesoDTO.getFechaFin()));
					}
				} catch (ParseException e) {
					elementoResultadoBusquedaDTO.setFechaFinInstanciaDeProceso(null);	
				}
				elementoResultadoBusquedaDTO.setNombreEstadoDeProceso(fechaEstadoInstanciaProcesoDTO.getNombreEstadoDeProceso());
				listaElementoResultadoBusquedaDTO.add(elementoResultadoBusquedaDTO);
			}	
		}			
		resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().clear();
		resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().addAll(listaElementoResultadoBusquedaDTO);		
	}
	
	@Override
	public InstanciaDeProceso getInstanciaDeProcesoPorNombre(String expediente) {
		return instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(expediente);
	}
	
	@Override
	public InfoProcesoExternoDTO getInstanciaDeProcesoPorNombreAPI(String expediente) {
		InfoProcesoExternoDTO ipe = new InfoProcesoExternoDTO();
		Object obj = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpedienteAPI(expediente);
		if (obj != null ) {
			Object[] arr = (Object[]) obj;
			Date fechaInicio = (Date) arr[1];
			Date fechaPlazo = (Date) arr[2];
			ipe.setExpediente((String) arr[0]);
			ipe.setEstadoActual((String) arr[3]); 
			ipe.setEtapaActual((String) arr[4]);
			ipe.setNombreProceso((String) arr[5]);
			Long diasDesdeInicio = 0l;
			Long diasHastaFin = 0l;
			try {
				diasDesdeInicio = FechaUtil.diasEntreFechas(new Date(), fechaInicio);
				diasHastaFin = FechaUtil.diasEntreFechas(fechaPlazo, new Date());
				ipe.setDiasDesdeInicio(diasDesdeInicio);
				ipe.setDiasRestantesDePlazo(diasHastaFin);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			return null;
		}
		
		return ipe;
	}
	
	@Override
	public InstanciaDeProcesoDTO getInstanciaDeProcesoPorNombreExpediente(String nombreExpediente) {
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(nombreExpediente);	
		InstanciaDeProcesoDTO ipDTO = new InstanciaDeProcesoDTO();
		if (instanciaDeProceso == null ) {
			return null;
		}
		ipDTO.setNombreExpediente(instanciaDeProceso.getNombreExpediente());
		ipDTO.setIdExpediente(instanciaDeProceso.getIdExpediente());
		ipDTO.setNombreDeProceso(instanciaDeProceso.getProceso().getNombreProceso());
		ipDTO.setNombreUnidad(instanciaDeProceso.getUnidad().getNombreCompletoUnidad());
		ipDTO.setAsunto(instanciaDeProceso.getAsunto());
		ipDTO.setFechaInicio(instanciaDeProceso.getFechaInicio());
		ipDTO.setFechaFin(instanciaDeProceso.getFechaFin());
		return ipDTO;
	}
	
	@Override
	public boolean actualizaAsunto(InstanciaDeProcesoDTO instanciaDeProcesoDTO, Usuario usuario) {
		RespuestaActualizaMetaDataExpedienteDTO respuestaActualizaMetaDataExpedienteDTO;
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(instanciaDeProcesoDTO.getIdExpediente());
		ExpedienteRestActMetaDTO expedienteRestActMetaDTO = new ExpedienteRestActMetaDTO();
		expedienteRestActMetaDTO.setIdExpediente(instanciaDeProceso.getIdExpediente());
		expedienteRestActMetaDTO.setMateria(instanciaDeProcesoDTO.getAsunto());
		try {
			respuestaActualizaMetaDataExpedienteDTO = gestorMetadataCMSService.actualizaMetaDataExpediente(usuario, expedienteRestActMetaDTO);
			if (respuestaActualizaMetaDataExpedienteDTO.getRespuesta().equalsIgnoreCase("OK")) {
				instanciaDeProceso.setAsunto(instanciaDeProcesoDTO.getAsunto());
				return true;
			} else {
				log.info("No se pudo actualizar el asunto del expediente: " + instanciaDeProceso.getNombreExpediente() 
				+ ". Motivo: " + respuestaActualizaMetaDataExpedienteDTO!=null ? respuestaActualizaMetaDataExpedienteDTO.getRespuesta() : "respuestaActualizaMetaDataExpedienteDTO==null");
				return false;
			}
		} catch (Exception e) {
			log.error(SGDPUtil.getStackTrace(e));
			return false;
		}
	}
	
	@Override
	public InstanciaDeProcesoDTO getInstanciaDeProcesoDTOPorIdExpediente(String idExpediente) {
		InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
		cargaInstanciaDeProcesoDTOPorIdExpediente(idExpediente, instanciaDeProcesoDTO);
		return instanciaDeProcesoDTO;
	}

}

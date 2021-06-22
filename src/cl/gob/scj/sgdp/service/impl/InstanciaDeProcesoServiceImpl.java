package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.SeguimientoIntanciaProcesoDao;
import cl.gob.scj.sgdp.dto.ArchivoExpedienteDTO;
import cl.gob.scj.sgdp.dto.ElementoResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.ExpedienteArchNacDTO;
import cl.gob.scj.sgdp.dto.FechaEstadoInstanciaProcesoDTO;
import cl.gob.scj.sgdp.dto.HistorialProcesoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.ArchivosInstDeTareaMetadata;
import cl.gob.scj.sgdp.model.EtapaDeInstanciaDeProceso;
import cl.gob.scj.sgdp.model.HistorialProceso;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoSeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProcesoPK;
import cl.gob.scj.sgdp.model.SolicitudCreacionExp;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.tipos.AccionType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.UtilArchivosTarea;

@Service
@Transactional(rollbackFor = Throwable.class)
public class InstanciaDeProcesoServiceImpl implements InstanciaDeProcesoService {

	private static final Logger log = Logger.getLogger(InstanciaDeProcesoServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private SeguimientoIntanciaProcesoDao seguimientoIntanciaProcesoDao;

//	@Autowired
//	private GestorDeDocumentosCMSService gestorDeDocumentosCMSService;
	
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
		/*for (EtapaDeInstanciaDeProceso etapaDeInstanciaDeProceso : etapasDeInstanciaDeProceso) {
			EtapaDeInstanciaDeProcesoDTO etapaDeInstanciaDeProcesoDTO = new EtapaDeInstanciaDeProcesoDTO();
			BeanUtils.copyProperties(etapaDeInstanciaDeProceso, etapaDeInstanciaDeProcesoDTO);			
			agregaEtapaDeInstanciaDeProcesoDTO(etapaDeInstanciaDeProcesoDTO, etapasDeInstanciaDeProcesoDTO);
		}*/		
		return etapasDeInstanciaDeProcesoDTO;
	}
	
	private void agregaEtapaDeInstanciaDeProcesoDTO(EtapaDeInstanciaDeProcesoDTO etapaDeInstanciaDeProcesoDTO, 
			List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO) {		
		//Iterator<EtapaDeInstanciaDeProcesoDTO> it = etapasDeInstanciaDeProcesoDTO.iterator();
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
					//etapasDeInstanciaDeProcesoDTO.remove(etapaDeInstanciaDeProcesoDTOF);
					it.remove();
					it.add(etapaDeInstanciaDeProcesoDTO);
					//etapasDeInstanciaDeProcesoDTO.add(etapaDeInstanciaDeProcesoDTO);
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
		if (solicitudCreacionExp!=null && solicitudCreacionExp.getComentario()!=null && !solicitudCreacionExp.getComentario().isEmpty()) {
			instanciaDeProcesoDTO.setComentarioSolicitudCreacionExpediente(solicitudCreacionExp.getComentario());
		}
		instanciaDeProcesoDTO.setNombreDeProceso(instanciaDeProceso.getProceso().getNombreProceso());
		BeanUtils.copyProperties(instanciaDeProceso, instanciaDeProcesoDTO);
		instanciaDeProcesoDTO.setDiasHabilesMaxDuracion(instanciaDeProceso.getProceso().getDiasHabilesMaxDuracion());
		if (instanciaDeProceso.getTipo() != null) {
			instanciaDeProcesoDTO.setIdTipo(Long.toString(instanciaDeProceso.getTipo().getIdTipo()));
		}
		
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
	public List<ExpedienteArchNacDTO> getMetadataListaExpediente(EnviarArchivoNacionalDTO enviarDTO, Usuario usuario)
			throws ArchivoNacionalException {
		List<ExpedienteArchNacDTO> list = new ArrayList<ExpedienteArchNacDTO>();
		ExpedienteArchNacDTO dto = null;
		try {
			List<InstanciaDeProceso> instProcesoList = this.instanciaDeProcesoDao.getMetadataListaExpediente(enviarDTO);
			// aca se deberian filtrar
			for (InstanciaDeProceso ins: instProcesoList) {
				//log.info("ID expediente:"+ins.getIdExpediente());
				List<InstanciaDeTarea> instanciasDeTareas = ins.getInstanciasDeTareas();
				List<ArchivosInstDeTarea> archivosTotales = new ArrayList<ArchivosInstDeTarea>();
				for (InstanciaDeTarea tar: instanciasDeTareas) {
					List<ArchivosInstDeTarea> archivosInstDeTarea = tar.getArchivosInstDeTarea();
					if (archivosInstDeTarea!=null && !archivosInstDeTarea.isEmpty())
						archivosTotales.addAll(archivosInstDeTarea);
					//tar.setArchivosInstDeTarea(new ArrayList<ArchivosInstDeTarea>());
				}
				
				List<ArchivosInstDeTarea> masiveFix = UtilArchivosTarea.masiveFix(archivosTotales);
				ins.setArchivosInstancia(masiveFix);
				//instanciasDeTareas.get(0).setArchivosInstDeTarea(masiveFix);
			}
			
			for (InstanciaDeProceso entity : instProcesoList) {
				
				dto = new ExpedienteArchNacDTO();
	
				if (entity.getAcceso() != null) {
					dto.setNivelAcceso(entity.getAcceso().getValorAccesoChar());
				}
				if (entity.getInstanciaProcesoMetadata() != null) {
					dto.setApellidoMaternoInteresado(entity.getInstanciaProcesoMetadata().getApellidoMaterno());
					dto.setApellidoPaternoInteresado(entity.getInstanciaProcesoMetadata().getApellidoPaterno());
					dto.setComuna(entity.getInstanciaProcesoMetadata().getComuna());
					dto.setEtiquetas(entity.getInstanciaProcesoMetadata().getEtiquetas());
					dto.setFechaCreacionExpediente(FechaUtil.toFormat(entity.getInstanciaProcesoMetadata().getFechaCreacion(), FechaUtil.simpleDateFormatForm));
					//Que como la fecha de finalizacion de la instancia
					dto.setFechaFinalizacionExpediente(FechaUtil.toFormat(entity.getFechaFin(), FechaUtil.simpleDateFormatForm));
					dto.setNombreInteresado(entity.getInstanciaProcesoMetadata().getNombreInteresado());
					dto.setRegion(entity.getInstanciaProcesoMetadata().getRegion());
					dto.setRutInteresado(entity.getInstanciaProcesoMetadata().getRut());
					dto.setTituloExpediente(entity.getInstanciaProcesoMetadata().getTitulo());
					dto.setVolumenExpediente(0);
				}
				
				if (entity.getArchivosInstancia() != null && !entity.getArchivosInstancia().isEmpty()) {
					List<ArchivoExpedienteDTO> listaArchivos = new ArrayList<ArchivoExpedienteDTO>();
					ArchivoExpedienteDTO archivoDTO = null;
					
					for (ArchivosInstDeTarea archivo : entity.getArchivosInstancia()) {
						if (archivo.getArchivosInstDeTareaMetadata() != null) {
							archivoDTO = new ArchivoExpedienteDTO();
							archivoDTO.setIdArchivoCms(archivo.getIdArchivoCms());
							
							archivoDTO.setAutor(archivo.getArchivosInstDeTareaMetadata().getAutor());
							archivoDTO.setDestinatario(archivo.getArchivosInstDeTareaMetadata().getDestinatarios());
							archivoDTO.setDocumentoDigitalizado(archivo.getArchivosInstDeTareaMetadata().getDigitalizado().toString());
							archivoDTO.setFormato(archivo.getMimeType());
							archivoDTO.setIdentificador(archivo.getIdArchivoCms());
							archivoDTO.setFechaDocumento(FechaUtil.toFormat(archivo.getFechaDocumento(), FechaUtil.simpleDateFormatForm));
							archivoDTO.setNombreArchivo(
									"/" + entity.getNombreExpediente() + "/" + archivo.getNombreArchivo());
							archivoDTO.setTitulo(archivo.getArchivosInstDeTareaMetadata().getTitulo());
							listaArchivos.add(archivoDTO);
						}
					}
					
					dto.setListaArchivos(listaArchivos);
				}
				
				/*if (entity.getInstanciasDeTareas() != null && !entity.getInstanciasDeTareas().isEmpty()) {
					List<ArchivoExpedienteDTO> listaArchivos = new ArrayList<ArchivoExpedienteDTO>();
					ArchivoExpedienteDTO archivoDTO = null;
					//Recorre lista de tareas del proceso
					for (InstanciaDeTarea instTarea : entity.getInstanciasDeTareas()) {
						if (instTarea.getArchivosInstDeTarea() != null && !instTarea.getArchivosInstDeTarea().isEmpty()) {
							//Recorre lista de archivos de cada tarea
							for (ArchivosInstDeTarea archivo : instTarea.getArchivosInstDeTarea()) {
								if (archivo.getArchivosInstDeTareaMetadata() != null) {
									archivoDTO = new ArchivoExpedienteDTO();
									archivoDTO.setIdArchivoCms(archivo.getIdArchivoCms());
									
									archivoDTO.setAutor(archivo.getArchivosInstDeTareaMetadata().getAutor());
									archivoDTO.setDestinatario(archivo.getArchivosInstDeTareaMetadata().getDestinatarios());
									archivoDTO.setDocumentoDigitalizado(archivo.getArchivosInstDeTareaMetadata().getDigitalizado().toString());
									archivoDTO.setFormato(archivo.getMimeType());
									archivoDTO.setIdentificador(archivo.getIdArchivoCms());
									archivoDTO.setFechaDocumento(FechaUtil.toFormat(archivo.getFechaDocumento(), FechaUtil.simpleDateFormatForm));
									archivoDTO.setNombreArchivo(
											"/" + entity.getNombreExpediente() + "/" + archivo.getNombreArchivo());
									archivoDTO.setTitulo(archivo.getArchivosInstDeTareaMetadata().getTitulo());
									listaArchivos.add(archivoDTO);
								}
							}
						}
					}
					dto.setListaArchivos(listaArchivos);
				}*/
				
				list.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlObtenerExpedientes"));
		}
		return list;
	}

}

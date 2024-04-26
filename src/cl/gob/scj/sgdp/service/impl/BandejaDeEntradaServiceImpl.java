

package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dao.EstadoDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.MacroProcesoDao;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.UsuarioRolDao;
import cl.gob.scj.sgdp.dto.AnadirAntecedenteDTO;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.EstadoDeProcesoDTO;
import cl.gob.scj.sgdp.dto.FiltroExpedienteDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.SuggestionsDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.Autor;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.EstadoDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.MacroProceso;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.ParametroService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class BandejaDeEntradaServiceImpl implements BandejaDeEntradaService {
	
	private static final Logger log = Logger.getLogger(BandejaDeEntradaServiceImpl.class);

	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private MacroProcesoDao macroProcesoDao;
		
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private EstadoDeProcesoDao estadoDeProcesoDao;
	
	@Autowired
	private TareaDao tareaDao;	
	
	@Autowired
	private UsuarioRolDao usuarioRolDao;
	
	@Autowired
	private AutorDao autorDao;	
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private EmailService emailService;
	
	@Resource(name = "configProps")
	private Properties configProps;
		
	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareaPorIdUsrNombreEstadoTarea(
			Usuario usuario, String nombreEstadoDeTarea, List<InstanciaDeTareaDTO> instanciasDeTareasDTO) throws IOException {	
		log.debug("Inicio instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTarea");
		List<InstanciaDeTarea> instanciasDeTareas = instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTarea(usuario.getIdUsuario(), 
				nombreEstadoDeTarea);		
		log.debug("Fin instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTarea");
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareas) { 
			//log.debug("Recorriendo instancias de tareas...");
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico());
			//instanciaDeTareaDTO.cargaAdvertenciaPlazoProceso(parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico());
			//log.debug(instanciaDeTarea.toString());
			//log.debug(instanciaDeTareaDTO.toString());
			instanciasDeTareasDTO.add(instanciaDeTareaDTO);			
		}
		return instanciasDeTareasDTO;
	}

	@Override
	public List<MacroProcesoDTO> getTodosLosMacroProcesos(List<MacroProcesoDTO> macroProcesosDTO) {
		List<MacroProceso> macroProcesos  = macroProcesoDao.getTodosLosMacroProcesos();
		for (MacroProceso macroProceso : macroProcesos) {
			MacroProcesoDTO macroProcesoDTO = new MacroProcesoDTO(macroProceso.getIdMacroProceso(), macroProceso.getDescripcionMacroProceso(), macroProceso.getNombreMacroProceso());
			macroProcesosDTO.add(macroProcesoDTO);
		}		
		return macroProcesosDTO;
	}
	
	@Override
	public EstadoDeProcesoDTO getEstadoDeProcesoPorId(long idEstadoDeProceso) {
		EstadoDeProceso estadosDeProceso = estadoDeProcesoDao.getEstadoDeProcesoPorId(idEstadoDeProceso);
		return new EstadoDeProcesoDTO(estadosDeProceso.getIdEstadoDeProceso(), estadosDeProceso.getNombreEstadoDeProceso(), estadosDeProceso.getCodigoEstadoDeProceso());
	}
	
	
	@Override
	public List<TareaDTO> getTareasPorVigenciaPorIdProceso(long idProceso) {
		List<Tarea> tareas = tareaDao.getTareasPorVigenciaPorIdProceso(idProceso, true);
		List<TareaDTO> tareasDTO = new ArrayList<TareaDTO>();
		for (Tarea tarea : tareas) {
			TareaDTO tareaDTO = new TareaDTO(tarea.getIdTarea(), tarea.getDescripcionTarea(), tarea.getNombreTarea(), tarea.getDiasHabilesMaxDuracion(), tarea.getOrden(), tarea.getVigente()
					, tarea.getIdDiagrama());
			tareasDTO.add(tareaDTO);
		}
		return tareasDTO;
	}	
	
	@Override
	public List<TareaDTO> getTareasPorIdProceso(long idProceso) {
		List<Tarea> tareas = tareaDao.getTareasPorIdProceso(idProceso);
		List<TareaDTO> tareasDTO = new ArrayList<TareaDTO>();
		for (Tarea tarea : tareas) {
			TareaDTO tareaDTO = new TareaDTO(tarea.getIdTarea(), tarea.getDescripcionTarea(), tarea.getNombreTarea(), tarea.getDiasHabilesMaxDuracion(), tarea.getOrden(), 
					tarea.getVigente(), tarea.getIdDiagrama());
			tareasDTO.add(tareaDTO);
		}
		return tareasDTO;
	}

	@Override
	public List<InstanciaDeProceso> getTodasLasInstanciasDeProcesos() {
		return instanciaDeProcesoDao.getTodasLasInstanciasDeProcesos();
	}
	
	
	@Override
	public List<SuggestionsDTO> getUsuariosSugeridos(String idUsuarioLike, List<SuggestionsDTO> listaDeUsuariosSugerida) {
		List<UsuarioRol> usuariosRolLike = usuarioRolDao.getUsuariosRolesPorIdUsuarioLike(idUsuarioLike, true);
		for (UsuarioRol usuarioRol: usuariosRolLike) {
			SuggestionsDTO suggestionsDTO = new SuggestionsDTO(usuarioRol.getIdUsuario(), usuarioRol.getIdUsuario());
			listaDeUsuariosSugerida.add(suggestionsDTO);
		}		
		return listaDeUsuariosSugerida;
	}

	@Override
	public List<AutorDTO> getTodosLosAutores() {
		List<AutorDTO> autoresDTO = new ArrayList<AutorDTO>();
		List<Autor> autores = autorDao.getTodosLosAutores();
		for (Autor autor : autores) {
			AutorDTO autorDTO = new AutorDTO(autor.getIdAutor(), autor.getNombreAutor());
			autoresDTO.add(autorDTO);
		}
		return autoresDTO;
	}

	/*@Override
	public List<TipoDeDocumentoDTO> getTodosLosTiposDeDocumentos(List<TipoDeDocumentoDTO> tiposDeDocumentosDTO) {
		List<TipoDeDocumento> tiposDeDocumentos = tipoDeDocumentoDao.getTodosLosTiposDeDocumentos();
		for(TipoDeDocumento tipoDeDocumento: tiposDeDocumentos) {
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(tipoDeDocumento.getIdTipoDeDocumento(), tipoDeDocumento.getNombreDeTipoDeDocumento());
			tiposDeDocumentosDTO.add(tipoDeDocumentoDTO);
		}
		return tiposDeDocumentosDTO;
	}*/
	
	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareaEnEjecucion(Usuario usuario, long idEstadoFinalizada, List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException {
		log.debug("idUsuario: " + usuario.getIdUsuario());
		log.debug("idEstadoFinalizada: " + idEstadoFinalizada);
		List<InstanciaDeTarea> instanciasDeTareas = instanciaDeTareaDao.getInstanciasDeTareasEnEjecucion(usuario.getIdUsuario(), idEstadoFinalizada);
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareas) { 
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
			parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico()/*,	instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion()*/);
			log.debug(instanciaDeTarea.toString());
			log.debug(instanciaDeTareaDTO.toString());
			instanciasDeTareasDTOEnEjecucion.add(instanciaDeTareaDTO);			
		}
		return instanciasDeTareasDTOEnEjecucion;
	}
	
	@Override
	public List<InstanciaDeTareaDTO> getTodasInstanciasDeTareasEnEjecucion(List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException {
		
		log.debug("Inicio instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucion()");
		List<InstanciaDeTarea> instanciasDeTareas = instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucion();
		log.debug("Fin instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucion()");
		
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareas) { 
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));			
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico()/*,
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion()*/);		
			instanciasDeTareasDTOEnEjecucion.add(instanciaDeTareaDTO);			
		}
		
		return instanciasDeTareasDTOEnEjecucion;
		
	}
	
	
	@Override
	public List<InstanciaDeTareaDTO> getNotificacionesSeguimientosPorUsuario(List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion,Usuario usuario) throws IOException {
		
		log.debug("Inicio instanciaDeTareaDao.getNotificacionesSeguimientosPorUsuario()");
		List<InstanciaDeTarea> instanciasDeTareas = instanciaDeTareaDao.getNotificacionesSeguimientosPorUsuario(usuario.getIdUsuario());
		log.debug("Fin instanciaDeTareaDao.getNotificacionesSeguimientosPorUsuario()");
		
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareas) { 
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico()/*,
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion()*/);		
			instanciasDeTareasDTOEnEjecucion.add(instanciaDeTareaDTO);			
		}
		
		return instanciasDeTareasDTOEnEjecucion;
		
	}	
	
	@Override
	public List<InstanciaDeTareaDTO> getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(long idUnidad, List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException {
		log.debug("Inicio instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(idUnidad)");
		List<InstanciaDeTarea> instanciasDeTareas = instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(idUnidad);
		log.debug("Fin instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(idUnidad)");
		log.debug("Cargando InstanciaDeTareas en InstanciaDeTareaDTOs");
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareas) { 			
			log.debug(instanciaDeTarea.toString());
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico()/*,
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion()*/);		
			instanciasDeTareasDTOEnEjecucion.add(instanciaDeTareaDTO);			
		}		
		return instanciasDeTareasDTOEnEjecucion;		
	}
	
	@Override
	public List<InstanciaDeTareaDTO> getTodasInstanciasDeTareasEnEjecucionPorIdUnidades(Usuario usuario, List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException {
		Set<Long> idUnidades = usuario.getIdUnidades();
		for (Long idUnidad : idUnidades) {
			List<InstanciaDeTarea> instanciasDeTareas = instanciaDeTareaDao.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(idUnidad);
			for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareas) { 			
				log.debug(instanciaDeTarea.toString());
				InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
				instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
				instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
				instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
						parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico()/*,
						instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion()*/);		
				instanciasDeTareasDTOEnEjecucion.add(instanciaDeTareaDTO);			
			}	
		}
		return instanciasDeTareasDTOEnEjecucion;
	}

	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro(Usuario usuario,
			String nombreEstadoDeTarea, List<InstanciaDeTareaDTO> instanciasDeTareasDTO,
			FiltroExpedienteDTO filtroExpedienteDTO) throws IOException {

		log.debug("Inicio getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro");

		List<InstanciaDeTarea> instanciasDeTareas = new ArrayList<InstanciaDeTarea>();

		if (filtroExpedienteDTO.getRespuestaEspera() == true && filtroExpedienteDTO.getTrabajoInterno() == true) {			

			instanciasDeTareas = instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTarea(usuario.getIdUsuario(), nombreEstadoDeTarea);
			
			log.debug("Cargo desde getInstanciasDeTareaPorIdUsrNombreEstadoTarea");
			log.debug("instanciasDeTareas.size(): " + instanciasDeTareas.size());

		} else if ((filtroExpedienteDTO.getRespuestaEspera() == true
				&& filtroExpedienteDTO.getTrabajoInterno() == false)
				|| (filtroExpedienteDTO.getRespuestaEspera() == false
						&& filtroExpedienteDTO.getTrabajoInterno() == true)) {
			// Mostrar solo el que esta con True
			boolean buscarRespuestaEspera = false;

			if (filtroExpedienteDTO.getRespuestaEspera() == true) {
				buscarRespuestaEspera = true;
			} else {
				buscarRespuestaEspera = false;
			}

			instanciasDeTareas = instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTareaPorEsperaRespuesta(
					usuario.getIdUsuario(), nombreEstadoDeTarea, buscarRespuestaEspera);

		} else if (filtroExpedienteDTO.getRespuestaEspera() == false
				&& filtroExpedienteDTO.getTrabajoInterno() == false) {
			boolean buscarRespuestaEspera = false;

			instanciasDeTareas = instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTareaPorEsperaRespuesta(
					usuario.getIdUsuario(), nombreEstadoDeTarea, buscarRespuestaEspera);

		}

		log.debug("instanciasDeTareas.size(): " + instanciasDeTareas.size());
		log.debug("instanciasDeTareas.isEmpty(): " + instanciasDeTareas.isEmpty());
		
		Map<Long, String> estadoInstaProcMap = instanciaDeTareaDao.getEstadosDeInstProcPorIdUsrNombreEstadoTarea(usuario.getIdUsuario(), nombreEstadoDeTarea);
		
		for (InstanciaDeTarea instanciaDeTarea : instanciasDeTareas) {
			log.debug("Entro al for de instanciasDeTareas");
			log.debug(instanciaDeTarea.toString());
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();			
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.setNombreEstadoHomologadoDeInstProceso(estadoInstaProcMap.get(instanciaDeTarea.getIdInstanciaDeTarea()));
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(/*,
							instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion(*/));
			instanciaDeTareaDTO.cargaAdvertenciaPlazoProceso(parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(/*,
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion(*/));
			/*List<ParametroRelacionTarea> parametrosRelacionTarea = parametroRelacionTareaDao.getParamTareaPorIdProc(instanciaDeTareaDTO.getIdProceso());
			if (parametrosRelacionTarea!=null && parametrosRelacionTarea.size()>0) {
				instanciaDeTareaDTO.setProcesoTieneRdsSnc(true);
			}*/
			instanciasDeTareasDTO.add(instanciaDeTareaDTO);
		}

		log.debug("instanciasDeTareasDTO.size(): " + instanciasDeTareasDTO.size());
		return instanciasDeTareasDTO;
	}

	@Override
	public RespuestaMailDTO preparaEnvioMail(List<RespuestaMailDTO> listaRespuestaMailDto,
			AnadirAntecedenteDTO anadirAntecedenteDTO, Usuario usuario) {

		List<String> listaDeUsuariosAsignados = new ArrayList<String>();

		RespuestaMailDTO respuestaMailDTO = new RespuestaMailDTO();
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao
				.getInstanciaDeTareaPorIdInstanciaDeTarea(anadirAntecedenteDTO.getIdInstanciaDeTarea());

		for (UsuarioAsignado usuariosAsignados : instanciaDeTarea.getUsuariosAsignados()) {
			listaDeUsuariosAsignados.add(usuariosAsignados.getId().getIdUsuario());
		}

		List<UsuarioRol> listaUsuarioTRol = usuarioRolDao.getUsuarioRolesPorIdRolIdUnidad(2,
				instanciaDeTarea.getInstanciaDeProceso().getProceso().getUnidad().getIdUnidad(), true);

		for (UsuarioRol usuarioRol : listaUsuarioTRol) {
			listaDeUsuariosAsignados.add(usuarioRol.getIdUsuario());
		}

		// String nombre = "vmenares";
		// listaDeUsuariosAsignados.add(nombre);

		// Generar lista de los documentos que estan en estado subido

		String documentosSubidosCorrectamente = "";
		Integer contadorDocumentosSubidos = 0;
		for (RespuestaMailDTO respuestaMailDTODocu : listaRespuestaMailDto) {

			if (respuestaMailDTODocu.getRespuesta().equals("SUBIDO")) {
				documentosSubidosCorrectamente = documentosSubidosCorrectamente + "<li>"
						+ respuestaMailDTODocu.getNombreArchivo() + "</li>";
				contadorDocumentosSubidos++;
			}

		}

		ParametroDTO parametroDTOBodyMensaje = parametroService
				.getParametroPorID(Constantes.ID_PARAM_CORREO_NOTIFICACION_DOCUMENTOS_CUALQUIER_ETAPA);
		ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
		String bodyMensaje = parametroDTOBodyMensaje.getValorParametroChar();
		bodyMensaje = bodyMensaje.replace("$proceso",
				instanciaDeTarea.getInstanciaDeProceso().getProceso().getNombreProceso());
		bodyMensaje = bodyMensaje.replace("$Expediente",
				instanciaDeTarea.getInstanciaDeProceso().getNombreExpediente());
		bodyMensaje = bodyMensaje.replace("$documentos", documentosSubidosCorrectamente);
		bodyMensaje = bodyMensaje.replace("$urlSGDP", parametroDTOUrlSGDP.getValorParametroChar());

		if (contadorDocumentosSubidos >= 1) {
			try {
				emailService.enviarMailConAsunto(usuario, instanciaDeTarea, listaDeUsuariosAsignados, bodyMensaje,
						"[SGDP] Nuevos antecedentes en Expediente "
								+ instanciaDeTarea.getInstanciaDeProceso().getNombreExpediente());
				respuestaMailDTO.setRespuesta("OK");
			} catch (SgdpException e) {
				respuestaMailDTO.setRespuesta("ERROR");
				respuestaMailDTO.setCodigoError(e.getMessage());
				e.printStackTrace();
			}
		} else {
			respuestaMailDTO.setRespuesta("ERROR");
			respuestaMailDTO.setCodigoError("NO se subieron documentos correctamente");
		}

		return respuestaMailDTO;
	}
	
	@Override
	public List<TareaDTO> getTareasPorCodigoProceso(String codigoProceso) {
		List<Tarea> tareas = tareaDao.getTareasPorCodigoProceso(codigoProceso);
		List<TareaDTO> tareasDTO = new ArrayList<TareaDTO>();
		for (Tarea tarea : tareas) {
			TareaDTO tareaDTO = new TareaDTO(tarea.getIdTarea(), tarea.getDescripcionTarea(), tarea.getNombreTarea(), tarea.getDiasHabilesMaxDuracion(), tarea.getOrden(), 
					tarea.getVigente(), tarea.getIdDiagrama());
			tareasDTO.add(tareaDTO);
		}
		return tareasDTO;
	}
		

	@Override
	public List<TipoDeDocumentoDTO> getTodosLosTiposDeDocumentosPorIdTarea(long idTarea) {
		List<TipoDeDocumentoDTO> tiposDeDocumentoDTO = new ArrayList<TipoDeDocumentoDTO>();
		Tarea tarea = tareaDao.getTareaPorIdTarea(idTarea);
		List<DocumentoDeSalidaDeTarea> documentosDeSalida = tarea.getDocumentosDeSalidasDeTarea();
		for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea : documentosDeSalida) {
			TipoDeDocumento tipoDeDocumento = documentoDeSalidaDeTarea.getId().getTipoDeDocumento();
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO();
			BeanUtils.copyProperties(tipoDeDocumento, tipoDeDocumentoDTO);
			tiposDeDocumentoDTO.add(tipoDeDocumentoDTO);
		}
		return tiposDeDocumentoDTO;
	}

}
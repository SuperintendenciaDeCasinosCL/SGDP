package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AccesoDao;
import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dao.EstadoDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.MacroProcesoDao;
import cl.gob.scj.sgdp.dao.ParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.TextoParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.TipoParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.UsuarioRolDao;
import cl.gob.scj.sgdp.dto.AccesoDTO;
import cl.gob.scj.sgdp.dto.AnadirAntecedenteDTO;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.EstadoDeProcesoDTO;
import cl.gob.scj.sgdp.dto.FiltroExpedienteDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.SuggestionsDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.TipoParametroDeTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.Acceso;
import cl.gob.scj.sgdp.model.Autor;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.EstadoDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.MacroProceso;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TextoParametroDeTarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
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
	private AccesoDao accesoDao;	
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ParametroDeTareaDao parametroDeTareaDao;
	
	@Autowired
	private TextoParametroDeTareaDao textoParametroDeTareaDao;
	
	@Autowired
	private TipoParametroDeTareaDao tipoParametroDeTareaDao;
	
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
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());
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

	@Override
	public List<AccesoDTO> getTodosLosAccesos() {
		List<AccesoDTO> accesosDTO = new ArrayList<AccesoDTO>();
		List<Acceso> accesos = accesoDao.getTodosLosAccesos();
		for (Acceso acceso : accesos) {
			AccesoDTO accesoDTO = new AccesoDTO(acceso.getIdAcceso(), acceso.getNombreAcceso(), acceso.getValorAccesoChar());
			accesosDTO.add(accesoDTO);
		}
		return accesosDTO;
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
			parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
			instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());
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
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());		
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
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());		
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
					parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
					instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());		
			instanciasDeTareasDTOEnEjecucion.add(instanciaDeTareaDTO);			
		}		
		return instanciasDeTareasDTOEnEjecucion;		
	}

	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro(Usuario usuario,
			String nombreEstadoDeTarea, List<InstanciaDeTareaDTO> instanciasDeTareasDTO,
			FiltroExpedienteDTO filtroExpedienteDTO) throws IOException {

		log.debug("Inicio instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro");

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

		log.debug("Fin instanciaDeTareaDao.getInstanciasDeTareaPorIdUsrNombreEstadoTarea");
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
			instanciaDeTareaDTO.cargaAdvertenciaDePlazo(parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
							instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());			
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
	public List<ParametroDeTareaDTO> getParametrosDeTareaDTOPorIdTarea(long idTarea) {
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = new ArrayList<ParametroDeTareaDTO>();
		List<ParametroDeTarea> parametrosDeTareaPorIdTarea = parametroDeTareaDao.getParametrosDeTareaPorIdTarea(idTarea);
		for (ParametroDeTarea parametroDeTarea : parametrosDeTareaPorIdTarea) {
			ParametroDeTareaDTO parametroDeTareaDTO = new ParametroDeTareaDTO();
			parametroDeTareaDTO.setIdParamTarea(parametroDeTarea.getIdParamTarea());
			parametroDeTareaDTO.setNombreParamTarea(parametroDeTarea.getNombreParamTarea());			
			TipoParametroDeTarea tipoParametroDeTarea = parametroDeTarea.getTipoParametroDeTarea();
			TipoParametroDeTareaDTO tipoParametroDeTareaDTO = new TipoParametroDeTareaDTO();
			tipoParametroDeTareaDTO.setIdTipoParametroDeTarea(tipoParametroDeTarea.getIdTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setNombreTipoParametroDeTarea(tipoParametroDeTarea.getNombreTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setTextoHtml(tipoParametroDeTarea.getTextoHtml());
			parametroDeTareaDTO.setTipoParametroDeTareaDTO(tipoParametroDeTareaDTO);
			parametrosDeTareaDTOPorIdTarea.add(parametroDeTareaDTO);
		}		
		return parametrosDeTareaDTOPorIdTarea;
	}
	
	@Override
	public List<ParametroDeTareaDTO> getParametrosDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = new ArrayList<ParametroDeTareaDTO>();
		List<ParametroDeTarea> parametrosDeTareaPorIdTarea = parametroDeTareaDao.getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for (ParametroDeTarea parametroDeTarea : parametrosDeTareaPorIdTarea) {
			ParametroDeTareaDTO parametroDeTareaDTO = new ParametroDeTareaDTO();
			parametroDeTareaDTO.setIdParamTarea(parametroDeTarea.getIdParamTarea());
			parametroDeTareaDTO.setNombreParamTarea(parametroDeTarea.getNombreParamTarea());
			parametroDeTareaDTO.setTitulo(parametroDeTarea.getTitulo());
			TipoParametroDeTarea tipoParametroDeTarea = parametroDeTarea.getTipoParametroDeTarea();
			TipoParametroDeTareaDTO tipoParametroDeTareaDTO = new TipoParametroDeTareaDTO();
			tipoParametroDeTareaDTO.setIdTipoParametroDeTarea(tipoParametroDeTarea.getIdTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setNombreTipoParametroDeTarea(tipoParametroDeTarea.getNombreTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setTextoHtml(tipoParametroDeTarea.getTextoHtml());
			parametroDeTareaDTO.setTipoParametroDeTareaDTO(tipoParametroDeTareaDTO);
			tipoParametroDeTareaDTO.setComenta(tipoParametroDeTarea.getComenta());
			parametrosDeTareaDTOPorIdTarea.add(parametroDeTareaDTO);
		}		
		return parametrosDeTareaDTOPorIdTarea;
	}
	
	@Override
	public Map<String, List<ParametroDeTareaDTO>> getMapParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea) {	
		
		Map<String, List<ParametroDeTareaDTO>> mapParametrosDeTareaDTOTitulo = new HashMap<String, List<ParametroDeTareaDTO>>();
		
		List<ParametroDeTareaDTO> parametrosDeTareaDTOTitulo = getParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(idInstanciaDeTarea);
		
		Iterator<ParametroDeTareaDTO> it = parametrosDeTareaDTOTitulo.iterator();
		
		while (it.hasNext()) {
			ParametroDeTareaDTO parametroDeTareaDTO = it.next();
			if (mapParametrosDeTareaDTOTitulo.containsKey(parametroDeTareaDTO.getTitulo())) {
				List<ParametroDeTareaDTO> parametrosDeTareaDTO = mapParametrosDeTareaDTOTitulo.get(parametroDeTareaDTO.getTitulo());
				parametrosDeTareaDTO.add(parametroDeTareaDTO);
				it.remove();
				mapParametrosDeTareaDTOTitulo.put(parametroDeTareaDTO.getTitulo(), parametrosDeTareaDTO);
			} else {
				List<ParametroDeTareaDTO> parametrosDeTareaDTO = new ArrayList<ParametroDeTareaDTO>();
				parametrosDeTareaDTO.add(parametroDeTareaDTO);
				it.remove();
				mapParametrosDeTareaDTOTitulo.put(parametroDeTareaDTO.getTitulo(), parametrosDeTareaDTO);
			}
		}
		
		return mapParametrosDeTareaDTOTitulo;
	
	}
	
	private List<ParametroDeTareaDTO> getParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea) {		
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		String inputComentario = null;
		String abreSelect = null;
		String cierraSelect = null;
		TipoParametroDeTarea inputComentarioParam = tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("comentario");
		TipoParametroDeTarea abreSelectParam =  tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("abre_select");
		TipoParametroDeTarea cierraSelectParam =  tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("cierra_select");
		if (inputComentarioParam!=null ) {
			inputComentario = inputComentarioParam.getTextoHtml();
			log.debug("inputComentario: " + inputComentario);
		}
		if (abreSelectParam!=null ) {
			abreSelect = abreSelectParam.getTextoHtml();
			log.debug("abreSelect: " + abreSelect);
		}
		if (cierraSelectParam!=null ) {
			cierraSelect = cierraSelectParam.getTextoHtml();
			log.debug("cierraSelect: " + cierraSelect);
		}
		for (ParametroDeTareaDTO parametroDeTareaDTO : parametrosDeTareaDTOPorIdTarea) {
			List<TextoParametroDeTarea> textosParametroDeTarea = textoParametroDeTareaDao.getTextosParametroDeTareaPorIdParamTarea(parametroDeTareaDTO.getIdParamTarea());
			for (TextoParametroDeTarea textoParametroDeTarea : textosParametroDeTarea) {
				log.debug(textoParametroDeTarea.toString());
				String textoHtml = parametroDeTareaDTO.getTipoParametroDeTareaDTO().getTextoHtml().replace("${textoParametro}", textoParametroDeTarea.getTexto());				
				textoHtml = textoHtml.replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea()));
				if (parametroDeTareaDTO.getTextoHtml()!=null && !parametroDeTareaDTO.getTextoHtml().isEmpty()) {
					StringBuilder textoHtmlTipoParam = new StringBuilder(parametroDeTareaDTO.getTextoHtml()); 
					textoHtmlTipoParam.append(" ");	
					textoHtmlTipoParam.append(textoHtml);					
					parametroDeTareaDTO.setTextoHtml(textoHtmlTipoParam.toString());
				} else {
					parametroDeTareaDTO.setTextoHtml(textoHtml);
				}	
			}
			if (parametroDeTareaDTO.getTipoParametroDeTareaDTO().getComenta()!=null && parametroDeTareaDTO.getTipoParametroDeTareaDTO().getComenta().booleanValue() == true) {
				if (parametroDeTareaDTO.getTextoHtml()!=null && !parametroDeTareaDTO.getTextoHtml().isEmpty()) {					
					StringBuilder textoHtmlTipoParamComentario = new StringBuilder(parametroDeTareaDTO.getTextoHtml()); 
					textoHtmlTipoParamComentario.append(" ");	
					if (inputComentario!=null) {
						textoHtmlTipoParamComentario.append(inputComentario.replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea())));
					}		
					parametroDeTareaDTO.setTextoHtml(textoHtmlTipoParamComentario.toString());
				}
			}
			if (parametroDeTareaDTO.getTextoHtml()!=null && !parametroDeTareaDTO.getTextoHtml().isEmpty()
					&& parametroDeTareaDTO.getTextoHtml().contains("<option") && parametroDeTareaDTO.getTextoHtml().contains("<select")) {
				StringBuilder select = new StringBuilder(parametroDeTareaDTO.getTextoHtml());
				select.insert(0, abreSelect);
		    	select.insert(select.length(), cierraSelect);
		    	String selectTextoHtml = select.toString().replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea()));
		    	parametroDeTareaDTO.setTextoHtml(selectTextoHtml);
			}			
			log.debug(parametroDeTareaDTO.toString());
		}
		return parametrosDeTareaDTOPorIdTarea;		
	}
	
		
	/*public Map<String, List<Map<String, String>>> getParametrosDeTareaMapTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Map<String, String> mapParametrosDeTarea = new HashMap<String, String>();
		Map<String, List<Map<String, String>>> mapParametrosDeTareaTitulo = new HashMap<String, List<Map<String, String>>>();
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);		
		String inputComentario = null;
		String abreSelect = null;
		String cierraSelect = null;
		TipoParametroDeTarea inputComentarioParam = tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("comentario");
		TipoParametroDeTarea abreSelectParam =  tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("abre_select");
		TipoParametroDeTarea cierraSelectParam =  tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("cierra_select");
		if (inputComentarioParam!=null ) {
			inputComentario = inputComentarioParam.getTextoHtml();
			log.debug("inputComentario: " + inputComentario);
		}
		if (abreSelectParam!=null ) {
			abreSelect = abreSelectParam.getTextoHtml();
			log.debug("abreSelect: " + abreSelect);
		}
		if (cierraSelectParam!=null ) {
			cierraSelect = cierraSelectParam.getTextoHtml();
			log.debug("cierraSelect: " + cierraSelect);
		}
		for (ParametroDeTareaDTO parametroDeTareaDTO : parametrosDeTareaDTOPorIdTarea) {
			List<TextoParametroDeTarea> textosParametroDeTarea = textoParametroDeTareaDao.getTextosParametroDeTareaPorIdParamTarea(parametroDeTareaDTO.getIdParamTarea());
			for (TextoParametroDeTarea textoParametroDeTarea : textosParametroDeTarea) {
				log.debug(textoParametroDeTarea.toString());
				String textoHtml = parametroDeTareaDTO.getTipoParametroDeTareaDTO().getTextoHtml().replace("${textoParametro}", textoParametroDeTarea.getTexto());				
				textoHtml = textoHtml.replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea()));
				if (mapParametrosDeTarea.containsKey(parametroDeTareaDTO.getNombreParamTarea())) {
					StringBuilder textoHtmlTipoParam = new StringBuilder(mapParametrosDeTarea.get(parametroDeTareaDTO.getNombreParamTarea())); 
					textoHtmlTipoParam.append(" ");	
					textoHtmlTipoParam.append(textoHtml);
					mapParametrosDeTarea.put(parametroDeTareaDTO.getNombreParamTarea(), textoHtmlTipoParam.toString());
				} else {
					mapParametrosDeTarea.put(parametroDeTareaDTO.getNombreParamTarea(), textoHtml);
				}	
			}
			if (parametroDeTareaDTO.getTipoParametroDeTareaDTO().getComenta()!=null && parametroDeTareaDTO.getTipoParametroDeTareaDTO().getComenta().booleanValue() == true) {
				if (mapParametrosDeTarea.containsKey(parametroDeTareaDTO.getNombreParamTarea())) {
					StringBuilder textoHtmlTipoParamComentario = new StringBuilder(mapParametrosDeTarea.get(parametroDeTareaDTO.getNombreParamTarea())); 
					textoHtmlTipoParamComentario.append(" ");	
					if (inputComentario!=null) {
						textoHtmlTipoParamComentario.append(inputComentario.replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea())));
					}					
					mapParametrosDeTarea.put(parametroDeTareaDTO.getNombreParamTarea(), textoHtmlTipoParamComentario.toString());
				}
			}
			for (Map.Entry<String, String> entry : mapParametrosDeTarea.entrySet()) {			   
			    if (entry.getValue().contains("<option") && !entry.getValue().contains("<select")) {
			    	log.debug("ACA entry.getValue(): " + entry.getValue());
			    	StringBuilder select = new StringBuilder(entry.getValue());
			    	select.insert(0, abreSelect);
			    	select.insert(select.length(), cierraSelect);
			    	String selectTextoHtml = select.toString().replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea()));
			    	mapParametrosDeTarea.put(entry.getKey(), selectTextoHtml);
			    }
			}
			log.debug(parametroDeTareaDTO.toString());
			if (mapParametrosDeTareaTitulo.containsKey(parametroDeTareaDTO.getTitulo())) {
				log.debug("Adjuntando en lista de: " + parametroDeTareaDTO.getTitulo());
				log.debug(mapParametrosDeTarea.toString());
				List<Map<String, String>> listaParametroPorTitulo = mapParametrosDeTareaTitulo.get(parametroDeTareaDTO.getTitulo());
				listaParametroPorTitulo.add(mapParametrosDeTarea);				
			} else {
				log.debug("Creando la lista de: " + parametroDeTareaDTO.getTitulo());
				log.debug(mapParametrosDeTarea.toString());
				List<Map<String, String>> listaParametroPorTitulo = new ArrayList<Map<String, String>>();
				listaParametroPorTitulo.add(mapParametrosDeTarea);
				mapParametrosDeTareaTitulo.put(parametroDeTareaDTO.getTitulo(), listaParametroPorTitulo);
			}			
		}		
		return mapParametrosDeTareaTitulo;
	}*/
	
	/*@Override
	public Map<String, Map<String, String>> getParametrosDeTareaMapTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Map<String, Map<String, String>> mapParametrosDeTareaTitulo = new HashMap<String, Map<String, String>>();
		Map<String, String> mapParametrosDeTarea = this.getParametrosDeTareaMapPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for () {
			
		}
		return mapParametrosDeTareaTitulo;
	}*/

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

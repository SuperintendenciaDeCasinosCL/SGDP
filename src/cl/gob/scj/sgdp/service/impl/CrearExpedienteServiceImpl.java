package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.AccionesHistInstDeTareaDao;
import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dao.ComplejidadExpedienteDao;
import cl.gob.scj.sgdp.dao.EstadoDeProcesoDao;
import cl.gob.scj.sgdp.dao.EstadoDeTareaDao;
import cl.gob.scj.sgdp.dao.FechaFeriadoDao;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoUsuariosAsignadosATareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.MacroProcesoDao;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dao.ResponsabilidadTareaDao;
import cl.gob.scj.sgdp.dao.UsuarioAsignadoDao;
import cl.gob.scj.sgdp.dao.UsuarioResponsabilidadDao;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ComplejidadExpediente;
import cl.gob.scj.sgdp.model.EstadoDeProceso;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATarea;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATareaPK;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.MacroProceso;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.model.UsuarioAsignadoPK;
import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;
import cl.gob.scj.sgdp.service.CrearExpedienteService;
import cl.gob.scj.sgdp.service.GestorMetadataService;
import cl.gob.scj.sgdp.tipos.AccionesHistInstDeTareaType;
import cl.gob.scj.sgdp.tipos.EstadoDeProcesoType;
import cl.gob.scj.sgdp.tipos.EstadoDeTareaType;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.CrearExpedienteCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CrearExpedienteServiceImpl implements CrearExpedienteService {

	private static final Logger log = Logger.getLogger(CrearExpedienteServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private EstadoDeProcesoDao estadoDeProcesoDao;
	
	@Autowired
	private ProcesoDao procesoDao;	
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private FechaFeriadoDao fechaFeriadoDao;
	
	@Autowired
	private EstadoDeTareaDao estadoDeTareaDao;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private AutorDao autorDao;
	
	@Autowired
	private UsuarioAsignadoDao usuarioAsignadoDao;
	
	@Autowired
	private AccionesHistInstDeTareaDao accionesHistInstDeTareaDao;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private HistoricoUsuariosAsignadosATareaDao historicoUsuariosAsignadosATareaDao;
	
	@Autowired
	private CrearExpedienteCMSService crearExpedienteCMSService;
	
	@Autowired
	private MacroProcesoDao macroProcesoDao;
	
	@Autowired
	private ResponsabilidadTareaDao responsabilidadTareaDao;
	
	@Autowired
	private UsuarioResponsabilidadDao usuarioResponsabilidadDao;
	
	@Autowired
	private GestorMetadataService gestorMetadataService;
	
	@Autowired
	private ComplejidadExpedienteDao complejidadExpedienteDao;
	
	@Autowired
	private GestorMetadataCMSService gestorMetadataCMSService;
	
	EstadoDeProcesoType estadoDeProcesoNuevoType = EstadoDeProcesoType.NUEVO;
	EstadoDeProcesoType estadoDeProcesoAsignadoType = EstadoDeProcesoType.ASIGNADO;
	
	EstadoDeTareaType estadoDeTareaAsignadaType = EstadoDeTareaType.ASIGNADA;
	
	EstadoDeTareaType estadoDeTareaNuevaType = EstadoDeTareaType.NUEVA;
	
	AccionesHistInstDeTareaType accionCreaHistInstDeTareaType = AccionesHistInstDeTareaType.CREA;
	
	PermisoType permisoTypeAutoAsignaPrimeraTarea = PermisoType.AUTO_ASIGNA_PRIMERA_TAREA;
	
	@Override
	public String crearExpediente(ExpedienteDTO expedienteDTO, Usuario usuario) throws SgdpException {
		
		log.debug("Inicio crear expediente");	
		
		StringBuilder idUsuarioQueHaParticipado = new StringBuilder();
		String usuarioSGDP = expedienteDTO.getIdUsuarioCrea()!=null && !expedienteDTO.getIdUsuarioCrea().isEmpty() ? expedienteDTO.getIdUsuarioCrea() : usuario.getIdUsuario();
		log.info("usuarioSGDP: " + usuarioSGDP);
		
		//Llamar a servicio web de Alfresco para crear expediente	
		
		Calendar cal = Calendar.getInstance();
		Calendar calModInstProc = Calendar.getInstance();
		Calendar calModInstTarea = Calendar.getInstance();
		
		cal.setTime(new Date());
		calModInstProc.setTime(new Date());
		
		EstadoDeProceso estadoDeProcesoNuevo = estadoDeProcesoDao.getEstadoDeProcesoPorCodigo(estadoDeProcesoNuevoType.getCodigoEstadoDeProceso());	
		EstadoDeProceso estadoDeProcesoAsignado = estadoDeProcesoDao.getEstadoDeProcesoPorCodigo(estadoDeProcesoAsignadoType.getCodigoEstadoDeProceso());
		
		Proceso proceso;
		
		if (expedienteDTO.getCodigoProceso()!=null && !expedienteDTO.getCodigoProceso().isEmpty()) {
			proceso = procesoDao.getProcesoVigentePorCodigoProceso(expedienteDTO.getCodigoProceso());
		} else {
			proceso = procesoDao.getProcesoPorIdProceso(expedienteDTO.getIdProceso());
		}
		
		//Proceso proceso = procesoDao.getProcesoPorIdProceso(expedienteDTO.getIdProceso());
		
		String nombreAutor = autorDao.getAutorPorIdAutor(Long.parseLong(expedienteDTO.getIdAutor())).getNombreAutor();
			
		InstanciaDeProceso instanciaDeProceso = new InstanciaDeProceso();	
		instanciaDeProceso.setEstadoDeProceso(estadoDeProcesoNuevo);		
		instanciaDeProceso.setIdUsuarioInicia(usuarioSGDP);		
		instanciaDeProceso.setFechaInicio(new Date());
		instanciaDeProceso.setTieneDocumentosEnCMS(false);
		instanciaDeProceso.setProceso(proceso);
		instanciaDeProceso.setFechaVencimiento(FechaUtil.getFechaHabil(calModInstProc, fechaFeriadoDao, proceso.getDiasHabilesMaxDuracion()).getTime());
		instanciaDeProceso.setEmisor(nombreAutor);
		instanciaDeProceso.setAsunto(expedienteDTO.getMateria());
		instanciaDeProceso.setUnidad(proceso.getUnidad());
		
		expedienteDTO.setCreador(usuarioSGDP);
		expedienteDTO.setNombreAutor(nombreAutor);
		expedienteDTO.setNombrePerpectiva(instanciaDeProceso.getProceso().getMacroProceso().getPerspectiva().getNombrePerspectiva());
		expedienteDTO.setNombreMacroProceso(instanciaDeProceso.getProceso().getMacroProceso().getNombreMacroProceso());
		expedienteDTO.setNombreProceso(instanciaDeProceso.getProceso().getNombreProceso());
		expedienteDTO.setEsConfidencial(instanciaDeProceso.getProceso().getEsConfidencial()!=null ? instanciaDeProceso.getProceso().getEsConfidencial().toString() : null);
		
		StringBuilder nombreExpediente = new StringBuilder();
		nombreExpediente.append(configProps.getProperty("prefijoExpediente"));
		nombreExpediente.append(instanciaDeProcesoDao.getNombreExpediente());
		nombreExpediente.append(configProps.getProperty("separadorNombreExpYear"));  
		nombreExpediente.append(FechaUtil.simpleDateFormatYear.format(new Date()));
		
		log.debug("nombreExpediente.toString(): " + nombreExpediente.toString());
		
		instanciaDeProceso.setNombreExpediente(nombreExpediente.toString());
		expedienteDTO.setNombreExpediente(nombreExpediente.toString());	
		
		log.debug(expedienteDTO.toString());		
		
		instanciaDeProcesoDao.insertInstanciaDeProceso(instanciaDeProceso, usuario);		
			
		List<Tarea> tareas = instanciaDeProceso.getProceso().getTareas();
		
		log.debug("Cantidad de tareas: " + tareas.size());
		
		List<InstanciaDeTarea> it = new ArrayList<InstanciaDeTarea>();
		
		int maxOrden = 0;
				
		for (Tarea tarea : tareas) {
			
			if (tarea.getOrden()>maxOrden) {
				maxOrden = tarea.getOrden();
			}
			
			cal.setTime(new Date());			
			InstanciaDeTarea instanciaDeTarea = new InstanciaDeTarea();	
			it.add(instanciaDeTarea);
			instanciaDeTarea.setInstanciaDeProceso(instanciaDeProceso);
			instanciaDeTarea.setFechaAsignacion(cal.getTime());
			instanciaDeTarea.setFechaInicio(cal.getTime());
			instanciaDeTarea.setTarea(tarea);
			instanciaDeTarea.setIdUsuarioQueAsigna(usuarioSGDP);
			log.debug("tarea.getDiasHabilesMaxDuracion(): " + tarea.getDiasHabilesMaxDuracion());
			
			if (tarea.getOrden()==new Integer(configProps.getProperty("numeroPrimeraTarea")).intValue()) {
				calModInstTarea.setTime(new Date());
				instanciaDeTarea.setFechaVencimiento(FechaUtil.getFechaHabil(calModInstTarea, fechaFeriadoDao, tarea.getDiasHabilesMaxDuracion()).getTime());
			} 
			
			log.debug("configProps.getProperty(\"numeroPrimeraTarea\"): " + configProps.getProperty("numeroPrimeraTarea"));
			
			List<ResponsabilidadTarea> responsabilidadesTareas = responsabilidadTareaDao.getResponsabilidadesTareasPorIdTarea(tarea.getIdTarea());
			
			//La tarea tiene responsabilidades asociadas
			if (responsabilidadesTareas != null && responsabilidadesTareas.size()>0) {	
				
				log.debug("responsabilidadesTareas != null && responsabilidadesTareas.size()>0");
				log.debug("tarea.getOrden(): " + tarea.getOrden());	
				
				UsuarioResponsabilidad usuarioResponsabilidad = null;
				
				if (usuario.getPermisos()!=null && usuario.getPermisos().containsKey(permisoTypeAutoAsignaPrimeraTarea.getNombrePermiso())
						&& tarea.getOrden() == new Integer(configProps.getProperty("numeroPrimeraTarea")).intValue()) {
					log.debug("Tiene permiso autoasigna primera tarea");
					List<UsuarioResponsabilidad> todosLosUsuariosResponsabilidadPorIdTarea = usuarioResponsabilidadDao.getTodosLosUsuariosResponsabilidadPorIdTarea(tarea.getIdTarea());
					for (UsuarioResponsabilidad usuarioResponsabilidadAutoAsigna : todosLosUsuariosResponsabilidadPorIdTarea) {
						if (usuarioResponsabilidadAutoAsigna.getIdUsuario().equals(usuarioSGDP)) {
							log.debug("usuarioResponsabilidadAutoAsigna.getIdUsuario().equals(usuarioSGDP)");
							usuarioResponsabilidad = usuarioResponsabilidadAutoAsigna;
							break;
						}						
					}
				} else if (usuarioResponsabilidad == null && tarea.getOrden() == new Integer(configProps.getProperty("numeroPrimeraTarea")).intValue()) {
					log.debug("No tiene permiso autoasigna primera tarea");
					log.debug("Asignando al primer usuario");
					usuarioResponsabilidad = usuarioResponsabilidadDao.getUsuarioResponsabilidadPorIdTarea(tarea.getIdTarea());
				}	
				
				//Existen un usuario asociado a la responsabilidad de la primera tarea	
				if (tarea.getOrden() == new Integer(configProps.getProperty("numeroPrimeraTarea")).intValue() && usuarioResponsabilidad != null) {		
				
					log.debug("Existe usuario asociado a la responsabilidad de la primera tarea");					
					
					log.debug("Setea estado de la instancia de la tarea como asignada");
					instanciaDeTarea.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaAsignadaType.getCodigoEstadoDeTarea()));
					
					instanciaDeTareaDao.insertInstanciaDeTarea(instanciaDeTarea, usuario);					
					
					//Asignamos la instancia de la tarea al usuario
					log.debug("usuarioResponsabilidad.getIdUsuario(): " + usuarioResponsabilidad.getIdUsuario());
					idUsuarioQueHaParticipado.append(usuarioResponsabilidad.getIdUsuario());
					log.debug("idUsuarioQueHaParticipado.toString(): " + idUsuarioQueHaParticipado.toString());
					UsuarioAsignadoPK usuarioAsignadoPK = new UsuarioAsignadoPK(instanciaDeTarea, usuarioResponsabilidad.getIdUsuario());
					UsuarioAsignado usuarioAsignado = new UsuarioAsignado();
					usuarioAsignadoPK.setInstanciaDeTarea(instanciaDeTarea);
					usuarioAsignado.setId(usuarioAsignadoPK);
					usuarioAsignadoDao.insertUsuarioAsignado(usuarioAsignado, usuario);	
					
					//Se registra esta asignacion en historico
					//Se inserta un nuevo registro en historico de instancia de tarea
					log.debug("Insertando en HistoricoDeInstDeTarea");
					HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
					historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(accionCreaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea()));
					historicoDeInstDeTarea.setIdUsuarioOrigen(usuarioSGDP);
					historicoDeInstDeTarea.setFechaMovimiento(new Date());
					historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTarea);
					historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTarea);					
					historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);
					
					log.debug("Insertando en historico de usuarios asignados");
					HistoricoUsuariosAsignadosATareaPK historicoUsuariosAsignadosATareaPK = new HistoricoUsuariosAsignadosATareaPK(historicoDeInstDeTarea, usuarioResponsabilidad.getIdUsuario()/*usuario.getIdUsuario()*/);
					HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea = new HistoricoUsuariosAsignadosATarea();
					historicoUsuariosAsignadosATarea.setId(historicoUsuariosAsignadosATareaPK);
					historicoUsuariosAsignadosATareaDao.insertHistoricoUsuarioAsignadoATarea(historicoUsuariosAsignadosATarea, usuario);
					
				} else if (tarea.getOrden() > new Integer(configProps.getProperty("numeroPrimeraTarea")).intValue()) {
				
					instanciaDeTarea.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaNuevaType.getCodigoEstadoDeTarea()));
					
					instanciaDeTareaDao.insertInstanciaDeTarea(instanciaDeTarea, usuario);
					
					//Se registra esta asignacion en historico
					//Se inserta un nuevo registro en historico de instancia de tarea
					log.debug("Insertando en HistoricoDeInstDeTarea");
					HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
					historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(accionCreaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea()));
					historicoDeInstDeTarea.setIdUsuarioOrigen(usuarioSGDP);
					historicoDeInstDeTarea.setFechaMovimiento(new Date());
					historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTarea);
					historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTarea);					
					historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);
					
				} else  {								
					
					log.error("Responsabilidad de la primera tarea sin usuarios asociados");
					
					log.error(responsabilidadesTareas.toString());
					log.error(responsabilidadesTareas.toArray());
					throw new SgdpException("Responsabilidad de la primera tarea sin usuarios asociados", Level.WARN, false);
					
				}				
				
				
			} else {
				
				log.error("La tarea no tiene responsabilidad asociada");
		
				log.error(tarea.toString());
				throw new SgdpException("La tarea no tiene responsabilidad asociada", Level.WARN, false);
				
			}
				
			// SACO EL ID INSTANCIA TAREA PARA PODER INSERTARLA EL DOCUMENTO ADJUNTO AL CONDUCTOR
			if (tarea.getOrden() == new Integer(configProps.getProperty("numeroPrimeraTarea")).intValue()) {
				expedienteDTO.setIdInstanciaDeTareaSalida(instanciaDeTarea.getIdInstanciaDeTarea());
			}
			
		}	
		
		log.debug("Antes de llamar al servicio crear expediente");
		try {			
			instanciaDeProceso.setIdExpediente(crearExpedienteCMSService.crearExpediente(usuario, expedienteDTO));
			instanciaDeProceso.setEstadoDeProceso(estadoDeProcesoAsignado);
			expedienteDTO.setIdExpediente(instanciaDeProceso.getIdExpediente());
			gestorMetadataService.actualizaUsuariosQueHanModificadoExpediente(usuario, instanciaDeProceso.getIdExpediente(), idUsuarioQueHaParticipado.toString());									
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			if (e instanceof SgdpException) {
				throw (SgdpException)e;
			}
			throw new SgdpException(configProps.getProperty("errorAlCrearExpEnCMS"));
		}
		
		if (expedienteDTO.getIdComplejidad() != null 
				&& usuario.getPermisos().get(PermisoType.PUEDE_INGRESAR_COMPLEJIDAD_AL_CREAR_EXPEDIENTE.getNombrePermiso()) !=null) {
			insertarComplejidad(expedienteDTO, instanciaDeProceso, usuario);
		}

		log.debug("Fin crear expediente");
		
		return configProps.getProperty("respuestaOK");
		
	}
	
	
	
	private void insertarComplejidad(ExpedienteDTO expedienteDTO, InstanciaDeProceso instanciaDeProceso,
			Usuario usuario) throws SgdpException {
		try {
			log.debug("Insertando Complejidad");
			ComplejidadExpediente complejidadModel = new ComplejidadExpediente();
			complejidadModel.setComplejidad(expedienteDTO.getIdComplejidad());
			complejidadModel.setMotivoComplejidad(expedienteDTO.getMotivoComplejidad());
			complejidadModel.setInstanciaDeProceso(instanciaDeProceso);
			complejidadModel.setUsuario(usuario.getIdUsuario());
			complejidadModel.setFecha(new Date());
			complejidadExpedienteDao.insert(complejidadModel);
			ExpedienteRestActMetaDTO expedienteRestActMetaDTO = new ExpedienteRestActMetaDTO();
			expedienteRestActMetaDTO.setIdExpediente(instanciaDeProceso.getIdExpediente());
			expedienteRestActMetaDTO.setComplejidad(expedienteDTO.getIdComplejidad());
			gestorMetadataCMSService.actualizaMetaDataExpediente(usuario, expedienteRestActMetaDTO);
		} catch (Exception e) {
			log.error("Error al insertar complejidad al expediente: " + expedienteDTO.getNombreExpediente(), e);
			log.error(SGDPUtil.getStackTrace(e));
			throw new SgdpException(configProps.getProperty("errorAlCrearExpEnCMS"));
		}
	}




	@Override
	public List<ProcesoDTO> getProcesosPorIdMacroProceso(long idMacroProceso, List<ProcesoDTO> procesosDTO) {
		List<Proceso> procesos = procesoDao.getProcesosPorIdMacroProceso(idMacroProceso, true);
		for (Proceso proceso : procesos) {
			ProcesoDTO procesoDTO = new ProcesoDTO(proceso.getIdProceso(), proceso.getDescripcionProceso(), proceso.getNombreProceso(), proceso.getVigente(), proceso.getDiasHabilesMaxDuracion());
			procesosDTO.add(procesoDTO);
		}
		return procesosDTO;
	}

	@Override
	public String getTareaPorIdSubProceso(long idSubProceso) {
	       List<Tarea> listTareas =  procesoDao.getTareaPorIdSubProceso(idSubProceso, true);
	       log.debug("total lista de tareas " + listTareas.size() );
	       
	       String respuesta = "";
	       
	       if (listTareas.size() >= 2){
	    	   respuesta = "DESHABILITAR";
	       }else{
	    	   respuesta = "HABILITAR";
	       }
		
		return respuesta;
	}	
	
	@Override
	public List<MacroProcesoDTO> getMacroProcesosDTO(Usuario usuario) {
		List<MacroProcesoDTO> macroProcesosDTO = new ArrayList<MacroProcesoDTO>();
		List<MacroProceso> macroProcesos;
		if (usuario.getPermisos().containsKey(PermisoType.INICIAR_TODOS_LOS_PROCESOS.getNombrePermiso())) {
			macroProcesos = macroProcesoDao.getTodosLosMacroProcesos();			
		} else {			
			macroProcesos = macroProcesoDao.getMacroProcesosPorIdUnidad(usuario.getUnidadDTO().getIdUnidad());			
		}	
		for (MacroProceso macroProceso : macroProcesos) {
			MacroProcesoDTO macroProcesoDTO = new MacroProcesoDTO(macroProceso.getIdMacroProceso(), macroProceso.getDescripcionMacroProceso(), macroProceso.getNombreMacroProceso());
			macroProcesosDTO.add(macroProcesoDTO);
		}	
		return macroProcesosDTO;
	}
	
}

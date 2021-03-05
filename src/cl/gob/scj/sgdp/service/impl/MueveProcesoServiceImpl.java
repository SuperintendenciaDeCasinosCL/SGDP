package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.integracion.IntegracionClient;
import cl.gob.scj.integracion.datosEntrada.IntegracionSatelitesDTO;
import cl.gob.scj.integracion.datosSalida.RespuestaIntegracionDTO;
import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AccionesHistInstDeTareaDao;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.EstadoDeProcesoDao;
import cl.gob.scj.sgdp.dao.EstadoDeTareaDao;
import cl.gob.scj.sgdp.dao.FechaFeriadoDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoFechaVencimientoInstanciaProcesoDao;
import cl.gob.scj.sgdp.dao.HistoricoUsuariosAsignadosATareaDao;
import cl.gob.scj.sgdp.dao.HistoricoValorParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.ParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.UsuarioAsignadoDao;
import cl.gob.scj.sgdp.dao.ValorParametroDeTareaDao;
import cl.gob.scj.sgdp.dto.AnulacionDTO;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.AsignacionTareaDTO;
import cl.gob.scj.sgdp.dto.CierraInstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ContinuarProcesoDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.FinalizaProcesoDTO;
import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.ParametroFormularioDTO;
import cl.gob.scj.sgdp.dto.ReabreInstanciaDeSubProcesoDTO;
import cl.gob.scj.sgdp.dto.RetrocedeProcesoDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.EstadoDeProceso;
import cl.gob.scj.sgdp.model.EstadoDeTarea;
import cl.gob.scj.sgdp.model.HistoricoArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoFechaVencimientoInstanciaProceso;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATarea;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATareaPK;
import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.model.UsuarioAsignadoPK;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;
import cl.gob.scj.sgdp.model.ValorParametroDeTarea;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.GestorMetadataService;
import cl.gob.scj.sgdp.service.HistoricoFirmaService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.MueveProcesoService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.ParametroDeTareaService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;
import cl.gob.scj.sgdp.tipos.AccionesHistInstDeTareaType;
import cl.gob.scj.sgdp.tipos.BifurcacionType;
import cl.gob.scj.sgdp.tipos.EstadoDeProcesoType;
import cl.gob.scj.sgdp.tipos.EstadoDeTareaType;
import cl.gob.scj.sgdp.tipos.MensajeExceptionType;
import cl.gob.scj.sgdp.tipos.NotificacionType;
import cl.gob.scj.sgdp.tipos.ReseteoType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.util.StringUtilSGDP;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MueveProcesoServiceImpl implements MueveProcesoService {

	private static final Logger log = Logger.getLogger(MueveProcesoServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private EstadoDeTareaDao estadoDeTareaDao;	

	@Autowired
	private UsuarioAsignadoDao usuarioAsignadoDao;
	
	@Autowired
	private AccionesHistInstDeTareaDao accionesHistInstDeTareaDao;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private HistoricoUsuariosAsignadosATareaDao historicoUsuariosAsignadosATareaDao;
	
	@Autowired
	private HistoricoArchivosInstDeTareaDao historicoArchivosInstDeTareaDao;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EstadoDeProcesoDao estadoDeProcesoDao;
	
	@Autowired
	private UsuarioResponsabilidadService usuarioResponsabilidadService;
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private GestorMetadataService gestorMetadataService;
	
	@Autowired
	private FechaFeriadoDao fechaFeriadoDao;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private HistoricoFechaVencimientoInstanciaProcesoDao historicoFechaVencimientoInstanciaProcesoDao;
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;	
	
	@Autowired
	private HistoricoFirmaService historicoFirmaService;
	
	@Autowired
	private ParametroDeTareaService parametroDeTareaService;
	
	EstadoDeProcesoType estadoDeProcesoType = EstadoDeProcesoType.NUEVO;
	
	EstadoDeTareaType estadoDeTareaAsignadaType = EstadoDeTareaType.ASIGNADA;
	
	EstadoDeTareaType estadoDeTareaNuevaType = EstadoDeTareaType.NUEVA;
	
	EstadoDeTareaType estadoDeTareaFinalizadaType = EstadoDeTareaType.FINALIZADA;
	
	EstadoDeProcesoType estadoDeProcesoFinalizadoType = EstadoDeProcesoType.FINALIZADO;
	
	EstadoDeProcesoType estadoDeProcesoAnuladoType = EstadoDeProcesoType.ANULADO;
	
	EstadoDeTareaType estadoDeTareaAnuladaType = EstadoDeTareaType.ANULADA;
	
	BifurcacionType bifurcacionANDType = BifurcacionType.AND;
	
	AccionesHistInstDeTareaType accionEnviaHistInstDeTareaType = AccionesHistInstDeTareaType.ENVIA;
	AccionesHistInstDeTareaType accionReasignaHistInstDeTareaType = AccionesHistInstDeTareaType.REASIGNA;
	AccionesHistInstDeTareaType accionFinalizaHistInstDeTareaType = AccionesHistInstDeTareaType.FINALIZA;	
	AccionesHistInstDeTareaType accionDevuelveHistInstDeTareaType = AccionesHistInstDeTareaType.DEVUELVE;
	AccionesHistInstDeTareaType accionAnulaHistInstDeTareaType = AccionesHistInstDeTareaType.ANULA;
	
	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(Usuario usuario, long idInstanciaDeTarea, boolean vigente
			, List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso) {
		List<InstanciaDeTarea> instanciasDeTareasQueContinuanDeInstanciaDeTarea = instanciaDeTareaDao.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(idInstanciaDeTarea);
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasQueContinuanDeInstanciaDeTarea) {
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.setIdUsuariosAsignados(new ArrayList<String>());			
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTO.getPosiblesIdUsuarios());
			instanciasDeTareasDTOContinuanProceso.add(instanciaDeTareaDTO);
		}
		return instanciasDeTareasDTOContinuanProceso;
	}
	
	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(Usuario usuario, InstanciaDeTareaDTO instanciaDeTareaDTO, 
			List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso) {		
		if (instanciaDeTareaDTO.getTipoDeBifurcacion()!=null && instanciaDeTareaDTO.getTipoDeBifurcacion().equals(bifurcacionANDType.getNombreTipoDeBifurcacion())) {
			log.info("Tarea de tipo AND");	
			List<InstanciaDeTarea> instanciasDeTareasQueContinuanDeInstanciaDeTarea = instanciaDeTareaDao.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea());			
			for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasQueContinuanDeInstanciaDeTarea) {
				if (instanciaDeTarea.getEstadoDeTarea().getCodigoEstadoDeTarea()==EstadoDeTareaType.NUEVA.getCodigoEstadoDeTarea()) {
					InstanciaDeTareaDTO instanciaDeTareaDTOQueContinua = new InstanciaDeTareaDTO();
					instanciaDeTareaDTOQueContinua.setIdUsuariosAsignados(new ArrayList<String>());			
					instanciaDeTareaDTOQueContinua.cargaInstanciaDeTareaDTO(instanciaDeTarea);
					usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuarios());
					usuarioResponsabilidadService.cargaUsuariosFueraOficinaRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuariosFueraDeOficina());
					instanciasDeTareasDTOContinuanProceso.add(instanciaDeTareaDTOQueContinua);	
				} else if (instanciaDeTarea.getEstadoDeTarea().getCodigoEstadoDeTarea()==EstadoDeTareaType.FINALIZADA.getCodigoEstadoDeTarea()) {
					//Si esta finalizada y no tiene movimientos de origen y destino
					List<HistoricoDeInstDeTarea> historicosDeInstDeTarea = historicoDeInstDeTareaDao.getHistoricosDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestino
							(instanciaDeTareaDTO.getIdInstanciaDeTarea(), instanciaDeTarea.getIdInstanciaDeTarea());
					if (historicosDeInstDeTarea == null || historicosDeInstDeTarea.isEmpty()) {
						InstanciaDeTareaDTO instanciaDeTareaDTOQueContinua = new InstanciaDeTareaDTO();
						instanciaDeTareaDTOQueContinua.setIdUsuariosAsignados(new ArrayList<String>());			
						instanciaDeTareaDTOQueContinua.cargaInstanciaDeTareaDTO(instanciaDeTarea);
						usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuarios());
						usuarioResponsabilidadService.cargaUsuariosFueraOficinaRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuariosFueraDeOficina());
						instanciasDeTareasDTOContinuanProceso.add(instanciaDeTareaDTOQueContinua);	
					}
				}
				
			}
			if (instanciasDeTareasDTOContinuanProceso==null || instanciasDeTareasDTOContinuanProceso.isEmpty()) {
				for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasQueContinuanDeInstanciaDeTarea) {
					if (instanciaDeTarea.getEstadoDeTarea().getCodigoEstadoDeTarea()==EstadoDeTareaType.FINALIZADA.getCodigoEstadoDeTarea()) {
						InstanciaDeTareaDTO instanciaDeTareaDTOQueContinua = new InstanciaDeTareaDTO();
						instanciaDeTareaDTOQueContinua.setIdUsuariosAsignados(new ArrayList<String>());			
						instanciaDeTareaDTOQueContinua.cargaInstanciaDeTareaDTO(instanciaDeTarea);
						usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuarios());
						usuarioResponsabilidadService.cargaUsuariosFueraOficinaRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuariosFueraDeOficina());
						instanciasDeTareasDTOContinuanProceso.add(instanciaDeTareaDTOQueContinua);	
					}
				}
			}
		} else {			
			List<InstanciaDeTarea> instanciasDeTareasQueContinuanDeInstanciaDeTarea = instanciaDeTareaDao.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea());
			if (instanciasDeTareasQueContinuanDeInstanciaDeTarea!=null) {
				log.debug("MV - instanciasDeTareasQueContinuanDeInstanciaDeTarea.size() : " + instanciasDeTareasQueContinuanDeInstanciaDeTarea.size());
			} else {
				log.debug("MV - instanciasDeTareasQueContinuanDeInstanciaDeTarea == null");
			}
			for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasQueContinuanDeInstanciaDeTarea) {
				InstanciaDeTareaDTO instanciaDeTareaDTOQueContinua = new InstanciaDeTareaDTO();
				instanciaDeTareaDTOQueContinua.setIdUsuariosAsignados(new ArrayList<String>());			
				instanciaDeTareaDTOQueContinua.cargaInstanciaDeTareaDTO(instanciaDeTarea);
				usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuarios());
				usuarioResponsabilidadService.cargaUsuariosFueraOficinaRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOQueContinua.getPosiblesIdUsuariosFueraDeOficina());
				instanciasDeTareasDTOContinuanProceso.add(instanciaDeTareaDTOQueContinua);
			}			
		}		
		return instanciasDeTareasDTOContinuanProceso;
	}
	
	@Override
	public void cargaInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea(Usuario usuario, long idInstanciaDeTarea, boolean vigente
			, List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso) {
		List<InstanciaDeTarea> instanciasDeTareasQueContinuanDeInstanciaDeTarea = instanciaDeTareaDao.getInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea(idInstanciaDeTarea);
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasQueContinuanDeInstanciaDeTarea) {
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.setIdUsuariosAsignados(new ArrayList<String>());			
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTO.getPosiblesIdUsuarios());
			instanciasDeTareasDTOContinuanProceso.add(instanciaDeTareaDTO);
		}
	}
	
	private void avanza(ContinuarProcesoDTO continuarProcesoDTO, 
			Usuario usuario, 
			long idSiguienteTarea, 
			String nombresDeUsuariosAsignados,
			String fechaAsignada,
			InstanciaDeTarea instanciaDeTareaDeOrigen
			) throws SgdpException {
		
		Calendar calModInstTarea = Calendar.getInstance();
		Long idAccion;	
		String caracterSeparadorDeUsuarios = configProps.getProperty("caracterSeparadorDeUsuarios");
		List<String> listaDeUsuariosAsignados = new ArrayList<String>();
		//Se actualiza la siguiente instancia de tarea en su fecha y su usuario asignado
		InstanciaDeTarea instanciaDeTareaSiguiente = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(idSiguienteTarea);
		
		Boolean tieneRdsSnc = instanciaDeTareaDeOrigen.getInstanciaDeProceso().getProceso().getTieneRdsSnc();
		
		log.debug("instanciaDeTareaSiguiente.getTarea().getNombreTarea(): "+ instanciaDeTareaSiguiente.getTarea().getNombreTarea());
		continuarProcesoDTO.setNombreDeTarea(instanciaDeTareaSiguiente.getTarea().getNombreTarea());
		
		StringBuilder nombreUsuariosAsignadosSB = new StringBuilder();
		int contadorNombreUsuariosAsigandos = 0;
		
		String usuarioSGDP = continuarProcesoDTO.getIdUsuarioMueve()!=null && !continuarProcesoDTO.getIdUsuarioMueve().isEmpty() ? continuarProcesoDTO.getIdUsuarioMueve() : usuario.getIdUsuario();
		log.info("usuarioSGDP: " + usuarioSGDP);
		
		String[] nombresDeUsuariosAsignadosArray = nombresDeUsuariosAsignados.split(caracterSeparadorDeUsuarios);		
		
		if (continuarProcesoDTO.getReasigna()==true) {
			//Reasigna
			idAccion = accionReasignaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea();
		} else if (continuarProcesoDTO.getReabre()==true) {
			if (instanciaDeTareaDeOrigen.getInstanciaDeProceso().getEstadoDeProceso().getCodigoEstadoDeProceso()==EstadoDeProcesoType.ASIGNADO.getCodigoEstadoDeProceso()) {
				idAccion = AccionesHistInstDeTareaType.ABRE.getIdAccionHistoricoInstDeTarea();
			} else {
				idAccion = AccionesHistInstDeTareaType.REABRE.getIdAccionHistoricoInstDeTarea();
			}			
		} else {
			//Envia
			idAccion = accionEnviaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea();
		}		
		
		//Si la instancia de la tarea siguiente tiene usr asignados se cambia la tarea de origen a finalizada y retorna
		if (instanciaDeTareaSiguiente.getUsuariosAsignados()!=null && !instanciaDeTareaSiguiente.getUsuariosAsignados().isEmpty() &&
				continuarProcesoDTO.getReasigna()==false && instanciaDeTareaDeOrigen.getIdInstanciaDeTarea()!=instanciaDeTareaSiguiente.getIdInstanciaDeTarea()) {
			
			for (UsuarioAsignado usuarioAsignado : instanciaDeTareaSiguiente.getUsuariosAsignados()) {
				nombreUsuariosAsignadosSB.append(usuarioAsignado.getId().getIdUsuario());
				contadorNombreUsuariosAsigandos = contadorNombreUsuariosAsigandos + 1;
				if (contadorNombreUsuariosAsigandos < instanciaDeTareaSiguiente.getUsuariosAsignados().size()) {
					nombreUsuariosAsignadosSB.append("; ");
				}
			}
			
			//Se elimina la asignacion anterior		
			List<UsuarioAsignado> usuarioAsignados = usuarioAsignadoDao.getUsuariosAsignadosPorInstanciaDeTarea(instanciaDeTareaDeOrigen);
			for (UsuarioAsignado usuarioAsignado: usuarioAsignados) {
				log.debug("Borrando usuario asignado: " + usuarioAsignado);
				usuarioAsignadoDao.deleteUsuarioAsignado(usuarioAsignado);
			}	
			
			log.debug("nombreUsuariosAsignadosSB.toString():" + nombreUsuariosAsignadosSB.toString());
			continuarProcesoDTO.getTareasUsuarios().add(MessageFormat.format(configProps.getProperty("tareaSeAsigno"),
					instanciaDeTareaSiguiente.getTarea().getNombreTarea()
					, nombreUsuariosAsignadosSB.toString()));
			
			//Se inserta un nuevo registro en historico de instancia de tarea
			log.debug("Insertando en HistoricoDeInstDeTarea");
			HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
			historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(idAccion));
			historicoDeInstDeTarea.setComentario(continuarProcesoDTO.getComentario());
			historicoDeInstDeTarea.setIdUsuarioOrigen(usuarioSGDP);
			historicoDeInstDeTarea.setFechaMovimiento(new Date());
			historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTareaSiguiente);
			historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTareaDeOrigen);
			historicoDeInstDeTarea.setHorasOcupadas(continuarProcesoDTO.getHorasOcupadas());
			historicoDeInstDeTarea.setMinutosOcupados(continuarProcesoDTO.getMinutosOcupados());
			
			if (continuarProcesoDTO.isAvanzaProcesoConAdvertenciaVisacion()) {
				historicoDeInstDeTarea.setMensajeException(MensajeExceptionType.AVANZA_PROCESO_CON_ADVERTENCIA_VISACION.getNombreMensajeException());
			}
			
			if ( continuarProcesoDTO.isAvanzaProcesoConAdvertenciaFEA()) {
				historicoDeInstDeTarea.setMensajeException(MensajeExceptionType.AVANZA_PROCESO_CON_ADVERTENCIA_FEA.getNombreMensajeException());
			}
			
			historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);		
				
			//Se guarda en historico de usuarios asignados
			for (String nombreDeUsuarioAsignado : nombresDeUsuariosAsignadosArray) {				
				log.debug("Insertando en historico de usuarios asignados");			
				HistoricoUsuariosAsignadosATareaPK historicoUsuariosAsignadosATareaPK = new HistoricoUsuariosAsignadosATareaPK(historicoDeInstDeTarea, nombreDeUsuarioAsignado);
				HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea = new HistoricoUsuariosAsignadosATarea();
				historicoUsuariosAsignadosATarea.setId(historicoUsuariosAsignadosATareaPK);
				historicoUsuariosAsignadosATareaDao.insertHistoricoUsuarioAsignadoATarea(historicoUsuariosAsignadosATarea, usuario);
			}				
			
			//Se guarda en historico de archivos
			List<ArchivosInstDeTarea> archivosInstDeTareaList = instanciaDeTareaDeOrigen.getArchivosInstDeTarea();
			for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaList) {
				HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea = new HistoricoArchivosInstDeTarea(historicoDeInstDeTarea, archivosInstDeTarea);								
				historicoArchivosInstDeTareaDao.insertArchivosHistInstDeTarea(historicoArchivosInstDeTarea, usuario);
			}
			
			//Se guardan parametros de formulario
			if (tieneRdsSnc!=null && tieneRdsSnc.booleanValue() == true && continuarProcesoDTO.getParametrosMapParaGuardarJSON()!=null && !continuarProcesoDTO.getParametrosMapParaGuardarJSON().isEmpty()) {
				log.debug("continuarProcesoDTO.getParametrosMapParaGuardarJSON(): " + continuarProcesoDTO.getParametrosMapParaGuardarJSON());
				ObjectMapper mapper = SingleObjectFactory.getMapper();
				List<ParametroFormularioDTO> listParametroFormularioDTO;
				try {				
					listParametroFormularioDTO = mapper.readValue(continuarProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){});					
				} catch (IOException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					log.error(exceptionAsString);				
					throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
				}
				parametroDeTareaService.guardaHistoricoValorParametroDeTarea(listParametroFormularioDTO, historicoDeInstDeTarea, instanciaDeTareaDeOrigen, usuario);				
			}	
					
			return;
			
		}	
	
		calModInstTarea.setTime(new Date());
		instanciaDeTareaSiguiente.setFechaVencimiento(FechaUtil.getFechaHabil(calModInstTarea, fechaFeriadoDao, instanciaDeTareaSiguiente.getTarea().getDiasHabilesMaxDuracion()).getTime());
		instanciaDeTareaSiguiente.setFechaAsignacion(new Date());	
		instanciaDeTareaSiguiente.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaAsignadaType.getCodigoEstadoDeTarea()));		
		instanciaDeTareaSiguiente.setIdUsuarioQueAsigna(usuarioSGDP);
		
		log.debug("Validando instanciaDeTareaSiguiente asignada");
		log.debug(instanciaDeTareaSiguiente.toString());
		
		//Se elimina la asignacion anterior		
		List<UsuarioAsignado> usuarioAsignados = usuarioAsignadoDao.getUsuariosAsignadosPorInstanciaDeTarea(instanciaDeTareaDeOrigen);
		for (UsuarioAsignado usuarioAsignado: usuarioAsignados) {
			log.debug("Borrando usuario asignado: " + usuarioAsignado.toString());
			usuarioAsignadoDao.deleteUsuarioAsignado(usuarioAsignado);
		}		
		
		//Se asigna la tarea a los usuarios seleccionados
		//String[] nombresDeUsuariosAsignadosArray = nombresDeUsuariosAsignados.split(caracterSeparadorDeUsuarios);
		
		// Se cambia MAP Por arrayList porque las llaves que se asignas al Map se repiten y eso causa que se reemplacen los valores 
		// y salga que la gente esta de vacaciones 
		// ##################################################################
		//Map<Integer, String> posiblesUsuarios = new TreeMap<Integer, String>();
		
		List<String> listaPosiblesUsuarios = new ArrayList<>();
		
		// ##################################################################
		
		String usuarioAAsignar;
		
		for (String nombreDeUsuarioAsignado : nombresDeUsuariosAsignadosArray) {
			
			usuarioResponsabilidadService.cargaUsuariosRolesPosiblesConOrdenPorIdInstanciaDeTarea(instanciaDeTareaSiguiente.getIdInstanciaDeTarea(), listaPosiblesUsuarios);
			
			if (listaPosiblesUsuarios.isEmpty()) {
			    log.debug("Todos los usuarios en el rol se han registrado como fuera de oficina");
			    throw new SgdpException("Uno de los usuarios seleccionados se ha registrado como fuera de oficina");
			}
			
			usuarioAAsignar = getUsuarioAAsignarIgualAUsuarioDeOrigenListaString(listaPosiblesUsuarios, nombreDeUsuarioAsignado);			
			
			if (usuarioAAsignar==null) {
				log.debug("Uno de los usuarios seleccionados se ha registrado como fuera de oficina(usuario no en la lista : "+nombreDeUsuarioAsignado+ ")");
			    throw new SgdpException("Uno de los usuarios seleccionados se ha registrado como fuera de oficina");
			}
						
			gestorMetadataService.actualizaUsuariosQueHanModificadoExpediente(usuario, continuarProcesoDTO.getIdExpedienteContinuarProceso(), nombreDeUsuarioAsignado);
			nombreUsuariosAsignadosSB.append(nombreDeUsuarioAsignado);
			UsuarioAsignadoPK usuarioAsignadoPK = new UsuarioAsignadoPK(instanciaDeTareaSiguiente, nombreDeUsuarioAsignado);
			UsuarioAsignado usuarioAsignado = new UsuarioAsignado();		
			usuarioAsignado.setId(usuarioAsignadoPK);
			listaDeUsuariosAsignados.add(nombreDeUsuarioAsignado);
			log.debug("Insertando usuario asignado");
			log.debug("nombreDeUsuarioAsignado: " + nombreDeUsuarioAsignado);
			log.debug("Id instanciaDeTareaSiguiente: " + instanciaDeTareaSiguiente.getIdInstanciaDeTarea());
			usuarioAsignadoDao.insertUsuarioAsignado(usuarioAsignado, usuario);
			contadorNombreUsuariosAsigandos = contadorNombreUsuariosAsigandos + 1;
			if (contadorNombreUsuariosAsigandos < nombresDeUsuariosAsignadosArray.length) {
				nombreUsuariosAsignadosSB.append("; ");
			}
		}		
		
		log.debug("nombreUsuariosAsignadosSB.toString():" + nombreUsuariosAsignadosSB.toString());
		continuarProcesoDTO.getTareasUsuarios().add(MessageFormat.format(configProps.getProperty("tareaSeAsigno"),
				instanciaDeTareaSiguiente.getTarea().getNombreTarea() //+ " ("+ instanciaDeTareaSiguiente.getIdInstanciaDeTarea() +") "
				, nombreUsuariosAsignadosSB.toString()));
		
		//Se inserta un nuevo registro en historico de instancia de tarea
		log.debug("Insertando en HistoricoDeInstDeTarea");
		HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
		historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(idAccion));
		historicoDeInstDeTarea.setComentario(continuarProcesoDTO.getComentario());
		historicoDeInstDeTarea.setIdUsuarioOrigen(usuarioSGDP);
		historicoDeInstDeTarea.setFechaMovimiento(new Date());
		historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTareaSiguiente);
		historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTareaDeOrigen);
		historicoDeInstDeTarea.setHorasOcupadas(continuarProcesoDTO.getHorasOcupadas());
		historicoDeInstDeTarea.setMinutosOcupados(continuarProcesoDTO.getMinutosOcupados());
		
		if (continuarProcesoDTO.isAvanzaProcesoConAdvertenciaVisacion()) {
			historicoDeInstDeTarea.setMensajeException(MensajeExceptionType.AVANZA_PROCESO_CON_ADVERTENCIA_VISACION.getNombreMensajeException());
		}
		if ( continuarProcesoDTO.isAvanzaProcesoConAdvertenciaFEA()) {
			historicoDeInstDeTarea.setMensajeException(MensajeExceptionType.AVANZA_PROCESO_CON_ADVERTENCIA_FEA.getNombreMensajeException());
		}
		historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);		
			
		//Se guarda en historico de usuarios asignados
		for (String nombreDeUsuarioAsignado : nombresDeUsuariosAsignadosArray) {				
			log.debug("Insertando en historico de usuarios asignados");			
			HistoricoUsuariosAsignadosATareaPK historicoUsuariosAsignadosATareaPK = new HistoricoUsuariosAsignadosATareaPK(historicoDeInstDeTarea, nombreDeUsuarioAsignado);
			HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea = new HistoricoUsuariosAsignadosATarea();
			historicoUsuariosAsignadosATarea.setId(historicoUsuariosAsignadosATareaPK);
			historicoUsuariosAsignadosATareaDao.insertHistoricoUsuarioAsignadoATarea(historicoUsuariosAsignadosATarea, usuario);
		}		
		
		List<ArchivosInstDeTarea> archivosInstDeTareaList = instanciaDeTareaDeOrigen.getArchivosInstDeTarea();
		for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaList) {			
			HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea = new HistoricoArchivosInstDeTarea(historicoDeInstDeTarea, archivosInstDeTarea);		
			historicoArchivosInstDeTareaDao.insertArchivosHistInstDeTarea(historicoArchivosInstDeTarea, usuario);
		}
		
		//Se guardan parametros de formulario
		if (tieneRdsSnc!=null && tieneRdsSnc.booleanValue() == true && continuarProcesoDTO.getParametrosMapParaGuardarJSON()!=null && !continuarProcesoDTO.getParametrosMapParaGuardarJSON().isEmpty()) {
			log.debug("continuarProcesoDTO.getParametrosMapParaGuardarJSON(): " + continuarProcesoDTO.getParametrosMapParaGuardarJSON());
			ObjectMapper mapper = SingleObjectFactory.getMapper();
			List<ParametroFormularioDTO> listParametroFormularioDTO;
			try {				
				listParametroFormularioDTO = mapper.readValue(continuarProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){});					
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
			}
			parametroDeTareaService.guardaHistoricoValorParametroDeTarea(listParametroFormularioDTO, historicoDeInstDeTarea, instanciaDeTareaDeOrigen, usuario);				
		}	
		
		emailService.enviarMail(usuario, instanciaDeTareaDeOrigen, listaDeUsuariosAsignados, continuarProcesoDTO.getComentario(), instanciaDeTareaSiguiente);		
		
	}
	
	/*public String getComentarioDePorIdParamTareaEnListParametroFormularioDTO(String idParamTarea, List<ParametroFormularioDTO> listParametroFormularioDTO) {
		for (ParametroFormularioDTO parametroFormularioDTO : listParametroFormularioDTO) {	
			if (parametroFormularioDTO.getName().contains("-") && parametroFormularioDTO.getName().contains(idParamTarea)) {
				if (parametroFormularioDTO.getValue() instanceof String) {
					return(String) parametroFormularioDTO.getValue();					
				} else if (parametroFormularioDTO.getValue() instanceof String[]) {
					String[] valor = (String[]) parametroFormularioDTO.getValue();
					return StringUtils.join(valor, ";");
				}				
			}
		}		
		return null;
	}*/

	/**
	 * Avanza un proceso a la siguiente tarea
	 * @param  continuarProcesoDTO  objeto que mapea al formulario que contiene los datos necesario para avanzar el proceso a la siguiente tarea
	 * @param  usuario 
	 * @return indica si fue exitoso o erronea la accion de avanzar a la siguiente tarea
	 * @throws JsonProcessingException 
	 */
	@Override
	public String avanzaProceso(ContinuarProcesoDTO continuarProcesoDTO, Usuario usuario) throws SgdpException {		
		log.debug("avanzaProceso... inicio");
		boolean validaTareaEstaBE = true;		
		List<ParametroFormularioDTO> listParametroFormularioDTO;
		if (continuarProcesoDTO.getAsignacionesTareasJSON()!=null && !continuarProcesoDTO.getAsignacionesTareasJSON().isEmpty()) {
			log.debug("continuarProcesoDTO.getAsignacionesTareasJSON(): " + continuarProcesoDTO.getAsignacionesTareasJSON());
			ObjectMapper mapper = SingleObjectFactory.getMapper();
			List<AsignacionTareaDTO> asignacionesTareasDTO;
			InstanciaDeTarea instanciaDeTareaDeOrigen = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(continuarProcesoDTO.getIdInstanciaDeTareaOrigen());
			Tarea tareaDeOrigen = instanciaDeTareaDeOrigen.getTarea();
			Boolean tieneRdsSnc = instanciaDeTareaDeOrigen.getInstanciaDeProceso().getProceso().getTieneRdsSnc();
			if ((continuarProcesoDTO.getIdUsuarioMueve()!=null && !continuarProcesoDTO.getIdUsuarioMueve().isEmpty()) || continuarProcesoDTO.getReasigna()==true) {
				validaTareaEstaBE = false;
			}
			if (continuarProcesoDTO.getReasigna() == false) {
				validaSiPuedeAvanzar(usuario, instanciaDeTareaDeOrigen, validaTareaEstaBE);
			}			
			try {
				asignacionesTareasDTO = mapper.readValue(continuarProcesoDTO.getAsignacionesTareasJSON(), new TypeReference<List<AsignacionTareaDTO>>(){});
				continuarProcesoDTO.setAsignacionesTareasDTO(asignacionesTareasDTO);
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
			}			
			for (AsignacionTareaDTO asignacionTareaDTO : continuarProcesoDTO.getAsignacionesTareasDTO()) {
				log.debug("asignacionTareaDTO.getIdInstanciaDeTarea(): " + asignacionTareaDTO.getIdInstanciaDeTarea());
				log.debug("asignacionTareaDTO.getUsuariosAsignados(): " + asignacionTareaDTO.getUsuariosAsignados());
				avanza(continuarProcesoDTO, usuario, asignacionTareaDTO.getIdInstanciaDeTarea(), asignacionTareaDTO.getUsuariosAsignados(), asignacionTareaDTO.getFechaAsignada(),
						instanciaDeTareaDeOrigen);
			}
			//Se guardan parametros de formulario
			if (tieneRdsSnc!=null && tieneRdsSnc.booleanValue() == true && continuarProcesoDTO.getParametrosMapParaGuardarJSON()!=null && !continuarProcesoDTO.getParametrosMapParaGuardarJSON().isEmpty()) {
				log.debug("continuarProcesoDTO.getParametrosMapParaGuardarJSON(): " + continuarProcesoDTO.getParametrosMapParaGuardarJSON());
				try {				
					listParametroFormularioDTO = mapper.readValue(continuarProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){});					
				} catch (IOException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					log.error(exceptionAsString);				
					throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
				}
				parametroDeTareaService.guardaValorParametroDeTarea(listParametroFormularioDTO, instanciaDeTareaDeOrigen);				
			}			
			log.info("Verificamos si es una tarea que llama a un ws");
			log.info(tareaDeOrigen.toString());
			//Verificamos si es una tarea que llama a un ws
			if (tareaDeOrigen.getUrlWS()!=null && continuarProcesoDTO.getReasigna()!=true && continuarProcesoDTO.getReabre()!=true) {
				IntegracionSatelitesDTO integracionSatelitesDTO = new IntegracionSatelitesDTO();
				integracionSatelitesDTO.setIdExpediente(continuarProcesoDTO.getIdExpedienteContinuarProceso());
				integracionSatelitesDTO.setNombreExpediente(instanciaDeTareaDeOrigen.getInstanciaDeProceso().getNombreExpediente());
				integracionSatelitesDTO.setUrlWS(tareaDeOrigen.getUrlWS());				
				invocaWS(integracionSatelitesDTO, continuarProcesoDTO.getIdInstanciaDeTareaOrigen(), usuario);				
			}
			//Verificamos si es una tarea de reseteo y si tiene alguna instancia de tarea anterior enviada
			HistoricoDeInstDeTarea historicoDeInstDeTareaUltimo = historicoDeInstDeTareaDao.getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino(instanciaDeTareaDeOrigen);
			if (tareaDeOrigen.getDiasReseteo()!=null && tareaDeOrigen.getDiasReseteo()>0 && tareaDeOrigen.getTipoReseteo()!=null && !tareaDeOrigen.getTipoReseteo().isEmpty() && historicoDeInstDeTareaUltimo != null) {
				log.debug("Tarea de reseteo");
				log.debug(historicoDeInstDeTareaUltimo.toString());
				Calendar calModFechaBase = Calendar.getInstance();
				if (tareaDeOrigen.getTipoReseteo().equalsIgnoreCase(ReseteoType.TAREA.getNombreReseteo())) {
					log.debug("Tipo de reseteo por tarea");
					calModFechaBase.setTime(new Date());
				} else if (tareaDeOrigen.getTipoReseteo().equalsIgnoreCase(ReseteoType.DOCUMENTO.getNombreReseteo())) {
					log.debug("Tipo de reseteo por documento");
					List<ArchivosInstDeTarea> archivosInstDeTarea = archivosInstDeTareaDao.getArchivosPorIdInstanciaDeTarea(instanciaDeTareaDeOrigen.getIdInstanciaDeTarea());
					if (archivosInstDeTarea.isEmpty()) {
						throw new SgdpException(configProps.getProperty("sinArchivosParaResetearPlazo"), Level.WARN, false);
					}
					ArchivosInstDeTarea archivoInstDeTarea = archivosInstDeTarea.get(0);
					DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, archivoInstDeTarea.getIdArchivoCms());
					log.debug(detalleDeArchivoDTO.toString());
					try {
						Date fechaBaseReseteo = FechaUtil.simpleDateFormatForm.parse(detalleDeArchivoDTO.getFechaDeRecepcion());
						log.debug("fechaBaseReseteo: " + fechaBaseReseteo);
						calModFechaBase.setTime(fechaBaseReseteo);						
					} catch (ParseException e) {
						throw new SgdpException(configProps.getProperty("errorAlParsearPorFechaRecep"), Level.WARN, false);
					}
				}
				//Se setea la fecha de vencimiento solo si el nuevo plazo es mayor que la actual fecha de vencimiento
				//Registra en HistoricoFechaVencimientoInstanciaProceso
				Date nuevaFechaDeVencimiento = FechaUtil.getFechaHabil(calModFechaBase, fechaFeriadoDao, tareaDeOrigen.getDiasReseteo()).getTime();	
				Date fechaDeVencimientoInstProceso = instanciaDeTareaDeOrigen.getInstanciaDeProceso().getFechaVencimiento();
				log.debug("nuevaFechaDeVencimiento: " + nuevaFechaDeVencimiento);
				log.debug("fechaDeVencimientoInstProceso: " + fechaDeVencimientoInstProceso);
				if (fechaDeVencimientoInstProceso.compareTo(nuevaFechaDeVencimiento)<0) {
					log.debug("Fecha de vencimiento de la instancia del subproceso menor a la nueva fecha de vencimiento");
					instanciaDeTareaDeOrigen.getInstanciaDeProceso().setFechaVencimiento(nuevaFechaDeVencimiento);
					HistoricoFechaVencimientoInstanciaProceso historicoFechaVencimientoInstanciaProceso = new HistoricoFechaVencimientoInstanciaProceso();
					historicoFechaVencimientoInstanciaProceso.setFechaVencimiento(fechaDeVencimientoInstProceso);
					historicoFechaVencimientoInstanciaProceso.setIdUsuario(usuario.getIdUsuario());
					historicoFechaVencimientoInstanciaProceso.setInstanciaDeTarea(instanciaDeTareaDeOrigen);
					historicoFechaVencimientoInstanciaProcesoDao.insertaHistoricoFechaVencimientoInstanciaProceso(historicoFechaVencimientoInstanciaProceso);
				}				
				log.debug(instanciaDeTareaDeOrigen.toString());
			}
			//Verificamos si es una tarea de Distribucion
			if (tareaDeOrigen.getDistribuye()!=null && tareaDeOrigen.getDistribuye().booleanValue() == true && continuarProcesoDTO.getReasigna()!=true && continuarProcesoDTO.getReabre()!=true) {
				try { 
					List<String> correosDeDistribucion = mapper.readValue(continuarProcesoDTO.getCorreosDeDistribucionJSON(), new TypeReference<List<String>>(){});
					List<String> idArchivosADistribuir = mapper.readValue(continuarProcesoDTO.getIdArchivosADistribuirJSON(), new TypeReference<List<String>>(){});
					emailService.enviarCorreosConAdjuntosAListaDeDistribucion(correosDeDistribucion, idArchivosADistribuir, 
							continuarProcesoDTO.getIdExpedienteContinuarProceso(), 
							instanciaDeTareaDeOrigen.getInstanciaDeProceso().getNombreExpediente(),
							instanciaDeTareaDeOrigen.getIdInstanciaDeTarea(),
							usuario,
							continuarProcesoDTO.getAsuntoCorreoDistribucion());
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					log.error(exceptionAsString);				
					throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
				}				
			}
			//Verificamos si es una tarea de notificacion para notificar y enviar correo
			List<UsuarioNotificacionTarea> usuariosNotificacionTarea = tareaDeOrigen.getUsuarioNotificacionTarea();
			if (usuariosNotificacionTarea!=null && !usuariosNotificacionTarea.isEmpty()) {
				MensajeJson mensajeJson = new MensajeJson();
				InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
				instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTareaDeOrigen);
				InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
				instanciaDeProcesoDTO.setIdInstanciaDeProceso(instanciaDeTareaDeOrigen.getInstanciaDeProceso().getIdInstanciaDeProceso());
				for (UsuarioNotificacionTarea usuarioNotificacionTarea : usuariosNotificacionTarea) {
					log.debug(usuarioNotificacionTarea.toString());
					instanciaDeProcesoService.guardarSeguimiento(instanciaDeProcesoDTO, mensajeJson, usuarioNotificacionTarea.getIdUsuario(), usuario, NotificacionType.PREDETERMINADA.getNombreTipoNotificacion());
					gestorMetadataService.actualizaUsuariosQueHanModificadoExpediente(usuario, continuarProcesoDTO.getIdExpedienteContinuarProceso(), usuarioNotificacionTarea.getIdUsuario());
					log.debug(mensajeJson.toString());
					if (mensajeJson.getMensaje().equals("ERROR")) {
						throw new SgdpException("Ocurrio un error al notificar");
					}
				}
				for (UsuarioNotificacionTarea usuarioNotificacionTarea : usuariosNotificacionTarea) {
					emailService.enviarMailNotificacionPorTarea(instanciaDeTareaDTO, usuarioNotificacionTarea.getIdUsuario(), usuario);			
				}
			}
			if (continuarProcesoDTO.getReasigna()!=true) {
				instanciaDeTareaDeOrigen.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaFinalizadaType.getCodigoEstadoDeTarea()));
			}			
			instanciaDeTareaDeOrigen.setFechaFinalizacion(new Date());			
		}	
		log.debug("avanzaProceso... fin");
		return configProps.getProperty("respuestaOK");
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public String retrocedeProceso(RetrocedeProcesoDTO retrocedeProcesoDTO, Usuario usuario) throws SgdpException {
		
		log.debug("retrocedeProceso... inicio");
		
		String respuesta;	
		List<String> listaDeUsuariosAsignados = new ArrayList<String>();
		String usuarioDeOrigen;		
		
		ObjectMapper mapper = SingleObjectFactory.getMapper();
		
		InstanciaDeTarea instanciaDeTareaActual = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(retrocedeProcesoDTO.getIdInstanciaDeTareaSeleccionada());
		log.debug("instanciaDeTareaActual: " + instanciaDeTareaActual.toString());	
		
		Boolean tieneRdsSnc = instanciaDeTareaActual.getInstanciaDeProceso().getProceso().getTieneRdsSnc();
		
		//Valida si la tarea la tiene el usuario que esta intentando retroceder		
		log.debug("Valida si la tarea la tiene el usuario que esta intentando retroceder");
		UsuarioAsignado ua = usuarioAsignadoDao.getUsuarioAsignadoPorInstanciaDeTareaIdUsuario(instanciaDeTareaActual, usuario.getIdUsuario());
		if (ua == null) {			
			SgdpException se = new SgdpException(configProps.getProperty("tareaNoEstaEnBE"), Level.WARN, true);
			throw se;
		}
		
		HistoricoDeInstDeTarea historicoDeInstDeTareaUltimo = historicoDeInstDeTareaDao.getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino(instanciaDeTareaActual);
		log.debug("historicoDeInstDeTareaUltimo: " + historicoDeInstDeTareaUltimo.toString());
		InstanciaDeTarea instanciaDeTareaAnterior = historicoDeInstDeTareaUltimo.getInstanciaDeTareaDeOrigen();//instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(historicoDeInstDeTareaUltimo.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea());
		log.debug("instanciaDeTareaAnterior: " + instanciaDeTareaAnterior.toString());		
		
		retrocedeProcesoDTO.setIdInstanciaDeTareaSeleccionadaActualSalida(instanciaDeTareaAnterior.getIdInstanciaDeTarea() + "");

		retrocedeProcesoDTO.setNombreDeTarea(instanciaDeTareaAnterior.getTarea().getNombreTarea());
	
		//Se actualiza el estado de la tarea actual a nueva
		instanciaDeTareaActual.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaNuevaType.getCodigoEstadoDeTarea()));
		
		List<UsuarioAsignado> usuarioAsignados = usuarioAsignadoDao.getUsuariosAsignadosPorInstanciaDeTarea(instanciaDeTareaActual);
		for (UsuarioAsignado usuarioAsig: usuarioAsignados) {
			usuarioAsignadoDao.deleteUsuarioAsignado(usuarioAsig);
		}	
		
		List<String> listaPosiblesUsuarios = new ArrayList<>();
		
		usuarioResponsabilidadService.cargaUsuariosRolesPosiblesConOrdenPorIdInstanciaDeTarea(instanciaDeTareaAnterior.getIdInstanciaDeTarea(), listaPosiblesUsuarios);
				
		if (listaPosiblesUsuarios.isEmpty()) {
			throw new SgdpException("No hay usuarios disponibles para devolver la tarea");
		}
	
		usuarioDeOrigen = getUsuarioAAsignarIgualAUsuarioDeOrigenListaString(listaPosiblesUsuarios, historicoDeInstDeTareaUltimo.getIdUsuarioOrigen());
		
		if (usuarioDeOrigen == null) {
			usuarioDeOrigen = listaPosiblesUsuarios.get(0);
		}
		
		respuesta = MessageFormat.format(configProps.getProperty("tareaDevueltaOK"),
				instanciaDeTareaAnterior.getTarea().getNombreTarea() 
				, usuarioDeOrigen);
		
		//Se inserta un nuevo registro en historico de instancia de tarea
		log.debug("Insertando en HistoricoDeInstDeTarea");
		HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
		historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(accionDevuelveHistInstDeTareaType.getIdAccionHistoricoInstDeTarea()));
		historicoDeInstDeTarea.setComentario(retrocedeProcesoDTO.getComentario());
		historicoDeInstDeTarea.setIdUsuarioOrigen(usuario.getIdUsuario());
		historicoDeInstDeTarea.setFechaMovimiento(new Date());
		historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTareaAnterior);
		historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTareaActual);
		historicoDeInstDeTarea.setHorasOcupadas(retrocedeProcesoDTO.getHorasOcupadas());
		historicoDeInstDeTarea.setMinutosOcupados(retrocedeProcesoDTO.getMinutosOcupados());
		historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);
		HistoricoUsuariosAsignadosATareaPK historicoUsuariosAsignadosATareaPK = new HistoricoUsuariosAsignadosATareaPK(historicoDeInstDeTarea, usuarioDeOrigen);
		HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea = new HistoricoUsuariosAsignadosATarea();
		historicoUsuariosAsignadosATarea.setId(historicoUsuariosAsignadosATareaPK);
		historicoUsuariosAsignadosATareaDao.insertHistoricoUsuarioAsignadoATarea(historicoUsuariosAsignadosATarea, usuario);		
		
		List<ArchivosInstDeTarea> archivosInstDeTareaList = instanciaDeTareaActual.getArchivosInstDeTarea();
		for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaList) {
			HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea = new HistoricoArchivosInstDeTarea(historicoDeInstDeTarea, archivosInstDeTarea);		
			historicoArchivosInstDeTareaDao.insertArchivosHistInstDeTarea(historicoArchivosInstDeTarea, usuario);
		}
		
		//Se actualiza el estado de la tarea anterior a asignada si es que la instancia de la tarea anterior no tiene usuarios asignados
		if (instanciaDeTareaAnterior.getUsuariosAsignados()==null || instanciaDeTareaAnterior.getUsuariosAsignados().isEmpty()) {			
			
			instanciaDeTareaAnterior.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaAsignadaType.getCodigoEstadoDeTarea()));
			instanciaDeTareaAnterior.setFechaFinalizacion(null);
			instanciaDeTareaAnterior.setIdUsuarioQueAsigna(usuario.getIdUsuario());
			instanciaDeTareaAnterior.setFechaAsignacion(new Date());
			Calendar calModInstTarea = Calendar.getInstance();
			calModInstTarea.setTime(new Date());
			instanciaDeTareaAnterior.setFechaVencimiento(FechaUtil.getFechaHabil(calModInstTarea, fechaFeriadoDao, instanciaDeTareaAnterior.getTarea().getDiasHabilesMaxDuracion()).getTime());			
		
			//Se le asigna la tarea al usuario de origen
			UsuarioAsignadoPK usuarioAsignadoPK = new UsuarioAsignadoPK(instanciaDeTareaAnterior, usuarioDeOrigen);				
					
			UsuarioAsignado usuarioAsignado = new UsuarioAsignado();		
			usuarioAsignado.setId(usuarioAsignadoPK);
			listaDeUsuariosAsignados.add(usuarioAsignado.getId().getIdUsuario());
			usuarioAsignadoDao.insertUsuarioAsignado(usuarioAsignado, usuario);
			
			gestorMetadataService.actualizaUsuariosQueHanModificadoExpediente(usuario, instanciaDeTareaActual.getInstanciaDeProceso().getIdExpediente(), usuarioAsignado.getId().getIdUsuario());
		
			emailService.enviarMail(usuario, instanciaDeTareaActual, listaDeUsuariosAsignados, retrocedeProcesoDTO.getComentario(), instanciaDeTareaAnterior);
		
		}
		
		//Se guardan parametros de formulario
		if (tieneRdsSnc!=null && tieneRdsSnc.booleanValue() == true && retrocedeProcesoDTO.getParametrosMapParaGuardarJSON()!=null && !retrocedeProcesoDTO.getParametrosMapParaGuardarJSON().isEmpty()) {			
			//List<ParametroFormularioDTO> listParametroFormularioDTO;
			log.debug("retrocedeProceso.getParametrosMapParaGuardarJSON(): " + retrocedeProcesoDTO.getParametrosMapParaGuardarJSON());
			try {				
				//listParametroFormularioDTO = mapper.readValue(retrocedeProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){});				
				parametroDeTareaService.guardaValorParametroDeTarea((List<ParametroFormularioDTO>) mapper.readValue(retrocedeProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){}), instanciaDeTareaActual);
				parametroDeTareaService.guardaHistoricoValorParametroDeTarea((List<ParametroFormularioDTO>) mapper.readValue(retrocedeProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){}), historicoDeInstDeTarea, instanciaDeTareaActual, usuario);				
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
			}
							
		}
		
		log.debug("retrocedeProceso... fin");		
		
		return respuesta;
	
	}
	
	
	@Override
	public String getUsuarioAAsignarIgualAUsuarioDeOrigenListaString(List<String> listaPosiblesUsuarios,
			String nombreDeUsuarioAsignado) {
		
		log.debug("getUsuarioAAsignarIgualAUsuarioDeOrigen... incio");
	
		for (String posibleUsuario : listaPosiblesUsuarios) {
			if (posibleUsuario.equals(nombreDeUsuarioAsignado)){
				return nombreDeUsuarioAsignado;
			}
		}		
		return null;
	}		
	
	private void validaSiPuedeAvanzar(Usuario usuario, InstanciaDeTarea instanciaDeTarea, boolean validaTareaEstaBE) throws SgdpException {
		
		log.debug("validaSiPuedeAvanzar... inicio");
		
		boolean verificaSiTipoDeDocEstaEnListaArchivoInfoDTO = false;
		
		Tarea tarea = instanciaDeTarea.getTarea();
		
		ParametroDTO parametroDTOToleranciaNombreTipoDoc = parametroService.getParametroPorID(Constantes.ID_PARAM_MAX_DIF_TOLERANCIA_NOMBRE_TIPO_DOC);		 
		//Valida si todos los tipos de documentos necesarios para avanzar existen
		log.debug("Valida si todos los tipos de documentos necesarios para avanzar existen");
		List<ArchivoInfoDTO> archivosInfoDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
				instanciaDeTarea.getInstanciaDeProceso().getIdExpediente(),
				true,
				tarea.getPuedeVisarDocumentos(),
				tarea.getPuedeAplicarFEA(),
				instanciaDeTarea.getIdInstanciaDeTarea());
		
		List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTarea = tarea.getDocumentosDeSalidasDeTarea();
		
		for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea : documentosDeSalidasDeTarea) {
			log.debug("documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento(): " + documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento());
			verificaSiTipoDeDocEstaEnListaArchivoInfoDTO = verificaSiTipoDeDocEstaEnListaArchivoInfoDTO(archivosInfoDTO, 
					documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento(), 
					parametroDTOToleranciaNombreTipoDoc.getValorParametroNumerico().intValue());
			if (verificaSiTipoDeDocEstaEnListaArchivoInfoDTO==false) {
				throw new SgdpException (configProps.getProperty("instanciaDeTareaSinDocumentosDeSalidaCargados"), Level.WARN, false);
			}			
		}	
		
		//Valida si todas las instancias de las tareas anteriores asignadas estan finalizadas		
		log.debug("Valida si todas las instancias de las tareas anteriores asignadas estan finalizadas");		
		if (tarea.getOrden()>Integer.parseInt(configProps.getProperty("numeroPrimeraTarea"))) {			
			List<InstanciaDeTarea> instanciasDeTareasPorIdNoFinalizadasConAntAnd = 
					instanciaDeTareaDao.getInstanciasDeTareasAnterioresAsignadasPorIdInstanciaDeProcesoIdInstanciaDeTarea(instanciaDeTarea.getInstanciaDeProceso().getIdInstanciaDeProceso(), instanciaDeTarea.getIdInstanciaDeTarea());						
			if (instanciasDeTareasPorIdNoFinalizadasConAntAnd != null && instanciasDeTareasPorIdNoFinalizadasConAntAnd.size()>0 ) {
				throw new SgdpException (configProps.getProperty("tareasAnterioresSinFinalizar"), Level.WARN, false);
			}			
		}
		
		if (validaTareaEstaBE == true) {
			//Valida si la tarea la tiene el usuario que esta intentando avanzar		
			log.debug("Valida si la tarea la tiene el usuario que esta intentando avanzar");
			UsuarioAsignado ua = usuarioAsignadoDao.getUsuarioAsignadoPorInstanciaDeTareaIdUsuario(instanciaDeTarea, usuario.getIdUsuario());
			if (ua == null) {
				SgdpException se = new SgdpException(configProps.getProperty("tareaNoEstaEnBE"), Level.WARN, true);
				throw se;
			}
		}
		
		//Valida si todos los documentos de FEA estan firmados
		if (tarea.getPuedeAplicarFEA()) {	
			
			boolean algunDocumentoConFEA = false;
			
			for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea : documentosDeSalidasDeTarea) {
				if (documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getAplicaFEA()==true) {
					algunDocumentoConFEA = true;
					break;
				}
			}
			
			if (algunDocumentoConFEA==false) {
				log.warn("Esta es una tarea de firma avanzada, pero no tiene documentos marcados para firma avanzada");
				SgdpException se = new SgdpException("Esta es una tarea de firma avanzada, pero no tiene documentos marcados para firma avanzada", Level.WARN, true);
				throw se;
			}
			
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			
			instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTO);
			
			obtenerArchivosExpedienteService.cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(usuario, instanciaDeTareaDTO, 
					new ArrayList<ArchivoInfoDTO>(), archivosInfoDTO);	
			
			if (instanciaDeTareaDTO.isPuedeAvanzarProcesoConAdvertenciaFEA() == true) {
				log.warn("No estan todos los documentos firmados");
				SgdpException se = new SgdpException("Esta es una tarea de firma avanzada, pero no ha firmado todos los documentos. Por favor firmar todos los documentos", Level.WARN, false);
				throw se;
			} else {
				log.info("Todos los documentos firmados");
			}	
		
		}
		
		//Valida si la tarea es de numeracion auto exista al menos un documento con numeracion auto
		if (tarea.getNumAuto()!=null && tarea.getNumAuto().booleanValue() == true) {
			
			boolean algunDocumentoConNumAuto = false;
			
			for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea : documentosDeSalidasDeTarea) {
				if (documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNumAuto()==true) {
					algunDocumentoConNumAuto = true;
					break;
				}
			}
			
			if (algunDocumentoConNumAuto==false) {
				log.warn("Esta es una tarea de numeracion automatica, pero no tiene documentos marcados para tal efecto");
				SgdpException se = new SgdpException("Esta es una tarea de numeracion automatica, pero no tiene documentos marcados para tal efecto", Level.WARN, true);
				throw se;
			}
			
		}
		
		log.debug("validaSiPuedeAvanzar... fin");
		
	}
	
		
	private boolean verificaSiTipoDeDocEstaEnListaArchivoInfoDTO(List<ArchivoInfoDTO> archivosInfoDTO, String nombreTipoDeDocumentoDeSalida, int toleranciaDifNomTipoDoc) {
		boolean comparaStringConLevenshteinDistance = false;
		for (ArchivoInfoDTO archivoInfoDTO : archivosInfoDTO) {
			comparaStringConLevenshteinDistance = StringUtilSGDP.comparaStringConLevenshteinDistance(archivoInfoDTO.getTipoDeDocumento(), 
					nombreTipoDeDocumentoDeSalida, toleranciaDifNomTipoDoc);
			if (comparaStringConLevenshteinDistance == true) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void finaliza(Usuario usuario, FinalizaProcesoDTO finalizaProcesoDTO) throws SgdpException {		
		
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(finalizaProcesoDTO.getIdInstanciaDeTarea());
		InstanciaDeProceso instanciaDeProceso = instanciaDeTarea.getInstanciaDeProceso();
		EstadoDeProceso estadoDeProcesoFinalizado = estadoDeProcesoDao.getEstadoDeProcesoPorCodigo(estadoDeProcesoFinalizadoType.getCodigoEstadoDeProceso());
		Tarea tareaDeOrigen = instanciaDeTarea.getTarea();
		
		Boolean tieneRdsSnc = instanciaDeTarea.getInstanciaDeProceso().getProceso().getTieneRdsSnc();
		
		ObjectMapper mapper = SingleObjectFactory.getMapper();
		
		List<InstanciaDeTarea> instanciaDeTareaAunAsignadas = 
				instanciaDeTareaDao.getInstanciasDeTareasAsignadasPorIdInstanciaDeProcesoDistintasDeIdInstanciaDeTarea(instanciaDeProceso.getIdInstanciaDeProceso(),
						instanciaDeTarea.getIdInstanciaDeTarea());
		
		if (instanciaDeTareaAunAsignadas==null || instanciaDeTareaAunAsignadas.size()==0) {
			instanciaDeProceso.setEstadoDeProceso(estadoDeProcesoFinalizado);
			instanciaDeProceso.setFechaFin(new Date());
			instanciaDeProceso.setIdUsuarioTermina(usuario.getIdUsuario());
		}		
		
		if (finalizaProcesoDTO.getCierra()!=true) {
			validaSiPuedeAvanzar(usuario, instanciaDeTarea, true);
		}
		
		instanciaDeTarea.setFechaFinalizacion(new Date());
		instanciaDeTarea.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaFinalizadaType.getCodigoEstadoDeTarea()));		
		List<UsuarioAsignado> usuarioAsignados = usuarioAsignadoDao.getUsuariosAsignadosPorInstanciaDeTarea(instanciaDeTarea);
		for (UsuarioAsignado usuarioAsig: usuarioAsignados) {
			usuarioAsignadoDao.deleteUsuarioAsignado(usuarioAsig);
		}				
		log.debug("Insertando en HistoricoDeInstDeTarea");
		HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
		
		if (finalizaProcesoDTO.getCierra()==true) {
			historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(AccionesHistInstDeTareaType.CERRAR.getIdAccionHistoricoInstDeTarea()));
		} else {
			historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(accionFinalizaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea()));			
		}
		
		historicoDeInstDeTarea.setComentario(finalizaProcesoDTO.getComentario());
		historicoDeInstDeTarea.setIdUsuarioOrigen(usuario.getIdUsuario());
		historicoDeInstDeTarea.setFechaMovimiento(new Date());
		historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTarea);
		historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTarea);
		historicoDeInstDeTarea.setHorasOcupadas(finalizaProcesoDTO.getHorasOcupadas());
		historicoDeInstDeTarea.setMinutosOcupados(finalizaProcesoDTO.getMinutosOcupados());
		historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);
		
		//Se guarda en historico de archivos
		List<ArchivosInstDeTarea> archivosInstDeTareaList = instanciaDeTarea.getArchivosInstDeTarea();
		for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaList) {
			HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea = new HistoricoArchivosInstDeTarea(historicoDeInstDeTarea, archivosInstDeTarea);		
			historicoArchivosInstDeTareaDao.insertArchivosHistInstDeTarea(historicoArchivosInstDeTarea, usuario);
		}
		
		//Se guardan parametros de formulario
		if (tieneRdsSnc!=null && tieneRdsSnc.booleanValue() == true && finalizaProcesoDTO.getParametrosMapParaGuardarJSON()!=null && !finalizaProcesoDTO.getParametrosMapParaGuardarJSON().isEmpty()) {			
			//List<ParametroFormularioDTO> listParametroFormularioDTO;
			log.debug("finalizaProcesoDTO.getParametrosMapParaGuardarJSON(): " + finalizaProcesoDTO.getParametrosMapParaGuardarJSON());
			try {				
				//listParametroFormularioDTO = mapper.readValue(finalizaProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){});					
				parametroDeTareaService.guardaValorParametroDeTarea((List<ParametroFormularioDTO>) mapper.readValue(finalizaProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){}), instanciaDeTarea);
				parametroDeTareaService.guardaHistoricoValorParametroDeTarea((List<ParametroFormularioDTO>) mapper.readValue(finalizaProcesoDTO.getParametrosMapParaGuardarJSON(), new TypeReference<List<ParametroFormularioDTO>>(){}), historicoDeInstDeTarea, instanciaDeTarea, usuario);				
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
			}			
		}
		//Verificamos si es una tarea de Distribucion
		if (instanciaDeTarea.getTarea().getDistribuye()!=null && instanciaDeTarea.getTarea().getDistribuye().booleanValue() == true && finalizaProcesoDTO.getCierra()==false) {
			try { 
				List<String> correosDeDistribucion = mapper.readValue(finalizaProcesoDTO.getCorreosDeDistribucionJSON(), new TypeReference<List<String>>(){});
				List<String> idArchivosADistribuir = mapper.readValue(finalizaProcesoDTO.getIdArchivosADistribuirJSON(), new TypeReference<List<String>>(){});
				emailService.enviarCorreosConAdjuntosAListaDeDistribucion(correosDeDistribucion, idArchivosADistribuir, 
						instanciaDeProceso.getIdExpediente(), 
						instanciaDeTarea.getInstanciaDeProceso().getNombreExpediente(),
						instanciaDeTarea.getIdInstanciaDeTarea(),
						usuario,
						finalizaProcesoDTO.getAsuntoCorreoDistribucion());
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
			}				
		}
		//Verificamos si es una tarea de notificacion para notificar y enviar correo
		List<UsuarioNotificacionTarea> usuariosNotificacionTarea = instanciaDeTarea.getTarea().getUsuarioNotificacionTarea();
		if (usuariosNotificacionTarea!=null && !usuariosNotificacionTarea.isEmpty()) {
			MensajeJson mensajeJson = new MensajeJson();
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
			instanciaDeProcesoDTO.setIdInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getIdInstanciaDeProceso());
			for (UsuarioNotificacionTarea usuarioNotificacionTarea : usuariosNotificacionTarea) {
				instanciaDeProcesoService.guardarSeguimiento(instanciaDeProcesoDTO, mensajeJson, usuarioNotificacionTarea.getIdUsuario(), usuario, NotificacionType.PREDETERMINADA.getNombreTipoNotificacion());
				gestorMetadataService.actualizaUsuariosQueHanModificadoExpediente(usuario, instanciaDeProceso.getIdExpediente(), usuarioNotificacionTarea.getIdUsuario());
				if (mensajeJson.getMensaje().equals("ERROR")) {
					throw new SgdpException("Ocurrio un error al notificar");
				}
			}
			for (UsuarioNotificacionTarea usuarioNotificacionTarea : usuariosNotificacionTarea) {
				emailService.enviarMailNotificacionPorTarea(instanciaDeTareaDTO, usuarioNotificacionTarea.getIdUsuario(), usuario);			
			}				
		}
		log.info("Verificamos si es una tarea que llama a un ws");
		log.info(tareaDeOrigen.toString());
		//Verificamos si es una tarea que llama a un ws
		if (tareaDeOrigen.getUrlWS()!=null && finalizaProcesoDTO.getCierra()==false) {
			IntegracionSatelitesDTO integracionSatelitesDTO  = new IntegracionSatelitesDTO();
			integracionSatelitesDTO.setIdExpediente(instanciaDeProceso.getIdExpediente());
			integracionSatelitesDTO.setNombreExpediente(instanciaDeProceso.getNombreExpediente());
			integracionSatelitesDTO.setUrlWS(tareaDeOrigen.getUrlWS());			
			invocaWS(integracionSatelitesDTO, finalizaProcesoDTO.getIdInstanciaDeTarea(), usuario) ;	
		}		
	}
	
	@Override
	public void anularProceso(Usuario usuario, AnulacionDTO anulacionDTO) {
		EstadoDeProceso estadoDeProcesoAnulado = estadoDeProcesoDao.getEstadoDeProcesoPorCodigo(estadoDeProcesoAnuladoType.getCodigoEstadoDeProceso());
		EstadoDeTarea estadoDeTareaAnulada = estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaAnuladaType.getCodigoEstadoDeTarea());
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdInstanciaDeProceso(anulacionDTO.getIdInstanciaDeProceso());
		List<InstanciaDeTarea> instanciasDeTarea = instanciaDeProceso.getInstanciasDeTareas();
		for (InstanciaDeTarea instanciaDeTarea : instanciasDeTarea) {
			if (instanciaDeTarea.getEstadoDeTarea().getIdEstadoDeTarea() == EstadoDeTareaType.ASIGNADA.getCodigoEstadoDeTarea()	) {
				instanciaDeTarea.setFechaAnulacion(new Date());
				instanciaDeTarea.setEstadoDeTarea(estadoDeTareaAnulada);
				HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
				historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(accionAnulaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea()));
				historicoDeInstDeTarea.setComentario(anulacionDTO.getMotivoAnulacion());
				historicoDeInstDeTarea.setIdUsuarioOrigen(usuario.getIdUsuario());
				historicoDeInstDeTarea.setFechaMovimiento(new Date());
				historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTarea);
				historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTarea);
				historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);
				List<UsuarioAsignado> usuarioAsignados = usuarioAsignadoDao.getUsuariosAsignadosPorInstanciaDeTarea(instanciaDeTarea);
				for (UsuarioAsignado usuarioAsig: usuarioAsignados) {
					usuarioAsignadoDao.deleteUsuarioAsignado(usuarioAsig);
				}
			}			
		}
		instanciaDeProceso.setEstadoDeProceso(estadoDeProcesoAnulado);
		instanciaDeProceso.setFechaFin(new Date());
		instanciaDeProceso.setIdUsuarioTermina(usuario.getIdUsuario());	
		anulacionDTO.setRespuestaAnulacion(configProps.getProperty("respuestaOK"));
	}
	
	@Override
	public void anularProcesoPorIdExpediente(Usuario usuario, AnulacionDTO anulacionDTO) {
		EstadoDeProceso estadoDeProcesoAnulado = estadoDeProcesoDao.getEstadoDeProcesoPorCodigo(estadoDeProcesoAnuladoType.getCodigoEstadoDeProceso());
		EstadoDeTarea estadoDeTareaAnulada = estadoDeTareaDao.getEstadoDeTareaPorCodigo(estadoDeTareaAnuladaType.getCodigoEstadoDeTarea());
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(anulacionDTO.getIdExpediente());
		List<InstanciaDeTarea> instanciasDeTarea = instanciaDeProceso.getInstanciasDeTareas();
		String usuarioSGDP = anulacionDTO.getIdUsuarioAnula()!=null && !anulacionDTO.getIdUsuarioAnula().isEmpty() ? anulacionDTO.getIdUsuarioAnula() : usuario.getIdUsuario();
		log.info("usuarioSGDP: " + usuarioSGDP);
		for (InstanciaDeTarea instanciaDeTarea : instanciasDeTarea) {
			if (instanciaDeTarea.getEstadoDeTarea().getIdEstadoDeTarea() == EstadoDeTareaType.ASIGNADA.getCodigoEstadoDeTarea()	) {
				instanciaDeTarea.setFechaAnulacion(new Date());
				instanciaDeTarea.setEstadoDeTarea(estadoDeTareaAnulada);
				HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
				historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(accionAnulaHistInstDeTareaType.getIdAccionHistoricoInstDeTarea()));
				historicoDeInstDeTarea.setComentario(anulacionDTO.getMotivoAnulacion());
				historicoDeInstDeTarea.setIdUsuarioOrigen(usuarioSGDP);
				historicoDeInstDeTarea.setFechaMovimiento(new Date());
				historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTarea);
				historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTarea);
				historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);
				List<UsuarioAsignado> usuarioAsignados = usuarioAsignadoDao.getUsuariosAsignadosPorInstanciaDeTarea(instanciaDeTarea);
				for (UsuarioAsignado usuarioAsig: usuarioAsignados) {
					usuarioAsignadoDao.deleteUsuarioAsignado(usuarioAsig);
				}
			}			
		}
		instanciaDeProceso.setEstadoDeProceso(estadoDeProcesoAnulado);
		instanciaDeProceso.setFechaFin(new Date());
		instanciaDeProceso.setIdUsuarioTermina(usuarioSGDP);	
		anulacionDTO.setRespuestaAnulacion(configProps.getProperty("respuestaOK"));
	}
	
	@Override
	public void reabrirExpedientePorIdInstanciaDeTareaIdUsuario(ReabreInstanciaDeSubProcesoDTO reabreInstanciaDeSubProcesoDTO, Usuario usuario) throws SgdpException {
		
		Calendar calModInstProc = Calendar.getInstance();
		Calendar calModInstTarea = Calendar.getInstance();
		calModInstTarea.setTime(new Date());
		calModInstProc.setTime(new Date());
		
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaService.getInstanciaDeTareaPorIdInstanciaDeTarea(reabreInstanciaDeSubProcesoDTO.getIdInstanciaDeTarea());
		InstanciaDeProceso instanciaDeProceso = instanciaDeTarea.getInstanciaDeProceso();
		
		instanciaDeTarea.setFechaVencimiento(FechaUtil.getFechaHabil(calModInstTarea, fechaFeriadoDao, instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion()).getTime());
		instanciaDeTarea.setFechaAsignacion(new Date());	
		instanciaDeTarea.setEstadoDeTarea(estadoDeTareaDao.getEstadoDeTareaPorCodigo(EstadoDeTareaType.ASIGNADA.getCodigoEstadoDeTarea()));	
		instanciaDeTarea.setIdUsuarioQueAsigna(usuario.getIdUsuario());
		
		log.debug("Validando instanciaDeTareaSiguiente asignada");
		log.debug(instanciaDeTarea.toString());
		
		ContinuarProcesoDTO continuarProcesoDTO = new ContinuarProcesoDTO();
		continuarProcesoDTO.setReabre(true);
		continuarProcesoDTO.setComentario(reabreInstanciaDeSubProcesoDTO.getComentario());
		continuarProcesoDTO.setIdExpedienteContinuarProceso(instanciaDeProceso.getIdExpediente());
		
		avanza(continuarProcesoDTO, 
				usuario, 
				reabreInstanciaDeSubProcesoDTO.getIdInstanciaDeTarea(), 
				reabreInstanciaDeSubProcesoDTO.getIdUsuarioSeleccionado(),
				null,
				instanciaDeTarea
				);
		
		//Hacer esto solo si esta cerrado
		if (instanciaDeProceso.getEstadoDeProceso().getIdEstadoDeProceso() == EstadoDeProcesoType.FINALIZADO.getCodigoEstadoDeProceso() 
				|| instanciaDeProceso.getEstadoDeProceso().getIdEstadoDeProceso() == EstadoDeProcesoType.ANULADO.getCodigoEstadoDeProceso()) {
			instanciaDeProceso.setEstadoDeProceso(estadoDeProcesoDao.getEstadoDeProcesoPorCodigo(EstadoDeProcesoType.ASIGNADO.getCodigoEstadoDeProceso()));	
			instanciaDeProceso.setFechaVencimiento(FechaUtil.getFechaHabil(calModInstProc, fechaFeriadoDao, instanciaDeProceso.getProceso().getDiasHabilesMaxDuracion()).getTime());		
		}	
	
	}
	
	@Override
	public void cierraInstanciaDeTarea(CierraInstanciaDeTareaDTO cierraInstanciaDeTareaDTO, Usuario usuario) throws SgdpException {
		FinalizaProcesoDTO finalizaProcesoDTO = new FinalizaProcesoDTO();
		finalizaProcesoDTO.setCierra(true);
		finalizaProcesoDTO.setIdInstanciaDeTarea(cierraInstanciaDeTareaDTO.getIdInstanciaDeTarea());
		finalizaProcesoDTO.setComentario(cierraInstanciaDeTareaDTO.getComentario());
		finaliza(usuario, finalizaProcesoDTO);
	}
	
	private void invocaWS(IntegracionSatelitesDTO integracionSatelitesDTO, long idInstanciaDeTarea, Usuario usuario) throws SgdpException {		
		List<ArchivosInstDeTareaDTO> todosLosDocSubidos = instanciaDeTareaService.getTodosLosDocSubidosPorIdInstTarea(idInstanciaDeTarea);
		try {
			obtenerArchivosExpedienteService.filtraPorNumeroDeDocumento(usuario, todosLosDocSubidos);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException("Error al buscar el archivo para enviar al sistema satelite", Level.ERROR, false);
		}
		if (todosLosDocSubidos==null || todosLosDocSubidos.isEmpty()) {
			throw new SgdpException("Error al buscar el archivo para enviar al sistema satelite", Level.ERROR, false);
		} else {
			log.debug("todosLosDocSubidos!=null");
			ArchivosInstDeTareaDTO archivosInstDeTareaDTO = todosLosDocSubidos.get(0);
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, archivosInstDeTareaDTO.getIdArchivoCms());
			log.info(detalleDeArchivoDTO.toString());
			byte[] contenidoArchivo = gestorDeDocumentosService.getContenidoArchivo(archivosInstDeTareaDTO.getIdArchivoCms(), usuario);
			String contenidoArchivoBase64;
			HistoricoFirmaDTO historicoFirmaDTO = historicoFirmaService.getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo(archivosInstDeTareaDTO.getIdArchivoCms());
			try {
			    contenidoArchivoBase64 = FileUtil.encodeByteArrayToBase64(contenidoArchivo, "UTF-8");
			    integracionSatelitesDTO.setContenidoArchivo(contenidoArchivoBase64);
			    integracionSatelitesDTO.setContentType(detalleDeArchivoDTO.getMimeType());
			    integracionSatelitesDTO.setIdArchivo(archivosInstDeTareaDTO.getIdArchivoCms());
			    integracionSatelitesDTO.setNombreArchivo(archivosInstDeTareaDTO.getNombreArchivo());
			    integracionSatelitesDTO.setNumeroDocumento(detalleDeArchivoDTO.getNumeroDocumento());
			    integracionSatelitesDTO.setFechaFirmaDocumentoOficial(historicoFirmaDTO.getFechaFirma());
			} catch (IOException e) {
			    throw new SgdpException("Error al leer el archivo para enviar al sistema satelite", Level.ERROR, false);
			}
			IntegracionClient integracionClient = new IntegracionClient();
			log.info("Ejecutando servicio satelite");
			log.info(integracionSatelitesDTO.toString());
			RespuestaIntegracionDTO respuestaIntegracionDTO = integracionClient.ejecuta(integracionSatelitesDTO);
			if (respuestaIntegracionDTO==null) {
			    throw new SgdpException("Sin respueta del sistema satelite", Level.ERROR, false);
			} else if (!respuestaIntegracionDTO.getEstado().equals("OK")) {
			    log.info("respuestaIntegracionDTO.getEstado(): " + respuestaIntegracionDTO.getEstado());
			    log.info("respuestaIntegracionDTO.getDescripcionError(): " + respuestaIntegracionDTO.getDescripcionError());
			    throw new SgdpException(respuestaIntegracionDTO.getDescripcionError(), Level.ERROR, false);
			}
			log.info("respuestaIntegracionDTO.getEstado(): " + respuestaIntegracionDTO.getEstado());
			log.info("respuestaIntegracionDTO.getDescripcionError(): " + respuestaIntegracionDTO.getDescripcionError());
		}
	}
	
}

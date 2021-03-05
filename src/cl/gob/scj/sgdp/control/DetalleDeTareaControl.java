package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.GestorDeTagsService;
import cl.gob.scj.sgdp.service.HistoricoDeInstDeTareaService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.ListaDeDistribucionService;
import cl.gob.scj.sgdp.service.MueveProcesoService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.ParametroDeTareaService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.tipos.NotificacionType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class DetalleDeTareaControl {	
		
	private static final Logger log = Logger.getLogger(DetalleDeTareaControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	InstanciaDeTareaService instanciaDeTareaService;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired
	private GestorDeTagsService gestorDeTagsService;
	
	@Autowired
	private MueveProcesoService mueveProcesoService;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ListaDeDistribucionService listaDeDistribucionService;
	
	@Autowired
	private HistoricoDeInstDeTareaService historicoDeInstDeTareaService;
	
	@Autowired
	private ParametroDeTareaService parametroDeTareaService;
	
	@RequestMapping("/getDetalleDeTarea")
	public String getDetalleDeTarea(Model model, HttpServletRequest request) {
		
		log.debug("getDetalleDeTarea");		
		
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
		
		List<TipoDeDocumentoDTO> tiposDeDocumentosDTO;
		List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso = new ArrayList<InstanciaDeTareaDTO>();
		List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea = new ArrayList<ArchivoInfoDTO>();
		List<DetalleDeArchivoDTO> detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea = new ArrayList<DetalleDeArchivoDTO>();
				
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		
		log.debug("idInstanciaDeTarea: " + request.getParameter("idInstanciaDeTarea"));	
		log.debug("muestraSoloDocumentosDeSalida: " + request.getParameter("muestraSoloDocumentosDeSalida"));	
		log.debug("desdeApp: " + request.getParameter("desdeApp"));
		
		String urlControl ="";
		
		try {
			 urlControl = request.getParameter("urlControl");
		} catch (Exception e1) {
			urlControl = "";   
		}
		
		try {
			
			List<String> listaDeTags = gestorDeTagsService.obtenerListaDeTags(usuario);
				
			instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(Long.parseLong(request.getParameter("idInstanciaDeTarea")), instanciaDeTareaDTO);
			log.debug(instanciaDeTareaDTO.toString());
			
			if (instanciaDeTareaDTO.getIdEstadoTarea()!=2 || !instanciaDeTareaDTO.getIdUsuariosAsignados().contains(usuario.getIdUsuario())) {				
				log.debug("ERROR al cargar detalle de la tarea... La tarea ya no est&aacute; en su bandeja");
				log.debug("instanciaDeTareaDTO.getUsuariosAsignadosString(): " + instanciaDeTareaDTO.getUsuariosAsignadosString());
				return "errorTareaNoEstaEnBE";
			}
			
			instanciasDeTareasDTOContinuanProceso = mueveProcesoService.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(usuario, 
					instanciaDeTareaDTO, instanciasDeTareasDTOContinuanProceso);
			
			List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeTareaDTO.getIdExpediente(), true, instanciaDeTareaDTO.getPuedeVisarDocumentos(), instanciaDeTareaDTO.getPuedeAplicarFEA(), instanciaDeTareaDTO.getIdInstanciaDeTarea());
			
			log.debug("instanciasDeTareasDTOContinuanProceso....");
			for (InstanciaDeTareaDTO instanciaDeTareaDTOContinuaProceso : instanciasDeTareasDTOContinuanProceso) {
				log.debug(instanciaDeTareaDTOContinuaProceso.toString());
			}
			
			/*for (ArchivoInfoDTO archivoInfoDTO: archivosExpedienteDTO) {
				log.debug(archivoInfoDTO.toString());				
			}*/
			
			obtenerArchivosExpedienteService.cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(
					usuario, 
					instanciaDeTareaDTO, 
					archivosInfoDTODeSalidaPorIdInstanciaDeTarea,
					archivosExpedienteDTO);
			
			log.debug("archivosInfoDTODeSalidaPorIdInstanciaDeTarea.size(): " + archivosInfoDTODeSalidaPorIdInstanciaDeTarea.size());
			
			for (ArchivoInfoDTO archivoInfoDTO : archivosInfoDTODeSalidaPorIdInstanciaDeTarea) {
				log.debug(archivoInfoDTO.toString());
			}			
			
			HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO = historicoDeInstDeTareaService.getHisDeInstDeTareaPorIdInstDeTareaDTODeDestIdUsrOrigenMaxFechaMov(instanciaDeTareaDTO.getIdInstanciaDeTarea(),
					instanciaDeTareaDTO.getIdUsuarioQueAsigna());			
			String ultimoComentario = historicoDeInstDeTareaDTO.getComentario();
					
			if (request.getParameter("muestraSoloDocumentosDeSalida").equals("true")) {
				instanciaDeTareaDTO.setMuestraDocumentosObligatoriosChecked(configProps.getProperty("checked"));
				instanciaDeTareaDTO.setMuestraTodosDocumentosChecked("");
			} else {
				instanciaDeTareaDTO.setMuestraDocumentosObligatoriosChecked("");
				instanciaDeTareaDTO.setMuestraTodosDocumentosChecked(configProps.getProperty("checked"));
			}
			
			if (request.getParameter("muestraSoloDocumentosDeSalida")!=null 
					&& request.getParameter("muestraSoloDocumentosDeSalida").equals("true")
					&& instanciaDeTareaDTO.getOrden()>Long.parseLong(configProps.getProperty("numeroPrimeraTarea"))) {
				filtraArchivosObligatorios(archivosExpedienteDTO, instanciaDeTareaDTO);				
			} 
			
			log.debug("archivosExpedienteDTO: ");
			for (ArchivoInfoDTO archivoInfoDTO: archivosExpedienteDTO) {
				log.debug(archivoInfoDTO.toString());				
			}			
		
			List<AutorDTO> autoresDTO = bandejaDeEntradaService.getTodosLosAutores();
			//tiposDeDocumentosDTO = tipoDeDocumentoService.getTodosLosTiposDeDocumentos();
			tiposDeDocumentosDTO = tipoDeDocumentoService.getTiposDeDocumentosPorIdExpediente(instanciaDeTareaDTO.getIdExpediente());
			tiposDeDocumentosDTO.add(tipoDeDocumentoService.getTipoDeDocumentoDTOPorIdTipoDeDocumento(Long.parseLong(configProps.getProperty("idTipoDeDocumentoAntecedente"))));
			
			instanciaDeProcesoService.cargaInstanciaDeProcesoDTOPorIdExpediente(instanciaDeTareaDTO.getIdExpediente(), instanciaDeProcesoDTO);				
			log.debug(instanciaDeProcesoDTO.toString());
			
			log.debug(instanciaDeTareaDTO.toString());			
			
			obtenerArchivosExpedienteService.cargaArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(usuario, 
					instanciaDeTareaDTO, 
					detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea);
			
			StringBuilder idArchivosInstTareasAnteriores = new StringBuilder();
			
			Iterator<DetalleDeArchivoDTO> it = detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea.iterator();
			while (it.hasNext()) {
				DetalleDeArchivoDTO da = it.next();
				idArchivosInstTareasAnteriores.append(da.getIdArchivo());
				if (it.hasNext()) {
					idArchivosInstTareasAnteriores.append(";");
				}
			}
			
			//Map<String, List<ParametroDeTareaDTO>> mapParametrosDeTareaDTOTitulo = bandejaDeEntradaService.getMapParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea());
			
			/*log.debug("Iterando mapParametrosDeTareaTitulo....");
			for (Map.Entry<String, List<ParametroDeTareaDTO>> entry : mapParametrosDeTareaDTOTitulo.entrySet()) {
			    log.debug(entry.getKey() + ":  ");
			    List<ParametroDeTareaDTO> listaDeMap = entry.getValue();			    
			    for (ParametroDeTareaDTO parametroDeTareaDTO : listaDeMap) {
			    	log.debug("   " + parametroDeTareaDTO.toString());
			    }
			}*/			
			
			// ####################################################################################################################
			// Seguimiento Instancia de proceso

			long tieneSeguimiento = instanciaDeProcesoService.buscaSiTieneSeguimiento(usuario.getIdUsuario(),instanciaDeProcesoDTO.getIdInstanciaDeProceso());
			
			//  log.debug("IdUsuario : " + usuario.getIdUsuario());
			//log.debug("tieneSeguimiento : " + tieneSeguimiento);
		   //log.debug("instanciaDeProceso : " + instanciaDeProcesoDTO.getIdInstanciaDeProceso());
			model.addAttribute("tieneSeguimiento", tieneSeguimiento);
			//model.addAttribute("instanciaDeProceso", instanciaDeProcesoDTO.getIdInstanciaDeProceso());

			// ###############################################################################################
			
			// Esta URL es para solo el caso de los form
			model.addAttribute("urlControl", urlControl);
			//
			
			/*long diasDiferenciaEntreHoyEInicioInstanciaProc;
			float porcentajeDeAvanceInstanProc;
			
			long diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc;
			float porcentajeDeAvanceDeInstTarea;
			float porcentajeDeAvanceDeInstTareaWidth;
			
			String progressBarStatus;
			
			diasDiferenciaEntreHoyEInicioInstanciaProc = FechaUtil.diasEntreFechas(new Date(), instanciaDeProcesoDTO.getFechaInicio());
			
			log.debug("Calculo Porcentaje Grafico Detalle De Tarea --- diasDiferenciaEntreHoyEInicioInstanciaProc: " + diasDiferenciaEntreHoyEInicioInstanciaProc);
			
			if (diasDiferenciaEntreHoyEInicioInstanciaProc > instanciaDeProcesoDTO.getDiasHabilesMaxDuracion() || diasDiferenciaEntreHoyEInicioInstanciaProc <= 0 ) {
				porcentajeDeAvanceInstanProc = Constantes.PORCENTAJE_AVANCE_CIEN_INST_PROCESO;
				progressBarStatus = configProps.getProperty("progressBarDanger");
			} else {
				porcentajeDeAvanceInstanProc = (diasDiferenciaEntreHoyEInicioInstanciaProc * 100 ) / instanciaDeProcesoDTO.getDiasHabilesMaxDuracion();
				progressBarStatus = configProps.getProperty("progressBarSuccess");
			}
						
			log.debug("Calculo Porcentaje Grafico Detalle De Tarea --- porcentajeDeAvanceDelSubproceso: " + porcentajeDeAvanceInstanProc);
			
			if (instanciaDeTareaDTO.getFechaVencimientoUsuario()!=null) {
				diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc = FechaUtil.diasEntreFechas(instanciaDeTareaDTO.getFechaVencimientoUsuario(), instanciaDeProcesoDTO.getFechaInicio());				
			} else {
				diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc = FechaUtil.diasEntreFechas(instanciaDeTareaDTO.getFechaVencimientoInstanciaDeTarea(), instanciaDeProcesoDTO.getFechaInicio());				
			}
			
			log.debug("Calculo Porcentaje Grafico Detalle De Tarea --- diasDiferenciaEntreAsigancionInstanciaTarEInicioInstanciaProc: " + diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc);			
			
			if (diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc > instanciaDeProcesoDTO.getDiasHabilesMaxDuracion() || diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc <= 0) {
				porcentajeDeAvanceDeInstTarea = Constantes.PORCENTAJE_AVANCE_CIEN_INST_PROCESO;
				porcentajeDeAvanceDeInstTareaWidth = Constantes.PORCENTAJE_AVANCE_WIDTH_INST_TAREA;
			} else {
				porcentajeDeAvanceDeInstTarea = (diasDiferenciaEntreAsignacionInstanciaTarEInicioInstanciaProc * 100 ) / instanciaDeProcesoDTO.getDiasHabilesMaxDuracion();
				porcentajeDeAvanceDeInstTareaWidth = porcentajeDeAvanceDeInstTarea;
			}
			
			if (porcentajeDeAvanceDeInstTareaWidth > Constantes.PORCENTAJE_AVANCE_WIDTH_INST_TAREA) {
				porcentajeDeAvanceDeInstTareaWidth = Constantes.PORCENTAJE_AVANCE_WIDTH_INST_TAREA;
			}
			
			log.debug("Calculo Porcentaje Grafico Detalle De Tarea --- porcentajeDeAvanceDeInstTarea: " + porcentajeDeAvanceDeInstTarea);*/
			
			/*List<InstanciaDeTareaDTO> instanciasDeTareasDTOAnteriores = null;
			
			if (instanciaDeTareaDTO.getPuedeDevolver() == true) {
				instanciasDeTareasDTOAnteriores = instanciaDeTareaService.getInstanciaDeTareaDTOAnteriores(instanciaDeTareaDTO);
			}*/			
			
			List<ParametroDeTareaDTO> parametrosDeTareaDTO = parametroDeTareaService.getParametrosDeTareaDTOPorIdTarea(instanciaDeTareaDTO.getIdTarea());
			
			boolean tareaTieneParametros = false;
			
			if (parametrosDeTareaDTO != null && !parametrosDeTareaDTO.isEmpty()) {
				tareaTieneParametros = true;
			}
			
			int numeroDeIteracionDeLaInstanciaDeTarea = historicoDeInstDeTareaService.getCantidadDeEjecutacionesInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea());
			
			model.addAttribute("filtraFirmas", true);
			model.addAttribute("permisos", usuario.getPermisos());
			//model.addAttribute("historialProcesoDTO", historialProcesoDTO);  
			model.addAttribute("archivosExpedienteDTO", archivosExpedienteDTO);
			model.addAttribute("autoresDTO", autoresDTO);
			model.addAttribute("tiposDeDocumentosDTO", tiposDeDocumentosDTO);			
			model.addAttribute("instanciaDeTareaDTO", instanciaDeTareaDTO);	
			model.addAttribute("listaDeTags", listaDeTags);			
			model.addAttribute("ultimoComentario", ultimoComentario); 
			//model.addAttribute("idUsuarioUltimoComentario", !historialProcesoDTO.isEmpty() ?  historialProcesoDTO.get(0).getIdDeUsuario() : usuario.getIdUsuario());			
			model.addAttribute("instanciaDeProcesoDTO", instanciaDeProcesoDTO);
			model.addAttribute("instanciasDeTareasDTOContinuanProceso" , instanciasDeTareasDTOContinuanProceso);
			model.addAttribute("archivosInfoDTODeSalidaPorIdInstanciaDeTarea", archivosInfoDTODeSalidaPorIdInstanciaDeTarea);
			model.addAttribute("detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea", detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea);
			model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
			//model.addAttribute("mapParametrosDeTareaDTOTitulo", mapParametrosDeTareaDTOTitulo);
			//model.addAttribute("instanciasDeTareasDTOAnteriores", instanciasDeTareasDTOAnteriores);
			model.addAttribute("idArchivosInstTareasAnteriores", idArchivosInstTareasAnteriores);
			model.addAttribute("tareaTieneParametros", tareaTieneParametros);
			model.addAttribute("numeroDeIteracionDeLaInstanciaDeTarea", numeroDeIteracionDeLaInstanciaDeTarea);
			
			/*model.addAttribute("porcentajeDeAvanceInstanProc", porcentajeDeAvanceInstanProc);
			model.addAttribute("porcentajeDeAvanceDeInstTarea", porcentajeDeAvanceDeInstTarea);
			model.addAttribute("porcentajeDeAvanceDeInstTareaWidth", porcentajeDeAvanceDeInstTareaWidth);
			model.addAttribute("diasDiferenciaEntreHoyEInicioInstanciaProc", diasDiferenciaEntreHoyEInicioInstanciaProc);
			model.addAttribute("progressBarStatus", progressBarStatus);*/			
						
			if (request.getParameter("desdeApp")!=null && !request.getParameter("desdeApp").isEmpty() && request.getParameter("desdeApp").equals("true")) {
				return configProps.getProperty("vistaEjecucionDeTareaDesdeApp");
			} else {
				return configProps.getProperty("vistaEjecucionDeTarea");
			}
			
			
		} catch (SgdpException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			log.error("ERROR al cargar detalle de la tarea: ", sgdpe);	
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssSucess"));			
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
			return sgdpe.getMessage();
		} catch (Exception e) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			log.error("ERROR al cargar detalle de la tarea: ", e);
			mensajeVistaDTO.getMensajes().put("ERROR al obtener archivos de expediente", configProps.getProperty("cssSucess"));
			return configProps.getProperty("respuestaError");
		}
		
	}
	
	private void filtraArchivosObligatorios(List<ArchivoInfoDTO> archivosExpedienteDTO, InstanciaDeTareaDTO instanciaDeTareaDTO) {	
		
		List<ArchivoInfoDTO> archivosExpedienteDTORF = new ArrayList<ArchivoInfoDTO>(archivosExpedienteDTO);
		
		Iterator<ArchivoInfoDTO> it = archivosExpedienteDTORF.iterator();	
		
		while (it.hasNext()) {
			ArchivoInfoDTO archivoInfoDTO = it.next();
			if (instanciaDeTareaDTO.getTiposDeDocumentosDeSalida()!=null && instanciaDeTareaDTO.getTiposDeDocumentosDeSalida().size()>0) {
				TipoDeDocumentoDTO tipoDeDocumentoDTO = instanciaDeTareaDTO.getTiposDeDocumentosDeSalida().get(archivoInfoDTO.getTipoDeDocumento());
				if (tipoDeDocumentoDTO == null) {
					it.remove();
				}
			}
		}
		
		/*for(ArchivoInfoDTO archivoInfoDTO: archivosExpedienteDTORF)  {
			if (instanciaDeTareaDTO.getTiposDeDocumentosDeSalida()!=null && instanciaDeTareaDTO.getTiposDeDocumentosDeSalida().size()>0) {
				TipoDeDocumentoDTO tipoDeDocumentoDTO = instanciaDeTareaDTO.getTiposDeDocumentosDeSalida().get(archivoInfoDTO.getTipoDeDocumento());
				if (tipoDeDocumentoDTO == null) {
					archivosExpedienteDTO.remove(archivoInfoDTO);
				}
			}			
		}*/
		
	}
	
	@RequestMapping("/getDocumentosDeSalida")
	public String getDocumentosDeSalida(Model model, HttpServletRequest request) {
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea = new ArrayList<ArchivoInfoDTO>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");				
		log.debug("idInstanciaDeTarea: " + request.getParameter("idInstanciaDeTarea"));
			instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(Long.parseLong(request.getParameter("idInstanciaDeTarea")), instanciaDeTareaDTO);
		log.debug(instanciaDeTareaDTO.toString());		
		try {
			List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeTareaDTO.getIdExpediente(), true, instanciaDeTareaDTO.getPuedeVisarDocumentos(), instanciaDeTareaDTO.getPuedeAplicarFEA(),
					Long.parseLong(request.getParameter("idInstanciaDeTarea"))
					);						
			obtenerArchivosExpedienteService.cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(usuario, 
					instanciaDeTareaDTO, 
					archivosInfoDTODeSalidaPorIdInstanciaDeTarea,
					archivosExpedienteDTO);
		} catch (SgdpException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			log.error("ERROR al obtener archivos de expediente: ", sgdpe);	
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssSucess"));			
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
			return sgdpe.getMessage();
		} catch (Exception e) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			log.error("ERROR al obtener archivos de expediente: ", e);
			mensajeVistaDTO.getMensajes().put("ERROR al obtener archivos de expediente", configProps.getProperty("cssSucess"));
			return configProps.getProperty("respuestaError");
		}
		log.debug("archivosInfoDTODeSalidaPorIdInstanciaDeTarea.size(): " + archivosInfoDTODeSalidaPorIdInstanciaDeTarea.size());
		model.addAttribute("instanciaDeTareaDTO", instanciaDeTareaDTO);
		model.addAttribute("archivosInfoDTODeSalidaPorIdInstanciaDeTarea", archivosInfoDTODeSalidaPorIdInstanciaDeTarea);
		model.addAttribute("permisos", usuario.getPermisos());
		return configProps.getProperty("vistaDocumentosDeSalida");
	}	
	
	@RequestMapping(value = "/guardarSeguimiento", method = RequestMethod.POST)
	public @ResponseBody MensajeJson guardarSeguimiento(@RequestBody InstanciaDeProcesoDTO instanciaDeProcesoDTO, /*InstanciaDeProceso instanciaDeProceso,*/
												HttpServletRequest request) {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");				
		
		MensajeJson mensajeJson = new MensajeJson();	
		
		instanciaDeProcesoService.guardarSeguimiento(instanciaDeProcesoDTO, mensajeJson, usuario.getIdUsuario(), usuario, NotificacionType.MANUAL.getNombreTipoNotificacion());
		
		return mensajeJson;			
	}
	
	@RequestMapping(value = "/dejarDeSeguimiento", method = RequestMethod.POST)
	public @ResponseBody MensajeJson dejarDeSeguimiento(@RequestBody InstanciaDeProcesoDTO instanciaDeProcesoDTO, /*InstanciaDeProceso instanciaDeProceso,*/
												HttpServletRequest request) {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");	
		
		MensajeJson mensajeJson = new MensajeJson();	
		
		instanciaDeProcesoService.dejarDeSeguimiento(instanciaDeProcesoDTO, mensajeJson, usuario.getIdUsuario(), usuario);
		
		return mensajeJson;			
	}	
	
	@RequestMapping("/documentoRequeridoFormulario")
	public String documentosRequeridosFormulario(Model model, HttpServletRequest request) {
	
		InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();

		
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea = new ArrayList<ArchivoInfoDTO>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
		log.debug("idInstanciaDeTarea: " + request.getParameter("idInstanciaDeTarea"));
			instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(Long.parseLong(request.getParameter("idInstanciaDeTarea")), instanciaDeTareaDTO);
		log.debug(instanciaDeTareaDTO.toString());
		
		try {
			List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeTareaDTO.getIdExpediente(), true, instanciaDeTareaDTO.getPuedeVisarDocumentos(), instanciaDeTareaDTO.getPuedeAplicarFEA(),
					Long.parseLong(request.getParameter("idInstanciaDeTarea"))
					);	
			
			instanciaDeProcesoService.cargaInstanciaDeProcesoDTOPorIdExpediente(instanciaDeTareaDTO.getIdExpediente(), instanciaDeProcesoDTO);				
			log.debug(instanciaDeProcesoDTO.toString());
			
			obtenerArchivosExpedienteService.cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(usuario, 
					instanciaDeTareaDTO, 
					archivosInfoDTODeSalidaPorIdInstanciaDeTarea,
					archivosExpedienteDTO);
		} catch (SgdpException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			log.error("ERROR al obtener archivos de expediente: ", sgdpe);	
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssSucess"));			
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
			return sgdpe.getMessage();
		} catch (Exception e) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			log.error("ERROR al obtener archivos de expediente: ", e);
			mensajeVistaDTO.getMensajes().put("ERROR al obtener archivos de expediente", configProps.getProperty("cssSucess"));
			return configProps.getProperty("respuestaError");
		}
		log.debug("archivosInfoDTODeSalidaPorIdInstanciaDeTarea.size(): " + archivosInfoDTODeSalidaPorIdInstanciaDeTarea.size());
		model.addAttribute("instanciaDeTareaDTO", instanciaDeTareaDTO);
		model.addAttribute("archivosInfoDTODeSalidaPorIdInstanciaDeTarea", archivosInfoDTODeSalidaPorIdInstanciaDeTarea);
		model.addAttribute("permisos", usuario.getPermisos());
		model.addAttribute("instanciaDeProcesoDTO", instanciaDeProcesoDTO);
		
		return "div/documentoRequeridoFormulario";
	}
			
	@RequestMapping("/getDetalleDeExpedienteEnDistribucion/{idExpediente}/{idInstanciaDeTarea}/{nombreExpediente}")
	public String getDetalleDeExpedienteEnDistribucion(
								@PathVariable("idExpediente") String idExpediente,
								@PathVariable("idInstanciaDeTarea") Integer idInstanciaDeTarea,
								@PathVariable("nombreExpediente") String nombreExpediente,
								Model model, 
								HttpServletRequest request) {
		
		log.debug("Inicio getDetalleDeExpedienteEnDistribucion");
		log.debug("idExpediente : " + idExpediente);
		log.debug("idInstanciaDeTarea : " + idInstanciaDeTarea);		
		
		List<ListaDeDistribucionDTO> listaDistribucion;
		List<ArchivosInstDeTareaDTO> todosLosDocSubidos;
		
		InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		try {
			todosLosDocSubidos = instanciaDeTareaService.getTodosLosDocSubidosPorIdInstTarea(idInstanciaDeTarea);			
			obtenerArchivosExpedienteService.filtraPorNumeroDeDocumento(usuario, todosLosDocSubidos);
			Map<String, ArchivosInstDeTareaDTO> mapDeArchivosInstDeTareaDTOPorIdCms = SGDPUtil.generaMapDeArchivosInstDeTareaDTOPorIdCms(todosLosDocSubidos);
			todosLosDocSubidos = new ArrayList<ArchivosInstDeTareaDTO>(mapDeArchivosInstDeTareaDTOPorIdCms.values());
			listaDistribucion = listaDeDistribucionService.getListaDeDistribucion();
			instanciaDeProcesoService.cargaInstanciaDeProcesoDTOPorIdExpediente(idExpediente, instanciaDeProcesoDTO);	
			log.debug(listaDistribucion.toString());
			model.addAttribute("nombreExpediente", nombreExpediente);
			model.addAttribute("todosLosDocSubidos", todosLosDocSubidos);
			model.addAttribute("idInstanciaDeTarea", idInstanciaDeTarea);
			model.addAttribute("listaDistribucion", listaDistribucion);			
			model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
			model.addAttribute("instanciaDeProcesoDTO", instanciaDeProcesoDTO);	
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
		}	
		
		return "div/detalleDeExpedienteEnDistribucion";
		
	}

}

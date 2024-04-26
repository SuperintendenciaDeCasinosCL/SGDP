package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.LogDocumentoDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.RespuestaActualizaMetadataDeDocumento;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.ConfidencialidadDocumentoService;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.GestorMetadataService;
import cl.gob.scj.sgdp.service.HistoricoFirmaService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.LogDocumentoService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.ParametroPorContextoService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.tipos.AccionType;
import cl.gob.scj.sgdp.tipos.ModuloType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class DetalleDeDocumentoControl {

	private static final Logger log = Logger.getLogger(DetalleDeDocumentoControl.class);
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private GestorMetadataService gestorMetadataService;
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	private ParametroPorContextoService parametroPorContextoService;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ArchivosInstDeTareaService archivosInstDeTareaService;
	
	@Autowired
	private HistoricoFirmaService historicoFirmaService;

	@Autowired
	private ConfidencialidadDocumentoService confidencialidadDocumentoService;
	
	@Autowired
	private LogDocumentoService logDocumentoService;
	
	@Resource(name = "configProps")
	private Properties configProps;

	@RequestMapping(value="/getDetalleDeDocumento/{idArchivo}/{esVisable}/{aplicaFEA}/{aplicaFirmaApplet}/{idExpediente}/{idInstanciaDeTarea}", method=RequestMethod.GET)
	public String getDetalleDeDocumento(
			@PathVariable("idArchivo") String idArchivo,
			@PathVariable("esVisable") boolean esVisable,
			@PathVariable("aplicaFEA") boolean aplicaFEA,
			@PathVariable("aplicaFirmaApplet") boolean aplicaFirmaApplet,
			@PathVariable("idExpediente") String idExpediente,
			@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea,
			Model model, 
			HttpServletRequest request) {			
		log.debug("inicio getDetalleDeDocumento");	
		log.debug("idArchivo: " + idArchivo);
		DetalleDeArchivoDTO detalleDeArchivoDTOError = new DetalleDeArchivoDTO();
		try {
			
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			detalleDeArchivoDTO.setIdArchivo(idArchivo);
			List<ArchivoInfoDTO> listaArchivoInfoDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, idExpediente, false, false, false, idInstanciaDeTarea);
			List<ArchivoInfoDTO> listaArchivoInfoDTOAdjuntos = obtenerArchivosExpedienteService.getListaArchivoInfoDTOAdjuntos(usuario, detalleDeArchivoDTO.getNombre(), listaArchivoInfoDTO);
			log.debug(detalleDeArchivoDTO.toString());
			detalleDeArchivoDTO.setResultadoObtenerDetalleDeArchivo(configProps.getProperty("obtenerDetalleDeArchivoExito"));
			List<AutorDTO> autoresDTO = bandejaDeEntradaService.getTodosLosAutores();
			List<TipoDeDocumentoDTO> tiposDeDocumentosDTO = tipoDeDocumentoService.getTodosLosTiposDeDocumentos();
			log.debug(detalleDeArchivoDTO.toString());
			detalleDeArchivoDTO.setEsEditable(obtenerArchivosExpedienteService.archivoEsEditable(detalleDeArchivoDTO.getMimeType()));
			detalleDeArchivoDTO.setCodigoMimeType(obtenerArchivosExpedienteService.archivoCodigoMineType(detalleDeArchivoDTO.getMimeType()));
			LogDocumentoDTO logDocumentoDTO = new LogDocumentoDTO();
			logDocumentoDTO.setIpLogDocumento(SGDPUtil.getClientIpAddress(request));
			logDocumentoDTO.setIdDocumentoLogDocumento(idArchivo);
			logDocumentoDTO.setTipoOperacionLogDocumento(AccionType.REVISA_DETALLE_DE_DOCUMENTO.getNombreAccion());
			logDocumentoDTO.setModuloLogDocumento(ModuloType.DETALLE_DE_DOCUMENTO.getNombreModulo());
			logDocumentoService.insertLogDocumento(usuario, logDocumentoDTO);
			model.addAttribute("detalleDeArchivoDTO", detalleDeArchivoDTO);
			model.addAttribute("detalleDeArchivoDTO", detalleDeArchivoDTO);	
			model.addAttribute("autoresDTO", autoresDTO);
			model.addAttribute("tiposDeDocumentosDTO", tiposDeDocumentosDTO);
			model.addAttribute("permisos", usuario.getPermisos());
			model.addAttribute("idArchivo", idArchivo);
			model.addAttribute("esVisable", esVisable);
			model.addAttribute("aplicaFEA", aplicaFEA);
			model.addAttribute("aplicaFirmaApplet", aplicaFirmaApplet);
			model.addAttribute("ticket", usuario.getAlfTicket());
			model.addAttribute("listaArchivoInfoDTO", listaArchivoInfoDTO);
			model.addAttribute("listaArchivoInfoDTOAdjuntos", listaArchivoInfoDTOAdjuntos);
		} catch (SgdpException sgdpe) {
			log.error("ERROR al obtener detalle de archivo: ", sgdpe);
			detalleDeArchivoDTOError.setResultadoObtenerDetalleDeArchivo(sgdpe.getMessage());
			detalleDeArchivoDTOError.setCssStatus(configProps.getProperty("cssError"));
			model.addAttribute("detalleDeArchivoDTOError", detalleDeArchivoDTOError);						
		}  catch (Exception e) {
			log.error("ERROR al obtener detalle de archivo: ", e);
			detalleDeArchivoDTOError.setResultadoObtenerDetalleDeArchivo(configProps.getProperty("respuestaError"));
			detalleDeArchivoDTOError.setCssStatus(configProps.getProperty("cssError"));
			model.addAttribute("detalleDeArchivoDTOError", detalleDeArchivoDTOError);					
		}
		return configProps.getProperty("vistaDetalleDeDocumentoDiv");
	}
	
	@RequestMapping(value="/agregarComentarioDocumento", method=RequestMethod.POST)
	public @ResponseBody DetalleDeArchivoDTO agregarComentarioANodo(@RequestParam("idDocumento") String idDocumento, 
			@RequestParam("comentario") String comentario, 
			HttpServletRequest request) {
		log.debug("inicio agregarComentarioANodo");
		DetalleDeArchivoDTO detalleDeArchivoDTOError = new DetalleDeArchivoDTO();
		try {
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			gestorMetadataService.agregarComentarioANodo(usuario, idDocumento, comentario);			
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idDocumento);
			detalleDeArchivoDTO.setResultadoObtenerDetalleDeArchivo(configProps.getProperty("obtenerDetalleDeArchivoExito"));			
			log.debug(detalleDeArchivoDTO.toString());
			log.debug("fin agregarComentarioANodo");
			return detalleDeArchivoDTO;
		} catch (SgdpException sgdpe) {
			log.error("ERROR al subir comentario: ", sgdpe);
			detalleDeArchivoDTOError.setResultadoObtenerDetalleDeArchivo(sgdpe.getMessage());
			detalleDeArchivoDTOError.setCssStatus(configProps.getProperty("cssError"));
			return detalleDeArchivoDTOError;
		} catch (Exception e) {
			log.error("ERROR al subir comentario: ", e);
			detalleDeArchivoDTOError.setResultadoObtenerDetalleDeArchivo(configProps.getProperty("respuestaError"));
			detalleDeArchivoDTOError.setCssStatus(configProps.getProperty("cssError"));
			return detalleDeArchivoDTOError;
		}	
	}
	
	@RequestMapping(value="/getTiposDeFirmaAvanzada", method=RequestMethod.GET)
	public @ResponseBody List<ParametroPorContextoDTO> getTiposDeFirmaAvanzada() {
		return parametroPorContextoService.getParametrosPorContextoPorNombreParam(Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_PROPOSITO_FEA);
	}
	
	/*@RequestMapping(value="/actualizaMetadataDeDocumento", method=RequestMethod.POST)
	public @ResponseBody String actualizaMetadataDeDocumento(@ModelAttribute("detalleDeArchivoDTO") DetalleDeArchivoDTORest detalleDeArchivoDTO, HttpServletRequest request) {
		log.debug(detalleDeArchivoDTO.toString());
		try {
			return gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO);
		} catch (SgdpException e) {
			return e.getMessage();
		}			
	}*/	
	
	@RequestMapping(value="/actualizaMetadataDeDocumento", method=RequestMethod.POST)
	public @ResponseBody RespuestaActualizaMetadataDeDocumento actualizaMetadataDeDocumento(@ModelAttribute("detalleDeArchivoDTO") DetalleDeArchivoDTO detalleDeArchivoDTO, HttpServletRequest request) {
		log.debug(detalleDeArchivoDTO.toString());
		//RespuestaActualizaMetadataDeDocumento respuestaActualizaMetadataDeDocumento = new RespuestaActualizaMetadataDeDocumento();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		RespuestaActualizaMetadataDeDocumento respuestaActualizaMetadataDeDocumento = new RespuestaActualizaMetadataDeDocumento();
		try {
			respuestaActualizaMetadataDeDocumento.setRespuesta(gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO));
			DetalleDeArchivoDTO d = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, detalleDeArchivoDTO.getIdArchivo());			
			log.debug(d.toString());
			cargaCondicionesFirma(respuestaActualizaMetadataDeDocumento, d, detalleDeArchivoDTO.getIdInstanciaDeTarea());
			return respuestaActualizaMetadataDeDocumento;			
		} catch (SgdpException e) {
			respuestaActualizaMetadataDeDocumento.setRespuesta(e.getMessage());
			return respuestaActualizaMetadataDeDocumento;
		} catch (Exception e) {
			respuestaActualizaMetadataDeDocumento.setRespuesta("Error al actualizar datos del documento");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return respuestaActualizaMetadataDeDocumento;
		} 				
	}	
	
	@RequestMapping(value = "/getDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento/{idExpediente}/{idTipoDeDocumento}/{puedeVisarDocumentos}/{puedeAplicarFEA}/{idInstanciaDeTarea}", method = RequestMethod.GET) 
	public @ResponseBody List<DetalleDeArchivoDTO> getDetalleDeArchivoDTO(
			@PathVariable("idExpediente") String idExpediente, 
			@PathVariable("idTipoDeDocumento") long idTipoDeDocumento, 
			@PathVariable("puedeVisarDocumentos") boolean puedeVisarDocumentos,
			@PathVariable("puedeAplicarFEA") boolean puedeAplicarFEA,
			@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea,
			HttpServletRequest request) {
		log.debug("Inicio getDetalleDeArchivoDTO");
		List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento = new ArrayList<DetalleDeArchivoDTO>();	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		try {
			obtenerArchivosExpedienteService.cargaDetalleDeArchivosDTO(
					idExpediente, usuario, idTipoDeDocumento, detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
					puedeVisarDocumentos, puedeAplicarFEA, idInstanciaDeTarea);
			confidencialidadDocumentoService.eliminaConfidenciales(detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento, usuario);
			log.debug("retornando");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);							
		}
		return detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento;
	}
	
	public void cargaCondicionesFirma(RespuestaActualizaMetadataDeDocumento respuestaActualizaMetadataDeDocumento, DetalleDeArchivoDTO d, long idInstanciaDeTarea) {
		
		log.debug("Inicio cargaCondicionesFirma");
		
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
	
		instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea, new InstanciaDeTareaDTO());
		
		log.debug(instanciaDeTareaDTO.toString());
		
		//TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumento(d.getTipoDeDocumento());
		TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(d.getTipoDeDocumento(), idInstanciaDeTarea);
		
		ParametroDTO parametroDTOEsMimeTypeVisable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, d.getMimeType());
			
		ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, d.getMimeType());
		
		ParametroDTO parametroDTOEsMimeTypeAplicaFirmaApplet = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FIRMA_APPLET, d.getMimeType());
					
		if (parametroDTOEsMimeTypeVisable!=null && tipoDeDocumentoDTO.getAplicaVisacion()==true && instanciaDeTareaDTO.getPuedeVisarDocumentos()) {
			respuestaActualizaMetadataDeDocumento.setEsVisable(true);
		}
		
		if (parametroDTOEsMimeTypeAplicaFEA!=null && tipoDeDocumentoDTO.getAplicaFEA()==true && instanciaDeTareaDTO.getPuedeAplicarFEA()) {
			respuestaActualizaMetadataDeDocumento.setAplicaFEA(true);
		}
		
		if (parametroDTOEsMimeTypeAplicaFirmaApplet!=null && tipoDeDocumentoDTO.getAplicaFEA()==true && instanciaDeTareaDTO.getPuedeAplicarFEA()) {
			respuestaActualizaMetadataDeDocumento.setAplicaFirmaApplet(true);
		}
		log.debug("Fin cargaCondicionesFirma");
	}
	
	@RequestMapping(value="/estaDocumentoFirmado/{idArchivo}/{idInstanciaDeTarea}", method=RequestMethod.GET)
	public @ResponseBody boolean estaDocumentoFirmado(@PathVariable("idArchivo") String idArchivo,	
										@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea,
										Model model, 
										HttpServletRequest request) {
		log.debug("Inicio estaDocumentoFirmado");
		log.debug("idArchivo: " + idArchivo);
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			ArchivosInstDeTareaDTO archivosInstDeTareaDTO = archivosInstDeTareaService.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(idArchivo, idInstanciaDeTarea, usuario.getIdUsuario());
			if (archivosInstDeTareaDTO!=null && (archivosInstDeTareaDTO.getEstaFirmadoConFEACentralizada()==true || archivosInstDeTareaDTO.getEstaFirmadoConFEAWebStart() == true )) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return false;
		}		
		
	}
	
	@RequestMapping(value="/validaSiHayFirmaHoy/{idTipoDeDocumento}/{idInstanciaDeTarea}", method=RequestMethod.GET)
	public @ResponseBody boolean validaSiHayFirmaHoy(@PathVariable("idTipoDeDocumento") Long idTipoDeDocumento, @PathVariable("idInstanciaDeTarea") Long idInstanciaDeTarea, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		return historicoFirmaService.validaSiHayFirmaHoy(idTipoDeDocumento, idInstanciaDeTarea, usuario.getIdUsuario());		
	}
	
	@RequestMapping(value="/getUltimoArchivoInstDeTareaFirmado/{idInstanciaDeTarea}/{idTipoDeDocumento}", method=RequestMethod.GET)
	public @ResponseBody ArchivosInstDeTareaDTO getUltimoArchivoInstDeTareaFirmado(@PathVariable("idInstanciaDeTarea") Long idInstanciaDeTarea, @PathVariable("idTipoDeDocumento") Long idTipoDeDocumento, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		return gestorDeDocumentosService.getUltimoArchivoInstDeTareaFirmado(idInstanciaDeTarea, idTipoDeDocumento, usuario.getIdUsuario());		
	}
	
}

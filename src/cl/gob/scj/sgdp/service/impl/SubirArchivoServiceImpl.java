package cl.gob.scj.sgdp.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AccionesHistInstDeTareaDao;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dao.UsuarioAsignadoDao;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaAsignarNumeroDocumento;
import cl.gob.scj.sgdp.dto.RespuestaConversionArchivoDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.RespuestaSimpleDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.rest.AsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.dto.rest.RespuestaAsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.dto.rest.TipoDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.tipos.AccionesHistInstDeTareaType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SgdpMultipartFile;
import cl.gob.scj.sgdp.util.StringUtilSGDP;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerArchivosExpedienteCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.SubirArchivoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ObtenerArchivosExpedienteResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.SubirArchivoResponse;
import cl.gob.scj.sgdp.ws.rest.client.NumeracionClientRestService;
import cl.gob.scj.sgdp.ws.rest.service.AsignacionNumeroDocService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class SubirArchivoServiceImpl implements SubirArchivoService {

	private static final Logger log = Logger.getLogger(SubirArchivoServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private SubirArchivoCMSService subirArchivoCMSService;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private AsignacionNumeroDocService asignacionNumeroDocService;
	
	@Autowired
	private GestorMetadataCMSService gestorMetadataCMSService;
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	private ObtenerArchivosExpedienteCMSService obtenerArchivosExpedienteCMSService;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private AccionesHistInstDeTareaDao accionesHistInstDeTareaDao;
		
	@Autowired
	private HistoricoArchivosInstDeTareaDao historicoArchivosInstDeTareaDao;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private TareaDao TareaDao;
	
	@Autowired
	private UsuarioAsignadoDao usuarioAsignadoDao;
	
	@Autowired
	private NumeracionClientRestService numeracionClientRestService;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;
	
	@Override
	public SubirArhivoDTO subirArchivo(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws SgdpException, SgdpExceptionValidaTareaEnBE {
		
		log.debug("Inico... subirArchivo..");
		log.debug(subirArhivoDTO.toString());
		
		RespuestaAsignarNumeroDocumento respuestaAsignarNumeroDocumento = new RespuestaAsignarNumeroDocumento();
						
		try {	
			
			boolean estaTipoDeDocumentoEnProceso = estaTipoDeDocumentoEnProceso(subirArhivoDTO); 
			
			if (estaTipoDeDocumentoEnProceso == false) {
				SgdpExceptionValidaTareaEnBE se = new SgdpExceptionValidaTareaEnBE(configProps.getProperty("tipoDeDocumentoNoEstaEnProceso"), Level.WARN, true);
				throw se;
			}
			
			if (subirArhivoDTO.isValidaInstanciaDeTareaEnBE()==true) {
				InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
				//Valida si la tarea la tiene el usuario que esta intentando subir el archivo		
				log.debug("Valida si la tarea la tiene el usuario que esta intentando subir el archivo");
				UsuarioAsignado ua = usuarioAsignadoDao.getUsuarioAsignadoPorInstanciaDeTareaIdUsuario(instanciaDeTarea, usuario.getIdUsuario());
				if (ua == null) {
					SgdpExceptionValidaTareaEnBE se = new SgdpExceptionValidaTareaEnBE(configProps.getProperty("tareaNoEstaEnBE"), Level.WARN, true);
					throw se;
				}
			}
			
			log.debug("Verificando si es documento requerido");
			TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir());
			TipoDeDocumento tipoDeDocumentoRequerido = tipoDeDocumentoDao.getTipoDeDocumentoRequeridoPorIdTipoDeDocumentoIdExpediente(subirArhivoDTO.getIdTipoDeDocumentoSubir(), subirArhivoDTO.getIdExpedienteSubirArchivo());
			if (tipoDeDocumentoRequerido!=null) {
				log.debug("Es Requerido");
				subirArhivoDTO.setEsRequerido(true);
			} else {
				log.debug("No es Requerido");
				subirArhivoDTO.setEsRequerido(false);
			}
			if (!subirArhivoDTO.getEsRequerido()) {
				log.debug("No es documento requerido");
				boolean existeComoDocumentoRequerido = validaSiExisteComoDocumentoRequerido(usuario, subirArhivoDTO);
				log.debug("existeComoDocumentoRequerido: " + existeComoDocumentoRequerido);
				if (existeComoDocumentoRequerido == true) {
					log.debug("existeComoDocumentoRequerido == true ");
					throw new SgdpException(configProps.getProperty("errorAlSubirArchivoEnCMSDocumentoYaExisteComoDocRequerido"), Level.WARN, true);
				}
			}
			if (subirArhivoDTO.getTipoDeDocumento()==null || subirArhivoDTO.getTipoDeDocumento().isEmpty()
					|| subirArhivoDTO.getTipoDeDocumento().equals("null")) {
				String tipo = URLEncoder.encode(tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir()).getNombreDeTipoDeDocumento(), "UTF-8");
				tipo = tipo.replace("+", "%20");
				log.debug("tipo: " + tipo);
				subirArhivoDTO.setTipoDeDocumento(tipo);
				//subirArhivoDTO.setTipoDeDocumento(tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir()).getNombreDeTipoDeDocumento());
			}
			boolean existeConIgualNombreDistintoTipo = validaSiDocumentoYaExisteConIgualNombreDistintoTipo(usuario, subirArhivoDTO);
			if (existeConIgualNombreDistintoTipo == true) {
				log.debug("existeConIgualNombreDistintoTipo == true ");
				throw new SgdpException(configProps.getProperty("errorAlSubirArchivoEnCMSDocumentoYaExisteConOtroTipoDeDocumento"), Level.WARN, true);
			}			
			/*if (subirArhivoDTO.getTieneFirma() == true) {
				log.debug("inicio this.agregarNumeroDocumentoPDF(subirArhivoDTO.getArchivo(), subirArhivoDTO.getIdTipoDeDocumentoSubir())");
				respuestaAsignarNumeroDocumento = this.agregarNumeroDocumentoPDF(subirArhivoDTO.getArchivo(), subirArhivoDTO.getIdTipoDeDocumentoSubir());				
				if (respuestaAsignarNumeroDocumento.getRespuesta()!=null && respuestaAsignarNumeroDocumento.getRespuesta().equals("OK")) {
					subirArhivoDTO.setArchivo(respuestaAsignarNumeroDocumento.getArchivo());					
				} else {
					throw new SgdpException(configProps.getProperty("errorAlSubirArchivoEnCMS"));
				}				
			}*/
			
			// --------------------------------------------------------------------------------------------------------------
			// Se sacan los datos del documento y se  busca si el documento es oficial en el campo B_CONFORMA_EXPEDIENTE
			
			Tarea tarea = TareaDao.getBuscaDocumentoOficialEnTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
			log.debug(tarea.toString());
			log.debug(tipoDeDocumento.toString());
			
			if (tipoDeDocumento.getConformaExpediente() == true 
					&& tarea.getConformaExpediente()!=null 
					&& tarea.getConformaExpediente() == true
					&& !subirArhivoDTO.getConvertirAPDF()){			
				subirArhivoDTO.setEsDocumentoOficial(true);						
			}
			
			log.debug("tipoDeDocumento.getCodTipoDocumento(): " + tipoDeDocumento.getCodTipoDocumento());
			if (tipoDeDocumento.getCodTipoDocumento()!=null && !tipoDeDocumento.getCodTipoDocumento().isEmpty()) {
				TipoDocumentoDTO tipoDocumentoDTO = numeracionClientRestService.getTipoDocumentoPorCodTipoDoc(tipoDeDocumento.getCodTipoDocumento());				
				if (tipoDocumentoDTO.getEstado().equals("0")) {
					subirArhivoDTO.setCategoriaDocumento(tipoDocumentoDTO.getNombreCompletoTipoDoc());
				} else {
					throw new SgdpException("ERROR al intentar obtener el tipo de documento del sistema de numeraci\u00F3n", Level.ERROR, true);
				}				
			}
	
			// --------------------------------------------------------------------------------------------------------------
			
			SubirArchivoResponse subirArchivoResponse = subirArchivoCMSService.subirArchivo(usuario, subirArhivoDTO);
			log.debug("subirArchivoResponse.getIdArchivo(): " + subirArchivoResponse.getIdArchivo());
			if (subirArhivoDTO.getComentario()!=null && !subirArhivoDTO.getComentario().equals("null") && !subirArhivoDTO.getComentario().equals("")) {
				gestorMetadataCMSService.agregarComentarioANodo(usuario, subirArchivoResponse.getIdArchivo(), subirArhivoDTO.getComentario());
			}
			subirArhivoDTO.setIdArchivoEnCMS(subirArchivoResponse.getIdArchivo());	
			subirArhivoDTO.setNombreDeArchivo(subirArhivoDTO.getArchivo().getOriginalFilename());
			marcarArchivoComoSubido(usuario, subirArhivoDTO);	
				
			// Los documentos que  se suben de manera trasversal entran a este metodo
			if (subirArhivoDTO.getMarcaSubirDocTransversal()!=null && subirArhivoDTO.getMarcaSubirDocTransversal().equals("SI")){
				RespuestaMailDTO respuestaMailDTO =  this.guardarHistoricoArchivoYTareas(subirArhivoDTO, usuario);
				if (respuestaMailDTO.getRespuesta().equals("ERROR")){
					throw new SgdpException("ERROR al intentar guardar el historial del documento", Level.ERROR, true);
				}				
			}
			  
			subirArhivoDTO.setResultadoSubirArchivo(configProps.getProperty("archivoSubidoConExito"));					
			return subirArhivoDTO;
		} catch (SgdpException|SgdpExceptionValidaTareaEnBE sgdpe) {	
			 // Se cambia el estado de la asignacion del documento al vacio si hay un error
			if (respuestaAsignarNumeroDocumento.getRespuesta()!=null && !respuestaAsignarNumeroDocumento.getRespuesta().equals("")){				 
				AsignacionesNumerosDocDto asignacionesNumerosDocDto = new AsignacionesNumerosDocDto();
				asignacionesNumerosDocDto.setIdAsignacionNumeroDocEntada(respuestaAsignarNumeroDocumento.getIdAsignacionNumeroDocEntada());
				asignacionesNumerosDocDto.setCodigoMensaje("ERROR");
			    asignacionNumeroDocService.modicarAsignacionDocumento(asignacionesNumerosDocDto);
			}    
			throw sgdpe;
		} catch (Exception e) {
			 // Se cambia el estado de la asignacion del documento al vacio si hay un error
			if (respuestaAsignarNumeroDocumento.getRespuesta()!=null && !respuestaAsignarNumeroDocumento.getRespuesta().equals("")){				 
				AsignacionesNumerosDocDto asignacionesNumerosDocDto = new AsignacionesNumerosDocDto();
				asignacionesNumerosDocDto.setIdAsignacionNumeroDocEntada(respuestaAsignarNumeroDocumento.getIdAsignacionNumeroDocEntada());
				asignacionesNumerosDocDto.setCodigoMensaje("ERROR");
			    asignacionNumeroDocService.modicarAsignacionDocumento(asignacionesNumerosDocDto);
			}		    				
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
			log.debug("Fin... subirArchivo.. error");			
			throw new SgdpException(configProps.getProperty("errorAlSubirArchivoEnCMS"), Level.ERROR, true);
		}		
	}
	
	private boolean validaSiExisteComoDocumentoRequerido (Usuario usuario, SubirArhivoDTO subirArhivoDTO) {
		
		log.debug("Inicio validaSiExisteComoDocumentoRequerido");
		log.debug("subirArhivoDTO.getArchivo().getOriginalFilename(): " + subirArhivoDTO.getArchivo().getOriginalFilename());
		
		List<ArchivosInstDeTarea> archivosRequeridosPorIdArchivo = archivosInstDeTareaDao.getArchivosRequeridosPorNombreArchivoIdExpediente(
				subirArhivoDTO.getArchivo().getOriginalFilename(),
				subirArhivoDTO.getIdExpedienteSubirArchivo());
		
		if (archivosRequeridosPorIdArchivo != null && !archivosRequeridosPorIdArchivo.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private boolean validaSiDocumentoYaExisteConIgualNombreDistintoTipo (Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws Exception {		
		
		String tipoDeDocumentoDeSubirArchivo = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir()).getNombreDeTipoDeDocumento();
		log.debug("Inicio validaSiDocumentoYaExisteConIgualNombreDistintoTipo");
		log.debug("subirArhivoDTO.getArchivo().getOriginalFilename(): " + subirArhivoDTO.getArchivo().getOriginalFilename());
		log.debug("tipoDeDocumentoDeSubirArchivo: " + tipoDeDocumentoDeSubirArchivo);		
		ObtenerArchivosExpedienteResponse obtenerArchivosExpedienteResponse = obtenerArchivosExpedienteCMSService.obtenerArchivosExpediente(usuario, subirArhivoDTO.getIdExpedienteSubirArchivo());
		List<ArchivoInfoDTO> listaDeArchivos = obtenerArchivosExpedienteResponse.getListaDeArchivos();
		//boolean comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = false;
		boolean comparaStringConLevenshteinDistance = false;
		ParametroDTO parametroDTOToleranciaNombreTipoDoc = parametroService.getParametroPorID(Constantes.ID_PARAM_MAX_DIF_TOLERANCIA_NOMBRE_TIPO_DOC);
		for (ArchivoInfoDTO archivoInfoDTO : listaDeArchivos) {
			log.debug("archivoInfoDTO.getNombre(): " + archivoInfoDTO.getNombre());
			log.debug("archivoInfoDTO.getTipoDeDocumento(): " + archivoInfoDTO.getTipoDeDocumento());
			log.debug("subirArhivoDTO.getArchivo().getOriginalFilename(): " + subirArhivoDTO.getArchivo().getOriginalFilename());			
			log.debug("tipoDeDocumentoDeSubirArchivo: " + tipoDeDocumentoDeSubirArchivo);			
			byte[] isoBytes = subirArhivoDTO.getArchivo().getOriginalFilename().getBytes("ISO-8859-1");
			String originalFileNameEncondeado = new String(isoBytes, "UTF-8");
			String originalFileName = URLDecoder.decode(originalFileNameEncondeado, "UTF-8");
			log.debug("originalFileName: " + originalFileName);			
			if (originalFileName.equals(archivoInfoDTO.getNombre())) {
				log.debug("Archivos con el mismo nombre");
			} else {
				log.debug("Archivos con nombre distinto");
			}				
			//comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = StringUtilSGDP.comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase(archivoInfoDTO.getTipoDeDocumento(), tipoDeDocumentoDeSubirArchivo);
			comparaStringConLevenshteinDistance = StringUtilSGDP.comparaStringConLevenshteinDistance(archivoInfoDTO.getTipoDeDocumento(), tipoDeDocumentoDeSubirArchivo, parametroDTOToleranciaNombreTipoDoc.getValorParametroNumerico().intValue());
			log.debug("comparaStringConLevenshteinDistance: " + comparaStringConLevenshteinDistance);
			if (archivoInfoDTO.getNombre().equals(originalFileName)
					//&& !archivoInfoDTO.getTipoDeDocumento().equals(tipoDeDocumentoDeSubirArchivo )) {
					&& comparaStringConLevenshteinDistance != true ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void marcarArchivoComoSubido(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws SgdpException, SgdpExceptionValidaTareaEnBE {
		ArchivosInstDeTarea archivosInstDeTarea;
		boolean nuevoRegistroArchivosInstDeTarea = false;
		boolean dejarContadorDeVisacionEnCero = false;
		RespuestaConversionArchivoDTO respuestaConversionArchivoDTO;
		
		log.info("Inicio marcarArchivoComoSubido");
		log.info(subirArhivoDTO.toString());
		
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
		
		//boolean puedeAplicarFEA = instanciaDeTarea.getTarea().getPuedeAplicarFEA();
		//boolean puedeTenerNumAuto = instanciaDeTarea.getTarea().getNumAuto()!=null ? instanciaDeTarea.getTarea().getNumAuto().booleanValue() : false;
		
		if (subirArhivoDTO.isValidaInstanciaDeTareaEnBE()==true) {
			//InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
			//Valida si la tarea la tiene el usuario que esta intentando subir el archivo		
			log.debug("Valida si la tarea la tiene el usuario que esta intentando subir el archivo");
			UsuarioAsignado ua = usuarioAsignadoDao.getUsuarioAsignadoPorInstanciaDeTareaIdUsuario(instanciaDeTarea, usuario.getIdUsuario());
			if (ua == null) {
				SgdpExceptionValidaTareaEnBE se = new SgdpExceptionValidaTareaEnBE(configProps.getProperty("tareaNoEstaEnBE"), Level.WARN, true);
				throw se;
			}
		}	
		
		String usuarioSGDP = subirArhivoDTO.getIdUsuarioSube()!=null && !subirArhivoDTO.getIdUsuarioSube().isEmpty() ? subirArhivoDTO.getIdUsuarioSube() : usuario.getIdUsuario();
		log.info("usuarioSGDP: " + usuarioSGDP);		
		TipoDeDocumento tipoDeDocumentoValidaOficial = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir());
		
		//boolean aplicaFea = tipoDeDocumentoValidaOficial.getAplicaFEA();
		//boolean tieneNumAuto = tipoDeDocumentoValidaOficial.getNumAuto()!=null ? tipoDeDocumentoValidaOficial.getNumAuto().booleanValue() : false;
		
		Tarea tarea = TareaDao.getBuscaDocumentoOficialEnTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
		
		try {		
			
			/*if (puedeAplicarFEA == true && aplicaFea == true && puedeTenerNumAuto == true && tieneNumAuto == true && tipoDeDocumentoValidaOficial.getCodTipoDocumento()!=null && !tipoDeDocumentoValidaOficial.getCodTipoDocumento().isEmpty()) {
				
				if (subirArhivoDTO.getCategoriaDocumento()== null || subirArhivoDTO.getCategoriaDocumento().isEmpty()) {

					TipoDocumentoDTO tipoDocumentoDTO = numeracionClientRestService.getTipoDocumentoPorCodTipoDoc(tipoDeDocumentoValidaOficial.getCodTipoDocumento());				
					if (tipoDocumentoDTO.getEstado().equals("0")) {
						subirArhivoDTO.setCategoriaDocumento(tipoDocumentoDTO.getNombreCompletoTipoDoc());
					} else {
						throw new SgdpException("ERROR al intentar obtener el tipo de documento del sistema de numeraci\u00F3n", Level.ERROR, true);
					}				
				}
				
				String extensionDocumento = subirArhivoDTO.getNombreDeArchivo().substring(subirArhivoDTO.getNombreDeArchivo().lastIndexOf('.'), subirArhivoDTO.getNombreDeArchivo().length());				
				String nombreArchivo = subirArhivoDTO.getCategoriaDocumento()  + " " + FechaUtil.simpleDateFormat.format(new Date()) + extensionDocumento;
				subirArhivoDTO.setNombreDeArchivo(nombreArchivo);
				subirArhivoDTO.setCambiaNombreArchivo(true);
							
			}*/
			
			if (tipoDeDocumentoValidaOficial.getCodTipoDocumento()!=null && !tipoDeDocumentoValidaOficial.getCodTipoDocumento().isEmpty()
					&& (subirArhivoDTO.getCategoriaDocumento()==null || subirArhivoDTO.getCategoriaDocumento().isEmpty())) {
				TipoDocumentoDTO tipoDocumentoDTO = numeracionClientRestService.getTipoDocumentoPorCodTipoDoc(tipoDeDocumentoValidaOficial.getCodTipoDocumento());				
				log.info(tipoDocumentoDTO.toString());
				if (tipoDocumentoDTO.getEstado().equals("0")) {
					subirArhivoDTO.setCategoriaDocumento(tipoDocumentoDTO.getNombreCompletoTipoDoc());
				} else {
					throw new SgdpException("ERROR al intentar obtener el tipo de documento del sistema de numeraci\u00F3n");
				}
			}
			
			if (subirArhivoDTO.getConvertirAPDF()) {				
				log.info("Conviertiendo a PDF");
				if (tipoDeDocumentoValidaOficial.getConformaExpediente() == true && tarea.getConformaExpediente()!=null && tarea.getConformaExpediente() == true ) {
					subirArhivoDTO.setEsDocumentoOficial(true);
				}				
				respuestaConversionArchivoDTO = gestorDeDocumentosService.convertirArchivoAPDF(usuario, subirArhivoDTO);
				if (respuestaConversionArchivoDTO!=null && respuestaConversionArchivoDTO.getResultadoOperacion().equals("1")) {
					subirArhivoDTO.setIdArchivoEnCMS(respuestaConversionArchivoDTO.getIdArchivo());
					subirArhivoDTO.setNombreDeArchivo(respuestaConversionArchivoDTO.getNombreArchivo());					
					log.debug("respuestaConversionArchivoDTO.getResultadoOperacion(): " + respuestaConversionArchivoDTO.getResultadoOperacion());
					if (subirArhivoDTO.getComentario()!=null && !subirArhivoDTO.getComentario().equals("null") && !subirArhivoDTO.getComentario().equals("")) {
						gestorMetadataCMSService.agregarComentarioANodo(usuario, subirArhivoDTO.getIdArchivoEnCMS(), subirArhivoDTO.getComentario());
					}
					if (subirArhivoDTO.getEsDocumentoOficial()==true) {
						DetalleDeArchivoDTO detalleDeArchivoDTOActualiza = new DetalleDeArchivoDTO();
						detalleDeArchivoDTOActualiza.setEsDocumentoOficial("true");
						detalleDeArchivoDTOActualiza.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());
						String respuestaActualizaMetaDataDeDocumento = gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTOActualiza);
						log.info("respuestaActualizaMetaDataDeDocumento: " + respuestaActualizaMetaDataDeDocumento);	
					}
				} else {						
					throw new SgdpException("Error al convertirArchivo a PDF");
				}
				List<ArchivosInstDeTarea> archivosInstDeTareaList = archivosInstDeTareaDao.getArchivosPorIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());
				if (archivosInstDeTareaList!=null && !archivosInstDeTareaList.isEmpty()) {
					for (ArchivosInstDeTarea archivosInstDeTareaV : archivosInstDeTareaList) {
						dejarContadorDeVisacionEnCero = true;
						archivosInstDeTareaV.setEstaVisado(false);												
					}
				}
				log.debug("dejarContadorDeVisacionEnCero: " + dejarContadorDeVisacionEnCero);
				if (dejarContadorDeVisacionEnCero==true) {
					DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();
					detalleDeArchivoDTO.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());
					detalleDeArchivoDTO.setFirmaSimple("0");
					String respuestaActualizaMetaDataDeDocumento = gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO);
					log.debug("respuestaActualizaMetaDataDeDocumento: " + respuestaActualizaMetaDataDeDocumento);
				}
			} else {
				log.info("No convierte a PDF");				
				if (tipoDeDocumentoValidaOficial.getConformaExpediente() == true 
						&& tarea.getConformaExpediente()!=null 
						&& tarea.getConformaExpediente() == true) {
					DetalleDeArchivoDTO detalleDeArchivoDTOActualiza = new DetalleDeArchivoDTO();
					detalleDeArchivoDTOActualiza.setEsDocumentoOficial("true");
					detalleDeArchivoDTOActualiza.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());	
					if (subirArhivoDTO.getCategoriaDocumento()!=null && !subirArhivoDTO.getCategoriaDocumento().isEmpty() 
							&& !subirArhivoDTO.getCategoriaDocumento().equals("null")) {
						detalleDeArchivoDTOActualiza.setCategoriaDocumento(subirArhivoDTO.getCategoriaDocumento());
					}
					log.info(detalleDeArchivoDTOActualiza.toString());
					String respuestaActualizaMetaDataDeDocumento = gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTOActualiza);
					log.info("respuestaActualizaMetaDataDeDocumento: " + respuestaActualizaMetaDataDeDocumento);				
				} else if (subirArhivoDTO.getCategoriaDocumento()!=null && !subirArhivoDTO.getCategoriaDocumento().isEmpty() 
						&& !subirArhivoDTO.getCategoriaDocumento().equals("null")) {
					DetalleDeArchivoDTO detalleDeArchivoDTOActualiza = new DetalleDeArchivoDTO();
					detalleDeArchivoDTOActualiza.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());
					detalleDeArchivoDTOActualiza.setCategoriaDocumento(subirArhivoDTO.getCategoriaDocumento());
					log.info(detalleDeArchivoDTOActualiza.toString());
					String respuestaActualizaMetaDataDeDocumento = gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTOActualiza);
					log.info("respuestaActualizaMetaDataDeDocumento: " + respuestaActualizaMetaDataDeDocumento);	
				}
				/*if (subirArhivoDTO.isCambiaNombreArchivo()==true) {
					subirArhivoDTO = gestorDeDocumentosService.subirArchivoDesdeOtroCambiandoNombre(usuario, subirArhivoDTO.getNombreDeArchivo(), subirArhivoDTO);
				}*/
			}
			if (subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo()>0) {
				DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, subirArhivoDTO.getIdArchivoEnCMS());
				if (subirArhivoDTO.getNombreDeArchivo()==null || subirArhivoDTO.getNombreDeArchivo().equals("null")) {
					subirArhivoDTO.setNombreDeArchivo(detalleDeArchivoDTO.getNombre());
				}
				TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir());				
				//InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());	
				log.debug("Buscando archivosInstDeTarea");				
				if (subirArhivoDTO.getEsRequerido()) {
					log.debug("buscando el archivosInstDeTarea cuando es requerido");
					log.debug(subirArhivoDTO.toString());
					//archivosInstDeTarea = archivosInstDeTareaDao.getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuario(
					//		usuarioSGDP, subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo(), subirArhivoDTO.getIdTipoDeDocumentoSubir());
					archivosInstDeTarea = archivosInstDeTareaDao.getArchivoPorIdInstanciaDeTareaIdTipoDeDocumento(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo(), subirArhivoDTO.getIdTipoDeDocumentoSubir());
				} else {
					log.debug("buscando el archivosInstDeTarea cuando no es requerido");
					log.debug(subirArhivoDTO.toString());
					archivosInstDeTarea = archivosInstDeTareaDao.getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuarioNombreArchivo(
							usuarioSGDP, subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo(), subirArhivoDTO.getIdTipoDeDocumentoSubir(),
							subirArhivoDTO.getNombreDeArchivo());
				}				
				log.debug("Busco archivosInstDeTarea");
				if (archivosInstDeTarea==null) {
					log.debug("***** archivosInstDeTarea==null ******");
					archivosInstDeTarea = new ArchivosInstDeTarea();
					nuevoRegistroArchivosInstDeTarea = true;
				} else {
					log.debug("***** archivosInstDeTarea!=null ******");
					log.debug(archivosInstDeTarea.toString());
				}
				log.debug(archivosInstDeTarea.toString());
				archivosInstDeTarea.setFechaSubido(new Date());
				archivosInstDeTarea.setIdArchivoCms(subirArhivoDTO.getIdArchivoEnCMS());
				archivosInstDeTarea.setIdUsuario(usuarioSGDP);
				archivosInstDeTarea.setInstanciaDeTarea(instanciaDeTarea);
				archivosInstDeTarea.setMimeType(detalleDeArchivoDTO.getMimeType());
				archivosInstDeTarea.setNombreArchivo(detalleDeArchivoDTO.getNombre());
				archivosInstDeTarea.setTipoDeDocumento(tipoDeDocumento);
				archivosInstDeTarea.setVersion(detalleDeArchivoDTO.getLabelUltimaVersion());
				archivosInstDeTarea.setEstaVisado(false);
				archivosInstDeTarea.setEstaFirmadoConFEACentralizada(false);
				archivosInstDeTarea.setEstaFirmadoConFEAWebStart(false);
				if (!detalleDeArchivoDTO.getFechaDeCreacion().isEmpty() && detalleDeArchivoDTO.getFechaDeCreacion()!=null) {
					try {
						Date fechaDoc = FechaUtil.simpleDateFormatForm.parse(detalleDeArchivoDTO.getFechaDeCreacion());
						archivosInstDeTarea.setFechaDocumento(fechaDoc);
					} catch (ParseException e) {
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						String exceptionAsString = sw.toString();
						log.info(exceptionAsString);
					}					
				}
				if (!detalleDeArchivoDTO.getFechaDeRecepcion().isEmpty() && detalleDeArchivoDTO.getFechaDeRecepcion()!=null) {
					try {
						Date fechaRecep = FechaUtil.simpleDateFormatForm.parse(detalleDeArchivoDTO.getFechaDeRecepcion());
						archivosInstDeTarea.setFechaRecepcion(fechaRecep);
					} catch (ParseException e) {
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						String exceptionAsString = sw.toString();
						log.info(exceptionAsString);
					}
				}				
				if (subirArhivoDTO.getConvertirAPDF()) {
					archivosInstDeTarea.setEstaVisado(false);
					archivosInstDeTarea.setEstaFirmadoConFEACentralizada(false);
					archivosInstDeTarea.setEstaFirmadoConFEAWebStart(false);
					List<ArchivosInstDeTarea> archivosInstDeTareaPorIdProcesoDistinIdInstanTarea = archivosInstDeTareaDao.getArchivosInstDeTareaPorIdProcesoDistinIdInstanTarea(
							subirArhivoDTO.getIdArchivoEnCMS(), 
							instanciaDeTarea.getInstanciaDeProceso().getIdInstanciaDeProceso(), 
							instanciaDeTarea.getIdInstanciaDeTarea());
					for (ArchivosInstDeTarea archivosInstDeTareaPorIdProcDistInsTarea: archivosInstDeTareaPorIdProcesoDistinIdInstanTarea) {
						archivosInstDeTareaPorIdProcDistInsTarea.setEstaVisado(false);
						archivosInstDeTareaPorIdProcDistInsTarea.setEstaFirmadoConFEACentralizada(false);
						archivosInstDeTareaPorIdProcDistInsTarea.setEstaFirmadoConFEAWebStart(false);
					}
				}
				log.debug(archivosInstDeTarea.toString());
				if (nuevoRegistroArchivosInstDeTarea==true) {
					log.debug("nuevoRegistroArchivosInstDeTarea==true");
					log.debug("insertando nvo registro");
					archivosInstDeTareaDao.insertaArchivosInstDeTarea(archivosInstDeTarea);	
				}									
			} else {
				log.error("Id Instancia De Tarea Invalido: " + subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
				throw new SgdpException(configProps.getProperty("errorAlMarcarArchivoComoSubido"));
			}
		} catch (SgdpException sgdpe) {			
			throw sgdpe;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			log.debug("Fin... marcarArchivoComoSubido.. error");
			throw new SgdpException(configProps.getProperty("errorAlMarcarArchivoComoSubido"));
		}
	}

	@Override
	public RespuestaAsignarNumeroDocumento agregarNumeroDocumentoPDF(MultipartFile archivo, long IdTipoDeDocumentoSubir)
			throws SgdpException, SgdpExceptionValidaTareaEnBE {

		log.debug("inicio agregarNumeroDocumentoPDF");
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();

		// Respuesta DTO

		RespuestaAsignarNumeroDocumento respuestaAsignarNumeroDocumento = new RespuestaAsignarNumeroDocumento();

		// Datos de entrada para asignar Documento
		AsignacionesNumerosDocDto asignacionesNumerosDocDto = new AsignacionesNumerosDocDto();
		asignacionesNumerosDocDto.setTipoDeDocumento(IdTipoDeDocumentoSubir);

		// Reservo Documento
		RespuestaAsignacionesNumerosDocDto respuestaAsignacionesNumerosDocDto = new RespuestaAsignacionesNumerosDocDto();
		respuestaAsignacionesNumerosDocDto = asignacionNumeroDocService
				.guardarAsignacionDocumento(asignacionesNumerosDocDto);
		respuestaAsignacionesNumerosDocDto
				.setRespuesta(respuestaAsignacionesNumerosDocDto.getRespuesta().toUpperCase());

		// Cambiar Estado Firmado

		asignacionesNumerosDocDto
				.setIdAsignacionNumeroDocEntada(respuestaAsignacionesNumerosDocDto.getIdAsignacionNumeroDoc());
		asignacionesNumerosDocDto.setCodigoMensaje(respuestaAsignacionesNumerosDocDto.getRespuesta());
		RespuestaAsignacionesNumerosDocDto respuestaAsignacionesNumerosDocDto2 = new RespuestaAsignacionesNumerosDocDto();
		if (respuestaAsignacionesNumerosDocDto.getRespuesta().equals("OK")) {
			respuestaAsignacionesNumerosDocDto2 = asignacionNumeroDocService
					.modicarAsignacionDocumento(asignacionesNumerosDocDto);
		} else {
			respuestaAsignacionesNumerosDocDto2 = asignacionNumeroDocService
					.modicarAsignacionDocumento(asignacionesNumerosDocDto);
			respuestaAsignarNumeroDocumento.setRespuesta("ERROR");
			respuestaAsignarNumeroDocumento
					.setIdAsignacionNumeroDocEntada(respuestaAsignacionesNumerosDocDto.getIdAsignacionNumeroDoc());
			return respuestaAsignarNumeroDocumento;
		}

		try

		{

			PdfReader pdfReader = new PdfReader(archivo.getBytes());

			File document_signed = File.createTempFile("temp-file-name", ".pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(document_signed));

			PdfContentByte canvas = pdfStamper.getOverContent(1);

			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
					new Phrase("Documento N\\U00B0 " + respuestaAsignacionesNumerosDocDto.getNumeroDocumento()), 400, 750, 0);

			pdfStamper.close();

			pdfReader.close();

			FileUtil fileUtil = new FileUtil();
			byte[] bFile = fileUtil.convertirFileEnBytes(document_signed);

			sgdpMultipartFile.setBytes(bFile);
			sgdpMultipartFile.setContentType(archivo.getContentType());
			sgdpMultipartFile.setName(archivo.getName());
			sgdpMultipartFile.setOriginalFilename(archivo.getOriginalFilename());

		} catch (Exception e) {
			log.error("Error AgregarNumeroDocumentoPDF " + e.getMessage());			
			respuestaAsignarNumeroDocumento.setRespuesta("ERROR");
			respuestaAsignarNumeroDocumento
					.setIdAsignacionNumeroDocEntada(respuestaAsignacionesNumerosDocDto.getIdAsignacionNumeroDoc());

			return respuestaAsignarNumeroDocumento;
		}

		log.debug("Seteando archivo");
		respuestaAsignarNumeroDocumento.setArchivo(sgdpMultipartFile);
		respuestaAsignarNumeroDocumento.setRespuesta("OK");
		respuestaAsignarNumeroDocumento
				.setIdAsignacionNumeroDocEntada(respuestaAsignacionesNumerosDocDto.getIdAsignacionNumeroDoc());
		return respuestaAsignarNumeroDocumento;

	}	
	
	
	public SubirArhivoDTO subirArchivoConAsignacion(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws SgdpException, SgdpExceptionValidaTareaEnBE {		
		log.debug("Entro al metodo subirArchivoConAsignacion ");
		log.info(subirArhivoDTO.toString());
		DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, subirArhivoDTO.getIdArchivoEnCMS());
		log.info(detalleDeArchivoDTO);		
		if (subirArhivoDTO.getFechaCreacionArchivo()!=null) {
			detalleDeArchivoDTO.setFechaDeCreacion(subirArhivoDTO.getFechaCreacionArchivo());
		}
		if (subirArhivoDTO.getFechaRecepcionArchivo()!=null) {
			detalleDeArchivoDTO.setFechaDeRecepcion(subirArhivoDTO.getFechaRecepcionArchivo());
		}	
		detalleDeArchivoDTO.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());
		detalleDeArchivoDTO.setNumeroDocumento(subirArhivoDTO.getNumeroDocumento());
		log.info("Despues de setear fechas");
		log.info(detalleDeArchivoDTO);
		String respuestActualizaMetaDataDeDocumento = gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO);		
		if(!respuestActualizaMetaDataDeDocumento.equals("Datos Actualizados Correctamente")){
			throw new SgdpException("Error al actualizar la metadata del documento");
		}		
		log.debug("Actualiza la metada del documento ");		
		this.marcarArchivoComoSubido(usuario, subirArhivoDTO);
		log.debug("Se marco el archivo como subido ");		
		return subirArhivoDTO;				
	}

	@Override
	public RespuestaMailDTO guardarHistoricoArchivoYTareas(
			SubirArhivoDTO subirArhivoDTO, Usuario usuario) {		 
		
		RespuestaMailDTO respuestaMailDTO = new RespuestaMailDTO();
	
		InstanciaDeTarea instanciaDeTarea = new InstanciaDeTarea();
		instanciaDeTarea.setIdInstanciaDeTarea(subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
		
		String usuarioSGDP = subirArhivoDTO.getIdUsuarioSube()!=null && !subirArhivoDTO.getIdUsuarioSube().isEmpty() ? subirArhivoDTO.getIdUsuarioSube() : usuario.getIdUsuario();
		log.info("usuarioSGDP: " + usuarioSGDP);
		
		try {
			
			//Se inserta un nuevo registro en historico de instancia de tarea
			log.debug("Insertando en HistoricoDeInstDeTarea");
			HistoricoDeInstDeTarea historicoDeInstDeTarea = new HistoricoDeInstDeTarea();
			historicoDeInstDeTarea.setAccionHistInstDeTarea(accionesHistInstDeTareaDao.getAccionHistInstDeTareaPorId(AccionesHistInstDeTareaType.SUBE.getIdAccionHistoricoInstDeTarea()));
			historicoDeInstDeTarea.setComentario("");
			historicoDeInstDeTarea.setIdUsuarioOrigen(usuarioSGDP);
			historicoDeInstDeTarea.setFechaMovimiento(new Date());
			historicoDeInstDeTarea.setInstanciaDeTareaDeDestino(instanciaDeTarea);
			historicoDeInstDeTarea.setInstanciaDeTareaDeOrigen(instanciaDeTarea);
			historicoDeInstDeTareaDao.insertHistoricoDeInstDeTarea(historicoDeInstDeTarea, usuario);		
				
			DetalleDeArchivoDTO	detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, subirArhivoDTO.getIdArchivoEnCMS());
			detalleDeArchivoDTO.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());				
			HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea = new HistoricoArchivosInstDeTarea();
			historicoArchivosInstDeTarea.setHistoricoDeInstDeTarea(historicoDeInstDeTarea);
			historicoArchivosInstDeTarea.setIdArchivoCms(detalleDeArchivoDTO.getIdArchivo());
			historicoArchivosInstDeTarea.setMimeType(detalleDeArchivoDTO.getMimeType());
			historicoArchivosInstDeTarea.setNombreArchivo(detalleDeArchivoDTO.getNombre());
			historicoArchivosInstDeTarea.setVersion(detalleDeArchivoDTO.getLabelUltimaVersion());
			historicoArchivosInstDeTarea.setTipoDeDocumento(tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir()));
			historicoArchivosInstDeTarea.setIdUsuario(usuario.getIdUsuario());
			historicoArchivosInstDeTarea.setFechaSubido(new Date());
			if (detalleDeArchivoDTO.getFechaDeCreacion()!=null) {
				try {
					historicoArchivosInstDeTarea.setFechaDocumento(FechaUtil.simpleDateFormatForm.parse(detalleDeArchivoDTO.getFechaDeCreacion()));
				} catch (ParseException e) {
					log.warn("Problema al parsear fecha de documento");
					log.info(detalleDeArchivoDTO.toString());
				}
			}
			if (detalleDeArchivoDTO.getFechaDeRecepcion()!=null) {
				try {
					historicoArchivosInstDeTarea.setFechaRecepcion(FechaUtil.simpleDateFormatForm.parse(detalleDeArchivoDTO.getFechaDeRecepcion()));
				} catch (ParseException e) {
					log.warn("Problema al parsear fecha de recepcion");
					log.info(detalleDeArchivoDTO.toString());
				}
			}			
			historicoArchivosInstDeTareaDao.insertArchivosHistInstDeTarea(historicoArchivosInstDeTarea, usuario);			
			respuestaMailDTO.setRespuesta("OK");
			return respuestaMailDTO;
				
		} catch (SgdpException e) {			
		    respuestaMailDTO.setRespuesta("ERROR");
		    respuestaMailDTO.setCodigoError(e.getMessage());
		    return respuestaMailDTO;
		}
			
	}
	
	private boolean estaTipoDeDocumentoEnProceso(SubirArhivoDTO subirArhivoDTO) {
		
		if (subirArhivoDTO.getIdTipoDeDocumentoSubir()==Long.parseLong(configProps.getProperty("idTipoDeDocumentoAntecedente"))) {
			return true;
		} else {
			List<TipoDeDocumentoDTO> tiposDeDocumentosDTO = tipoDeDocumentoService.getTiposDeDocumentosPorIdExpediente(subirArhivoDTO.getIdExpedienteSubirArchivo());
			for (TipoDeDocumentoDTO tipoDeDocumentoDTO : tiposDeDocumentosDTO) {
				if (tipoDeDocumentoDTO.getIdTipoDeDocumento() == subirArhivoDTO.getIdTipoDeDocumentoSubir()) {
					return true;
				}
			}
			return false;
		}
		
	}

}
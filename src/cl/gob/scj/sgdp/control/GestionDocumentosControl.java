package cl.gob.scj.sgdp.control;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.FirmaAvanzadaDTO;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.FirmaSimpleDocumentoResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaArchivoRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;

@Controller
public class GestionDocumentosControl {	
	
	private static final Logger log = Logger.getLogger(GestionDocumentosControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	private SubirArchivoService subirArchivoService;
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@RequestMapping(value="/visarDocumento/{idDocumento}/{idInstanciaDeTarea}/{idTipoDeDocumento}", method=RequestMethod.POST)
	public @ResponseBody String visarDocumento(@PathVariable("idDocumento") String idDocumento,
			@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea,
			@PathVariable("idTipoDeDocumento") long idTipoDeDocumento, HttpServletRequest request) {	
		
		log.debug("Inicio visarDocumento");
		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		subirArhivoDTO.setIdArchivoEnCMS(idDocumento);
		subirArhivoDTO.setIdTipoDeDocumentoSubir(idTipoDeDocumento);
		subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(idInstanciaDeTarea);

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		 
		
		try {
			
			FirmaSimpleDocumentoResponse firmaSimpleDocumentoResponse = gestorDeDocumentosService.firmaSimpleDocumento(idDocumento, usuario, idInstanciaDeTarea, idTipoDeDocumento);
			
			subirArchivoService.marcarArchivoComoSubido(usuario, subirArhivoDTO);
			
			log.debug("firmaSimpleDocumentoResponse.getIdArchivo(): " + firmaSimpleDocumentoResponse.getIdArchivo());
			
			return configProps.getProperty("seFirmoCorrectamenteElDocumento");
		
		} catch (SgdpException e) {
			
			log.error(e.getMessage());
			
			return e.getMessage();
		
		} catch (Exception e) {
			
			return configProps.getProperty("errorAlAplicarFirmaSimpleADocumento");
		
		}		
				
	}
	
	@RequestMapping(value="/firmaAvanzadaDocumento", method=RequestMethod.POST)
	public @ResponseBody FirmaAvanzadaDTO firmaAvanzadaDocumento(
			@ModelAttribute("firmaAvanzadaDTO") FirmaAvanzadaDTO firmaAvanzadaDTO,		
			HttpServletRequest request) {
		
		log.info("Inicio firmaAvanzaDocumento");
		log.info(firmaAvanzadaDTO.toString());
		
		KeyParametroPorContextoDTO keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS = new KeyParametroPorContextoDTO();
		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		subirArhivoDTO.setIdArchivoEnCMS(firmaAvanzadaDTO.getIdDocumento());
		subirArhivoDTO.setIdTipoDeDocumentoSubir(firmaAvanzadaDTO.getIdTipoDeDocumento());
		subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(firmaAvanzadaDTO.getIdInstanciaDeTarea());
		subirArhivoDTO.setEsRequerido(true);
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			
		keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS.setValorContexto(firmaAvanzadaDTO.getMimeType());		
		keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS.setNombreParametro(Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_TIPO_DE_DOCUMENTO_FEA_POR_MIME_TYPE_EN_CMS);
			
		FirmaAvanzadaRequest firmaAvanzadaRequest = new FirmaAvanzadaRequest();
		FirmaAvanzadaArchivoRequest firmaAvanzadaArchivoRequest = new FirmaAvanzadaArchivoRequest();
		
		firmaAvanzadaDTO = gestorDeDocumentosService.firmarDocumentoConFEA(firmaAvanzadaRequest, 
			firmaAvanzadaArchivoRequest, firmaAvanzadaDTO, usuario, keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS);
	
		log.debug(firmaAvanzadaDTO.toString());
	
		return firmaAvanzadaDTO;
			
	}
	
	@ResponseBody
	@RequestMapping(value = "/getImagenCodigoQRUsuario", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImagenCodigoQRUsuario(HttpServletRequest request) {
		log.debug("inicio getImagenCodigoQRUsuario");
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {			
			if (usuario.getPermisos().containsKey(PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso())) {
				byte[] byteImagenCodigoQRUsuario = gestorDeDocumentosService.getContenidoArchivo(usuario.getIdArchivoImagenQR(), usuario);			
				return byteImagenCodigoQRUsuario;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			log.error("ERROR al obtener imagen del codigo QR: ", e);
			return null;
		}
	}
		
	@RequestMapping(value = "/getArchivoPorId/{idArchivo}", method = RequestMethod.GET)
	public @ResponseBody void getArchivoPorId(@PathVariable("idArchivo") String idArchivo, HttpServletRequest request, HttpServletResponse response) {
		log.debug("inicio getArchivoPorId");
		log.debug("idArchivo: " + idArchivo);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {			
			byte[] byteArchivo = gestorDeDocumentosService.getContenidoArchivo(idArchivo, usuario);			
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			detalleDeArchivoDTO.setIdArchivo(idArchivo);
			log.debug(detalleDeArchivoDTO.toString());
			response.setContentType(detalleDeArchivoDTO.getMimeType());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + detalleDeArchivoDTO.getNombre() + "\"");
		    response.setHeader("Content-Length", String.valueOf(byteArchivo.length));
		    OutputStream out = response.getOutputStream();
		    out.write(byteArchivo);		  
		    out.flush();
		} catch (Exception e) {
			log.error("ERROR al obtener archivo por id: ", e);			
		}
	}
	
	@RequestMapping(value = "/getContenidoArchivoPorIdYVersion", method = RequestMethod.GET)
	public @ResponseBody void getContenidoArchivoPorIdYVersion(/*@PathVariable("idArchivo") String idArchivo, 
			@PathVariable("versionLabel") String versionLabel,
			@PathVariable("versionMimeType") String versionMimeType,*/
			HttpServletRequest request, HttpServletResponse response) {
		
		log.debug("inicio getContenidoArchivoPorIdYVersion");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		try {	
			String idArchivo = request.getParameter("idArchivo");
			String versionLabel = request.getParameter("versionLabel");
			String versionMimeType = request.getParameter("versionMimeType");
			versionMimeType = URLDecoder.decode(versionMimeType, "UTF-8");
			log.debug("idArchivo: " + idArchivo);
			log.debug("versionLabel: " + versionLabel);
			log.debug("versionMimeType: " + versionMimeType);
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			detalleDeArchivoDTO.setIdArchivo(idArchivo);
			log.debug(detalleDeArchivoDTO.toString());
			byte[] byteArchivo = gestorDeDocumentosService.getContenidoArchivoDesdeUrlYVersion(detalleDeArchivoDTO, versionLabel, usuario)	;
			response.setContentType(versionMimeType);
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + detalleDeArchivoDTO.getNombre() + "\"");
		    response.setHeader("Content-Length", String.valueOf(byteArchivo.length));
		    OutputStream out = response.getOutputStream();
		    out.write(byteArchivo);		  
		    out.flush();
		} catch (Exception e) {
			log.error("ERROR al obtener archivo por id y version: ", e);			
		}
	}
	
	@RequestMapping(value = "/firmaApplets/{idArchivo}/{nombreDocumento}/{idExpediente}/{idTipoDeDocumento}/{idInstanciaDeTarea}" , method = RequestMethod.GET)
	public void revisionesImplementoObj( Model model ,
										  @PathVariable("idArchivo") String idArchivo, 									
										  @PathVariable("nombreDocumento") String nombreDocumento, 
										  @PathVariable("idExpediente") String idExpediente,  
										  @PathVariable("idTipoDeDocumento") long idTipoDeDocumento, 
										  @PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea,										  
										  HttpServletRequest request, 
										  HttpServletResponse response) {     	
		
		log.info("Inicio firmando con Web Start");
		
		log.info("idArchivo: " + idArchivo);
		log.info("nombreDocumento: " + nombreDocumento);
		log.info("idExpediente: " + idExpediente);
		log.info("idTipoDeDocumento: " + idTipoDeDocumento);
		log.info("idInstanciaDeTarea: " + idInstanciaDeTarea);
		
		PrintWriter out;
		try {
			out = response.getWriter();			
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");					      
			String ticket = usuario.getAlfTicket();
				
			String jnlp = gestorDeDocumentosService.getJnlp(idArchivo, ticket, nombreDocumento, idExpediente, 
				idTipoDeDocumento, usuario, idInstanciaDeTarea);
			try {
				response.setContentType("application/x-java-jnlp-file");
		      	response.setHeader("Content-disposition", "attachment; filename = FirmaAvanzada.jnlp");
		      	out.print(jnlp);
			} finally {
				out.close();
			}
		  
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}   

	}
}

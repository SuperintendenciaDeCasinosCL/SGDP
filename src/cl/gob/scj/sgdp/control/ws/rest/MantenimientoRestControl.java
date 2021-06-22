package cl.gob.scj.sgdp.control.ws.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO;
import cl.gob.scj.sgdp.dto.UsuarioNotificacionTareaDTO;
import cl.gob.scj.sgdp.dto.rest.DatosUsuarioRequest;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.UsuarioNotificacionTareaService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.rest.service.MatenimientoRestService;

@RestController
@RequestMapping("/mantenimiento")
public class MantenimientoRestControl {
	
	private static final Logger log = Logger.getLogger(MantenimientoRestControl.class);
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	private ArchivosInstDeTareaService archivosInstDeTareaService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Autowired
	private UsuarioNotificacionTareaService usuarioNotificacionTareaService; 
	
	@Autowired
	private	EmailService emailService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private	MatenimientoRestService matenimientoRestService;
	
	private static final String PASSWORD_SHA256 = "a1c54595d6eb601ea775e92e9e8a00712e1313009b753a0c17657a44f332bed9";
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@RequestMapping(value = "actualizaMetadatoEsDocumentoOficial", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody List<String> actualizaMetadatoEsDocumentoOficial(@RequestBody DatosUsuarioRequest datosUsuarioRequest ) throws Exception {
		
		log.debug("Inicio actualizaMetadatoEsDocumentoOficial");
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(datosUsuarioRequest.getIdUsuario());
		
		log.debug("usuario.getIdUsuario()" + usuario.getIdUsuario());
		
		usuario.setAlfTicket(autenticacionService.login(datosUsuarioRequest.getIdUsuario()));
		
		List<String> idDeDocumentosOficiales = archivosInstDeTareaService.getIdDeDocumentosSubidosOficiales();
		
		DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();
		
		String respuestaActualizaDocumento;
		
		List<String> respuestaListActualizaDocumento = new ArrayList<String>();
		
		for (String idDocumentoOficial : idDeDocumentosOficiales) {
			log.debug("idDocumentoOficial: " + idDocumentoOficial);
			detalleDeArchivoDTO.setIdArchivo(idDocumentoOficial);
			detalleDeArchivoDTO.setEsDocumentoOficial("true");
			try {
				respuestaActualizaDocumento = gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO);
				respuestaActualizaDocumento = idDocumentoOficial + ": " + respuestaActualizaDocumento;
			} catch (SgdpException e) {
				respuestaActualizaDocumento = idDocumentoOficial + ": " + e.getMessage();
			}
			respuestaListActualizaDocumento.add(respuestaActualizaDocumento);
		}
		
		return respuestaListActualizaDocumento;
	}
	
	@RequestMapping(value = "getUsuariosNotificacionTareaPorIdTarea/{idTarea}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO getUsuariosNotificacionTareaPorIdTarea(@PathVariable("idTarea") long idTarea, HttpServletRequest request) {
		Usuario usuario = new Usuario();
		RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO respuestaGetUsuariosNotificacionTareaPorIdTareaDTO = new RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO();
		try {

			List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO = usuarioNotificacionTareaService.getUsuariosNotificacionTareaPorIdTareaOnOut(idTarea, usuario);
			respuestaGetUsuariosNotificacionTareaPorIdTareaDTO.setUsuariosNotificacionTareaDTO(usuariosNotificacionTareaDTO);
			respuestaGetUsuariosNotificacionTareaPorIdTareaDTO.setResultado("OK");			
		} catch (SgdpException e) {
			respuestaGetUsuariosNotificacionTareaPorIdTareaDTO.setResultado("ERROR");
		}
		return respuestaGetUsuariosNotificacionTareaPorIdTareaDTO;
	}
	
	@RequestMapping(value = "enviarMailNotificacionPorTarea", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String enviarMailNotificacionPorTarea(HttpServletRequest request) {
		String nombre = request.getParameter("nombre");
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		instanciaDeTareaDTO.setNombreDeTarea("Tarea 1");
		instanciaDeTareaDTO.setNombreDeProceso("Proceso 1");
		instanciaDeTareaDTO.setNombreExpediente("EXP-1010-2018");
		try {
			ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO);		
			ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
			ParametroDTO parametroMailAsuntoNotificacion = parametroService.getParametroPorID(Constantes.ID_PARAM_ASUNTO_NOTIFICACION);
			ParametroDTO parametroMailBodyMsgNotificacion = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_NOTIFICACION);
			ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
			ParametroDTO parametroSgdpMail = parametroService.getParametroPorID(Constantes.ID_PARAM_SGDP_CORREO);
			ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
			String asunto = parametroMailAsuntoNotificacion.getValorParametroChar().replace(configProps.getProperty("replaceNombreSubProceso"), instanciaDeTareaDTO.getNombreDeProceso()).
					replace(configProps.getProperty("replaceExpediente"), instanciaDeTareaDTO.getNombreExpediente()).replace(configProps.getProperty("replaceNombreDeTarea"), instanciaDeTareaDTO.getNombreDeTarea());
			String mensaje = parametroMailBodyMsgNotificacion.getValorParametroChar().replace(configProps.getProperty("replaceNombreDeTarea"), instanciaDeTareaDTO.getNombreDeTarea()).
					replace(configProps.getProperty("replaceNombreSubProceso"), instanciaDeTareaDTO.getNombreDeProceso()).
					replace(configProps.getProperty("replaceExpediente"), instanciaDeTareaDTO.getNombreExpediente())
					.replace(configProps.getProperty("replaceUrlSGDP"), parametroDTOUrlSGDP.getValorParametroChar());
			nombre = nombre + parametroSgdpMail.getValorParametroChar();
			emailService.enviarCorreoeX (parametroMailFrom.getValorParametroChar(), nombre, parametroMailSmtpHost.getValorParametroChar(), 
					asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
			return "OK";
		} catch (Exception e) {
			return "ERROR";
		}		
	}
	
	@RequestMapping(value = "testHash/{ps}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String testHash(@PathVariable("ps") String ps, HttpServletRequest request) {
		
		log.debug("ps: " + ps);
		
		String sha256hex = DigestUtils.sha256Hex(ps);
    	
		log.debug("sha256hex: " + sha256hex);
    	
    	if (sha256hex.equals(PASSWORD_SHA256)) {
    		return "OK";
    	} else {
    		return "ERROR";
    	}
		
	}
	
	@RequestMapping(value = "recargaParametros", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String recargaParametros(HttpServletRequest request) {
		try {
			parametroService.recargaParametros();
			return "OK";
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return "ERROR " + e.getMessage();
		}		
	}
	
	@RequestMapping(value = "actualizaMetaDataExpedientes/{idUsuario}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String actualizaMetaDataExpedientes(@PathVariable("idUsuario") String idUsuario, @RequestBody List<ExpedienteRestActMetaDTO> lista) {
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		
		log.debug("idUSuario: " + idUsuario);
		
		try {
		
			usuario.setAlfTicket(autenticacionService.login(idUsuario));
			
			return matenimientoRestService.actualizaMetaDataExpedientes(usuario, lista)	;
		
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return "ERROR " + e.getMessage();
		}	
	
	}

}

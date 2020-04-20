package cl.gob.scj.sgdp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.FechaFeriadoDao;
import cl.gob.scj.sgdp.dao.LogErrorDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dao.UsuarioRolDao;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.LogError;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.tipos.ErroresType;
import cl.gob.scj.sgdp.tipos.RolType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SgdpMultipartFile;
import cl.gob.scj.sgdp.ws.soap.client.BorraRegistroDocumentoResponse;
import cl.gob.scj.sgdp.ws.soap.client.GeneraRegistroDocumentoResponse;

@Service
@Transactional(rollbackFor = Throwable.class)
public class EmailServiceImpl implements EmailService {
	
	private static final Logger log = Logger.getLogger(EmailServiceImpl.class);	
		
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;	
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private SubirArchivoService subirArchivoService;
	
	@Autowired
	private FechaFeriadoDao fechaFeriadoDao;
	
	@Autowired
    JavaMailSender mailSender;
	
	@Autowired
	LogErrorDao logErrorDao;
	
	@Autowired
	UsuarioRolDao usuarioRolDao;
	
	@Override
	public RespuestaMailDTO enviarMail(Usuario usuario, InstanciaDeTarea instanciaDeTareaDeOrigen, List<String> listaDeUsuariosAsignados, String comentario, InstanciaDeTarea instanciaDeTareaDeDestino) throws SgdpException {
		log.debug("Enviando Mail");	
		log.debug(instanciaDeTareaDeOrigen.toString());
		log.debug(instanciaDeTareaDeDestino.toString());		
		String asuntoMensaje = new String(configProps.getProperty("asuntoMensaje"));
		
		try {
			
			asuntoMensaje = asuntoMensaje.replace(configProps.getProperty("replaceNombreDeTarea"), instanciaDeTareaDeDestino.getTarea().getNombreTarea()).replace(configProps.getProperty("replaceNombreSubProceso"), instanciaDeTareaDeDestino.getInstanciaDeProceso().getProceso().getNombreProceso());
			log.debug("asuntoMensaje: " + asuntoMensaje);
			
			ParametroDTO parametroDTOBodyMensaje = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE);
			ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
			ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
			ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
			String bodyMensaje = parametroDTOBodyMensaje.getValorParametroChar();
			log.debug(parametroDTOBodyMensaje.toString());
			log.debug(parametroDTOUrlSGDP.toString());
			log.debug(parametroMailSmtpHost.toString());
			log.debug(parametroMailTipoContenido.toString());
			bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceNombreSubProceso"), instanciaDeTareaDeDestino.getInstanciaDeProceso().getProceso().getNombreProceso());
			bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceRemitente"), usuario.getIdUsuario());
			bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceNombreDeTarea"), instanciaDeTareaDeDestino.getTarea().getNombreTarea());
			bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceUrlSGDP"), parametroDTOUrlSGDP.getValorParametroChar());
			bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceExpediente"), instanciaDeTareaDeDestino.getInstanciaDeProceso().getNombreExpediente());
			bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceComentario"), comentario);		
			log.debug("bodyMensaje: " + bodyMensaje);
			
			List<ArchivosInstDeTarea> archivosEnviados = archivosInstDeTareaDao.getArchivosPorIdInstanciaDeTarea(instanciaDeTareaDeOrigen.getIdInstanciaDeTarea());
			
			StringBuilder sbDocumentosTarea = new StringBuilder();
			
			if (archivosEnviados!=null && !archivosEnviados.isEmpty()) {			
				Iterator<ArchivosInstDeTarea> it = archivosEnviados.iterator();
				log.debug("archivosEnviados.size(): " + archivosEnviados.size());
				while (it.hasNext()) {
					ArchivosInstDeTarea archivosInstDeTarea = (ArchivosInstDeTarea) it.next();
					if (sbDocumentosTarea.indexOf(archivosInstDeTarea.getNombreArchivo())<0) {
						sbDocumentosTarea.append(archivosInstDeTarea.getNombreArchivo());
						if (it.hasNext()) {
							sbDocumentosTarea.append(", ");
						}
					}
				}
				bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceDocumentos"), sbDocumentosTarea.toString());
			} else {
				log.debug("archivosEnviados == null || archivosEnviadosisEmpty() == true");
				bodyMensaje = bodyMensaje.replace(configProps.getProperty("replaceDocumentos"), configProps.getProperty("noHayDocumentos"));			
			}	
			log.debug("bodyMensaje: " + bodyMensaje);
			
			long diasEntreFechas = 0;
			
			if (instanciaDeTareaDeDestino.getFechaVencimientoUsuario()!=null) {	
				diasEntreFechas = FechaUtil.diasHabilesHastaFecha(instanciaDeTareaDeDestino.getFechaVencimientoUsuario(), fechaFeriadoDao);
			} else {				
				diasEntreFechas = FechaUtil.diasHabilesHastaFecha(instanciaDeTareaDeDestino.getFechaVencimiento(), fechaFeriadoDao);
			}
			
			log.debug("instanciaDeTareaDeDestino.getFechaVencimiento(): " + FechaUtil.getSimpleDateFormatFormHHMMSSMS().format(instanciaDeTareaDeDestino.getFechaVencimiento()));
			log.debug("new Date(): " + FechaUtil.getSimpleDateFormatFormHHMMSSMS().format(new Date()));
			log.debug("diasEntreFechas: " + diasEntreFechas);
			
			if (diasEntreFechas > 1) {
				bodyMensaje = bodyMensaje.replace(configProps.getProperty("replacePlazo"), new Long(diasEntreFechas).toString() + " " + configProps.getProperty("replaceDias"));
			} else if (diasEntreFechas == 1) {
				bodyMensaje = bodyMensaje.replace(configProps.getProperty("replacePlazo"), new Long(diasEntreFechas).toString() + " " + configProps.getProperty("replaceDia"));
			} else if (diasEntreFechas == 0) {
				bodyMensaje = bodyMensaje.replace(configProps.getProperty("replacePlazo"), configProps.getProperty("hoyVenceElPlazo"));
			} else {
				bodyMensaje = bodyMensaje.replace(configProps.getProperty("replacePlazo"), configProps.getProperty("plazoVencido"));
			}
			
			for (String to : listaDeUsuariosAsignados) {
				enviarCorreo (usuario.getIdUsuario()+configProps.getProperty("dominio.correo"), to+configProps.getProperty("dominio.correo"), parametroMailSmtpHost.getValorParametroChar(), asuntoMensaje, 
						bodyMensaje, parametroMailTipoContenido.getValorParametroChar());				
			}	
			
			RespuestaMailDTO respuestaMailDTO = new RespuestaMailDTO();
			respuestaMailDTO.setRespuesta("OK");
			
			return respuestaMailDTO;
			
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);				
			throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			SgdpException ex = new SgdpException("Error al enviar email");
			throw ex;
		}
	}

	@Override
	public RespuestaMailDTO enviarMailConAsunto(Usuario usuario,
			InstanciaDeTarea instanciaDeTarea,
			List<String> listaDeUsuariosAsignados, String cuerpo,
			String asunto) throws SgdpException {
		
		log.debug("Enviando Mail");	
		log.debug(instanciaDeTarea.toString());	 			
	
		
		try {	
			
			ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
			ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
			
			for (String to : listaDeUsuariosAsignados) {
				enviarCorreo (usuario.getIdUsuario()+configProps.getProperty("dominio.correo"), to+configProps.getProperty("dominio.correo"), parametroMailSmtpHost.getValorParametroChar(), asunto, 
						cuerpo, parametroMailTipoContenido.getValorParametroChar());				
			}
			
			RespuestaMailDTO respuestaMailDTO = new RespuestaMailDTO();
			respuestaMailDTO.setRespuesta("OK");
			
			return respuestaMailDTO;
		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString(); 
			log.error(exceptionAsString);
			SgdpException ex = new SgdpException("Error al enviar email");
			throw ex;
		}
		
	}
	
	@Override
	public void enviarMailNotificacionPorTarea(InstanciaDeTareaDTO instanciaDeTareaDTO, String to, Usuario usuario) throws SgdpException {		
		try {
			log.debug(instanciaDeTareaDTO.toString());
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
			to = to + parametroSgdpMail.getValorParametroChar();
			enviarCorreo (parametroMailFrom.getValorParametroChar(), to, parametroMailSmtpHost.getValorParametroChar(), 
					asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException("Error al enviar email");
		}
	}
	
	@Override
	public void enviarCorreo(String from, String to, String mailSmtpHost, String asunto, String mensaje, String tipoDeContenido) { 
		
		log.info("Inicio enviarCorreo");
		log.info("from: " + from);	
		log.info("to: " + to);	
		log.info("mailSmtpHost: " + mailSmtpHost);	
		log.info("asunto: " + asunto);	
		log.info("mensaje: " + mensaje);
		log.info("tipoDeContenido: " + tipoDeContenido);
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
	        mimeMessage.setContent(mensaje, tipoDeContenido);
	        helper.setTo(new InternetAddress(to));	       
	        helper.setSubject(asunto);
	        helper.setFrom(new InternetAddress(from));	        
	        mailSender.send(mimeMessage);	        
			log.info("Fin enviarCorreo");		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString(); 
			log.error(exceptionAsString);
			LogError logError = new LogError();
			logError.setMessageException(exceptionAsString);
			logError.setFechaError(new Date());
			logError.setIdUsuario(from);
			logError.setNombreError(ErroresType.E_MAIL.getNombreError());
			logError.setDatosAdicionales("Asunto: " + asunto 
					+ "; tipoDeContenido: " + tipoDeContenido
					+ "; mensaje: " + mensaje
					+ "; from: " + from
					+ "; to: " + to);
			logErrorDao.insert(logError);
			log.info("Fin enviarCorreo");
		}
		
	}
	
	@Override
	public void enviarCorreosConAdjuntosAListaDeDistribucion (List<String> correosDeDistribucion,
																List<String> idArchivosADistribuir,
																String idExpediente,
																String nombreExpediente,
																long idInstanciaDeTarea,
																Usuario usuario,
																String asunto) throws Exception { 
		
		log.info("Inicio enviarCorreosConAdjuntoAListaDeDistribucion");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);		
		ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO_DISTRIBUCION);
		ParametroDTO parametroMailBodyMsgDistribucion = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_DISTRIBUCION);
		ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);		
		ParametroDTO parametroContentTypeArchRespalCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_CONTENT_TYPE_ARCHIVO_RESPALDO_CORREO_DISTRIBUCION);
		ParametroDTO parametroPrimPartNomArchRespalCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_PRIMERA_PART_NOMBRE_ARCHIVO_RESPALDO_CORREO_DISTRIBUCION);		
		ParametroDTO parametroCodImgCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_CODIGO_SRC_IMAGE_CORREO_DISTRIBUCION);
		ParametroDTO parametroIdSrcImgCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_ID_SRC_IMAGE_CORREO_DISTRIBUCION);		
		
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", parametroMailSmtpHost.getValorParametroChar());       
		Session session = Session.getInstance(properties);        
		MimeMessage message = new MimeMessage(session);      
		message.setFrom(new InternetAddress(parametroMailFrom.getValorParametroChar()));  
		for (String bccTo : correosDeDistribucion) {
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccTo));	
		}		
		message.setSubject(asunto);   
		
		Multipart multipart = new MimeMultipart();	
		
		StringBuilder listaDedocumentos = new StringBuilder();
		
		for (String idArchivo : idArchivosADistribuir) {
			BodyPart messageBodyPartArchivo = new MimeBodyPart();
			byte[] contenidoArchivo = gestorDeDocumentosService.getContenidoArchivo(idArchivo, usuario);
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			DataSource dataSource = new ByteArrayDataSource(contenidoArchivo, detalleDeArchivoDTO.getMimeType());
			DataHandler dataHandler = new DataHandler(dataSource);
			messageBodyPartArchivo.setDataHandler(dataHandler);
			messageBodyPartArchivo.setFileName(detalleDeArchivoDTO.getNombre());
	        multipart.addBodyPart(messageBodyPartArchivo);
	        listaDedocumentos.append("<li>");
	        listaDedocumentos.append(detalleDeArchivoDTO.getTipoDeDocumento());
	        if (detalleDeArchivoDTO.getNumeroDocumento()!=null && !detalleDeArchivoDTO.getNumeroDocumento().isEmpty()) {
	        	listaDedocumentos.append(" - N\u00famero: ");
	        	listaDedocumentos.append(detalleDeArchivoDTO.getNumeroDocumento());
	        	listaDedocumentos.append(" -");	        	        	
	        }
	        listaDedocumentos.append(" (");
        	listaDedocumentos.append(detalleDeArchivoDTO.getNombre());
        	listaDedocumentos.append(")");	
	        listaDedocumentos.append("</li>");
		}
		
		byte[] imgCorreoDist = gestorDeDocumentosService.getContenidoArchivo(parametroIdSrcImgCorreoDist.getValorParametroChar(), usuario);
		String imgCorreoDistBase64 = FileUtil.encodeByteArrayToBase64(imgCorreoDist, "UTF-8");
		
		String bodyContent = parametroMailBodyMsgDistribucion.getValorParametroChar()
				.replace("${listaDeDocumentos}", listaDedocumentos.toString())
				.replace("${codigoImage}", parametroCodImgCorreoDist.getValorParametroChar())
				.replace("${imageBase64}", imgCorreoDistBase64)
				.replace("${asunto}", asunto + ".");
		
		BodyPart messageBodyPartContenido = new MimeBodyPart();
		messageBodyPartContenido.setContent(bodyContent, parametroMailTipoContenido.getValorParametroChar());
		multipart.addBodyPart(messageBodyPartContenido);
		message.setContent(multipart);
		message.setSentDate(new Date());
		
		message.writeTo(out);			
		
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumento("Antecedente");		
		Date d = new Date();		
		String s = FechaUtil.getSimpleDateFormatNomArch().format(d);
		log.debug("fecha parte nombre: " + s);
		log.debug(URLEncoder.encode(parametroPrimPartNomArchRespalCorreoDist.getValorParametroChar(), "UTF-8"));		
		StringBuilder nombreArchivoRespCorreo = new StringBuilder(URLEncoder.encode(parametroPrimPartNomArchRespalCorreoDist.getValorParametroChar(), "UTF-8"));
		nombreArchivoRespCorreo.append(nombreExpediente);
		nombreArchivoRespCorreo.append(" ");
		nombreArchivoRespCorreo.append(s);
		nombreArchivoRespCorreo.append(".eml");
		log.debug("nombreArchivoRespCorreo: " + nombreArchivoRespCorreo);		
		sgdpMultipartFile.setBytes(out.toByteArray());
		sgdpMultipartFile.setContentType(parametroContentTypeArchRespalCorreoDist.getValorParametroChar());
		sgdpMultipartFile.setName(nombreArchivoRespCorreo.toString());
		sgdpMultipartFile.setOriginalFilename(nombreArchivoRespCorreo.toString());		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		subirArhivoDTO.setArchivo(sgdpMultipartFile);
		subirArhivoDTO.setIdTipoDeDocumentoSubir(tipoDeDocumento.getIdTipoDeDocumento());
		subirArhivoDTO.setIdExpedienteSubirArchivo(idExpediente);		
		subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(idInstanciaDeTarea);		
		subirArchivoService.subirArchivo(usuario, subirArhivoDTO);	
		
		Transport.send(message);   
		
	}
	
	/*@Override
	public void enviarCorreosConAdjuntosAListaDeDistribucion (List<String> correosDeDistribucion,
																List<String> idArchivosADistribuir,
																String idExpediente,
																String nombreExpediente,
																long idInstanciaDeTarea,
																Usuario usuario,
																String asunto) throws Exception { 
		
		log.info("Inicio enviarCorreosConAdjuntoAListaDeDistribucion");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
				
		ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO_DISTRIBUCION);
		ParametroDTO parametroMailBodyMsgDistribucion = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_DISTRIBUCION);
		ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);		
		ParametroDTO parametroContentTypeArchRespalCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_CONTENT_TYPE_ARCHIVO_RESPALDO_CORREO_DISTRIBUCION);
		ParametroDTO parametroPrimPartNomArchRespalCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_PRIMERA_PART_NOMBRE_ARCHIVO_RESPALDO_CORREO_DISTRIBUCION);		
		ParametroDTO parametroCodImgCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_CODIGO_SRC_IMAGE_CORREO_DISTRIBUCION);
		ParametroDTO parametroIdSrcImgCorreoDist = parametroService.getParametroPorID(Constantes.ID_PARAM_ID_SRC_IMAGE_CORREO_DISTRIBUCION);		
				
		Multipart multipart = new MimeMultipart();	
		
		StringBuilder listaDedocumentos = new StringBuilder();
		
		MimeMessage message = mailSender.createMimeMessage();		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);        
        helper.setFrom(parametroMailFrom.getValorParametroChar());        
        for (String bccTo : correosDeDistribucion) {			
			helper.addBcc(bccTo);
		}
        helper.setSubject(asunto);
		
		for (String idArchivo : idArchivosADistribuir) {
			log.debug("idArchivosADistribuir: " + idArchivo);
			BodyPart messageBodyPartArchivo = new MimeBodyPart();
			byte[] contenidoArchivo = gestorDeDocumentosService.getContenidoArchivo(idArchivo, usuario);
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			DataSource dataSource = new ByteArrayDataSource(contenidoArchivo, detalleDeArchivoDTO.getMimeType());
			DataHandler dataHandler = new DataHandler(dataSource);
			messageBodyPartArchivo.setDataHandler(dataHandler);
			messageBodyPartArchivo.setFileName(detalleDeArchivoDTO.getNombre());
	        multipart.addBodyPart(messageBodyPartArchivo);
	        listaDedocumentos.append("<li>");
	        listaDedocumentos.append(detalleDeArchivoDTO.getTipoDeDocumento());
	        if (detalleDeArchivoDTO.getNumeroDocumento()!=null && !detalleDeArchivoDTO.getNumeroDocumento().isEmpty()) {
	        	listaDedocumentos.append(" - N\u00famero: ");
	        	listaDedocumentos.append(detalleDeArchivoDTO.getNumeroDocumento());
	        	listaDedocumentos.append(" -");	        	        	
	        }
	        listaDedocumentos.append(" (");
        	listaDedocumentos.append(detalleDeArchivoDTO.getNombre());
        	listaDedocumentos.append(")");	
	        listaDedocumentos.append("</li>");
	        helper.addAttachment(detalleDeArchivoDTO.getNombre(),  new ByteArrayResource(contenidoArchivo));
		}
				
		byte[] imgCorreoDist = gestorDeDocumentosService.getContenidoArchivo(parametroIdSrcImgCorreoDist.getValorParametroChar(), usuario);
		String imgCorreoDistBase64 = FileUtil.encodeByteArrayToBase64(imgCorreoDist, "UTF-8");
		
		String bodyContent = parametroMailBodyMsgDistribucion.getValorParametroChar()
				.replace("${listaDeDocumentos}", listaDedocumentos.toString())
				.replace("${codigoImage}", parametroCodImgCorreoDist.getValorParametroChar())
				.replace("${imageBase64}", imgCorreoDistBase64)
				.replace("${asunto}", asunto + ".");
		
		message.setContent(bodyContent, parametroMailTipoContenido.getValorParametroChar());
		
		BodyPart messageBodyPartContenido = new MimeBodyPart();
		messageBodyPartContenido.setContent(bodyContent, parametroMailTipoContenido.getValorParametroChar());
		multipart.addBodyPart(messageBodyPartContenido);
		
		MimeMessage messageRespaldo = mailSender.createMimeMessage();     
		messageRespaldo.setFrom(new InternetAddress(parametroMailFrom.getValorParametroChar()));  
		for (String bccTo : correosDeDistribucion) {
			log.info(bccTo);
			messageRespaldo.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccTo));	
		}		
		messageRespaldo.setSubject(asunto); 
		messageRespaldo.setContent(multipart);
		messageRespaldo.writeTo(out);	
		
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumento("Antecedente");		
		Date d = new Date();		
		String s = FechaUtil.getSimpleDateFormatNomArch().format(d);
		log.debug("fecha parte nombre: " + s);
		log.debug(URLEncoder.encode(parametroPrimPartNomArchRespalCorreoDist.getValorParametroChar(), "UTF-8"));		
		StringBuilder nombreArchivoRespCorreo = new StringBuilder(URLEncoder.encode(parametroPrimPartNomArchRespalCorreoDist.getValorParametroChar(), "UTF-8"));
		nombreArchivoRespCorreo.append(nombreExpediente);
		nombreArchivoRespCorreo.append(" ");
		nombreArchivoRespCorreo.append(s);
		nombreArchivoRespCorreo.append(".eml");
		log.debug("nombreArchivoRespCorreo: " + nombreArchivoRespCorreo);		
		sgdpMultipartFile.setBytes(out.toByteArray());
		sgdpMultipartFile.setContentType(parametroContentTypeArchRespalCorreoDist.getValorParametroChar());
		sgdpMultipartFile.setName(nombreArchivoRespCorreo.toString());
		sgdpMultipartFile.setOriginalFilename(nombreArchivoRespCorreo.toString());		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		subirArhivoDTO.setArchivo(sgdpMultipartFile);
		subirArhivoDTO.setIdTipoDeDocumentoSubir(tipoDeDocumento.getIdTipoDeDocumento());
		subirArhivoDTO.setIdExpedienteSubirArchivo(idExpediente);		
		subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(idInstanciaDeTarea);		
		subirArchivoService.subirArchivo(usuario, subirArhivoDTO);
				
        mailSender.send(message);
        
        log.info("Fin enviarCorreosConAdjuntoAListaDeDistribucion");
		
	}*/
	
	@Override
	public void enviarCorreoeX (String from, String to, String mailSmtpHost, String asunto, String mensaje, String tipoDeContenido) throws Exception { 
		
		log.info("Inicio enviarCorreo");
		log.info("from: " + from);	
		log.info("to: " + to);	
		log.info("mailSmtpHost: " + mailSmtpHost);	
		log.info("asunto: " + asunto);	
		log.info("mensaje: " + mensaje);
		log.info("tipoDeContenido: " + tipoDeContenido);
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
	        mimeMessage.setContent(mensaje, tipoDeContenido);
	        helper.setTo(new InternetAddress(to));
	        helper.setSubject(asunto);
	        helper.setFrom(new InternetAddress(from));	        
	        mailSender.send(mimeMessage);	        
			log.info("Fin enviarCorreo");		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString(); 
			log.error(exceptionAsString);
			log.info("Fin enviarCorreo");
			throw e;
		}
		
	}
	
	@Override
	public void enviarMailNotificacionPorNumeroDeDocumento(Object o, Usuario usuario, InstanciaDeProceso instanciaDeProceso, boolean anula) throws SgdpException {		
		
		log.info("Enviando correo por numeraci\u00f3n de documento");
		
		try {
		
			String mensaje = null;
			
			ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO);		
			ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
			ParametroDTO parametroMailAsuntoNotificacion = parametroService.getParametroPorID(Constantes.ID_PARAM_NUMERO_DOC_ASUNTO_NOTIFICACION);
			ParametroDTO parametroMailBodyMsgNotificacion = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_NUMERO_DOC);
			ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
			ParametroDTO parametroSgdpMail = parametroService.getParametroPorID(Constantes.ID_PARAM_SGDP_CORREO);
			
			String asunto = parametroMailAsuntoNotificacion.getValorParametroChar();
			
			if (anula==false && o instanceof GeneraRegistroDocumentoResponse) {
				
				log.info(((GeneraRegistroDocumentoResponse)o).toString());
				
				mensaje = parametroMailBodyMsgNotificacion.getValorParametroChar().replace(configProps.getProperty("replaceNombreSubProceso"), instanciaDeProceso.getProceso().getNombreProceso())
						.replace(configProps.getProperty("replaceExpediente"), ((GeneraRegistroDocumentoResponse) o).getExpDoc())
						.replace("$asunto", instanciaDeProceso.getAsunto())
						.replace("$tipoDeDocumento", ((GeneraRegistroDocumentoResponse) o).getNombreTipoDocumento())
						.replace("$numeroDeDocumento", Long.toString(((GeneraRegistroDocumentoResponse) o).getNumeroDoc()))
						.replace("$fechaDeDocumento", ((GeneraRegistroDocumentoResponse) o).getFechaDocS())
						.replace("${tipoDeMovimiento}", "numeraci\u00f3n")
						;
				
			} else if (anula==true && o instanceof BorraRegistroDocumentoResponse) {
				
				log.info(((BorraRegistroDocumentoResponse)o).toString());
				
				mensaje = parametroMailBodyMsgNotificacion.getValorParametroChar().replace(configProps.getProperty("replaceNombreSubProceso"), instanciaDeProceso.getProceso().getNombreProceso())
						.replace(configProps.getProperty("replaceExpediente"), ((BorraRegistroDocumentoResponse) o).getExpDoc())
						.replace("$asunto", instanciaDeProceso.getAsunto())
						.replace("$tipoDeDocumento", ((BorraRegistroDocumentoResponse) o).getNombreTipoDocumento())
						.replace("$numeroDeDocumento", Long.toString(((BorraRegistroDocumentoResponse) o).getNumeroDoc()))
						.replace("<li>Fecha de documento: $fechaDeDocumento</li>", "")
						.replace("${tipoDeMovimiento}",  "anulaci\u00f3n")
						;
			}
						
			List<String> usuariosOP = usuarioRolDao.getTodosLosIdUsuariosPorNombreRol(RolType.OFICINA_DE_PARTES.getNombreRol());			

			StringBuilder s = new StringBuilder();
			int i = 1;
			
			for (String to : usuariosOP) {
				to = to + parametroSgdpMail.getValorParametroChar();
				s.append(to);
				if (i<usuariosOP.size()) {
					s.append(",");
				}
				i++;				
			}	
			
			String[] t = s.toString().split(",");
			
			enviarCorreo (parametroMailFrom.getValorParametroChar(), t, parametroMailSmtpHost.getValorParametroChar(), 
					asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
			
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException("Error al enviar email");
		}
	}
	
	private void enviarCorreo (String from, String[] to, String mailSmtpHost, String asunto, String mensaje, String tipoDeContenido) { 
		
		log.info("Inicio enviarCorreo");
		log.info("from: " + from);	
		log.info("to: " + to);	
		log.info("mailSmtpHost: " + mailSmtpHost);	
		log.info("asunto: " + asunto);	
		log.info("mensaje: " + mensaje);
		log.info("tipoDeContenido: " + tipoDeContenido);
		
		try {			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
	        mimeMessage.setContent(mensaje, tipoDeContenido);	       
	        helper.setTo(to);	       
	        helper.setSubject(asunto);
	        helper.setFrom(new InternetAddress(from));	        
	        mailSender.send(mimeMessage);	        
			log.info("Fin enviarCorreo");			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString(); 
			log.error(exceptionAsString);
			LogError logError = new LogError();
			logError.setMessageException(exceptionAsString);
			logError.setFechaError(new Date());
			logError.setIdUsuario(from);
			logError.setNombreError(ErroresType.E_MAIL.getNombreError());
			logError.setDatosAdicionales("Asunto: " + asunto 
					+ "; tipoDeContenido: " + tipoDeContenido
					+ "; mensaje: " + mensaje
					+ "; from: " + from
					+ "; to: " + to);
			logErrorDao.insert(logError);
			log.info("Fin enviarCorreo");
		}

	}

}
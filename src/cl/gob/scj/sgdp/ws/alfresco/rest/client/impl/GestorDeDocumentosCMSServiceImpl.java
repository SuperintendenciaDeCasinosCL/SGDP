package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaConversionArchivoDTO;
import cl.gob.scj.sgdp.dto.RespuestaSimpleDTO;
import cl.gob.scj.sgdp.dto.VersionArchivoDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorDeDocumentosCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ArchivoImagenQRPorUsuarioResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.FirmaSimpleDocumentoResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.IdArchivoPorIdUsrNomCarpetaResponse;

@Service
public class GestorDeDocumentosCMSServiceImpl implements GestorDeDocumentosCMSService {

	private static final Logger log = Logger.getLogger(GestorDeDocumentosCMSServiceImpl.class);
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public FirmaSimpleDocumentoResponse firmaSimpleDocumento(String idDocumento, Usuario usuario) throws Exception {
		
		log.debug("firmaSimpleDocumento... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_FIRMA_SIMPLE);
		String serviceRestURLFirmaSimpleDocumento = parametroDTO.getValorParametroChar();
		
		log.debug("serviceRestURLFirmaSimpleDocumento: " + serviceRestURLFirmaSimpleDocumento);
		
		StringBuilder idDocumentoSB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		idDocumentoSB.append(idDocumento);
		log.debug("idDocumentoSB.toString(): " + idDocumentoSB.toString());
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
		
		//parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_DOCUMENTO_FIRMA_SIMPLE, idDocumentoSB.toString());
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_INICIALES_FIRMA_SIMPLE, usuario.getIdUsuario());
		
		String requestJson = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			FirmaSimpleDocumentoResponse firmaSimpleDocumentoResponse = restTemplate.postForObject(serviceRestURLFirmaSimpleDocumento, 
					entity, FirmaSimpleDocumentoResponse.class, parametrosURL);
			log.debug("firmaSimpleDocumento... fin");	
			return firmaSimpleDocumentoResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase()  );
			log.error(e);
			log.error("firmaSimpleDocumento... fin");	
			throw e;
		}	
		
	}
	
	@Override
	public byte[] getContenidoArchivo(String idArchivo, Usuario usuario) throws Exception {
		
		log.debug("getContenidoArchivo... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_GET_CONTENT);
		String serviceRestURLObtenerContenido = parametroDTO.getValorParametroChar();
		
		log.debug(serviceRestURLObtenerContenido);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_ARCHIVO, idArchivo);
		//parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			byte[] contenidoArchivo = restTemplate.getForObject(serviceRestURLObtenerContenido, byte[].class, parametrosURL);
			return contenidoArchivo;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("getContenidoArchivo... fin");	
			throw e;
		}		
	}
	
	@Override
	public byte[] getContenidoArchivoDesdeUrlYVersion(DetalleDeArchivoDTO detalleDeArchivoDTO, String version, Usuario usuario) throws Exception {
		
		log.debug("getContenidoArchivoDesdeUrl... inicio");
		String linkAversion;
		byte[] contenidoArchivo = null;
		try {
			for (VersionArchivoDTO versionArchivoDTO : detalleDeArchivoDTO.getVersiones()) { 
				if (versionArchivoDTO.getVersionLabel().equals(version)) {
					linkAversion = versionArchivoDTO.getLinkAversion()+"?ticket="+usuario.getAlfTicket();
					log.info("linkAversion: " + linkAversion);
					contenidoArchivo = FileUtil.descargaArchivoDesdeUrl(linkAversion, 10000, 10000);					
				}
			}	
			return contenidoArchivo;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("getContenidoArchivoDesdeUrl... fin");	
			throw e;
		}		
	}

	@Override
	public ArchivoImagenQRPorUsuarioResponse getArchivoImagenQRPorUsuario(Usuario usuario) throws Exception {
		
		log.debug("getArchivoImagenQRPorUsuario... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_GET_ID_DOC_IMAGEN_QR);
		String serviceRestURLGetArchivoImagenQRPorUsuario = parametroDTO.getValorParametroChar();
		
		log.debug(serviceRestURLGetArchivoImagenQRPorUsuario);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_USUARIO, usuario.getIdUsuario());
		//parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
		
		log.debug("usuario.getIdUsuario(): " + usuario.getIdUsuario());
		log.debug("usuario.getAlfTicket(): " + usuario.getAlfTicket());
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			ArchivoImagenQRPorUsuarioResponse archivoImagenQRPorUsuarioResponse = restTemplate.getForObject(serviceRestURLGetArchivoImagenQRPorUsuario, 
					ArchivoImagenQRPorUsuarioResponse.class, parametrosURL);
			return archivoImagenQRPorUsuarioResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("getArchivoImagenQRPorUsuario... fin");
			throw e;
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	@Override
	public RespuestaSimpleDTO actualizaMetaDataDeDocumento(Usuario usuario, DetalleDeArchivoDTO detalleDeArchivoDTO) throws Exception {
		
		log.debug("actualizaMetaDataDeDocumento... inicio");
		log.info(detalleDeArchivoDTO.toString());
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_ACTUALIZA_METADATA_DE_DOCUMENTO);
		StringBuilder serviceRestURLActualizaMetadataDeDocumento = new StringBuilder(parametroDTO.getValorParametroChar());
		
		serviceRestURLActualizaMetadataDeDocumento.append("?alf_ticket=");
		
		StringBuilder idArchivoSB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		idArchivoSB.append(detalleDeArchivoDTO.getIdArchivo());
		log.debug("idArchivoSB.toString(): " + idArchivoSB.toString());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		
		mvm.add("idArchivo", idArchivoSB.toString());
		if (detalleDeArchivoDTO.getCdr()!=null) {
			mvm.add("cdr", detalleDeArchivoDTO.getCdr());
		}
		if (detalleDeArchivoDTO.getNumeroDocumento()!=null) {
			mvm.add("numeroDeDocumento", detalleDeArchivoDTO.getNumeroDocumento());
		}
		if (detalleDeArchivoDTO.getTipoDeDocumento()!=null) {
			mvm.add("tipoDeDocumento", detalleDeArchivoDTO.getTipoDeDocumento());
		}
		
		if (detalleDeArchivoDTO.getCartaRelacionada()!=null) {
			mvm.add("cartaRelacionada", detalleDeArchivoDTO.getCartaRelacionada());
		}
		//mvm.add("cartaRelacionada", detalleDeArchivoDTO.getCartaRelacionada() !=null ? detalleDeArchivoDTO.getCartaRelacionada() : "" ); 
		if (detalleDeArchivoDTO.getEmisor()!=null) {
			mvm.add("emisor", detalleDeArchivoDTO.getEmisor());
		}	
		if (detalleDeArchivoDTO.getNumeroDeFirma()!=null) {
			mvm.add("numeroDeFirma", detalleDeArchivoDTO.getNumeroDeFirma());
		}
		
		if (detalleDeArchivoDTO.getFechaDeCreacion()!=null) {
			log.debug(FechaUtil.toFormatCMS(detalleDeArchivoDTO.getFechaDeCreacion()));		
			mvm.add("fechaDeCreacion", FechaUtil.toFormatCMS(detalleDeArchivoDTO.getFechaDeCreacion()));
		}
		if (detalleDeArchivoDTO.getFechaDeRecepcion()!=null) {
			log.debug(FechaUtil.toFormatCMS(detalleDeArchivoDTO.getFechaDeRecepcion()));		
			mvm.add("fechaDeRecepcion", FechaUtil.toFormatCMS(detalleDeArchivoDTO.getFechaDeRecepcion()));
		}		
		
		if (detalleDeArchivoDTO.getFirmaSimple()!=null) {
			mvm.add("firmaSimple", detalleDeArchivoDTO.getFirmaSimple());
		}
		if (detalleDeArchivoDTO.getEsDocumentoOficial()!=null) {
			mvm.add("esDocumentoOficial", detalleDeArchivoDTO.getEsDocumentoOficial());
		}
		if (detalleDeArchivoDTO.getCategoriaDocumento()!=null && !detalleDeArchivoDTO.getCategoriaDocumento().isEmpty()) {
			mvm.add("categoriaDocumento", URLEncoder.encode(detalleDeArchivoDTO.getCategoriaDocumento(), "UTF-8"));
		}		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
		
		try {
			autenticacionService.validaSesion(usuario);
			serviceRestURLActualizaMetadataDeDocumento.append(usuario.getAlfTicket());
			log.debug("serviceRestURLActualizaMetadataDeDocumento.toString(): " + serviceRestURLActualizaMetadataDeDocumento.toString());
			ResponseEntity<RespuestaSimpleDTO> respuestaSimpleDTO = restTemplate.postForEntity(serviceRestURLActualizaMetadataDeDocumento.toString(), request , RespuestaSimpleDTO.class );
			log.debug(respuestaSimpleDTO);			
			log.debug("actualizaMetaDataDeDocumento... fin");	
			return respuestaSimpleDTO.getBody();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.error("actualizaMetaDataDeDocumento... fin");	
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public RespuestaConversionArchivoDTO convertirArchivoAPDF(Usuario usuario, String idArchivo) throws Exception {
		
		log.debug("convertirArchivoAPDF... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_CONVERTIR_ARCHIVO);
		String serviceRestURLConvertirArchivoAPDF = parametroDTO.getValorParametroChar();
		
		log.debug(serviceRestURLConvertirArchivoAPDF);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
		
		StringBuilder idArchivoSB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		idArchivoSB.append(idArchivo);
		log.debug("idArchivoSB.toString(): " + idArchivoSB.toString());
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_ARCHIVO, idArchivoSB.toString());
		
		String requestJson = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			RespuestaConversionArchivoDTO respuestaConversionArchivoDTO = restTemplate.postForObject(serviceRestURLConvertirArchivoAPDF, entity, 
					RespuestaConversionArchivoDTO.class, parametrosURL);
			return respuestaConversionArchivoDTO;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("convertirArchivoAPDF... fin");
			throw e;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public byte[] convertirArchivoAPDF(Usuario usuario, byte[] byteArchivoAConvertir) {
		log.debug("convertirArchivoAPDF... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		String serviceRestURLConvertirArchivo = parametroService.getParametroPorID(Constantes.ID_PARAM_NET_REST_URL_CONVERTIR_ARCHIVO).getValorParametroChar();
		log.debug("serviceRestURLConvertirArchivo: " + serviceRestURLConvertirArchivo);
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		
		final byte[] rawBytes = byteArchivoAConvertir.clone();
		
		ByteArrayResource bar = new ByteArrayResource(rawBytes) {
	        @Override
	        public String getFilename() {
	            return "Test-"+rawBytes.length + ".pdf";	            
	        }
	    };
	    
		mvm.add("file", bar);
		
		try {
			byte[] byteArchivoConvertido = restTemplate.postForObject(serviceRestURLConvertirArchivo, mvm, byte[].class);
			log.debug("convertirArchivoAPDF... fin");	
			return byteArchivoConvertido;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.debug("convertirArchivoAPDF... fin");
			throw e;
		}
	}
	
	@Override
	public IdArchivoPorIdUsrNomCarpetaResponse getIdArchivoPorIdUsrNomCarpeta(Usuario usuario, String nombreCarpeta) throws Exception {
		
		log.debug("getIdArchivoPorIdUsrNomCarpeta... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_GET_ID_DOC_POR_USER_NOM_CARP);
		String serviceRestURLGetIdArchivoPorIdUsrNomCarpetaResponse = parametroDTO.getValorParametroChar();
		
		log.debug("serviceRestURLGetIdArchivoPorIdUsrNomCarpetaResponse: " + serviceRestURLGetIdArchivoPorIdUsrNomCarpetaResponse);
		log.debug("nombreCarpeta: " + nombreCarpeta);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_USUARIO, usuario.getIdUsuario());
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_NOMBRE_CARPETA, nombreCarpeta);
		
		log.debug("usuario.getIdUsuario(): " + usuario.getIdUsuario());
		log.debug("usuario.getAlfTicket(): " + usuario.getAlfTicket());
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			IdArchivoPorIdUsrNomCarpetaResponse idArchivoPorIdUsrNomCarpetaResponse = restTemplate.getForObject(serviceRestURLGetIdArchivoPorIdUsrNomCarpetaResponse, 
					IdArchivoPorIdUsrNomCarpetaResponse.class, parametrosURL);
			return idArchivoPorIdUsrNomCarpetaResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("getIdArchivoPorIdUsrNomCarpeta... fin");
			throw e;
		} catch (Exception e) {
			throw e;
		}
	
	}
	

}

package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerDetalleDeArchivoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.DetalleDeArchivoResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ObtenerArchivosExpedienteResponse;

@Service
public class ObtenerDetalleDeArchivoCMSServiceImpl implements ObtenerDetalleDeArchivoCMSService {

	private static final Logger log = Logger.getLogger(ObtenerDetalleDeArchivoCMSServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public DetalleDeArchivoResponse obtenerDetalleDeArchivo(Usuario usuario,
			String idArchivo) throws Exception {		
		
		log.debug("obtenerDetalleDeArchivo... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_OBTENER_DETALLE_DE_ARCHIVO);
		String serviceRestURLObtenerDetalleDeArchivo = parametroDTO.getValorParametroChar();
		
		//Se carga con el prefijo
		StringBuilder idArchivoSB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		idArchivoSB.append(idArchivo);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_ARCHIVO, idArchivoSB.toString());
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			DetalleDeArchivoResponse detalleDeArchivoResponse = restTemplate.getForObject(serviceRestURLObtenerDetalleDeArchivo, DetalleDeArchivoResponse.class, parametrosURL);
			log.debug("obtenerDetalleDeArchivo... fin");	
			return detalleDeArchivoResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("obtenerDetalleDeArchivo... fin");	
			throw e;
		}		

	}
	
}

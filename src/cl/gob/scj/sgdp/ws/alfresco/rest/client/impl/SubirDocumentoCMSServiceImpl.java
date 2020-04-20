package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.SubirDocumentoDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.SubirDocumentoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.SubirArchivoResponse;

@Service
public class SubirDocumentoCMSServiceImpl implements SubirDocumentoCMSService {
	
	private static final Logger log = Logger.getLogger(SubirDocumentoCMSServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private AutorDao autorDao;	
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public String subirDocumento(Usuario usuario,
			SubirDocumentoDTO subirDocumentoDTO) throws Exception {
		
		log.debug("subirDocumentoDTO... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		log.debug(subirDocumentoDTO.toString());
		
		StringBuilder serviceRestURLSubirArchivo = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_SUBIR_ARCHIVO).getValorParametroChar());
		
		//Se carga con el prefijo 
		StringBuilder idExpediente = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		
		idExpediente.append(subirDocumentoDTO.getIdExpedienteSubirDocumento());
		
		log.debug(idExpediente.toString());
		
		serviceRestURLSubirArchivo.append("?alf_ticket=");
		
		subirDocumentoDTO.setNombreDocumentoConductorSalida(subirDocumentoDTO.getDocumento().getOriginalFilename());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		mvm.add("idExpediente", idExpediente.toString());
		mvm.add("nombreDeArchivo", subirDocumentoDTO.getDocumento().getOriginalFilename());
		mvm.add("cdr", subirDocumentoDTO.getCdr());
		mvm.add("numeroDeDocumento", subirDocumentoDTO.getNumeroDocumento());	
		
		if (subirDocumentoDTO.getTipoDeDocumentoSubirExpediente()!=null && !subirDocumentoDTO.getTipoDeDocumentoSubirExpediente().isEmpty()
				&& !subirDocumentoDTO.getTipoDeDocumentoSubirExpediente().equals("null")) {
			mvm.add("tipoDeDocumento", subirDocumentoDTO.getTipoDeDocumentoSubirExpediente());
		} else {
			mvm.add("tipoDeDocumento", tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirDocumentoDTO.getIdTipoDeDocumentoSubir()).getNombreDeTipoDeDocumento());			
		}
		
		//String tipoDeDocumento  = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirDocumentoDTO.getIdTipoDeDocumentoSubir()).getNombreDeTipoDeDocumento();
		//log.debug("tipoDeDocumento: " + tipoDeDocumento);		
		//mvm.add("tipoDeDocumento", tipoDeDocumento);
		
		log.debug(FechaUtil.toFormatCMS(subirDocumentoDTO.getFechaDeCreacionDocumento()));
		mvm.add("fechaDeCreacion", FechaUtil.toFormatCMS(subirDocumentoDTO.getFechaDeCreacionDocumento()));
		// Falta poner Fecha de recepcion		

		if (!subirDocumentoDTO.getFechaRecepcionDocumento().equals("")){
			 mvm.add("fechaDeRecepcion", FechaUtil.toFormatCMS(subirDocumentoDTO.getFechaRecepcionDocumento()));
		}
	
		
		mvm.add("emisor", autorDao.getAutorPorIdAutor(subirDocumentoDTO.getIdAutorSubirDocumento()).getNombreAutor());
		mvm.add("desc", subirDocumentoDTO.getDescripcion());
		mvm.add("cartaRelacionada", "");
		mvm.add("otro", subirDocumentoDTO.getOtro());
		mvm.add("mimetype", subirDocumentoDTO.getDocumento().getContentType()); 
		
		
		// Se agrega el campo documento oficial
		 mvm.add("esDocumentoOficial", subirDocumentoDTO.getEsDocumentoOficial()+"");
		
		
		
		final byte[] rawBytes = subirDocumentoDTO.getDocumento().getBytes().clone();
		
		ByteArrayResource bar = new ByteArrayResource(rawBytes) {
	        @Override
	        public String getFilename() {
	            return "Test-"+rawBytes.length + ".pdf";	            
	        }
	    };
	    
		mvm.add("file", bar);
		
		try {
			autenticacionService.validaSesion(usuario);
			serviceRestURLSubirArchivo.append(usuario.getAlfTicket());			
			log.debug("serviceRestURLSubirArchivo.toString(): " + serviceRestURLSubirArchivo.toString());	
			//String respuestaCargaArchivo = restTemplate.postForObject(serviceRestURLSubirArchivo.toString(), mvm, String.class);	
			SubirArchivoResponse subirArchivoResponse =  restTemplate.postForObject(serviceRestURLSubirArchivo.toString(), mvm, SubirArchivoResponse.class);	
			//log.debug("respuestaCargaArchivo: " + respuestaCargaArchivo);
			log.debug("subirArchivoResponse.getIdArchivo(: " + subirArchivoResponse.getIdArchivo());
			subirAntecedentes(usuario, subirDocumentoDTO, serviceRestURLSubirArchivo.toString(), idExpediente.toString());
			log.debug("subirCarta... fin");	
			return subirArchivoResponse.getIdArchivo();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);
			log.error("subirCarta... fin");	
			throw e;
		}		
		
	}
	
	private void subirAntecedentes(Usuario usuario,
			SubirDocumentoDTO subirDocumentoDTO, String serviceRestURLSubirArchivo, String idExpediente) throws Exception {
		
		log.debug("subirAntecedentes... inicio");
		
		try {
			log.debug("subirDocumentoDTO.getAntecedentes(): " + subirDocumentoDTO.getAntecedentes().size());
		} catch (Exception e1) {
			log.debug("No hay antecedentes");
			return;
		}
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		
		if (
				subirDocumentoDTO.getAntecedentes().size()==1  
				&& 
				( subirDocumentoDTO.getAntecedentes().get(0).getOriginalFilename()!= null
				&& subirDocumentoDTO.getAntecedentes().get(0).getOriginalFilename().isEmpty() )
				|| (subirDocumentoDTO.getAntecedentes().get(0).getOriginalFilename() == null)
			) {
			log.debug("No hay antecedentes");
			return;
		}
		
		for (MultipartFile multipartFile: subirDocumentoDTO.getAntecedentes()) {
			mvm.add("idExpediente", idExpediente);
			mvm.add("nombreDeArchivo", multipartFile.getOriginalFilename());	
			mvm.add("tipoDeDocumento", subirDocumentoDTO.getNombreTipoDocumentoAdjuntos());
			log.debug(FechaUtil.toFormatCMS(subirDocumentoDTO.getFechaDeCreacionDocumento()));
			mvm.add("fechaDeCreacion", FechaUtil.toFormatCMS(subirDocumentoDTO.getFechaDeCreacionDocumento()));		
			mvm.add("emisor", autorDao.getAutorPorIdAutor(subirDocumentoDTO.getIdAutorSubirDocumento()).getNombreAutor());
			mvm.add("desc", subirDocumentoDTO.getDescripcion());
			mvm.add("cartaRelacionada", subirDocumentoDTO.getDocumento().getOriginalFilename());			
			mvm.add("mimetype", multipartFile.getContentType()); 
			
			final byte[] rawBytes = multipartFile.getBytes().clone();
			
			ByteArrayResource bar = new ByteArrayResource(rawBytes) {
		        @Override
		        public String getFilename() {
		            return "Test-"+rawBytes.length + ".pdf";	            
		        }
		    };
		    
			mvm.add("file", bar);
			
			try {			
				String respuestaCargaArchivo = restTemplate.postForObject(serviceRestURLSubirArchivo, mvm, String.class);				
				log.debug("respuestaCargaArchivo: " + respuestaCargaArchivo);				
				log.debug("subirAntecedentes... fin");					
			} catch (HttpClientErrorException e) {
				HttpStatus status = e.getStatusCode();
				log.error(status.getReasonPhrase() + status.value());
				log.error(e);
				log.error("subirAntecedentes... fin");	
				throw e;
			}
			
		}
		
	}
	
	
}

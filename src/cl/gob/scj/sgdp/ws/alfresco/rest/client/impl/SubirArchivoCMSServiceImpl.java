package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.SubirArchivoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.SubirArchivoResponse;

@Service
public class SubirArchivoCMSServiceImpl implements SubirArchivoCMSService {

	private static final Logger log = Logger.getLogger(SubirArchivoCMSServiceImpl.class);
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private AutorDao autorDao;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public SubirArchivoResponse subirArchivo(Usuario usuario, SubirArhivoDTO subirArhivoDTO)
			throws Exception {
		
		log.debug("Subir archivo... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		log.debug(subirArhivoDTO.toString());
		
		StringBuilder serviceRestURLSubirArchivo = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_SUBIR_ARCHIVO).getValorParametroChar());
		
		//Se carga con el prefijo 
		StringBuilder idExpediente = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		
		idExpediente.append(subirArhivoDTO.getIdExpedienteSubirArchivo());
		
		log.debug("idExpediente: " + idExpediente.toString());
		
		serviceRestURLSubirArchivo.append("?alf_ticket=");
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		mvm.add("idExpediente", idExpediente.toString());
		mvm.add("nombreDeArchivo", subirArhivoDTO.getArchivo().getOriginalFilename());
		mvm.add("cdr", subirArhivoDTO.getCdr());
		mvm.add("numeroDeDocumento", subirArhivoDTO.getNumeroDocumento());
		
		if (subirArhivoDTO.getNumeroDocumento()!=null && !subirArhivoDTO.getNumeroDocumento().isEmpty() && !subirArhivoDTO.getNumeroDocumento().equals(null) && subirArhivoDTO.getAsignarnumerodocumento() == true ){ 
			mvm.add("numeroDeFirma", subirArhivoDTO.getNumeroDocumento());
		}
		
		if (subirArhivoDTO.getTipoDeDocumento()!=null && !subirArhivoDTO.getTipoDeDocumento().isEmpty()
				&& !subirArhivoDTO.getTipoDeDocumento().equals("null")) {
			mvm.add("tipoDeDocumento", subirArhivoDTO.getTipoDeDocumento());
		} else if (subirArhivoDTO.getIdTipoDeDocumentoSubir() == 0L) {
			mvm.add("tipoDeDocumento", "");
		} else {
			mvm.add("tipoDeDocumento", tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir()).getNombreDeTipoDeDocumento());			
		}
		
		if (subirArhivoDTO.getFechaCreacionArchivo() == null || subirArhivoDTO.getFechaCreacionArchivo().isEmpty()) {
			log.debug("FechaUtil.simpleDateFormatForm.format(new Date()): " + FechaUtil.simpleDateFormatForm.format(new Date()));
			mvm.add("fechaDeCreacion", FechaUtil.toFormatCMS(FechaUtil.simpleDateFormatForm.format(new Date())));
		} else {
			log.debug(FechaUtil.toFormatCMS(subirArhivoDTO.getFechaCreacionArchivo()));		
			mvm.add("fechaDeCreacion", FechaUtil.toFormatCMS(subirArhivoDTO.getFechaCreacionArchivo()));
		}	
		
		if (subirArhivoDTO.getFechaRecepcionArchivo() == null || subirArhivoDTO.getFechaRecepcionArchivo().isEmpty()) {
			log.debug("FechaUtil.simpleDateFormatForm.format(new Date()): " + FechaUtil.simpleDateFormatForm.format(new Date()));
			mvm.add("fechaDeRecepcion", FechaUtil.toFormatCMS(FechaUtil.simpleDateFormatForm.format(new Date())));
		} else {
			log.debug(FechaUtil.toFormatCMS(subirArhivoDTO.getFechaRecepcionArchivo()));		
			mvm.add("fechaDeRecepcion", FechaUtil.toFormatCMS(subirArhivoDTO.getFechaRecepcionArchivo()));
		}
		
		if (subirArhivoDTO.getEmisor()!=null && !subirArhivoDTO.getEmisor().isEmpty()
				&& !subirArhivoDTO.getEmisor().equals("null")) {
			mvm.add("emisor", subirArhivoDTO.getEmisor());
		} else if (subirArhivoDTO.getIdAutorSubirDocumento() == 0L) {
			mvm.add("emisor", "");
		} else {
			mvm.add("emisor", autorDao.getAutorPorIdAutor(subirArhivoDTO.getIdAutorSubirDocumento()).getNombreAutor());
		}		
		
		mvm.add("desc", subirArhivoDTO.getDescripcion());
		
		if (subirArhivoDTO.getCartaRelacionada()!=null && !subirArhivoDTO.getCartaRelacionada().isEmpty()
				&& !subirArhivoDTO.getCartaRelacionada().equals("null")) {
			mvm.add("cartaRelacionada", subirArhivoDTO.getCartaRelacionada());
		} else {
			mvm.add("cartaRelacionada", "");
		}	
		
		if (subirArhivoDTO.getCategoriaDocumento()!=null && !subirArhivoDTO.getCategoriaDocumento().isEmpty()
				&& !subirArhivoDTO.getCategoriaDocumento().equals("null")) {
			mvm.add("categoriaDocumento", URLEncoder.encode(subirArhivoDTO.getCategoriaDocumento(), "UTF-8"));
		}		
		
		// Separar el String por comas 
        //  String listaTag = String.join(",", subirArhivoDTO.getIdTags());
		
		StringBuilder listaTag = new StringBuilder();
		int c = 0;
		
        if (subirArhivoDTO.getIdTags() != null && subirArhivoDTO.getIdTags().size()>0){        	
        	
	    	for (String s : subirArhivoDTO.getIdTags()) {	    		
	    		listaTag.append(s);
	    		if (c < subirArhivoDTO.getIdTags().size()-1) {
	    			listaTag.append(",");
	    		}
	    		c++;
	    	}
	    	
	    	mvm.add("tag", URLEncoder.encode(listaTag.toString()));
	    	
        }
				
		mvm.add("otro", subirArhivoDTO.getOtro());
		mvm.add("mimetype", subirArhivoDTO.getArchivo().getContentType()); 
		
		
		// Se agrega el campo documento oficial
		if (subirArhivoDTO.getEsDocumentoOficial()!=false) {
			mvm.add("esDocumentoOficial", subirArhivoDTO.getEsDocumentoOficial()+"");
		}	
		////		 
		
		if (subirArhivoDTO.getArchivo()!=null) {
			log.info("subirArhivoDTO.getArchivo().getContentType(): " + subirArhivoDTO.getArchivo().getContentType());
		}
		
		/*final byte[] rawBytes = subirArhivoDTO.getArchivo().getBytes().clone();*/
		
		ByteArrayResource bar = new ByteArrayResource(subirArhivoDTO.getArchivo().getBytes()/*rawBytes*/) {
	        @Override
	        public String getFilename() {
	            //return "Test-"+rawBytes.length + ".pdf";
	            return "Test.pdf";
	        }
	    };
	    
		mvm.add("file", bar);
		
		try {
			autenticacionService.validaSesion(usuario);
			serviceRestURLSubirArchivo.append(usuario.getAlfTicket());			
			log.debug("serviceRestURLSubirArchivo.toString(): " + serviceRestURLSubirArchivo.toString());
			SubirArchivoResponse subirArchivoResponse = restTemplate.postForObject(serviceRestURLSubirArchivo.toString(), mvm, SubirArchivoResponse.class);	
			log.debug(subirArchivoResponse.toString());			
			log.debug("Subir archivo... fin");	
			return subirArchivoResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.error("Subir archivo... fin");	
			throw e;
		}		
	}

}
package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import cl.gob.scj.sgdp.dto.BuscarConFiltroDTO;
import cl.gob.scj.sgdp.dto.BuscarDTO;
import cl.gob.scj.sgdp.dto.CargaFacetDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaFacetDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.DataTableRequestDTO;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.BusquedaCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ResultadoBusquedaResponse;

@Service
public class BusquedaCMSServiceImpl implements BusquedaCMSService {

	private static final Logger log = Logger.getLogger(BusquedaCMSServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public ResultadoBusquedaResponse buscar(BuscarDTO buscarDTO, Usuario usuario) throws Exception {
		log.debug("buscar... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_BUSCAR);
		StringBuilder serviceRestURLBuscar = new StringBuilder(parametroDTO.getValorParametroChar());
		
		log.debug("serviceRestURLBuscar: " + serviceRestURLBuscar);
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_TIPO_DE_OBJETO, buscarDTO.getTipoDeObjetoParaBuscar());	
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_PALABRA_CLAVE, buscarDTO.getPalabraClave());		
		
		// Se agrega el parametro del nombre del tipo de documento
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_NOMBRE_TIPO_DOCUMENTO, buscarDTO.getNombreTipoDocumento());		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_NOMBRE_SUBPROCESO_VIGENTE, buscarDTO.getNombreSubprocesoVigente());			
		
		
		//parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
		
		if (buscarDTO.getFechaInicio()!=null && !buscarDTO.getFechaInicio().isEmpty()) {
			serviceRestURLBuscar.append("&");
			serviceRestURLBuscar.append(Constantes.NOMBRE_PARAMETRO_FECHA_INICIO);
			serviceRestURLBuscar.append("=");
			serviceRestURLBuscar.append(buscarDTO.getFechaInicio());
		}	
		
		if ( buscarDTO.getFechaFin()!=null && !buscarDTO.getFechaFin().isEmpty()) {
			serviceRestURLBuscar.append("&");
			serviceRestURLBuscar.append(Constantes.NOMBRE_PARAMETRO_FECHA_FIN);
			serviceRestURLBuscar.append("=");
			serviceRestURLBuscar.append(buscarDTO.getFechaFin());
			
		}	
		
		log.debug("serviceRestURLBuscar.toString(): " + serviceRestURLBuscar.toString());		
		
		try {
			autenticacionService.validaSesion(usuario);

			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			ResultadoBusquedaResponse resultadoBusquedaResponse = restTemplate.getForObject(serviceRestURLBuscar.toString(), ResultadoBusquedaResponse.class, parametrosURL);
			log.debug(resultadoBusquedaResponse.toString());
			return resultadoBusquedaResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("buscar... fin");	
			throw e;
		}	
	}

	/*
	@Override
	public ResultadoBusquedaResponse buscarConFiltro(BuscarConFiltroDTO buscarConFiltroDTO, Usuario usuario){
		
	log.debug("buscarConFiltro... inicio");
	
	RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
	
	ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_BUSCAR_FILTRO_TABLA);
	StringBuilder serviceRestURLBuscarConFiltro = new StringBuilder(parametroDTO.getValorParametroChar());
	
	log.debug("serviceRestURLbuscarConFiltro: " + serviceRestURLBuscarConFiltro);
	Map<String, String> parametrosURL = new HashMap<String, String>();

	
	parametrosURL.put(Constantes.NOMBRE_PARAMETRO_NOMBRE_FILTRO, buscarConFiltroDTO.getNombreFiltro());	
	parametrosURL.put(Constantes.NOMBRE_PARAMETRO_TIPO_FILTRO, buscarConFiltroDTO.getTipoFiltro().toString());		
	

	log.debug("serviceRestURLBuscar.toString(): " + serviceRestURLBuscarConFiltro.toString());		
	
	
	try {
		autenticacionService.validaSesion(usuario);

		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
		ResultadoBusquedaResponse resultadoBusquedaResponse = restTemplate.getForObject(serviceRestURLBuscarConFiltro.toString(), ResultadoBusquedaResponse.class, parametrosURL);
		log.debug(resultadoBusquedaResponse.toString());
		return resultadoBusquedaResponse;
	} catch (HttpClientErrorException e) {
		HttpStatus status = e.getStatusCode();
		log.error(status.value() + " " + status.getReasonPhrase());
		log.error(e);
		log.error("buscar... fin");	
		throw e;
	}	

	}
	*/
	
	@Override
	public ResultadoBusquedaResponse buscarConFiltro(BuscarConFiltroDTO buscarConFiltroDTO, Usuario usuario) throws Exception{
		
	log.debug("buscarConFiltro... inicio");
	
	RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
	
	ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_BUSCA_REGISTROS_PAGINADOS);
	StringBuilder serviceRestURLBuscarConFiltro = new StringBuilder(parametroDTO.getValorParametroChar());
	
	log.debug("serviceRestURLbuscarConFiltro: " + serviceRestURLBuscarConFiltro);
	//Map<String, String> parametrosURL = new HashMap<String, String>();

	LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();

	mvm.add("nombreFiltro", buscarConFiltroDTO.getNombreFiltro());
	mvm.add("tipoFiltro", buscarConFiltroDTO.getTipoFiltro());
	mvm.add("flagTipoBusqueda", buscarConFiltroDTO.getFlagTipoBusqueda());

	
	HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
	
	
	

	log.debug("serviceRestURLBuscar.toString(): " + serviceRestURLBuscarConFiltro.toString());		
	
	
	try {
		autenticacionService.validaSesion(usuario);
		// parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
		
		serviceRestURLBuscarConFiltro.append("?alf_ticket=");
		serviceRestURLBuscarConFiltro.append(usuario.getAlfTicket());			
		ResponseEntity<ResultadoBusquedaResponse> resultadoBusquedaResponse = restTemplate.postForEntity(serviceRestURLBuscarConFiltro.toString(), request, ResultadoBusquedaResponse.class);	
	
		log.debug(resultadoBusquedaResponse.toString());
		return resultadoBusquedaResponse.getBody();
	} catch (HttpClientErrorException e) {
		HttpStatus status = e.getStatusCode();
		log.error(status.value() + " " + status.getReasonPhrase());
		log.error(e);
		log.error("buscar... fin");	
		throw e;
	} catch (Exception e) {		
		throw e;
	}	

	}
	
	@Override
	public RespuestaCargaFacetDTO cargaFacet(CargaFacetDTO cargaFacetDTO, Usuario usuario) throws Exception {
			
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_CARGA_FACET);
		
		StringBuilder serviceRestURLCargaFacet = new StringBuilder(parametroDTO.getValorParametroChar());
		
		log.debug(cargaFacetDTO.toString());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();		
		mvm.add("palabraClave", cargaFacetDTO.getPalabraClave());
		mvm.add("tipoObjeto", cargaFacetDTO.getTipoObjeto());
		mvm.add("nombreTipoDocumento", cargaFacetDTO.getNombreTipoDocumento());
		mvm.add("nombreSubprocesoVigente", cargaFacetDTO.getNombreSubprocesoVigente());
		mvm.add("fechaInicio", cargaFacetDTO.getFechaInicio());
		mvm.add("fechaFin", cargaFacetDTO.getFechaFin());
		mvm.add("flagTipoBusqueda", cargaFacetDTO.getFlagTipoBusqueda());
		mvm.add("nombreFiltro", cargaFacetDTO.getNombreFiltro());
		mvm.add("tipoFiltro", cargaFacetDTO.getTipoFiltro());
		mvm.add("tipoDocumentoOficial", cargaFacetDTO.getTipoDocumentoOficial());
		mvm.add("filtraPorConfidencialidad", cargaFacetDTO.getFiltraPorConfidencialidad());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
		
		try {
			
			autenticacionService.validaSesion(usuario);
			serviceRestURLCargaFacet.append(usuario.getAlfTicket());
			
			ResponseEntity<RespuestaCargaFacetDTO> respuestaCargaFacetDTO = restTemplate.postForEntity(serviceRestURLCargaFacet.toString(), request , RespuestaCargaFacetDTO.class);
			log.debug(respuestaCargaFacetDTO.toString());
			return respuestaCargaFacetDTO.getBody();
			
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + " " + status.value());
			log.error(e);
			log.error("cargaFacet... fin");	
			throw e;
		}
		
	}

	@Override
	public ResultadoBusquedaResponse buscarRegistrosPaginados(DataTableRequestDTO dataTableInput,BuscarDTO buscarDTO, Usuario usuario) throws Exception {
		
		log.debug("buscarRegistrosPaginados... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();

		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_BUSCA_REGISTROS_PAGINADOS);
		StringBuilder serviceRestURLbuscarRegistrosPaginados = new StringBuilder(parametroDTO.getValorParametroChar());


		//String URLBuscarRegistroPaginado = "http://172.16.10.73:8080/alfresco/s/buscarRegistrosPaginados";
		//StringBuilder serviceRestURLbuscarRegistrosPaginados = new StringBuilder(URLBuscarRegistroPaginado);
		
		log.debug("serviceRestURLbuscarRegistrosPaginados: " + serviceRestURLbuscarRegistrosPaginados);
		//Map<String, String> parametrosURL = new HashMap<String, String>();

		Integer numeroPagina = 0; 
		
	
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
	
		mvm.add("palabraClave", buscarDTO.getPalabraClave());		
		mvm.add("fechaInicio", buscarDTO.getFechaInicio());
		mvm.add("fechaFin", buscarDTO.getFechaFin());
		mvm.add("tipoObjeto", buscarDTO.getTipoDeObjetoParaBuscar());		
		mvm.add("tipoDocumentoOficial", buscarDTO.getTipoDocumentoOficial());
		mvm.add("nombreTipoDocumento", buscarDTO.getNombreTipoDocumento());
		mvm.add("nombreSubprocesoVigente", buscarDTO.getNombreSubprocesoVigente());
		mvm.add("flagTipoBusqueda", buscarDTO.getFlagTipoBusqueda());
		
		
		//mvm.add("nombreSubprocesoVigente", buscarDTO.getNombreSubprocesoVigente());
		
		mvm.add("nombreFiltro", buscarDTO.getNombreFiltro());
		mvm.add("tipoFiltro", buscarDTO.getTipoFiltro());
		mvm.add("filtraPorConfidencialidad ", buscarDTO.getFiltraPorConfidencialidad());
		
		mvm.add("listaSubproceso ", buscarDTO.getListaSubproceso());
		mvm.add("listaMateria ", buscarDTO.getListaMateria());
		mvm.add("listaAutor ", buscarDTO.getListaAutor());
		mvm.add("listaCreador ", buscarDTO.getListaCreador());
		
		
	  if (buscarDTO.getFlagExportaExcel() == null){	
		
			if (dataTableInput.getLength() <= dataTableInput.getStart()){
				numeroPagina = (dataTableInput.getStart()/dataTableInput.getLength());
			}else{
				numeroPagina = 0;
			}
			
			
			mvm.add("campoOrden", dataTableInput.getOrder().getData());
			mvm.add("orderBy", dataTableInput.getOrder().getSortDir());
			mvm.add("buscar ", dataTableInput.getSearch());
			mvm.add("cantidadRegistrosPorPagina", dataTableInput.getLength().toString());
			mvm.add("numeroPagina", numeroPagina.toString());
	  }else{
		    mvm.add("flagExportaExcel", buscarDTO.getFlagExportaExcel());
	  }
		

		
		/**/
		//parametrosURL.put("cantidadRegistrosPorPagina",dataTableInput.getLength().toString());	
		//parametrosURL.put("numeroPagina",numeroPagina.toString());		
			
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
	
	
		
		try {
			autenticacionService.validaSesion(usuario);
			//parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			// ResultadoBusquedaResponse resultadoBusquedaResponse = restTemplate.getForObject(serviceRestURLbuscarRegistrosPaginados.toString(), ResultadoBusquedaResponse.class, parametrosURL);
			serviceRestURLbuscarRegistrosPaginados.append("?alf_ticket=");
			serviceRestURLbuscarRegistrosPaginados.append(usuario.getAlfTicket());			
			ResponseEntity<ResultadoBusquedaResponse> resultadoBusquedaResponse = restTemplate.postForEntity(serviceRestURLbuscarRegistrosPaginados.toString(), request, ResultadoBusquedaResponse.class);	
						
			// log.debug(resultadoBusquedaResponse.toString());
			return resultadoBusquedaResponse.getBody();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.value() + " " + status.getReasonPhrase());
			log.error(e);
			log.error("buscar... fin");	
			throw e;
		}	
	}

}

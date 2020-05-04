package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.dto.rest.TipoDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.RegistroDocumentoService;
import cl.gob.scj.sgdp.ws.rest.client.NumeracionClientRestService;
import cl.gob.scj.sgdp.ws.soap.client.BorraRegistroDocumentoRequest;
import cl.gob.scj.sgdp.ws.soap.client.BorraRegistroDocumentoResponse;
import cl.gob.scj.sgdp.ws.soap.client.GeneraRegistroDocumentoRequest;
import cl.gob.scj.sgdp.ws.soap.client.GeneraRegistroDocumentoResponse;
import cl.gob.scj.sgdp.ws.soap.client.RegistroDocumentoWS;
import cl.gob.scj.sgdp.ws.soap.client.RegistroDocumentoWS_Service;

@Service
@Transactional(rollbackFor = Throwable.class)
public class RegistroDocumentoServiceImpl implements RegistroDocumentoService {	
	
	private static final Logger log = Logger.getLogger(RegistroDocumentoServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private NumeracionClientRestService numeracionClientRestService;

	@Override
	public BorraRegistroDocumentoResponseRest borraRegistroDocumento(
			BorraRegistroDocumentoRequestRest borraRegistroDocumentoRequestRest) throws IOException {
		
		log.info("Inicio borraRegistroDocumento");
		
		log.info(borraRegistroDocumentoRequestRest.toString());
		
		BorraRegistroDocumentoRequest borraRegistroDocumentoRequest = new BorraRegistroDocumentoRequest();
		BorraRegistroDocumentoResponseRest borraRegistroDocumentoResponseRest = new BorraRegistroDocumentoResponseRest();
		BeanUtils.copyProperties(borraRegistroDocumentoRequestRest, borraRegistroDocumentoRequest);
		String url = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_REGISTRA_DOC_WS).getValorParametroChar();
		URL urlRegistroDocumentoWS = new URL(url);
		RegistroDocumentoWS_Service rws = new RegistroDocumentoWS_Service(urlRegistroDocumentoWS);
		RegistroDocumentoWS registroDocumentoWS = rws.getRegistroDocumentoWSPort();
		
		Map<String, Object> req_ctx = ((BindingProvider)registroDocumentoWS).getRequestContext();
		req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
		
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Username", Collections.singletonList(borraRegistroDocumentoRequestRest.getUser()));
		headers.put("Password", Collections.singletonList(borraRegistroDocumentoRequestRest.getPass()));
		req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(borraRegistroDocumentoRequestRest.getExpDoc());
		log.info(instanciaDeProceso.toString());
		
		BorraRegistroDocumentoResponse borraRegistroDocumentoResponse = registroDocumentoWS.borraRegistroDocumento(borraRegistroDocumentoRequest);		
		BeanUtils.copyProperties(borraRegistroDocumentoResponse, borraRegistroDocumentoResponseRest);
		borraRegistroDocumentoResponse.setExpDoc(instanciaDeProceso.getNombreExpediente());
		borraRegistroDocumentoResponse.setNumeroDoc(borraRegistroDocumentoRequest.getNumeroDoc());
		TipoDocumentoDTO tipoDocumentoDTO = numeracionClientRestService.getTipoDocumentoPorCodTipoDoc(borraRegistroDocumentoRequest.getCodTipoDoc());
		borraRegistroDocumentoResponse.setNombreTipoDocumento(tipoDocumentoDTO.getNombreCompletoTipoDoc());
		try {
			emailService.enviarMailNotificacionPorNumeroDeDocumento(borraRegistroDocumentoResponse, null, instanciaDeProceso, true);
		} catch (SgdpException e) {
			log.error(e);
		}
		return borraRegistroDocumentoResponseRest;
	}

	@Override
	public GeneraRegistroDocumentoResponseRest generaRegistroDocumento(
			GeneraRegistroDocumentoRequestRest generaRegistroDocumentoRequestRest, Usuario usuario) throws IOException {
		
		log.info("Inicio generaRegistroDocumento");
		
		log.info(generaRegistroDocumentoRequestRest.toString());
		
		GeneraRegistroDocumentoRequest generaRegistroDocumentoRequest = new GeneraRegistroDocumentoRequest();
		GeneraRegistroDocumentoResponseRest generaRegistroDocumentoResponseRest = new GeneraRegistroDocumentoResponseRest();
		BeanUtils.copyProperties(generaRegistroDocumentoRequestRest, generaRegistroDocumentoRequest);
		String url = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_REGISTRA_DOC_WS).getValorParametroChar();
		URL urlRegistroDocumentoWS = new URL(url);
		RegistroDocumentoWS_Service rws = new RegistroDocumentoWS_Service(urlRegistroDocumentoWS);
		RegistroDocumentoWS registroDocumentoWS = rws.getRegistroDocumentoWSPort();
		
		Map<String, Object> req_ctx = ((BindingProvider)registroDocumentoWS).getRequestContext();
		req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
		
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Username", Collections.singletonList(generaRegistroDocumentoRequestRest.getUser()));
		headers.put("Password", Collections.singletonList(generaRegistroDocumentoRequestRest.getPass()));
		req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(generaRegistroDocumentoRequestRest.getExpDoc());
		log.info(instanciaDeProceso.toString());
		generaRegistroDocumentoRequest.setMateria(instanciaDeProceso.getAsunto());
		
		GeneraRegistroDocumentoResponse generaRegistroDocumentoResponse = registroDocumentoWS.generaRegistroDocumento(generaRegistroDocumentoRequest);
		BeanUtils.copyProperties(generaRegistroDocumentoResponse, generaRegistroDocumentoResponseRest);
		try {
			emailService.enviarMailNotificacionPorNumeroDeDocumento(generaRegistroDocumentoResponse, usuario, instanciaDeProceso, false);
		} catch (SgdpException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString(); 
			log.error(exceptionAsString);
		}
		return generaRegistroDocumentoResponseRest;
	}

}

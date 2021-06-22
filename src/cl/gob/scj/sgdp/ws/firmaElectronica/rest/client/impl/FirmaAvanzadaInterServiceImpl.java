package cl.gob.scj.sgdp.ws.firmaElectronica.rest.client.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.client.FirmaAvanzadaInterService;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.TokenResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FirmaAvanzadaInterServiceImpl implements FirmaAvanzadaInterService {

	private static final Logger log = Logger.getLogger(FirmaAvanzadaInterServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Override
	public TokenResponse firmarDocumentoConFEA(FirmaAvanzadaRequest firmaAvanzadaRequest/*, String opt*/) throws Exception {
		
		log.info("firmarDocumentoConFEA... inicio");
		
		TokenResponse tokenResponse;
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_REST_URL_FEA);
		String serviceRestURLFirmaAvanzada = parametroDTO.getValorParametroChar();		
		
		log.info("serviceRestURLFirmaAvanzada: " + serviceRestURLFirmaAvanzada);
		//log.debug(firmaAvanzadaRequest.toString());
	 
	    try {
	    	URL url = new URL(serviceRestURLFirmaAvanzada);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
			//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String requestJson;
			
			ObjectMapper mapper = SingleObjectFactory.getMapper();
			
			requestJson = mapper.writeValueAsString(firmaAvanzadaRequest);

			//log.debug(requestJson);
			
			con.setDoOutput(true);
			OutputStream wr = con.getOutputStream();
			wr.write(requestJson.getBytes("UTF-8"));
			wr.flush();
			wr.close();
			
			log.debug(requestJson);
			
			int status = con.getResponseCode();
			String responseMessage = con.getResponseMessage();

			log.info("status: " + status);
			log.info("responseMessage: " + responseMessage);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
			
            log.debug("sb.toString(): " + sb.toString());
            
            if (status == 200) {
            	tokenResponse = mapper.readValue(sb.toString(), TokenResponse.class);
            	return tokenResponse;
            } else {
            	log.error("sb.toString(): " + sb.toString());
            	Exception e = new Exception(sb.toString());
            	throw e;
            }     	
			
			
	    } catch (Exception e) {
	    	StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			throw e;
		}	
		
	}	
	
	@Override
	public FirmaAvanzadaMinSegPresResponse getDocumentosFEA(TokenResponse tokenResponse, String otp) throws Exception {
		
		log.info("getDocumentosFEA... inicio");	
		log.info("tokenResponse.getSession_token(): " + tokenResponse.getSession_token());
		
		FirmaAvanzadaMinSegPresResponse firmaAvanzadaMinSegPresResponse;
		
		ObjectMapper mapper = SingleObjectFactory.getMapper();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_REST_URL_FEA_GET_DOCUMENTOS);
		String serviceRestURLFirmaAvanzadaGetDocumentos = parametroDTO.getValorParametroChar() + tokenResponse.getSession_token();		
		
		log.info("serviceRestURLFirmaAvanzadaGetDocumentos: " + serviceRestURLFirmaAvanzadaGetDocumentos);		
		
		try {
	    	URL url = new URL(serviceRestURLFirmaAvanzadaGetDocumentos);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
			if (otp!=null && !otp.isEmpty() && !otp.equals("null")) {
				log.debug("otp!=null");
				con.addRequestProperty(Constantes.NOMBRE_HEADER_OTP_FEA, otp);
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder result = new StringBuilder();
		    String line ;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			in.close();
			 
			log.debug("respuesta : " + result.toString());
			
			int status = con.getResponseCode();

			log.info("status: " + status);
			
			if (status == 200) {
				firmaAvanzadaMinSegPresResponse = mapper.readValue(result.toString(), FirmaAvanzadaMinSegPresResponse.class);
            	return firmaAvanzadaMinSegPresResponse;
            } else {
            	log.error("result.toString(): " + result.toString());
            	Exception e = new Exception(result.toString());
            	throw e;
            } 
			
			
		} catch (Exception e) {
	    	StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			throw e;
		}
		
		/*RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_REST_URL_FEA_GET_DOCUMENTOS);
		String serviceRestURLFirmaAvanzadaGetDocumentos = parametroDTO.getValorParametroChar();		
		
		log.debug("serviceRestURLFirmaAvanzadaGetDocumentos: " + serviceRestURLFirmaAvanzadaGetDocumentos);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_SESSION_TOKEN_FEA_GET, tokenResponse.getSession_token());		
		
		HttpHeaders headers = new HttpHeaders();
		
		if (opt!=null && !opt.isEmpty() && !opt.equals("null")) {
			log.debug("opt!=null");
			headers.add(Constantes.NOMBRE_HEADER_OTP_FEA, opt);
		}
		
		HttpEntity entity = new HttpEntity(headers);
		
		try {
			
			
			HttpEntity<FirmaAvanzadaMinSegPresResponse> response = restTemplate.exchange(
					serviceRestURLFirmaAvanzadaGetDocumentos, HttpMethod.GET, entity, FirmaAvanzadaMinSegPresResponse.class, parametrosURL);
			
			return response.getBody();
			
			
			
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);			
			throw e;
		}*/
		
	}

}

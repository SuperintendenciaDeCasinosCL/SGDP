package cl.gob.scj.sgdp.ws.firmaElectronica.rest.client.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.client.FirmaAvanzadaInterService;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponseV3;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.TokenResponse;

@Service
public class FirmaAvanzadaInterServiceImpl implements FirmaAvanzadaInterService {

	private static final Logger log = Logger.getLogger(FirmaAvanzadaInterServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Override
	public TokenResponse firmarDocumentoConFEA(FirmaAvanzadaRequest firmaAvanzadaRequest) throws Exception {		
		log.info("firmarDocumentoConFEA... inicio");		
		TokenResponse tokenResponse;		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_REST_URL_FEA);
		String serviceRestURLFirmaAvanzada = parametroDTO.getValorParametroChar();
		log.info("serviceRestURLFirmaAvanzada: " + serviceRestURLFirmaAvanzada);	 
	    try {
	    	URL url = new URL(serviceRestURLFirmaAvanzada);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", System.getProperty("http.agent"));			
			String requestJson;			
			ObjectMapper mapper = SingleObjectFactory.getMapper();			
			requestJson = mapper.writeValueAsString(firmaAvanzadaRequest);			
			con.setDoOutput(true);
			OutputStream wr = con.getOutputStream();
			wr.write(requestJson.getBytes("UTF-8"));
			wr.flush();
			wr.close();			
			log.debug("requestJson: " + requestJson);			
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
            } else if (status == 500) {            	
            	SgdpException e = new SgdpException("El servicio de firma de Segpres no est\u00E1 disponible en este momento, por favor int\u00E9ntelo m\u00E1s tarde.");
            	throw e;
            } else {
            	log.error("sb.toString(): " + sb.toString());
            	Exception e = new Exception(sb.toString());
            	throw e;
            }   			
			
	    }  catch (SgdpException e) {
			throw e;
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
            } else if (status == 401 || status == 206) {
            	log.error("result.toString(): " + result.toString());
            	SgdpException e = new SgdpException("OTP expirado, por favor intentar nuevamente.");
            	throw e;
            } else if (status == 500) {
            	log.error("result.toString(): " + result.toString());
            	SgdpException e = new SgdpException("El servicio de firma de Segpres no est\u00E1 disponible en este momento, por favor int\u00E9ntelo m\u00E1s tarde.");
            	throw e;
            } else {
            	log.error("result.toString(): " + result.toString());
            	Exception e = new Exception(result.toString());
            	throw e;
            } 		
			
		} catch (SgdpException e) {
			throw e;
		} catch (Exception e) {
	    	StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			throw e;
		}
		
		
	}

	@Override
	public FirmaAvanzadaMinSegPresResponseV3 firmarDocumentoConFEAV3(FirmaAvanzadaRequest firmaAvanzadaRequest, String otp) throws Exception {
		log.info("firmarDocumentoConFEA... inicio");		
		FirmaAvanzadaMinSegPresResponseV3 firmaAvanzadaMinSegPresResponse;		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FEA_V2);
		String serviceRestURLFirmaAvanzada = parametroDTO.getValorParametroChar();
		log.info("serviceRestURLFirmaAvanzada: " + serviceRestURLFirmaAvanzada);
		ObjectMapper mapper = SingleObjectFactory.getMapper();
		StringBuilder sb = new StringBuilder();
		HttpsURLConnection con = null;
		try {
	    	URL url = new URL(serviceRestURLFirmaAvanzada);
			con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", System.getProperty("http.agent"));	
			if (otp!=null && !otp.isEmpty() && !otp.equals("null")) {
				log.debug("otp!=null");
				con.addRequestProperty(Constantes.NOMBRE_HEADER_OTP_FEA, otp);
			}
			String requestJson;					
			requestJson = mapper.writeValueAsString(firmaAvanzadaRequest);			
			con.setDoOutput(true);
			OutputStream wr = con.getOutputStream();
			wr.write(requestJson.getBytes("UTF-8"));
			wr.flush();
			wr.close();
			log.debug("otp: " + otp);
			log.debug("requestJson: [INICIO] " + requestJson + " [FIN]");
			int status = con.getResponseCode();
			String responseMessage = con.getResponseMessage();
			log.info("status: " + status);
			log.info("responseMessage: " + responseMessage);			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));           
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();			
            log.debug("sb.toString(): " + sb.toString());             
            if (status == 200) {
            	firmaAvanzadaMinSegPresResponse = mapper.readValue(sb.toString(), FirmaAvanzadaMinSegPresResponseV3.class);
            	return firmaAvanzadaMinSegPresResponse;
            } else if (status == 500) {            	
            	SgdpException e = new SgdpException("El servicio de firma de Segpres no est\u00E1 disponible en este momento, por favor int\u00E9ntelo m\u00E1s tarde.");
            	throw e;
            } else {
            	InputStream inputStream = con.getErrorStream();            	
            	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        	    StringBuilder response = new StringBuilder();
        	    String currentLine;
        	    while ((currentLine = in.readLine()) != null) {
        	        response.append(currentLine);
        	    }
        	    in.close();  
        	    log.error("responseError.toString(): " + response.toString());
            	log.error("sb.toString(): " + sb.toString());
            	Exception e = new Exception(sb.toString());
            	throw e;
            }   			
			
	    }  catch (SgdpException e) {
			throw e;
		} catch (Exception e) {
	    	StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			throw e;
		} finally {
			if (con!=null) {
				con.disconnect();
			}
		}
	}

}
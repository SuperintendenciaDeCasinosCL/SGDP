package cl.gob.scj.sgdp.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger log = Logger.getLogger(RequestLoggingInterceptor.class);
	
	private String alfTicketCookie;
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		
		if (alfTicketCookie!=null) {			
			request.getHeaders().set("Cookie", alfTicketCookie);			
		}
		
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		if (alfTicketCookie==null) {
			List<String> alfTicketsCookie = response.getHeaders().get("Set-Cookie");
			if (alfTicketsCookie!=null) {
				alfTicketCookie = alfTicketsCookie.get(0);
				for (String s : alfTicketsCookie) {
					log.debug(s);
				}							
			}			
		}
		traceResponse(request, response);			

        return response;
	        
	}

	public String getAlfTicketCookie() {
		return alfTicketCookie;
	}

	public void setAlfTicketCookie(String alfTicketCookie) {
		this.alfTicketCookie = alfTicketCookie;
	}
	
	
	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		if (request.getURI().toString().contains("/alfresco/service/api/login/ticket/")) {
			return;
		}
		String getUri;
		if (request.getURI().toString().contains("login.json?u=")) {
			getUri = request.getURI().toString().substring(0, request.getURI().toString().indexOf("&pw="));
		} else {
			getUri = request.getURI().toString();
		}
        log.info("===========================request begin===========================================");
        log.info(MessageFormat.format("URI         : {0}", getUri));
        log.info(MessageFormat.format("Method      : {0}", request.getMethod()));
        log.info(MessageFormat.format("Headers     : {0}", request.getHeaders()));
		if (!request.getURI().toString().contains("/alfresco/s/scj/subirArchivo") &&
				!request.getURI().toString().contains("/Convert") &&
				!request.getURI().toString().contains("/realms/master/protocol/openid-connect/token") &&
				!request.getURI().toString().contains("/convertToPDFFromSupported")) {
			log.info(MessageFormat.format("Request body: {0}", new String(body, "UTF-8")));
		}
		log.info("==========================request end==============================================");
    }

    private void traceResponse(HttpRequest request, ClientHttpResponse response) throws IOException { 
		if (request.getURI().toString().contains("/alfresco/service/api/login/ticket/")) {
			return;
		}
		log.info("============================response begin==========================================");
        log.info(MessageFormat.format("Status code  : {0}", response.getStatusCode()));
        log.info(MessageFormat.format("Status text  : {0}", response.getStatusText()));
        log.info(MessageFormat.format("Headers      : {0}", response.getHeaders()));
        if (!request.getURI().toString().contains("/alfresco/service/api/node/content/workspace/SpacesStore/")
        	&& 
        	!request.getURI().toString().contains("/alfresco/s/scj/buscar")
        	&& 
        	!request.getURI().toString().contains("/alfresco/s/buscarRegistrosPaginados")
        	&& 
        	!request.getURI().toString().contains("/Convert")) {
        	StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }      
        	log.info(MessageFormat.format("Response body: {0}", inputStringBuilder.toString()));
        }         
        log.info("=======================response end=================================================");  
    }

}

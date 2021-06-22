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

			/*if (!request.getURI().toString().contains("login.json?u=")) {
			log.info(MessageFormat.format("request URI: {0}, request headers: {1}, response headers: {2}",

					request.getURI(),
		            request.getHeaders(),	          
		            response.getHeaders()
		            ));*/
			/*log.info("===========================request begin================================================");
	        log.info("URI         : " + request.getURI());
	        log.info("Method      : " + request.getMethod());
	        log.info("Headers     : " + request.getHeaders() );
	        log.info("===========================request end==================================================");*/
		/*} else if (request.getURI().toString().contains("login.json?u=")) {
			String getUri = request.getURI().toString().substring(0, request.getURI().toString().indexOf("&pw="));
			log.info("===========================request begin================================================");
	        log.info("URI         : " + getUri);
	        log.info("Method      : " + request.getMethod());
	        log.info("Headers     : " + request.getHeaders() );
	        log.info("Request body: " + new String(body, "UTF-8"));
	        log.info("===========================request end==================================================");*/
			/*log.info(MessageFormat.format("request URI: {0}, request headers: {1}, response headers: {2}, response body: {3}",
					getUri,
		            request.getHeaders(),	          
		            response.getHeaders(),
		            response.getBody()

		            ));
		}*/

        return response;
	        
	}

	public String getAlfTicketCookie() {
		return alfTicketCookie;
	}

	public void setAlfTicketCookie(String alfTicketCookie) {
		this.alfTicketCookie = alfTicketCookie;
	}
	
	
	//http://172.16.10.73:8080/alfresco/s/scj/subirArchivo
	//http://172.16.10.73:8080/alfresco/service/api/node/content/workspace/SpacesStore/{id_archivo}/?alf_ticket={alf_ticket}
	//http://172.16.160.221:8080/alfresco/service/api/login/ticket/{ticket_valida}?alf_ticket={alf_ticket}
	///alfresco/s/scj/buscar
	///alfresco/s/buscarRegistrosPaginados
	
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
        if (!request.getURI().toString().contains("/alfresco/s/scj/subirArchivo")) {
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
        	!request.getURI().toString().contains("/alfresco/s/buscarRegistrosPaginados")) {
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

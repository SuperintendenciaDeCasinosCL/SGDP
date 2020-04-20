package cl.gob.scj.sgdp.interceptor;

import java.io.IOException;
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
		
		//traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		//traceResponse(response);
		
		if (alfTicketCookie==null) {
			List<String> alfTicketsCookie = response.getHeaders().get("Set-Cookie");
			if (alfTicketsCookie!=null) {
				alfTicketCookie = alfTicketsCookie.get(0);
				for (String s : alfTicketsCookie) {
					log.debug(s);
				}							
			}			
		}
		
		if (!request.getURI().toString().contains("login.json?u=")) {
			log.info(MessageFormat.format("request URI: {0}, request headers: {1}, response headers: {2}",
					request.getURI(),
		            request.getHeaders(),	          
		            response.getHeaders()
		            ));
		}

        return response;
	        
	}

	public String getAlfTicketCookie() {
		return alfTicketCookie;
	}

	public void setAlfTicketCookie(String alfTicketCookie) {
		this.alfTicketCookie = alfTicketCookie;
	}
	
	/*private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("===========================request begin================================================");
        log.info(MessageFormat.format("URI         : {0}", request.getURI()));
        log.info(MessageFormat.format("Method      : {0}", request.getMethod()));
        log.info(MessageFormat.format("Headers     : {0}", request.getHeaders()));
        log.info(MessageFormat.format("Request body: {0}", new String(body, "UTF-8")));
        log.info("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {        
        log.info("============================response begin==========================================");
        log.info(MessageFormat.format("Status code  : {0}", response.getStatusCode()));
        log.info(MessageFormat.format("Status text  : {0}", response.getStatusText()));
        log.info(MessageFormat.format("Headers      : {0}", response.getHeaders()));
        log.info(MessageFormat.format("Response body: {0}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()))); 
        log.info("=======================response end=================================================");        
    }*/

}

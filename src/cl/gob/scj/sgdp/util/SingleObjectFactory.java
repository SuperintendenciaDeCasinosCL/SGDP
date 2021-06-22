package cl.gob.scj.sgdp.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.interceptor.RequestLoggingInterceptor;

public class SingleObjectFactory {
	
	private static RestTemplate restTemplate;	
	private static ObjectMapper mapper = new ObjectMapper();
		
	public static RestTemplate getRestTemplateInstance() {
		
		if (restTemplate == null) {	
		    
			BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(clientHttpRequestFactory());
			
			restTemplate = new RestTemplate(bufferingClientHttpRequestFactory);			
			//restTemplate.setRequestFactory(clientHttpRequestFactory());
			
			
		    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
	        messageConverters.add(new FormHttpMessageConverter());        
	        messageConverters.add(new StringHttpMessageConverter());   
	        messageConverters.add(new MappingJackson2HttpMessageConverter());
	        messageConverters.add(new ByteArrayHttpMessageConverter());	        
	        restTemplate.setMessageConverters(messageConverters);	        
	        
	        List<ClientHttpRequestInterceptor> listClientRequestInterceptor = new ArrayList<ClientHttpRequestInterceptor>();
	        listClientRequestInterceptor.add(new RequestLoggingInterceptor());
	        
	        restTemplate.setInterceptors(listClientRequestInterceptor);	                
	        
		}		
		return restTemplate;
		
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}
	
	
	@SuppressWarnings("deprecation")
	private static  ClientHttpRequestFactory clientHttpRequestFactory() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
	   	httpClient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
		    @Override
		    public boolean retryRequest(IOException exception, int executionCount, 
		                                HttpContext context) {
		        if (executionCount > 3) {
		           System.out.println("Maximum tries reached for client http pool ");
		            return false;
		        }
		        if (exception instanceof org.apache.http.NoHttpResponseException) {
		        	System.out.println("No response from server on " + executionCount + " call");
		            return true;
		        }
		        return false;
		      }
		   }); 
	   	HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		//HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        factory.setConnectTimeout(22000);
        return factory;
    }
	

}

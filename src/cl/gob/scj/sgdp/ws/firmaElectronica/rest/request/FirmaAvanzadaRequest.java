package cl.gob.scj.sgdp.ws.firmaElectronica.rest.request;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("request")
public class FirmaAvanzadaRequest {
	
	private String token;
	private String api_token_key;
	private List<FirmaAvanzadaArchivoRequest> files;
	
	public FirmaAvanzadaRequest() {
		super();
		files = new ArrayList<FirmaAvanzadaArchivoRequest>();
	}
	/*@PostConstruct
	public void init() {
		files = new ArrayList<FirmaAvanzadaArchivoRequest>();
	}*/
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getApi_token_key() {
		return api_token_key;
	}
	public void setApi_token_key(String api_token_key) {
		this.api_token_key = api_token_key;
	}
	public List<FirmaAvanzadaArchivoRequest> getFiles() {
		return files;
	}
	public void setFiles(List<FirmaAvanzadaArchivoRequest> files) {
		this.files = files;
	}	

}

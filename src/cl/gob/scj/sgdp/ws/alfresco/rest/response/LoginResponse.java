package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse implements Serializable {
	
	private static final long serialVersionUID = -9179691947869891838L;	
	
	public LoginResponse() {
	
	}

	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
}

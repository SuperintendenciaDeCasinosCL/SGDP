package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data implements Serializable  {

	private static final long serialVersionUID = -7745778174921024200L;
	
	public Data() {
		
	}

	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
}

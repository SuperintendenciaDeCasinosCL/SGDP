package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

public class AgregaRemueveTagDeObjetoResponse implements Serializable {

	//{ "statusString": "Document tagged", "statusCode": true, "newTag": "workspace:\/\/SpacesStore\/a3ca92bc-ddff-4cb1-910d-2a7874bfa501" }
	
	private String statusString;
	private String statusCode;
	private String newTag;
	
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getNewTag() {
		return newTag;
	}
	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}
	@Override
	public String toString() {
		return "AgregaORemueveTagDeObjetoResponse [statusString=" + statusString + ", statusCode=" + statusCode
				+ ", newTag=" + newTag + "]";
	}
	
}

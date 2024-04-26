package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class FileV3 {

	@JsonProperty("content")
	public String content;
	@JsonProperty("status")
	public String status;
	@JsonProperty("description")
	public String description;
	@JsonProperty("contentType")
	public String contentType;
	@JsonProperty("documentStatus")
	public String documentStatus;
	@JsonProperty("checksum_original")
	public String checksum_original;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getChecksum_original() {
		return checksum_original;
	}
	public void setChecksum_original(String checksum_original) {
		this.checksum_original = checksum_original;
	}	

}
package cl.gob.scj.sgdp.ws.firmaElectronica.rest.request;

import javax.annotation.Generated;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Component
@Scope("request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "description", "checksum", "content", "content-type" })
public class FirmaAvanzadaArchivoRequest {

	@JsonProperty("description")
	private String description;
	@JsonProperty("checksum")
	private String checksum;
	@JsonProperty("content")
	private String content;
	@JsonProperty("content-type")
	private String type;
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonProperty("checksum")
	public String getChecksum() {
		return checksum;
	}
	@JsonProperty("checksum")
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	@JsonProperty("content")
	public String getContent() {
		return content;
	}
	@JsonProperty("content")
	public void setContent(String content) {
		this.content = content;
	}
	@JsonProperty("content-type")
	public String getType() {
		return type;
	}
	@JsonProperty("content-type")
	public void setType(String type) {
		this.type = type;
	}
	
}

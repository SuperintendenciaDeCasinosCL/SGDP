package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
* Recepcion de documentos firmados
*
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"session_token",
"metadata",
"files"
})
@Generated("jsonschema2pojo")
public class FirmaAvanzadaMinSegPresResponseV2 {

	/**
	*
	* (Required)
	*
	*/
	@JsonProperty("session_token")
	private String sessionToken;
	@JsonProperty("metadata")
	private MetadataV2 metadata;
	/**
	*
	* (Required)
	*
	*/
	@JsonProperty("files")
	private List<FileV2> files = null;

	/**
	*
	* (Required)
	*
	*/
	@JsonProperty("session_token")
	public String getSessionToken() {
	return sessionToken;
	}

	/**
	*
	* (Required)
	*
	*/
	@JsonProperty("session_token")
	public void setSessionToken(String sessionToken) {
	this.sessionToken = sessionToken;
	}

	@JsonProperty("metadata")
	public MetadataV2 getMetadata() {
	return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(MetadataV2 metadata) {
	this.metadata = metadata;
	}

	/**
	*
	* (Required)
	*
	*/
	@JsonProperty("files")
	public List<FileV2> getFiles() {
	return files;
	}

	/**
	*
	* (Required)
	*
	*/
	@JsonProperty("files")
	public void setFiles(List<FileV2> files) {
	this.files = files;
	}
	
	
}

package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "session_token", "metadata", "files" })
public class FirmaAvanzadaMinSegPresResponse {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("session_token")
	private String sessionToken;
	/**
* 
*/
	@JsonProperty("metadata")
	private Metadata metadata;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("files")
	private List<File> files = new ArrayList<File>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return The sessionToken
	 */
	@JsonProperty("session_token")
	public String getSessionToken() {
		return sessionToken;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param sessionToken
	 *            The session_token
	 */
	@JsonProperty("session_token")
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	/**
	 * 
	 * @return The metadata
	 */
	@JsonProperty("metadata")
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * 
	 * @param metadata
	 *            The metadata
	 */
	@JsonProperty("metadata")
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The files
	 */
	@JsonProperty("files")
	public List<File> getFiles() {
		return files;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param files
	 *            The files
	 */
	@JsonProperty("files")
	public void setFiles(List<File> files) {
		this.files = files;
	}

}

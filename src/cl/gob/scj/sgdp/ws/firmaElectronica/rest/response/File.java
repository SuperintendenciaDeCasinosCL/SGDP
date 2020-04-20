package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;

import javax.annotation.Generated;

import cl.gob.scj.sgdp.tipos.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "content-type", "content", "checksum_original", "checksum_signed",
		"description", "status" })
public class File {

	@JsonProperty("content-type")
	private Type type;
	@JsonProperty("content")
	private String content;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("checksum_original")
	private String checksumOriginal;
	@JsonProperty("checksum")
	private String checksum;
	@JsonProperty("description")
	private String description;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 
	 * @return The type
	 */
	@JsonProperty("content-type")
	public Type getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	@JsonProperty("content-type")
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The content
	 */
	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 *            The content
	 */
	@JsonProperty("content")
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The checksumOriginal
	 */
	@JsonProperty("checksum_original")
	public String getChecksumOriginal() {
		return checksumOriginal;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param checksumOriginal
	 *            The checksum_original
	 */
	@JsonProperty("checksum_original")
	public void setChecksumOriginal(String checksumOriginal) {
		this.checksumOriginal = checksumOriginal;
	}

	/**
	 * 
	 * @return The checksum
	 */
	@JsonProperty("checksum_signed")
	public String getChecksum() {
		return checksum;
	}

	/**
	 * 
	 * @param checksum
	 *            The checksum
	 */
	@JsonProperty("checksum_signed")
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	/**
	 * 
	 * @return The description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}
}
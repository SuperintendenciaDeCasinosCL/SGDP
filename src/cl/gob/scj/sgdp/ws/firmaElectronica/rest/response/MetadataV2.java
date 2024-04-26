package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "OTP_expired", "files_recived", "files_signed", "signed_failed" })
@Generated("jsonschema2pojo")
public class MetadataV2 {

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("OTP_expired")
	private Boolean oTPExpired;
	@JsonProperty("files_recived")
	private Double filesRecived;
	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("files_signed")
	private Double filesSigned;
	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("signed_failed")
	private Double signedFailed;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("OTP_expired")
	public Boolean getOTPExpired() {
		return oTPExpired;
	}

	@JsonProperty("OTP_expired")
	public void setOTPExpired(Boolean oTPExpired) {
		this.oTPExpired = oTPExpired;
	}

	@JsonProperty("files_recived")
	public Double getFilesRecived() {
		return filesRecived;
	}

	@JsonProperty("files_recived")
	public void setFilesRecived(Double filesRecived) {
		this.filesRecived = filesRecived;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("files_signed")
	public Double getFilesSigned() {
		return filesSigned;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("files_signed")
	public void setFilesSigned(Double filesSigned) {
		this.filesSigned = filesSigned;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("signed_failed")
	public Double getSignedFailed() {
		return signedFailed;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("signed_failed")
	public void setSignedFailed(Double signedFailed) {
		this.signedFailed = signedFailed;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
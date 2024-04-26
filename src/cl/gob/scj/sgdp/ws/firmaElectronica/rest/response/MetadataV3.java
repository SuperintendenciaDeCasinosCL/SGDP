package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;


import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class MetadataV3 {
	
	@JsonProperty("otpExpired")
	public boolean otpExpired;
	@JsonProperty("filesSigned")
    public int filesSigned;
	@JsonProperty("signedFailed")
    public int signedFailed;
	@JsonProperty("objectsReceived")
    public int objectsReceived;
	
	public boolean isOtpExpired() {
		return otpExpired;
	}
	public void setOtpExpired(boolean otpExpired) {
		this.otpExpired = otpExpired;
	}
	public int getFilesSigned() {
		return filesSigned;
	}
	public void setFilesSigned(int filesSigned) {
		this.filesSigned = filesSigned;
	}
	public int getSignedFailed() {
		return signedFailed;
	}
	public void setSignedFailed(int signedFailed) {
		this.signedFailed = signedFailed;
	}
	public int getObjectsReceived() {
		return objectsReceived;
	}
	public void setObjectsReceived(int objectsReceived) {
		this.objectsReceived = objectsReceived;
	}
	
}
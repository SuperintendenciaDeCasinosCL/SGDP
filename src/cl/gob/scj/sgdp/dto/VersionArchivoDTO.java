package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class VersionArchivoDTO implements Serializable {

	private String versionName;
	private String versionLabel;
	private String linkAversion;
	private String creador;
	private String versionMimeType;
	
	public VersionArchivoDTO() {
		super();
	}	
	public VersionArchivoDTO(String versionName, String versionLabel,
			String linkAversion, String creador) {
		super();
		this.versionName = versionName;
		this.versionLabel = versionLabel;
		this.linkAversion = linkAversion;
		this.creador = creador;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionLabel() {
		return versionLabel;
	}
	public void setVersionLabel(String versionLabel) {
		this.versionLabel = versionLabel;
	}
	public String getLinkAversion() {
		return linkAversion;
	}
	public void setLinkAversion(String linkAversion) {
		this.linkAversion = linkAversion;
	}	
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}	
	public String getVersionMimeType() {
		return versionMimeType;
	}
	public void setVersionMimeType(String versionMimeType) {
		this.versionMimeType = versionMimeType;
	}
	@Override
	public String toString() {
		return "VersionArchivoDTO [versionName=" + versionName
				+ ", versionLabel=" + versionLabel + ", linkAversion="
				+ linkAversion + ", creador=" + creador 
				+ ", versionMimeType=" + versionMimeType +
				"]";
	}
}
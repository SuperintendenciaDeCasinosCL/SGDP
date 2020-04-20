package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;


public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idDocumento;

	private String autor;
	
	private String mimeType;

	private long idDocumentoEnAlfresco;

	private String version;
	
	private String idExpediente;

	public Documento() {
	}

	public long getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getAutor() {
		return this.autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public long getIdDocumentoEnAlfresco() {
		return this.idDocumentoEnAlfresco;
	}

	public void setIdDocumentoEnAlfresco(long idDocumentoEnAlfresco) {
		this.idDocumentoEnAlfresco = idDocumentoEnAlfresco;
	}	

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	
}
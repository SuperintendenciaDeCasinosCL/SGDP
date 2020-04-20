package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class TipoDeDocumentoDTO implements Serializable {
	
	private long idTipoDeDocumento;
	private String nombreDeTipoDeDocumento;
	private boolean conformaExpediente;
	private boolean aplicaVisacion;
	private boolean aplicaFEA;
	private boolean esDocumentoConductor;
	
	public TipoDeDocumentoDTO() {
		super();
	}

	public TipoDeDocumentoDTO(long idTipoDeDocumento,
			String nombreDeTipoDeDocumento) {
		super();
		this.idTipoDeDocumento = idTipoDeDocumento;
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}
	
	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}
	
	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}
	
	public String getNombreDeTipoDeDocumento() {
		return nombreDeTipoDeDocumento;
	}
	
	public void setNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}	

	public boolean getConformaExpediente() {
		return conformaExpediente;
	}

	public void setConformaExpediente(boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}

	public boolean getAplicaVisacion() {
		return aplicaVisacion;
	}

	public void setAplicaVisacion(boolean aplicaVisacion) {
		this.aplicaVisacion = aplicaVisacion;
	}

	public boolean getAplicaFEA() {
		return aplicaFEA;
	}

	public void setAplicaFEA(boolean aplicaFEA) {
		this.aplicaFEA = aplicaFEA;
	}

	public boolean getEsDocumentoConductor() {
		return esDocumentoConductor;
	}

	public void setEsDocumentoConductor(boolean esDocumentoConductor) {
		this.esDocumentoConductor = esDocumentoConductor;
	}

	@Override
	public String toString() {
		return "TipoDeDocumentoDTO [idTipoDeDocumento=" + idTipoDeDocumento + ", nombreDeTipoDeDocumento="
				+ nombreDeTipoDeDocumento + ", conformaExpediente=" + conformaExpediente + ", aplicaVisacion="
				+ aplicaVisacion + ", aplicaFEA=" + aplicaFEA + ", esDocumentoConductor=" + esDocumentoConductor + "]";
	}
	
}

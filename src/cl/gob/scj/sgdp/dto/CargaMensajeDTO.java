package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class CargaMensajeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1824873325131829917L;
	private long idCarga;
	private String alfTicket;
	private String idArchivoCms;
	private int totalArchvivos;
	private String nombreArchivo;
	private String idTransferencia;
	private String carpetaExp;
	private Long idEstadoAcuerdo;
	private Long idAcuerdo;

	public CargaMensajeDTO() {
		super();
	}

	public long getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(long idCarga) {
		this.idCarga = idCarga;
	}

	public String getAlfTicket() {
		return alfTicket;
	}

	public void setAlfTicket(String alfTicket) {
		this.alfTicket = alfTicket;
	}

	public String getIdArchivoCms() {
		return idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}

	public int getTotalArchvivos() {
		return totalArchvivos;
	}

	public void setTotalArchvivos(int totalArchvivos) {
		this.totalArchvivos = totalArchvivos;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getIdTransferencia() {
		return idTransferencia;
	}

	public void setIdTransferencia(String idTransferencia) {
		this.idTransferencia = idTransferencia;
	}

	public Long getIdEstadoAcuerdo() {
		return idEstadoAcuerdo;
	}

	public void setIdEstadoAcuerdo(Long idEstadoAcuerdo) {
		this.idEstadoAcuerdo = idEstadoAcuerdo;
	}

	public Long getIdAcuerdo() {
		return idAcuerdo;
	}

	public void setIdAcuerdo(Long idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}

	public String getCarpetaExp() {
		return carpetaExp;
	}

	public void setCarpetaExp(String carpetaExp) {
		this.carpetaExp = carpetaExp;
	}

	@Override
	public String toString() {
		return "CargaMensajeDTO [idCarga=" + idCarga + ", alfTicket=" + alfTicket + ", idArchivoCms=" + idArchivoCms
				+ ", totalArchvivos=" + totalArchvivos + ", nombreArchivo=" + nombreArchivo + ", idTransferencia="
				+ idTransferencia + ", carpetaExp=" + carpetaExp + ", idEstadoAcuerdo=" + idEstadoAcuerdo
				+ ", idAcuerdo=" + idAcuerdo + "]";
	}

}

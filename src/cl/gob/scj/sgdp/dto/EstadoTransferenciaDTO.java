package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class EstadoTransferenciaDTO implements Serializable {

	private long idCarga;
	private String nombreAcuerdo;
	private long totalArchivos;
	private long totalArchivoEnviado;
	private long totalArchivoNoEnviado;
	private String fechaTransferencia;
	private String descripcionEstado;
	public String getNombreAcuerdo() {
		return nombreAcuerdo;
	}
	public void setNombreAcuerdo(String nombreAcuerdo) {
		this.nombreAcuerdo = nombreAcuerdo;
	}
	public long getTotalArchivos() {
		return totalArchivos;
	}
	public void setTotalArchivos(long totalArchivos) {
		this.totalArchivos = totalArchivos;
	}
	public long getTotalArchivoEnviado() {
		return totalArchivoEnviado;
	}
	public void setTotalArchivoEnviado(long totalArchivoEnviado) {
		this.totalArchivoEnviado = totalArchivoEnviado;
	}
	public long getTotalArchivoNoEnviado() {
		return totalArchivoNoEnviado;
	}
	public void setTotalArchivoNoEnviado(long totalArchivoNoEnviado) {
		this.totalArchivoNoEnviado = totalArchivoNoEnviado;
	}
	public String getFechaTransferencia() {
		return fechaTransferencia;
	}
	public void setFechaTransferencia(String fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public long getIdCarga() {
		return idCarga;
	}
	public void setIdCarga(long idCarga) {
		this.idCarga = idCarga;
	}
}

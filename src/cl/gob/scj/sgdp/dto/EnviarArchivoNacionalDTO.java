package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class EnviarArchivoNacionalDTO implements Serializable {

	@Override
	public String toString() {
		return "EnviarArchivoNacionalDTO [idSerie=" + idSerie + ", nombreSerie=" + nombreSerie + ", idEstadoAcuerdo="
				+ idEstadoAcuerdo + ", estadoAcuerdo=" + estadoAcuerdo + ", idAcuerdo=" + idAcuerdo + ", nombreAcuerdo="
				+ nombreAcuerdo + ", fechaTransferirInicio=" + fechaTransferirInicio + ", fechaTransferirTermino="
				+ fechaTransferirTermino + "]";
	}

	private static final long serialVersionUID = 472060425462063347L;

	private String idSerie;
	private String nombreSerie;
	private String idEstadoAcuerdo;
	private String estadoAcuerdo;
	private String idAcuerdo;
	private String nombreAcuerdo;
	private String fechaTransferirInicio;
	private String fechaTransferirTermino;

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public String getIdEstadoAcuerdo() {
		return idEstadoAcuerdo;
	}

	public void setIdEstadoAcuerdo(String idEstadoAcuerdo) {
		this.idEstadoAcuerdo = idEstadoAcuerdo;
	}

	public String getEstadoAcuerdo() {
		return estadoAcuerdo;
	}

	public void setEstadoAcuerdo(String estadoAcuerdo) {
		this.estadoAcuerdo = estadoAcuerdo;
	}

	public String getIdAcuerdo() {
		return idAcuerdo;
	}

	public void setIdAcuerdo(String idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}

	public String getNombreAcuerdo() {
		return nombreAcuerdo;
	}

	public void setNombreAcuerdo(String nombreAcuerdo) {
		this.nombreAcuerdo = nombreAcuerdo;
	}

	public String getFechaTransferirInicio() {
		return fechaTransferirInicio;
	}

	public void setFechaTransferirInicio(String fechaTransferirInicio) {
		this.fechaTransferirInicio = fechaTransferirInicio;
	}

	public String getFechaTransferirTermino() {
		return fechaTransferirTermino;
	}

	public void setFechaTransferirTermino(String fechaTransferirTermino) {
		this.fechaTransferirTermino = fechaTransferirTermino;
	}

}

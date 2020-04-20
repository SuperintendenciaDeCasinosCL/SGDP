package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class RespuestaConversionArchivoDTO implements Serializable {

	private String resultadoOperacion;
	private String idArchivo;
	private String codigoError;
	private String detalleError;
	private String nombreArchivo;

	public String getResultadoOperacion() {
		return resultadoOperacion;
	}

	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getDetalleError() {
		return detalleError;
	}

	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@Override
	public String toString() {
		return "RespuestaConversionArchivoDTO [resultadoOperacion=" + resultadoOperacion + ", idArchivo=" + idArchivo
				+ ", codigoError=" + codigoError + ", detalleError=" + detalleError + ", nombreArchivo=" + nombreArchivo
				+ "]";
	}

}

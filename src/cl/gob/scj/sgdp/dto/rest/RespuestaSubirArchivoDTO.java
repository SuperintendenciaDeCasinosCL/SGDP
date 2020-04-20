package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class RespuestaSubirArchivoDTO implements Serializable {

	private String mensaje;
	private String idArchivo;
	private String link;	
	private String detalleRespuesta;
	
	public RespuestaSubirArchivoDTO() {
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDetalleRespuesta() {
		return detalleRespuesta;
	}
	public void setDetalleRespuesta(String detalleRespuesta) {
		this.detalleRespuesta = detalleRespuesta;
	}

	@Override
	public String toString() {
		return "RespuestaSubirArchivoDTO [mensaje=" + mensaje +
				", idArchivo=" + idArchivo +
				", link=" + link +
				", detalleRespuesta=" + detalleRespuesta +
				"]";
	}
	
}

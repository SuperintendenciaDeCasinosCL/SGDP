package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

public class TipoDeDestinatarioDTO implements Serializable {
	
	private long idTipoDestinatario;
	private String nombreTipoDestinatario;	
	private Date fechaCreacion;	
	private String usuarioCreacion;
	private String respuesta;
	
	public long getIdTipoDestinatario() {
		return idTipoDestinatario;
	}
	public void setIdTipoDestinatario(long idTipoDestinatario) {
		this.idTipoDestinatario = idTipoDestinatario;
	}
	public String getNombreTipoDestinatario() {
		return nombreTipoDestinatario;
	}
	public void setNombreTipoDestinatario(String nombreTipoDestinatario) {
		this.nombreTipoDestinatario = nombreTipoDestinatario;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	@Override
	public String toString() {
		return "TipoDeDestinatarioDTO [idTipoDestinatario=" + idTipoDestinatario + ", nombreTipoDestinatario="
				+ nombreTipoDestinatario + ", fechaCreacion=" + fechaCreacion + ", usuarioCreacion=" + usuarioCreacion
				+ ", respuesta=" + respuesta + "]";
	}

}
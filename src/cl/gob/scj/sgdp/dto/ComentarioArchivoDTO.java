package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ComentarioArchivoDTO implements Serializable {

	private String comentario;
	private String fecha;
	private String usuario;
	
	public ComentarioArchivoDTO(String comentario, String fecha, String usuario) {
		super();
		this.comentario = comentario;
		this.fecha = fecha;
		this.usuario = usuario;
	}
	public ComentarioArchivoDTO() {
		super();
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		return "ComentarioDTO [comentario=" + comentario + ", fecha=" + fecha
				+ ", usuario=" + usuario + "]";
	}	
	
}

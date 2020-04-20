package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RetrocedeEstadoDTO implements Serializable {

	long idInstanciaDeTareaSeleccionada;
	String comentario;
	private String nombreDeTarea;
	private String usuario;
	private String password;
	
	
	public RetrocedeEstadoDTO() {
	}
		
	public long getIdInstanciaDeTareaSeleccionada() {
		return idInstanciaDeTareaSeleccionada;
	}
	public void setIdInstanciaDeTareaSeleccionada(
			long idInstanciaDeTareaSeleccionada) {
		this.idInstanciaDeTareaSeleccionada = idInstanciaDeTareaSeleccionada;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getNombreDeTarea() {
		return nombreDeTarea;
	}
	public void setNombreDeTarea(String nombreDeTarea) {
		this.nombreDeTarea = nombreDeTarea;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RetrocedeEstadoDTO [idInstanciaDeTareaSeleccionada=" + idInstanciaDeTareaSeleccionada + ", comentario="
				+ comentario + ", nombreDeTarea=" + nombreDeTarea + ", usuario=" + usuario + "]";
	}
	
	

	
}

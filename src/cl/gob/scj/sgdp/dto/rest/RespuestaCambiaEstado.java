package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;



public class RespuestaCambiaEstado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private String listaIdInstanciaDeTarea;
	private String descripcionError;
	
	public RespuestaCambiaEstado() {
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getListaIdInstanciaDeTarea() {
		return listaIdInstanciaDeTarea;
	}
	public void setListaIdInstanciaDeTarea(String listaIdInstanciaDeTarea) {
		this.listaIdInstanciaDeTarea = listaIdInstanciaDeTarea;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	@Override
	public String toString() {
		return "RespuestaCambiaEstado [mensaje=" + mensaje + ", listaIdInstanciaDeTarea=" + listaIdInstanciaDeTarea
				+ ", descripcionError=" + descripcionError + "]";
	}
	

}

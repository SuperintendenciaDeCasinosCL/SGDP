package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class RespuestaExpedienteRestDTO implements Serializable  {

	private String mensaje;
	private String idExpediente;
	private String nombreExpediente;
	// Lista de instancia de tareas
	private String listaIdInstanciaDeTarea;
		
	
	public RespuestaExpedienteRestDTO() {
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getIdExpediente() {
		return idExpediente;
	}


	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}


	public String getNombreExpediente() {
		return nombreExpediente;
	}


	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}


	public String getListaIdInstanciaDeTarea() {
		return listaIdInstanciaDeTarea;
	}


	public void setListaIdInstanciaDeTarea(String listaIdInstanciaDeTarea) {
		this.listaIdInstanciaDeTarea = listaIdInstanciaDeTarea;
	}

	@Override
	public String toString() {
		return "RespuestaExpedienteRestDTO [mensaje=" + mensaje + ", idExpediente=" + idExpediente
				+ ", nombreExpediente=" + nombreExpediente 
				+ ", listaIdInstanciaDeTarea=" + listaIdInstanciaDeTarea				
				+ "]";
	}	
	
	
	
}

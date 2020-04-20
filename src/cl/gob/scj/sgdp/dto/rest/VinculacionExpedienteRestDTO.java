package cl.gob.scj.sgdp.dto.rest;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VinculacionExpedienteRestDTO extends RespuestaDTO {
		
	private String idUsuario;	
	private Date fechaVinculacion;	
	private String nombreExpediente;
	private String nombreProceso;
	private String comentario;
	private List<String> comentarios;	
	private List<VinculacionExpedienteRestDTO> expedientesAntecesores;
	private List<VinculacionExpedienteRestDTO> expedientesSucesores;
	private String nombreExpAntecesor;
	private long idRol;
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}
	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente!=null ? nombreExpediente.toUpperCase().trim() : nombreExpediente;
	}
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public List<String> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<String> comentarios) {
		this.comentarios = comentarios;
	}	
	public String getNombreExpAntecesor() {
		return nombreExpAntecesor;
	}
	public void setNombreExpAntecesor(String nombreExpAntecesor) {	
		this.nombreExpAntecesor = nombreExpAntecesor!=null ? nombreExpAntecesor.toUpperCase().trim() : nombreExpAntecesor;
	}
	public List<VinculacionExpedienteRestDTO> getExpedientesAntecesores() {
		return expedientesAntecesores;
	}
	public void setExpedientesAntecesores(List<VinculacionExpedienteRestDTO> expedientesAntecesores) {
		this.expedientesAntecesores = expedientesAntecesores;
	}
	public List<VinculacionExpedienteRestDTO> getExpedientesSucesores() {
		return expedientesSucesores;
	}
	public void setExpedientesSucesores(List<VinculacionExpedienteRestDTO> expedientesSucesores) {
		this.expedientesSucesores = expedientesSucesores;
	}	
	public long getIdRol() {
		return idRol;
	}
	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}
	@JsonIgnore
	public String getValidaDatos() {
		StringBuilder validaDatosVinculacion = new StringBuilder();
		if (idUsuario==null || idUsuario.isEmpty()) {
			validaDatosVinculacion.append("El nombre de usuario es obligatorio; ");
		}
		if (nombreExpediente==null || nombreExpediente.isEmpty()) {
			validaDatosVinculacion.append("El nombre de expediente es obligatorio; ");
		}
		if (comentario==null || comentario.isEmpty()) {
			validaDatosVinculacion.append("El comentario es obligatorio; ");
		}
		if (nombreExpAntecesor==null || nombreExpAntecesor.isEmpty()) {
			validaDatosVinculacion.append("El nombre del expediente antecesor es obligatorio; ");
		}		
		if (idRol==0) {
			validaDatosVinculacion.append("El id del rol es obligatorio; ");
		}
		if (nombreExpediente.equalsIgnoreCase(nombreExpAntecesor)) {
			validaDatosVinculacion.append("El nombre del expediente antecesor debe ser distinto al nombre de expediente; ");
		}
		return validaDatosVinculacion.toString();
	}	

	@Override
	public String toString() {
		return "VinculacionExpedienteRestDTO [idUsuario=" + idUsuario + ", fechaVinculacion=" + fechaVinculacion
				+ ", nombreExpediente=" + nombreExpediente + ", nombreProceso=" + nombreProceso + ", comentario="
				+ comentario + ", comentarios=" + comentarios + ", expedientesAntecesores=" + expedientesAntecesores
				+ ", expedientesSucesores=" + expedientesSucesores 
				+ ", nombreExpAntecesor=" + nombreExpAntecesor
				+ ", idRol=" + idRol
				+ "]";
	}
}

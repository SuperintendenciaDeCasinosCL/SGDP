package cl.gob.scj.sgdp.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VinculacionExpedienteDTO implements Comparable<VinculacionExpedienteDTO> {

	private String idUsuario;	
	private Date fechaVinculacion;	
	private String nombreExpediente;
	private String nombreProceso;
	private String comentario;
	private List<String> comentarios;	
	private List<VinculacionExpedienteDTO> expedientesAntecesores;
	private List<VinculacionExpedienteDTO> expedientesSucesores;
	private String nombreExpAntecesor;
	private String respuestaVinculacion;
	private String cssStatus;
	private int numeroExpediente;
	private String tipoVinculacion;
	
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
		if (nombreExpediente!=null) {
			String[] nbrExpArr = nombreExpediente.split("-");
			if (nbrExpArr!=null && nbrExpArr.length==3) {
				this.numeroExpediente = Integer.parseInt(nbrExpArr[1]);
			}
		}
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
	public List<VinculacionExpedienteDTO> getExpedientesAntecesores() {
		return expedientesAntecesores;
	}
	public void setExpedientesAntecesores(List<VinculacionExpedienteDTO> expedientesAntecesores) {
		this.expedientesAntecesores = expedientesAntecesores;
	}	
	public List<VinculacionExpedienteDTO> getExpedientesSucesores() {
		return expedientesSucesores;
	}
	public void setExpedientesSucesores(List<VinculacionExpedienteDTO> expedientesSucesores) {
		this.expedientesSucesores = expedientesSucesores;
	}	
	public String getNombreExpAntecesor() {
		return nombreExpAntecesor;
	}
	public void setNombreExpAntecesor(String nombreExpAntecesor) {
		this.nombreExpAntecesor = nombreExpAntecesor!=null ? nombreExpAntecesor.toUpperCase().trim() : nombreExpAntecesor;
	}	
	public String getRespuestaVinculacion() {
		return respuestaVinculacion;
	}
	public void setRespuestaVinculacion(String respuestaVinculacion) {
		this.respuestaVinculacion = respuestaVinculacion;
	}	
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}	
	public int getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(int numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}	
	public String getTipoVinculacion() {
		return tipoVinculacion;
	}
	public void setTipoVinculacion(String tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
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
		if (nombreExpediente.equalsIgnoreCase(nombreExpAntecesor)) {
			validaDatosVinculacion.append("El nombre del expediente antecesor debe ser distinto al nombre de expediente; ");
		}
		return validaDatosVinculacion.toString();
	}
	@Override
	public String toString() {
		return "VinculacionExpedienteDTO [idUsuario=" + idUsuario + ", fechaVinculacion=" + fechaVinculacion
				+ ", nombreExpediente=" + nombreExpediente + ", nombreProceso=" + nombreProceso + ", comentario="
				+ comentario + ", comentarios=" + comentarios + ", expedientesAntecesores=" + expedientesAntecesores
				+ ", expedientesSucesores=" + expedientesSucesores 
				+ ", nombreExpAntecesor=" + nombreExpAntecesor
				+ ", respuestaVinculacion=" + respuestaVinculacion
				+ ", cssStatus=" + cssStatus
				+ ", numeroExpediente=" + numeroExpediente
				+ ", tipoVinculacion=" + tipoVinculacion
				+ "]";
	}	
	@Override
	public int compareTo(VinculacionExpedienteDTO o) {	
		return this.numeroExpediente - o.numeroExpediente;
	}
}

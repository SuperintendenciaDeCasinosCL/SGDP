package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InstanciaDeTareaDTORest implements Serializable {
	
	private long idInstanciaDeTarea;
	private String nombreDeTarea;
	private String nombreDeProceso;
	private String nombreExpediente;
	private Date fechaVencimientoInstanciaDeTarea;
	private List<String> idUsuariosAsignados;
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}
	public String getNombreDeTarea() {
		return nombreDeTarea;
	}
	public void setNombreDeTarea(String nombreDeTarea) {
		this.nombreDeTarea = nombreDeTarea;
	}
	public String getNombreDeProceso() {
		return nombreDeProceso;
	}
	public void setNombreDeProceso(String nombreDeProceso) {
		this.nombreDeProceso = nombreDeProceso;
	}
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}
	public Date getFechaVencimientoInstanciaDeTarea() {
		return fechaVencimientoInstanciaDeTarea;
	}
	public void setFechaVencimientoInstanciaDeTarea(Date fechaVencimientoInstanciaDeTarea) {
		this.fechaVencimientoInstanciaDeTarea = fechaVencimientoInstanciaDeTarea;
	}
	public List<String> getIdUsuariosAsignados() {
		return idUsuariosAsignados;
	}
	public void setIdUsuariosAsignados(List<String> idUsuariosAsignados) {
		this.idUsuariosAsignados = idUsuariosAsignados;
	}
	
	@Override
	public String toString() {
		return "InstanciaDeTareaDTORest [idInstanciaDeTarea=" + idInstanciaDeTarea + ", nombreDeTarea=" + nombreDeTarea
				+ ", nombreDeProceso=" + nombreDeProceso + ", nombreExpediente=" + nombreExpediente
				+ ", fechaVencimientoInstanciaDeTarea=" + fechaVencimientoInstanciaDeTarea + ", idUsuariosAsignados="
				+ idUsuariosAsignados + "]";
	}	
	
}

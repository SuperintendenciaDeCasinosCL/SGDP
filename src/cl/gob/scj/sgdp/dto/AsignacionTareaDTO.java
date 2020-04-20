package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

public class AsignacionTareaDTO implements Serializable {

	private long idInstanciaDeTarea;
	private String usuariosAsignados;
	private String fechaAsignada;
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}
	public String getUsuariosAsignados() {
		return usuariosAsignados;
	}
	public void setUsuariosAsignados(String usuariosAsignados) {
		this.usuariosAsignados = usuariosAsignados;
	}		
	public String getFechaAsignada() {
		return fechaAsignada;
	}
	public void setFechaAsignada(String fechaAsignada) {
		this.fechaAsignada = fechaAsignada;
	}
	@Override
	public String toString() {
		return "AsignacionTareaDTO [idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", usuariosAsignados=" + usuariosAsignados
				+ ", fechaAsignada=" + fechaAsignada + "]";
	}	
}

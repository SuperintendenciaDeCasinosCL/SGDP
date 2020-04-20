package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class RetrocedeProcesoDTO implements Serializable {
	
	private static final long serialVersionUID = -5953309941830085424L;
	
	private long idInstanciaDeTareaSeleccionada;
	private String idInstanciaDeTareaSeleccionadaActualSalida;
	private String comentario;
	private String nombreDeTarea;
	private String resultadoRetrocedeProceso;
	private String cssStatus;
	private boolean recarga;
	private String parametrosMapParaGuardarJSON;
	
	public RetrocedeProcesoDTO() {
		super();		
	}
	
	public RetrocedeProcesoDTO(long idInstanciaDeTareaSeleccionada,
			String comentario) {
		super();
		this.idInstanciaDeTareaSeleccionada = idInstanciaDeTareaSeleccionada;
		this.comentario = comentario;
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

	public String getIdInstanciaDeTareaSeleccionadaActualSalida() {
		return idInstanciaDeTareaSeleccionadaActualSalida;
	}

	public void setIdInstanciaDeTareaSeleccionadaActualSalida(
			String idInstanciaDeTareaSeleccionadaActualSalida) {
		this.idInstanciaDeTareaSeleccionadaActualSalida = idInstanciaDeTareaSeleccionadaActualSalida;
	}	
	
	public String getResultadoRetrocedeProceso() {
		return resultadoRetrocedeProceso;
	}

	public void setResultadoRetrocedeProceso(String resultadoRetrocedeProceso) {
		this.resultadoRetrocedeProceso = resultadoRetrocedeProceso;
	}

	public String getCssStatus() {
		return cssStatus;
	}

	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}	
	
	public boolean isRecarga() {
		return recarga;
	}

	public void setRecarga(boolean recarga) {
		this.recarga = recarga;
	}	

	public String getParametrosMapParaGuardarJSON() {
		return parametrosMapParaGuardarJSON;
	}

	public void setParametrosMapParaGuardarJSON(String parametrosMapParaGuardarJSON) {
		this.parametrosMapParaGuardarJSON = parametrosMapParaGuardarJSON;
	}

	@Override
	public String toString() {
		return "RetrocedeProcesoDTO [idInstanciaDeTareaSeleccionada="
				+ idInstanciaDeTareaSeleccionada 
				+ ", idInstanciaDeTareaSeleccionadaActualSalida=" + idInstanciaDeTareaSeleccionadaActualSalida
				+ ", comentario=" + comentario
				+ ", resultadoRetrocedeProceso=" + resultadoRetrocedeProceso 
				+ ", cssStatus=" + cssStatus 
				+ ", nombreDeTarea=" + nombreDeTarea
				+ ", recarga=" + recarga
				+ ", parametrosMapParaGuardarJSON=" + parametrosMapParaGuardarJSON
				+ "]";
	}	
	
}

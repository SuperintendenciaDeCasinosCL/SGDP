package cl.gob.scj.sgdp.dto.rest;

public class ExpedienteRestActMetaDTO extends ExpedienteRestDTO  {	
	
	private String idExpediente;
	private String autor;
	private String idUsuarioQueParticipa;
	private String expedienteAntecesor;
	private String esConfidencial;
	private String perspectiva;
	private String proceso;
	private String subproceso;
	private String nombreExpediente;

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}	

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getIdUsuarioQueParticipa() {
		return idUsuarioQueParticipa;
	}

	public void setIdUsuarioQueParticipa(String idUsuarioQueParticipa) {
		this.idUsuarioQueParticipa = idUsuarioQueParticipa;
	}	

	public String getExpedienteAntecesor() {
		return expedienteAntecesor;
	}

	public void setExpedienteAntecesor(String expedienteAntecesor) {
		this.expedienteAntecesor = expedienteAntecesor;
	}

	public String getEsConfidencial() {
		return esConfidencial;
	}

	public void setEsConfidencial(String esConfidencial) {
		this.esConfidencial = esConfidencial;
	}

	public String getPerspectiva() {
		return perspectiva;
	}

	public void setPerspectiva(String perspectiva) {
		this.perspectiva = perspectiva;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getSubproceso() {
		return subproceso;
	}

	public void setSubproceso(String subproceso) {
		this.subproceso = subproceso;
	}	

	public String getNombreExpediente() {
		return nombreExpediente;
	}

	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}

	@Override
	public String toString() {
		return "ExpedienteRestActMetaDTO [idExpediente=" + idExpediente + ", autor=" + autor
				+ ", idUsuarioQueParticipa=" + idUsuarioQueParticipa + ", expedienteAntecesor=" + expedienteAntecesor
				+ ", esConfidencial=" + esConfidencial + ", perspectiva=" + perspectiva + ", proceso=" + proceso
				+ ", subproceso=" + subproceso 
				+ ", nombreExpediente=" + nombreExpediente 
				+ "]" + super.toString();
	}

}
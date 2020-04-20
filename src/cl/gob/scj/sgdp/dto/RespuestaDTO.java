package cl.gob.scj.sgdp.dto;

public abstract class RespuestaDTO {

	private String estado;
	private String glosa;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	
}

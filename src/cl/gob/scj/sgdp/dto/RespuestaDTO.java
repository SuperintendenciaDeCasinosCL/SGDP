package cl.gob.scj.sgdp.dto;

public abstract class RespuestaDTO {

	private String estado;
	private String glosa;
	private String message;
	private String type;
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}

package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class TipoActividadBitacoraDTO implements Serializable {

	private static final long serialVersionUID = 4754136431681304459L;

	private Long id;
	private String nombre;
	private boolean activo = true;
	
	private String glosa;
	private String estado;
	
	public TipoActividadBitacoraDTO() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
}

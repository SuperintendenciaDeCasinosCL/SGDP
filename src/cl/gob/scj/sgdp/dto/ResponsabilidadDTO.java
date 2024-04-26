package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ResponsabilidadDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private Long idProceso;
	
	
	
	public ResponsabilidadDTO() {

	}
	
	public ResponsabilidadDTO(Long id, String nombre, Long idProceso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.idProceso = idProceso;
	}
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
	public Long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

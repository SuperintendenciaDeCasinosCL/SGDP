package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class SuperProcesoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long idMacroProceso;
	private String descripcion;
	private String nombre;
	
	public SuperProcesoDTO() {
		
	}
	
	public SuperProcesoDTO(Long id, Long idMacroProceso, String descripcion, String nombre) {
		super();
		this.id = id;
		this.idMacroProceso = idMacroProceso;
		this.descripcion = descripcion;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMacroProceso() {
		return idMacroProceso;
	}

	public void setIdMacroProceso(Long idMacroProceso) {
		this.idMacroProceso = idMacroProceso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	
}

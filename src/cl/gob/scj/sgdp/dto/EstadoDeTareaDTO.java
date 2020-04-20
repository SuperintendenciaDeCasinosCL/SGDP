package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class EstadoDeTareaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6698489021951293041L;

	private Long idEstadoDeTarea;
	private String nombreEstadoDeTarea;
	private Integer codigoEstadoDeTarea;
	
	public EstadoDeTareaDTO(Long idEstadoDeTarea, String nombreEstadoDeTarea,
			Integer codigoEstadoDeTarea) {
		super();
		this.idEstadoDeTarea = idEstadoDeTarea;
		this.nombreEstadoDeTarea = nombreEstadoDeTarea;
		this.codigoEstadoDeTarea = codigoEstadoDeTarea;
	}
	
	public Long getIdEstadoDeTarea() {
		return idEstadoDeTarea;
	}
	public void setIdEstadoDeTarea(Long idEstadoDeTarea) {
		this.idEstadoDeTarea = idEstadoDeTarea;
	}
	public String getNombreEstadoDeTarea() {
		return nombreEstadoDeTarea;
	}
	public void setNombreEstadoDeTarea(String nombreEstadoDeTarea) {
		this.nombreEstadoDeTarea = nombreEstadoDeTarea;
	}
	public Integer getCodigoEstadoDeTarea() {
		return codigoEstadoDeTarea;
	}
	public void setCodigoEstadoDeTarea(Integer codigoEstadoDeTarea) {
		this.codigoEstadoDeTarea = codigoEstadoDeTarea;
	}
		
}

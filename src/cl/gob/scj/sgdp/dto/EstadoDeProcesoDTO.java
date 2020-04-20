package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class EstadoDeProcesoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4019483107576879878L;

	private Long idEstadoDeProceso;
	private String nombreEstadoDeProceso;
	private Integer codigoEstadoDeProceso;
	
	public EstadoDeProcesoDTO(Long idEstadoDeProceso,
			String nombreEstadoDeProceso, Integer codigoEstadoDeProceso) {
		super();
		this.idEstadoDeProceso = idEstadoDeProceso;
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
		this.codigoEstadoDeProceso = codigoEstadoDeProceso;
	}
	
	public Long getIdEstadoDeProceso() {
		return idEstadoDeProceso;
	}
	public void setIdEstadoDeProceso(Long idEstadoDeProceso) {
		this.idEstadoDeProceso = idEstadoDeProceso;
	}
	public String getNombreEstadoDeProceso() {
		return nombreEstadoDeProceso;
	}
	public void setNombreEstadoDeProceso(String nombreEstadoDeProceso) {
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
	}
	public Integer getCodigoEstadoDeProceso() {
		return codigoEstadoDeProceso;
	}
	public void setCodigoEstadoDeProceso(Integer codigoEstadoDeProceso) {
		this.codigoEstadoDeProceso = codigoEstadoDeProceso;
	}
	
	
	
}

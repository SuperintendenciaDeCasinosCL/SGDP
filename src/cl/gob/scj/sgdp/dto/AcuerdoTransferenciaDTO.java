package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcuerdoTransferenciaDTO implements Serializable {

	@JsonProperty(value = "id")
	private Long id;
	@JsonProperty(value = "fechaSesion")
	private List<String> fechaSesion;

	@JsonProperty(value = "fechaSesionString")
	private String fechaSesionString;
	@JsonProperty(value = "descripcionPit")
	private String descripcionPIT;
	@JsonProperty(value = "cantidadDocs")
	private Integer cantidadDocs;
	@JsonProperty(value = "estado")
	private String estado;
	@JsonProperty(value = "estadoId")
	private Integer estadoId;
	@JsonProperty(value = "fechaTransferirInicio")
	private List<String> fechaTransferirInicio;
	@JsonProperty(value = "fechaTransferirInicioString")
	private String fechaTransferirInicioString;
	@JsonProperty(value = "fechaTransferirTermino")
	private List<String> fechaTransferirTermino;
	@JsonProperty(value = "fechaTransferirTerminoString")
	private String fechaTransferirTerminoString;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getFechaSesion() {
		return fechaSesion;
	}

	public void setFechaSesion(List<String> fechaSesion) {
		this.fechaSesion = fechaSesion;
	}

	public String getFechaSesionString() {
		return fechaSesionString;
	}

	public void setFechaSesionString(String fechaSesionString) {
		this.fechaSesionString = fechaSesionString;
	}

	public String getDescripcionPIT() {
		return descripcionPIT;
	}

	public void setDescripcionPIT(String descripcionPIT) {
		this.descripcionPIT = descripcionPIT;
	}

	public Integer getCantidadDocs() {
		return cantidadDocs;
	}

	public void setCantidadDocs(Integer cantidadDocs) {
		this.cantidadDocs = cantidadDocs;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estadoId) {
		this.estadoId = estadoId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7883864840323228777L;


	public List<String> getFechaTransferirInicio() {
		return fechaTransferirInicio;
	}

	public void setFechaTransferirInicio(List<String> fechaTransferirInicio) {
		this.fechaTransferirInicio = fechaTransferirInicio;
	}

	public String getFechaTransferirInicioString() {
		return fechaTransferirInicioString;
	}

	public void setFechaTransferirInicioString(String fechaTransferirInicioString) {
		this.fechaTransferirInicioString = fechaTransferirInicioString;
	}

	public List<String> getFechaTransferirTermino() {
		return fechaTransferirTermino;
	}

	public void setFechaTransferirTermino(List<String> fechaTransferirTermino) {
		this.fechaTransferirTermino = fechaTransferirTermino;
	}

	public String getFechaTransferirTerminoString() {
		return fechaTransferirTerminoString;
	}

	public void setFechaTransferirTerminoString(String fechaTransferirTerminoString) {
		this.fechaTransferirTerminoString = fechaTransferirTerminoString;
	}

}

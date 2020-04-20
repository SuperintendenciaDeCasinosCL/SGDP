package cl.gob.scj.sgdp.dto;

import java.util.Date;

public class SubprocesoIndicadoresDTO {

	String nombreProceso;
	String nombreExpediente;
	String nombreEstadoDeProceso;
	Date fechaInicioDocumento;
	Date fechaTareaInicio;

	Date fechaFinDocumento;
	Date fechaTareaFin;

	// lista de valores necesario replicar

	private Integer duracionEsperada;
	private String tipoDeInicio;
	private String tipoFin;

	public SubprocesoIndicadoresDTO() {
		super();
	}

	public SubprocesoIndicadoresDTO(String nombreProceso, String nombreExpediente, String nombreEstadoDeProceso,
			Date fechaInicioDocumento, Date fechaTareaInicio, Date fechaFinDocumento, Date fechaTareaFin) {
		super();
		this.nombreProceso = nombreProceso;
		this.nombreExpediente = nombreExpediente;
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
		this.fechaInicioDocumento = fechaInicioDocumento;
		this.fechaTareaInicio = fechaTareaInicio;
		this.fechaFinDocumento = fechaFinDocumento;
		this.fechaTareaFin = fechaTareaFin;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getNombreExpediente() {
		return nombreExpediente;
	}

	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}

	public String getNombreEstadoDeProceso() {
		return nombreEstadoDeProceso;
	}

	public void setNombreEstadoDeProceso(String nombreEstadoDeProceso) {
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
	}

	public Date getFechaInicioDocumento() {
		return fechaInicioDocumento;
	}

	public void setFechaInicioDocumento(Date fechaInicioDocumento) {
		this.fechaInicioDocumento = fechaInicioDocumento;
	}

	public Date getFechaTareaInicio() {
		return fechaTareaInicio;
	}

	public void setFechaTareaInicio(Date fechaTareaInicio) {
		this.fechaTareaInicio = fechaTareaInicio;
	}

	public Date getFechaFinDocumento() {
		return fechaFinDocumento;
	}

	public void setFechaFinDocumento(Date fechaFinDocumento) {
		this.fechaFinDocumento = fechaFinDocumento;
	}

	public Date getFechaTareaFin() {
		return fechaTareaFin;
	}

	public void setFechaTareaFin(Date fechaTareaFin) {
		this.fechaTareaFin = fechaTareaFin;
	}

	public Integer getDuracionEsperada() {
		return duracionEsperada;
	}

	public void setDuracionEsperada(Integer duracionEsperada) {
		this.duracionEsperada = duracionEsperada;
	}

	public String getTipoDeInicio() {
		return tipoDeInicio;
	}

	public void setTipoDeInicio(String tipoDeInicio) {
		this.tipoDeInicio = tipoDeInicio;
	}

	public String getTipoFin() {
		return tipoFin;
	}

	public void setTipoFin(String tipoFin) {
		this.tipoFin = tipoFin;
	}

}

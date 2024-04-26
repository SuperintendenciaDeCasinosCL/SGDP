package cl.gob.scj.sgdp.dto;

import java.util.Objects;

public class SubprocesoIndicadoresSalidaDTO {

	private String nombreSubproceso;
	private String numeroExpediente;
	private String estado;
	private String fechaInicioElementoMedido;
	private String fechaFinElementoMedido;
	private String duracion;
	private Integer duracionProgramadaProceso;
	private String tipoDeInicio;
	private String tipoFin;
	
	
	private String marcaFechaHasta;
	private String marcaDuracion;

	public SubprocesoIndicadoresSalidaDTO() {
		super();
	}

	public String getNombreSubproceso() {
		return nombreSubproceso;
	}

	public void setNombreSubproceso(String nombreSubproceso) {
		this.nombreSubproceso = nombreSubproceso;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaInicioElementoMedido() {
		return fechaInicioElementoMedido;
	}

	public void setFechaInicioElementoMedido(String fechaInicioElementoMedido) {
		this.fechaInicioElementoMedido = fechaInicioElementoMedido;
	}

	public String getFechaFinElementoMedido() {
		return fechaFinElementoMedido;
	}

	public void setFechaFinElementoMedido(String fechaFinElementoMedido) {
		this.fechaFinElementoMedido = fechaFinElementoMedido;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Integer getDuracionProgramadaProceso() {
		return duracionProgramadaProceso;
	}

	public void setDuracionProgramadaProceso(Integer duracionProgramadaProceso) {
		this.duracionProgramadaProceso = duracionProgramadaProceso;
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

	public String getMarcaFechaHasta() {
		return marcaFechaHasta;
	}

	public void setMarcaFechaHasta(String marcaFechaHasta) {
		this.marcaFechaHasta = marcaFechaHasta;
	}

	public String getMarcaDuracion() {
		return marcaDuracion;
	}

	public void setMarcaDuracion(String marcaDuracion) {
		this.marcaDuracion = marcaDuracion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duracion, duracionProgramadaProceso, estado, fechaFinElementoMedido,
				fechaInicioElementoMedido, marcaDuracion, marcaFechaHasta, nombreSubproceso, numeroExpediente,
				tipoDeInicio, tipoFin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubprocesoIndicadoresSalidaDTO other = (SubprocesoIndicadoresSalidaDTO) obj;
		return Objects.equals(duracion, other.duracion)
				&& Objects.equals(duracionProgramadaProceso, other.duracionProgramadaProceso)
				&& Objects.equals(estado, other.estado)
				&& Objects.equals(fechaFinElementoMedido, other.fechaFinElementoMedido)
				&& Objects.equals(fechaInicioElementoMedido, other.fechaInicioElementoMedido)
				&& Objects.equals(marcaDuracion, other.marcaDuracion)
				&& Objects.equals(marcaFechaHasta, other.marcaFechaHasta)
				&& Objects.equals(nombreSubproceso, other.nombreSubproceso)
				&& Objects.equals(numeroExpediente, other.numeroExpediente)
				&& Objects.equals(tipoDeInicio, other.tipoDeInicio) && Objects.equals(tipoFin, other.tipoFin);
	}


	
	
	
}

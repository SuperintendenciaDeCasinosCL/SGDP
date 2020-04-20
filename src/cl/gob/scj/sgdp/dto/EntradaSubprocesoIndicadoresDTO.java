package cl.gob.scj.sgdp.dto;

public class EntradaSubprocesoIndicadoresDTO {

	private String idIndicador;
	private String fechaDesde;
	private String fechaHasta;
	private String flagFechaSubprocesoCombo;

	public EntradaSubprocesoIndicadoresDTO() {
		super();
	}

	public String getIdIndicador() {
		return idIndicador;
	}

	public void setIdIndicador(String idIndicador) {
		this.idIndicador = idIndicador;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getFlagFechaSubprocesoCombo() {
		return flagFechaSubprocesoCombo;
	}

	public void setFlagFechaSubprocesoCombo(String flagFechaSubprocesoCombo) {
		this.flagFechaSubprocesoCombo = flagFechaSubprocesoCombo;
	}

}

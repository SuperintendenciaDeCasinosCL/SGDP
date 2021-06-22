package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TablaRetencionDTO implements Serializable {
	@JsonProperty(value = "serie")
	private String serie;
	@JsonProperty(value = "clasificacionId")
	private Long clasificacionId;
	@JsonProperty(value = "nivelDescripcion")
	private String nivelDescripcion;
	@JsonProperty(value = "tablaRetencionId")
	private Long tablaRetencionId;
	@JsonProperty(value = "disposicionId")
	private Long disposicionId;
	@JsonProperty(value = "fundamento")
	private String fundamento;
	@JsonProperty(value = "institucionId")
	private Long institucionId;
	@JsonProperty(value = "soporte")
	private String soporte;
	@JsonProperty(value = "fechaCreacion")
	private String fechaCreacion;
	@JsonProperty(value = "fechaCreacionString")
	private String fechaCreacionString;

	public String getFechaCreacionString() {
		return fechaCreacionString;
	}

	public void setFechaCreacionString(String fechaCreacionString) {
		this.fechaCreacionString = fechaCreacionString;
	}

	@JsonProperty(value = "archivoGestionAnios")
	private Long archivoGestionAnios;
	@JsonProperty(value = "archivoCentralInstitucionalAnios")
	private Long archivoCentralInstitucionalAnios;
	@JsonProperty(value = "archivoNacionalAnios")
	private Long archivoNacionalAnios;
	@JsonProperty(value = "conservacionPermanente")
	private Boolean conservacionPermanente;
	@JsonProperty(value = "transferencia")
	private Boolean transferencia;

	@JsonProperty
	private String eliminacion;

	@JsonProperty
	private String transferenciaString;
	@JsonProperty
	private String conservacionPermanenteString;

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Long getClasificacionId() {
		return clasificacionId;
	}

	public void setClasificacionId(Long clasificacionId) {
		this.clasificacionId = clasificacionId;
	}

	public String getNivelDescripcion() {
		return nivelDescripcion;
	}

	public void setNivelDescripcion(String nivelDescripcion) {
		this.nivelDescripcion = nivelDescripcion;
	}

	public Long getTablaRetencionId() {
		return tablaRetencionId;
	}

	public void setTablaRetencionId(Long tablaRetencionId) {
		this.tablaRetencionId = tablaRetencionId;
	}

	public Long getDisposicionId() {
		return disposicionId;
	}

	public void setDisposicionId(Long disposicionId) {
		this.disposicionId = disposicionId;
	}

	public String getFundamento() {
		return fundamento;
	}

	public void setFundamento(String fundamento) {
		this.fundamento = fundamento;
	}

	public Long getInstitucionId() {
		return institucionId;
	}

	public void setInstitucionId(Long institucionId) {
		this.institucionId = institucionId;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getArchivoGestionAnios() {
		return archivoGestionAnios;
	}

	public void setArchivoGestionAnios(Long archivoGestionAnios) {
		this.archivoGestionAnios = archivoGestionAnios;
	}

	public Long getArchivoCentralInstitucionalAnios() {
		return archivoCentralInstitucionalAnios;
	}

	public void setArchivoCentralInstitucionalAnios(Long archivoCentralInstitucionalAnios) {
		this.archivoCentralInstitucionalAnios = archivoCentralInstitucionalAnios;
	}

	public Long getArchivoNacionalAnios() {
		return archivoNacionalAnios;
	}

	public void setArchivoNacionalAnios(Long archivoNacionalAnios) {
		this.archivoNacionalAnios = archivoNacionalAnios;
	}

	public Boolean getConservacionPermanente() {
		return conservacionPermanente;
	}

	public void setConservacionPermanente(Boolean conservacionPermanente) {
		this.conservacionPermanente = conservacionPermanente;
	}

	public Boolean getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Boolean transferencia) {
		this.transferencia = transferencia;
	}

	public String getEliminacion() {
		this.eliminacion = "Si";
		if (new Boolean(true).equals(this.conservacionPermanente)) {
			this.eliminacion = "No";
		}
		return eliminacion;
	}

	public void setEliminacion(String eliminacion) {
		this.eliminacion = eliminacion;
	}

	public String getTransferenciaString() {
		this.transferenciaString = "No";
		if (new Boolean(true).equals(this.transferencia)) {
			this.transferenciaString = "Si";
		}
		return transferenciaString;
	}

	public String getConservacionPermanenteString() {
		this.conservacionPermanenteString = "No";
		if (new Boolean(true).equals(this.conservacionPermanente)) {
			this.conservacionPermanenteString = "Si";
		}
		return conservacionPermanenteString;
	}

	public void setConservacionPermanenteString(String conservacionPermanenteString) {
		this.conservacionPermanenteString = conservacionPermanenteString;
	}

	public TablaRetencionDTO() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8186292004316490337L;
}

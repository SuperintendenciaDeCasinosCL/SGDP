package cl.gob.scj.sgdp.dto;

import java.util.Date;

public class IndicadorSubprocesoDTO {

	private Integer subprocesoId;

	private String idIndicador;

	private String nombreDocumentoFin;

	private Integer idDocumentoInicio;

	private Integer idDocumentoFin;

	private String nombreDocumentoInicio;

	private Integer duracionEsperada;

	private Integer idTareaInicio;

	private String nombreSubproceso;

	private String nombreTareaInicio;

	private Integer idTareaFin;

	private String nombreTareaFin;

	private String tipoDeInicio;

	private String tipoFin;

	private Date fechaCreacion;

	private Date fechaModificacion;

	private String nombreUsuarioLogin;

	public IndicadorSubprocesoDTO() {
		super();
	}

	public Integer getSubprocesoId() {
		return subprocesoId;
	}

	public void setSubprocesoId(Integer subprocesoId) {
		this.subprocesoId = subprocesoId;
	}

	public String getIdIndicador() {
		return idIndicador;
	}

	public void setIdIndicador(String idIndicador) {
		this.idIndicador = idIndicador;
	}

	public String getNombreDocumentoFin() {
		return nombreDocumentoFin;
	}

	public void setNombreDocumentoFin(String nombreDocumentoFin) {
		this.nombreDocumentoFin = nombreDocumentoFin;
	}

	public Integer getIdDocumentoInicio() {
		return idDocumentoInicio;
	}

	public void setIdDocumentoInicio(Integer idDocumentoInicio) {
		this.idDocumentoInicio = idDocumentoInicio;
	}

	public Integer getIdDocumentoFin() {
		return idDocumentoFin;
	}

	public void setIdDocumentoFin(Integer idDocumentoFin) {
		this.idDocumentoFin = idDocumentoFin;
	}

	public String getNombreDocumentoInicio() {
		return nombreDocumentoInicio;
	}

	public void setNombreDocumentoInicio(String nombreDocumentoInicio) {
		this.nombreDocumentoInicio = nombreDocumentoInicio;
	}

	public Integer getDuracionEsperada() {
		return duracionEsperada;
	}

	public void setDuracionEsperada(Integer duracionEsperada) {
		this.duracionEsperada = duracionEsperada;
	}

	public Integer getIdTareaInicio() {
		return idTareaInicio;
	}

	public void setIdTareaInicio(Integer idTareaInicio) {
		this.idTareaInicio = idTareaInicio;
	}

	public String getNombreSubproceso() {
		return nombreSubproceso;
	}

	public void setNombreSubproceso(String nombreSubproceso) {
		this.nombreSubproceso = nombreSubproceso;
	}

	public String getNombreTareaInicio() {
		return nombreTareaInicio;
	}

	public void setNombreTareaInicio(String nombreTareaInicio) {
		this.nombreTareaInicio = nombreTareaInicio;
	}

	public Integer getIdTareaFin() {
		return idTareaFin;
	}

	public void setIdTareaFin(Integer idTareaFin) {
		this.idTareaFin = idTareaFin;
	}

	public String getNombreTareaFin() {
		return nombreTareaFin;
	}

	public void setNombreTareaFin(String nombreTareaFin) {
		this.nombreTareaFin = nombreTareaFin;
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNombreUsuarioLogin() {
		return nombreUsuarioLogin;
	}

	public void setNombreUsuarioLogin(String nombreUsuarioLogin) {
		this.nombreUsuarioLogin = nombreUsuarioLogin;
	}

	@Override
	public String toString() {
		return "IndicadorSubprocesoDTO [subprocesoId=" + subprocesoId + ", idIndicador=" + idIndicador
				+ ", nombreDocumentoFin=" + nombreDocumentoFin + ", idDocumentoInicio=" + idDocumentoInicio
				+ ", idDocumentoFin=" + idDocumentoFin + ", nombreDocumentoInicio=" + nombreDocumentoInicio
				+ ", duracionEsperada=" + duracionEsperada + ", idTareaInicio=" + idTareaInicio + ", nombreSubproceso="
				+ nombreSubproceso + ", nombreTareaInicio=" + nombreTareaInicio + ", idTareaFin=" + idTareaFin
				+ ", nombreTareaFin=" + nombreTareaFin + ", tipoDeInicio=" + tipoDeInicio + ", tipoFin=" + tipoFin
				+ ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion
				+ ", nombreUsuarioLogin=" + nombreUsuarioLogin + "]";
	}

}

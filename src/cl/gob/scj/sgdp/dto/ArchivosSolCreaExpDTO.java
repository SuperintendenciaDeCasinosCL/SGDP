package cl.gob.scj.sgdp.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

public class ArchivosSolCreaExpDTO extends RespuestaDTO {
	
	private long idSolicitudCreacionExp;
	private int pagina;
	private int tamanoPagina;
	private String nombreDocumento;
	private String folioOrigen;
	private String tipoOrigen;
	private String fechaCreacionOrigen;	
	private Long idArchivosSolCreaExp;	
	private String descripcionOrigen;
	private String fechaFolio;	
	private String urlDocumentoOrigen;	
	private Boolean foliado;	
	private Date fechaSubido;
	private String idArchivoCms;
	private Long idDocumentoOrigen;
	private String idUsuario;
	private Boolean confidencialidadFinal;
	private String usuariosConfidenciales;
	private String reservado;
	
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public String getFolioOrigen() {
		return folioOrigen;
	}
	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}
	public String getTipoOrigen() {
		return tipoOrigen;
	}
	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}
	public String getFechaCreacionOrigen() {
		return fechaCreacionOrigen;
	}
	public void setFechaCreacionOrigen(String fechaCreacionOrigen) {
		this.fechaCreacionOrigen = fechaCreacionOrigen;
	}
	public Long getIdArchivosSolCreaExp() {
		return idArchivosSolCreaExp;
	}
	public void setIdArchivosSolCreaExp(Long idArchivosSolCreaExp) {
		this.idArchivosSolCreaExp = idArchivosSolCreaExp;
	}
	public String getDescripcionOrigen() {
		return descripcionOrigen;
	}
	public void setDescripcionOrigen(String descripcionOrigen) {
		this.descripcionOrigen = descripcionOrigen;
	}
	public String getFechaFolio() {
		return fechaFolio;
	}
	public void setFechaFolio(String fechaFolio) {
		this.fechaFolio = fechaFolio;
	}
	public String getUrlDocumentoOrigen() {
		return urlDocumentoOrigen;
	}
	public void setUrlDocumentoOrigen(String urlDocumentoOrigen) {
		this.urlDocumentoOrigen = urlDocumentoOrigen;
	}
	public Boolean getFoliado() {
		return foliado;
	}
	public void setFoliado(Boolean foliado) {
		this.foliado = foliado;
	}
	public Date getFechaSubido() {
		return fechaSubido;
	}
	public void setFechaSubido(Date fechaSubido) {
		this.fechaSubido = fechaSubido;
	}
	public String getIdArchivoCms() {
		return idArchivoCms;
	}
	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}
	public Long getIdDocumentoOrigen() {
		return idDocumentoOrigen;
	}
	public void setIdDocumentoOrigen(Long idDocumentoOrigen) {
		this.idDocumentoOrigen = idDocumentoOrigen;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdSolicitudCreacionExp() {
		return idSolicitudCreacionExp;
	}
	public void setIdSolicitudCreacionExp(long idSolicitudCreacionExp) {
		this.idSolicitudCreacionExp = idSolicitudCreacionExp;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public int getTamanoPagina() {
		return tamanoPagina;
	}
	public void setTamanoPagina(int tamanoPagina) {
		this.tamanoPagina = tamanoPagina;
	}

	public Boolean getConfidencialidadFinal() {
		return confidencialidadFinal;
	}

	public void setConfidencialidadFinal(Boolean confidencialidadFinal) {
		this.confidencialidadFinal = confidencialidadFinal;
	}

	public String getUsuariosConfidenciales() {
		return usuariosConfidenciales;
	}

	public void setUsuariosConfidenciales(String usuariosConfidenciales) {
		this.usuariosConfidenciales = usuariosConfidenciales;
	}

	public String getReservado() {
		return reservado;
	}

	public void setReservado(String reservado) {
		this.reservado = reservado;
	}

	@Override
	public String toString() {
		return "ArchivosSolCreaExpDTO [idSolicitudCreacionExp=" + idSolicitudCreacionExp + ", pagina=" + pagina
				+ ", tamanoPagina=" + tamanoPagina + ", nombreDocumento=" + nombreDocumento + ", folioOrigen="
				+ folioOrigen + ", tipoOrigen=" + tipoOrigen + ", fechaCreacionOrigen=" + fechaCreacionOrigen
				+ ", idArchivosSolCreaExp=" + idArchivosSolCreaExp + ", descripcionOrigen=" + descripcionOrigen
				+ ", fechaFolio=" + fechaFolio + ", urlDocumentoOrigen=" + urlDocumentoOrigen + ", foliado=" + foliado
				+ ", fechaSubido=" + fechaSubido + ", idArchivoCms=" + idArchivoCms
				+ ", idDocumentoOrigen=" + idDocumentoOrigen
				+ ", idUsuario=" + idUsuario
				+ ", confidencialidadFinal=" + confidencialidadFinal
				+ ", usuariosConfidenciales=" + usuariosConfidenciales
				+ ", reservado=" + reservado
				+ "]";
	}

}
package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class InstanciaDeProcesoDTO implements Serializable {

	private long idInstanciaDeProceso;
	private String nombreExpediente;
	private Date fechaFin;
	private Date fechaInicio;
	private Date fechaVencimientoUsuario;
	private Date fechaVencimiento;
	private String idExpediente;
	private String idUsuarioInicia;
	private String idUsuarioTermina;
	private Boolean tieneDocumentosEnCMS;
	private String emisor;
	private String nombreDeProceso;
	private int diasHabilesMaxDuracion;
	private String asunto;
	private String comentarioSolicitudCreacionExpediente;
	
	public long getIdInstanciaDeProceso() {
		return idInstanciaDeProceso;
	}
	public void setIdInstanciaDeProceso(long idInstanciaDeProceso) {
		this.idInstanciaDeProceso = idInstanciaDeProceso;
	}
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaVencimientoUsuario() {
		return fechaVencimientoUsuario;
	}
	public void setFechaVencimientoUsuario(Date fechaVencimientoUsuario) {
		this.fechaVencimientoUsuario = fechaVencimientoUsuario;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	public String getIdUsuarioInicia() {
		return idUsuarioInicia;
	}
	public void setIdUsuarioInicia(String idUsuarioInicia) {
		this.idUsuarioInicia = idUsuarioInicia;
	}
	public String getIdUsuarioTermina() {
		return idUsuarioTermina;
	}
	public void setIdUsuarioTermina(String idUsuarioTermina) {
		this.idUsuarioTermina = idUsuarioTermina;
	}
	public Boolean getTieneDocumentosEnCMS() {
		return tieneDocumentosEnCMS;
	}
	public void setTieneDocumentosEnCMS(Boolean tieneDocumentosEnCMS) {
		this.tieneDocumentosEnCMS = tieneDocumentosEnCMS;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}	
	public String getNombreDeProceso() {
		return nombreDeProceso;
	}
	public void setNombreDeProceso(String nombreDeProceso) {
		this.nombreDeProceso = nombreDeProceso;
	}	
	public int getDiasHabilesMaxDuracion() {
		return diasHabilesMaxDuracion;
	}
	public void setDiasHabilesMaxDuracion(int diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}	
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}	
	public String getComentarioSolicitudCreacionExpediente() {
		return comentarioSolicitudCreacionExpediente;
	}
	public void setComentarioSolicitudCreacionExpediente(String comentarioSolicitudCreacionExpediente) {
		this.comentarioSolicitudCreacionExpediente = comentarioSolicitudCreacionExpediente;
	}
	@Override
	public String toString() {
		return "InstanciaDeProcesoDTO [idInstanciaDeProceso=" + idInstanciaDeProceso + ", nombreExpediente="
				+ nombreExpediente + ", fechaFin=" + fechaFin + ", fechaInicio=" + fechaInicio
				+ ", fechaVencimientoUsuario=" + fechaVencimientoUsuario + ", fechaVencimiento=" + fechaVencimiento
				+ ", idExpediente=" + idExpediente + ", idUsuarioInicia=" + idUsuarioInicia + ", idUsuarioTermina="
				+ idUsuarioTermina + ", tieneDocumentosEnCMS=" + tieneDocumentosEnCMS + ", emisor=" + emisor
				+ ", nombreDeProceso=" + nombreDeProceso
				+ ", diasHabilesMaxDuracion=" + diasHabilesMaxDuracion
				+ ", asunto=" + asunto 
				+ ", comentarioSolicitudCreacionExpediente=" + comentarioSolicitudCreacionExpediente 
				+ "]";
	}
}

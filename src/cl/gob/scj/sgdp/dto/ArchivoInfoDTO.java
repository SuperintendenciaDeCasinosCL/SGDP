package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import cl.gob.scj.sgdp.util.FechaUtil;

public class ArchivoInfoDTO implements Serializable {

	private String nombre;
	private String idArchivo;
	private String cdr;
	private String mimeType;
	private String numeroDocumento;
	private String fechaUltimaModificacion;
	private String usuarioUltimaModificacion;
	private String descripcion;
	private String linkDescarga;
	private String linkDescargaNavegador;
	private String linkSharpoint;
	private String creador;
	private String fechaCreacion;
	private String tipoDeDocumento;
	private String labelUltimaVersion;
	private boolean esEditable;
	private int codigoMimeType;
	private boolean esVisable;
	private boolean aplicaFEA;
	private boolean aplicaFirmaApplet;
	private String idExpediente;
	private String numeroDeFirma;
	private String fechaDeRecepcion;
	private boolean cargadoEnCMS;
	private String cartaRelacionada;
	private long idTipoDeDocumento;
	private boolean conformaExpediente;
	private boolean esDocumentoConductor;
	private boolean subidoALaTareaPorElUsuario;
	private String emisor;
	private boolean aplicaFEAPorTipoDeDocumento;
	private boolean estaVisado;
	private boolean estaFirmadoConFEAWebStart;
	private boolean estaFirmadoConFEACentralizada;
	private boolean aplicaVisacionPorTipoDeDocumento;
	private String fechaUltimaModificacionCompleta;
	private String labelVersionEnInstanciaDeTarea;
	private Date fechaDocumento;
	private String esDocumentoOficial;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}
	public String getCdr() {
		return cdr;
	}
	public void setCdr(String cdr) {
		this.cdr = cdr;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public String getUsuarioUltimaModificacion() {
		return usuarioUltimaModificacion;
	}
	public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLinkDescarga() {
		return linkDescarga;
	}
	public void setLinkDescarga(String linkDescarga) {
		this.linkDescarga = linkDescarga;
	}
	public String getLinkDescargaNavegador() {
		return linkDescargaNavegador;
	}
	public void setLinkDescargaNavegador(String linkDescargaNavegador) {
		this.linkDescargaNavegador = linkDescargaNavegador;
	}
	public String getLinkSharpoint() {
		return linkSharpoint;
	}
	public void setLinkSharpoint(String linkSharpoint) {
		this.linkSharpoint = linkSharpoint;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
		if (fechaCreacion!=null && !fechaCreacion.isEmpty()) {
			try {
				this.fechaDocumento = FechaUtil.simpleDateFormatForm.parse(fechaCreacion);
			} catch (ParseException e) {
				this.fechaDocumento = null;
			}
		}
	}	
	public String getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	public void setTipoDeDocumento(String tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}	
	public String getLabelUltimaVersion() {
		return labelUltimaVersion;
	}
	public void setLabelUltimaVersion(String labelUltimaVersion) {
		this.labelUltimaVersion = labelUltimaVersion;
	}	
	public boolean getEsEditable() {
		return esEditable;
	}
	public void setEsEditable(boolean esEditable) {
		this.esEditable = esEditable;
	}
	public int getCodigoMimeType() {
		return codigoMimeType;
	}
	public void setCodigoMimeType(int codigoMimeType) {
		this.codigoMimeType = codigoMimeType;
	}	
	public boolean getEsVisable() {
		return esVisable;
	}
	public void setEsVisable(boolean esVisable) {
		this.esVisable = esVisable;
	}
	public boolean getAplicaFEA() {
		return aplicaFEA;
	}
	public void setAplicaFEA(boolean aplicaFEA) {
		this.aplicaFEA = aplicaFEA;
	}
	public boolean getAplicaFirmaApplet() {
		return aplicaFirmaApplet;
	}
	public void setAplicaFirmaApplet(boolean aplicaFirmaApplet) {
		this.aplicaFirmaApplet = aplicaFirmaApplet;
	}	
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}	
	public String getNumeroDeFirma() {
		return numeroDeFirma;
	}
	public void setNumeroDeFirma(String numeroDeFirma) {
		this.numeroDeFirma = numeroDeFirma;
	}
	public String getFechaDeRecepcion() {
		return fechaDeRecepcion;
	}
	public void setFechaDeRecepcion(String fechaDeRecepcion) {
		this.fechaDeRecepcion = fechaDeRecepcion;
	}	
	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}
	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}
	public boolean getConformaExpediente() {
		return conformaExpediente;
	}
	public void setConformaExpediente(boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}
	public boolean isEsDocumentoConductor() {
		return esDocumentoConductor;
	}
	public void setEsDocumentoConductor(boolean esDocumentoConductor) {
		this.esDocumentoConductor = esDocumentoConductor;
	}	
	public boolean getCargadoEnCMS() {
		return cargadoEnCMS;
	}
	public void setCargadoEnCMS(boolean cargadoEnCMS) {
		this.cargadoEnCMS = cargadoEnCMS;
	}	
	public String getCartaRelacionada() {
		return cartaRelacionada;
	}
	public void setCartaRelacionada(String cartaRelacionada) {
		this.cartaRelacionada = cartaRelacionada;
	}	
	public boolean getSubidoALaTareaPorElUsuario() {
		return subidoALaTareaPorElUsuario;
	}
	public void setSubidoALaTareaPorElUsuario(boolean subidoALaTareaPorElUsuario) {
		this.subidoALaTareaPorElUsuario = subidoALaTareaPorElUsuario;
	}	
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}	
	public boolean getEstaVisado() {
		return estaVisado;
	}
	public void setEstaVisado(boolean estaVisado) {
		this.estaVisado = estaVisado;
	}	
	public boolean getAplicaFEAPorTipoDeDocumento() {
		return aplicaFEAPorTipoDeDocumento;
	}
	public void setAplicaFEAPorTipoDeDocumento(boolean aplicaFEAPorTipoDeDocumento) {
		this.aplicaFEAPorTipoDeDocumento = aplicaFEAPorTipoDeDocumento;
	}
	public boolean getEstaFirmadoConFEAWebStart() {
		return estaFirmadoConFEAWebStart;
	}
	public void setEstaFirmadoConFEAWebStart(boolean estaFirmadoConFEAWebStart) {
		this.estaFirmadoConFEAWebStart = estaFirmadoConFEAWebStart;
	}
	public boolean getEstaFirmadoConFEACentralizada() {
		return estaFirmadoConFEACentralizada;
	}
	public void setEstaFirmadoConFEACentralizada(boolean estaFirmadoConFEACentralizada) {
		this.estaFirmadoConFEACentralizada = estaFirmadoConFEACentralizada;
	}	
	public boolean isAplicaVisacionPorTipoDeDocumento() {
		return aplicaVisacionPorTipoDeDocumento;
	}
	public void setAplicaVisacionPorTipoDeDocumento(boolean aplicaVisacionPorTipoDeDocumento) {
		this.aplicaVisacionPorTipoDeDocumento = aplicaVisacionPorTipoDeDocumento;
	}	
	public String getFechaUltimaModificacionCompleta() {
		return fechaUltimaModificacionCompleta;
	}
	public void setFechaUltimaModificacionCompleta(String fechaUltimaModificacionCompleta) {
		this.fechaUltimaModificacionCompleta = fechaUltimaModificacionCompleta;
	}	
	public String getLabelVersionEnInstanciaDeTarea() {
		return labelVersionEnInstanciaDeTarea;
	}
	public void setLabelVersionEnInstanciaDeTarea(String labelVersionEnInstanciaDeTarea) {
		this.labelVersionEnInstanciaDeTarea = labelVersionEnInstanciaDeTarea;
	}	
	public Date getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}	
	public String getEsDocumentoOficial() {
		return esDocumentoOficial;
	}
	public void setEsDocumentoOficial(String esDocumentoOficial) {
		this.esDocumentoOficial = esDocumentoOficial;
	}
	@Override
	public String toString() {
		return "ArchivoInfoDTO [nombre=" + nombre + ", idArchivo=" + idArchivo + ", cdr=" + cdr + ", mimeType="
				+ mimeType + ", numeroDocumento=" + numeroDocumento + ", fechaUltimaModificacion="
				+ fechaUltimaModificacion + ", usuarioUltimaModificacion=" + usuarioUltimaModificacion
				+ ", descripcion=" + descripcion + ", linkDescarga=" + linkDescarga + ", linkDescargaNavegador="
				+ linkDescargaNavegador + ", linkSharpoint=" + linkSharpoint + ", creador=" + creador
				+ ", fechaCreacion=" + fechaCreacion + ", tipoDeDocumento=" + tipoDeDocumento + ", labelUltimaVersion="
				+ labelUltimaVersion + ", esEditable=" + esEditable + ", codigoMimeType=" + codigoMimeType
				+ ", esVisable=" + esVisable + ", aplicaFEA=" + aplicaFEA + ", aplicaFirmaApplet=" + aplicaFirmaApplet
				+ ", idExpediente=" + idExpediente + ", numeroDeFirma=" + numeroDeFirma + ", fechaDeRecepcion="
				+ fechaDeRecepcion + ", cargadoEnCMS=" + cargadoEnCMS + ", cartaRelacionada=" + cartaRelacionada
				+ ", idTipoDeDocumento=" + idTipoDeDocumento + ", conformaExpediente=" + conformaExpediente
				+ ", subidoALaTareaPorElUsuario=" + subidoALaTareaPorElUsuario
				+ ", esDocumentoConductor=" + esDocumentoConductor 
				+ ", emisor=" + emisor 
				+ ", aplicaFEAPorTipoDeDocumento=" + aplicaFEAPorTipoDeDocumento
				+ ", estaVisado=" + estaVisado
				+ ", estaFirmadoConFEAWebStart=" + estaFirmadoConFEAWebStart
				+ ", estaFirmadoConFEACentralizada=" + estaFirmadoConFEACentralizada
				+ ", aplicaVisacionPorTipoDeDocumento=" + aplicaVisacionPorTipoDeDocumento
				+ ", fechaUltimaModificacionCompleta=" + fechaUltimaModificacionCompleta
				+ ", labelVersionEnInstanciaDeTarea=" + labelVersionEnInstanciaDeTarea
				+ ", esDocumentoOficial=" + esDocumentoOficial
				+ "]";
	}	
}

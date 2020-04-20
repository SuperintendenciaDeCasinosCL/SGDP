package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.Date;

public class DetalleDeArchivoDTORest implements Serializable {

	private String nombre;
	private String autor;
	private String cdr;
	private String numeroDocumento;
	private String fechaDeDocumento;
	private String tipoDeDocumento;
	private String fechaUltimaModificacion;
	private String descripcion;
	private String linkDescarga;
	private String linkDescargaNavegador;
	private String linkSharpoint;
	private String emisor;	
	private String resultadoObtenerDetalleDeArchivo;
	private String cssStatus;
	private String cartaRelacionada;
	private String idArchivo;
	private String mimeType;
	private String idExpedienteQueLoContiene;
	private long idInstanciaDeTarea;
	private String numeroDeFirma;
	private String fechaDeRecepcion;
	private String labelUltimaVersion;
	private boolean esEditable;	
	private int codigoMimeType;
	private boolean convertirAPDF;
	private String fechaUltimaModificacionCompleta;
	private String usuarioUltimaModificacion;
	private String firmaSimple;
	private String esDocumentoOficial;
	private String documentoSubproceso;
	private String documentoMateria;
	private String documentoAutor;
	private String documentoNombreExpediente;
	private boolean tieneFEA;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getCdr() {
		return cdr;
	}
	public void setCdr(String cdr) {
		this.cdr = cdr;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}	
	public String getFechaDeDocumento() {
		return fechaDeDocumento;
	}
	public void setFechaDeDocumento(String fechaDeDocumento) {
		this.fechaDeDocumento = fechaDeDocumento;
	}
	public String getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	public void setTipoDeDocumento(String tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}
	public String getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
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
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}	
	public String getResultadoObtenerDetalleDeArchivo() {
		return resultadoObtenerDetalleDeArchivo;
	}
	public void setResultadoObtenerDetalleDeArchivo(
			String resultadoObtenerDetalleDeArchivo) {
		this.resultadoObtenerDetalleDeArchivo = resultadoObtenerDetalleDeArchivo;
	}
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}	
	public String getCartaRelacionada() {
		return cartaRelacionada;
	}
	public void setCartaRelacionada(String cartaRelacionada) {
		this.cartaRelacionada = cartaRelacionada;
	}	
	public String getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}	
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getIdExpedienteQueLoContiene() {
		return idExpedienteQueLoContiene;
	}
	public void setIdExpedienteQueLoContiene(String idExpedienteQueLoContiene) {
		this.idExpedienteQueLoContiene = idExpedienteQueLoContiene;
	}	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
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
	public String getLabelUltimaVersion() {
		return labelUltimaVersion;
	}
	public void setLabelUltimaVersion(String labelUltimaVersion) {
		this.labelUltimaVersion = labelUltimaVersion;
	}
	public boolean isEsEditable() {
		return esEditable;
	}
	public void setEsEditable(boolean esEditable) {
		this.esEditable = esEditable;
	}
	public boolean getConvertirAPDF() {
		return convertirAPDF;
	}
	public void setConvertirAPDF(boolean convertirAPDF) {
		this.convertirAPDF = convertirAPDF;
	}
	public int getCodigoMimeType() {
		return codigoMimeType;
	}
	public void setCodigoMimeType(int codigoMimeType) {
		this.codigoMimeType = codigoMimeType;
	}		
	public String getFechaUltimaModificacionCompleta() {
		return fechaUltimaModificacionCompleta;
	}
	public void setFechaUltimaModificacionCompleta(String fechaUltimaModificacionCompleta) {
		this.fechaUltimaModificacionCompleta = fechaUltimaModificacionCompleta;
	}	
	public String getUsuarioUltimaModificacion() {
		return usuarioUltimaModificacion;
	}
	public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
	}	
	public String getFirmaSimple() {
		return firmaSimple;
	}
	public void setFirmaSimple(String firmaSimple) {
		this.firmaSimple = firmaSimple;
	}
		
	public String getEsDocumentoOficial() {
		return esDocumentoOficial;
	}
	public void setEsDocumentoOficial(String esDocumentoOficial) {
		this.esDocumentoOficial = esDocumentoOficial;
	}		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idArchivo == null) ? 0 : idArchivo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleDeArchivoDTORest other = (DetalleDeArchivoDTORest) obj;
		if (idArchivo == null) {
			if (other.idArchivo != null)
				return false;
		} else if (!idArchivo.equals(other.idArchivo))
			return false;
		return true;
	}
	public String getDocumentoSubproceso() {
		return documentoSubproceso;
	}
	public void setDocumentoSubproceso(String documentoSubproceso) {
		this.documentoSubproceso = documentoSubproceso;
	}
	public String getDocumentoMateria() {
		return documentoMateria;
	}
	public void setDocumentoMateria(String documentoMateria) {
		this.documentoMateria = documentoMateria;
	}
	public String getDocumentoAutor() {
		return documentoAutor;
	}
	public void setDocumentoAutor(String documentoAutor) {
		this.documentoAutor = documentoAutor;
	}
	public String getDocumentoNombreExpediente() {
		return documentoNombreExpediente;
	}
	public void setDocumentoNombreExpediente(String documentoNombreExpediente) {
		this.documentoNombreExpediente = documentoNombreExpediente;
	}	
	public boolean isTieneFEA() {
		return tieneFEA;
	}
	public void setTieneFEA(boolean tieneFEA) {
		this.tieneFEA = tieneFEA;
	}
	@Override
	public String toString() {
		return "DetalleDeArchivoDTORest [nombre=" + nombre + ", autor=" + autor + ", cdr=" + cdr + ", numeroDocumento="
				+ numeroDocumento + ", fechaDeDocumento=" + fechaDeDocumento + ", tipoDeDocumento=" + tipoDeDocumento
				+ ", fechaUltimaModificacion=" + fechaUltimaModificacion + ", descripcion=" + descripcion
				+ ", linkDescarga=" + linkDescarga + ", linkDescargaNavegador=" + linkDescargaNavegador
				+ ", linkSharpoint=" + linkSharpoint + ", emisor=" + emisor + ", resultadoObtenerDetalleDeArchivo="
				+ resultadoObtenerDetalleDeArchivo + ", cssStatus=" + cssStatus + ", cartaRelacionada="
				+ cartaRelacionada + ", idArchivo=" + idArchivo + ", mimeType=" + mimeType
				+ ", idExpedienteQueLoContiene=" + idExpedienteQueLoContiene + ", idInstanciaDeTarea="
				+ idInstanciaDeTarea + ", numeroDeFirma=" + numeroDeFirma + ", fechaDeRecepcion=" + fechaDeRecepcion
				+ ", labelUltimaVersion=" + labelUltimaVersion + ", esEditable=" + esEditable + ", codigoMimeType="
				+ codigoMimeType + ", convertirAPDF=" + convertirAPDF + ", fechaUltimaModificacionCompleta="
				+ fechaUltimaModificacionCompleta + ", usuarioUltimaModificacion=" + usuarioUltimaModificacion
				+ ", firmaSimple=" + firmaSimple + ", esDocumentoOficial=" + esDocumentoOficial
				+ ", documentoSubproceso=" + documentoSubproceso + ", documentoMateria=" + documentoMateria
				+ ", documentoAutor=" + documentoAutor + ", documentoNombreExpediente=" + documentoNombreExpediente
				+ ", tieneFEA=" + tieneFEA				
				+ "]";
	}
		
}

package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class FirmaAvanzadaDTO implements Serializable {
	
	private String idExpediente; 
	private String idDocumento;
	private String nombreArchivo;
	private String rut;
	private String entidad;
	private String proposito;
	private String expiracion;	
	private String mimeType;
	private String otp;
	private String sessionToken;
	private String resultadoFirmarDocumentoConFEA;
	private String cssStatus;
	private String tipoDeDocumento;
	private String emisor;
	private String cartaRelacionada;
	private String cdr;
	private String numeroDocumento;
	private long idInstanciaDeTarea;
	private long idTipoDeDocumento;
	private String tipoDeFirma;
	private String nombreExpediente;
	private String categoriaDeDocumento;
	
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}	
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getProposito() {
		return proposito;
	}
	public void setProposito(String proposito) {
		this.proposito = proposito;
	}
	public String getExpiracion() {
		return expiracion;
	}
	public void setExpiracion(String expiracion) {
		this.expiracion = expiracion;
	}	
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}		
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public String getResultadoFirmarDocumentoConFEA() {
		return resultadoFirmarDocumentoConFEA;
	}
	public void setResultadoFirmarDocumentoConFEA(
			String resultadoFirmarDocumentoConFEA) {
		this.resultadoFirmarDocumentoConFEA = resultadoFirmarDocumentoConFEA;
	}
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}	
	public String getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	public void setTipoDeDocumento(String tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}	
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getCartaRelacionada() {
		return cartaRelacionada;
	}
	public void setCartaRelacionada(String cartaRelacionada) {
		this.cartaRelacionada = cartaRelacionada;
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
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}	
	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}
	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}	
	public String getTipoDeFirma() {
		return tipoDeFirma;
	}
	public void setTipoDeFirma(String tipoDeFirma) {
		this.tipoDeFirma = tipoDeFirma;
	}	
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}	
	public String getCategoriaDeDocumento() {
		return categoriaDeDocumento;
	}
	public void setCategoriaDeDocumento(String categoriaDeDocumento) {
		this.categoriaDeDocumento = categoriaDeDocumento;
	}
	@Override
	public String toString() {
		return "FirmaAvanzadaDTO [idExpediente=" + idExpediente
				+ ", idDocumento=" + idDocumento + ", nombreArchivo="
				+ nombreArchivo + ", rut=" + rut + ", entidad=" + entidad
				+ ", proposito=" + proposito + ", expiracion=" + expiracion
				+ ", mimeType=" + mimeType + ", otp=" + otp + ", sessionToken="
				+ sessionToken + ", resultadoFirmarDocumentoConFEA="
				+ resultadoFirmarDocumentoConFEA + ", cssStatus=" + cssStatus
				+ ", tipoDeDocumento=" + tipoDeDocumento + ", emisor=" + emisor
				+ ", cartaRelacionada=" + cartaRelacionada + ", cdr=" + cdr
				+ ", numeroDocumento=" + numeroDocumento 
				+ ", idInstanciaDeTarea=" + idInstanciaDeTarea 
				+ ", idTipoDeDocumento=" + idTipoDeDocumento 
				+ ", tipoDeFirma=" + tipoDeFirma
				+ ", nombreExpediente=" + nombreExpediente 
				+ "]";
	}		
}

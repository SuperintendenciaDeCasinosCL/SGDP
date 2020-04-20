package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;
import java.util.List;

public class ElementoBuscar implements Serializable   {

	private String tipoObjeto;
	private String nombreDeObjeto;
	private String idObjeto;
	private String idExpedienteQueLoContiene;
	private String nombreExpedienteQueLoContiene;
	private String emisor;
	private String cdr;
	private String numeroDeDocumento;
	private String tipoDeDocumento;
	private String fechaDeCreacion;
	private String cartaRelacionada;
	private String otro;
	private String creador;
	private String materia;
	private String autor;
	private String perspectiva;
	private String proceso;
	private String subproceso;
	private List<ExpedienteContiene> expedientesContiene;
	private String linkDescargaNavegador;
	private String esConfidencial;
	private List<String> usuariosQueHanModificado;
	private String usuariosQueHanParticipado;
	
	public String getTipoObjeto() {
		return tipoObjeto;
	}
	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	public String getNombreDeObjeto() {
		return nombreDeObjeto;
	}
	public void setNombreDeObjeto(String nombreDeObjeto) {
		this.nombreDeObjeto = nombreDeObjeto;
	}
	public String getIdObjeto() {
		return idObjeto;
	}
	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}
	public String getIdExpedienteQueLoContiene() {
		return idExpedienteQueLoContiene;
	}
	public void setIdExpedienteQueLoContiene(String idExpedienteQueLoContiene) {
		this.idExpedienteQueLoContiene = idExpedienteQueLoContiene;
	}	
	public String getNombreExpedienteQueLoContiene() {
		return nombreExpedienteQueLoContiene;
	}
	public void setNombreExpedienteQueLoContiene(
			String nombreExpedienteQueLoContiene) {
		this.nombreExpedienteQueLoContiene = nombreExpedienteQueLoContiene;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getCdr() {
		return cdr;
	}
	public void setCdr(String cdr) {
		this.cdr = cdr;
	}
	public String getNumeroDeDocumento() {
		return numeroDeDocumento;
	}
	public void setNumeroDeDocumento(String numeroDeDocumento) {
		this.numeroDeDocumento = numeroDeDocumento;
	}
	public String getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	public void setTipoDeDocumento(String tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}
	public String getFechaDeCreacion() {
		return fechaDeCreacion;
	}
	public void setFechaDeCreacion(String fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}
	public String getCartaRelacionada() {
		return cartaRelacionada;
	}
	public void setCartaRelacionada(String cartaRelacionada) {
		this.cartaRelacionada = cartaRelacionada;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getPerspectiva() {
		return perspectiva;
	}
	public void setPerspectiva(String perspectiva) {
		this.perspectiva = perspectiva;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getSubproceso() {
		return subproceso;
	}
	public void setSubproceso(String subproceso) {
		this.subproceso = subproceso;
	}
	public List<ExpedienteContiene> getExpedientesContiene() {
		return expedientesContiene;
	}
	public void setExpedientesContiene(List<ExpedienteContiene> expedientesContiene) {
		this.expedientesContiene = expedientesContiene;
	}
	public String getLinkDescargaNavegador() {
		return linkDescargaNavegador;
	}
	public void setLinkDescargaNavegador(String linkDescargaNavegador) {
		this.linkDescargaNavegador = linkDescargaNavegador;
	}	
	public String getEsConfidencial() {
		return esConfidencial;
	}
	public void setEsConfidencial(String esConfidencial) {
		this.esConfidencial = esConfidencial;
	}
	public List<String> getUsuariosQueHanModificado() {
		return usuariosQueHanModificado;
	}
	public void setUsuariosQueHanModificado(List<String> usuariosQueHanModificado) {
		this.usuariosQueHanModificado = usuariosQueHanModificado;
	}	
	public String getUsuariosQueHanParticipado() {
		return usuariosQueHanParticipado;
	}
	public void setUsuariosQueHanParticipado(String usuariosQueHanParticipado) {
		this.usuariosQueHanParticipado = usuariosQueHanParticipado;
	}
	@Override
	public String toString() {
		return "ElementoBuscar [tipoObjeto=" + tipoObjeto + ", nombreDeObjeto="
				+ nombreDeObjeto + ", idObjeto=" + idObjeto
				+ ", idExpedienteQueLoContiene=" + idExpedienteQueLoContiene
				+ ", nombreExpedienteQueLoContiene="
				+ nombreExpedienteQueLoContiene + ", emisor=" + emisor
				+ ", cdr=" + cdr + ", numeroDeDocumento=" + numeroDeDocumento
				+ ", tipoDeDocumento=" + tipoDeDocumento + ", fechaDeCreacion="
				+ fechaDeCreacion + ", cartaRelacionada=" + cartaRelacionada
				+ ", otro=" + otro + ", creador=" + creador + ", materia="
				+ materia + ", autor=" + autor + ", perspectiva=" + perspectiva
				+ ", proceso=" + proceso + ", subproceso=" + subproceso
				+ ", expedientesContiene=" + expedientesContiene
				+ ", linkDescargaNavegador=" + linkDescargaNavegador 
				+ ", esConfidencial=" + esConfidencial
				+ ", usuariosQueHanModificado=" + usuariosQueHanModificado
				+ ", usuariosQueHanParticipado=" + usuariosQueHanParticipado
				+ "]";
	}	
}

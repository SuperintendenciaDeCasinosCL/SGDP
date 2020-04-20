package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubirArchivoRestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Datos del archivo en Base64
	
	private String archivo;
	private String name;
	private String originalFilename;
	private String contentType;
	
	// Datos del archivo
	
	private String cdr;
	private String numeroDocumento;
	private String fechaCreacionArchivo;
	private long idAutorSubirDocumento;
	private String otro;
	private long idTipoDeDocumentoSubir;
	private List<String> idTags;
	private String descripcion;	
	private String idExpedienteSubirArchivo;
	private String idArchivoEnCMS;
	private String resultadoSubirArchivo;
	private String cssStatus;
	private long idInstanciaDeTareaSubirArchivo;
	private String comentario;
	private String tipoDeDocumento;
	private String emisor;
	private String cartaRelacionada;
	private String idUsuario;
	private String clave;
	private String idUsuarioSube;	
	private String alfTicket;	
	private String marcaSubirDocTransversal;
	
	
	public SubirArchivoRestDTO() {
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public String getFechaCreacionArchivo() {
		return fechaCreacionArchivo;
	}

	public void setFechaCreacionArchivo(String fechaCreacionArchivo) {
		this.fechaCreacionArchivo = fechaCreacionArchivo;
	}

	public long getIdAutorSubirDocumento() {
		return idAutorSubirDocumento;
	}

	public void setIdAutorSubirDocumento(long idAutorSubirDocumento) {
		this.idAutorSubirDocumento = idAutorSubirDocumento;
	}

	public String getOtro() {
		return otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}

	public long getIdTipoDeDocumentoSubir() {
		return idTipoDeDocumentoSubir;
	}

	public void setIdTipoDeDocumentoSubir(long idTipoDeDocumentoSubir) {
		this.idTipoDeDocumentoSubir = idTipoDeDocumentoSubir;
	}

	public List<String> getIdTags() {
		return idTags;
	}

	public void setIdTags(List<String> idTags) {
		this.idTags = idTags;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdExpedienteSubirArchivo() {
		return idExpedienteSubirArchivo;
	}

	public void setIdExpedienteSubirArchivo(String idExpedienteSubirArchivo) {
		this.idExpedienteSubirArchivo = idExpedienteSubirArchivo;
	}

	public String getIdArchivoEnCMS() {
		return idArchivoEnCMS;
	}

	public void setIdArchivoEnCMS(String idArchivoEnCMS) {
		this.idArchivoEnCMS = idArchivoEnCMS;
	}

	public String getResultadoSubirArchivo() {
		return resultadoSubirArchivo;
	}

	public void setResultadoSubirArchivo(String resultadoSubirArchivo) {
		this.resultadoSubirArchivo = resultadoSubirArchivo;
	}

	public String getCssStatus() {
		return cssStatus;
	}

	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}

	public long getIdInstanciaDeTareaSubirArchivo() {
		return idInstanciaDeTareaSubirArchivo;
	}

	public void setIdInstanciaDeTareaSubirArchivo(
			long idInstanciaDeTareaSubirArchivo) {
		this.idInstanciaDeTareaSubirArchivo = idInstanciaDeTareaSubirArchivo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getIdUsuarioSube() {
		return idUsuarioSube;
	}

	public void setIdUsuarioSube(String idUsuarioSube) {
		this.idUsuarioSube = idUsuarioSube;
	}		

	public String getAlfTicket() {
		return alfTicket;
	}

	public void setAlfTicket(String alfTicket) {
		this.alfTicket = alfTicket;
	}
		
	public String getMarcaSubirDocTransversal() {
		return marcaSubirDocTransversal;
	}

	public void setMarcaSubirDocTransversal(String marcaSubirDocTransversal) {
		this.marcaSubirDocTransversal = marcaSubirDocTransversal;
	}

	@Override
	public String toString() {
		return "SubirArchivoRestDTO [name=" + name + ", originalFilename=" + originalFilename
				+ ", contentType=" + contentType + ", cdr=" + cdr + ", numeroDocumento=" + numeroDocumento
				+ ", fechaCreacionArchivo=" + fechaCreacionArchivo + ", idAutorSubirDocumento=" + idAutorSubirDocumento
				+ ", otro=" + otro + ", idTipoDeDocumentoSubir=" + idTipoDeDocumentoSubir + ", idTags=" + idTags
				+ ", descripcion=" + descripcion + ", idExpedienteSubirArchivo=" + idExpedienteSubirArchivo
				+ ", idArchivoEnCMS=" + idArchivoEnCMS + ", resultadoSubirArchivo=" + resultadoSubirArchivo
				+ ", cssStatus=" + cssStatus + ", idInstanciaDeTareaSubirArchivo=" + idInstanciaDeTareaSubirArchivo
				+ ", comentario=" + comentario + ", tipoDeDocumento=" + tipoDeDocumento + ", emisor=" + emisor
				+ ", cartaRelacionada=" + cartaRelacionada + ", idUsuario=" + idUsuario 
				//+ ", archivo=" + archivo
				+ ", idUsuarioSube=" + idUsuarioSube
				+ ", alfTicket=" + alfTicket
				+ ", marcaSubirDocTransversal=" + marcaSubirDocTransversal
				+ "]";
	}
	
	
}

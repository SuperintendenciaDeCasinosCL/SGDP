package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubirArhivoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private MultipartFile archivo;
	
	private String cdr;
	private String numeroDocumento;
	private String fechaCreacionArchivo;
	private String fechaRecepcionArchivo;
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
	private String cartaRelacionada; //Nombre de documento conductor	
	private String nombreDeArchivo;
	private boolean convertirAPDF;
	private boolean tieneFirma;
	private boolean esRequerido;
	private String numeroDeFirma;
	private boolean asignarnumerodocumento;
	private String marcaSubirDocTransversal;	
	private String idUsuarioSube;
	private boolean esDocumentoOficial; 
	private boolean validaInstanciaDeTareaEnBE;
	protected boolean recarga;
	private long idCategoriaDocumento;
	private String categoriaDocumento;
	private String nombreArchivoFileUpload;
	
	public SubirArhivoDTO() {	
	}
	
	public MultipartFile getArchivo() {
		return archivo;
	}
	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
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
		if (numeroDocumento!=null) {
			this.numeroDocumento = numeroDocumento.replaceAll("\\p{Cntrl}", " ").replaceAll("( )+", " ");
		} else {
			this.numeroDocumento = numeroDocumento;
		}
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
		if (descripcion!=null) {
			this.descripcion = descripcion.replaceAll("\\p{Cntrl}", " ").replaceAll("( )+", " ");
		} else {
			this.descripcion = descripcion;
		}		
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
		if (comentario!=null) {
			this.comentario = comentario.replaceAll("\\p{Cntrl}", " ").replaceAll("( )+", " ");
		} else {
			this.comentario = comentario;
		}		
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
	public void setCartaRelacionada(String cartaRelacionada) throws UnsupportedEncodingException {		
		if (cartaRelacionada!=null) {
			this.cartaRelacionada = URLDecoder.decode(cartaRelacionada, "UTF-8");
		} else {
			this.cartaRelacionada = cartaRelacionada;
		}
	}	
	public String getFechaRecepcionArchivo() {
		return fechaRecepcionArchivo;
	}
	public void setFechaRecepcionArchivo(String fechaRecepcionArchivo) {
		this.fechaRecepcionArchivo = fechaRecepcionArchivo;
	}
	public String getNombreDeArchivo() {
		return nombreDeArchivo;
	}
	public void setNombreDeArchivo(String nombreDeArchivo) throws UnsupportedEncodingException {
		if (nombreDeArchivo!=null) {
			this.nombreDeArchivo = URLDecoder.decode(nombreDeArchivo, "UTF-8");
		} else {
			this.nombreDeArchivo = nombreDeArchivo;
		}		
	}	
	public boolean getConvertirAPDF() {
		return convertirAPDF;
	}
	public void setConvertirAPDF(boolean convertirAPDF) {
		this.convertirAPDF = convertirAPDF;
	}
	public boolean getTieneFirma() {
		return tieneFirma;
	}
	public void setTieneFirma(boolean tieneFirma) {
		this.tieneFirma = tieneFirma;
	}	
	public boolean getEsRequerido() {
		return esRequerido;
	}
	public void setEsRequerido(boolean esRequerido) {
		this.esRequerido = esRequerido;
	}	
	public String getNumeroDeFirma() {
		return numeroDeFirma;
	}
	public void setNumeroDeFirma(String numeroDeFirma) {
		this.numeroDeFirma = numeroDeFirma;
	}
	public boolean getAsignarnumerodocumento() {
		return asignarnumerodocumento;
	}
	public void setAsignarnumerodocumento(boolean asignarnumerodocumento) {
		this.asignarnumerodocumento = asignarnumerodocumento;
	}	
	public String getMarcaSubirDocTransversal() {
		return marcaSubirDocTransversal;
	}
	public void setMarcaSubirDocTransversal(String marcaSubirDocTransversal) {
		this.marcaSubirDocTransversal = marcaSubirDocTransversal;
	}
	public String getIdUsuarioSube() {
		return idUsuarioSube;
	}
	public void setIdUsuarioSube(String idUsuarioSube) {
		this.idUsuarioSube = idUsuarioSube;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emisor == null) ? 0 : emisor.hashCode());
		result = prime
				* result
				+ ((idExpedienteSubirArchivo == null) ? 0
						: idExpedienteSubirArchivo.hashCode());
		result = prime
				* result
				+ (int) (idInstanciaDeTareaSubirArchivo ^ (idInstanciaDeTareaSubirArchivo >>> 32));
		result = prime * result
				+ ((nombreDeArchivo == null) ? 0 : nombreDeArchivo.hashCode());
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
		SubirArhivoDTO other = (SubirArhivoDTO) obj;
		if (emisor == null) {
			if (other.emisor != null)
				return false;
		} else if (!emisor.equals(other.emisor))
			return false;
		if (idExpedienteSubirArchivo == null) {
			if (other.idExpedienteSubirArchivo != null)
				return false;
		} else if (!idExpedienteSubirArchivo
				.equals(other.idExpedienteSubirArchivo))
			return false;
		if (idInstanciaDeTareaSubirArchivo != other.idInstanciaDeTareaSubirArchivo)
			return false;
		if (nombreDeArchivo == null) {
			if (other.nombreDeArchivo != null)
				return false;
		} else if (!nombreDeArchivo.equals(other.nombreDeArchivo))
			return false;
		return true;
	}

	public boolean getEsDocumentoOficial() {
		return esDocumentoOficial;
	}

	public void setEsDocumentoOficial(boolean esDocumentoOficial) {
		this.esDocumentoOficial = esDocumentoOficial;
	}	

	public boolean isValidaInstanciaDeTareaEnBE() {
		return validaInstanciaDeTareaEnBE;
	}

	public void setValidaInstanciaDeTareaEnBE(boolean validaInstanciaDeTareaEnBE) {
		this.validaInstanciaDeTareaEnBE = validaInstanciaDeTareaEnBE;
	}	

	public boolean isRecarga() {
		return recarga;
	}

	public void setRecarga(boolean recarga) {
		this.recarga = recarga;
	}	

	public long getIdCategoriaDocumento() {
		return idCategoriaDocumento;
	}

	public void setIdCategoriaDocumento(long idCategoriaDocumento) {
		this.idCategoriaDocumento = idCategoriaDocumento;
	}

	public String getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(String categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}
	
	public String getNombreArchivoFileUpload() {
		return nombreArchivoFileUpload;
	}

	public void setNombreArchivoFileUpload(String nombreArchivoFileUpload) {
		this.nombreArchivoFileUpload = nombreArchivoFileUpload;
	}

	public SubirArhivoDTO(MultipartFile archivo, String cdr, String numeroDocumento, String fechaCreacionArchivo,
			String fechaRecepcionArchivo, long idAutorSubirDocumento, String otro, long idTipoDeDocumentoSubir,
			List<String> idTags, String descripcion, String idExpedienteSubirArchivo, String idArchivoEnCMS,
			String resultadoSubirArchivo, String cssStatus, long idInstanciaDeTareaSubirArchivo, String comentario,
			String tipoDeDocumento, String emisor, String cartaRelacionada, String nombreDeArchivo,
			boolean convertirAPDF, boolean tieneFirma, boolean esRequerido, String numeroDeFirma,
			boolean asignarnumerodocumento, String marcaSubirDocTransversal, String idUsuarioSube,
			boolean esDocumentoOficial) {
		super();
		this.archivo = archivo;
		this.cdr = cdr;
		this.numeroDocumento = numeroDocumento;
		this.fechaCreacionArchivo = fechaCreacionArchivo;
		this.fechaRecepcionArchivo = fechaRecepcionArchivo;
		this.idAutorSubirDocumento = idAutorSubirDocumento;
		this.otro = otro;
		this.idTipoDeDocumentoSubir = idTipoDeDocumentoSubir;
		this.idTags = idTags;
		this.descripcion = descripcion;
		this.idExpedienteSubirArchivo = idExpedienteSubirArchivo;
		this.idArchivoEnCMS = idArchivoEnCMS;
		this.resultadoSubirArchivo = resultadoSubirArchivo;
		this.cssStatus = cssStatus;
		this.idInstanciaDeTareaSubirArchivo = idInstanciaDeTareaSubirArchivo;
		this.comentario = comentario;
		this.tipoDeDocumento = tipoDeDocumento;
		this.emisor = emisor;
		this.cartaRelacionada = cartaRelacionada;
		this.nombreDeArchivo = nombreDeArchivo;
		this.convertirAPDF = convertirAPDF;
		this.tieneFirma = tieneFirma;
		this.esRequerido = esRequerido;
		this.numeroDeFirma = numeroDeFirma;
		this.asignarnumerodocumento = asignarnumerodocumento;
		this.marcaSubirDocTransversal = marcaSubirDocTransversal;
		this.idUsuarioSube = idUsuarioSube;
		this.esDocumentoOficial = esDocumentoOficial;
	}

	@Override
	public String toString() {
		return "SubirArhivoDTO [archivo=" + archivo + ", cdr=" + cdr + ", numeroDocumento=" + numeroDocumento
				+ ", fechaCreacionArchivo=" + fechaCreacionArchivo + ", fechaRecepcionArchivo=" + fechaRecepcionArchivo
				+ ", idAutorSubirDocumento=" + idAutorSubirDocumento + ", otro=" + otro + ", idTipoDeDocumentoSubir="
				+ idTipoDeDocumentoSubir + ", idTags=" + idTags + ", descripcion=" + descripcion
				+ ", idExpedienteSubirArchivo=" + idExpedienteSubirArchivo + ", idArchivoEnCMS=" + idArchivoEnCMS
				+ ", resultadoSubirArchivo=" + resultadoSubirArchivo + ", cssStatus=" + cssStatus
				+ ", idInstanciaDeTareaSubirArchivo=" + idInstanciaDeTareaSubirArchivo + ", comentario=" + comentario
				+ ", tipoDeDocumento=" + tipoDeDocumento + ", emisor=" + emisor + ", cartaRelacionada="
				+ cartaRelacionada + ", nombreDeArchivo=" + nombreDeArchivo + ", convertirAPDF=" + convertirAPDF
				+ ", tieneFirma=" + tieneFirma + ", esRequerido=" + esRequerido + ", numeroDeFirma=" + numeroDeFirma
				+ ", asignarnumerodocumento=" + asignarnumerodocumento + ", marcaSubirDocTransversal="
				+ marcaSubirDocTransversal + ", idUsuarioSube=" + idUsuarioSube + ", esDocumentoOficial="
				+ esDocumentoOficial + ", validaInstanciaDeTareaEnBE=" + validaInstanciaDeTareaEnBE 
				+ ", recarga=" + recarga
				+ ", idCategoriaDocumento=" + idCategoriaDocumento
				+ ", categoriaDocumento=" + categoriaDocumento
				+ ", nombreArchivoFileUpload=" + nombreArchivoFileUpload				
				+ "]";
	}
	
}

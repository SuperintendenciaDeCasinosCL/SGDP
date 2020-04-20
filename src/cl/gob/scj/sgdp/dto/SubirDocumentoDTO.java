package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubirDocumentoDTO implements Serializable {

	private static final long serialVersionUID = 234861787970872835L;
	
	@JsonIgnore 
	private MultipartFile documento;
	
	private long idTipoDeDocumentoSubir;
	private String cdr;
	private String numeroDocumento;
	private String fechaDeCreacionDocumento;
	private String fechaRecepcionDocumento;
	private long idAutorSubirDocumento;
	private String otro;
	private String nombreTipoDocumentoAdjuntos;
	private String nombreDocumentoConductorSalida;
	@JsonIgnore 
	private List<MultipartFile> antecedentes;
	
	private String descripcion;
	private String idExpedienteSubirDocumento;
	private long idInstanciaDeTareaOrigenSubirDocumento;
	private String resultadoSubirDocumento;
	private String cssStatus;
	private String tipoDeDocumentoSubirExpediente;
	private boolean esDocumentoOficial; 
	
	
	public MultipartFile getDocumento() {
		return documento;
	}
	public void setDocumento(MultipartFile documento) {
		this.documento = documento;
	}
	public long getIdTipoDeDocumentoSubir() {
		return idTipoDeDocumentoSubir;
	}
	public void setIdTipoDeDocumentoSubir(long idTipoDeDocumentoSubir) {
		this.idTipoDeDocumentoSubir = idTipoDeDocumentoSubir;
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
	public String getFechaDeCreacionDocumento() {
		return fechaDeCreacionDocumento;
	}
	public void setFechaDeCreacionDocumento(String fechaDeCreacionDocumento) {
		this.fechaDeCreacionDocumento = fechaDeCreacionDocumento;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}
	public List<MultipartFile> getAntecedentes() {
		return antecedentes;
	}
	public void setAntecedentes(List<MultipartFile> antecedentes) {
		this.antecedentes = antecedentes;
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
	public long getIdAutorSubirDocumento() {
		return idAutorSubirDocumento;
	}
	public void setIdAutorSubirDocumento(long idAutorSubirDocumento) {
		this.idAutorSubirDocumento = idAutorSubirDocumento;
	}
	public String getIdExpedienteSubirDocumento() {
		return idExpedienteSubirDocumento;
	}
	public void setIdExpedienteSubirDocumento(String idExpedienteSubirDocumento) {
		this.idExpedienteSubirDocumento = idExpedienteSubirDocumento;
	}		
	public long getIdInstanciaDeTareaOrigenSubirDocumento() {
		return idInstanciaDeTareaOrigenSubirDocumento;
	}
	public void setIdInstanciaDeTareaOrigenSubirDocumento(
			long idInstanciaDeTareaOrigenSubirDocumento) {
		this.idInstanciaDeTareaOrigenSubirDocumento = idInstanciaDeTareaOrigenSubirDocumento;
	}
	public String getResultadoSubirDocumento() {
		return resultadoSubirDocumento;
	}
	public void setResultadoSubirDocumento(String resultadoSubirDocumento) {
		this.resultadoSubirDocumento = resultadoSubirDocumento;
	}
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}	
	public String getNombreTipoDocumentoAdjuntos() {
		return nombreTipoDocumentoAdjuntos;
	}
	public void setNombreTipoDocumentoAdjuntos(String nombreTipoDocumentoAdjuntos) {
		this.nombreTipoDocumentoAdjuntos = nombreTipoDocumentoAdjuntos;
	}
		
	public String getFechaRecepcionDocumento() {
		return fechaRecepcionDocumento;
	}
	
	public void setFechaRecepcionDocumento(String fechaRecepcionDocumento) {
		this.fechaRecepcionDocumento = fechaRecepcionDocumento;
	}
	
	public String getNombreDocumentoConductorSalida() {
		return nombreDocumentoConductorSalida;
	}
	
	public void setNombreDocumentoConductorSalida(
			String nombreDocumentoConductorSalida) {
		this.nombreDocumentoConductorSalida = nombreDocumentoConductorSalida;
	}		
	
	public String getTipoDeDocumentoSubirExpediente() {
		return tipoDeDocumentoSubirExpediente;
	}
	
	public void setTipoDeDocumentoSubirExpediente(String tipoDeDocumentoSubirExpediente) {
		this.tipoDeDocumentoSubirExpediente = tipoDeDocumentoSubirExpediente;
	}


	public boolean getEsDocumentoOficial() {
		return esDocumentoOficial;
	}
	public void setEsDocumentoOficial(boolean esDocumentoOficial) {
		this.esDocumentoOficial = esDocumentoOficial;
	}
	@Override
	public String toString() {
		return "SubirDocumentoDTO [documento=" + documento + ", idTipoDeDocumentoSubir=" + idTipoDeDocumentoSubir
				+ ", cdr=" + cdr + ", numeroDocumento=" + numeroDocumento + ", fechaDeCreacionDocumento="
				+ fechaDeCreacionDocumento + ", fechaRecepcionDocumento=" + fechaRecepcionDocumento
				+ ", idAutorSubirDocumento=" + idAutorSubirDocumento + ", otro=" + otro
				+ ", nombreTipoDocumentoAdjuntos=" + nombreTipoDocumentoAdjuntos + ", nombreDocumentoConductorSalida="
				+ nombreDocumentoConductorSalida + ", antecedentes=" + antecedentes + ", descripcion=" + descripcion
				+ ", idExpedienteSubirDocumento=" + idExpedienteSubirDocumento
				+ ", idInstanciaDeTareaOrigenSubirDocumento=" + idInstanciaDeTareaOrigenSubirDocumento
				+ ", resultadoSubirDocumento=" + resultadoSubirDocumento + ", cssStatus=" + cssStatus
				+ ", tipoDeDocumentoSubirExpediente=" + tipoDeDocumentoSubirExpediente + ", esDocumentoOficial="
				+ esDocumentoOficial + "]";
	}
	
	
	
}

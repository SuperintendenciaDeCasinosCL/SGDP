package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class SubirAntecedenteDTO implements Serializable  {
	
	private	String	descripcion;
	private	long	idTipoDeDocumentoSubir; 
	private String	numeroDocumento; 
	private String	fechaCreacionArchivo; 
	private long	idAutorSubirDocumento; 
	private String	idExpedienteSubirArchivo; 
	private long	idInstanciaDeTareaSubirArchivo; 
	private String	cartaRelacionada;
	
	
	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getIdTipoDeDocumentoSubir() {
		return idTipoDeDocumentoSubir;
	}

	public void setIdTipoDeDocumentoSubir(long idTipoDeDocumentoSubir) {
		this.idTipoDeDocumentoSubir = idTipoDeDocumentoSubir;
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
		this.fechaCreacionArchivo = "ggg";
	}

	public long getIdAutorSubirDocumento() {
		return idAutorSubirDocumento;
	}

	public void setIdAutorSubirDocumento(long idAutorSubirDocumento) {
		this.idAutorSubirDocumento = idAutorSubirDocumento;
	}

	public String getIdExpedienteSubirArchivo() {
		return idExpedienteSubirArchivo;
	}

	public void setIdExpedienteSubirArchivo(String idExpedienteSubirArchivo) {
		this.idExpedienteSubirArchivo = idExpedienteSubirArchivo;
	}

	public long getIdInstanciaDeTareaSubirArchivo() {
		return idInstanciaDeTareaSubirArchivo;
	}

	public void setIdInstanciaDeTareaSubirArchivo(
			long idInstanciaDeTareaSubirArchivo) {
		this.idInstanciaDeTareaSubirArchivo = idInstanciaDeTareaSubirArchivo;
	}

	public String getCartaRelacionada() {
		return cartaRelacionada;
	}

	public void setCartaRelacionada(String cartaRelacionada) {
		this.cartaRelacionada = cartaRelacionada;
	} 
	
	
	
}

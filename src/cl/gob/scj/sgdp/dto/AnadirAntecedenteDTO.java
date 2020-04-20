package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class AnadirAntecedenteDTO implements Serializable  {

	private int idTipoDocumento;	
	private String nombreDocumento;
	private String nombreTipoDocumento;		
	
	private String idExpediente;
	private int idInstanciaDeTarea;
	private String emisor;
	
	
	public AnadirAntecedenteDTO() {
	}

	public AnadirAntecedenteDTO(int idTipoDocumento, String nombreDocumento,
			String nombreTipoDocumento, String idExpediente,
			int idInstanciaDeTarea, String emisor) {
		super();
		this.idTipoDocumento = idTipoDocumento;
		this.nombreDocumento = nombreDocumento;
		this.nombreTipoDocumento = nombreTipoDocumento;
		this.idExpediente = idExpediente;
		this.idInstanciaDeTarea = idInstanciaDeTarea;
		this.emisor = emisor;
	}


	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}


	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}


	public String getNombreDocumento() {
		return nombreDocumento;
	}


	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}


	public String getIdExpediente() {
		return idExpediente;
	}


	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}


	public int getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}


	public void setIdInstanciaDeTarea(int idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}


	public String getEmisor() {
		return emisor;
	}


	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}

	
}

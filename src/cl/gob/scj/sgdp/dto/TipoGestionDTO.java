package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class TipoGestionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2829015351080757454L;
	private long idTipo;
	private String nombreTipo;

	
	public TipoGestionDTO() {
		super();
	}

	public TipoGestionDTO(long idTipo,
			String nombreTipo) {
		super();
		this.idTipo = idTipo;
		this.nombreTipo = nombreTipo;
	}

	public long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(long idTipo) {
		this.idTipo = idTipo;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	@Override
	public String toString() {
		return "TipoGestionDTO [idTipo=" + idTipo + ", nombreTipo=" + nombreTipo + "]";
	}

	
}

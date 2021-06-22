package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class AccesoDTO implements Serializable {

	private long idAcceso;
	private String nombreAcceso;
	private String valorAccesoChar;

	public long getIdAcceso() {
		return idAcceso;
	}

	public void setIdAcceso(long idAcceso) {
		this.idAcceso = idAcceso;
	}

	public String getNombreAcceso() {
		return nombreAcceso;
	}

	public void setNombreAcceso(String nombreAcceso) {
		this.nombreAcceso = nombreAcceso;
	}

	public String getValorAccesoChar() {
		return valorAccesoChar;
	}

	public void setValorAccesoChar(String valorAccesoChar) {
		this.valorAccesoChar = valorAccesoChar;
	}

	public AccesoDTO() {
		super();
	}

	public AccesoDTO(long idAcceso, String nombreAcceso, String valorAccesoChar) {
		super();
		this.idAcceso = idAcceso;
		this.nombreAcceso = nombreAcceso;
		this.valorAccesoChar = valorAccesoChar;
	}

	@Override
	public String toString() {
		return "AccesoDTO [idAcceso=" + idAcceso + ", nombreAcceso=" + nombreAcceso + ", valorAccesoChar="
				+ valorAccesoChar + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idAcceso ^ (idAcceso >>> 32));
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
		AccesoDTO other = (AccesoDTO) obj;
		if (idAcceso != other.idAcceso)
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7883864840323228777L;

}

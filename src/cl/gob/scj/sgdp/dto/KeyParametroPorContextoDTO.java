package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class KeyParametroPorContextoDTO implements Serializable {

	private String nombreParametro;
	private String valorContexto;
	
	public KeyParametroPorContextoDTO() {
		super();
	}

	public KeyParametroPorContextoDTO(String nombreParametro,
			String valorContexto) {
		super();
		this.nombreParametro = nombreParametro;
		this.valorContexto = valorContexto;
	}

	public String getNombreParametro() {
		return nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public String getValorContexto() {
		return valorContexto;
	}

	public void setValorContexto(String valorContexto) {
		this.valorContexto = valorContexto;
	}

	@Override
	public String toString() {
		return "KeyParametroPorContextoDTO [nombreParametro=" + nombreParametro
				+ ", valorContexto=" + valorContexto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nombreParametro == null) ? 0 : nombreParametro.hashCode());
		result = prime * result
				+ ((valorContexto == null) ? 0 : valorContexto.hashCode());
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
		KeyParametroPorContextoDTO other = (KeyParametroPorContextoDTO) obj;
		if (nombreParametro == null) {
			if (other.nombreParametro != null)
				return false;
		} else if (!nombreParametro.equals(other.nombreParametro))
			return false;
		if (valorContexto == null) {
			if (other.valorContexto != null)
				return false;
		} else if (!valorContexto.equals(other.valorContexto))
			return false;
		return true;
	}
	
}

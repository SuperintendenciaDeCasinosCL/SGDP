package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ParametroPorContextoDTO implements Serializable {

	private long idParametroPorContexto;	
	private String nombreParametro;
	private String valorContexto;
	private String valorParametroChar;
	private int valorParametroNumerico;
	
	
	public ParametroPorContextoDTO() {
		super();
	}	
	
	public ParametroPorContextoDTO(long idParametroPorContexto,
			String nombreParametro, String valorContexto,
			String valorParametroChar, int valorParametroNumerico) {
		super();
		this.idParametroPorContexto = idParametroPorContexto;
		this.nombreParametro = nombreParametro;
		this.valorContexto = valorContexto;
		this.valorParametroChar = valorParametroChar;
		this.valorParametroNumerico = valorParametroNumerico;
	}

	public long getIdParametroPorContexto() {
		return idParametroPorContexto;
	}
	public void setIdParametroPorContexto(long idParametroPorContexto) {
		this.idParametroPorContexto = idParametroPorContexto;
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
	public String getValorParametroChar() {
		return valorParametroChar;
	}
	public void setValorParametroChar(String valorParametroChar) {
		this.valorParametroChar = valorParametroChar;
	}
	public int getValorParametroNumerico() {
		return valorParametroNumerico;
	}
	public void setValorParametroNumerico(int valorParametroNumerico) {
		this.valorParametroNumerico = valorParametroNumerico;
	}
	
	@Override
	public String toString() {
		return "ParametroPorContextoDTO [idParametroPorContexto="
				+ idParametroPorContexto + ", nombreParametro="
				+ nombreParametro + ", valorContexto=" + valorContexto
				+ ", valorParametroChar=" + valorParametroChar
				+ ", valorParametroNumerico=" + valorParametroNumerico + "]";
	}
	
}

package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ParametroDTO implements Serializable {

	private static final long serialVersionUID = 4754136431681304459L;

	private Long idParametro;
	private String nombreParametro;
	private String valorParametroChar;
	private Integer valorParametroNumerico;	
	private String respuesta;
	
	private Long idParametroAnterior;
	
	public ParametroDTO(){
		super();
	}
	
	public ParametroDTO(Long idParametro, String nombreParametro,
			String valorParametroChar, Integer valorParametroNumerico) {
		super();
		this.idParametro = idParametro;
		this.nombreParametro = nombreParametro;
		this.valorParametroChar = valorParametroChar;
		this.valorParametroNumerico = valorParametroNumerico;
	}
	
	public Long getIdParametro() {
		return idParametro;
	}
	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}
	public String getNombreParametro() {
		return nombreParametro;
	}
	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}
	public String getValorParametroChar() {
		return valorParametroChar;
	}
	public void setValorParametroChar(String valorParametroChar) {
		this.valorParametroChar = valorParametroChar;
	}
	public Integer getValorParametroNumerico() {
		return valorParametroNumerico;
	}
	public void setValorParametroNumerico(Integer valorParametroNumerico) {
		this.valorParametroNumerico = valorParametroNumerico;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}	
	public Long getIdParametroAnterior() {
		return idParametroAnterior;
	}
	public void setIdParametroAnterior(Long idParametroAnterior) {
		this.idParametroAnterior = idParametroAnterior;
	}
	@Override
	public String toString() {
		return "ParametroDTO [idParametro=" + idParametro
				+ ", nombreParametro=" + nombreParametro
				+ ", valorParametroChar=" + valorParametroChar
				+ ", valorParametroNumerico=" + valorParametroNumerico 
				+ ", idParametroAnterior=" + idParametroAnterior
				+ ", respuesta=" + respuesta
				+ "]";
	}
	
}

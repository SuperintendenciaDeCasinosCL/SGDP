package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class IndicadorDTO implements Serializable {

	private String indicadorId;
	private String indicadorDescripcion;
	private String indicadorNombre;
	private String indicadorSentido;
	private Integer indicadorVigencia;
	
	public IndicadorDTO() {
		super();
	}

	public String getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(String indicadorId) {
		this.indicadorId = indicadorId;
	}

	public String getIndicadorDescripcion() {
		return indicadorDescripcion;
	}

	public void setIndicadorDescripcion(String indicadorDescripcion) {
		this.indicadorDescripcion = indicadorDescripcion;
	}

	public String getIndicadorNombre() {
		return indicadorNombre;
	}

	public void setIndicadorNombre(String indicadorNombre) {
		this.indicadorNombre = indicadorNombre;
	}

	public String getIndicadorSentido() {
		return indicadorSentido;
	}

	public void setIndicadorSentido(String indicadorSentido) {
		this.indicadorSentido = indicadorSentido;
	}

	public Integer getIndicadorVigencia() {
		return indicadorVigencia;
	}

	public void setIndicadorVigencia(Integer indicadorVigencia) {
		this.indicadorVigencia = indicadorVigencia;
	}

	@Override
	public String toString() {
		return "IndicadorDTO [indicadorId=" + indicadorId + ", indicadorDescripcion=" + indicadorDescripcion
				+ ", indicadorNombre=" + indicadorNombre + ", indicadorSentido=" + indicadorSentido
				+ ", indicadorVigencia=" + indicadorVigencia + "]";
	}

	
	
}

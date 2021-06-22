package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SerieDTO implements Serializable {

	@JsonProperty(value = "id")
	private Long id;
	@JsonProperty(value = "serie")
	private String serie;
	@JsonProperty(value = "nivel")
	private String nivel;
	@JsonProperty(value = "idTRD")
	private Long idTRD;
	@JsonProperty(value = "aniosAG")
	private Integer aniosAG;
	@JsonProperty(value = "aniosACI")
	private Integer aniosACI;
	@JsonProperty(value = "aniosAN")
	private Integer aniosAC;

	public SerieDTO() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSerie() {
		return serie;
	}


	public void setSerie(String serie) {
		this.serie = serie;
	}


	public String getNivel() {
		return nivel;
	}


	public void setNivel(String nivel) {
		this.nivel = nivel;
	}


	public Long getIdTRD() {
		return idTRD;
	}


	public void setIdTRD(Long idTRD) {
		this.idTRD = idTRD;
	}


	public Integer getAniosAG() {
		return aniosAG;
	}


	public void setAniosAG(Integer aniosAG) {
		this.aniosAG = aniosAG;
	}


	public Integer getAniosACI() {
		return aniosACI;
	}


	public void setAniosACI(Integer aniosACI) {
		this.aniosACI = aniosACI;
	}


	public Integer getAniosAC() {
		return aniosAC;
	}


	public void setAniosAC(Integer aniosAC) {
		this.aniosAC = aniosAC;
	}


	@Override
	public String toString() {
		return "SerieDTO [id=" + id + ", serie=" + serie + ", nivel=" + nivel + ", idTRD=" + idTRD + ", aniosAG="
				+ aniosAG + ", aniosACI=" + aniosACI + ", aniosAC=" + aniosAC + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 7883864840323228777L;

}

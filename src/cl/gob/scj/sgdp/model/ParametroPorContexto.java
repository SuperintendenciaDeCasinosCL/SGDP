package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_PARAMETROS_POR_CONTEXTO" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PARAMETROS_POR_CONTEXTO\"")
//@NamedQuery(name="ParametroPorContexto.findAll", query="SELECT p FROM ParametroPorContexto p")
@NamedQueries({		
	@NamedQuery(name="ParametroPorContexto.findAll", query="SELECT p FROM ParametroPorContexto p"),	
	
	@NamedQuery(name="ParametroPorContexto.getParametroPorContextoPorValorParametroChar",	
	query="SELECT p FROM ParametroPorContexto p WHERE p.valorParametroChar = :valorParametroChar"),
	
	@NamedQuery(name="ParametroPorContexto.getParametroPorContextoPorNombreParamValorContexto",	
	query="SELECT p FROM ParametroPorContexto p WHERE p.nombreParametro = :nombreParametro and p.valorContexto = :valorContexto"),
	
	@NamedQuery(name="ParametroPorContexto.getParametrosPorContextoPorNombreParam",	
	query="SELECT p FROM ParametroPorContexto p WHERE p.nombreParametro = :nombreParametro")
})

public class ParametroPorContexto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_PARAMETRO_POR_CONTEXTO", sequenceName="\"SEQ_ID_PARAMETRO_POR_CONTEXTO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PARAMETRO_POR_CONTEXTO")
	@Column(name="\"ID_PARAMETRO_POR_CONTEXTO\"")
	private long idParametroPorContexto;

	@Column(name="\"A_NOMBRE_PARAMETRO\"")
	private String nombreParametro;

	@Column(name="\"A_VALOR_CONTEXTO\"")
	private String valorContexto;

	@Column(name="\"A_VALOR_PARAMETRO_CHAR\"")
	private String valorParametroChar;

	@Column(name="\"N_VALOR_PARAMETRO_NUMERICO\"")
	private int valorParametroNumerico;

	public ParametroPorContexto() {
	}

	public long getIdParametroPorContexto() {
		return this.idParametroPorContexto;
	}

	public void setIdParametroPorContexto(long idParametroPorContexto) {
		this.idParametroPorContexto = idParametroPorContexto;
	}

	public String getNombreParametro() {
		return this.nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public String getValorContexto() {
		return this.valorContexto;
	}

	public void setValorContexto(String valorContexto) {
		this.valorContexto = valorContexto;
	}

	public String getValorParametroChar() {
		return this.valorParametroChar;
	}

	public void setValorParametroChar(String valorParametroChar) {
		this.valorParametroChar = valorParametroChar;
	}

	public int getValorParametroNumerico() {
		return this.valorParametroNumerico;
	}

	public void setValorParametroNumerico(int valorParametroNumerico) {
		this.valorParametroNumerico = valorParametroNumerico;
	}

	@Override
	public String toString() {
		return "ParametroPorContexto [idParametroPorContexto="
				+ idParametroPorContexto + ", nombreParametro="
				+ nombreParametro + ", valorContexto=" + valorContexto
				+ ", valorParametroChar=" + valorParametroChar
				+ ", valorParametroNumerico=" + valorParametroNumerico + "]";
	}
	
}
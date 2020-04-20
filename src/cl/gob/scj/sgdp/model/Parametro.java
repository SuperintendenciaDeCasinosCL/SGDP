package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_PARAMETROS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PARAMETROS\"")
@NamedQuery(name="Parametro.findAll", query="SELECT p FROM Parametro p")
public class Parametro implements Serializable {
	private static final long serialVersionUID = 1L;	
		
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_PARAMETRO", sequenceName="\"SEQ_ID_PARAMETRO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PARAMETRO")
	@Column(name="\"ID_PARAMETRO\"")
	private long idParametro;

	@Column(name="\"A_NOMBRE_PARAMETRO\"")
	private String nombreParametro;

	@Column(name="\"A_VALOR_PARAMETRO_CHAR\"")
	private String valorParametroChar;

	@Column(name="\"N_VALOR_PARAMETRO_NUMERICO\"")
	private int valorParametroNumerico;

	public Parametro() {
	}

	public long getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(long idParametro) {
		this.idParametro = idParametro;
	}

	public String getNombreParametro() {
		return this.nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
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

}
package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_PARAMETROS_ARCHIVO_NACIONAL" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PARAMETROS_ARCHIVO_NACIONAL\"")
@NamedQuery(name = "ParametroArchivoNacional.findAll", query = "SELECT p FROM ParametroArchivoNacional p order by p.idParametro asc")
public class ParametroArchivoNacional implements Serializable {
	private static final long serialVersionUID = 1L;	
		
	@Id
	@SequenceGenerator(name="SEQ_ID_PARAMETRO_ARCHIVO_NACIONAL", sequenceName="\"SEQ_ID_PARAMETRO_ARCHIVO_NACIONAL\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PARAMETRO_ARCHIVO_NACIONAL")
	@Column(name="\"ID_PARAMETRO_ARCHIVO_NACIONAL\"")
	private long idParametro;

	@Column(name="\"A_NOMBRE_PARAMETRO\"")
	private String nombreParametro;

	@Column(name="\"A_VALOR_PARAMETRO_CHAR\"")
	private String valorParametroChar;

	@Column(name="\"D_FECHA_CREACION\"")
	private Date fechaCreacion;
	
	@Column(name="\"D_FECHA_ACTUALIZACION\"")
	private Date fechaActualizacion;

	public long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(long idParametro) {
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public ParametroArchivoNacional() {
	}



}
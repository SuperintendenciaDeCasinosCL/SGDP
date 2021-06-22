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
 * The persistent class for the "SGDP_ACCESOS" database table.
 * 
 */
@Entity
@Table(name = "\"SGDP_TIPOS\"")
@NamedQuery(name = "Tipo.findAll", query = "SELECT p FROM Tipo p order by p.idTipo asc")
public class Tipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ID_TIPO", sequenceName = "\"SEQ_ID_TIPO\"", allocationSize = Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_TIPO")
	@Column(name = "\"ID_TIPO\"")
	private long idTipo;

	@Column(name = "\"A_NOMBRE_TIPO\"")
	private String nombreTipo;



	@Column(name = "\"D_FECHA_CREACION\"")
	private Date fechaCreacion;

	public Tipo() {
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


	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	@Override
	public String toString() {
		return "Tipo [idTipo=" + idTipo + ", nombreTipo=" + nombreTipo + ", fechaCreacion=" + fechaCreacion + "]";
	}

}
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
@Table(name = "\"SGDP_ACCESOS\"")
@NamedQuery(name = "Acceso.findAll", query = "SELECT p FROM Acceso p where p.idAcceso > 0 order by p.idAcceso asc")
public class Acceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ID_ACCESO", sequenceName = "\"SEQ_ID_ACCESO\"", allocationSize = Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_ACCESO")
	@Column(name = "\"ID_ACCESO\"")
	private long idAcceso;

	@Column(name = "\"A_NOMBRE_ACCESO\"")
	private String nombreAcceso;

	@Column(name = "\"A_VALOR_ACCESO_CHAR\"")
	private String valorAccesoChar;

	@Column(name = "\"D_FECHA_CREACION\"")
	private Date fechaCreacion;

	public Acceso() {
	}

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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
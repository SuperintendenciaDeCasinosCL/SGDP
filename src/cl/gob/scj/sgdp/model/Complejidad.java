package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_ARCHIVOS_SOL_CREA_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_COMPLEJIDAD\"")
public class Complejidad implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	
    @SequenceGenerator(name="SEQ_ID_COMPLEJIDAD", sequenceName="\"SEQ_ID_COMPLEJIDAD\"", allocationSize=Constantes.ALLOCATION_SIZE)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_COMPLEJIDAD")
	@Column(name="\"ID_COMPLEJIDAD\"")
	private Long idComplejidad;

	@Column(name="\"A_COMPLEJIDAD\"")
	private String complejidad;

	@Column(name="\"D_FECHA_CREACION\"")
	private Date fechaCreacion;
	
	@Column(name="\"A_USUARIO_CREACION\"")
	private String usuarioCreacion;

	public Long getIdComplejidad() {
		return idComplejidad;
	}

	public void setIdComplejidad(Long idComplejidad) {
		this.idComplejidad = idComplejidad;
	}

	public String getComplejidad() {
		return complejidad;
	}

	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	

}

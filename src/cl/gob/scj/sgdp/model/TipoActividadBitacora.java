package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_UNIDADES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_BITACORA_TIPO_ACTIVIDAD\"")

@NamedQueries({
	@NamedQuery(name="bitacoraTipoActividad.findAll", query="SELECT t FROM TipoActividadBitacora t"),
	@NamedQuery(name="bitacoraTipoActividad.elimina", query="update TipoActividadBitacora  set activo = false where idActividad = :idActividad")
})

public class TipoActividadBitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_ACTIVIDAD", sequenceName="\"SEQ_ID_ACTIVIDAD\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ACTIVIDAD")
	@Column(name="\"ID_ACTIVIDAD\"")
	private long idActividad;

	@Column(name="\"A_NOMBRE_ACTIVIDAD\"")
	private String nombreActividad;

	@Column(name="\"B_ACTIVO\"")
	private Boolean activo;
	
	public TipoActividadBitacora() {
	}


	public long getIdActividad() {
		return idActividad;
	}


	public void setIdActividad(long idActividad) {
		this.idActividad = idActividad;
	}


	public String getNombreActividad() {
		return nombreActividad;
	}


	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}


	public Boolean getActivo() {
		return activo;
	}


	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	

}
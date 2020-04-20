package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_PERMISOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PERMISOS\"")
@NamedQuery(name="Permiso.findAll", query="SELECT p FROM Permiso p")
public class Permiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_PERMISO", sequenceName="\"SEQ_ID_PERMISO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PERMISO")
	@Column(name="\"ID_PERMISO\"")
	private long idPermiso;

	@Column(name="\"A_NOMBRE_PERMISO\"")
	private String nombrePermiso;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="\"ID_ROL\"")
	private Rol rol;

	public Permiso() {
	}

	public long getIdPermiso() {
		return this.idPermiso;
	}

	public void setIdPermiso(long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getNombrePermiso() {
		return this.nombrePermiso;
	}

	public void setNombrePermiso(String nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Permiso [idPermiso=" + idPermiso + ", nombrePermiso="
				+ nombrePermiso + ", rol=" + rol.toString() + "]";
	}
	
	

}
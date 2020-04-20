package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_ROLES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_ROLES\"")
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_ROL", sequenceName="\"SEQ_ID_ROL\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ROL")
	@Column(name="\"ID_ROL\"")
	private long idRol;

	@Column(name="\"A_NOMBRE_ROL\"")
	private String nombreRol;

	//bi-directional many-to-one association to UsuarioRol
	@JsonIgnore
	@OneToMany(mappedBy="rol", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	private List<UsuarioRol> usuariosRoles;
	
	//bi-directional many-to-one association to Permiso
	@JsonIgnore
	@OneToMany(mappedBy="rol", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	private List<Permiso> permisos;
	
	//bi-directional many-to-one association to TareaRol
	@OneToMany(mappedBy="id.rol")
	private List<TareaRol> tareasRoles;	
	
	public Rol() {
	}

	public long getIdRol() {
		return this.idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return this.nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public List<UsuarioRol> getUsuariosRoles() {
		return this.usuariosRoles;
	}

	public void setUsuariosRoles(List<UsuarioRol> usuariosRoles) {
		this.usuariosRoles = usuariosRoles;
	}

	public UsuarioRol addUsuariosRole(UsuarioRol usuariosRole) {
		getUsuariosRoles().add(usuariosRole);
		usuariosRole.setRol(this);

		return usuariosRole;
	}

	public UsuarioRol removeUsuariosRole(UsuarioRol usuariosRole) {
		getUsuariosRoles().remove(usuariosRole);
		usuariosRole.setRol(null);

		return usuariosRole;
	}
	
	public List<Permiso> getPermisos() {
		return this.permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	public Permiso addPermiso(Permiso permiso) {
		getPermisos().add(permiso);
		permiso.setRol(this);

		return permiso;
	}

	public Permiso removePermiso(Permiso permiso) {
		getPermisos().remove(permiso);
		permiso.setRol(null);

		return permiso;
	}
	
	/*public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	public Tarea addTarea(Tarea tarea) {
		getTareas().add(tarea);
		tarea.setRol(this);
		
		return tarea;
	}
	
	public Tarea removeTarea(Tarea tarea) {
		getTareas().remove(tarea);
		tarea.setRol(null);
		
		return tarea;
	}*/

	@Override
	public String toString() {
		return "Rol [idRol=" + idRol + ", nombreRol=" + nombreRol + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idRol ^ (idRol >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (idRol != other.idRol)
			return false;
		return true;
	}
	
	public List<TareaRol> getTareasRoles() {
		return this.tareasRoles;
	}

	public void setTareasRoles(List<TareaRol> tareasRoles) {
		this.tareasRoles = tareasRoles;
	}

	public TareaRol addTareasRole(TareaRol tareasRole) {
		getTareasRoles().add(tareasRole);
		tareasRole.getId().setRol(this);

		return tareasRole;
	}

	public TareaRol removeTareasRole(TareaRol tareasRole) {
		getTareasRoles().remove(tareasRole);
		tareasRole.getId().setRol(null);

		return tareasRole;
	}
	
	
	
}
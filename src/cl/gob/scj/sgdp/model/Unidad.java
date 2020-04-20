package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_UNIDADES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_UNIDADES\"")
@NamedQuery(name="Unidad.findAll", query="SELECT u FROM Unidad u")
public class Unidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_UNIDAD", sequenceName="\"SEQ_ID_UNIDAD\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_UNIDAD")
	@Column(name="\"ID_UNIDAD\"")
	private long idUnidad;

	@Column(name="\"A_CODIGO_UNIDAD\"")
	private String codigoUnidad;

	@Column(name="\"A_NOMBRE_COMPLETO_UNIDAD\"")
	private String nombreCompletoUnidad;

	//bi-directional many-to-one association to Proceso
	@JsonIgnore
	@OneToMany(mappedBy="unidad", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<Proceso> procesos;
	
	//bi-directional many-to-one association to Proceso
	@JsonIgnore
	@OneToMany(mappedBy="unidad", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<UsuarioRol> usuariosRoles;
	
	//bi-directional many-to-one association to Proceso
	@JsonIgnore
	@OneToMany(mappedBy="unidad", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeProceso> InstanciasDeProcesos;
	
	public Unidad() {
	}

	public long getIdUnidad() {
		return this.idUnidad;
	}

	public void setIdUnidad(long idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getCodigoUnidad() {
		return this.codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getNombreCompletoUnidad() {
		return this.nombreCompletoUnidad;
	}

	public void setNombreCompletoUnidad(String nombreCompletoUnidad) {
		this.nombreCompletoUnidad = nombreCompletoUnidad;
	}

	public List<Proceso> getProcesos() {
		return this.procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public Proceso addProceso(Proceso proceso) {
		getProcesos().add(proceso);
		proceso.setUnidad(this);

		return proceso;
	}

	public Proceso removeProceso(Proceso proceso) {
		getProcesos().remove(proceso);
		proceso.setUnidad(null);

		return proceso;
	}

	public List<UsuarioRol> getUsuariosRoles() {
		return usuariosRoles;
	}

	public void setUsuariosRoles(List<UsuarioRol> usuariosRoles) {
		this.usuariosRoles = usuariosRoles;
	}
	
	public UsuarioRol addUsuarioRol(UsuarioRol usuarioRol) {
		getUsuariosRoles().add(usuarioRol);
		usuarioRol.setUnidad(this);
		
		return usuarioRol;
	}
	
	public UsuarioRol removeUsuarioRol(UsuarioRol usuarioRol) {
		getUsuariosRoles().remove(usuarioRol);
		usuarioRol.setUnidad(null);
		
		return usuarioRol;
	}

	public List<InstanciaDeProceso> getInstanciasDeProcesos() {
		return InstanciasDeProcesos;
	}

	public void setInstanciasDeProcesos(List<InstanciaDeProceso> instanciasDeProcesos) {
		InstanciasDeProcesos = instanciasDeProcesos;
	}
	
	public InstanciaDeProceso addInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso) {
		getInstanciasDeProcesos().add(instanciaDeProceso);
		instanciaDeProceso.setUnidad(this);

		return instanciaDeProceso;
	}

	public InstanciaDeProceso removeInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso) {
		getInstanciasDeProcesos().remove(instanciaDeProceso);
		instanciaDeProceso.setUnidad(null);

		return instanciaDeProceso;
	}
	
	

}
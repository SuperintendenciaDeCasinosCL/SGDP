package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_USUARIOS_ROLES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_USUARIOS_ROLES\"")
//@NamedQuery(name="UsuarioRol.findAll", query="SELECT u FROM UsuarioRol u")
@NamedQueries({
	
	@NamedQuery(name="UsuarioRol.findAll", query="SELECT u FROM UsuarioRol u"),	
	
	@NamedQuery(name="UsuarioRol.getUsuariosRolesPosiblesPorIdInstanciaDeTarea", 
				query="SELECT u FROM UsuarioRol u, Rol r, TareaRol tr, Tarea t, InstanciaDeTarea it "
						+ "WHERE u.rol.idRol = r.idRol "
						+ "AND r.idRol = tr.id.rol.idRol "
						+ "AND tr.id.tarea.idTarea = t.idTarea "
						+ "AND t.idTarea = it.tarea.idTarea "
						+ "AND it.idInstanciaDeTarea =:idInstanciaDeTarea" ),
						
	@NamedQuery(name="UsuarioRol.getBuscarJefeDePorIdUnidad", 
						query="SELECT u FROM UsuarioRol ur "
							+ " inner join ur.unidad u "
							+ " inner join ur.rol r"
							+ " where r.idRol = 2 "
							+ " and u.idUnidad = :idUnidad" ),
	
	@NamedQuery(name="UsuarioRol.getUsuarioRolPorIdUsuarioIdRol", 
	query="SELECT ur FROM UsuarioRol ur "
		+ " where ur.idUsuario = :idUsuario "
		+ " and ur.rol.idRol = :idRol" ),
	
	@NamedQuery(name="UsuarioRol.getTodosLosIdUsuariosPorVigencia", 
	query="SELECT distinct ur.idUsuario FROM UsuarioRol ur WHERE ur.activo = :vigente "),
	
	@NamedQuery(name="UsuarioRol.getTodosLosIdUsuariosPorNombreRol", 
	query="SELECT distinct ur.idUsuario "
			+ "FROM UsuarioRol ur "
			+ "WHERE ur.activo = true "
			+ "AND ur.fueraDeOficina = false "
			+ "AND ur.rol.nombreRol = :nombreRol "			
			+ "AND strpos(( SELECT p.valorParametroChar FROM Parametro p WHERE p.idParametro = 73 ), ur.idUsuario ) = 0")

})
public class UsuarioRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_USUARIO_ROL", sequenceName="\"SEQ_ID_USUARIO_ROL\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_USUARIO_ROL")
	@Column(name="\"ID_USUARIO_ROL\"")
	private long idUsuarioRol;	

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	@Column(name="\"B_ACTIVO\"")
	private boolean activo;
	
	@Column(name="\"B_FUERA_DE_OFICINA\"")
	private boolean fueraDeOficina;	

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="\"ID_ROL\"")
	private Rol rol;
	
	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="\"ID_UNIDAD\"")
	private Unidad unidad;
	
	@Column(name="\"A_NOMBRE_COMPLETO\"")
	private String nombreCompleto;
	
	@Column(name="\"A_RUT\"")
	private String rut;

	public UsuarioRol() {
	}

	public long getIdUsuarioRol() {
		return this.idUsuarioRol;
	}

	public void setIdUsuarioRol(long idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	
	public boolean getFueraDeOficina() {
		return fueraDeOficina;
	}

	public void setFueraDeOficina(boolean fueraDeOficina) {
		this.fueraDeOficina = fueraDeOficina;
	}	

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}	

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	@Override
	public String toString() {
		return "UsuarioRol [idUsuarioRol=" + idUsuarioRol + ", idUsuario="
				+ idUsuario + ", activo=" + activo + ", rol=" + rol.toString() + ", unidad=" + unidad.toString() 
				 		+ ", fueraDeOficina=" + fueraDeOficina
				 		+ ", nombreCompleto=" + nombreCompleto
				 		+ ", rut=" + rut
						+ "]";
	}
		
}
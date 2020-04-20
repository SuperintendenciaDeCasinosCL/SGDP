package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_USUARIO_RESPONSABILIDAD\"")
@NamedQueries({
	@NamedQuery(name="UsuarioResponsabilidad.findAll", query="SELECT R FROM UsuarioResponsabilidad R"),
	
	@NamedQuery(name="UsuarioResponsabilidad.getUsuariosResponsabilidadesPorIdInstanciaDeTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, InstanciaDeTarea it, UsuarioRol u "
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "		
			+ "and rt.id.tarea.idTarea = it.tarea.idTarea "
			+ "and ur.idUsuario = u.idUsuario "
			+ "and it.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and strpos(( SELECT p.valorParametroChar FROM Parametro p WHERE p.idParametro = 73 ), ur.idUsuario ) = 0"),
	
	@NamedQuery(name="UsuarioResponsabilidad.getPrimerUsuarioResponsabilidadPorIdInstanciaDeTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, InstanciaDeTarea it, UsuarioRol u "
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "		
			+ "and rt.id.tarea.idTarea = it.tarea.idTarea "
			//+ "and ur.orden = 1 "
			+ "and ur.idUsuario = u.idUsuario "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and it.idInstanciaDeTarea = :idInstanciaDeTarea"),
	
	@NamedQuery(name="UsuarioResponsabilidad.getUsuarioResponsabilidadPorIdUsuarioIdTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, UsuarioRol u "
			+ "where ur.idUsuario = :idUsuario "
			+ "and ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "	
			+ "and ur.idUsuario = u.idUsuario "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and rt.id.tarea.idTarea = :idTarea "),
	
	@NamedQuery(name="UsuarioResponsabilidad.getUsuarioResponsabilidadPorIdTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, UsuarioRol u "			
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "
			//+ "and ur.orden = 1 "
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "	
			+ "and ur.idUsuario = u.idUsuario "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and rt.id.tarea.idTarea = :idTarea "),
	
	@NamedQuery(name="UsuarioResponsabilidad.getTodosLosUsuariosResponsabilidadPorIdTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, UsuarioRol u "			
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "			
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "
			+ "and ur.idUsuario = u.idUsuario "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and rt.id.tarea.idTarea = :idTarea "),
	
	@NamedQuery(name="UsuarioResponsabilidad.getUsuariosResponsabilidadesDePrimeraTareaExcluyePorIdProceso", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, Proceso pr, UsuarioRol u "
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "
			+ "and rt.id.tarea.orden = 1 "
			+ "and rt.id.tarea.proceso.idProceso = pr.idProceso "
			+ "and ur.idUsuario = u.idUsuario "
			+ "and pr.idProceso = :idProceso "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and strpos(( SELECT p.valorParametroChar FROM Parametro p WHERE p.idParametro = 94 ), ur.idUsuario ) = 0"),
	
	@NamedQuery(name="UsuarioResponsabilidad.getTodosLosUsuariosResponsabilidadExcluyePorIdTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, UsuarioRol u "			
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "			
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "
			+ "and ur.idUsuario = u.idUsuario "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = false "
			+ "and rt.id.tarea.idTarea = :idTarea "
			+ "and strpos(( SELECT p.valorParametroChar FROM Parametro p WHERE p.idParametro = 94 ), ur.idUsuario ) = 0"),
	
	@NamedQuery(name="UsuarioResponsabilidad.getUsuariosFueraOficinaResponsabilidadesPorIdInstanciaDeTarea", 
	query="SELECT distinct ur FROM UsuarioResponsabilidad ur, Responsabilidad r, ResponsabilidadTarea rt, InstanciaDeTarea it, UsuarioRol u "
			+ "where ur.responsabilidad.idResponsabilidad = r.idResponsabilidad "
			+ "and r.idResponsabilidad = rt.id.responsabilidad.idResponsabilidad "		
			+ "and rt.id.tarea.idTarea = it.tarea.idTarea "
			+ "and ur.idUsuario = u.idUsuario "
			+ "and it.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "and u.activo = true "
			+ "and u.fueraDeOficina = true "
			+ "and strpos(( SELECT p.valorParametroChar FROM Parametro p WHERE p.idParametro = 73 ), ur.idUsuario ) = 0")
})
public class UsuarioResponsabilidad implements Serializable  {

	@Id
	@SequenceGenerator(name="SEQ_ID_USUARIO_RESPONSABILIDAD", sequenceName="\"SEQ_ID_USUARIO_RESPONSABILIDAD\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_USUARIO_RESPONSABILIDAD")
	@Column(name="\"ID_USUARIO_RESPONSABILIDAD\"")
	private long idUsuarioResponsabilidad;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@ManyToOne
	@JoinColumn(name="\"ID_RESPONSABILIDAD\"")
	private Responsabilidad responsabilidad;
	
	@Column(name="\"N_ORDEN\"")
	private int orden;

	public long getIdUsuarioResponsabilidad() {
		return idUsuarioResponsabilidad;
	}

	public void setIdUsuarioResponsabilidad(long idUsuarioResponsabilidad) {
		this.idUsuarioResponsabilidad = idUsuarioResponsabilidad;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Responsabilidad getResponsabilidad() {
		return responsabilidad;
	}

	public void setResponsabilidad(Responsabilidad responsabilidad) {
		this.responsabilidad = responsabilidad;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Override
	public String toString() {
		return "UsuarioResponsabilidad [idUsuarioResponsabilidad=" + idUsuarioResponsabilidad + ", idUsuario="
				+ idUsuario + ", orden=" + orden + "]";
	}
	
}

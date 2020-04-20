package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_USUARIO_NOTIFICACION_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_USUARIO_NOTIFICACION_TAREA\"")
@NamedQueries({	
	
	@NamedQuery(name="usuarioNotificacionTarea.findAll", query="SELECT s FROM UsuarioNotificacionTarea s"),
	
	@NamedQuery(name="UsuarioNotificacionTarea.getUsuariosNotificacionTareaPorIdTareaYUsuario", query="SELECT t FROM UsuarioNotificacionTarea t"
					+ "	where t.tarea.idTarea = :idTarea and  t.idUsuario = :idUsuario "),
	
	@NamedQuery(name="UsuarioNotificacionTarea.getUsuariosNotificacionTareaPorIdTarea", 
				query="SELECT s FROM UsuarioNotificacionTarea s INNER JOIN s.tarea t WHERE t.idTarea = :idTarea")

})

public class UsuarioNotificacionTarea implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="SGDP_USUARIO_NOTIFICACION_TAR_ID_USUARIO_NOTIFICACION_TAREA_seq", sequenceName="\"SGDP_USUARIO_NOTIFICACION_TAR_ID_USUARIO_NOTIFICACION_TAREA_seq\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SGDP_USUARIO_NOTIFICACION_TAR_ID_USUARIO_NOTIFICACION_TAREA_seq")	
	@Column(name="\"ID_USUARIO_NOTIFICACION_TAREA\"")
	private Long idUsuarioNotificacionTarea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_CREACION\"")
	private Date dFechaCreacion;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA\"")
	private Tarea tarea;

	public UsuarioNotificacionTarea() {
	}

	public Long getIdUsuarioNotificacionTarea() {
		return this.idUsuarioNotificacionTarea;
	}

	public void setIdUsuarioNotificacionTarea(Long idUsuarioNotificacionTarea) {
		this.idUsuarioNotificacionTarea = idUsuarioNotificacionTarea;
	}

	public Date getDFechaCreacion() {
		return this.dFechaCreacion;
	}

	public void setDFechaCreacion(Date dFechaCreacion) {
		this.dFechaCreacion = dFechaCreacion;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	@Override
	public String toString() {
		return "UsuarioNotificacionTarea [idUsuarioNotificacionTarea=" + idUsuarioNotificacionTarea
				+ ", dFechaCreacion=" + dFechaCreacion + ", idUsuario=" + idUsuario + ", tarea=" + tarea + "]";
	}
	
}
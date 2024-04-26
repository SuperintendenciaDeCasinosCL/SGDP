package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_LOG_ACCIONES_USUARIO" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_LOG_ACCIONES_USUARIO\"")
@NamedQueries({	
	@NamedQuery(name="LogAccionesUsuario.findAll", query="SELECT u FROM LogAccionesUsuario u")
	})
public class LogAccionesUsuario implements Serializable {
	
	@Id
	@SequenceGenerator(name="SEQ_ID_LOG_ACCION_USUARIO", sequenceName="\"SEQ_ID_LOG_ACCION_USUARIO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_LOG_ACCION_USUARIO")
	@Column(name="\"ID_LOG_ACCION_USUARIO\"")
	private long idLogAccionUsuario;

	@Column(name="\"A_MODULO\"")
	private String modulo;
	
	@Column(name="\"A_NOMBRE_ACCION\"")
	private String nombreAccion;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ACCION\"")
	private Date fechaAccion;
	
	@Column(name="\"J_DATA\"")
	private String data;

	public long getIdLogAccionUsuario() {
		return idLogAccionUsuario;
	}

	public void setIdLogAccionUsuario(long idLogAccionUsuario) {
		this.idLogAccionUsuario = idLogAccionUsuario;
	}

	public String getNombreAccion() {
		return nombreAccion;
	}

	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

}

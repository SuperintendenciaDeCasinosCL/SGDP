package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import java.util.Date;


/**
 * The persistent class for the "SGDP_LOG_TRANSACCIONES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_LOG_TRANSACCIONES\"")
@NamedQuery(name="LogTransaccion.findAll", query="SELECT l FROM LogTransaccion l")
public class LogTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_LOG_TRANSACCION", sequenceName="\"SEQ_ID_LOG_TRANSACCION\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_LOG_TRANSACCION")
	@Column(name="\"ID_LOG_TRANSACCION\"")
	private long idLogTransaccion;

	@Column(name="\"A_ACCION\"")
	private String accion;

	@Column(name="\"A_NOMBRE_TABLA\"")
	private String nombreTabla;

	@Column(name="\"A_PARAMETROS\"")
	private String parametros;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_TRANSACCION\"")
	private Date fechaTransaccion;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	public LogTransaccion() {
	}

	public long getIdLogTransaccion() {
		return this.idLogTransaccion;
	}

	public void setIdLogTransaccion(long idLogTransaccion) {
		this.idLogTransaccion = idLogTransaccion;
	}

	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNombreTabla() {
		return this.nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}


	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public Date getFechaTransaccion() {
		return this.fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

}
package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_LOG_ERROR\"")
@NamedQuery(name="LogError.findAll", query="SELECT l FROM LogError l")
public class LogError implements Serializable {	
	
	@Id
	@SequenceGenerator(name="SEQ_ID_LOG_ERROR", sequenceName="\"SEQ_ID_LOG_ERROR\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_LOG_ERROR")
	@Column(name="\"ID_LOG_ERROR\"")
	private long idLogError;
	
	@Column(name="\"A_NOMBRE_ERROR\"")
	private String nombreError;
	
	@Column(name="\"A_MENSAJE_EXCEPCION\"")
	private String messageException;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ERROR\"")
	private Date fechaError;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Column(name="\"A_DATOS_ADICIONALES\"")
	private String datosAdicionales;

	public long getIdLogError() {
		return idLogError;
	}

	public void setIdLogError(long idLogError) {
		this.idLogError = idLogError;
	}

	public String getNombreError() {
		return nombreError;
	}

	public void setNombreError(String nombreError) {
		this.nombreError = nombreError;
	}

	public String getMessageException() {
		return messageException;
	}

	public void setMessageException(String messageException) {
		this.messageException = messageException;
	}

	public Date getFechaError() {
		return fechaError;
	}

	public void setFechaError(Date fechaError) {
		this.fechaError = fechaError;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDatosAdicionales() {
		return datosAdicionales;
	}

	public void setDatosAdicionales(String datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

	@Override
	public String toString() {
		return "LogError [idLogError=" + idLogError + ", nombreError=" + nombreError + ", messageException="
				+ messageException + ", fechaError=" + fechaError + ", idUsuario=" + idUsuario + ", datosAdicionales="
				+ datosAdicionales + "]";
	}
	
}

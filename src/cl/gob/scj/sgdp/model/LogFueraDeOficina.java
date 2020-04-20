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
@Table(name="\"SGDP_LOG_FUERA_DE_OFICINA\"")
@NamedQuery(name="LogFueraDeOficina.findAll", query="SELECT l FROM LogFueraDeOficina l")
public class LogFueraDeOficina implements Serializable {

	@Id
	@SequenceGenerator(name="SEQ_ID_LOG_FUERA_DE_OFICINA", sequenceName="\"SEQ_ID_LOG_FUERA_DE_OFICINA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_LOG_FUERA_DE_OFICINA")
	@Column(name="\"ID_LOG_FUERA_DE_OFICINA\"")
	private long idLogFueraDeOficina;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ACTUALIZACION\"")
	private Date fechaActualizacion;
	
	@Column(name="\"B_FUERA_DE_OFICINA\"")
	private boolean fueraDeOficina;

	public long getIdLogFueraDeOficina() {
		return idLogFueraDeOficina;
	}

	public void setIdLogFueraDeOficina(long idLogFueraDeOficina) {
		this.idLogFueraDeOficina = idLogFueraDeOficina;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public boolean isFueraDeOficina() {
		return fueraDeOficina;
	}

	public void setFueraDeOficina(boolean fueraDeOficina) {
		this.fueraDeOficina = fueraDeOficina;
	}

	@Override
	public String toString() {
		return "LogFueraDeOficina [idLogFueraDeOficina=" + idLogFueraDeOficina + ", idUsuario=" + idUsuario
				+ ", fechaActualizacion=" + fechaActualizacion + ", fueraDeOficina=" + fueraDeOficina + "]";
	}	
		
}

package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"SGDP_LOG_CARGA\"")
public class LogCarga implements Serializable {
	@Id
	@SequenceGenerator(name = "SEQ_ID_LOG_CARGA", sequenceName = "\"SEQ_ID_LOG_CARGA\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_LOG_CARGA")
	@Column(name = "\"ID_LOG_CARGA\"")
	private Long idLogCarga;

	@ManyToOne
	@JoinColumn(name = "\"ID_CARGA\"")
	private Carga cargaLog;

	@Column(name = "\"A_DESCRIPCION\"")
	private String aDescripcion;

	@Column(name = "\"D_FECHA_CREACION\"")
	private Date dFechaCreacion;
	
	public Long getIdLogCarga() {
		return idLogCarga;
	}

	public void setIdLogCarga(Long idLogCarga) {
		this.idLogCarga = idLogCarga;
	}



	public String getaDescripcion() {
		return aDescripcion;
	}

	public void setaDescripcion(String aDescripcion) {
		this.aDescripcion = aDescripcion;
	}

	public Date getdFechaCreacion() {
		return dFechaCreacion;
	}

	public void setdFechaCreacion(Date dFechaCreacion) {
		this.dFechaCreacion = dFechaCreacion;
	}

	public Carga getCargaLog() {
		return cargaLog;
	}

	public void setCargaLog(Carga cargaLogEntity) {
		this.cargaLog = cargaLogEntity;
	}
	
	
	public LogCarga() {
		super();
	}
	
	public LogCarga(Carga cargaLog, String aDescripcion, Date dFechaCreacion ) {
		this.cargaLog = cargaLog;
		this.aDescripcion = aDescripcion;
		this.dFechaCreacion = dFechaCreacion;
	}
}

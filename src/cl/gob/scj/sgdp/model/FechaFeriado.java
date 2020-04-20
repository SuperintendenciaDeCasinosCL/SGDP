package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the "SGDP_FECHAS_FERIADOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_FECHAS_FERIADOS\"")
@NamedQuery(name="FechaFeriado.findAll", query="SELECT f FROM FechaFeriado f")
public class FechaFeriado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"A_FECHA_FERIADO\"")
	private String fechaFeriado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_FERIADO\"")
	private Date dFechaFeriado;

	public FechaFeriado() {
	}

	public String getFechaFeriado() {
		return this.fechaFeriado;
	}

	public void setFechaFeriado(String fechaFeriado) {
		this.fechaFeriado = fechaFeriado;
	}

	public Date getDFechaFeriado() {
		return this.dFechaFeriado;
	}

	public void setDFechaFeriado(Date dFechaFeriado) {
		this.dFechaFeriado = dFechaFeriado;
	}

}
package cl.gob.scj.sgdp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="\"SGDP_HISTORICO_FECHA_VENC_INS_PROC\"")
@NamedQuery(name="HistoricoFechaVencimientoInstanciaProceso.findAll", query="SELECT a FROM HistoricoFechaVencimientoInstanciaProceso a")
public class HistoricoFechaVencimientoInstanciaProceso {
	
	@Id
	@SequenceGenerator(name="SEQ_ID_HIST_FECHA_VENC_INS_PROC", sequenceName="\"SEQ_ID_HIST_FECHA_VENC_INS_PROC\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HIST_FECHA_VENC_INS_PROC")
	@Column(name="\"ID_HIST_FECHA_VENC_INS_PROC\"")
	private long idHistoricoFechaVencimientoInstanciaProceso;	
	
	//bi-directional many-to-one association to InstanciaDeProceso
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"")
	private InstanciaDeTarea instanciaDeTarea;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VENCIMIENTO\"")
	private Date fechaVencimiento;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	public long getIdHistoricoFechaVencimientoInstanciaProceso() {
		return idHistoricoFechaVencimientoInstanciaProceso;
	}

	public void setIdHistoricoFechaVencimientoInstanciaProceso(long idHistoricoFechaVencimientoInstanciaProceso) {
		this.idHistoricoFechaVencimientoInstanciaProceso = idHistoricoFechaVencimientoInstanciaProceso;
	}

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "HistoricoFechaVencimientoInstanciaProceso [idHistoricoFechaVencimientoInstanciaProceso="
				+ idHistoricoFechaVencimientoInstanciaProceso + ", instanciaDeTarea=" + instanciaDeTarea
				+ ", fechaVencimiento=" + fechaVencimiento + ", idUsuario=" + idUsuario + "]";
	}

}

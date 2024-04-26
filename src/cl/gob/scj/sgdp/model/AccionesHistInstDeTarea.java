package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the "SGDP_ACCIONES_HIST_INST_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_ACCIONES_HIST_INST_DE_TAREAS\"")
@NamedQuery(name="AccionesHistInstDeTarea.findAll", query="SELECT a FROM AccionesHistInstDeTarea a")
public class AccionesHistInstDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_ACCION_HISTORICO_INST_DE_TAREA", sequenceName="\"SEQ_ID_ACCION_HISTORICO_INST_DE_TAREA\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ACCION_HISTORICO_INST_DE_TAREA")
	@Column(name="\"ID_ACCION_HISTORICO_INST_DE_TAREA\"")
	private long idAccionHistoricoInstDeTarea;

	@Column(name="\"A_NOMBRE_ACCION\"")
	private String nombreAccion;

	//bi-directional many-to-one association to HistoricoDeInstDeTarea
	@OneToMany(mappedBy="accionHistInstDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoDeInstDeTarea> historicosDeInstDeTareas;	
	
	@Column(name="\"A_DESC_ACCION\"")
	private String descAccion;

	public AccionesHistInstDeTarea() {
	}

	public long getIdAccionHistoricoInstDeTarea() {
		return this.idAccionHistoricoInstDeTarea;
	}

	public void setIdAccionHistoricoInstDeTarea(long idAccionHistoricoInstDeTarea) {
		this.idAccionHistoricoInstDeTarea = idAccionHistoricoInstDeTarea;
	}

	public String getNombreAccion() {
		return this.nombreAccion;
	}

	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}

	public List<HistoricoDeInstDeTarea> getHistoricosDeInstDeTareas() {
		return this.historicosDeInstDeTareas;
	}

	public void setHistoricosDeInstDeTareas(List<HistoricoDeInstDeTarea> historicosDeInstDeTareas) {
		this.historicosDeInstDeTareas = historicosDeInstDeTareas;
	}

	public String getDescAccion() {
		return descAccion;
	}

	public void setDescAccion(String descAccion) {
		this.descAccion = descAccion;
	}

	public HistoricoDeInstDeTarea addHistoricosDeInstDeTarea(HistoricoDeInstDeTarea historicosDeInstDeTarea) {
		getHistoricosDeInstDeTareas().add(historicosDeInstDeTarea);
		historicosDeInstDeTarea.setAccionHistInstDeTarea(this);

		return historicosDeInstDeTarea;
	}

	public HistoricoDeInstDeTarea removeHistoricosDeInstDeTarea(HistoricoDeInstDeTarea historicosDeInstDeTarea) {
		getHistoricosDeInstDeTareas().remove(historicosDeInstDeTarea);
		historicosDeInstDeTarea.setAccionHistInstDeTarea(null);

		return historicosDeInstDeTarea;
	}

}
package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_ESTADO_SOLICITUD_CREACION_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_ESTADO_SOLICITUD_CREACION_EXP\"")
@NamedQuery(name="EstadoSolicitudCreacionExp.findAll", query="SELECT e FROM EstadoSolicitudCreacionExp e")
public class EstadoSolicitudCreacionExp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_ESTADO_SOLICITUD_CREACION_EXP", sequenceName="\"SEQ_ID_ESTADO_SOLICITUD_CREACION_EXP\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ESTADO_SOLICITUD_CREACION_EXP")
	@Column(name="\"ID_ESTADO_SOLICITUD_CREACION_EXP\"")
	private Long idEstadoSolicitudCreacionExp;

	@Column(name="\"A_NOMBRE_ESTADO_SOLICITUD_CREACION_EXP\"")
	private String nombreEstadoSolicitudCreacionExp;

	//bi-directional many-to-one association to SolicitudCreacionExp
	@OneToMany(mappedBy="estadoSolicitudCreacionExp")
	private List<SolicitudCreacionExp> solicitudesCreacionExps;
	
	//bi-directional many-to-one association to HistoricoSolicitudCreacionExp
	@OneToMany(mappedBy="estadoSolicitudCreacionExp")
	private List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps;

	public EstadoSolicitudCreacionExp() {
	}

	public Long getIdEstadoSolicitudCreacionExp() {
		return this.idEstadoSolicitudCreacionExp;
	}

	public void setIdEstadoSolicitudCreacionExp(Long idEstadoSolicitudCreacionExp) {
		this.idEstadoSolicitudCreacionExp = idEstadoSolicitudCreacionExp;
	}

	public String getNombreEstadoSolicitudCreacionExp() {
		return this.nombreEstadoSolicitudCreacionExp;
	}

	public void setNombreEstadoSolicitudCreacionExp(String nombreEstadoSolicitudCreacionExp) {
		this.nombreEstadoSolicitudCreacionExp = nombreEstadoSolicitudCreacionExp;
	}

	public List<SolicitudCreacionExp> getSolicitudesCreacionExps() {
		return this.solicitudesCreacionExps;
	}

	public void setSolicitudesCreacionExps(List<SolicitudCreacionExp> solicitudesCreacionExps) {
		this.solicitudesCreacionExps = solicitudesCreacionExps;
	}

	public SolicitudCreacionExp addSolicitudesCreacionExp(SolicitudCreacionExp solicitudesCreacionExp) {
		getSolicitudesCreacionExps().add(solicitudesCreacionExp);
		solicitudesCreacionExp.setEstadoSolicitudCreacionExp(this);

		return solicitudesCreacionExp;
	}

	public SolicitudCreacionExp removeSolicitudesCreacionExp(SolicitudCreacionExp solicitudesCreacionExp) {
		getSolicitudesCreacionExps().remove(solicitudesCreacionExp);
		solicitudesCreacionExp.setEstadoSolicitudCreacionExp(null);

		return solicitudesCreacionExp;
	}

	public List<HistoricoSolicitudCreacionExp> getHistoricoSolicitudesCreacionExps() {
		return historicoSolicitudesCreacionExps;
	}

	public void setHistoricoSolicitudesCreacionExps(List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps) {
		this.historicoSolicitudesCreacionExps = historicoSolicitudesCreacionExps;
	}
	
	public HistoricoSolicitudCreacionExp addHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().add(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setEstadoSolicitudCreacionExp(this);

		return historicoSolicitudCreacionExp;
	}

	public HistoricoSolicitudCreacionExp removeHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().remove(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setEstadoSolicitudCreacionExp(null);

		return historicoSolicitudCreacionExp;
	}	

}
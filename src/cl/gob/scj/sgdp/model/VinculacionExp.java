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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_VINCULACION_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_VINCULACION_EXP\"")
@NamedQueries({
	@NamedQuery(name="VinculacionExp.findAll", query="SELECT v FROM VinculacionExp v"),
	
	@NamedQuery(name="VinculacionExp.getVinculacionExpSucesoresPorIdInstProcAntecesor", 
		query="SELECT v FROM VinculacionExp v WHERE v.instanciaDeProcesoAntecesor.idInstanciaDeProceso = :idInstanciaDeProceso "),
	
	@NamedQuery(name="VinculacionExp.getVinculacionExpPorNombreExp", 
	query="SELECT v FROM VinculacionExp v WHERE v.instanciaDeProceso.nombreExpediente = :nombreExpediente "),
	
	@NamedQuery(name="VinculacionExp.getVinculacionExpPorIdInstProcIdInstProcAntecesor", 
		query="SELECT v FROM VinculacionExp v WHERE v.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso "
				+ "AND v.instanciaDeProcesoAntecesor.idInstanciaDeProceso = :idInstanciaDeProcesoAntecesor ")
})
public class VinculacionExp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_ID_VINCULACION_EXP", sequenceName="\"SEQ_ID_VINCULACION_EXP\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_VINCULACION_EXP")
	@Column(name="\"ID_VINCULACION_EXP\"")
	private long idVinculacionExp;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VINCULACION\"")
	private Date fechaVinculacion;

	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO\"")	
	private InstanciaDeProceso instanciaDeProceso;
	
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO_ANTECESOR\"")	
	private InstanciaDeProceso instanciaDeProcesoAntecesor;
	
	@Column(name="\"A_COMENTARIO\"")
	private String comentario;
	
	public long getIdVinculacionExp() {
		return idVinculacionExp;
	}

	public void setIdVinculacionExp(long idVinculacionExp) {
		this.idVinculacionExp = idVinculacionExp;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}

	public InstanciaDeProceso getInstanciaDeProceso() {
		return instanciaDeProceso;
	}

	public void setInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso) {
		this.instanciaDeProceso = instanciaDeProceso;
	}

	public InstanciaDeProceso getInstanciaDeProcesoAntecesor() {
		return instanciaDeProcesoAntecesor;
	}

	public void setInstanciaDeProcesoAntecesor(InstanciaDeProceso instanciaDeProcesoAntecesor) {
		this.instanciaDeProcesoAntecesor = instanciaDeProcesoAntecesor;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "VinculacionExp [idVinculacionExp=" + idVinculacionExp + ", idUsuario=" + idUsuario
				+ ", fechaVinculacion=" + fechaVinculacion + ", instanciaDeProceso=" + instanciaDeProceso
				+ ", instanciaDeProcesoAntecesor=" + instanciaDeProcesoAntecesor 
				+ ", comentario=" + comentario 
				+ "]";
	}
}
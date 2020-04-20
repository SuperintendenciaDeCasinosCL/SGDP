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
 * The persistent class for the "SGDP_HISTORICO_VINCULACION_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_VINCULACION_EXP\"")
@NamedQueries({
	@NamedQuery(name="HistoricoVinculacionExp.findAll", query="SELECT h FROM HistoricoVinculacionExp h"),
	@NamedQuery(name="HistoricoVinculacionExp.getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor", 
		query="SELECT h FROM HistoricoVinculacionExp h WHERE h.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso "
				+ "AND h.instanciaDeProcesoAntecesor.idInstanciaDeProceso = :idInstanciaDeProcesoAntecesor AND (h.vigente <> false OR h.vigente is null  ) "
				+ "ORDER BY h.fecha DESC ")
})
public class HistoricoVinculacionExp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_HISTORICO_VINCULACION_EXP", sequenceName="\"SEQ_ID_HISTORICO_VINCULACION_EXP\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HISTORICO_VINCULACION_EXP")
	@Column(name="\"ID_HISTORICO_VINCULACION_EXP\"")
	private long idHistoricoVinculacionExp;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA\"")
	private Date fecha;

	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO\"")
	private InstanciaDeProceso instanciaDeProceso;
	
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO_ANTECESOR\"")
	private InstanciaDeProceso instanciaDeProcesoAntecesor;
	
	@Column(name="\"A_TIPO_ACCION\"")
	private String tipoAccion;
	
	@Column(name="\"A_COMENTARIO\"")
	private String comentario;
	
	@Column(name="\"B_VIGENTE\"")
	private Boolean vigente;

	public long getIdHistoricoVinculacionExp() {
		return idHistoricoVinculacionExp;
	}

	public void setIdHistoricoVinculacionExp(long idHistoricoVinculacionExp) {
		this.idHistoricoVinculacionExp = idHistoricoVinculacionExp;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}	
	
}

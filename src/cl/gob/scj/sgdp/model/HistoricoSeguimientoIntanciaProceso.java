package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_HISTORICO_SEGUIMIENTO_INTANCIA_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_SEGUIMIENTO_INTANCIA_PROCESOS\"")
@NamedQuery(name="HistoricoSeguimientoIntanciaProceso.findAll", query="SELECT s FROM HistoricoSeguimientoIntanciaProceso s")
public class HistoricoSeguimientoIntanciaProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SGDP_HISTORIAL_SEGUIMIENTO_IN_ID_HISTORICO_INSTANCIA_PROCES_seq", sequenceName="\"SGDP_HISTORIAL_SEGUIMIENTO_IN_ID_HISTORICO_INSTANCIA_PROCES_seq\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SGDP_HISTORIAL_SEGUIMIENTO_IN_ID_HISTORICO_INSTANCIA_PROCES_seq")
	@Column(name="\"ID_HISTORICO_INSTANCIA_PROCESO\"")
	private Long idHistoricoInstanciaProceso;

	@Column(name="\"ID_INSTANCIA_PROCESO\"")
	private Long idInstanciaProceso;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	@Column(name="\"ID_USUARIO_ACCION\"")
	private String idUsuarioAccion;
	
	@Column(name="\"A_ACCION\"")
	private String accion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ACCION\"")
	private Date fechaAccion;
	
	@Column(name="\"A_TIPO_DE_NOTIFICACION\"")
	private String tipoDeNotificacion;	
	
	public HistoricoSeguimientoIntanciaProceso() {
	}

	public Long getIdHistoricoInstanciaProceso() {
		return this.idHistoricoInstanciaProceso;
	}

	public void setIdHistoricoInstanciaProceso(Long idHistoricoInstanciaProceso) {
		this.idHistoricoInstanciaProceso = idHistoricoInstanciaProceso;
	}

	public Long getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	public void setIdInstanciaProceso(Long idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdUsuarioAccion() {
		return idUsuarioAccion;
	}

	public void setIdUsuarioAccion(String idUsuarioAccion) {
		this.idUsuarioAccion = idUsuarioAccion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public String getTipoDeNotificacion() {
		return tipoDeNotificacion;
	}

	public void setTipoDeNotificacion(String tipoDeNotificacion) {
		this.tipoDeNotificacion = tipoDeNotificacion;
	}
		
}
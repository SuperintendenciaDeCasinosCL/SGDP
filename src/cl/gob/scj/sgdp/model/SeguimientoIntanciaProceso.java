package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "SGDP_SEGUIMIENTO_INTANCIA_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_SEGUIMIENTO_INTANCIA_PROCESOS\"")
@NamedQuery(name="SeguimientoIntanciaProceso.findAll", query="SELECT s FROM SeguimientoIntanciaProceso s")
public class SeguimientoIntanciaProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SeguimientoIntanciaProcesoPK id;
	
	@Column(name="\"A_TIPO_DE_NOTIFICACION\"")
	private String tipoDeNotificacion;	

	public SeguimientoIntanciaProceso() {
	}

	public SeguimientoIntanciaProcesoPK getId() {
		return this.id;
	}

	public void setId(SeguimientoIntanciaProcesoPK id) {
		this.id = id;
	}

	public String getTipoDeNotificacion() {
		return tipoDeNotificacion;
	}

	public void setTipoDeNotificacion(String tipoDeNotificacion) {
		this.tipoDeNotificacion = tipoDeNotificacion;
	}	
	
}
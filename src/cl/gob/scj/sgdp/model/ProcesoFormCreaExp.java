package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the "SGDP_PROCESO_FORM_CREA_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PROCESO_FORM_CREA_EXP\"")
@NamedQueries({
@NamedQuery(name="ProcesoFormCreaExp.getTodosProcesoFormCreaExp", query="SELECT "
		+ "p.idProcesoFormCreaExp as idProcesoFormCreaExp, "
		+ "p.codigoProceso as codigoProceso, "
		+ "p.fecha as fecha, "
		+ "p.idUsuario as idUsuario, "
		+ "(SELECT q.nombreProceso FROM Proceso q WHERE q.vigente = true AND q.codigoProceso = p.codigoProceso) as nombreProceso, "
		+ "(SELECT q.idProceso FROM Proceso q WHERE q.vigente = true AND q.codigoProceso = p.codigoProceso) as idProceso "
		+ " FROM ProcesoFormCreaExp p, Proceso h WHERE p.codigoProceso = h.codigoProceso and h.vigente = true ORDER BY h.nombreProceso ")
})
public class ProcesoFormCreaExp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_PROCESO_FORM_CREA_EXP", sequenceName="\"SEQ_ID_PROCESO_FORM_CREA_EXP\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PROCESO_FORM_CREA_EXP")
	@Column(name="\"ID_PROCESO_FORM_CREA_EXP\"")
	private Long idProcesoFormCreaExp;

	@Column(name="\"A_CODIGO_PROCESO\"")
	private String codigoProceso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA\"")
	private Date fecha;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	public ProcesoFormCreaExp() {
	}

	public Long getIdProcesoFormCreaExp() {
		return this.idProcesoFormCreaExp;
	}

	public void setIdProcesoFormCreaExp(Long idProcesoFormCreaExp) {
		this.idProcesoFormCreaExp = idProcesoFormCreaExp;
	}

	public String getCodigoProceso() {
		return this.codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}		

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "ProcesoFormCreaExp [idProcesoFormCreaExp=" + idProcesoFormCreaExp + ", codigoProceso=" + codigoProceso
				+ ", fecha=" + fecha + ", idUsuario=" + idUsuario + "]";
	}
	
}
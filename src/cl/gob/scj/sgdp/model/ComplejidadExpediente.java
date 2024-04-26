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

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_ARCHIVOS_SOL_CREA_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_COMPLEJIDAD_EXPEDIENTE\"")
@NamedQueries({
	@NamedQuery(name="ComplejidadExpediente.findLastByIdInstanciaProceso", query="SELECT c FROM ComplejidadExpediente c where c.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso order by c.fecha desc"),
	@NamedQuery(name="ComplejidadExpediente.findLastByNombreExpediente", query="SELECT c FROM ComplejidadExpediente c where c.instanciaDeProceso.nombreExpediente = :nombreExpediente order by c.fecha desc"),
	@NamedQuery(name="ComplejidadExpediente.findAllByNombreExpediente", query="SELECT c FROM ComplejidadExpediente c where c.instanciaDeProceso.nombreExpediente = :nombreExpediente order by c.fecha desc"),
	@NamedQuery(name="ComplejidadExpediente.findAllByIdInstanciaProceso", query="SELECT c FROM ComplejidadExpediente c where c.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso ")
})
public class ComplejidadExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
    @SequenceGenerator(name="SEQ_ID_COMPLEJIDAD_EXPEDIENTE", sequenceName="\"SEQ_ID_COMPLEJIDAD_EXPEDIENTE\"", allocationSize=Constantes.ALLOCATION_SIZE)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_COMPLEJIDAD_EXPEDIENTE")
	@Column(name="\"ID_COMPLEJIDAD_EXPEDIENTE\"")
	private Long idComplejidadExpediente;

	@Column(name="\"A_COMPLEJIDAD\"")
	private String complejidad;

	@Column(name="\"A_MOTIVO_COMPLEJIDAD\"")
	private String motivoComplejidad;

	@Column(name="\"D_FECHA\"")
	private Date fecha;
	
	@Column(name="\"A_USUARIO\"")
	private String usuario;

	//bi-directional many-to-one association to InstanciaDeProceso
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO\"")
	private InstanciaDeProceso instanciaDeProceso;

	public ComplejidadExpediente() {
	}

	public Long getIdComplejidadExpediente() {
		return idComplejidadExpediente;
	}

	public void setIdComplejidadExpediente(Long idComplejidadExpediente) {
		this.idComplejidadExpediente = idComplejidadExpediente;
	}

	public String getComplejidad() {
		return complejidad;
	}

	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}

	public String getMotivoComplejidad() {
		return motivoComplejidad;
	}

	public void setMotivoComplejidad(String motivoComplejidad) {
		this.motivoComplejidad = motivoComplejidad;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	

}

package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_ESTADOS_DE_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_ESTADOS_DE_PROCESOS\"")
@NamedQuery(name="EstadoDeProceso.findAll", query="SELECT e FROM EstadoDeProceso e")
public class EstadoDeProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_ESTADO_DE_PROCESO", sequenceName="\"SEQ_ID_ESTADO_DE_PROCESO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ESTADO_DE_PROCESO")
	@Column(name="\"ID_ESTADO_DE_PROCESO\"")
	private long idEstadoDeProceso;

	@Column(name="\"A_NOMBRE_ESTADO_DE_PROCESO\"")
	private String nombreEstadoDeProceso;

	@Column(name="\"N_CODIGO_ESTADO_DE_PROCESO\"")
	private int codigoEstadoDeProceso;

	//bi-directional many-to-one association to InstanciaDeProceso
	@JsonIgnore
	@OneToMany(mappedBy="estadoDeProceso" , cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeProceso> instanciasDeProcesos;

	public EstadoDeProceso() {
	}

	public long getIdEstadoDeProceso() {
		return this.idEstadoDeProceso;
	}

	public void setIdEstadoDeProceso(long idEstadoDeProceso) {
		this.idEstadoDeProceso = idEstadoDeProceso;
	}

	public String getNombreEstadoDeProceso() {
		return this.nombreEstadoDeProceso;
	}

	public void setNombreEstadoDeProceso(String nombreEstadoDeProceso) {
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
	}

	public int getCodigoEstadoDeProceso() {
		return this.codigoEstadoDeProceso;
	}

	public void setCodigoEstadoDeProceso(int codigoEstadoDeProceso) {
		this.codigoEstadoDeProceso = codigoEstadoDeProceso;
	}

	public List<InstanciaDeProceso> getInstanciasDeProcesos() {
		return this.instanciasDeProcesos;
	}

	public void setInstanciasDeProcesos(List<InstanciaDeProceso> instanciasDeProcesos) {
		this.instanciasDeProcesos = instanciasDeProcesos;
	}

	public InstanciaDeProceso addInstanciasDeProceso(InstanciaDeProceso instanciasDeProceso) {
		getInstanciasDeProcesos().add(instanciasDeProceso);
		instanciasDeProceso.setEstadoDeProceso(this);

		return instanciasDeProceso;
	}

	public InstanciaDeProceso removeInstanciasDeProceso(InstanciaDeProceso instanciasDeProceso) {
		getInstanciasDeProcesos().remove(instanciasDeProceso);
		instanciasDeProceso.setEstadoDeProceso(null);

		return instanciasDeProceso;
	}

}
package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the "SGDP_SEGUIMIENTO_INTANCIA_PROCESOS" database
 * table.
 * 
 */
@Embeddable
public class SeguimientoIntanciaProcesoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/*
	 * @Column(name="\"ID_INSTANCIA_PROCESO\"", insertable=false,
	 * updatable=false) private Long idInstanciaProceso;
	 */

	// bi-directional many-to-one association to SgdpInstanciasDeProceso
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_PROCESO\"", insertable = true)
	private InstanciaDeProceso instanciaDeProceso;

	@Column(name = "\"ID_USUARIO\"")
	private String idUsuario;

	public SeguimientoIntanciaProcesoPK() {
	}

	public InstanciaDeProceso getInstanciaDeProceso() {
		return instanciaDeProceso;
	}

	public void setInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso) {
		this.instanciaDeProceso = instanciaDeProceso;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime
				* result
				+ ((instanciaDeProceso == null) ? 0 : instanciaDeProceso
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeguimientoIntanciaProcesoPK other = (SeguimientoIntanciaProcesoPK) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (instanciaDeProceso == null) {
			if (other.instanciaDeProceso != null)
				return false;
		} else if (!instanciaDeProceso.equals(other.instanciaDeProceso))
			return false;
		return true;
	}


	

}
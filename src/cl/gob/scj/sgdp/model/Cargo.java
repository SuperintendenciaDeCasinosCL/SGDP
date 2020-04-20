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
 * The persistent class for the "SGDP_CARGO" database table.
 * 
 */
@Entity
@Table(name="\" SGDP_CARGO\"")
@NamedQuery(name="Cargo.findAll", query="SELECT c FROM Cargo c")
public class Cargo implements Serializable {

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_CARGO", sequenceName="\"SEQ_ID_CARGO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_CARGO")
	@Column(name="\"ID_CARGO\"")
	private long idCargo;

	@Column(name="\"A_NOMBRE_CARGO\"")
	private String nombreCargo;

	//bi-directional many-to-one association to ResponsabilidadTarea
	@OneToMany(mappedBy="id.cargo")
	private List<CargoResponsabilidad> CargoResponsabilidad;
	
	public long getIdCargo() {
		return idCargo;
	}
		
	public void setIdCargo(long idCargo) {
		this.idCargo = idCargo;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCargo ^ (idCargo >>> 32));
		result = prime * result + ((nombreCargo == null) ? 0 : nombreCargo.hashCode());
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
		Cargo other = (Cargo) obj;
		if (idCargo != other.idCargo)
			return false;
		if (nombreCargo == null) {
			if (other.nombreCargo != null)
				return false;
		} else if (!nombreCargo.equals(other.nombreCargo))
			return false;
		return true;
	}
	
	
}

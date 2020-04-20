package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_RESPONSABILIDAD\"")
@NamedQuery(name="Responsabilidad.findAll", query="SELECT R FROM Responsabilidad R")
public class Responsabilidad implements Serializable  {

	@Id
	@SequenceGenerator(name="SEQ_ID_RESPONSABILIDAD", sequenceName="\"SEQ_ID_RESPONSABILIDAD\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_RESPONSABILIDAD")
	@Column(name="\"ID_RESPONSABILIDAD\"")
	private long idResponsabilidad;

	@Column(name="\"A_NOMBRE_RESPONSABILIDAD\"")
	private String nombreResponsabilidad;
	
	//bi-directional many-to-one association to UsuarioRol
	@JsonIgnore
	@OneToMany(mappedBy="responsabilidad", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	private List<UsuarioResponsabilidad> usuarioResponsabilidades;
	
	//bi-directional many-to-one association to ResponsabilidadTarea
	@OneToMany(mappedBy="id.responsabilidad")
	private List<CargoResponsabilidad> CargoResponsabilidad;

	public long getIdResponsabilidad() {
		return idResponsabilidad;
	}

	public void setIdResponsabilidad(long idResponsabilidad) {
		this.idResponsabilidad = idResponsabilidad;
	}

	public String getNombreResponsabilidad() {
		return nombreResponsabilidad;
	}

	public void setNombreResponsabilidad(String nombreResponsabilidad) {
		this.nombreResponsabilidad = nombreResponsabilidad;
	}

	public List<UsuarioResponsabilidad> getUsuarioResponsabilidades() {
		return usuarioResponsabilidades;
	}

	public void setUsuarioResponsabilidades(List<UsuarioResponsabilidad> usuarioResponsabilidades) {
		this.usuarioResponsabilidades = usuarioResponsabilidades;
	}
	
	public UsuarioResponsabilidad addUsuarioResponsabilidad(UsuarioResponsabilidad usuarioResponsabilidad) {
		getUsuarioResponsabilidades().add(usuarioResponsabilidad);
		usuarioResponsabilidad.setResponsabilidad(this);
		return usuarioResponsabilidad;
	}

	public UsuarioResponsabilidad removeUsuarioResponsabilidad(UsuarioResponsabilidad usuarioResponsabilidad) {
		getUsuarioResponsabilidades().remove(usuarioResponsabilidad);
		usuarioResponsabilidad.setResponsabilidad(null);
		return usuarioResponsabilidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idResponsabilidad ^ (idResponsabilidad >>> 32));
		result = prime * result + ((nombreResponsabilidad == null) ? 0 : nombreResponsabilidad.hashCode());
		result = prime * result + ((usuarioResponsabilidades == null) ? 0 : usuarioResponsabilidades.hashCode());
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
		Responsabilidad other = (Responsabilidad) obj;
		if (idResponsabilidad != other.idResponsabilidad)
			return false;
		if (nombreResponsabilidad == null) {
			if (other.nombreResponsabilidad != null)
				return false;
		} else if (!nombreResponsabilidad.equals(other.nombreResponsabilidad))
			return false;
		if (usuarioResponsabilidades == null) {
			if (other.usuarioResponsabilidades != null)
				return false;
		} else if (!usuarioResponsabilidades.equals(other.usuarioResponsabilidades))
			return false;
		return true;
	}
	
}

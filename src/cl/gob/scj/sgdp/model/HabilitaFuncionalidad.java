package cl.gob.scj.sgdp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_HABILITA_FUNCIONALIDAD\"")
@NamedQuery(name="HabilitaFuncionalidad.findAll", query="SELECT e FROM HabilitaFuncionalidad e")
public class HabilitaFuncionalidad {
	
	@Id
	@SequenceGenerator(name="SEQ_ID_HABILITA_FUNCIONALIDAD", sequenceName="\"SEQ_ID_HABILITA_FUNCIONALIDAD\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HABILITA_FUNCIONALIDAD")
	@Column(name="\"ID_HABILITA_FUNCIONALIDAD\"")
	private long idHabilitaFuncionalidad;

	@Column(name="\"A_NOMBRE_FUNCIONALIDAD\"")
	private String nombreFuncionalidad;

	@Column(name="\"B_HABILITADA\"")
	private boolean habilitada;

	public long getIdHabilitaFuncionalidad() {
		return idHabilitaFuncionalidad;
	}

	public void setIdHabilitaFuncionalidad(long idHabilitaFuncionalidad) {
		this.idHabilitaFuncionalidad = idHabilitaFuncionalidad;
	}

	public String getNombreFuncionalidad() {
		return nombreFuncionalidad;
	}

	public void setNombreFuncionalidad(String nombreFuncionalidad) {
		this.nombreFuncionalidad = nombreFuncionalidad;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}

	@Override
	public String toString() {
		return "HabilitaFuncionalidad [idHabilitaFuncionalidad=" + idHabilitaFuncionalidad + ", nombreFuncionalidad="
				+ nombreFuncionalidad + ", habilitada=" + habilitada + "]";
	}
	
}

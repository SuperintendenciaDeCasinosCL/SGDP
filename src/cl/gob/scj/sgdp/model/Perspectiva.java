package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_PERSPECTIVAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PERSPECTIVAS\"")
@NamedQuery(name="Perspectiva.findAll", query="SELECT p FROM Perspectiva p")
public class Perspectiva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_PERSPECTIVA", sequenceName="\"SEQ_ID_PERSPECTIVA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PERSPECTIVA")
	@Column(name="\"ID_PERSPECTIVA\"")
	private long idPerspectiva;

	@Column(name="\"A_DESCRIPCION_PERSPECTIVA\"")
	private String descripcionPerspectiva;

	@Column(name="\"A_NOMBRE_PERSPECTIVA\"")
	private String nombrePerspectiva;

	//bi-directional many-to-one association to MacroProceso
	@JsonIgnore
	@OneToMany(mappedBy="perspectiva", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<MacroProceso> macroProcesos;

	public Perspectiva() {
	}

	public long getIdPerspectiva() {
		return this.idPerspectiva;
	}

	public void setIdPerspectiva(long idPerspectiva) {
		this.idPerspectiva = idPerspectiva;
	}

	public String getDescripcionPerspectiva() {
		return this.descripcionPerspectiva;
	}

	public void setDescripcionPerspectiva(String descripcionPerspectiva) {
		this.descripcionPerspectiva = descripcionPerspectiva;
	}

	public String getNombrePerspectiva() {
		return this.nombrePerspectiva;
	}

	public void setNombrePerspectiva(String nombrePerspectiva) {
		this.nombrePerspectiva = nombrePerspectiva;
	}

	public List<MacroProceso> getMacroProcesos() {
		return this.macroProcesos;
	}

	public void setMacroProcesos(List<MacroProceso> macroProcesos) {
		this.macroProcesos = macroProcesos;
	}

	public MacroProceso addMacroProceso(MacroProceso macroProceso) {
		getMacroProcesos().add(macroProceso);
		macroProceso.setPerspectiva(this);

		return macroProceso;
	}

	public MacroProceso removeMacroProceso(MacroProceso macroProceso) {
		getMacroProcesos().remove(macroProceso);
		macroProceso.setPerspectiva(null);

		return macroProceso;
	}

}
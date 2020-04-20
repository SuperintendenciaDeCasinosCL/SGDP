package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "SGDP_MACRO_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_MACRO_PROCESOS\"")
@NamedQueries({
	@NamedQuery(name="MacroProceso.findAll", query="SELECT m FROM MacroProceso m "),
	@NamedQuery(name="MacroProceso.getMacroProcesosPorVigentes", query="SELECT DISTINCT(m) FROM MacroProceso m, Proceso p "
			+ "where m.idMacroProceso = p.macroProceso.idMacroProceso and p.vigente = true"),
	@NamedQuery(name="MacroProceso.getMacroProcesosPorIdUnidad", query="SELECT DISTINCT(m) FROM MacroProceso m, Proceso p "
			+ "where m.idMacroProceso = p.macroProceso.idMacroProceso "
			+ "and p.unidad.idUnidad =:idUnidad and p.vigente = true")
})
public class MacroProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_MACRO_PROCESO", sequenceName="\"SEQ_ID_MACRO_PROCESO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_MACRO_PROCESO")
	@Column(name="\"ID_MACRO_PROCESO\"")
	private long idMacroProceso;

	@Column(name="\"A_DESCRIPCION_MACRO_PROCESO\"")
	private String descripcionMacroProceso;

	@Column(name="\"A_NOMBRE_MACRO_PROCESO\"")
	private String nombreMacroProceso;

	/*@Column(name="\"ID_PERSPECTIVA\"")
	private long idPerspectiva;*/

	//bi-directional many-to-one association to Perspectiva
	@ManyToOne
	@JoinColumn(name="\"ID_PERSPECTIVA\"")
	private Perspectiva perspectiva;

	//bi-directional many-to-one association to Proceso
	@JsonIgnore
	@OneToMany(mappedBy="macroProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<Proceso> procesos;

	public MacroProceso() {
	}

	public long getIdMacroProceso() {
		return this.idMacroProceso;
	}

	public void setIdMacroProceso(long idMacroProceso) {
		this.idMacroProceso = idMacroProceso;
	}

	public String getDescripcionMacroProceso() {
		return this.descripcionMacroProceso;
	}

	public void setDescripcionMacroProceso(String descripcionMacroProceso) {
		this.descripcionMacroProceso = descripcionMacroProceso;
	}

	public String getNombreMacroProceso() {
		return this.nombreMacroProceso;
	}

	public void setNombreMacroProceso(String nombreMacroProceso) {
		this.nombreMacroProceso = nombreMacroProceso;
	}
	
	public Perspectiva getPerspectiva() {
		return this.perspectiva;
	}

	public void setPerspectiva(Perspectiva perspectiva) {
		this.perspectiva = perspectiva;
	}

	public List<Proceso> getProcesos() {
		return this.procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public Proceso addProceso(Proceso proceso) {
		getProcesos().add(proceso);
		proceso.setMacroProceso(this);

		return proceso;
	}

	public Proceso removeProceso(Proceso proceso) {
		getProcesos().remove(proceso);
		proceso.setMacroProceso(null);

		return proceso;
	}

}
package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_SUPER_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_SUPER_PROCESOS\"")
@NamedQueries({
	@NamedQuery(name="SuperProceso.findAllByMacroProceso", query="SELECT s FROM SuperProceso s where macroProceso.idMacroProceso = :idMacroProceso "),
	
})
public class SuperProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_SUPER_PROCESO", sequenceName="\"SEQ_ID_SUPER_PROCESO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_SUPER_PROCESO")
	@Column(name="\"ID_SUPER_PROCESO\"")
	private long idSuperProceso;

	@Column(name="\"A_DESCRIPCION_SUPER_PROCESO\"")
	private String descripcionSuperProceso;

	@Column(name="\"A_NOMBRE_SUPER_PROCESO\"")
	private String nombreSuperProceso;

	//bi-directional many-to-one association to Perspectiva
	@ManyToOne
	@JoinColumn(name="\"ID_MACRO_PROCESO\"")
	private MacroProceso macroProceso;

	//bi-directional many-to-one association to Proceso
	@JsonIgnore
	@OneToMany(mappedBy="superProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<Proceso> procesos;

	public SuperProceso() {
	}

	public long getIdSuperProceso() {
		return idSuperProceso;
	}

	public void setIdSuperProceso(long idSuperProceso) {
		this.idSuperProceso = idSuperProceso;
	}

	public String getDescripcionSuperProceso() {
		return descripcionSuperProceso;
	}

	public void setDescripcionSuperProceso(String descripcionSuperProceso) {
		this.descripcionSuperProceso = descripcionSuperProceso;
	}

	public String getNombreSuperProceso() {
		return nombreSuperProceso;
	}

	public void setNombreSuperProceso(String nombreSuperProceso) {
		this.nombreSuperProceso = nombreSuperProceso;
	}

	public MacroProceso getMacroProceso() {
		return macroProceso;
	}

	public void setMacroProceso(MacroProceso macroProceso) {
		this.macroProceso = macroProceso;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	

}
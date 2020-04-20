package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import java.util.List;


/**
 * The persistent class for the "SGDP_TIPO_PARAMETRO_DE_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_TIPO_PARAMETRO_DE_TAREA\"")
@NamedQueries({
	@NamedQuery(name="TipoParametroDeTarea.findAll", query="SELECT t FROM TipoParametroDeTarea t"),
	@NamedQuery(name="TipoParametroDeTarea.getTipoParametroDeTareaPorNombreTipoParametroDeTarea", 
	query="SELECT t FROM TipoParametroDeTarea t WHERE t.nombreTipoParametroDeTarea = :nombreTipoParametroDeTarea")
})
public class TipoParametroDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_TIPO_PARAMETRO_DE_TAREA", sequenceName="\"SEQ_ID_TIPO_PARAMETRO_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TIPO_PARAMETRO_DE_TAREA")
	@Column(name="\"ID_TIPO_PARAMETRO_DE_TAREA\"")
	private Long idTipoParametroDeTarea;

	@Column(name="\"A_NOMBRE_TIPO_PARAMETRO_DE_TAREA\"")
	private String nombreTipoParametroDeTarea;

	@Column(name="\"A_TEXTO_HTML\"")
	private String textoHtml;	

	@Column(name="\"B_COMENTA\"")
	private Boolean comenta;

	//bi-directional many-to-one association to ParametroDeTarea
	@OneToMany(mappedBy="tipoParametroDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ParametroDeTarea> ParametrosDeTarea;

	public TipoParametroDeTarea() {
	}

	public Long getIdTipoParametroDeTarea() {
		return this.idTipoParametroDeTarea;
	}

	public void setIdTipoParametroDeTarea(Long idTipoParametroDeTarea) {
		this.idTipoParametroDeTarea = idTipoParametroDeTarea;
	}

	public String getNombreTipoParametroDeTarea() {
		return this.nombreTipoParametroDeTarea;
	}

	public void setNombreTipoParametroDeTarea(String nombreTipoParametroDeTarea) {
		this.nombreTipoParametroDeTarea = nombreTipoParametroDeTarea;
	}
	
	public Boolean getComenta() {
		return comenta;
	}

	public void setComenta(Boolean comenta) {
		this.comenta = comenta;
	}

	public String getTextoHtml() {
		return this.textoHtml;
	}

	public void setTextoHtml(String textoHtml) {
		this.textoHtml = textoHtml;
	}

	public List<ParametroDeTarea> getParametrosDeTarea() {
		return this.ParametrosDeTarea;
	}

	public void setParametrosDeTarea(List<ParametroDeTarea> ParametrosDeTarea) {
		this.ParametrosDeTarea = ParametrosDeTarea;
	}

	public ParametroDeTarea addParametrosDeTarea(ParametroDeTarea ParametrosDeTarea) {
		getParametrosDeTarea().add(ParametrosDeTarea);
		ParametrosDeTarea.setTipoParametroDeTarea(this);

		return ParametrosDeTarea;
	}

	public ParametroDeTarea removeParametrosDeTarea(ParametroDeTarea ParametrosDeTarea) {
		getParametrosDeTarea().remove(ParametrosDeTarea);
		ParametrosDeTarea.setTipoParametroDeTarea(null);

		return ParametrosDeTarea;
	}

}
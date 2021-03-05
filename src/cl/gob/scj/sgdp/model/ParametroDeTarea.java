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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_PARAMETRO_DE_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PARAMETRO_DE_TAREA\"")
@NamedQueries({
	@NamedQuery(name="ParametroDeTarea.findAll", query="SELECT p FROM ParametroDeTarea p"),
	@NamedQuery(name="ParametroDeTarea.getParametroDeTareaPorIdParamTarea", 
				query="SELECT p FROM ParametroDeTarea p "
			+ "WHERE p.idParamTarea = :idParamTarea "),
	@NamedQuery(name="ParametroDeTarea.getParametrosDeTareaPorIdTarea", 
				query="SELECT p FROM ParametroDeTarea p, ParametroRelacionTarea pr "
						+ "WHERE pr.id.parametroDeTarea.idParamTarea = p.idParamTarea "
						+ "AND pr.id.tarea.idTarea = :idTarea "),
	@NamedQuery(name="ParametroDeTarea.getParametrosDeTareaPorIdInstanciaDeTarea", 
			query="SELECT p FROM ParametroDeTarea p, ParametroRelacionTarea pr, Tarea t, InstanciaDeTarea i "
			+ "WHERE pr.id.parametroDeTarea.idParamTarea = p.idParamTarea "
			+ "AND pr.id.tarea.idTarea = t.idTarea "
			+ "AND t.idTarea = i.tarea.idTarea "
			+ "AND i.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND p.vigente = true "
			+ "order by p.esSNC desc, p.nombreParamTarea asc")
})
public class ParametroDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_PARAM_TAREA", sequenceName="\"SEQ_ID_PARAM_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PARAM_TAREA")
	@Column(name="\"ID_PARAM_TAREA\"")
	private Long idParamTarea;

	@Column(name="\"A_NOMBRE_PARAM_TAREA\"")
	private String nombreParamTarea;

	/*@Column(name="\"A_TIPO\"")
	private String tipo;*/
	
	//bi-directional many-to-one association to ParametroRelacionTarea
	@OneToMany(mappedBy="id.parametroDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ParametroRelacionTarea> parametroRelacionTareas;
	
	//bi-directional many-to-one association to TipoParametroDeTarea
	@ManyToOne		
	@JoinColumn(name="\"ID_TIPO_PARAMETRO_DE_TAREA\"")
	private TipoParametroDeTarea tipoParametroDeTarea;
	
	//bi-directional many-to-one association to ValorParametroDeTarea
	@OneToMany(mappedBy="parametroDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ValorParametroDeTarea> valorParametrosDeTarea;
	
	//bi-directional many-to-one association to HistoricoValorParametroDeTarea
	@OneToMany(mappedBy="parametroDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoValorParametroDeTarea> historicosValorParametroDeTarea;
	
	//bi-directional many-to-one association to TextoParametroDeTarea
	@OneToMany(mappedBy="parametroDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<TextoParametroDeTarea> textosParametroDeTarea;
	
	@Column(name="\"A_TITULO\"")
	private String titulo;
	
	@Column(name="\"B_VIGENTE\"")
	private Boolean vigente;
	
	@Column(name="\"B_ES_SNC\"")
	private Boolean esSNC;

	public ParametroDeTarea() {
	}

	public Long getIdParamTarea() {
		return this.idParamTarea;
	}

	public void setIdParamTarea(Long idParamTarea) {
		this.idParamTarea = idParamTarea;
	}

	public String getNombreParamTarea() {
		return this.nombreParamTarea;
	}

	public void setNombreParamTarea(String nombreParamTarea) {
		this.nombreParamTarea = nombreParamTarea;
	}
	
	public List<ParametroRelacionTarea> getParametroRelacionTareas() {
		return this.parametroRelacionTareas;
	}

	public void setParametroRelacionTareas(List<ParametroRelacionTarea> parametroRelacionTareas) {
		this.parametroRelacionTareas = parametroRelacionTareas;
	}

	public ParametroRelacionTarea addParametroRelacionTarea(ParametroRelacionTarea parametroRelacionTarea) {
		getParametroRelacionTareas().add(parametroRelacionTarea);
		parametroRelacionTarea.getId().setParametroDeTarea(this);

		return parametroRelacionTarea;
	}

	public ParametroRelacionTarea removeParametroRelacionTarea(ParametroRelacionTarea ParametroRelacionTarea) {
		getParametroRelacionTareas().remove(ParametroRelacionTarea);
		ParametroRelacionTarea.getId().setParametroDeTarea(null);

		return ParametroRelacionTarea;
	}

	public TipoParametroDeTarea getTipoParametroDeTarea() {
		return tipoParametroDeTarea;
	}

	public void setTipoParametroDeTarea(TipoParametroDeTarea tipoParametroDeTarea) {
		this.tipoParametroDeTarea = tipoParametroDeTarea;
	}
	
	public List<ValorParametroDeTarea> getValorParametrosDeTarea() {
		return valorParametrosDeTarea;
	}

	public void setValorParametrosDeTarea(List<ValorParametroDeTarea> valorParametrosDeTarea) {
		this.valorParametrosDeTarea = valorParametrosDeTarea;
	}
	
	public ValorParametroDeTarea addValorParametrosDeTarea(ValorParametroDeTarea valorParametrosDeTarea) {
		getValorParametrosDeTarea().add(valorParametrosDeTarea);
		valorParametrosDeTarea.setParametroDeTarea(this);

		return valorParametrosDeTarea;
	}

	public ValorParametroDeTarea removeValorParametrosDeTarea(ValorParametroDeTarea valorParametrosDeTarea) {
		getValorParametrosDeTarea().remove(valorParametrosDeTarea);
		valorParametrosDeTarea.setParametroDeTarea(null);

		return valorParametrosDeTarea;
	}

	public List<HistoricoValorParametroDeTarea> getHistoricosValorParametroDeTarea() {
		return historicosValorParametroDeTarea;
	}

	public void setHistoricosValorParametroDeTarea(List<HistoricoValorParametroDeTarea> historicosValorParametroDeTarea) {
		this.historicosValorParametroDeTarea = historicosValorParametroDeTarea;
	}
	
	public HistoricoValorParametroDeTarea addHistoricoValorParametroDeTarea(HistoricoValorParametroDeTarea historicoValorParametroDeTarea) {
		this.getHistoricosValorParametroDeTarea().add(historicoValorParametroDeTarea);
		historicoValorParametroDeTarea.setParametroDeTarea(this);
		
		return historicoValorParametroDeTarea;
	}
	
	public HistoricoValorParametroDeTarea removeHistoricoValorParametroDeTarea(HistoricoValorParametroDeTarea historicoValorParametroDeTarea) {
		this.getHistoricosValorParametroDeTarea().remove(historicoValorParametroDeTarea);
		historicoValorParametroDeTarea.setParametroDeTarea(null);
		
		return historicoValorParametroDeTarea;
	}
	
	public List<TextoParametroDeTarea> getTextosParametroDeTarea() {
		return this.textosParametroDeTarea;
	}

	public void setTextosParametroDeTarea(List<TextoParametroDeTarea> textosParametroDeTarea) {
		this.textosParametroDeTarea = textosParametroDeTarea;
	}

	public TextoParametroDeTarea addTextosParametroDeTarea(TextoParametroDeTarea textosParametroDeTarea) {
		getTextosParametroDeTarea().add(textosParametroDeTarea);
		textosParametroDeTarea.setParametroDeTarea(this);

		return textosParametroDeTarea;
	}

	public TextoParametroDeTarea removeTextosParametroDeTarea(TextoParametroDeTarea textosParametroDeTarea) {
		getTextosParametroDeTarea().remove(textosParametroDeTarea);
		textosParametroDeTarea.setParametroDeTarea(null);

		return textosParametroDeTarea;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Boolean getEsSNC() {
		return esSNC;
	}

	public void setEsSNC(Boolean esSNC) {
		this.esSNC = esSNC;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idParamTarea == null) ? 0 : idParamTarea.hashCode());
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
		ParametroDeTarea other = (ParametroDeTarea) obj;
		if (idParamTarea == null) {
			if (other.idParamTarea != null)
				return false;
		} else if (!idParamTarea.equals(other.idParamTarea))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParametroDeTarea [idParamTarea=" + idParamTarea 
				+ ", nombreParamTarea=" + nombreParamTarea 
				+ ", titulo=" + titulo				
				+ "]";
	}
	
}
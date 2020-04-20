package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_VALOR_PARAMETRO_DE_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_VALOR_PARAMETRO_DE_TAREA\"")
@NamedQueries({
	@NamedQuery(name="ValorParametroDeTarea.findAll", query="SELECT v FROM ValorParametroDeTarea v"),
	@NamedQuery(name="ValorParametroDeTarea.getValorParametroDeTareaPorIdParamIdInstanciaTarea", 
		query="SELECT v FROM ValorParametroDeTarea v "
				+ "WHERE v.parametroDeTarea.idParamTarea = :idParamTarea AND v.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea")
})
public class ValorParametroDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_VALOR_PARAMETRO_DE_TAREA", sequenceName="\"SEQ_ID_VALOR_PARAMETRO_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_VALOR_PARAMETRO_DE_TAREA")
	@Column(name="\"ID_VALOR_PARAMETRO_DE_TAREA\"")
	private Long idValorParametroDeTarea;

	@Column(name="\"A_VALOR\"")
	private String valor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA\"")
	private Date fecha;	

	//bi-directional many-to-one association to HistoricoValorParametroDeTarea
	/*@OneToMany(mappedBy="valorParametroDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoValorParametroDeTarea> historicoValorParametrosDeTarea;*/

	//bi-directional many-to-one association to InstanciasDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"")
	private InstanciaDeTarea instanciaDeTarea;

	//bi-directional many-to-one association to ParametroDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_PARAM_TAREA\"")
	private ParametroDeTarea parametroDeTarea;
	
	@Column(name="\"A_COMENTARIO\"")
	private String comentario;

	public ValorParametroDeTarea() {
	}

	public Long getIdValorParametroDeTarea() {
		return this.idValorParametroDeTarea;
	}

	public void setIdValorParametroDeTarea(Long idValorParametroDeTarea) {
		this.idValorParametroDeTarea = idValorParametroDeTarea;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/*public List<HistoricoValorParametroDeTarea> getHistoricoValorParametrosDeTarea() {
		return this.historicoValorParametrosDeTarea;
	}

	public void setHistoricoValorParametrosDeTarea(List<HistoricoValorParametroDeTarea> historicoValorParametrosDeTarea) {
		this.historicoValorParametrosDeTarea = historicoValorParametrosDeTarea;
	}*/

	/*public HistoricoValorParametroDeTarea addHistoricoValorParametrosDeTarea(HistoricoValorParametroDeTarea historicoValorParametrosDeTarea) {
		getHistoricoValorParametrosDeTarea().add(historicoValorParametrosDeTarea);
		historicoValorParametrosDeTarea.setValorParametroDeTarea(this);

		return historicoValorParametrosDeTarea;
	}

	public HistoricoValorParametroDeTarea removeHistoricoValorParametrosDeTarea(HistoricoValorParametroDeTarea historicoValorParametrosDeTarea) {
		getHistoricoValorParametrosDeTarea().remove(historicoValorParametrosDeTarea);
		historicoValorParametrosDeTarea.setValorParametroDeTarea(null);

		return historicoValorParametrosDeTarea;
	}*/

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public ParametroDeTarea getParametroDeTarea() {
		return this.parametroDeTarea;
	}

	public void setParametroDeTarea(ParametroDeTarea parametroDeTarea) {
		this.parametroDeTarea = parametroDeTarea;
	}

}
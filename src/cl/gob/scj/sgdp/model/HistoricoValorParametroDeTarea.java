package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_HISTORICO_VALOR_PARAMETRO_DE_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_VALOR_PARAMETRO_DE_TAREA\"")
@NamedQueries({
	@NamedQuery(name="HistoricoValorParametroDeTarea.findAll", query="SELECT h FROM HistoricoValorParametroDeTarea h"),
	@NamedQuery(name="HistoricoValorParametroDeTarea.getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen", 
		query="SELECT v FROM HistoricoValorParametroDeTarea v "
				+ "WHERE v.historicoDeInstDeTarea.instanciaDeTareaDeOrigen.idInstanciaDeTarea = :idInstanciaDeTareaOrigen")
})
public class HistoricoValorParametroDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_HISTORICO_VALOR_PARAMETRO_DE_TAREA", sequenceName="\"SEQ_ID_HISTORICO_VALOR_PARAMETRO_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HISTORICO_VALOR_PARAMETRO_DE_TAREA")
	@Column(name="\"ID_HISTORICO_VALOR_PARAMETRO_DE_TAREA\"")
	private Long idHistoricoValorParametroDeTarea;

	@Column(name="\"A_VALOR\"")
	private String valor;

	//bi-directional many-to-one association to InstanciasDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_HISTORICO_DE_INST_DE_TAREA\"")
	private HistoricoDeInstDeTarea historicoDeInstDeTarea;

	//bi-directional many-to-one association to ParametroDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_PARAM_TAREA\"")
	private ParametroDeTarea parametroDeTarea;
	
	@Column(name="\"A_COMENTARIO\"")
	private String comentario;

	public HistoricoValorParametroDeTarea() {
	}

	public Long getIdHistoricoValorParametroDeTarea() {
		return this.idHistoricoValorParametroDeTarea;
	}

	public void setIdHistoricoValorParametroDeTarea(Long idHistoricoValorParametroDeTarea) {
		this.idHistoricoValorParametroDeTarea = idHistoricoValorParametroDeTarea;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public HistoricoDeInstDeTarea getHistoricoDeInstDeTarea() {
		return historicoDeInstDeTarea;
	}

	public void setHistoricoDeInstDeTarea(HistoricoDeInstDeTarea historicoDeInstDeTarea) {
		this.historicoDeInstDeTarea = historicoDeInstDeTarea;
	}

	public ParametroDeTarea getParametroDeTarea() {
		return parametroDeTarea;
	}

	public void setParametroDeTarea(ParametroDeTarea parametroDeTarea) {
		this.parametroDeTarea = parametroDeTarea;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_TEXTO_PARAMETRO_DE_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_TEXTO_PARAMETRO_DE_TAREA\"")
@NamedQueries({
	@NamedQuery(name="TextoParametroDeTarea.findAll", query="SELECT t FROM TextoParametroDeTarea t"),
	@NamedQuery(name="TextoParametroDeTarea.getTextosParametroDeTareaPorIdParamTarea", 
		query="SELECT t FROM TextoParametroDeTarea t WHERE t.parametroDeTarea.idParamTarea = :idParamTarea ")
})
public class TextoParametroDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_TEXTO_PARAMETRO_DE_TAREA", sequenceName="\"SEQ_ID_TEXTO_PARAMETRO_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TEXTO_PARAMETRO_DE_TAREA")
	@Column(name="\"ID_TEXTO_PARAMETRO_DE_TAREA\"")
	private Long idTextoParametroDeTarea;

	@Column(name="\"A_TEXTO\"")
	private String texto;

	//bi-directional many-to-one association to ParametroDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_PARAM_TAREA\"")
	private ParametroDeTarea parametroDeTarea;

	public TextoParametroDeTarea() {
	}

	public Long getIdTextoParametroDeTarea() {
		return this.idTextoParametroDeTarea;
	}

	public void setIdTextoParametroDeTarea(Long idTextoParametroDeTarea) {
		this.idTextoParametroDeTarea = idTextoParametroDeTarea;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public ParametroDeTarea getParametroDeTarea() {
		return this.parametroDeTarea;
	}

	public void setParametroDeTarea(ParametroDeTarea parametroDeTarea) {
		this.parametroDeTarea = parametroDeTarea;
	}

	@Override
	public String toString() {
		return "TextoParametroDeTarea [idTextoParametroDeTarea=" + idTextoParametroDeTarea + ", texto=" + texto
				+ ", parametroDeTarea=" + parametroDeTarea + "]";
	}

}
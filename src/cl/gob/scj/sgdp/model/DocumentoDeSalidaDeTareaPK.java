package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the "SGDP_DOCUMENTOS_DE_SALIDA_DE_TAREAS" database table.
 * 
 */
@Embeddable
public class DocumentoDeSalidaDeTareaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Tarea
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA\"", insertable = true)
	private Tarea tarea;
	
	//bi-directional many-to-one association to TipoDeDocumento
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DOCUMENTO\"", insertable = true)
	private TipoDeDocumento tipoDeDocumento;
	
	@Column(name="\"N_ORDEN\"")
	private int orden;
	
	public DocumentoDeSalidaDeTareaPK() {
	}
	
	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orden;
		result = prime * result + ((tarea == null) ? 0 : tarea.hashCode());
		result = prime * result + ((tipoDeDocumento == null) ? 0 : tipoDeDocumento.hashCode());
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
		DocumentoDeSalidaDeTareaPK other = (DocumentoDeSalidaDeTareaPK) obj;
		if (orden != other.orden)
			return false;
		if (tarea == null) {
			if (other.tarea != null)
				return false;
		} else if (!tarea.equals(other.tarea))
			return false;
		if (tipoDeDocumento == null) {
			if (other.tipoDeDocumento != null)
				return false;
		} else if (!tipoDeDocumento.equals(other.tipoDeDocumento))
			return false;
		return true;
	}
	
}
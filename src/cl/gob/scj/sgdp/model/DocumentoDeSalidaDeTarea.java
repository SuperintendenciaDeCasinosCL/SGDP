package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "SGDP_DOCUMENTOS_DE_SALIDA_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_DOCUMENTOS_DE_SALIDA_DE_TAREAS\"")
@NamedQuery(name="DocumentoDeSalidaDeTarea.findAll", query="SELECT d FROM DocumentoDeSalidaDeTarea d")
public class DocumentoDeSalidaDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocumentoDeSalidaDeTareaPK id;	
	
	public DocumentoDeSalidaDeTarea() {
	}

	public DocumentoDeSalidaDeTareaPK getId() {
		return this.id;
	}

	public void setId(DocumentoDeSalidaDeTareaPK id) {
		this.id = id;
	}	

}
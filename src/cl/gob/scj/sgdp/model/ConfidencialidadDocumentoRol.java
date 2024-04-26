package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_CARGO" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_CONFIDENCIALIDAD_DOCUMENTO_ROL\"")
@NamedQueries({
	@NamedQuery(name="CONFIDENCIALIDAD_DOCUMENTO_ROL.findByIdDocumento", 
			query="SELECT cr "
					+ "	FROM "
					+ "		ConfidencialidadDocumentoRol cr "
					+ "	where "
					+ "		cr.idArchivoCms = :idDocumento"),
	
	@NamedQuery(name="CONFIDENCIALIDAD_DOCUMENTO_ROL.deleteIdDocumento", 
	query="Delete "
			+ "	FROM "
			+ "		ConfidencialidadDocumentoRol cr "
			+ "	where "
			+ "		cr.idArchivoCms = :idDocumento")
})

public class ConfidencialidadDocumentoRol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_CONFIDENCIALIDAD_DOCUMENTO_ROL", sequenceName="\"SEQ_ID_CONFIDENCIALIDAD_DOCUMENTO_ROL\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_CONFIDENCIALIDAD_DOCUMENTO_ROL")
	@Column(name="\"ID\"")
	private long id;

	@Column (name="\"ID_ARCHIVO_CMS\"")
	private String idArchivoCms;
	
	@OneToOne
	@JoinColumn(name="\"ID_ROL\"")	
	private Rol rol;

	public ConfidencialidadDocumentoRol() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getIdArchivoCms() {
		return idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}
	
}

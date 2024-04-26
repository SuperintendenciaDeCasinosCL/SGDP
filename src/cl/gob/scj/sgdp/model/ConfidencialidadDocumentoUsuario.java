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
@Table(name="\"SGDP_CONFIDENCIALIDAD_DOCUMENTO_USUARIO\"")

@NamedQueries({
	@NamedQuery(name="CONFIDENCIALIDAD_DOCUMENTO_USUARIO.findByIdDocumento", 
			query=""
			+ "SELECT "
			+ "	cu "
			+ "FROM "
			+ "	ConfidencialidadDocumentoUsuario cu "
			+ "where "
			+ "	cu.idArchivoCms = :idDocumento"),
	@NamedQuery(name="CONFIDENCIALIDAD_DOCUMENTO_USUARIO.deleteByIdDocumento", 
	query=""
	+ "delete "
	+ "FROM "
	+ "	ConfidencialidadDocumentoUsuario cu "
	+ "where "
	+ "	cu.idArchivoCms = :idDocumento")
})

public class ConfidencialidadDocumentoUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_CONFIDENCIALIDAD_DOCUMENTO_USUARIO", sequenceName="\"SEQ_ID_CONFIDENCIALIDAD_DOCUMENTO_USUARIO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_CONFIDENCIALIDAD_DOCUMENTO_USUARIO")
	@Column(name="\"ID\"")
	private long id;

	@Column (name="\"ID_ARCHIVO_CMS\"")
	private String idArchivoCms;
	
	@Column (name="\"ID_USUARIO\"")
	private String idUsuario;

	public ConfidencialidadDocumentoUsuario() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdArchivoCms() {
		return idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}
	
	
	
}

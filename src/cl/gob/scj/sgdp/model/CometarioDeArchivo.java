package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the "SGDP_COMETARIOS_DE_ARCHIVO" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_COMETARIOS_DE_ARCHIVO\"")
@NamedQuery(name="CometarioDeArchivo.findAll", query="SELECT c FROM CometarioDeArchivo c")
public class CometarioDeArchivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_COMENTARIO_DE_ARCHIVO", sequenceName="\"SEQ_ID_COMENTARIO_DE_ARCHIVO\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_COMENTARIO_DE_ARCHIVO")
	@Column(name="\"ID_COMENTARIO_DE_ARCHIVO\"")
	private long idComentarioDeArchivo;

	@Column(name="\"A_COMENTARIO\"")
	private String comentario;

	@Column(name="\"A_NOMBRE_ARCHIVO\"")
	private String nombreArchivo;

	@Column(name="\"A_USUARIO\"")
	private String usuario;

	@Column(name="\"D_FECHA_COMENTARIO\"")
	private Timestamp fechaComentario;

	public CometarioDeArchivo() {
	}

	public long getIdComentarioDeArchivo() {
		return this.idComentarioDeArchivo;
	}

	public void setIdComentarioDeArchivo(long idComentarioDeArchivo) {
		this.idComentarioDeArchivo = idComentarioDeArchivo;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Timestamp getFechaComentario() {
		return this.fechaComentario;
	}

	public void setFechaComentario(Timestamp fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

}
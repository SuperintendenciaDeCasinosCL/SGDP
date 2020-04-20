package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the "SGDP_ASIGNACIONES_NUMEROS_DOC" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_ASIGNACIONES_NUMEROS_DOC\"")

@NamedQueries({

	@NamedQuery(name="AsignacionesNumerosDoc.findAll", query="SELECT a FROM AsignacionesNumerosDoc a"),

	@NamedQuery(name="AsignacionesNumerosDoc.BuscarIdNumeroDocumentoReservadoNoUtilizado",
			query=" SELECT a FROM AsignacionesNumerosDoc a "
					+ " where a.anio = :anio "
					+ " and a.estado in ('NUll','','Reservado') "
					+ "	and a.tipoDeDocumento.idTipoDeDocumento = :tipoDeDocumento "
					+ " and a.numeroDocumento in ( "
					+ " SELECT min(c.numeroDocumento) FROM AsignacionesNumerosDoc c "
					+ " where c.tipoDeDocumento.idTipoDeDocumento = :tipoDeDocumento "
					+ " and c.anio = :anio "
					+ " and c.estado in ('NUll','','Reservado') "
					+ ")"),
		/*	query=" SELECT a FROM AsignacionesNumerosDoc a "
				+ " where a.tipoDeDocumento.idTipoDeDocumento = :tipoDeDocumento "
				+ " and a.anio = :anio "
				+ " and a.estado = '' "
				+ " and a.numeroDocumento in ( "
				+ " SELECT max(c.numeroDocumento) FROM AsignacionesNumerosDoc c "
				+ " where c.tipoDeDocumento.idTipoDeDocumento = :tipoDeDocumento "
				+ " and c.anio = :anio "
				+ " and c.estado = '' "
				+ ")")*/
	
	@NamedQuery(name="AsignacionesNumerosDoc.BuscarIdNumeroDocumentoFirmado", 
	query=" SELECT a FROM AsignacionesNumerosDoc a "
		+ " where a.tipoDeDocumento.idTipoDeDocumento = :tipoDeDocumento "
		+ " and a.anio = :anio "
		+ " and a.estado in (:estados) "
		+ " and a.numeroDocumento in ( "
		+ " SELECT max(c.numeroDocumento) FROM AsignacionesNumerosDoc c "
		+ " where c.tipoDeDocumento.idTipoDeDocumento = :tipoDeDocumento "
		+ " and c.anio = :anio "
		+ " and c.estado in (:estados) "
		+ ")")
	
})
public class AsignacionesNumerosDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_ASIGNACION_NUMERO_DOC", sequenceName="\"SEQ_ID_ASIGNACION_NUMERO_DOC\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ASIGNACION_NUMERO_DOC")
	@Column(name="\"ID_ASIGNACION_NUMERO_DOC\"")
	private Long idAsignacionNumeroDoc;

	@Column(name="\"A_ESTADO\"")
	private String estado;

	@Column(name="\"D_ANIO\"")
	private String anio;

	@Column(name="\"D_FECHA_CREACION\"")
	private Timestamp fechaCreacion;

	@Column(name="\"D_FECHA_MODIFICACION\"")
	private Timestamp fechaModificacion;

	@Column(name="\"N_NUMERO_DOCUMENTO\"")
	private long numeroDocumento;

	//bi-directional many-to-one association to TiposDeDocumento
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DOCUMENTO\"")
	private TipoDeDocumento tipoDeDocumento;

	public AsignacionesNumerosDoc() {
	}

	public Long getIdAsignacionNumeroDoc() {
		return this.idAsignacionNumeroDoc;
	}

	public void setIdAsignacionNumeroDoc(Long idAsignacionNumeroDoc) {
		this.idAsignacionNumeroDoc = idAsignacionNumeroDoc;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAnio() {
		return this.anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}


	public long getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}
	
	

}
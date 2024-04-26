package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_TABLA_RETENCION\"")
@NamedQueries({
	@NamedQuery(name="TablaRetencion.findAllByIdTipoDocumento", 
			query="select tr from TablaRetencion tr where tr.tipoDocumento.idTipoDeDocumento = :idTipoDocumento "),
	@NamedQuery(name="TablaRetencion.findByIdTipoDocumentoAndIdSerieDocumental", 
		query="select tr from TablaRetencion tr where tr.tipoDocumento.idTipoDeDocumento = :idTipoDocumento "
				+ "and tr.serieDocumental.idSerieDocumental = :idSerieDocumental"),
})
public class TablaRetencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_TABLA_RETENCION", sequenceName="\"SEQ_ID_TABLA_RETENCION\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TABLA_RETENCION")
	@Column(name="\"ID_TABLA_RETENCION\"")
	private long idTablaRetencion;
	
	@ManyToOne
	@JoinColumn(name="\"ID_SERIE_DOCUMENTAL\"")
	private SerieDocumental serieDocumental;
	
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DOCUMENTO\"")
	private TipoDeDocumento tipoDocumento;
	

	@Column(name="\"N_ANIOS_RETENCION\"")
	private Integer aniosRetencion;

	@Column(name="\"B_TRANSFERIBLE_ARCHIVO\"")
	private Boolean transferibleArchivo;

	public TablaRetencion() {
	}

	public long getIdTablaRetencion() {
		return idTablaRetencion;
	}

	public void setIdTablaRetencion(long idTablaRetencion) {
		this.idTablaRetencion = idTablaRetencion;
	}

	public SerieDocumental getSerieDocumental() {
		return serieDocumental;
	}

	public void setSerieDocumental(SerieDocumental serieDocumental) {
		this.serieDocumental = serieDocumental;
	}

	public TipoDeDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDeDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getAniosRetencion() {
		return aniosRetencion;
	}

	public void setAniosRetencion(Integer aniosRetencion) {
		this.aniosRetencion = aniosRetencion;
	}

	public Boolean getTransferibleArchivo() {
		return transferibleArchivo;
	}

	public void setTransferibleArchivo(Boolean transferibleArchivo) {
		this.transferibleArchivo = transferibleArchivo;
	}

	
}
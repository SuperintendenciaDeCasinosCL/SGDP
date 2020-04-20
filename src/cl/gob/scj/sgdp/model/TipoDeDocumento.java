package cl.gob.scj.sgdp.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the "SGDP_TIPOS_DE_DOCUMENTOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_TIPOS_DE_DOCUMENTOS\"")
//@NamedQuery(name="TipoDeDocumento.findAll", query="SELECT t FROM TipoDeDocumento t")
@NamedQueries({
	@NamedQuery(name="TipoDeDocumento.findAll", query="SELECT t FROM TipoDeDocumento t"),
	
	@NamedQuery(name="TipoDeDocumento.getTipoDeDocumentoPorNombreDeTipoDeDocumento", 
				query="SELECT t FROM TipoDeDocumento t WHERE t.nombreDeTipoDeDocumento = :nombreDeTipoDeDocumento "
						),
	
	@NamedQuery(name="TipoDeDocumento.getTiposDeDocumentosDeSalidaPorIdInstanciaDeTarea", 
	query="SELECT ti FROM TipoDeDocumento ti, DocumentoDeSalidaDeTarea d, Tarea t, InstanciaDeTarea it "
			+ "WHERE it.idInstanciaDeTarea =:idInstanciaDeTarea " 
			+ "AND it.tarea.idTarea = t.idTarea "
			+ "AND d.id.tarea.idTarea = t.idTarea "
			+ "AND d.id.tipoDeDocumento.idTipoDeDocumento = ti.idTipoDeDocumento"),
	
	@NamedQuery(name="TipoDeDocumento.getTipoDeDocumentosPorIdProceso", 
	query="SELECT d FROM TipoDeDocumento d, DocumentoDeSalidaDeTarea s, Tarea t, Proceso p "
			+ "WHERE d.idTipoDeDocumento = s.id.tipoDeDocumento.idTipoDeDocumento " 
			+ "AND s.id.tarea.idTarea = t.idTarea "
			+ "AND t.proceso.idProceso = p.idProceso "
			+ "AND p.idProceso = :idProceso "
			+ "AND t.orden = 1 "
			+ "AND s.id.orden = 1"),	
	
	@NamedQuery(name="TipoDeDocumento.getTipoDeDocumentoRequeridoPorIdTipoDeDocumentoIdExpediente", 
	query="SELECT td FROM DocumentoDeSalidaDeTarea d "
			+ "INNER JOIN d.id.tipoDeDocumento td "
			+ "INNER JOIN d.id.tarea t "		
			+ "WHERE td.idTipoDeDocumento = :idTipoDeDocumento "
			+ "AND t.idTarea IN ( "
										+ "SELECT it.tarea.idTarea FROM InstanciaDeTarea it WHERE it.instanciaDeProceso.idExpediente = :idExpediente "
			+ ")"),			
	@NamedQuery(name="TipoDeDocumento.getTipoDeDocumentoPorNombreDeTipoDeDocumentoIdInstanciaDeTarea", 
	query="	SELECT t "
			+ "	FROM TipoDeDocumento t, DocumentoDeSalidaDeTarea d, Tarea ta, InstanciaDeTarea i "
			+ "	WHERE i.idInstanciaDeTarea = :idInstanciaDeTarea " 
			+ "	AND i.tarea.idTarea = ta.idTarea "
			+ "	AND d.id.tarea.idTarea = ta.idTarea "
			+ "	AND d.id.tipoDeDocumento.idTipoDeDocumento = t.idTipoDeDocumento "
				+ "	AND levenshtein(upper(replace(t.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', '')) ) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63) "
			+ " order by levenshtein(upper(replace(t.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', '')) ) asc"),			
	
	@NamedQuery(name="TipoDeDocumento.getTipoDeDocumentoPrimeraTareaDocAdiccionales", 
			query=" SELECT  new cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO( min(d.idTipoDeDocumento) , d.nombreDeTipoDeDocumento )  "
					+ " FROM TipoDeDocumento d, DocumentoDeSalidaDeTarea s, Tarea t "
					+ " WHERE d.idTipoDeDocumento = s.id.tipoDeDocumento.idTipoDeDocumento " 
					+ " AND s.id.tarea.idTarea = t.idTarea "
					+ " AND t.orden = 1 "
					+ " AND s.id.orden = 1 "
					+ "	GROUP BY  d.nombreDeTipoDeDocumento "
					+ " ORDER BY  d.nombreDeTipoDeDocumento asc " ),	
	
	@NamedQuery(name="TipoDeDocumento.getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente", 
	query="SELECT t FROM TipoDeDocumento t, DocumentoDeSalidaDeTarea d, Tarea ta, InstanciaDeTarea i, InstanciaDeProceso ip "
			+ "WHERE ip.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
			+ "AND ip.idExpediente = :idExpediente " 
			+ "AND i.tarea.idTarea = ta.idTarea "
			+ "AND d.id.tarea.idTarea = ta.idTarea "
			+ "AND d.id.tipoDeDocumento.idTipoDeDocumento = t.idTipoDeDocumento "		
			+ "	AND levenshtein(upper(replace(t.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', '')) ) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63) "
			+ " order by levenshtein(upper(replace(t.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', '')) ) asc"),
	
	@NamedQuery(name="TipoDeDocumento.buscaTodosLosNombreDeLosDocumentos",
			query=" SELECT DISTINCT t.nombreDeTipoDeDocumento "
				+ " FROM TipoDeDocumento t "
				+ "	order by t.nombreDeTipoDeDocumento asc "),	
	
	@NamedQuery(name="TipoDeDocumento.buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso",
			query=" SELECT DISTINCT td "
				+ " FROM TipoDeDocumento td "
				+ "	inner join td.documentosDeSalidasDeTareas ds "
				+ " inner join ds.id.tarea t "
				+ " inner join t.proceso p "
				+ " where t.orden = 1 "
				+ " and p.vigente = true"
				+ " and p.codigoProceso = :codigoProceso "
				),	
	
	@NamedQuery(name="TipoDeDocumento.buscaTodosLosNombreDeLosDocumentosSubidos",
	query=" SELECT DISTINCT t.nombreDeTipoDeDocumento "
		+ " FROM TipoDeDocumento t "
		+ " WHERE t.idTipoDeDocumento in ( "
		+ " SELECT a.tipoDeDocumento.idTipoDeDocumento FROM ArchivosInstDeTarea a "
		+ " )"
		+ "	order by t.nombreDeTipoDeDocumento asc "),
	
	@NamedQuery(name="TipoDeDocumento.getTiposDeDocumentosPorCodigoProceso",
	query="SELECT DISTINCT d FROM TipoDeDocumento d INNER JOIN d.documentosDeSalidasDeTareas s "		
			+ "WHERE s.id.tarea.idTarea in ("
			+ "  	SELECT t.idTarea FROM Tarea t INNER JOIN t.proceso p "
			+ "		WHERE p.codigoProceso = :codigoProceso AND p.vigente = true "
			+ ")  "
			),
	
	@NamedQuery(name="TipoDeDocumento.getTiposDeDocumentosPorNombreExpediente",
	query="SELECT DISTINCT d FROM TipoDeDocumento d INNER JOIN d.documentosDeSalidasDeTareas s "		
			+ "WHERE s.id.tarea.idTarea in ("
			+ "  	SELECT t.idTarea "
			+ "		FROM Tarea t, InstanciaDeProceso ip "
			+ "		INNER JOIN t.proceso p "
			+ "		WHERE ip.proceso.idProceso = p.idProceso "
			+ "		AND ip.nombreExpediente = :nombreExpediente "
			//+ "		AND p.vigente = true "
			+ ")  "
			)
})
public class TipoDeDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_TIPO_DE_DOCUMENTO", sequenceName="\"SEQ_ID_TIPO_DE_DOCUMENTO\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TIPO_DE_DOCUMENTO")
	@Column(name="\"ID_TIPO_DE_DOCUMENTO\"")
	private long idTipoDeDocumento;

	@Column(name="\"A_NOMBRE_DE_TIPO_DE_DOCUMENTO\"")
	private String nombreDeTipoDeDocumento;

	//bi-directional many-to-one association to DocumentoDeSalidaDeTarea
	@OneToMany(mappedBy="id.tipoDeDocumento", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTareas;
	
	@Column(name="\"B_CONFORMA_EXPEDIENTE\"")
	private boolean conformaExpediente;
	
	//bi-directional many-to-one association to AsignacionesNumerosDoc
	@OneToMany(mappedBy="tipoDeDocumento", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<AsignacionesNumerosDoc> asignacionesNumerosDocs;

	@Column(name="\"B_APLICA_VISACION\"")
	private boolean aplicaVisacion;
	
	@Column(name="\"B_APLICA_FEA\"")
	private boolean aplicaFEA;
	
	@Column(name="\"B_ES_DOCUMENTO_CONDUCTOR\"")
	private boolean esDocumentoConductor;
	
	//bi-directional many-to-one association to AsignacionesNumerosDoc
	@OneToMany(mappedBy="tipoDeDocumento", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoArchivosInstDeTarea> historicosArchivosInstDeTarea;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="tipoDeDocumento", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ArchivosInstDeTarea> archivosInstDeTarea;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="tipoDeDocumento", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoFirma> historicoFirmas;
	
	//bi-directional many-to-one association to Proceso
	@ManyToOne
	@JoinColumn(name="\"ID_CATEGORIA_DE_TIPO_DE_DOCUMENTO\"")
	private CategoriaDeTipoDeDocumento categoriaDeTipoDeDocumento;
	
	@Column(name="\"B_NUMERACION_AUTO\"")
	private Boolean numAuto;
	
	@Column(name="\"A_COD_TIPO_DOC\"")
	private String codTipoDocumento;
	
	@Column(name="\"A_NOM_COMP_CAT_TIPO_DOC\"")
	private String nombreCompletoCategoriaTipoDocumento;	

	public TipoDeDocumento() {
	}

	public long getIdTipoDeDocumento() {
		return this.idTipoDeDocumento;
	}

	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}

	public String getNombreDeTipoDeDocumento() {
		return this.nombreDeTipoDeDocumento;
	}

	public void setNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}
	
	public boolean getConformaExpediente() {
		return conformaExpediente;
	}

	public void setConformaExpediente(boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}

	public boolean getAplicaVisacion() {
		return aplicaVisacion;
	}

	public void setAplicaVisacion(boolean aplicaVisacion) {
		this.aplicaVisacion = aplicaVisacion;
	}

	public boolean getAplicaFEA() {
		return aplicaFEA;
	}

	public void setAplicaFEA(boolean aplicaFEA) {
		this.aplicaFEA = aplicaFEA;
	}

	public boolean getEsDocumentoConductor() {
		return esDocumentoConductor;
	}

	public void setEsDocumentoConductor(boolean esDocumentoConductor) {
		this.esDocumentoConductor = esDocumentoConductor;
	}

	public List<DocumentoDeSalidaDeTarea> getDocumentosDeSalidasDeTareas() {
		return this.documentosDeSalidasDeTareas;
	}

	public void setDocumentosDeSalidasDeTareas(List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTareas) {
		this.documentosDeSalidasDeTareas = documentosDeSalidasDeTareas;
	}

	public DocumentoDeSalidaDeTarea addDocumentosDeSalidasDeTarea(DocumentoDeSalidaDeTarea documentosDeSalidasDeTarea) {
		getDocumentosDeSalidasDeTareas().add(documentosDeSalidasDeTarea);
		documentosDeSalidasDeTarea.getId().setTipoDeDocumento(this);

		return documentosDeSalidasDeTarea;
	}

	public DocumentoDeSalidaDeTarea removeDocumentosDeSalidasDeTarea(DocumentoDeSalidaDeTarea documentosDeSalidasDeTarea) {
		getDocumentosDeSalidasDeTareas().remove(documentosDeSalidasDeTarea);
		documentosDeSalidasDeTarea.getId().setTipoDeDocumento(null);
		return documentosDeSalidasDeTarea;
	}
	
	public List<AsignacionesNumerosDoc> getAsignacionesNumerosDocs() {
		return this.asignacionesNumerosDocs;
	}

	public void setAsignacionesNumerosDocs(List<AsignacionesNumerosDoc> asignacionesNumerosDocs) {
		this.asignacionesNumerosDocs = asignacionesNumerosDocs;
	}

	public AsignacionesNumerosDoc addAsignacionesNumerosDoc(AsignacionesNumerosDoc asignacionesNumerosDoc) {
		getAsignacionesNumerosDocs().add(asignacionesNumerosDoc);
		asignacionesNumerosDoc.setTipoDeDocumento(this);

		return asignacionesNumerosDoc;
	}

	public AsignacionesNumerosDoc removeAsignacionesNumerosDoc(AsignacionesNumerosDoc asignacionesNumerosDoc) {
		getAsignacionesNumerosDocs().remove(asignacionesNumerosDoc);
		asignacionesNumerosDoc.setTipoDeDocumento(null);

		return asignacionesNumerosDoc;	
	}

	public List<HistoricoArchivosInstDeTarea> getHistoricosArchivosInstDeTarea() {
		return historicosArchivosInstDeTarea;
	}

	public void setHistoricosArchivosInstDeTarea(List<HistoricoArchivosInstDeTarea> historicosArchivosInstDeTarea) {
		this.historicosArchivosInstDeTarea = historicosArchivosInstDeTarea;
	}
	
	public HistoricoArchivosInstDeTarea addHistoricoArchivosInstDeTarea(HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea) {
		getHistoricosArchivosInstDeTarea().add(historicoArchivosInstDeTarea);
		historicoArchivosInstDeTarea.setTipoDeDocumento(this);

		return historicoArchivosInstDeTarea;
	}

	public HistoricoArchivosInstDeTarea removeHistoricoArchivosInstDeTarea(HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea) {
		getHistoricosArchivosInstDeTarea().remove(historicoArchivosInstDeTarea);
		historicoArchivosInstDeTarea.setTipoDeDocumento(null);

		return historicoArchivosInstDeTarea;	
	}

	public List<ArchivosInstDeTarea> getArchivosInstDeTarea() {
		return archivosInstDeTarea;
	}

	public void setArchivosInstDeTarea(List<ArchivosInstDeTarea> archivosInstDeTarea) {
		this.archivosInstDeTarea = archivosInstDeTarea;
	}
	
	public ArchivosInstDeTarea addArchivosInstDeTarea(ArchivosInstDeTarea archivosInstDeTarea) {
		getArchivosInstDeTarea().add(archivosInstDeTarea);
		archivosInstDeTarea.setTipoDeDocumento(this);
		return archivosInstDeTarea;
	}

	public ArchivosInstDeTarea removeArchivosInstDeTarea(ArchivosInstDeTarea archivosInstDeTarea) {
		getArchivosInstDeTarea().remove(archivosInstDeTarea);
		archivosInstDeTarea.setTipoDeDocumento(null);
		return archivosInstDeTarea;
	}

	public List<HistoricoFirma> getHistoricoFirmas() {
		return historicoFirmas;
	}

	public void setHistoricoFirmas(List<HistoricoFirma> historicoFirmas) {
		this.historicoFirmas = historicoFirmas;
	}
	
	public HistoricoFirma addHistoricoFirma(HistoricoFirma historicoFirma) {
		getHistoricoFirmas().add(historicoFirma);
		historicoFirma.setTipoDeDocumento(this);
		return historicoFirma;
	}
	
	public HistoricoFirma removeHistoricoFirma(HistoricoFirma historicoFirma) {
		getHistoricoFirmas().remove(historicoFirma);
		historicoFirma.setTipoDeDocumento(null);
		return historicoFirma;
	}
	
	public CategoriaDeTipoDeDocumento getCategoriaDeTipoDeDocumento() {
		return categoriaDeTipoDeDocumento;
	}

	public void setCategoriaDeTipoDeDocumento(CategoriaDeTipoDeDocumento categoriaDeTipoDeDocumento) {
		this.categoriaDeTipoDeDocumento = categoriaDeTipoDeDocumento;
	}	

	public Boolean getNumAuto() {
		return numAuto;
	}

	public void setNumAuto(Boolean numAuto) {
		this.numAuto = numAuto;
	}

	public String getCodTipoDocumento() {
		return codTipoDocumento;
	}

	public void setCodTipoDocumento(String codTipoDocumento) {
		this.codTipoDocumento = codTipoDocumento;
	}
	
	public String getNombreCompletoCategoriaTipoDocumento() {
		return nombreCompletoCategoriaTipoDocumento;
	}

	public void setNombreCompletoCategoriaTipoDocumento(String nombreCompletoCategoriaTipoDocumento) {
		this.nombreCompletoCategoriaTipoDocumento = nombreCompletoCategoriaTipoDocumento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aplicaFEA ? 1231 : 1237);
		result = prime * result + (aplicaVisacion ? 1231 : 1237);
		result = prime * result + ((archivosInstDeTarea == null) ? 0 : archivosInstDeTarea.hashCode());
		result = prime * result + ((asignacionesNumerosDocs == null) ? 0 : asignacionesNumerosDocs.hashCode());
		result = prime * result + (conformaExpediente ? 1231 : 1237);
		result = prime * result + ((documentosDeSalidasDeTareas == null) ? 0 : documentosDeSalidasDeTareas.hashCode());
		result = prime * result + (esDocumentoConductor ? 1231 : 1237);
		result = prime * result + ((historicoFirmas == null) ? 0 : historicoFirmas.hashCode());
		result = prime * result
				+ ((historicosArchivosInstDeTarea == null) ? 0 : historicosArchivosInstDeTarea.hashCode());
		result = prime * result + (int) (idTipoDeDocumento ^ (idTipoDeDocumento >>> 32));
		result = prime * result + ((nombreDeTipoDeDocumento == null) ? 0 : nombreDeTipoDeDocumento.hashCode());
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
		TipoDeDocumento other = (TipoDeDocumento) obj;
		if (aplicaFEA != other.aplicaFEA)
			return false;
		if (aplicaVisacion != other.aplicaVisacion)
			return false;
		if (archivosInstDeTarea == null) {
			if (other.archivosInstDeTarea != null)
				return false;
		} else if (!archivosInstDeTarea.equals(other.archivosInstDeTarea))
			return false;
		if (asignacionesNumerosDocs == null) {
			if (other.asignacionesNumerosDocs != null)
				return false;
		} else if (!asignacionesNumerosDocs.equals(other.asignacionesNumerosDocs))
			return false;
		if (conformaExpediente != other.conformaExpediente)
			return false;
		if (documentosDeSalidasDeTareas == null) {
			if (other.documentosDeSalidasDeTareas != null)
				return false;
		} else if (!documentosDeSalidasDeTareas.equals(other.documentosDeSalidasDeTareas))
			return false;
		if (esDocumentoConductor != other.esDocumentoConductor)
			return false;
		if (historicoFirmas == null) {
			if (other.historicoFirmas != null)
				return false;
		} else if (!historicoFirmas.equals(other.historicoFirmas))
			return false;
		if (historicosArchivosInstDeTarea == null) {
			if (other.historicosArchivosInstDeTarea != null)
				return false;
		} else if (!historicosArchivosInstDeTarea.equals(other.historicosArchivosInstDeTarea))
			return false;
		if (idTipoDeDocumento != other.idTipoDeDocumento)
			return false;
		if (nombreDeTipoDeDocumento == null) {
			if (other.nombreDeTipoDeDocumento != null)
				return false;
		} else if (!nombreDeTipoDeDocumento.equals(other.nombreDeTipoDeDocumento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoDeDocumento [idTipoDeDocumento=" + idTipoDeDocumento + ", nombreDeTipoDeDocumento="
				+ nombreDeTipoDeDocumento + ", conformaExpediente=" + conformaExpediente + ", aplicaVisacion="
				+ aplicaVisacion 
				+ ", aplicaFEA=" + aplicaFEA 
				+ ", conformaExpediente=" + conformaExpediente 
				+ ", esDocumentoConductor=" + esDocumentoConductor 
				+ ", numAuto=" + numAuto
				+ ", codTipoDocumento=" + codTipoDocumento
				+ ", nombreCompletoCategoriaTipoDocumento=" + nombreCompletoCategoriaTipoDocumento				
				+ "]";
	}

}
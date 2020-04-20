package cl.gob.scj.sgdp.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_HISTORICO_FIRMAS\"")
@NamedQueries({
	
	@NamedQuery(name="HistoricoFirma.findAll", query="SELECT a FROM HistoricoFirma a"),
	
	@NamedQuery(name="HistoricoFirma.getHistoricoFirmaDocumentoFEAPorIdArchivo", 
	query=" SELECT hf from HistoricoFirma hf "
			+ "	where hf.tipoFirma IN ('WEB_START', 'CENTRALIZADO') "
			+ "	and hf.idArchivoCMS = :idArchivoCMS "),
	
	@NamedQuery(name="HistoricoFirma.getListaDocumentosFirmados", 
	query=" SELECT DISTINCT hf.idArchivoCMS from InstanciaDeProceso ip"
			+ " inner join ip.instanciasDeTareas it "
			+ " inner join it.historicoFirmas hf "
			+ "	where hf.tipoFirma = 'WEB_START' "
			+ "	and ip.idExpediente = :idExpediente "),
	
	@NamedQuery(name="HistoricoFirma.getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario", 
	query="SELECT hf FROM HistoricoFirma hf, ArchivosInstDeTarea ai "			
			+ "WHERE hf.tipoFirma IN ('WEB_START', 'CENTRALIZADO') "
			+ "AND hf.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND hf.idUsuario = :idUsuario "
			+ "AND ai.instanciaDeTarea.idInstanciaDeTarea = hf.instanciaDeTarea.idInstanciaDeTarea "			
			+ "AND ai.idArchivoCms = hf.idArchivoCMS "
			+ "AND ai.idUsuario = hf.idUsuario "
			+ "AND ai.tipoDeDocumento.idTipoDeDocumento = hf.tipoDeDocumento.idTipoDeDocumento "
			+ "AND ("
			+ "		SELECT count (*) FROM HistoricoFirma hf2, ArchivosInstDeTarea ai2 "
			+ "		WHERE hf2.tipoFirma IN ('WEB_START', 'CENTRALIZADO')"
			+ "		AND hf2.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "		AND hf2.idUsuario = :idUsuario "
			+ "		AND ai2.instanciaDeTarea.idInstanciaDeTarea = hf2.instanciaDeTarea.idInstanciaDeTarea "			
			+ "		AND ai2.idArchivoCms = hf2.idArchivoCMS "
			+ "		AND ai2.idUsuario = hf2.idUsuario "
			+ "		AND ai2.tipoDeDocumento.idTipoDeDocumento = hf2.tipoDeDocumento.idTipoDeDocumento "
			+ "    )"
			+ "		>= "
			+ "( SELECT count(*) FROM DocumentoDeSalidaDeTarea ds WHERE ds.id.tarea.idTarea = ai.instanciaDeTarea.tarea.idTarea "
			//+ "  AND ds.id.tipoDeDocumento.idTipoDeDocumento = hf.tipoDeDocumento.idTipoDeDocumento "
			+ " AND ds.id.tipoDeDocumento.aplicaFEA = true "			
			+ ") ")	
	})

public class HistoricoFirma {

	@Id
	@SequenceGenerator(name="SEQ_ID_HISTORICO_FIRMA", sequenceName="\"SEQ_ID_HISTORICO_FIRMA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HISTORICO_FIRMA")
	@Column(name="\"ID_HISTORICO_FIRMA\"")
	private long idHistoricoFirma;	
	
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"")
	private InstanciaDeTarea instanciaDeTarea;

	@Column(name="\"ID_ARCHIVO_CMS\"")
	private String idArchivoCMS;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_FIRMA\"")
	private Date fechaFirma;
	
	@Column(name="\"A_TIPO_FIRMA\"")
	private String tipoFirma;
	
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DOCUMENTO\"")
	private TipoDeDocumento tipoDeDocumento;
	
	public long getIdHistoricoFirma() {
		return idHistoricoFirma;
	}

	public void setIdHistoricoFirma(long idHistoricoFirma) {
		this.idHistoricoFirma = idHistoricoFirma;
	}

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public String getIdArchivoCMS() {
		return idArchivoCMS;
	}

	public void setIdArchivoCMS(String idArchivoCMS) {
		this.idArchivoCMS = idArchivoCMS;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaFirma() {
		return fechaFirma;
	}

	public void setFechaFirma(Date fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}

	@Override
	public String toString() {
		return "HistoricoFirma [idHistoricoFirma=" + idHistoricoFirma + ", instanciaDeTarea=" + instanciaDeTarea
				+ ", idArchivoCMS=" + idArchivoCMS + ", idUsuario=" + idUsuario + ", fechaFirma=" + fechaFirma
				+ ", tipoFirma=" + tipoFirma + ", tipoDeDocumento=" + tipoDeDocumento + "]";
	}	
		
}

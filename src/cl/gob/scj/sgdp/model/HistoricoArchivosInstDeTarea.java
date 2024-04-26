package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_ARCHIVOS_HIST_INST_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_ARCHIVOS_INST_DE_TAREAS\"")
@NamedQueries({
	
	@NamedQuery(name="HistoricoArchivosInstDeTarea.getHistoricoDeArchivosPorIdArchivoCMS",
			query="SELECT HA FROM HistoricoArchivosInstDeTarea HA "
			+ "WHERE HA.idArchivoCms =:idArchivoCms "),
	
	@NamedQuery(name="HistoricoArchivosInstDeTarea.findAll", query="SELECT a FROM HistoricoArchivosInstDeTarea a"),
	
	@NamedQuery(name="HistoricoArchivosInstDeTarea.findByIdCMS", query="SELECT a FROM HistoricoArchivosInstDeTarea a where a.idArchivoCms = :idArchivoCms"),
	
	@NamedQuery(name="HistoricoArchivosInstDeTarea.getHistoricoDeArchivosPorIdInstanciaDeTareaIdUsuario", 
				query="SELECT HA FROM HistoricoArchivosInstDeTarea HA "
						+ "INNER JOIN HA.historicoDeInstDeTarea.instanciaDeTareaDeOrigen HI "						
						+ "WHERE HA.idUsuario =:idUsuario  "
						+ "AND HI.idInstanciaDeTarea =:idInstanciaDeTarea"),
	
	@NamedQuery(name="HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTareaHistoricoOrigen", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
		+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
		+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
		+ "( "
		+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
		+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ "AND h.fechaMovimiento = "
		+ "( "
		+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
		+ " WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
		+ ") "
		+ ") as ultimoComentario, "
		+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA, "
		+ "ip.idExpediente as idExpediente, "
		+ "( "
		+ "SELECT rs.nombreResponsabilidad "
		+ "FROM ResponsabilidadTarea rt "
		+ "INNER JOIN rt.id.responsabilidad rs "
		+ "WHERE rt.id.tarea.idTarea = t.idTarea "
		+ "AND rs.idResponsabilidad = (SELECT MAX(w.idResponsabilidad) FROM ResponsabilidadTarea k INNER JOIN k.id.responsabilidad w WHERE k.id.tarea.idTarea = t.idTarea ) "
		+ ") as nombreResponsabilidad ,"
		+ "( "
		+ "SELECT l.estaFirmadoConFEAWebStart "
		+ "FROM ArchivosInstDeTarea l "
		+ "WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
		+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
		+ ") as estaFirmadoConFEAWebStart, "
		+ "( "
		+ "SELECT l.estaFirmadoConFEACentralizada "
		+ "FROM ArchivosInstDeTarea l "
		+ "WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
		+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
		+ ") as estaFirmadoConFEACentralizada, "
		+ "td.conformaExpediente as conformaExpediente "
		+ "FROM HistoricoArchivosInstDeTarea ai "		
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeOrigen i "
		+ "INNER JOIN i.tarea t "
		+ "INNER JOIN ai.tipoDeDocumento td " 
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeOrigen.instanciaDeProceso ip "
		+ "WHERE ip.idInstanciaDeProceso = "
		+ "( "
		+ "SELECT i2.instanciaDeProceso.idInstanciaDeProceso FROM InstanciaDeTarea i2 "
		+ "WHERE i2.idInstanciaDeTarea = :idInstanciaDeTarea "
		+ ") "
	+ "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, "
	+ "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA, ip.idExpediente, t.idTarea ORDER BY fechaSubido DESC "),

	/*@NamedQuery(name="HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTareaHistoricoDestino", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
		+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
		+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
		+ "( "
		+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
		+ "WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ "AND h.fechaMovimiento = "
		+ "( "
		+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
		+ " WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
		+ ") "
		+ ") as ultimoComentario, "
		+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA, "
		+ "ip.idExpediente as idExpediente, "
		+ "( "
		+ "SELECT rs.nombreResponsabilidad "
		+ "FROM ResponsabilidadTarea rt "
		+ "INNER JOIN rt.id.responsabilidad rs "
		+ "WHERE rt.id.tarea.idTarea = t.idTarea "
		+ "AND rs.idResponsabilidad = (SELECT MAX(w.idResponsabilidad) FROM ResponsabilidadTarea k INNER JOIN k.id.responsabilidad w WHERE k.id.tarea.idTarea = t.idTarea ) "
		+ ") as nombreResponsabilidad "
		+ "FROM HistoricoArchivosInstDeTarea ai "
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeDestino i "
		+ "INNER JOIN i.tarea t "
		+ "INNER JOIN ai.tipoDeDocumento td " 
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeDestino.instanciaDeProceso ip "
		+ "WHERE ip.idInstanciaDeProceso = "
		+ "( "
		+ "SELECT i2.instanciaDeProceso.idInstanciaDeProceso FROM InstanciaDeTarea i2 "
		+ "WHERE i2.idInstanciaDeTarea = :idInstanciaDeTarea "
		+ ") "
		+ "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, "
		+ "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA, ip.idExpediente, t.idTarea ORDER BY fechaSubido DESC "),*/
	
	@NamedQuery(name="HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdExpedienteHistoricoOrigen", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
		+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
		+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
		+ "( "
		+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
		+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ "AND h.fechaMovimiento = "
		+ "( "
		+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
		+ " WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
		+ ") "
		+ ") as ultimoComentario, "
		+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA, "
		+ "ip.idExpediente as idExpediente, "
		+ "( "
		+ "SELECT rs.nombreResponsabilidad "
		+ "FROM ResponsabilidadTarea rt "
		+ "INNER JOIN rt.id.responsabilidad rs "
		+ "WHERE rt.id.tarea.idTarea = t.idTarea "
		+ "AND rs.idResponsabilidad = (SELECT MAX(w.idResponsabilidad) FROM ResponsabilidadTarea k INNER JOIN k.id.responsabilidad w WHERE k.id.tarea.idTarea = t.idTarea ) "
		+ ") as nombreResponsabilidad, "		
		+ "( "
		+ "SELECT l.estaFirmadoConFEAWebStart "
		+ "FROM ArchivosInstDeTarea l WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
		+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
		+ ") as estaFirmadoConFEAWebStart, "
		+ "( "
		+ "SELECT l.estaFirmadoConFEACentralizada "
		+ "FROM ArchivosInstDeTarea l WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
		+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
		+ ") as estaFirmadoConFEACentralizada "		
		+ "FROM HistoricoArchivosInstDeTarea ai "
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeOrigen i "
		+ "INNER JOIN i.tarea t "
		+ "INNER JOIN ai.tipoDeDocumento td " 
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeOrigen.instanciaDeProceso ip "
		+ "WHERE ip.idExpediente = :idExpediente "	
	+ "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, "
	+ "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA, ip.idExpediente, t.idTarea ORDER BY fechaSubido DESC "),

	/*@NamedQuery(name="HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdExpedienteHistoricoDestino", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
		+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
		+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
		+ "( "
		+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
		+ "WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ "AND h.fechaMovimiento = "
		+ "( "
		+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
		+ " WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = i.idInstanciaDeTarea "
		+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
		+ ") "
		+ ") as ultimoComentario, "
		+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA, "
		+ "ip.idExpediente as idExpediente, "
		+ "( "
		+ "SELECT rs.nombreResponsabilidad "
		+ "FROM ResponsabilidadTarea rt "
		+ "INNER JOIN rt.id.responsabilidad rs "
		+ "WHERE rt.id.tarea.idTarea = t.idTarea "
		+ "AND rs.idResponsabilidad = (SELECT MAX(w.idResponsabilidad) FROM ResponsabilidadTarea k INNER JOIN k.id.responsabilidad w WHERE k.id.tarea.idTarea = t.idTarea ) "
		+ ") as nombreResponsabilidad "
		+ "FROM HistoricoArchivosInstDeTarea ai "
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeDestino i "
		+ "INNER JOIN i.tarea t "
		+ "INNER JOIN ai.tipoDeDocumento td " 
		+ "INNER JOIN ai.historicoDeInstDeTarea.instanciaDeTareaDeDestino.instanciaDeProceso ip "
		+ "WHERE ip.idExpediente = :idExpediente "
		+ "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, "
		+ "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA, ip.idExpediente, t.idTarea ORDER BY fechaSubido DESC ")*/
	
})
public class HistoricoArchivosInstDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_HISTORICO_ARCHIVOS_INST_DE_TAREAS", sequenceName="\"SEQ_ID_HISTORICO_ARCHIVOS_INST_DE_TAREAS\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HISTORICO_ARCHIVOS_INST_DE_TAREAS")
	//@Column(name="\"ID_ARCHIVOS_HIST_INST_DE_TAREAS\"")
	@Column(name="\"ID_HISTORICO_ARCHIVOS_INST_DE_TAREAS\"")
	private long idHistoricoArchivosInstDeTarea;	
	
	@Column(name="\"ID_ARCHIVO_CMS\"")
	private String idArchivoCms;

	@Column(name="\"A_MIME_TYPE\"")
	private String mimeType;

	@Column(name="\"A_NOMBRE_ARCHIVO\"")
	private String nombreArchivo;
	
	@Column(name="\"A_VERSION\"")
	private String version;

	//bi-directional many-to-one association to HistoricoDeInstDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_HISTORICO_DE_INST_DE_TAREA\"")
	private HistoricoDeInstDeTarea historicoDeInstDeTarea;
	
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DOCUMENTO\"")
	private TipoDeDocumento tipoDeDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_SUBIDO\"")
	private Date fechaSubido;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_DOCUMENTO\"")
	private Date fechaDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_RECEPCION\"")
	private Date fechaRecepcion;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Column(name="\"B_ANULADO\"")
	private Boolean anulado;

	public HistoricoArchivosInstDeTarea() {
	}	

	public HistoricoArchivosInstDeTarea(HistoricoDeInstDeTarea historicoDeInstDeTarea, ArchivosInstDeTarea archivosInstDeTarea) {
		super();
		this.setHistoricoDeInstDeTarea(historicoDeInstDeTarea);
		this.setIdArchivoCms(archivosInstDeTarea.getIdArchivoCms());
		this.setMimeType(archivosInstDeTarea.getMimeType());
		this.setNombreArchivo(archivosInstDeTarea.getNombreArchivo());
		this.setVersion(archivosInstDeTarea.getVersion());
		this.setIdUsuario(archivosInstDeTarea.getIdUsuario());
		this.setFechaDocumento(archivosInstDeTarea.getFechaDocumento());
		this.setFechaRecepcion(archivosInstDeTarea.getFechaRecepcion());
		this.setFechaSubido(archivosInstDeTarea.getFechaSubido());
		this.setTipoDeDocumento(archivosInstDeTarea.getTipoDeDocumento());
	}

	public String getIdArchivoCms() {
		return this.idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HistoricoDeInstDeTarea getHistoricoDeInstDeTarea() {
		return this.historicoDeInstDeTarea;
	}

	public void setHistoricoDeInstDeTarea(HistoricoDeInstDeTarea historicoDeInstDeTarea) {
		this.historicoDeInstDeTarea = historicoDeInstDeTarea;
	}	

	public long getIdHistoricoArchivosInstDeTarea() {
		return idHistoricoArchivosInstDeTarea;
	}
	
	public void setIdHistoricoArchivosInstDeTarea(
			long idHistoricoArchivosInstDeTarea) {
		this.idHistoricoArchivosInstDeTarea = idHistoricoArchivosInstDeTarea;
	}	
	
	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	
	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}	
	
	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Date getFechaSubido() {
		return fechaSubido;
	}

	public void setFechaSubido(Date fechaSubido) {
		this.fechaSubido = fechaSubido;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	public Boolean getAnulado() {
		return anulado;
	}

	public void setAnulado(Boolean anulado) {
		this.anulado = anulado;
	}

	@Override
	public String toString() {
		return "ArchivosHistInstDeTarea [idHistoricoArchivosInstDeTarea="
				+ idHistoricoArchivosInstDeTarea + ", idArchivoCms=" + idArchivoCms
				+ ", mimeType=" + mimeType + ", nombreArchivo=" + nombreArchivo
				+ ", version=" + version 
				+ ", fechaSubido=" + fechaSubido
				+ ", fechaDocumento=" + fechaDocumento
				+ ", fechaRecepcion=" + fechaRecepcion
				+ ", anulado=" + anulado	
				+ "]";
	}		

}
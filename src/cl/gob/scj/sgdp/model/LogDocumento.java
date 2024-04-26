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
@Table(name="\"SGDP_LOG_DOCUMENTO\"")
@NamedQueries({
	@NamedQuery(name="LogDocumento.findAllAsc", query="SELECT l "
				+ " FROM LogDocumento l "
				+ " where l.tipoOperacionLogDocumento LIKE :filterString "
				+ " ORDER BY :orderColum ASC "
			),
	@NamedQuery(name="LogDocumento.findAllDesc", query="SELECT l "
			+ " FROM LogDocumento l "
			+ " where l.tipoOperacionLogDocumento LIKE :filterString "
			+ " ORDER BY :orderColum DESC "
		),
	@NamedQuery(name="LogDocumento.countAll", query="SELECT count(*) "
				+ " FROM LogDocumento l "
				+ " where l.tipoOperacionLogDocumento LIKE :filterString "
				
			)
})
public class LogDocumento {

	@Id
	@SequenceGenerator(name="SEQ_ID_LOG_DOCUMENTO", sequenceName="\"SEQ_ID_LOG_DOCUMENTO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_LOG_DOCUMENTO")
	@Column(name="\"ID_LOG_DOCUMENTO\"")
	private long idLogDocumento;
	
	@Column(name="\"A_TIPO_OPERACION_LOG_DOCUMENTO\"")
	private String tipoOperacionLogDocumento;
	
	@Column(name="\"A_MODULO\"")
	private String moduloLogDocumento;
	
	@Column(name="\"A_IP_LOG_DOCUMENTO\"")
	private String ipLogDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_LOG_DOCUMENTO\"")
	private Date fechaLogDocumento;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuarioLogDocumento;
	
	@Column(name="\"A_NOMBRE_USUARIO\"")
	private String nombreUsuarioLogDocumento;
	
	@Column(name="\"ID_EXPEDIENTE\"")
	private String idExpedienteLogDocumento;
	
	@Column(name="\"A_CODIGO_EXPEDIENTE\"")
	private String codigoExpedienteLogDocumento;
	
	@Column(name="\"ID_DOCUMENTO\"")
	private String idDocumentoLogDocumento;
	
	@Column(name="\"A_NOMBRE_DOCUMENTO\"")
	private String nombreDocumento;
	
	//bi-directional many-to-one association to SolicitudCreacionExp
	@ManyToOne
	@JoinColumn(name="\"ID_SOLICITUD_CREACION_EXP\"")
	private SolicitudCreacionExp solicitudCreacionExp; 	
	
	public long getIdLogDocumento() {
		return idLogDocumento;
	}

	public void setIdLogDocumento(long idLogDocumento) {
		this.idLogDocumento = idLogDocumento;
	}

	public String getTipoOperacionLogDocumento() {
		return tipoOperacionLogDocumento;
	}

	public void setTipoOperacionLogDocumento(String tipoOperacionLogDocumento) {
		this.tipoOperacionLogDocumento = tipoOperacionLogDocumento;
	}

	public String getModuloLogDocumento() {
		return moduloLogDocumento;
	}

	public void setModuloLogDocumento(String moduloLogDocumento) {
		this.moduloLogDocumento = moduloLogDocumento;
	}

	public String getIpLogDocumento() {
		return ipLogDocumento;
	}

	public void setIpLogDocumento(String ipLogDocumento) {
		this.ipLogDocumento = ipLogDocumento;
	}

	public Date getFechaLogDocumento() {
		return fechaLogDocumento;
	}

	public void setFechaLogDocumento(Date fechaLogDocumento) {
		this.fechaLogDocumento = fechaLogDocumento;
	}

	public String getIdUsuarioLogDocumento() {
		return idUsuarioLogDocumento;
	}

	public void setIdUsuarioLogDocumento(String idUsuarioLogDocumento) {
		this.idUsuarioLogDocumento = idUsuarioLogDocumento;
	}

	public String getNombreUsuarioLogDocumento() {
		return nombreUsuarioLogDocumento;
	}

	public void setNombreUsuarioLogDocumento(String nombreUsuarioLogDocumento) {
		this.nombreUsuarioLogDocumento = nombreUsuarioLogDocumento;
	}

	public String getIdExpedienteLogDocumento() {
		return idExpedienteLogDocumento;
	}

	public void setIdExpedienteLogDocumento(String idExpedienteLogDocumento) {
		this.idExpedienteLogDocumento = idExpedienteLogDocumento;
	}

	public String getCodigoExpedienteLogDocumento() {
		return codigoExpedienteLogDocumento;
	}

	public void setCodigoExpedienteLogDocumento(String codigoExpedienteLogDocumento) {
		this.codigoExpedienteLogDocumento = codigoExpedienteLogDocumento;
	}

	public String getIdDocumentoLogDocumento() {
		return idDocumentoLogDocumento;
	}

	public void setIdDocumentoLogDocumento(String idDocumentoLogDocumento) {
		this.idDocumentoLogDocumento = idDocumentoLogDocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public SolicitudCreacionExp getSolicitudCreacionExp() {
		return solicitudCreacionExp;
	}

	public void setSolicitudCreacionExp(SolicitudCreacionExp solicitudCreacionExp) {
		this.solicitudCreacionExp = solicitudCreacionExp;
	}

	@Override
	public String toString() {
		return "LogDocumento [idLogDocumento=" + idLogDocumento + ", tipoOperacionLogDocumento="
				+ tipoOperacionLogDocumento + ", moduloLogDocumento=" + moduloLogDocumento + ", ipLogDocumento="
				+ ipLogDocumento + ", fechaLogDocumento=" + fechaLogDocumento + ", idUsuarioLogDocumento="
				+ idUsuarioLogDocumento + ", nombreUsuarioLogDocumento=" + nombreUsuarioLogDocumento
				+ ", idExpedienteLogDocumento=" + idExpedienteLogDocumento + ", codigoExpedienteLogDocumento="
				+ codigoExpedienteLogDocumento + ", idDocumentoLogDocumento=" + idDocumentoLogDocumento
				+ ", nombreDocumento=" + nombreDocumento + "]";
	}
	
}

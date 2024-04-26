package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cl.gob.scj.sgdp.model.LogDocumento;

public class LogDocumentoDTO extends RespuestaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idLogDocumento;
	private String tipoOperacionLogDocumento;
	private String moduloLogDocumento;
	private String ipLogDocumento;
	private String fechaLogDocumento;
	private String idUsuarioLogDocumento;
	private String nombreUsuarioLogDocumento;
	private String idExpedienteLogDocumento;
	private String codigoExpedienteLogDocumento;
	private String idDocumentoLogDocumento;
	private String nombreDocumento;
	private long idSolicitudCreacionExp;

	public LogDocumentoDTO() {
		
	}
	
	public LogDocumentoDTO(LogDocumento e) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.idLogDocumento = e.getIdLogDocumento();
		this.tipoOperacionLogDocumento = e.getTipoOperacionLogDocumento();
		this.moduloLogDocumento = e.getModuloLogDocumento();
		this.ipLogDocumento = e.getIpLogDocumento();
		this.fechaLogDocumento = df.format(e.getFechaLogDocumento());
		this.idUsuarioLogDocumento = e.getIdUsuarioLogDocumento();
		this.nombreUsuarioLogDocumento = e.getNombreUsuarioLogDocumento();
		this.idExpedienteLogDocumento = e.getIdExpedienteLogDocumento();
		this.codigoExpedienteLogDocumento = e.getCodigoExpedienteLogDocumento();
		this.idDocumentoLogDocumento = e.getIdDocumentoLogDocumento();
		this.nombreDocumento = e.getNombreDocumento();
	}

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

	public String getFechaLogDocumento() {
		return fechaLogDocumento;
	}

	public void setFechaLogDocumento(String fechaLogDocumento) {
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
	
	public long getIdSolicitudCreacionExp() {
		return idSolicitudCreacionExp;
	}

	public void setIdSolicitudCreacionExp(long idSolicitudCreacionExp) {
		this.idSolicitudCreacionExp = idSolicitudCreacionExp;
	}

	@Override
	public String toString() {
		return "LogDocumentoDTO ["
				+ "idLogDocumento=" + this.idLogDocumento + ", "
				+ "tipoOperacionLogDocumento=" + this.tipoOperacionLogDocumento + ", "
				+ "moduloLogDocumento=" + this.moduloLogDocumento + ", "
				+ "ipLogDocumento=" + this.ipLogDocumento + ", "
				+ "fechaLogDocumento=" + this.fechaLogDocumento + ", "
				+ "idUsuarioLogDocumento=" + this.idUsuarioLogDocumento + ", "
				+ "nombreUsuarioLogDocumento=" + this.nombreUsuarioLogDocumento + ", "
				+ "idExpedienteLogDocumento=" + this.idExpedienteLogDocumento + ", "
				+ "codigoExpedienteLogDocumento=" + this.codigoExpedienteLogDocumento + ", "
				+ "idDocumentoLogDocumento=" + this.idDocumentoLogDocumento + ", "
				+ "nombreDocumento=" + this.nombreDocumento
				+ "idSolicitudCreacionExp=" + this.idSolicitudCreacionExp
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idLogDocumento ^ (idLogDocumento >>> 32));
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
		LogDocumentoDTO other = (LogDocumentoDTO) obj;
		if (idLogDocumento != other.idLogDocumento)
			return false;
		return true;
	}
	
}

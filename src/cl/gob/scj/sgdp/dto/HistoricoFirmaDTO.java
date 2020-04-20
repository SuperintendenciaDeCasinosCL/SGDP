package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

import cl.gob.scj.sgdp.tipos.FirmaType;

public class HistoricoFirmaDTO implements Serializable  {
	
	private long idHistoricoFirma;	
	private long idInstanciaDeTarea;	
	private String idArchivoCMS;
	private String idUsuario;
	private Date fechaFirma;
	private FirmaType tipoFirma;
	private long idTipoDeDocumento;
	
	public long getIdHistoricoFirma() {
		return idHistoricoFirma;
	}
	public void setIdHistoricoFirma(long idHistoricoFirma) {
		this.idHistoricoFirma = idHistoricoFirma;
	}
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
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
	public FirmaType getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(FirmaType tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}
	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}
	@Override
	public String toString() {
		return "HistoricoFirmaDTO [idHistoricoFirma=" + idHistoricoFirma + ", idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", idArchivoCMS=" + idArchivoCMS + ", idUsuario=" + idUsuario + ", fechaFirma=" + fechaFirma
				+ ", tipoFirma=" + tipoFirma.getNombreFirma() 
				+ ", idTipoDeDocumento=" + idTipoDeDocumento 
				+ "]";
	}
}

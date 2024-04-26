package cl.gob.scj.sgdp.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoFirma;

public class ArchivoAnulacionDocumentosDTO {
	private String idArchivo;
	private String nombreArchivo;
	private String idExpediente;
	private String codeExpediente;
	private long idTipoDocumento;
	private String nombreTipoDocumento;
	private long idTarea;
	private String nombreTarea;
	private String fechaSubida;
	private Boolean anulado;

	public ArchivoAnulacionDocumentosDTO() {
		super();
	}

	public ArchivoAnulacionDocumentosDTO(HistoricoFirma e) {
		super();
		DateFormat df = new SimpleDateFormat(Constantes.FORMATO_FECHA_FORM_DMY_HMS);
		this.idExpediente = e.getInstanciaDeTarea().getInstanciaDeProceso().getIdExpediente();
		this.codeExpediente = e.getInstanciaDeTarea().getInstanciaDeProceso().getNombreExpediente();
		this.idTipoDocumento = e.getTipoDeDocumento().getIdTipoDeDocumento();
		this.nombreTipoDocumento = e.getTipoDeDocumento().getNombreDeTipoDeDocumento();
		this.idTarea = e.getInstanciaDeTarea().getTarea().getIdTarea();
		this.nombreTarea = e.getInstanciaDeTarea().getTarea().getNombreTarea();
		this.fechaSubida = df.format(e.getFechaFirma());
		this.idArchivo = e.getIdArchivoCMS();
		this.anulado = e.getAnulado();
	}

	public String getIdArchivo() {
		return this.idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getCodeExpediente() {
		return codeExpediente;
	}

	public void setCodeExpediente(String codeExpediente) {
		this.codeExpediente = codeExpediente;
	}

	public long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}

	public long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(String fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public Boolean getAnulado() {
		return anulado;
	}

	public void setAnulado(Boolean anulado) {
		this.anulado = anulado;
	}

	@Override
	public String toString() {
		return "ArchivoAnulacionDocumentosDTO [idArchivo=" + idArchivo + ", nombreArchivo=" + nombreArchivo
				+ ", idExpediente=" + idExpediente + ", codeExpediente=" + codeExpediente + ", idTipoDocumento="
				+ idTipoDocumento + ", nombreTipoDocumento=" + nombreTipoDocumento + ", idTarea=" + idTarea
				+ ", nombreTarea=" + nombreTarea + ", fechaSubida=" + fechaSubida + ", anulado=" + anulado + "]";
	}

}

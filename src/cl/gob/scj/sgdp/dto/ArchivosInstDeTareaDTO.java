package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

public class ArchivosInstDeTareaDTO extends RespuestaDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6812730917092358190L;
	
	private long idInstanciaDeTarea;
	private String nombreTarea;
	private String idArchivoCms;
	private String mimeType;
	private String nombreArchivo;
	private String version;
	private String idUsuario;
	private Date fechaSubido;
	private long idTipoDeDocumento;
	private String nombreDeTipoDeDocumento;
	private String idExpediente;
	private boolean esVisable;
	private boolean aplicaFEA;
	private boolean aplicaFirmaApplet;
	private boolean esEditable;
	private String ultimoComentario;
	private boolean puedeVisarDocumentos;
	private boolean puedeAplicarFEA;
	private String nombreResponsabilidad;
	private Boolean estaFirmadoConFEAWebStart;
	private Boolean estaFirmadoConFEACentralizada;
	private Boolean conformaExpediente;
	
	private int cantAccionesEnBitacora = 0;
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}
	public String getNombreTarea() {
		return nombreTarea;
	}
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	public String getIdArchivoCms() {
		return idArchivoCms;
	}
	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
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
	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}
	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}
	public String getNombreDeTipoDeDocumento() {
		return nombreDeTipoDeDocumento;
	}
	public void setNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}	
	public boolean isEsVisable() {
		return esVisable;
	}
	public void setEsVisable(boolean esVisable) {
		this.esVisable = esVisable;
	}
	public boolean isAplicaFEA() {
		return aplicaFEA;
	}
	public void setAplicaFEA(boolean aplicaFEA) {
		this.aplicaFEA = aplicaFEA;
	}
	public boolean isAplicaFirmaApplet() {
		return aplicaFirmaApplet;
	}
	public void setAplicaFirmaApplet(boolean aplicaFirmaApplet) {
		this.aplicaFirmaApplet = aplicaFirmaApplet;
	}	
	public boolean isEsEditable() {
		return esEditable;
	}
	public void setEsEditable(boolean esEditable) {
		this.esEditable = esEditable;
	}	
	public String getUltimoComentario() {
		return ultimoComentario;
	}
	public void setUltimoComentario(String ultimoComentario) {
		this.ultimoComentario = ultimoComentario;
	}	
	public boolean getPuedeVisarDocumentos() {
		return puedeVisarDocumentos;
	}
	public void setPuedeVisarDocumentos(boolean puedeVisarDocumentos) {
		this.puedeVisarDocumentos = puedeVisarDocumentos;
	}
	public boolean getPuedeAplicarFEA() {
		return puedeAplicarFEA;
	}
	public void setPuedeAplicarFEA(boolean puedeAplicarFEA) {
		this.puedeAplicarFEA = puedeAplicarFEA;
	}	
	public String getNombreResponsabilidad() {
		return nombreResponsabilidad;
	}
	public void setNombreResponsabilidad(String nombreResponsabilidad) {
		this.nombreResponsabilidad = nombreResponsabilidad;
	}	
	public Boolean getEstaFirmadoConFEAWebStart() {
		return estaFirmadoConFEAWebStart;
	}
	public void setEstaFirmadoConFEAWebStart(Boolean estaFirmadoConFEAWebStart) {
		this.estaFirmadoConFEAWebStart = estaFirmadoConFEAWebStart;
	}
	public Boolean getEstaFirmadoConFEACentralizada() {
		return estaFirmadoConFEACentralizada;
	}
	public void setEstaFirmadoConFEACentralizada(Boolean estaFirmadoConFEACentralizada) {
		this.estaFirmadoConFEACentralizada = estaFirmadoConFEACentralizada;
	}	
	public Boolean getConformaExpediente() {
		return conformaExpediente;
	}
	public void setConformaExpediente(Boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idArchivoCms == null) ? 0 : idArchivoCms.hashCode());
		result = prime * result + (int) (idInstanciaDeTarea ^ (idInstanciaDeTarea >>> 32));
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
		ArchivosInstDeTareaDTO other = (ArchivosInstDeTareaDTO) obj;
		if (idArchivoCms == null) {
			if (other.idArchivoCms != null)
				return false;
		} else if (!idArchivoCms.equals(other.idArchivoCms))
			return false;
		if (idInstanciaDeTarea != other.idInstanciaDeTarea)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ArchivosInstDeTareaDTO [idInstanciaDeTarea=" + idInstanciaDeTarea + ", nombreTarea=" + nombreTarea
				+ ", idArchivoCms=" + idArchivoCms + ", mimeType=" + mimeType + ", nombreArchivo=" + nombreArchivo
				+ ", version=" + version + ", idUsuario=" + idUsuario + ", fechaSubido=" + fechaSubido
				+ ", idTipoDeDocumento=" + idTipoDeDocumento + ", nombreDeTipoDeDocumento=" + nombreDeTipoDeDocumento
				+ ", idExpediente=" + idExpediente + ", esVisable=" + esVisable + ", aplicaFEA=" + aplicaFEA
				+ ", aplicaFirmaApplet=" + aplicaFirmaApplet 
				+ ", esEditable=" + esEditable 
				+ ", ultimoComentario=" + ultimoComentario
				+ ", puedeVisarDocumentos=" + puedeVisarDocumentos
				+ ", puedeAplicarFEA=" + puedeAplicarFEA
				+ ", nombreResponsabilidad=" + nombreResponsabilidad
				+ ", estaFirmadoConFEAWebStart=" + estaFirmadoConFEAWebStart
				+ ", estaFirmadoConFEACentralizada=" + estaFirmadoConFEACentralizada
				+ ", conformaExpediente=" + conformaExpediente
				+ "]";
	}
	public int getCantAccionesEnBitacora() {
		return cantAccionesEnBitacora;
	}
	public void setCantAccionesEnBitacora(int cantAccionesEnBitacora) {
		this.cantAccionesEnBitacora = cantAccionesEnBitacora;
	}	
}

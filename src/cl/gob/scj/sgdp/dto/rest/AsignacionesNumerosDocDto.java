package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class AsignacionesNumerosDocDto implements Serializable  {

	// Valores de entrada Accion Ingresar

	private Long tipoDeDocumento;
	private String idArchivo;
	private String idUsuario;
	private long idInstanciaDeTarea;
	private String alfTicket;
	private long idDocumentoFirmado;
	private String uuid;
	
	// Valores de entrada Accion Modificar

	private long idAsignacionNumeroDocEntada;
	private String codigoMensaje; // OK - ERROR

	// Accion transversal a ambos metodos

	private int accion;// 0 = ingresar
						// 1 = modificar

	// Lo extrae la aplicacion
	private String Anio;

	// Valores de respuesta

	private long UltimoNumeroDocumentoExtraido;
	private long idAsignacionNumeroDoc;
	private String estado;

	public AsignacionesNumerosDocDto() {
	}

	public Long getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(Long tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}

	public long getIdAsignacionNumeroDocEntada() {
		return idAsignacionNumeroDocEntada;
	}

	public void setIdAsignacionNumeroDocEntada(long idAsignacionNumeroDocEntada) {
		this.idAsignacionNumeroDocEntada = idAsignacionNumeroDocEntada;
	}

	public String getCodigoMensaje() {
		return codigoMensaje;
	}

	public void setCodigoMensaje(String codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public String getAnio() {
		return Anio;
	}

	public void setAnio(String anio) {
		Anio = anio;
	}

	public long getUltimoNumeroDocumentoExtraido() {
		return UltimoNumeroDocumentoExtraido;
	}

	public void setUltimoNumeroDocumentoExtraido(long ultimoNumeroDocumentoExtraido) {
		UltimoNumeroDocumentoExtraido = ultimoNumeroDocumentoExtraido;
	}

	public long getIdAsignacionNumeroDoc() {
		return idAsignacionNumeroDoc;
	}

	public void setIdAsignacionNumeroDoc(long idAsignacionNumeroDoc) {
		this.idAsignacionNumeroDoc = idAsignacionNumeroDoc;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}

	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}	
	
	public String getAlfTicket() {
		return alfTicket;
	}

	public void setAlfTicket(String alfTicket) {
		this.alfTicket = alfTicket;
	}

	public long getIdDocumentoFirmado() {
		return idDocumentoFirmado;
	}

	public void setIdDocumentoFirmado(long idDocumentoFirmado) {
		this.idDocumentoFirmado = idDocumentoFirmado;
	}	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

		
	@Override
	public String toString() {
		return "AsignacionesNumerosDocDto [tipoDeDocumento=" + tipoDeDocumento + ", idArchivo=" + idArchivo
				+ ", idUsuario=" + idUsuario + ", idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", idAsignacionNumeroDocEntada=" + idAsignacionNumeroDocEntada + ", codigoMensaje=" + codigoMensaje
				+ ", accion=" + accion + ", Anio=" + Anio + ", UltimoNumeroDocumentoExtraido="
				+ UltimoNumeroDocumentoExtraido + ", idAsignacionNumeroDoc=" + idAsignacionNumeroDoc + ", estado="
				+ estado 
				+ ", alfTicket=" + alfTicket
				+ ", idDocumentoFirmado=" + idDocumentoFirmado
				+ ", uuid=" + uuid +"]";
	}
	
}

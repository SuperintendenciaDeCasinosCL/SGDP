package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class ExpedienteRestDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String creador;
	private String materia;
	private String idAutor;
	private Long idMacroProceso;
	private Long idProceso;
	private String idUsuario;
	private long idRolUsuarioSeleccionado;
	private String alfTicket;
	private String clave;
	private String fechaDeInicioInstProc;
	private String idUsuarioCrea;
	private String codigoProceso;
			
	public ExpedienteRestDTO() {
	}
	
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(String idAutor) {
		this.idAutor = idAutor;
	}
	public Long getIdMacroProceso() {
		return idMacroProceso;
	}
	public void setIdMacroProceso(Long idMacroProceso) {
		this.idMacroProceso = idMacroProceso;
	}
	public Long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdRolUsuarioSeleccionado() {
		return idRolUsuarioSeleccionado;
	}
	public void setIdRolUsuarioSeleccionado(long idRolUsuarioSeleccionado) {
		this.idRolUsuarioSeleccionado = idRolUsuarioSeleccionado;
	}
	public String getAlfTicket() {
		return alfTicket;
	}
	public void setAlfTicket(String alfTicket) {
		this.alfTicket = alfTicket;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFechaDeInicioInstProc() {
		return fechaDeInicioInstProc;
	}
	public void setFechaDeInicioInstProc(String fechaDeInicioInstProc) {
		this.fechaDeInicioInstProc = fechaDeInicioInstProc;
	}	
	public String getIdUsuarioCrea() {
		return idUsuarioCrea;
	}
	public void setIdUsuarioCrea(String idUsuarioCrea) {
		this.idUsuarioCrea = idUsuarioCrea;
	}	
	public String getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	@Override
	public String toString() {
		return "ExpedienteRestDTO [creador=" + creador + ", materia=" + materia + ", idAutor=" + idAutor
				+ ", idMacroProceso=" + idMacroProceso + ", idProceso=" + idProceso + ", idUsuario=" + idUsuario
				+ ", idRolUsuarioSeleccionado=" + idRolUsuarioSeleccionado + ", alfTicket=" + alfTicket
				+ ", fechaDeInicioInstProc=" + fechaDeInicioInstProc 
				+ ", idUsuarioCrea=" + idUsuarioCrea 
				+ ", codigoProceso=" + codigoProceso +
				"]";
	}	
}

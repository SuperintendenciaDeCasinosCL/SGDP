package cl.gob.scj.sgdp.dto;

public class DestinatarioGrupoDTO {
	
	private Integer idListaDeDistribucion;
	private String nombreCompleto;
	private String email;
	private String organizacion;
	private String cargo;
	private String respuesta;
	private TipoDeDestinatarioDTO tipoDeDestinatarioDTO;
	private String idTipoDestinatario;
	private String nombreGrupo;
	
	
	public Integer getIdListaDeDistribucion() {
		return idListaDeDistribucion;
	}
	public void setIdListaDeDistribucion(Integer idListaDeDistribucion) {
		this.idListaDeDistribucion = idListaDeDistribucion;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrganizacion() {
		return organizacion;
	}
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public TipoDeDestinatarioDTO getTipoDeDestinatarioDTO() {
		return tipoDeDestinatarioDTO;
	}
	public void setTipoDeDestinatarioDTO(TipoDeDestinatarioDTO tipoDeDestinatarioDTO) {
		this.tipoDeDestinatarioDTO = tipoDeDestinatarioDTO;
	}
	public String getIdTipoDestinatario() {
		return idTipoDestinatario;
	}
	public void setIdTipoDestinatario(String idTipoDestinatario) {
		this.idTipoDestinatario = idTipoDestinatario;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	@Override
	public String toString() {
		return "DestinatarioGrupoDTO [idListaDeDistribucion=" + idListaDeDistribucion + ", nombreCompleto="
				+ nombreCompleto + ", email=" + email + ", organizacion=" + organizacion + ", cargo=" + cargo
				+ ", respuesta=" + respuesta + ", tipoDeDestinatarioDTO=" + tipoDeDestinatarioDTO
				+ ", idTipoDestinatario=" + idTipoDestinatario + ", nombreGrupo=" + nombreGrupo + "]";
	}
	
	
	
	

}

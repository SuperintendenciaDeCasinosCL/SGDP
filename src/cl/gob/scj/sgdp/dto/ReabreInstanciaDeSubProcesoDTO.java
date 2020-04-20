package cl.gob.scj.sgdp.dto;

public class ReabreInstanciaDeSubProcesoDTO extends RespuestaDTO {

	private long idInstanciaDeTarea;
	private String idUsuarioSeleccionado;
	private String comentario;
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}
	public String getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}
	public void setIdUsuarioSeleccionado(String idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	@Override
	public String toString() {
		return "ReabreInstanciaDeSubProcesoDTO [idInstanciaDeTarea=" + idInstanciaDeTarea + ", idUsuarioSeleccionado="
				+ idUsuarioSeleccionado + ", comentario=" + comentario + "]";
	}	
}

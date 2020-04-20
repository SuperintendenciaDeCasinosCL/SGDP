package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO implements Serializable {


	private List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO;
	private String resultado;
		
	public List<UsuarioNotificacionTareaDTO> getUsuariosNotificacionTareaDTO() {
		return usuariosNotificacionTareaDTO;
	}
	public void setUsuariosNotificacionTareaDTO(List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO) {
		this.usuariosNotificacionTareaDTO = usuariosNotificacionTareaDTO;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	@Override
	public String toString() {
		return "RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO [usuariosNotificacionTareaDTO="
				+ usuariosNotificacionTareaDTO + ", resultado=" + resultado + "]";
	}	
}

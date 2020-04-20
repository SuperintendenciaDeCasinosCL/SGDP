package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class RespuestaAsignarNumeroDocumento implements Serializable  {

	private String Respuesta;
	private MultipartFile archivo;
	private Long IdAsignacionNumeroDocEntada;
	
	public RespuestaAsignarNumeroDocumento() {
	}


	public String getRespuesta() {
		return Respuesta;
	}


	public void setRespuesta(String respuesta) {
		Respuesta = respuesta;
	}


	public MultipartFile getArchivo() {
		return archivo;
	}


	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}


	public Long getIdAsignacionNumeroDocEntada() {
		return IdAsignacionNumeroDocEntada;
	}


	public void setIdAsignacionNumeroDocEntada(Long idAsignacionNumeroDocEntada) {
		IdAsignacionNumeroDocEntada = idAsignacionNumeroDocEntada;
	}
	
	
	
}

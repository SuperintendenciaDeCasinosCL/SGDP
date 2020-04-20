package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class RespuestaActualizaMetadataDeDocumento implements Serializable {

	private String respuesta;
	private boolean esVisable;
	private boolean aplicaFEA;
	private boolean aplicaFirmaApplet;
	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public boolean getEsVisable() {
		return esVisable;
	}
	public void setEsVisable(boolean esVisable) {
		this.esVisable = esVisable;
	}
	public boolean getAplicaFEA() {
		return aplicaFEA;
	}
	public void setAplicaFEA(boolean aplicaFEA) {
		this.aplicaFEA = aplicaFEA;
	}
	public boolean getAplicaFirmaApplet() {
		return aplicaFirmaApplet;
	}
	public void setAplicaFirmaApplet(boolean aplicaFirmaApplet) {
		this.aplicaFirmaApplet = aplicaFirmaApplet;
	}
	@Override
	public String toString() {
		return "RespuestaActualizaMetadataDeDocumento [respuesta=" + respuesta
				+ ", esVisable=" + esVisable + ", aplicaFEA=" + aplicaFEA
				+ ", aplicaFirmaApplet=" + aplicaFirmaApplet + "]";
	}
	
}

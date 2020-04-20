package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class AvanzaEstadoRestDTO implements Serializable  {

	private String idExpediente;

	private String emisor;
	private String passwordEmisor;
	
	// Se colocan los usuarios separados por una coma (,)
	private String receptor;
	private String comentario;
	
	private String idUsuarioMueve;
	private boolean tareasEnEspera;
	
	public AvanzaEstadoRestDTO() {
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public String getPasswordEmisor() {
		return passwordEmisor;
	}

	public void setPasswordEmisor(String passwordEmisor) {
		this.passwordEmisor = passwordEmisor;
	}	
	
	public String getIdUsuarioMueve() {
		return idUsuarioMueve;
	}

	public void setIdUsuarioMueve(String idUsuarioMueve) {
		this.idUsuarioMueve = idUsuarioMueve;
	}	

	public boolean getTareasEnEspera() {
		return tareasEnEspera;
	}

	public void setTareasEnEspera(boolean tareasEnEspera) {
		this.tareasEnEspera = tareasEnEspera;
	}

	@Override
	public String toString() {
		return "AvanzaEstadoRestDTO [idExpediente=" + idExpediente 
				+ ", emisor=" + emisor 
				+ ", receptor=" + receptor
				+ ", comentario=" + comentario
				+ ", idUsuarioMueve=" + idUsuarioMueve
				+ ", tareasEnEspera=" + tareasEnEspera 
				+ "]";
	}

	
}	

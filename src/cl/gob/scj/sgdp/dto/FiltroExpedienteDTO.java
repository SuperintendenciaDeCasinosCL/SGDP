package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class FiltroExpedienteDTO implements Serializable  {

	private boolean respuestaEspera;
	private boolean trabajoInterno;
	
	
	public FiltroExpedienteDTO() {
		super();
	}


	public boolean getRespuestaEspera() {
		return respuestaEspera;
	}


	public void setRespuestaEspera(boolean respuestaEspera) {
		this.respuestaEspera = respuestaEspera;
	}


	public boolean getTrabajoInterno() {
		return trabajoInterno;
	}


	public void setTrabajoInterno(boolean trabajoInterno) {
		this.trabajoInterno = trabajoInterno;
	}
	
	
	
	
}

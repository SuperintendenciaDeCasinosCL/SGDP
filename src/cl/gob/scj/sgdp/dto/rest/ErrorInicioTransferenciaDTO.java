package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class ErrorInicioTransferenciaDTO implements Serializable {

	private long codigo;
	private String mensaje;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "ErrorInicioTransferenciaDTO [codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}

}

package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class CustomMetadata implements Serializable {

	private String llave;
	private String valor;

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}

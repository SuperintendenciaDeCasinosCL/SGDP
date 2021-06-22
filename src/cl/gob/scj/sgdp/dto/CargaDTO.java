package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class CargaDTO implements Serializable {

	private Long idCarga;
	private String idTransferencia;

	public Long getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(Long idCarga) {
		this.idCarga = idCarga;
	}

	public String getIdTransferencia() {
		return idTransferencia;
	}

	public void setIdTransferencia(String idTransferencia) {
		this.idTransferencia = idTransferencia;
	}
}

package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import cl.gob.scj.sgdp.dto.UuidCarpeta;

public class InicioTransferenciaResponse implements Serializable {

	private List<UuidCarpeta> uuidTrans;
	private List<ErrorInicioTransferenciaDTO> errores;

	public List<ErrorInicioTransferenciaDTO> getErrores() {
		return errores;
	}

	public void setErrores(List<ErrorInicioTransferenciaDTO> errores) {
		this.errores = errores;
	}

	public List<UuidCarpeta> getUuidTrans() {
		return uuidTrans;
	}

	public void setUuidTrans(List<UuidCarpeta> uuidTrans) {
		this.uuidTrans = uuidTrans;
	}

	



}

package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.dto.rest.AsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.model.AsignacionesNumerosDoc;

public interface AsignacionNumeroDocDao {

	public String guardarAsignacionDocumento(AsignacionesNumerosDoc asignacionesNumerosDoc);
	public AsignacionesNumerosDoc buscarIdNumeroDocumentoReservadoNoUtilizado(AsignacionesNumerosDocDto asignacionesNumerosDocDto);
	public AsignacionesNumerosDoc buscarIdNumeroDocumentoFirmado(AsignacionesNumerosDocDto asignacionesNumerosDocDto);
	public AsignacionesNumerosDoc getAsignacionesNumerosDocPorId(long idAsignacionNumeroDoc);
	public AsignacionesNumerosDoc buscarAsignacionDocumentoPorId(AsignacionesNumerosDocDto asignacionesNumerosDocDto);
}

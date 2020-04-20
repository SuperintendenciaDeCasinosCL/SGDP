package cl.gob.scj.sgdp.ws.rest.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.rest.AsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.dto.rest.RespuestaAsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;

@Service
public interface AsignacionNumeroDocService {
	
	public RespuestaAsignacionesNumerosDocDto guardarAsignacionDocumento(AsignacionesNumerosDocDto asignacionesNumerosDocDto);
	public String buscarDocumentoAsignar(AsignacionesNumerosDocDto asignacionesNumerosDocDto);
	public RespuestaAsignacionesNumerosDocDto modicarAsignacionDocumento(AsignacionesNumerosDocDto asignacionesNumerosDocDto) throws SgdpException, SgdpExceptionValidaTareaEnBE;
	public RespuestaAsignacionesNumerosDocDto cambiarStatusFirmaAvanzada(AsignacionesNumerosDocDto asignacionesNumerosDocDto) throws SgdpException, SgdpExceptionValidaTareaEnBE;

}


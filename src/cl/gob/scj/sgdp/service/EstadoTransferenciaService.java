package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ResultadoBusquedaEstadoTransferenciaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface EstadoTransferenciaService {

	ResultadoBusquedaEstadoTransferenciaDTO getResultadoBusquedaEstadoTransferencia() throws ArchivoNacionalException;

}

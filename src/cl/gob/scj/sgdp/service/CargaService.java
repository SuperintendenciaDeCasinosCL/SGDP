package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.CargaDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.EstadoTransferenciaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface CargaService {

	CargaDTO guardarCargaArchivoNacional(EnviarArchivoNacionalDTO dto, long cantidadArchivos, String idTransferencia)
			throws ArchivoNacionalException;

	List<EstadoTransferenciaDTO> getResultadoBusquedaEstadoTransferencia() throws ArchivoNacionalException;

}

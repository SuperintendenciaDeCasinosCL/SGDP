package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.BuscarAcuerdoDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaAcuerdoDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface AcuerdoDeTransferenciaService {

	ResultadoBusquedaAcuerdoDTO getResultadoBusquedaAcuerdo(BuscarAcuerdoDTO buscarDTO, boolean all) throws ArchivoNacionalException;


}

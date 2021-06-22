package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.BuscarTablaRetencionDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaTablaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface TablaRetencionService {

	String getCodigoInstitucion();

	ResultadoBusquedaTablaDTO getResultadoDeBusquedaTablaRetencion(BuscarTablaRetencionDTO buscarDTO) throws ArchivoNacionalException;



}

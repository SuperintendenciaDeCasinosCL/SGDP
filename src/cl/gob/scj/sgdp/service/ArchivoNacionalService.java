package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AcuerdoTransferenciaDTO;
import cl.gob.scj.sgdp.dto.BuscarAcuerdoDTO;
import cl.gob.scj.sgdp.dto.BuscarEnvioArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.BuscarTablaRetencionDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.RespuestaEnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.RespuestaEnvioArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.SerieDTO;
import cl.gob.scj.sgdp.dto.TablaRetencionDTO;
import cl.gob.scj.sgdp.dto.TokenDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface ArchivoNacionalService {

	TokenDTO login() throws ArchivoNacionalException;

	List<SerieDTO> getSeries(TokenDTO token) throws ArchivoNacionalException;

	List<AcuerdoTransferenciaDTO> getAcuerdosDeTransferencia(TokenDTO token, BuscarAcuerdoDTO buscarDTO)
			throws ArchivoNacionalException;

	List<TablaRetencionDTO> getResultadoDeBusquedaTablaRetencion(TokenDTO token, BuscarTablaRetencionDTO buscarDTO)
			throws ArchivoNacionalException;

	RespuestaEnvioArchivoNacionalDTO getResultadoBusquedaEnvioArchivoNacional(BuscarEnvioArchivoNacionalDTO buscarEnvioArchivoNacionalDTO) throws ArchivoNacionalException;

	RespuestaEnviarArchivoNacionalDTO getInicioTransferencia(EnviarArchivoNacionalDTO enviarArchivoNacionalDTO, Usuario usuario) throws ArchivoNacionalException;

}

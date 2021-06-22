package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AccesoArchivoNacionalDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface AccesoTransferenciaService {



	String guardarAccesoArchivoNacional(AccesoArchivoNacionalDTO accesoArchivoNacionalDTO, Usuario usuario)
			throws SgdpException;

	String guardarConfigracionInstitucion(AccesoArchivoNacionalDTO accesoArchivoNacionalDTO, Usuario usuario)
			throws SgdpException;

}

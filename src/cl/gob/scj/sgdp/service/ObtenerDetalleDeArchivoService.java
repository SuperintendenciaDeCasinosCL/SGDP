package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface ObtenerDetalleDeArchivoService {

	public DetalleDeArchivoDTO obtenerDetalleDeArchivo(Usuario usuario, String idArchivo) throws SgdpException;
	
}

package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface GestorDeTagsService {
	
	public List<String> obtenerListaDeTags(Usuario usuario) throws SgdpException;	
	
	public void agregaRemueveTagsDeObjeto(Usuario usuario, SubirArhivoDTO subirArhivoDTO, String accion) throws SgdpException;	

}

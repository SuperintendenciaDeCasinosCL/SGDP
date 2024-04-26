package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.TipoDeDestinatarioDTO;

@Service
public interface TipoDeDestinatarioService {
	
	List<TipoDeDestinatarioDTO> getAllTipoDeDestinatario();
	
	TipoDeDestinatarioDTO getTipoDeDestinatarioById(long idTipoDestinatario);
	
	void insertaTipoDeDestinatario(TipoDeDestinatarioDTO tipoDeDestinatarioDTO, Usuario usuario);
	
	void borraTipoDeDestinatario(TipoDeDestinatarioDTO tipoDeDestinatarioDTO, Usuario usuario);
	
	void actualizaTipoDeDestinatario(TipoDeDestinatarioDTO tipoDeDestinatarioDTO, Usuario usuario);

}
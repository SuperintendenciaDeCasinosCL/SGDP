package cl.gob.scj.sgdp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaListaDistribucionMasivaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface ListaDeDistribucionService {
	
	List<ListaDeDistribucionDTO> getListaDeDistribucion(boolean filtraVigentes);
	
	void borrarRegistroDeListaDeDistribucion(long idListaDeDistribucion, String motivo, Usuario usuario) throws JsonProcessingException;
	
	public ListaDeDistribucionDTO getRegistroDeListaDeDistribucionPorId(long idListaDeDistribucion);
	
	void actualizaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO, Usuario usuario) throws JsonProcessingException, SgdpException;
	
	void creaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO, Usuario usuario) throws JsonProcessingException, IOException, SgdpException;
	
	List<ListaDeDistribucionDTO> getListaDistribucionPorIdTipoDestinatario(long idTipoDestinatario);
	
	RespuestaCargaListaDistribucionMasivaDTO cargaListaDistribucionMasivo(ListaDeDistribucionDTO listaDeDistribucionDTO, Usuario usuario) throws Exception, SgdpException;

}

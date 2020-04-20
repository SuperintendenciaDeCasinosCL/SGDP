package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;

@Service
public interface ListaDeDistribucionService {
	
	List<ListaDeDistribucionDTO> getListaDeDistribucion();
	
	void borrarRegistroDeListaDeDistribucion(long idListaDeDistribucion);
	
	public ListaDeDistribucionDTO getRegistroDeListaDeDistribucionPorId(long idListaDeDistribucion);
	
	void actualizaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO);
	
	void creaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO);

}

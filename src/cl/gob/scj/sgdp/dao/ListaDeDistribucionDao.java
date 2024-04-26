package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.DestinatarioGrupoDTO;
import cl.gob.scj.sgdp.model.ListaDeDistribucion;

public interface ListaDeDistribucionDao extends GenericDao<ListaDeDistribucion> {	
	
	List<ListaDeDistribucion> getListaDistribucionPorIdTipoDestinatario(long idTipoDestinatario);
	
	List<DestinatarioGrupoDTO> getListaDistribucionGrupo();
	
	List<ListaDeDistribucion> getListaDistribucionPorEmail(String email);
	
	List<ListaDeDistribucion> getListaDistribucionPorEmailIdDistinto(String email, long idListaDeDistribucion);


}

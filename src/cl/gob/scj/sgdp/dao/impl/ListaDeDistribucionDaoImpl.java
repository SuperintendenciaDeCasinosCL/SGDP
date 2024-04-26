package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ListaDeDistribucionDao;
import cl.gob.scj.sgdp.dto.DestinatarioGrupoDTO;
import cl.gob.scj.sgdp.model.ListaDeDistribucion;

@Repository
public class ListaDeDistribucionDaoImpl extends GenericDaoImpl<ListaDeDistribucion> implements ListaDeDistribucionDao {

	@Override
	public List<ListaDeDistribucion> getListaDistribucionPorIdTipoDestinatario(long idTipoDestinatario) {
		Query query = getSession().getNamedQuery("ListaDeDistribucion.getListaDistribucionPorIdTipoDestinatario");
		query.setLong("idTipoDestinatario", idTipoDestinatario);
		return query.list();
	}

	@Override
	public List<DestinatarioGrupoDTO> getListaDistribucionGrupo() {
		Query query = getSession().getNamedQuery("ListaDeDistribucion.getListaDistribucionGrupo");
		
		return query.list();
	}
	
	@Override
	public List<ListaDeDistribucion> getListaDistribucionPorEmail(String email) {
		Query query = getSession().getNamedQuery("ListaDeDistribucion.getListaDistribucionPorEmail");
		query.setString("email", email);
		return query.list();
	}
	
	@Override
	public List<ListaDeDistribucion> getListaDistribucionPorEmailIdDistinto(String email, long idListaDeDistribucion) {
		Query query = getSession().getNamedQuery("ListaDeDistribucion.getListaDistribucionPorEmailIdDistinto");
		query.setString("email", email);
		query.setLong("idListaDeDistribucion", idListaDeDistribucion);
		return query.list();
	}


}
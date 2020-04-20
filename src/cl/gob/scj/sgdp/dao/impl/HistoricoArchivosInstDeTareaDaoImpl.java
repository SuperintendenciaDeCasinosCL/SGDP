package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoArchivosInstDeTarea;

@Repository
public class HistoricoArchivosInstDeTareaDaoImpl implements
		HistoricoArchivosInstDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertArchivosHistInstDeTarea(
			HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea, Usuario usuario) {
		getSession().save(historicoArchivosInstDeTarea);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoArchivosInstDeTarea> getHistoricoDeArchivosPorIdInstanciaDeTareaIdUsuario(
			long idInstanciaDeTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("HistoricoArchivosInstDeTarea.getHistoricoDeArchivosPorIdInstanciaDeTareaIdUsuario");
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);			
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaHistoricoOrigen(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTareaHistoricoOrigen");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaHistoricoDestino(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTareaHistoricoDestino");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpedienteHistoricoOrigen(String idExpediente) {
		Query query = getSession().getNamedQuery("HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdExpedienteHistoricoOrigen");		
		query.setString("idExpediente", idExpediente);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpedienteHistoricoDestino(String idExpediente) {
		Query query = getSession().getNamedQuery("HistoricoArchivosInstDeTarea.getTodosLosDocSubidosPorIdExpedienteHistoricoDestino");		
		query.setString("idExpediente", idExpediente);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();
	}*/
	
}

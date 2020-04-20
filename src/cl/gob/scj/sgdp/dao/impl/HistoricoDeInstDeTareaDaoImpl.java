package cl.gob.scj.sgdp.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;

@Repository
public class HistoricoDeInstDeTareaDaoImpl implements HistoricoDeInstDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorId(
			long idHistoricoDeInstDeTarea) {
		return (HistoricoDeInstDeTarea) getSession().get(HistoricoDeInstDeTarea.class, idHistoricoDeInstDeTarea);
	}
	
	@Override
	public void insertHistoricoDeInstDeTarea(HistoricoDeInstDeTarea historicoDeInstDeTarea, Usuario usuario) { 
		getSession().save(historicoDeInstDeTarea);		
	}

	@Override
	public HistoricoDeInstDeTarea getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino(InstanciaDeTarea instanciaDeTareaDeDestino) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino");
		query.setLong("idInstanciaDeTarea", instanciaDeTareaDeDestino.getIdInstanciaDeTarea());
		return (HistoricoDeInstDeTarea) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoDeInstDeTarea> getHistorialDeTareasPorIdIntanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoDeInstDeTarea> getHistoricoDeInstDeTareaPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdExpediente");	
		query.setString("idExpediente", idExpediente);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoDeInstDeTareaDTO> getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(String idExpediente) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda");		
		query.setString("idExpediente", idExpediente);
        query.setResultTransformer(Transformers.aliasToBean(HistoricoDeInstDeTareaDTO.class));
		return query.list();
	}
	
	@Override
	public HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return (HistoricoDeInstDeTarea) query.uniqueResult();	
	}
			
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoDeInstDeTarea> getTodosEnviaDevuelveReasignaHistoricoDeInstDeTareaPorIdInstanciaDeProceso(long idInstanciaDeProceso) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getTodosEnviaDevuelveReasignaHistoricoDeInstDeTareaPorIdInstanciaDeProceso");		
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);		
		return query.list();
	}
	
	@Override
	public HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelve(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelve");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return (HistoricoDeInstDeTarea) query.uniqueResult();	
	}
	
	@Override
	public HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech(long idInstanciaDeTarea, Date fechaMovimiento) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setTimestamp("fechaMovimiento", fechaMovimiento);
		return (HistoricoDeInstDeTarea) query.uniqueResult();	
	}
	
	@Override
	public HistoricoDeInstDeTarea getHisDeInstDeTareaPorIdInstDeTareaDeDestIdUsrOrigenMaxFechaMov(long idInstanciaDeTarea, String idUsuarioOrigen) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHisDeInstDeTareaPorIdInstDeTareaDeDestIdUsrOrigenMaxFechaMov");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setString("idUsuarioOrigen", idUsuarioOrigen);
		return (HistoricoDeInstDeTarea) query.uniqueResult();	
	}
	
	@Override
	public HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestinoMaxFechaMovimiento(long idInstanciaDeTareaOrigen, long idInstanciaDeTareaDestino) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestinoMaxFechaMovimiento");		
		query.setLong("idInstanciaDeTareaOrigen", idInstanciaDeTareaOrigen);
		query.setLong("idInstanciaDeTareaDestino", idInstanciaDeTareaDestino);	
		return (HistoricoDeInstDeTarea) query.uniqueResult();	
	}
	
	@Override
	public HistoricoDeInstDeTarea getHistDeInstDeTareaPorIdInstaDeTareaDeOrigenMaxFechaMov(long idInstanciaDeTareaOrigen) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistDeInstDeTareaPorIdInstaDeTareaDeOrigenMaxFechaMov");		
		query.setLong("idInstanciaDeTareaOrigen", idInstanciaDeTareaOrigen);
		return (HistoricoDeInstDeTarea) query.uniqueResult();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoDeInstDeTarea> getHistoricosDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestino(long idInstanciaDeTareaOrigen, long idInstanciaDeTareaDestino) {
		Query query = getSession().getNamedQuery("HistoricoDeInstDeTarea.getHistoricosDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestino");		
		query.setLong("idInstanciaDeTareaOrigen", idInstanciaDeTareaOrigen);
		query.setLong("idInstanciaDeTareaDestino", idInstanciaDeTareaDestino);	
		return query.list();
	}
	
}

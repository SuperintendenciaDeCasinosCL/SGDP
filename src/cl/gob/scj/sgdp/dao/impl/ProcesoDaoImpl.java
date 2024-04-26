package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.Tarea;

@Repository
public class ProcesoDaoImpl implements ProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Proceso> getProcesosPorIdUnidadYVigencia(long idUnidad, boolean vigente) {		
		Query query = getSession().getNamedQuery("Proceso.getProcesosPorIdUnidad");
		query.setLong("idUnidad", idUnidad);
		query.setBoolean("vigente", vigente);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proceso> getProcesosPorIdMacroProceso(long idMacroProceso, boolean vigente) {			
		Query query = getSession().getNamedQuery("Proceso.getProcesosPorIdMacroProceso");		
		query.setLong("idMacroProceso", idMacroProceso);
		query.setBoolean("vigente", vigente);
		return query.list();		
	}
	
	@Override
	public Proceso getProcesoPorIdProceso(long idProceso) {	
		return (Proceso) getSession().get(Proceso.class, idProceso);  
	}
	
	@Override
	public Long insertaProceso(Proceso proceso) {
		return (Long) getSession().save(proceso);
	}

	@Override
	public List<Tarea> getTareaPorIdSubProceso(long idSubProceso,
			boolean vigente) {
		Query query = getSession().getNamedQuery("Tarea.getTareaPorIdSubProceso");		
		query.setLong("idSubProceso", idSubProceso);
		query.setBoolean("vigente", vigente);
		return query.list();	
	}

	@Override
	public List<Proceso> getBuscarTodosProcesosVigente(boolean vigente) {
		Query query = getSession().getNamedQuery("Proceso.buscarTodosProcesoPorVigencia");		
		query.setBoolean("vigente", vigente);
		return query.list();
	}
	
	@Override
	public List<String> getBuscarTodosLosNombresdeLosProcesoVigente(boolean vigente) {
		Query query = getSession().getNamedQuery("Proceso.getBuscarTodosLosNombresdeLosProcesoVigente");		
		query.setBoolean("vigente", vigente);
		return query.list();	
	}

	@Override
	public Proceso getProcesoVigentePorCodigoProceso(String codigoProceso) {
		Query query = getSession().getNamedQuery("Proceso.getProcesoVigentePorCodigoProceso");		
		query.setString("codigoProceso", codigoProceso);
		return (Proceso) query.uniqueResult();	
	}
	
	@Override
	public List<Proceso> getTodosProcesos() {
		Query query = getSession().getNamedQuery("Proceso.buscarTodosProceso");	
		return query.list();
	}
	
	@Override
	public List<String> getTodosLosNombresDeProcesos() {
		Query query = getSession().getNamedQuery("Proceso.buscarTodosLosNombresDeProcesos");	
		return query.list();
	}
	
	
	@Override
	public List<Proceso> buscarTodosProcesoVigente(boolean vigente) {
		Query query = getSession().getNamedQuery("Proceso.buscarTodosProcesoVigente");		
		query.setBoolean("vigente", vigente);
		return query.list();
	}
	
	@Override
	public List<Proceso> buscarTodosProcesoVigenteOrderPorCod(boolean vigente) {
		Query query = getSession().getNamedQuery("Proceso.buscarTodosProcesoVigenteOrderPorCod");		
		query.setBoolean("vigente", vigente);
		return query.list();
	}

	@Override
	public Long insertaProceso(Proceso proceso, Session session) {
		return (Long) session.save(proceso);
	}	
	
	@Override
	public Integer deshabilitaProceso(Long idProcesoNuevo, String codigoProceso, Session session) {
		if (session == null ) {
			session = getSession();
		}
		
		Query query = session.getNamedQuery("Proceso.deshabilitaProceso");
		query.setLong("idProceso", idProcesoNuevo);
		query.setString("codigoProceso", codigoProceso);
		return query.executeUpdate();
	}

	@Override
	public List<Proceso> getProcesosPorSuperProceso(long idSuperProceso) {
		Query query = getSession().getNamedQuery("Proceso.findAllBiSuperProceso");		
		query.setLong("idSuperProceso", idSuperProceso);
		return query.list();	
	}
	
}

package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.Tarea;

@Repository
public class TareaDaoImpl implements TareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tarea> getTareasPorVigenciaPorIdProceso(long idProceso, boolean vigente) {
		Query query = getSession().createQuery(
				"from Tarea t where t.proceso.idProceso =:idProceso and t.vigente =:vigente ");
		query.setLong("idProceso", idProceso);
		query.setBoolean("vigente", vigente);
		return query.list();
	}

	@Override
	public Tarea getBuscaDocumentoOficialEnTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("Tarea.getBuscaDocumentoOficialEnTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return (Tarea) query.uniqueResult();
	}

	@Override
	public Tarea getTareaPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("Tarea.getTareaPorIdTarea");
		query.setLong("idTarea", idTarea);
		return (Tarea) query.uniqueResult();
	}
	
	@Override
	public List<Tarea> getTareasPorIdProceso(long idProceso) {
		Query query = getSession().getNamedQuery("Tarea.getTareasPorIdProceso");
		query.setLong("idProceso", idProceso);
		return query.list();
	}
	
	@Override
	public List<Tarea> getTareasPorCodigoProceso(String codigoProceso) {
		Query query = getSession().getNamedQuery("Tarea.getTareasPorCodigoProceso");
		query.setString("codigoProceso", codigoProceso);
		return query.list();
	}
	
	@Override
	public List<Tarea> getSegundasTareasPorIdProceso(long idProceso) {
		Query query = getSession().getNamedQuery("Tarea.getSegundasTareasPorIdProceso");
		query.setLong("idProceso", idProceso);
		return query.list();
	}
	
}

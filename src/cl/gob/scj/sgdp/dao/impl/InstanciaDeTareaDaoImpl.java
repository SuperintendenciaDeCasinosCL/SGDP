package cl.gob.scj.sgdp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dto.HistoricoDeDocumentos;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;

@Repository
public class InstanciaDeTareaDaoImpl implements InstanciaDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareaPorIdUsrNombreEstadoTarea(
			String idUsuario, String nombreEstadoDeTarea) {	
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareaPorIdUsrNombreEstadoTarea");
		query.setString("nombreEstadoDeTarea", nombreEstadoDeTarea);
		query.setString("idUsuario", idUsuario);
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();		
	}	

	@Override
	public void insertInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea, Usuario usuario) { 
		getSession().save(instanciaDeTarea);		
	}

	@Override
	public InstanciaDeTarea getInstanciaDeTareaPorIdInstanciaDeTarea(
			long idInstanciaDeTarea) {
		return (InstanciaDeTarea) getSession().get(InstanciaDeTarea.class, idInstanciaDeTarea);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasEnEjecucion(String idUsuario, long idEstadoFinalizada) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasEnEjecucion");
		query.setLong("idEstadoFinalizada", idEstadoFinalizada);
		query.setString("idUsuario", idUsuario);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getTodasInstanciasDeTareasEnEjecucion() {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getTodasInstanciasDeTareasEnEjecucion");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getNotificacionesSeguimientosPorUsuario(String idUsuario) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getNotificacionesSeguimientosPorUsuario");
		query.setString("idUsuario", idUsuario);
		
		Query query2 = getSession().getNamedQuery("InstanciaDeTarea.getNotificacionesSeguimientosPorUsuarioUnion");
		query2.setString("idUsuario", idUsuario);
		
		List<InstanciaDeTarea> instanciaDeTarea1 = query.list();
		
		List<InstanciaDeTarea> instanciaDeTarea2 = query2.list();
		
		instanciaDeTarea1.addAll(instanciaDeTarea2);
		
		return instanciaDeTarea1;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(long idUnidad) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad");
		query.setLong("idUnidad", idUnidad);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public InstanciaDeTarea getInstanciaDeTareasSiLasAnterioresAsignadasEstanFinalizadas(InstanciaDeTarea instanciaDeTarea) {		
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciaDeTareasSiLasAnterioresAsignadasEstanFinalizadas");		
		query.setLong("idInstanciaDeTarea", instanciaDeTarea.getIdInstanciaDeTarea());		
		return (InstanciaDeTarea) query.uniqueResult();

	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdExpediente");		
		query.setString("idExpediente", idExpediente);		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteAsignadas(String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteAsignadas");		
		query.setString("idExpediente", idExpediente);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteAsignadasEnEspera(String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteAsignadasEnEspera");		
		query.setString("idExpediente", idExpediente);		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoDeDocumentos> buscarHistorialDeDocumento(
			Integer idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.buscarHistorialDeDocumento");		
		query.setInteger("idInstanciaDeTarea", idInstanciaDeTarea);
        query.setResultTransformer(Transformers.aliasToBean(HistoricoDeDocumentos.class));
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public InstanciaDeTarea getPrimeraInstanciaDeTareaPorId(long idInstanciaDeTarea) {		
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getPrimeraInstanciaDeTareaPorId");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return (InstanciaDeTarea) query.uniqueResult();
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasPorIdNoFinalizadasConAntAnd(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdNoFinalizadasConAntAnd");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTareaDTO> getInstanciasDeTareasPorIdExpedienteBusqueda(String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteBusqueda");		
		query.setString("idExpediente", idExpediente);
        query.setResultTransformer(Transformers.aliasToBean(InstanciaDeTareaDTO.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteAsignadasUsuario(
			String idExpediente, String usuario) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteAsignadasUsuario");		
		query.setString("idExpediente", idExpediente);		
		query.setString("idUsuario", usuario);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteUsuarioFinalizada(
			String idExpediente, String usuario) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteUsuarioFinalizada");		
		query.setString("idExpediente", idExpediente);		
		query.setString("idUsuario", usuario);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasAnterioresAsignadasPorIdInstanciaDeProcesoIdInstanciaDeTarea(
			long idInstanciaDeProceso, long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasAnterioresAsignadasPorIdInstanciaDeProcesoIdInstanciaDeTarea");		
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasAsignadasPorIdInstanciaDeProcesoDistintasDeIdInstanciaDeTarea(
			long idInstanciaDeProceso, long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasAsignadasPorIdInstanciaDeProcesoDistintasDeIdInstanciaDeTarea");		
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareaPorIdUsrNombreEstadoTareaPorEsperaRespuesta(
			String idUsuario, String nombreEstadoDeTarea,
			boolean buscarRespuestaEspera) {	
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareaPorIdUsrNombreEstadoTareaPorEsperaRespuesta");
		query.setString("nombreEstadoDeTarea", nombreEstadoDeTarea);
		query.setString("idUsuario", idUsuario);
		query.setBoolean("esperaRespuesta", buscarRespuestaEspera);
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasOrdenUnoPorIdExpedienteAsignadas(
			String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasOrdenUnoPorIdExpedienteAsignadas");		
		query.setString("idExpediente", idExpediente);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> getEstadosDeInstProcPorIdUsrNombreEstadoTarea(String idUsuario, String nombreEstadoDeTarea) {
		Map<Long, String> res = new HashMap<Long, String>();
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getEstadosDeInstProcPorIdUsrNombreEstadoTarea");	
		query.setString("nombreEstadoDeTarea", nombreEstadoDeTarea);
		query.setString("idUsuario", idUsuario);
		List<Object[]> resultado = query.list();
		for (Object[] elemento : resultado) {
			res.put((Long)elemento[0], (String)elemento[1]);
		}
		//query.setResultTransformer(Transformers.aliasToBean(EstadoInstaProcDTO.class));
		//return query.list();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeTarea> getInstanciasDeTareasAnterioresPorIdInstanciaDeTareaDeDestino(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciasDeTareasAnterioresPorIdInstanciaDeTareaDeDestino");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();
	}
	
	public InstanciaDeTarea getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(String idDiagramaTarea, String nombreExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeTarea.getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente");
		query.setString("idDiagramaTarea", idDiagramaTarea);
		query.setString("nombreExpediente", nombreExpediente);
		return (InstanciaDeTarea) query.uniqueResult();
	}
	

}
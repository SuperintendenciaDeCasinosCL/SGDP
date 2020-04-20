package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.VinculacionExpDao;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.VinculacionExp;

@Repository
public class VinculacionExpDaoImpl extends GenericDaoImpl<VinculacionExp> implements VinculacionExpDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<VinculacionExp> getVinculacionExpSucesoresPorIdInstProcAntecesor(long idInstanciaDeProceso) {
		Query query = getSession().getNamedQuery("VinculacionExp.getVinculacionExpSucesoresPorIdInstProcAntecesor");
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);
		List<VinculacionExp> resultado = (List<VinculacionExp>)query.list();
		return resultado;
	}

	@Override
	public List<VinculacionExp> getVinculacionExpPorNombreExp(String nombreExpediente) {
		Query query = getSession().getNamedQuery("VinculacionExp.getVinculacionExpPorIdInstProc");
		query.setString("nombreExpediente", nombreExpediente);
		List<VinculacionExp> resultado = (List<VinculacionExp>)query.list();
		return resultado;
	}
	
	@Override
	public VinculacionExp getVinculacionExpPorIdInstProcIdInstProcAntecesor(long idInstanciaDeProceso, long idInstanciaDeProcesoAntecesor) {
		Query query = getSession().getNamedQuery("VinculacionExp.getVinculacionExpPorIdInstProcIdInstProcAntecesor");		
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);
		query.setLong("idInstanciaDeProcesoAntecesor", idInstanciaDeProcesoAntecesor);
		return (VinculacionExp) query.uniqueResult();	
	}
	
}
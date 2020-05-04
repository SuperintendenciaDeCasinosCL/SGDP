package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.model.TipoDeDocumento;

@Repository
public class TipoDeDocumentoDaoImpl implements TipoDeDocumentoDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoDeDocumento> getTodosLosTiposDeDocumentos() {
		return getSession().createQuery("from TipoDeDocumento").list();
	}
	
	@Override
	public TipoDeDocumento getTipoDeDocumentoPorIdTipoDeDocumento(long idTipoDeDocumento) {	
		return (TipoDeDocumento) getSession().get(TipoDeDocumento.class, idTipoDeDocumento);
	}
	
	@Override
	public TipoDeDocumento getTipoDeDocumentoPorNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTipoDeDocumentoPorNombreDeTipoDeDocumento");	
		query.setString("nombreDeTipoDeDocumento", nombreDeTipoDeDocumento);
		query.setFirstResult(0);
        query.setMaxResults(1);
		return (TipoDeDocumento) query.uniqueResult();
	}
	
	@Override
	public TipoDeDocumento getTipoDeDocumentoPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(String nombreDeTipoDeDocumento, long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTipoDeDocumentoPorNombreDeTipoDeDocumentoIdInstanciaDeTarea");	
		query.setString("nombreDeTipoDeDocumento", nombreDeTipoDeDocumento);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setFirstResult(0);
        query.setMaxResults(1);
		return (TipoDeDocumento) query.uniqueResult();
	}
	
	@Override
	public TipoDeDocumento getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente(String nombreDeTipoDeDocumento, String idExpediente) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente");	
		query.setString("nombreDeTipoDeDocumento", nombreDeTipoDeDocumento);
		query.setString("idExpediente", idExpediente);
		query.setFirstResult(0);
        query.setMaxResults(1);
		return (TipoDeDocumento) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoDeDocumento> getTiposDeDocumentosDeSalidaPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTiposDeDocumentosDeSalidaPorIdInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();
	}
	
	@Override
	public TipoDeDocumento getTipoDeDocumentoPorIdProceso(long idProceso) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTipoDeDocumentosPorIdProceso");	
		query.setLong("idProceso", idProceso);			
		return (TipoDeDocumento) query.uniqueResult();
	}
	
	@Override
	public TipoDeDocumento getTipoDeDocumentoRequeridoPorIdTipoDeDocumentoIdExpediente(long idTipoDeDocumento, String idExpediente) {	
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTipoDeDocumentoRequeridoPorIdTipoDeDocumentoIdExpediente");
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		query.setString("idExpediente", idExpediente);			
		return (TipoDeDocumento) query.uniqueResult();
	}

	@Override
	public List<TipoDeDocumentoDTO> getTipoDeDocumentoPrimeraTareaDocAdiccionales() {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTipoDeDocumentoPrimeraTareaDocAdiccionales");
		return query.list();
	}

	@Override
	public List<String> buscaTodosLosNombreDeLosDocumentos() {
		Query query = getSession().getNamedQuery("TipoDeDocumento.buscaTodosLosNombreDeLosDocumentos");
		return query.list();
	}
	
	@Override
	public List<String> buscaTodosLosNombreDeLosDocumentosSubidos() {
		Query query = getSession().getNamedQuery("TipoDeDocumento.buscaTodosLosNombreDeLosDocumentosSubidos");
		return query.list();
	}

	@Override
	public List<TipoDeDocumento> buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(String codigoProceso) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso");
		query.setString("codigoProceso", codigoProceso);
		return query.list();
	}
	
	@Override
	public List<TipoDeDocumento> getTiposDeDocumentosPorCodigoProceso(String codigoProceso) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTiposDeDocumentosPorCodigoProceso");	
		query.setString("codigoProceso", codigoProceso);			
		return query.list();
	}
	
	@Override
	public List<TipoDeDocumento> getTiposDeDocumentosPorNombreExpediente(String nombreExpediente) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTiposDeDocumentosPorNombreExpediente");	
		query.setString("nombreExpediente", nombreExpediente);			
		return query.list();
	}
	
	@Override
	public List<TipoDeDocumento> getTiposDeDocumentosPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("TipoDeDocumento.getTiposDeDocumentosPorIdExpediente");	
		query.setString("idExpediente", idExpediente);			
		return query.list();
	}


}

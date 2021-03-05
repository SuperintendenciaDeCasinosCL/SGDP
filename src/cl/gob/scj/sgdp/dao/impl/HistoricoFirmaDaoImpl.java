package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.HistoricoFirmaDao;
import cl.gob.scj.sgdp.model.HistoricoFirma;

@Repository
public class HistoricoFirmaDaoImpl implements HistoricoFirmaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}	
	
	@Override
	public void insertaHistoricoFirma(HistoricoFirma historicoFirma) {
		getSession().save(historicoFirma);
	}

	@Override
	public List<String> getListaDocumentosFirmados(String idExpediente) {
		Query query = getSession().getNamedQuery("HistoricoFirma.getListaDocumentosFirmados");	
		query.setString("idExpediente", idExpediente);		
		return query.list();
	}

	@Override
	public List<HistoricoFirma> getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(long idInstanciaDeTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("HistoricoFirma.getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setString("idUsuario", idUsuario);
		return query.list();
	}
	
	@Override
	public List<HistoricoFirma> getHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS) {
		Query query = getSession().getNamedQuery("HistoricoFirma.getHistoricoFirmaDocumentoFEAPorIdArchivo");
		query.setString("idArchivoCMS", idArchivoCMS);
		return query.list();
	}

	@Override
	public HistoricoFirma getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS) {
		Query query = getSession().getNamedQuery("HistoricoFirma.getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo");
		query.setString("idArchivoCMS", idArchivoCMS);
		return (HistoricoFirma) query.uniqueResult();
	}
	
	@Override
	public long getIdDocumentoFirmado() {
		SQLQuery query = getSession().createSQLQuery("select nextval('sgdp.\"SEQ_ID_DOCUMENTO_FIRMADO\"')");
		return  ((java.math.BigInteger) query.uniqueResult()).longValue();
	}	
	
	@Override
	public HistoricoFirma getHistoricoFirmaPorIdDocumentoFirmado(Long idDocumentoFirmado) {
		Query query = getSession().getNamedQuery("HistoricoFirma.getHistoricoFirmaPorIdDocumentoFirmado");
		query.setLong("idDocumentoFirmado", idDocumentoFirmado);
		return (HistoricoFirma) query.uniqueResult();	
	}
	
	@Override
	public List<HistoricoFirma> getHistoricoFirmaPorIdTipoDocumentoIdInstanciaDeTareaIdUsuario(Long idTipoDeDocumento, Long idInstanciaDeTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("HistoricoFirma.getHistoricoFirmaPorIdTipoDocumentoIdInstanciaDeTareaIdUsuario");
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setString("idUsuario", idUsuario);
		return query.list();
	}
	
}

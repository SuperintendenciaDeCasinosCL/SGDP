package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;

import org.springframework.stereotype.Repository;
import cl.gob.scj.sgdp.dao.TablaRetencionDao;
import cl.gob.scj.sgdp.model.TablaRetencion;

@Repository
public class TablaRetencionDaoImpl extends GenericDaoImpl<TablaRetencion> implements TablaRetencionDao {
	
	public void update(TablaRetencion tr) {
		Query query = getSession().createQuery("update TablaRetencion set aniosRetencion = :aniosRetencion, transferibleArchivo = :transferibleArchivo where tipoDocumento.idTipoDeDocumento = :idTipoDeDocumento");
		query.setInteger("aniosRetencion", tr.getAniosRetencion());
		query.setBoolean("transferibleArchivo", tr.getTransferibleArchivo());
		query.setLong("idTipoDeDocumento", tr.getTipoDocumento().getIdTipoDeDocumento());
		query.executeUpdate();
	}
	
	public void deleteBySerieDocumental(long idSerieDocumental) {
		Query query = getSession().createQuery("delete from TablaRetencion tr where tr.serieDocumental.idSerieDocumental = :idSerieDocumental");
		query.setLong("idSerieDocumental", idSerieDocumental);
		query.executeUpdate();	
	}
	
	@Override
	public List<TablaRetencion> getTablasDeRetencion(Long idTipoDocumento) {
		Query query = getSession().getNamedQuery("TablaRetencion.findAllByIdTipoDocumento");
		query.setLong("idTipoDocumento", idTipoDocumento);
		return query.list();	
	}
	
	@Override
	public TablaRetencion findByIdTipoDocumentoAndIdSerieDocumental(Long idTipoDocumento, Long idSerieDocumental) {
		Query query = getSession().getNamedQuery("TablaRetencion.findByIdTipoDocumentoAndIdSerieDocumental");
		query.setLong("idTipoDocumento", idTipoDocumento);
		query.setLong("idSerieDocumental", idSerieDocumental);
		return (TablaRetencion) query.uniqueResult();
	}

	
}

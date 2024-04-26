package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.DocumentoDeSalidaDeTareaDao;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTareaPK;


@Repository
public class DocumentoDeSalidaDeTareaDaoImpl implements DocumentoDeSalidaDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<DocumentoDeSalidaDeTarea> getByIdProceso(long idProceso) {
		Query query = getSession().getNamedQuery("DocumentoDeSalidaDeTarea.findAllByIdProceso");	
		query.setLong("idProceso", idProceso);
		return query.list();
	}


	@Override
	public int updatePlantilla(Long idPlantilla, Long idTipoDeDocumento) {
		Query query = getSession().getNamedQuery("DocumentoDeSalidaDeTarea.updatePlantilla");	
		query.setLong("plantilla", idPlantilla);
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		return query.executeUpdate();
	}

	@Override
	public DocumentoDeSalidaDeTarea getByIdDocumentoSalida(Long idTipoDeDocumento, Long idTarea) {
		Query query = getSession().getNamedQuery("DocumentoDeSalidaDeTarea.findByIdTipoDocumento");
		query.setMaxResults(1);
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		query.setLong("idTarea", idTarea);
		return (DocumentoDeSalidaDeTarea) query.uniqueResult();
	}

	@Override
	public DocumentoDeSalidaDeTarea updatePlantilla(DocumentoDeSalidaDeTarea docSalida) {
		getSession().saveOrUpdate(docSalida);
		return docSalida;
	}
	
}

package cl.gob.scj.sgdp.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.PlantillaDeDocumentoDao;
import cl.gob.scj.sgdp.dto.PlantillaDTO;
import cl.gob.scj.sgdp.model.PlantillaDeDocumento;

@Repository
public class PlantillaDeDocumentoDaoImpl implements PlantillaDeDocumentoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<PlantillaDeDocumento> getTodasLasPlantillas() {
		Query query = getSession().getNamedQuery("PlantillaDeDocumento.findAll");				
		return query.list();
	}

	@Override
	public Integer actualizaVigencia(String codige) {
		Query query = getSession().getNamedQuery("PlantillaDeDocumento.cancelaVigenciaPorCodigo");
		query.setString("codigo", codige);
		return query.executeUpdate();
	}

	@Override
	public Serializable guardaPlantilla(PlantillaDeDocumento plantilla) {
		return getSession().save(plantilla);
	}

	@Override
	public PlantillaDeDocumento getPlantillaPorIdTipoDeDocumento(Long idTipoDeDocumento) {
		Query query = getSession().getNamedQuery("PlantillaDeDocumento.getPlantillaPorIdTipoDocumento");
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		return (PlantillaDeDocumento) query.uniqueResult();
	}
	
	

}
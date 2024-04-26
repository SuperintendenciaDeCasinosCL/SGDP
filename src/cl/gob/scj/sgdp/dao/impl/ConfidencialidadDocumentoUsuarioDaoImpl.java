package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ConfidencialidadDocumentoUsuarioDao;
import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoUsuario;

@Repository
public class ConfidencialidadDocumentoUsuarioDaoImpl implements ConfidencialidadDocumentoUsuarioDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfidencialidadDocumentoUsuario> getUsuariosAsignados(String id) {
		Query query = getSession().getNamedQuery("CONFIDENCIALIDAD_DOCUMENTO_USUARIO.findByIdDocumento");
		query.setString("idDocumento", id);
		return (List<ConfidencialidadDocumentoUsuario>) query.list();
	}

	@Override
	public boolean save(ConfidencialidadDocumentoUsuario cdu) {
		getSession().save(cdu);
		return true;
	}

	@Override
	public void eliminar(String id) {
		Query query = getSession().getNamedQuery("CONFIDENCIALIDAD_DOCUMENTO_USUARIO.deleteByIdDocumento");
		query.setString("idDocumento", id);
		query.executeUpdate();
		
	}
	
	

}
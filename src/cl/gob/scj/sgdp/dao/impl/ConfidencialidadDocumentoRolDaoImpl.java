package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ConfidencialidadDocumentoRolDao;
import cl.gob.scj.sgdp.dao.ConfidencialidadDocumentoUsuarioDao;
import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoRol;
import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoUsuario;

@Repository
public class ConfidencialidadDocumentoRolDaoImpl implements ConfidencialidadDocumentoRolDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfidencialidadDocumentoRol> getRolesAsignados(String id) {
		Query query = getSession().getNamedQuery("CONFIDENCIALIDAD_DOCUMENTO_ROL.findByIdDocumento");
		query.setString("idDocumento", id);
		return (List<ConfidencialidadDocumentoRol>) query.list();
	}

	@Override
	public boolean save(ConfidencialidadDocumentoRol cdr) {
		getSession().save(cdr);
		return true;
	}

	@Override
	public void eliminar(String id) {
		Query query = getSession().getNamedQuery("CONFIDENCIALIDAD_DOCUMENTO_ROL.deleteIdDocumento");
		query.setString("idDocumento", id);
		query.executeUpdate();
	}
	
	
	
	

}
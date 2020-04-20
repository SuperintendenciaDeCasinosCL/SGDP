package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.UsuarioNotificacionTareaDao;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;

@Repository
public class UsuarioNotificacionTareaDaoImpl implements UsuarioNotificacionTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea) {
		getSession().save(usuarioNotificacionTarea);	
	}
	
	
	public void eliminarUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea) {
		getSession().delete(usuarioNotificacionTarea);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioNotificacionTarea> getUsuariosNotificacionTareaPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("UsuarioNotificacionTarea.getUsuariosNotificacionTareaPorIdTarea");
		query.setLong("idTarea", idTarea);	
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioNotificacionTarea getUsuariosNotificacionTareaPorIdTareaYUsuario(long idTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("UsuarioNotificacionTarea.getUsuariosNotificacionTareaPorIdTareaYUsuario");
		query.setLong("idTarea", idTarea);	
		query.setString("idUsuario", idUsuario);
		return (UsuarioNotificacionTarea) query.uniqueResult();
	}
	
	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioNotificacionTareaDTO> getUsuariosNotificacionTareaPorIdTareaSQL(long idTarea) {
		SQLQuery query = getSession().createSQLQuery("SELECT DISTINCT l.\"D_FECHA_CREACION\" as dFechaCreacion, n.\"ID_USUARIO\" as idUsuario , l.\"ID_TAREA\" as idTarea "
				+ " FROM \"SGDP_USUARIO_NOTIFICACION_TAREA\" l RIGHT join \"SGDP_USUARIOS_ROLES\" n on l.\"ID_USUARIO\" = n.\"ID_USUARIO\" " 
				+ " WHERE (l.\"ID_TAREA\" = :idTarea or l.\"ID_TAREA\" is null) AND n.\"B_ACTIVO\" = true ");				
		query.addScalar("dFechaCreacion", StandardBasicTypes.DATE);
		query.addScalar("idUsuario", StandardBasicTypes.STRING);
		query.addScalar("idTarea", StandardBasicTypes.LONG);
		query.setResultTransformer(Transformers.aliasToBean(UsuarioNotificacionTareaDTO.class));
		query.setLong("idTarea", idTarea);
		return query.list();
	}*/

}

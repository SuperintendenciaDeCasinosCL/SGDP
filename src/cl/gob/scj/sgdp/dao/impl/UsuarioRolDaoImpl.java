package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.UsuarioRolDao;
import cl.gob.scj.sgdp.model.LogFueraDeOficina;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.model.VinculacionExp;

@Repository
public class UsuarioRolDaoImpl extends GenericDaoImpl<UsuarioRol> implements UsuarioRolDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuarioRolesPorIdUsuario(String idUsuario, boolean activo) {
		Query query = getSession().createQuery(
				"from UsuarioRol r where r.idUsuario =:idUsuario and r.activo =:activo");
		query.setString("idUsuario", idUsuario);
		query.setBoolean("activo", activo);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuarioRolesPorIdUsuarioTarea(String idUsuario, Tarea tarea, boolean activo) {
		Query query = getSession().createQuery("select r from UsuarioRol r, TareaRol tr where r.idUsuario =:idUsuario "
				+ "and r.rol.idRol = tr.id.rol.idRol and tr.id.tarea.idTarea =:idTarea and r.activo =:activo");
		query.setString("idUsuario", idUsuario);
		query.setLong("idTarea", tarea.getIdTarea());
		query.setBoolean("activo", activo);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuarioRolesPorIdRolIdUnidad(long idRol, long idUnidad, boolean activo) {
		Query query = getSession().createQuery(
				"from UsuarioRol r where r.rol.idRol =:idRol and r.unidad.idUnidad =:idUnidad and r.activo =:activo");
		query.setLong("idRol", idRol);
		query.setLong("idUnidad", idUnidad);
		query.setBoolean("activo", activo);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuariosRolesPorIdRol(long idRol, boolean activo) {
		Query query = getSession().createQuery(
				"from UsuarioRol r where r.rol.idRol =:idRol and r.activo =:activo");
		query.setLong("idRol", idRol);		
		query.setBoolean("activo", activo);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuariosRolesPorIdUsuarioLike(String idUsuario, boolean activo) {
		Query query = getSession().createQuery(
				"from UsuarioRol r where r.idUsuario like :idUsuario and r.activo =:activo");
		query.setString("idUsuario", idUsuario + "%");
		query.setBoolean("activo", activo);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuariosRolesPorTareaIdRol(Tarea tarea, boolean activo, int ordenTareaRol, long idRol) {
		Query query = getSession().createQuery(
				"select ur from UsuarioRol ur, TareaRol tr where ur.rol = tr.id.rol and ur.rol.idRol =:idRol"
				+ " and tr.id.tarea.idTarea =:idTarea and tr.orden =:ordenTareaRol and ur.activo =:activo");
		query.setLong("idRol", idRol);
		query.setLong("idTarea", tarea.getIdTarea());
		query.setInteger("ordenTareaRol", ordenTareaRol);
		query.setBoolean("activo", activo);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("UsuarioRol.getUsuariosRolesPosiblesPorIdInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();
	}
	
	@Override
	public UsuarioRol getUsuarioRolPorIdUsuarioIdRol(String idUsuario, long idRol) {
		Query query = getSession().getNamedQuery("UsuarioRol.getUsuarioRolPorIdUsuarioIdRol");
		query.setString("idUsuario", idUsuario);
		query.setLong("idRol", idRol);
		return (UsuarioRol) query.uniqueResult();
	}
	
	@Override
	public void insertLogFueraDeOficina(LogFueraDeOficina logFueraDeOficina) { 
		getSession().save(logFueraDeOficina);		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTodosLosIdUsuariosPorVigencia(boolean vigente) {
		Query query = getSession().getNamedQuery("UsuarioRol.getTodosLosIdUsuariosPorVigencia");
		query.setBoolean("vigente", vigente);
		List<String> resultado = (List<String>)query.list();
		return resultado;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTodosLosIdUsuariosPorVigenciaOrdenadoPorUnidadYNombre(boolean vigente) {
		SQLQuery query = getSession().createSQLQuery("SELECT po.\"ID_USUARIO\" as idUsuario FROM "
				+ "( SELECT DISTINCT ur.\"ID_UNIDAD\" , \"ID_USUARIO\" FROM sgdp.\"SGDP_USUARIOS_ROLES\" ur WHERE ur.\"B_ACTIVO\" = :vigente  ) po "
				+ "ORDER BY po.\"ID_UNIDAD\" , po.\"ID_USUARIO\" asc ");
		query.addScalar("idUsuario", StandardBasicTypes.STRING);
		query.setBoolean("vigente", vigente);
		List<String> resultado = (List<String>)query.list();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTodosLosIdUsuariosPorNombreRol(String nombreRol) {
		Query query = getSession().getNamedQuery("UsuarioRol.getTodosLosIdUsuariosPorNombreRol");
		query.setString("nombreRol", nombreRol);
		List<String> resultado = (List<String>)query.list();
		return resultado;
	}


	@Override
	public List<UsuarioRol> getTodosLosUsuarios() {
		Query query = getSession().getNamedQuery("UsuarioRol.getTodosLosUsuarios");
		return query.list();
}


	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> getTodosLosUsuariosAsignadosPorIdRol(Long rolId) {
		Query query = getSession().getNamedQuery("UsuarioRol.getTodosLosUsuariosAsignadosPorIdRol");
		query.setLong("rol", rolId);
		return query.list();
	}

	@Override
	public List<UsuarioRol> getTodosLosUsuariosActivos() {
		Query query = getSession().getNamedQuery("UsuarioRol.getTodosLosIdUsuariosactivos");
		return query.list();
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioRol getUsuarioRolPorIdUsuarioRol(long idUsuarioRol) {
		Query query = getSession().createQuery(
				"from UsuarioRol r where r.idUsuarioRol =:idUsuarioRol");
		query.setLong("idUsuarioRol", idUsuarioRol);
		return (UsuarioRol) query.uniqueResult();
	}
	
}
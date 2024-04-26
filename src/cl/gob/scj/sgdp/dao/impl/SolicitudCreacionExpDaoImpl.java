package cl.gob.scj.sgdp.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.SolicitudCreacionExpDao;
import cl.gob.scj.sgdp.dto.SolicitudCreacionExpDTO;
import cl.gob.scj.sgdp.model.SolicitudCreacionExp;

@Repository
public class SolicitudCreacionExpDaoImpl extends GenericDaoImpl<SolicitudCreacionExp> implements SolicitudCreacionExpDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<SolicitudCreacionExp> getSolicitudesCreaExpSolicitadasPorOAsignadasA(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		Query query = getSession().getNamedQuery("SolicitudCreacionExp.getSolicitudesCreaExpSolicitadasPorOAsignadasA");
		query.setString("idUsuarioSolicitante", solicitudCreacionExpDTO.getIdUsuarioSolicitante());
		query.setString("idUsuarioCreadorExpediente", solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente());
		query.setLong("idEstadoSolicitudCreacionExp", solicitudCreacionExpDTO.getIdEstadoSolicitudCreacionExp());
		List<SolicitudCreacionExp> resultado = (List<SolicitudCreacionExp>)query.list();
		return resultado;
	}

	@Override
	public List<SolicitudCreacionExp> getSolicitudesCreaExpFinalizadas(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		Query query = getSession().getNamedQuery("SolicitudCreacionExp.getSolicitudesCreaExpFinalizadas");
		query.setString("idUsuarioSolicitante", solicitudCreacionExpDTO.getIdUsuarioSolicitante());
		query.setString("idUsuarioCreadorExpediente", solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente());
		query.setFirstResult(solicitudCreacionExpDTO.getPagina()).setMaxResults(solicitudCreacionExpDTO.getTamanoPagina());
		List<SolicitudCreacionExp> resultado = (List<SolicitudCreacionExp>)query.list();
		return resultado;
	}
	
	@Override
	public int getTotalSolicitudesCreaExpFinalizadas(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		Query query = getSession().getNamedQuery("SolicitudCreacionExp.getTotalSolicitudesCreaExpFinalizadas");
		query.setString("idUsuarioSolicitante", solicitudCreacionExpDTO.getIdUsuarioSolicitante());
		query.setString("idUsuarioCreadorExpediente", solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente());
		Long total = (Long)query.uniqueResult();
		return total.intValue();
	}
	
	@Override
	public List<SolicitudCreacionExp> getSolicitudesCreaExpSolicitadasPorOAsignadasAMultiOficina(SolicitudCreacionExpDTO solicitudCreacionExpDTO, String idUsuario) {
		Query query = getSession().getNamedQuery("SolicitudCreacionExp.getSolicitudesCreaExpSolicitadasPorOAsignadasAMultiOficina");
		query.setString("idUsuarioSolicitante", solicitudCreacionExpDTO.getIdUsuarioSolicitante());
		query.setString("idUsuarioCreadorExpediente", solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente());
		query.setLong("idEstadoSolicitudCreacionExp", solicitudCreacionExpDTO.getIdEstadoSolicitudCreacionExp());
		query.setString("idUsuario", idUsuario);
		List<SolicitudCreacionExp> resultado = (List<SolicitudCreacionExp>)query.list();
		return resultado;
	}
	
}
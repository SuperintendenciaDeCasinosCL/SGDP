package cl.gob.scj.sgdp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.FechaEstadoInstanciaProcesoDTO;
import cl.gob.scj.sgdp.dto.rest.ConsultaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaAvanzadaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaBasicaEstadoProceso;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.EtapaDeInstanciaDeProceso;
import cl.gob.scj.sgdp.model.HistorialProceso;
import cl.gob.scj.sgdp.model.HistoricoSeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;


@Repository
public class InstanciaDeProcesoDaoImpl implements InstanciaDeProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanciaDeProceso> getTodasLasInstanciasDeProcesos() {
		return getSession().createQuery("from InstanciaDeProceso").list();
	}
	
	@Override
	public void insertInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso, Usuario usuario) {
		getSession().save(instanciaDeProceso);
	}

	@Override
	public String getNombreExpediente() {
		SQLQuery query = getSession().createSQLQuery("select nextval('sgdp.\"SEQ_NOMBRE_ID_EXPEDIENTE\"')");
		return String.valueOf((java.math.BigInteger) query.uniqueResult());	
	}	
		
	@SuppressWarnings("unchecked")
	@Override
	public List<HistorialProceso> getHistorialDelProceso(String idExpediente) {
		SQLQuery query = getSession().createSQLQuery("SELECT IT.\"ID_INSTANCIA_DE_TAREA\" as idInstanciaDeTarea, "
				+ "T.\"A_NOMBRE_TAREA\" as nombreDeTarea, "
				+ "UA.\"ID_USUARIO\" as idDeUsuario, "
				+ "HI.\"A_COMENTARIO\" as ultimoComentario, "
				+ "MAX(HI.\"D_FECHA_MOVIMIENTO\") as fechaMovimiento, "
				+ "HI.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" as idInstanciaDeTareaOrigen," 
				+ "IT.\"D_FECHA_INICIO\"  as fechaInicio,"
				+ "IT.\"D_FECHA_FINALIZACION\" as fechaFinalizacion " +
			"FROM sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" IT " +
			"INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\" IP ON IT.\"ID_INSTANCIA_DE_PROCESO\" = IP.\"ID_INSTANCIA_DE_PROCESO\" " +
			"INNER JOIN sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" HI ON IT.\"ID_INSTANCIA_DE_TAREA\" = HI.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" " +
			"INNER JOIN sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" UA ON IT.\"ID_INSTANCIA_DE_TAREA\" = UA.\"ID_INSTANCIA_DE_TAREA\" " +  
			"INNER JOIN sgdp.\"SGDP_TAREAS\" T ON IT.\"ID_TAREA\" = T.\"ID_TAREA\" " +
			"WHERE IP.\"ID_EXPEDIENTE\" =:idExpediente " +
			"GROUP BY T.\"A_NOMBRE_TAREA\", UA.\"ID_USUARIO\", HI.\"A_COMENTARIO\", HI.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\", " +
			"IT.\"D_FECHA_INICIO\", IT.\"D_FECHA_FINALIZACION\", IT.\"ID_INSTANCIA_DE_TAREA\" " +
			"HAVING MAX(HI.\"D_FECHA_MOVIMIENTO\") = ( " +
			"	SELECT MAX(HIN.\"D_FECHA_MOVIMIENTO\") FROM sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" HIN " + 
			"	WHERE HIN.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = HI.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" " +
			")");	
		
		query.addScalar("idInstanciaDeTarea", StandardBasicTypes.LONG);
		query.addScalar("nombreDeTarea", StandardBasicTypes.STRING);
		query.addScalar("idDeUsuario", StandardBasicTypes.STRING);
		query.addScalar("ultimoComentario", StandardBasicTypes.STRING);
		query.addScalar("fechaMovimiento", StandardBasicTypes.DATE);
		query.addScalar("idInstanciaDeTareaOrigen", StandardBasicTypes.LONG);
		query.addScalar("fechaInicio", StandardBasicTypes.DATE);
		query.addScalar("fechaFinalizacion", StandardBasicTypes.DATE);
		query.setResultTransformer(Transformers.aliasToBean(HistorialProceso.class));
		query.setString("idExpediente", idExpediente);
		return query.list();
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<EtapaDeInstanciaDeProceso> getEtapasDeInstanciaDeProcesoPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.getEtapasDeInstanciaDeProcesoPorIdExpediente");	
		query.setString("idExpediente", idExpediente);
		query.setResultTransformer(Transformers.aliasToBean(EtapaDeInstanciaDeProceso.class));
		return query.list();
	}	
	
	@Override
	public InstanciaDeProceso getInstanciaDeProcesoPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.getInstanciaDeProcesoPorIdExpediente");	
		query.setString("idExpediente", idExpediente);			
		return (InstanciaDeProceso) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FechaEstadoInstanciaProcesoDTO getFechaEstadoInstanciaDeProcesoPorIdExpediente(
			String idExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.getFechaEstadoInstanciaDeProcesoPorIdExpediente");	
		query.setString("idExpediente", idExpediente);			
		query.setResultTransformer(Transformers.aliasToBean(FechaEstadoInstanciaProcesoDTO.class));
		return (FechaEstadoInstanciaProcesoDTO) query.uniqueResult();
	}	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<RespuestaConsultaAvanzadaEstadoProceso> ConsultaAvanzadaEstadoProceso(
			ConsultaEstadoProceso consultaEstadoProceso) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.ConsultaAvanzadaEstadoProceso");	
		query.setString("idExpediente", consultaEstadoProceso.getIdExpediente());
		query.setResultTransformer(Transformers.aliasToBean(RespuestaConsultaAvanzadaEstadoProceso.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RespuestaConsultaBasicaEstadoProceso> ConsultaBasicaEstadoProceso(
			ConsultaEstadoProceso consultaEstadoProceso) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.ConsultaBasicaEstadoProceso");	
		query.setString("idExpediente", consultaEstadoProceso.getIdExpediente());
		query.setResultTransformer(Transformers.aliasToBean(RespuestaConsultaBasicaEstadoProceso.class));
		return query.list();
	}
	
	@Override
	public InstanciaDeProceso getInstanciaDeProcesoPorIdInstanciaDeProceso(long idInstanciaDeProceso) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.getInstanciaDeProcesoPorIdInstanciaDeProceso");	
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);			
		return (InstanciaDeProceso) query.uniqueResult();
	}

	@Override
	public InstanciaDeProceso getInstanciaDeProcesoPorNombreExpediente(String nombreExpediente) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.getInstanciaDeProcesoPorNombreExpediente");	
		query.setString("nombreExpediente", nombreExpediente);			
		return (InstanciaDeProceso) query.uniqueResult();
	}

	@Override
	public List<InstanciaDeProceso> getMetadataListaExpediente(EnviarArchivoNacionalDTO enviarDTO) {
		Query query = getSession().getNamedQuery("InstanciaDeProceso.getMetadataListaExpediente");	
		query.setString("nombreProceso", enviarDTO.getNombreSerie());
		query.setString("fechaTransferirInicio", enviarDTO.getFechaTransferirInicio());
		query.setString("fechaTransferirTermino", enviarDTO.getFechaTransferirTermino());
		List<InstanciaDeProceso> list = query.list();
		// se quitan aquellas tareas que no tienen tipo
		for (InstanciaDeProceso inst: list) {
			List<InstanciaDeTarea> tareas = inst.getInstanciasDeTareas();
			for (InstanciaDeTarea tar: tareas) {
				List<ArchivosInstDeTarea> archivosInstDeTarea = tar.getArchivosInstDeTarea();
				List<ArchivosInstDeTarea> copiaArchivos = new ArrayList<ArchivosInstDeTarea>();
				for (ArchivosInstDeTarea arch: archivosInstDeTarea) {
					if (arch.getArchivosInstDeTareaMetadata().getTipo()!=null) {
						copiaArchivos.add(arch);
					}
				}
				tar.setArchivosInstDeTarea(copiaArchivos);
			}
		}
		return list;
	}
	
}

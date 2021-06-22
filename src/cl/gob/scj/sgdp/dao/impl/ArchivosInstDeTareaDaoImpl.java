package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;

@Repository
public class ArchivosInstDeTareaDaoImpl implements ArchivosInstDeTareaDao {

	private static final Logger log = Logger.getLogger(ArchivosInstDeTareaDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertaArchivosInstDeTarea(ArchivosInstDeTarea archivosInstDeTarea) {
		getSession().save(archivosInstDeTarea);
	}
	
	@Override
	public ArchivosInstDeTarea getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(
				String idArchivo, long idInstanciaDeTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario");
		query.setString("idArchivo", idArchivo);
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return (ArchivosInstDeTarea) query.uniqueResult();
	}
	
	@Override
	public ArchivosInstDeTarea getArchivosInstDeTareaPorIdInstanciaDeTareaIdUsuarioNomArchivo(
			long idInstanciaDeTarea, String idUsuario, String nombreArchivo) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosInstDeTareaPorIdInstanciaDeTareaIdUsuarioNomArchivo");		
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setString("nombreArchivo", nombreArchivo);
		return (ArchivosInstDeTarea) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdInstanciaDeTareaIdUsuario(
			long idInstanciaDeTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdUsuario");
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);			
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTarea");	
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);			
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales");	
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);			
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosPorIdInstanciaDeTareaIdUsuarioTiposDeDocDeTarea(
			long idInstanciaDeTarea, String idUsuario) {
		log.debug("Inicio getArchivosPorIdInstanciaDeTareaIdUsuarioTiposDeDocDeTarea");
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdUsuarioTiposDeDocDeTarea");
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);			
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosDeInstDeTareaAnteriorPorIdInstanTareaPorIdTipoDeDocumento(
			long idInstanciaDeTarea, long idTipoDeDocumento) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosDeInstDeTareaAnteriorPorIdInstanTareaPorIdTipoDeDocumento");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);	
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		return query.list();		
	}
	
	@Override
	public void actualizaEstatusDeVisacionYFirmasAFalsePorIdArchivoIdProceso(String idArchivoCms, long idInstanciaDeProceso) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.actualizaEstatusDeVisacionYFirmasAFalsePorIdArchivoIdProceso");		
		query.setString("idArchivoCms", idArchivoCms);
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdProcesoDistinIdInstanTarea(
			String idArchivoCms, long idInstanciaDeProceso, long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosInstDeTareaPorIdProcesoDistinIdInstanTarea");		
		query.setString("idArchivoCms", idArchivoCms);	
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();		
	}
	
	@Override
	public ArchivosInstDeTarea getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuarioNombreArchivo(
				String idUsuario, long idInstanciaDeTarea, long idTipoDeDocumento, String nombreArchivo) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuarioNombreArchivo");
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		query.setString("nombreArchivo", nombreArchivo);
		return (ArchivosInstDeTarea) query.uniqueResult();
	}
	
	@Override
	public ArchivosInstDeTarea getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuario(
				String idUsuario, long idInstanciaDeTarea, long idTipoDeDocumento) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuario");
		query.setString("idUsuario", idUsuario);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setLong("idTipoDeDocumento", idTipoDeDocumento);
		return (ArchivosInstDeTarea) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTarea");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosPorIdArchivo(String idArchivoCms) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdArchivo");		
		query.setString("idArchivoCms", idArchivoCms);	
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosPorIdArchivoEnTareaConNumero(String idArchivoCms) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdArchivoEnTareaConNumero");		
		query.setString("idArchivoCms", idArchivoCms);	
		return query.list();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosRequeridosPorNombreArchivoIdExpediente(String nombreArchivo, String idExpediente) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosRequPorNombreArchivoIdExpediente");		
		query.setString("nombreArchivo", nombreArchivo);
		query.setString("idExpediente", idExpediente);
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getTodosArchivosSubidosRequeridosPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getTodosArchivosSubidosRequeridosPorIdExpediente");		
		query.setString("idExpediente", idExpediente);	
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosEnviadosPorIdInstanciaDeTareaNombreDeDocumento(long idInstanciaDeTarea, String nombreDeTipoDeDocumento) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosEnviadosPorIdInstanciaDeTareaNombreDeDocumento");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setString("nombreDeTipoDeDocumento", nombreDeTipoDeDocumento);
		return query.list();	
	}

	@Override
	public List<ArchivosInstDeTarea> buscaArchivoFirmadoDeLaInstanciaDeTarea(String idObjeto) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.buscaArchivoFirmadoDeLaInstanciaDeTarea");		
		query.setString("idObjeto", idObjeto);
		return query.list();	
	}
	
	@Override
	public List<String> getIdDeDocumentosSubidosOficiales() {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getIdDeDocumentosSubidosOficiales");
		return query.list();	
	}
	
	@Override
	public List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDoc(Long idInstanciaDeTarea, String idUsuario, String nombreDeTipoDeDocumento) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDoc");		
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setString("idUsuario", idUsuario);
		query.setString("nombreDeTipoDeDocumento", nombreDeTipoDeDocumento);
		return query.list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTarea> getArchivosPorIdInstanciaDeTareaTiposDeDocDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaTiposDeDocDeTarea");	
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);			
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getTodosLosDocSubidosPorIdExpediente");		
		query.setString("idExpediente", idExpediente);
        query.setResultTransformer(Transformers.aliasToBean(ArchivosInstDeTareaDTO.class));
		return query.list();
	}

	@Override
	public List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdSerie(long idSerie, Long idEstadoProceso, Long estadoDocumento, String fechaTransferirInicio, String fechaTransferirTermino) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosInstDeTareaPorIdSerie");		
		query.setLong("idProceso", idSerie);
		query.setLong("idEstadoProceso", idEstadoProceso);
		query.setLong("estadoDocumento", estadoDocumento);
		query.setString("fechaTransferirInicio", fechaTransferirInicio);
		query.setString("fechaTransferirTermino", fechaTransferirTermino);
		return query.list();	
	}

	@Override
	public List<ArchivosInstDeTarea> getArchivosInstDeTareaPorNombreSerie(String nombreSerie, String fechaTransferirInicio, String fechaTransferirTermino) {
		Query query = getSession().getNamedQuery("ArchivosInstDeTarea.getArchivosInstDeTareaPorNombreSerie");	
		String sqlString = query.getQueryString();
		log.info("QUERY?????:"+query);
		query.setString("nombreSerie", nombreSerie);
		query.setString("fechaTransferirInicio", fechaTransferirInicio);
		query.setString("fechaTransferirTermino", fechaTransferirTermino);
		
		return query.list();	
	}
	
}

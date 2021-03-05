package cl.gob.scj.sgdp.dao;

import java.util.Date;
import java.util.List;

import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;

public interface ArchivosInstDeTareaDao {
	
	List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdInstanciaDeTareaIdUsuario(
			long idInstanciaDeTarea, String idUsuario);
	
	ArchivosInstDeTarea getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(
			String idArchivo, long idInstanciaDeTarea, String idUsuario);
	
	void insertaArchivosInstDeTarea(ArchivosInstDeTarea archivosInstDeTarea);
	
	List<ArchivosInstDeTarea> getTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales(long idInstanciaDeTarea);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales(long idInstanciaDeTarea);
	
	List<ArchivosInstDeTarea> getArchivosPorIdInstanciaDeTareaIdUsuarioTiposDeDocDeTarea(
			long idInstanciaDeTarea, String idUsuario);
	
	List<ArchivosInstDeTarea> getArchivosDeInstDeTareaAnteriorPorIdInstanTareaPorIdTipoDeDocumento(
			long idInstanciaDeTarea, long idTipoDeDocumento);
	
	ArchivosInstDeTarea getArchivosInstDeTareaPorIdInstanciaDeTareaIdUsuarioNomArchivo(
			long idInstanciaDeTarea, String idUsuario, String nombreArchivo);
	
	void actualizaEstatusDeVisacionYFirmasAFalsePorIdArchivoIdProceso(String idArchivoCms, long idInstanciaDeProceso);
	
	List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdProcesoDistinIdInstanTarea(
			String idArchivoCms, long idInstanciaDeProceso, long idInstanciaDeTarea);
	
	ArchivosInstDeTarea getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuarioNombreArchivo(
			String idUsuario, long idInstanciaDeTarea, long idTipoDeDocumento, String nombreArchivo);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTarea(long idInstanciaDeTarea);
	
	List<ArchivosInstDeTarea> getArchivosPorIdArchivo(String idArchivoCms);
	
	List<ArchivosInstDeTarea> getArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(
			long idInstanciaDeTarea);
	
	List<ArchivosInstDeTarea> getArchivosPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	List<ArchivosInstDeTarea> getArchivosRequeridosPorNombreArchivoIdExpediente(String nombreArchivo, String idExpediente);
	
	ArchivosInstDeTarea getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuario(String idUsuario, long idInstanciaDeTarea, long idTipoDeDocumento);
	
	List<ArchivosInstDeTarea> getTodosArchivosSubidosRequeridosPorIdExpediente(String idExpediente);
	
	List<ArchivosInstDeTarea> getArchivosEnviadosPorIdInstanciaDeTareaNombreDeDocumento(long idInstanciaDeTarea, String nombreDeTipoDeDocumento);

	List<ArchivosInstDeTarea> buscaArchivoFirmadoDeLaInstanciaDeTarea(String idObjeto);
	
	List<String> getIdDeDocumentosSubidosOficiales();
	
	List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDoc(Long idInstanciaDeTarea, String idUsuario, String nombreTipoDocumento);
	
	List<ArchivosInstDeTarea> getArchivosPorIdInstanciaDeTareaTiposDeDocDeTarea(long idInstanciaDeTarea);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpediente(String idExpediente);
	
	List<ArchivosInstDeTarea> getArchivosPorIdArchivoEnTareaConNumero(String idArchivoCms);	

	ArchivosInstDeTarea getArchivoPorIdInstanciaDeTareaIdTipoDeDocumento(long idInstanciaDeTarea, long idTipoDeDocumento);
	
	ArchivosInstDeTarea getUltimoArchivoInstDeTareaFirmado(long idInstanciaDeTarea, long idTipoDeDocumento, String idUsuario);
	
	List<ArchivosInstDeTarea> getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDocFechaSubidoMayorA(Long idInstanciaDeTarea, String idUsuario, String nombreDeTipoDeDocumento, Date fechaSubido);
	
}

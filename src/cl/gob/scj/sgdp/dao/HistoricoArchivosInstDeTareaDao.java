package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoArchivosInstDeTarea;

public interface HistoricoArchivosInstDeTareaDao {

	void insertArchivosHistInstDeTarea(HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea, Usuario usuario);
	
	List<HistoricoArchivosInstDeTarea> getHistoricoDeArchivosPorIdInstanciaDeTareaIdUsuario(
			long idInstanciaDeTarea, String idUsuario);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaHistoricoOrigen(long idInstanciaDeTarea);
	
	//List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaHistoricoDestino(long idInstanciaDeTarea);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpedienteHistoricoOrigen(String idExpediente);
	
	//List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpedienteHistoricoDestino(String idExpediente);
	

	HistoricoArchivosInstDeTarea getPorIdArchivoCMS(String idCMS);

	public List<HistoricoArchivosInstDeTarea> getHistoricoDeArchivosPorIdArchivoCMS(String idArchivoCms);
	

}

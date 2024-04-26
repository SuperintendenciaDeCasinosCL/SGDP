package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.ReportFilterDTO;
import cl.gob.scj.sgdp.model.HistoricoFirma;

public interface HistoricoFirmaDao {	
	
	void insertaHistoricoFirma(HistoricoFirma historicoFirma);

	List<String> getListaDocumentosFirmados(String idExpediente);
	
	List<HistoricoFirma> getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(long idInstanciaDeTarea, String idUsuario);
	
	List<HistoricoFirma> getHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS);
	
	HistoricoFirma getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS);
	
	long getIdDocumentoFirmado();
	
	HistoricoFirma getHistoricoFirmaPorIdDocumentoFirmado(Long idDocumentoFirmado);
	
	List<HistoricoFirma> getHistoricoFirmaPorIdTipoDocumentoIdInstanciaDeTareaIdUsuario(Long idTipoDeDocumento, Long idInstanciaDeTarea, String idUsuario);
	
	List<HistoricoFirma> getListArchivoByCodeExpedienteWithLimit(ReportFilterDTO reportFilterDTO);
	
	Long getCountListArchivoByCodeExpedienteWithLimit(ReportFilterDTO reportFilterDTO);
	
}

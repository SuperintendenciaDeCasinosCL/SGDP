package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.HistoricoFirma;

public interface HistoricoFirmaDao {	
	
	void insertaHistoricoFirma(HistoricoFirma historicoFirma);

	List<String> getListaDocumentosFirmados(String idExpediente);
	
	List<HistoricoFirma> getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(long idInstanciaDeTarea, String idUsuario);
	
	List<HistoricoFirma> getHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS);

}

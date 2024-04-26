package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;

public interface DocumentoDeSalidaDeTareaDao {
	
	public List<DocumentoDeSalidaDeTarea> getByIdProceso(long idProceso);
	public DocumentoDeSalidaDeTarea getByIdDocumentoSalida(Long idDocumentoSalida, Long idTarea);
	public int updatePlantilla(Long idPlantilla, Long idTipoDocumento);
	public DocumentoDeSalidaDeTarea updatePlantilla(DocumentoDeSalidaDeTarea docSalida);

}

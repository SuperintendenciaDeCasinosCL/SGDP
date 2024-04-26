package cl.gob.scj.sgdp.dao;

import java.io.Serializable;
import java.util.List;

import cl.gob.scj.sgdp.model.PlantillaDeDocumento;

public interface PlantillaDeDocumentoDao {
	
	public List<PlantillaDeDocumento> getTodasLasPlantillas();
	public Integer actualizaVigencia(String codige);
	public Serializable guardaPlantilla(PlantillaDeDocumento plantilla);
	public PlantillaDeDocumento getPlantillaPorIdTipoDeDocumento(Long idTipoDeDocumento);

}

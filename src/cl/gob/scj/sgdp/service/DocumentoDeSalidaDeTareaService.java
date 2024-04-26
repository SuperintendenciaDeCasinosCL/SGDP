package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.PlantillaDTO;
import cl.gob.scj.sgdp.dto.TareaYTipoDeDocumentoDTO;


@Service
public interface DocumentoDeSalidaDeTareaService {

	List<TareaYTipoDeDocumentoDTO> getTareaYTipoDeDocumentoPorIdProceso(Long idProceso);
	List<TareaYTipoDeDocumentoDTO> updatePlantillaDocumento(List<TareaYTipoDeDocumentoDTO> list);
	PlantillaDTO savePlantilla(PlantillaDTO plantilla);
	PlantillaDTO getPlantillaPorIdTipoDeDocumento(Long idTipoDeDocumento);
}

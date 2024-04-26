package cl.gob.scj.sgdp.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.dto.PlantillaDTO;
import cl.gob.scj.sgdp.dto.PlantillaDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.TareaYTipoDeDocumentoDTO;
import cl.gob.scj.sgdp.service.DocumentoDeSalidaDeTareaService;
import cl.gob.scj.sgdp.service.PlantillaDeDocumentoService;

@Controller
public class PlantillaDocumentoTareaControl {

	@Autowired
	private DocumentoDeSalidaDeTareaService docSalidaTareaService;
	
	@Autowired
	private PlantillaDeDocumentoService plantillaService;
	
	@RequestMapping(value = "/documentoYtarea/{idProceso}", method = RequestMethod.GET)
	public @ResponseBody List<TareaYTipoDeDocumentoDTO> getTareasPorIdProceso(@PathVariable("idProceso") Long idProceso) {
		return docSalidaTareaService.getTareaYTipoDeDocumentoPorIdProceso(idProceso);
	}
	
	@RequestMapping(value = "/plantillas", method = RequestMethod.GET)
	public @ResponseBody List<PlantillaDeDocumentoDTO> getAll() {
		return plantillaService.getAll();
	}
	
	@RequestMapping(value = "/plantillaDocumento", method = RequestMethod.PUT)
	public @ResponseBody List<TareaYTipoDeDocumentoDTO> updatePlantillaDocumento(
			@RequestBody List<TareaYTipoDeDocumentoDTO> plantillas ) {
		return docSalidaTareaService.updatePlantillaDocumento(plantillas);
	}
	
	@RequestMapping(value = "/plantilla", method = RequestMethod.POST)
	public @ResponseBody PlantillaDTO guardarPlantilla(@RequestBody PlantillaDTO plantilla ) {
		return docSalidaTareaService.savePlantilla(plantilla);
	}
	
	@RequestMapping(value = "/plantilla/{idTipoDeDocumento}", method = RequestMethod.GET)
	public @ResponseBody PlantillaDTO getPlantillaPorIdTipoDeDocumento(@PathVariable("idTipoDeDocumento") Long idTipoDeDocumento ) {
		return docSalidaTareaService.getPlantillaPorIdTipoDeDocumento(idTipoDeDocumento);
	}
	
	
}

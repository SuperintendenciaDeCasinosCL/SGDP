package cl.gob.scj.sgdp.control;

import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;


import cl.gob.scj.sgdp.dto.TareaYTipoDeDocumentoDTO;

import cl.gob.scj.sgdp.service.TipoDeDocumentoService;


@Controller
public class TipoDeDocumentoControl {

private static final Logger log = Logger.getLogger(TipoDeDocumentoControl.class);	

	@Autowired
	private TipoDeDocumentoService tdService;
	
	@RequestMapping(value = "/tipoDeDocumento/{idProceso}", method = RequestMethod.GET)
	public @ResponseBody List<TareaYTipoDeDocumentoDTO> getByIdProceso(@PathVariable("idProceso") Long idProceso) {
		return tdService.getTiposDeDocumentosPorIdProceso(idProceso);
	}
	
	
}

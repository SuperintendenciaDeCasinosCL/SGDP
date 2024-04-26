package cl.gob.scj.sgdp.control;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.dto.InfoProcesoExternoDTO;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;

@Controller
public class ApiUsuariosExternos {
	
	private static final Logger log = Logger.getLogger(ApiUsuariosExternos.class);
	@Autowired
	private InstanciaDeProcesoService instanciaService;

	@RequestMapping(value="/ext/expediente/{expediente}", method=RequestMethod.GET)
	public @ResponseBody InfoProcesoExternoDTO getProcesos(@PathVariable String expediente) {			
		return instanciaService.getInstanciaDeProcesoPorNombreAPI(expediente);
	}

}

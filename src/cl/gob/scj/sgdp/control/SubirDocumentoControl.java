package cl.gob.scj.sgdp.control;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.SubirDocumentoService;

@Controller
public class SubirDocumentoControl {

	private static final Logger log = Logger.getLogger(SubirDocumentoControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private SubirDocumentoService subirDocumentoService;
	
	//Usuario usuario;
	
	// Subir Carta (Documento conductor)
	@RequestMapping(value = "/subirDocumento", method = RequestMethod.POST)
	public @ResponseBody SubirDocumentoDTO subirDocumento(@ModelAttribute("subirDocumentoDTO") SubirDocumentoDTO subirDocumentoDTO, Model model, HttpServletRequest request) {
		try {
			subirDocumentoDTO.setNombreTipoDocumentoAdjuntos("Antecedente");//Buscar este dato en la BD
			log.debug(subirDocumentoDTO.toString());	
			log.debug("subirDocumentoDTO.getDocumento().getSize(): " + subirDocumentoDTO.getDocumento().getSize());
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			String respuestasubirCarta = subirDocumentoService.subirDocumento(usuario, subirDocumentoDTO);
			log.debug("respuestasubirCarta: " + respuestasubirCarta);			
			subirDocumentoDTO.setResultadoSubirDocumento(configProps.getProperty("cartaSubidaConExito"));
			subirDocumentoDTO.setCssStatus(configProps.getProperty("cssSucess"));			
			return subirDocumentoDTO;		
		} catch (SgdpException sgdpe) {
			log.error("ERROR al subir carta: ", sgdpe);			
			subirDocumentoDTO.setResultadoSubirDocumento(sgdpe.getMessage());
			subirDocumentoDTO.setCssStatus(configProps.getProperty("cssError"));
			return subirDocumentoDTO;
		} catch (Exception e) {
			log.error("ERROR al subir carta: ", e);
			subirDocumentoDTO.setResultadoSubirDocumento(configProps.getProperty("respuestaError"));
			subirDocumentoDTO.setCssStatus(configProps.getProperty("cssError"));
			return subirDocumentoDTO;
		}	
	}	
}
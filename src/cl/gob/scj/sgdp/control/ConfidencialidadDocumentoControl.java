package cl.gob.scj.sgdp.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.service.ConfidencialidadDocumentoService;

@Controller
public class ConfidencialidadDocumentoControl {

	@Autowired
	private ConfidencialidadDocumentoService confidencialidadDocumentoService;

	@RequestMapping(value = "/confidencialidadDocumento/{id}", method = RequestMethod.GET)
	public @ResponseBody ConfidencialidadDocumentoDTO getDataConfidencialidadDocumento(@PathVariable String id) {
		ConfidencialidadDocumentoDTO cf = confidencialidadDocumentoService.getByIdTipoDocumento(id);
		return cf;
	}

	@RequestMapping(value = "/confidencialidadDocumento/{id}/{usuarios}/{roles}", method = RequestMethod.POST)
	public  @ResponseBody ConfidencialidadDocumentoDTO guardarConfidencialidadDocumento(@PathVariable String id, @PathVariable List<String> usuarios, @PathVariable List<String> roles ) {
		ConfidencialidadDocumentoDTO cd = new ConfidencialidadDocumentoDTO();
		cd.setUsuarios(usuarios);
		cd.setId(id);
		List<RolDTO> rolesList = new ArrayList<>();
		for(String r: roles) {
			RolDTO rol = new RolDTO();
			rol.setIdRol(Long.parseLong(r));
			rolesList.add(rol);
		}
		
		cd.setRoles(rolesList);
		
		return confidencialidadDocumentoService.guardar(cd);
	}

	@RequestMapping(value = "/confidencialidadDocumento/puedeVerDocumento/{idArchivo}", method = RequestMethod.GET)
	public @ResponseBody Boolean puedeVerDocumento(@PathVariable String idArchivo, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		return confidencialidadDocumentoService.puedeVerPorRol(idArchivo, usuario) && 	confidencialidadDocumentoService.puedeVerPorUsuario(idArchivo, usuario);
	}

}

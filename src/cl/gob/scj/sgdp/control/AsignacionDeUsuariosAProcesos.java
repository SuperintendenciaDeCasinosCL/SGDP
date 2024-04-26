package cl.gob.scj.sgdp.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ResponsabilidadDTO;
import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.dto.UsuariosPorRolDTO;
import cl.gob.scj.sgdp.model.Responsabilidad;
import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.service.ResponsabilidadTareaService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;
import cl.gob.scj.sgdp.service.UsuarioRolService;


@Controller
public class AsignacionDeUsuariosAProcesos {
	
	@Autowired
	private ProcesoService procesoService;
	
	@Autowired
	private ResponsabilidadTareaService responsabilidadTareaService;
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private UsuarioResponsabilidadService usuarioResponsabilidadService;

	private static final Logger log = Logger.getLogger(AsignacionDeUsuariosAProcesos.class);

		
	@RequestMapping(value="/asignacionUsuarios/procesos", method=RequestMethod.GET)
	public @ResponseBody List<ProcesoDTO> getProcesos() {			
		return procesoService.buscarTodosProcesoVigente(true);
	}
	
	@RequestMapping(value="/asignacionUsuarios/roles/{processId}", method=RequestMethod.GET)
	public @ResponseBody List<ResponsabilidadDTO> getRoles(@PathVariable("processId") Long processId) {			
		return responsabilidadTareaService.getAllByProcessId(processId);
	}
	
	@RequestMapping(value="/asignacionUsuarios/usuarios/{rol}", method=RequestMethod.GET)
	public @ResponseBody UsuariosPorRolDTO getUsuarios(@PathVariable("rol") Long rol) {		
		return usuarioRolService.getUsuariosPorRol(rol);
	}
	
	@RequestMapping(value="/asignacionUsuarios/usuarios/{rol}", method=RequestMethod.POST)
	public @ResponseBody Integer asignaUsuarios(@RequestBody List<UsuarioResponsabilidadDTO> usuarios, @PathVariable Long rol) {
		Map<String, Boolean> existePar = new HashMap<String, Boolean>();
		
		usuarioResponsabilidadService.eliminaUsuarioResponsabilidadPorIdResponsabilidad(rol);
		int i = 0;
		for (UsuarioResponsabilidadDTO usuario : usuarios) {
			String key = usuario.getIdUsuario();
			UsuarioResponsabilidad ur = new UsuarioResponsabilidad();
			Responsabilidad r = new Responsabilidad();
			
			r.setIdResponsabilidad(usuario.getIdUsuarioResponsabilidad());
			ur.setResponsabilidad(r);
			ur.setIdUsuario(usuario.getIdUsuario());
			ur.setOrden(i+1);
			
			if (existePar.get(key) == null) { 
				usuarioResponsabilidadService.guardar(ur);
				existePar.put(key, true);
			}
			i++;
		}
		return i;
	}
	

}

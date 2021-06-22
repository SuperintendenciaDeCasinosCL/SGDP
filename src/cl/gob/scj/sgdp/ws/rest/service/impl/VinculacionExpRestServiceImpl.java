package cl.gob.scj.sgdp.ws.rest.service.impl;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.VinculacionExpedienteDTO;
import cl.gob.scj.sgdp.dto.rest.VinculacionExpedienteRestDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.service.VinculacionesExpedienteService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.rest.service.VinculacionExpRestService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class VinculacionExpRestServiceImpl implements VinculacionExpRestService {
	
	private static final Logger log = Logger.getLogger(VinculacionExpRestServiceImpl.class);
	
	@Autowired
	private VinculacionesExpedienteService vinculacionesExpedienteService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Override
	public VinculacionExpedienteRestDTO getVinculacionExpedientRestDTO(String idExpediente) {
		VinculacionExpedienteDTO vinculacionExpedienteDTO = vinculacionesExpedienteService.getVinculacionExpedienteDTO(idExpediente);
		VinculacionExpedienteRestDTO vinculacionExpedienteRestDTO = new VinculacionExpedienteRestDTO();
		BeanUtils.copyProperties(vinculacionExpedienteDTO, vinculacionExpedienteRestDTO);
		return vinculacionExpedienteRestDTO;
	}

	@Override
	public void vincularExp(VinculacionExpedienteRestDTO vinculacionExpedienteRestDTO) throws Exception {
		
		String validaDatos = vinculacionExpedienteRestDTO.getValidaDatos();
		
		if (validaDatos!=null && !validaDatos.isEmpty()) {
			throw new SgdpException(validaDatos, Level.WARN, true);
		}
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(vinculacionExpedienteRestDTO.getIdUsuario());
	    usuario.setIdRolUsuarioSeleccionado(vinculacionExpedienteRestDTO.getIdRol());        
	    usuario.setAlfTicket(autenticacionService.login(vinculacionExpedienteRestDTO.getIdUsuario()));
	    
	    List<UsuarioRol> usuarioRoles = usuarioRolService.getUsuarioRolesPorIdUsuario(usuario.getIdUsuario());
        usuario.setRolUnidadPermisosPorIdRolUsuarioSeleccionado(usuarioRoles);
        
        String permisoPuedeVincularExp = PermisoType.PUEDE_VINCULAR_EXPEDIENTES.getNombrePermiso();
		String permisoUsrPuedeVincularExp = usuario.getPermisos().get(permisoPuedeVincularExp);
		log.debug("permisoPuedeVincularExp: " + permisoPuedeVincularExp);
		log.debug("permisoUsrPuedeVincularExp: " + permisoUsrPuedeVincularExp);			
		
		if (permisoUsrPuedeVincularExp==null || (permisoUsrPuedeVincularExp!=null && !permisoUsrPuedeVincularExp.equals(permisoPuedeVincularExp))) {
			throw new SgdpException("No tiene permiso para vincular expedientes", Level.WARN, true);
		}   
		
        VinculacionExpedienteDTO vinculacionExpedienteDTO = new VinculacionExpedienteDTO();
        
        BeanUtils.copyProperties(vinculacionExpedienteRestDTO, vinculacionExpedienteDTO);
        
        vinculacionesExpedienteService.vincularExp(usuario, vinculacionExpedienteDTO);
	    
	}

}
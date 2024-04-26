package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.model.Rol;

@Service
public interface RolService {
	

	public List<Rol> getAllRoles();
	public List<RolDTO> getRoles();
	
	
	List<RolDTO> getAllRolesDTO();
	RolDTO crearRol(RolDTO rolDTO);
	RolDTO getRolPorIdRol(long idRol);
	RolDTO actualizaRol(RolDTO rolDTO);
	
	List<RolDTO> getRolesDTOPorIdUnidad(long idUnidad);
	

}
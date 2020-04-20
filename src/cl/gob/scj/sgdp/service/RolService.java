package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.model.Rol;

@Service
public interface RolService {
	
	public List<Rol> getAllRoles();

}

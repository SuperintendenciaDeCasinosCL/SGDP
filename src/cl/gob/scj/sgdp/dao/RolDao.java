package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Rol;

public interface RolDao {

	public List<Rol> getAllRoles();	
	
	public Rol getRolPorIdRol(long idRol);
	
}

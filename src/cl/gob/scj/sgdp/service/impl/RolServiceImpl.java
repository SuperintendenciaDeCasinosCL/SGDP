package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.PermisoDao;
import cl.gob.scj.sgdp.dao.RolDao;
import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.dto.PermisoDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.model.Permiso;
import cl.gob.scj.sgdp.model.Rol;
import cl.gob.scj.sgdp.model.Unidad;
import cl.gob.scj.sgdp.service.RolService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class RolServiceImpl implements RolService {

	private static final Logger log = Logger.getLogger(RolServiceImpl.class);
	@Autowired
	private RolDao rolDao;

	@Autowired
	private UnidadDao unidadDao;

	@Autowired
	private PermisoDao permisoDao;

	@Override
	public List<Rol> getAllRoles() {
		return rolDao.getAllRoles();
	}

	@Override
	public List<RolDTO> getRoles() {
		List<RolDTO> roles = new ArrayList<RolDTO>();
		for (Rol rol : rolDao.getAllRoles()) {
			roles.add(new RolDTO(rol.getIdRol(), rol.getNombreRol(), rol.getUnidad()!=null ? rol.getUnidad().getIdUnidad() : 0L,
					 rol.getUnidad()!=null ? rol.getUnidad().getNombreCompletoUnidad() : ""));
		}
		return roles;
	}

	public List<RolDTO> getAllRolesDTO() {
		List<Rol> todosLosRoles = rolDao.getAllRoles();
		List<RolDTO> todosLosRolesDTO = new ArrayList<RolDTO>();
		for (Rol rol : todosLosRoles) {
			if (rol.getUnidad() != null) {
				RolDTO rolDTO = new RolDTO(rol.getIdRol(), rol.getNombreRol(), rol.getUnidad()!=null ? rol.getUnidad().getIdUnidad() : 0L,
						 rol.getUnidad()!=null ? rol.getUnidad().getNombreCompletoUnidad() : "");
				todosLosRolesDTO.add(rolDTO);
			}
		}
		log.debug("todosLosRolesDTO: " + todosLosRolesDTO);
		return todosLosRolesDTO;
	}

	@Override
	public RolDTO crearRol(RolDTO rolDTO) {
		Unidad unidad = unidadDao.find(rolDTO.getIdUnidad());
		Rol rol = new Rol();
		rol.setNombreRol(rolDTO.getNombreRol().toUpperCase());
		rol.setUnidad(unidad);
		rolDao.insert(rol);
		rolDTO.setEstado("OK");
		return rolDTO;
	}

	@Override
	public RolDTO getRolPorIdRol(long idRol) {
		Rol rol = rolDao.getRolPorIdRol(idRol);
		log.debug("Rol: " + rol);
		RolDTO rolDTO = new RolDTO();
		rolDTO.setIdRol(rol.getIdRol());
		rolDTO.setNombreRol(rol.getNombreRol());
		rolDTO.setIdUnidad(rol.getUnidad().getIdUnidad());
		rolDTO.setNombreUnidad(rol.getUnidad().getNombreCompletoUnidad());
		List<PermisoDTO> permisosDTO = new ArrayList<PermisoDTO>();
		Set<String> targetSet = new HashSet<String>();
		for (Permiso permiso : rol.getPermisos()) {
			if (targetSet.add(permiso.getNombrePermiso())) {
				log.debug("getRolPorIdRol: " + permiso.toString());
				PermisoDTO permisoDTO = new PermisoDTO();
				permisoDTO.setNombrePermiso(permiso.getNombrePermiso());
				permisoDTO.setActivo(true);
				permisosDTO.add(permisoDTO);
			}
		}
		cargaPermisosDTO(permisosDTO);
		rolDTO.setPermisosDTO(permisosDTO);
		return rolDTO;
	}

	private void cargaPermisosDTO(List<PermisoDTO> permisosDTO) {
		PermisoType[] permisosType = PermisoType.values();
		Set<String> targetSet = new HashSet<String>();
		for (PermisoDTO permisoDTO : permisosDTO) {
			targetSet.add(permisoDTO.getNombrePermiso());
		}
		for (PermisoType permisoType: permisosType){
			if (targetSet.add(permisoType.getNombrePermiso())) {
				PermisoDTO permisoDTO = new PermisoDTO();
				permisoDTO.setNombrePermiso(permisoType.getNombrePermiso());
				permisosDTO.add(permisoDTO);
			}
		}
	}

	@Override
	public RolDTO actualizaRol(RolDTO rolDTO) {
		Unidad unidad = unidadDao.find(rolDTO.getIdUnidad());
		Rol rol = rolDao.getRolPorIdRol(rolDTO.getIdRol());
		rol.setNombreRol(rolDTO.getNombreRol().toUpperCase());
		rol.setUnidad(unidad);
		List<Permiso> permisos = rol.getPermisos();
		for (Permiso permiso : permisos) {
			permisoDao.delete(permiso);
		}
		List<PermisoDTO> permisosDTO = rolDTO.getPermisosDTO();
		for (PermisoDTO permisoDTO : permisosDTO) {
			Permiso permiso = new Permiso();
			permiso.setRol(rol);
			permiso.setNombrePermiso(permisoDTO.getNombrePermiso());
			permisoDao.insert(permiso);
		}
		rolDTO.setEstado("OK");
		return rolDTO;
	}

	@Override
	public List<RolDTO> getRolesDTOPorIdUnidad(long idUnidad) {
		List<Rol> listaRol = rolDao.getRolesDTOPorIdUnidad(idUnidad);
		List<RolDTO> rolesDTOPorIUnidad = new ArrayList<RolDTO>();
		for (Rol rol : listaRol) {
			RolDTO rolDTO = new RolDTO(rol.getIdRol(), rol.getNombreRol(), rol.getUnidad()!=null ? rol.getUnidad().getIdUnidad() : 0L,
					 rol.getUnidad()!=null ? rol.getUnidad().getNombreCompletoUnidad() : "");
			rolesDTOPorIUnidad.add(rolDTO);
		}
		log.debug("rolesDTOPorIUnidad: " + rolesDTOPorIUnidad);
		return rolesDTOPorIUnidad;
	}

}

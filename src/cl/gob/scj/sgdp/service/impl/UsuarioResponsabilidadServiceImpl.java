package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.UsuarioResponsabilidadDao;
import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsuarioResponsabilidadServiceImpl implements UsuarioResponsabilidadService {
	
	@Autowired
	private UsuarioResponsabilidadDao usuarioResponsabilidadDao;
	
	@Autowired
	private TareaDao tareaDao;

	@Override
	public void cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuarios) {
		List<UsuarioResponsabilidad> usuarioResponsabilidades = usuarioResponsabilidadDao.getUsuariosResponsabilidadesPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for (UsuarioResponsabilidad usuarioResponsabilidad: usuarioResponsabilidades) {
			posiblesUsuarios.add(usuarioResponsabilidad.getIdUsuario());
		}
	}
	
	public UsuarioResponsabilidadDTO getPrimerUsuarioResponsabilidadDTOPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		UsuarioResponsabilidad usuarioResponsabilidad = usuarioResponsabilidadDao.getPrimerUsuarioResponsabilidadPorIdInstanciaDeTarea(idInstanciaDeTarea);
		UsuarioResponsabilidadDTO usuarioResponsabilidadDTO = new UsuarioResponsabilidadDTO(usuarioResponsabilidad.getIdUsuarioResponsabilidad(), usuarioResponsabilidad.getIdUsuario(), usuarioResponsabilidad.getOrden());
		return usuarioResponsabilidadDTO;
	}

	@Override
	public void cargaUsuariosRolesPosiblesConOrdenPorIdInstanciaDeTarea(long idInstanciaDeTarea,
			List<String> listaPosiblesUsuarios) {		
		List<UsuarioResponsabilidad> usuarioResponsabilidades = usuarioResponsabilidadDao.getUsuariosResponsabilidadesPorIdInstanciaDeTarea(idInstanciaDeTarea);
 		for (UsuarioResponsabilidad usuarioResponsabilidad: usuarioResponsabilidades) {
 			listaPosiblesUsuarios.add(usuarioResponsabilidad.getIdUsuario());
		}		
	}

	@Override
	public List<UsuarioResponsabilidadDTO> getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso(long idProceso) {		
		List<UsuarioResponsabilidadDTO> usuariosResponsabilidadDTO = new ArrayList<UsuarioResponsabilidadDTO>();		
		List<Tarea> segundasTareas = tareaDao.getSegundasTareasPorIdProceso(idProceso);		
		for (Tarea tarea: segundasTareas) {
			List<UsuarioResponsabilidad> usuariosResponsabilidad  = usuarioResponsabilidadDao.getTodosLosUsuariosResponsabilidadPorIdTarea(tarea.getIdTarea());
			for (UsuarioResponsabilidad usuarioResponsabilidad : usuariosResponsabilidad) {
				UsuarioResponsabilidadDTO usuarioResponsabilidadDTO = new UsuarioResponsabilidadDTO();
				BeanUtils.copyProperties(usuarioResponsabilidad, usuarioResponsabilidadDTO);
				usuariosResponsabilidadDTO.add(usuarioResponsabilidadDTO);
			}			
		}		
		return usuariosResponsabilidadDTO;
	}
	
	@Override
	public void cargaUsuariosFueraOficinaRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuariosFueaOficina) {
		List<UsuarioResponsabilidad> usuarioResponsabilidades = usuarioResponsabilidadDao.getUsuariosFueraOficinaResponsabilidadesPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for (UsuarioResponsabilidad usuarioResponsabilidad: usuarioResponsabilidades) {
			posiblesUsuariosFueaOficina.add(usuarioResponsabilidad.getIdUsuario());
		}
	}

}

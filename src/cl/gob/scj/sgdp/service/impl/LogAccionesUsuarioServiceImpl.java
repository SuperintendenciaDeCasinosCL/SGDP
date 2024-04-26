package cl.gob.scj.sgdp.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.LogAccionesUsuarioDao;
import cl.gob.scj.sgdp.model.LogAccionesUsuario;
import cl.gob.scj.sgdp.service.LogAccionesUsuarioService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LogAccionesUsuarioServiceImpl implements LogAccionesUsuarioService {
	
	@Autowired
	private LogAccionesUsuarioDao logAccionesUsuarioDao;

	@Override
	public void guardaLogAccionesUsuario(Usuario usuario, Object data, String nombreAccion, String modulo) throws JsonProcessingException {
		LogAccionesUsuario logAccionesUsuario = new LogAccionesUsuario();
		logAccionesUsuario.setNombreAccion(nombreAccion);
		logAccionesUsuario.setModulo(modulo);
		logAccionesUsuario.setIdUsuario(usuario.getIdUsuario());
		logAccionesUsuario.setData(SingleObjectFactory.getMapper().writeValueAsString(data));
		logAccionesUsuario.setFechaAccion(new Date());
		logAccionesUsuarioDao.insert(logAccionesUsuario);
	}

}

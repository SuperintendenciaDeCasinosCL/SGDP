package cl.gob.scj.sgdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.RolDao;
import cl.gob.scj.sgdp.model.Rol;
import cl.gob.scj.sgdp.service.RolService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class RolServiceImpl implements RolService {

	@Autowired
	private RolDao rolDao;
	
	@Override
	public List<Rol> getAllRoles() {
		// TODO Auto-generated method stub
		return rolDao.getAllRoles();
	}

}

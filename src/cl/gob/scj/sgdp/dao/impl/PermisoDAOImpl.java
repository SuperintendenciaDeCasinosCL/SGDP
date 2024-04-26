package cl.gob.scj.sgdp.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.PermisoDao;
import cl.gob.scj.sgdp.model.Permiso;

@Repository
@Transactional(rollbackFor = Throwable.class)
public class PermisoDAOImpl extends GenericDaoImpl<Permiso> implements PermisoDao {

}

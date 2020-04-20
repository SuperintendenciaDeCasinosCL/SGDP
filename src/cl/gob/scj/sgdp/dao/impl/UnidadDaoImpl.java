package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroDao;
import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.model.Parametro;
import cl.gob.scj.sgdp.model.Unidad;

@Repository
public class UnidadDaoImpl extends GenericDaoImpl<Unidad> implements UnidadDao {	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Unidad> getTodasLasUnidades() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Unidad").list();
	}

}

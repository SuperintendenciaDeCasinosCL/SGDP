package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ProcesoFormCreaExpDao;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;
import cl.gob.scj.sgdp.model.ProcesoFormCreaExp;

@Repository
public class ProcesoFormCreaExpDaoImpl extends GenericDaoImpl<ProcesoFormCreaExp> implements ProcesoFormCreaExpDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<ProcesoFormCreaExpDTO> getTodosProcesoFormCreaExp() {
		Query query = getSession().getNamedQuery("ProcesoFormCreaExp.getTodosProcesoFormCreaExp");		
        query.setResultTransformer(Transformers.aliasToBean(ProcesoFormCreaExpDTO.class));
		return query.list();	
	}
		
}

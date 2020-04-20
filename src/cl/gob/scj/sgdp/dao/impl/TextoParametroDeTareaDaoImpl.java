package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TextoParametroDeTareaDao;
import cl.gob.scj.sgdp.model.TextoParametroDeTarea;

@Repository
public class TextoParametroDeTareaDaoImpl extends GenericDaoImpl<TextoParametroDeTarea> implements TextoParametroDeTareaDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TextoParametroDeTarea> getTextosParametroDeTareaPorIdParamTarea(long idParamTarea) {
		Query query = getSession().getNamedQuery("TextoParametroDeTarea.getTextosParametroDeTareaPorIdParamTarea");
		query.setLong("idParamTarea", idParamTarea);
		return query.list();
	}

}

package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.TextoParametroDeTarea;

public interface TextoParametroDeTareaDao extends GenericDao<TextoParametroDeTarea> {
	
	List<TextoParametroDeTarea> getTextosParametroDeTareaPorIdParamTarea (long idParamTarea);

}

package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.BitacoraSubTareaDao;
import cl.gob.scj.sgdp.model.BitacoraSubTareas;

@Repository
public class BitacoraSubTareaDaoImpl extends GenericDaoImpl<BitacoraSubTareas> implements BitacoraSubTareaDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraSubTareas> getAllByIdInstTarea(Long idInstTarea) {
		Query query = getSession().getNamedQuery("bitacoraSuBTareas.findAllByIdInstTarea");
		query.setLong("idInstanciaDeTarea", idInstTarea);
		return (List<BitacoraSubTareas>) query.list();
	}

	@Override
	public BitacoraSubTareas findByIdBitacoraSubTareaIdInstTarea(Long IdBitacoraSubTarea, Long idInstTarea) {
		Query query = getSession().getNamedQuery("BitacoraSubTareas.findByIdBitacoraSubTareaIdInstTarea");
		query.setLong("IdBitacoraSubTarea", IdBitacoraSubTarea);
		query.setLong("idInstTarea", idInstTarea);		
		return (BitacoraSubTareas) query.uniqueResult();	
	}

	
}

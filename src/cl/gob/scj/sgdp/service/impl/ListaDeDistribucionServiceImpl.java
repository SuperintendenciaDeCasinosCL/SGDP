package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.control.ProcesoControl;
import cl.gob.scj.sgdp.dao.ListaDeDistribucionDao;
import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;
import cl.gob.scj.sgdp.model.ListaDeDistribucion;
import cl.gob.scj.sgdp.service.ListaDeDistribucionService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ListaDeDistribucionServiceImpl implements ListaDeDistribucionService {
	
	private static final Logger log = Logger.getLogger(ListaDeDistribucionServiceImpl.class);
	
	@Autowired
	private ListaDeDistribucionDao listaDeDistribucionDao;

	@Override
	public List<ListaDeDistribucionDTO> getListaDeDistribucion() {
		List<ListaDeDistribucion> listaDis = listaDeDistribucionDao.getAll(ListaDeDistribucion.class);
		log.debug("listaDis.size(): " + listaDis.size());
		List<ListaDeDistribucionDTO> listaDisDTO = new ArrayList<ListaDeDistribucionDTO>();
		for (ListaDeDistribucion listaDeDistribucion : listaDis) {
			ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
			BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
			listaDisDTO.add(listaDeDistribucionDTO);
		}
		return listaDisDTO;
	}
	
	@Override
	public void borrarRegistroDeListaDeDistribucion(long idListaDeDistribucion) {
		log.debug("Inicio borrarRegistroDeListaDeDistribucion");
		log.debug("idListaDeDistribucion: " + idListaDeDistribucion);
		ListaDeDistribucion listaDeDistribucion = listaDeDistribucionDao.find(idListaDeDistribucion);
		log.debug(listaDeDistribucion.toString());
		listaDeDistribucionDao.delete(listaDeDistribucion);
	}

	@Override
	public ListaDeDistribucionDTO getRegistroDeListaDeDistribucionPorId(long idListaDeDistribucion) {		
		ListaDeDistribucion listaDeDistribucion = listaDeDistribucionDao.find(idListaDeDistribucion);
		ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
		BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
		return listaDeDistribucionDTO;
	}
	
	@Override
	public void actualizaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO) {
		ListaDeDistribucion listaDeDistribucion = listaDeDistribucionDao.find(listaDeDistribucionDTO.getIdListaDeDistribucion());
		BeanUtils.copyProperties(listaDeDistribucionDTO, listaDeDistribucion);
	}
	
	@Override
	public void creaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO) {
		ListaDeDistribucion listaDeDistribucion = new ListaDeDistribucion();
		BeanUtils.copyProperties(listaDeDistribucionDTO, listaDeDistribucion);
		listaDeDistribucionDao.insert(listaDeDistribucion);
	}

}
package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ReportesDao;
import cl.gob.scj.sgdp.service.ReportesService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ReportesServiceImpl implements ReportesService{
	
	@Autowired
	private ReportesDao reporteDao;

	@Override
	public List<?> getReporte(String tipo, String desde, String hasta, String filtro) throws SQLException, IOException, ParseException {
		List<?> lista = new ArrayList<>();
		switch(tipo) {
		case "area":
			lista = reporteDao.getTareasPorAreaData(desde, hasta);
			break;
		case "pendientes":
			lista = reporteDao.getTareasPendientesData(desde, hasta);
			break;
		case "finalizados":
			lista = reporteDao.getTareasFinalizadasData(desde, hasta);
			break;
		case "procesos":
			lista = reporteDao.getProcesosData(desde, hasta);
			break;
		default:
			lista = reporteDao.getTareasPorAreaData(desde, hasta);
			break;
		}
		
		return lista;

	}



}

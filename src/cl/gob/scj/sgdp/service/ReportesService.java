package cl.gob.scj.sgdp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface ReportesService {
	
	List<?> getReporte(String tipo, String desde, String hasta, String filtro)
			throws SQLException, IOException, ParseException;
	

}

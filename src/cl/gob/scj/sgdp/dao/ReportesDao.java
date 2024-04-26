package cl.gob.scj.sgdp.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ReportesDao {
	
	public List<?> getTareasPorAreaData(String desde, String hasta) throws SQLException, IOException, ParseException;
	public List<?> getTareasPendientesData(String desde, String hasta) throws SQLException, IOException, ParseException;
	public List<?> getTareasFinalizadasData(String desde, String hasta) throws SQLException, IOException, ParseException;
	List<?> getProcesosData(String desde, String hasta) throws SQLException, ParseException;
}
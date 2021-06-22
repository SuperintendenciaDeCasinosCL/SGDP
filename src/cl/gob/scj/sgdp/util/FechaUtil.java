package cl.gob.scj.sgdp.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.FechaFeriadoDao;
import cl.gob.scj.sgdp.model.FechaFeriado;

public class FechaUtil {
	
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_FECHA);
	
	public static SimpleDateFormat simpleDateFormatForm = new SimpleDateFormat(Constantes.FORMATO_FECHA_FORM);
	
	public static SimpleDateFormat simpleDateFormatFormHHMMSS = new SimpleDateFormat(Constantes.FORMATO_FECHA_FORM_HH_MM_SS);
	
	public static SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat(Constantes.FORMATO_YEAR);
	
	public static SimpleDateFormat simpleDateFormatCMS = new SimpleDateFormat(Constantes.FORMATO_FECHA_CMS);
	
	public static DateFormat simpleDateFormatUTCISOFEA = new SimpleDateFormat(Constantes.FORMATO_FECHA_UTC_ISO_FEA);
	
	public static SimpleDateFormat simpleDateFormatShortDate = new SimpleDateFormat(Constantes.FORMATO_FECHA_SHORT_DATE);

	public static SimpleDateFormat simpleDateFormatPieFea = new SimpleDateFormat(Constantes.FORMATO_FECHA_PIE_FEA);
	
	public static SimpleDateFormat simpleDateFormatNomArch = new SimpleDateFormat(Constantes.FORMATO_FECHA_PARA_NOM_ARCH);
	
	public static SimpleDateFormat simpleDateFormatFormHHMMSSMS = new SimpleDateFormat(Constantes.FORMATO_FECHA_FORM_HH_MM_SS_SSS);
	
	private static final Logger log = Logger.getLogger(FechaUtil.class);
	
	public static Calendar getFechaHabil(Calendar calendar, FechaFeriadoDao fechaFeriadoDao, int diasSuma) {
		
		log.debug("Inicio getFechaHabil...");
		
		log.debug("calendar.getTime(): " + calendar.getTime());
		log.debug("diasSuma: " + diasSuma);	
		
		int i = 0;
		while ( i < diasSuma ) {
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			
			log.debug("calendar.toString(): " + calendar.toString());
			
			if (esDiaHabil(calendar, fechaFeriadoDao) == true) {
				log.debug("Dia Habil");
				i++;
				
			}
		}
		
		log.debug("calendar.getTime(): " + calendar.getTime());
		log.debug("Fin getFechaHabil...");		
		
		return calendar;
	}
	
	public static boolean esDiaHabil(Calendar calendar, FechaFeriadoDao fechaFeriadoDao) {
		
		Date fechaFeriadoDate = calendar.getTime();
		String fechaFeriadoString = simpleDateFormat.format(fechaFeriadoDate);
		
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) {
			log.debug("Es sabado o domingo");
			return false;
		}
		
		FechaFeriado fechaFeriado = fechaFeriadoDao.getFechaFeriadoPorFechaFeriado(fechaFeriadoString);
		
		if (fechaFeriado!=null) {
			log.debug("Es feriado");
			return false;
		}
		
		log.debug("Es dia habil");
		return true;
	}
	
	public static String toFormatCMS(String fechaInput) throws ParseException  {
		
		log.debug("fechaInput: " + fechaInput);
		
		Date fechaInputDate = simpleDateFormatForm.parse(fechaInput);
		
		log.debug("fechaInputDate: " + fechaInputDate);
		
		log.debug("simpleDateFormatCMS.format(fechaInputDate): " + simpleDateFormatCMS.format(fechaInputDate));
		
		return simpleDateFormatCMS.format(fechaInputDate);

	
	}
	
	public static String toFormat(String fechaInput, DateFormat dfInput, DateFormat dfOutput) throws ParseException  {
		
		log.debug("fechaInput: " + fechaInput);
		
		Date fechaInputDate = dfInput.parse(fechaInput);
		
		log.debug("fechaInputDate: " + fechaInputDate);
		
		log.debug("dfOutput.format(fechaInputDate): " + dfOutput.format(fechaInputDate));
		
		return dfOutput.format(fechaInputDate);
		
	}
	
	public static String toFormat(Date fechaInput, DateFormat dfOutput) throws ParseException  {
		
		log.debug("fechaInput: " + fechaInput);	
		
		log.debug("dfOutput.format(fechaInput): " + dfOutput.format(fechaInput));
		
		return dfOutput.format(fechaInput);
		
	}

	public static long diasEntreFechas(Date d1, Date d2) throws IOException {
		log.debug("diasEntreFechas inicio");
		String fecha1 = FechaUtil.getSimpleDateFormat().format(d1);
		String fecha2 = FechaUtil.getSimpleDateFormat().format(d2);
		Date dd1;
		Date dd2;
		long resultado = 0;
		try {
			dd1 = FechaUtil.getSimpleDateFormat().parse(fecha1);
			dd2 = FechaUtil.getSimpleDateFormat().parse(fecha2);
			long diff = dd1.getTime() - dd2.getTime(); 
			log.debug("diff: " + diff);
			resultado = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			log.debug("resultado: " + resultado);	
		} catch (ParseException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
		}			
	    return resultado;
	}
	
	public static long diasHabilesHastaFecha(Date d1, FechaFeriadoDao fechaFeriadoDao) throws IOException {
		log.debug("diasHabilesHastaFecha inicio");
		Date d2 = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d2);
		String fecha1 = FechaUtil.getSimpleDateFormat().format(d1);
		String fecha2 = FechaUtil.getSimpleDateFormat().format(d2);	
		log.debug("fecha1" + fecha1);
		log.debug("fecha2" + fecha2);
		long resultado = 0;
		long diasEntreFechas = diasEntreFechas(d1, d2);
		if (fecha1.equals(fecha2)) {
			return resultado;
		} else {
			log.debug("En ciclo avanza dias");
			int i = 0;
			while (i < diasEntreFechas) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);			
				log.debug("calendar.toString(): " + calendar.toString());
				if (esDiaHabil(calendar, fechaFeriadoDao) == true) {
					log.debug("Dia Habil");
					i++;
					resultado++;
					fecha2  = FechaUtil.getSimpleDateFormat().format(calendar.getTime());
					log.debug("fecha1" + fecha1);
					log.debug("fecha2" + fecha2);
					if (fecha1.equals(fecha2)) {
						return resultado;
					} 
				}
			}			
			return resultado;
		}
	}
	
	/*public static int diasHabilesDesdeHoyHasta(int diasSuma, FechaFeriadoDao fechaFeriadoDao) throws IOException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int i = 0;
		while ( i < diasSuma ) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);			
			log.debug("calendar.toString(): " + calendar.toString());
			if (esDiaHabil(calendar, fechaFeriadoDao) == true) {
				log.debug("Dia Habil");
				i++;				
			}
		}
		return i;
	}*/
	
	/*public static Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias);
		return cal.getTime();
	}*/

	public static SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	/*public static void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		FechaUtil.simpleDateFormat = simpleDateFormat;
	}*/
	
	public static SimpleDateFormat getSimpleDateFormatForm() {
		return simpleDateFormatForm;
	}

	/*public static void setSimpleDateFormatForm(SimpleDateFormat simpleDateFormatForm) {
		FechaUtil.simpleDateFormatForm = simpleDateFormatForm;
	}*/

	public static SimpleDateFormat getSimpleDateFormatYear() {
		return simpleDateFormatYear;
	}

	/*public static void setSimpleDateFormatYear(SimpleDateFormat simpleDateFormatYear) {
		FechaUtil.simpleDateFormatYear = simpleDateFormatYear;
	}*/

	public static SimpleDateFormat getSimpleDateFormatCMS() {
		return simpleDateFormatCMS;
	}

	/*public static void setSimpleDateFormatCMS(SimpleDateFormat simpleDateFormatCMS) {
		FechaUtil.simpleDateFormatCMS = simpleDateFormatCMS;
	}*/

	public static DateFormat getSimpleDateFormatUTCISOFEA() {
		return simpleDateFormatUTCISOFEA;
	}

	/*public static void setSimpleDateFormatUTCISOFEA(
			DateFormat simpleDateFormatUTCISOFEA) {
		FechaUtil.simpleDateFormatUTCISOFEA = simpleDateFormatUTCISOFEA;
	}*/

	public static SimpleDateFormat getSimpleDateFormatFormHHMMSS() {
		return simpleDateFormatFormHHMMSS;
	}

	/*public static void setSimpleDateFormatFormHHMMSS(SimpleDateFormat simpleDateFormatFormHHMMSS) {
		FechaUtil.simpleDateFormatFormHHMMSS = simpleDateFormatFormHHMMSS;
	}*/

	public static SimpleDateFormat getSimpleDateFormatShortDate() {
		return simpleDateFormatShortDate;
	}

	/*public static void setSimpleDateFormatShortDate(SimpleDateFormat simpleDateFormatShortDate) {
		FechaUtil.simpleDateFormatShortDate = simpleDateFormatShortDate;
	}*/

	public static SimpleDateFormat getSimpleDateFormatNomArch() {
		return simpleDateFormatNomArch;
	}

	public static SimpleDateFormat getSimpleDateFormatFormHHMMSSMS() {
		return simpleDateFormatFormHHMMSSMS;
	}

	public static Date toFormatDate(String fechaCreacion) {
		// TODO Auto-generated method stub
		return null;
	}

	/*public static void setSimpleDateFormatNomArch(SimpleDateFormat simpleDateFormatNomArch) {
		FechaUtil.simpleDateFormatNomArch = simpleDateFormatNomArch;
	}*/	
	
}

package cl.gob.scj.sgdp.excel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cl.gob.scj.sgdp.dto.VinculacionExpedienteDTO;
import cl.gob.scj.sgdp.tipos.VinculacionType;

public class VinculacionExpExcel extends AbstractExcelView {

	private static final Logger log = Logger.getLogger(VinculacionExpExcel.class);
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			VinculacionExpedienteDTO vinculacionExpedienteDTO = (VinculacionExpedienteDTO) model.get("vinculacionExpedienteDTO");
			
			log.info(vinculacionExpedienteDTO.toString());
			
			response.setHeader("Content-disposition", "attachment; filename=\"vinculaciones_expediente_"+vinculacionExpedienteDTO.getNombreExpediente()+".xls\"");
			
			Sheet sheet = workbook.createSheet("vinculaciones_"+vinculacionExpedienteDTO.getNombreExpediente());
			
			HSSFFont fontTitulo = workbook.createFont();
			fontTitulo.setFontHeightInPoints((short) 10);
			fontTitulo.setFontName("Arial");		
			fontTitulo.setBold(false);
			fontTitulo.setItalic(false);
			
			//primera fila
			Row titulo = sheet.createRow(0);
			CellStyle styleTexto = workbook.createCellStyle();	
			styleTexto.setFont(fontTitulo);		
			Cell cellExp = titulo.createCell(0);
			cellExp.setCellValue(vinculacionExpedienteDTO.getNombreExpediente() + " (" + vinculacionExpedienteDTO.getNombreProceso()  + " )");
			cellExp.setCellStyle(styleTexto);
			sheet.autoSizeColumn(0);
			
			HSSFFont fontCabecera = workbook.createFont();
			fontCabecera.setFontHeightInPoints((short) 10);
			fontCabecera.setFontName("Arial");	
			fontCabecera.setItalic(false);
			fontCabecera.setColor(IndexedColors.BLACK.getIndex());
			fontCabecera.setBold(true);
			
			//segunda fila
			Row cabecera = sheet.createRow(1);	
			CellStyle styleCabecera = workbook.createCellStyle();		
			styleCabecera.setFont(fontCabecera);
			
			//primera columna
			Cell cellVinExpCabecera = cabecera.createCell(0);
			cellVinExpCabecera.setCellValue("EXP");
			cellVinExpCabecera.setCellStyle(styleCabecera);
			sheet.autoSizeColumn(0);
			
			//segunda columna
			Cell cellVinSubCabecera = cabecera.createCell(1);
			cellVinSubCabecera.setCellValue("Subproceso");
			cellVinSubCabecera.setCellStyle(styleCabecera);
			sheet.autoSizeColumn(1);
			
			//tercer columna
			Cell cellVinTipoCabecera = cabecera.createCell(2);
			cellVinTipoCabecera.setCellValue("Tipo (Antecesor o Sucesor)");
			cellVinTipoCabecera.setCellStyle(styleCabecera);
			sheet.autoSizeColumn(2);
			
			//cuarta columna
			Cell cellVinExpComCebecera = cabecera.createCell(3);
			cellVinExpComCebecera.setCellValue("Comentario(s)");
			cellVinExpComCebecera.setCellStyle(styleCabecera);
			sheet.autoSizeColumn(3);		
			
			List<VinculacionExpedienteDTO> todosLasVinculaciones = new ArrayList<VinculacionExpedienteDTO>();
			
			for (VinculacionExpedienteDTO vinculacionExpedienteDTOSucesor: vinculacionExpedienteDTO.getExpedientesAntecesores()) {
				vinculacionExpedienteDTOSucesor.setTipoVinculacion(VinculacionType.ANTECESOR.getNombreVinculacion());
				todosLasVinculaciones.add(vinculacionExpedienteDTOSucesor);
			}
					
			for (VinculacionExpedienteDTO vinculacionExpedienteDTOSucesor: vinculacionExpedienteDTO.getExpedientesSucesores()) {
				vinculacionExpedienteDTOSucesor.setTipoVinculacion(VinculacionType.SUCESOR.getNombreVinculacion());
				todosLasVinculaciones.add(vinculacionExpedienteDTOSucesor);
			}
			
			Collections.sort(todosLasVinculaciones);
			
			int indexFila = 2;
			for (VinculacionExpedienteDTO vinculacionExpedienteDTOFinal: todosLasVinculaciones) {
				
				Row fila = sheet.createRow(indexFila++);
				
				Cell cellNomExp = fila.createCell(0);
				cellNomExp.setCellValue(vinculacionExpedienteDTOFinal.getNombreExpediente());
				cellNomExp.setCellStyle(styleTexto);
				sheet.autoSizeColumn(0);
				
				Cell cellNomSub = fila.createCell(1);
				cellNomSub.setCellValue(vinculacionExpedienteDTOFinal.getNombreProceso());
				cellNomSub.setCellStyle(styleTexto);
				sheet.autoSizeColumn(1);
				
				Cell cellVinTipo = fila.createCell(2);
				cellVinTipo.setCellValue(vinculacionExpedienteDTOFinal.getTipoVinculacion());
				cellVinTipo.setCellStyle(styleTexto);
				sheet.autoSizeColumn(2);
				
				Cell cellVinCom = fila.createCell(3);
				cellVinCom.setCellValue(vinculacionExpedienteDTOFinal.getComentario());
				cellVinCom.setCellStyle(styleTexto);
				sheet.autoSizeColumn(3);
				
			}			
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
			throw e;
		}
		
	
	}

}

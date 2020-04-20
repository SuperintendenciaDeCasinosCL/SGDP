package cl.gob.scj.sgdp.excel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cl.gob.scj.sgdp.dto.ElementoResultadoBusquedaDTO;


public class ExcelBuscador extends AbstractExcelView {


	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook
				.createSheet("Resumen Metas Solicitud Homologaci\u00F3n"); 

		setexcelHeader4(excelSheet, workbook);

		//List<Map<String, Object>> listaArreglos = (ArrayList<Map<String, Object>>) model
			//	.get("listaArreglos");
		
		List<ElementoResultadoBusquedaDTO> listaElementoResultadoBusquedaDTO =  
						(List<ElementoResultadoBusquedaDTO>) model.get("listaElementosResultadoBusquedaDTO");

		setExcelRows(excelSheet, listaElementoResultadoBusquedaDTO, workbook);
	}

	public void setexcelHeader4(HSSFSheet excelSheet, HSSFWorkbook workbook) {

		// create font
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(true);
		font.setItalic(false);

		// Create cell style
		CellStyle style = workbook.createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setBorderBottom(style.BORDER_MEDIUM);
        style.setBorderTop(style.BORDER_MEDIUM);
        style.setBorderRight(style.BORDER_MEDIUM);
        style.setBorderLeft(style.BORDER_MEDIUM);

		// Setting font to style
		style.setFont(font);

		HSSFRow excelHeader = excelSheet.createRow(1);

		// Setting cell style
		Cell cell = excelHeader.createCell(0);
		cell.setCellValue("Tipo");
		cell.setCellStyle(style);
		excelSheet.autoSizeColumn(0);

		Cell cell1 = excelHeader.createCell(1);
		cell1.setCellValue("Nombre");
		cell1.setCellStyle(style);
		excelSheet.autoSizeColumn(1);

		Cell cell2 = excelHeader.createCell(2);
		cell2.setCellValue("SubProceso");
		cell2.setCellStyle(style);
		excelSheet.autoSizeColumn(2);

		Cell cell3 = excelHeader.createCell(3);
		cell3.setCellValue("Materia");
		cell3.setCellStyle(style);
		excelSheet.autoSizeColumn(3);

		Cell cell4 = excelHeader.createCell(4);
		cell4.setCellValue("Creador");
		cell4.setCellStyle(style);
		excelSheet.autoSizeColumn(4);

		Cell cell5 = excelHeader.createCell(5);
		cell5.setCellValue("Fecha Inicio Proceso");
		cell5.setCellStyle(style);
		excelSheet.autoSizeColumn(5);

		Cell cell6 = excelHeader.createCell(6);
		cell6.setCellValue("Fecha Fin proceso");
		cell6.setCellStyle(style);
		excelSheet.autoSizeColumn(6);

		Cell cell7 = excelHeader.createCell(7);
		cell7.setCellValue("Estado Proceso");
		cell7.setCellStyle(style);
		excelSheet.autoSizeColumn(7);

		
	}

	public void setExcelRows(HSSFSheet excelSheet,List<ElementoResultadoBusquedaDTO> listaElementoResultadoBusquedaDTO 
							, HSSFWorkbook workbook) {

		
	
		// Create cell style
		CellStyle style = workbook.createCellStyle();
		// style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		

	
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		int record = 2;
		
		
		
        try {
			for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO : listaElementoResultadoBusquedaDTO) {
				
				HSSFRow excelRow = excelSheet.createRow(record++);
				
				Cell cell = excelRow.createCell(0);
				
				if (elementoResultadoBusquedaDTO.getTipoObjeto().equals("Documento")){        	
					cell.setCellValue(elementoResultadoBusquedaDTO.getTipoDeDocumento());
				}else{
					cell.setCellValue(elementoResultadoBusquedaDTO.getTipoObjeto());
				}
				cell.setCellStyle(style);

				
				
				Cell cell1 = excelRow.createCell(1);     	
				cell1.setCellValue(elementoResultadoBusquedaDTO.getNombreDeObjeto());
				cell1.setCellStyle(style);

				Cell cell2 = excelRow.createCell(2);
				cell2.setCellValue(elementoResultadoBusquedaDTO.getSubProceso());
				cell2.setCellStyle(style);

				Cell cell3 = excelRow.createCell(3);
				cell3.setCellValue(elementoResultadoBusquedaDTO.getMateria());
				cell3.setCellStyle(style);

				
				// Datos Para sacar el creador
				String creadorFinal = "";
				if (elementoResultadoBusquedaDTO.getTipoObjeto().equals("Documento")){     
					 if(elementoResultadoBusquedaDTO.getCreador().equals("")){
						 creadorFinal= "N/A"; 
					 }else{
						 creadorFinal = elementoResultadoBusquedaDTO.getCreador();
					 }
				}else{
					if(elementoResultadoBusquedaDTO.getAutor().equals("")){
						 creadorFinal= "N/A"; 
					}else{
						 creadorFinal = elementoResultadoBusquedaDTO.getAutor();
					}
				}
				
				Cell cell4 = excelRow.createCell(4);
				cell4.setCellValue(creadorFinal);
				cell4.setCellStyle(style);

				Cell cell5 = excelRow.createCell(5);
				if(elementoResultadoBusquedaDTO.getFechaInicioInstanciaDeProceso() == null ){
					cell5.setCellValue("N/A");
				}else{
					cell5.setCellValue(formatter.format(elementoResultadoBusquedaDTO.getFechaInicioInstanciaDeProceso()));
				}
				cell5.setCellStyle(style);

				Cell cell6 = excelRow.createCell(6);
				if(elementoResultadoBusquedaDTO.getFechaFinInstanciaDeProceso() == null ){
					 cell6.setCellValue("N/A");
				}else{
					 cell6.setCellValue(formatter.format(elementoResultadoBusquedaDTO.getFechaFinInstanciaDeProceso()));
				}
					 
				cell6.setCellStyle(style);

				Cell cell7 = excelRow.createCell(7);
				cell7.setCellValue(elementoResultadoBusquedaDTO.getNombreEstadoDeProceso());
				cell7.setCellStyle(style);
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		excelSheet.autoSizeColumn(0);
		excelSheet.autoSizeColumn(1);
		excelSheet.autoSizeColumn(2);
		excelSheet.autoSizeColumn(3);
		excelSheet.autoSizeColumn(4);
		excelSheet.autoSizeColumn(5);
		excelSheet.autoSizeColumn(7);

	}
}

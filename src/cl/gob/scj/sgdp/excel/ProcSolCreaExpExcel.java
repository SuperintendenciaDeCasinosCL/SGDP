package cl.gob.scj.sgdp.excel;

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

import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;

public class ProcSolCreaExpExcel extends AbstractExcelView {
	
	private static final Logger log = Logger.getLogger(ProcSolCreaExpExcel.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		@SuppressWarnings("unchecked")
		List<ProcesoDTO> todosLosProcVigentes = (List<ProcesoDTO>) model.get("todosLosProcVigentes");
		@SuppressWarnings("unchecked")
		List<ProcesoFormCreaExpDTO> todosLosProcFormCreaExp = (List<ProcesoFormCreaExpDTO>) model.get("todosLosProcFormCreaExp");
		
		response.setHeader("Content-disposition", "attachment; filename=\"MantenedorSubprocSolCreaExp.xls\"");		
		Sheet sheet = workbook.createSheet("MantenedorSubprocSolCreaExp");
		
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
		Cell nombreSubproceso = cabecera.createCell(0);
		nombreSubproceso.setCellValue("SubProcesos Vigentes");
		nombreSubproceso.setCellStyle(styleCabecera);
		sheet.autoSizeColumn(0);
		
		//segunda columna
		Cell codigoSubProceso = cabecera.createCell(1);
		codigoSubProceso.setCellValue("C\u00f3digo SubProceso");
		codigoSubProceso.setCellStyle(styleCabecera);
		sheet.autoSizeColumn(0);		
		
		//tercera columna
		Cell subProcSol = cabecera.createCell(2);
		subProcSol.setCellValue("SubProcesos para solicitud de creaci\u00f3n de expediente");
		subProcSol.setCellStyle(styleCabecera);
		sheet.autoSizeColumn(0);
		
		//cuarta columna
		Cell codSubProcSol = cabecera.createCell(3);
		codSubProcSol.setCellValue("C\u00f3digo SubProcesos para solicitud de creaci\u00f3n de expediente");
		codSubProcSol.setCellStyle(styleCabecera);
		sheet.autoSizeColumn(0);
		
		HSSFFont fontTitulo = workbook.createFont();
		fontTitulo.setFontHeightInPoints((short) 10);
		fontTitulo.setFontName("Arial");		
		fontTitulo.setBold(false);
		fontTitulo.setItalic(false);
		CellStyle styleTexto = workbook.createCellStyle();	
		styleTexto.setFont(fontTitulo);
		
		int indexFila = 2;
		
		for (ProcesoDTO procesoDTO : todosLosProcVigentes) {
			
			Row fila = sheet.createRow(indexFila++);			
			
			Cell cellNombreSubproceso = fila.createCell(0);
			cellNombreSubproceso.setCellValue(procesoDTO.getNombreProceso());
			cellNombreSubproceso.setCellStyle(styleTexto);
			sheet.autoSizeColumn(0);
			
			Cell cellCodSubproceso = fila.createCell(1);
			cellCodSubproceso.setCellValue(procesoDTO.getCodigoProceso());
			cellCodSubproceso.setCellStyle(styleTexto);
			sheet.autoSizeColumn(0);
			
		}
		
		indexFila = 2;
		
		for (ProcesoFormCreaExpDTO procesoFormCreaExpDTO: todosLosProcFormCreaExp) {
				
			Row fila = null;
			
			fila = sheet.getRow(indexFila);	
			
			if (fila==null) {
				fila = sheet.createRow(indexFila);
			}
			
			indexFila++;
			
			Cell cellNombreSubprocesoFormCreac = fila.createCell(2);
			cellNombreSubprocesoFormCreac.setCellValue(procesoFormCreaExpDTO.getNombreProceso());
			cellNombreSubprocesoFormCreac.setCellStyle(styleTexto);
			sheet.autoSizeColumn(0);
			
			Cell cellCodSubprocesoFormCreac = fila.createCell(3);
			cellCodSubprocesoFormCreac.setCellValue(procesoFormCreaExpDTO.getCodigoProceso());
			cellCodSubprocesoFormCreac.setCellStyle(styleTexto);
			sheet.autoSizeColumn(0);			
			
		}
		
	}

}

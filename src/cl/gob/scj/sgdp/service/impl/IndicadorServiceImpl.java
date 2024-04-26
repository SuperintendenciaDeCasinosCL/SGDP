package cl.gob.scj.sgdp.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.control.DetalleDeDocumentoControl;
import cl.gob.scj.sgdp.dao.IndicadorDao;
import cl.gob.scj.sgdp.dto.EntradaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.IndicadorDTO;
import cl.gob.scj.sgdp.dto.IndicadorSubprocesoDTO;
import cl.gob.scj.sgdp.dto.RespuestaBuscarSubprocesoPorIndicador;
import cl.gob.scj.sgdp.dto.SalidaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.SubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.SubprocesoIndicadoresSalidaDTO;
import cl.gob.scj.sgdp.service.IndicadorService;
import cl.gob.scj.sgdp.ws.rest.client.IndicadoresClientRestService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class IndicadorServiceImpl implements IndicadorService {

	private static final Logger log = Logger.getLogger(DetalleDeDocumentoControl.class);

	@Autowired
	IndicadoresClientRestService indicadoresClientRestService;
	
	@Autowired
	IndicadorDao indicadorDao;
	
	@Override
	public SalidaSubprocesoIndicadoresDTO buscarSubprocesoPorIdIndicador(
			EntradaSubprocesoIndicadoresDTO entradaSubprocesoIndicadoresDTO) {
		IndicadorDTO  indicadorDTO = new IndicadorDTO();
		indicadorDTO.setIndicadorId(entradaSubprocesoIndicadoresDTO.getIdIndicador());
		RespuestaBuscarSubprocesoPorIndicador respuestaBuscarSubprocesoPorIndicador = indicadoresClientRestService.buscarTodosSubprocesoPorIdIndicadorRest(indicadorDTO);
		
		
		SalidaSubprocesoIndicadoresDTO salidaSubprocesoIndicadoresDTO = new SalidaSubprocesoIndicadoresDTO();
		 Integer numeroElementosDentroDelPlazo = 0;
		 Integer numeroElementosfueraDelPlazo = 0;
		 Integer total = 0;
		
		
		try {
			// Lista para el inicio
			List<SubprocesoIndicadoresDTO> listaSubprocesoIndicadoresDTOInicio = new ArrayList<SubprocesoIndicadoresDTO>();

			
			
			// lista para el fin		
			List<SubprocesoIndicadoresDTO> listaSubprocesoIndicadoresDTOFin = new ArrayList<SubprocesoIndicadoresDTO>();
			

			
			
			for (IndicadorSubprocesoDTO indicadorSubprocesoDTO : respuestaBuscarSubprocesoPorIndicador.getListaIndicadorSubprocesoDTO()) {
				
				List<SubprocesoIndicadoresDTO> listaSubprocesoIndicadoresDTOSalidaInicio= indicadorDao.buscarTodosSubprocesoConTipoiPorIdSubProcesoInicio(indicadorSubprocesoDTO,"inicio",entradaSubprocesoIndicadoresDTO);
				listaSubprocesoIndicadoresDTOInicio.addAll(listaSubprocesoIndicadoresDTOSalidaInicio);
				
				List<SubprocesoIndicadoresDTO> listaSubprocesoIndicadoresDTOSaludaFin= indicadorDao.buscarTodosSubprocesoConTipoiPorIdSubProcesoInicio(indicadorSubprocesoDTO,"fin",entradaSubprocesoIndicadoresDTO);
				listaSubprocesoIndicadoresDTOFin.addAll(listaSubprocesoIndicadoresDTOSaludaFin);
				
			}
			
			
			
			
			// lista salida vista
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			List<SubprocesoIndicadoresSalidaDTO> listaSubprocesoIndicadoresSalidaDTO=new ArrayList<SubprocesoIndicadoresSalidaDTO>();
			
			for (SubprocesoIndicadoresDTO subprocesoIndicadoresDTOInicio : listaSubprocesoIndicadoresDTOInicio) {
				
				//String expDuplicado = subprocesoIndicadoresDTOFin.getNombreExpediente();
				
				for (SubprocesoIndicadoresDTO subprocesoIndicadoresDTOFin : listaSubprocesoIndicadoresDTOFin) {
					
					if (subprocesoIndicadoresDTOInicio.getNombreExpediente().equals(subprocesoIndicadoresDTOFin.getNombreExpediente())){
							
						
						
						String fechaInicio = "";
						String fechaFin = "";
						String mensajeFechaVacio = "sin fecha";
						String duracionTexto = "No se puede definir";
						
						
						if (subprocesoIndicadoresDTOInicio.getTipoDeInicio().equals("Documento")){
						
						  if (subprocesoIndicadoresDTOInicio.getFechaInicioDocumento() == null || subprocesoIndicadoresDTOInicio.getFechaInicioDocumento().toString().equals("null")){
							   fechaInicio = mensajeFechaVacio;
						  }else {
								fechaInicio = dateFormat.format(subprocesoIndicadoresDTOInicio.getFechaInicioDocumento()); 
						  }
							
			
							
						}else if (subprocesoIndicadoresDTOInicio.getTipoDeInicio().equals("Tarea")){			
							
							  if (subprocesoIndicadoresDTOInicio.getFechaTareaInicio() == null || subprocesoIndicadoresDTOInicio.getFechaTareaInicio().toString().equals("null")){							  
								  fechaInicio = mensajeFechaVacio;
							  }else{
								  fechaInicio = dateFormat.format(subprocesoIndicadoresDTOInicio.getFechaTareaInicio());
							  }
						}
						
						
						
						if (subprocesoIndicadoresDTOFin.getTipoFin().equals("Documento")){
							
							
							 if (subprocesoIndicadoresDTOFin.getFechaFinDocumento() == null || subprocesoIndicadoresDTOFin.getFechaFinDocumento().toString().equals("null")){
								 	fechaFin = mensajeFechaVacio;
							 }else{
									fechaFin = dateFormat.format(subprocesoIndicadoresDTOFin.getFechaFinDocumento());
							 }
							
						
							
						}else if (subprocesoIndicadoresDTOFin.getTipoFin().equals("Tarea")){
							
							 if (subprocesoIndicadoresDTOFin.getFechaTareaFin() == null || subprocesoIndicadoresDTOFin.getFechaTareaFin().toString().equals("null")){
									fechaFin = mensajeFechaVacio;
							 }else{
									fechaFin = dateFormat.format(subprocesoIndicadoresDTOFin.getFechaTareaFin());
							 }
							
						}
						
						// Validar si te toma la fecha de hoy
						
						String marcaFechaHoy = "";
						String duracion="";
						Date fechaInicioDate = null;
						Date fechaFinDate = null;
						long totalDias= 0;
						
						 
						if (fechaFin.equals(mensajeFechaVacio)){
								
								 fechaFinDate = new Date();
								 marcaFechaHoy = "A";

						}else{
								fechaFinDate = (Date) dateFormat.parse(fechaFin);
						}
							
						
						
						
						 if (!fechaInicio.equals(mensajeFechaVacio)){
							 
							 fechaInicioDate = (Date) dateFormat.parse(fechaInicio);
							 							
							totalDias = fechaFinDate.getTime() - fechaInicioDate.getTime();
							 
							totalDias = Math.round(totalDias / 86400000);
							 
							duracion = Long.toString(totalDias);
						 }else{
							 duracion = duracionTexto;
						 }
						
						 
				      // Se define marca y contador
						 
						 String  marcaDuracion= "";

			
						 if (duracion.equals(duracionTexto)){
							 numeroElementosfueraDelPlazo = numeroElementosfueraDelPlazo + 1;
							 marcaDuracion = "R"; // ROJO
							 
						 }else if (Integer.parseInt(duracion) <= subprocesoIndicadoresDTOFin.getDuracionEsperada()) {
							 
							 if (Integer.parseInt(duracion) < 0){
								 marcaDuracion = "A"; // AMARILLO
							 }else{
								 marcaDuracion = "V"; // VERDE
							 }
							 
							 numeroElementosDentroDelPlazo = numeroElementosDentroDelPlazo + 1;
				
						 }else{
							 numeroElementosfueraDelPlazo = numeroElementosfueraDelPlazo + 1;
							 marcaDuracion = "R"; // ROJO
						 }
						 
							total = total + 1;
						
						 fechaFin = dateFormat.format(fechaFinDate);
						
						//-------------------------------------------------
				
						SubprocesoIndicadoresSalidaDTO subprocesoIndicadoresSalidaDTO= new SubprocesoIndicadoresSalidaDTO();
						subprocesoIndicadoresSalidaDTO.setMarcaFechaHasta(marcaFechaHoy);
						subprocesoIndicadoresSalidaDTO.setNombreSubproceso(subprocesoIndicadoresDTOFin.getNombreProceso());
						subprocesoIndicadoresSalidaDTO.setNumeroExpediente(subprocesoIndicadoresDTOFin.getNombreExpediente());
						subprocesoIndicadoresSalidaDTO.setEstado(subprocesoIndicadoresDTOFin.getNombreEstadoDeProceso());
						
						subprocesoIndicadoresSalidaDTO.setFechaInicioElementoMedido(fechaInicio);
						subprocesoIndicadoresSalidaDTO.setFechaFinElementoMedido(fechaFin);
						subprocesoIndicadoresSalidaDTO.setDuracion(duracion);
						subprocesoIndicadoresSalidaDTO.setDuracionProgramadaProceso(subprocesoIndicadoresDTOFin.getDuracionEsperada());
						subprocesoIndicadoresSalidaDTO.setTipoDeInicio(subprocesoIndicadoresDTOFin.getTipoDeInicio());
						subprocesoIndicadoresSalidaDTO.setTipoFin(subprocesoIndicadoresDTOFin.getTipoFin());
						
						subprocesoIndicadoresSalidaDTO.setMarcaDuracion(marcaDuracion);
						
						//MIG
//						SubprocesoIndicadoresSalidaDTO subprocesoIndicadoresSalidaDTOAUX =   new  SubprocesoIndicadoresSalidaDTO();
//						for (SubprocesoIndicadoresDTO subprocesoIndicadoresDTOFinAux : listaSubprocesoIndicadoresDTOFin) {
//							if(subprocesoIndicadoresSalidaDTO.getNumeroExpediente().equalsIgnoreCase(subprocesoIndicadoresDTOFinAux.getNombreExpediente())){
//								if(subprocesoIndicadoresSalidaDTO.getDuracionProgramadaProceso() >= subprocesoIndicadoresDTOFinAux.getDuracionEsperada() ) {
//									//listaSubprocesoIndicadoresSalidaDTO.add(subprocesoIndicadoresSalidaDTO);
//									subprocesoIndicadoresSalidaDTOAUX = subprocesoIndicadoresSalidaDTO;
//								}
//							}
//						}
					
						listaSubprocesoIndicadoresSalidaDTO.add(subprocesoIndicadoresSalidaDTO);
						
						
					
						
					}
					
				}

			}
			
			log.info("listaSubprocesoIndicadoresSalidaDTO: "+listaSubprocesoIndicadoresSalidaDTO);
			List<SubprocesoIndicadoresSalidaDTO> listaSubprocesoIndicadoresSalidaDTOReponse=new ArrayList<SubprocesoIndicadoresSalidaDTO>();
			
			if(!listaSubprocesoIndicadoresSalidaDTO.isEmpty() && listaSubprocesoIndicadoresSalidaDTO != null) {			
			
				List<SubprocesoIndicadoresSalidaDTO> listaSubprocesoIndicadoresSalidaDTOSalida=new ArrayList<SubprocesoIndicadoresSalidaDTO>();
				
				
				for (SubprocesoIndicadoresSalidaDTO subprocesoIndicadoresSalidaDTOAUX : listaSubprocesoIndicadoresSalidaDTO) {
					SubprocesoIndicadoresSalidaDTO subprocesoIndicadoresSalidaParaLista =   new  SubprocesoIndicadoresSalidaDTO();
					subprocesoIndicadoresSalidaParaLista.setDuracion("-10000");
					
						if(!subprocesoIndicadoresSalidaDTOAUX.getDuracion().equalsIgnoreCase("No se puede definir")) {						
						
							for (SubprocesoIndicadoresSalidaDTO subprocesoIndicadoresSalidaDTOAUX2 : listaSubprocesoIndicadoresSalidaDTO) {	
								if(!subprocesoIndicadoresSalidaDTOAUX2.getDuracion().equalsIgnoreCase("No se puede definir")) {
									if(subprocesoIndicadoresSalidaDTOAUX.getNumeroExpediente().equalsIgnoreCase(subprocesoIndicadoresSalidaDTOAUX2.getNumeroExpediente())){
										
										if(Integer.parseInt(subprocesoIndicadoresSalidaDTOAUX.getDuracion()) >= Integer.parseInt(subprocesoIndicadoresSalidaDTOAUX2.getDuracion())
												&& Integer.parseInt(subprocesoIndicadoresSalidaDTOAUX.getDuracion()) > Integer.parseInt(subprocesoIndicadoresSalidaParaLista.getDuracion())) {							
											subprocesoIndicadoresSalidaParaLista = subprocesoIndicadoresSalidaDTOAUX;
											
										}else if (Integer.parseInt(subprocesoIndicadoresSalidaDTOAUX2.getDuracion()) > Integer.parseInt(subprocesoIndicadoresSalidaParaLista.getDuracion()) ){
											subprocesoIndicadoresSalidaParaLista = subprocesoIndicadoresSalidaDTOAUX2;
										}
									}							
								}
							}
						}else {
							subprocesoIndicadoresSalidaParaLista = subprocesoIndicadoresSalidaDTOAUX;
						}
					listaSubprocesoIndicadoresSalidaDTOSalida.add(subprocesoIndicadoresSalidaParaLista);
				}
				
				
				
				for(SubprocesoIndicadoresSalidaDTO list : listaSubprocesoIndicadoresSalidaDTOSalida) {
					if ( !listaSubprocesoIndicadoresSalidaDTOReponse.contains(list)) {
						listaSubprocesoIndicadoresSalidaDTOReponse.add(list);
					}
				}

			}

			salidaSubprocesoIndicadoresDTO.setTotal(total);
			salidaSubprocesoIndicadoresDTO.setNumeroElementosDentroDelPlazo(numeroElementosDentroDelPlazo);
			salidaSubprocesoIndicadoresDTO.setNumeroElementosfueraDelPlazo(numeroElementosfueraDelPlazo);
			
			
			salidaSubprocesoIndicadoresDTO.setSubprocesoIndicadoresSalidaDTO(listaSubprocesoIndicadoresSalidaDTOReponse);
			salidaSubprocesoIndicadoresDTO.setRespuesta("OK");
			return salidaSubprocesoIndicadoresDTO;
		} catch (Exception e) {
			salidaSubprocesoIndicadoresDTO.setRespuesta("ERROR");
			salidaSubprocesoIndicadoresDTO.setDescripcionRespuesta(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return salidaSubprocesoIndicadoresDTO;
		}
	}

}

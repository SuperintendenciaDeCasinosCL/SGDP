package cl.gob.scj.sgdp.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.control.DetalleDeDocumentoControl;
import cl.gob.scj.sgdp.dao.IndicadorDao;
import cl.gob.scj.sgdp.dto.EntradaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.IndicadorSubprocesoDTO;
import cl.gob.scj.sgdp.dto.SubprocesoIndicadoresDTO;	

@Repository
public class IndicadorDaoImpl implements IndicadorDao {

	
	@Autowired
	private SessionFactory sessionFactory;// para que se comunique con el DAO

	private static final Logger log = Logger.getLogger(DetalleDeDocumentoControl.class);

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	@Override
	public List<SubprocesoIndicadoresDTO> buscarTodosSubprocesoConTipoiPorIdSubProcesoInicio(IndicadorSubprocesoDTO indicadorSubprocesoDTO,
			String tipoFecha,EntradaSubprocesoIndicadoresDTO entradaSubprocesoIndicadoresDTO) {

   String queryHQL = "";
	
  
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
   
   queryHQL = queryHQL + " select DISTINCT new cl.gob.scj.sgdp.dto.SubprocesoIndicadoresDTO( "
   			+ " pp.nombreProceso ,"
		   + " ip.nombreExpediente , "
		   + " ep.nombreEstadoDeProceso ,";			   
   					   
   

   
      if(tipoFecha.equals("inicio")){
	    	  
			   if(indicadorSubprocesoDTO.getTipoDeInicio().equals("Documento")){
				   
			
				   queryHQL = queryHQL +  " ( select max(ait2.fechaDocumento) "
							   + " from InstanciaDeTarea it2 "
							   + " inner join it2.archivosInstDeTarea ait2 "
							   + " inner join ait2.tipoDeDocumento td2"
							   + " where it2.idInstanciaDeTarea = it.idInstanciaDeTarea"
							   + " and td2.idTipoDeDocumento = :idTipoDeDocumentoParametro  ) as fechaInicioDocumento , ";
			   }else{
				   queryHQL = queryHQL + " it.fechaInicio as fechaInicioDocumento,"; //  Esta fecha se tiene que dejar en null no se usa porque es una tarea
			   }
			   
			
			
			   
			   queryHQL = queryHQL + "( select max(hit3.fechaMovimiento) from InstanciaDeTarea it3 "
			   						+ " inner join it3.historicosDeInstDeTareasDeDestino hit3 "
			   						+ " inner join it3.tarea tt3 "
			   						+ " inner join hit3.accionHistInstDeTarea ahit3 "
			   						+ " where ( (ahit3.idAccionHistoricoInstDeTarea  = 1 AND tt3.orden =1 AND it3.idInstanciaDeTarea = it.idInstanciaDeTarea and ep.idEstadoDeProceso != 4  ) "
			   						+ " or ( ahit3.idAccionHistoricoInstDeTarea  in (3,6 ) AND tt3.orden <> 1  AND it3.idInstanciaDeTarea = it.idInstanciaDeTarea and ep.idEstadoDeProceso != 4  )) "
			   						+ ") as fechaTareaInicio "
			   						+ "  , it.fechaInicio as fechaFinDocumento , it.fechaInicio as fechaTareaFin )"; // fecha de fin no se usan porque es de inicio
			   
   
      }else if(tipoFecha.equals("fin")){
    	  
    	  
		   queryHQL = queryHQL +  "   it.fechaInicio as fechaInicioDocumento , it.fechaInicio as fechaTareaInicio,"; // fechas de inicio no se ocupan porque es de fin

    	  
    	   if(indicadorSubprocesoDTO.getTipoFin().equals("Documento")){
			   
    		   
			   queryHQL = queryHQL +  " ( select max(ait2.fechaDocumento) "
						   + " from InstanciaDeTarea it2 "
						   + " inner join it2.archivosInstDeTarea ait2 "
						   + " inner join ait2.tipoDeDocumento td2"
						   + " where it2.idInstanciaDeTarea = it.idInstanciaDeTarea"
						   + " and td2.idTipoDeDocumento = :idTipoDeDocumentoParametro  ) as fechaFinDocumento , ";
		   }else{
			   queryHQL = queryHQL + " it.fechaInicio as fechaFinDocumento ,"; // Esta fecha se tiene que dejar en null no se usa porque es una tarea
		   }
		   
		
		
		   
		   queryHQL = queryHQL + "( select max(hit3.fechaMovimiento) from InstanciaDeTarea it3 "
		   						+ " inner join it3.historicosDeInstDeTareasDeDestino hit3 "
		   						+ " inner join it3.tarea tt3 "
		   						+ " inner join hit3.accionHistInstDeTarea ahit3 "
		   						+ " where ( (ahit3.idAccionHistoricoInstDeTarea  = 1 AND tt3.orden =1 AND it3.idInstanciaDeTarea = it.idInstanciaDeTarea  and ep.idEstadoDeProceso != 4 ) "
		   						+ " or ( ahit3.idAccionHistoricoInstDeTarea  in (3,6 ) AND tt3.orden <> 1  AND it3.idInstanciaDeTarea = it.idInstanciaDeTarea and ep.idEstadoDeProceso != 4  )) "
		   						+ ") as fechaTareaFin "
		   						+ " )";
    	  
    	  
      }
   
   queryHQL = queryHQL +  "from Proceso pp "
   					+ "	inner join  pp.instanciasDeProcesos ip "
   					+ " inner join ip.instanciasDeTareas it "
   					+ " inner join ip.estadoDeProceso ep  "
   					+ "	inner join it.historicosDeInstDeTareasDeDestino hit "
   					+ " inner join it.tarea tt  "
   					+ " inner join hit.accionHistInstDeTarea ahit "
   					+ " where pp.idProceso = :idProceso "
   					+ " and tt.idTarea = :idTarea "
   					+ " and ( (ahit.idAccionHistoricoInstDeTarea  = 1 AND tt.orden =1 and ep.idEstadoDeProceso != 4 ) "
   					+ " or ( ahit.idAccionHistoricoInstDeTarea  in (3,6) AND tt.orden <> 1 and ep.idEstadoDeProceso != 4  )) ";
   
   
   
   if(entradaSubprocesoIndicadoresDTO.getFlagFechaSubprocesoCombo().equals("inicio")){
	   
	   queryHQL = queryHQL + "and ip.fechaInicio  BETWEEN :fechaDesde and :fechaHasta ";
	   
   }else if(entradaSubprocesoIndicadoresDTO.getFlagFechaSubprocesoCombo().equals("fin")){
	   
	   queryHQL = queryHQL + "and ip.fechaFin  BETWEEN :fechaDesde and :fechaHasta "
	   					   + " and ep.idEstadoDeProceso = 3";

	   
   }
   
   
   
	Query query = getSession().createQuery(queryHQL);
   
	
	 if(tipoFecha.equals("inicio")){
		 
	
		query.setInteger("idProceso", indicadorSubprocesoDTO.getSubprocesoId());
		query.setInteger("idTarea", indicadorSubprocesoDTO.getIdTareaInicio());
		 if(indicadorSubprocesoDTO.getTipoDeInicio().equals("Documento")){
			 query.setInteger("idTipoDeDocumentoParametro", indicadorSubprocesoDTO.getIdDocumentoInicio()) ;
		 }
		
	  }else if(tipoFecha.equals("fin")){
		  
		  query.setInteger("idProceso", indicadorSubprocesoDTO.getSubprocesoId());
			query.setInteger("idTarea", indicadorSubprocesoDTO.getIdTareaFin());
			 if(indicadorSubprocesoDTO.getTipoFin().equals("Documento")){
				 query.setInteger("idTipoDeDocumentoParametro", indicadorSubprocesoDTO.getIdDocumentoFin()) ;
			 }
		  
	  }
	 
	 try {
		query.setDate("fechaDesde", dateFormat.parse(entradaSubprocesoIndicadoresDTO.getFechaDesde()));
		query.setDate("fechaHasta", dateFormat.parse(entradaSubprocesoIndicadoresDTO.getFechaHasta()));	  	 
	} catch (ParseException e) {
		log.debug("error al trasformar la fecha desde y fecha hasta metodo buscarTodosSubprocesoConTipoiPorIdSubProcesoInicio, modulo indicadores)");
		e.printStackTrace();
	}
	
	 
	 List<SubprocesoIndicadoresDTO> listaProcesosIndicadores = query.list();
	 
	 List<SubprocesoIndicadoresDTO> listaProcesosIndicadoresSalida = new  ArrayList<SubprocesoIndicadoresDTO>();
	 
	 for (SubprocesoIndicadoresDTO subprocesoIndicadoresDTO : listaProcesosIndicadores) {
		 subprocesoIndicadoresDTO.setDuracionEsperada(indicadorSubprocesoDTO.getDuracionEsperada());
		 subprocesoIndicadoresDTO.setTipoDeInicio(indicadorSubprocesoDTO.getTipoDeInicio());
		 subprocesoIndicadoresDTO.setTipoFin(indicadorSubprocesoDTO.getTipoFin());
		 
		 listaProcesosIndicadoresSalida.add(subprocesoIndicadoresDTO);
	 }
	 
	 
	 return   listaProcesosIndicadoresSalida;
	
   
	}

}

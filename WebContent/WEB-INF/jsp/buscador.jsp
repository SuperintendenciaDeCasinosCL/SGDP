<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:set var="filtrosDeBusquedaValues" value="<%=FiltroDeBusquedaType.values()%>"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
	
		<title>Busqueda</title>

		<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>
	
	</head>

	<body>
		
		<div class="container-fluid container-sgdp">
		
			<div class="row content">
			
				<div class="col-sm-2 sidenav">		      
			      <c:import url="/WEB-INF/jsp/objetos/menu.jsp"></c:import>		      
			    </div>
			    
			    <div class="col-sm-10">	
			    
			    	<c:choose>
						<c:when test = "${usuario.fueraDeOficina eq true}">
							<c:set var="colorBackHeaderBuscador" value="#FFD51D" />
							<c:set var="mensajeBuscador" value="Buscador (Fuera de Oficina)" />
						</c:when>
						<c:otherwise>
							<c:set var="colorBackHeaderBuscador" value="#41CAC0" />
							<c:set var="mensajeBuscador" value="Buscador" />							
						</c:otherwise>						
					</c:choose> 
					
					<div id="divBackHeaderBuscador" class="row" style="background-color: ${colorBackHeaderBuscador}; color: #fff;">
			    	
			    		<div class="col-sm-1"></div>
			    	
			    		<div class="col-sm-9"><h2 id="h2MensajeBuscador">${mensajeBuscador}</h2></div>
			    		
			    		<div class="col-sm-2"><c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import></div> 
			    	
			    	</div>   	
		      
		      		<hr>
		      		
		      		<c:if test = "${mensajeVistaDTO.mensajes ne null and !empty mensajeVistaDTO.mensajes}">
		            	<br><br>
	            		<div class="row">	
	            		
	            			<%-- <div class="col-sm-1"></div>--%>	            			
					
							<div class="col-sm-12" id="mensajeria">
						
								<c:import url="/WEB-INF/jsp/div/mensajeria.jsp"></c:import>
						
							</div>                   	
	                   
						</div>
	            
	            	</c:if>
			
					<div class="row">
						
						<div id="divFormBuscar">
						
							<form id="formBuscar" class="form-horizontal">
							  <div class="form-group">
							    <label class="control-label col-sm-3" for="palabraClave">Ingrese Palabra clave:</label>
							    <div class="col-sm-5">
							      <input type="text" class="form-control" id="palabraClave" name="palabraClave" />
							    </div>
							    <div class="col-sm-4 hidden">
							      <input type="checkbox" name="soloEnMiBandejaDeSalida" id="soloEnMiBandejaDeSalida" onclick="buscaSoloEnMiBandejaDeSalida()" 
										  	value=true />
										  	Solo en Mi Bandeja de Salida
							    </div>
							  </div>
							  	
							  	
							  <!--  
							  <div class="form-group">
							  	<label class="control-label col-sm-3" for="filtrosBusqueda">Tipo:</label>
							  	<div class="col-sm-5">
							      <select class="form-control select2-filtros-busqueda-multiple" id="filtrosBusqueda" name="filtrosBusqueda" multiple>
								    <c:forEach items="${filtrosDeBusquedaValues}" var="enumValue">
									   <option value="${enumValue.codigoFiltroDeBusquedaType}">${enumValue.nombreFiltroDeBusquedaType}</option>
									</c:forEach>								    
								  </select>
							    </div>
							  </div>	
							  -->
							  
							 <div class="form-group">
							  	<label class="control-label col-sm-3" for="filtrosBusqueda">Tipo de Documento:</label>
							 <div class="col-sm-5">
								 <input type="hidden" id="nombreTipoDocumento" name="nombreTipoDocumento"/>
							 
							      <select class="form-control select2-filtros-busqueda-multiple" id="filtroNombreTipoDocumento" name="filtroNombreTipoDocumento" multiple="multiple">
								       <option value="">Seleccione Tipo Documento</option>
								    <c:forEach items="${listaNombreTipoDocumento}" var="nombreTipoDocumento">
									   <option value="${nombreTipoDocumento}">${nombreTipoDocumento}</option>
									</c:forEach>	 					    
								  </select> 
								  
							    </div>
							  
							  </div>				  
							<div class="form-group">
							  	<label class="control-label col-sm-3" for="filtrosBusqueda">Subproceso:</label>
							    <div class="col-sm-5">
							    
							     <input type="hidden" id="nombreSubprocesoVigente" name="nombreSubprocesoVigente"/>
							      <select class="form-control select2-filtros-busqueda-multiple" id="filtroNombreSubprocesoVigente" name="filtroNombreSubprocesoVigente" multiple="multiple">
								       <option value="">Seleccione Subproceso</option>
								    <c:forEach items="${listaNombreProcesosVigentes}" var="nombreProcesosVigentes">
									   <option value="${nombreProcesosVigentes}">${nombreProcesosVigentes}</option>
									</c:forEach>	 					    
								  </select> 
							   							   
							    </div>
							 
							  </div>  
							  					
							  <div class="form-group">
							    <label class="control-label col-sm-3" for="fechaInicio">Fecha Inicio:</label>
							    <div class="col-sm-5">							      						     
									<div class='input-group date' id='fechaInicioDate'>
		                   				<input type='text' class="form-control validate[validaRangoFecha[fechaInicio,fechaFin]] " id="fechaInicio" name="fechaInicio" placeholder='__/__/____' />
		                   				<span class="input-group-addon">
		                       				<span class="glyphicon glyphicon-calendar"></span>
		                   				</span>
		               				</div>
							    </div>
							  </div>
							  <div class="form-group">
							    <label class="control-label col-sm-3" for="fechaFin">Fecha Fin:</label>
							    <div class="col-sm-5"> 							      
							      	<div class='input-group date' id='fechaFinDate'>
		                   				<input type='text' class="form-control  validate[validaRangoFecha[fechaInicio,fechaFin]]" id="fechaFin" name="fechaFin" placeholder='__/__/____' />
		                   				<span class="input-group-addon">
		                       				<span class="glyphicon glyphicon-calendar"></span>
		                   				</span>
		               				</div>
							    </div>
							  </div>							   
							  <div class="form-group"> 
							    <div class="col-sm-offset-3 col-sm-5">		
							    
							    	<c:forEach items="${parametrosPorContextoDTOTiposDeObjetosCMSSGDP}" var="parametroPorContextoDTOTiposDeObjetosCMSSGDP">										
										<div class="checkbox">
										  <label>
										  	<input type="checkbox" class="tipoObjeto" name="tipoDeObjeto" 
										  	value="${parametroPorContextoDTOTiposDeObjetosCMSSGDP.valorParametroChar}" />
										  	${parametroPorContextoDTOTiposDeObjetosCMSSGDP.valorContexto}
										  </label>
										</div>
									</c:forEach>					    
							      	
							    </div>
							  </div>							 
							  <div class="form-group"> 
							    <div class="col-sm-offset-3 col-sm-5">
							      <button type="button" id="botonBuscar" onclick="cargaResultadoBusqueda();" class="btn btn-default btn-block">
							      	<span class="glyphicon glyphicon-search"></span> Buscar
							      </button>
							    </div>
							  </div>							 
							</form>
						
						</div>				
	                   
					</div>	
					
					<div  class="row">
					
						<div id="divResultadoBusqueda">				
							
						
						</div>
						
					</div>
			    
			    </div>
			
			</div>
		
		</div>	
		
		<!-- Modal Detalle De Proceso -->
					
		<c:import url="/WEB-INF/jsp/modals/detalleDeProceso.jsp"></c:import>
		
		<c:import url="/WEB-INF/jsp/modals/historialDeTareas.jsp"></c:import>
		
		<!-- Modal Detalle Documento-->
	
	    <c:import url="/WEB-INF/jsp/modals/detalleDeDocumento.jsp"></c:import>
		
	</body>
	
<script>
	document.addEventListener("keyup", function(event) {
		  if (event.keyCode === 13) {
		   event.preventDefault();
		   document.getElementById("botonBuscar").click();
		  }
	});
	$(function() {
	    $('#fechaInicioDate').datetimepicker({
	          locale : 'es',
	          format : 'DD/MM/YYYY'
	    });
	    $('#fechaFinDate').datetimepicker({
	        locale : 'es',
	        format : 'DD/MM/YYYY'
	  });
	});
	var inicializaSelect2MultipleFiltrosDeBusqueda = function(){
		$(".select2-filtros-busqueda-multiple").select2({
 		    theme: "bootstrap",
 		    dropdownParent: $("#divFormBuscar"),
 		    language: "es"
 		});
	};
	var checkeaTipoDeElemento = function () {
		$('input[type=checkbox]').each(function () {
		    console.log($(this).val());
		    if ($(this).val() == 'CARPETA') {
		    	 $(this).attr('checked', true);
			}   
		   
		});
	};
	function callOpenerCargaResultadoBusqueda() {
		cargaResultadoBusqueda();
	}
	$(document).ready(function() {
		$(inicializaSelect2MultipleFiltrosDeBusqueda);
		//$(checkeaTipoDeElemento);	
	});
</script>
	
</html>


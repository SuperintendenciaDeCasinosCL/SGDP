<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>

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
			    	
			    		<div class="col-sm-9"><h2 id="h2MensajeBuscador">Notificación Predeterminada</h2></div>
			    		
			    		<div class="col-sm-2"><c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import></div> 
			    	
			    	</div>   	
		      
		      		<hr>
			    
   					<div class="col-sm-12" id="notificarTareaCuerpo">
				
						<c:import url="/WEB-INF/jsp/div/notificarTareaCuerpo.jsp"></c:import>
				
					</div>  
			    
			    
			    
			    </div>
			
			</div>
		
		</div>	
	
		
	</body>
	
<script>
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
		$(".select2-filtros-busqueda-multiple").select2();
	};
	var checkeaTipoDeElemento = function () {
		$('input[type=checkbox]').each(function () {
		    console.log($(this).val());
		    if ($(this).val() == 'CARPETA') {
		    	 $(this).attr('checked', true);
			}   
		   
		});
	};
	$(document).ready(function() {
		$(inicializaSelect2MultipleFiltrosDeBusqueda);
		//$(checkeaTipoDeElemento);	
	});
</script>
	
</html>


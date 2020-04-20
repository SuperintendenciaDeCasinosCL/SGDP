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
							<c:set var="colorBackHeaderMantenedorParametros" value="#FFD51D" />
							<c:set var="mensajeMantenedorParametros" value="Mantenedor de par&aacute;metros (Fuera de Oficina)" />
						</c:when>
						<c:otherwise>
							<c:set var="colorBackHeaderMantenedorParametros" value="#41CAC0" />
							<c:set var="mensajeMantenedorParametros" value="Mantenedor de par&aacute;metros" />							
						</c:otherwise>						
					</c:choose> 
					
					<div id="divBackHeaderMantenedorParametros" class="row" style="background-color: ${colorBackHeaderMantenedorParametros}; color: #fff;">
			    	
			    		<div class="col-sm-1"></div>
			    	
			    		<div class="col-sm-9"><h2 id="h2MensajeMantenedorParametros">${mensajeMantenedorParametros}</h2></div>
			    		
			    		<div class="col-sm-2"><c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import></div> 
			    	
			    	</div>   	
		      
		      		<hr>
			    
   					<div class="col-sm-12" id="mantenedorParametrosCuerpo">
				    	<c:import url="/WEB-INF/jsp/div/mantenedorParametrosCuerpo.jsp"></c:import>   	 		
					</div>  
			    
			    
			    </div>
			
			</div>
		
		</div>	
	
		
	</body>
	
<script>
	
</script>
	
</html>


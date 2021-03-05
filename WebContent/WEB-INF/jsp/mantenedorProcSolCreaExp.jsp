<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

		<head>
	
		<title>Mantenedor Subprocesos Solicitud Creaci&oacute;n Expediente</title>

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
							<c:set var="colorBackHeaderMantenedorProcCreacExp" value="#FFD51D" />
							<c:set var="mensajeMantenedorProcCreacExp" value="Mantenedor Subprocesos Solicitud Creaci&oacute;n Expediente (Fuera de Oficina)" />
						</c:when>
						<c:otherwise>
							<c:set var="colorBackHeaderMantenedorProcCreacExp" value="#41CAC0" />
							<c:set var="mensajeMantenedorProcCreacExp" value="Mantenedor Subprocesos Solicitud Creaci&oacute;n Expediente" />							
						</c:otherwise>						
					</c:choose> 
					
					<div id="divBackHeaderMantenedorProcCreacExp" class="row div-area-trabajo-cab" style="background-color: ${colorBackHeaderMantenedorProcCreacExp}; color: #fff;">
			    	
			    		<div class="col-sm-1"></div>
			    	
			    		<div class="col-sm-9"><h2 id="h2MensajeMantenedorProcCreacExp">${mensajeMantenedorProcCreacExp}</h2></div>
			    		
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
	            	
	            		<div class="col-sm-12" id="mantProcSolCreaExpDiv">
	            			
				    		<c:import url="/WEB-INF/jsp/div/mantProcSolCreaExp.jsp"></c:import>
				    			 		
						</div>
				   		
	            	</div> 			     
			     
			     </div>
			
			</div>		
		
		</div>
		
	</body>
	
</html>
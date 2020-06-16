<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<c:set var="permisoSubirCarta" value="<%=PermisoType.SUBIR_CARTA.getNombrePermiso()%>"/>				
			
	<div id="page-wrapper" style="min-height: 218px; padding: 0px; padding-right: 15px;">

		<div class="row" style=" background-color: #41CAC0; padding-left: 30px; color: #fff;">
			                
              <h3><spring:message code="bandejaDeEntrada.cabecera.nombre"/></h3>
              
           </div>
           
           <c:if test = "${mensajeVista.mensajes ne null and !empty mensajeVista.mensajes}">
           
           	<div class="row" style="padding-left: 30px;">
			
				<div id="mensajeria" class="panel-body">
				
					<c:import url="/WEB-INF/jsp/div/mensajeria.jsp"></c:import>
				
				</div>                   	
                  
			</div>
           
           </c:if>

		<div class="row" style="padding-left: 30px;">
			
			<div id="tareasBandejaDeEntrada" class="panel-body">
			
				<c:import url="/WEB-INF/jsp/div/tareasBandejaDeEntrada.jsp"></c:import>
			
			</div>
                 
		</div>
		
		<c:if test = "${instanciasDeTareasDTOEnEjecucion ne null and !empty instanciasDeTareasDTOEnEjecucion}">
		
			<div class="row" style="padding-left: 30px;">
			
				<div id="tareasEnEjecucion" class="panel-body">
				
					<c:import url="/WEB-INF/jsp/div/tareasEnEjecucion.jsp"></c:import>
				
				</div>                   	
                  
			</div>
		
		</c:if>		
		
		
		<div  class="row" style="padding-left: 30px;">
		
			<div id="divDetalleDeTarea" class="panel-body">
			
			</div>
			
		</div>
	
	</div>			
		
	<!-- Modal Crear Expediente -->
	
	<c:import url="/WEB-INF/jsp/modals/crearExpediente.jsp"></c:import>		
	
	<!-- Modal Continuar Proceso -->
	
	<c:import url="/WEB-INF/jsp/modals/continuarProceso.jsp"></c:import>
	
	<!-- Modal Subir Documento-->
	
	<c:import url="/WEB-INF/jsp/modals/subirDocumento.jsp"></c:import>	
		
	
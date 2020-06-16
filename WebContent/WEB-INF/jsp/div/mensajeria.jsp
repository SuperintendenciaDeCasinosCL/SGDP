<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
	
	<div id="panelMensajeria" class="panel panel-success">
	
		<div class="panel-heading">
			
			<button type="button" class="close" onclick="cerrarElemento('panelMensajeria')">
				<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
				<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
			</button> 
			<h4 class="panel-title">Mensajes</h4>

		</div>
		
		<div class="panel-body">
		
			<c:forEach var="entry" items="${mensajeVistaDTO.mensajes}">			
  				
  				<div class="${entry.value}">
				  <strong>${entry.key}</strong>
				</div>
			</c:forEach>
		
		</div>	
		
	</div>
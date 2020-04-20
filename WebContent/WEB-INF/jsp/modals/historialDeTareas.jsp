<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="historialDeTareaPorIdTareaModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">

	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				
				<h3 id="tituloHistorialDeTareas" class="modal-title"></h3>
				
				<br>
				
				<div id="divHistorialDeTareaPorIdTarea" class="row text-center">
		        
		        </div>
				
			</div>
			
			<div class="modal-body">	
			</div>
		</div>
	</div>
</div>
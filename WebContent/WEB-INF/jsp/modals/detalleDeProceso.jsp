<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="detalleDeProcesoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">

	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				
				<h3 id="nombreProcesoHeaderModal" class="modal-title"></h3>
				
				<br>
				
				<div id="etapasInstanciaDeProcesoDetalleDeProcesoModal" class="row text-center">
		        
		        </div>
				
			</div>
			
			<div class="modal-body">		
			
				<div class="row">
						
					<%-- <div id="instanciasDeTareasDetalleDeProcesoModal" class="panel-body">					
					
					</div> --%>     
					
					<div id="historicoDeInstanciaDeTareaDetalleDeProcesoModal" class="panel-body">					
					
					</div>              	
                   
				</div>	
				
				<div class="row">
						
					<div id="detalleDeExpedienteEnDetalleDeProcesoModal" class="panel-body">					
					
					</div>                   	
                   
				</div>	
			
			</div>
		
		</div>
	</div>

</div>
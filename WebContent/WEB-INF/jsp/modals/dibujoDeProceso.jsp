<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- ------------------------------------ DIBUJA PROCESO -------------------------------------- -->
	
	
<div class="modal fade" id="dibujaProcesoModal"
	tabindex="-1" role="dialog" aria-labelledby="modalLabel"
	aria-hidden="true">
		
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message
								code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
								code="boton.cerrar.nombre" /></span>
					</button>
					<h3 class="modal-title" id="lineModalLabel">
						 Proceso
					</h3>

				</div>
				<div id="dibujaProcesoModalDiv" class="modal-body">					
				
				</div>
			
		</div>
	</div>
</div>
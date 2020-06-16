<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="continuarProcesoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			 <form method="POST" id="formContinuarProceso" action="${pageContext.request.contextPath}/mueveProceso" 
			 class="form-horizontal">
				
				<input type="hidden" name="idInstanciaDeTareaOrigen" id="idInstanciaDeTareaOrigen" />
				<input type="hidden" name="avanzaRetrocede" id="avanzaRetrocede" />
				<input type="hidden" name="idExpedienteContinuarProceso" id="idExpedienteContinuarProceso" />
				<input type="hidden" name="asignacionesTareasJSON" id="asignacionesTareasJSON" />
				<input type="hidden" name="reasigna" id="reasigna" />
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 id="cabeceraContinuarProcesoModal" class="modal-title"></h3>
				</div>
				
				<div class="modal-body">	
								
					<div class="form-group" id="divTareasContinuan">	
					
						<div id="etapasInstanciaDeProcesoContinuarProcesoModal" class="col-sm-12">						
							
					    </div>		
					             
					</div>	              	
	              
              		<div class="form-group"> 
              			<label class="control-label col-sm-3"><spring:message code="bandejaDeEntrada.modal.continuarProceso.form.input.comentario.label"/></label>	             			
              			<div class="col-sm-8">	
              				<textarea class="form-control validate[required]" id="comentario" name="comentario" placeholder='<spring:message code="bandejaDeEntrada.modal.continuarProceso.form.input.comentario.placeholder"/>' rows="10"></textarea>              			
              			</div>
              		</div>
              			              		
				</div>
				
				<div class="modal-footer">
				
           			<button id="buttonAvanzarProcesoModal" type="button" class="btn btn-labeled btn-primary col-md-offset-7">
           				<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>
           				<spring:message code="bandejaDeEntrada.modal.continuarProceso.form.botonEjecuar.valor"/>
           			</button>
				
				</div>
			</form>			
		</div>
	</div>
</div>
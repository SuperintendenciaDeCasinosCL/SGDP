<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="retrocedeProcesoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<sf:form method="POST" commandName="retrocedeProcesoDTO" id="formRetrocedeProceso" action="${pageContext.request.contextPath}/retrocedeProceso">
					<input type="hidden" value="${instanciaDeTareaDTO.idInstanciaDeTarea}" name="idInstanciaDeTareaSeleccionada" id="idInstanciaDeTareaSeleccionada" />
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
							<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
						</button>
						<h3 class="modal-title" id="lineModalLabel">Devolver - ${instanciaDeTareaDTO.nombreDeTarea} - ${instanciaDeTareaDTO.nombreDeProceso} - ${instanciaDeTareaDTO.nombreExpediente}</h3>
					</div>
					<div class="modal-body">
		              	<div class="row">
							<div class="col-md-3">Comentario:</div>
  							<div class="col-md-9"></div>	              			              			
	              		</div>	    
	              		<br>          		
	              		<div class="row">
              				<div class="col-md-12">
              					<textarea class="form-control validate[required]" id="comentarioRetrocedeProceso" name="comentario" placeholder='Ingrese Comentario' rows="10"></textarea>
              				</div>              				
              			</div>
					</div>
					<div class="modal-footer">						
               			<button type="button" onclick="devuelveTarea()" id="botonDevuelveTarea" class="btn btn-primary btn-lg btn-block">
               				<span class="glyphicon glyphicon-triangle-left"></span>
               				Devolver
               			</button>
					</div>
				</sf:form>			
			</div>
		</div>
	</div>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="modalBitacora" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title">Bit&aacute;cora de Subtareas</h3>
			</div>
			<div class="modal-body" id="bitacoraDiv" style="max-width: 95% !important; ">
					<table id="bitacoraTable" class="table table-striped table-bordered table-fixed" style="max-width: 95% !important; ">
						<thead>
							<tr>
								<th>Fecha</th><th>Tarea</th><th>Usuario</th><th>Tipo subtarea</th><th>Subtarea</th><th>Tiempo dedicado</th><th>Usuarios relacionados</th>
							</tr>
						</thead>
						<tbody id="bitacoraList">
							
						</tbody>
					</table>
			</div>	
			<div class="modal-footer">
				
			</div>	
		</div>
	</div>

</div>
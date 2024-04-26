<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<div id="viewLogDocumentoModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">
							<spring:message code="boton.cerrar.signo" />
						</span> 
						<span class="sr-only">
							<spring:message code="boton.cerrar.nombre" />
						</span>
					</button>
					<h3 id="lineModalLabelUpdate" class="modal-title"><spring:message code="reporteLogDocumento.modal.titulo"/></h3>
				</div>
				
				<div class="modal-body">
				
					<div class="row">
					
						<div class="panel-body">
						
							<div class="table-responsive col-sm-12" >
								<table class="table table-striped table-bordered" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>Campo</th>
											<th>Valor</th>
										</tr>
									</thead>
									<tbody id="bodyModalExtraData">
										<tr>
											<td>Campo 1</td>
											<td>Valor 1</td>
										</tr>
										<tr>
											<td>Campo 1</td>
											<td>Valor 1</td>
										</tr>
										<tr>
											<td>Campo 1</td>
											<td>Valor 1</td>
										</tr>
										<tr>
											<td>Campo 1</td>
											<td>Valor 1</td>
										</tr>
									</tbody>
								</table>
							</div>
						
						</div>  
					
					</div>
					
				</div>
			
		</div>
	</div>
</div>








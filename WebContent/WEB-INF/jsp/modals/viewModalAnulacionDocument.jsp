<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<div id="divModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="formModal" class="form-horizontal" >
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">
							<spring:message code="boton.cerrar.signo" />
						</span> 
						<span class="sr-only">
							<spring:message code="boton.cerrar.nombre" />
						</span>
					</button>
					<h3 id="lineModalLabelUpdate" class="modal-title"><spring:message code="mantenedorAnulacion.modal.titulo" /></h3>
				</div>
				
				<div class="modal-body">
					<input id="txtId" type="hidden">
					<div class="row">
						<div class="col-sm-12" style="padding: 0 15px;">
							<div id="form-group-code" class="form-group" style="margin-left: 0 !Important; margin-right: 0 !Important;" >
								<label for="txtMotivo" >  
									<spring:message code="mantenedorAnulacion.modal.form.input.motivo.label" />
								</label>
								<div>
									<textarea id="txtMotivo" class="form-control" rows="3" 
										placeholder='<spring:message code="mantenedorAnulacion.modal.form.input.codigo.placeholder"/>'>
									</textarea>
									<span id="error-code" class="help-block">Debe ingresar un motivo valido.</span>
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<span id="txt-general-error" class="help-block text-danger"></span>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<div class="form-group">
						<div class=" col-sm-offset-8 col-sm-4">
							<button id="buttonAnularDocumento" type="button"  onclick="anulDocument()" class="btn btn-primary btn-lg btn-block">
								<span id="spanAnularDocumento" class="glyphicon glyphicon-floppy-disk text-lg-left"></span>
								<spring:message code="mantenedorAnulacion.modal.form.boton.guardar" />
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>








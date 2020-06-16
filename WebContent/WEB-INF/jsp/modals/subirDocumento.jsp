<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import= "cl.gob.scj.sgdp.control.AppContextControl" %> 

<div class="modal fade" id="subirDocumentoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<sf:form id="formSubirDocumento" class="form-horizontal" method="POST" enctype="multipart/form-data" commandName="subirDocumentoDTO">
				<input type="hidden" name="idInstanciaDeTareaOrigenSubirDocumento" id="idInstanciaDeTareaOrigenSubirDocumento" />
				<input type="hidden" name="idExpedienteSubirDocumento" id="idExpedienteSubirDocumento" />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 id="cabeceraSubirDocumentoModal" class="modal-title" id="lineModalLabel"></h3>
				</div>
				<div class="modal-body">	
					<div class="form-group">						
						<label class="control-label col-sm-4" for="documento"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.documento"/></label>
					    <div class="col-sm-8">
					    	<input class="validate[required]" type="file" name="documento"/>						  
					    </div>
					</div>	
					<div class="form-group">						
						<label class="control-label col-sm-4" for="idTipoDeDocumentoSubir"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento"/></label>
					    <div class="col-sm-8">		    
					    	<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione" var="tipoDeDocumentoSeleccione"/>							    
					    	<select class="form-control validate[required]" name="idTipoDeDocumentoSubir" id="idTipoDeDocumentoSubir">						    		
					    		<option value="">${tipoDeDocumentoSeleccione}</option>
					    		<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO">					    			
									<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
								</c:forEach>
					    	</select>			    	
					    </div>
					</div>	
					<div class="form-group">						
						<label class="control-label col-sm-4" for="cdr"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.cdr"/></label>
					    <div class="col-sm-8">
					    	 <input type="text" class="form-control validate[required]" id="cdr" name="cdr" placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.cdr.placeholder"/>'>
					    </div>
					</div>
					<div class="form-group">						
						<label class="control-label col-sm-4" for="numeroDocumento"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder"/></label>
					    <div class="col-sm-8">
					    	 <input type="text" class="form-control validate[required]" id="numeroDocumento" name="numeroDocumento" placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder"/>'>
					    </div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="fechaCreacionDoc"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.fechaDeCreacion"/></label>
						<div class="col-sm-8">
							<div class='input-group date' id='fechaCreacionDoc'>
                   				<input type='text' class="form-control validate[required]" id="fechaDeCreacionDocumento" name="fechaDeCreacionDocumento" placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
                   				<span class="input-group-addon">
                       				<span class="glyphicon glyphicon-calendar"></span>
                   				</span>
               				</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="idAutorSubirDocumento"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.autor"/></label>
						<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.select.autor.seleccione" var="autorSubirDocumentoSeleccione"/>
						<div class="col-sm-8">
							<select class="form-control validate[required]" name="idAutorSubirDocumento" id="idAutorSubirDocumento">
								<option value="">${autorSubirDocumentoSeleccione}</option>
								<c:forEach items="${autoresDTO}" var="autorDTO">
									<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
								</c:forEach>
							</select>
						</div>
					</div>	
					<div class="form-group">
						<label class="control-label col-sm-4" for="otro"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.otro"/></label>
						<div class="col-sm-8">
					    	 <input type="text" class="form-control validate[required]" id="otro" name="otro" placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.otro.placeholder"/>'>
					    </div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="antecedentes"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.label.antecedentes"/></label>
						<div class="col-sm-8">
							<input class="validate[required]" type="file" name="antecedentes" multiple/>							
						</div>
					</div>
					<div class="form-group">
              			<label class="control-label col-sm-4" for="descripcion"><spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.label"/></label>	
              			<div class="col-sm-8">
              				<textarea class="form-control validate[required]" id="descripcion" name="descripcion" placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.placeholder"/>' rows="10"></textarea>
              			</div>
              		</div>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btn-lg btn-block" onclick="subirDocumentoModal()">
						<span class="glyphicon glyphicon-upload"></span>  
                       	<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.boton.subirDocumento.value"/>
				    </button>
				</div>
			</sf:form>
		</div>
	</div>
</div>

<script>
$(function() {
    $('#fechaCreacionDoc').datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY'
    });
    jQuery("#formSubirDocumento").validationEngine();
});
</script>

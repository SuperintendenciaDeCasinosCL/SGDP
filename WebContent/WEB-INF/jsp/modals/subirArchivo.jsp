<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:set var="permisoIngresaDatosAdicionalesAlsubirArchivo" value="<%=PermisoType.INGRESA_DATOS_ADICIONALES_AL_SUBIR_ARCHIVO.getNombrePermiso()%>"/>

<div class="modal fade" id="subirArchivoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">

	<div class="modal-dialog">
	
		<div class="modal-content">
		
			<sf:form id="formSubirArhivo" class="form-horizontal" method="POST" enctype="multipart/form-data" commandName="subirArhivoDTO">
							
				<input type="hidden" id="idExpedienteSubirArchivo" name="idExpedienteSubirArchivo" value="${instanciaDeTareaDTO.idExpediente}" />
				<input type="hidden" id="idInstanciaDeTareaSubirArchivo" name="idInstanciaDeTareaSubirArchivo" value="${instanciaDeTareaDTO.idInstanciaDeTarea}" />
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 class="modal-title" id="lineModalLabel">Subir Archivo</h3>
				</div>
				
				<div class="modal-body">
				
					<div class="form-group">						
						<label class="control-label col-sm-4" for=""archivo"">Seleccionar archivo...</label>
					    <div class="col-sm-8">
					    	<input class="validate[required]" type="file" name="archivo"/>
					    </div>
					</div>
					
					<c:if test = "${permisos[permisoIngresaDatosAdicionalesAlsubirArchivo] eq permisoIngresaDatosAdicionalesAlsubirArchivo}">
					
						<div class="form-group">						
							<label class="control-label col-sm-4" for="cdr">CDR:</label>
						    <div class="col-sm-8">
						    	 <input type="text" class="form-control validate[required]" id="cdr" name="cdr" placeholder='CDR'>
						    </div>
						</div>
					
					</c:if>
					
					<c:if test = "${permisos[permisoIngresaDatosAdicionalesAlsubirArchivo] eq permisoIngresaDatosAdicionalesAlsubirArchivo}">
					
						<div class="form-group">						
							<label class="control-label col-sm-4" for="numeroDocumento">N&ring; Documento:</label>
						    <div class="col-sm-8">
						    	 <input type="text" class="form-control validate[required]" id="numeroDocumento" name="numeroDocumento" placeholder='N&ring; Documento:'>
						    </div>
						</div>
						
					</c:if>
					
					<c:if test = "${permisos[permisoIngresaDatosAdicionalesAlsubirArchivo] eq permisoIngresaDatosAdicionalesAlsubirArchivo}">
					
						<div class="form-group">
							<label class="control-label col-sm-4" for="fechaCreacionArch">Fecha de Creaci&oacute;n:</label>
							<div class="col-sm-8">
								<div class='input-group date' id='fechaCreacionArch'>
	                   				<input type='text' class="form-control validate[required]" id="fechaCreacionArchivo" name="fechaCreacionArchivo" placeholder='__/__/____' />
	                   				<span class="input-group-addon">
	                       				<span class="glyphicon glyphicon-calendar"></span>
	                   				</span>
	               				</div>
							</div>
						</div>
					
					</c:if>
					
					<div class="form-group">
						<label class="control-label col-sm-4" for="idAutorSubirDocumento">Autor:</label>
						<div class="col-sm-8">
							<select class="form-control validate[required]" name="idAutorSubirDocumento" id="idAutorSubirDocumento">
								<option value="">Seleccione Autor</option>
								<c:forEach items="${autoresDTO}" var="autorDTO">
									<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
								</c:forEach>
							</select>
						</div>
					</div>				
					
					<c:if test = "${permisos[permisoIngresaDatosAdicionalesAlsubirArchivo] eq permisoIngresaDatosAdicionalesAlsubirArchivo}">
					
						<div class="form-group">
							<label class="control-label col-sm-4" for="otro">Otro:</label>
							<div class="col-sm-8">
						    	 <input type="text" class="form-control validate[required]" id="otro" name="otro" placeholder='Otro'>
						    </div>
						</div>
					
					</c:if>
					
					<div class="form-group">						
						<label class="control-label col-sm-4" for="idTipoDeDocumentoSubir">Tipo de Documento:</label>
					    <div class="col-sm-8">		    
					    	<select class="form-control validate[required]" name="idTipoDeDocumentoSubir" id="idTipoDeDocumentoSubir">						    		
					    		<option value="">Seleccione Tipo de Documento</option>
					    		<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO">
									<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
								</c:forEach>
					    	</select>			    	
					    </div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-4" for="idTag">Tags:</label>
						<div class="col-sm-8">
							<select multiple name="idTags" id="idTags">
							  <c:forEach items="${listaDeTags}" var="tag">
									<option value="${tag}">${tag}</option>
							</c:forEach>
							</select>
						</div>
					</div>						
					
					<div class="form-group">
              			<label class="control-label col-sm-4" for="descripcion">Descripci&oacute;n:</label>	
              			<div class="col-sm-8">
              				<textarea class="form-control validate[required]" id="descripcion" name="descripcion" placeholder='Descripci&oacute;n' rows="10"></textarea>
              			</div>
              		</div>	
				
				</div>
				
				<div class="modal-footer">
				
					<button type="button" id="botonSubirArchivo" class="btn btn-primary btn-lg btn-block" onclick="subirArhivoModal()">
						<span class="glyphicon glyphicon-upload"></span>  
                       	Subir Archivo
				    </button>
				
				</div>			
			
			</sf:form>
		
		</div>
	
	</div>

</div>

<script>
$(function() {
    $('#fechaCreacionArch').datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY'
    });
    jQuery("#formSubirArhivo").validationEngine();
});
</script>
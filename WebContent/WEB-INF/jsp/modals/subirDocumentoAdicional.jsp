<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="subirDocumentoAdicionalModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title" id="subirDocumentoAdicionalModalTitulo">Subir documento adicional</h3>
			</div>				
			<div class="modal-body">
				<form class="form-horizontal" id="formSubirDocumentoAdicionalModal" method="POST" enctype="multipart/form-data">
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="documentoAdicionalModal">Archivo (*):</label>
				      <div class="col-sm-4">	
						<span class="btn btn-success fileinput-button btn-file btn-verde-tarea" id="spanDocumentoAdicionalModal"> 
                    		<i class="glyphicon glyphicon-plus"></i>
                    		<span id="nombreArchivoSpanDocumentoAdicionalModal"></span>
                    		<input class="documento-adicional-file" 
                    			type="file" name="archivo"
                    			id="documentoAdicionalModal" >
                		</span>
				      </div>
				      <div class="col-sm-5">
				      	<span id="nombreDocumentoAdicionalModal"></span>
				      </div>
				    </div>
				    <div class="hide">
						<label class="control-label col-sm-3"
							for="idTipoDeDocumentoAdicionalModal"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento" /></label>
						<div class="col-sm-9">
							<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione"
								var="tipoDeDocumentoSeleccione" />
							<select class="form-control validate[required] select2-subir-doc-adicional-modal"
								style="width: 100%" 
								id="idTipoDeDocumentoAdicionalModal">
								<option value="">${tipoDeDocumentoSeleccione}</option>
								<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO">								
									<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
								</c:forEach>								
							</select>
						</div>
					</div>
					<div class="form-group hide">
				    	<label class="control-label col-sm-3" for="cdrDocumentoAdicionalModal">CDR:</label>
				    	<div class="col-sm-9">
				    		<input type="text" class="form-control" id="cdrDocumentoAdicionalModal">
				    	</div>
				    </div>
				    <div class="form-group">
				    	<label class="control-label col-sm-3" for="numeroDocumentoAdicionalModal">N&ring; Documento:</label>
				    	<div class="col-sm-9">
				    		<input type="text" class="form-control" id="numeroDocumentoAdicionalModal">
				    	</div>
				    </div>
				   	<div class="form-group">
				    	<label class="control-label col-sm-3" for="fechaDocumentoAdicionalModal">Fecha de Documento (*):</label>
				    	<div class="col-sm-9">
				    		 <div class='input-group date fecha-subir-doc-adicional-modal'
								id='fechaDocAdicionalModal'>
								<input type='text' class="form-control validate[required]"
									id="fechaDocumentoAdicionalModal"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
							</div>
				    	</div>
				    </div>
				    <div class="form-group">
				    	<label class="control-label col-sm-3" for="fechaRecepcionDocumentoAdicionalModal">Fecha de Recepci&oacute;n (*):</label>
				    	<div class="col-sm-9">
				    		 <div class='input-group date fecha-subir-doc-adicional-modal'
								id='fechaRecepcionDocAdicionalModal'>
								<input type='text' class="form-control validate[required]"
									id="fechaRecepcionDocumentoAdicionalModal"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
							</div>
				    	</div>
				    </div>
				    
				    <!--  
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="accionDocumentoAdicionalModal">Acciones Documento:</label>
				      <div class="col-sm-9">	
						<select id="accionDocumentoAdicionalModal" class="form-control select2-subir-doc-adicional-modal">
							<option value="">xxxxxxx</option>
						</select>
				      </div>				     
				    </div>
				    -->
				    
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="tagsDocumentoAdicionalModal">Tags:</label>
				      <div class="col-sm-9">	
						<select id="tagsDocumentoAdicionalModal" class="form-control select2-subir-doc-adicional-modal-multiple" multiple="multiple">
							<c:forEach items="${todosLosTags}" var="tag">								
								<option value="${tag}">${tag}</option>
							</c:forEach>
						</select>
				      </div>				     
				    </div>	
				    <div class="form-group"><label class="control-label col-sm-3" for="observacionDocumentoAdicionalModal">Observaci&oacute;n :</label><div class="col-sm-9"></div></div>
				    <div class="form-group"><div class="col-sm-1"></div><div class="col-sm-11"><textarea class="form-control col-sm-12" rows="2" id="observacionDocumentoAdicionalModal"></textarea></div></div>
					<div class="form-group"> 
						<div class="col-sm-12 text-center"> 
							<button id="botonSubirDocumentoAdicionalModal" type="button" class="btn btn-primary">
								<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir
							</button> 
						</div>
					</div>
					
				</form>			
			</div>		
		</div>	
	</div>
</div>

<script>
var inicializaSelect2SubirDocumentoAdicionalModal = function(){
	$(".select2-subir-doc-adicional-modal").select2({
		    theme: "bootstrap",
		    dropdownParent: $("#subirDocumentoAdicionalModal"),
		    language: "es"
		});
};
var inicializaSelect2MultipleSubirDocumentoAdicionalModal = function(){
	$(".select2-subir-doc-adicional-modal-multiple").select2({
	    theme: "bootstrap",
	    dropdownParent: $("#subirDocumentoAdicionalModal"),
	    language: "es"
	});
};
var inicializaFechasDocumentoAdicionalModal = function() {
	$('.fecha-subir-doc-adicional-modal').datetimepicker({
		locale : 'es',
		format : 'DD/MM/YYYY'
	});
};
var inicializaFileUploadDocumentoAdicionalModal = function(){
	var sacar = 0
	var urlSubirArchivo = $("#urlSubirArchivo").val();
	console.log("urlSubirArchivo: " + urlSubirArchivo);	
  	var notify = "";
  	var tamanoDeArchivo;
  	$('#documentoAdicionalModal').fileupload({
  		dataType: 'json',
        url: urlSubirArchivo,
        autoUpload: false,
        add: function (e, data) {    
            $( "#nombreArchivoSpanDocumentoAdicionalModal" ).empty();       
        	$.each(data.files, function (index, file) {
                $('#nombreArchivoSpanDocumentoAdicionalModal').text(file.name);
                tamanoDeArchivo = file.size;
                console.log("tamanoDeArchivo: " + tamanoDeArchivo);               
            });        	
        	$("#botonSubirDocumentoAdicionalModal").off('click').on('click', function () {
        		var x = $("#formSubirDocumentoAdicionalModal").validationEngine('hide');
            	console.log('$(this).attr("data-idinstanciadetarea"): ' + $(this).attr("data-idinstanciadetarea")); //data-idinstanciadetarea
            	var validaTamanoDeArchivo = true;
        		data.formData = {
            			"idExpedienteSubirArchivo" : $(this).attr("data-idexpediente") , 
            			"idTipoDeDocumentoSubir" : $("#idTipoDeDocumentoAdicionalModal").val(),
            			"idInstanciaDeTareaSubirArchivo" : $(this).attr("data-idinstanciadetarea"),
            			"cdr" : $("#cdrDocumentoAdicionalModal").val(),
            			"numeroDocumento" : $("#numeroDocumentoAdicionalModal").val(),
            			"fechaCreacionArchivo" : $("#fechaDocumentoAdicionalModal").val(), 
            			"fechaRecepcionArchivo" : $("#fechaRecepcionDocumentoAdicionalModal").val(),
            			"idTags" : $("#tagsDocumentoAdicionalModal").val(),
            			"comentario" : $("#observacionDocumentoAdicionalModal").val(),
            			"tipoDeDocumento" : encodeURIComponent($('#idTipoDeDocumentoAdicionalModal :selected').text()),
            			"esRequerido" : false,
            			"validaInstanciaDeTareaEnBE": true       			
            			}; 
        		var validaForm = $("#formSubirDocumentoAdicionalModal").validationEngine('validate'); 
        		console.log("validaForm: " + validaForm);  			
        		if ($('#nombreArchivoSpanDocumentoAdicionalModal').text() == "Seleccionar archivo") {
        			$("#spanDocumentoAdicionalModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
        		}  
        		if (tamanoDeArchivo<=0) {
	            	validaTamanoDeArchivo = false;
	            	$("#spanDocumentoAdicionalModal").validationEngine('showPrompt', '* El tamaño del archivo debe ser mayor a 0 bytes', 'error');
		        }     		
        		var validaFechMenor = validaFechaMenor('fechaDocumentoAdicionalModal', 'fechaRecepcionDocumentoAdicionalModal', 'Fecha documento mayor a fecha recepci&oacute;n');
        		console.log("validaFechMenor: " + validaFechMenor);
        		console.log("validaForm: " + validaForm);        		        		
        		if (validaForm == true && validaFechMenor && validaTamanoDeArchivo
        				&& $('#nombreArchivoSpanDocumentoAdicionalModal').text() != "Seleccionar archivo"
                			&& $('#nombreArchivoSpanDocumentoAdicionalModal').text() != "") {
        			data.submit();
            	}  
            });
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);             
            if (sacar == 0){
		             notify = $.notify('<strong>Guardando</strong> No cerrar esta pagina...', {
		            	type: 'success',
		            	allow_dismiss: false,
		            	showProgressbar: true
		            });
		       sacar = 1;      
            }
            if (progress == 100){
                notify.update({'type': 'success', 'message': ' Ha terminado la carga!', 'progress': progress});
                sacar = 0;
            }else{
                notify.update({'type': 'success', 'message': ' <strong>Guardando</strong> No cerrar esta pagina ... ', 'progress': progress});
            }            
        },
        error : function(e) {
			console.log("ERROR: ", e);			
		},
		/*beforeSend: function(xhr) {
			$('#subirDocumentoAdicionalModal').modal('hide');
		},*/
		complete : function(data){
        	console.log("data" + data);
       	 	console.log("data.toString()" + data.toString());
       	 	console.log("data.responseJSON: ", data.responseJSON);       	 

       	 	if (data.responseJSON.cssStatus == "alert alert-warning") {
				$('#subirAdjuntoModal').modal('hide');
				$.notify({
		   			message: data.responseJSON.resultadoSubirArchivo
		   		},{
		   			type: 'warning'
		   		});
				if (data.responseJSON.recarga == true) {             			
        			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
        			$("#divTabsDetalleDeTarea").addClass('hide');	
    				$("html, body").animate({ scrollTop: 0 }, "slow");	
    				$("#contenedorBEPrincipal").find(".cargando").remove();
                }
			} else if (data.responseJSON.cssStatus == "alert alert-danger") {
         	//if (data.responseJSON.resultadoSubirArchivo != "OK") {         		
         		 notify.update({'type': 'danger', 'message': ' Error al guardar !'});
         		 setTimeout(function() {
         	        notify.close();
         	    }, 4500);
        		$.notify({
         			message: data.responseJSON.resultadoSubirArchivo
         		},{
         			type: 'danger'
         		});	
		   	} else {
				document.getElementById('formSubirDocumentoAdicionalModal').reset();
				$.notify({
		   			message: 'Se ha guardado correctamente el documento requerido!'
		   		},{
		   			type: 'success'
		   		});	
         		var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
         		urlGetTablaHistorialDeDocumentoPorIdExpediente = 
             		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#botonSubirDocumentoAdicionalModal").attr("data-idexpediente")
             		+ "/" + $("#botonSubirDocumentoAdicionalModal").attr("data-idinstanciadetarea")
             		+ "/" + $("#botonSubirDocumentoAdicionalModal").attr("data-nombreexpediente");
         		console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);
         		$('#divTablaHistorialDeDocumentos').each(function(){        	 
         	        $(this).fadeOut("slow").load(urlGetTablaHistorialDeDocumentoPorIdExpediente).fadeIn('slow');
         	    });
         		var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#botonSubirDocumentoAdicionalModal").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
				console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
				$('#divDetalleDeTarea').each(function(){        	 
		   			$(this).fadeOut("slow").load(urlGetDetalleDeTarea, function() {	        		      	
		        		if ($("#divTabsDetalleDeTarea").length>0) {
		        			$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
		        		}
		        		seteaBotonAbrirCorreoDeDistribucionModal();
		        	}).fadeIn('slow');
		   		});
		   	}
			$('#subirDocumentoAdicionalModal').modal('hide');	
        }
  	});
};
/*var inicializaBotonSubirDocumentoAdicionalModal = function() {
	$("#botonSubirDocumentoAdicionalModal").off('click').on('click', function () {		
		var x = $("#formSubirDocumentoAdicionalModal").validationEngine('hide');	
		var validaForm = $("#formSubirDocumentoAdicionalModal").validationEngine('validate');	
		console.log("validaForm: " + validaForm);
		if ($('#nombreArchivoSpanDocumentoAdicionalModal').text() == "Seleccionar archivo") {
			$("#spanDocumentoAdicionalModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
		} 		
		var validaFechMenor = validaFechaMenor('fechaDocumentoAdicionalModal', 'fechaRecepcionDocumentoAdicionalModal', 'Fecha documento mayor a fecha recepci&oacute;n');		
		console.log("validaFechMenor: " + validaFechMenor);					
	});	
};*/
$(document).ready(function() {
	$(inicializaSelect2SubirDocumentoAdicionalModal);	
	$(inicializaFechasDocumentoAdicionalModal);
	$(inicializaSelect2MultipleSubirDocumentoAdicionalModal);
	$(inicializaFileUploadDocumentoAdicionalModal);
	/*inicializaBotonSubirDocumentoAdicionalModal);*/
	
});
</script>
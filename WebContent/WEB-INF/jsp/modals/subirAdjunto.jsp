<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="subirAdjuntoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg"> 
		<div class="modal-content">		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title" id="subirAdjuntoModalTitulo"></h3>
			</div>				
			<div class="modal-body">
				<form class="form-horizontal" id="formSubirAdjuntoModal" method="POST" enctype="multipart/form-data">
					<div class="form-group">
				      <label class="control-label col-sm-3" for="archivoAdjuntoModal">Documento (*):</label>
				      <div class="col-sm-9">	
						<span class="btn btn-success fileinput-button btn-file btn-verde-tarea" id="spanArchivoAdjuntoModal"> 
                    		<i class="glyphicon glyphicon-plus"></i>
                    		<span id="nombreArchivoSubirAdjuntoModal"></span>
                    		<input class="documento-adjunto-file" 
                    			type="file" name="archivo"
                    			id="archivoAdjuntoModal" >
                		</span>
				      </div>				     
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="numeroDocumentoAdjuntoModal">N&ring; Documento:</label>
				      <div class="col-sm-9">          
				        <input type="text" class="form-control" id="numeroDocumentoAdjuntoModal" placeholder='<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder" />'>
				      </div>
				    </div>
				    <div class="form-group">
				    	<label class="control-label col-sm-3" for="fechaDocumentoAdjuntoModal">Fecha de adjunto (*):</label>
				    	<div class="col-sm-9">
				    		 <div class='input-group date'
								id='fechaDocAdjuntoModal'>
								<input type='text' class="form-control"
									id="fechaDocumentoAdjuntoModal"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
							</div>
				    	</div>
				    </div>
				    <div class="form-group">
				    	<label class="control-label col-sm-3" for="autorAdjuntoModal">Autor (*):</label>
				    	<div class="col-sm-9">
				    		<div>
								<spring:message
									code="bandejaDeEntrada.modal.crearExpediente.form.select.autor.seleccione"
									var="autorSeleccione" />						
								<select class="form-control validate[required] select2-subir-adjunto"
									style="width: 100%" id="idAutorAdjuntoModal">									
									<option value="">${autorSeleccione}</option>									
									<c:forEach items="${autoresDTO}" var="autorDTO">										
										<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
									</c:forEach>
								</select>
							</div>
				    	</div>
				    </div>	
				    <div class="hide">
						<label class="control-label col-sm-3"
							for="idTipoDeDocumentoSubirAdjuntoModal"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento" /></label>
						<div class="col-sm-9">
							<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione"
								var="tipoDeDocumentoSeleccione" />
							<select class="form-control validate[required] select2-subir-adjunto"
								style="width: 100%" 
								id="idTipoDeDocumentoSubirAdjuntoModal">
								<option value="">${tipoDeDocumentoSeleccione}</option>
								<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO">								
									<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
								</c:forEach>								
							</select>
						</div>
					</div>
				    <div class="form-group">
				    	<label class="control-label col-sm-3" for="autorAdjuntoModal">Observacion :</label>
				    	<div class="col-sm-9">
				    		<input type="text" class="form-control" id="observacionesAdjuntoModal">
				    	</div>
				    </div>	
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="tagsDocumentoAdjuntoModal">Tags:</label>
				      <div class="col-sm-9">	
						<select id="tagsDocumentoAdjuntoModal" class="form-control select2-subir-adjunto-modal-multiple" multiple="multiple">
							<c:forEach items="${todosLosTags}" var="tag">								
								<option value="${tag}">${tag}</option>
							</c:forEach>
						</select>
				      </div>				     
				    </div>
				    <div class="form-group">
				    	<div class="col-sm-3"></div>
				    	<div class="col-sm-4"><button id="botonSubirDocumentoAdjuntoModal" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-upload"></span> Subir</button></div>
				    	<div class="col-sm-5"><button id="botonSubirDocumentoAdjuntoModalMas" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-upload"></span> Subir y adjuntar nuevo</button></div>
				    </div>				    				    		   
				</form>			
			</div>		
		</div>	
	</div>
</div>

<script>
var inicializaFileUploadDocAdjunto = function(){
	var sacar = 0;
	var cerrarModal;
	var urlSubirArchivo = $("#urlSubirArchivo").val();
	console.log("urlSubirArchivo: " + urlSubirArchivo);	
    var notify = "";
    var tamanoDeArchivo;
	$('#archivoAdjuntoModal').fileupload({
		dataType: 'json',
        url: urlSubirArchivo,
        autoUpload: false,
        add: function (e, data) {         
            $( "#nombreArchivoSubirAdjuntoModal" ).empty();       
        	$.each(data.files, function (index, file) {
                $('#nombreArchivoSubirAdjuntoModal').text(file.name);   
                tamanoDeArchivo = file.size;
                console.log("tamanoDeArchivo: " + tamanoDeArchivo);             
            });  
        	$("#botonSubirDocumentoAdjuntoModal").off('click').on('click', function () {
        		var x = $("#formSubirAdjuntoModal").validationEngine('hide');
        		console.log('$("#tagsDocumentoAdjuntoModal").val(): ' + $("#tagsDocumentoAdjuntoModal").val());
        		cerrarModal = true;
        		var validaTamanoDeArchivo = true;
        		data.formData = {
            			"idExpedienteSubirArchivo" : $(this).attr("data-idexpediente") , 
            			"idTipoDeDocumentoSubir" : $("#idTipoDeDocumentoSubirAdjuntoModal").val(),
            			"cartaRelacionada" : encodeURIComponent($(this).attr("data-cartarelacionada")),
            			"numeroDocumento" : $("#numeroDocumentoAdjuntoModal").val(),
            			"fechaCreacionArchivo" : $("#fechaDocumentoAdjuntoModal").val(),
            			"idAutorSubirDocumento" : $("#idAutorAdjuntoModal").val(), 
            			"comentario" : $("#observacionesAdjuntoModal").val(),
            			"idInstanciaDeTareaSubirArchivo" : $(this).attr("data-idinstanciadetarea"),
            			"fechaRecepcionArchivo" : $(this).attr("data-fechaderecepcion"),
            			"tipoDeDocumento" : encodeURIComponent($('#idTipoDeDocumentoSubirAdjuntoModal :selected').text()),
            			"esRequerido" : false,
            			"idTags" : $("#tagsDocumentoAdjuntoModal").val(),
            			"validaInstanciaDeTareaEnBE": true
            			};        		
        		if ($('#nombreArchivoSubirAdjuntoModal').text() == "Seleccionar archivo") {
        			$("#spanArchivoAdjuntoModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');
        		} 
        		var validaForm = $("#formSubirAdjuntoModal").validationEngine('validate');
        		console.log("validaForm: " + validaForm);
        		if (tamanoDeArchivo<=0) {
	            	validaTamanoDeArchivo = false;
	            	$("#spanArchivoAdjuntoModal").validationEngine('showPrompt', '* El tamaño del archivo debe ser mayor a 0 bytes', 'error');
		        }
        		if (validaForm == true && validaTamanoDeArchivo
                		&& $('#nombreArchivoSubirAdjuntoModal').text() != "Seleccionar archivo"
                    		&& $('#nombreArchivoSubirAdjuntoModal').text() != "") {
        			data.submit();
            	}  
            });
        	$("#botonSubirDocumentoAdjuntoModalMas").off('click').on('click', function () { 
        		$("#idTipoDeDocumentoSubirAdjuntoModal").val($('#idTipoDeDocumentoSubirAdjuntoModal option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();
        		var x = $("#formSubirAdjuntoModal").validationEngine('hide');
        		console.log('$("#tagsDocumentoAdjuntoModal").val(): ' + $("#tagsDocumentoAdjuntoModal").val());
        		cerrarModal = false;
        		var validaTamanoDeArchivo = true;
        		data.formData = {
            			"idExpedienteSubirArchivo" : $(this).attr("data-idexpediente") , 
            			"idTipoDeDocumentoSubir" : $("#idTipoDeDocumentoSubirAdjuntoModal").val(),
            			"cartaRelacionada" : encodeURIComponent($(this).attr("data-cartarelacionada")),
            			"numeroDocumento" : $("#numeroDocumentoAdjuntoModal").val(),
            			"fechaCreacionArchivo" : $("#fechaDocumentoAdjuntoModal").val(),
            			"idAutorSubirDocumento" : $("#idAutorAdjuntoModal").val(), 
            			"comentario" : $("#observacionesAdjuntoModal").val(),
            			"idInstanciaDeTareaSubirArchivo" : $(this).attr("data-idinstanciadetarea"),
            			"fechaRecepcionArchivo" : $(this).attr("data-fechaderecepcion"),
            			"tipoDeDocumento" : encodeURIComponent($('#idTipoDeDocumentoSubirAdjuntoModal :selected').text()),
            			"esRequerido" : false,
            			"idTags" : $("#tagsDocumentoAdjuntoModal").val(),
            			"validaInstanciaDeTareaEnBE": true 
            			};  
        		var validaForm = $("#formSubirAdjuntoModal").validationEngine('validate');
        		console.log("validaForm: " + validaForm);      		
        		if ($('#nombreArchivoSubirAdjuntoModal').text() == "Seleccionar archivo") {
        			$("#spanArchivoAdjuntoModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');        			
        		}    
        		if (tamanoDeArchivo<=0) {
	            	validaTamanoDeArchivo = false;
	            	$("#spanArchivoAdjuntoModal").validationEngine('showPrompt', '* El tamaño del archivo debe ser mayor a 0 bytes', 'error');
		        }     		
        		if (validaForm == true && validaTamanoDeArchivo
        				&& $('#nombreArchivoSubirAdjuntoModal').text() != "Seleccionar archivo"
                    		&& $('#nombreArchivoSubirAdjuntoModal').text() != "") {
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
                notify.update({'type': 'success', 'message': ' Se ha guardado correctamente el adjunto!', 'progress': progress});
                sacar = 0;
            }else{
                notify.update({'type': 'success', 'message': ' <strong>Guardando</strong> No cerrar esta pagina ... ', 'progress': progress});
            }            
        },
        error : function(e) {
			console.log("ERROR: ", e);			
		},
		complete : function(data){
        	console.log("data" + data);
       	 	console.log("data.toString()" + data.toString());
       	 	console.log("data.responseJSON: ", data.responseJSON);       	 
         	if (data.responseJSON.cssStatus == "alert alert-danger"){         		
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
		   		if (cerrarModal == true) {
					$('#subirAdjuntoModal').modal('hide');
				} 
				$("#numeroDocumentoAdjuntoModal").val("");
                $("#observacionesAdjuntoModal").val("");
				$( "#nombreArchivoSubirAdjuntoModal" ).empty(); 
				$('#nombreArchivoSubirAdjuntoModal').text("Seleccionar archivo");
				$('.select2-subir-adjunto-modal-multiple').val('').change();
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
				} else {
					$.notify({
	         			message: 'Se ha guardado correctamente el adjunto!'
	         		},{
	         			type: 'success'
	         		});
					var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
	         		urlGetTablaHistorialDeDocumentoPorIdExpediente = 
	             		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#linkAdjuntarDocumento").attr("data-idexpediente")
	             		+ "/" + $("#linkAdjuntarDocumento").attr("data-idinstanciadetarea")
	             		+ "/" + $("#linkAdjuntarDocumento").attr("data-nombreexpediente");
	         		console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);
	         		$("#divTablaHistorialDeDocumentosForm").empty();
	         		$('#divTablaHistorialDeDocumentos').each(function(){        	 
	         	        $(this).fadeOut("slow").load(urlGetTablaHistorialDeDocumentoPorIdExpediente).fadeIn('slow');
	         	    });
	         		var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#linkAdjuntarDocumento").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
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
		   	}	
        }
	});
};
var inicializaFechaDocumentoAdjuntoModal = function() {
	$('#fechaDocAdjuntoModal').datetimepicker({
		locale : 'es',
		format : 'DD/MM/YYYY'
	});
};
var inicializaSelect2AutoresSubirAdjunto = function() {
	$(".select2-subir-adjunto").select2({
 		    theme: "bootstrap",
 		    dropdownParent: $("#subirAdjuntoModal"),
 		    language: "es"
 		});
};
/* var inicializaBotonesSubir = function() {
	$("#botonSubirDocumentoAdjuntoModal").off('click').on('click', function () {
		var x = $("#formSubirAdjuntoModal").validationEngine('hide');	
		var validaForm = $("#formSubirAdjuntoModal").validationEngine('validate');
		console.log("validaForm: " + validaForm);			
		if ($('#nombreArchivoSubirAdjuntoModal').text() == "Seleccionar archivo") {
			$("#spanArchivoAdjuntoModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
		}
	});*/
/*	$("#botonSubirDocumentoAdjuntoModalMas").off('click').on('click', function () {		
		var x = $("#formSubirAdjuntoModal").validationEngine('hide');	
		var validaForm = $("#formSubirAdjuntoModal").validationEngine('validate');
		console.log("validaForm: " + validaForm);	
		if ($('#nombreArchivoSubirAdjuntoModal').text() == "Seleccionar archivo") {
			$("#spanArchivoAdjuntoModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
		}			
	});
};*/
var inicializaSelect2MultipleSubirAdjuntoModal = function(){
	$(".select2-subir-adjunto-modal-multiple").select2({
 		    theme: "bootstrap",
 		    dropdownParent: $("#subirAdjuntoModal"),
 		    language: "es"
 		});
};
$(document).ready(function() {
	$(inicializaFileUploadDocAdjunto);
	$(inicializaFechaDocumentoAdjuntoModal);
	$(inicializaSelect2AutoresSubirAdjunto);	
	//$(inicializaBotonesSubir);
	$(inicializaSelect2MultipleSubirAdjuntoModal);	
});
</script>
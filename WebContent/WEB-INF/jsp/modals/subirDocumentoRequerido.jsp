<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="subirDocumentoRequeridoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title" id="subirDocumentoRequeridoModalTitulo"></h3>
			</div>				
			<div class="modal-body">
				<form class="form-horizontal" id="formSubirDocumentoRequeridoModal" method="POST" enctype="multipart/form-data">
					<div class="form-group">
				      <label class="control-label col-sm-3" for="">Seleccione documento:</label>
				      <div class="col-sm-9">	
						<select id="seleccioneDocumentoRequeridoModal" class="form-control">												
						</select>
				      </div>				     
				    </div>
				    
				    
				    <!-- ###################################################### -->
				    <!-- Subir Documento requerido sin asignar número de documento-->
				    
				    <div id="divInputsSubirDocumentoRequeridoModal" class="hide">
				    	<div class="form-group">
					      <label class="control-label col-sm-3" for="documentoRequeridoModal">Archivo (*):</label>
					      <div class="col-sm-4">	
							<span class="btn btn-success fileinput-button btn-file btn-verde-tarea" id="spanDocumentoRequeridoModal"> 
	                    		<i class="glyphicon glyphicon-plus"></i>
	                    		<span id="nombreArchivoSpanDocumentoRequeridoModal"></span>
	                    		<input class="documento-requerido-file" 
	                    			type="file" name="archivo"
	                    			id="documentoRequeridoModal" >
	                		</span>
					      </div>
					      <div class="col-sm-5">
					      	<span id="nombreDocumentoRequeridoModal"></span>
					      </div>
					    </div>
					    <div class="form-group">
							<label class="control-label col-sm-3"
								for="idTipoDeDocumentoRequeridoModal"><spring:message
									code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento" /></label>
							<div class="col-sm-9">
								<spring:message
									code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione"
									var="tipoDeDocumentoSeleccione" />
								<select class="form-control validate[required] select2-subir-doc-requerido-modal"
									style="width: 100%" 
									id="idTipoDeDocumentoRequeridoModal">
									<option value="">${tipoDeDocumentoSeleccione}</option>
									<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO">								
										<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
									</c:forEach>								
								</select>
							</div>
						</div>
						<div class="form-group hide">
					    	<label class="control-label col-sm-3" for="cdrDocumentoRequeridoModal">CDR:</label>
					    	<div class="col-sm-9">
					    		<input type="text" class="form-control" id="cdrDocumentoRequeridoModal">
					    	</div>
					    </div>
					    <div class="form-group">
					    	<label class="control-label col-sm-3" id="numeroDocumentoRequeridoLabel" for="numeroDocumentoRequeridoModal">N&ring; Documento:</label>
					    	<div class="col-sm-9">
					    		<input type="text" class="form-control" id="numeroDocumentoRequeridoModal">
					    	</div>
					    </div>
					    <div class="form-group">
					    	<label class="control-label col-sm-3" for="fechaDocumentoRequeridoModal">Fecha de Documento (*):</label>
					    	<div class="col-sm-9">
					    		 <div class='input-group date fecha-subir-doc-requerido-modal'
									id='fechaDocRequeridoModal'>
									<input type='text' class="form-control validate[required]"
										id="fechaDocumentoRequeridoModal"
										placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
								</div>
					    	</div>
					    </div>
					    <div class="form-group">
					    	<label class="control-label col-sm-3" for="fechaRecepcionDocumentoRequeridoModal">Fecha de Recepci&oacute;n (*):</label>
					    	<div class="col-sm-9">
					    		 <div class='input-group date fecha-subir-doc-requerido-modal'
									id='fechaRecepcionDocRequeridoModal'>
									<input type='text' class="form-control validate[required]"
										id="fechaRecepcionDocumentoRequeridoModal"
										placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
								</div>
					    	</div>
					    </div>
					    <div class="form-group">
					      <label class="control-label col-sm-3" for="tagsDocumentoRequeridoModal">Tags:</label>
					      <div class="col-sm-9">	
							<select id="tagsDocumentoRequeridoModal" class="form-control select2-subir-doc-requerido-modal-multiple" multiple="multiple">
								<c:forEach items="${todosLosTags}" var="tag">								
									<option value="${tag}">${tag}</option>
								</c:forEach>
							</select>
					      </div>				     
					    </div>
					    
					    <div class="form-group checkboxFirma">
					    	<label class="control-label col-sm-3" for="tieneFirmaDocumentoRequeridoModal">Tiene Firma:</label>  
					    	<div class="col-sm-9">
					    		<label class="control-label AgrandarCentrarChex"><input type="checkbox" id="tieneFirmaDocumentoRequeridoModal"> </label>
					    	</div>
					    </div>
					    <div class="form-group"><label class="control-label col-sm-3" for="observacionDocumentoRequeridoModal">Observaci&oacute;n :</label><div class="col-sm-9"></div></div>
				    	<div class="form-group"><div class="col-sm-1"></div><div class="col-sm-11"><textarea class="form-control col-sm-12" rows="2" id="observacionDocumentoRequeridoModal"></textarea></div></div>
					    <div class="form-group"> 
							<div class="col-sm-12 text-center"> 
								<button id="botonSubirDocumentoRequeridoModal" type="button" class="btn btn-primary">
									<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir
								</button> 
							</div>
						</div>
				    </div>
				    
				    <!-- ############################################################## -->
				    <!-- Subir Documento requerido con asignación de número de documento-->
				       <div id="divInputsSubirDocumentoRequeridoConAsignacionModal" class="hide">
					    <div class="form-group">
							<label class="control-label col-sm-3"
								for="idTipoDeDocumentoRequeridoConAsignacionModal"><spring:message
									code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento" /></label>
							<div class="col-sm-9">
								<spring:message
									code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione"
									var="tipoDeDocumentoSeleccione" />
								<select class="form-control validate[required] select2-subir-doc-requerido-modal"
									style="width: 100%" 
									id="idTipoDeDocumentoRequeridoConAsignacionModal">
									<option value="">${tipoDeDocumentoSeleccione}</option>
									<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO">								
										<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
									</c:forEach>								
								</select>
							</div>
						</div>
						<div class="form-group hide">
					    	<label class="control-label col-sm-3" for="cdrDocumentoRequeridoConAsignacionModal">CDR:</label>
					    	<div class="col-sm-9">
					    		<input type="text" class="form-control" id="cdrDocumentoRequeridoConAsignacionModal">
					    	</div>
					    </div>
					    
					    <div class="form-group">
					    	<label class="control-label col-sm-3" for="numeroDocumentoRequeridoConAsignacionModal">N&ring; Documento (*):</label>
					    	<div class="col-sm-9">
					    		<input type="text" class="form-control validate[required]" id="numeroDocumentoRequeridoConAsignacionModal">
					    	</div>
					    </div>
					    
					    
					    <div class="form-group">
					    	<label class="control-label col-sm-3" for="fechaDocumentoRequeridoConAsignacionModal">Fecha de Documento (*):</label>
					    	<div class="col-sm-9">
					    		 <div class='input-group date fecha-subir-doc-requerido-modal'
									id='fechaDocRequeridoConAsignacionModal'>
									<input type='text' class="form-control validate[required]"
										id="fechaDocumentoRequeridoConAsignacionModal"
										placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
								</div>
					    	</div>
					    </div>
					    <div class="form-group">
					    	<label class="control-label col-sm-3" for="fechaRecepcionDocumentoRequeridoConAsignacionModal">Fecha de Recepci&oacute;n (*):</label>
					    	<div class="col-sm-9">
					    		 <div class='input-group date fecha-subir-doc-requerido-modal'
									id='fechaRecepcionDocRequeridoConAsignacionModal'>
									<input type='text' class="form-control validate[required]"
										id="fechaRecepcionDocumentoRequeridoConAsignacionModal"
										placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
								</div>
					    	</div>
					    </div>
					    <div class="form-group">
					      <label class="control-label col-sm-3" for="tagsDocumentoRequeridoModal">Tags:</label>
					      <div class="col-sm-9">	
							<select id="tagsDocumentoRequeridoConAsignacionModal" class="form-control select2-subir-doc-requerido-modal-multiple" multiple="multiple">
								<c:forEach items="${todosLosTags}" var="tag">								
									<option value="${tag}">${tag}</option>
								</c:forEach>
							</select>
					      </div>				     
					    </div>
					    
					    <div class="form-group checkboxFirma">
					    	<label class="control-label col-sm-3" for="tieneFirmaDocumentoRequeridoConAsignacionModal">Tiene Firma:</label>  
					    	<div class="col-sm-9">
					    		<label class="control-label AgrandarCentrarChex"><input type="checkbox" id="tieneFirmaDocumentoRequeridoConAsignacionModal"> </label>
					    	</div>
					    </div>
					    
					    <div class="form-group"><label class="control-label col-sm-3" for="observacionDocumentoRequeridoConAsignacionModal">Observaci&oacute;n :</label><div class="col-sm-9"></div></div>
				    	<div class="form-group"><div class="col-sm-1"></div><div class="col-sm-11"><textarea class="form-control col-sm-12" rows="2" id="observacionDocumentoRequeridoConAsignacionModal"></textarea></div></div>
					    <div class="form-group"> 
							<div class="col-sm-12 text-center"> 
								<button id="botonSubirDocumentoRequeridoConAsignacionModal" type="button" class="btn btn-primary">
									<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir
								</button> 
							</div>
						</div>
				    </div>
				    
				    
				    <!-- ############################################################## -->
				    
				    <div id="divBotonSoloSubirDocumentoRequeridoModal" class="hide">
				    	<div class="form-group"> 
							<div class="col-sm-12 text-center"> 
								<button id="botonSoloSubirDocumentoRequeridoModal" type="button" class="btn btn-primary">
									<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir
								</button> 
							</div>
						</div>
				    </div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();

var inicializaSelect2SubirDocumentoRequeridoModal = function(){
	$(".select2-subir-doc-requerido-modal").select2({
		    theme: "bootstrap",
		    dropdownParent: $("#subirDocumentoRequeridoModal"),
		    language: "es"
		});
};
var inicializaSelect2MultipleSubirDocumentoRequeridoModal = function(){
	$(".select2-subir-doc-requerido-modal-multiple").select2({
	    theme: "bootstrap",
	    dropdownParent: $("#subirDocumentoRequeridoModal"),
	    language: "es"
	});
};
var inicializaFechasDocumentoRequeridoModal = function() {
	$('.fecha-subir-doc-requerido-modal').datetimepicker({
		locale : 'es',
		format : 'DD/MM/YYYY'
	});
};
var inicializaFileUploadDocumentoRequeridoModal = function(){
	var sacar = 0
	var urlSubirArchivo = $("#urlSubirArchivo").val();
	console.log("urlSubirArchivo: " + urlSubirArchivo);	
  	var notify = "";
  	var tipoDeArchivo;  
  	var tamanoDeArchivo;	
  	$('#documentoRequeridoModal').fileupload({
  		dataType: 'json',
        url: urlSubirArchivo,
        autoUpload: false,        
        add: function (e, data) {    
            $( "#nombreArchivoSpanDocumentoRequeridoModal" ).empty();       
        	$.each(data.files, function (index, file) {
                $('#nombreArchivoSpanDocumentoRequeridoModal').text(file.name);      
                tipoDeArchivo = file.type;
                tamanoDeArchivo = file.size;
                console.log("tipoDeArchivo: " + tipoDeArchivo); 
                console.log("tamanoDeArchivo: " + tamanoDeArchivo);               
            });        	
        	$("#botonSubirDocumentoRequeridoModal").off('click').on('click', function () {
        		var validaInput = true;
        		var validaTamanoDeArchivo = true;
        		var x = $("#formSubirDocumentoRequeridoModal").validationEngine('hide');
        		var convertirAPDF = false;
				if ($(this).attr('data-puedevisardocumentos') == "true") {
					convertirAPDF = $(this).attr('data-aplicavisacionportipodedocumento') == "true"
						 && $(this).attr('data-permisopuedevisardocumento') == $(this).attr('data-nombrepermisopuedevisardocumento')
						 && tipoDeArchivo != "application/pdf";
				} else if ($(this).attr('data-puedeaplicarfea') == "true") {
					convertirAPDF = $(this).attr('data-aplicafeaportipodedocumento') == "true"
						 && $(this).attr('data-permisopuedefirmarconfea') == $(this).attr('data-nombrepermisopuedefirmarconfea')
						 && tipoDeArchivo != "application/pdf";
				}        		
				console.log("convertirAPDF: " + convertirAPDF); 
        		data.formData = {
            			"idExpedienteSubirArchivo" : $(this).attr("data-idexpediente") , 
            			"idTipoDeDocumentoSubir" : $("#idTipoDeDocumentoRequeridoModal").val(),
            			"idInstanciaDeTareaSubirArchivo" : $(this).attr("data-idinstanciadetarea"),
            			"cdr" : $("#cdrDocumentoRequeridoModal").val(),
            			"numeroDocumento" : $("#numeroDocumentoRequeridoModal").val(),
            			"fechaCreacionArchivo" : $("#fechaDocumentoRequeridoModal").val(), 
            			"fechaRecepcionArchivo" : $("#fechaRecepcionDocumentoRequeridoModal").val(),            			
            			"idTags" : $("#tagsDocumentoRequeridoModal").val(),
            			"comentario" : $("#observacionDocumentoRequeridoModal").val(),
            			"tieneFirma" : $('#tieneFirmaDocumentoRequeridoModal').is(':checked'),
            			"convertirAPDF" : convertirAPDF,
            			"tipoDeDocumento" : encodeURIComponent($('#idTipoDeDocumentoRequeridoModal :selected').text()),
            			"esRequerido" : true,
            			"asignarnumerodocumento" : $(this).attr("data-asignarnumerodocumento"),  
            			"validaInstanciaDeTareaEnBE": true    			
            			};    			
        		var validaForm = $("#formSubirDocumentoRequeridoModal").validationEngine('validate');
        		console.log("validaForm: " + validaForm);
        		var validaFechMenor = validaFechaMenor('fechaDocumentoRequeridoModal', 'fechaRecepcionDocumentoRequeridoModal', 'Fecha documento mayor a fecha recepci&oacute;n');
	            console.log("validaFechMenor: " + validaFechMenor);   
	            if ($('#tieneFirmaDocumentoRequeridoModal').is(':checked') && tipoDeArchivo != "application/pdf") {
	            	validaInput = false;
	              	$("#spanDocumentoRequeridoModal").validationEngine('showPrompt', '* El tipo de archivo permitido para un documento firmado es pdf', 'error');
	            }
	            console.log("validaInput: " + validaInput);
	            if ($('#nombreArchivoSpanDocumentoRequeridoModal').text() == "Seleccionar archivo") {
	                $("#spanDocumentoRequeridoModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
	            } 
	            if (tamanoDeArchivo<=0) {
	            	validaTamanoDeArchivo = false;
	            	$("#spanDocumentoRequeridoModal").validationEngine('showPrompt', '* El tamaño del archivo debe ser mayor a 0 bytes', 'error');
		        }
        		if (convertirAPDF==true && validaTamanoDeArchivo
                		&& validaForm == true && validaFechMenor && validaInput
	                      && $('#nombreArchivoSpanDocumentoRequeridoModal').text() != "Seleccionar archivo"
		                        && $('#nombreArchivoSpanDocumentoRequeridoModal').text() != "") {
	       			console.log("Entro a confirmar para convertir a PDF");
	       			bootbox.confirm({
	       		    	  title: "Subir archivo",
	       		        message: "Al subir el archivo este se convertirá a PDF",
	       		        buttons: {
	       		            confirm: {
	       		                label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
	       		                className: 'btn-success'
	       		            },
	       		            cancel: {
	       		                label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
	       		                className: 'btn-danger'
	       		            }
	       		        },
	       		        callback: function (result) {
	       		            console.log('El usuario selecciono: ' + result);
	       		            if (result == true) {	
	                         if (validaForm == true && validaFechMenor && validaInput && validaTamanoDeArchivo
	                                 && $('#nombreArchivoSpanDocumentoRequeridoModal').text() != "Seleccionar archivo"
	                                   && $('#nombreArchivoSpanDocumentoRequeridoModal').text() != "") {
	                             console.log("Haciendo submit");
	                             data.submit();	                            
	                         }  
	       				    }
	       		        }
	       		    });
        		} else {
	              if (validaForm == true && validaFechMenor && validaInput && validaTamanoDeArchivo
	                      && $('#nombreArchivoSpanDocumentoRequeridoModal').text() != "Seleccionar archivo"
	                        && $('#nombreArchivoSpanDocumentoRequeridoModal').text() != "") {
	                  console.log("Haciendo submit");
	                  data.submit();	                  
	              }  
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
			$("#contenedorBEPrincipal").find(".cargando").remove();			
		},
		beforeSend: function(xhr) {
			$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
			$('#subirDocumentoRequeridoModal').modal('hide');
		}, 
		complete : function(data){
        	console.log("data" + data);
       	 	console.log("data.toString()" + data.toString());
       	 	console.log("data.responseJSON: ", data.responseJSON);       	 
         	if (data.responseJSON.cssStatus == "alert alert-danger"){  
         		$("#contenedorBEPrincipal").find(".cargando").remove();       		
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

				if (data.responseJSON.cssStatus == "alert alert-warning") {

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

					document.getElementById('formSubirDocumentoRequeridoModal').reset();									   				   		
	         		$.notify({
	         			message: 'Se ha guardado correctamente el documento requerido!'
	         		},{
	         			type: 'success'
	         		});
	         		var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
	         		urlGetTablaHistorialDeDocumentoPorIdExpediente = 
	             		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#botonSubirDocumentoRequeridoModal").attr("data-idexpediente")
	             		+ "/" + $("#botonSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")
	             		+ "/" + $("#botonSubirDocumentoRequeridoModal").attr("data-nombreexpediente");
	         		console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);  
	         		       		
	         	    // documentos requeridos de los sistemas satelites

	         	    $("#divTablaHistorialDeDocumentosForm").empty();   

	         	    try {
	         	      cargaDocumentoRequeridoFormulario();
	         	      console.log("recarga documentos requeridos de sistemas satelites:");  	      
	         	     }
	         	   catch(err) {      
	         			console.log("No recarga documentos requeridos de sistemas satelites ");  	      
	         	    }

	         	    //--------------------------------------------------------
	         	    $("#divTablaHistorialDeDocumentosForm").empty();
	         	   	$('#divTablaHistorialDeDocumentos').load(urlGetTablaHistorialDeDocumentoPorIdExpediente);
	        	   
	         		var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#botonSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
					console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
	 			
	 				$("#divDetalleDeTarea").load(urlGetDetalleDeTarea ,
	 					function() {
	 						$("#contenedorBEPrincipal").find(".cargando").remove();
	 						seteaBotonAbrirCorreoDeDistribucionModal();
	 	 			});
	 	 			
				}	       	
		   	}	
        }
  	});
};

/*var inicializaBotonSubirDocumentoRequeridoModal = function() {
	$("#botonSubirDocumentoRequeridoModal").off('click').on('click', function () {
		var x = $("#formSubirDocumentoRequeridoModal").validationEngine('hide');
		var validaForm = $("#formSubirDocumentoRequeridoModal").validationEngine('validate');
		console.log("validaForm: " + validaForm);
		if ($('#nombreArchivoSpanDocumentoRequeridoModal').text() == "Seleccionar archivo") {
			$("#spanDocumentoRequeridoModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
		}		
		var validaFechMenor = validaFechaMenor('fechaDocumentoRequeridoModal', 'fechaRecepcionDocumentoRequeridoModal', 'Fecha documento mayor a fecha recepci&oacute;n');
		console.log("validaFechMenor: " + validaFechMenor);		
	});	
};*/
var inicializaBotonSoloSubirDocumentoRequeridoModal = function() {	
	$("#botonSoloSubirDocumentoRequeridoModal").on('click', function () {
		var urlMarcarArchivoComoSubido = $("#urlMarcarArchivoComoSubido").val();
		var convertirAPDF = $('#seleccioneDocumentoRequeridoModal option:selected').attr('data-convertirapdf');
		console.log("urlMarcarArchivoComoSubido: " + urlMarcarArchivoComoSubido);
		console.log("convertirAPDF: " + convertirAPDF);
		var formData = new FormData();
		formData.append("idExpedienteSubirArchivo", $(this).attr("data-idexpediente"));
		formData.append("idTipoDeDocumentoSubir", $(this).attr("data-idtipodedocumentorequeridomodal")); 
		formData.append("idInstanciaDeTareaSubirArchivo", $(this).attr("data-idinstanciadetarea"));
		formData.append("idArchivoEnCMS", $('#seleccioneDocumentoRequeridoModal option:selected').val());
		formData.append("nombreDeArchivo", encodeURIComponent($('#seleccioneDocumentoRequeridoModal option:selected').attr('data-nombre')));
		formData.append("convertirAPDF", convertirAPDF);
		formData.append("tipoDeDocumento", encodeURIComponent($(this).attr("data-nombretipodocumento")));
		formData.append("esRequerido", true);
		formData.append("validaInstanciaDeTareaEnBE", true);	
		if (convertirAPDF=="true") {
			console.log("Entro a confirmar para convertir a PDF");
			bootbox.confirm({
		    	title: "Subir archivo",
		        message: "Al subir el archivo este se convertirá a PDF",
		        buttons: {
		            confirm: {
		                label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
		                className: 'btn-success'
		            },
		            cancel: {
		                label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
		                className: 'btn-danger'
		            }
		        },
		        callback: function (result) {
		            console.log('El usuario selecciono: ' + result);
		            if (result == true) {
						$.ajax({
						    url: urlMarcarArchivoComoSubido,
						    type: 'POST',
						    data: formData,
						    async: true,
						    cache: false,
						    contentType: false,
						    processData: false,
						    success: function (returnData) {
						    	console.log("SUCCESS -- marcarArchivoComoSubido: ", returnData);	    	
						    },
						    error : function(e) {
								console.log("ERROR: ", e);
								$("#contenedorBEPrincipal").find(".cargando").remove();		
						      	$.notify({
						   			message: 'Error al subir el archivo'
						   		},{
						   			type: 'danger'
						   		});	
								},
							done : function(e) {
								console.log("DONE");
							},
							beforeSend: function(xhr) {	
								$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
								$('#subirDocumentoRequeridoModal').modal('hide');
							},
							complete : function(returnData) {
								document.getElementById('formSubirDocumentoRequeridoModal').reset();	
								$('#tagsDocumentoRequeridoModal').val(null).trigger("change");
								if (returnData.responseJSON.cssStatus == "alert alert-danger"){	
									$("#contenedorBEPrincipal").find(".cargando").remove();				         	
					        		$.notify({
					         			message: 'Problemas al guardar el documento, favor intentelo nuevamente'
					         		},{
					         			type: 'danger'
					         		});	
							   	} else {
									if (returnData.responseJSON.cssStatus == "alert alert-warning") {
										$.notify({
								   			message: returnData.responseJSON.resultadoSubirArchivo
								   		},{
								   			type: 'warning'
								   		});
										if (returnData.responseJSON.recarga == true) {
			                     			/*if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
					            				actualizaTareas("tareasEnEjecucion", urlGetTareasEnEjecucion, false);
					            			}*/	
					            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
					            			$("#divTabsDetalleDeTarea").addClass('hide');	
				            				$("html, body").animate({ scrollTop: 0 }, "slow");
				            				$("#contenedorBEPrincipal").find(".cargando").remove();	
					                    }
									} else {
										$.notify({
								   			message: 'Se ha guardado correctamente el documento requerido!'
								   		},{
								   			type: 'success'
								   		});
										// Se recarga el DIV de los documentos requeridos de los formularios									
									    try {
						         	      cargaDocumentoRequeridoFormulario();
						         	      console.log("recarga documentos requeridos de sistemas satelites:");  	      
						         	    } catch (err) {      
						         			console.log("No recarga documentos requeridos de sistemas satelites ");  	      
						         	    }									
										// ------------------------------------------------------------------								   		
								      	var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
						       			urlGetTablaHistorialDeDocumentoPorIdExpediente = 
						           		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idexpediente")
						           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")
						           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-nombreexpediente");
						       			console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);					       			
						       	        $("#divTablaHistorialDeDocumentosForm").empty();
						       			$('#divTablaHistorialDeDocumentos').load(urlGetTablaHistorialDeDocumentoPorIdExpediente);
						       			var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
										console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
												
										$("#divDetalleDeTarea").load(urlGetDetalleDeTarea, function() {
											$("#contenedorBEPrincipal").find(".cargando").remove();
											if ($("#divTabsDetalleDeTarea").length>0) {
												$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
											}
											seteaBotonAbrirCorreoDeDistribucionModal();	
						 	 			});	
									}
					        	}				     	
							}	            		
						 });
				    }
		        }
		    });
		} else {
			console.log("No entro a confirmar para convertir a PDF");
			$.ajax({
			    url: urlMarcarArchivoComoSubido,
			    type: 'POST',
			    data: formData,
			    async: true,
			    cache: false,
			    contentType: false,
			    processData: false,
			    success: function (returnData) {
			    	console.log("SUCCESS -- marcarArchivoComoSubido: ", returnData);	    	
			    },
			    error : function(e) {
					console.log("ERROR: ", e);		
					$("#contenedorBEPrincipal").find(".cargando").remove();
			     	$.notify({
			   			message: 'Error al subir el archivo'
			   		},{
			   			type: 'danger'
			   		});	
					},
				done : function(e) {
					console.log("DONE");
				},
				beforeSend: function(xhr) {
					$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
					$('#subirDocumentoRequeridoModal').modal('hide');
				},
				complete : function(returnData) {
					document.getElementById('formSubirDocumentoRequeridoModal').reset();	
					$('#tagsDocumentoRequeridoModal').val(null).trigger("change");
					if (returnData.responseJSON.cssStatus == "alert alert-danger"){	
						$("#contenedorBEPrincipal").find(".cargando").remove();				         	
		        		$.notify({
		         			message: 'Problemas al guardar el documento, favor intentelo nuevamente'
		         		},{
		         			type: 'danger'
		         		});	
				   	} else {

					   	if (returnData.responseJSON.cssStatus == "alert alert-warning") {

					   		$.notify({
					   			message: returnData.responseJSON.resultadoSubirArchivo
					   		},{
					   			type: 'warning'
					   		});
							if (returnData.responseJSON.recarga == true) {
                     			/*if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
		            				actualizaTareas("tareasEnEjecucion", urlGetTareasEnEjecucion, false);
		            			}*/	
		            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
		            			$("#divTabsDetalleDeTarea").addClass('hide');	
	            				$("html, body").animate({ scrollTop: 0 }, "slow");
	            				$("#contenedorBEPrincipal").find(".cargando").remove();	
		                    }
					   		
						} else {

							$.notify({
					   			message: 'Se ha guardado correctamente el documento requerido!'
					   		},{
					   			type: 'success'
					   		});		      
					      	var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
			       			urlGetTablaHistorialDeDocumentoPorIdExpediente = 
			           		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idexpediente")
			           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")
			           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-nombreexpediente");
			       			console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);		       			

							// Se recarga el DIV de los documentos requeridos de los formularios
							
							try {
				         		cargaDocumentoRequeridoFormulario();
				         	    console.log("recarga documentos requeridos de sistemas satelites:");  	      
				         	} catch (err) {      
				         		console.log("No recarga documentos requeridos de sistemas satelites ");  	      
				         	}
							
							// ------------------------------------------------------------------					   		
			       	    	
			       	        $("#divTablaHistorialDeDocumentosForm").empty();
			       	    	$('#divTablaHistorialDeDocumentos').load(urlGetTablaHistorialDeDocumentoPorIdExpediente);		       	    	
			       			var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
							console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);					
							$("#divDetalleDeTarea").load(urlGetDetalleDeTarea, function() {
		 						$("#contenedorBEPrincipal").find(".cargando").remove();
		 						if ($("#divTabsDetalleDeTarea").length>0) {
				        			$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
				        		}
		 						seteaBotonAbrirCorreoDeDistribucionModal();
		 	 				});	
							
						}					   	
				   			
				   	}		      			     	
				}	            		
			 });
		}	
	});	
};

var inicializaTieneFirmaDocumentoRequeridoModal = function() {
	$("#tieneFirmaDocumentoRequeridoModal").off('click').on('click', function () {
		if ($('#tieneFirmaDocumentoRequeridoModal').is(':checked')) {
			$("#documentoRequeridoModal").attr("accept", "application/pdf");
		} else {
			$("#documentoRequeridoModal").removeAttr("accept");
		}
	});	
};


var inicializaBotonSoloSubirDocumentoRequeridoConAsignacionModal = function() {	
	$("#botonSubirDocumentoRequeridoConAsignacionModal").on('click', function () {


         // Validaciones
         
         var validaForm = $("#formSubirDocumentoRequeridoModal").validationEngine('validate');
     	 console.log("validaForm: " + validaForm);    
     	  	 
     	 var validaFechMenor = validaFechaMenor('fechaDocumentoRequeridoConAsignacionModal', 'fechaRecepcionDocumentoRequeridoConAsignacionModal', 'Fecha documento mayor a fecha recepci&oacute;n');
         console.log("validaFechMenor: " + validaFechMenor);   
         
         if (validaForm == false || validaFechMenor == false) {
            return false;
         } 

     	
		var urlMarcarArchivoComoSubido = $("#urlMarcarArchivoComoSubido").val();
		var convertirAPDF = $('#seleccioneDocumentoRequeridoModal option:selected').attr('data-convertirapdf');
		console.log("urlMarcarArchivoComoSubido: " + urlMarcarArchivoComoSubido);
		console.log("convertirAPDF: " + convertirAPDF);
		var contextPath = "${pageContext.request.contextPath}";

  
		var subirArchivoDTO = {};
		subirArchivoDTO["idExpedienteSubirArchivo"] = $(this).attr("data-idexpediente");
		subirArchivoDTO["idTipoDeDocumentoSubir"] = $(this).attr("data-idtipodedocumentorequeridomodal");
		subirArchivoDTO["idInstanciaDeTareaSubirArchivo"] = $(this).attr("data-idinstanciadetarea");
		//subirArchivoDTO["cdr"] = $("#cdrDocumentoRequeridoConAsignacionModal").val();// Preguntar si se usa
		subirArchivoDTO["numeroDocumento"] = $("#numeroDocumentoRequeridoConAsignacionModal").val();
		subirArchivoDTO["fechaCreacionArchivo"] =  $("#fechaDocumentoRequeridoConAsignacionModal").val();
		subirArchivoDTO["fechaRecepcionArchivo"] = $("#fechaRecepcionDocumentoRequeridoConAsignacionModal").val();
		subirArchivoDTO["idTags"] = $("#tagsDocumentoRequeridoConAsignacionModal").val();
		subirArchivoDTO["comentario"] = $("#observacionDocumentoRequeridoConAsignacionModal").val();
		// subirArchivoDTO["tieneFirma"] = $('#tieneFirmaDocumentoRequeridoConAsignacionModal').is(':checked'); // Preguntar si se usa
		subirArchivoDTO["convertirAPDF"] = convertirAPDF;
		subirArchivoDTO["tipoDeDocumento"] = $('#idTipoDeDocumentoRequeridoConAsignacionModal :selected').text();
		subirArchivoDTO["esRequerido"] = true;

		//  Atributos del metodo a unificar 
		
		subirArchivoDTO["idArchivoEnCMS"] = $('#seleccioneDocumentoRequeridoModal option:selected').val();
		subirArchivoDTO["nombreDeArchivo"] = encodeURIComponent($('#seleccioneDocumentoRequeridoModal option:selected').attr('data-nombre'));
		subirArchivoDTO["validaInstanciaDeTareaEnBE"] = true;
		
		
		
		if (convertirAPDF=="true") {
			console.log("Entro a confirmar para convertir a PDF");
			bootbox.confirm({
		    	title: "Subir archivo",
		        message: "Al subir el archivo este se convertirá a PDF",
		        buttons: {
		            confirm: {
		                label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
		                className: 'btn-success'
		            },
		            cancel: {
		                label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
		                className: 'btn-danger'
		            }
		        },
		        callback: function (result) {
		            console.log('El usuario selecciono: ' + result);
		            
		            if (result == true) {
						$.ajax({
							type : "POST",
							contentType : "application/json",
							url :contextPath + "/subirArchivoConAsignacion",
							data : JSON.stringify(subirArchivoDTO),
							dataType : 'json',
							timeout : 100000,
						    success: function (returnData) {
						    	console.log("SUCCESS -- subirArchivoConAsignacion: ", returnData);	    	
						    },
						    error : function(e) {
						    	$("#contenedorBEPrincipal").find(".cargando").remove();			
								console.log("ERROR: ", e);		
						      	$.notify({
						   			message: 'Error al subir el archivo'
						   		},{
						   			type: 'danger'
						   		});	
								},
							done : function(e) {
								console.log("DONE");
							},
							beforeSend: function(xhr) {
								$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
								$('#subirDocumentoRequeridoModal').modal('hide');
							},
							complete : function(returnData) {
								document.getElementById('formSubirDocumentoRequeridoModal').reset();	
								$('#tagsDocumentoRequeridoModal').val(null).trigger("change");

								// Se recarga el DIV de los documentos requeridos de los formularios
								
								// cargaDocumentoRequeridoFormulario()
								
								// ------------------------------------------------------------------		
								
								if (returnData.responseJSON.cssStatus == "alert alert-danger"){	
									$("#contenedorBEPrincipal").find(".cargando").remove();				         	
					        		$.notify({
					         			message: 'Problemas al guardar el documento, favor intentelo nuevamente'
					         		},{
					         			type: 'danger'
					         		});	
							   	} else {

							   		if (returnData.responseJSON.cssStatus == "alert alert-warning") {

							   			$.notify({
								   			message: returnData.responseJSON.resultadoSubirArchivo
								   		},{
								   			type: 'warning'
								   		});
										if (returnData.responseJSON.recarga == true) {
			                     			/*if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
					            				actualizaTareas("tareasEnEjecucion", urlGetTareasEnEjecucion, false);
					            			}*/	
					            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
					            			$("#divTabsDetalleDeTarea").addClass('hide');	
				            				$("html, body").animate({ scrollTop: 0 }, "slow");
				            				$("#contenedorBEPrincipal").find(".cargando").remove();	
					                    }
							   			
									} else {

										$.notify({
								   			message: 'Se ha guardado correctamente el documento requerido!'
								   		},{
								   			type: 'success'
								   		});
								      	var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
						       			urlGetTablaHistorialDeDocumentoPorIdExpediente = 
						           		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idexpediente")
						           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")
						           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-nombreexpediente");
						       			console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);					       		
						       	        $("#divTablaHistorialDeDocumentosForm").empty();
						       	     	$('#divTablaHistorialDeDocumentos').load(urlGetTablaHistorialDeDocumentoPorIdExpediente);
						       			var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
										console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);									
										$("#divDetalleDeTarea").load(urlGetDetalleDeTarea, function() {
					 						$("#contenedorBEPrincipal").find(".cargando").remove();
					 						if ($("#divTabsDetalleDeTarea").length>0) {
							        			$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
							        		}
					 						seteaBotonAbrirCorreoDeDistribucionModal();
					 	 				});
										
									}
				 	 				
					        	}				     	
							}	            		
						 });
				    }
		        }
		    });
		} else {
			console.log("No entro a confirmar para convertir a PDF");
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url :contextPath + "/subirArchivoConAsignacion",
				data : JSON.stringify(subirArchivoDTO),
				dataType : 'json',
				timeout : 100000,
			    success: function (returnData) {
			    	console.log("SUCCESS -- marcarArchivoComoSubido: ", returnData);	    	
			    },
			    error : function(e) {
			    	$("#contenedorBEPrincipal").find(".cargando").remove();
					console.log("ERROR: ", e);		
			      	$.notify({
			   			message: 'Error al subir el archivo'
			   		},{
			   			type: 'danger'
			   		});	
					},
				done : function(e) {
					console.log("DONE");
				},
				beforeSend: function(xhr) {
					$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
					$('#subirDocumentoRequeridoModal').modal('hide');
				},
				complete : function(returnData) {
					document.getElementById('formSubirDocumentoRequeridoModal').reset();	
					$('#tagsDocumentoRequeridoModal').val(null).trigger("change");
					if (returnData.responseJSON.cssStatus == "alert alert-danger"){	
						$("#contenedorBEPrincipal").find(".cargando").remove();				         	
		        		$.notify({
		         			message: 'Problemas al guardar el documento, favor intentelo nuevamente'
		         		},{
		         			type: 'danger'
		         		});	
				   	} else {

						if (returnData.responseJSON.cssStatus == "alert alert-warning") {

							$.notify({
					   			message: returnData.responseJSON.resultadoSubirArchivo
					   		},{
					   			type: 'warning'
					   		});
							if (returnData.responseJSON.recarga == true) {
                     			/*if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
		            				actualizaTareas("tareasEnEjecucion", urlGetTareasEnEjecucion, false);
		            			}*/	
		            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
		            			$("#divTabsDetalleDeTarea").addClass('hide');	
	            				$("html, body").animate({ scrollTop: 0 }, "slow");
	            				$("#contenedorBEPrincipal").find(".cargando").remove();	
		                    }
							
						} else {

							$.notify({
					   			message: 'Se ha guardado correctamente el documento requerido!'
					   		},{
					   			type: 'success'
					   		});		      
					      	var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
			       			urlGetTablaHistorialDeDocumentoPorIdExpediente = 
			           		urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idexpediente")
			           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")
			           		+ "/" + $("#botonSoloSubirDocumentoRequeridoModal").attr("data-nombreexpediente");
			       			console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);		       		
			       	        $("#divTablaHistorialDeDocumentosForm").empty();
			       	    	$('#divTablaHistorialDeDocumentos').load(urlGetTablaHistorialDeDocumentoPorIdExpediente);
			       			var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#botonSoloSubirDocumentoRequeridoModal").attr("data-idinstanciadetarea")+"&muestraSoloDocumentosDeSalida="+true;
							console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);						
							$("#divDetalleDeTarea").load(urlGetDetalleDeTarea, function() {
		 						$("#contenedorBEPrincipal").find(".cargando").remove();
		 						if ($("#divTabsDetalleDeTarea").length>0) {
				        			$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
				        		}
		 						seteaBotonAbrirCorreoDeDistribucionModal();
		 	 				});							
						}	
				   	}		      			     	
				}	            		
			 });
		}	
	});	
};



$(document).ready(function() {
	$(inicializaSelect2SubirDocumentoRequeridoModal);	
	$(inicializaSelect2MultipleSubirDocumentoRequeridoModal);
	$(inicializaFechasDocumentoRequeridoModal);
	$(inicializaFileUploadDocumentoRequeridoModal);
	$(inicializaBotonSoloSubirDocumentoRequeridoModal);
	$(inicializaTieneFirmaDocumentoRequeridoModal);
	$(inicializaBotonSoloSubirDocumentoRequeridoConAsignacionModal);
  
});
</script>
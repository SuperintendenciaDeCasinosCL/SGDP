jQuery(document).ready(function($) {	
	jQuery("#formCrearExpediente").validationEngine();		
});

function cargarProcesos() {	
	
	var idMacroProceso = $('#idMacroProceso').val();
	
	if (idMacroProceso == ""){
		return;
	}
	
	var dialog = bootbox.dialog({  		   
  		message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Sub Procesos...</p>',
  		closeButton: false
  	});
	
	console.log("idMacroProceso: ", idMacroProceso);
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/sgdp/getProcesosPorIdMacroProceso?idMacroProceso="+idMacroProceso,		
		timeout : 100000,
		success : function(returnData) {
			console.log("SUCCESS -- getProcesosPorIdMacroProceso: ", returnData);				
		},
		error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData) {		
			console.log("COMPLETE -- getProcesosPorIdMacroProceso: ", returnData);	
			$('#idProceso').empty();
            $('#idProceso').append($('<option>').text("Seleccione Proceso").attr('value', ""));
            $.each(returnData.responseJSON, function(i, obj){
                $('#idProceso').append($('<option>').text(obj.nombreProceso).attr('value', obj.idProceso));
            });
			dialog.modal('hide');
		}
	}); 
	
}

function crearExpedienteSubirArchivo() {
	
	$("#buttonSubirCrearExpediente").attr("disabled", "disabled");
	
	var validaForm = $("#formCrearExpediente").validationEngine('validate');
	
	if (!validaForm) {
		return;
	}
	
	$("#idTipoDeDocumentoSubirExpediente").empty();
	
	$('#buttonCrearExpediente').addClass('disabled');
	
	var urlCrearExpediente = $("#urlCrearExpediente").val();
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();
	
	console.log("urlCrearExpediente: " + urlCrearExpediente);
	console.log("urlGetInstanciasDeTarea: " + urlGetInstanciasDeTarea);
	console.log("urlGetTareasEnEjecucion: " + urlGetTareasEnEjecucion);

	var expedienteDTO = {}
	expedienteDTO["materia"] =  $("#materia").val();
	expedienteDTO["idAutor"] = $("#idAutor").val();
	expedienteDTO["idProceso"] = $("#idProcesosVigente").val();
	
	if( $('#idComplejidadCrearExpediente').length ) {
		expedienteDTO['idComplejidad'] = $("#idComplejidadCrearExpediente").val();
		expedienteDTO['motivoComplejidad'] = $("#motivoComplejidadCrearExpediente").val();
	}
	
	const selectedAutor = $("#idAutor").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : urlCrearExpediente,
        data : JSON.stringify(expedienteDTO),
        dataType : 'json',
        timeout : 100000,
	    success: function (returnData) {
	    	console.log("SUCCESS -- crearExpediente: ", returnData);	 
		},
	    error : function(e) {
			console.log("ERROR: ", e);	
			$("#buttonSubirCrearExpediente").prop("disabled", false);	
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData) {
			console.log("COMPLETE -- crearExpediente: " + returnData.responseText);
			console.log("COMPLETE -- crearExpediente 2 : " + returnData.responseJSON);
			console.log("COMPLETE -- crearExpediente 2 : " + returnData.responseJSON.mensajeRespuesta);
		 
			
			
	        $("#tablaFirmas").fadeOut("slow").load(urlGetInstanciasDeTarea).fadeIn('slow');
	
			//Cargar Tareas
			/*$('#tareasBandejaDeEntrada').each(function(){        	 
				$(this).fadeOut("slow").load(urlGetInstanciasDeTarea, function() {
					 if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
						 $('#tareasEnEjecucion').each(function(){        	 
						        $(this).fadeOut("slow").load(urlGetTareasEnEjecucion).fadeIn('slow');
						    });	
					 }
			    }).fadeIn('slow');
			});*/
			
			//$('#tareasBandejaDeEntrada').fadeOut("slow").load(urlGetInstanciasDeTarea).fadeIn('slow');
			
			//$('#tareasEnEjecucion').fadeOut("slow").load(urlGetTareasEnEjecucion).fadeIn('slow');
			
			/*if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
				actualizaTareas("tareasEnEjecucion", urlGetTareasEnEjecucion, false);
			}*/			
			
			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
			
			$('#buttonCrearExpediente').removeClass('disabled');
			 
			 
			  //  cerrar el mensaje de cargando			
			  //  dialog.modal('hide');
				
				
				if (returnData.responseJSON.mensajeError == "OK") {
					
					cargaTipoDeDocumentoSubirExpediente($("#idProcesosVigente option:selected").val());
					
					$.notify({
						message: returnData.responseJSON.mensajeRespuesta
						//message: "Expediente Creado Exitosamente"
					},{
						type: 'success'
					});
					
					document.getElementById("idExpedienteSubirDocumentoExpediente").value = returnData.responseJSON.idExpediente;
					document.getElementById("nombreExpedienteSubirDocumentoExpediente").value = returnData.responseJSON.nombreExpediente;
					document.getElementById("idInstanciaDeTareaOrigenSubirDocumentoExpediente").value = returnData.responseJSON.idInstanciaDeTareaSalida;										
					document.getElementById("idExpedienteSubirArchivoAntecedenteTabla").value =	returnData.responseJSON.idExpediente;				
					document.getElementById("idInstanciaDeTareaSubirArchivoAntecedenteTabla").value = returnData.responseJSON.idInstanciaDeTareaSalida;
					
					// agregado por Hugo Cifuentes para seleccionar de manera automatica el autor
					$("#idAutorSubirDocumentoExpediente").val(selectedAutor).change();
					$("#idAutorSubirDocumentoExpediente").attr("disabled", "disabled");
									
					$("#cabeceraSubirDocumentoExpdienteModal").html("Subir Documento " + returnData.responseJSON.nombreExpediente);
			
					$('#fechaDeCreacionDocumentoExpediente').val(moment(new Date().getTime()).format('DD/MM/YYYY'));
					$('#fechaRecepcionDocumentoExpediente').val(moment(new Date().getTime()).format('DD/MM/YYYY'));
					
					$('#fechaDeCreacionDocumentoExpediente').on('keyup keypress', function(e) { e.preventDefault(); return false;  })
					$('#fechaRecepcionDocumentoExpediente').on('keyup keypress', function(e) { e.preventDefault(); return false; 
					 })
					
					$('#crearExpedienteModal').modal('hide');
					   
					setTimeout(function(){
						 $('#subirDocumentoExpdienteModal').modal({backdrop: 'static', keyboard: false});    
					}, 500);
										
				}else{
					$.notify({
						message: returnData.responseJSON.mensajeRespuesta
					},{
						type: 'danger'
					});				
				}		
				$("#buttonSubirCrearExpediente").prop("disabled", false);
		}
	  });
	  
}

function cargaTipoDeDocumentoSubirExpediente(idProcesoSeleccionado) {
	console.log("idProcesoSeleccionado: " + idProcesoSeleccionado);
	var urlGetTipoDeDocumentoDTOPorIdProceso = $("#urlGetTipoDeDocumentoDTOPorIdProceso").val()+"?idProceso="+idProcesoSeleccionado;
	var urlGetTiposDeDocumentosDTO = $("#urlGetTiposDeDocumentosDTO").val();
	$.get(urlGetTipoDeDocumentoDTOPorIdProceso, function(tipoDeDocumentoDTO){
		console.log("tipoDeDocumentoDTO: " + tipoDeDocumentoDTO);
		if (jQuery.isEmptyObject(tipoDeDocumentoDTO)) {
			$.get(urlGetTiposDeDocumentosDTO, function(listaTiposDeDocumentosDTO){
				 $.each(listaTiposDeDocumentosDTO, function(i, obj){
					 $('#idTipoDeDocumentoSubirExpediente').append($('<option>').text(obj.nombreDeTipoDeDocumento).attr('value', obj.idTipoDeDocumento));
		         });	
				 //$('#idTipoDeDocumentoSubirExpediente').val($('#idTipoDeDocumentoSubirExpediente > option:contains("Antecedente")').val()).change();
				 $("#idTipoDeDocumentoSubirExpediente").val($('#idTipoDeDocumentoSubirExpediente option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();
				 $('#idTipoDeDocumentoSubirExpediente').attr('disabled', 'disabled');
			});
		} else {
			$('#idTipoDeDocumentoSubirExpediente').append($('<option>').text(tipoDeDocumentoDTO.nombreDeTipoDeDocumento).attr('value', tipoDeDocumentoDTO.idTipoDeDocumento));
			$('#idTipoDeDocumentoSubirExpediente').prop('selected', true).prop('disabled' , 'disabled');
		}		
	});
}

function subirDocumentoConductor() {
	$("#idAutorSubirDocumentoExpediente").removeAttr("disabled");
	$("#botonSubirDocumentoConductor").attr("disabled", "disabled");
	
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();	
	var validaForm = $("#formSubirDocumentoExpediente").validationEngine('validate');

	if (!validaForm) {
		return;
	}
	$('#botonSubirArchivo').addClass('disabled');	
	console.log("En subirArhivoModal");		
	$("#idTipoDeDocumentoSubirExpedienteHidden").val($("#idTipoDeDocumentoSubirExpediente").val());
	$("#tipoDeDocumentoSubirExpediente").val(encodeURIComponent($('#idTipoDeDocumentoSubirExpediente :selected').text()));
	console.log($("#tipoDeDocumentoSubirExpediente").val());
	console.log($("#idTipoDeDocumentoSubirExpedienteHidden").val());
	$("#numeroDocumentoExpediente").val($("#numeroDocumentoExpediente").val() === '' ? 0 : $("#numeroDocumentoExpediente").val());	
	var form = $('#formSubirDocumentoExpediente')[0]; 
	var formData = new FormData(form);

	var urlSubirDocumento = $('#urlSubirDocumento').val();	
	console.log("urlSubirDocumento: " + urlSubirDocumento);	
	  $.ajax({
	    url: urlSubirDocumento,
	    type: 'POST',
	    data: formData,
	    async: true,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returnData) {
	    	console.log("SUCCESS -- subirArhivoModal: ", returnData);	    	 
	    },
	    error : function(e) {
			console.log("ERROR: ", e);		
			$("#botonSubirDocumentoConductor").prop("disabled", false);	
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData){
			
			console.log("COMPLETE -- subirArhivoModal: ", returnData.responseJSON);	
			
			//Cargar Tareas
			 $('#tareasBandejaDeEntrada').each(function(){        	 
			        $(this).fadeOut("slow").load(urlGetInstanciasDeTarea).fadeIn('slow');
			    });
			//Cargar Tareas en Ejecucion
			/* if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
				 $('#tareasEnEjecucion').each(function(){        	 
				        $(this).fadeOut("slow").load(urlGetTareasEnEjecucion).fadeIn('slow');
				    });	
			 }*/				
			 document.getElementById("cartaRelacionadaAntecedentesTabla").value = returnData.responseJSON.nombreDocumentoConductorSalida;	
			 
		    $("#cabeceraSubirDocumentoModalAntecedente").html("Subir Adjunto " + returnData.responseJSON.nombreDocumentoConductorSalida);
		    $("#cabeceraSubirMasDocumentoBotonesModal").html("Documento Conductor  " + returnData.responseJSON.nombreDocumentoConductorSalida);
		    
		   	if (returnData.responseJSON.cssStatus == "alert alert-danger"){
		   		$.notify({
					message: returnData.responseJSON.resultadoSubirDocumento
				},{
					type: 'danger'
				});
		   		
		   	}else{
		   		
		   		$.notify({
					// message: returnData.responseJSON.resultadoSubirDocumento
		   			message: 'Documento conductor Subido Exitosamente'
				},{
					type: 'success'
				});
		   		
				 $('#subirDocumentoExpdienteModal').modal('hide');
				 
				 setTimeout(function(){
					 // Elimar Tr del body
					 $("tbody", "#tablaDocumentosAdjuntosLista").remove();
					 // Agregar Columna Documento Adjunto
					 agregarDocumentoAdjunto();					 
					 $('#subirDocumentoAntecedenteTablaModal').modal({backdrop: 'static', keyboard: false});
				 }, 500);
				 
				 
				 document.getElementById("documentoConductor").value = ""; 				 
				 $("#idTipoDeDocumentoSubirExpediente").val("").trigger("change");				 
				 document.getElementById("cdrExpediente").value = ""; 
				 document.getElementById("numeroDocumentoExpediente").value = ""; 
				 document.getElementById("fechaDeCreacionDocumentoExpediente").value = ""; 
				 document.getElementById("fechaRecepcionDocumentoExpediente").value = ""; 
				 $("#idAutorSubirDocumentoExpediente").val("").trigger("change");			
				 document.getElementById("descripcionExpediente").value = ""; 
		   	}
			$("#botonSubirDocumentoConductor").prop("disabled", false);
		}
		
	  });	
$("#idAutorSubirDocumentoExpediente").attr("disabled", "disabled");
}


function buscarNumeroTareas(tipoCreacion) {	

	if (tipoCreacion == 0){
		var idSubProceso = $('#idProcesosVigente').val();
	} else if (tipoCreacion == 1){
		var idSubProceso = $('#idProceso').val();
    }
	 
	if(idSubProceso ==""){
		return;
	}
	
	console.log("idProceso: ", idSubProceso);
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/sgdp/getTareaPorIdSubProceso?idSubProceso="+idSubProceso,		
		timeout : 100000,
		success : function(returnData) {
			console.log("SUCCESS -- getProcesosPorIdMacroProceso: ", returnData);	
			if ( returnData == "DESHABILITAR"){
						
				$.notify({
					message: 'Se deshabilita el boton por tener mas de una tarea inicial '
				},{
					type: 'warning'
				});

                if (tipoCreacion == 0){
			       document.getElementById("buttonSubirCrearExpediente").disabled = true;
                }else if (tipoCreacion == 1){
    			  document.getElementById("buttonAyudaProcesoSubproceso").disabled = true;
                }
                
			}else{				
		        if (tipoCreacion == 0){
		        	document.getElementById("buttonSubirCrearExpediente").disabled = false;
                }else if (tipoCreacion == 1){
                	document.getElementById("buttonAyudaProcesoSubproceso").disabled = false;
                }
			}
			
		},
		error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},	
	}); 
	
}

function subirDocumentoAdjuntoModal(accion) {		
	
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();
	
	var validaForm = $("#formSubirDocumentoAntecedente").validationEngine('validate');
	
	if (!validaForm) {
		return;
	}
	
	//$('#subirArchivoModal').modal('hide');
	
	/*
	var dialog = bootbox.dialog({	   
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Archivo...</p>'	   
	});	
	*/
	$('#formSubirDocumentoAntecedente').addClass('disabled');
	
  //	console.log("En subirArhivoModal");
	
	var form = $('#formSubirDocumentoAntecedente')[0]; 
	var formData = new FormData(form);
	
	//var idInstanciaDeTareaSubirArchivo = $("#idInstanciaDeTareaSubirArchivo").val();
	//console.log("idInstanciaDeTareaSubirArchivo: " + idInstanciaDeTareaSubirArchivo);	
	
	// var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTareaSubirArchivo+"&muestraSoloDocumentosDeSalida=true";
	var urlSubirArchivo = $("#urlSubirArchivo").val();
	
	// console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
	
	console.log("urlSubirArchivo: " + urlSubirArchivo);
	
	  $.ajax({
	    url: urlSubirArchivo,
	    type: 'POST',
	    data: formData,
	    async: true,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returnData) {
	    	console.log("SUCCESS -- subirArhivoModal: ", returnData);
	    	 
	    },
	    error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData){
			console.log("COMPLETE -- subirArhivoModal: ", returnData.responseJSON);
			
	       // En el caso que salga todo bien	
			
			//Cargar Tareas
			 $('#tareasBandejaDeEntrada').each(function(){        	 
			        $(this).fadeOut("slow").load(urlGetInstanciasDeTarea).fadeIn('slow');
			    });
			//Cargar Tareas en Ejecucion
			/* if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
				 $('#tareasEnEjecucion').each(function(){        	 
				        $(this).fadeOut("slow").load(urlGetTareasEnEjecucion).fadeIn('slow');
				    });	
			 }	*/
			
		   	if (returnData.responseJSON.cssStatus == "alert alert-danger"){
		   		$.notify({
		   			message: returnData.responseJSON.setResultadoSubirArchivo
				},{
					type: 'danger'
				});
		   		
		   	}else{
		   		
		   		$.notify({
				//	message: returnData.responseJSON.setResultadoSubirArchivo
				    message :"Se ingreso el antecedente correctamente"
				},{
					type: 'success'
				});
		   		
                  if (accion == 1){ // Cerrar Modal
		   		  // Limpiar campos del formulario
				  $('#subirDocumentoAntecedenteModal').modal('hide');
                  }	
					// Limpiar campos del formulario
				  document.getElementById("archivoAntecedente").value = "";
				  $("#idTipoDeDocumentoSubirAntecedente").val("").trigger("change");
				  document.getElementById("numeroDocumentoAntecedente").value = "";
				  document.getElementById("fechaCreacionArchivoAntecedente").value = "";
				  document.getElementById("fechaRecepcionArchivoAntecedente").value = "";
				  document.getElementById("descripcionAntecedente").value = "";
				  $("#idAutorSubirDocumentoAntecedente").val("").trigger("change");
		   		   				   		
		   	}	
		}
	  });
	
}

function abrirCrearExpediente() {
	$("#buttonSubirCrearExpediente").removeAttr("disabled");
	$("#botonSubirDocumentoConductor").removeAttr("disabled");
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	console.log("urlSessionValida: " + urlSessionValida);
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  
	    	  $("#divTabsDetalleDeTarea").addClass("hide");
	    	  $('#tablaTareas').DataTable().rows('.selected').nodes().to$().removeClass('selected');
	    	  
	    	  // Limpiar campos del formulario	    	     	  
	    	  document.getElementById("materia").value = "";
	    	  $("#idAutor").val("").trigger("change");
	    	  $("#idMacroProceso").val("").trigger("change");
	    	  $("#idProcesosVigente").val("").trigger("change");
	    	  $('#crearExpedienteModal').modal({backdrop: 'static', keyboard: false});
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_self');
	                          }
	             );
	      }
	});  
}

function cerrarModalBotones() {	
	 $('#subirMasDocumentoExpdienteModal').modal('hide');
}

function adjuntarOtroDocConductor(){
	 $('#subirDocumentoExpdienteModal').modal('show');
}

function cerrarModalSubirAdjunto() {	
	 $('#subirDocumentoAntecedenteTablaModal').modal('hide');
}


// Estas funciones son para que el modal que se habra siempre salga en la primera posición

$(document).ready(function () {

    $('.modal').on('show.bs.modal', function (event) {
        var idx = $('.modal:visible').length;
        $(this).css('z-index', 1040 + (10 * idx));
    });
    $('.modal').on('shown.bs.modal', function (event) {
        var idx = ($('.modal:visible').length) - 1; // raise backdrop after animation.
        $('.modal-backdrop').not('.stacked').css('z-index', 1039 + (10 * idx));
        $('.modal-backdrop').not('.stacked').addClass('stacked');
    });

});


$(function() {
    $('.fechaCreacionArchivoAntecedenteTablaDiv').datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY'
    }).on("dp.show", function (e) {
    	$(".table-responsive").css("padding-bottom", "207px");
    })
    .on("dp.hide", function (e) {
    	$(".table-responsive").css("padding-bottom", "0px");
    });
    jQuery("#formSubirDocumento").validationEngine();
});

$('.fechaCreacionArchivoAntecedenteTabla').val(moment(new Date().getTime()).format('DD/MM/YYYY'));

$(function() {
    $('.fechaCreacionArchivoAntecedenteTabla').datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY'
    }).on("dp.show", function (e) {
    	$(".table-responsive").css("padding-bottom", "175px");
    })
    .on("dp.hide", function (e) {
    	$(".table-responsive").css("padding-bottom", "0px");
    });
    jQuery("#formSubirDocumento").validationEngine();
});


$(document).ready(function() {

	$(".idTipoDeDocumentoSubirAntecedenteTabla").select2({
	    theme: "bootstrap",
	    dropdownParent: $("#subirDocumentoAntecedenteTablaModal"),
	    language: "es"
	});
	  
	$(".idAutorSubirDocumentoAntecedenteTabla").select2({
		    theme: "bootstrap",
		    dropdownParent: $("#subirDocumentoAntecedenteTablaModal"),
		    language: "es"
		});
});


/*
$(function () {
	var sacar = 0
    var notify = "";

	
    $('.archivoAntecedenteTabla').fileupload({
        dataType: 'json',
        url: '/sgdp/guardarAdjuntoTabla',
        add: function (e, data) {
         
        	if ($('.descripcionAntecedenteTabla').validationEngine('validate') == false ||
        	   $('.idTipoDeDocumentoSubirAntecedenteTabla').validationEngine('validate') == false ||
        	   $('.numeroDocumentoAntecedenteTabla').validationEngine('validate') == false ||
        	   $('.fechaCreacionArchivoAntecedenteTabla').validationEngine('validate') == false ||
        	   $('.idAutorSubirDocumentoAntecedenteTabla').validationEngine('validate') == false ){
        		
        		$('.descripcionAntecedenteTabla').validationEngine('validate');
        		$('.idTipoDeDocumentoSubirAntecedenteTabla').validationEngine('validate');
        		$('.numeroDocumentoAntecedenteTabla').validationEngine('validate');
        		 $('.fechaCreacionArchivoAntecedenteTabla').validationEngine('validate');
        		 $('.idAutorSubirDocumentoAntecedenteTabla').validationEngine('validate');
        		return;
        	}
        	
        	
        	var SubirAntecedenteDTO = new Array();   
    		$($(this).parent().parent().parent()).each(function (colIndex, c) {
           		 console.log($(this).find("#descripcionAntecedenteTabla"+ (fila -1)).val())	 
    			 console.log($(this).find("#idTipoDeDocumentoSubirAntecedenteTabla"+ (fila -1)).val())	
    			 console.log($(this).find("#numeroDocumentoAntecedenteTabla"+ (fila -1)).val())	 
    			 console.log($(this).find("#fechaCreacionArchivoAntecedenteTabla"+ (fila -1)).val())	
    			 console.log($(this).find("#idAutorSubirDocumentoAntecedenteTabla"+ (fila -1)).val())	 
    				 SubirAntecedenteDTO.push({					
    					descripcion : $(this).find("#descripcionAntecedenteTabla"+ (fila -1)).val(),
    					idTipoDeDocumentoSubir :$(this).find("#idTipoDeDocumentoSubirAntecedenteTabla"+ (fila -1)).val(),	
    					numeroDocumento : $(this).find("#numeroDocumentoAntecedenteTabla"+ (fila -1)).val(),
    					fechaCreacionArchivo :$(this).find("#fechaCreacionArchivoAntecedenteTabla"+ (fila -1)).val(),	
    					idAutorSubirDocumento : $(this).find("#idAutorSubirDocumentoAntecedenteTabla"+ (fila -1)).val(),
    					idExpedienteSubirArchivo : $("#idExpedienteSubirArchivoAntecedenteTabla").val(),
    					idInstanciaDeTareaSubirArchivo : $("#idInstanciaDeTareaSubirArchivoAntecedenteTabla").val(),
    					cartaRelacionada : $("#cartaRelacionadaAntecedentesTabla").val(),
    				});		
            });  
    		 console.log(SubirAntecedenteDTO); 
    		data.formData = {"subirArhivoDTO" : JSON.stringify(SubirAntecedenteDTO)};
            data.submit();
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
                notify.update({'type': 'success', 'message': ' Se ha guardado correctamente el antecedente!', 'progress': progress});
                sacar = 0;
            }else{
                notify.update({'type': 'success', 'message': ' <strong>Guardando</strong> No cerrar esta pagina ... ', 'progress': progress});
            }
            
        },
        done: function (e, data) {
        	
        	console.log("data" + data);
         	if (data.responseJSON.cssStatus == "alert alert-danger"){
         		
         		 notify.update({'type': 'danger', 'message': ' Se ha guardado correctamente el antecedente!', 'progress': progress});

		   	}else{
		      	 notify.update({'type': 'success', 'message': ' Se ha guardado correctamente el antecedente!', 'progress': progress});
		   	}	
        	
        	
        	 console.log(data.result.mensaje);
        	 agregarDocumentoAdjunto ();
           // data.context.text('Upload finished.');
           //$.notify("Upload finished");
        }
    });
});


*/
function buscarAyudaProceso() {
	
	$('#ayudaProcesoSubproceso').modal({backdrop: 'static', keyboard: false});
	
 }

function asignarProceso() {
	
 	var validaForm = $("#formAyudaProcesoSubproceso").validationEngine('validate');
	
	if (!validaForm) {
		return;
	}

	$('#ayudaProcesoSubproceso').modal('hide');  
	
	$("#idProcesosVigente").val($("#idProceso").val()).trigger("change");			
	$("#idMacroProceso").val("").trigger("change");	
	$("#idProceso").val("").trigger("change");	
			
 }


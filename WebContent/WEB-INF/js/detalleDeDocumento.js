/**
 * 
 */
function cargaDetalleDeDocumento(idArchivo, esVisable, aplicaFEA, aplicaFirmaApplet, idExpediente, nombreArchivo, mimeType, idInstanciaDeTarea) {
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();		
	$.get(urlSessionValida, function(haySession) {
		console.log("haySession: " + haySession);
	    if(haySession) {
	    	$("#detalleDeDocumentoModalTitulo").empty();
			$('#detalleDeDocumentoDiv').empty();
	    	$("#detalleDeDocumentoModalTitulo").html('Detalle de documento ' + nombreArchivo + '  <a href="#" id="linkRecargaDetalleDocumento" onclick="recargaDetalleDocumentoModal()"'
	    			+ ' data-idarchivo='+idArchivo
	    			+ ' data-esvisable='+esVisable
	    			+ ' data-aplicafea='+aplicaFEA
	    			+ ' data-aplicafirmaapplet='+aplicaFirmaApplet
	    			+ ' data-idexpediente='+idExpediente
	    			+ ' data-idinstanciadetarea='+idInstanciaDeTarea
	    			+ ' class="btn btn-primary btn-lg">'
	    			+ ' <span class="glyphicon glyphicon-repeat"></span> '
	    			+ ' </a>');	    	
	    	$("#idExpedienteDetalleDeDocumentoModal").val(idExpediente);
	    	$("#nombreArchivoDetalleDeDocumentoModal").val(nombreArchivo);
	    	$("#mimeTypeDetalleDeDocumentoModal").val(mimeType);
	    	$("#idDocumentoDetalleDeDocumentoModal").val(idArchivo);
	    	var urlGetDetalleDeDocumento = $('#urlGetDetalleDeDocumento').val()
	    								+"/"+idArchivo
	    								+"/"+esVisable
	    								+"/"+aplicaFEA
	    								+"/"+aplicaFirmaApplet
	    								+"/"+idExpediente
	    								+"/"+idInstanciaDeTarea
	    								;	    	
	    	console.log("urlGetDetalleDeDocumento: " + urlGetDetalleDeDocumento);	    	
	    	$('#detalleDeDocumentoModal').modal({backdrop: 'static', keyboard: false});
			$('#detalleDeDocumentoDiv').load(urlGetDetalleDeDocumento);
	    	/*$('#detalleDeDocumentoDiv').each(function() {        	 
	    		$(this).fadeOut("slow").load(urlGetDetalleDeDocumento ).fadeIn('slow');	
	    	});*/ 
	    } else {
            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
            		, function(){ window.open(raizURL, '_blank'); }
            );
	    }		
	});	
}

$("#detalleDeDocumentoModal").on("hidden.bs.modal", function () {
	if ($('#detalleDeDocumentoDiv').length >= 1) {
		$('#detalleDeDocumentoDiv').empty();
	}	
});

function modificarDatosDeDocumento(idArchivo) {
	
	var dialog = bootbox.dialog({	   
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Actualizando Datos del Documento...</p>'	   
	});
	
	console.log("idArchivo: " + idArchivo);
	console.log('$("#idInstanciaDeTareaTablaDetalleDeExpediente").val(): ' + $("#idInstanciaDeTareaTablaDetalleDeExpediente").val());
	
	var urlActualizaMetadataDeDocumento = $('#urlActualizaMetadataDeDocumento').val();
	var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+$("#idInstanciaDeTareaTablaDetalleDeExpediente").val()+"&muestraSoloDocumentosDeSalida="+false;
	
	console.log("urlActualizaMetadataDeDocumento: " + urlActualizaMetadataDeDocumento);
	console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
	
	var formData = new FormData();
	
	formData.append("idArchivo", idArchivo);
	formData.append("cdr", $('#cdrDetalleDeDocumento').val());
	formData.append("numeroDocumento", $('#numeroDocumentoDetalleDeDocumento').val());
	formData.append("idInstanciaDeTarea", $("#idInstanciaDeTareaTablaDetalleDeExpediente").val());
	
	if ($('#idTipoDeDocumentoDetalleDeDocumento').val() != "") {
		formData.append("tipoDeDocumento", $("#idTipoDeDocumentoDetalleDeDocumento option:selected").text());
	}
	
	formData.append("fechaDeCreacion", $('#fechaCreacionActualizaMetadataDocumento').val());
	
	if ($('#idAutorActualizaMetadataDocumento').val() != "") {
		formData.append("emisor", $("#idAutorActualizaMetadataDocumento option:selected").text());
	}
		
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : urlActualizaMetadataDeDocumento,		
		timeout : 100000,
		data: formData,
		processData: false, 
		//contentType: false,
		success : function(data) {	
			console.log(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(responseData) {
			
			console.log("COMPLETE: ", responseData); 
			
			$('#detalleDeDocumentoDiv').each(function(){
				
				var urlGetDetalleDeDocumento = $('#urlGetDetalleDeDocumento').val()
								+"/"+idArchivo
								+"/"+responseData.responseJSON.esVisable
								+"/"+responseData.responseJSON.aplicaFEA
								+"/"+responseData.responseJSON.aplicaFirmaApplet
								;
				
				console.log("urlGetDetalleDeDocumento: " + urlGetDetalleDeDocumento);
				
				$(this).fadeOut("slow").load(urlGetDetalleDeDocumento).fadeIn('slow');			
				
				dialog.find('.bootbox-body').html(responseData.responseJSON.respuesta);					
			
			});			
		
		}
	});
	
}

function cargaTablaComentariosDetalleDeDocumento(data) {
	
	// Limpiar text Area comentario
	$('#comentarioDetalleDeDocumento').val("");
	console.log(data);
    
    var tablaComentariosDetalleDeDocumento = $('#comentariosDetalleDeDocumento').DataTable();   
    tablaComentariosDetalleDeDocumento.destroy();
    
    var tablaComentariosDetalleDeDocumento = $('#comentariosDetalleDeDocumento')
                 .DataTable(
                               { data: data,
                                     columns:[
                                                {data:'comentario'},
                                                {data:'fecha'},                                              
                                                {data:'usuario'}
                                            ],
                                     buttons : [/* {
                                            extend : 'copyHtml5',
                                            exportOptions : {
                                                   columns : [ 0, ':visible' ]
                                            }
                                     }, */{
                                            extend : 'excelHtml5',
                                            filename : 'Comentarios.*',
                                            exportOptions : {
                                                   columns : ':visible'
                                            }
                                     }, 'colvis',
                                     ],
                                     //scrollY:        '200px',
                                     /*scrollCollapse: true,
                                     paging:         false,
                                     bFilter: 		false,
                                     bPaginate: 	false,
                                     bInfo : false,*/
                                     "language" : {

             							"sProcessing" : "Procesando...",
             							"sLengthMenu" : "Mostrar _MENU_ registros",
             							"sZeroRecords" : "No se encontraron resultados",
             							"sEmptyTable" : "Ning\u00fan dato disponible en esta tabla",
             							"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
             							"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
             							"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
             							"sInfoPostFix" : "",
             							"sSearch" : "Buscar:",
             							"sUrl" : "",
             							"sInfoThousands" : ",",
             							"sLoadingRecords" : "Cargando...",
             							"oPaginate" : {
             								"sFirst" : "Primero",
             								"sLast" : "\u00faltimo",
             								"sNext" : "Siguiente",
             								"sPrevious" : "Anterior"
             							},
             							"oAria" : {
             								"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
             								"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
             							},
             							"buttons" : {
             								"excel" : 'Exportar a Excel',
             								"colvis" : 'Cambiar columnas',
             								"copy" : 'Copiar a Portapapeles',
             								"copyTitle" : 'A&ntilde;adido al portapapeles',
             								"copyKeys" : 'Pulse <i> Ctrl </i> o <i>\ u2318 </i> + <i> C </i> para copiar datos de la tabla en el portapapeles. <br> cancelar, haga clic en este mensaje o pulse Esc . ',
             								"copySuccess" : {
             									_ : 'Copiados %d filas',
             									1 : 'Copiado 1 fila'
             								}
             							}
             						},
                     				"pageLength": 3,
                     				bFilter: false,
                     				bLengthChange: false,
                     				"order": [[ 1, "desc" ]],
                     				bInfo : false,
                                     "emptyTable":     "No hay comentarios"
                               });
    /*tablaComentariosDetalleDeDocumento.buttons().container().appendTo(
                 '#comentariosDetalleDeDocumento_wrapper .row:eq(0)');*/

}

function agregarComentarioDocumento(idArchivo) {
	
	console.log('idArchivo: ' + idArchivo);
	var comentario = $("#comentarioDetalleDeDocumento").val();
	console.log("comentario: " + comentario);
	
	  if ($('#comentarioDetalleDeDocumento').validationEngine('validate') == false){
     		return;
     	}
	
	
	var urlAgregarComentarioDocumento = $('#urlAgregarComentarioDocumento').val()+"?idDocumento="+idArchivo+"&comentario="+comentario;
	
	console.log("urlAgregarComentarioDocumento: " + urlAgregarComentarioDocumento);
	var dialogoObservacion = "";
	
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : encodeURI(urlAgregarComentarioDocumento),		
		timeout : 100000,
		
        beforeSend: function () {
       	 
        	dialogoObservacion = bootbox.dialog({
                message: '<p><i class="fa fa-spin fa-spinner"></i> Agregando Observaci&oacute;n </p>',
                  closeButton: false
            });
 	 
     	    var tableLogOperaciones = $('#tablaImplementos').DataTable();	
				tableLogOperaciones.destroy();		
       },
	
		success : function(data) {	
			console.log(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(responseData) {
			console.log("COMPLETE: ", responseData);
			cargaTablaComentariosDetalleDeDocumento(responseData.responseJSON.comentarios);
			dialogoObservacion.modal('hide');
			// $("#detalleDeDocumentoModal").css("overflow-x: hidden;","overflow-y: auto;");
			$( "#detalleDeDocumentoModal" ).addClass("modalPrincipal");

		}
	}); 
}

function visarDocumento(idArchivo, esVisable, aplicaFEA, aplicaFirmaApplet, idExpediente, nombreArchivo, mimeType, idInstanciaDeTarea, nombreExpediente,
		idTipoDeDocumento) {
	
	/*var dialog = bootbox.dialog({
	    title: 'Resultado de visaci&oacute;n de documento',
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Visando Documento...</p>'	    
	});*/
	
	var urlVisarDocumento = $('#urlVisarDocumento').val() + "/" + idArchivo + "/" + idInstanciaDeTarea + "/" + idTipoDeDocumento;
	console.log("urlVisarDocumento: " + urlVisarDocumento);
	
	bootbox.confirm({
    	title: "Visar documento",
        message: "Desea visar documento?",
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
					url : urlVisarDocumento,		
					timeout : 100000,
					success : function(data) {	
						console.log(data);			
					},
					error : function(e) {
						console.log("ERROR: ", e);	
						$.notify({
								message: 'Error al visar el documento' 
							},{
								type: 'danger'
							});			
					},
					complete : function(data) {
						console.log("data.responseText: " + data.responseText);		       	 	
			       	 	if (data.responseText.indexOf("Error") !== -1) {
				       	 	$.notify({
								message: 'Error al visar el documento'
							},{
								type: 'danger'
							});	
			       	 	} else {
				       	 	$.notify({
								message: 'Se ha visado correctamente el documento'
							},{
								type: 'success'
							});						
				       	 	var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
							urlGetTablaHistorialDeDocumentoPorIdExpediente = 
									urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + idExpediente
									+ "/" + idInstanciaDeTarea
									+ "/" + nombreExpediente;
							console.log("urlGetTablaHistorialDeDocumentoPorIdExpediente: " + urlGetTablaHistorialDeDocumentoPorIdExpediente);
							$('#divTablaHistorialDeDocumentos').each(function(){        	 
										$(this).fadeOut("slow").load(urlGetTablaHistorialDeDocumentoPorIdExpediente).fadeIn('slow');
								});
							var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTarea+"&muestraSoloDocumentosDeSalida="+true;
							console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
							$('#divDetalleDeTarea').each(function(){        	 
								$(this).fadeOut("slow").load(urlGetDetalleDeTarea).fadeIn('slow');
							});
			       	 	}						
					},
					done : function(e) {
						console.log("DONE");
					}
				}); 
		    }
        }
    }); 
}

function aplicarFirmaAvanzada() {
	
	var validaForm = $("#formFirmaAvanzada").validationEngine('validate');
	
	if (validaForm == false) {
		return;
	}
	
	console.log("$('#urlFirmaAvanzadaDocumento').val(): " + $('#urlFirmaAvanzadaDocumento').val());
	
	console.log('data-tipofirma: ' + $("#propositoFirmaAvanzadaSelect option:selected").attr('data-tipofirma'));
	
	var idExpediente = $("#botonAplicarFirmaAvanzada").attr('data-idexpediente');
	var idArchivo = $("#botonAplicarFirmaAvanzada").attr('data-idarchivo');
	var nombreArchivo = $("#botonAplicarFirmaAvanzada").attr('data-nombrearchivo');
	var mimeType = $("#botonAplicarFirmaAvanzada").attr('data-mimetype');
	var idTipoDeDocumento = $("#botonAplicarFirmaAvanzada").attr('data-idtipodedocumento');
	var nombreTipoDeDocumento = $("#botonAplicarFirmaAvanzada").attr('data-nombretipodedocumento');
	var cdr = $("#botonAplicarFirmaAvanzada").attr('data-cdr');
	var numeroDocumento = $("#botonAplicarFirmaAvanzada").attr('data-numerodocumento');
	var cartaRelacionada = $("#botonAplicarFirmaAvanzada").attr('data-cartarelacionada');
	var emisor = $("#botonAplicarFirmaAvanzada").attr('data-emisor');
	var idInstanciaDeTarea = $("#botonAplicarFirmaAvanzada").attr('data-idinstanciadetarea');
	var nombreExpediente = $("#botonAplicarFirmaAvanzada").attr('data-nombreexpediente');
	var rutFirmaAvanzada = $('#rutFirmaAvanzada').val();
	rutFirmaAvanzada = rutFirmaAvanzada.replace(".","");
	var tmp = rutFirmaAvanzada.split('-');
	rutFirmaAvanzada = tmp[0];
	
	/*console.log("idExpediente: " + idExpediente);
	console.log("idArchivo: " + idArchivo);
	console.log("nombreArchivo: " + nombreArchivo);
	console.log("mimeType: " + mimeType);
	console.log("idTipoDeDocumento: " + idTipoDeDocumento);
	console.log("nombreTipoDeDocumento: " + nombreTipoDeDocumento);
	console.log("cdr: " + cdr);	
	console.log("numeroDocumento: " + numeroDocumento);	
	console.log("cartaRelacionada: " + cartaRelacionada);
	console.log("emisor: " + emisor);
	console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	console.log("rutFirmaAvanzada: " + rutFirmaAvanzada);*/
	
	var urlFirmaAvanzadaDocumento = $('#urlFirmaAvanzadaDocumento').val();
	
	var formData = new FormData();
	
	formData.append("idExpediente", idExpediente);
	formData.append("nombreExpediente", nombreExpediente);
	formData.append("idDocumento", idArchivo); 
	formData.append("nombreArchivo", nombreArchivo);
	formData.append("mimeType", mimeType);	
	formData.append("rut", rutFirmaAvanzada);	
	formData.append("proposito", encodeURIComponent($('#propositoFirmaAvanzada').val()));
	formData.append("otp", $('#otpFirmaAvanzada').val());
	formData.append("tipoDeDocumento", nombreTipoDeDocumento);	
	formData.append("cdr", cdr);
	formData.append("numeroDocumento", numeroDocumento);
	formData.append("emisor", emisor);
	formData.append("cartaRelacionada", cartaRelacionada);	
	formData.append("idInstanciaDeTarea", idInstanciaDeTarea);
	formData.append("idTipoDeDocumento", idTipoDeDocumento);	
	formData.append("tipoDeFirma", $("#propositoFirmaAvanzadaSelect option:selected").attr('data-tipofirma'));
	
	var notifyFEA;
	
	$.ajax({
	    url: urlFirmaAvanzadaDocumento,
	    type: 'POST',
	    data: formData,
	    async: true,
	    cache: false,
	    processData: false,
	    contentType: false, 
	    beforeSend: function(xhr) {
	    	$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
			$('#botonAplicarFirmaAvanzada').addClass('disabled');
			$('#firmaAvanzadaModal').modal('hide');			
		},
	    success: function (data) {
	    	console.log(data);	
	    },
	    error : function(e) {
	    	$("#contenedorBEPrincipal").find(".cargando").remove();
			console.log("ERROR: ", e);
			$.notify({
     			message: 'Error al firmar documento!'
     		},{
     			type: 'danger'
     		});			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData) {
			console.log("COMPLETE -- firmaAvanzadaDocumento: ", returnData.responseJSON );
			console.log("COMPLETE -- firmaAvanzadaDocumento: ", returnData );
			if (returnData.responseJSON == undefined ) {
				$("#contenedorBEPrincipal").find(".cargando").remove();
				$.notify({
	     			message: 'Error al firmar documento!'	     			
	     		},{
	     			type: 'danger'
	     		});
			} else if (returnData.responseJSON.cssStatus == "alert alert-success") {				
				$.notify({
	     			message: 'Documento firmado!'
	     		},{
	     			type: 'success'
	     		});
				var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
				urlGetTablaHistorialDeDocumentoPorIdExpediente = 
						urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + idExpediente
						+ "/" + idInstanciaDeTarea
						+ "/" + nombreExpediente;			
				var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTarea+"&muestraSoloDocumentosDeSalida="+true;				
				$("#divDetalleDeTarea").load(urlGetDetalleDeTarea, function() {
						$("#contenedorBEPrincipal").find(".cargando").remove();
						if ($("#divTabsDetalleDeTarea").length>0) {
	        			$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
	        		}
	 			});				
			} else {
				$("#contenedorBEPrincipal").find(".cargando").remove();
				$.notify({	     			
	     			message: returnData.responseJSON.resultadoFirmarDocumentoConFEA
	     		},{
	     			type: 'danger'
	     		});
			}
			$('#botonAplicarFirmaAvanzada').removeClass('disabled');			
		}
	  });	
}

function loadModalFEA(idExpediente,
		idArchivo,
		nombreArchivo,
		mimeType,
		idTipoDeDocumento,
		nombreTipoDeDocumento,
		cdr,
		numeroDocumento,
		cartaRelacionada,
		emisor,
		idInstanciaDeTarea,
		nombreExpediente) {
	
	$('#botonAplicarFirmaAvanzada').removeClass('disabled');	
    var urlGetTiposDeFirmaAvanzada = $("#urlGetTiposDeFirmaAvanzada").val();
    $('#propositoFirmaAvanzadaSelect').empty();
    $('#propositoFirmaAvanzadaSelect').append($('<option>').text("Seleccione").attr('value', ''));                                
    $("#botonAplicarFirmaAvanzada").attr('data-idexpediente', idExpediente);
    $("#botonAplicarFirmaAvanzada").attr('data-idarchivo', idArchivo);
    $("#botonAplicarFirmaAvanzada").attr('data-nombrearchivo', nombreArchivo);
    $("#botonAplicarFirmaAvanzada").attr('data-mimetype', mimeType);
    $("#botonAplicarFirmaAvanzada").attr('data-idtipodedocumento', idTipoDeDocumento);
    $("#botonAplicarFirmaAvanzada").attr('data-nombretipodedocumento', nombreTipoDeDocumento);
    $("#botonAplicarFirmaAvanzada").attr('data-cdr', cdr);
    $("#botonAplicarFirmaAvanzada").attr('data-numerodocumento', numeroDocumento);
    $("#botonAplicarFirmaAvanzada").attr('data-cartarelacionada', cartaRelacionada);
    $("#botonAplicarFirmaAvanzada").attr('data-emisor', emisor);
    $("#botonAplicarFirmaAvanzada").attr('data-idinstanciadetarea', idInstanciaDeTarea);
    $("#botonAplicarFirmaAvanzada").attr('data-nombreexpediente', nombreExpediente);                                
    $.ajax({
        type : "GET",
        contentType : "application/json",
        url : urlGetTiposDeFirmaAvanzada,		
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);					
        },
        error : function(e) {
            console.log("ERROR: ", e);			
        },
        done : function(e) {
            console.log("DONE");
        },
        complete : function(data) {	
            $.when( $.each(data.responseJSON, function(i, obj){
                $('#propositoFirmaAvanzadaSelect').append($('<option data-tipofirma="'+obj.valorParametroChar+'">').text(obj.valorContexto).attr('value', obj.valorContexto));
            }) ).then(function( x ) {
                $("#propositoFirmaAvanzadaSelect").val($('#propositoFirmaAvanzadaSelect option').filter(function () { return $(this).attr('data-tipofirma') == "ATENDIDA"; }).val()).change();
                $('#propositoFirmaAvanzadaSelect').prop('disabled', 'disabled');
            }); 
            $('#firmaAvanzadaModal').modal({backdrop: 'static', keyboard: false});
            $("#rutFirmaAvanzada").val("");
            $("#otpFirmaAvanzada").val("");            
        }
    }); 
	
}

function cargaModalFEA(idExpediente,
		idArchivo,
		nombreArchivo,
		mimeType,
		idTipoDeDocumento,
		nombreTipoDeDocumento,
		cdr,
		numeroDocumento,
		cartaRelacionada,
		emisor,
		idInstanciaDeTarea,
		nombreExpediente) {	
	
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();

	$.get(urlSessionValida, function(haySession) {
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var urlEstaDocumentoFirmado = raizURL + "estaDocumentoFirmado/" + idArchivo + "/" + idInstanciaDeTarea;
	        //console.log("urlEstaDocumentoFirmado: " + urlEstaDocumentoFirmado);
        	$.get(urlEstaDocumentoFirmado, function(estaDocumentoFirmado) {
            	if (estaDocumentoFirmado == true) {
            		bootbox.confirm({
        		    	title: "Firmar documento",
        		        message: "El documento ya est&aacute; firmado por usted. ¿Desea firmarlo nuevamente?",
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
        		            if (result == true) {
        		            	loadModalFEA(idExpediente,
        	                			idArchivo,
        	                			nombreArchivo,
        	                			mimeType,
        	                			idTipoDeDocumento,
        	                			nombreTipoDeDocumento,
        	                			cdr,
        	                			numeroDocumento,
        	                			cartaRelacionada,
        	                			emisor,
        	                			idInstanciaDeTarea,
        	                			nombreExpediente);
        				    }
        		        }
        		    });
                } else {
	
					var urlValidaSiHayFirmaHoy = raizURL + "validaSiHayFirmaHoy/" + idTipoDeDocumento + "/" + idInstanciaDeTarea;
					
	        		$.get(urlValidaSiHayFirmaHoy, function(urlValidaSiHayFirmaHoy) { 
		
						if (urlValidaSiHayFirmaHoy == true) {
							
							bootbox.confirm({
		        		    	title: "Firmar documento",

		        		        message: "En esta tarea HOY ya se firm&oacute; un documentom ¿desea firmar nuevamente?",
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
		        		            if (result == true) {
		        		            	loadModalFEA(idExpediente,
		        	                			idArchivo,
		        	                			nombreArchivo,
		        	                			mimeType,
		        	                			idTipoDeDocumento,
		        	                			nombreTipoDeDocumento,
		        	                			cdr,
		        	                			numeroDocumento,
		        	                			cartaRelacionada,
		        	                			emisor,
		        	                			idInstanciaDeTarea,
		        	                			nombreExpediente);
		        				    }
		        		        }
	        		    	});
							
							
						} else {
							
							loadModalFEA(idExpediente,
                			idArchivo,
                			nombreArchivo,
                			mimeType,
                			idTipoDeDocumento,
                			nombreTipoDeDocumento,
                			cdr,
                			numeroDocumento,
                			cartaRelacionada,
                			emisor,
                			idInstanciaDeTarea,
                			nombreExpediente);
							
						}
		
	 				});
                	
                }
            });  
	    } else {
	        bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                , function(){ window.open(raizURL, '_self'); }
	        );
	    }    
	});
	
	/*$('#botonAplicarFirmaAvanzada').removeClass('disabled');
	
	var urlGetTiposDeFirmaAvanzada = $("#urlGetTiposDeFirmaAvanzada").val();
	$('#propositoFirmaAvanzadaSelect').empty();
	$('#propositoFirmaAvanzadaSelect').append($('<option>').text("Seleccione").attr('value', ''));
	
	$("#botonAplicarFirmaAvanzada").attr('data-idexpediente', idExpediente);
	$("#botonAplicarFirmaAvanzada").attr('data-idarchivo', idArchivo);
	$("#botonAplicarFirmaAvanzada").attr('data-nombrearchivo', nombreArchivo);
	$("#botonAplicarFirmaAvanzada").attr('data-mimetype', mimeType);
	$("#botonAplicarFirmaAvanzada").attr('data-idtipodedocumento', idTipoDeDocumento);
	$("#botonAplicarFirmaAvanzada").attr('data-nombretipodedocumento', nombreTipoDeDocumento);
	$("#botonAplicarFirmaAvanzada").attr('data-cdr', cdr);
	$("#botonAplicarFirmaAvanzada").attr('data-numerodocumento', numeroDocumento);
	$("#botonAplicarFirmaAvanzada").attr('data-cartarelacionada', cartaRelacionada);
	$("#botonAplicarFirmaAvanzada").attr('data-emisor', emisor);
	$("#botonAplicarFirmaAvanzada").attr('data-idinstanciadetarea', idInstanciaDeTarea);
	$("#botonAplicarFirmaAvanzada").attr('data-nombreexpediente', nombreExpediente);
	
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : urlGetTiposDeFirmaAvanzada,		
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);					
		},
		error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(data) {	
            $.when( $.each(data.responseJSON, function(i, obj){
                $('#propositoFirmaAvanzadaSelect').append($('<option data-tipofirma="'+obj.valorParametroChar+'">').text(obj.valorContexto).attr('value', obj.valorContexto));
            }) ).then(function( x ) {
            	$("#propositoFirmaAvanzadaSelect").val($('#propositoFirmaAvanzadaSelect option').filter(function () { return $(this).attr('data-tipofirma') == "ATENDIDA"; }).val()).change();
             	$('#propositoFirmaAvanzadaSelect').prop('disabled', 'disabled');
            }); 
            $('#firmaAvanzadaModal').modal({backdrop: 'static', keyboard: false});
            $("#rutFirmaAvanzada").val("");
            $("#otpFirmaAvanzada").val("");            
		}
	}); */	
	
}


function cargaTipoDeFirma() {
	var tipofirma = $("#propositoFirmaAvanzadaSelect").find("option:selected").attr("data-tipofirma");
	console.log(tipofirma);
	console.log($( "#propositoFirmaAvanzadaSelect option:selected" ).val());
	$("#propositoFirmaAvanzada").val($( "#propositoFirmaAvanzadaSelect option:selected" ).val());
	if (tipofirma == 'ATENDIDA') {		
		$("#divOPT").removeClass('hidden');		
	} else {		
		$("#otpFirmaAvanzada").val("");			
		$("#divOPT").addClass('hidden');
		$("#divImagenQR").addClass('hidden');
	}
}

function muestraCodigoQR() {
	
	if ($("#divImagenQR").hasClass('hidden')) {
		$("#divImagenQR").removeClass('hidden');
	} else {
		$("#divImagenQR").addClass('hidden');
	}
	
}


/*
function firmaDocumento(idArchivo,nombreDocumento,idExpediente) {
	
    location.href= "../firmaApplets/"+idArchivo+"/"+ nombreDocumento + "/" +idExpediente;

}
*/
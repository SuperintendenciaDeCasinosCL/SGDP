/**
 * 
 */
jQuery(document).ready(function($) {
	jQuery("#formContinuarProceso").validationEngine();	
});

function cargarDatosContinuarProcesoModal(idInstanciaDeTarea, idExpediente, puedeDevolver, esUltimaTarea, tituloCabecera, tieneDocumentosEnCMS, reasigna, tipoDeBifurcacion, nombreDeTarea) {
	var urlVerificaSessionJs = $(location).attr('protocol')+"//"+$(location).attr('host')+"/sgdp/verificarSession";	
	var urlLogin = $(location).attr('protocol')+"//"+$(location).attr('host')+"/sgdp/";	
	$.get(urlVerificaSessionJs, function(haySession) {	
	    if (haySession) {
	    	$("#divTabsDetalleDeTarea").addClass('hide');
	    	$("#comentario").val("");
	    	
	        var dialog = bootbox.dialog({  		   
	      		message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando datos para continuar proceso...</p>',
	      		closeButton: false
	      	});
	        
	        var urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente = $("#urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente").val()+'?idExpediente='+idExpediente;
	       	var urlGetInstanciasDeTareasQueContinuanDeInstanciaDeTarea = $("#urlGetInstanciasDeTareasQueContinuanDeInstanciaDeTarea").val()+'?idInstanciaDeTarea='+idInstanciaDeTarea;	
	    	var urlGetUsuariosPosiblesPorIdInstanciaDeTarea = $("#urlGetUsuariosPosiblesPorIdInstanciaDeTarea").val(); 
	    	
	        $("#etapasInstanciaDeProcesoContinuarProcesoModal").load(urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente,
	    		    function() {
	    		           $("#etapasInstanciaDeProcesoContinuarProcesoModal").find(
	    		                        ".cargando").remove();
	    		    	   
	    		    }
	    		);
	    	
	    	var htmlDivTareasContinuan = '';
	    	
	    	$( ".asignaciones-tareas-modal" ).remove();
	    	
	    	$("#buttonAvanzarProcesoModal").unbind("click");	
	    	$("#buttonAvanzarProcesoModal").click(function(){		
	    		mueveProceso('avanzarProceso', tieneDocumentosEnCMS, reasigna);
	    	});
	    	
	    	var existeBotonSubirCartaContinuarProcesoModal = false;
	    	
	    	$('#buttonAvanzarProcesoModal').hide();
	    	
	    	if ($('#botonSubirCartaContinuarProcesoModal') != 'undefined' || $('#botonSubirCartaContinuarProcesoModal') != null ) {
	    		$('#botonSubirCartaContinuarProcesoModal').hide();
	    		existeBotonSubirCartaContinuarProcesoModal = true;
	    	}	
	    	
	    	$('#cabeceraSubirDocumentoModal').html('');
	    	$('#cabeceraContinuarProcesoModal').html('');
	    	
	    	$("#cabeceraSubirDocumentoModal").append(tituloCabecera);
	    	$("#cabeceraContinuarProcesoModal").append(tituloCabecera);	    	
	    		    	
	    	if (esUltimaTarea == false) {
	    		$('#buttonAvanzarProcesoModal').show();
	    	}
	    	
	    	if (existeBotonSubirCartaContinuarProcesoModal && tieneDocumentosEnCMS == false) {
	    		$('#botonSubirCartaContinuarProcesoModal').show();
	    	}
	    	
	    	$("#idInstanciaDeTareaOrigen").val(idInstanciaDeTarea);
	    	$("#idInstanciaDeTareaOrigenSubirDocumento").val(idInstanciaDeTarea);
	    	$("#idExpedienteSubirDocumento").val(idExpediente);
	    	$("#idExpedienteContinuarProceso").val(idExpediente);
	    	$("#reasigna").val(reasigna);
	    	
	    	var idUsuariosAsignados = [];
	    	var posiblesUsuarios = [];
	    	var optionPosiblesUsuario;
	    	var fechaVencimientoInstanciaDeTarea;
	    	
	    	if (reasigna == true) {	
	    		
	    		$('#buttonAvanzarProcesoModal').show();

	    		$.ajax({
	    			    url: urlGetUsuariosPosiblesPorIdInstanciaDeTarea+'?idInstanciaDeTarea='+idInstanciaDeTarea,
	    			    type: 'GET',
	    			    async: true,
	    			    cache: false,
	    			    contentType: false,
	    			    processData: false,
	    			    success: function (returnData) {
	    			    	console.log("SUCCESS -- getUsuariosRolesPosiblesPorIdInstanciaDeTarea: ", returnData);	    	
	    			    },
	    			    error : function(e) {
	    					console.log("ERROR: getUsuariosRolesPosiblesPorIdInstanciaDeTarea", e);			
	    				},
	    				done : function(e) {
	    					console.log("DONE: getUsuariosRolesPosiblesPorIdInstanciaDeTarea");
	    				},	
	    				complete : function(returnData) {
	    					console.log("COMPLETE: getUsuariosRolesPosiblesPorIdInstanciaDeTarea", returnData);
	    					posiblesUsuarios = returnData.responseJSON;				
	    					
	    					for (i = 0; i < posiblesUsuarios.length; i++) { 
	    						if (posiblesUsuarios[i] != 'undefined') {
	    							optionPosiblesUsuario = '<option value="' + posiblesUsuarios[i] + '">' + posiblesUsuarios[i] + '</option>' + optionPosiblesUsuario;
	    						}						
	    					}
	    						    					
	    					htmlDivTareasContinuan = htmlDivTareasContinuan +
	    					'<div class="form-group asignaciones-tareas-modal">' +
	    						'<label style="font-size: smaller;" class="control-label col-sm-3" for=" ' + idInstanciaDeTarea + ' ">' + nombreDeTarea + '</label>' +
	    						'<div class="col-sm-4">' +					
	    							
	    							'<select class="form-control usuarios-asignados" id="' + idInstanciaDeTarea + '" >' +									
	    							
	    								optionPosiblesUsuario +
	    					        
	    					        '</select>' +
	    						
	    						
	    						'</div>' +
	    						'<div class="input-group date col-sm-4 div-fecha-asignada hide" id="divDate' + idInstanciaDeTarea + '" > ' +
	    							'<input type="text" class="form-control fecha-asignada" data-toggle="tooltip" data-placement="top" title="Fecha M&aacute;xima de la Tarea" placeholder="__/__/____" /> ' +	   				
	    							'<span class="input-group-addon">' +
	    			   					'<span class="glyphicon glyphicon-calendar"></span>'+
	    							'</span>'+
	    						'</div>' +
	    					'</div>';					
	    					
	    					$.when( $("#divTareasContinuan").after(htmlDivTareasContinuan)).then(function() {
	    						aplicaDatePickerPorIdElemento('divDate'+idInstanciaDeTarea);
	    					});				
	    					
	    					$('#continuarProcesoModal').modal('show');
	    					
	    					dialog.modal('hide');				
	    					$("#continuarProcesoModal" ).addClass("modalPrincipal");
	    				}
	    	
	    		});		
	    		
	    		$("#buttonAvanzarProcesoModal").html('<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>Reasignar');			
	    		
	    	} else {
	    		$("#buttonAvanzarProcesoModal").html('<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>Ejecutar');
	    		$.ajax({
	    			type : "GET",
	    			contentType : "application/json",
	    			url : urlGetInstanciasDeTareasQueContinuanDeInstanciaDeTarea,		
	    			timeout : 100000,
	    			success : function(data) {
	    				
	    				console.log("SUCCESS -- cargarDatosContinuarProcesoModal: ", data);				
	    				
	    			},
	    			error : function(e) {
	    				console.log("ERROR: ", e);			
	    			},
	    			done : function(e) {
	    				console.log("DONE");			
	    			},
	    			complete : function(returnData) {	
	    					
	    				$.each(returnData.responseJSON, function(i, obj){
	    				
	    					posiblesUsuarios = obj.posiblesIdUsuarios;
	    					
	    					fechaVencimientoInstanciaDeTarea = obj.fechaVencimientoInstanciaDeTareaJavaScript;
	    					
	    					for (i = 0; i < posiblesUsuarios.length; i++) { 
	    						if (posiblesUsuarios[i] != 'undefined') {
	    							optionPosiblesUsuario = '<option value="' + posiblesUsuarios[i] + '">' + posiblesUsuarios[i] + '</option>' + optionPosiblesUsuario;
	    						}						
	    					}		    					
	    					
	    					if (tipoDeBifurcacion == '' || tipoDeBifurcacion == 'AND') {
	    						
	    						htmlDivTareasContinuan = htmlDivTareasContinuan +
	    						'<div class="form-group asignaciones-tareas-modal">' +
	    							'<label style="font-size: smaller;" class="control-label col-sm-3" for=" ' + obj.idInstanciaDeTarea + ' ">' + obj.nombreDeTarea + '</label>' +
	    							'<div class="col-sm-4">' +
	    								
	    								//'<input type="text" class="form-control validate[required] usuarios-asignados" placeholder="Ingrese Usuario a Asignar" id="' + obj.idInstanciaDeTarea + '" />' +
	    							
	    								'<select class="form-control usuarios-asignados" id="' + obj.idInstanciaDeTarea + '" >' +									
	    								
	    									optionPosiblesUsuario +
	    					        
	    								'</select>' +
	    								
	    								
	    							'</div>' +
	    							'<div class="input-group date col-sm-4 div-fecha-asignada hide" id="divDate' + obj.idInstanciaDeTarea + '" > ' +
	                       				'<input type="text" class="form-control fecha-asignada" data-toggle="tooltip" data-placement="top" title="Fecha M&aacute;xima de la Tarea" placeholder="__/__/____" /> ' +	                   		
	                       				'<span class="input-group-addon">' +
	                       					'<span class="glyphicon glyphicon-calendar"></span>'+
	                       				'</span>'+
	                   				'</div>' +
	    						'</div>';
	    						
	    					} else if (tipoDeBifurcacion != '' || tipoDeBifurcacion == 'OR') {
	    						
	    						htmlDivTareasContinuan = htmlDivTareasContinuan + 
	    						'<div class="form-group asignaciones-tareas-modal">' +
	    							'<div class="radio col-sm-3"> ' +
	    						    	'<label style="font-size: smaller;" class="control-label" for="' + obj.idInstanciaDeTarea + '">' + 
	    						    		'<input type="radio" onclick="habilitaImputUsuarioAsignado(' + obj.idInstanciaDeTarea + ');" name="usuarioSiguienteTareaRadio" />' + obj.nombreDeTarea + 
	    						    	'</label> ' + 
	    						    '</div>' +
	    							'<div class="col-sm-4" >' +
	    								
	    								//'<input disabled="true" type="text" class="form-control usuarios-asignados" placeholder="Ingrese Usuario a Asignar" id="' + obj.idInstanciaDeTarea + '" />' +
	    							
	    								'<select disabled="true" class="form-control usuarios-asignados" id="' + obj.idInstanciaDeTarea + '" >' +									
	    								
	    									optionPosiblesUsuario +
	    				        
	    								'</select>' +
	    							
	    							'</div>' +								
	    							'<div class="input-group date col-sm-4 div-fecha-asignada hide" id="divDate' + obj.idInstanciaDeTarea + '" > ' +
	                       				'<input disabled="true" id=inputDate"' + obj.idInstanciaDeTarea + '" type="text" class="form-control fecha-asignada" data-toggle="tooltip" data-placement="top" title="Fecha M&aacute;xima de la Tarea" placeholder="__/__/____" /> ' +
	                       				 '<span class="input-group-addon">' +
	                           				'<span class="glyphicon glyphicon-calendar"></span>'+
	                       				'</span>'+
	                   				'</div>' +								
	    						'</div>';
	    						
	    					}				
	    					
	    				});	
	    				
	    				$.when( $("#divTareasContinuan").after(htmlDivTareasContinuan)).then(function() {
	    					$(".asignaciones-tareas-modal").each(function (colIndex, c) {				    	
	    						aplicaDatePicker($(this).find(".div-fecha-asignada"), fechaVencimientoInstanciaDeTarea);				    	
	    				    });	
	    					$('[data-toggle="tooltip"]').tooltip();
	    				});			
	    				
	    				$('#continuarProcesoModal').modal('show');
	    				jQuery("#formContinuarProceso").validationEngine();
	    				dialog.modal('hide');
	    				$("#continuarProcesoModal" ).addClass("modalPrincipal");
	    			}
	    		});
	    	}
	   } else {
	         bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacuten y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             , function(){ window.open(urlLogin, '_blank');}
	         );
	   }
	});
}

function habilitaImputUsuarioAsignado(idInput) {
	
	 $(".asignaciones-tareas-modal").each(function (colIndex, c) {	    
	    	
	    	$(this).find(".div-fecha-asignada").attr("disabled", true);
	    	$(this).find(".fecha-asignada").attr("disabled", true);    		    	
	    
	    });
	
	$("#" + idInput).attr("disabled", false);	
	$("#" + idInput).addClass("validate[required]");
	
	$("#inputDate" + idInput).attr("disabled", false);	
	$("#inputDate" + idInput).addClass("validate[required]");	
	
	var usrAsig = $("#" + idInput).val();	
	console.log("usrAsig: " + usrAsig);
	
	var fechaAsignada = $("#inputDate" + idInput).val();
	console.log("fechaAsignada: " + fechaAsignada);
	
	 $(".asignaciones-tareas-modal").each(function (colIndex, c) {		
		 
	    	$(this).find(".usuarios-asignados").val("");
	    	$(this).find(".fecha-asignada").val("");    		    	
	    
	    });
	
	$("#" + idInput).val(usrAsig);
	$("#inputDate" + idInput).val(fechaAsignada);
	
}

function avanzaProceso() {	
	
	$.post('/sgdp/avanzaProceso', $('#formContinuarProceso').serialize(),
		    function(data, status){
		        console.log("Data: " + data + "\nStatus: " + status);
		        $('#continuarProcesoModal').modal('hide');
		    });	
}


function mueveProceso(avanzaORetrocede, tieneDocumentosEnCMS, reasigna) {
	
	var validaForm = $("#formContinuarProceso").validationEngine('validate');		
	if (!validaForm) {
		return;
	}
	
	if ($('#buttonAvanzarProcesoModal') != 'undefined' || $('#buttonAvanzarProcesoModal') != null ) {
		$('#buttonAvanzarProcesoModal').addClass('disabled');
	}
	
	var mensajeSpin;
	
	if (reasigna == true) {
		mensajeSpin = "Reasignando Tarea";
	} else {
		mensajeSpin = "Avanzando a la Siguiente Tarea";
	}
	
	var dialogMueveProceso = bootbox.dialog({
			title: "Actualiza Proceso",
		    message: '<p><i class="fa fa-spin fa-spinner"></i> ' + mensajeSpin +  '...</p>'		    
		});
	
	var urlMueveProceso = $("#urlMueveProceso").val();	
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();
	
	$("#asignacionesTareasJSON").val('');
	
	$("#avanzaRetrocede").val(avanzaORetrocede);
	
	if (tieneDocumentosEnCMS) {
		$( "#formContinuarProceso" ).attr( "enctype", "multipart/form-data" ).attr( "encoding", "multipart/form-data" );
	} else {
		$( "#formContinuarProceso" ).attr( "enctype", "" ).attr( "encoding", "" );
	}
	
	var asignacionesTareasArray = new Array(); 
	
    $(".asignaciones-tareas-modal").each(function (colIndex, c) {
    
    	if ($(this).find(".usuarios-asignados option:selected").text() != "") {
    		asignacionesTareasArray.push({    		
        		idInstanciaDeTarea : $(this).find(".usuarios-asignados").attr("id") ,
        		usuariosAsignados : $(this).find(".usuarios-asignados option:selected").text(),
        		fechaAsignada : $(this).find(".fecha-asignada").val()
            }); 
    	}
    });   
    
    var asignacionesTareasJSON = JSON.stringify(asignacionesTareasArray);
    
    $("#asignacionesTareasJSON").val(asignacionesTareasJSON);

    var form = $('#formContinuarProceso')[0]; 
	var formData = new FormData(form);
	
	$.ajax({
	    url: urlMueveProceso,
	    type: 'POST',
	    data: formData,
	    async: true,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returnData) {
	    	console.log("SUCCESS -- mueveProceso: ", returnData);	    	
	    },
	    error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData) {
			console.log("COMPLETE -- mueveProceso: ", returnData.responseJSON );	
			
			if ($('#tareasEnEjecucion') != 'undefined' || $('#tareasEnEjecucion') != null ) {
				cargaTareasEnEjecucion();
			}	
			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
			if ($('#buttonAvanzarProcesoModal') != 'undefined' || $('#buttonAvanzarProcesoModal') != null ) {
				$('#buttonAvanzarProcesoModal').removeClass('disabled');
			}
			$('#continuarProcesoModal').modal('hide');			
			if (returnData.responseJSON.respuestaMueveProceso == "OK") {
				var arrayRespuesta = returnData.responseJSON.tareasUsuarios;
				var htmlTareaUsuarios = '<p><ul class="list-group">';
				for (var i = 0; i < arrayRespuesta.length; i++) {				   
					htmlTareaUsuarios = htmlTareaUsuarios + '<li class="list-group-item">'+ arrayRespuesta[i] +'</li>';
				}				
				htmlTareaUsuarios = htmlTareaUsuarios + "</ul></p>";
				dialogMueveProceso.find('.bootbox-body').html(htmlTareaUsuarios);
				
			} else {		
				dialogMueveProceso.find('.bootbox-body').html(returnData.responseJSON.respuestaMueveProceso);
			}		
		}
		
	  });		
}

function actualizaTareas(idDiv, url, actualizaCantidadDeTareas) {
	var urlGetCantidadDeTareas = $("#urlGetCantidadDeTareas").val();
	$('#'+idDiv).each(function(){        	 
        $(this).fadeOut("slow").load(url, function() {
        	if (actualizaCantidadDeTareas == true) {
        		$.ajax({
            	    url: urlGetCantidadDeTareas,
            	    type: 'GET',
            	    async: true,
            	    cache: false,
            	    contentType: false,
            	    processData: false,
            	    success: function (returnDataGetCantidadDeTareas) {
            	    	console.log("SUCCESS -- urlGetCantidadDeTareas: ", returnDataGetCantidadDeTareas);	    	
            	    },
            	    error : function(e) {
            			console.log("ERROR: ", e);			
            		},
            		done : function(e) {
            			console.log("DONE");
            		},
            		complete : function(returnDataGetCantidadDeTareas) {
            			console.log("COMPLETE -- urlGetCantidadDeTareas: ", returnDataGetCantidadDeTareas.responseText);
            			$('#cantidadDeTareas').text(returnDataGetCantidadDeTareas.responseText);
            		}
            	});	 
        	}
        }).fadeIn('slow');
    });	
}

function actualizaTareasConCallBack(idDiv, url, actualizaCantidadDeTareas, callback) {
	
	var urlGetCantidadDeTareas = $("#urlGetCantidadDeTareas").val();
	
	$('#'+idDiv).fadeOut("slow").load(url, function() {
    	if (actualizaCantidadDeTareas == true) {
    		$.ajax({
        	    url: urlGetCantidadDeTareas,
        	    type: 'GET',
        	    async: true,
        	    cache: false,
        	    contentType: false,
        	    processData: false,
        	    success: function (returnDataGetCantidadDeTareas) {
        	    	console.log("SUCCESS -- urlGetCantidadDeTareas: ", returnDataGetCantidadDeTareas);	    	
        	    },
        	    error : function(e) {
        			console.log("ERROR: ", e);			
        		},
        		done : function(e) {
        			console.log("DONE");
        		},
        		complete : function(returnDataGetCantidadDeTareas) {
        			console.log("COMPLETE -- urlGetCantidadDeTareas: ", returnDataGetCantidadDeTareas.responseText);
        			$('#cantidadDeTareas').text(returnDataGetCantidadDeTareas.responseText);
        		}
        	});	 
    	}
    	callback();
    }).fadeIn('slow');
	
	/*$('#'+idDiv).each(function(){        	 
        $(this).fadeOut("slow").load(url, function() {
        	if (actualizaCantidadDeTareas == true) {
        		$.ajax({
            	    url: urlGetCantidadDeTareas,
            	    type: 'GET',
            	    async: true,
            	    cache: false,
            	    contentType: false,
            	    processData: false,
            	    success: function (returnDataGetCantidadDeTareas) {
            	    	console.log("SUCCESS -- urlGetCantidadDeTareas: ", returnDataGetCantidadDeTareas);	    	
            	    },
            	    error : function(e) {
            			console.log("ERROR: ", e);			
            		},
            		done : function(e) {
            			console.log("DONE");
            		},
            		complete : function(returnDataGetCantidadDeTareas) {
            			console.log("COMPLETE -- urlGetCantidadDeTareas: ", returnDataGetCantidadDeTareas.responseText);
            			$('#cantidadDeTareas').text(returnDataGetCantidadDeTareas.responseText);
            		}
            	});	 
        	}
        }).fadeIn('slow');
    });*/
}

function devuelveTarea() {
	
	var validaForm = $("#formRetrocedeProceso").validationEngine('validate');	
	if (!validaForm) {
		return;
	}
	
	$('#botonDevuelveTarea').addClass('disabled');
	$("#formRetrocedeProceso").submit();
}

function finalizarProceso(idInstanciaDeTarea, idExpediente) {
	
	var dialog = bootbox.dialog({
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Despachando proceso...</p>'		    
	});	
	
	var urlFinalizarProceso = $("#urlFinalizarProceso").val()+"?idInstanciaDeTarea="+idInstanciaDeTarea;
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	
	$.ajax({
	    url: urlFinalizarProceso,
	    type: 'POST',
	    async: true,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returnData) {
	    	console.log("SUCCESS -- finalizar: ", returnData);	    	
	    },
	    error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData) {			
			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
			dialog.find('.bootbox-body').html(returnData.responseText);
		}
		
	  });
	
}

function aplicaDatePickerPorIdElemento(idDivDate) {
	
    $("#"+idDivDate).datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY'
    });	  
    	
}

function aplicaDatePicker(idDivDate, fechaVencimientoInstanciaDeTarea) {
	
	var maxDate = new Date(fechaVencimientoInstanciaDeTarea);
	var minDate = new Date();
	
	if (maxDate<minDate) {
		minDate = maxDate;
	}
	
    $(idDivDate).datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY',
          minDate: minDate,
          maxDate : maxDate          
    });	
    
    var id = $(idDivDate).attr('id');
    
    $("#"+id+" :input").val('');
	
}
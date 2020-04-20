/**
 * 
 */

function cargaResultadoBusqueda() {	
	
	var urlSessionValida = $("#urlSessionValida").val();
	console.log("urlSessionValida: " + urlSessionValida);
	var raizURL = $("#raizURL").val();	
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  var valid = $("#formBuscar").validationEngine('validate');

	    		if (valid == false) {
	    			return;
	    		}
	    		
	    		var url = $("#urlGetResultadoBusqueda").val();
	    		
	    		console.log("url: " + url);    
	    	    
	    		dialogBuscar = bootbox.dialog({
	    	        message: '<p><i class="fa fa-spin fa-spinner"></i> Buscando Datos ... </p>',
	    	          closeButton: false
	    	    });
	    		
	    		var filtroNombreTipoDocumento = $("#filtroNombreTipoDocumento").val();
	    		var filtroNombreTipoDocumentoS;
	    		
	    		if ($("#filtroNombreTipoDocumento").val()!=null) {
	    			for (var i = 0; i < filtroNombreTipoDocumento.length; i++) {
		    			if (i==0) {
		    				filtroNombreTipoDocumentoS = filtroNombreTipoDocumento[i];
		    			} else {
		    				filtroNombreTipoDocumentoS = filtroNombreTipoDocumentoS + filtroNombreTipoDocumento[i];
		    			}	    			
		    			if (i < filtroNombreTipoDocumento.length - 1 ) {
		    				filtroNombreTipoDocumentoS = filtroNombreTipoDocumentoS + "{}";
		    			}
		    		}
	    		} else {
	    			filtroNombreTipoDocumentoS = "";
	    		}
	    		
	    		console.log(encodeURIComponent(filtroNombreTipoDocumentoS));
	    		    		
	    		var filtroNombreSubprocesoVigente = $("#filtroNombreSubprocesoVigente").val();
	    		var filtroNombreSubprocesoVigenteS;
	    		
	    		if ($("#filtroNombreSubprocesoVigente").val()!=null) {
					for (var i = 0; i < filtroNombreSubprocesoVigente.length; i++) {
						if (i==0) {
							filtroNombreSubprocesoVigenteS = filtroNombreSubprocesoVigente[i];
						} else {
							filtroNombreSubprocesoVigenteS = filtroNombreSubprocesoVigenteS + filtroNombreSubprocesoVigente[i];
						}	    			
						if (i < filtroNombreSubprocesoVigente.length - 1 ) {
							filtroNombreSubprocesoVigenteS = filtroNombreSubprocesoVigenteS + "{}";
						}
					}
	    		} else {
	    			filtroNombreSubprocesoVigenteS = "";
	    		}    		
	    		
	    		console.log(encodeURIComponent(filtroNombreSubprocesoVigenteS));
	    		
	    		$("#palabraClave").val(encodeURIComponent($("#palabraClave").val()));
	    		//$("#nombreTipoDocumento").val(encodeURIComponent($("#filtroNombreTipoDocumento").val()));
	    		$("#nombreTipoDocumento").val(encodeURIComponent(filtroNombreTipoDocumentoS));
	    		//$("#nombreSubprocesoVigente").val(encodeURIComponent($("#filtroNombreSubprocesoVigente").val()));
	    		$("#nombreSubprocesoVigente").val(encodeURIComponent(filtroNombreSubprocesoVigenteS));
	    	    $('#divResultadoBusqueda').fadeOut("slow").load(url, $('#formBuscar').serialize(), function() {
	    	    		dialogBuscar.modal('hide');
	    	    	}).fadeIn('slow');
	    	    	
	    	    $("#palabraClave").val(decodeURIComponent($("#palabraClave").val()));
	    	    $("#nombreTipoDocumento").val(decodeURIComponent($("#nombreTipoDocumento").val()));
	    	    $("#nombreSubprocesoVigente").val(decodeURIComponent($("#nombreSubprocesoVigente").val()));
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_blank');
	                          }
	             );
	      }
	});
}

function buscaSoloEnMiBandejaDeSalida(){	
	
	console.log("Checkeado: " +  $('#soloEnMiBandejaDeSalida').is(':checked'));
	
	if ( $('#soloEnMiBandejaDeSalida').is(':checked')) {
		$('#filtrosBusqueda').attr("disabled", "disabled" );	
	} else {
		$('#filtrosBusqueda').removeAttr("disabled");
	}
	
	$(".tipoObjeto").each(function( i ) {
		
		if ( $('#soloEnMiBandejaDeSalida').is(':checked')) {
			$(this).attr("disabled", "disabled" );
		} else {
			$(this).removeAttr("disabled");
		}	    
	    
	});
	
}

function cargarDetalleDeProceso(idExpediente, nombreProceso, nombreExpediente) {	
	console.log('idExpediente: ' + idExpediente);
	console.log('nombreProceso: ' + nombreProceso);
	console.log('nombreExpediente: ' + nombreExpediente);	
	var urlSessionValida = $("#urlSessionValida").val();
	console.log("urlSessionValida: " + urlSessionValida);
	var raizURL = $("#raizURL").val();	
	$.get(urlSessionValida, function(haySession) {
        console.log("haySession: " + haySession);
        if(haySession) {           
        	$('#detalleDeProcesoModal').modal({backdrop: 'static', keyboard: false});
        	$("#nombreProcesoHeaderModal").html(nombreProceso + " (" + nombreExpediente + ")");
        	var urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda = $("#urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda").val()+"/"+idExpediente;
        	var urlGetTablaDetalleDeExpedientePorIdExpediente = $("#urlGetTablaDetalleDeExpedientePorIdExpediente").val()+'?idExpediente='+idExpediente;
        	var urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente = $("#urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente").val()+'?idExpediente='+idExpediente;
        	console.log("urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda: " + urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda);
        	console.log("urlGetTablaDetalleDeExpedientePorIdExpediente: " + urlGetTablaDetalleDeExpedientePorIdExpediente);
        	console.log("urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente: " + urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente);
            $('#historicoDeInstanciaDeTareaDetalleDeProcesoModal').each(function(){        	 
           			$(this).fadeOut("slow").load(urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda).fadeIn('slow');
           	});            
            $('#detalleDeExpedienteEnDetalleDeProcesoModal').each(function(){        	 
        			$(this).fadeOut("slow").load(urlGetTablaDetalleDeExpedientePorIdExpediente).fadeIn('slow');
        	});            
            $('#etapasInstanciaDeProcesoDetalleDeProcesoModal').each(function(){        	 
        			$(this).fadeOut("slow").load(urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente).fadeIn('slow');
        	});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open(raizURL, '_blank');
                            }
               );
        }
  	});
	
}

function cargaVistaHistorialDeTareasPorIdIntanciaDeTarea(idInstanciaDeTarea, nombreDeTarea) {
	
	console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);	
	console.log("nombreDeTarea: " + nombreDeTarea);
	var urlGetVistaHistorialDeTareasPorIdIntanciaDeTarea = $("#urlGetVistaHistorialDeTareasPorIdIntanciaDeTarea").val() +"?idInstanciaDeTarea=" + idInstanciaDeTarea;
	console.log("urlGetVistaHistorialDeTareasPorIdIntanciaDeTarea: " + urlGetVistaHistorialDeTareasPorIdIntanciaDeTarea);
	
	$("#tituloHistorialDeTareas").html("Historial de " + nombreDeTarea + " (" + idInstanciaDeTarea + ")");
	
	$('#historialDeTareaPorIdTareaModal').modal('show');
	
	$("#divHistorialDeTareaPorIdTareaModalModal").css("position",
    "relative").css("min-height", "200px").prepend(
    $("<div />").addClass("cargando"));

	$("#divHistorialDeTareaPorIdTarea").load(urlGetVistaHistorialDeTareasPorIdIntanciaDeTarea,
														    function() {
														           $("#divHistorialDeTareaPorIdTarea").find(
														                        ".cargando").remove();
														    	   
														    }
														);
	
	
}

function cargarUsuariosDestinosModal(usuariosDestinoString, nombreDeTareaOrigen, nombreTareaDEstino) {

	console.log("usuariosDestinoString: " + usuariosDestinoString);
	
	$("#ulUsuariosAsignadosATarea").html('');
	
	$("#cabeceraUsuariosAsigandosATareaModa").html('');
	
	$("#cabeceraUsuariosAsigandosATareaModa").append("Usuarios Asignados desde " + nombreDeTareaOrigen + " a " + nombreTareaDEstino);
	
	if (usuariosDestinoString.indexOf(",") >= 0) {
		var arr = usuariosDestinoString.split(',');		
		for(i=0; i < arr.length; i++) {
			$("#ulUsuariosAsignadosATarea").append('<li class="list-group-item">' + arr[i] + '</li>');
		}
	} else {
		$("#ulUsuariosAsignadosATarea").append('<li class="list-group-item">' + usuariosDestinoString + '</li>');
	}	
	$('#usuariosAsigandosATareaModal').modal('show');
}
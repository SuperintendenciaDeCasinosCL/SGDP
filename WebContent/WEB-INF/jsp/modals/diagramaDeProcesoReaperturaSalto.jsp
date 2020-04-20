<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:url value="http://${urlFuncPhp}/proceso/bpm/this_task.php?nomExp=${nombreExpediente}" var="urlDiagramaSubProceso" />
<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Reapertura de expediente y salto de tareas</title>
 
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/plugins/datatable2/jquery-1.12.3.js'></script>
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootstrap.min.js'></script>
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootbox.min.js'></script>	
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/buscador.js'></script>	
	<link href='${pageContext.request.contextPath}/css/validaciones/validationEngine.jquery.css' rel="stylesheet">  
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/validaciones/jquery.validationEngine-es.js'></script>  
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/validaciones/jquery.validationEngine.js'></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link href='${pageContext.request.contextPath}/css/sgdpComun.css' rel="stylesheet">
	
	<script>
	
		var inicializaCargaUsuariosRoles = function() {
			$(".djs-shape").off('click').on('click', function () {
				var posiblesUsuarios = [];
				$("#cabeceraReabreInstanciaDeSubprocesoModal").empty();
				$("#idUsuariosPosiblesReabreInstanciaDeSubproceso").empty();				
				$("#botonReabreInstanciaDeSubproceso").attr("data-idinstanciadetarea", "");	
				if ($(this).attr("data-element-id").includes("Task_")) {
					let idDiagramaTarea = $(this).attr("data-element-id");					
					$.get("${sessionURL}", function(haySession) {
						if(haySession) {
					        var urlGetInstanciaDeTareaPorIdDiagramaTareaNombreExpediente = '${pageContext.request.contextPath}/' + "getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente/" + idDiagramaTarea + "/" + "${nombreExpediente}";
					        $.get(urlGetInstanciaDeTareaPorIdDiagramaTareaNombreExpediente, function(data, status) {
					        	$("#botonReabreInstanciaDeSubproceso").attr("data-idInstanciaDeTarea", data.idInstanciaDeTarea);						        
					            if (data.idEstadoTarea==3 || data.idEstadoTarea==1 || data.idEstadoTarea==4) {
					            	posiblesUsuarios = data.posiblesIdUsuarios;
					            	$("#cabeceraReabreInstanciaDeSubprocesoModal").text("Abrir instancia de tarea " + data.nombreDeTarea + " (${nombreExpediente})");
					            	for (i = 0; i < posiblesUsuarios.length; i++) { 
			    						if (posiblesUsuarios[i] != 'undefined') {	
			    							$("#idUsuariosPosiblesReabreInstanciaDeSubproceso").append(new Option(posiblesUsuarios[i], posiblesUsuarios[i]));
			    						}						
			    					}			    					
					            	$('#reabreInstanciaDeSubprocesoModal').modal('show');
						        } else if (data.idEstadoTarea==2) {									
									bootbox.dialog({
									    title: 'Seleccione si requiere reasignar o cerrar la tarea',
									    message: "<p>Seleccione si requiere reasignar o cerrar la tarea.</p>",
									    size: 'medium',
									    buttons: {
									        reasigna: {
									            label: "Reasignar tarea",
									            className: 'btn-info',
									            callback: function(){
									            	cargarDatosContinuarProcesoModal(data.idInstanciaDeTarea, 
									        			    data.idExpediente, 
									        			    false, 
									        			    false,
									        			    data.nombreDeTarea + ' - ' + data.nombreDeProceso + ' - ' + data.nombreExpediente ,//'Visar documento - taller 4 - EXP-792-2017',
									        			    false, 
									        			    true, 
									        			    '',
									        			    data.nombreDeTarea);
									            }
									        },
									        cierra: {
									            label: "Cerrar tarea",
									            className: 'btn-warning',									            
									            callback: function() {
									            	bootbox.prompt("Por favor confirmar cierre de tarea!", function(comentarioCierre){
									            	    let urlCierraInstanciaDeTarea = '${pageContext.request.contextPath}/cierraInstanciaDeTarea';							
									            	    let cierraInstanciaDeTareaDTO = {};
									            	    cierraInstanciaDeTareaDTO["idInstanciaDeTarea"] = data.idInstanciaDeTarea;   
									            	    cierraInstanciaDeTareaDTO["comentario"] = comentarioCierre;
									            	    if (comentarioCierre!=null && comentarioCierre!="") {
									            	    	$.ajax({
										            	        type : "POST",
										            	        contentType : "application/json",
										            	        url : urlCierraInstanciaDeTarea,
										            	        data : JSON.stringify(cierraInstanciaDeTareaDTO),
										            	        dataType : 'json',
										            	        timeout : 100000,
										            	        success : function(data) {
										            	            console.log("SUCCESS: ", data);
										            	        },
										            	        error : function(e) {
										            	            console.log("ERROR: ", e);
										            	        },
										            	        done : function(e) {
										            	            console.log("DONE: ", e);
										            	        },
										            	        beforeSend: function(xhr) {
										            	            $("#divDiagramaDeProcesoReaperturaSalto").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));          
										            	        },
										            	        complete : function(returnData) {
										            	            $("#divDiagramaDeProcesoReaperturaSalto").find(".cargando").remove();
										            	            let mensajeCierre;
																	if (returnData.responseJSON.estado == 0) {
																		mensajeCierre = "Tarea cerrada!";
																	} else {
																		mensajeCierre = "Ocurrio un error al cerrar la tarea!";
																		console.log(returnData.responseJSON.glosa);
																	}
										            	            bootbox.alert({
										            	                message: mensajeCierre,
										            	                callback: function () {
										            	                    window.opener.callParentCargaResultadoBusqueda();
										            	                    window.close();								    	
										            	                }
										            	            });	
										            	        }
										            	    });
									            	    } else if (comentarioCierre==null) {
															return true;
									            		} else {									            	    	
									            	    	$(".bootbox-input-text").validationEngine('showPrompt', 'Por favor ingrese un comentario', 'error');
									            	    	return false;
										            	}									            	     
									            	});									            									                
									            }
									        }
									    }
									});
							    } else {
							    	bootbox.alert("La tarea no est&aacute; en un estado que pueda ser gestionado");
								}
					    	});		
						} else {
							bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
											, function(){
												  window.open('${raizURL}', '_blank');
											}
							);
						}
				  	});	
			    }
			});	
		};
	
		var cargaCursorPuntero = function() {
		    $('[data-element-id^="Task_"]').each(function() {
		        console.log($( this ));      
		        $( this ).addClass('cursorPuntero');
		    });
		};

		var inicializaBotonReabreInstanciaDeSubproceso = function() {
			$("#botonReabreInstanciaDeSubproceso").off('click').on('click', function () {				      
				$.get("${sessionURL}", function(haySession) {
					if(haySession) {
						let idUsuarioSeleccionado = $('#idUsuariosPosiblesReabreInstanciaDeSubproceso').find(":selected").text();
						let urlReabreExpediente = '${pageContext.request.contextPath}/' 
							 + "reabrirExpedientePorIdInstanciaDeTareaIdUsuario";							
						let reabreInstanciaDeSubProcesoDTO = {};
						reabreInstanciaDeSubProcesoDTO["idInstanciaDeTarea"] = $("#botonReabreInstanciaDeSubproceso").attr("data-idInstanciaDeTarea");
						reabreInstanciaDeSubProcesoDTO["idUsuarioSeleccionado"] = idUsuarioSeleccionado;
						reabreInstanciaDeSubProcesoDTO["comentario"] = $("#comentarioReabreInstanciaDeSubproceso").val();
						$.ajax({
			                type : "POST",
			                contentType : "application/json",
			                url : urlReabreExpediente,
			                data : JSON.stringify(reabreInstanciaDeSubProcesoDTO),
			                dataType : 'json',
			                timeout : 100000,
			                success : function(data) {
			                    console.log("SUCCESS: ", data);
			                },
			                error : function(e) {
			                    console.log("ERROR: ", e);
			                },
			                done : function(e) {
			                    console.log("DONE: ", e);
			                },
			                beforeSend: function(xhr) {
								$("#divDiagramaDeProcesoReaperturaSalto").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
								$('#reabreInstanciaDeSubprocesoModal').modal('hide');
							},
							complete : function(returnData) {
								$("#divDiagramaDeProcesoReaperturaSalto").find(".cargando").remove();
								let mensajeReapertura;
								if (returnData.responseJSON.estado == 0) {
									mensajeReapertura = "Tarea reabierta!";
								} else {
									mensajeReapertura = "Ocurrio un error al reabrir!";
									console.log(returnData.responseJSON.glosa);
								}
								bootbox.alert({
								    message: mensajeReapertura,
								    callback: function () {
								    	window.opener.callParentCargaResultadoBusqueda();
								    	window.close();								    	
								    }
								});	
							}
			            });									
					} else {
						bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
								, function(){
									  window.open('${raizURL}', '_blank');
								}
				   		);
					}					
				});
			});
		};

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

			        var urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente = $(location).attr('protocol')+"//"+$(location).attr('host')+"/sgdp/getVistaEtapasInstanciaDeProcesoPorIdExpediente?idExpediente="+idExpediente;
			        var urlGetInstanciasDeTareasQueContinuanDeInstanciaDeTarea = $(location).attr('protocol')+"//"+$(location).attr('host')+"/sgdp/getInstanciasDeTareasQueContinuanDeInstanciaDeTarea?idInstanciaDeTarea="+idInstanciaDeTarea;
			    	var urlGetUsuariosPosiblesPorIdInstanciaDeTarea = $(location).attr('protocol')+"//"+$(location).attr('host')+"/sgdp/getUsuariosPosiblesPorIdInstanciaDeTarea";
			    	
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

			    					$("#divTareasContinuan").after(htmlDivTareasContinuan);			    									
			    					
			    					$('#continuarProcesoModal').modal('show');
			    					
			    					dialog.modal('hide');				
			    					$("#continuarProcesoModal" ).addClass("modalPrincipal");
			    				}
			    	
			    		});	
			    		
			    		$("#buttonAvanzarProcesoModal").html('<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>Reasignar');		
			    		
			    	} 
			   } else {
			         bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacuten y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
			             , function(){ window.open(urlLogin, '_blank');}
			         );
			   }
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
		    
		    let urlMueveProceso = '${pageContext.request.contextPath}/' 
									 + "mueveProceso";
			
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
						window.opener.callParentCargaResultadoBusqueda();
 	                    window.close();	
					} else {		
						dialogMueveProceso.find('.bootbox-body').html(returnData.responseJSON.respuestaMueveProceso);
					}		
				}
				
			  });		
		}	
		
		$(document).ready(function() {
			$(inicializaCargaUsuariosRoles);
			$(cargaCursorPuntero);
			$(inicializaBotonReabreInstanciaDeSubproceso);	
		});
		
	</script>
</head>
<body>
	<div class="col-sm-12" id="divDiagramaDeProcesoReaperturaSalto">
		<c:import url="${urlDiagramaSubProceso}"></c:import>
	</div>
	
	<!-- Reabre Instancia de SubProceso  -->
	
	<div class="modal fade" id="reabreInstanciaDeSubprocesoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			 <form id="formReabreInstanciaDeSubproceso" class="form-horizontal">
			
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 id="cabeceraReabreInstanciaDeSubprocesoModal" class="modal-title"></h3>
				</div>
				
				<div class="modal-body">
				
					<div class="form-group asignaciones-tareas-modal">
					
						<label id="labelNombreTareaReabreInstanciaDeSubproceso" style="font-size: smaller;" class="control-label col-sm-3">Usuarios: </label>
						
						<div class="col-sm-4">
						
							<select class="form-control usuarios-asignados" id="idUsuariosPosiblesReabreInstanciaDeSubproceso" >
							
							</select>
						
						</div>
					
					</div>
	              
              		<div class="form-group"> 
              		
              			<label class="control-label col-sm-3"><spring:message code="bandejaDeEntrada.modal.continuarProceso.form.input.comentario.label"/></label>	             			
              			<div class="col-sm-8">	
              				<textarea class="form-control validate[required]" id="comentarioReabreInstanciaDeSubproceso" name="comentario" placeholder='<spring:message code="bandejaDeEntrada.modal.continuarProceso.form.input.comentario.placeholder"/>' rows="10"></textarea>              			
              			</div>
              		
              		</div>
              			              		
				</div>
				
				<div class="modal-footer">
				
           			<button id="botonReabreInstanciaDeSubproceso" type="button" class="btn btn-labeled btn-primary btn-block">
           				<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>
           				<span id="textoBotonReabreInstanciaDeSubproceso">Reabrir</span>
           			</button>
				
				</div>
			</form>			
		</div>
	</div>
</div>


<div class="modal fade" id="continuarProcesoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			 <form method="POST" id="formContinuarProceso" action="${pageContext.request.contextPath}/mueveProceso" 
			 class="form-horizontal">
				
				<input type="hidden" name="idInstanciaDeTareaOrigen" id="idInstanciaDeTareaOrigen" />
				<input type="hidden" name="avanzaRetrocede" id="avanzaRetrocede" />
				<input type="hidden" name="idExpedienteContinuarProceso" id="idExpedienteContinuarProceso" />
				<input type="hidden" name="asignacionesTareasJSON" id="asignacionesTareasJSON" />
				<input type="hidden" name="reasigna" id="reasigna" />
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 id="cabeceraContinuarProcesoModal" class="modal-title"></h3>
				</div>
				
				<div class="modal-body">	
								
					<div class="form-group" id="divTareasContinuan">	
					
						<div id="etapasInstanciaDeProcesoContinuarProcesoModal" class="col-sm-12">						
							
					    </div>		
					             
					</div>	              	
	              
              		<div class="form-group"> 
              			<label class="control-label col-sm-3"><spring:message code="bandejaDeEntrada.modal.continuarProceso.form.input.comentario.label"/></label>	             			
              			<div class="col-sm-8">	
              				<textarea class="form-control validate[required]" id="comentario" name="comentario" placeholder='<spring:message code="bandejaDeEntrada.modal.continuarProceso.form.input.comentario.placeholder"/>' rows="10"></textarea>              			
              			</div>
              		</div>
              			              		
				</div>
				
				<div class="modal-footer">
				
           			<button id="buttonAvanzarProcesoModal" type="button" class="btn btn-labeled btn-primary col-md-offset-7">
           				<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>
           				<spring:message code="bandejaDeEntrada.modal.continuarProceso.form.botonEjecuar.valor"/>
           			</button>
				
				</div>
			</form>			
		</div>
	</div>
</div>
	
</body>
</html>





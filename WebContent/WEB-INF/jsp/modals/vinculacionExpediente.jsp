<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 

<c:set var="permisoPuedeVincularExpedientes" value="<%=PermisoType.PUEDE_VINCULAR_EXPEDIENTES.getNombrePermiso()%>"/>

<div class="modal fade" id="vinculacionExpedienteModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		
	<div class="modal-dialog modal-lg">
	
		<div class="modal-content">
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title" id="vinculacionExpedienteModalTitulo"></h3>
			</div>
			
			<div class="modal-body">
				<div class="row">						
					<div class="panel-body">
						<div class="col-sm-12">	
							<c:if test="${permisos[permisoPuedeVincularExpedientes] eq permisoPuedeVincularExpedientes}">
								<form class="form-horizontal" id="formVinculacionExp">
									<div class="form-group">		
										<div class="col-sm-2"><label class="control-label" for="expedienteAntecesor">Expediente antecesor:</label></div>
										<div class="col-sm-10"><input type="text" class="form-control validate[required]" id="expedienteAntecesor"></div>
									</div>
									<div class="form-group">
										<div class="col-sm-2"><label class="control-label" for="comentarioVinculacion">Comentario de la vinculaci&oacute;n:</label></div>
										<div class="col-sm-10"><input type="text" class="form-control validate[required]" id="comentarioVinculacion"></div>
									</div>						        	
					        		<div class="form-group">					        													
										<div class="col-sm-12">
											<button onclick="vincularExpedienteM()" id="vincularExpedienteBoton" type="button" title="Vincular expediente" class="btn btn-primary btn-block">
												<span class="glyphicon glyphicon-link font-icon-2"></span> Vincular
											</button>
										</div>
										<div class="col-sm-8"></div>
									</div>			
								</form>	
							</c:if>
							<div class="col-sm-12">	
								<div class="row">
									<div id="respuestaVinculacion"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="row"><div class="col-sm-12"><h4>Lista de v&iacute;nculos</h4></div></div>
							<div class="row">								
								<div class="col-sm-4">
									<a id="linkVincularExpExcelHidden" href="#" style="display: none;"></a>
									<a id="linkVincularExpExcel" onclick="vincularExpExcel()" href="#" class="btn btn-default btn-block" role="button">Exportar a Excel</a>
								</div>
								<div class="col-sm-8"></div>
							</div>
							<br>	
						</div>	
						<div class="col-sm-12">	
							<div class="row">										
								<div id="vinculacionExpedienteDiv">						
								</div>
							</div>
						</div>							
					</div> 
				</div>
			</div>						
			<div class="modal-footer"></div>
		</div>
	</div>
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script type="text/javascript">	
	function vincularExpExcel() {
		$.get("${sessionURL}", function(haySession) {
			if(haySession) {
				$("#linkVincularExpExcelHidden").attr('href', '${pageContext.request.contextPath}/vincularExpExcel/'+$("#linkVincularExpExcel").attr('data-id-expediente'));	    		
				document.getElementById("linkVincularExpExcelHidden").click();
			} else {
				bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                     , function(){
	                           window.open('${raizURL}', '_blank');
	                     }
	        	);
			}
		});	
	}
	function vinculaciones(idExpediente, nombreExpediente) {
		$.get("${sessionURL}", function(haySession) {
			if(haySession) {
				console.log("idExpediente: " + idExpediente);
				console.log("nombreExpediente: " + nombreExpediente);
				if ($("#expedienteAntecesor").length) {
					$("#expedienteAntecesor").val('');
				}
				if ($("#comentarioVinculacion").length) {
					$("#comentarioVinculacion").val('');
				}				
				if ($("#vincularExpedienteBoton").length) {
					$("#vincularExpedienteBoton").attr('data-nombre-expediente', nombreExpediente);
					$("#vincularExpedienteBoton").attr('data-id-expediente', idExpediente);
				}	
				if ($("#linkVincularExpExcel").length) {
					$("#linkVincularExpExcel").attr('data-id-expediente', idExpediente);
				}		
				$('#vinculacionExpedienteModalTitulo').empty();
				$('#respuestaVinculacion').empty();
				$("#respuestaVinculacion").removeClass();
				$('#vinculacionExpedienteModalTitulo').text('Vinculaciones del expediente ' + nombreExpediente);			
				$('#vinculacionExpedienteModal').modal({backdrop: 'static', keyboard: false});
				$("#vinculacionExpedienteDiv").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
				var urlGetVinculacionesDeExpediente = '${pageContext.request.contextPath}/' + "getVinculacionesDeExpediente/" + idExpediente;
				$("#vinculacionExpedienteDiv").load(urlGetVinculacionesDeExpediente, function() {
					formatTablaVinculacionesDeExpediente();					
					$("#vinculacionExpedienteDiv").find(".cargando").remove();
					$("#vinculacionExpedienteDiv").css("position","");
					$("#vinculacionExpedienteDiv").css("min-height","");                			                       
                } );
			}
			else {
				 bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                     , function(){
	                           window.open('${raizURL}', '_blank');
	                     }
	        	);
			}
		});	
	}	
	function formatTablaVinculacionesDeExpediente() {
		var tablaVinculacionesDeExpediente = $('#tablaVinculacionesDeExpediente')
		.DataTable(
				{					
					"language" : lenguaje_es,
					"paging": false,
					"searching": false			
				});		
	};
	function vincularExpedienteM() {		
	    $.get("${sessionURL}", function(haySession) {
			console.log("haySession: " + haySession);
	    	if(haySession) {
	    		var validaForm = $("#formVinculacionExp").validationEngine('validate');
	    		if (validaForm == true) {
	    			var vinculacionExpedienteDTO = {};
	    			var urlVinculacionExpediente = '${pageContext.request.contextPath}/' + "vincularExp";
	    	       	vinculacionExpedienteDTO["nombreExpediente"] = $("#vincularExpedienteBoton").attr('data-nombre-expediente');
	    	       	vinculacionExpedienteDTO["nombreExpAntecesor"] =  $("#expedienteAntecesor").val().trim().toUpperCase();
	    	       	vinculacionExpedienteDTO["comentario"] =  $("#comentarioVinculacion").val();	    	       	
	    			var mensajeDeConfirmacion = 'Se vincular&aacute; el expediente ' + $("#vincularExpedienteBoton").attr('data-nombre-expediente') + 
	    										' con el expediente ' + $("#expedienteAntecesor").val() + ' y el comentario de la vinculaci&oacute;n ser&aacute;: "' + $("#comentarioVinculacion").val() + '"';
	    			bootbox.confirm({
	    			    message: mensajeDeConfirmacion,
	    			    buttons: {
	    			        confirm: {
	    			            label: 'Vincular',
	    			            className: 'btn-success'
	    			        },
	    			        cancel: {
	    			            label: 'No',
	    			            className: 'btn-danger'
	    			        }
	    			    },
	    			    callback: function (result) {
	    			    	if (result == true) {
	    			    		$("#vinculacionExpedienteDiv").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
		    	    	       	$.ajax({
		    						type : "POST",
		    						contentType : "application/json",
		    					    url: urlVinculacionExpediente,
		    						data : JSON.stringify(vinculacionExpedienteDTO),
		    						dataType : 'json',
		    						timeout : 100000,
		    						success : function(data) {
		    	                        console.log("SUCESS: ", data);           							 
		    						},
		    						error : function(e) {
		    							 console.log("ERROR: ", e);
		    						},
		    						complete: function(data){							
		    							console.log("COMPLETE: ", data);
		    							$('#respuestaVinculacion').text(data.responseJSON.respuestaVinculacion);
		    							$("#respuestaVinculacion").removeClass();
		    							$('#respuestaVinculacion').addClass(data.responseJSON.cssStatus);
		    							var urlGetVinculacionesDeExpediente = '${pageContext.request.contextPath}/' + "getVinculacionesDeExpediente/" + $("#vincularExpedienteBoton").attr('data-id-expediente');						
		    							$("#vinculacionExpedienteDiv").load(urlGetVinculacionesDeExpediente, function() {
		    								formatTablaVinculacionesDeExpediente();
		    								$("#vinculacionExpedienteDiv").find(".cargando").remove(); 
		    								$("#vinculacionExpedienteDiv").css("position","");
		    								$("#vinculacionExpedienteDiv").css("min-height","");                			                       
		    			                } );
		    						},		
		    					});
	    			    	}
	    			    }
	    			});
	        	}  
	          } else {
	                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                              , function(){
	                                    window.open('${raizURL}', '_blank');
	                              }
	                 );
	        }
	    });		
	};			
</script>
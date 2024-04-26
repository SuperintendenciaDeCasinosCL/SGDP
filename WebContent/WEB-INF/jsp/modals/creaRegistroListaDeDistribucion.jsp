<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="creaRegistroListaDeDistribucionModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="creaRegistroCorreoDeDistribucionModalH4" class="modal-title">Nuevo registro</h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="creaRegistroListaDeDistribucionDiv" class="panel-body">
					
						<form class="form-horizontal" id="formCreaRegistroListaDeDistribucion">
							<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="creaNombreCompletoLabel" for="creaNombreCompleto">Nombre Completo (*): </label>
						  		<div class="col-sm-9">  			
						  			<input type="text" class="form-control validate[required]" id="creaNombreCompleto">
						  		</div>  		
						  	</div>
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="creaOrganizacionLabel" for="creaOrganizacion">Organizaci&oacute;n (*): </label>
						  		<div class="col-sm-9">  			
						  			<input type="text" class="form-control validate[required]" id="creaOrganizacion">
						  		</div>  		
						  	</div>
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="creaCargoLabel" for="creaCargo">Cargo (*): </label>
						  		<div class="col-sm-9">  			
						  			<input type="text" class="form-control validate[required]" id="creaCargo">
						  		</div>  		
						  	</div>
							<div class="form-group">  		
								<label class="control-label col-sm-3" id="creaEmailLabel" for="creaEmail">Email (*): </label>
								<div class="col-sm-9">  			
									<input type="text" class="form-control validate[required, custom[email]]" id="creaEmail">
								</div>  		
						  	</div>
						  	<div class="form-group">
						    	<label class="control-label col-sm-3" for="fechaInicioVigencia">Fecha Inicio Vigencia (*):</label>
						    	<div class="col-sm-9">
						    		 <div class="input-group date fecha-desde-hasta-destinatario-modal">
										<input type="text" class="form-control validate[required]" id="fechaInicioVigencia" placeholder="Ej: 12/08/2023">
											<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
											</span>
									</div>
						    	</div>
						    </div>
						    <div class="form-group">
						    	<label class="control-label col-sm-3" for="fechaFinVigencia">Fecha Fin Vigencia (*):</label>
						    	<div class="col-sm-9">
						    		 <div class="input-group date fecha-desde-hasta-destinatario-modal">
										<input type="text" class="form-control validate[required]" id="fechaFinVigencia" placeholder="Ej: 12/08/2023">
											<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
											</span>
									</div>
						    	</div>
						    </div>
						    <div class="form-group">  		
								<label class="control-label col-sm-3" id="numeroTelefono1Label" for="numeroTelefono1">N&uacute;mero T&eacute;lefono 1 : </label>
								<div class="col-sm-9">  			
									<input type="text" maxlength="15" class="form-control validate[custom[onlyNumberSp]]" id="numeroTelefono1">
								</div>  		
						  	</div>
						  	<div class="form-group">  		
								<label class="control-label col-sm-3" id="numeroTelefono2Label" for="numeroTelefono2">N&uacute;mero T&eacute;lefono 2 : </label>
								<div class="col-sm-9">  			
									<input type="text" maxlength="15" class="form-control validate[custom[onlyNumberSp]]" id="numeroTelefono2">
								</div>  		
						  	</div>
						  	<div class="form-group">  		
								<label class="control-label col-sm-3" id="creaTipoDestinatarioLabel" for="creaEmail">Tipo de Destinatario (*): </label>
								<div class="col-sm-9">  			
									<select class="form-control validate[required]" id="tipoDestinatario" name="tipoDestinatario">
							        	<option value="">Seleccione Destinatario</option>
									    <c:forEach items="${listaTipoDeDestinatarioDTO}" var="tipoDeDestinatarioDTO">
											   <option value="${tipoDeDestinatarioDTO.idTipoDestinatario}">${tipoDeDestinatarioDTO.nombreTipoDestinatario}</option>
									   </c:forEach>	
									</select>	  
								</div>  		
						  	</div>
						  	<div class="col-sm-3">						  	
						  	</div>
						  	<div class="col-sm-9"> 
								<button id="botonCreaRegistroDeListaDeDistribucion" onclick="creaRegistroDeListaDeDistribucion()"						       									       								       			
												type="button" 
												class="btn btn-labeled btn-primary btn-lg btn-block">
											Guardar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
											</button> 								
							</div>    		     
						</form>				
					
					</div>  
				</div>	
	    	</div>
	    </div>    
  	</div>
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function creaRegistroDeListaDeDistribucion() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var validaForm = $("#formCreaRegistroListaDeDistribucion").validationEngine('validate');
	        var urlCreaRegistroDeListaDeDistribucion = "${pageContext.request.contextPath}" + "/creaRegistroDeListaDeDistribucion";
	        var urlCargaListaDeDistribucion = $("#urlCargaListaDeDistribucion").val();
	        console.log("urlCreaRegistroDeListaDeDistribucion: " + urlCreaRegistroDeListaDeDistribucion);
	        console.log("urlCargaListaDeDistribucion: "+ urlCargaListaDeDistribucion);
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Crea registro",
	                message: "Por favor confirmar la creaci&oacute;n",
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
	                        var registroListaDeDistribucionDTO = {};			        
	                        registroListaDeDistribucionDTO["nombreCompleto"] = $("#creaNombreCompleto").val();
	                        registroListaDeDistribucionDTO["email"] = $("#creaEmail").val();
	                        registroListaDeDistribucionDTO["organizacion"] = $("#creaOrganizacion").val();
	                        registroListaDeDistribucionDTO["cargo"] = $("#creaCargo").val();
	                        registroListaDeDistribucionDTO["idTipoDestinatario"] = $("#tipoDestinatario").val();
	                        registroListaDeDistribucionDTO["fechaInicioVigenciaS"] = $("#fechaInicioVigencia").val();
	                        registroListaDeDistribucionDTO["fechaFinVigenciaS"] = $("#fechaFinVigencia").val();
	                        registroListaDeDistribucionDTO["numeroTelefono1"] = $("#numeroTelefono1").val();
	                        registroListaDeDistribucionDTO["numeroTelefono2"] = $("#numeroTelefono2").val();
	                        console.log(JSON.stringify(registroListaDeDistribucionDTO));			      			       
	                        $.ajax( {
	                            url: urlCreaRegistroDeListaDeDistribucion,
	                            type: 'POST',
	                            data: JSON.stringify(registroListaDeDistribucionDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                            	if (document.getElementById('listaDeDistribucionCuerpo') !== null) {
	                            		$("#listaDeDistribucionCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                                }
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                if (document.getElementById('listaDeDistribucionCuerpo') !== null) {
	                                	$("#listaDeDistribucionCuerpo").find(".cargando").remove();	
	                                }	
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                console.log("COMPLETE -- crea registro: ", returnData);
	                                if (returnData.responseJSON.respuesta=="OK") {
	                                	if ($("#detalleDeExpedienteEnDistribucionModal").length>0 && $('#correosDist').length>0) {
	                                		$("#detalleDeExpedienteEnDistribucionModal").css("position", "relative").prepend($("<div />").addClass("cargando"));
	                                		$.get("${sessionURL}", function(haySession){
	                                	        console.log("haySession: " + haySession);
	                                	        if(haySession) {
	                                	        	$('#correosDist').load("${pageContext.request.contextPath}"+ "/getListaDistribucionCorreo", function() {
	                                					$("#detalleDeExpedienteEnDistribucionModal").find(".cargando").remove();
	                                					});	
	                                	        } else {
	                                	              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                                	                            , function(){
	                                	                                  window.open('${raizURL}', '_self');
	                                	                            }
	                                	               );
	                                	        }
	                                	  	});		
	                                	}
	                                    $.notify({
	                                        message: "Registro creado"
	                                    },{
	                                        type: 'success'
	                                    });
	                                } else {
	                                    $.notify({
	                                        message: returnData.responseJSON.respuesta
	                                    },{
	                                        type: 'danger'
	                                    });
	                                }          
	                                if (document.getElementById('listaDeDistribucionCuerpo') !== null) {
	                                	$("#listaDeDistribucionCuerpo").load(urlCargaListaDeDistribucion,
		                                        function() {
		                                            $("#listaDeDistribucionCuerpo").find(".cargando").remove();                			                       
		                                });	
	                                }
	                                $('#creaRegistroListaDeDistribucionModal').modal('hide'); 	            					
	                            }                		
	                        });
	                    } 
	                }
	            });
	        }
	    } else {
	          bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                        , function(){
	                              window.open('${raizURL}', '_blank');
	                        }
	           );
	    }
	});
}

$(document).ready(function() {
	$(inicializaFechasDesdeHastaDestinatario);
});

var inicializaFechasDesdeHastaDestinatario = function() {
	$('.fecha-desde-hasta-destinatario-modal').datetimepicker({
		locale : 'es',
		format : 'DD/MM/YYYY'
	});
};

</script> 
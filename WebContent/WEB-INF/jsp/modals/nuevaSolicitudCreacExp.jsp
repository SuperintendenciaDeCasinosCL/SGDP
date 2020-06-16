<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="nuevaSolicitudCreacExpModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="nuevaSolicitudCreacExpH4" class="modal-title">Formulario de solicitud de creaci&oacute;n de expediente</h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="nuevaSolicitudCreacExpDiv" class="panel-body">					
						<form class="form-horizontal" id="formNuevaSolicitudCreacExp">
							<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="subprocesoMateriaFormCreacExpLabel" for="subprocesoMateriaFormCreacExp">SubProceso (*): </label>
						  		<div class="col-sm-8">  			
						  			  <select onchange="cargaDestinatariosPorIdProceso()" class="form-control validate[required] select2-proc-sol-crea" id="subprocesoMateriaFormCreacExp" name="subprocesoMateriaFormCreacExp">
						  			  	<option value="">Seleccione subproceso</option>
									    <c:forEach items="${todosProcesoFormCreaExpDTO}" var="procesoFormCreaExpDTO">
										   <option value="${procesoFormCreaExpDTO.idProceso}">${procesoFormCreaExpDTO.nombreProceso}</option>
										</c:forEach>								    
									  </select>
						  		</div>  		
						  	</div>						  	
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="autorCreacExpLabel" for="autorCreacExp">Autor (*): </label>
						  		<div class="col-sm-8">  			
						  			  <select class="form-control validate[required] select2-autor-sol-crea" id="autorCreacExp" name="autorCreacExp">
									    <option value="">Seleccione autor</option>
									    <c:forEach items="${autoresDTO}" var="autorDTO">
										   <option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
										</c:forEach>								    
									  </select>
						  		</div>  		
						  	</div>						  	
							<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="asuntoMateriaFormCreacExpLabel" for="asuntoMateriaFormCreacExp">Asunto o materia (*): </label>
						  		<div class="col-sm-8">  			
						  			<input type="text" class="form-control validate[required]" id="asuntoMateriaFormCreacExp">
						  		</div>  		
						  	</div>
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="destinatarioCreacExpLabel" for="destinatarioCreacExp">Destinatario (*): </label>
						  		<div class="col-sm-8">  			
						  			  <select class="form-control validate[required]" id="destinatarioCreacExp" name="destinatarioCreacExp">
						  			  	<option value="">Seleccione destinatario</option>									   								    
									  </select>
						  		</div>  		
						  	</div>
						  	
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="comentarioSolicitudCreaExpLabel" for="comentarioSolicitudCreaExp">Comentario al creador de expediente: </label>
						  		<div class="col-sm-8">  			
						  			 <input class="form-control" type="text" id="comentarioSolicitudCreaExp" name="comentarioSolicitudCreaExp" />
						  		</div>  		
						  	</div>
						  		
						  	<div class="form-group">						 						
							  	<div class="col-sm-3">						  	
							  	</div>
							  	<div class="col-sm-8"> 
									<button id="botonNuevaSolicitudCreacExp" onclick="nuevaSolicitudCreacExp()"						       									       								       			
													type="button" 
													class="btn btn-labeled btn-primary btn-lg btn-block">
										Solicitar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
									</button> 								
								</div>
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

function nuevaSolicitudCreacExp() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	    	var validaForm = $("#formNuevaSolicitudCreacExp").validationEngine('validate');
	    	if (validaForm == true) {
	    		bootbox.confirm({
	                title: "Nueva Solicitud",
	                message: "Por favor confirmar la creaci&oacute;n de una nueva solicitud",
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
	                    	var urlNuevaSolicitudCreacionExp = "${pageContext.request.contextPath}"+"/nuevaSolicitudCreacionExp";
	                    	var urlGetSolicitudesDeCreacionDeExpediente = "${pageContext.request.contextPath}"+"/getSolicitudesDeCreacionDeExpediente";	        		    	
	                        var solicitudCreacionExpDTO = {};			        
	                        solicitudCreacionExpDTO["idProceso"] = $("#subprocesoMateriaFormCreacExp").val();
	                        solicitudCreacionExpDTO["idAutor"] = $("#autorCreacExp").val();
	                        solicitudCreacionExpDTO["idUsuarioDestinatario"] = $("#destinatarioCreacExp").val();
	                        solicitudCreacionExpDTO["asuntoMateria"] = $("#asuntoMateriaFormCreacExp").val();
	                        solicitudCreacionExpDTO["comentario"] = $("#comentarioSolicitudCreaExp").val();	                        	   			      			       
	                        $.ajax( {
	                            url: urlNuevaSolicitudCreacionExp,
	                            type: 'POST',
	                            data: JSON.stringify(solicitudCreacionExpDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#nuevaSolicitudCreacExpModal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#nuevaSolicitudCreacExpModal").find(".cargando").remove();	
	                                $("#nuevaSolicitudCreacExpModal").css("position", "");		
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                if (returnData.responseJSON.glosa=="OK") {
	                                    $.notify({
	                                        message: "Solicitud creada con id " + returnData.responseJSON.idSolicitudCreacionExp
	                                    },{
	                                        type: 'success'
	                                    });
	                                } else {
	                                    $.notify({
	                                        message: "Ocurrio un error al crear el registro"
	                                    },{
	                                        type: 'danger'
	                                    });
	                                }     			
	                                $("#solicitudesCreacionExp").load(urlGetSolicitudesDeCreacionDeExpediente,	    	                                
	                                        function() {
	                                            $("#nuevaSolicitudCreacExpModal").find(".cargando").remove();                			                       
	                                });	
	                                $('#nuevaSolicitudCreacExpModal').modal('hide');
	                                $("#nuevaSolicitudCreacExpModal").css("position", ""); 	            					
	                            }                		
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
}

function cargaDestinatariosPorIdProceso() {

	$.get("${sessionURL}", function(haySession){
		
	    console.log("haySession: " + haySession);
	    
	    if(haySession) {

	    	var idProceso = $("#subprocesoMateriaFormCreacExp").val();

	    	if (idProceso != "") {

	    		var urlGetUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso = "${pageContext.request.contextPath}"+"/getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso/"+idProceso;
		    	   
		        $.get(urlGetUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso, function(usuariosResponsabilidadDTO, status) {
		            var create = '<option value="">Seleccione destinatario</option>';
		            usuariosResponsabilidadDTO.forEach(function (usuarioResponsabilidadDTO) {
		                create += '<option value="'+usuarioResponsabilidadDTO.idUsuario +'">'+ usuarioResponsabilidadDTO.idUsuario +'</option>';                    
		            }); 
		            $('#destinatarioCreacExp').html("");
		            $('#destinatarioCreacExp').append(create);       
		            console.log(create);
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
       	   			
}

var inicializaSelect2ProcSolCrea = function(){
	$(".select2-proc-sol-crea").select2({
		    theme: "bootstrap",
 		    dropdownParent: $("#formNuevaSolicitudCreacExp"),
 		    language: "es"
 		});
};

var inicializaSelect2AutorSolCrea = function(){
	$(".select2-autor-sol-crea").select2({
		    theme: "bootstrap",
 		    dropdownParent: $("#formNuevaSolicitudCreacExp"),
 		    language: "es"
 		});
};

$(document).ready(function(){
	$(inicializaSelect2ProcSolCrea);
	$(inicializaSelect2AutorSolCrea);		
});

</script> 
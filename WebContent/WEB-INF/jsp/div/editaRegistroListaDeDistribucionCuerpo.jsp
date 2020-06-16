<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaRegistroListaDeDistribucion">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="nombreCompletoLabel" for="nombreCompleto">Nombre: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="nombreCompleto" 
  			     value="${registroListaDeDistribucionDTO.nombreCompleto}">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="organizacionLabel" for="organizacion">Organizaci&oacute;n: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="organizacion" 
  			     value="${registroListaDeDistribucionDTO.organizacion}">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="cargoLabel" for="cargo">Cargo: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="cargo" 
  			     value="${registroListaDeDistribucionDTO.cargo}">
  		</div>  		
  	</div>
	<div class="form-group">  		
		<label class="control-label col-sm-3" id="emailLabel" for="email">Email: </label>
		<div class="col-sm-9">  			
			<input type="text" class="form-control validate[required, custom[email]]" id="email" 
			     value="${registroListaDeDistribucionDTO.email}">
		</div>  		
  	</div>
  	<div class="col-sm-3"></div>
  	<div class="col-sm-9"> 
		<button id="botonActualizarRegistroDeListaDeDistribucion" onclick="actualizarRegistroDeListaDeDistribucion(${registroListaDeDistribucionDTO.idListaDeDistribucion})"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Actualizar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 
	</div>    		     
</form>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function actualizarRegistroDeListaDeDistribucion(idListaDeDistribucion) {	
	$.get("${sessionURL}", function(haySession) {	    
	    if(haySession) {
	        var validaForm = $("#formEditaRegistroListaDeDistribucion").validationEngine('validate');
	        var urlActualizaRegistroDeListaDeDistribucion = "${pageContext.request.contextPath}" + "/actualizaRegistroDeListaDeDistribucion";
	        var urlCargaListaDeDistribucion = $("#urlCargaListaDeDistribucion").val();	       
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Actualizar registro",
	                message: "Por favor confirmar la actualizaci&oacute;n",
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
	                        registroListaDeDistribucionDTO["idListaDeDistribucion"] = idListaDeDistribucion;
	                        registroListaDeDistribucionDTO["nombreCompleto"] = $("#nombreCompleto").val();
	                        registroListaDeDistribucionDTO["email"] = $("#email").val();
	                        registroListaDeDistribucionDTO["organizacion"] = $("#organizacion").val();
	                        registroListaDeDistribucionDTO["cargo"] = $("#cargo").val();
	                        console.log(JSON.stringify(registroListaDeDistribucionDTO));			      			       
	                        $.ajax( {
	                            url: urlActualizaRegistroDeListaDeDistribucion,
	                            type: 'POST',
	                            data: JSON.stringify(registroListaDeDistribucionDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#listaDeDistribucionCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#listaDeDistribucionCuerpo").find(".cargando").remove();			
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                console.log("COMPLETE -- actualiza registro: ", returnData);
	                                if (returnData.responseJSON.respuesta=="OK") {
	                                    $.notify({
	                                        message: "Registro actualizado"
	                                    },{
	                                        type: 'success'
	                                    });
	                                } else {
	                                    $.notify({
	                                        message: "Ocurrio un error al actualizar el registro"
	                                    },{
	                                        type: 'danger'
	                                    });
	                                }                			
	                                $("#listaDeDistribucionCuerpo").load(urlCargaListaDeDistribucion,
	                                        function() {
	                                            $("#listaDeDistribucionCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#editaRegistroListaDeDistribucionModal').modal('hide'); 	            					
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

</script>
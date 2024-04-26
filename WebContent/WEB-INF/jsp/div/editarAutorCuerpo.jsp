<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaAutor">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" for="idAutor">ID Autor: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="idAutorEditar" 
  			     value="${autorDTO.idAutor}" readonly>
  		</div>  		
  	</div>
	<div class="form-group">  		
  		<label class="control-label col-sm-3" for="nombreAutor">Nombre Autor: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="nombreAutorEditar" 
  			     value="${autorDTO.nombreAutor}">
  		</div>  		
  	</div>
  	<div class="col-sm-3"></div>
  	<div class="col-sm-9"> 
		<button id="botonActualizarRegistroDeListaDeDistribucion" onclick="actualizarAutor(${autorDTO.idAutor})"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Actualizar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 
	</div>    		     
</form>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>
	function actualizarAutor(idAutor) {	
		$.get("${sessionURL}", function(haySession) {
			if(haySession) {
				var validaForm = $("#formEditaAutor").validationEngine('validate');
				var urlActualizarAutor = "${pageContext.request.contextPath}" + "/actualizarAutor";
			    var urlCargaAutores = "${pageContext.request.contextPath}" + "/cargarAutores";
			    if (validaForm == true) {
			    	bootbox.confirm({
		            	title: "Actualizar Autor",
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
		                    if (result == true) {
		                        var autorDTO = {};
		                        autorDTO["idAutor"] = $("#idAutorEditar").val();			        
		                        autorDTO["nombreAutor"] = $("#nombreAutorEditar").val();	   			      			       
		                        $.ajax( {
		                            url: urlActualizarAutor,
		                            type: 'POST',
		                            data: JSON.stringify(autorDTO),
		                            dataType: 'json',
		                            contentType: "application/json",                	    
		                            beforeSend: function(xhr) {
		                                $("#mantenedorAutoresCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
		                            },
		                            success: function (returnData) {
		                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
		                            },
		                            error : function(e) {
		                                console.log("ERROR: ", e);
		                                $("#mantenedorAutoresCuerpo").find(".cargando").remove();			
		                            },
		                            done : function(e) {
		                                console.log("DONE");
		                            },
		                            complete : function(returnData) {
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
		                                $("#mantenedorAutoresCuerpo").load(urlCargaAutores,
		                                        function() {
		                                            $("#mantenedorAutoresCuerpo").find(".cargando").remove();                			                       
		                                });	
		                                $('#editarAutorModal').modal('hide'); 	            					
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
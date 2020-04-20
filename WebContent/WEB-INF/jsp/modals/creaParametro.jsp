<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="creaParametroModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="creaParametroModalH4" class="modal-title">Nuevo par&aacute;metro</h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="creaParametroDiv" class="panel-body">
					
						<form class="form-horizontal" id="formCreaParametro">
							<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="creaNombreParametroLabel" for="creaNombreParametro">Nombre par&aacute;metro: </label>
						  		<div class="col-sm-9">  			
						  			<input type="text" class="form-control validate[required]" id="creaNombreParametro">
						  		</div>  		
						  	</div>
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="creaValorStringParametroLabel" for="creaValorStringParametro">Valor texto par&aacute;metro: </label>
						  		<div class="col-sm-9">  			
						  			<input type="text" class="form-control validate[required]" id="creaValorStringParametro">
						  		</div>  		
						  	</div>
						  	<div class="form-group">  		
						  		<label class="control-label col-sm-3" id="creaValorNumParametroLabel" for="creaValorNumParametro">Valor Numerico par&aacute;metro: </label>
						  		<div class="col-sm-9">  			
						  			<input type="text" class="form-control validate[required]" id="creaValorNumParametro">
						  		</div>  		
						  	</div>							
						  	<div class="col-sm-3">						  	
						  	</div>
						  	<div class="col-sm-9"> 
								<button id="botonCreaParametro" onclick="creaParametro()"						       									       								       			
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

function creaParametro() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var validaForm = $("#formCreaParametro").validationEngine('validate');
	        var urlCreaParametro = "${pageContext.request.contextPath}" + "/creaParametro";
	        var urlCargaParametros = "${pageContext.request.contextPath}" + "/cargaParametros";	       
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Crea par&aacute;metro",
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
	                    if (result == true) {
	                        var parametroDTO = {};			        
	                        parametroDTO["nombreParametro"] = $("#creaNombreParametro").val();
	                        parametroDTO["valorParametroChar"] = $("#creaValorStringParametro").val();
	                        parametroDTO["valorParametroNumerico"] = $("#creaValorNumParametro").val();	   			      			       
	                        $.ajax( {
	                            url: urlCreaParametro,
	                            type: 'POST',
	                            data: JSON.stringify(parametroDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorParametrosCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#mantenedorParametrosCuerpo").find(".cargando").remove();			
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                if (returnData.responseJSON.respuesta=="OK") {
	                                    $.notify({
	                                        message: "Registro creado"
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
	                                $("#mantenedorParametrosCuerpo").load(urlCargaParametros,
	                                        function() {
	                                            $("#mantenedorParametrosCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#creaParametroModal').modal('hide'); 	            					
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
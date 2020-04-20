<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaParametro">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="idParametroLabel" for="idParametro">ID par&aacute;metro: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="idParametro" 
  			     value="${parametroDTO.idParametro}">
  		</div>  		
  	</div>
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="nombreParametroLabel" for="nombreParametro">Nombre par&aacute;metro: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="nombreParametro" 
  			     value="${parametroDTO.nombreParametro}">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="valorStringParametroLabel" for="valorStringParametro">Valor texto par&aacute;metro: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="valorStringParametro" 
  			     value='<c:out value="${parametroDTO.valorParametroChar}"/>'>  
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="valorNumParametroLabel" for="valorNumParametro">Valor Numerico par&aacute;metro: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="valorNumParametro" 
  			     value="${parametroDTO.valorParametroNumerico}">
  		</div>  		
  	</div>
  	<div class="col-sm-3"></div>
  	<div class="col-sm-9"> 
		<button id="botonActualizarRegistroDeListaDeDistribucion" onclick="actualizarParametro(${parametroDTO.idParametro})"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Actualizar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 
	</div>    		     
</form>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function actualizarParametro(idParametroAnterior) {	
	$.get("${sessionURL}", function(haySession) {
		if(haySession) {
			var validaForm = $("#formEditaParametro").validationEngine('validate');
			var urlActualizaParametro = "${pageContext.request.contextPath}" + "/actualizaParametro";
		    var urlCargaParametros = "${pageContext.request.contextPath}" + "/cargaParametros";
		    if (validaForm == true) {
		    	bootbox.confirm({
	                title: "Actualizar par&aacute;metro",
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
	                        var parametroDTO = {};
	                        parametroDTO["idParametro"] = $("#idParametro").val();			        
	                        parametroDTO["nombreParametro"] = $("#nombreParametro").val();
	                        parametroDTO["valorParametroChar"] = $("#valorStringParametro").val();
	                        parametroDTO["valorParametroNumerico"] = $("#valorNumParametro").val();	
	                        parametroDTO["idParametroAnterior"] = idParametroAnterior;	   			      			       
	                        $.ajax( {
	                            url: urlActualizaParametro,
	                            type: 'POST',
	                            data: JSON.stringify(parametroDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorParametrosCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
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
	                                $("#mantenedorParametrosCuerpo").load(urlCargaParametros,
	                                        function() {
	                                            $("#mantenedorParametrosCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#editaParametroModal').modal('hide'); 	            					
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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal" id="formCreaTipoSubtareaBitacora">

 	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaNombreTipoSubtareaBitacoraLabel" for="creaNombreTipoSubtareaBitacora">Nombre: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control" id="creaNombreTipoSubtareaBitacora">
  		</div>  		
  	</div>	

  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonCreaTipoSubtareaBitacora" onclick="creaTipoSubtareaBitacora()"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Guardar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 								
	</div>    		     
</form>		

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

// var inicializaSelect2CreaUnidadO = function(){
// 	$(".select2-crea-unidad-o").select2({
// 	    theme: "bootstrap",
// 	    dropdownParent: $("#formCreaUnidad"),
// 	    language: "es"
// 	});
// };

// var inicializaSelect2CreaCargo = function(){
// 	$(".select2-crea-cargo").select2({
// 	    theme: "bootstrap",
// 	    dropdownParent: $("#formCreaUsuario"),
// 	    language: "es"
// 	});
// };

$(document).ready(function() {
	//$(inicializaSelect2CreaUnidadO);
// 	$(inicializaSelect2CreaCargo);
});

function creaTipoSubtareaBitacora() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var validaForm = $("#formCreaTipoSubtareaBitacora").validationEngine('validate');
	        var urlCreaTipoSubtareaBitacora = "${pageContext.request.contextPath}" + "/creaTipoSubtareaBitacora";
	        
	        var urlMantenedorTiposSubtareaBitacoraCuerpo = "${pageContext.request.contextPath}" + "/mantenedorTiposSubtareaBitacoraCuerpo";	       
	        
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Crea tipo de subtarea de bit&aacute;cora",
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
	                        var tipoSubtareaBitacoraDTO = {};	

	                        tipoSubtareaBitacoraDTO["nombre"] = $("#creaNombreTipoSubtareaBitacora").val().trim();

	                        $.ajax( {
	                            url: urlCreaTipoSubtareaBitacora,
	                            type: 'POST',
	                            data: JSON.stringify(tipoSubtareaBitacoraDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorSubtareasBitacoraCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#mantenedorSubtareasBitacoraCuerpo").find(".cargando").remove();			
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                if (returnData.responseJSON.estado=="OK") {
	                                    $.notify({
	                                        message: "Registro creado"
	                                    },{
	                                        type: 'success'
	                                    });
	                                } else {
	                                    $.notify({
	                                        message: returnData.responseJSON.glosa
	                                    },{
	                                        type: 'danger'
	                                    });
	                                }                			

	                                $("#mantenedorSubtareasBitacoraCuerpo").load(urlMantenedorTiposSubtareaBitacoraCuerpo,
	                                        function() {

	                                            $("#mantenedorSubtareasBitacoraCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#creaTipoSubtareaBitacoraModal').modal('hide'); 	            					
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
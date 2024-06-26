<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaTipoSubtareaBitacora">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaIdTipoSubtareaBitacoraLabel" for="editaIdTipoSubtareaBitacoraInput">ID: </label>  	
  		<div class="col-sm-9"> 		
  			<input type="text" class="form-control validate[required]" id="editaIdTipoSubtareaBitacoraInput" disabled value="${TipoActividadBitacoraDTO.id}">
  		</div>  		   		
  	</div>  	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaNombreTipoSubtareaBitacoraLabel" for="editaNombreTipoSubtareaBitacoraInput">Nombre: </label>  	
  		<div class="col-sm-9"> 		
  			<input type="text" class="form-control validate[required]" id="editaNombreTipoSubtareaBitacoraInput"  value="${TipoActividadBitacoraDTO.nombre}">
  		</div>  		   		
  	</div> 
  	

  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonEditaUnidadOp" onclick="editaTipoSubtareaBitacora(${TipoActividadBitacoraDTO.id})"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Guardar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 								
	</div> 	 	     
</form>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

// var inicializaSelect2EditaUnidad = function(){
// 	$(".select2-edita-unidad-o").select2({
// 	    theme: "bootstrap",
// 	    dropdownParent: $("#formEditaUnidad"),
// 	    language: "es"
// 	});
// };

// var inicializaSelect2CEditaCargo = function(){
// 	$(".select2-edita-cargo").select2({
// 	    theme: "bootstrap",
// 	    dropdownParent: $("#formEditaUsuario"),
// 	    language: "es"
// 	});
// };

$(document).ready(function() {
	//$(inicializaSelect2EditaUnidad);
	//$(inicializaSelect2CEditaCargo);
});

$('.check_switch').change(function(){
    if($(this).attr('checked')){
         $(this).val('true');
    }else{
         $(this).val('false');
    }
});

function editaTipoSubtareaBitacora(idUnidadOperativa) {	
	$.get("${sessionURL}", function(haySession) {
		if(haySession) {
			var validaForm = $("#formEditaTipoSubtareaBitacora").validationEngine('validate');
			
		   var urlEditaTipoSubtareaBitacora = "${pageContext.request.contextPath}" + "/editarTipoSubtareaBitacora";
	        
	        var urlMantenedorTiposSubtareaBitacoraCuerpo = "${pageContext.request.contextPath}" + "/mantenedorTiposSubtareaBitacoraCuerpo";
		    
		    if (validaForm == true) {
		    	bootbox.confirm({
	                title: "Actualizar tipo de subtarea",
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
	                    	var tipoDTO = {};		
	                    	tipoDTO["id"] = idUnidadOperativa;
	                    	tipoDTO["nombre"] = $("#editaNombreTipoSubtareaBitacoraInput").val().trim();
        
	                        $.ajax( {
	                            url: urlEditaTipoSubtareaBitacora,
	                            type: 'POST',
	                            data: JSON.stringify(tipoDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorSubtareasBitacoraCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
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
	                                        message: "Registro actualizado"
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
	                                $('#editaTipoSubtareaBitacoraModal').modal('hide'); 	            					
	                            }                		
	                        });
	                    } 
	                }
	            });
		    }
		} else {
			 bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu �ltima acci�n y hemos caducado tu sesi�n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                     , function(){
                           window.open('${raizURL}', '_blank');
                     }
        	);
		}
	});

}

</script>
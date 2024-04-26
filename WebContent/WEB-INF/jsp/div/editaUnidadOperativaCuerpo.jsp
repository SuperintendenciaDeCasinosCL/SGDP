<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaUnidadOperativa">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaIdUsuarioLabel" for="editaIdUnidadOp">ID Unidad operativa: </label>  	
  		<div class="col-sm-9"> 		
  			<input type="text" class="form-control validate[required]" id="editaIdUnidadOp" disabled value="${unidadOperativaDTO.idUnidadOperativa}">
  		</div>  		   		
  	</div>  	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaCodUnidadLabel" for="editaCodUnidadOp">Codigo unidad operativa: </label>  	
  		<div class="col-sm-9"> 		
  			<input type="text" class="form-control validate[required]" id="editaCodUnidadOp"  value="${unidadOperativaDTO.codigoUnidadOperativa}">
  		</div>  		   		
  	</div> 
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaNombreCompletoLabel" for="editaNombreUnidadOp">Nombre de unidad operativa: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="editaNombreUnidadOp" value="${unidadOperativaDTO.nombreUnidadOperativa}">
  		</div>  		
  	</div>
<!--   	<div class="form-group">  		 -->
<!--   		<label class="control-label col-sm-3" id="editaUnidadOLabel" for="editaUnidadO">Unidad operativa(*): </label> -->
<!--   		<div class="col-sm-9">  			 -->
<!--   			<select class="form-control select2-edita-unidad-o"  -->
<!-- 				name="editaUnidadO" -->
<!-- 				id="editaUnidadO">				 -->
<%-- 				<c:forEach items="${todasLasUnidadesOperativasDTO}" var="unidadDTOOperativa">					 --%>
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${unidadDTO.idUnidadOperativa eq unidadDTOOperativa.idUnidadOperativa}">			 --%>
<%-- 							<option value="${unidadDTOOperativa.idUnidadOperativa}" selected="selected">${unidadDTOOperativa.nombreUnidadOperativa}</option>					 --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise>		 --%>
<%-- 							<option value="${unidadDTOOperativa.idUnidadOperativa}">${unidadDTOOperativa.nombreUnidadOperativa}</option>				 --%>
<%-- 						</c:otherwise>	 --%>
<%-- 					</c:choose>					 --%>
<%-- 				</c:forEach> --%>
<!-- 			</select> -->
<!--   		</div>  		 -->
<!--   	</div>  	 -->
  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonEditaUnidadOp" onclick="editaUnidadOperativa(${unidadOperativaDTO.idUnidadOperativa})"						       									       								       			
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

function editaUnidadOperativa(idUnidadOperativa) {	
	$.get("${sessionURL}", function(haySession) {
		if(haySession) {
			var validaForm = $("#formEditaUnidadOperativa").validationEngine('validate');
			var urlActualizaUnidadOp = "${pageContext.request.contextPath}" + "/actualizaUnidadOperativa";
		    var urlMantenedorUnidadesCuerpoOp = "${pageContext.request.contextPath}" + "/mantenedorUnidadesOperativasCuerpo";
		    if (validaForm == true) {
		    	bootbox.confirm({
	                title: "Actualizar unidad operativa",
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
	                    	var unidadOperativaDTO = {};		
	                    	unidadOperativaDTO["idUnidadOperativa"] = idUnidadOperativa;
	                    	unidadOperativaDTO["codigoUnidadOperativa"] = $("#editaCodUnidadOp").val().trim();
	                    	unidadOperativaDTO["nombreUnidadOperativa"] = $("#editaNombreUnidadOp").val();
 	                        
	                      
	                        $.ajax( {
	                            url: urlActualizaUnidadOp,
	                            type: 'POST',
	                            data: JSON.stringify(unidadOperativaDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorUnidadesOperativasCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#mantenedorUnidadesOperativasCuerpo").find(".cargando").remove();			
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
	                                $("#mantenedorUnidadesOperativasCuerpo").load(urlMantenedorUnidadesCuerpoOp,
	                                        function() {
	                                            $("#mantenedorUnidadesOperativasCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#editaUnidadOperativaModal').modal('hide'); 	            					
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
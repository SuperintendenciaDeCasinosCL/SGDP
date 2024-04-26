<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal" id="formCreaUnidadOperativa">


	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaCodigoCompletoLabel" for="creaCodigoUnidadOp">Codigo Unidad Operativa: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control" id="creaCodigoUnidadOp">
  		</div>  		
  	</div>	
  	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaNombreCompletoLabel" for="creaNombreUnidadOp">Nombre Unidad Operativa: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control" id="creaNombreUnidadOp">
  		</div>  		
  	</div>	

<!--   	<div class="form-group">  		 -->
<!--   		<label class="control-label col-sm-3" id="creaUnidadOpearitaLabel" for="creaUnidadOperativa">Unidad Operativa(*): </label> -->
<!--   		<div class="col-sm-9">  			 -->
<!--   			<select class="form-control select2-crea-unidad-o" name="creaUnidadOperativa" -->
<!-- 				id="creaUnidadOperativa"> -->
<!-- 				<option value="">Seleccione Unidad Operativa</option> -->
<%-- 				<c:forEach items="${todasLasUnidadesOperativasDTO}" var="unidadOpeDTO"> --%>
<%-- 					<option value="${unidadOpeDTO.idUnidadOperativa}">${unidadOpeDTO.nombreUnidadOperativa}</option> --%>
<%-- 				</c:forEach> --%>
<!-- 			</select> -->
<!--   		</div>  		 -->
<!--   	</div>	  	 -->

  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonCreaUnidadOperativa" onclick="creaUnidadOperativa()"						       									       								       			
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

function creaUnidadOperativa() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var validaForm = $("#formCreaUnidadOperativa").validationEngine('validate');
	        var urlCreaUnidadOperativa = "${pageContext.request.contextPath}" + "/creaUnidadOperativa";
	        var urlMantenedorUnidadesOperativasCuerpo = "${pageContext.request.contextPath}" + "/mantenedorUnidadesOperativasCuerpo";	       
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Crea unidad operativa",
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
	                        var unidadOpertaivaDTO = {};			        
	                        unidadOpertaivaDTO["codigoUnidadOperativa"] = $("#creaCodigoUnidadOp").val().trim();
	                        unidadOpertaivaDTO["nombreUnidadOperativa"] = $("#creaNombreUnidadOp").val();
 	                        //unidadDTO["idUnidadOperativa"] = $("#creaUnidadOperativa").val();	   
// 	                        usuarioRolDTO["nombreCompleto"] = $("#creaNombreCompletoR").val();
// 	                        usuarioRolDTO["rut"] = $("#creaRUT").val();
	                        $.ajax( {
	                            url: urlCreaUnidadOperativa,
	                            type: 'POST',
	                            data: JSON.stringify(unidadOpertaivaDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorUnidadesOperativasCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
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
	                                $("#mantenedorUnidadesOperativasCuerpo").load(urlMantenedorUnidadesOperativasCuerpo,
	                                        function() {
	                                            $("#mantenedorUnidadesOperativasCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#creaUnidadOperativaModal').modal('hide'); 	            					
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
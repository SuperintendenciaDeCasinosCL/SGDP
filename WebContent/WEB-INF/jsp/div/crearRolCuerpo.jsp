<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal" id="formCreaRol">

  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaNombreCompletoLabel" for="creaNombreRol">Nombre del Cargo: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]"  id="creaNombreRol">
  		</div>  		
  	</div>	
<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaUnidadOpearitaLabel" for="creaUnidadOperativa">Unidad Operativa(*): </label>
  		<div id="divUnidadOperativa" class="col-sm-9">  			
  			<select class="form-control select2-crea-unidad-o validate[required]" name="creaUnidadOperativa"
				id="creaUnidadOperativa">
				<option value="">Seleccione Unidad Operativa</option>
				<c:forEach items="${todasLasUnidadesOperativasDTO}" var="unidadOpeDTO">
					<option value="${unidadOpeDTO.idUnidadOperativa}">${unidadOpeDTO.nombreUnidadOperativa}</option>
				</c:forEach>
			</select>
  		</div>  		
  	</div>	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaUnidadLabel" for="creaUnidad">Unidad (*): </label>
  		<div id="divUnidades" class="col-sm-9">  			
  			<select class="form-control select2-unidad validate[required]" name="creaUnidad"
				id="creaUnidad">
				<option value="">Seleccione Unidad</option>
			</select>
  		</div>  		
  	</div>	  	

  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonCreaRol" onclick="creaRol()"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Guardar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 								
	</div>    		     
</form>		

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

// var inicializaSelect2CreaUnidad = function(){
// 	$(".select2-crea-unidad").select2({
// 	    theme: "bootstrap",
// 	    dropdownParent: $("#formCreaRol"),
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
// 	$(inicializaSelect2CreaUnidad);
 	$(inicializaSelect2UnidadOpe);
});

function creaRol() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var validaForm = $("#formCreaRol").validationEngine('validate');
	        var urlCreaRol = "${pageContext.request.contextPath}" + "/creaRol";
	        var urlMantenedorRolesCuerpo = "${pageContext.request.contextPath}" + "/mantenedorRolesCuerpo";	       
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Crea cargo",
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
	                        var rolDTO = {};			        
	                        rolDTO["nombreRol"] = $("#creaNombreRol").val().trim();
	                        rolDTO["idUnidad"] = $("#creaUnidad").val();
// 	                        usuarioRolDTO["idRol"] = $("#creaRol").val();	   
// 	                        usuarioRolDTO["nombreCompleto"] = $("#creaNombreCompletoR").val();
// 	                        usuarioRolDTO["rut"] = $("#creaRUT").val();
	                        $.ajax( {
	                            url: urlCreaRol,
	                            type: 'POST',
	                            data: JSON.stringify(rolDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorRolesCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#mantenedorRolesCuerpo").find(".cargando").remove();			
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
	                                $("#mantenedorRolesCuerpo").load(urlMantenedorRolesCuerpo,
	                                        function() {
	                                            $("#mantenedorRolesCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#creaRolModal').modal('hide'); 	            					
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


var inicializaSelect2UnidadOpe = function() {
	$(".select2-crea-unidad-o").select2({
		theme : "bootstrap",
		dropdownParent : $("#divUnidadOperativa"),
		language : "es"
	});
};


$('.select2-crea-unidad-o').on('change', function() {
	//$("#detalleDeExpedienteEnDistribucionModal").css("position", "relative").prepend($("<div />").addClass("cargando"));
	  var idUnidadOperativa = this.value;	  
	   console.log("idUnidadOperativa: "+ idUnidadOperativa)
	   
	   if (idUnidadOperativa === ""){
		   console.log("idUnidadOperativa Vacia");
		   idUnidadOperativa = 0;
	   }
	   
	  $("#divUnidades").load("${pageContext.request.contextPath}"+"/getUnidadesPorUnidadOperativa/"+ idUnidadOperativa);//, function() {
	//	$("#detalleDeExpedienteEnDistribucionModal").find(".cargando").remove();
		//});	 
	  
});

</script> 
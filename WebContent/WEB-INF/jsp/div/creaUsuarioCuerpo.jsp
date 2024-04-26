<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal" id="formCreaUsuario">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaIdUsuarioLabel" for="creaIdUsuario">ID Usuario (*): </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="creaIdUsuario">
  		</div>  		
  	</div>
  	<!--<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaActivoParametroLabel" for="creaActivoParametro">Activo: </label>
  		<div class="col-sm-9">  			
  			<input type="checkbox" id="creaActivoParametro">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaFueraDeOficinaLabel" for="creaFueraDeOficina">Fuera de oficina: </label>
  		<div class="col-sm-9">  			
  			<input type="checkbox" id="creaFueraDeOficina">
  		</div>  		
  	</div>-->
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaNombreCompletoLabel" for="creaNombreCompletoR">Nombre completo: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control" id="creaNombreCompletoR">
  		</div>  		
  	</div>	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaRUTLabel" for="creaRUT">RUT: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control" name="creaRUT" id="creaRUT" placeholder="11111111-1" maxlength="10" />
  		</div>  		
  	</div>  	
  	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaUnidadOpearitaLabel" for="creaUnidadOperativa">Unidad Operativa(*): </label>
  		<div id="divUnidadOperativaUsu" class="col-sm-9">  			
  			<select class="form-control select2-crea-unidad-operativa validate[required]" " name="creaUnidadOperativa"
				id="creaUnidadOperativa">
				<option value="">Seleccione Unidad Operativa</option>
				<c:forEach items="${todasLasUnidadesOperativasDTO}" var="unidadOpeDTO">
					<option value="${unidadOpeDTO.idUnidadOperativa}">${unidadOpeDTO.nombreUnidadOperativa}</option>
				</c:forEach>
			</select>
  		</div>  		
  	</div>
  	
  <div id="divUnidadCargo">
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaUnidadLabel" for="creaUnidad">Unidad (*): </label>
  		<div id="divUnidadesUsu" class="col-sm-9">  			
  			<select class="form-control select2-unidad validate[required]" name="creaUnidad"
				id="creaUnidad">
				<option value="">Seleccione Unidad</option>
			</select>
  		</div>  		
  	</div>	  

  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaCargoLabel" for="creaCargo">Cargo (*): </label>
  		<div class="col-sm-9" id="divCargos">  			
			<select class="form-control select2-crea-cargo-a validate[required]" name="creaRol"
				id="creaRol">
				<option value="">Seleccione Cargo</option>
			</select>
  		</div>  		
  	</div>	 
  </div>	
  	
  	 					
  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonCreaUsuario" onclick="creaUsuario()"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Guardar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 								
	</div>    		     
</form>		

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>



$(document).ready(function() {
	$(inicializaSelect2UnidadOperativa);
});

function creaUsuario() {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	        var validaForm = $("#formCreaUsuario").validationEngine('validate');
	        var urlCreaUsuario = "${pageContext.request.contextPath}" + "/creaUsuario";
	        var urlMantenedorUsuariosCuerpo = "${pageContext.request.contextPath}" + "/mantenedorUsuariosCuerpo";	       
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Crea usuario",
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
	                        var usuarioRolDTO = {};			        
	                        usuarioRolDTO["idUsuario"] = $("#creaIdUsuario").val().trim();
	                        usuarioRolDTO["idUnidad"] = $("#creaUnidad").val();
	                        usuarioRolDTO["idRol"] = $("#creaRol").val();	   
	                        usuarioRolDTO["nombreCompleto"] = $("#creaNombreCompletoR").val();
	                        usuarioRolDTO["rut"] = $("#creaRUT").val();
	                        $.ajax( {
	                            url: urlCreaUsuario,
	                            type: 'POST',
	                            data: JSON.stringify(usuarioRolDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorUsuariosCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#mantenedorUsuariosCuerpo").find(".cargando").remove();			
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
	                                $("#mantenedorUsuariosCuerpo").load(urlMantenedorUsuariosCuerpo,
	                                        function() {
	                                            $("#mantenedorUsuariosCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#creaUsuarioModal').modal('hide'); 	            					
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

var inicializaSelect2UnidadOperativa = function() {
	$(".select2-crea-unidad-operativa").select2({
		theme : "bootstrap",
		dropdownParent : $("#divUnidadOperativaUsu"),
		language : "es"
	});
};


$('.select2-crea-unidad-operativa').on('change', function() {	
	  var idUnidadOperativa = this.value;	  
	   console.log("idUnidadOperativa: "+ idUnidadOperativa);

	   if (idUnidadOperativa === ""){
		   console.log("idUnidadOperativa Vacia");
		   idUnidadOperativa = 0;
	   }
		   
	   $("#divUnidadCargo").load("${pageContext.request.contextPath}"+"/getUnidadesPorUnidadOperativaUsuario/"+ idUnidadOperativa);
	 
		
});

</script> 
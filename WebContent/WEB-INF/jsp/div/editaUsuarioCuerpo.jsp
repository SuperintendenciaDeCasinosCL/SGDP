<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaUsuario">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaIdUsuarioLabel" for="editaIdUsuario">ID Usuario: </label>  	
  		<div class="col-sm-9"> 		
  			<input type="text" class="form-control validate[required]" id="editaIdUsuario" disabled value="${usuarioRolDTO.idUsuario}">
  		</div>  		   		
  	</div>  	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaNombreCompletoLabel" for="editaNombreCompletoR">Nombre completo: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control" id="editaNombreCompletoR" value="${usuarioRolDTO.nombreCompleto}">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaRUTLabel" for="editaRUT">RUT: </label>
  		<div class="col-sm-9">
  			<c:if test="${not empty usuarioRolDTO.rut}">
  				<input type="text" class="form-control" name="editaRUT" id="editaRUT" value="${usuarioRolDTO.rut}-${usuarioRolDTO.dv}" placeholder="11111111-1" maxlength="10"/>
  			</c:if>  			
  			<c:if test="${empty usuarioRolDTO.rut}">
  				<input type="text" class="form-control" name="editaRUT" id="editaRUT" placeholder="11111111-1" maxlength="10"/>
  			</c:if>
  		</div>  		
  	</div> 
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaActivoLabel" for="editaActivo">Activo: </label>
  		<div class="col-sm-9">  			
  			<label class="switch">
  				<c:choose>							
					<c:when test="${usuarioRolDTO.activo eq true}">
						<input id="editaActivo" class="check_switch" type="checkbox" value="${usuarioRolDTO.activo}" checked>
			  			<span class="slider round"></span>
					</c:when>								
					<c:otherwise>
			          <input id="editaActivo" class="check_switch" type="checkbox" value="${usuarioRolDTO.activo}">
			  			<span class="slider round"></span>
			       </c:otherwise>							
				</c:choose>
			</label>  			
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaFueraDeOficinaLabel" for="editaFueraDeOficina">Fuera de oficina: </label>
  		<div class="col-sm-9">  			
  			<label class="switch">
  				<c:choose>							
					<c:when test="${usuarioRolDTO.fueraDeOficina eq true}">
						<input id="editaFueraDeOficina" class="check_switch" type="checkbox" value="${usuarioRolDTO.fueraDeOficina}" checked>
			  			<span class="slider round"></span>
					</c:when>								
					<c:otherwise>
			          	<input id="editaFueraDeOficina" class="check_switch" type="checkbox" value="${usuarioRolDTO.fueraDeOficina}">
			  			<span class="slider round"></span>
			       </c:otherwise>							
				</c:choose>
			</label>  			
  		</div>  		
  	</div> 
		<div class="form-group">  		
	  		<label class="control-label col-sm-3" id="creaUnidadOpearitaLabel" for="creaUnidadOperativaEdita">Unidad Operativa(*): </label>
	  		<div id="divUnidadOperativaUsuEdita" class="col-sm-9">  			
	  			<select class="form-control select2-crea-unidad-operativa-edita validate[required]" " name="creaUnidadOperativaEdita"
					id="creaUnidadOperativaEdita">					
					<c:forEach items="${todasLasUnidadesOperativasDTO}" var="unidadOpeDTO">					
					<c:choose>
						<c:when test="${unidadDTORol.idUnidadOperativa eq unidadOpeDTO.idUnidadOperativa}">			
							<option value="${ unidadOpeDTO.idUnidadOperativa}" selected="selected">${unidadOpeDTO.nombreUnidadOperativa}</option>					
						</c:when>
						<c:otherwise>		
							<option value="${ unidadOpeDTO.idUnidadOperativa}">${unidadOpeDTO.nombreUnidadOperativa}</option>				
						</c:otherwise>	
					</c:choose>					
				</c:forEach>
				
				</select>
	  		</div>  		
  		</div>

		<div id="divUnidadEditaCargo">
		  	<div class="form-group">  		
		  		<label class="control-label col-sm-3" id="creaUnidadLabel" for="editaUnidad">Unidad (*): </label>
		  		<div id="divUnidadesUsuEdita" class="col-sm-9">  			
		  			<select class="form-control select2-unidad-edita validate[required]" name="editaUnidad"
						id="editaUnidad">
							<c:forEach items="${unidadesDTOporOpertaiva}" var="unidadDTO">					 
								<c:choose>
									<c:when test="${rolDTO.idUnidad eq unidadDTO.idUnidad}">			
										<option value="${unidadDTO.idUnidad}" selected="selected">${unidadDTO.nombreCompletoUnidad}</option>					
									</c:when>
									<c:otherwise>		
										<option value="${unidadDTO.idUnidad}">${unidadDTO.nombreCompletoUnidad}</option>				
									</c:otherwise>	
								</c:choose>					
							</c:forEach>
					</select>
		  		</div>  		
		  	</div>	  
	
		  	<div class="form-group">  		
		  		<label class="control-label col-sm-3" id="creaCargoLabel" for="editaCargoForm">Cargo (*): </label>
		  		<div class="col-sm-9" id="divCargosEdita">  			
					<select class="form-control select2-crea-cargo-edita validate[required]" name="editaCargo"
						id="editaCargo">
						<c:forEach items="${rolesDTOUnidad}" var="rolDTO">					 
							<c:choose>
								<c:when test="${usuarioRolDTO.idRol eq rolDTO.idRol}">			
									<option value="${rolDTO.idRol}" selected="selected">${rolDTO.nombreRol}</option>					
								</c:when>
								<c:otherwise>		
									<option value="${rolDTO.idRol}">${rolDTO.nombreRol}</option>				
								</c:otherwise>	
							</c:choose>						
						</c:forEach>
					</select>
		  		</div>  		
		  	</div>	 
  	</div>	  

  	<div class="col-sm-3">						  	
  	</div>
  	<div class="col-sm-9"> 
		<button id="botonCreaUsuario" onclick="editaUsuario(${usuarioRolDTO.idUsuarioRol})"						       									       								       			
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
 	$(inicializaSelect2UnidadUsu);
	$(inicializaSelect2UnidadOperativaEdita);
});

$('.check_switch').change(function(){
    if($(this).attr('checked')){
         $(this).val('true');
    }else{
         $(this).val('false');
    }
});

function editaUsuario(idUsuarioRol) {	
	$.get("${sessionURL}", function(haySession) {
		if(haySession) {
			var validaForm = $("#formEditaUsuario").validationEngine('validate');
			var urlActualizaUsuario = "${pageContext.request.contextPath}" + "/actualizaUsuario";
		    var urlMantenedorUsuariosCuerpo = "${pageContext.request.contextPath}" + "/mantenedorUsuariosCuerpo";
		    if (validaForm == true) {
		    	bootbox.confirm({
	                title: "Actualizar usuario",
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
	                    	var usuarioRolDTO = {};		
	                    	usuarioRolDTO["idUsuarioRol"] = idUsuarioRol;
	                   		usuarioRolDTO["idUsuario"] = $("#editaIdUsuario").val().trim();
	                        usuarioRolDTO["idUnidad"] = $("#editaUnidad").val();
	                        usuarioRolDTO["idRol"] = $("#editaCargo").val();	   
	                        usuarioRolDTO["nombreCompleto"] = $("#editaNombreCompletoR").val();
	                        usuarioRolDTO["rut"] = $("#editaRUT").val();
	                        usuarioRolDTO["activo"] = $('#editaActivo').is(':checked');
	                        usuarioRolDTO["fueraDeOficina"] = $('#editaFueraDeOficina').is(':checked');;
	                        $.ajax( {
	                            url: urlActualizaUsuario,
	                            type: 'POST',
	                            data: JSON.stringify(usuarioRolDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorUsuariosCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
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
	                                $("#mantenedorUsuariosCuerpo").load(urlMantenedorUsuariosCuerpo,
	                                        function() {
	                                            $("#mantenedorUsuariosCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#editaUsuarioModal').modal('hide'); 	            					
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

var inicializaSelect2UnidadOperativaEdita = function() {
	$(".select2-crea-unidad-operativa-edita").select2({
		theme : "bootstrap",
		dropdownParent : $("#divUnidadOperativaUsuEdita"),
		language : "es"
	});
};


$('.select2-crea-unidad-operativa-edita').on('change', function() {	
	  var idUnidadOperativa = this.value;	  
	   console.log("idUnidadOperativa: "+ idUnidadOperativa);		   
	   $("#divUnidadEditaCargo").load("${pageContext.request.contextPath}"+"/getUnidadesPorUnidadOperativaUsuarioEdita/"+ idUnidadOperativa);
	
});


var inicializaSelect2UnidadUsu = function() {
	$(".select2-unidad-edita").select2({
		theme : "bootstrap",
		dropdownParent : $("#divUnidadesUsuEdita"),
		language : "es"
	});
};


$('.select2-unidad-edita').on('change', function() {	
	  var idUnidad = this.value;	  
	   console.log("idUnidadOperativa: "+ idUnidad)
	  $("#divCargosEdita").load("${pageContext.request.contextPath}"+"/getRolesPorUnidadEdita/"+ idUnidad);//, function() {
	  
});


</script>
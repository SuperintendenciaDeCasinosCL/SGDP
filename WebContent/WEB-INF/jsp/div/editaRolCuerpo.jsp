<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="form-horizontal" id="formEditaRol">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaIdUsuarioLabel" for="editaIdUsuario">ID Cargo: </label>  	
  		<div class="col-sm-9"> 		
  			<input type="text" class="form-control validate[required]" id="editaIdUsuario" disabled value="${rolDTO.idRol}">
  		</div>  		   		
  	</div>  	
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaNombreCompletoLabel" for="editaNombreCargo">Nombre del cargo: </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="editaNombreCargo" value="${rolDTO.nombreRol}">
  		</div>  		
  	</div>

<div class="form-group">  		
  		<label class="control-label col-sm-3" id="creaUnidadOpearitaLabel" for="creaUnidadOperativa">Unidad Operativa(*): </label>
  		<div id="divUnidadOperativaEdita" class="col-sm-9">  			
  			<select class="form-control select2-edita-unidad-o validate[required]" name="creaUnidadOperativa"
				id="creaUnidadOperativa">
	
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
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="editaUnidadLabel" for="editaUnidad">Unidad (*): </label>
  		<div id="divUnidadesEdita" class="col-sm-9">  			
  			<select class="form-control select2-unidad validate[required]" name="idUnidadEdita"
				id="idUnidadEdita">
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
  	 <br>
  	  <div class="col-sm-12">
  	  	<h3 style="text-align: center;">Permisos</h3>
  	  </div>
	 <br> 	
	 <div class="col-sm-4">
		<select name="libres[]" class="form-control" id="libresCargos" multiple="multiple" size="15">
			<c:forEach items="${rolDTO.permisosDTO}" var="permisoDTO">
				<c:if test="${not permisoDTO.activo}">
					<option title="${permisoDTO.nombrePermiso}" value="${permisoDTO.nombrePermiso}">${permisoDTO.nombrePermiso}</option>
				</c:if>
			</c:forEach>
		</select>
	 </div>
	 <div class="col-sm-4 centrar-botones" >
	    <br><br><br>
		<input type="button" class="pasar izq btn btn-primary" onclick="pasarC()" value="Pasar »">
		<input type="button" class="quitar der btn btn-primary" onclick="quitarC()" value="« Quitar">
		<br><br>
		<input type="button" class="pasartodos izq btn btn-primary" onclick="pasartodosC()" value="Todos »">
		<input type="button" class="quitartodos der btn btn-primary" onclick="quitartodosC()" value="« Todos">
	</div>
	<div class="col-sm-4">
		<select name="asignados[]" class="form-control" id="asignadosCargos" multiple="multiple" size="15">
			<c:forEach items="${rolDTO.permisosDTO}" var="permisoDTO">
				<c:if test="${permisoDTO.activo}">
					<option title="${permisoDTO.nombrePermiso}" value="${permisoDTO.nombrePermiso}">${permisoDTO.nombrePermiso}</option>
				</c:if>
			</c:forEach>
		</select>
	</div>

  	<div class="col-sm-12 centrar-botones ">
		<br></br>
		<button id="botonEditaRol" onclick="editaRol(${rolDTO.idRol})"						       									       								       			
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
	$(inicializaSelect2UnidadEditaOpe);
});

$('.check_switch').change(function(){
    if($(this).attr('checked')){
         $(this).val('true');
    }else{
         $(this).val('false');
    }
});

function pasartodosC(){
	  $('#libresCargos option').each(function() { $(this).remove().appendTo('#asignadosCargos'); });
}	  

function quitartodosC(){
	  $('#asignadosCargos option').each(function() { $(this).remove().appendTo('#libresCargos'); });
}

function pasarC(){
	   return !$('#libresCargos option:selected').remove().appendTo('#asignadosCargos');
}

function quitarC(){
	  return !$('#asignadosCargos option:selected').remove().appendTo('#libresCargos'); 
}

function editaRol(idRol) {	
	$.get("${sessionURL}", function(haySession) {
		if(haySession) {
			var validaForm = $("#formEditaRol").validationEngine('validate');
			var urlActualizaRol = "${pageContext.request.contextPath}" + "/actualizaRol";
		    var urlMantenedorRolesCuerpo = "${pageContext.request.contextPath}" + "/mantenedorRolesCuerpo";
		    if (validaForm == true) {
		    	bootbox.confirm({
	                title: "Actualizar cargo",
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
	                    	var rolDTO = {};
	                    	rolDTO["idRol"] = idRol;    
	                        rolDTO["nombreRol"] = $("#editaNombreCargo").val();
	                        rolDTO["idUnidad"] = $("#idUnidadEdita").val();
	                        var permisosDTO = new Array();
	        		        $("#asignadosCargos option").each(function (colIndex, c) {
	        		        	permisosDTO.push({    		
	        		        		nombrePermiso : $(this).val() 
	        					            }); 
	        				});   	
	        		        rolDTO["permisosDTO"] = permisosDTO;
	                        $.ajax( {
	                            url: urlActualizaRol,
	                            type: 'POST',
	                            data: JSON.stringify(rolDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorRolesCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
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
	                                $("#mantenedorRolesCuerpo").load(urlMantenedorRolesCuerpo,
	                                        function() {
	                                            $("#mantenedorRolesCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#editaRolModal').modal('hide'); 	            					
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




var inicializaSelect2UnidadEditaOpe = function() {
	$(".select2-edita-unidad-o").select2({
		theme : "bootstrap",
		dropdownParent : $("#divUnidadOperativaEdita"),
		language : "es"
	});
};


$('.select2-edita-unidad-o').on('change', function() {	
	  var idUnidadOperativa = this.value;	  
	   console.log("idUnidadOperativa: "+ idUnidadOperativa)
	  $("#divUnidadesEdita").load("${pageContext.request.contextPath}"+"/getUnidadesPorUnidadOperativaEdita/"+ idUnidadOperativa);//, function() {
		  
});


</script>
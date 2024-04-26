<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />


<form class="form-horizontal" id="formEditaRegistroListaDeDistribucion">
	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="nombreCompletoLabel" for="nombreCompleto">Nombre (*): </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="nombreCompleto" 
  			     value="${registroListaDeDistribucionDTO.nombreCompleto}">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="organizacionLabel" for="organizacion">Organizaci&oacute;n (*): </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="organizacion" 
  			     value="${registroListaDeDistribucionDTO.organizacion}">
  		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="cargoLabel" for="cargo">Cargo (*): </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="cargo" 
  			     value="${registroListaDeDistribucionDTO.cargo}">
  		</div>  		
  	</div>
	<div class="form-group">  		
		<label class="control-label col-sm-3" id="emailLabel" for="email">Email (*): </label>
		<div class="col-sm-9">  			
			<input type="text" class="form-control validate[required, custom[email]]" id="email" 
			     value="${registroListaDeDistribucionDTO.email}">
		</div>  		
  	</div>
  	<div class="form-group">
    	<label class="control-label col-sm-3" for="fechaInicioVigenciaE">Fecha Inicio Vigencia (*):</label>
    	<div class="col-sm-9">
    		 <div class="input-group date fecha-desde-hasta-destinatario-modal-e">
				<input type="text" class="form-control validate[required]" 
						id="fechaInicioVigenciaE" value='<fmt:formatDate pattern="${FORMATO_FECHA}" value="${registroListaDeDistribucionDTO.fechaInicioVigencia}" />'' placeholder="Ej: 12/08/2023">
					<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
					</span>
			</div>
    	</div>
    </div>
    <div class="form-group">
    	<label class="control-label col-sm-3" for="fechaFinVigenciaE">Fecha Fin Vigencia (*):</label>
    	<div class="col-sm-9">
    		 <div class="input-group date fecha-desde-hasta-destinatario-modal-e">
				<input type="text" class="form-control validate[required]" 
						id="fechaFinVigenciaE" value='<fmt:formatDate pattern="${FORMATO_FECHA}" value="${registroListaDeDistribucionDTO.fechaFinVigencia}" />' placeholder="Ej: 12/08/2023">
					<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
					</span>
			</div>
    	</div>
    </div>
    <div class="form-group">  		
		<label class="control-label col-sm-3" id="numeroTelefono1Label" for="numeroTelefono1E">N&uacute;mero T&eacute;lefono 1 : </label>
		<div class="col-sm-9">  			
			<input type="text" value="${registroListaDeDistribucionDTO.numeroTelefono1}" class="form-control validate[custom[onlyNumberSp]]" id="numeroTelefono1E">
		</div>  		
  	</div>
  	<div class="form-group">  		
		<label class="control-label col-sm-3" id="numeroTelefono2Label" for="numeroTelefono2E">N&uacute;mero T&eacute;lefono 2 : </label>
		<div class="col-sm-9">  			
			<input type="text" value="${registroListaDeDistribucionDTO.numeroTelefono2}" class="form-control validate[custom[onlyNumberSp]]" id="numeroTelefono2E">
		</div>  		
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="motivoListaDistribucionLabel" for="motivoListaDistribucion">Motivo (*): </label>
  		<div class="col-sm-9">  			
  			<input type="text" class="form-control validate[required]" id="motivoListaDistribucion" >
  		</div>  		
  	</div>	
  	<div class="form-group">  		
		<label class="control-label col-sm-3" id="destinatarioLabel" for="tipoDestinatario">Tipo de Destinatario (*): </label>
		<div class="col-sm-9">  			
			<select class="form-control validate[required]" id="tipoDestinatario" name="tipoDestinatario">
		       	<c:if test="${registroListaDeDistribucionDTO.idTipoDestinatario eq 0}">
		       		<option value="">Seleccione Destinatario</option>
		       	</c:if>		       	
			    <c:forEach items="${listaTipoDeDestinatarioDTO}" var="tipoDeDestinatarioDTO">			    
			    	<c:choose>
						<c:when test="${tipoDeDestinatarioDTO.idTipoDestinatario eq registroListaDeDistribucionDTO.idTipoDestinatario}">
							<option selected="selected" value="${tipoDeDestinatarioDTO.idTipoDestinatario}">${tipoDeDestinatarioDTO.nombreTipoDestinatario}</option>
						</c:when>
						<c:otherwise>
							<option value="${tipoDeDestinatarioDTO.idTipoDestinatario}">${tipoDeDestinatarioDTO.nombreTipoDestinatario}</option>
						</c:otherwise>
					</c:choose>
			   </c:forEach>	
			</select>	
		</div>  		
  	</div>  	
  	<div class="col-sm-3"></div>
  	<div class="col-sm-9"> 
		<button id="botonActualizarRegistroDeListaDeDistribucion" onclick="actualizarRegistroDeListaDeDistribucion(${registroListaDeDistribucionDTO.idListaDeDistribucion})"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary btn-lg btn-block">
					Actualizar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 
	</div>    		     
</form>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

var inicializaFechasDesdeHastaDestinatarioE = function() {
	$('.fecha-desde-hasta-destinatario-modal-e').datetimepicker({
		locale : 'es',
		format : 'DD/MM/YYYY'
	});
};

function actualizarRegistroDeListaDeDistribucion(idListaDeDistribucion) {	
	$.get("${sessionURL}", function(haySession) {	    
	    if(haySession) {
	        var validaForm = $("#formEditaRegistroListaDeDistribucion").validationEngine('validate');
	        var urlActualizaRegistroDeListaDeDistribucion = "${pageContext.request.contextPath}" + "/actualizaRegistroDeListaDeDistribucion";
	        var urlCargaListaDeDistribucion = $("#urlCargaListaDeDistribucion").val();	       
	        if (validaForm == true) {
	            bootbox.confirm({
	                title: "Actualizar registro",
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
	                    console.log('El usuario selecciono: ' + result);
	                    if (result == true) {
	                        var registroListaDeDistribucionDTO = {};
	                        registroListaDeDistribucionDTO["idListaDeDistribucion"] = idListaDeDistribucion;
	                        registroListaDeDistribucionDTO["nombreCompleto"] = $("#nombreCompleto").val();
	                        registroListaDeDistribucionDTO["email"] = $("#email").val();
	                        registroListaDeDistribucionDTO["organizacion"] = $("#organizacion").val();
	                        registroListaDeDistribucionDTO["cargo"] = $("#cargo").val();
	                        registroListaDeDistribucionDTO["idTipoDestinatario"] = $("#tipoDestinatario").val();
	                        registroListaDeDistribucionDTO["motivo"] = $("#motivoListaDistribucion").val();
	                        registroListaDeDistribucionDTO["fechaInicioVigenciaS"] = $("#fechaInicioVigenciaE").val();
	                        registroListaDeDistribucionDTO["fechaFinVigenciaS"] = $("#fechaFinVigenciaE").val();
	                        registroListaDeDistribucionDTO["numeroTelefono1"] = $("#numeroTelefono1E").val();
	                        registroListaDeDistribucionDTO["numeroTelefono2"] = $("#numeroTelefono2E").val();
	                        console.log(JSON.stringify(registroListaDeDistribucionDTO));			      			       
	                        $.ajax( {
	                            url: urlActualizaRegistroDeListaDeDistribucion,
	                            type: 'POST',
	                            data: JSON.stringify(registroListaDeDistribucionDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#listaDeDistribucionCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- actualiza registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#listaDeDistribucionCuerpo").find(".cargando").remove();			
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                console.log("COMPLETE -- actualiza registro: ", returnData);
	                                if (returnData.responseJSON.respuesta=="OK") {
	                                    $.notify({
	                                        message: "Registro actualizado"
	                                    },{
	                                        type: 'success'
	                                    });
	                                } else {
	                                    $.notify({
	                                    	message: returnData.responseJSON.respuesta
	                                    },{
	                                        type: 'danger'
	                                    });
	                                }                			
	                                $("#listaDeDistribucionCuerpo").load(urlCargaListaDeDistribucion,
	                                        function() {
	                                            $("#listaDeDistribucionCuerpo").find(".cargando").remove();                			                       
	                                });	
	                                $('#editaRegistroListaDeDistribucionModal').modal('hide'); 	            					
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

$(document).ready(function() {
	$(inicializaFechasDesdeHastaDestinatarioE);
});

</script>
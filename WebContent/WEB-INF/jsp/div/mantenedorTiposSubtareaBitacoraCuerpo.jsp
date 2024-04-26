<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container">

	<div class="row">
	
		<div class="col-sm-4">
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModaCreaTipoSubtareaBitacoral()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaMantenedorUnidadesOp"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre del tipo de subtarea</th>										
						<th>Editar / Eliminar</th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${TiposSubtareaBitacora}" var="TiposSubtareaBitacora">
						<tr>
							<td>${TiposSubtareaBitacora.id}</td>							
							
							<td>${TiposSubtareaBitacora.nombre}</td>
							
							<td>
								<p data-placement="top" data-toggle="tooltip" title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaTipoSubtareaBitacora" data-toggle="modal"										
										onclick="abreModalEditaTipoSubtareaBitacora(${TiposSubtareaBitacora.id})">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
									
									<button 
										class="btn btn-danger btn-sm" 									
										onclick="eliminarActividadMensaje(${TiposSubtareaBitacora.id})">
											<span class="glyphicon glyphicon-remove"></span>
									</button>
								</p>
							</td>												
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
		</div>
		
	
	</div>
	
	
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function eliminarActividadMensaje (id){
	bootbox.confirm({
		message: "¿Est&aacute; seguro que desea Eliminar esta actividad?",
	    buttons: {
	    	confirm: {
	    		label: 'Si',
	    	    className: 'btn-success'
	    	},
	    	cancel: {
	    		label: 'No',
	    	    className: 'btn-danger'
	    	}
	   	},
	    callback: function (result) {
	   		if (result == true){
	 	    	eliminaActividad(id);
	 	    }
	 	} 
	 });
}

function eliminaActividad(id) {

	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        
		
			$.ajax({
				type : "DELETE",
				contentType : "application/json",
				url : '/sgdp/eliminaTipoSubTareaBitacora/' + id,		
				timeout : 100000,
				success : function(returnData) {
					console.log("SUCCESS -- : ", returnData);				
				},
				error : function(e) {
					console.log("ERROR: ", e);			
				},
				done : function(e) {
					console.log("DONE");
				},
				complete : function(returnData) {		
					console.log("COMPLETE -- : ", returnData);	
					window.location.reload();
				}
			}); 
			
		} else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
        });
	
}


function abreModaCreaTipoSubtareaBitacoral() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        	
        	$('#creaTipoSubtareaBitacoraModal').modal( {backdrop: 'static', keyboard: false});
        	var urlNuevoTipoSubTareaBitacora = "${pageContext.request.contextPath}" + "/nuevoTipoSubTareaBitacora";        	
        	$("#creaTipoSubtareaBitacoraDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#creaTipoSubtareaBitacoraDiv").load(urlNuevoTipoSubTareaBitacora, function() {
        		$('#formCreaUnidadOperativa').trigger("reset");
        		$("#creaTipoSubtareaBitacoraDiv").find(".cargando").remove();
        	});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}


function abreModalEditaTipoSubtareaBitacora(id) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaTipoSubtareaBitacoraModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditarTipoSubtareaBitacora = "${pageContext.request.contextPath}" + "/editaTipoSubtareaBitacora/" + id;        	
        	$("#editaTipoSubtareaBitacoraDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaTipoSubtareaBitacoraDiv").load(urlEditarTipoSubtareaBitacora, function() {$("#editaTipoSubtareaBitacoraDiv").find(".cargando").remove();});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}


function formatTablaMantenedorUnidadesOp() {
 	console.log("Mantenedor Unidades")
	var tablaMantenedorUnidadesOp = $('#tablaMantenedorUnidadesOp')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'UnidadesOperativas.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 10
				
			});

 	tablaMantenedorUnidadesOp.buttons().container().appendTo(
	'#tablaMantenedorUnidadesOp_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaMantenedorUnidadesOp();	
})

</script>
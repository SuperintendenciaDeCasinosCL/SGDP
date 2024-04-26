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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalNuevoRol()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaMantenedorRoles"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Cargo</th>
						<th>Unidad</th>						
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${rolDTO}" var="rolDTO">
						<tr>
							<td>${rolDTO.idRol}</td>							
							
							<td>${rolDTO.nombreRol}</td>
							
							<td>${rolDTO.nombreUnidad}</td>
							
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaRol" data-toggle="modal"										
										onclick="abreModalEditaRol(${rolDTO.idRol})">
										<span class="glyphicon glyphicon-pencil"></span>
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

function abreModalNuevoRol() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        	
        	$('#creaRolModal').modal( {backdrop: 'static', keyboard: false});
        	var urlNuevoRol = "${pageContext.request.contextPath}" + "/nuevoRol";        	
        	$("#creaRolDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#creaRolDiv").load(urlNuevoRol, function() {
        		$('#formCreaRol').trigger("reset");
        		$("#creaRolDiv").find(".cargando").remove();
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


function abreModalEditaRol(idRol) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaRolModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaRol = "${pageContext.request.contextPath}" + "/editaRol/" + idRol;        	
        	$("#editaRolDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaRolDiv").load(urlEditaRol, function() {$("#editaRolDiv").find(".cargando").remove();});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}


function formatTablaMantenedorRoles() {
 	console.log("Mantenedor Roles")
	var tablaMantenedorRoles = $('#tablaMantenedorRoles')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'Cargos.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 10
				
			});

	tablaMantenedorRoles.buttons().container().appendTo(
	'#tablaMantenedorRoles_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaMantenedorRoles();	
})

</script>
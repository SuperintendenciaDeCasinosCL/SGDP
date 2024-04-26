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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalNuevoUsuario()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaMantenedorUsuarios"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Unidad</th>	
						<th>Cargo</th>
						<th>Fuera de oficina</th>
						<th>Activo</th>					
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${usuariosRolDTO}" var="usuarioRolDTO">
						<tr>
							<td>${usuarioRolDTO.idUsuario}</td>							
							
							<td>${usuarioRolDTO.nombreUnidad}</td>
							<td>${usuarioRolDTO.nombreRol}</td>
							
							<td>
								<c:choose>							
									<c:when test="${usuarioRolDTO.fueraDeOficina eq true}">
										SI
									</c:when>								
									<c:otherwise>
							          	NO
							       </c:otherwise>							
								</c:choose>
							</td>
							<td>
								<c:choose>							
									<c:when test="${usuarioRolDTO.activo eq true}">
										<span class="glyphicon glyphicon-ok-circle font-icon-2 text-success"></span>
									</c:when>								
									<c:otherwise>
							          <span class="glyphicon glyphicon-ban-circle font-icon-2 text-danger"></span>
							       </c:otherwise>							
								</c:choose>
							</td>
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaUsuario" data-toggle="modal"										
										onclick="abreModalEditaUsuario(${usuarioRolDTO.idUsuarioRol})">
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

function abreModalNuevoUsuario() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        	
        	$('#creaUsuarioModal').modal( {backdrop: 'static', keyboard: false});
        	var urlNuevoUsuario = "${pageContext.request.contextPath}" + "/nuevoUsuario";        	
        	$("#creaUsuarioDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#creaUsuarioDiv").load(urlNuevoUsuario, function() {
        		$('#formCreaUsuario').trigger("reset");
        		$("#creaUsuarioDiv").find(".cargando").remove();
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

function abreModalEditaUsuario(idUsuarioRol) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaUsuarioModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaUsuario = "${pageContext.request.contextPath}" + "/editaUsuario/" + idUsuarioRol;        	
        	$("#editaUsuarioDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaUsuarioDiv").load(urlEditaUsuario, function() {$("#editaUsuarioDiv").find(".cargando").remove();});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}

function formatTablaMantenedorUsuarios() {

	var tablaMantenedorUsuarios = $('#tablaMantenedorUsuarios')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'Usuarios.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 10
				
			});

	tablaMantenedorUsuarios.buttons().container().appendTo(
	'#tablaMantenedorUsuarios_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaMantenedorUsuarios();	
})

</script>
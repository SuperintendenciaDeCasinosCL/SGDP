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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalNuevaUnidad()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaMantenedorUnidades"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Codigo unidad</th>
						<th>Nombre de unidad</th>	
						<th>Unidad Operativa</th>					
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${unidadDTO}" var="unidadDTO">
						<tr>
							<td>${unidadDTO.idUnidad}</td>							
							
							<td>${unidadDTO.codigoUnidad}</td>
							
							<td>${unidadDTO.nombreCompletoUnidad}</td>
							
							<td>${unidadDTO.nombreUnidadOperativa}</td>
							
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaUnidad" data-toggle="modal"										
										onclick="abreModalEditaUnidad(${unidadDTO.idUnidad})">
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

function abreModalNuevaUnidad() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        	
        	$('#creaUnidadModal').modal( {backdrop: 'static', keyboard: false});
        	var urlNuevoUnidad = "${pageContext.request.contextPath}" + "/nuevaUnidad";        	
        	$("#creaUnidadDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#creaUnidadDiv").load(urlNuevoUnidad, function() {
        		$('#formCreaUnidad').trigger("reset");
        		$("#creaUnidadDiv").find(".cargando").remove();
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


function abreModalEditaUnidad(idUnidad) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaUnidadModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaUnidad = "${pageContext.request.contextPath}" + "/editaUnidad/" + idUnidad;        	
        	$("#editaUnidadDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaUnidadDiv").load(urlEditaUnidad, function() {$("#editaUnidadDiv").find(".cargando").remove();});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}


function formatTablaMantenedorUnidades() {
 	console.log("Mantenedor Unidades")
	var tablaMantenedorUnidades = $('#tablaMantenedorUnidades')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'Unidades.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 10
				
			});

 	tablaMantenedorUnidades.buttons().container().appendTo(
	'#tablaMantenedorUnidades_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaMantenedorUnidades();	
})

</script>
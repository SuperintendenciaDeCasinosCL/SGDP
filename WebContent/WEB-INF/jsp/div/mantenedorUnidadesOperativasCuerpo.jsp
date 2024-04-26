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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalNuevaUnidadOp()">Nuevo</button>
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
						<th>Codigo unidad operativa</th>
						<th>Nombre de unidad operativa</th>										
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${unidadOperativaDTO}" var="unidadOperativaDTO">
						<tr>
							<td>${unidadOperativaDTO.idUnidadOperativa}</td>							
							
							<td>${unidadOperativaDTO.codigoUnidadOperativa}</td>
							
							<td>${unidadOperativaDTO.nombreUnidadOperativa}</td>							
							
							
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaUnidadOp" data-toggle="modal"										
										onclick="abreModalEditaUnidadOp(${unidadOperativaDTO.idUnidadOperativa})">
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

function abreModalNuevaUnidadOp() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        	
        	$('#creaUnidadOperativaModal').modal( {backdrop: 'static', keyboard: false});
        	var urlNuevoUnidadOperativa = "${pageContext.request.contextPath}" + "/nuevaUnidadOperativa";        	
        	$("#creaUnidadOperativaDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#creaUnidadOperativaDiv").load(urlNuevoUnidadOperativa, function() {
        		$('#formCreaUnidadOperativa').trigger("reset");
        		$("#creaUnidadOperativaDiv").find(".cargando").remove();
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


function abreModalEditaUnidadOp(idUnidadOperativa) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaUnidadOperativaModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaUnidadOp = "${pageContext.request.contextPath}" + "/editaUnidadOperativa/" + idUnidadOperativa;        	
        	$("#editaUnidadOperativaDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaUnidadOperativaDiv").load(urlEditaUnidadOp, function() {$("#editaUnidadOperativaDiv").find(".cargando").remove();});
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
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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="creaTipoDestinatario()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaTiposDestinatario"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre Tipo De Destinatario</th>				
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${todosTipoDeDestinatarioDTO}" var="tipoDeDestinatarioDTO">
						<tr>
							<td>${tipoDeDestinatarioDTO.idTipoDestinatario}</td>							
							<td>${tipoDeDestinatarioDTO.nombreTipoDestinatario}</td>							
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"																			
										onclick="actualizaTipoDestinatario(${tipoDeDestinatarioDTO.idTipoDestinatario})">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
								</p>
							</td>
							<td><p data-placement="top" data-toggle="tooltip"
										title="Borrar">
										<button class="btn btn-danger btn-sm"
											data-title="borrarTipoDestinatario"
											onclick="borrarTipoDestinatario(${tipoDeDestinatarioDTO.idTipoDestinatario})">
											<span class="glyphicon glyphicon-trash"></span>
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

<script>

function formatTablaTiposDestinatario() {

	var tablaTiposDestinatario = $('#tablaTiposDestinatario')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'Parametros.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 10
				
			});

	tablaTiposDestinatario.buttons().container().appendTo(
	'#tablaTiposDestinatario_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaTiposDestinatario();	
})

</script>
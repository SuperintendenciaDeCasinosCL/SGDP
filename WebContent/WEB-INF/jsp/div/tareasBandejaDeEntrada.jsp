<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />
                  
	<div class="table-responsive">	
		<table id="tablaTareas" class="table table-striped table-bordered table-hover table-fixed">											
		    <thead>
		        <tr>
		        	<th>Expediente</th>
		        	<th>Subproceso</th>
		        	<th>Tarea</th>
		            <th>Asunto</th>			
					<th>Plazo</th>
					<th>De</th>
					<th>Estado</th>
					<th>Autor</th>
					<th>Observaci&oacute;n</th>
		    	</tr>
			</thead>
			
			<tbody>
				<c:forEach var="instanciaDeTareaDTO" items="${instanciasDeTareasDTO}" varStatus="status">
					
					<tr>
							<td class="view-message">${instanciaDeTareaDTO.nombreExpediente}</td>
							
							<td class="view-message">								
								<a class="btn btn-link btn-wrap-break" id="${instanciaDeTareaDTO.idInstanciaDeTarea}LinkProceso" onclick='cargaDetalleDeTarea("${instanciaDeTareaDTO.nombreExpediente}","${instanciaDeTareaDTO.idInstanciaDeTarea}", "true", "${instanciaDeTareaDTO.idExpediente}", "${instanciaDeTareaDTO.urlControl}")'>${instanciaDeTareaDTO.nombreDeProceso}</a>
							</td>
							
							<td class="view-message">
								<a type="button" class="btn btn-link btn-wrap-break" id="${instanciaDeTareaDTO.idInstanciaDeTarea}LinkTarea"
									onclick='cargaDetalleDeTarea("${instanciaDeTareaDTO.nombreExpediente}","${instanciaDeTareaDTO.idInstanciaDeTarea}", "true", "${instanciaDeTareaDTO.idExpediente}", "${instanciaDeTareaDTO.urlControl}")'>
									${instanciaDeTareaDTO.nombreDeTarea}
								</a>
							</td>
							
							<td class="view-message tamano-asunto">${instanciaDeTareaDTO.asunto}</td>
							
							<c:choose>
							    <c:when test="${instanciaDeTareaDTO.adviertePlazo eq true}">
							       	<td class="view-message btn-warning">
							       		<c:choose>
							       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
							       				<span style="display: none;" >
									    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" pattern="yyyy-MM-dd" /> 
									    		</span>
							       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" />
							       			</c:when>
							       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
							       				<span style="display: none;" >
									    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" pattern="yyyy-MM-dd" /> 
									    		</span>
							       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
							       			</c:when>
							       		</c:choose>							       						       								       		
							       	</td>
							    </c:when>
							    <c:when test="${instanciaDeTareaDTO.fueraDePlazo eq true}">
							        <td class="view-message btn-danger">
							        	<c:choose>
							       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
							       				<span style="display: none;" >
									    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" pattern="yyyy-MM-dd" /> 
									    		</span>
							       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" />
							       			</c:when>
							       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
							       				<span style="display: none;" >
									    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" pattern="yyyy-MM-dd" /> 
									    		</span>
							       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
							       			</c:when>
							       		</c:choose>		
							        </td>
							    </c:when>
							    <c:otherwise>
							        <td class="view-message">
							        	<c:choose>
							       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
							       				<span style="display: none;" >
									    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" pattern="yyyy-MM-dd" /> 
									    		</span>
							       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" />
							       			</c:when>
							       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
							       				<span style="display: none;" >
									    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" pattern="yyyy-MM-dd" /> 
									    		</span>
							       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
							       			</c:when>
							       		</c:choose>		
							        </td>
							    </c:otherwise>
							</c:choose>
							<td class="view-message">${instanciaDeTareaDTO.idUsuarioQueAsigna}</td>
							<td class="view-message">${instanciaDeTareaDTO.nombreEstadoHomologadoDeInstProceso}</td>
							<td class="view-message">${instanciaDeTareaDTO.emisor}</td>
							<td class="view-message details-control cursorPuntero" data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}">
								<i id="plus${instanciaDeTareaDTO.idInstanciaDeTarea}" class="glyphicon glyphicon-plus"></i>
							</td>								
					</tr>
									
				</c:forEach>															  
			 </tbody>
		</table>
	</div>
	
<script>

function formatTablaTareas() {
	
	$('#tablaTareas').DataTable().destroy();
	var tablaTareas = $('#tablaTareas')
	.DataTable(
			{	
				"autoWidth": false,
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'Tareas.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : languajeDataTableTareas,
				"pageLength": 5,
				"order": [[ 5, "desc" ]],
				
				"columnDefs": [
  		             {
  		                 "targets": [ 7, 8 ],
  		                 "visible": false				                       		                
  		             }
  		         ]
				
			});

	tablaTareas.buttons().container().appendTo(
	'#tablaTareas_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaTareas();	
	$(function() {

		var tablaTareas = $('#tablaTareas').DataTable();
		
		// Add event listener for opening and closing details
	    $('#tablaTareas tbody').on('click', 'td.details-control', function () {  

	    	idInstanciaDeTarea = $(this).attr("data-idinstanciadetarea");
		    var urlGetHistorialDeTareasPorIdIntanciaDeTarea = $("#urlGetHistorialDeTareasPorIdIntanciaDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTarea;
		    
			console.log("urlGetHistorialDeTareasPorIdIntanciaDeTarea: "+ urlGetHistorialDeTareasPorIdIntanciaDeTarea);

			var tr = $(this).closest('tr');
	        var row = tablaTareas.row( tr );

	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            /*row.child.hide();
	            tr.removeClass('shown');*/

	            $('div.sliderTBE', row.child()).slideUp( function () {
	                row.child.hide();
	                tr.removeClass('shown');
	            } );
	            
	            $("#plus"+idInstanciaDeTarea).addClass('glyphicon-plus');
	            $("#plus"+idInstanciaDeTarea).removeClass('glyphicon-minus');
	        }
	        else {
	            // Open this row
	            
	            $.ajax({
					type : "GET",
					contentType : "application/json",
					url : urlGetHistorialDeTareasPorIdIntanciaDeTarea,		
					timeout : 100000,
					success : function(returnData) {
						console.log("SUCCESS -- getHistorialDeTareasPorIdIntanciaDeTarea: ", returnData);				
					},
					error : function(e) {
						console.log("ERROR: ", e);			
					},
					done : function(e) {
						console.log("DONE");
					},
					complete : function(returnData) {		
	
						console.log("COMPLETE -- getHistorialDeTareasPorIdIntanciaDeTarea: ", returnData);	

						var dataRowTable;

						dataRowTable = '<div class="sliderTBE"><table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
						
						if (returnData.responseJSON.length > 0) {
						
				            $.each(returnData.responseJSON, function(i, obj){		                
				                dataRowTable = dataRowTable + '<tr>'+
					            '<td>Comentario:</td>'+
					            '<td>'+obj.comentario+'</td>'+
						        '</tr>';
				            });
							
						} else {
							dataRowTable = dataRowTable + '<tr>'+
				            '<td>No hay comentarios para esta tarea</td>'+				            
					        '</tr>';
						}						

						dataRowTable = dataRowTable + '</table></div>';
						
			            console.log("dataRowTable: " + dataRowTable);
			            
			            /*row.child( dataRowTable ).show();
			            tr.addClass('shown');*/

			            row.child( dataRowTable, 'no-padding' ).show();
			            tr.addClass('shown');
			            $('div.sliderTBE', row.child()).slideDown();
			            
			            $("#plus"+idInstanciaDeTarea).removeClass('glyphicon-plus');
			            $("#plus"+idInstanciaDeTarea).addClass('glyphicon-minus');
						
					}
				});
	            
	        }
	        
	    } );
	    
	});
});

</script>
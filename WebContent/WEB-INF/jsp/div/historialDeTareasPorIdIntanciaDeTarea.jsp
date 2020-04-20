<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />

<div class="table-responsive">
				  		
	<table id="tablaHistorialDeTareasPorIdInstanciaDeTarea" class="table table-striped table-bordered">											
	    <thead>
	        <tr>	         
	            <th>Fecha Movimiento</th>
	            <th>Usuario</th>
	            <th>Comentario</th>
	            <th>Acci&acute;n</th>	 
	            <th>Nombre Tarea Origen</th> 
	            <th>Nombre Tarea Destino</th>       
	            <%-- <th>Usuarios Destino</th>--%>    					       
	        </tr>
	    </thead>
	    <tbody>
	    
    		<c:forEach var="historicoDeInstDeTareaDTO" items="${historicosDeInstDeTareaDTO}">
    	
	    		<tr>    		
		    		<td class="view-message">
		    			<fmt:formatDate pattern="${FORMATO_FECHA}" value="${historicoDeInstDeTareaDTO.fechaMovimiento}" />
		    		</td>	
		    		<td class="view-message">
		    			${historicoDeInstDeTareaDTO.idUsuarioOrigen}
		    		</td>	
		    		<td class="view-message">
		    			${historicoDeInstDeTareaDTO.comentario}
		    		</td>	
		    		<td class="view-message">
		    			${historicoDeInstDeTareaDTO.nombreAccion}
		    		</td>
		    		<td class="view-message">
		    			${historicoDeInstDeTareaDTO.nombreTareaDeOrigen}
		    		</td>
		    		<td class="view-message">
		    			${historicoDeInstDeTareaDTO.nombreTareaDeDestino}
		    		</td>
		    		<%--<td class="view-message">
		    			<a href="#" class="btn btn-info btn-md" 
		    			onclick="cargarUsuariosDestinosModal('${historicoDeInstDeTareaDTO.usuariosDestinoString}', 
		    												'${historicoDeInstDeTareaDTO.nombreTareaDeOrigen}',
		    												'${historicoDeInstDeTareaDTO.nombreTareaDeDestino}')">
							<span class="glyphicon glyphicon-user"></span> Usuarios
						</a>
		    		</td>--%>
		    	</tr>
	    	
	    	</c:forEach>
	    
	    </tbody>
	</table>
	
</div>

<script>

function formatTablaHistorialDeTareasPorIdInstanciaDeTarea() {

	var tablaHistorialDeTareasPorIdInstanciaDeTarea = $('#tablaHistorialDeTareasPorIdInstanciaDeTarea')
	.DataTable(
			{
				buttons : [ /*{
					extend : 'copyHtml5',
					exportOptions : {
						columns : [ 0, ':visible' ]
					}
				},*/ {
					extend : 'excelHtml5',
					filename : 'HistoriaDeTareas.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : languajeDataTableTareas,
				"pageLength": 3,
				bFilter: false,
				bLengthChange: false,
				bInfo : false,
				sDom: '<"top"flp>rt<"bottom"i><"clear">',
				
       		    columnDefs: [
       		             {
       		                 "targets": [ 2 ],
       		                 "visible": false				                       		                
       		             }
       		         ],	
			});

	tablaHistorialDeTareasPorIdInstanciaDeTarea.buttons().container().appendTo(
	'#tablaHistorialDeTareasPorIdInstanciaDeTarea_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaHistorialDeTareasPorIdInstanciaDeTarea();		 
});

</script>
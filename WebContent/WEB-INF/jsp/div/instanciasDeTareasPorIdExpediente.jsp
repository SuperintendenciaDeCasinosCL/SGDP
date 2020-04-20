<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />

<div class="table-responsive">
				  		
	<table id="tablaInstanciasDeTareasPorIdExpediente" class="table table-striped table-bordered">											
	    <thead>
	        <tr>
	            <th>Tarea</th>
	            <th>Fecha Vencimiento</th>
	            <th>&Uacute;ltimo Comentario</th>	          					       
	        </tr>
	    </thead>
	    <tbody>
	    
	    	<c:forEach var="instanciaDeTareaDTO" items="${instanciasDeTareasDTOPorIdExpediente}">
	    	
	    		<tr class="cursorPuntero" data-idinstanciadetarea=${instanciaDeTareaDTO.idInstanciaDeTarea} 
	    									data-nombretarea="${instanciaDeTareaDTO.nombreDeTarea}" >   		
	    		
		    		<td class="view-message">${instanciaDeTareaDTO.nombreDeTarea} (${instanciaDeTareaDTO.idInstanciaDeTarea})</td>	    		    		
		    		
		    		<c:choose>
					    <c:when test="${instanciaDeTareaDTO.adviertePlazo eq true}">
					       	<td class="view-message btn-warning">
					       		<c:choose>
					       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
					       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" />
					       			</c:when>
					       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
					       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
					       			</c:when>
					       		</c:choose>							       						       								       		
					       	</td>
					    </c:when>
					    <c:when test="${instanciaDeTareaDTO.fueraDePlazo eq true}">
					        <td class="view-message btn-danger">
					        	<c:choose>
					       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
					       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" />
					       			</c:when>
					       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
					       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
					       			</c:when>
					       		</c:choose>		
					        </td>
					    </c:when>
					    <c:otherwise>
					        <td class="view-message">
					        	<c:choose>
					       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
					       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" />
					       			</c:when>
					       			<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
					       				<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
					       			</c:when>
					       		</c:choose>		
					        </td>
					    </c:otherwise>
					</c:choose>	
					<td class="view-message">${instanciaDeTareaDTO.ultimoComentario}</td>
	    		</tr>
	    	</c:forEach>   
	    
	    </tbody>
	</table>
 	
 	
</div>

<script>

function formatTablaInstanciasDeTareasPorIdExpediente() {

	var tablaInstanciasDeTareasPorIdExpediente = $('#tablaInstanciasDeTareasPorIdExpediente')
	.DataTable(
			{
				buttons : [/* {
					extend : 'copyHtml5',
					exportOptions : {
						columns : [ 0, ':visible' ]
					}
				},*/ {
					extend : 'excelHtml5',
					filename : 'TareasPorExpediente.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : languajeDataTableTareas,
				"pageLength": 3,
				bFilter: false,
				bLengthChange: false,
				bInfo : false,
				sDom: '<"top"flp>rt<"bottom"i><"clear">'	
			});

	tablaInstanciasDeTareasPorIdExpediente.buttons().container().appendTo(
	'#tablaInstanciasDeTareasPorIdExpediente_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaInstanciasDeTareasPorIdExpediente();	

	 $('#tablaInstanciasDeTareasPorIdExpediente tbody').on( 'click', 'tr', function () {
		 var idInstanciaDeTarea = $(this).attr("data-idinstanciadetarea");
		 var nombreDeTarea = $(this).attr("data-nombretarea");
		 console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
		 console.log("nombreDeTarea: " + nombreDeTarea);
		 cargaVistaHistorialDeTareasPorIdIntanciaDeTarea(idInstanciaDeTarea, nombreDeTarea);
	    } 
	  );
	
});

</script>
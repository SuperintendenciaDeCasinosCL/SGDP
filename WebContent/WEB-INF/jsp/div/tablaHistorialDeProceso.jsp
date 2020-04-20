<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM_SS" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM_SS%>" />

<br>
<fieldset class="col-md-12">

<legend>Historial de Tareas</legend>

<br>

<div class="table-responsive">
				  		
 		<table id="tablaHistorialDelProceso" class="table table-striped table-bordered">											
	    <thead>
	        <tr>
	        	<th>Id</th>
	        	<th>Fecha</th>
	            <th>Tarea</th>
	            <th>Usuario</th>
	            <th>Comentario</th>	            	 
	            <th>Acci&oacute;n</th>        						       
	        </tr>
	    </thead>
	    <tbody>
	    
	    	<c:forEach var="hpDTO" items="${historialProcesoDTO}">
	    	
	    		<tr>
			    	<td class="view-message">${hpDTO.idInstanciaDeTarea}</td>
			    	<td class="view-message">
			    		<fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM_SS}" value="${hpDTO.fechaMovimiento}" />
			    	</td>
			    	<td class="view-message">${hpDTO.nombreDeTarea}</td>
			    	<td class="view-message">${hpDTO.idDeUsuario}</td>
			    	<td class="view-message">${hpDTO.comentario}</td>			    	
			    	<td class="view-message">${hpDTO.accion}</td>
			    </tr>
	    	
	    	</c:forEach>
	    
	    </tbody>
	</table>
 	
 	
</div>

</fieldset>

<script>

function formatTablaHistorialDelProceso() {

	var tablaHistorialDelProceso = $('#tablaHistorialDelProceso')
	.DataTable(
			{
				buttons : [/* {
					extend : 'copyHtml5',
					exportOptions : {
						columns : [ 0, ':visible' ]
					}
				},*/ {
					extend : 'excelHtml5',
					filename : 'HistorialProceso.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : languajeDataTableHistorialDelProceso,
				"pageLength": 4,
				"order": [[ 4, "desc" ]]
				
			});
	
	tablaHistorialDelProceso.buttons().container().appendTo(
	'#tablaHistorialDelProceso_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaHistorialDelProceso();
});

</script>
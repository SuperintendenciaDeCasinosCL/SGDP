<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ page import= "cl.gob.scj.sgdp.tipos.RequisitoType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<c:set var="requisitoStyle" value="tarea-rds" />

<div class="table-responsive col-sm-12">				  		
 		hola
</div>

<script>

function formatTablaHistorialDeCondicionesDeSatisfaccion() {
	
	var tablaHistorialDeCondicionesDeSatisfaccion = $('#tablaHistorialDeCondicionesDeSatisfaccion')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'HistorialRDSSNC.*',
					exportOptions : {
						columns : ':visible'
					}
				}],
				"language" : lenguaje_es,
				"pageLength": 5,
				"order": [[ 3, "desc" ], [5, "desc"], [0, "asc"]]
				
			});
	
	tablaHistorialDeCondicionesDeSatisfaccion.buttons().container().appendTo(
	'#tablaHistorialDeCondicionesDeSatisfaccion_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaHistorialDeCondicionesDeSatisfaccion();		
});

</script>
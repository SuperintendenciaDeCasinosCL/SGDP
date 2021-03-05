<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ page import= "cl.gob.scj.sgdp.tipos.RequisitoType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<c:set var="requisitoStyle" value="tarea-rds" />

<div class="table-responsive col-sm-12">				  		
 		<table id="tablaDeRDSSNC" class="table table-striped table-bordered">											
		    <thead>
		        <tr>	        		            	 
		            <th>RDS/SNC</th>
		            <th>Nombre</th>		             						       
		        </tr>
		    </thead>
		    <tbody>
			    <c:forEach var="parametroDeTareaDTO" items="${parametrosDeTareaDTO}"> 
			    		
			    		<c:choose>
			    		
				    		<c:when test="${parametroDeTareaDTO.esSNC eq true}">
				    			
				    			<c:set var="requisitoStyle" value="tarea-snc" />
				    		
				    		</c:when>				    
			    		
			    		</c:choose>
			    		
			    		<tr class="${requisitoStyle}">		    		
			    			<td>${parametroDeTareaDTO.nombreDeTipoDeRequisito}</td>	
			    			<td>${parametroDeTareaDTO.nombreParamTarea}</td>		    				    		
			    		</tr>
			    				    		
			    		<c:set var="requisitoStyle" value="tarea-rds" />
			    				    
			    </c:forEach>
		    </tbody>	    
	    </table>
</div>

<script>

function formatTablaDeRDSSNC() {
	
	$('#tablaDeRDSSNC').DataTable().destroy();
	var tablaDeRDSSNC = $('#tablaDeRDSSNC')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'RDS-SNC.*',
					exportOptions : {
						columns : ':visible'
					}
				}],
				"language" : lenguaje_es,
				"pageLength": 5,
				"order": [[ 0, "desc" ], [1, "asc"]],
				"columnDefs": [
				    { "width": "10%", "targets": 0 }
				  ]
			});
	
	tablaDeRDSSNC.buttons().container().appendTo(
	'#tablaDeRDSSNC_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaDeRDSSNC();		
});

</script>
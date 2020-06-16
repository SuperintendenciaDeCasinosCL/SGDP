<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<div class="table-responsive col-sm-12">				  		
 		<table id="tablaHistorialDeCondicionesDeSatisfaccion" class="table table-striped table-bordered table-fixed">											
		    <thead>
		        <tr>	        		            	 
		            <th>Nombre</th>
		            <th>Valor Ingresado</th>  
		            <th>Ingresado por</th>
		            <th>Fecha Ingreso</th>      						       
		        </tr>
		    </thead>
		    <tbody>
			    <c:forEach var="historicoValorParametroDeTareaDTO" items="${historicoValorParametroDeTareaDTOList}"> 
			    		<tr>		    		
			    			<td>${historicoValorParametroDeTareaDTO.parametroDeTareaDTO.nombreParamTarea}</td>
			    			<td>${historicoValorParametroDeTareaDTO.valor}</td>
			    			<td>${historicoValorParametroDeTareaDTO.historicoDeInstDeTareaDTO.idUsuarioOrigen}</td>
			    			<td>		    			
				    			<span style="display: none;" >
					    		  <fmt:formatDate value="${historicoValorParametroDeTareaDTO.historicoDeInstDeTareaDTO.fechaMovimiento}" pattern="yyyy-MM-dd HH:mm:ss" /> 
					    		</span>
					    		<fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM}" value="${historicoValorParametroDeTareaDTO.historicoDeInstDeTareaDTO.fechaMovimiento}" />			    			
			    			</td>		    		
			    		</tr>		    
			    </c:forEach>
		    </tbody>	    
	    </table>
</div>

<script>

function formatTablaHistorialDeCondicionesDeSatisfaccion() {
	
	var tablaHistorialDeCondicionesDeSatisfaccion = $('#tablaHistorialDeCondicionesDeSatisfaccion')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'HistorialTareas.*',
					exportOptions : {
						columns : ':visible'
					}
				}],
				"language" : lenguaje_es,
				"pageLength": 5,				
				"order": [[ 3, "desc" ]]
				
			});
	
	tablaHistorialDeCondicionesDeSatisfaccion.buttons().container().appendTo(
	'#tablaHistorialDeCondicionesDeSatisfaccion_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaHistorialDeCondicionesDeSatisfaccion();		
});

</script>
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ page import= "cl.gob.scj.sgdp.tipos.RequisitoType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<c:set var="requisitoStyle" value="tarea-rds" />

<div class="table-responsive col-sm-12">				  		
 		<table id="tablaHistorialDeCondicionesDeSatisfaccion" class="table table-striped table-bordered table-fixed">											
		    <thead>
		        <tr>	        		            	 
		            <th>Nombre</th>
		            <th>Valor</th>  
		            <th>Ingresado por</th>
		            <th>Fecha Ingreso</th>
		            <th>Tarea</th>
		            <th>RDS/SNC</th>      						       
		        </tr>
		    </thead>
		    <tbody>
			    <c:forEach var="historicoValorParametroDeTareaDTO" items="${historicoValorParametroDeTareaDTOList}"> 
			    		
			    		<c:choose>
			    		
				    		<c:when test="${historicoValorParametroDeTareaDTO.parametroDeTareaDTO.esSNC eq true}">
				    			
				    			<c:set var="requisitoStyle" value="tarea-snc" />
				    		
				    		</c:when>				    
			    		
			    		</c:choose>
			    		
			    		<tr class="${requisitoStyle}">		    		
			    			<td>${historicoValorParametroDeTareaDTO.parametroDeTareaDTO.nombreParamTarea}</td>
			    			<td>${historicoValorParametroDeTareaDTO.valor}</td>
			    			<td>${historicoValorParametroDeTareaDTO.nombreResponsabilidad}: ${historicoValorParametroDeTareaDTO.idUsuario}</td>
			    			<td>		    			
				    			<span style="display: none;" >
					    		  <fmt:formatDate value="${historicoValorParametroDeTareaDTO.fecha}" pattern="yyyy-MM-dd HH" /> 
					    		</span>
					    		<fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM}" value="${historicoValorParametroDeTareaDTO.fecha}" />			    			
			    			</td>
			    			<td>${historicoValorParametroDeTareaDTO.instanciaDeTareaDTO.nombreDeTarea}</td>
			    			<td>${historicoValorParametroDeTareaDTO.parametroDeTareaDTO.nombreDeTipoDeRequisito}		    		
			    		</tr>

			    		<c:set var="requisitoStyle" value="tarea-rds" />
			    				    
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
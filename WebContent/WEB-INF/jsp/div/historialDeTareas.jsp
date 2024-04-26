<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<div class="table-responsive col-sm-12">
				  		
	<table id="tablaHistorialDeTareasPorIdExpediente${numeroTabla}" class="table table-striped table-bordered table-fixed">											
	    <thead>
	        <tr>
	            <th>Tarea Realizada</th>
	            <th>Realizada Por</th>
	            <th>Acci&oacute;n Realizada</th>
	            <th>Fecha</th>
	            <th>Usuario Destino</th>
	            <th>Tarea Destino</th>
	            <th>Comentario</th>
	            <th>Tiempo Dedicado</th>	
	            <th></th>       
	        </tr>
	    </thead>
	    <tbody>	    
	    	<c:forEach var="historicoDeInstDeTareaDTO" items="${historicoDeInstDeTareaPorIdExpedienteBusqueda}">	    	
	    		<tr>   			    		
		    		<td class="view-message">${historicoDeInstDeTareaDTO.nombreTareaDeOrigen}</td>
		    		<td class="view-message">${historicoDeInstDeTareaDTO.nombreResponsabilidadOrigen}: ${historicoDeInstDeTareaDTO.idUsuarioOrigen}</td>
		    		<td class="view-message">${historicoDeInstDeTareaDTO.nombreAccion}</td>	
		    		<td>
		    			<span style="display: none;" >
			    		  <fmt:formatDate value="${historicoDeInstDeTareaDTO.fechaMovimiento}" pattern="yyyy-MM-dd HH:mm:ss" /> 
			    		</span>
			    		<fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM}" value="${historicoDeInstDeTareaDTO.fechaMovimiento}" />
			    	</td>
		    		<td class="view-message">${historicoDeInstDeTareaDTO.nombreResponsabilidadDestino}: ${historicoDeInstDeTareaDTO.usuariosDestinoString}</td>
		    		<td class="view-message">${historicoDeInstDeTareaDTO.nombreTareaDeDestino}</td>
					<td class="view-message">${historicoDeInstDeTareaDTO.comentario}</td>
					<td class="view-message">
		    			<c:if test = "${historicoDeInstDeTareaDTO.horasOcupadas ne null}">
		    				${historicoDeInstDeTareaDTO.horasOcupadas} h : ${historicoDeInstDeTareaDTO.minutosOcupados} m
		    			</c:if>		    			
		    		</td>
					<td class="view-message">
						<c:if test = "${historicoDeInstDeTareaDTO.tieneHistoricoValorParametroDeTarea eq true}">
		    			
		    				<button 
		    				onclick="cargaHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea(${historicoDeInstDeTareaDTO.idHistoricoDeInstDeTarea},
		    				'${historicoDeInstDeTareaDTO.nombreExpediente}',
		    				'${historicoDeInstDeTareaDTO.nombreTareaDeOrigen}')" 
		    				type="button" title="Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes:" class="btn btn-primary btn-sm">
								<span class="glyphicon glyphicon-eye-open"></span>
							</button>
		    			
		    			</c:if>
		    			<c:if test="${historicoDeInstDeTareaDTO.cantAccionesEnBitacora > 0}">
							<button type="button" title="Bit&aacute;cora de Subtareas"
											class="btn btn-primary btn-xs"
											onclick="cargaBitacora(${historicoDeInstDeTareaDTO.idInstanciaDeTareaDeOrigen})">
											<span class="fa fa-compass font-icon-2 "></span>
							</button>
						</c:if>	
		    		</td>		    		
	    		</tr>
	    	</c:forEach> 
	    </tbody>
	</table>
 	
</div>

<script>

function formatTablaHistorialDeTareasPorIdExpediente() {
	
	var tablaHistorialDeTareasPorIdExpediente = $('#tablaHistorialDeTareasPorIdExpediente${numeroTabla}')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'HistorialTareas.*',
					exportOptions : {
						columns : ':visible'
					}
				}],
				"language" : languajeDataTableTareas,
				"pageLength": 5,				
				"order": [[ 3, "desc" ]]
				
			});
	
	tablaHistorialDeTareasPorIdExpediente.buttons().container().appendTo(
	'#tablaHistorialDeTareasPorIdExpediente_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaHistorialDeTareasPorIdExpediente();		
});

</script>
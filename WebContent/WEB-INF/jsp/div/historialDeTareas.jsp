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
	            <th>Acciones</th>		       
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
						<c:if test = "${historicoDeInstDeTareaDTO.tieneHistoricoValorParametroDeTarea eq true}">
		    			
		    				<button 
		    				onclick="cargaCondicionesDeSatisfaccion(${historicoDeInstDeTareaDTO.idInstanciaDeTareaDeOrigen},
		    				'${historicoDeInstDeTareaDTO.nombreExpediente}',
		    				'${historicoDeInstDeTareaDTO.nombreTareaDeOrigen}')" 
		    				type="button" title="Historial de Condiciones de Satisfacci&oacute;n" class="btn btn-primary btn-sm">
								<span class="glyphicon glyphicon-eye-open"></span>
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

function cargaCondicionesDeSatisfaccion(idInstanciaDeTarea, nombreExpediente, nombreTarea) {

	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();

	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  var urlGetHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea = $("#urlGetHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea").val();
	    		console.log("urlGetHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea: " + urlGetHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea);
	    		console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	    		$("#historialCondicionesDeSatisfaccionH4").empty();
	    		$("#historialCondicionesDeSatisfaccionH4").append('Historial de Condiciones de Satisfacci&oacute;n: ' + nombreTarea + " ( " + nombreExpediente + " ) ");
	    		$('#historialCondDeSatisfaccionModal').modal({backdrop: 'static', keyboard: false});
	    		$('#historialDeCondicionesDeSatisfaccionDiv').load(urlGetHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea + "/" + idInstanciaDeTarea );
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_blank');
	                          }
	             );
	      }
	}); 
	
}

$(document).ready(function() {
	formatTablaHistorialDeTareasPorIdExpediente();		
});

</script>
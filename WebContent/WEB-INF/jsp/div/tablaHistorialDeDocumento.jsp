<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />
<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<%--<legend>Documentos de ${nombreExpediente} <span class="glyphicon glyphicon-info-sign cursorPuntero info-sgdp-historial hide"  
 	                	data-toggle="tooltip" data-placement="top" title="Ver Historial del SubProceso"
 	                	 onclick="dibujarHistorialDeInstanciaDeProceso(${idInstanciaDeTarea})"></span></legend> --%>

<div class="table-responsive col-sm-12">
				  		
 		<table id="tablaHistorialDelDocumento${numeroTabla}" class="table table-striped table-bordered table-fixed">											
	    <thead>
	        <tr>
	        	<th>Fecha</th>
	            <th>Tarea</th>
	            <th>Rol</th>
	            <th>Responsable</th>
	            <%--<th>Comentario</th> --%>	            	 
	            <th>Documento (Tipo)</th>  
	            <th></th>      						       
	        </tr>
	    </thead>
	    <tbody>
	    
	    	<c:forEach var="archivoInstDeTareaDTO" items="${todosLosDocSubidos}">
	    	
	    		<tr>
			    	<td>
			    		<span style="display: none;" >
			    		  <fmt:formatDate value="${archivoInstDeTareaDTO.fechaSubido}" pattern="yyyy-MM-dd HH:mm:ss" /> 
			    		</span>
			    		<fmt:formatDate value="${archivoInstDeTareaDTO.fechaSubido}" pattern="${FORMATO_FECHA_FORM_HH_MM}"/> 
			    	</td>
			    	<td>${archivoInstDeTareaDTO.nombreTarea}</td>
			    	<td>${archivoInstDeTareaDTO.nombreResponsabilidad}</td>			    	
			    	<td>${archivoInstDeTareaDTO.idUsuario}</td>
			    	<%--<td>${archivoInstDeTareaDTO.ultimoComentario}</td>--%>
			    	<td>${archivoInstDeTareaDTO.nombreArchivo} (${archivoInstDeTareaDTO.nombreDeTipoDeDocumento})</td>			    	
			    	<td>
		    			<a href="#" class="btn btn-primary btn-xs" id="${archivoInstDeTareaDTO.idArchivoCms}" title="Detalle documento"
		                	onclick="cargaDetalleDeDocumento('${archivoInstDeTareaDTO.idArchivoCms}', 
		                	${archivoInstDeTareaDTO.esVisable}, 
		                	${archivoInstDeTareaDTO.aplicaFEA}, 
		                	${archivoInstDeTareaDTO.aplicaFirmaApplet},
		                	'${archivoInstDeTareaDTO.idExpediente}',								                	
		                	'${archivoInstDeTareaDTO.nombreArchivo}',
		                	'${archivoInstDeTareaDTO.mimeType}',
			                '${idInstanciaDeTarea}');" >&nbsp;
						<span class="glyphicon glyphicon-eye-open"></span></a>	
						<button type="button" title="Descarga documento"
							class="btn btn-primary btn-xs"
							onclick='descargaArchivo("<c:url value='getArchivoPorId/${archivoInstDeTareaDTO.idArchivoCms}'/>")'
							data-iddocumento="${archivoInstDeTareaDTO.idArchivoCms}">
							<span class="fa fa-download font-icon-2 "></span>
						</button>	
			    	</td>
			    </tr>
	    	
	    	</c:forEach>
	    
	    <%-- 
	    	<c:forEach var="listaHistoricoDocumento" items="${listaHistoricoDocumento}">
	    	
	    		<tr>
			    	<td>
			    		<fmt:formatDate value="${listaHistoricoDocumento.fecha}" pattern="dd/MM/yyyy HH:mm"/> 
			    	</td>
			    	<td>${listaHistoricoDocumento.nombreTarea}</td>			    	
			    	<td>${listaHistoricoDocumento.idUsuarioQueAsigna}</td>
			    	<td>${listaHistoricoDocumento.comentario}</td>
			    	<td>${listaHistoricoDocumento.nombreDeTipoDeDocumento}</td>			    	
			    	<td>
		    			<a href="#" class="btn btn-primary " id="${listaHistoricoDocumento.idArchivo}"
		                	onclick="cargaDetalleDeDocumento('${listaHistoricoDocumento.idArchivo}', 
		                	${listaHistoricoDocumento.esVisable}, 
		                	${listaHistoricoDocumento.aplicaFEA}, 
		                	${listaHistoricoDocumento.aplicaFirmaApplet},
		                	'${listaHistoricoDocumento.idExpediente}',								                	
		                	'${listaHistoricoDocumento.nombre}',
		                	'${listaHistoricoDocumento.mimeType}');" >
						<span class="glyphicon glyphicon-eye-open "></span></a>		
			    	</td>
			    </tr>
	    	
	    	</c:forEach>
	       --%>
	       
	    </tbody>
	</table>
 	
 	
</div>

<script>

var formatTablaHistorialDelDocumento = function () {
	
	var tablaHistorialDelDocumento = $('#tablaHistorialDelDocumento${numeroTabla}')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'HistorialDocumento.*',
					exportOptions : {
						columns : ':visible'
					}
				}/*, 'colvis'*/ ],
				//bLengthChange: false,
				//"sDom": 'l<"toolbarTablaHistorialDelDocumento">frtip'
				"language" : languajeDataTableDocumentos,
				"pageLength": 7,
				"order": [[ 0, "desc" ]]//,
				//"dom": '<"toolbarTablaHistorialDelDocumento">frtip'
				//"dom": '<"bottom"ip>rt<"top"fl><"clear">'
				
			});

	//$("div.toolbarTablaHistorialDelDocumento").html('<b>Historial de Documentos</b>');
	
	tablaHistorialDelDocumento.buttons().container().appendTo(
	'#tablaHistorialDelDocumento_wrapper .row:eq(0)');
};

$(document).ready(function(){
	$(formatTablaHistorialDelDocumento);	
});

function dibujarHistorialDeInstanciaDeProceso(idInstanciaDeTarea){
	console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	var urlDibujaHistorialInstanciaDeProceso = "http://${urlFuncPhp}/proceso/indexpe.php?idtar="+idInstanciaDeTarea;
	console.log("urlDibujaHistorialInstanciaDeProceso: " + urlDibujaHistorialInstanciaDeProceso);
		$.get("${sessionURL}", function(haySession){
				console.log("haySession: " + haySession);
				if(haySession){
					window.open(urlDibujaHistorialInstanciaDeProceso, "Proceso", 'width=1080, height=600');
				}else{
					bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
												, function(){
															window.open('${raizURL}', '_blank');
												}
					 );
				}
	});
}

</script>



	<!-- Modal para probar funcionalidad-->
	
	<!-- Modal Detalle Documento
	
	<c:import url="/WEB-INF/jsp/modals/detalleDeDocumento.jsp"></c:import>-->

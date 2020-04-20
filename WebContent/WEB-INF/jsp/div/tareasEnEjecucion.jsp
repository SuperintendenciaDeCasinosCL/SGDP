<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="permisoReasignaTarea" value="<%=PermisoType.REASIGNA_TAREA.getNombrePermiso()%>"/>	
<c:set var="permisoPuedeCerrarExpediente" value="<%=PermisoType.PUEDE_CERRAR_EXPEDIENTE.getNombrePermiso()%>"/>
<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />
<c:set var="permisoAdjuntarDocEnTodaEtapa" value="<%=PermisoType.ADJUNTAR_DOC_EN_TODA_ETAPA.getNombrePermiso()%>"/>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<%--<div id="panelTareasEnEjecucion" class="panel panel-primary">--%>

	<%--<div class="panel-heading">		
		
		<legend>Procesos en ejecuci&oacute;n</legend>	

	</div> --%>
	
	<%--<div class="panel-body"> --%>
	
		<div class="table-responsive">	
			<!-- <table id="tablaTareas" class="table table-inbox table-hover">	 -->
			<table id="tablaTareasEnEjecucion" class="table table-striped table-bordered table-hover">											
			    <thead>
			        <tr>
			            <th>SubProceso</th>
						<th>Tarea</th>
						<th>Expediente</th>
						<th>Responsable</th>
						<th>Plazo Tarea</th>
			        	<th>Plazo Total</th>
			        	<th>Fecha Creaci&oacute;n Expediente</th>
			        	<th>Acciones</th>
			        	<%-- 
				        	<c:if test = "${permisos[permisoReasignaTarea] eq permisoReasignaTarea}">
				        		<th>Reasignar</th>
				        	</c:if>
			        	--%>
			    	</tr>
				</thead>
				
				<tbody>
					<c:forEach var="instanciaDeTareaDTO" items="${instanciasDeTareasDTOEnEjecucion}">
						<tr>
						    <td class="view-message">${instanciaDeTareaDTO.nombreDeProceso}</td>
							<td class="view-message">${instanciaDeTareaDTO.nombreDeTarea}</td>
							<td class="view-message">${instanciaDeTareaDTO.nombreExpediente}</td>
							<td class="view-message">							
								 ${instanciaDeTareaDTO.primerUsuarioAsignado}
								 <c:if test = "${instanciaDeTareaDTO.cantidadDeMasUsuariosAsignados gt 0}">								
								  	y ${instanciaDeTareaDTO.cantidadDeMasUsuariosAsignados} <i onclick="muestraMasUsuariosAsignados('${instanciaDeTareaDTO.usuariosAsignadosString}', '${instanciaDeTareaDTO.idInstanciaDeTarea}')" id="plusUsuarios${instanciaDeTareaDTO.idInstanciaDeTarea}" class="glyphicon glyphicon-plus cursorPuntero" ></i>
								 </c:if>
								 <%-- <a href="#" class="btn btn-info btn-sm" onclick="cargarUsuarioAsignadosModal('${instanciaDeTareaDTO.usuariosAsignadosString}', '${instanciaDeTareaDTO.nombreDeTarea}')">
						         	<span class="glyphicon glyphicon-user"></span> Usuarios
						         </a>--%>
							</td>
							<td class="view-message">
								<span style="display: none;" >
					    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" pattern="yyyy-MM-dd" /> 
					    		</span>								
								<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
							</td>
							<td class="view-message">
								<span style="display: none;" >
					    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeProceso}" pattern="yyyy-MM-dd" /> 
					    		</span>									
								<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeProceso}" />
							</td>							
							<td class="view-message">
								<span style="display: none;" >
					    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaInicioInstanciaDeProceso}" pattern="yyyy-MM-dd" /> 
					    		</span>									
								<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaInicioInstanciaDeProceso}" />
							</td>							
							<td class="inbox-small-cells">							
								<c:if test = "${permisos[permisoReasignaTarea] eq permisoReasignaTarea}">									
									<a href="#" class="btn btn-success btn-sm" onclick="cargarDatosContinuarProcesoModal(${instanciaDeTareaDTO.idInstanciaDeTarea}, 
																							'${instanciaDeTareaDTO.idExpediente}', 
																							${instanciaDeTareaDTO.puedeDevolver}, 
																							${instanciaDeTareaDTO.esUltimaTarea},
																							'${instanciaDeTareaDTO.nombreDeTarea} - ${instanciaDeTareaDTO.nombreDeProceso} - ${instanciaDeTareaDTO.nombreExpediente}',
																							${instanciaDeTareaDTO.tieneDocumentosEnCMS}, 
																							true, 
																							'${instanciaDeTareaDTO.tipoDeBifurcacion}',
																							'${instanciaDeTareaDTO.nombreDeTarea}')"
																							title="Reasignar Tarea">
										<span class="glyphicon glyphicon-share"></span>
									</a>	
								</c:if>
								<c:if test = "${permisos[permisoPuedeCerrarExpediente] eq permisoPuedeCerrarExpediente}">									
									<a href="#" class="btn btn-danger btn-sm boton-cerrar-expediente" title="Anular expediente" 
										data-idinstanciadeproceso="${instanciaDeTareaDTO.idInstanciaDeProceso}"
										data-nombredeproceso="${instanciaDeTareaDTO.nombreDeProceso}"
										onclick="cerrarExpediente(${instanciaDeTareaDTO.idInstanciaDeProceso}, '${instanciaDeTareaDTO.nombreDeProceso}')"
										>
										<span class="glyphicon glyphicon-remove"></span>
									</a>	
								</c:if>
								
								<c:if test = "${permisos[permisoAdjuntarDocEnTodaEtapa] eq permisoAdjuntarDocEnTodaEtapa && instanciaDeTareaDTO.esperaRespuesta == false}">									
									<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Añadir Antecedente" 
										data-idexpediente="${instanciaDeTareaDTO.idExpediente}"
										data-idinstanciatarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
										data-emisor="${instanciaDeTareaDTO.emisor}"	
																											
										onclick="adjuntarDocumentoEnTodaEtapa('${instanciaDeTareaDTO.idExpediente}',
																			  '${instanciaDeTareaDTO.idInstanciaDeTarea}',
																			  '${instanciaDeTareaDTO.emisor}',
																			  '${instanciaDeTareaDTO.nombreExpediente}')">
										<span class="glyphicon glyphicon-upload"></span>
									</a>	
									
								</c:if>
								
						    	<!-- Boton Prueba agregar flujo expediente -->
						    	<!-- ////////////////////////////////////////////////////////// -->
							
								<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
										onclick="buscarDiagramaSubproceso('${instanciaDeTareaDTO.idDiagrama}',
																		  '${instanciaDeTareaDTO.idProceso}',
																		  '${instanciaDeTareaDTO.idInstanciaDeProceso}',
																		  '${instanciaDeTareaDTO.idExpediente}')">
										<span class="fa fa-gears"></span>
								</a>
								
								<!-- ////////////////////////////////////////////////////////// -->
								
								<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Vinculaciones" 																										
										onclick="vinculaciones('${instanciaDeTareaDTO.idExpediente}', '${instanciaDeTareaDTO.nombreExpediente}')">
										<span class="glyphicon glyphicon-link font-icon-1"></span>
						        </a>
								
							</td>
						</tr>
					</c:forEach>															  
				 </tbody>
			</table>
		</div>
    
    <%-- Modal que carga el diagrama --%>
    
	<div class="modal fade" id="infoDeProcesoModal2" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		
	<div class="modal-dialog modal-lg">
		<div class="modal-content">		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				
				<h3 class="modal-title">Informaci&oacute;n del SubProceso</h3>
			
			</div>
			
			<div class="modal-body" id="informacionDelProcesoDiv2">		
			
							
			</div>
		</div>
	</div>
			
			
			
	</div>
	
	<script>

	  function buscarDiagramaSubproceso(idDiagrama,idProceso,idInstanciaDeProceso,idExpediente){

   		var contextPath = "${pageContext.request.contextPath}"

   			$('#infoDeProcesoModal2').modal('show');   
   			
 			$("#informacionDelProcesoDiv2").css("position",
 				"relative").css("min-height", "200px").prepend(
 			$("<div />").addClass("cargando"));
 			
 			$("#informacionDelProcesoDiv2").load(
 					contextPath + "/diagramaProceso2/"+idDiagrama+ "/" + idProceso +"/"+idInstanciaDeProceso + "/" + idExpediente ,
 					function() {
 						$("#tabNotificacionesSeguimientosDiv").find(
 								".cargando").remove();
 	 				});
				
 		       	
	  }
	
	</script>
				
	<%-- //////////////////////////////////////////////////////////  --%>	
	
	<%-- </div>--%>
	
	<c:import url="/WEB-INF/jsp/modals/usuariosAsignadosATarea.jsp"></c:import>	

<%-- </div>--%>

<script>

function adjuntarDocumentoEnTodaEtapa (idExpediente,idInstanciaDeTarea,emisor,nombreExpediente) {

	$.get("${sessionURL}", function(haySession) {
	    if (haySession) {  	
	    	$('#anadirAntecedenteTodaEtapaModal').modal({backdrop: 'static', keyboard: false})  
	    	
	    	$(".AdjuntarAntecedentesTodaTabla > tbody").html("");
	
	    	$("#botonAdjuntarAntecedentesTodaEtapa").attr("disabled","disabled");
	    	
	    	console.log("idExpediente: " + idExpediente);
	    	console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	    	console.log("emisor: " + emisor);
	    	console.log("nombreExpediente: " + nombreExpediente);

	    	$("#adjuntarAntecedentesTodaEtapaFile").attr('data-idexpediente', idExpediente);
	    	$("#adjuntarAntecedentesTodaEtapaFile").attr('data-idinstanciatarea', idInstanciaDeTarea);
	    	$("#adjuntarAntecedentesTodaEtapaFile").attr('data-emisor', emisor);
	    	$("#adjuntarAntecedentesTodaEtapaFile").attr('data-nombreexpediente', nombreExpediente);
	    	$("#adjuntarAntecedentesTodaEtapaFile").attr('data-flagrestablecerdocumento', 0);

	    	$(inicializaFileUploadAdjuntarAntecedenteEtapaModal);
	    	
	   } else {
	         bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             , function(){ window.open('${raizURL}', '_blank');}
	         );
	   }
	});		
}

function cerrarExpediente (idInstanciaDeProceso, nombreDeProceso) {
	$.get("${sessionURL}", function(haySession) {
		console.log("haySession: " + haySession);
	    if (haySession) {  	
	    	var urlCerrarProceso = $("#urlCerrarProceso").val();
	    	console.log("urlCerrarProceso: " + urlCerrarProceso);
	    	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	    	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();
	    	bootbox.prompt({	    	
	        	title: "Anular SubProceso: " + nombreDeProceso,
	        	message: "<p>Anular SubProceso.</p>",
	        	placeholder: "Ingrese motivo de anulación",
	        	size: 'medium',
	            buttons: {
	                confirm: {
	                    label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
	                    className: 'btn-success'
		            },
	                cancel: {
	                    label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
	                    className: 'btn-danger'
	                }
	            },
	            callback: function (motivoAnulacion) {	    			
	    			var anulacionDTO = {};
	    			anulacionDTO["idInstanciaDeProceso"] = idInstanciaDeProceso;
	    			anulacionDTO["motivoAnulacion"] = motivoAnulacion;
	    			if (motivoAnulacion!=null && motivoAnulacion!="") {
	    				$.ajax({
	    					type : "POST",
	    					contentType : "application/json",
	    					url : urlCerrarProceso,
	    					data : JSON.stringify(anulacionDTO),
	    					dataType : 'json',
	    					timeout : 100000,
	    					success: function (returnData) {
	    						console.log("SUCCESS: ", returnData);	    	
	    					},
	    					error : function(e) {
	    						console.log("ERROR: ", e);			
	    					},
	    					done : function(e) {
	    						console.log("DONE");
	    					},
	    					complete : function(returnData) {
	    						console.log("returnData: " + returnData);
	    						actualizaTareas('tareasBandejaDeEntrada', urlGetInstanciasDeTarea, true);
	    						cargaTareasEnEjecucion();
	    					}        		
	    				});					
	    			} else if (motivoAnulacion==null) {
						return true;
            		} else {									            	    	
            	    	$(".bootbox-input-text").validationEngine('showPrompt', 'Por favor ingrese un motivo', 'error');
            	    	return false;
	            	}										
	            }
	        });
	   } else {
	         bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             , function(){ window.open('${raizURL}', '_blank');}
	         );
	   }
	});
}

function formatTablaTareasEnEjecucion() {

	var tablaTareasEnEjecucion = $('#tablaTareasEnEjecucion')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'TareasEnEjecucion.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : languajeDataTableTareasEnEjecucion,
				"pageLength": 8
				
			});

	tablaTareasEnEjecucion.buttons().container().appendTo(
	'#tablaTareasEnEjecucion_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaTareasEnEjecucion();	
})

</script>

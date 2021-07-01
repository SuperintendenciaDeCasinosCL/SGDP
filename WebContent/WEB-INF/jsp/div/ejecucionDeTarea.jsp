<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 
<%@ page import= "cl.gob.scj.sgdp.tipos.BifurcacionType" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />

<c:set var="requisitoStyle" value="" />

<c:set var="tipoBifurcacionAND" value="<%=BifurcacionType.AND.getNombreTipoDeBifurcacion()%>"/>
<c:set var="tipoBifurcacionOR" value="<%=BifurcacionType.OR.getNombreTipoDeBifurcacion()%>"/>

<c:url value="http://${urlFuncPhp}/proceso/bpm/notificar.php?idproc=${instanciaDeProcesoDTO.nombreExpediente}" var="urlNotificarInstanciaDeProceso" />

<c:url value="http://${urlFuncPhp}/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}&idInsProc=${instanciaDeTareaDTO.idInstanciaDeProceso}" var="urlDiagramaSubProcesoBoton" />

<c:choose>
	<c:when test = "${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
		<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoUsuario}" var="fechaPlazoFormat" />		       				
	</c:when>
	<c:when test = "${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
		<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" var="fechaPlazoFormat" />
	</c:when>
</c:choose>

<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeProcesoDTO.fechaVencimiento}" var="fechaPlazoInstProcesoFormat" />

<input type="hidden" id="numeroDeIteracionDeLaInstanciaDeTarea" name="numeroDeIteracionDeLaInstanciaDeTarea" value="${numeroDeIteracionDeLaInstanciaDeTarea}" />
	
	<%--
	<div class="col-sm-12">
		<h3>
    		Estado Avance del Subproceso
  		</h3>
  		<br>
   	    <div class="progress">
               <div class="bar-step">               		
                	<div class="label-txt">Inicio SubProceso: <fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeProcesoDTO.fechaInicio}"/></div>                   									                    
              </div>
               <div class="bar-step-2" style="left: ${porcentajeDeAvanceDeInstTareaWidth}%">               		
                   	<div class="label-txt"><span class="glyphicon glyphicon-arrow-up"></span> Plazo Tarea: ${fechaPlazoFormat}</div>                 
               </div>
               <div class="bar-step" style="left: 85.7%">
                   <div class="label-txt">Plazo SubProceso: ${fechaPlazoInstProcesoFormat}</div>                
               </div>
 			   <div class="progress-bar ${progressBarStatus}" style="width: ${porcentajeDeAvanceInstanProc}%;">Tiempo transcurrido (${diasDiferenciaEntreHoyEInicioInstanciaProc} dias)</div>
		</div>
   	</div>		
	--%>	  
	     		
	<div class="col-sm-4">	
	
		<br>
		
		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<font size="2"><strong>Expediente:</strong> ${instanciaDeTareaDTO.nombreExpediente}</font>	
			</div>	
		</div>
		
		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<font size="2"><strong>SubProceso:</strong> ${instanciaDeTareaDTO.nombreDeProceso}</font>	
			</div>	
		</div>		
 		
 		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<font size="2"><strong>Plazo m&aacute;ximo del SubProceso :</strong> ${fechaPlazoInstProcesoFormat}</font>	
			</div>	
		</div>
			
		<div class="row espacio-entre-row">		
			
			<div class="col-sm-12">
				<font size="2"><strong>Tarea:</strong> ${instanciaDeTareaDTO.nombreDeTarea}</font>
			</div>
						
			<script type="text/javascript">
			function seguir(idInstanciaDeProceso){

				var contextPath = "${pageContext.request.contextPath}"
				

				 var InstanciaDeProceso = {}
				 InstanciaDeProceso["idInstanciaDeProceso"] =  idInstanciaDeProceso;

						$.ajax({
						type : "POST",
						contentType : "application/json",
					    url: contextPath + "/" + "guardarSeguimiento",
						data : JSON.stringify(InstanciaDeProceso),
						dataType : 'json',
						timeout : 100000,
						success : function(data) {
	                        if (data.mensaje == "ERROR"){
	                        	$.notify({
									message: 'Error al seguir'
								},{
									type: 'danger'
								}); 
		                      }	           							 
						},
						error : function(e) {
							 console.log("ERROR: ", e);
						},
						complete: function(e){
							cargaDetalleDeTarea("${instanciaDeTareaDTO.nombreExpediente}"
									,"${instanciaDeTareaDTO.idInstanciaDeTarea}"
									,"true"
									,"${instanciaDeTareaDTO.idExpediente}"
									,"${instanciaDeTareaDTO.urlControl}");							
							
						},
		
					});
				 
				}

			
			function dejarSeguir(idInstanciaDeProceso){
					bootbox.confirm({
				     message: "¿Dejar de seguir el proceso?",
				    buttons: {
				        confirm: {
				            label: 'Si',
				            className: 'btn-success'
				        },
				        cancel: {
				            label: 'No',
				            className: 'btn-danger'
				        }
				    },
				    callback: function (result) {
				        if (result){

							var contextPath = "${pageContext.request.contextPath}"
								
								 var InstanciaDeProceso = {}
								 InstanciaDeProceso["idInstanciaDeProceso"] =  idInstanciaDeProceso;

										$.ajax({
										type : "POST",
										contentType : "application/json",
									    url: contextPath + "/" + "dejarDeSeguimiento",
										data : JSON.stringify(InstanciaDeProceso),
										dataType : 'json',
										timeout : 100000,
										success : function(data) {

					                        if (data.mensaje == "ERROR"){
					                        	$.notify({
													message: 'Error al dejar de seguir'
												},{
													type: 'danger'
												}); 
						                      }	           							 
										},
										error : function(e) {
											 console.log("ERROR: ", e);
										},
										complete: function(e){
											
											cargaDetalleDeTarea("${instanciaDeTareaDTO.nombreExpediente}"
													,"${instanciaDeTareaDTO.idInstanciaDeTarea}"
													,"true"
													,"${instanciaDeTareaDTO.idExpediente}"
													,"${instanciaDeTareaDTO.urlControl}");										
										
										},
						
									});
				        }
				    }
				});			
				}
			
			</script>

		</div>
		
		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<font size="2"><strong>Plazo m&aacute;ximo tarea:</strong> ${fechaPlazoFormat}</font>			
			</div>			
		</div>
		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<font size="2"><strong>Rol:</strong> ${instanciaDeTareaDTO.nombreRolQueEjecuta}</font>	
			</div>	
		</div>		
		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<font size="2"><strong>De:</strong> ${instanciaDeTareaDTO.idUsuarioQueAsigna}</font> 
			</div>
		</div>		
		
		<div class="row espacio-entre-row">
			<div class="col-sm-12">
				<c:choose>	
					<c:when test="${not empty ultimoComentario}">
						<font size="2"><strong>Comentario:</strong> ${ultimoComentario}</font>
					</c:when>		
					<c:when test="${not empty instanciaDeProcesoDTO.comentarioSolicitudCreacionExpediente and instanciaDeTareaDTO.puedeDevolver ne true}">
						<font size="2"><strong>Comentario:</strong> ${instanciaDeProcesoDTO.comentarioSolicitudCreacionExpediente}</font>
					</c:when>			
					<c:otherwise>
						<font size="2"><strong>Comentario:</strong></font>
					</c:otherwise>			
				</c:choose>
			</div>			
		</div>
								
		<br>
   	</div>   	
   	
   	<div class="col-sm-5">
   	
   		<br>
   		<div class="div-iframe-resize">
   			<iframe class="iframe-resize" scrolling="si" src='http://${urlFuncPhp}/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}&idInsProc=${instanciaDeTareaDTO.idInstanciaDeProceso}'></iframe>
   		</div>
   		
   		<div class="col-sm-4">   		
   		</div>	
   		
   		<div class="col-sm-4">   		
   			<button type="button" class="btn btn-default btn-sm boton-amplicar-diagrama" id="botonVerDiagramaEnNuevaVentanaET">
				Ampliar Diagrama
			</button>   		
   		</div>
   		
   		<div class="col-sm-4">   		
   		</div>   			   
   	</div>
   	
   	<div class="col-sm-3">
   	
   		<br>
		
		<div class="row espacio-entre-row">
			<div class="col-sm-12">	
				<button type="button" class="btn btn-default btn-sm botonVinculaciones" id="botonVinculaciones" onclick="vinculaciones('${instanciaDeProcesoDTO.idExpediente}', '${instanciaDeProcesoDTO.nombreExpediente}')">
		            Vinculaciones
		      	</button>							
				<c:choose>
					<c:when test = "${tieneSeguimiento == 0}">
					  <button type="button" class="botonSeguir" id="botonSiguiendo" onclick="seguir(${instanciaDeProcesoDTO.idInstanciaDeProceso})">
				            Seguir
				      </button>	
				 	</c:when>
				 	<c:otherwise>
				 		  <button type="button" class="botonSiguiendo" id="botonSeguir" onclick="dejarSeguir(${instanciaDeProcesoDTO.idInstanciaDeProceso})" >
				            Siguiendo
				          </button>	
				  	</c:otherwise>
				</c:choose>
				<button type="button" class="btn btn-default btn-sm boton-notificar" id="botonNotificarInstanciaDeProceso">
				            Notificar
				</button>
	 		</div>
 		</div>
   	
   	</div>   	
   	
   	<div class="col-sm-12">	
   	
   		<c:if test="${!empty detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea}">
			<div class="row">
				<div class="col-sm-12">
					<h4>Documentos enviados</h4>
				</div>
			</div>			
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-condensed ancho-tabla-documentos-enviados">
						<thead>
					    	<tr>
					        	<th>Nombre</th>
					        	<th>Tipo</th>
					        	<th>Opci&oacute;n</th>
					      	</tr>
					    </thead>
					    <tbody>
					    	<c:forEach var="detalleDeArchivoDTOAnterior" items="${detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea}">
						    	<tr>
							    	<td>${detalleDeArchivoDTOAnterior.nombre}</td>
							    	<td>${detalleDeArchivoDTOAnterior.tipoDeDocumento}</td>
							    	<td>							    	
							    		<button type="button" title="Descargar documento"
											class="btn btn-primary btn-sm"
											onclick='descargaArchivo("<c:url value='getArchivoPorId/${detalleDeArchivoDTOAnterior.idArchivo}'/>")'
											data-iddocumento="${detalleDeArchivoDTOAnterior.idArchivo}">
											<span class="fa fa-download font-icon-2 "></span>
										</button>&nbsp;
										<c:if test = "${detalleDeArchivoDTOAnterior.esEditable eq true}">
											<a href="#" class="btn btn-primary btn-sm boton-editar-documento-enviado" id="botonEditarDocumentoEnviadoEnEjecucionTarea" 
											title="Editar documento"
												data-codigomimetype="${detalleDeArchivoDTOAnterior.codigoMimeType}"
						                        data-linksharpoint="${detalleDeArchivoDTOAnterior.linkSharpoint}">
						                              <span class="glyphicon glyphicon-edit font-icon-2"></span>
						                	</a>
										</c:if>	
							    	</td>
							    </tr>
					    	</c:forEach>     
					    </tbody>
					</table>
				</div>
			</div>
		</c:if>
   	
   	</div>
   	
   	<div class="col-sm-12">	
   	
   		<c:if test="${instanciaDeProcesoDTO.tieneParametroPorTarea eq true}">
			
			<div class="row espacio-entre-row">
				<div class="col-sm-6">
					<button 
			    				onclick="cargaHistorialDeCondicionesDeSatisfaccionPorIdExpediente('${instanciaDeTareaDTO.nombreExpediente}',
			    				'${instanciaDeTareaDTO.idExpediente}')" 
			    				type="button" title="Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes" class="btn btn-info btn-block">
									Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes
								</button>
				</div>
			</div>
			
			<c:if test="${tareaTieneParametros eq true }">
			
				<div class="row espacio-entre-row">
					<div class="col-sm-6">
						<button id="botonCargaCondicionesDeSatisfaccion"
				    				onclick="cargaCondicionesDeSatisfaccion('${instanciaDeTareaDTO.idInstanciaDeTarea}'
				    				, '${instanciaDeTareaDTO.nombreExpediente}'
				    				, '${instanciaDeProcesoDTO.nombreDeProceso}')" 
				    				type="button" title="Completar Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes" class="btn btn-danger btn-block">
										Completar Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes
									</button>
					</div>
				</div>
			
			</c:if>
			
			
		</c:if>
   	
   	</div>
   	
   	<div class="col-sm-12">	   	
   			
		<div class="panel panel-primary">
		
      		<div class="panel-heading">Enviar Tarea</div>
      		
      		<div class="panel-body back-panel"> 
      		
      			<c:if test="${not empty archivosInfoDTODeSalidaPorIdInstanciaDeTarea}">    					
  				
  					<div class="row">
  					
  						<div class="col-sm-12">Documentos requeridos:</div>  						
  					
  					</div>
  					
  					<br>
  					
  					<div class="row"> 
  					 						
  						<div class="col-sm-12" id="documentosDeSalidaDiv" data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}">
  							 	
  							<c:import url="/WEB-INF/jsp/div/documentosDeSalida.jsp"></c:import>
  					
  						</div>
  					
  					</div>
 					
 				</c:if> 				
 						
				<div class="row">					
					
					<div class="col-sm-12">	
						<div class="pull-right">
							<button type="button" title="An&#771;adir antecedente"
								data-idexpediente="${instanciaDeTareaDTO.idExpediente}" 
								data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
								data-nombreexpediente="${instanciaDeTareaDTO.nombreExpediente}"
								id="botonAnadirDocumentoOpcional" class="btn btn-primary">
								<span class="glyphicon glyphicon-upload font-icon-1"></span> Antecedente
							</button>
						</div>										
					</div>
				</div>	
				
				<br>	
				      		
      			<label for="commentarioEjecucionTarea">Comentario:</label>
      			
  				<textarea class="form-control" rows="4" id="commentarioEjecucionTarea"></textarea> 

  				<br> 							
  				
  				<div class="row">
				
					<div class="col-sm-12">	
						<div class="pull-left">
							<c:if test = "${instanciaDeTareaDTO.distribuye eq true}">
								<button type="button" title="Distribuir documentos"	
								onclick="abrirCorreoDeDistribucionModal('${instanciaDeTareaDTO.idExpediente}', ${instanciaDeTareaDTO.idInstanciaDeTarea}, '${instanciaDeTareaDTO.nombreExpediente}')"							
									id="botonAbrirCorreoDeDistribucionModal" class="btn btn-warning">
									<span class="glyphicon glyphicon-send font-icon-1"></span>
									 Datos para correo de distribuci&oacute;n
									<span id="estadoFormCorreoDeDistribucionModal" class="glyphicon glyphicon-ban-circle font-icon-1" ></span>
								</button>							
							</c:if>							
						</div>										
					</div>
				
				</div>
				
				<br>
				
				<div class="row"> 				
 					<div class="col-sm-12">
						<label class="control-label">Tiempo dedicado:</label>
						<input type="text" id="tiempoDedicado" name="tiempoDedicado">					
					</div>

 				</div> 
  				
  				<br>
  				
  				<br>
  				
  				<div class="col-sm-12" id="divListaTareaAsignar">
  					
  					<c:if test="${not empty instanciasDeTareasDTOContinuanProceso}">
  					
  						<div class="row">
					
							<c:if test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR or instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionAND}">
								<div class="col-sm-1"></div>  							
							</c:if>
							
							<div class="col-sm-4"><strong>Tarea siguiente</strong></div>
							<div class="col-sm-4"><strong>Rol de tarea</strong></div>
						
							<c:choose>
								<c:when test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR or instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionAND}">
											<div class="col-sm-3"><strong>Usuario</strong></div>
								</c:when>
								<c:otherwise>		
											<div class="col-sm-4"><strong>Usuario</strong></div>			
								</c:otherwise>	
							</c:choose>	
					
							<%-- <div class="col-sm-3"><strong>Plazo</strong></div>--%>
						
						</div>
  					
  					</c:if>
					
  						
  					<br>
  						
  					<c:forEach items="${instanciasDeTareasDTOContinuanProceso}" var="instanciasDeTareaDTOContinuanProceso" varStatus="statusInstanciasDeTareaDTOContinuanProceso">
  						
						<div class="row asignaciones-tareas">  	
							
							<c:if test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR}">
								<div class="col-sm-1 div-radio-tarea">
									<c:choose>
										<c:when test="${statusInstanciasDeTareaDTOContinuanProceso.count eq 1}">			
											<input class="zoom-sgdp" type="radio" name="optRadioTareas" checked>					
										</c:when>
										<c:otherwise>		
											<input class="zoom-sgdp" type="radio" name="optRadioTareas">				
										</c:otherwise>	
									</c:choose>
								</div>	
							</c:if>
									
							<div class="col-sm-4">
							
								<c:if test = "${instanciasDeTareaDTOContinuanProceso.esRdsSnc eq true}">
									<c:set var="requisitoStyle" value="subproceso-rds-snc" />
								</c:if>
															
								<div class="well ${requisitoStyle}">
								
									<c:choose>
										
											<c:when test="${instanciasDeTareaDTOContinuanProceso.esRdsSnc eq true}">
												${instanciasDeTareaDTOContinuanProceso.nombreDeTarea}&nbsp;&nbsp;
												<button type="button" class="btn btn-warning"
												onclick="cargaCondicionesDeSatisfaccionParaMostrar('${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}',
				    																				'${instanciasDeTareaDTOContinuanProceso.nombreDeTarea}',
				    																				'${instanciasDeTareaDTOContinuanProceso.nombreDeProceso}')" >
													<spring:message code="tarea.esRDSNC"/>
												</button>
											</c:when>
											
											<c:otherwise>
											
												${instanciasDeTareaDTOContinuanProceso.nombreDeTarea}
											
											</c:otherwise>
										
									</c:choose>
								
								</div>
								
								<c:set var="requisitoStyle" value="" />
								
							</div>
							<div class="col-sm-4">								
								<div class="well">${instanciasDeTareaDTOContinuanProceso.nombreRolQueEjecuta}</div>
							</div>
							<c:choose>
								<c:when test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR}">
									<div class="col-sm-3">
										<select class="form-control usuarios-asignados" name="idUsuario${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}" id="${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}">
											<option selected="selected" value="">Seleccione Usuario</option>
											<c:forEach items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuarios}" var="posibleUsuario">
												<option value="${posibleUsuario}">${posibleUsuario}</option>																	
											</c:forEach>
											<c:forEach items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuariosFueraDeOficina}" var="posibleUsuarioFO">
												<option value="${posibleUsuarioFO}" disabled>${posibleUsuarioFO} (Fuera de oficina)</option>																	
											</c:forEach>
										</select>
									</div>
								</c:when>
								<c:otherwise>		
									<div class="col-sm-4">	
										<select class="form-control usuarios-asignados" name="idUsuario${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}" id="${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}">
											<option selected="selected" value="">Seleccione Usuario</option>
											<c:forEach items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuarios}" var="posibleUsuario">
												<option value="${posibleUsuario}">${posibleUsuario}</option>																
											</c:forEach>
											<c:forEach items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuariosFueraDeOficina}" var="posibleUsuarioFO">
												<option value="${posibleUsuarioFO}" disabled>${posibleUsuarioFO} (Fuera de oficina)</option>																	
											</c:forEach>
										</select>	  									
									</div>		
								</c:otherwise>	
							</c:choose>	
									 
							<div class="hide">
								<div class="form-group">									
									<div class='input-group date plazo-siguiente-tarea'>									
										<c:choose>
											<c:when test = "${instanciasDeTareaDTOContinuanProceso.fechaVencimientoUsuario ne null}">
												<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciasDeTareaDTOContinuanProceso.fechaVencimientoUsuario}" var="fechaPlazoFormatSiguiente" />		       				
											</c:when>
											<c:when test = "${instanciasDeTareaDTOContinuanProceso.fechaVencimientoInstanciaDeTarea ne null}">
												<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciasDeTareaDTOContinuanProceso.fechaVencimientoInstanciaDeTarea}" var="fechaPlazoFormatSiguiente" />
											</c:when>
										</c:choose>
			               				<input type='hidden' class="form-control validate[required] fecha-asignada" 
			               					value="${fechaPlazoFormatSiguiente}"
			               					id="plazo${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}" 
			               					name="plazo${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}" />			                   					
			               				<span class="input-group-addon">
			                   				<span class="glyphicon glyphicon-calendar"></span>
			               				</span>
			            			</div>
								</div>
							</div>	
												
						</div>
  						
  					</c:forEach>	
  					
  					<br>
  					
  					<div class="row">
			
					    <div class="col-sm-2">							
							<c:if test = "${instanciaDeTareaDTO.puedeDevolver eq true}">														
								<button id="botonDevolverTarea" 
										data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
										type="button" title="La tarea ser&aacute; retrocedida a: ${instanciaDeTareaDTO.usuarioAnterior}" 
										class="btn btn-labeled btn-primary">
		             				<span class="btn-label-default"><i class="glyphicon glyphicon-backward"></i></span>	Retroceder tarea 		             				
		             			</button>					
							</c:if>		
					    </div>
					    
					    <div class="col-sm-5">					    
					    	<c:choose>					    	
					    		<c:when test = "${instanciaDeTareaDTO.esUltimaTarea eq true and instanciaDeTareaDTO.puedeAvanzarProceso eq true}">
							        <button type="button" id="botonFinalizaProceso"
							        	data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
							        	data-idexpediente="${instanciaDeTareaDTO.idExpediente}"							        	
				       					data-nombreexpediente="${instanciaDeTareaDTO.nombreExpediente}"				       					
				       					data-distribuye="${instanciaDeTareaDTO.distribuye}"	
										class="btn btn-labeled btn-danger pull-right">
			               				Finalizar 		               				
			               			</button>
							    </c:when>		
							    <c:when test = "${instanciaDeTareaDTO.esUltimaTarea eq true and instanciaDeTareaDTO.puedeAvanzarProceso eq false}">
							        <button disabled type="button" id="botonFinalizaProceso" data-toggle="tooltip" title="No se han subido todos los documentos requeridos"
							        	data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
										class="btn btn-labeled btn-danger disabled pull-right">
			               				Finalizar 			               				
			               			</button>
							    </c:when>					    	
					    	</c:choose>					    
					    </div>		
					    
					    <div class="col-sm-5">			             			
			            	<c:choose>
					    		<c:when test = "${instanciaDeTareaDTO.puedeAvanzarProceso eq true and !empty instanciasDeTareasDTOContinuanProceso}">	
				    				<button id="botonEnviarTarea" 
				       			   		data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
				       					data-idexpediente="${instanciaDeTareaDTO.idExpediente}"	
				       					data-puedeavanzarprocesoconadvertenciavisacion="${instanciaDeTareaDTO.puedeAvanzarProcesoConAdvertenciaVisacion}"
				       					data-puedeavanzarprocesoconadvertenciafea="${instanciaDeTareaDTO.puedeAvanzarProcesoConAdvertenciaFEA}"				       					
				       					data-nombreexpediente="${instanciaDeTareaDTO.nombreExpediente}"				       					
				       					data-distribuye="${instanciaDeTareaDTO.distribuye}"				       									       									       								       			
										type="button" 
										class="btn btn-labeled btn-primary pull-right">
               							Enviar tarea <span class="btn-label-default"><i class="glyphicon glyphicon-forward"></i></span>	               						
               						</button>					    			
					    		</c:when>
					    		<c:when test = "${instanciaDeTareaDTO.puedeAvanzarProceso eq false and !empty instanciasDeTareasDTOContinuanProceso}">	
				    				<button disabled id="botonEnviarTarea" data-toggle="tooltip" title="No se han subido todos los documentos requeridos"
				       			   		data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
				       					data-idexpediente="${instanciaDeTareaDTO.idExpediente}"						       									       								       			
										type="button" 
										class="btn btn-labeled btn-primary disabled pull-right">
               							Enviar tarea <span class="btn-label-default"><i class="glyphicon glyphicon-forward"></i></span>	               						
               						</button>					    			
					    		</c:when>							    						   
							</c:choose>							
					    </div>			
					
					</div>	
  				
  				</div> 	 				
				  				
      		</div>
      		
    	</div>	
    		
	</div>

	<!-- Modal Subir Documento Adicional  -->
 			
	<c:import url="/WEB-INF/jsp/modals/subirDocumentoAdicional.jsp"></c:import>
 
	<!-- Modal Subir Documento Requerido  -->					
	<c:import url="/WEB-INF/jsp/modals/subirDocumentoRequerido.jsp"></c:import>	

	<c:url value="/verificarSession" var="sessionURL" />
	<c:url value="/" var="raizURL" />
	
<script>

var tareaTieneParametros = '${tareaTieneParametros}';

var inicializaBotonNotificarInstanciaDeProceso = function (){
	$("#botonNotificarInstanciaDeProceso").off('click').on('click', function () {
	    var urlNotificarInstanciaDeProceso = "${urlNotificarInstanciaDeProceso}";
	    console.log("urlNotificarInstanciaDeProceso: " + urlNotificarInstanciaDeProceso);
	    $.get("${sessionURL}", function(haySession) {
          console.log("haySession: " + haySession);
          if(haySession) {
              console.log("Inicio window.open");
              window.open(urlNotificarInstanciaDeProceso, "Notificar", 'width=750, height=720');
              console.log("Fin window.open");
          } else {
                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                              , function(){
                                    window.open('${raizURL}', '_blank');
                              }
                 );
          }
	    });
	});
};
var inicializaDatePlazoSiguienteTarea = function(){
	$(".plazo-siguiente-tarea").each(function() {
	    $(this).datetimepicker({
	          locale : 'es',
	          format : 'DD/MM/YYYY'
	    });
	});
};
var inicializaBotonDevolverTarea = function(){
	var urlRetrocedeProceso = $("#urlRetrocedeProceso").val();	
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();	
	$('#botonDevolverTarea').off('click').on('click', function () {		
		var haySessionB = true;
		$.get("${sessionURL}", function(haySession) {
            console.log("haySession: " + haySession);
            if(!haySession) {
            	haySessionB = false;
                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                    , function(){
                            window.open('${raizURL}', '_blank');                            
                    }
                );                
            } 
        });
		if(!haySessionB) {
			return;
		} 
		if ($("#commentarioEjecucionTarea").val().trim().length == 0) {
			$("#commentarioEjecucionTarea").validationEngine('showPrompt', 'Por favor ingrese un comentario antes de devolver la tarea', 'error');
		} else {
			var formData = new FormData();
			var comentario = $("#commentarioEjecucionTarea").val();
			var idInstanciaDeTareaSeleccionada = $(this).attr("data-idinstanciadetarea");
			var parametrosMapParaGuardarJSON = JSON.stringify($("#parametrosForm").serializeArray());
			var horasOcupadas = $("#duration-hours").val();
			var minutosOcupados = $("#duration-minutes").val();	
			if (horasOcupadas == "") {
				horasOcupadas = 0;
			}
			if (minutosOcupados == "") {
				minutosOcupados = 0;
			}
			if (horasOcupadas <= 0 && minutosOcupados <= 0) {
				$("#duration-hours").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
				$("#duration-minutes").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');
				return;
			}	
			formData.append("comentario", comentario);
			formData.append("idInstanciaDeTareaSeleccionada", idInstanciaDeTareaSeleccionada);
			formData.append("parametrosMapParaGuardarJSON", parametrosMapParaGuardarJSON);
			formData.append("horasOcupadas", horasOcupadas);
			formData.append("minutosOcupados", minutosOcupados);			
			var mensajeConfirmacionRetrocederTarea = "";		    
		    if (tareaTieneParametros == 'true') {
		    	mensajeConfirmacionRetrocederTarea = "¿Desea retroceder la tarea?<br><br><div style='color:red'> Recuerde que esta tarea tiene requisitos de satisfacci&oacute;n y/o salidas no conformes que deben ser verificados</div>";	
		    } else {
		    	mensajeConfirmacionRetrocederTarea = "¿Desea retroceder la tarea?";		    	
		    }			
			bootbox.confirm({
		    	title: "Retroceder tarea",
		        message: mensajeConfirmacionRetrocederTarea,
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
		        callback: function (result) {
		            console.log('El usuario selecciono: ' + result);
		            if (result == true) {
		            	$.ajax({
		            	    url: urlRetrocedeProceso,
		            	    type: 'POST',
		            	    data: formData,
		            	    async: true,
		            	    cache: false,
		            	    contentType: false,
		            	    processData: false,
		            	    beforeSend: function(xhr) {
		            	    	$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
		            	    },
		            	    success: function (returnData) {
		            	    	console.log("SUCCESS -- retrocedeProceso: ", returnData);	    	
		            	    },
		            	    error : function(e) {
		            			console.log("ERROR: ", e);			
		            		},
		            		done : function(e) {
		            			console.log("DONE");
		            		},
		            		complete : function(returnData) {
		            			console.log("COMPLETE -- retrocedeProceso: ", returnData.responseJSON );
		            			resetCondicioDeSatisFaccion();
		            			if (returnData.responseJSON.cssStatus == "alert alert-success") {	
		            				$.notify({
		                     			message: returnData.responseJSON.resultadoRetrocedeProceso
		                     		},{
		                     			type: 'success'
		                     		});
		            				actualizaTareasConCallBack("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true, 
				            				function() {
				            					$("#divTabsDetalleDeTarea").addClass('hide');
					            				$("#contenedorBEPrincipal").find(".cargando").remove();	
					            				$("html, body").animate({ scrollTop: 0 }, "slow");	
			            			});	            				           				
		            			} else {		
		            				$.notify({
		                     			message: returnData.responseJSON.resultadoRetrocedeProceso
		                     		},{
		                     			type: 'danger'
		                     		});
		            				if (returnData.responseJSON.recarga == true) {  
		            					actualizaTareasConCallBack("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true, 
					            				function() {
					            					$("#divTabsDetalleDeTarea").addClass('hide');
						            				$("#contenedorBEPrincipal").find(".cargando").remove();	
						            				$("html, body").animate({ scrollTop: 0 }, "slow");	
				            			});
				                    } else {
				                    	$("#contenedorBEPrincipal").find(".cargando").remove();	
						            }
		            			}		
		            		}
		            		
		            	});
				    }
		        }
		    });
		}				
	});
};

var inicializaBotonEnviarTarea = function(){
	var urlMueveProceso = $("#urlMueveProceso").val();	
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();
	$('#botonEnviarTarea').off('click').on('click', function () {		
		var haySessionB = true;
		$.get("${sessionURL}", function(haySession) {
            console.log("haySession: " + haySession);
            if(!haySession) {
            	haySessionB = false;
                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                    , function(){
                            window.open('${raizURL}', '_blank');
                    }
                );
            } 
        });
		if(!haySessionB) {
			return;
		}				
		if ($("#parametrosForm").length) {
			var validaFormTarea = $("#parametrosForm").validationEngine('validate');
			console.log("validaFormTarea: " + validaFormTarea);
			if (!validaFormTarea) {
				return;
			}
		}
		var distribuye = $(this).attr("data-distribuye");
		if (distribuye == "true" && $("#validoFormDistribucion").val()!="true") {
			$("#botonAbrirCorreoDeDistribucionModal").validationEngine('showPrompt', 'Por favor complete los datos de la distribuci&oacute;n', 'error');
			return;
		}	
		var cantidadDeOption = $('#divListaTareaAsignar :radio').length;
		var noHaSeleccionadoTodosLosUsuarios = false;
		var formData = new FormData();
		var asignacionesTareasArray = new Array();
		var docTieneFEA;
		$(".asignaciones-tareas").each(function (colIndex, c) {	    
	    	if ( (cantidadDeOption==0) || ( cantidadDeOption>0 && $(this).find(".div-radio-tarea input:radio:checked").val() == "on" ) )  {
				console.log('$(this).find(".usuarios-asignados option:selected").val(): ' + $(this).find(".usuarios-asignados option:selected").val());
	    		if($(this).find(".usuarios-asignados option:selected").val() == "") {
	    			noHaSeleccionadoTodosLosUsuarios = true;
		    	}
		    }     
	    });	    
		if ($("#commentarioEjecucionTarea").val().trim().length == 0) {
			$("#commentarioEjecucionTarea").validationEngine('showPrompt', 'Por favor ingrese un comentario antes de enviar la tarea', 'error');	
		} else if (noHaSeleccionadoTodosLosUsuarios == true) {
			bootbox.alert("Por favor seleccione el o los usuarios a asignar");
		} else {								
			var comentario = $("#commentarioEjecucionTarea").val();
			var idInstanciaDeTareaOrigen = $(this).attr("data-idinstanciadetarea");
			var avanzaRetrocede= "avanzarProceso";
			var idExpedienteContinuarProceso = $(this).attr("data-idexpediente");
			var reasigna = false;				
			var nombreExpediente = $(this).attr("data-nombreexpediente");			
			var horasOcupadas = $("#duration-hours").val();
			var minutosOcupados = $("#duration-minutes").val();
			if (horasOcupadas == "") {
				horasOcupadas = 0;
			}
			if (minutosOcupados == "") {
				minutosOcupados = 0;
			}
			if (horasOcupadas <= 0 && minutosOcupados <= 0) {
				$("#duration-hours").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
				$("#duration-minutes").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');
				return;
			}
		    $(".asignaciones-tareas").each(function (colIndex, c) {		    	
		    	if ( (cantidadDeOption==0) || ( cantidadDeOption>0 && $(this).find(".div-radio-tarea input:radio:checked").val() == "on" ) )  {
		    		if ($(this).find(".usuarios-asignados option:selected").text() != "") {
			    		asignacionesTareasArray.push({    		
			        		idInstanciaDeTarea : $(this).find(".usuarios-asignados").attr("id") ,
			        		usuariosAsignados : $(this).find(".usuarios-asignados option:selected").text(),
			        		fechaAsignada : $(this).find(".fecha-asignada").val()
			            }); 
			    	}
			    }	   
		    });   	    
		    var asignacionesTareasJSON = JSON.stringify(asignacionesTareasArray);
		    var parametrosMapParaGuardarJSON = JSON.stringify($("#parametrosForm").serializeArray());    
		    formData.append("comentario", comentario);
		    formData.append("idInstanciaDeTareaOrigen", idInstanciaDeTareaOrigen);
		    formData.append("avanzaRetrocede", avanzaRetrocede);
		    formData.append("idExpedienteContinuarProceso", idExpedienteContinuarProceso);
		    formData.append("reasigna", reasigna);
		    formData.append("asignacionesTareasJSON", asignacionesTareasJSON);
		    formData.append("parametrosMapParaGuardarJSON", parametrosMapParaGuardarJSON);
		    formData.append("horasOcupadas", horasOcupadas);
		    formData.append("minutosOcupados", minutosOcupados);
		    var puedeAvanzarProcesoConAdvertenciaVisacion = $(this).attr("data-puedeavanzarprocesoconadvertenciavisacion");
		    var puedeAvanzarProcesoConAdvertenciaFEA = $(this).attr("data-puedeavanzarprocesoconadvertenciafea");	    
		    var mensajeConfirmacionEnviaTarea = "";		    
		    if (tareaTieneParametros == 'true') {
		    	mensajeConfirmacionEnviaTarea = "¿Desea enviar su tarea?<br><br><div style='color:red'> Recuerde que esta tarea tiene requisitos de satisfacci&oacute;n y/o salidas no conformes que deben ser verificados</div>";	
		    } else {
		    	mensajeConfirmacionEnviaTarea = "¿Desea enviar su tarea?";		    	
		    }
		    if (distribuye == "true" && $("#validoFormDistribucion").val()=="true") {			   
		    	var correosDeDistribucionJSON = JSON.stringify($("#correosDeDistribucion").val());
		    	var idArchivosADistribuirArray = [];
		    	$('input[name="idArchivosADistribuirHiden"]').each(function() {   
		    	    idArchivosADistribuirArray.push(this.value);
		    	 });
		    	var idArchivosADistribuirJSON = JSON.stringify(idArchivosADistribuirArray);	
		    	formData.append("correosDeDistribucionJSON", correosDeDistribucionJSON);
		    	formData.append("asuntoCorreoDistribucion", $("#asuntoCorreoDistribucion").val());
		    	formData.append("idArchivosADistribuirJSON", idArchivosADistribuirJSON);		    	
		    }	
		    
		    if (puedeAvanzarProcesoConAdvertenciaVisacion == "true" && tareaTieneParametros == 'true') {
		    	mensajeConfirmacionEnviaTarea = "Esta es una tarea de visaci&oacuten, pero no ha visado todos los documentos. <br> ¿Desea enviar la tarea de todos modos?<br><br><div style='color:red'> Recuerde que esta tarea tiene requisitos de satisfacci&oacute;n y/o salidas no conformes que deben ser verificados</div>";
		    	formData.append("avanzaProcesoConAdvertenciaVisacion", true);
			} else if (puedeAvanzarProcesoConAdvertenciaVisacion == "true") {
				mensajeConfirmacionEnviaTarea = "Esta es una tarea de visaci&oacuten, pero no ha visado todos los documentos. <br> ¿Desea enviar la tarea de todos modos?";
		    	formData.append("avanzaProcesoConAdvertenciaVisacion", true);
			}

		    bootbox.confirm({
		    	title: "Enviar tarea",
		        message: mensajeConfirmacionEnviaTarea,
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
		        callback: function (result) {
		            console.log('El usuario selecciono: ' + result);
		            if (result == true) {
		            	$.ajax( {
		            	    url: urlMueveProceso,
		            	    type: 'POST',
		            	    data: formData,
		            	    async: true,
		            	    cache: false,
		            	    contentType: false,
		            	    processData: false,
		            	    beforeSend: function(xhr) {
		            	    	$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
		            	    },
		            	    success: function (returnData) {
		            	    	console.log("SUCCESS -- mueveProceso: ", returnData);	    	
		            	    },
		            	    error : function(e) {
		            			console.log("ERROR: ", e);
		            			$("#contenedorBEPrincipal").find(".cargando").remove();		    		            						
		            		},
		            		done : function(e) {
		            			console.log("DONE");
		            		},
		            		complete : function(returnData) {
		            			console.log("COMPLETE -- mueveProceso: ", returnData.responseJSON );
		            			resetCondicioDeSatisFaccion();
		            			if (returnData.responseJSON.respuestaMueveProceso == "OK") { 
		            				$.notify({
		                     			message: returnData.responseJSON.tareasUsuarios
		                     		},{
		                     			type: 'success'
		                     		});			            				
		            				actualizaTareasConCallBack("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true, 
				            				function() {
				            					$("#divTabsDetalleDeTarea").addClass('hide');
					            				$("#contenedorBEPrincipal").find(".cargando").remove();	
					            				$("html, body").animate({ scrollTop: 0 }, "slow");	
			            			});		            				
		            			} else {		
		            				$.notify({
		                     			message: returnData.responseJSON.respuestaMueveProceso
		                     		},{
		                     			type: 'danger'
		                     		});
		            				if (returnData.responseJSON.recarga == true) {
			            				actualizaTareasConCallBack("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true, 
					            				function() {
					            					$("#divTabsDetalleDeTarea").addClass('hide');
						            				$("#contenedorBEPrincipal").find(".cargando").remove();	
						            				$("html, body").animate({ scrollTop: 0 }, "slow");	
				            			});	
				                    } else {
				                    	$("#contenedorBEPrincipal").find(".cargando").remove();	
						            }							                    
		            			}		
		            		}
		            		
		            	});
				    } 
		        }
		    });
			
		}
	});
};
var inicializaBotonAnadirDocumentoOpcional = function() {
	$('#botonAnadirDocumentoOpcional').click(function() {
		$.get("${sessionURL}", function(haySession){
	    	if (haySession) {	    		
		    	var idExpediente = $("#botonAnadirDocumentoOpcional").attr("data-idexpediente");
		   		var idInstanciaDeTarea = $("#botonAnadirDocumentoOpcional").attr("data-idinstanciadetarea");
		   		var nombreExpediente = $("#botonAnadirDocumentoOpcional").attr("data-nombreexpediente");
		   		$("#botonSubirDocumentoAdicionalModal").attr('data-idexpediente', idExpediente);
		   		$("#botonSubirDocumentoAdicionalModal").attr('data-idinstanciadetarea', idInstanciaDeTarea);
		   		$("#botonSubirDocumentoAdicionalModal").attr('data-nombreexpediente', nombreExpediente);
		   		$("#nombreArchivoSpanDocumentoAdicionalModal" ).empty();       
		       	$('#nombreArchivoSpanDocumentoAdicionalModal').text("Seleccionar archivo");
		       	document.getElementById('formSubirDocumentoAdicionalModal').reset();
		       	$('.select2-subir-doc-adicional-modal').val('').change();
		       	$('.select2-subir-doc-adicional-modal-multiple').val('').change();
		       	$("#idTipoDeDocumentoAdicionalModal").val($('#idTipoDeDocumentoAdicionalModal option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();
		       	$('#idTipoDeDocumentoAdicionalModal').prop('disabled', 'disabled');
	    		$("#fechaDocumentoAdicionalModal").val(moment().format("DD/MM/YYYY"));
	    		$("#fechaRecepcionDocumentoAdicionalModal").val(moment().format("DD/MM/YYYY"));
		   		$("#subirDocumentoAdicionalModal").modal({backdrop: "static"});
	       } else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_blank');}
	             );
	       }
		});
	});
};
var inicializaBotonFinalizaProceso = function(){	
	var urlFinalizarProceso = $("#urlFinalizarProceso").val();
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();		
	$('#botonFinalizaProceso').off('click').on('click', function () {
		var haySessionB = true;
		$.get("${sessionURL}", function(haySession) {			
            console.log("haySession: " + haySession);
            if(!haySession) {
            	haySessionB = false;
                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                    , function(){
                            window.open('${raizURL}', '_blank');                            
                    }
                );                
            } 
        });
		if(!haySessionB) {
			return;
		}
		if ($("#parametrosForm").length) {
			var validaFormTarea = $("#parametrosForm").validationEngine('validate');
			console.log("validaFormTarea: " + validaFormTarea);
			if (!validaFormTarea) {
				return;
			}
        }
		var distribuye = $(this).attr("data-distribuye");
		if (distribuye == "true" && $("#validoFormDistribucion").val()!="true") {
			$("#botonAbrirCorreoDeDistribucionModal").validationEngine('showPrompt', 'Por favor complete los datos de la distribuci&oacute;n', 'error');
			return;
		}
		if ($("#commentarioEjecucionTarea").val().trim().length == 0) {
			$("#commentarioEjecucionTarea").validationEngine('showPrompt', 'Por favor ingrese un comentario antes de enviar la tarea', 'error');			
		} else {
			var idInstanciaDeTarea = $(this).attr("data-idinstanciadetarea");
			var comentario = $("#commentarioEjecucionTarea").val();
			var parametrosMapParaGuardarJSON = JSON.stringify($("#parametrosForm").serializeArray());	
            var nombreExpediente = $(this).attr("data-nombreexpediente");
            var idExpediente = $(this).attr("data-idexpediente");
            var horasOcupadas = $("#duration-hours").val();
            var minutosOcupados = $("#duration-minutes").val();
            if (horasOcupadas == "") {
				horasOcupadas = 0;
			}
			if (minutosOcupados == "") {
				minutosOcupados = 0;
			}
			if (horasOcupadas <= 0 && minutosOcupados <= 0) {
				$("#duration-hours").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
				$("#duration-minutes").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');
				return;
			}
			var parametrosMapParaGuardarJSON = JSON.stringify($("#parametrosForm").serializeArray());			
	        var formData = new FormData();
	        formData.append("idInstanciaDeTarea", idInstanciaDeTarea);
	        formData.append("comentario", comentario);
	        formData.append("parametrosMapParaGuardarJSON", parametrosMapParaGuardarJSON);
	        formData.append("horasOcupadas", horasOcupadas);
	        formData.append("minutosOcupados", minutosOcupados);
	        var distribuye = $(this).attr("data-distribuye");
	        if (distribuye == "true" && $("#validoFormDistribucion").val()=="true") {			   
                var correosDeDistribucionJSON = JSON.stringify($("#correosDeDistribucion").val());
                var idArchivosADistribuirArray = [];
                $('input[name="idArchivosADistribuirHiden"]').each(function() {   
                    idArchivosADistribuirArray.push(this.value);
                 });
                var idArchivosADistribuirJSON = JSON.stringify(idArchivosADistribuirArray);	
                formData.append("correosDeDistribucionJSON", correosDeDistribucionJSON);
                formData.append("asuntoCorreoDistribucion", $("#asuntoCorreoDistribucion").val());
                formData.append("idArchivosADistribuirJSON", idArchivosADistribuirJSON);		    	
            }
	        var mensajeFinalizaTarea = "";		    
		    if (tareaTieneParametros == 'true') {
		    	mensajeFinalizaTarea = "¿Desea finalizar la tarea?<br><br><div style='color:red'> Recuerde que esta tarea tiene requisitos de satisfacci&oacute;n y/o salidas no conformes que deben ser verificados</div>";	
		    } else {
		    	mensajeFinalizaTarea = "¿Desea finalizar la tarea?";		    	
		    }
			bootbox.confirm({
		    	title: "Finalizar Tarea",
		        message: mensajeFinalizaTarea,
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
		        callback: function (result) {
		            console.log('El usuario selecciono: ' + result);	          
		            console.log("urlFinalizarProceso: " + urlFinalizarProceso);
		            if (result == true) {
		            	$.ajax({
		            	    url: urlFinalizarProceso,
		            	    type: 'POST',
		            	    async: true,
		            	    cache: false,
		            	    data: formData,
		            	    contentType: false,
		            	    processData: false,
		            	    success: function (returnData) {
		            	    	console.log("SUCCESS -- finalizarProceso: ", returnData);	    	
		            	    },
		            	    beforeSend: function(xhr) {
		                        $("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
		                    },
		            	    error : function(e) {
		            			console.log("ERROR: ", e);		            			
		                        $("#contenedorBEPrincipal").find(".cargando").remove();			
		            		},
		            		done : function(e) {
		            			console.log("DONE");
		            		},
		            		complete : function(returnData) {
		            			console.log("COMPLETE -- finalizarProceso: ", returnData );
		            			resetCondicioDeSatisFaccion();
		            			if (returnData.responseJSON.respuestaFinalizaProceso == "OK") {		            				
		            				$.notify({
		                     			message: 'Se finalizo correctamente!'
		                     		},{
		                     			type: 'success'
		                     		});	
		            				actualizaTareasConCallBack("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true, 
				            				function() {
				            					$("#divTabsDetalleDeTarea").addClass('hide');
					            				$("#contenedorBEPrincipal").find(".cargando").remove();	
					            				$("html, body").animate({ scrollTop: 0 }, "slow");	
			            			});
		            			} else {		
		            				$.notify({
		                     			message: returnData.responseJSON.respuestaFinalizaProceso
		                     		},{
		                     			type: 'danger'
		                     		});
		            				if (returnData.responseJSON.recarga == true) {
		            					actualizaTareasConCallBack("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true, 
					            				function() {
					            					$("#divTabsDetalleDeTarea").addClass('hide');
						            				$("#contenedorBEPrincipal").find(".cargando").remove();	
						            				$("html, body").animate({ scrollTop: 0 }, "slow");	
				            			});	
				                    } else {
				                    	$("#contenedorBEPrincipal").find(".cargando").remove();	
						            }
		                     		
		            			}		
		            		}
		            		
		            	});
				    }
		        }
		    });
		}			
	});
};

var inicializaBotonEditarDocumentoEnviadoEnEjecucionTarea = function(){
	
	$('.boton-editar-documento-enviado').click(function() {

		var codigoMimeType = $(this).attr("data-codigomimetype"); 
		var linkSharpoint = $(this).attr("data-linksharpoint");

		console.log("codigoMimeType: " + codigoMimeType);
		console.log("linkSharpoint: " + linkSharpoint);
		
		$.get("${sessionURL}", function(haySession) {
	    	if (haySession) {  	
				if(!(window.ActiveXObject)) {			
					if(codigoMimeType==1) { //word
						var miwindow = window.open("ms-word:ofe|u|"+linkSharpoint,"_self");
						miwindow.locate;
					}
					else if (codigoMimeType==2) { //excel
						var miwindow = window.open("ms-excel:ofe|u|"+linkSharpoint,"_self");
						miwindow.locate;
					}
			   } else {
				   if(codigoMimeType==1) { //word								
						var wdApp = new ActiveXObject("Word.Application");
						wdApp.Visible = true;
						var wdDoc = wdApp.Documents;
						wdDoc.Open(linkSharpoint);
					}
					else if (codigoMimeType==2) { //excel							
						var wdApp = new ActiveXObject("Excel.Application");
						wdApp.Visible = true;
						var wb = wdApp.Workbooks;
						wb.Open(linkSharpoint);
					}
			   }
	       } else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_blank');}
	             );
	       }
		});
	});
};

var inicializaBotonVerDiagramaEnNuevaVentanaET = function (){
  $("#botonVerDiagramaEnNuevaVentanaET").off('click').on('click', function () {
    var urlDiagramaSubProcesoBoton = "${urlDiagramaSubProcesoBoton}";
    console.log("urlDiagramaSubProcesoBoton: " + urlDiagramaSubProcesoBoton);
    $.get("${sessionURL}", function(haySession){
          console.log("haySession: " + haySession);
          if(haySession) {
              //console.log("Inicio window.open");
              window.open(urlDiagramaSubProcesoBoton, "SubProceso", 'width=1700, height=900');
              console.log("Fin window.open");
          } else {
                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                              , function(){
                                    window.open('${raizURL}', '_blank');
                              }
                 );
          }
    });
	});
};

$( ".option-target" ).change(function() {  
    var until =  $(this).parent().nextUntil('.div-comentario');
    //console.log(until.length);
    //console.log($(this).val());
    if (until.length<1) {                
        //console.log($(this).parent().next().find( "input[type=text]" ).val());
        if ($(this).val()=='NO') {
            $(this).parent().next().find( "input[type=text]" ).addClass('validate[required]');
        } else {
            $(this).parent().next().find( "input[type=text]" ).removeClass('validate[required]');
        }
    } else {
        console.log(until.next().find( "input[type=text]" ).val());
        if ($(this).val()=='NO') {
            until.next().find( "input[type=text]" ).addClass('validate[required]');
        } else {
            until.next().find( "input[type=text]" ).removeClass('validate[required]');
        }
    }            
});

function abrirCorreoDeDistribucionModal(idExpediente, idInstanciaDeTarea, nombreExpediente) {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
        	var urlGetDetalleDeExpedienteEnDistribucion = $("#urlGetDetalleDeExpedienteEnDistribucion").val();	
        	$("#correoDeDistribucionH4").empty();
        	$("#correoDeDistribucionH4").append('Distribuci&oacute;n: ' + nombreExpediente);
        	$('#correoDeDistribucionModal').modal({backdrop: 'static', keyboard: false});
        	$("#detalleDeExpedienteEnDistribucionModal").css("position", "relative").prepend($("<div />").addClass("cargando"));
        	$('#detalleDeExpedienteEnDistribucionModal').load(urlGetDetalleDeExpedienteEnDistribucion + "/" + idExpediente + "/" 
        			+ idInstanciaDeTarea + "/" + nombreExpediente, function() {
        				$('#correosDeDistribucionHiden option').each(function() {      
        			        $("#correosDeDistribucion option[value='"+$(this).val()+"']").attr("selected", true);			       
        			    });        				
        				$(".select2-correos-de-distribucion").select2({
        				    theme: "bootstrap",
        		 		    dropdownParent: $("#formCorreoDeDistribucionModal"),
        		 		    language: "es"
        		 		});
        				$('#idsArchivosADistribuir input').each(function() { 
        				    var inputGuardado = $(this).val();   
        				    $('#checkBoxesADistribuir input').filter(function(){      
        				        console.log(this.value + " / " + inputGuardado); 
        				        var b = (this.value==inputGuardado);
        				        console.log(b);
        				        return b;
        				    }).prop("checked", true);
        				});
        				$("#detalleDeExpedienteEnDistribucionModal").find(".cargando").remove();        				
        	});	
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  });		
}

$("#condicionesDeSatisfaccionModal").on("hidden.bs.modal", function () {
	$("#botonCargaCondicionesDeSatisfaccion").removeClass("btn-danger");
	$("#botonCargaCondicionesDeSatisfaccion").addClass("btn-primary");	
	cargoRDSSNC = true;
});

function cargaCondicionesDeSatisfaccion(idInstanciaDeTarea, nombreExpediente, nombreDeProceso) {

	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();

	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	   			var urlGetCondicionesDeSatisfaccionPorIdInstanciaDeTarea = $("#urlGetCondicionesDeSatisfaccionPorIdInstanciaDeTarea").val();
	    		$("#condicionesDeSatisfaccionH4").empty();
	    		$("#condicionesDeSatisfaccionH4").append('Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes: ' + nombreDeProceso + ' ( ' + nombreExpediente + '  ) ');
	    		$('#condicionesDeSatisfaccionModal').modal({backdrop: 'static', keyboard: false});
	    		if (cargoRDSSNC == false) {
	    			$('#condicionesDeSatisfaccionDiv').load(urlGetCondicionesDeSatisfaccionPorIdInstanciaDeTarea + "/" + idInstanciaDeTarea );
	    		} else {	    			
	    			$("#botonCargaCondicionesDeSatisfaccion").removeClass("btn-danger");
	    			$("#botonCargaCondicionesDeSatisfaccion").addClass("btn-primary");
	    			$("#estadoCargaCondicionesDeSatisfaccionModal").removeClass("glyphicon-ban-circle");
	    			$("#estadoCargaCondicionesDeSatisfaccionModal").addClass("glyphicon-ok-circle");
	    		}	    		
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_blank');
	                          }
	             );
	      }
	      cargoRDSSNC = true;
	}); 
	
}

function seteaBotonCargaCondicionesDeSatisfaccion() {
	if ($("#botonCargaCondicionesDeSatisfaccion").length > 0 && cargoRDSSNC == true) {
		$("#botonCargaCondicionesDeSatisfaccion").removeClass("btn-danger");
		$("#botonCargaCondicionesDeSatisfaccion").addClass("btn-primary");	
	}
}

function inicializaTiempoDedicado() {
	$("#tiempoDedicado").durationPicker({
		  hours: {
		    label: "h",
		    min: 0
		  },
		  minutes: {
		    label: "m",
		    min: 0,
		    max: 59
		  },
		  classname: 'form-control',
		  type: 'number',
		  responsive: true
	});
}

function agregaValidityTiempoDedicado() {
	$("#duration-hours").attr("oninput", "validity.valid||(value='');");
	$("#duration-minutes").attr("oninput", "validity.valid||(value='');");
}

function getUltimoArchivoInstDeTareaFirmado(idInstanciaDeTarea) {	
	var idInstanciaDeTareaSeleccionada = $('#documentosDeSalidaDiv').attr("data-idinstanciadetarea");	
	if ($('#documentosDeSalidaDiv').length >=1 && idInstanciaDeTareaSeleccionada == idInstanciaDeTarea) {
		//console.log('Ejecutando cada 3 segundos inicio');
	    //console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	    var urlGetDocumentosDeSalida = $("#urlGetDocumentosDeSalida").val();
	    $('#documentosDeSalidaDiv').load(urlGetDocumentosDeSalida + "?idInstanciaDeTarea=" + idInstanciaDeTarea );  
	    //console.log('Ejecutando cada 3 segundos fin');
	}
}
  
function stopInterval(id) {
    //console.log("Deteniendo despues de 1 minuto -- id: " + id);
    clearInterval(id);
}

function consultaEstadoDocumentoFirmado(idInstanciaDeTarea) {	
	var intervalID = setInterval(getUltimoArchivoInstDeTareaFirmado, 3000, idInstanciaDeTarea);	
	//console.log('intervalID: ' + intervalID);
	setTimeout(stopInterval, 60000, intervalID);
}

$(document).ready(function() {		
	if( $('#botonDevolverTarea').length != 0 ) {
		new Tippy('#botonDevolverTarea');
	}
	$(seteaBotonCargaCondicionesDeSatisfaccion);
	$(inicializaDatePlazoSiguienteTarea);
	$(inicializaBotonEnviarTarea);	
	$(inicializaBotonDevolverTarea);
	$(inicializaBotonAnadirDocumentoOpcional);
	$(inicializaBotonFinalizaProceso);
	$(inicializaBotonEditarDocumentoEnviadoEnEjecucionTarea);
	$(inicializaBotonNotificarInstanciaDeProceso);
	$(inicializaBotonVerDiagramaEnNuevaVentanaET);
	$(inicializaTiempoDedicado);
	$(agregaValidityTiempoDedicado);
});



</script>
	
<!-- Modal Subir Archivo-->

<c:import url="/WEB-INF/jsp/modals/subirArchivo.jsp"></c:import>	

<!-- Modal Retrocede Proceso-->

<c:import url="/WEB-INF/jsp/modals/retrocedeProceso.jsp"></c:import>

<!-- Modal Detalle Documento-->

<%-- 
<c:import url="/WEB-INF/jsp/modals/detalleDeDocumento.jsp"></c:import>
--%>
<!-- Firma Avanzada-->

<%-- <c:import url="/WEB-INF/jsp/modals/firmaAvanzada.jsp"></c:import>--%>

<c:import url="/WEB-INF/jsp/modals/infoDeProceso.jsp"></c:import>

<%@ page import="cl.gob.scj.sgdp.config.Constantes"%>
<%@ page import="cl.gob.scj.sgdp.tipos.BifurcacionType"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />
<c:set var="tipoBifurcacionAND"
	value="<%=BifurcacionType.AND.getNombreTipoDeBifurcacion()%>" />
<c:set var="tipoBifurcacionOR"
	value="<%=BifurcacionType.OR.getNombreTipoDeBifurcacion()%>" />
<c:set var="requisitoStyle" value="" />

<c:url
	value="http://${urlFuncPhp}/proceso/bpm/notificar.php?idproc=${instanciaDeProcesoDTO.nombreExpediente}"
	var="urlNotificarInstanciaDeProceso" />
<c:url
	value="http://${urlFuncPhp}/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}&idInsProc=${instanciaDeTareaDTO.idInstanciaDeProceso}"
	var="urlDiagramaSubProcesoBoton" />

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<input type="hidden" id="idInstanciaDeTarea" name="idInstanciaDeTarea"
	value="${instanciaDeTareaDTO.idInstanciaDeTarea}" />
	
<input type="hidden" id="numeroDeIteracionDeLaInstanciaDeTarea" name="numeroDeIteracionDeLaInstanciaDeTarea"
	value="${numeroDeIteracionDeLaInstanciaDeTarea}" />

<c:choose>
	<c:when test="${instanciaDeTareaDTO.fechaVencimientoUsuario ne null}">
		<fmt:formatDate pattern="${FORMATO_FECHA}"
			value="${instanciaDeTareaDTO.fechaVencimientoUsuario}"
			var="fechaPlazoFormat" />
	</c:when>
	<c:when
		test="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea ne null}">
		<fmt:formatDate pattern="${FORMATO_FECHA}"
			value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}"
			var="fechaPlazoFormat" />
	</c:when>
</c:choose>

<fmt:formatDate pattern="${FORMATO_FECHA}"
	value="${instanciaDeProcesoDTO.fechaVencimiento}"
	var="fechaPlazoInstProcesoFormat" />

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

<%-- Inicio Descripción --%>

<div class="col-sm-4">

	<br>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<font size="2"><strong>Expediente:</strong>
				${instanciaDeTareaDTO.nombreExpediente}</font>
		</div>
	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<font size="2"><strong>SubProceso:</strong>
				${instanciaDeTareaDTO.nombreDeProceso}</font>
		</div>
	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<font size="2"><strong>Plazo m&aacute;ximo del
					SubProceso :</strong> ${fechaPlazoInstProcesoFormat}</font>
		</div>
	</div>

	<div class="row espacio-entre-row">

		<div class="col-sm-12">
			<font size="2"><strong>Tarea:</strong>
				${instanciaDeTareaDTO.nombreDeTarea}</font>
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

									/*
									 if (data.mensaje == "OK"){
									$.notify({
										message: 'Se ha seguido Correctamente'
									},{
										type: 'success'
									}); 
									   
									 }else{
									 
									 */
							
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
									,"${instanciaDeTareaDTO.urlControl}"
									,"${instanciaDeTareaDTO.idEstadoTarea}");
							// notificacionSeguimiento();
							
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
													,"${instanciaDeTareaDTO.urlControl}"
													,"${instanciaDeTareaDTO.idEstadoTarea}");
										
										// $("#divTabsDetalleDeTarea").addClass('hide');			
										//	notificacionSeguimiento();
											
										},
						
									});
				        }
				    }
				});			
				}


			var inicializaBotonNotificarInstanciaDeProcesoForm = function (){
				$("#botonNotificarInstanciaDeProcesoForm").off('click').on('click', function () {
				    var urlNotificarInstanciaDeProceso = "${urlNotificarInstanciaDeProceso}";
				    console.log("urlNotificarInstanciaDeProceso: " + urlNotificarInstanciaDeProceso);
				    $.get("${sessionURL}", function(haySession){
				          console.log("haySession: " + haySession);
				          if(haySession) {
				              console.log("Inicio window.open");
				              window.open(urlNotificarInstanciaDeProceso, "Notificar", 'width=750, height=720');
				              console.log("Fin window.open");
				          } else {
				                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
				                              , function(){
				                                    window.open('${raizURL}', '_blank');
				                              }
				                 );
				          }
				    });
				});					
			};

			$(document).ready(function() {
				$(inicializaBotonNotificarInstanciaDeProcesoForm);		
			});
			
			
			</script>

	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<font size="2"><strong>Plazo m&aacute;ximo tarea:</strong>
				${fechaPlazoFormat}</font>
		</div>
	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<font size="2"><strong>Rol:</strong>
				${instanciaDeTareaDTO.nombreRolQueEjecuta}</font>
		</div>
	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<font size="2"><strong>De:</strong>
				${instanciaDeTareaDTO.idUsuarioQueAsigna}</font>
		</div>
	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<c:choose>
				<c:when
					test="${not empty instanciaDeProcesoDTO.comentarioSolicitudCreacionExpediente and instanciaDeTareaDTO.puedeDevolver ne true}">
					<font size="2"><strong>Comentario:</strong>
						${instanciaDeProcesoDTO.comentarioSolicitudCreacionExpediente}</font>
				</c:when>
				<c:otherwise>
					<font size="2"><strong>Comentario:</strong>
						${ultimoComentario}</font>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<%-- Documentos requeridos validar que solo salga con los documentos obligatios --%>

	<%--
		    <div class="row">
				<div class="col-sm-12">
					<h4>Documentos Requeridos</h4>
				</div>
			</div>	
		
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-condensed ancho-tabla-documentos-enviados">
						<thead>
					    	<tr>
					        	<th>Nombre</th>
					        	<th>Tipo</th>
					        	<th>Añadir</th>
					      	</tr>
					    </thead>
					    <tbody>
					    
					    </tbody>
					</table>
				</div>
			</div>
			--%>

	<script type="text/javascript">
			
			  function cargaDocumentoRequeridoFormulario(){
				  
				  var contextPath = "${pageContext.request.contextPath}";

				  var idInstanciaDeTarea = '<c:out value="${instanciaDeTareaDTO.idInstanciaDeTarea}"/>'; 
				  
				  var urlControldocumentoRequeridoFormulario =  contextPath + "/documentoRequeridoFormulario"
				  									+ "?idInstanciaDeTarea=" + idInstanciaDeTarea;
				  					  
			        $('#documentoRequeridoFormulario').each(function(){        	 
				      	  $(this).fadeOut("slow").load(urlControldocumentoRequeridoFormulario, function() {			        			        	
				        }).fadeIn('slow');
				    });
				    
			  } 
					
			 $(document).ready(function() {
				 cargaDocumentoRequeridoFormulario();
			 });
			

			</script>

	<div id="documentoRequeridoFormulario"></div>

	<%--  --------------------------------------------------------------------------------------        --%>


	<c:if
		test="${!empty detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea}">

		<%-- <div class="row">
				<div class="col-sm-12">
					<h4>Documentos enviados</h4>
				</div>
			</div>--%>

		<%-- Se comenta porque aqui no se utiliza 
					
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
							    		<button type="button"
											class="btn btn-primary"
											onclick='descargaArchivo("<c:url value='getArchivoPorId/${detalleDeArchivoDTOAnterior.idArchivo}'/>")'
											data-iddocumento="${detalleDeArchivoDTOAnterior.idArchivo}">
											<span class="fa fa-download font-icon-2 "></span>
										</button>&nbsp;

										<c:if test = "${detalleDeArchivoDTOAnterior.esEditable eq true}">
											<a href="#" class="btn btn-primary btn-sm boton-editar-documento-enviado" id="botonEditarDocumentoEnviadoEnEjecucionTarea" title="Editar documento"
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
			
			--%>


	</c:if>
	<br>
</div>


<div class="col-sm-6">

	<br>
	<div class="div-iframe-resize">
		<iframe class="iframe-resize" scrolling="yes"
			src='http://${urlFuncPhp}/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}&idInsProc=${instanciaDeTareaDTO.idInstanciaDeProceso}'></iframe>
	</div>

	<div class="col-sm-4"></div>

	<div class="col-sm-4">
		<button type="button"
			class="btn btn-default btn-sm boton-amplicar-diagrama"
			id="botonVerDiagramaEnNuevaVentanaApp">Ampliar Diagrama</button>
	</div>

</div>

<div class="col-sm-2">

	<br>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<c:choose>
				<c:when test="${tieneSeguimiento == 0}">
					<button type="button" class="botonSeguir" id="botonSiguiendo"
						onclick="seguir(${instanciaDeProcesoDTO.idInstanciaDeProceso})">
						Seguir</button>
				</c:when>
				<c:otherwise>
					<button type="button" class="botonSiguiendo" id="botonSeguir"
						onclick="dejarSeguir(${instanciaDeProcesoDTO.idInstanciaDeProceso})">
						Siguiendo</button>
				</c:otherwise>
			</c:choose>
			<button type="button" class="btn btn-default btn-sm boton-notificar"
				id="botonNotificarInstanciaDeProceso">Notificar</button>
		</div>
	</div>

	<div class="row espacio-entre-row">
		<div class="col-sm-12">
			<button type="button" class="botonVinculaciones"
				id="botonVinculaciones"
				onclick="vinculaciones('${instanciaDeProcesoDTO.idExpediente}', '${instanciaDeProcesoDTO.nombreExpediente}')">
				Vinculaciones</button>
		</div>
	</div>

</div>

<%-- Fin Descripción Inicio --%>

<%-- Inicio carga Formulario Sistemas satelites --%>
<div class="col-sm-12">
	<script>
				
				  var iniciarlizarFormAplicacion = function(){
		
						$("#iframeDivDetalleDeTareaDentro").removeClass("hide");
						
		                var urlControl = '<c:out value="${urlControl}"/>'+'/${idArchivosInstTareasAnteriores}'+'?nombreExpediente=${instanciaDeProcesoDTO.nombreExpediente}'; 
					  
						$("<iframe id='myId'></iframe>").appendTo("#iframeDivDetalleDeTareaDentro")
					    .attr('src', urlControl)
					    .attr('class', "embed-responsive-item col-sm-12")
					    .load(function() 
					     {        
		       			$('html, body').animate({scrollTop: $("#iframeDivDetalleDeTareaDentro").offset().top}, 2000);
					        
					     });
						
					};
					
		
				 $(document).ready(function() {
					 $(iniciarlizarFormAplicacion);
				 });
		
			</script>


	<div class="embed-responsive embed-responsive-16by9 row hide"
		id="iframeDivDetalleDeTareaDentro"></div>
</div>
<%-- Fin carga Formulario Sistemas satelites --%>



<div class="col-sm-12 hide" id="divDetalleDeTareaDesdeAppContenido">
	<br> <br>
	<div class="panel panel-primary">
		<div class="panel-heading">Enviar Tarea</div>
		<div class="panel-body back-panel">

			<div class="col-sm-12">

				<label for="commentarioEjecucionTareaDesdeApp">Comentario:</label>

				<textarea class="form-control" rows="4"
					id="commentarioEjecucionTareaDesdeApp"></textarea>

			</div>

			<div class="col-sm-12" id="tiempoDedicadoDivDesdeAPP">

				<label class="control-label" for="tiempoDedicadoDesdeAPP">Tiempo
					dedicado:</label> <input type="text" id="tiempoDedicadoDesdeAPP"
					name="tiempoDedicadoDesdeAPP"> <br>

			</div>

			<div class="col-sm-12" id="divListaTareaAsignarDesdeApp">

				<c:if test="${not empty instanciasDeTareasDTOContinuanProceso}">

					<div class="row">

						<c:if
							test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR or instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionAND}">
							<div class="col-sm-1"></div>
						</c:if>

						<div class="col-sm-4">
							<strong>Tarea siguiente</strong>
						</div>
						<div class="col-sm-4">
							<strong>Rol de tarea</strong>
						</div>

						<c:choose>
							<c:when
								test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR or instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionAND}">
								<div class="col-sm-3">
									<strong>Usuario</strong>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-4">
									<strong>Usuario</strong>
								</div>
							</c:otherwise>
						</c:choose>

					</div>

				</c:if>


				<br>

				<c:forEach items="${instanciasDeTareasDTOContinuanProceso}"
					var="instanciasDeTareaDTOContinuanProceso"
					varStatus="statusInstanciasDeTareaDTOContinuanProceso">

					<div class="row asignaciones-tareas-desde-app">

						<c:if
							test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR}">
							<div class="col-sm-1 div-radio-tarea-desde-app">
								<c:choose>
									<c:when
										test="${statusInstanciasDeTareaDTOContinuanProceso.count eq 1}">
										<input class="zoom-sgdp" type="radio"
											name="optRadioTareasDesdeApp" checked>
									</c:when>
									<c:otherwise>
										<input class="zoom-sgdp" type="radio"
											name="optRadioTareasDesdeApp">
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>

						<div class="col-sm-4">
							<c:if
								test="${instanciasDeTareaDTOContinuanProceso.esRdsSnc eq true}">
								<c:set var="requisitoStyle" value="subproceso-rds-sncnc" />
							</c:if>

							<div class="well ${requisitoStyle}">

								<c:choose>

									<c:when
										test="${instanciasDeTareaDTOContinuanProceso.esRdsSnc eq true}">
														${instanciasDeTareaDTOContinuanProceso.nombreDeTarea}&nbsp;&nbsp;
														<button type="button" class="btn btn-warning"
											onclick="cargaCondicionesDeSatisfaccionParaMostrar('${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}',
						    																				'${instanciasDeTareaDTOContinuanProceso.nombreDeTarea}',
						    																				'${instanciasDeTareaDTOContinuanProceso.nombreDeProceso}')">
											<spring:message code="tarea.esRDSNC" />
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
							<c:when
								test="${instanciaDeTareaDTO.tipoDeBifurcacion eq tipoBifurcacionOR}">
								<div class="col-sm-3">
									<select class="form-control usuarios-asignados-desde-app"
										name="idUsuario${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}"
										id="${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}">
										<option selected="selected" value="">Seleccione
											Usuario</option>
										<c:forEach
											items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuarios}"
											var="posibleUsuario">
											<option value="${posibleUsuario}">${posibleUsuario}</option>
										</c:forEach>
										<c:forEach
											items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuariosFueraDeOficina}"
											var="posibleUsuarioFO">
											<option value="${posibleUsuarioFO}" disabled>${posibleUsuarioFO}
												(Fuera de oficina)</option>
										</c:forEach>
									</select>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-4">
									<select class="form-control usuarios-asignados-desde-app"
										name="idUsuario${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}"
										id="${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}">
										<option selected="selected" value="">Seleccione
											Usuario</option>
										<c:forEach
											items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuarios}"
											var="posibleUsuario">
											<option value="${posibleUsuario}">${posibleUsuario}</option>
										</c:forEach>
										<c:forEach
											items="${instanciasDeTareaDTOContinuanProceso.posiblesIdUsuariosFueraDeOficina}"
											var="posibleUsuarioFO">
											<option value="${posibleUsuarioFO}" disabled>${posibleUsuarioFO}
												(Fuera de oficina)</option>
										</c:forEach>
									</select>
								</div>
							</c:otherwise>
						</c:choose>

						<div class="hide">
							<div class="form-group">
								<div class='input-group date plazo-siguiente-tarea-desde-app'>
									<c:choose>
										<c:when
											test="${instanciasDeTareaDTOContinuanProceso.fechaVencimientoUsuario ne null}">
											<fmt:formatDate pattern="${FORMATO_FECHA}"
												value="${instanciasDeTareaDTOContinuanProceso.fechaVencimientoUsuario}"
												var="fechaPlazoFormatSiguiente" />
										</c:when>
										<c:when
											test="${instanciasDeTareaDTOContinuanProceso.fechaVencimientoInstanciaDeTarea ne null}">
											<fmt:formatDate pattern="${FORMATO_FECHA}"
												value="${instanciasDeTareaDTOContinuanProceso.fechaVencimientoInstanciaDeTarea}"
												var="fechaPlazoFormatSiguiente" />
										</c:when>
									</c:choose>
									<input type='hidden'
										class="form-control validate[required] fecha-asignada-desde-app"
										value="${fechaPlazoFormatSiguiente}"
										id="plazoDesdeApp${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}"
										name="plazo${instanciasDeTareaDTOContinuanProceso.idInstanciaDeTarea}" />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>

					</div>

				</c:forEach>

				<br>

				<div class="row">

					<div class="col-sm-2">
						<c:if test="${instanciaDeTareaDTO.puedeDevolver eq true}">
							<button id="botonDevolverTareaDesdeApp"
								data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
								type="button" class="btn btn-labeled btn-primary">
								<span class="btn-label-default"><i
									class="glyphicon glyphicon-backward"></i></span> Retroceder tarea
							</button>
						</c:if>
					</div>

					<div class="col-sm-5">
						<c:choose>
							<c:when test="${instanciaDeTareaDTO.esUltimaTarea eq true}">
								<button type="button" id="botonFinalizaProcesoDesdeApp"
									data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
									class="btn btn-labeled btn-danger pull-right">
									Finalizar</button>
							</c:when>
						</c:choose>
					</div>

					<div class="col-sm-5">
						<c:choose>
							<c:when test="${!empty instanciasDeTareasDTOContinuanProceso}">
								<button id="botonEnviarTareaDesdeApp"
									data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
									data-idexpediente="${instanciaDeTareaDTO.idExpediente}"
									data-puedeavanzarprocesoconadvertenciavisacion="${instanciaDeTareaDTO.puedeAvanzarProcesoConAdvertenciaVisacion}"
									data-puedeavanzarprocesoconadvertenciafea="${instanciaDeTareaDTO.puedeAvanzarProcesoConAdvertenciaFEA}"
									type="button" class="btn btn-labeled btn-primary pull-right">
									Enviar tarea <span class="btn-label-default"><i
										class="glyphicon glyphicon-forward"></i></span>
								</button>
							</c:when>
						</c:choose>
					</div>

				</div>

			</div>

			<script>
				
					var inicializaDatePlazoSiguienteTareaDesdeApp = function(){
						$(".plazo-siguiente-tarea-desde-app").each(function() {
						    $(this).datetimepicker({
						          locale : 'es',
						          format : 'DD/MM/YYYY'
						    });
						});
					};
					
					var inicializaBotonDevolverTareaDesdeApp = function(){
					var urlRetrocedeProceso = $("#urlRetrocedeProceso").val();	
					var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
					var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();	
					
					$('#botonDevolverTareaDesdeApp').click(function() {						
						var haySessionB = true;
						$.get("${sessionURL}", function(haySession) {
				            console.log("haySession: " + haySession);
				            if(!haySession) {
				            	haySessionB = false;
				                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
				                    , function(){
				                            window.open('${raizURL}', '_blank');                            
				                    }
				                );                
				            } 
				        });
						if(!haySessionB) {
							return;
						}						
						if ($("#commentarioEjecucionTareaDesdeApp").val().trim().length == 0) {
							bootbox.alert("Por favor ingrese un comentario antes de devolver la tarea");
						} else {
							var formData = new FormData();
							var comentario = $("#commentarioEjecucionTareaDesdeApp").val();
							var idInstanciaDeTareaSeleccionada = $(this).attr("data-idinstanciadetarea");
							//var horasOcupadas = $("#duration-hours").val();
							//var minutosOcupados = $("#duration-minutes").val();														
							var tiempoDeDicadoDivContainerDesdeAPP = $("#tiempoDedicadoDivDesdeAPP");
							var durationArray = tiempoDeDicadoDivContainerDesdeAPP.find(".durationpicker-duration");
							var horasOcupadas;
							var minutosOcupados;
							$(durationArray).each(function() {
								if ($(this).attr('id') == "duration-hours") {
									horasOcupadas = $(this).val();
									
								}
								if ($(this).attr('id') == "duration-minutes") {
									minutosOcupados = $(this).val();
								}
							});	
							formData.append("comentario", comentario);
							formData.append("idInstanciaDeTareaSeleccionada", idInstanciaDeTareaSeleccionada);
							formData.append("horasOcupadas", horasOcupadas);
							formData.append("minutosOcupados", minutosOcupados);
							if (horasOcupadas == "") {
								horasOcupadas = 0;
							}
							if (minutosOcupados == "") {
								minutosOcupados = 0;
							}
							if (horasOcupadas <= 0 && minutosOcupados <= 0) {
								//$("#duration-hours").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
								//$("#duration-minutes").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');
								$(durationArray).each(function() {
									$(this).validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
								});	
								return;
							}
							bootbox.confirm({
								title: "Retroceder tarea",
						        message: "¿Desea retroceder la tarea?",
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
						            			if (returnData.responseJSON.cssStatus == "alert alert-success") {
						            				$("#divTabsDetalleDeTareaForm").addClass("hide");
						            				$("#iframeDivDetalleDeTarea").addClass("hide");
						            			    $("#iframeDivDetalleDeTarea").empty();
						            			    $("#divDetalleDeTareaDesdeAppContenido").addClass("hide");
						            			    $("#divDetalleDeTareaDesdeApp").empty();						            			
							            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);	 
						            				$.notify({
						                     			message: returnData.responseJSON.resultadoRetrocedeProceso
						                     		},{
						                     			type: 'success'
						                     		});							            				
						            				$("html, body").animate({ scrollTop: 0 }, "slow");	            				           				
						            			} else {		
						            				$.notify({
						                     			message: returnData.responseJSON.resultadoRetrocedeProceso
						                     		},{
						                     			type: 'danger'
						                     		});
						            				if (returnData.responseJSON.recarga == true) {						                     			
								            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
								            			$("#divTabsDetalleDeTarea").addClass('hide');	
							            				$("html, body").animate({ scrollTop: 0 }, "slow");	
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
					var inicializaBotonEnviarTareaDesdeApp = function(){
					var urlMueveProceso = $("#urlMueveProceso").val();	
					var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
					var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();	
					$('#botonEnviarTareaDesdeApp').click(function(){
						var haySessionB = true;
						$.get("${sessionURL}", function(haySession) {
				            console.log("haySession: " + haySession);
				            if(!haySession) {
				            	haySessionB = false;
				                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
				                    , function(){
				                            window.open('${raizURL}', '_blank');
				                    }
				                );
				            } 
				        });
						if(!haySessionB) {
							return;
						}
						var cantidadDeOption = $('#divListaTareaAsignarDesdeApp :radio').length;
						var noHaSeleccionadoTodosLosUsuarios = false;
						var formData = new FormData();
						var asignacionesTareasArray = new Array();						
						$(".asignaciones-tareas-desde-app").each(function (colIndex, c) {
					    	console.log('$(this).find(".usuarios-asignados-desde-app option:selected").text() :' + $(this).find(".usuarios-asignados-desde-app option:selected").text());
					    	console.log('$(this).find(".usuarios-asignados-desde-app").attr("id") :' + $(this).find(".usuarios-asignados-desde-app").attr("id"));
					    	console.log('$(this).find(".fecha-asignada-desde-app").val() :' + $(this).find(".fecha-asignada-desde-app").val());
					    	console.log('cantidadDeOption: ' + cantidadDeOption);
					    	console.log('$(this).find(".div-radio-tarea-desde-app input:radio:checked").val(): ' + $(this).find(".div-radio-tarea-desde-app input:radio:checked").val());
					    	if ( (cantidadDeOption==0) || ( cantidadDeOption>0 && $(this).find(".div-radio-tarea-desde-app input:radio:checked").val() == "on" ) )  {
								console.log('$(this).find(".usuarios-asignados-desde-app option:selected").val(): ' + $(this).find(".usuarios-asignados-desde-app option:selected").val());
					    		if($(this).find(".usuarios-asignados-desde-app option:selected").val() == "") {
					    			noHaSeleccionadoTodosLosUsuarios = true;
						    	}
						    } 	    
					    });	    
						if ($("#commentarioEjecucionTareaDesdeApp").val().trim().length == 0) {
							bootbox.alert("Por favor ingrese un comentario antes de enviar la tarea", 
									function(){ 
										console.log("Poniendo foco en comentarioEjecucionTarea");
										$("#commentarioEjecucionTareaDesdeApp").focus(); 
									}
							);
						} else if (noHaSeleccionadoTodosLosUsuarios == true) {
							bootbox.alert("Por favor seleccione el o los usuarios a asignar");
						} else {					
							var comentario = $("#commentarioEjecucionTareaDesdeApp").val();
							var idInstanciaDeTareaOrigen = $(this).attr("data-idinstanciadetarea");
							var avanzaRetrocede= "avanzarProceso";
							var idExpedienteContinuarProceso = $(this).attr("data-idexpediente");
							var reasigna = false;
							var tiempoDeDicadoDivContainerDesdeAPP = $("#tiempoDedicadoDivDesdeAPP");
							var durationArray = tiempoDeDicadoDivContainerDesdeAPP.find(".durationpicker-duration");
							var horasOcupadas;
							var minutosOcupados;
							$(durationArray).each(function() {
								if ($(this).attr('id') == "duration-hours") {
									horasOcupadas = $(this).val();
									
								}
								if ($(this).attr('id') == "duration-minutes") {
									minutosOcupados = $(this).val();
								}
							});	
							if (horasOcupadas == "") {
								horasOcupadas = 0;
							}
							if (minutosOcupados == "") {
								minutosOcupados = 0;
							}
							if (horasOcupadas <= 0 && minutosOcupados <= 0) {
								//$("#duration-hours").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
								//$("#duration-minutes").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');
								$(durationArray).each(function() {
									$(this).validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
								});
								return;
							}	
						    $(".asignaciones-tareas-desde-app").each(function (colIndex, c) {						    	
						    	if ( (cantidadDeOption==0) || ( cantidadDeOption>0 && $(this).find(".div-radio-tarea-desde-app input:radio:checked").val() == "on" ) )  {
						    		if ($(this).find(".usuarios-asignados-desde-app option:selected").text() != "") {
							    		asignacionesTareasArray.push({    		
							        		idInstanciaDeTarea : $(this).find(".usuarios-asignados-desde-app").attr("id") ,
							        		usuariosAsignados : $(this).find(".usuarios-asignados-desde-app option:selected").text(),
							        		fechaAsignada : $(this).find(".fecha-asignada-desde-app").val()
							            }); 
							    	}
							    } 	
						    	console.log('asignacionesTareasArray: ' + asignacionesTareasArray);	    
						    });   	    
						    var asignacionesTareasJSON = JSON.stringify(asignacionesTareasArray); 
						    formData.append("comentario", comentario);
						    formData.append("idInstanciaDeTareaOrigen", idInstanciaDeTareaOrigen);
						    formData.append("avanzaRetrocede", avanzaRetrocede);
						    formData.append("idExpedienteContinuarProceso", idExpedienteContinuarProceso);
						    formData.append("reasigna", reasigna);
						    formData.append("asignacionesTareasJSON", asignacionesTareasJSON);
						    formData.append("horasOcupadas", horasOcupadas);
						    formData.append("minutosOcupados", minutosOcupados);
						    var puedeAvanzarProcesoConAdvertenciaVisacion = $(this).attr("data-puedeavanzarprocesoconadvertenciavisacion");
						    var puedeAvanzarProcesoConAdvertenciaFEA = $(this).attr("data-puedeavanzarprocesoconadvertenciafea");
						    var mensajeConfirmacionEnviaTarea = "¿Desea enviar su tarea?";						   

						    if (puedeAvanzarProcesoConAdvertenciaVisacion == "true") {
						    	mensajeConfirmacionEnviaTarea = "Esta es una tarea de visación, pero no ha visado todos los documentos. <br> ¿Desea enviar la tarea de todos modos?";
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
						            	    success: function (returnData) {
						            	    	console.log("SUCCESS -- mueveProceso: ", returnData);	    	
						            	    },
						            	    error : function(e) {
						            			console.log("ERROR: ", e);			
						            		},
						            		done : function(e) {
						            			console.log("DONE");
						            		},
						            		complete : function(returnData) {
						            			console.log("COMPLETE -- mueveProceso: ", returnData.responseJSON );
						            			if (returnData.responseJSON.respuestaMueveProceso == "OK") {						            				
						            				$("#divTabsDetalleDeTareaForm").addClass("hide");
						            				$("#iframeDivDetalleDeTarea").addClass("hide");
						            			    $("#iframeDivDetalleDeTarea").empty();
						            			    $("#divDetalleDeTareaDesdeAppContenido").addClass("hide");
						            			    $("#divDetalleDeTareaDesdeApp").empty();									            				
							            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);	
						            				$.notify({
						                     			message: returnData.responseJSON.tareasUsuarios
						                     		},{
						                     			type: 'success'
						                     		});										            				
						            				$("html, body").animate({ scrollTop: 0 }, "slow");
						            			} else {		
						            				$.notify({
						                     			message: returnData.responseJSON.respuestaMueveProceso
						                     		},{
						                     			type: 'danger'
						                     		});
						            				if (returnData.responseJSON.recarga == true) {									                     				
								            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);
								            			$("#divTabsDetalleDeTarea").addClass('hide');	
							            				$("html, body").animate({ scrollTop: 0 }, "slow");	
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


				var inicializaBotonFinalizaProcesoDesdeApp = function(){	
					var urlFinalizarProceso = $("#urlFinalizarProceso").val();
					var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
					var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();	
					$('#botonFinalizaProcesoDesdeApp').click(function(){
						var haySessionB = true;
						$.get("${sessionURL}", function(haySession) {			
				            console.log("haySession: " + haySession);
				            if(!haySession) {
				            	haySessionB = false;
				                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
				                    , function(){
				                            window.open('${raizURL}', '_self');                            
				                    }
				                );                
				            } 
				        });
						if(!haySessionB) {
							return;
						}
						if ($("#commentarioEjecucionTareaDesdeApp").val().trim().length == 0) {
							bootbox.alert("Por favor ingrese un comentario antes de despachar la tarea");
						} else {
							var idInstanciaDeTarea = $(this).attr("data-idinstanciadetarea");
							var comentario = $("#commentarioEjecucionTareaDesdeApp").val();
							//var horasOcupadas = $("#duration-hours").val();
							//var minutosOcupados = $("#duration-minutes").val();
							var tiempoDeDicadoDivContainerDesdeAPP = $("#tiempoDedicadoDivDesdeAPP");
							var durationArray = tiempoDeDicadoDivContainerDesdeAPP.find(".durationpicker-duration");
							var horasOcupadas;
							var minutosOcupados;
							$(durationArray).each(function() {
								if ($(this).attr('id') == "duration-hours") {
									horasOcupadas = $(this).val();
									
								}
								if ($(this).attr('id') == "duration-minutes") {
									minutosOcupados = $(this).val();
								}
							});	
							if (horasOcupadas == "") {
								horasOcupadas = 0;
							}
							if (minutosOcupados == "") {
								minutosOcupados = 0;
							}
							if (horasOcupadas <= 0 && minutosOcupados <= 0) {
								//$("#duration-hours").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
								//$("#duration-minutes").validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');
								$(durationArray).each(function() {
									$(this).validationEngine('showPrompt', 'Por favor ingrese un valor para horas y/o segundos', 'error');	
								});
								return;
							}	
					        var formData = new FormData();
					        formData.append("idInstanciaDeTarea", idInstanciaDeTarea);
					        formData.append("comentario", comentario);
					        formData.append("horasOcupadas", horasOcupadas);
					        formData.append("minutosOcupados", minutosOcupados);
							bootbox.confirm({
						    	title: "Finalizar Proceso",
						        message: "¿Desea finalizar el proceso?",
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
						            	    error : function(e) {
						            			console.log("ERROR: ", e);			
						            		},
						            		done : function(e) {
						            			console.log("DONE");
						            		},
						            		complete : function(returnData) {
						            			console.log("COMPLETE -- finalizarProceso: ", returnData );
						            			if (returnData.responseJSON.respuestaFinalizaProceso == "OK") {						            			
							            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);	
						            				$.notify({
						                     			message: 'Se finalizo el proceso!'
						                     		},{
						                     			type: 'success'
						                     		});
						            				$("#divTabsDetalleDeTareaForm").addClass("hide");
						              				console.log("Enconder correctamente")
						            				$("#divTabsDetalleDeTarea").addClass('hide');	
						            				$("html, body").animate({ scrollTop: 0 }, "slow");
						            			} else {						            			
							            			actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);		
						            				$.notify({
						                     			message: returnData.responseJSON.respuestaFinalizaProceso
						                     		},{
						                     			type: 'danger'
						                     		});
						            				$("#divTabsDetalleDeTareaForm").addClass("hide");
						              				console.log("Enconder correctamente")
						            				$("#divTabsDetalleDeTarea").addClass('hide');	
						            				$("html, body").animate({ scrollTop: 0 }, "slow");
						            			}		
						            		}
						            		
						            	});
								    }
						        }
						    });
						}			
					});
				};

				var inicializaBotonVerDiagramaEnNuevaVentanaApp = function (){
					  $("#botonVerDiagramaEnNuevaVentanaApp").off('click').on('click', function () {
					    var urlDiagramaSubProcesoBoton = "${urlDiagramaSubProcesoBoton}";
					    console.log("urlDiagramaSubProcesoBoton: " + urlDiagramaSubProcesoBoton);
					    $.get("${sessionURL}", function(haySession){
					          console.log("haySession: " + haySession);
					          if(haySession) {
					              console.log("Inicio window.open");
					              window.open(urlDiagramaSubProcesoBoton, "SubProceso", 'width=1700, height=900');
					              console.log("Fin window.open");
					          } else {
					                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
					                              , function(){
					                                    window.open('${raizURL}', '_blank');
					                              }
					                 );
					          }
					    });
						});
				};
				
				function inicializaTiempoDedicadoDesdeAPP() {
					$("#tiempoDedicadoDesdeAPP").durationPicker({
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
						  responsive: true
						});
				}
				
				function agregaValidityTiempoDedicadoDesdeAPP() {
					$("#duration-hours").attr("oninput", "validity.valid||(value='');");
					$("#duration-minutes").attr("oninput", "validity.valid||(value='');");
				}
				
				$(document).ready(function() {
					$(inicializaDatePlazoSiguienteTareaDesdeApp);
					$(inicializaBotonEnviarTareaDesdeApp);	
					$(inicializaBotonDevolverTareaDesdeApp);	
					$(inicializaBotonFinalizaProcesoDesdeApp);
					$(inicializaBotonVerDiagramaEnNuevaVentanaApp);
					$(inicializaTiempoDedicadoDesdeAPP);
					$(agregaValidityTiempoDedicadoDesdeAPP);
				});
				
				
				
	  		 </script>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/jsp/modals/infoDeProceso.jsp"></c:import>
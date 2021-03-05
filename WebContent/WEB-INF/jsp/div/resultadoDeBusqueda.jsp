<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="cl.gob.scj.sgdp.tipos.NombreObjetoEnBusquedaType"%>
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>
<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %>  

<c:url value="http://${urlFuncPhp}/proceso/bpm/notificar.php?idproc=" var="urlNotificarInstanciaDeProcesoDesdeBusqueda" />

<c:set var="nombreTipoDocumento" value="<%=NombreObjetoEnBusquedaType.DOCUMENTO.getNombreNombreObjetoEnBusquedaType()%>" />
<c:set var="nombreTipoExpediente" value="<%=NombreObjetoEnBusquedaType.EXPEDIENTE.getNombreNombreObjetoEnBusquedaType()%>" />
<c:set var="tipoDocumentoOficial" value="OFICIALES" />

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />
<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" /> 

<c:set var="permisoNoFiltraPorConfidencialidad" value="<%=PermisoType.NO_FILTRA_POR_CONFIDENCIALIDAD.getNombrePermiso()%>"/>
<c:set var="permisoReabrirExpYSaltarTareas" value="<%=PermisoType.PUEDE_REABRIR_EXPEDIENTE_Y_SATAR_TAREA.getNombrePermiso()%>"/>

<c:choose>
	<c:when test="${buscarDTO.soloEnMiBandejaDeSalida eq true}">		
		<c:set var="indiceColumnaSubProceso" value="0" />
		<c:set var="indiceColumnaMateria" value="2" />
		<c:set var="indiceColumnaAutor" value="3" />
		<c:set var="indiceColumnaCreador" value="4" />
		<c:set var="indiceColumnaFechaCreacion" value="5" />
		<c:set var="cantidaDeColumnas" value="6" />
	</c:when>
	<c:otherwise>
		<c:set var="indiceColumnaSubProceso" value="2" />
		<c:set var="indiceColumnaMateria" value="3" />
		<c:set var="indiceColumnaAutor" value="4" />
		<c:set var="indiceColumnaCreador" value="5" />
		<c:set var="indiceColumnaFechaCreacion" value="6" />
		<c:set var="cantidaDeColumnas" value="7" />
	</c:otherwise>
</c:choose>

<hr>

<div class="row">
	<div class="col-sm-2 col-sm-offset-1"></div>
	<div class="col-sm-9">
		<h4>Resultado de la busqueda</h4>
	</div>
</div>
<script>


 function busquedaInformacionConFiltro(nombreColumna,tipoFiltro){
  
	var url = "/sgdp/getResultadoBusquedaConFiltro";
	var nombreColumna = encodeURIComponent(nombreColumna);
	console.log("url: " + url);    

	$.get("${sessionURL}", function(haySession) {
	    if (haySession) {  	
	        dialogBuscar = bootbox.dialog({
	            message: '<p><i class="fa fa-spin fa-spinner"></i> Buscando Datos ... </p>',
	              closeButton: false
	        });
	        $('#divResultadoBusqueda').fadeOut("slow").load(url + "/" + nombreColumna + "/" + tipoFiltro, function() {
	                dialogBuscar.modal('hide');
	        }).fadeIn('slow');			   
	    } else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	            , function(){ window.open('${raizURL}', '_blank');}
	            );
	    }
	});
	
    	
 }

</script>
				
<div class="row">
	
	<div class="col-sm-3 menuBusquedaOrden">

		<ul class="list-group ">

			<c:if test="${not empty resultadoBusquedaDTO.facetSubProcesos}">

				<li class="list-group-item">SubProceso
					<ul class="list-group tagsProceso">
						<c:forEach var="entry"
							items="${resultadoBusquedaDTO.facetSubProcesos}">							
						<%--	<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="busquedaInformacionConFiltro('${entry.key}',1);">
								${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>
						 --%>
						
							 <li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="filterColumn(1, '${entry.key}', $(this));">
								 <input type="checkbox" value="" readonly>
								<span> ${entry.key}</span>
								
								<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>	
						</c:forEach>
					</ul> <a class="cursorPuntero hidden" id="procesoVerMenos"
					onclick="OcultarProceso()">Ver menos</a> <a
					class="pull-right cursorPuntero hidden" id="procesoVerMas"
					onclick="verProceso()">Ver mas</a>

				</li>
				
				 <STYLE type="text/css">
					  input[type="checkbox"][readonly]
					   {
					  	pointer-events: none;
					  }
 				</STYLE>

			</c:if>

			<c:if test="${not empty resultadoBusquedaDTO.facetMaterias}">

				<li class="list-group-item">Materia
					<ul class="list-group tagMateria">
						<c:forEach var="entry"
							items="${resultadoBusquedaDTO.facetMaterias}">
						 <%-- 	<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="busquedaInformacionConFiltro('${entry.key}',2);">
								${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>
						--%>
							<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="filterColumn(2, '${entry.key}', $(this));">
								 <input type="checkbox" value="" readonly>
								<span> ${entry.key}</span>
								<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>	
						</c:forEach>

					</ul> <a class="cursorPuntero hidden" id="materiaVerMenos"
					onclick="OcultarMaterias()">Ver menos</a> <a
					class="pull-right cursorPuntero hidden" id="materiaVerMas"
					onclick="verMaterias()">Ver mas</a>
				</li>

			</c:if>

			<c:if test="${not empty resultadoBusquedaDTO.facetAutores}">

				<li class="list-group-item">Autor
					<ul class="list-group tagAutor">
						<c:forEach var="entry"
							items="${resultadoBusquedaDTO.facetAutores}">
						 <%-- 	<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="busquedaInformacionConFiltro('${entry.key}',3);">
								${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>
						--%>
							<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="filterColumn(3, '${entry.key}', $(this));">
								 <input type="checkbox" value="" readonly>
								<span> ${entry.key}</span>								
								<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>	
						</c:forEach>
					</ul> <a class="cursorPuntero hidden" id="autorVerMenos"
					onclick="OcultarAutor()">Ver menos</a> <a
					class="pull-right cursorPuntero hidden" id="autorVerMas"
					onclick="verAutor()">Ver mas</a>

				</li>

			</c:if>

			<c:if test="${not empty resultadoBusquedaDTO.facetCreadores}">

				<li class="list-group-item">Creador
					<ul class="list-group tagCreador">
						<c:forEach var="entry"
							items="${resultadoBusquedaDTO.facetCreadores}">
							<%-- 
							<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="busquedaInformacionConFiltro( '${entry.key}',4);">
								${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>
							--%>
							<li
								class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
								onclick="filterColumn(4, '${entry.key}', $(this));">
								 <input type="checkbox" value="" readonly>
								<span> ${entry.key}</span>
								<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>
						</c:forEach>
					</ul> <a class="cursorPuntero hidden" id="creadorVerMenos"
					onclick="OcultarCreador()">Ver menos</a> <a
					class="pull-right cursorPuntero hidden" id="creadorVerMas"
					onclick="verCreador()">Ver mas</a>

				</li>

			</c:if>

		<%-- 
			<c:if test="${not empty resultadoBusquedaDTO.facetFechasCreacion}">

				<li class="list-group-item">Fecha Creaci&oacute;n
					<ul class="list-group tagFechaCreacion">
						<c:forEach var="entry"
							items="${resultadoBusquedaDTO.facetFechasCreacion}">
							<li class="list-group-item cursorPuntero"
								onclick="filterColumn(${indiceColumnaFechaCreacion}, '${entry.key}');">
								${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
							</li>
						</c:forEach>
					</ul> <a class="cursorPuntero hidden" id="creacionVerMenos"
					onclick="OcultarCreacion()">Ver menos</a> <a
					class="pull-right cursorPuntero hidden" id="creacionVerMas"
					onclick="verCreacion()">Ver mas</a>

				</li>

			</c:if>
			--%>
		</ul>

	</div>

  

	<div class="col-sm-9 table-responsive">

    <!--  
	 <div class="col-sm-12">
         <button type="button" class="btn btn-default" onclick="buscarRegistrosPaginadosExportExcel();">Generar Excel</button> 
         <br>
         <br>
    </div>
    -->
		<c:choose>
			<%-- test="${buscarDTO.soloEnMiBandejaDeSalida == 'soloEnMiBandejaDeSalida'}">--%>
			<c:when
				test="${buscarDTO.soloEnMiBandejaDeSalida eq true}">
				<table id="tablaResultadoBusqueda"
					class="table table-striped table-bordered table-hover table-fixed">

					<thead>
						<tr>
							<th>SubProceso</th>
							<th>Expediente</th>
							<th>Materia</th>
							<th>Autor</th>
							<%-- <th>Fecha Creaci&oacute;n</th>--%>
							<th>Creador</th>
							<th>Fecha Creaci&oacute;n</th>
							<th></th>
						</tr>
					</thead>

					<tbody>

						<c:forEach
							items="${resultadoBusquedaDTO.elementosResultadoBusquedaDTO}"
							var="elementoResultadoBusquedaDTO">
							<tr>
								<td>${elementoResultadoBusquedaDTO.subProceso}</td>
								<c:choose>
										<c:when test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoDocumento}">
											<td>${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}</td>
										</c:when>
										<c:when test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoExpediente}">
											<td>${elementoResultadoBusquedaDTO.nombreDeObjeto}</td>
										</c:when>
								</c:choose>
								<td>${elementoResultadoBusquedaDTO.materia}</td>
								<td>${elementoResultadoBusquedaDTO.autor}</td>
								<%--<td><fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM}" value="${elementoResultadoBusquedaDTO.fechaDeCreacion}" /></td>--%>
								<td>${elementoResultadoBusquedaDTO.creador}</td>
								<td>${elementoResultadoBusquedaDTO.facetFechaCreacion}</td>
								<td>
									<c:choose>
										<c:when
											test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoDocumento}">
											
											 <div>
											    <p>
											    
											    	<a href="#" class="btn btn-info btn-xs"
														onclick="cargarDetalleDeProceso('${elementoResultadoBusquedaDTO.idExpedienteQueLoContiene}', 
						    																'${elementoResultadoBusquedaDTO.subProceso}',
						    																'${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}')">
														 Ver
													</a>
											    
											    </p>
											 </div>
											 
											  <div>
											    <p>
											    
											    	<a href="#" data-nombreexpediente="${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}" 
														onclick="notificarInstanciaDeProcesoDesdeBusqqueda('${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}')"
														class="btn btn-default btn-sm boton-notificar-busqueda" id="botonNotificarInstanciaDeProcesoDesdeBusqqueda">
														<span class="glyphicon glyphicon-send"></span>									            
													</a>
											    
											    </p>
											 </div>
											 
											  <div>
											    <p>
											    	<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
														onclick="buscarDiagramaSubproceso('${elementoResultadoBusquedaDTO.idExpedienteQueLoContiene}')">
														<span class="fa fa-gears"></span>
										        	</a>
								        	
											    </p>
											 </div>
									
										</c:when>
										<c:when
											test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoExpediente}">											
											
											<div>
										      <p>
										      	<a href="#" class="btn btn-info btn-xs"
												onclick="cargarDetalleDeProceso('${elementoResultadoBusquedaDTO.idObjeto}', 
				    																'${elementoResultadoBusquedaDTO.subProceso}',
				    																'${elementoResultadoBusquedaDTO.nombreDeObjeto}')">
												 Ver
												</a>
											</p>
										    </div>
										    
										    <div>
											    <p>
											    	<a href="#" data-nombreexpediente="${elementoResultadoBusquedaDTO.nombreDeObjeto}" 
														onclick="notificarInstanciaDeProcesoDesdeBusqqueda('${elementoResultadoBusquedaDTO.nombreDeObjeto}')"
														class="btn btn-default btn-sm boton-notificar-busqueda" id="botonNotificarInstanciaDeProcesoDesdeBusqqueda">	
														<span class="glyphicon glyphicon-send"></span>											
													</a>
											    </p>
										    </div>
										    
										    <div>
											    <p>
											    
											    	<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
														onclick="buscarDiagramaSubproceso('${elementoResultadoBusquedaDTO.idObjeto}')">
														<span class="fa fa-gears"></span>
										        	</a>
											    </p>
										    </div>
										    
										    <div>
											    <p>											    
											    	<a href="#" class="btn btn-warning btn-xs" title="Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes"		
														onclick="cargaHistorialDeCondicionesDeSatisfaccionPorIdExpediente('${elementoResultadoBusquedaDTO.nombreDeObjeto}',
					    										'${elementoResultadoBusquedaDTO.idObjeto}')">
														RDS/SNC
										        	</a>								        	
											    </p>
										    </div>
											
										</c:when>
									</c:choose>									
								</td>
							</tr>
						</c:forEach>

					</tbody>

				</table>
			</c:when>
			<c:otherwise>
				<table id="tablaResultadoBusqueda" class="table table-striped table-bordered table-hover table-fixed">
					<thead>
						<tr>
							<th>Tipo</th>
							<th>Nombre</th>
							<th>N&uacute;mero</th>
							<th>SubProceso</th>
							<th>Materia</th>
							<th>Creador</th>
							<%-- <th>Fecha Creaci&oacute;n</th>--%>
							<%-- <th>Creador</th> --%>
							<%-- <th>Fecha Creaci&oacute;n</th>--%>
							<th>Fecha Inicio</th>
							<th>Fecha Fin</th>
							<th>Estado</th>
							
							<th></th>
						</tr>
					</thead>
					<tbody>
                     
						<c:forEach items="${resultadoBusquedaDTO.elementosResultadoBusquedaDTO}" var="elementoResultadoBusquedaDTO">
							<c:set var="haParticipado" value="false" />
							<c:forEach var="usuarioQueHanModificado" items="${elementoResultadoBusquedaDTO.usuariosQueHanModificado}">
							  <c:if test="${usuarioQueHanModificado eq usuario.idUsuario}">
							    <c:set var="haParticipado" value="true" />
							  </c:if>
							</c:forEach>
							<c:if test="${haParticipado eq 'false'}">
								<c:forEach var="usuarioQueHanModificado" items="${elementoResultadoBusquedaDTO.usuariosQueHanParticipado}">
								  <c:if test="${usuarioQueHanModificado eq usuario.idUsuario}">
								    <c:set var="haParticipado" value="true" />
								  </c:if>
								</c:forEach>
							</c:if>							
							<tr>
								<c:choose>
									<c:when test="${elementoResultadoBusquedaDTO.tipoObjeto == 'Documento'}">											
										<td>${elementoResultadoBusquedaDTO.tipoDeDocumento}</td> 								
									</c:when>
									<c:otherwise>
										<td>${elementoResultadoBusquedaDTO.tipoObjeto}</td>	
					    			</c:otherwise>
								</c:choose>
																								
								
								<td><c:choose>
										<c:when
											test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoDocumento}">
											<a href='#'
												onclick='descargaArchivo("<c:url value='getArchivoPorId/${elementoResultadoBusquedaDTO.idObjeto}'/>")'>
													${elementoResultadoBusquedaDTO.nombreDeObjeto}
												</a>								
										</c:when>
										<c:otherwise>
					    					${elementoResultadoBusquedaDTO.nombreDeObjeto}
					    				</c:otherwise>
									</c:choose></td>
								<td>${elementoResultadoBusquedaDTO.numeroDeDocumento}</td>
								<td>${elementoResultadoBusquedaDTO.subProceso}</td>
								<td>${elementoResultadoBusquedaDTO.materia}</td>
								 
								 <c:choose>
							
									<c:when test="${elementoResultadoBusquedaDTO.tipoObjeto == 'Documento'}">		
																	    
										<c:choose>
											<c:when test="${elementoResultadoBusquedaDTO.creador == ''}">														
												 <td>N/A</td>					
											</c:when>
											<c:otherwise>
												 <td>${elementoResultadoBusquedaDTO.creador}</td>			
											</c:otherwise>
										</c:choose> 	
																																		
									</c:when>
							    	<c:otherwise>
							    		
										<c:choose>
											<c:when test="${elementoResultadoBusquedaDTO.autor == ''}">														
												 <td>N/A</td>					
											</c:when>
											<c:otherwise>
												 <td>${elementoResultadoBusquedaDTO.autor}</td>			
											</c:otherwise>
										</c:choose>
							  		
					    			</c:otherwise>
								</c:choose>
																																																			
								<%-- <td>${elementoResultadoBusquedaDTO.creador}</td>   --%>
								<%-- <td>${elementoResultadoBusquedaDTO.facetFechaCreacion}</td> --%>
								<td>
								 <span style="display: none;" >
						    		  <fmt:formatDate value="${elementoResultadoBusquedaDTO.fechaInicioInstanciaDeProceso}" pattern="yyyy-MM-dd" /> 
						    		</span>								
									<fmt:formatDate pattern="${FORMATO_FECHA}" value="${elementoResultadoBusquedaDTO.fechaInicioInstanciaDeProceso}" /> 
								
								</td>
								
								
								<c:choose>
									<c:when test="${elementoResultadoBusquedaDTO.fechaFinInstanciaDeProceso == null}">											
										<td>N/A</td>				
									</c:when>
									<c:otherwise>
										<td>
											<span style="display: none;" >
								    			  <fmt:formatDate value="${elementoResultadoBusquedaDTO.fechaFinInstanciaDeProceso}" pattern="yyyy-MM-dd" /> 
								    		</span>								
											<fmt:formatDate pattern="${FORMATO_FECHA}" value="${elementoResultadoBusquedaDTO.fechaFinInstanciaDeProceso}" /> 
									</td>
					    			</c:otherwise>
								</c:choose>
												
								
								
								
								
								<td>${elementoResultadoBusquedaDTO.nombreEstadoDeProceso}</td>
								
								<td><c:choose>
										<c:when test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoDocumento}">
																	
											<c:choose>
																						
												<c:when test="${buscarDTO.tipoDocumentoOficial eq tipoDocumentoOficial and permisos[permisoNoFiltraPorConfidencialidad] eq permisoNoFiltraPorConfidencialidad}">
												
													<div>
											    		<p>
											    			<a href="#" class="btn btn-info btn-xs"
																onclick="cargarDetalleDeProceso('${elementoResultadoBusquedaDTO.idExpedienteQueLoContiene}', 
							    																'${elementoResultadoBusquedaDTO.subProceso}',
							    																'${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}')">
															 Ver
															</a>	
											    		</p>
											    	</div>									
												
												</c:when>
												
												<c:when test="${buscarDTO.tipoDocumentoOficial eq tipoDocumentoOficial and haParticipado eq 'true'}">
												
													<div>
											    		<p>
											    			<a href="#" class="btn btn-info btn-xs"
																onclick="cargarDetalleDeProceso('${elementoResultadoBusquedaDTO.idExpedienteQueLoContiene}', 
							    																'${elementoResultadoBusquedaDTO.subProceso}',
							    																'${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}')">
															 Ver
															</a>
											    		</p>
											    	</div>																								
												
												</c:when>
												
												<c:when test="${buscarDTO.tipoDocumentoOficial ne tipoDocumentoOficial}">
												
													<div>
											    		<p>
											    			<a href="#" class="btn btn-info btn-xs"
																onclick="cargarDetalleDeProceso('${elementoResultadoBusquedaDTO.idExpedienteQueLoContiene}', 
							    																'${elementoResultadoBusquedaDTO.subProceso}',
							    																'${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}')">
															 Ver
															</a>	
											    		</p>											    		
											    	</div>									
												
												</c:when>											
											
											</c:choose>											
											
											<div>
												<p>
													<a href="#" data-nombreexpediente="${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}"
														onclick="notificarInstanciaDeProcesoDesdeBusqqueda('${elementoResultadoBusquedaDTO.nombreExpedienteQueLoContiene}')" 
														class="btn btn-default btn-sm boton-notificar-busqueda" id="botonNotificarInstanciaDeProcesoDesdeBusqqueda">
														<span class="glyphicon glyphicon-send"></span>												
													</a>	
												</p>
											</div>
											
											<div>
												<p>
													<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
															onclick="buscarDiagramaSubproceso('${elementoResultadoBusquedaDTO.idExpedienteQueLoContiene}')">
															<span class="fa fa-gears"></span>
											        </a>	
												</p>
											</div>
											
										</c:when>
										<c:when test="${elementoResultadoBusquedaDTO.tipoObjeto == nombreTipoExpediente}">
										
											<div>
												<p>
													<a href="#" class="btn btn-info btn-xs"
														onclick="cargarDetalleDeProceso('${elementoResultadoBusquedaDTO.idObjeto}', 
						    																'${elementoResultadoBusquedaDTO.subProceso}',
						    																'${elementoResultadoBusquedaDTO.nombreDeObjeto}')">
														 Ver
													</a>	
												</p>
											</div>
											
											<div>
												<p>
													<a href="#" data-nombreexpediente="${elementoResultadoBusquedaDTO.nombreDeObjeto}"
														onclick="notificarInstanciaDeProcesoDesdeBusqqueda('${elementoResultadoBusquedaDTO.nombreDeObjeto}')" 
														class="btn btn-default btn-sm" id="botonNotificarInstanciaDeProcesoDesdeBusqqueda">
														<span class="glyphicon glyphicon-send"></span>												
													</a>
												</p>
											</div>
											
											<div>
												<p>
													<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
														onclick="buscarDiagramaSubproceso('${elementoResultadoBusquedaDTO.idObjeto}')">
														<span class="fa fa-gears"></span>
										        	</a>	
												</p>
											</div>
												
								        	<c:if test="${permisos[permisoReabrirExpYSaltarTareas] eq permisoReabrirExpYSaltarTareas}">
								        	
								        		<div>
													<p>
														<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Reapertura y cierre de tareas" 																										
															onclick="abrirDiagramaSubprocesoParaReaperturaYSalto('${elementoResultadoBusquedaDTO.nombreDeObjeto}')">
															<span class="glyphicon glyphicon-retweet"></span>
											        	</a>
													</p>
												</div>
								        		
								        	
								        	</c:if>
								        	
								        	<div>
												<p>
													<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Vinculaciones" 																										
														onclick="vinculaciones('${elementoResultadoBusquedaDTO.idObjeto}', '${elementoResultadoBusquedaDTO.nombreDeObjeto}')">
														<span class="glyphicon glyphicon-link font-icon-1"></span>
										        	</a>
												</p>
											</div>
											
											<div>
												<p>
													<a href="#" class="btn btn-warning btn-xs" title="Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes"		
														onclick="cargaHistorialDeCondicionesDeSatisfaccionPorIdExpediente('${elementoResultadoBusquedaDTO.nombreDeObjeto}',
					    										'${elementoResultadoBusquedaDTO.idObjeto}')">
														RDS/SNC
										        	</a>
												</p>
											</div>									      	
																						
										</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>
				
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>


	</div>

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

	  function buscarDiagramaSubproceso(idExpediente){

   		var contextPath = "${pageContext.request.contextPath}"

   			$('#infoDeProcesoModal2').modal('show');   
   			
 			$("#informacionDelProcesoDiv2").css("position",
 				"relative").css("min-height", "200px").prepend(
 			$("<div />").addClass("cargando"));
 			
 			$("#informacionDelProcesoDiv2").load(
 					contextPath + "/diagramaProceso2/"+idExpediente ,
 					function() {
 						$("#informacionDelProcesoDiv2").find(
 								".cargando").remove();
 	 				});
				
 		       	
	  }
	
	</script>
				
	<%-- //////////////////////////////////////////////////////////  --%>	




<c:choose>
	<%-- test="${buscarDTO.soloEnMiBandejaDeSalida == 'soloEnMiBandejaDeSalida'}">--%>
	<c:when test="${buscarDTO.soloEnMiBandejaDeSalida eq true}">

		<script>


		function DejarLosPrimerosAtributos() {

            if ($(".tagsProceso li").length > 5 ){
         	    $(".tagsProceso li:gt(4)").hide()
         	    $("#procesoVerMenos").removeClass("hidden");
			    $("#procesoVerMas").removeClass("hidden");
             }

            if ( $(".tagMateria li").length > 5 ){
           	    $(".tagMateria li:gt(4)").hide()
           	     $("#materiaVerMenos").removeClass("hidden");
			     $("#materiaVerMas").removeClass("hidden");
               }

            if ($(".tagAutor li").length > 5 ){
        	    $(".tagAutor li:gt(4)").hide()
          	    $("#autorVerMenos").removeClass("hidden");
			    $("#autorVerMas").removeClass("hidden");
            }

            if ($(".tagCreador li").length > 5 ){
        	    $(".tagCreador li:gt(4)").hide()
        	      $("#creadorVerMenos").removeClass("hidden");
			      $("#creadorVerMas").removeClass("hidden");
            }

            if ($(".tagFechaCreacion li").length > 5 ){
        	    $(".tagFechaCreacion li:gt(4)").hide()
        	    $("#creacionVerMenos").removeClass("hidden");
			    $("#creacionVerMas").removeClass("hidden");
            }
	    			    		
	    	};
	    		    	

	    	 /////////// 
	    	var contadorProceso = 5;
	    	function verProceso() {
	    		// $(".tagsProceso li:gt(5)").show()
	    		 if ( $(".tagsProceso li").length >= contadorProceso  ){
	    			 contadorProceso = contadorProceso + 5;
	    		 }			   
	    		$(".tagsProceso li:lt("+contadorProceso+")").show() 
	    		
	    	}

	    	function OcultarProceso() {
	    		 //$(".tagsProceso li:gt(5)").hide()
	    		if ( contadorProceso > 5 ){
	    			contadorProceso = contadorProceso - 5;
	    		 }
	    		 
	    		$(".tagsProceso li:gt("+contadorProceso+")").hide() 
	    		 
	    	}

       ///////////      
             var contadorMaterias = 5 ;
	    	function verMaterias() {
			   // $(".tagMateria li:gt(5)").show() 
			   
	    		 if ( $(".tagMateria li").length >= contadorMaterias  ){
	    			 contadorMaterias = contadorMaterias + 5;
	    		 }
			   
	    		$(".tagMateria li:lt("+contadorMaterias+")").show() 
 		
			}
		
	    	function OcultarMaterias() {
	    		//$(".tagMateria li:gt(5)").hide()
	    		if ( contadorMaterias > 5 ){
	    			 contadorMaterias = contadorMaterias - 5;
	    		 }
	    		 
	    		$(".tagMateria li:gt("+contadorMaterias+")").hide() 
	    			    		    		
	    	}
        //////////////
        
          var contadorAutor = 5 ;
        	function verAutor() {
        		//$(".tagAutor li:gt(5)").show()

        		 if ( $(".tagAutor li").length >= contadorAutor  ){
        			 contadorAutor = contadorAutor + 5;
	    		 }
			   
	    		$(".tagAutor li:lt("+contadorAutor+")").show() 
        		
	    	}

	    	function OcultarAutor() {
	    		// $(".tagAutor li:gt(5)").hide()

	    		if ( contadorAutor > 5 ){
	    			contadorAutor = contadorAutor - 5;
	    		 }
	    		 
	    		$(".tagAutor li:gt("+contadorAutor+")").hide() 
	    		
	    	}
	    	///////////////////////
	    	var contadorCreador = 5 ;
	        function verCreador() {
	        	 //$(".tagCreador li:gt(5)").show()

	        	 if ( $(".tagCreador li").length >= contadorCreador  ){
	        		 contadorCreador = contadorCreador + 5;
	    		 }
			   
	    		$(".tagCreador li:lt("+contadorCreador+")").show() 
	        	 
	    	}

	    	function OcultarCreador() {
	    		 //$(".tagCreador li:gt(5)").hide()
	    		if ( contadorCreador > 5 ){
	    			contadorCreador = contadorCreador - 5;
	    		 }
	    		 
	    		$(".tagCreador li:gt("+contadorCreador+")").hide() 
	    		
	    	}

	    	////////////////////////
	    	var contadorFechaCreacion = 5 ;
	    	function verCreacion() {
	    		// $(".tagFechaCreacion li:gt(5)").show()

	    		 if ( $(".tagFechaCreacion li").length >= contadorFechaCreacion  ){
	    			 contadorFechaCreacion = contadorFechaCreacion + 5;
	    		 }
			   
	    		$(".tagFechaCreacion li:lt("+contadorFechaCreacion+")").show() 
	    		
	    	}

	    	function OcultarCreacion() {
	    		// $(".tagFechaCreacion li:gt(5)").hide()
	    		if ( contadorFechaCreacion > 5 ){
	    			contadorFechaCreacion = contadorFechaCreacion - 5;
	    		 }
	    		 
	    		$(".tagFechaCreacion li:gt("+contadorFechaCreacion+")").hide() 
	    		
	    	}
    	
				function formatTablaResultadoBusqueda() {
				    var tablaResultadoBusqueda = $('#tablaResultadoBusqueda')
				                 .DataTable(                        		
				                               {
				                            	   buttons : [ /*{
				                                       extend : 'copyHtml5',
				                                       exportOptions : {
				                                              columns : [ 0, ':visible' ]
				                                       }
				                       	         },*/ {
				                       	                extend : 'excelHtml5',
				                       	                filename : 'Busqueda.*',
				                       	                exportOptions : {
				                       	                       columns : ':visible'
				                       	                }
				                       	         }, 'colvis' ],
				                       			bFilter: false,	
				                       			pageLength: 10,		
				                       			language: lenguaje_es,
												/*
				                       		    columnDefs: [
				                       		             {
				                       		                 "targets": [ 4 ],
				                       		                 "visible": false				                       		                
				                       		             },
				                       		             {
				                       		                 "targets": [ 5 ],
				                       		                 "visible": false
				                       		             }
				                       		         ],
				                       		        */ 
					                       		  searching: true
				
				                                     
				                               });
				    tablaResultadoBusqueda.buttons().container().appendTo(
				                 '#tablaResultadoBusqueda_wrapper .row:eq(0)');
				
				}
				
				$(document).ready(function() {
					formatTablaResultadoBusqueda();
					DejarLosPrimerosAtributos();
				});
				
			</script>

	</c:when>

	<c:otherwise>

		<script>


		function DejarLosPrimerosAtributos() {

            if ($(".tagsProceso li").length > 5 ){
         	    $(".tagsProceso li:gt(4)").hide()
         	    $("#procesoVerMenos").removeClass("hidden");
			    $("#procesoVerMas").removeClass("hidden");
             }

            if ( $(".tagMateria li").length > 5 ){
           	    $(".tagMateria li:gt(4)").hide()
           	     $("#materiaVerMenos").removeClass("hidden");
			     $("#materiaVerMas").removeClass("hidden");
               }

            if ($(".tagAutor li").length > 5 ){
        	    $(".tagAutor li:gt(4)").hide()
          	    $("#autorVerMenos").removeClass("hidden");
			    $("#autorVerMas").removeClass("hidden");
            }

            if ($(".tagCreador li").length > 5 ){
        	    $(".tagCreador li:gt(4)").hide()
        	      $("#creadorVerMenos").removeClass("hidden");
			      $("#creadorVerMas").removeClass("hidden");
            }

            if ($(".tagFechaCreacion li").length > 5 ){
        	    $(".tagFechaCreacion li:gt(4)").hide()
        	    $("#creacionVerMenos").removeClass("hidden");
			    $("#creacionVerMas").removeClass("hidden");
            }
	    			    		
	    	};
	    		    	
	    	 /////////// 
	    	var contadorProceso = 5;
	    	function verProceso() {
	    		// $(".tagsProceso li:gt(5)").show()
	    		 if ( $(".tagsProceso li").length >= contadorProceso  ){
	    			 contadorProceso = contadorProceso + 5;
	    		 }			   
	    		$(".tagsProceso li:lt("+contadorProceso+")").show() 
	    		
	    	}

	    	function OcultarProceso() {
	    		 //$(".tagsProceso li:gt(5)").hide()
	    		if ( contadorProceso > 5 ){
	    			contadorProceso = contadorProceso - 5;
	    		 }
	    		 
	    		$(".tagsProceso li:gt("+contadorProceso+")").hide() 
	    		 
	    	}

       ///////////      
             var contadorMaterias = 5 ;
	    	function verMaterias() {
			   // $(".tagMateria li:gt(5)").show() 
			   
	    		 if ( $(".tagMateria li").length >= contadorMaterias  ){
	    			 contadorMaterias = contadorMaterias + 5;
	    		 }
			   
	    		$(".tagMateria li:lt("+contadorMaterias+")").show() 
 		
			}
		
	    	function OcultarMaterias() {
	    		//$(".tagMateria li:gt(5)").hide()
	    		if ( contadorMaterias > 5 ){
	    			 contadorMaterias = contadorMaterias - 5;
	    		 }
	    		 
	    		$(".tagMateria li:gt("+contadorMaterias+")").hide() 
	    			    		    		
	    	}
        //////////////
        
          var contadorAutor = 5 ;
        	function verAutor() {
        		//$(".tagAutor li:gt(5)").show()

        		 if ( $(".tagAutor li").length >= contadorAutor  ){
        			 contadorAutor = contadorAutor + 5;
	    		 }
			   
	    		$(".tagAutor li:lt("+contadorAutor+")").show() 
        		
	    	}

	    	function OcultarAutor() {
	    		// $(".tagAutor li:gt(5)").hide()

	    		if ( contadorAutor > 5 ){
	    			contadorAutor = contadorAutor - 5;
	    		 }
	    		 
	    		$(".tagAutor li:gt("+contadorAutor+")").hide() 
	    		
	    	}
	    	///////////////////////
	    	var contadorCreador = 5 ;
	        function verCreador() {
	        	 //$(".tagCreador li:gt(5)").show()

	        	 if ( $(".tagCreador li").length >= contadorCreador  ){
	        		 contadorCreador = contadorCreador + 5;
	    		 }
			   
	    		$(".tagCreador li:lt("+contadorCreador+")").show() 
	        	 
	    	}

	    	function OcultarCreador() {
	    		 //$(".tagCreador li:gt(5)").hide()
	    		if ( contadorCreador > 5 ){
	    			contadorCreador = contadorCreador - 5;
	    		 }
	    		 
	    		$(".tagCreador li:gt("+contadorCreador+")").hide() 
	    		
	    	}

	    	////////////////////////
	    	var contadorFechaCreacion = 5 ;
	    	function verCreacion() {
	    		// $(".tagFechaCreacion li:gt(5)").show()

	    		 if ( $(".tagFechaCreacion li").length >= contadorFechaCreacion  ){
	    			 contadorFechaCreacion = contadorFechaCreacion + 5;
	    		 }
			   
	    		$(".tagFechaCreacion li:lt("+contadorFechaCreacion+")").show() 
	    		
	    	}

	    	function OcultarCreacion() {
	    		// $(".tagFechaCreacion li:gt(5)").hide()
	    		if ( contadorFechaCreacion > 5 ){
	    			contadorFechaCreacion = contadorFechaCreacion - 5;
	    		 }
	    		 
	    		$(".tagFechaCreacion li:gt("+contadorFechaCreacion+")").hide() 
	    		
	    	}

	    	/*
	    	function formatTablaResultadoBusqueda() {
		    	
				var contextPath = "${pageContext.request.contextPath}"
	
			
				var buscarDTO = {}
				buscarDTO["palabraClave"] = "${buscarDTO.palabraClave}";
				buscarDTO["fechaInicio"] = "${buscarDTO.fechaInicio}";
				buscarDTO["fechaFin"] = "${buscarDTO.fechaFin}";


				
				if ("${buscarDTO.tipoDeObjeto}" != ""){
					   buscarDTO["tipoDeObjeto"] =  ("${buscarDTO.tipoDeObjeto}").replace("[", "").replace("]", "").replace(" ","").split(","); 
				}

				 
				if ("${buscarDTO.filtrosBusqueda}" != ""){
						buscarDTO["filtrosBusqueda"] = 	("${buscarDTO.filtrosBusqueda}").replace("[", "").replace("]", "").replace(" ","").split(",");
				}
				
				buscarDTO["tipoDeObjetoParaBuscar"] = "${buscarDTO.tipoDeObjetoParaBuscar}";
				buscarDTO["soloEnMiBandejaDeSalida"] = "${buscarDTO.soloEnMiBandejaDeSalida}";
				buscarDTO["tipoDocumentoOficial"] = "${buscarDTO.tipoDocumentoOficial}";
				buscarDTO["nombreTipoDocumento"] = "${buscarDTO.nombreTipoDocumento}";
				buscarDTO["flagTipoBusqueda"] = "${buscarDTO.flagTipoBusqueda}";
				buscarDTO["nombreSubprocesoVigente"] = "${buscarDTO.nombreSubprocesoVigente}";
				
				buscarDTO["nombreFiltro"] = "${buscarDTO.nombreFiltro}";
				buscarDTO["tipoFiltro"] = "${buscarDTO.tipoFiltro}";
				
				  var tablaResultadoBusqueda = $('#tablaResultadoBusqueda').DataTable( {
				        processing: true,
				        serverSide: true,
				        pageLength: 10,
				        language: lenguaje_es,
				        ajax: {
				        	url:contextPath+"/getResultadoBusquedaPaginado",	
				            type: "POST",
	 						 data: function ( data ) {
							  	console.log(data);
							   data.buscaDTOString = JSON.stringify(buscarDTO);
	 
				             }
				         },
				         columns:[
				        	 {data:'tipoObjeto' ,
			                	 render: function ( data, type, row ) {
	
			                	 		var valor = "";
						                      if ( data == 'Documento' ) {					                          
						                    	 valor = row.tipoDeDocumento;
					                         }else{
					                        	 valor = data;
						                     }
					                 
	                        		 return valor;	
			                	 }	 
	                         ,orderable: false},		
				        	 {data:'nombreDeObjeto'
				        		 ,
			                	 render: function ( data, type, row ) { // revisar problema que no imprime el /
	
			                	 		var valor = "";
						                      if (row.tipoObjeto == 'Documento' ) {		
						                    	  valor = '<a href= "#" onclick=descargaArchivo(\"getArchivoPorId/' + row.idObjeto + '\") >' + data + '</a>';																														
					                         }else{
					                        	 valor = data;
						                     }
					                 
	                        		 return valor;	
			                	 }	 
	
	                         ,orderable: false},		
				        	 {data:'subProceso',orderable: false},		
				        	 {data:'materia',orderable: false},		
				        	 {data:'creador' 
				        		 ,
			                	 render: function ( data, type, row ) {
	
			                	 		var valor = "";
						                      if ( row.tipoObjeto == 'Documento' ) {
						                          if ( data == '' ) {
								                    	 valor = "N/A";
							                         }else{
							                        	 valor = data;
								                     }
					                         }else{
					                        	 if ( row.autor == '' ) {
							                    	 valor = "N/A";
						                         }else{
						                        	 valor = row.autor;
							                     }
						                     }
					                 
	                        		 return valor;	
			                	 }	 
				        	 ,orderable: false},				
				        	 {data:'fechaInicioInstanciaDeProceso' ,
			                	 render: function ( data, type, row ) {
			                		 return (moment(data).format("DD/MM/YYYY (HH:mm:ss)"));
			                	 }	 
				        	 ,orderable: false},		
				        	 {data:'fechaFinInstanciaDeProceso' ,
			                	 render: function ( data, type, row ) {
	
			                	 		var valor = "";
						                      if ( data == null ) {					                          
						                    	 valor = "N/A"
					                         }else{
					                        	 valor = (moment(data).format("DD/MM/YYYY (HH:mm:ss)"));
						                     }
					                 
	                        		 return valor;	
			                	 }	 
				        	 ,orderable: false},		
				        	 {data:'nombreEstadoDeProceso',orderable: false},		
				        	 {data:'tipoObjeto' ,
			                	 render: function ( data, type, row ) {
	
			                	 		var valor = "";
						                      if ( row.tipoObjeto == 'Documento' ) {					                          
						                    	 valor = '<a href="#" class="btn btn-info btn-xs"'
														+ "onclick=\"cargarDetalleDeProceso('" + row.idExpedienteQueLoContiene + "'," 
														+ "'" + row.subProceso + "',"
														+ "'" + row.nombreExpedienteQueLoContiene+ "')\"> Ver </a><br>"
														
														+ '<a href="#" data-nombreexpediente="' + row.nombreExpedienteQueLoContiene + '"'
														+ "onclick=\"notificarInstanciaDeProcesoDesdeBusqqueda('" + row.nombreExpedienteQueLoContiene + "')\"" 
														+ 'class="btn btn-default btn-sm boton-notificar-busqueda" id="botonNotificarInstanciaDeProcesoDesdeBusqqueda">'
														+ '<span class="glyphicon glyphicon-send"></span></a><br>'
	                                                   // Boton que permite buscar el diagrama
														+ '<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso"' 																										
														+ "onclick=\"buscarDiagramaSubproceso('" + row.idExpedienteQueLoContiene + "')\" >"
														+	' <span class="fa fa-gears"></span></a>'
													   ///////////////////////////////////////////////////// 	
					                         }else{
					                        	 valor = '<a href="#" class="btn btn-info btn-xs"'
														+ "onclick=\"cargarDetalleDeProceso('" + row.idObjeto + "'," 
														+ "'" + row.subProceso + "',"
														+ "'" + row.nombreDeObjeto+ "')\"> Ver </a><br>"
														
														+ '<a href="#" data-nombreexpediente="' + row.nombreDeObjeto + '"'
														+ "onclick=\"notificarInstanciaDeProcesoDesdeBusqqueda('" + row.nombreDeObjeto + "')\"" 
														+ 'class="btn btn-default btn-sm boton-notificar-busqueda" id="botonNotificarInstanciaDeProcesoDesdeBusqqueda">'
	
															+ '<span class="glyphicon glyphicon-send"></span></a><br>'
	                                                  // Boton que permite buscar el diagrama
														+ '<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso"' 																										
														+ "onclick=\"buscarDiagramaSubproceso('" + row.idObjeto + "')\" >"
														+	' <span class="fa fa-gears"></span></a>'
													   ///////////////////////////////////////////////////// 	
						                     }
					                 
	                        		 return valor;	
			                	 }	 
				        	 ,orderable: false}
			             ],
	
			                
					});

			
	    	}




	    	function buscarRegistrosPaginadosExportExcel() {
	    		var contextPath = "${pageContext.request.contextPath}"


	    		var buscarDTO = {}
				buscarDTO["palabraClave"] = "${buscarDTO.palabraClave}";
				buscarDTO["fechaInicio"] = "${buscarDTO.fechaInicio}";
				buscarDTO["fechaFin"] = "${buscarDTO.fechaFin}";


				
				if ("${buscarDTO.tipoDeObjeto}" != ""){
					   buscarDTO["tipoDeObjeto"] =  ("${buscarDTO.tipoDeObjeto}").replace("[", "").replace("]", "").replace(" ","").split(","); 
				}

				 
				if ("${buscarDTO.filtrosBusqueda}" != ""){
						buscarDTO["filtrosBusqueda"] = 	("${buscarDTO.filtrosBusqueda}").replace("[", "").replace("]", "").replace(" ","").split(",");
				}
				
				buscarDTO["tipoDeObjetoParaBuscar"] = "${buscarDTO.tipoDeObjetoParaBuscar}";
				buscarDTO["soloEnMiBandejaDeSalida"] = "${buscarDTO.soloEnMiBandejaDeSalida}";
				buscarDTO["tipoDocumentoOficial"] = "${buscarDTO.tipoDocumentoOficial}";
				buscarDTO["nombreTipoDocumento"] = "${buscarDTO.nombreTipoDocumento}";
				buscarDTO["flagTipoBusqueda"] = "${buscarDTO.flagTipoBusqueda}";
				buscarDTO["nombreSubprocesoVigente"] = "${buscarDTO.nombreSubprocesoVigente}";
				
				buscarDTO["nombreFiltro"] = "${buscarDTO.nombreFiltro}";
				buscarDTO["tipoFiltro"] = "${buscarDTO.tipoFiltro}";
		    		
		    		
	    		var a = document.createElement('a');
	    		  a.target="_blank";
	    		  a.href= contextPath + "/buscarRegistrosPaginadosExportExcel?" + [
		    		      "buscarDTO=" + JSON.stringify(buscarDTO)
	    			  ].join("&");
	    		  a.click(); 
	    	}
			*/
	    	
		 	   
				function formatTablaResultadoBusqueda() {
			
				    var tablaResultadoBusqueda = $('#tablaResultadoBusqueda')
				                 .DataTable(                        		
				                               {
				                            	   buttons : [{
				                       	                extend : 'excelHtml5',
				                       	                filename : 'Busqueda.*',
				                       	                exportOptions : {
				                       	                       columns : ':visible'
				                       	                }
				                       	         }, 'colvis' ],
				                       			bFilter: false,	
				                       			pageLength: 10,		
				                       			language: lenguaje_es,

				                				/*
				                       		    columnDefs: [
				                       		             {
				                       		                 "targets": [ 5 ],
				                       		                 "visible": false				                       		                
				                       		             },
				                       		             {
				                       		                 "targets": [ 6 ],
				                       		                 "visible": false
				                       		             }
				                       		         ],
				                       		      */   
				                       		      searching: true
				
				                                     
				                               });
				    tablaResultadoBusqueda.buttons().container().appendTo(
				                 '#tablaResultadoBusqueda_wrapper .row:eq(0)');
		           
				
				}
				
				$(document).ready(function() {
				    formatTablaResultadoBusqueda();
					DejarLosPrimerosAtributos();
				});				
				
			</script>

	</c:otherwise>

</c:choose>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function notificarInstanciaDeProcesoDesdeBusqqueda(nombreExpediente) {
	console.log("nombreExpediente: " + nombreExpediente);
   	var urlNotificarInstanciaDeProcesoDesdeBusqueda = "${urlNotificarInstanciaDeProcesoDesdeBusqueda}" + nombreExpediente;
   	console.log("urlNotificarInstanciaDeProcesoDesdeBusqueda: " + urlNotificarInstanciaDeProcesoDesdeBusqueda);
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
            console.log("Inicio window.open");
            window.open(urlNotificarInstanciaDeProcesoDesdeBusqueda, "Notificar", 'width=750, height=720');
            console.log("Fin window.open");
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});
}

function limpiaBusqueda() {

	for (i = 0; i < ${cantidaDeColumnas}; i++) { 
		 $('#tablaResultadoBusqueda').DataTable().column(i).search(
		    		'',
		        false,
		        true
		    ).draw();
	}
	
}

function filterColumn(indiceColumna, texto, thisObj) {


	//limpiaBusquedaPorCampo(indiceColumna);
	
	   var	hijoImput = thisObj.children();
	   
	   var estadoImput = hijoImput[0].checked;

	   if (estadoImput == true){
			$(thisObj).children(':checkbox').prop('checked', false);  
	   }else{
		   $(thisObj).children(':checkbox').prop('checked', true);
	   }
	


		
  // ---------------------------------- busca lista  ------------------------------------
  var listaProceso = generarListaFiltrosLaterales(".tagsProceso");
  var listaMateria = generarListaFiltrosLaterales(".tagMateria");
  var listaAutor =  generarListaFiltrosLaterales(".tagAutor");
  var listaCreador =  generarListaFiltrosLaterales(".tagCreador");
  // ---------------------------------- ------------------------------------	
  
  
	    /*  Este if es para buscar en el datable , falta agregar el for del metodo de arriba
	     if (hijoImput == true){
	    	 if (listaTextoColumna == ""){
	    		 listaTextoColumna = '^\\s*'+$(c).children(colIndex)[1].innerText +'\\s*$';
		     }else{
		    	 listaTextoColumna = listaTextoColumna + "|" + '^\\s*'+$(c).children(colIndex)[1].innerText +'\\s*$';
			 } 
		  }
		  */


	busquedaInformacionConListaDeFiltro(listaProceso,listaMateria,listaAutor,listaCreador);
	

	
/*	
   var	hijoImput = thisObj.children();
   
   var estadoImput = hijoImput[0].checked;

   if (estadoImput == true){
		$(thisObj).children(':checkbox').prop('checked', false);  
		 limpiaBusquedaPorCampo(indiceColumna)
   }else{
	    filtrarPorColumna(indiceColumna, texto);
		$(thisObj).children(':checkbox').prop('checked', true);
   }
 */
}



function busquedaInformacionConListaDeFiltro(listaSubproceso,listaMateria,listaAutor,listaCreador){

	var contextPath = "${pageContext.request.contextPath}"

	var buscarDTO = {}
	buscarDTO["palabraClave"] = "${buscarDTO.palabraClave}";
	buscarDTO["fechaInicio"] = "${buscarDTO.fechaInicio}";
	buscarDTO["fechaFin"] = "${buscarDTO.fechaFin}";


	
	if ("${buscarDTO.tipoDeObjeto}" != ""){
		   buscarDTO["tipoDeObjeto"] =  ("${buscarDTO.tipoDeObjeto}").replace("[", "").replace("]", "").replace(" ","").split(","); 
	}

	 
	if ("${buscarDTO.filtrosBusqueda}" != ""){
			buscarDTO["filtrosBusqueda"] = 	("${buscarDTO.filtrosBusqueda}").replace("[", "").replace("]", "").replace(" ","").split(",");
	}
	
	buscarDTO["tipoDeObjetoParaBuscar"] = "${buscarDTO.tipoDeObjetoParaBuscar}";
	buscarDTO["soloEnMiBandejaDeSalida"] = "${buscarDTO.soloEnMiBandejaDeSalida}";
	buscarDTO["tipoDocumentoOficial"] = "${buscarDTO.tipoDocumentoOficial}";
	buscarDTO["nombreTipoDocumento"] = "${buscarDTO.nombreTipoDocumento}";
	buscarDTO["flagTipoBusqueda"] = "${buscarDTO.flagTipoBusqueda}";
	buscarDTO["nombreSubprocesoVigente"] = "${buscarDTO.nombreSubprocesoVigente}";
	
	buscarDTO["nombreFiltro"] = "${buscarDTO.nombreFiltro}";
	buscarDTO["tipoFiltro"] = "${buscarDTO.tipoFiltro}";
		  
	buscarDTO["listaSubproceso"] = encodeURIComponent(listaSubproceso);
	buscarDTO["listaMateria"] = encodeURIComponent(listaMateria);
	buscarDTO["listaAutor"] = encodeURIComponent(listaAutor);
	buscarDTO["listaCreador"] = encodeURIComponent(listaCreador);

    var listaSubprocesoGuardada = listaSubproceso;
	var listaMateriaGuardada = listaMateria;
    var listaAutorGuardado = listaAutor;
    var listaCreadorGuardada = listaCreador;
			
	var dialogBuscarFiltro = "";
	
	$.get("${sessionURL}", function(haySession) {
	    if (haySession) {  
	    	$.ajax({
				type : "POST",
				contentType : "application/json",
				url : contextPath + "/getResultadoBusquedaConListaFiltros",
				data : JSON.stringify(buscarDTO),
				timeout : 100000,
				 beforeSend: function () {
				     dialogBuscarFiltro = bootbox.dialog({
				            message: '<p><i class="fa fa-spin fa-spinner"></i> Buscando Datos ... </p>',
				              closeButton: false
				        });
				    },
				    complete: function () {
				    	  dialogBuscarFiltro.modal('hide');
				    		var listaSubproceso = decodeURIComponent("${buscarDTO.listaSubproceso}");

			    		// ----------------------------------------------------------------------
			          buscaCheckSeleccionado(".tagsProceso",listaSubprocesoGuardada);
			          buscaCheckSeleccionado(".tagMateria",listaMateriaGuardada);
			          buscaCheckSeleccionado(".tagAutor",listaAutorGuardado);
			          buscaCheckSeleccionado(".tagCreador",listaCreadorGuardada);
			          			    	  
				    	  
				    },	
					success : function(data) {
						 $("#divResultadoBusqueda").html(data);
					}
			});

  
	    } else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	            , function(){ window.open('${raizURL}', '_blank');}
	            );
	    }
	});
	
    	
 }



		function generarListaFiltrosLaterales(tag) {
			
			 var listaFiltro = "";
				
			  $(tag).children().each(function (colIndex, c) {
				   var	hijoImput = $(c).children()[0].checked;

				     if (hijoImput == true){
				    	 if (listaFiltro == ""){
				    		 listaFiltro = $(c).children(colIndex)[1].innerText;
					     }else{
					    	 listaFiltro = listaCreador  + ",," +$(c).children(colIndex)[1].innerText;
						 } 
					  }


			  }); 

			  return listaFiltro
				
		}



	function buscaCheckSeleccionado(tag, listaGuardada) {
			  $(tag).children().each(function (colIndex, c) {

		    		var  nombre = $(c).children(colIndex)[1].innerText ;
			    		
		    		var arrayFiltro = listaGuardada.split(',,');
			    		
		    		for(var i = 0; i < arrayFiltro.length; i++) {

		    			if (nombre == arrayFiltro[i] ){				    		
							var imputHijo =  $(c).children()[0];
							$(imputHijo).prop('checked', true);
		    			}
						
		    		 }      					    		  

	    	  }); 
			
		}

	function limpiaBusquedaPorCampo(indiceColumna) {
	
		 $('#tablaResultadoBusqueda').DataTable().column(indiceColumna).search(
		    		'',
		        false,
		        true
		    ).draw();		
	}
	
	function filtrarPorColumna(indiceColumna, texto) {
	
	console.log("indiceColumna: " + indiceColumna);
	console.log("texto: " + texto);		
	// limpiaBusqueda();    
	$('#tablaResultadoBusqueda').DataTable().column(indiceColumna).search(texto,
		//'^\\s*'+texto +'\\s*$',
	   true, // expresiones regulares
	   false, // si la primera esta en true poner el fase la segunda
	   false  // caseInsensitive
	).draw();
	
	}

	function abrirDiagramaSubprocesoParaReaperturaYSalto(nombreExpediente) {

		var urlDiagramaDeProcesoReaperturaSalto = "${pageContext.request.contextPath}" + "/diagramaDeProcesoReaperturaSalto/" + nombreExpediente;
	    $.get("${sessionURL}", function(haySession) {
			if(haySession) {
				window.open(urlDiagramaDeProcesoReaperturaSalto, "Reapertura y salto", 'width=1700, height=900');			
			} else {
				  bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
								, function(){
									  window.open('${raizURL}', '_blank');
								}
				   );
			}
	  	});	
	 		       	
	}

	function callParentCargaResultadoBusqueda() {
		cargaResultadoBusqueda();
	}

	/*
	function callParentReasigna() {
		cargarDatosContinuarProcesoModal(5832, 
			    'd5f37392-475f-498b-a2c7-e59ed3ea426f', 
			    false, 
			    false,
			    'Visar documento - taller 4 - EXP-792-2017',
			    false, 
			    true, 
			    '',
			    'Visar documento');
	}*/

</script>
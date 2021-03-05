<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="permisoSubirCarta" value="<%=PermisoType.SUBIR_CARTA.getNombrePermiso()%>"/>

<c:set var="permisoCrearExpediente" value="<%=PermisoType.CREAR_EXPEDIENTE.getNombrePermiso()%>"/>

<c:set var="permisoPuedeVerTareasEnEjecucion" value="<%=PermisoType.PUEDE_VER_TAREAS_EN_EJECUCION.getNombrePermiso()%>"/>

<html>

	<head>
	
		<title>Bandeja de entrada</title>

		
		<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>
		
	
	</head>

	<body>
	
		<div class="container-fluid container-sgdp" id="contenedorBEPrincipal">
		
			<div class="row content">
			
			    <div class="col-sm-2 sidenav">
			    			      
			      	<c:import url="/WEB-INF/jsp/objetos/menu.jsp"></c:import>
			      			      
			    </div>	
			    	
		    	<div class="col-sm-10">
		    		
		    		<%-- 
			    	<div class="row" style="background-color: #41CAC0; color: #fff;">
			    	
			    		<div class="col-sm-1"></div>
			    	
			    		<div class="col-sm-9"><h2>&Aacuterea de Trabajo</h2></div>		
			    		
			    		<div class="col-sm-2"><c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import></div>  
			    	
			    	</div>  
		      		--%>
		      
		      		<c:choose>
						<c:when test = "${usuario.fueraDeOficina eq true}">
							<c:set var="colorBackHeaderAreaDeTrabajo" value="#FFD51D" />
							<c:set var="mensajeAreaDeTrabajo" value="&Aacuterea de Trabajo (Fuera de Oficina)" />
						</c:when>
						<c:otherwise>
							<c:set var="colorBackHeaderAreaDeTrabajo" value="#41CAC0" />
							<c:set var="mensajeAreaDeTrabajo" value="&Aacuterea de Trabajo" />							
						</c:otherwise>						
					</c:choose>
					
					<div id="divBackHeaderAreaDeTrabajo" class="row div-area-trabajo-cab" style="background-color: ${colorBackHeaderAreaDeTrabajo}; color: #fff;">			    		
			    	
			    		<div class="col-sm-10"><h2 id="h2MensajeAreaDeTrabajo">${mensajeAreaDeTrabajo}</h2></div>		
			    		
			    		<div class="col-sm-2"><c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import></div>  
			    	
			    	</div> 
		      
		      		<hr>
		      
			      	<div class="row">
			      
				      	<%-- <div class="col-sm-1"></div>--%>
				      
						<div class="col-sm-12">
						
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#tabBandejaDeEntrada">Bandeja de Entrada</a></li>								
								<c:if test = "${permisos[permisoPuedeVerTareasEnEjecucion] eq permisoPuedeVerTareasEnEjecucion}"> 
									<li><a data-toggle="tab" href="#tabProcesosEnEjecucion" onclick="cargaTareasEnEjecucion()">SubProcesos en Ejecuci&oacute;n</a></li>
								</c:if>
								<li><a data-toggle="tab" href="#tabNotificacionesSeguimientos" onclick="notificacionSeguimiento()">Notificaciones y Seguimientos</a></li>
								<%--<li><a data-toggle="tab" href="#tabTodosLosProcesosEnEjecucion" onclick="cargaTodosLosProcesosEnEjecucion()">Todos los SubProcesos en Ejecuci&oacute;n</a></li>--%>
							</ul>			
						
						</div>		      
			      
			      	</div> 
			  
			  
					<div class="tab-content">
			
						<div id="tabBandejaDeEntrada" class="tab-pane fade in active">	   	
						   	
						   	<c:if test = "${mensajeVistaDTO.mensajes ne null and !empty mensajeVistaDTO.mensajes}">
				            	<br>
			            		<div class="row">	
			            		
			            			<%-- <div class="col-sm-1"></div>--%>	            			
							
									<div class="col-sm-12" id="mensajeria">
									
																		
										<c:import url="/WEB-INF/jsp/div/mensajeria.jsp"></c:import>
										
								
									</div>                   	
			                   
								</div>
			            
			            	</c:if>			            
			            	
						   	<br><br> 	
						   	
							   	<div class="row">
							   	
							   		<%-- <div class="col-sm-1"></div>--%>
						            <div class="col-sm-12">	
						            
						            	 <c:if test = "${permisos[permisoCrearExpediente] eq permisoCrearExpediente}">						  
							              	<button type="button" class="btn btn-primary" id="crearExpediente" onclick="abrirCrearExpediente()">
							            		<i class="glyphicon glyphicon-pencil"></i> <spring:message code="bandejaDeEntrada.boton.crearExpediente.nombre"/>
							              	</button>
							            </c:if>
							            <!--  
							             <button type="button" class="btn btn-primary alinearDerecha" id="filtroExpediente" >
							            <i class="glyphicon glyphicon-pencil"></i> Filtro
							              </button>
							            -->   							            

							      <div class="button-group pull-right">
									<button type="button"
										class="btn btn-default btn-sm dropdown-toggle"
										data-toggle="dropdown">
										<span class="fa fa-filter"></span> <span
											class="caret"></span>
									</button>
									<ul class="dropdown-menu pull-right">
										<li class="tamanioTextoFiltro">										
											<a href="#" class="small" data-value="option1" tabIndex="-1">
												<input type="checkbox" name="respuestaEspera" id="respuestaEspera" >
												En espera
											</a>
										</li>
										<li class="tamanioTextoFiltro">
											<a href="#" class="small" data-value="option2" tabIndex="-1">
											<input type="checkbox" name="trabajoInterno" id="trabajoInterno" checked>
												En ejecuci&oacute;n
											</a>	
										</li>
										
										<li class="centrarTextoBoton"><button type="button" class="btn btn-primary tamanioBotonFiltro" id="filtarExpediente" 
                                           onclick="filtrarExpediente()">
							            	 Filtrar
							            </button></li>
									</ul>
								</div>
							            
						            </div>
							   	</div>	
							  <script type="text/javascript">

							  var options = [];

							  $( '.dropdown-menu a' ).on( 'click', function( event ) {

							     var $target = $( event.currentTarget ),
							         val = $target.attr( 'data-value' ),
							         $inp = $target.find( 'input' ),
							         idx;	
						         
							     if ( ( idx = options.indexOf( val ) ) > -1 ) {
							        options.splice( idx, 1 );
							        setTimeout( function() {  $inp.prop( 'checked', false ); }, 0);
							     } else {								   
							        options.push( val );
							        setTimeout( function() { $inp.prop( 'checked', true ); }, 0);
							     }

							     $( event.target ).blur();
							        
							     console.log(options);
							     
							     return false;
							     
							  });

							  
								  function filtrarExpediente(){	

									  var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();  
									  var contextPath = "${pageContext.request.contextPath}"
										  
									  var filtroExpediente = {}
									   filtroExpediente["respuestaEspera"] =  $('#respuestaEspera').is(':checked');
									   filtroExpediente["trabajoInterno"] =  $('#trabajoInterno').is(':checked');
																																			 
										// -----------------------
										 
									    	$.ajax({
												type : "POST",
												contentType : "application/json",
												url : contextPath + "/filtrarExpediente",
												data : JSON.stringify(filtroExpediente),
												dataType : 'json',
												timeout : 100000,
												success : function(data) {
								                     console.log("SUCCESS: ", data);	
								                     actualizaTareas("tareasBandejaDeEntrada", urlGetInstanciasDeTarea, true);	
								                     $("#divTabsDetalleDeTarea").addClass('hide');								                											 
												},
												error : function(e) {
													 console.log("ERROR: ", e);
												},
												done : function(e) {
													 console.log("MAS ERROR: ", e);
												}
											});									  										    								     
								  }


							         $(document).ready(function(){
							        	 actualizarFiltrosGuardadosEnSession();
								       });

							         
								function actualizarFiltrosGuardadosEnSession(){
									
							        	 var tareaEnEspera = '<c:out value="${tareaEnEspera}"/>'; 
							        	 var trabajoInterno = '<c:out value="${trabajoInterno}"/>'; 
	
	                                      if(tareaEnEspera != ''){
	                                    	  if(tareaEnEspera == "true"){
	                                    		  $('#respuestaEspera').prop('checked', true);
	
	                                          }else{
	                                        	  $('#respuestaEspera').prop('checked', false);
	                                          }
	
	                                      }
	
	                                      if(trabajoInterno != ''){
	                                    	  if(trabajoInterno == "true"){
	                                    		  $('#trabajoInterno').prop('checked', true);
	
	                                          }else{
	                                        	  $('#trabajoInterno').prop('checked', false);
	                                          }	
	                                      }						        	 
									}
									  
								  
							  </script>         				
							   	<br>

						   	<div class="row">
						   		<div class="col-sm-12" id="tareasBandejaDeEntrada">
						   			
						   			<c:import url="/WEB-INF/jsp/div/tareasBandejaDeEntrada.jsp"></c:import>
						   	
						   		</div>											
							</div>							
							
							<br><br> 
							
							
							<%-- Corresponde al div
							
							
							<div class="embed-responsive embed-responsive-16by9 row hide" id="iframeDivDetalleDeTarea">
                         	</div>		
                         	 --%>
                         	 
                      		<div id="divTabsDetalleDeTareaForm" class="row hide">								
									
							   		<div class="col-sm-12">
							   		
							   			<ul class="nav nav-tabs">
											<li class="active"><a data-toggle="tab" href="#tabDivDetalleDeTareaForm">Tarea</a></li>
											<%-- <li><a data-toggle="tab" href="#tabInformacionDelProceso">Informaci&oacute;n del SubProceso</a></li>--%>
											<li><a data-toggle="tab" href="#tabHistorialDelProcesoForm">Historial</a></li>																								
										</ul>
							   		
							   		</div>
							   		
							   		<div class="tab-content">
							   		
							   			<div id="tabDivDetalleDeTareaForm" class="tab-pane fade in active">
																				 	
											<div class="row">											 
												
												<div class="col-sm-12" id="divDetalleDeTareaDesdeApp"></div>
												
											</div>	

										</div>		
									 
										<div id="tabHistorialDelProcesoForm" class="tab-pane fade">	
																			 	
										 	<div class="row">	
										 	
										 		<fieldset class="col-md-12">
													<br>
													<div class="col-sm-12">														
														
														<legend>Historial de tareas</legend>
														<div id="divTablaHistorialDeInstanciaDeProcesoForm">					
														</div>
														
														<legend>Historial de documentos</legend>
														<div class="col-sm-12" id="divTablaHistorialDeDocumentosForm">					
														</div>														
														
													</div>
												
												</fieldset>	
												
												
											</div>									
											  
										 </div>
						            
					            	</div>
							 	</div>	
							
							<%--  ######################################################    --%>
								
							
							<div id="divTabsDetalleDeTarea" class="row hide">								
									
							   		<div class="col-sm-12">
							   		
							   			<ul class="nav nav-tabs">
											<li id="liTabDivDetalleDeTarea" class="active"><a data-toggle="tab" href="#tabDivDetalleDeTarea">Tarea</a></li>
											<li id="liTabHistorialDeTareas"><a data-toggle="tab" href="#tabDivHistorialDeTareas">Historial de tareas</a></li>
											<li id="liTabHistorialDeDocumentos"><a data-toggle="tab" href="#tabDivHistorialDeDocumentos">Historial de documentos</a></li>																								
										</ul>
							   		
							   		</div>
							   		
							   		<div class="tab-content">
							   		
							   			<div id="tabDivDetalleDeTarea" class="tab-pane fade">	
							   			
											<div class="col-sm-12" id="divDetalleDeTarea">																								
											</div>	
										</div>

										<div id="tabDivHistorialDeTareas" class="tab-pane fade">																						
											<div class="col-sm-12 panel-body" id="divTablaHistorialDeInstanciaDeProceso">												
											</div>																						
										</div>
										
										<div id="tabDivHistorialDeDocumentos" class="tab-pane fade">																						
											<div class="col-sm-12 panel-body" id="divTablaHistorialDeDocumentos">
											</div>											
										</div>

					            	</div>
							 	</div>					 
						 		 		
				 			
						</div>
							
								
						<div id="tabProcesosEnEjecucion" class="tab-pane fade">

						 
						 	<br><br> 
						 	
						 	<c:if test = "${permisos[permisoPuedeVerTareasEnEjecucion] eq permisoPuedeVerTareasEnEjecucion}"> 
								<div class="row">
									<div class="col-sm-12" id="tareasEnEjecucion">
										<%--<c:import url="/WEB-INF/jsp/div/tareasEnEjecucion.jsp"></c:import>--%>
									</div> 
								</div>
							</c:if>	
						 							   
						   	<%-- <c:if test = "${instanciasDeTareasDTOEnEjecucion ne null and !empty instanciasDeTareasDTOEnEjecucion}">
							
								<div class="row">
								
									<div class="col-sm-12" id="tareasEnEjecucion">
									
										<c:import url="/WEB-INF/jsp/div/tareasEnEjecucion.jsp"></c:import>
									
									</div>                   	
				                   
								</div>
							
							</c:if>	--%>

						 </div>		
						<div id="tabNotificacionesSeguimientos" class="tab-pane fade">	

							<div id="tabNotificacionesSeguimientosDiv">
							
							</div>

	                         <script type="text/javascript">
	
		                         jQuery(document).ready(
		                        			function($) { 
		                        				notificacionSeguimiento();                       				   
		                       		     	});
		                			
		                         function notificacionSeguimiento() {
			                         
		                         	var urlSessionValida = $("#urlSessionValida").val();
		                         	var raizURL = $("#raizURL").val();
		                     		
		                        	var contextPath = "${pageContext.request.contextPath}";  

		                 			$.get(urlSessionValida, function(haySession) {
		                 			      console.log("haySession: " + haySession);
		                 			      if(haySession) {

		                 			    	 $("#tabNotificacionesSeguimientosDiv").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
		 		                 			
		 		                 			$("#tabNotificacionesSeguimientosDiv").load(contextPath + "/notificacionSeguimientos" ,
		 		                 					function() {
		 		                 						$("#tabNotificacionesSeguimientosDiv").find(".cargando").remove();
		 		                 	 				});
		                 			 		
			                 			    	 
		                 			      }	else {
		                 			            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
		                 			                          , function(){
		                 			                                window.open(raizURL, '_blank');
		                 			                          }
		                 			             );
		                 			      }
		                 			}); 
                 	 				
		                          }
	                                          
	
	                         </script>                           
                        
			 		    </div>	
			 		    
			 		    <%--<div id="tabTodosLosProcesosEnEjecucion" class="tab-pane fade">	

                			<div id="tabTodosLosProcesosEnEjecucionDiv">
                
                			</div>

            			</div>--%>
			 		    	
		     		</div> 
		      <%-- 
		      
		      <div class="row"> 
		      
		      	<div class="col-sm-1"></div>
		      
				<div class="col-sm-11" id="tareasBandejaDeEntrada">
				
					<c:import url="/WEB-INF/jsp/div/tareasBandejaDeEntrada.jsp"></c:import>
				
				</div>
                  
				</div>
		   
		        <br><br> 
		      
	             <c:if test = "${mensajeVistaDTO.mensajes ne null and !empty mensajeVistaDTO.mensajes}">
	            
	            	<div class="row">
					
						<div id="mensajeria" class="panel-body">
						
							<c:import url="/WEB-INF/jsp/div/mensajeria.jsp"></c:import>
						
						</div>                   	
	                   
					</div>
	            
	            </c:if>
		    </div>
		  </div>
		</div>
	
		--%>
	
		<%-- 
		
		<div class="container-sgdp">
		
			<div id="wrapper">
				
				<div class="col-sm-3">
				
					<c:import url="/WEB-INF/jsp/objetos/menu.jsp"></c:import>
				
				</div>
			
				<div class="col-sm-9">
			
					<div class="row" style="background-color: #41CAC0; color: #fff;">
					
						<div class="col-sm-5">
							<h3><spring:message code="bandejaDeEntrada.cabecera.nombre"/></h3>
						</div>
						
						<div class="col-sm-7">
						
							<c:if test = "${permisos[permisoCrearExpediente] eq permisoCrearExpediente}">        		
					      	
					            <h3>
						            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#crearExpedienteModal">
						            	<i class="glyphicon glyphicon-pencil"></i> <spring:message code="bandejaDeEntrada.boton.crearExpediente.nombre"/>
						            </button>
					            </h3>
					            	       
				        </c:if>	
						
						</div>					                
		               
		               
		            </div>
		            
		            <c:if test = "${mensajeVistaDTO.mensajes ne null and !empty mensajeVistaDTO.mensajes}">
		            
		            	<div class="row">
						
							<div id="mensajeria" class="panel-body">
							
								<c:import url="/WEB-INF/jsp/div/mensajeria.jsp"></c:import>
							
							</div>                   	
		                   
						</div>
		            
		            </c:if>
			
					<div class="row">
						
						<div id="tareasBandejaDeEntrada" class="panel-body">
						
							<c:import url="/WEB-INF/jsp/div/tareasBandejaDeEntrada.jsp"></c:import>
						
						</div>
	                   
					</div>
					
					<c:if test = "${instanciasDeTareasDTOEnEjecucion ne null and !empty instanciasDeTareasDTOEnEjecucion}">
					
						<div class="row" style="padding-left: 30px;">
						
							<div id="tareasEnEjecucion" class="panel-body">
							
								<c:import url="/WEB-INF/jsp/div/tareasEnEjecucion.jsp"></c:import>
							
							</div>                   	
		                   
						</div>
					
					</c:if>		
					
					
					<div  class="row">
					
						<div id="divDetalleDeTarea" class="panel-body">
						
						</div>
						
					</div>
				
				</div>			
			
			</div>
			
		</div>	

		--%>
		
				</div>			
				
			</div>
			
		</div>
		
		<!-- Modal Continuar Proceso  -->				
		<c:import url="/WEB-INF/jsp/modals/continuarProceso.jsp"></c:import>	
		<!-- Modal Subir Documento-->
		
		<c:import url="/WEB-INF/jsp/modals/subirDocumento.jsp"></c:import>
		<!-- Modal Subir Adjunto  -->
		
		<c:import url="/WEB-INF/jsp/modals/subirAdjunto.jsp"></c:import>
		<!-- Modal Subir Documento Requerido  -->
					
		<c:import url="/WEB-INF/jsp/modals/subirDocumentoRequerido.jsp"></c:import>
		<!-- Modal Subir Documento Adicional  -->
				
		<c:import url="/WEB-INF/jsp/modals/subirDocumentoAdicional.jsp"></c:import>
		
		<!-- Firma Avanzada-->
		<c:import url="/WEB-INF/jsp/modals/firmaAvanzada.jsp"></c:import>
		
		<!-- Dibujo de Proceso-->
		<c:import url="/WEB-INF/jsp/modals/dibujoDeProceso.jsp"></c:import>
		
		<!-- Dibujo de Proceso-->
		<c:import url="/WEB-INF/jsp/modals/adjuntarAnteTodaEtapa.jsp"></c:import>
		
		<!-- Modal Detalle Documento-->
		<c:import url="/WEB-INF/jsp/modals/detalleDeDocumento.jsp"></c:import>
		
		<!-- Correo de Distribucion-->
		<c:import url="/WEB-INF/jsp/modals/correoDeDistribucion.jsp"></c:import>
		
		
	</body>
</html>
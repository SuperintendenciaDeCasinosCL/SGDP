<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 
<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %>  
<%@ page import= "cl.gob.scj.sgdp.tipos.EstadoSolicitudCreacionExpType" %>  

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />
<c:set var="permisoCrearExpediente" value="<%=PermisoType.CREAR_EXPEDIENTE.getNombrePermiso()%>"/>
<c:set var="idEstadoSolicitudCreacionExpExt" value="<%=EstadoSolicitudCreacionExpType.SOLICITADA_EXT.getIdEstadoSolicitudCreacionExp()%>"/>

<div class="container">

	<div class="row">
	
		<div class="col-sm-4">
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalNuevaSolicitudDeCreacExp()"><span class="glyphicon glyphicon-plus"></span> Nueva solicitud</button>
				<br>				
		</div>
	
		<div class="col-sm-4">
			
		</div>
		
		<div class="col-sm-4">
			<button type="button" class="btn btn-warning btn-lg btn-block" onclick="abreModalVerSolicitudesFinalizadas()">Ver solicitudes finalizadas</button>
				<br>	
		</div>
	
	</div>
	
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
				  		
			<table id="tablaSolicitudesDeCreacionDeExpediente" class="table table-striped table-bordered table-fixed">											
			    <thead>
			        <tr>
			            <th>ID</th>
			            <th>Nombre Subproceso</th>
			            <th>Usuario que solicita crear expediente</th>
			            <th>Usuario creador de expediente</th>
			            <th>Destinatario</th>
			            <th style="display: none;">Destinatario</th>			            
			            <th>Fecha de la solicitud</th>
			            <th style="display: none;">Fecha de la solicitud</th>
			            <th>Asunto o Materia</th>
			            <th style="display: none;">Asunto o Materia</th>	
			            <th>Autor</th>
			            <th style="display: none;">Autor</th>		         
			            <th>Acciones</th>			            		       
			        </tr>
			    </thead>
			    <tbody>	    
			    	<c:forEach var="solicitudCreacionExpDTO" items="${solicitudesCreacionExpDTO}">	    	
			    		<tr>   			    		
				    		<td class="view-message">${solicitudCreacionExpDTO.idSolicitudCreacionExp}</td>
				    		<td class="view-message">
				    			<c:choose>				    			
									<c:when test="${solicitudCreacionExpDTO.nombreSubProceso ne null and !empty solicitudCreacionExpDTO.nombreSubProceso}">
										<input type="hidden" id="idProceso${solicitudCreacionExpDTO.idSolicitudCreacionExp}" value="${solicitudCreacionExpDTO.idProceso}" />
										${solicitudCreacionExpDTO.nombreSubProceso}		
									</c:when>									
									<c:when test="${solicitudCreacionExpDTO.idEstadoSolicitudCreacionExp eq idEstadoSolicitudCreacionExpExt}">
										<select data-id-solicitud-crea-exp="${solicitudCreacionExpDTO.idSolicitudCreacionExp}" style="width: 100%" class="form-control id-proceso" name="idProceso${solicitudCreacionExpDTO.idSolicitudCreacionExp}" id="idProceso${solicitudCreacionExpDTO.idSolicitudCreacionExp}">									
											<option value="">Seleccione Subproceso</option>
											<c:forEach items="${listaProcesoDto}" var="procesoDTO">
												<option value="${procesoDTO.idProceso}">${procesoDTO.nombreProceso}</option>
											</c:forEach>
										</select>	
									</c:when>	
									<c:otherwise>
										<select data-id-solicitud-crea-exp="${solicitudCreacionExpDTO.idSolicitudCreacionExp}" style="width: 100%" class="form-control id-proceso" name="idProceso${solicitudCreacionExpDTO.idSolicitudCreacionExp}" id="idProceso${solicitudCreacionExpDTO.idSolicitudCreacionExp}">									
											<option value="">Seleccione Subproceso</option>
											<c:forEach items="${todosProcesoFormCreaExpDTO}" var="procesoFormCreaExpDTO">
												<option value="${procesoFormCreaExpDTO.idProceso}">${procesoFormCreaExpDTO.nombreProceso}</option>
											</c:forEach>
										</select>						
									</c:otherwise>
								</c:choose>
							</td>
				    		<td class="view-message">${solicitudCreacionExpDTO.idUsuarioSolicitante}</td>		    		
				    		<td class="view-message">${solicitudCreacionExpDTO.idUsuarioCreadorExpediente}</td>				    		
				    		<td class="view-message">				    		
				    			<c:choose>				    			
									<c:when test="${(permisos[permisoCrearExpediente] eq permisoCrearExpediente and solicitudCreacionExpDTO.idUsuarioCreadorExpediente eq usuario.idUsuario)
											or 
											(permisos[permisoCrearExpediente] eq permisoCrearExpediente and empty solicitudCreacionExpDTO.idUsuarioCreadorExpediente)}">

                                        <select style="width: 100%" class="form-control" name="idUsuarioDestinatario${solicitudCreacionExpDTO.idSolicitudCreacionExp}" id="idUsuarioDestinatario${solicitudCreacionExpDTO.idSolicitudCreacionExp}">									
                                            <c:if test="${solicitudCreacionExpDTO.idsUsuariosDestinatarios ne null and !empty solicitudCreacionExpDTO.idsUsuariosDestinatarios}">
                                                <c:forEach items="${solicitudCreacionExpDTO.idsUsuariosDestinatarios}" var="idUsuarioDestinatario">
                                                    <option value="${idUsuarioDestinatario}">${idUsuarioDestinatario}</option>
                                                </c:forEach>
                                            </c:if>									
                                        </select>
									</c:when>	
									<c:otherwise>
                                        ${solicitudCreacionExpDTO.idUsuarioDestinatario}																
									</c:otherwise>
								</c:choose>
				    		</td>
				    		<td style="display: none;">${solicitudCreacionExpDTO.idUsuarioDestinatario}</td>
							<td>
				    			<span style="display: none;" >
					    		  <fmt:formatDate value="${solicitudCreacionExpDTO.fechaSolicitud}" pattern="yyyy-MM-dd HH:mm:ss" /> 
					    		</span>
					    		<fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM}" value="${solicitudCreacionExpDTO.fechaSolicitud}" />
					    	</td>
					    	<td style="display: none;"><fmt:formatDate pattern="${FORMATO_FECHA_FORM_HH_MM}" value="${solicitudCreacionExpDTO.fechaSolicitud}" /></td>
					    	<td class="view-message">					    		
					    		<c:choose>				    			
									<c:when test="${(permisos[permisoCrearExpediente] eq permisoCrearExpediente and solicitudCreacionExpDTO.idUsuarioCreadorExpediente eq usuario.idUsuario)
											or 
											(permisos[permisoCrearExpediente] eq permisoCrearExpediente and empty solicitudCreacionExpDTO.idUsuarioCreadorExpediente)}">
                                        <input style="width: 100%" type="text" class="form-control" id="asuntoMateria${solicitudCreacionExpDTO.idSolicitudCreacionExp}" 
                                        value='<c:out value="${solicitudCreacionExpDTO.asuntoMateria}"/>  '>
									</c:when>	
									<c:otherwise>
                                        ${solicitudCreacionExpDTO.asuntoMateria}																
									</c:otherwise>
								</c:choose>	
					    	</td>
					    	<td style="display: none;">${solicitudCreacionExpDTO.asuntoMateria}</td>
							<td class="view-message">								
								<c:choose>				    			
									<c:when test="${(permisos[permisoCrearExpediente] eq permisoCrearExpediente and solicitudCreacionExpDTO.idUsuarioCreadorExpediente eq usuario.idUsuario)
											or 
											(permisos[permisoCrearExpediente] eq permisoCrearExpediente and empty solicitudCreacionExpDTO.idUsuarioCreadorExpediente)}">

                                        <select style="width: 100%" class="form-control" name="idAutorSolicitudExp${solicitudCreacionExpDTO.idSolicitudCreacionExp}" id="idAutorSolicitudExp${solicitudCreacionExpDTO.idSolicitudCreacionExp}">									
											<c:if test="${solicitudCreacionExpDTO.idEstadoSolicitudCreacionExp eq idEstadoSolicitudCreacionExpExt}">
												<option value="">Seleccione autor</option>
											</c:if>									
											<c:forEach items="${solicitudCreacionExpDTO.autoresDTO}" var="autorDTO">
												<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
											</c:forEach>
										</select>
									</c:when>	
									<c:otherwise>
                                        ${solicitudCreacionExpDTO.autorDTO.nombreAutor}																
									</c:otherwise>
								</c:choose>								
							</td>
							<td style="display: none;">${solicitudCreacionExpDTO.autorDTO.nombreAutor}</td>	
							<td class="view-message">														
								<c:if test="${(permisos[permisoCrearExpediente] eq permisoCrearExpediente and solicitudCreacionExpDTO.idUsuarioCreadorExpediente eq usuario.idUsuario)
											or 
											(permisos[permisoCrearExpediente] eq permisoCrearExpediente and empty solicitudCreacionExpDTO.idUsuarioCreadorExpediente)}">								
									<a href="#" id="botonRechazaCreacionExp" title="Rechazar solicitud" onclick="rechazaCreacionExp(${solicitudCreacionExpDTO.idSolicitudCreacionExp});" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove font-icon-2"></span></a>								
									<a href="#" id="botonCreaExp" title="Crea expediente" onclick="creaExp(${solicitudCreacionExpDTO.idSolicitudCreacionExp});" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-ok font-icon-2"></span></a>								
								</c:if>							
							</td>			            							
			    		</tr>
			    	</c:forEach> 
			    </tbody>
			</table>
		 	
		</div>
	
	</div>

</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function creaExp(idSolicitudCreacionExp) {
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	    	$(".formError").remove();
	    	var validaForm = true;
	    	var idElemntIdProceso = "#idProceso"+idSolicitudCreacionExp;
	    	var idElementIdUsuarioDestinatario = "#idUsuarioDestinatario"+idSolicitudCreacionExp;	
	    	var idElementAsuntoMateria = "#asuntoMateria"+idSolicitudCreacionExp;
	    	var idElementIdAutorSolicitudExp = "#idAutorSolicitudExp"+idSolicitudCreacionExp; 
	        var idProceso = $(idElemntIdProceso).val();			
			var idUsuarioDestinatario = $(idElementIdUsuarioDestinatario).val();
			var asuntoMateria = $(idElementAsuntoMateria).val();
			var idAutor = $(idElementIdAutorSolicitudExp).val();
			console.log("idSolicitudCreacionExp: " + idSolicitudCreacionExp);
	    	console.log("idProceso: " + idProceso);	    	
	    	console.log("idUsuarioDestinatario: " + idUsuarioDestinatario);
	    	console.log("idElementAsuntoMateria: " + idElementAsuntoMateria);
	    	console.log("idAutor: " + idAutor);	    	
	    	if (idProceso == "" || idProceso == undefined  || idProceso == null || idProceso == 'null' || idProceso == 'undefined') {
	    		validaForm = false;
	    		$(idElemntIdProceso).validationEngine('showPrompt', '* Por favor seleccione el SubProceso', 'error');
		    }
	    	if (idUsuarioDestinatario == "" || idUsuarioDestinatario == undefined  || idUsuarioDestinatario == null || idUsuarioDestinatario == 'null' || idUsuarioDestinatario == 'undefined') {
	    		validaForm = false;
	    		$(idElementIdUsuarioDestinatario).validationEngine('showPrompt', '* Por favor seleccione el usuario destinatario', 'error');
		    }
	    	if (asuntoMateria == "" || asuntoMateria == undefined  || asuntoMateria == null || asuntoMateria == 'null' || asuntoMateria == 'undefined') {
	    		validaForm = false;
	    		$(idElementAsuntoMateria).validationEngine('showPrompt', '* Por favor seleccione el aunto', 'error');
		    }
	    	if (idAutor == "" || idAutor == undefined  || idAutor == null || idAutor == 'null' || idAutor == 'undefined') {
	    		validaForm = false;
	    		$(idElementIdAutorSolicitudExp).validationEngine('showPrompt', '* Por favor seleccione el autor', 'error');
		    }	    	
	    	if (validaForm == true) {
	    		bootbox.confirm({
	                title: "Creaci&oacute;n de expediente",
	                message: "Por favor confirmar la creaci&oacute;n del expediente",
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
	                    if (result == true) {
	                    	var urlCreaExpedienteSolicitudCreacionExp = "${pageContext.request.contextPath}/creaExpedienteSolicitudCreacionExp";    
	                    	var urlGetSolicitudesDeCreacionDeExpediente = "${pageContext.request.contextPath}"+"/getSolicitudesDeCreacionDeExpediente";	                    	   		    	
	                        var solicitudCreacionExpDTO = {};	
	                        solicitudCreacionExpDTO["idSolicitudCreacionExp"] = idSolicitudCreacionExp;		        
	                        solicitudCreacionExpDTO["idProceso"] = idProceso;
	                        solicitudCreacionExpDTO["idUsuarioDestinatario"] = idUsuarioDestinatario;
	                        solicitudCreacionExpDTO["asuntoMateria"] = asuntoMateria;	
	                        solicitudCreacionExpDTO["idAutor"] = idAutor; 	                        			      			       
	                        $.ajax( {
	                            url: urlCreaExpedienteSolicitudCreacionExp,
	                            type: 'POST',
	                            data: JSON.stringify(solicitudCreacionExpDTO),
	                            dataType: 'json',
	                            contentType: "application/json",                	    
	                            beforeSend: function(xhr) {
	                                $("#solicitudesCreacionExp").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- crea registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#solicitudesCreacionExp").find(".cargando").remove();	
	                                $("#solicitudesCreacionExp").css("position", "");		
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                                if (returnData.responseJSON.glosa=="OK") {
	                                    $.notify({
	                                        message: 'Se ha creado el expediente ' + returnData.responseJSON.nombreExpediente
	                                    },{
	                                        type: 'success'
	                                    });
	                                } else {
	                                    $.notify({
	                                        message: "Ocurrio un error al crear el expediente"
	                                    },{
	                                        type: 'danger'
	                                    });
	                                }     			
	                                $("#solicitudesCreacionExp").load(urlGetSolicitudesDeCreacionDeExpediente,	    	                                
	                                        function() {
	                                            $("#solicitudesCreacionExp").find(".cargando").remove();                			                       
	                                });	
	                                $('#solicitudesCreacionExp').modal('hide');
	                                $("#solicitudesCreacionExp").css("position", ""); 	            					
	                            }                		
	                        });
	                    } 
	                }
	            });
	    	}		       	   
	    } else {
	          bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                        , function(){
	                              window.open('${raizURL}', '_blank');
	                        }
	           );
	    }
	});
}

function rechazaCreacionExp(idSolicitudCreacionExp) {
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {
	    	bootbox.prompt({	    	
	        	title: "Rechaza Solicitud",
	        	message: "<p>Por favor confirmar el rechazo de la solicitud.</p>",
	        	placeholder: "Ingrese motivo de rechazo",
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
	            callback: function (motivoRechazo) {
	            	var urlRechazaSolicitudCreacionExp = "${pageContext.request.contextPath}"+"/rechazaSolicitudCreacionExp/"+idSolicitudCreacionExp;                    	                        	   			      			       
                	var urlGetSolicitudesDeCreacionDeExpediente = "${pageContext.request.contextPath}"+"/getSolicitudesDeCreacionDeExpediente";		    			
	            	var solicitudCreacionExpDTO = {};			        
                    solicitudCreacionExpDTO["idSolicitudCreacionExp"] = idSolicitudCreacionExp;
                    solicitudCreacionExpDTO["comentario"] = motivoRechazo;                   	
	    			if (motivoRechazo!=null && motivoRechazo!="") {
	    				$.ajax({
	    					type : "POST",
	    					contentType : "application/json",
	    					url : urlRechazaSolicitudCreacionExp,
	    					data : JSON.stringify(solicitudCreacionExpDTO),
	    					dataType : 'json',
	    					timeout : 100000,
	    					beforeSend: function(xhr) {
                                $("#solicitudesCreacionExp").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
                            },
                            success: function (returnData) {
                                console.log("SUCCESS -- crea registro: ", returnData);	    	
                            },
                            error : function(e) {
                                console.log("ERROR: ", e);
                                $("#solicitudesCreacionExp").find(".cargando").remove();	
                                $("#solicitudesCreacionExp").css("position", "");		
                            },
                            done : function(e) {
                                console.log("DONE");
                            },
                            complete : function(returnData) {
                                if (returnData.responseJSON.glosa=="OK") {
                                    $.notify({
                                        message: "Registro eliminado"
                                    },{
                                        type: 'success'
                                    });
                                } else {
                                    $.notify({
                                        message: "Ocurrio un error al eliminar el registro"
                                    },{
                                        type: 'danger'
                                    });
                                }     			
                                $("#solicitudesCreacionExp").load(urlGetSolicitudesDeCreacionDeExpediente,	    	                                
                                        function() {
                                            $("#solicitudesCreacionExp").find(".cargando").remove();                			                       
                                });	
                                $("#solicitudesCreacionExp").find(".cargando").remove();	
                                $("#solicitudesCreacionExp").css("position", "");		            					
                            }        		
	    				});					
	    			} else if (motivoRechazo==null) {
						return true;
            		} else {									            	    	
            	    	$(".bootbox-input-text").validationEngine('showPrompt', 'Por favor ingrese un motivo', 'error');
            	    	return false;
	            	}										
	            }
	        });		       	   
	    } else {
	          bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                        , function(){
	                              window.open('${raizURL}', '_blank');
	                        }
	           );
	    }
	});
}

$('.id-proceso').on('change', function() {
	  var idProceso = this.value;
	  var idSolicitudCreacionExp = $(this).attr("data-id-solicitud-crea-exp");
	  $.get("${sessionURL}", function(haySession) {
		    console.log("haySession: " + haySession);
		    if(haySession) {
		    	if (idProceso != "") {
		    		var getIdsUsuariosDestinatariosPorIdProceso = "${pageContext.request.contextPath}"+"/getIdsUsuariosDestinatariosPorIdProceso/"+idProceso;			    	   
			        $.get(getIdsUsuariosDestinatariosPorIdProceso, function(idsUsuariosDestinatariosPorIdProceso, status) {
			            var create = '<option value="">Seleccione destinatario</option>';
			            idsUsuariosDestinatariosPorIdProceso.forEach(function (idUsuarioDestinatario) {
			                create += '<option value="'+idUsuarioDestinatario +'">'+ idUsuarioDestinatario +'</option>';                    
			            }); 
			            var idSelectOptIdUsrDest = "idUsuarioDestinatario"+idSolicitudCreacionExp;
			            $('#'+idSelectOptIdUsrDest).html(""); 
			            $('#'+idSelectOptIdUsrDest).append(create);
			            console.log(create);
			    	});		    		
			    }			    
		    } else {
		          bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
		                        , function(){
		                              window.open('${raizURL}', '_blank');
		                        }
		           );
		    }		    
		});
});

function abreModalNuevaSolicitudDeCreacExp() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {        	
        	$("#subprocesoMateriaFormCreacExp").val("").trigger("change");
	    	$("#autorCreacExp").val("").trigger("change");
	    	$("#asuntoMateriaFormCreacExp").val("").trigger("change");
            $("#destinatarioCreacExp").val("").trigger("change");
            $("#comentarioSolicitudCreaExp").val("").trigger("change");
        	$('#nuevaSolicitudCreacExpModal').modal( {backdrop: 'static', keyboard: false}); 
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

function formatTablaSolicitudesDeCreacionDeExpediente() {
	
	var tablaSolicitudesDeCreacionDeExpediente = $('#tablaSolicitudesDeCreacionDeExpediente')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'SolicitudesDeCreacionDeExpediente.*',
					exportOptions : {
						columns: [ 0, 1, 2, 3, 5, 7, 9, 11]
					}
				}],
				"language" : lenguaje_es,
				"pageLength": 6,	
				"order": [[ 0, "asc" ]]
				
			});
	
	tablaSolicitudesDeCreacionDeExpediente.buttons().container().appendTo(
	'#tablaSolicitudesDeCreacionDeExpediente_wrapper .row:eq(0)');
}

function abreModalVerSolicitudesFinalizadas() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
        	$('#tablaSolicitudesDeCreacionDeExpedienteFinalizadas').DataTable().destroy();;
        	inicializaTablaSolicitudesDeCreacionDeExpedienteFinalizadas();
        	$('#solicitudesCreacExpFinalizadasModal').modal( {backdrop: 'static', keyboard: false}); 
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

var inicializaTablaSolicitudesDeCreacionDeExpedienteFinalizadas = function() {

	var urlGetSolicitudesCreaExpFinalizadas = "${pageContext.request.contextPath}"+"/getSolicitudesCreaExpFinalizadas";	

	$("#tablaSolicitudesDeCreacionDeExpedienteFinalizadas").dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		//"sort" : "position",
		"bSort" : false,
		//bStateSave variable you can use to save state on client cookies: set value "true"
		"bStateSave" : false,
		//Default: Page display length
		"iDisplayLength" : 8,
		//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
		"iDisplayStart" : 0,
		"language" : lenguaje_es,	
		"fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
			$.get("${sessionURL}", function(haySession){
		        console.log("haySession: " + haySession);
		        if(haySession) {
		        	oSettings.jqXHR = $.ajax( {
				        "dataType": 'json',
				        "type": "GET",
				        "url": sSource,
				        "data": aoData,
				        "success": fnCallback
				      } );		        	
		        } else {
		              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
		                            , function(){
		                                  window.open('${raizURL}', '_blank');
		                            }
		              );		             
		        }
		  	});		      
		 },		 
		"sAjaxSource" : "getSolicitudesCreaExpFinalizadas",
		"aoColumns" : [
						{
							"mData" : "idSolicitudCreacionExp"
						},
						{
							"mData" : "nombreSubProceso"
						},
						{
							"mData" : "idUsuarioSolicitante"
						},
						{
							"mData" : "idUsuarioCreadorExpediente"
						},
						{
							"mData" : "fechaSolicitudS"
						},
						{
							"mData" : "fechaAtencionS"
						},
						{
							"mData" : "comentario"
						},
						{
							"mData" : "nombreExpediente"
						},
						{
							"mData" : "nombreEstadoSolicitudCreacionExp"
						}
		]

	});

}

jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(objValues) {

	return {

		"iStart" : objValues._iDisplayStart,

		"iEnd" : objValues.fnDisplayEnd(),

		"iLength" : objValues._iDisplayLength,

		"iTotal" : objValues.fnRecordsTotal(),

		"iFilteredTotal" : objValues.fnRecordsDisplay(),

		"iPage" : objValues._iDisplayLength === -1 ?

		0 : Math.ceil(objValues._iDisplayStart / objValues._iDisplayLength),

		"iTotalPages" : objValues._iDisplayLength === -1 ?

		0 : Math.ceil(objValues.fnRecordsDisplay()
				/ objValues._iDisplayLength)

	};

};

$(document).ready(function() {
	formatTablaSolicitudesDeCreacionDeExpediente();	
	new Tippy('#botonRechazaCreacionExp');
	new Tippy('#botonCreaExp');		
});

</script>
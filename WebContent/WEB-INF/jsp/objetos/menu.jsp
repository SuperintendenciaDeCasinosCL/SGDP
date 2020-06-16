<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<%@ page import= "cl.gob.scj.sgdp.control.AppContextControl" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="permisoPuedeVerDashBoard" value="<%=PermisoType.PUEDE_VER_DASHBOARD.getNombrePermiso()%>"/>
<c:set var="permisoPuedeVerMantenedores" value="<%=PermisoType.PUEDE_VER_MANTENEDORES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerAutores" value="<%=PermisoType.PUEDE_MANTENER_AUTORES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerProcSolCreaExp" value="<%=PermisoType.PUEDE_MANTENER_PROCESOS_SOL_CREAC_EXP.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerNotificacionesPredeterminadas" value="<%=PermisoType.PUEDE_MANTENER_NOTIFICIONES_PREDETERMINADAS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeVerIndicaodres" value="<%=PermisoType.PUEDE_VER_INDICADORES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerListaDistribucion" value="<%=PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerParametros" value="<%=PermisoType.PUEDE_MANTENER_PARAMETROS.getNombrePermiso()%>"/>
<c:set var="urlReporteSGDP" value="<%=AppContextControl.getUrlReporteSGDP()%>"/>
<c:set var="urlIndicadoresIgestion" value="<%=AppContextControl.getUrlIndicadoresIgestion()%>"/>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<c:url value="bandejaDeEntrada" var="bandejaDeEntradaURL">
   <c:param name="linkActivo" value="bandejaDeEntrada" />
</c:url>

<c:url value="buscador" var="buscadorURL">
   <c:param name="linkActivo" value="buscador" />
</c:url>

<c:url value="mantenedorAutor" var="mantenedorAutorURL">
   <c:param name="linkActivo" value="mantenedores" />
   <c:param name="linkActivoMant" value="mantenedorAutor" />
</c:url>

<c:url value="notificadorTarea" var="notificadorTareaURL">
   <c:param name="linkActivo" value="notificacionPredeterminada" />
</c:url>

<c:url value="indicador" var="indicadorURL">
   <c:param name="linkActivo" value="indicador" />
</c:url>

<c:url value="listaDeDistribucion" var="listaDeDistribucionURL">
   <c:param name="linkActivo" value="listaDeDistribucion" />
</c:url>

<c:url value="mantenedorParametros" var="mantenedorParametrosURL">
   <c:param name="linkActivo" value="mantenedorParametros" />
</c:url>

<c:url value="solicitudesCreacionExp" var="solicitudesCreacionExpURL">
   <c:param name="linkActivo" value="solicitudesCreacionExp" />
</c:url>

<c:url value="mantenedorProcSolCreaExp" var="mantenedorProcSolCreaExpURL">
   <c:param name="linkActivo" value="mantenedores" />
   <c:param name="linkActivoMant" value="mantenedorProcSolCreaExp" />
   <c:param name="vistaCompleta" value="true" />
</c:url>

<c:url var="urlVideoInicioSesion" value="/cargaVideo?idVideo=Login.mp4" />
<c:url var="urlVideoIniciarUnProceso" value="/cargaVideo?idVideo=Iniciar_un_proceso.mp4" />
<c:url var="urlVideoDetalleTareaAsignada" value="/cargaVideo?idVideo=Tarea_asignada.mp4" />
<c:url var="urlVideoVerHistorialDeDocumentosDeUnaTarea" value="/cargaVideo?idVideo=Ver_historial_de_documentos_de_una_tarea.mp4" />
<c:url var="urlVideoSubirDocumentoRequerido" value="/cargaVideo?idVideo=Subir_documento_requerido.mp4" />
<c:url var="urlVideoSubirAdjuntoDeUnDocumento" value="/cargaVideo?idVideo=Adjuntar_documento.mp4" />
<c:url var="urlVideoAnadirDocumentoOpcional" value="/cargaVideo?idVideo=Anadir_documento_opcional.mp4" />
<c:url var="urlVideoEnviarTarea" value="/cargaVideo?idVideo=Enviar_tarea.mp4" />
<c:url var="urlVideoDevolverTarea" value="/cargaVideo?idVideo=Devolver_tarea.mp4" />
<c:url var="urlVideoVerFlujosEnCursoYReasignarUnaTarea" value="/cargaVideo?idVideo=Ver_flujos_en_curso_y_reasignar_una tarea.mp4" />
<c:url var="urlVideoVisarUnDocumento" value="/cargaVideo?idVideo=Visar_documento.mp4" />
<c:url var="urlVideoFirmarUnDocumentoConFirmaCentralizada" value="/cargaVideo?idVideo=Firmar_un_documento.mp4" />
<c:url var="urlVideoFirmarUnDocumentoConFirmaToken" value="/cargaVideo?idVideo=Firma_avanzada.mp4" />

	 <div class="row" style="background-color:#00a8b3;">	
	 
	 	<div class="user-head col-sm-offset-4">

            <a class="inbox-avatar avatar-scj" href="#" style="background-image: url('<c:url value="/img/no-image.png"/>');"></a>           

            <div class="user-name">
            
            	<div class="dropdown">
				    <a href="#" style="color: #87e2e7" class="dropdown-toggle" data-toggle="dropdown">${not empty usuario ? usuario.idUsuario : 'Anonimo'}
				    <span class="caret"></span></a>
				    <ul class="dropdown-menu">
				    	<c:choose>
							<c:when test = "${usuario.fueraDeOficina eq true}">
								<li id="liCheckBoxFueraDeOficina" class="tamanioTextoFiltro cursorPuntero dropdown-li-a"><input type="checkbox" checked name="checkBoxFueraDeOficina" id="checkBoxFueraDeOficina"> Fuera de oficina</li>
							</c:when>
							<c:otherwise>
								<li id="liCheckBoxFueraDeOficina" class="tamanioTextoFiltro cursorPuntero dropdown-li-a"><input type="checkbox" name="checkBoxFueraDeOficina" id="checkBoxFueraDeOficina"> Fuera de oficina</li>
							</c:otherwise>						
						</c:choose>	
				    </ul>
			  	</div>
			  	
                <span><a href="#">${not empty usuario ? usuario.unidadDTO.codigoUnidad : 'Anonima'}</a></span>
                <span><br><a href="http://sgdocb/proceso/bpm/roles_user_all.php" target="_blank" >Roles</a></span>
            </div>                        

        </div>
	 
	 </div>	

	<br>
	  
      <ul class="nav nav-pills nav-stacked">
	  	
	  	<c:choose>
		    <c:when test="${param.linkActivo.equals('bandejaDeEntrada')}">
		       <c:set var="classBEActive" value="active"/>
		    </c:when>
		    <c:when test="${param.linkActivo.equals('buscador')}">
		       <c:set var="classBuscadorActive" value="active"/>
		    </c:when>
		     <c:when test="${param.linkActivo.equals('dashboard')}">
		       <c:set var="classDashBoardActive" value="active"/>
		    </c:when>	
		    <c:when test="${param.linkActivo.equals('mantenedores')}">
		       <c:set var="classMantendorActive" value="active"/>		       
		       <c:choose>
		       	<c:when test="${param.linkActivoMant.equals('mantenedorAutor')}">
		       		<c:set var="classMantenedorAutorActive" value="active"/>
		       	</c:when>
		       	<c:when test="${param.linkActivoMant.equals('mantenedorProcSolCreaExp')}">
		       		<c:set var="classMantenedorProcSolCreaExp" value="active"/>
		       	</c:when>
		       	<c:otherwise>
		       		<c:set var="classMantenedorAutorActive" value=""/>
		       		<c:set var="classMantenedorProcSolCreaExp" value=""/>
		       	</c:otherwise>
		       </c:choose>		       
		    </c:when>		
		    <c:when test="${param.linkActivo.equals('notificacionPredeterminada')}">
		       <c:set var="classNotificacionPredeterminadaActive" value="active"/>
		    </c:when>
		    <c:when test="${param.linkActivo.equals('indicador')}">
		       <c:set var="indicadorActive" value="active"/>
		    </c:when>	
		    <c:when test="${param.linkActivo.equals('listaDeDistribucion')}">
		       <c:set var="classListaDeDistribucionActive" value="active"/>
		    </c:when>	
		    <c:when test="${param.linkActivo.equals('mantenedorParametros')}">
		       <c:set var="classMantenedorParametrosActive" value="active"/>
		    </c:when>
		    <c:when test="${param.linkActivo.equals('solicitudesCreacionExp')}">
		       <c:set var="classSolicitudesCreacionExpActive" value="active"/>
		    </c:when>
		    		    
		    <c:otherwise>
		        <c:set var="classBEActive" value="active"/>
		        <c:set var="classBuscadorActive" value=""/>
		        <c:set var="classDashBoardActive" value=""/>
		        <c:set var="classMantendorActive" value=""/>
		        <c:set var="classListaDeDistribucionActive" value=""/>
		        <c:set var="classMantenedorParametrosActive" value=""/>
		        <c:set var="classSolicitudesCreacionExpActive" value=""/>
		        <c:set var="classMantenedorAutorActive" value=""/>
		         <c:set var="classMantenedorProcSolCreaExp" value=""/>
		    </c:otherwise>
		</c:choose>
		
      
        <li class="${classBEActive} liMenu">
	    	<a href="${bandejaDeEntradaURL}"><i class="glyphicon glyphicon-inbox"></i> <spring:message code="menu.principal.link.nombre.bandejaDeEntrada"/><span id="cantidadDeTareas" class="badge">${cantidadDeTareas}</span></a>
	    </li>
	    
	    <li class="${classBuscadorActive} liMenu">
	    	<a href="${buscadorURL}"><i class="glyphicon glyphicon-search"></i> B&uacute;squeda</a>
	    </li>	    
	    
	    <c:if test = "${usuario.permisos[permisoPuedeMantenerNotificacionesPredeterminadas] eq permisoPuedeMantenerNotificacionesPredeterminadas}">
		    <li class="${classNotificacionPredeterminadaActive} liMenu">
		    	<a href="${notificadorTareaURL}"><i class="glyphicon glyphicon-envelope"></i> Notificaci&oacute;n Predeterminada</a>
		    </li>
	    </c:if>
	   	  
	   	<c:if test = "${usuario.permisos[permisoPuedeVerIndicaodres] eq permisoPuedeVerIndicaodres}">
		   	<li class="${indicadorActive} liMenu">
		   		<a href="indicador?linkActivo=indicador"><i class="glyphicon glyphicon-th-list"></i> Indicadores</a>
		  	</li>
	   	</c:if> 
	 	
	   <c:if test = "${usuario.permisos[permisoPuedeMantenerListaDistribucion] eq permisoPuedeMantenerListaDistribucion}">
		   	<li class="${classListaDeDistribucionActive} liMenu">
		   		<a href="${listaDeDistribucionURL}"><i class="glyphicon glyphicon-list-alt"></i> Lista de Distribuci&oacute;n</a>
		  	</li>
	   	</c:if>

	    <c:if test = "${usuario.permisos[permisoPuedeVerDashBoard] eq permisoPuedeVerDashBoard}">	    
	    	<li class="${classDashBoardActive} liMenu">
		    	<a href="${bandejaDeEntradaURL}"><i class="glyphicon glyphicon-dashboard"></i> DashBoard</a>
		    </li>	    
	    </c:if>	   
	    
	    <c:if test = "${usuario.permisos[permisoPuedeVerMantenedores] eq permisoPuedeVerMantenedores}">
		 	<li class="${classMantendorActive} liMenu">
		 		<a href="#"><i class="glyphicon glyphicon-cog"></i> Mantenedores</a>     
		 	</li>		 	
		 	<c:if test = "${usuario.permisos[permisoPuedeMantenerAutores] eq permisoPuedeMantenerAutores}">
		 		<li class="${classMantenedorAutorActive} liMenu li-sub-menu">
					<a onclick="cargaManAutores('${pageContext.request.contextPath}'+'/'+'${mantenedorAutorURL}')" href="#"><i class="glyphicon glyphicon-briefcase"></i> Autores</a>
				</li>
		 	</c:if>	
		 	<c:if test = "${usuario.permisos[permisoPuedeMantenerProcSolCreaExp] eq permisoPuedeMantenerProcSolCreaExp}">
		 		<li class="${classMantenedorProcSolCreaExp} liMenu li-sub-menu">					
					<a href="${mantenedorProcSolCreaExpURL}"><i class="glyphicon glyphicon-transfer"></i> Subprocesos solicitud de creaci&oacute;n expediente</a>
				</li>
		 	</c:if> 
	    </c:if>
	    
    	<li class="liMenu">
	    	<a target="_blank" href="${urlReporteSGDP}"><i class="glyphicon glyphicon-signal"></i> Reporte</a>
	    </li>
	    
	    <c:if test = "${usuario.permisos[permisoPuedeVerIndicaodres] eq permisoPuedeVerIndicaodres}">
	    	<li class="liMenu">
	    		<a target="_blank" href="${urlIndicadoresIgestion}"><i class="glyphicon glyphicon-random"></i> Indicadores Igesti&oacute;n</a>
	    	</li>	    
	    </c:if>
	    
	    <c:if test = "${usuario.permisos[permisoPuedeMantenerParametros] eq permisoPuedeMantenerParametros}">
		   	<li class="${classMantenedorParametrosActive} liMenu">
		   		<a href="${mantenedorParametrosURL}"><i class="glyphicon glyphicon-list-alt"></i> Mantenedor de par&aacute;metros</a>
		  	</li>
	   	</c:if>
	   	
	   	
	   	 <li class="${classSolicitudesCreacionExpActive} liMenu">
		 	<a href="${solicitudesCreacionExpURL}"><i class="glyphicon glyphicon-bullhorn"></i> Solicitudes de creaci&oacute;n expedientes</a>
		 </li>		 
	     
	     
      </ul>
      <br>
      
       <span>          
         <a class="btn btn-danger" href="logout"><i class="glyphicon glyphicon-off"></i> <spring:message code="menu.principal.salir.nombre"/></a>
       </span>
      
	<!-- Modal Crear Expediente -->
		
	<c:import url="/WEB-INF/jsp/modals/crearExpediente.jsp"></c:import>	


<script type="text/javascript">

var options = [];

$( '.dropdown-menu-fuera-oficina a' ).on( 'click', function( event ) {

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

var inicializaLiCheckBoxFueraDeOficina = function() {
	$("#liCheckBoxFueraDeOficina").off('click').on('click', function (event) {		
		console.log("event.target.id: " + event.target.id);
		console.log("$('#checkBoxFueraDeOficina').is(':checked')" + $('#checkBoxFueraDeOficina').is(':checked'));
		$.get("${sessionURL}", function(haySession) {
			console.log("haySession: " + haySession);
		    if (haySession) {			    
		    	if (event.target.id == "liCheckBoxFueraDeOficina") {
					$( "#checkBoxFueraDeOficina").prop('checked', !$('#checkBoxFueraDeOficina').is(':checked'));			
				}		
				var urlActualizaFueraDeOficina = $("#urlActualizaFueraDeOficina").val() + "/" + $('#checkBoxFueraDeOficina').is(':checked');
		    	console.log("urlActualizaFueraDeOficina: " + urlActualizaFueraDeOficina);
		    	$.ajax({
		            type : "POST",
		            contentType : "application/json",
		            url : urlActualizaFueraDeOficina,       
		            timeout : 100000,
		            success : function(data) {
		            },
		    	    error : function(e) {
		    			console.log("ERROR: ", e);
		    			return false;			
		    		},
		    		done : function(e) {
		    			console.log("DONE");							    			
		    		},
		    		complete : function(returnData) {
		    			$.notify({
				   			message: 'Se ha actualizado correctamente su estado de fuera de oficina!'
				   		},{
				   			type: 'success'
				   		});
				   		if ($('#checkBoxFueraDeOficina').is(":checked") == true) {
					   		
				   			$('#checkBoxFueraDeOficina').attr('checked', true);

				   			if (document.getElementById("divBackHeaderAreaDeTrabajo") !== null) {
				   				$("#divBackHeaderAreaDeTrabajo").css("background-color", "#FFD51D");
					   		}

				   			if (document.getElementById("divBackHeaderBuscador") !== null) {
				   				$("#divBackHeaderBuscador").css("background-color", "#FFD51D");
					   		}

					   		if (document.getElementById("h2MensajeAreaDeTrabajo") !== null) {
					   			$('#h2MensajeAreaDeTrabajo').text("Área de Trabajo (Fuera de Oficina)");
							}

					   		if (document.getElementById("h2MensajeBuscador") !== null) {
					   			$('#h2MensajeBuscador').text("Buscador (Fuera de Oficina)");
							}
				   			
					   	} else {

					   		$('#checkBoxFueraDeOficina').attr('checked', false);

					   		if (document.getElementById("divBackHeaderAreaDeTrabajo") !== null) {
					   			$("#divBackHeaderAreaDeTrabajo").css("background-color", "#41CAC0");
					   		}

					   		if (document.getElementById("divBackHeaderBuscador") !== null) {
				   				$("#divBackHeaderBuscador").css("background-color", "#41CAC0");
					   		}
					   		
					   		if (document.getElementById("h2MensajeAreaDeTrabajo") !== null) {
					   			$('#h2MensajeAreaDeTrabajo').text("Área de Trabajo");
					   		}

					   		if (document.getElementById("h2MensajeBuscador") !== null) {
					   			$('#h2MensajeBuscador').text("Buscador");
							}					   		
					   		
						}	
		    			console.log("COMPLETE -- respuesta: ", returnData );
		                console.log("returnData.responseText: " + returnData.responseText );               						    			
		            }
		        });
		   } else {
		         bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
		             , function(){ window.open('${raizURL}', '_blank');}
		         );
		   }
		});
	});	
};

function cargaManAutores(url) {
	window.open(url,"_self");
}

$(document).ready(function() {
	$(inicializaLiCheckBoxFueraDeOficina);
  
});



</script>

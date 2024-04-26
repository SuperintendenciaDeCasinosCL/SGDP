<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<%@ page import= "cl.gob.scj.sgdp.control.AppContextControl" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import= "cl.gob.scj.sgdp.control.AppContextControl" %>

<c:set var="urlFuncPhp" value="<%=AppContextControl.getUrlFuncPhp()%>"/>
<c:set var="permisoPuedeVerDashBoard" value="<%=PermisoType.PUEDE_VER_DASHBOARD.getNombrePermiso()%>"/>
<c:set var="permisoPuedeVerMantenedores" value="<%=PermisoType.PUEDE_VER_MANTENEDORES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerAutores" value="<%=PermisoType.PUEDE_MANTENER_AUTORES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerProcSolCreaExp" value="<%=PermisoType.PUEDE_MANTENER_PROCESOS_SOL_CREAC_EXP.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerNotificacionesPredeterminadas" value="<%=PermisoType.PUEDE_MANTENER_NOTIFICIONES_PREDETERMINADAS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeVerIndicaodres" value="<%=PermisoType.PUEDE_VER_INDICADORES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerListaDistribucion" value="<%=PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerParametros" value="<%=PermisoType.PUEDE_MANTENER_PARAMETROS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerTipoDeDestinatario" value="<%=PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerUsuarios" value="<%=PermisoType.PUEDE_MANTENER_USUARIOS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerCargos" value="<%=PermisoType.PUEDE_MANTENER_CARGOS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerUnidades" value="<%=PermisoType.PUEDE_MANTENER_UNIDADES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerUnidadesOperativas" value="<%=PermisoType.PUEDE_MANTENER_UNIDADES_OPERATIVAS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerTiposSubtareasBitacora" value="<%=PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerSeriesDocumentales" value="<%=PermisoType.PUEDE_MANTENER_SERIES_DOCUMENTALES.getNombrePermiso()%>"/>
<c:set var="permisoPuedeMantenerPlantillas" value="<%=PermisoType.PUEDE_MANTENER_PLANTILLAS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeCargarProseso" value="<%=PermisoType.PUEDE_CARGAR_PROCESO.getNombrePermiso()%>"/>
<c:set var="permisoPuedeEditarAsunto" value="<%=PermisoType.PUEDE_EDITAR_ASUNTO.getNombrePermiso()%>"/>
<c:set var="permisoPuedeVerReportLogDocumento" value="<%=PermisoType.PUEDE_VER_LOG_DOCUMENTOS.getNombrePermiso()%>"/>
<c:set var="permisoPuedeVerAnuladorDocumentos" value="<%=PermisoType.PUEDE_ANULAR_DOCUMENTOS.getNombrePermiso()%>"/>

<c:set var="urlReporteSGDP" value="<%=AppContextControl.getUrlReporteSGDP()%>"/>
<c:set var="urlIndicadoresIgestion" value="<%=AppContextControl.getUrlIndicadoresIgestion()%>"/>
<c:set var="urlFuncPhp" value="<%=AppContextControl.getUrlFuncPhp()%>"/>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<c:url value="bandejaDeEntrada" var="bandejaDeEntradaURL">
   <c:param name="linkActivo" value="bandejaDeEntrada" />
</c:url>

<c:url value="buscador" var="buscadorURL">
   <c:param name="linkActivo" value="buscador" />
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
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorParametros" />
</c:url>

<c:url value="solicitudesCreacionExp" var="solicitudesCreacionExpURL">
   <c:param name="linkActivo" value="solicitudesCreacionExp" />
</c:url>

<c:url value="mantenedorProcSolCreaExp" var="mantenedorProcSolCreaExpURL">
   <c:param name="linkActivo" value="mantenedores" />
   <c:param name="linkActivoMant" value="mantenedorProcSolCreaExp" />
   <c:param name="vistaCompleta" value="true" />
</c:url>

<c:url value="mantenedorAutores" var="mantenedorAutoresURL">
   <c:param name="linkActivo" value="mantenedores" />
   <c:param name="linkActivoMant" value="mantenedorAutores" />
   <c:param name="vistaCompleta" value="true" />
</c:url>

<c:url value="mantenedorUsuarios" var="mantenedorUsuariosURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorUsuarios" />
</c:url>

<!-- MIG -->
<c:url value="mantenedorRoles" var="mantenedorRolesURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorRoles" />
</c:url>

<c:url value="mantenedorUnidades" var="mantenedorUnidadesURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorUnidades" />
</c:url>

<c:url value="mantenedorUnidadesOperativas" var="mantenedorUnidadesOperativasURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorUnidadesOperativas" />
</c:url>

<c:url value="mantenedorTiposSubtareaBitacora" var="mantenedorTiposSubtareaBitacoraURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorTiposSubtareaBitacora" />
</c:url>


<c:url value="mantenedorTipoDestinatario" var="mantenedorTipoDestinatarioURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorTipoDestinatario" />
</c:url>

<c:url value="cargaProcesos" var="cargaProcesosURL">
	<c:param name="linkActivo" value="cargaProcesos" />
</c:url>

<c:url value="plantillaDocumento" var="plantillaDocumentoURL">
	<c:param name="linkActivo" value="plantillaDocumento" />
</c:url>

<c:url value="reportes" var="reportesURL">
	<c:param name="linkActivo" value="reportes" />
</c:url>

<c:url value="serieDocumental" var="serieDocumentalURL">
	<c:param name="linkActivo" value="mantenedores" />
	<c:param name="linkActivoMant" value="serieDocumental" />
</c:url>

<c:url value="mantenedorPlantillas" var="mantenedorPlantillasURL">
	<c:param name="linkActivo" value="mantenedores" />
   	<c:param name="linkActivoMant" value="mantenedorPlantillas" />
</c:url>

<c:url value="editorDeAsunto" var="editorDeAsuntoURL">
	<c:param name="linkActivo" value="editorDeAsunto" />
</c:url>

<c:url value="reportLogDocumento" var="reportLogDocumentoURL">
   <c:param name="linkActivoMant" value="reportLogDocumento" />
</c:url>

<c:url value="anuladorDocumentos" var="anuladorDocumentosURL">
   <c:param name="linkActivo" value="anuladorDocumentos" />
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
	 
	 	<div class="user-head col-sm-offset-3">	 	

	 		<%--<div class="logo-sgdp" style="background-image: url('<c:url value="/img/logo_sgdp.png"/>');">
                        	
            </div>--%>
            
            <div class="inbox-avatar avatar-scj logo-sgdp" style="background-image: url('/sgdp/img/logo_sgdp.png');">
                        	
            </div>

            <%--<a class="inbox-avatar avatar-scj" href="#" style="background-image: url('<c:url value="/img/no-image.png"/>');"></a>--%>           

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
                <span><br><a href="http://${urlFuncPhp}/proceso/bpm/roles_user_all.php" target="_blank" >Roles</a></span>
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
		    <c:when test="${param.linkActivoMant.equals('mantenedorAutores')}">
	       		<c:set var="classMantenedorAutoresActive" value="active"/>
	       	</c:when>
		    <c:when test="${param.linkActivo.equals('mantenedores')}">
		       <c:set var="classMantendorActive" value="active"/>		       
		       <c:choose>
		       	<c:when test="${param.linkActivoMant.equals('mantenedorAutores')}">
		       		<c:set var="classMantenedorAutoresActive" value="active"/>
		       	</c:when>
		       	<c:when test="${param.linkActivoMant.equals('mantenedorProcSolCreaExp')}">
		       		<c:set var="classMantenedorProcSolCreaExp" value="active"/>
		       	</c:when>
		       	<c:when test="${param.linkActivoMant.equals('mantenedorParametros')}">
			       <c:set var="classMantenedorParametrosActive" value="active"/>
			    </c:when>
			    <c:when test="${param.linkActivoMant.equals('mantenedorTipoDestinatario')}">
			       <c:set var="classMantenedorTipoDestinatarioActive" value="active"/>
			    </c:when>
			    <c:when test="${param.linkActivoMant.equals('mantenedorUsuarios')}">
			       <c:set var="classMantenedorUsuariosActive" value="active"/>
			    </c:when>
			    <c:when test="${param.linkActivoMant.equals('mantenedorRoles')}">
			       <c:set var="classMantenedorRolesActive" value="active"/>
			    </c:when>
			     <c:when test="${param.linkActivoMant.equals('mantenedorUnidades')}">
			       <c:set var="classMantenedorUnidadesActive" value="active"/>
			    </c:when>		       
		       	 <c:when test="${param.linkActivoMant.equals('mantenedorUnidadesOperativas')}">
			       <c:set var="classMantenedorUnidadesOperativasActive" value="active"/>
			    </c:when>
			    <c:when test="${param.linkActivoMant.equals('mantenedorTiposSubtareaBitacora')}">
			       <c:set var="classMantenedorTiposSubtareaBitacoraActive" value="active"/>
			    </c:when>
			     <c:when test="${param.linkActivoMant.equals('mantenedorPlantillas')}">
			       <c:set var="classMantenedorPlantillasActive" value="active"/>
			    </c:when>
			     <c:when test="${param.linkActivoMant.equals('serieDocumental')}">
		       		<c:set var="classserieDocumental" value="active"/>
		    	</c:when>
		       	<c:otherwise>
		       		<c:set var="classMantenedorAutorActive" value=""/>
		       		<c:set var="classMantenedorProcSolCreaExp" value=""/>
		       		<c:set var="classMantenedorParametrosActive" value=""/>
		       		<c:set var="classMantenedorTipoDestinatarioActive" value=""/>
		       		<c:set var="classMantenedorUsuariosActive" value=""/>
		       		<c:set var="classMantenedorRolesActive" value=""/>
		       		<c:set var="classMantenedorUnidadesActive" value=""/>
		       		<c:set var="classMantenedorUnidadesOperativasActive" value=""/>
		       		<c:set var="classMantenedorTiposSubtareaBitacoraActive" value=""/>
		       		<c:set var="classMantenedorPlantillasActive" value=""/>
		       		<c:set var="classserieDocumental" value=""/>
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
		    <c:when test="${param.linkActivo.equals('solicitudesCreacionExp')}">
		       <c:set var="classSolicitudesCreacionExpActive" value="active"/>
		    </c:when>	
		    <c:when test="${param.linkActivo.equals('cargaProcesos')}">
		       <c:set var="classCargaProcesos" value="active"/>
		    </c:when>	
		    <c:when test="${param.linkActivo.equals('reportes')}">
		       <c:set var="classReportes" value="active"/>
		    </c:when>
		    <c:when test="${param.linkActivo.equals('editorDeAsunto')}">
		       <c:set var="classEditorDeAsunto" value="active"/>
		    </c:when>
		    <c:when test="${param.linkActivoMant.equals('reportLogDocumento')}">
					<c:set var="classReportLogDocumento" value="active"/>
			</c:when>
			<c:when test="${param.linkActivo.equals('anuladorDocumentos')}">
				<c:set var="classAnuladorDocumentosActive" value="active"/>
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
		        <c:set var="classMantenedorTipoDestinatarioActive" value=""/>
		        <c:set var="classMantenedorUsuariosActive" value=""/>
		        <c:set var="classMantenedorRolesActive" value=""/>
		        <c:set var="classMantenedorUnidadesActive" value=""/>
		        <c:set var="classMantenedorUnidadesOperativasActive" value=""/>
		        <c:set var="classMantenedorTiposSubtareaBitacoraActive" value=""/>
		        <c:set var="classCargaProcesos" value=""/>
		        <c:set var="classPlantillaDocumento" value=""/>
		        <c:set var="classReportes" value=""/>
		        <c:set var="classserieDocumental" value=""/>
		        <c:set var="classEditorDeAsunto" value=""/>
		        <c:set var="classReportLogDocumento" value=""/>
		        <c:set var="classAnuladorDocumentosActive" value=""/>
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
	   <c:if test = "${usuario.permisos[permisoPuedeMantenerListaDistribucion] eq permisoPuedeMantenerListaDistribucion}">
		   	<li class="${classListaDeDistribucionActive} liMenu">
		   		<a href="${listaDeDistribucionURL}"><i class="glyphicon glyphicon-plane"></i> Lista de Distribuci&oacute;n</a>
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
		 		<li class="${classMantenedorAutoresActive} liMenu li-sub-menu">					
					<a href="${mantenedorAutoresURL}"><i class="glyphicon glyphicon-briefcase"></i> Mantenedor de Autores</a>
				</li>
			</c:if>	
		 	<c:if test = "${usuario.permisos[permisoPuedeMantenerProcSolCreaExp] eq permisoPuedeMantenerProcSolCreaExp}">
		 		<li class="${classMantenedorProcSolCreaExp} liMenu li-sub-menu">					
					<a href="${mantenedorProcSolCreaExpURL}"><i class="glyphicon glyphicon-transfer"></i> Subprocesos solicitud de creaci&oacute;n Expediente</a>
				</li>
		 	</c:if>
		 	<c:if test = "${usuario.permisos[permisoPuedeMantenerParametros] eq permisoPuedeMantenerParametros}">
			   	<li class="${classMantenedorParametrosActive} li-sub-menu">
			   		<a href="${mantenedorParametrosURL}"><i class="glyphicon glyphicon-record"></i> Mantenedor de Par&aacute;metros</a>
			  	</li>
		   	</c:if>
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerTipoDeDestinatario] eq permisoPuedeMantenerTipoDeDestinatario}">
		    	<li class="${classMantenedorTipoDestinatarioActive} li-sub-menu">
		        	<a href="${mantenedorTipoDestinatarioURL}"><i class="glyphicon glyphicon-list-alt"></i> Mantenedor Tipo de Destinatario</a>
		    	</li>
		    </c:if>		   	
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerUsuarios] eq permisoPuedeMantenerUsuarios}">
			   	<li class="${classMantenedorUsuariosActive} li-sub-menu">
			   		<a href="${mantenedorUsuariosURL}"><i class="glyphicon glyphicon-user"></i> Mantenedor Usuarios</a>
			  	</li>
		   	</c:if>	
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerCargos] eq permisoPuedeMantenerCargos}">
			   	<li class="${classMantenedorRolesActive} li-sub-menu">
			   		<a href="${mantenedorRolesURL}"><i class="glyphicon glyphicon-tasks"></i> Mantenedor Cargos</a>
			  	</li>
		   	</c:if>		
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerUnidades] eq permisoPuedeMantenerUnidades}">
			   	<li class="${classMantenedorUnidadesActive} li-sub-menu">
			   		<a href="${mantenedorUnidadesURL}"><i class="glyphicon glyphicon-align-justify"></i> Mantenedor Unidades</a>
			  	</li>
		   	</c:if>	 
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerUnidadesOperativas] eq permisoPuedeMantenerUnidadesOperativas}">
			   	<li class="${classMantenedorUnidadesOperativasActive} li-sub-menu">
			   		<a href="${mantenedorUnidadesOperativasURL}"><i class="glyphicon glyphicon-grain"></i> Mantenedor Unidades Operativas</a>
			  	</li>
		   	</c:if>	 
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerTiposSubtareasBitacora] eq permisoPuedeMantenerTiposSubtareasBitacora}">
			   	<li class="${classMantenedorTiposSubtareaBitacoraActive} li-sub-menu">
			   		<a href="${mantenedorTiposSubtareaBitacoraURL}"><i class="glyphicon glyphicon-th-large"></i> Mantenedor Tipos Subtareas en bit&aacute;cora</a>
			  	</li>
		   	</c:if>
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerPlantillas] eq permisoPuedeMantenerPlantillas}">
			   	<li class="${classMantenedorPlantillasActive} li-sub-menu">
			   		<a href="${mantenedorPlantillasURL}"><i class="glyphicon glyphicon-file"></i> Mantenedor Plantillas Documentos</a>
			  	</li>
		   	</c:if>
		   	<c:if test = "${usuario.permisos[permisoPuedeMantenerSeriesDocumentales] eq permisoPuedeMantenerSeriesDocumentales}">
			   	<li class="${classserieDocumental} li-sub-menu">
				   		<a href="${serieDocumentalURL}"><i class="glyphicon glyphicon-folder-open"></i> Mantenedor Series Documentales</a>
				</li>
		   	</c:if>	 		 	
	    </c:if>	    
    	<li class="liMenu">
	    	<a target="_blank" href="${urlReporteSGDP}"><i class="glyphicon glyphicon-signal"></i> Reporte</a>
	    </li>
	   	 <li class="${classSolicitudesCreacionExpActive} liMenu">
		 	<a href="${solicitudesCreacionExpURL}"><i class="glyphicon glyphicon-bullhorn"></i> Solicitudes de Creaci&oacute;n Expedientes</a>
		 </li>
		 <c:if test = "${usuario.permisos[permisoPuedeCargarProseso] eq permisoPuedeCargarProseso}">
			<li class="${classCargaProcesos} liMenu">
			 	<a href="${cargaProcesosURL}"><i class="glyphicon glyphicon-object-align-horizontal"></i> Carga de Procesos</a>
			</li>    
		</c:if>
		<c:if test = "${usuario.permisos[permisoPuedeEditarAsunto] eq permisoPuedeEditarAsunto}">
			 <li class="${classEditorDeAsunto} liMenu">
		 		<a href="${editorDeAsuntoURL}"><i class="glyphicon glyphicon-comment"></i> Editor de Asunto</a>
		 	</li>    
		</c:if>
		<li class="${classReportes} liMenu">
	 		<a href="${reportesURL}"><i class="glyphicon glyphicon-file"></i> Reportes</a>
		</li> 
		<c:if test = "${usuario.permisos[permisoPuedeVerReportLogDocumento] eq permisoPuedeVerReportLogDocumento}">
	 		<li class="${classReportLogDocumento} liMenu">
				<a href="${reportLogDocumentoURL}"><i class="glyphicon glyphicon-road"></i> Reporte Log Documentos</a>
			</li>
	 	</c:if>
	 	<c:if test = "${usuario.permisos[permisoPuedeVerAnuladorDocumentos] eq permisoPuedeVerAnuladorDocumentos}">
	 		<li class="${classAnuladorDocumentosActive} liMenu">
				<a href="${anuladorDocumentosURL}"><i class="glyphicon glyphicon-eye-close"></i> Anulador Documentos</a>
			</li>
	 	</c:if>	 
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
					   			$('#h2MensajeAreaDeTrabajo').text("rea de Trabajo (Fuera de Oficina)");
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
					   			$('#h2MensajeAreaDeTrabajo').text("rea de Trabajo");
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
		         bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
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
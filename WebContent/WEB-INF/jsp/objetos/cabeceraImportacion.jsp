<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
<input type="hidden" id="urlGetDetalleDeTarea" value="<c:url value='/getDetalleDeTarea' />" />
<input type="hidden" id="urlCrearExpediente" value="<c:url value='/crearExpediente' />" />
<input type="hidden" id="urlGetProcesosPorIdMacroProceso" value="<c:url value='/getProcesosPorIdMacroProceso' />" />
<input type="hidden" id="urlGetDetalleDeDocumento" value="<c:url value='/getDetalleDeDocumento' />" />
<input type="hidden" id="urlGetInstanciasDeTareasQueContinuanDeInstanciaDeTarea" value="<c:url value='/getInstanciasDeTareasQueContinuanDeInstanciaDeTarea' />" />
<input type="hidden" id="urlMueveProceso" value="<c:url value='/mueveProceso' />" />
<input type="hidden" id="urlSubirArchivo" value="<c:url value='/subirArchivo' />" />
<input type="hidden" id="urlSubirDocumento" value="<c:url value='/subirDocumento' />" />
<input type="hidden" id="urlAgregarComentarioDocumento" value="<c:url value='/agregarComentarioDocumento' />" />
<input type="hidden" id="urlVisarDocumento" value="<c:url value='/visarDocumento' />" />
<input type="hidden" id="urlFirmaAvanzadaDocumento" value="<c:url value='/firmaAvanzadaDocumento' />" />
<input type="hidden" id="urlGetResultadoBusqueda" value="<c:url value='/getResultadoBusqueda' />" />
<input type="hidden" id="urlGetInstanciasDeTarea" value="<c:url value='/getInstanciasDeTarea' />" />
<input type="hidden" id="urlCrearExpediente" value="<c:url value='/crearExpediente' />" />
<input type="hidden" id="urlGetTareasEnEjecucion" value="<c:url value='/getTareasEnEjecucion' />" />
<input type="hidden" id="urlGetTablaHistorialDeProcesoPorIdExpediente" value="<c:url value='/getTablaHistorialDeProcesoPorIdExpediente' />" />
<input type="hidden" id="urlGetTablaDetalleDeExpedientePorIdExpediente" value="<c:url value='/getTablaDetalleDeExpedientePorIdExpediente' />" />
<input type="hidden" id="urlGetVistaEtapasInstanciaDeProcesoPorIdExpediente" value="<c:url value='/getVistaEtapasInstanciaDeProcesoPorIdExpediente' />" />
<input type="hidden" id="urlGetTiposDeFirmaAvanzada" value="<c:url value='/getTiposDeFirmaAvanzada' />" />
<input type="hidden" id="urlGetVistaInstanciasDeTareasPorIdExpediente" value="<c:url value='/getVistaInstanciasDeTareasPorIdExpediente' />" />
<input type="hidden" id="urlGetVistaHistorialDeTareasPorIdIntanciaDeTarea" value="<c:url value='/getVistaHistorialDeTareasPorIdIntanciaDeTarea' />" />
<input type="hidden" id="urlFinalizarProceso" value="<c:url value='/finalizarProceso' />" />
<input type="hidden" id="urlGetInstanciasDeTareasQueContinuanDeInstanciaDeTarea" value="<c:url value='/getInstanciasDeTareasQueContinuanDeInstanciaDeTarea' />" />
<input type="hidden" id="urlGetUsuariosPosiblesPorIdInstanciaDeTarea" value="<c:url value='/getUsuariosPosiblesPorIdInstanciaDeTarea' />" />
<input type="hidden" id="urlActualizaMetadataDeDocumento" value="<c:url value='/actualizaMetadataDeDocumento' />" />
<input type="hidden" id="urlGetHistorialDeTareasPorIdIntanciaDeTarea" value="<c:url value='/getHistorialDeTareasPorIdIntanciaDeTarea' />" />
<input type="hidden" id="urlGetDocumentosDeSalida" value="<c:url value='/getDocumentosDeSalida' />" />
<input type="hidden" id="urlRetrocedeProceso" value="<c:url value='/retrocedeProceso' />" />
<input type="hidden" id="urlGetCantidadDeTareas" value="<c:url value='/getCantidadDeTareas' />" />
<input type="hidden" id="urlGetTablaHistorialDeDocumentoPorIdExpediente" value="<c:url value='/getTablaHistorialDeDocumentoPorIdExpediente' />" />
<input type="hidden" id="urlGetDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento" value="<c:url value='/getDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento' />" />
<input type="hidden" id="urlMarcarArchivoComoSubido" value="<c:url value='/marcarArchivoComoSubido' />" />
<input type="hidden" id="urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda" value="<c:url value='/getHistoricoDeInstDeTareaPorIdExpedienteBusqueda' />" />
<input type="hidden" id="urlGetTipoDeDocumentoDTOPorIdProceso" value="<c:url value='/getTipoDeDocumentoDTOPorIdProceso' />" />
<input type="hidden" id="urlGetTiposDeDocumentosDTO" value="<c:url value='/getTiposDeDocumentosDTO' />" />
<input type='hidden' id="urlSessionValida" value="<c:url value='/verificarSession' />" />
<input type='hidden' id="raizURL" value="<c:url value='/' />" />
<input type='hidden' id="urlCerrarProceso" value="<c:url value='/cerrarProceso' />" />
<input type='hidden' id="urlGetHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario" value="<c:url value='/getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario' />" />
<input type='hidden' id="urlActualizaFueraDeOficina" value="<c:url value='/actualizaFueraDeOficina' />" />
<input type='hidden' id="urlGetDetalleDeExpedienteEnDistribucion" value="<c:url value='/getDetalleDeExpedienteEnDistribucion' />" />
<input type='hidden' id="urlGetHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea" value="<c:url value='/getHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea' />" />
<input type='hidden' id="urlBorrarRegistroDeListaDeDistribucion" value="<c:url value='/borrarRegistroDeListaDeDistribucion' />" />
<input type='hidden' id="urlCargaListaDeDistribucion" value="<c:url value='/cargaListaDeDistribucion' />" />
<input type='hidden' id="urlGetTiposDeDocumentosDTOPorNombreExpediente" value="<c:url value='/getTiposDeDocumentosDTOPorNombreExpediente' />" />
<input type='hidden' id="urlGetHistorialDeCondicionesDeSatisfaccionPorIdExpediente" value="<c:url value='/getHistorialDeCondicionesDeSatisfaccionPorIdExpediente' />" />
<input type='hidden' id="urlGetCondicionesDeSatisfaccionPorIdInstanciaDeTarea" value="<c:url value='/getCondicionesDeSatisfaccionPorIdInstanciaDeTarea' />" />
<input type='hidden' id="urlGetCondDeSatisParaMostrarPorIdInstanciaDeTarea" value="<c:url value='/getCondDeSatisParaMostrarPorIdInstanciaDeTarea' />" />

<select id="correosDeDistribucionHiden" class="hide">

</select>

<div id="idsArchivosADistribuir" class="hide">

</div>

<input type='hidden' id="validoFormDistribucion" >

<!-- Data table -->

<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/jquery-1.12.3.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/jquery.dataTables.min.js" />'></script>


<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/dataTables.bootstrap.min.js" />'></script>


<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/dataTables.buttons.min.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/buttons.bootstrap.min.js" />'></script>

<script type="text/javascript" src='<c:url value="js/plugins/datatable2/jszip.min.js" />'></script>


<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/pdfmake.min.js" />'></script>


<script type="text/javascript" src='<c:url value="js/plugins/datatable2/vfs_fonts.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/buttons.html5.min.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/buttons.print.min.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/plugins/datatable2/buttons.colVis.min.js" />'></script>


<link href='<c:url value="css/datatable2/buttons.bootstrap.min.css"/>' rel="stylesheet">
<link href='<c:url value="css/datatable2/dataTables.bootstrap.min.css"/>'
	rel="stylesheet">

<!-- Bootstrap Core CSS -->
<link href='<c:url value="css/bootstrap.min.css"/>' rel="stylesheet">
<link href='<c:url value="fonts/css/font-awesome.css"/>'
	rel="stylesheet">


<!-- MetisMenu CSS -->
<link href='<c:url value="css/plugins/metisMenu/metisMenu.min.css"/>'
	rel="stylesheet">

<!-- Timeline CSS -->
<link href='<c:url value="css/plugins/timeline.css"/>'
	rel="stylesheet">


<!-- Custom CSS -->
<link href='<c:url value="css/sb-admin-2.css"/>' rel="stylesheet">


<!-- Morris Charts CSS -->
<link href='<c:url value="css/plugins/morris.css"/>' rel="stylesheet">

<!-- Custom Fonts -->
<link href='<c:url value="fonts/css/font-awesome.min.css"/>'
	rel="stylesheet" type="text/css">

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript"
	src='<c:url value="js/bootstrap.min.js" />'></script>

<!-- Metis Menu Plugin JavaScript -->

<script type="text/javascript"
	src='<c:url value="js/plugins/metisMenu/metisMenu.min.js" />'></script>

<!-- Morris Charts JavaScript -->

<script type="text/javascript"
	src='<c:url value="js/plugins/morris/raphael.min.js" />'></script>
<script type="text/javascript"
	src='<c:url value="js/plugins/morris/morris.min.js" />'></script>
<!-- <script type="text/javascript"
	src='<c:url value="js/plugins/morris/morris-data.js" />'></script>
-->
<!-- Custom Theme JavaScript -->
<script type="text/javascript"
	src='<c:url value="js/sb-admin-2.js" />'></script>



<!-- validaciones -->

<link
	href='<c:url value="css/validaciones/validationEngine.jquery.css"/>'
	rel="stylesheet">

<script type="text/javascript"
	src='<c:url value="js/validaciones/jquery.validationEngine-es.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/validaciones/jquery.validationEngine.js" />'></script>

<!-- Librerias calendario -->

<script type="text/javascript"
	src='<c:url value="js/calendario/moment-with-locales.js" />'></script>
	
<script type="text/javascript"
	src='<c:url value="js/calendario/bootstrap-datetimepicker.js" />'></script>
<script type="text/javascript"	src='<c:url value="js/calendario/prettify-1.0.min.js" />'></script>
<script type="text/javascript"	src='<c:url value="js/calendario/base.js" />'></script>
 
<link	href='<c:url value="css/calendario/bootstrap-datetimepicker-standalone.css"/>'
	rel="stylesheet">

<link
	href='<c:url value="css/calendario/bootstrap-datetimepicker.css"/>'
	rel="stylesheet">
<link
	href='<c:url value="css/calendario/bootstrap-datetimepicker.min.css"/>'
	rel="stylesheet">
	
<script type="text/javascript"
	src='<c:url value="js/initVar.js" />'>	
</script>

<link href='<c:url value="css/responsiveInboxAndCompose.css"/>'
	rel="stylesheet">

<link href='<c:url value="css/uploadFile.css"/>'
	rel="stylesheet">
	
<script type="text/javascript"
	src='<c:url value="js/jquery-ui.min.js" />'></script>
	
<!--
<script type="text/javascript"
	src='<c:url value="css/jquery-ui.structure.css" />'></script>-->
	
<link href='<c:url value="css/jquery-ui.min.css"/>'
	rel="stylesheet">
	
<link href='<c:url value="css/sgdpComun.css"/>'
	rel="stylesheet">
	
<link href='<c:url value="css/tippy.css"/>'
	rel="stylesheet">

<script type="text/javascript"
	src='<c:url value="js/tippy.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/crearExpediente.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/mueveProceso.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/subirDocumento.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/subirArchivo.js" />'></script>

<script type="text/javascript"
	src='<c:url value="js/detalleDeTarea.js" />'></script>
	
<script type="text/javascript"
	src='<c:url value="js/bootbox.min.js" />'></script>		
	
<script type="text/javascript"
	src='<c:url value="js/detalleDeDocumento.js" />'></script>	

<script type="text/javascript"
	src='<c:url value="/js/tareasEnEjecucion.js" />'></script>	
	
<script type="text/javascript"
			src='<c:url value="/js/bandejaDeEntrada.js" />'>
		</script>
		
<script type="text/javascript"
			src='<c:url value="/js/buscador.js" />'>
		</script>


<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

 <link href='<c:url value="/css/select2.min.css"/>' rel="stylesheet"> 
<link href='<c:url value="/css/select2-bootstrap.css"/>' rel="stylesheet">
<link href='<c:url value="/css/select2-bootstrap.min.css"/>' rel="stylesheet">
<script type="text/javascript" src='<c:url value="/js/select2.full.min.js" />'></script>
<script type="text/javascript" src='<c:url value="/js/es_select2.js" />'></script>

<!--  Le da el efecto a las librerias de notificación -->
<link href='<c:url value="/css/animate.css"/>' rel="stylesheet">
<script type="text/javascript" src='<c:url value="/js/plugins/notify/bootstrap-notify.min.js" />'></script>


<link href='<c:url value="/css/jquery.fileupload.css"/>' rel="stylesheet">
<script type="text/javascript" src='<c:url value="/js/jquery.ui.widget.js" />'></script>
<script type="text/javascript" src='<c:url value="/js/jquery.fileupload.js" />'></script>


<script type="text/javascript" src='<c:url value="/js/plugins/pdf/pdfobject.js" />'></script>

<script type="text/javascript" src='<c:url value="/js/plugins/pdf/pdfobject.js" />'></script>

<script type="text/javascript" src='<c:url value="/js/plugins/moment/datetime-moment.js" />'></script>
<script type="text/javascript" src='<c:url value="/js/plugins/moment/moment.js" />'></script>

<link href='<c:url value="/css/duration-picker/duration-picker.min.css"/>' rel="stylesheet">
<script src='<c:url value="/js/plugins/duration-picker/duration-picker.min.js"/>'></script>


<script>	
     $(function(){
            $( document ).ajaxError(function( event, jqxhr, settings, thrownError ) {
                   console.log(event);
                   console.log(jqxhr);
                   console.log(settings);
                   console.log(thrownError);
                   $.get("${sessionURL}", function(haySession){
                          if(haySession){
                                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-displeased don_sshi\"></i><p style=\"margin-top: 15px;\">¡Vaya!, algo no ha salido como se esperaba, por favor intente nuevamente o contacte a soporte. </p> <p>"+thrownError+"</p></div>");
                          }else{
                                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                                              , function(){
                                                    window.open('${raizURL}', '_blank');
                                              }
                                              );
                          }
                   });
            });
     });     
</script>

 <style>
   /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
   .row.content {height: 1500px}
   
   /* Set gray background color and 100% height */
   .sidenav {
     background-color: #f1f1f1;
     height: 100%;
   }
   
   /* Set black background color, white text and some padding */
   footer {
     background-color: #555;
     color: white;
     padding: 15px;
   }
   
   /* On small screens, set height to 'auto' for sidenav and grid */
   @media screen and (max-width: 767px) {
     .sidenav {
       height: auto;
       padding: 15px;
     }
     .row.content {height: auto;} 
   }
 </style>
 
 <!-- Vinculaciones de expedientes-->

<c:import url="/WEB-INF/jsp/modals/vinculacionExpediente.jsp"></c:import>	

<!-- Condiciones de Satisfaccion-->

<c:import url="/WEB-INF/jsp/modals/historialDeCondDeSatisfaccion.jsp"></c:import>

<!-- Edicion Registro Lista de Distribucion-->

<c:import url="/WEB-INF/jsp/modals/editaRegistroListaDeDistribucion.jsp"></c:import>

<!-- Creacion Registro Lista de Distribucion-->

<c:import url="/WEB-INF/jsp/modals/creaRegistroListaDeDistribucion.jsp"></c:import>		

<!-- Creacion Registro Parametros-->

<c:import url="/WEB-INF/jsp/modals/creaParametro.jsp"></c:import>	

<!-- Edita Registro Parametros-->

<c:import url="/WEB-INF/jsp/modals/editaParametro.jsp"></c:import>
	
<!-- Nueva solicitud de creacion de expediente-->

<c:import url="/WEB-INF/jsp/modals/nuevaSolicitudCreacExp.jsp"></c:import>

<!-- Solicitudes de creacion de expediente finalizadas-->

<c:import url="/WEB-INF/jsp/modals/solicitudesDeCreacionDeExpedientesFinalizadas.jsp"></c:import>	

<!-- Condiciones de satisfaccion-->

<c:import url="/WEB-INF/jsp/modals/condDeSatisfaccion.jsp"></c:import>	

<c:import url="/WEB-INF/jsp/modals/condDeSatisfaccionParaMostrar.jsp"></c:import>

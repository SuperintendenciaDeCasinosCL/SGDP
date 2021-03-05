<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<div class="dropdown">
	<br>
   <button class="btn btn-primary btn-block dropdown-toggle" type="button" data-toggle="dropdown">
   	<i class="glyphicon glyphicon-education"></i> Tutoriales
   	<span class="caret"></span>
   </button>
   <ul class="dropdown-menu">
	     <li><a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoInicioSesion}" data-tituloventana="Iniciar Sesión"><span class="glyphicon glyphicon-facetime-video"></span> Iniciar sesi&oacute;n</a></li>
	     <li><a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoIniciarUnProceso}" data-tituloventana="Iniciar Un Proceso"><span class="glyphicon glyphicon-facetime-video"></span> Iniciar un proceso</a></li>
	     <li class="dropdown-submenu">
	       <a class="sub-menu-ayuda" tabindex="-1" href="#">Tarea asignada <span class="caret"></span></a>
	       <ul class="dropdown-menu">
	         <li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoDetalleTareaAsignada}" data-tituloventana="Ver el detalle de una tarea asignada"><span class="glyphicon glyphicon-facetime-video"></span> Ver el detalle de una tarea asignada</a>
			</li>
			<li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoVerHistorialDeDocumentosDeUnaTarea}" data-tituloventana="Ver historial de documentos de una tarea"><span class="glyphicon glyphicon-facetime-video"></span> Ver historial de documentos de una tarea</a>
			</li>
			<li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoSubirDocumentoRequerido}" data-tituloventana="Subir un documento requerido"><span class="glyphicon glyphicon-facetime-video"></span> Subir un documento requerido</a>
			</li>
			<li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoSubirAdjuntoDeUnDocumento}" data-tituloventana="Subir un adjunto de un documento"><span class="glyphicon glyphicon-facetime-video"></span> Subir un adjunto de un documento</a>
			</li>
			<li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoAnadirDocumentoOpcional}" data-tituloventana="Añadir documento opcional"><span class="glyphicon glyphicon-facetime-video"></span> A&ntilde;adir documento opcional</a>
			</li>
			<li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoEnviarTarea}" data-tituloventana="Enviar tarea"><span class="glyphicon glyphicon-facetime-video"></span> Enviar tarea</a>
			</li>
			<li>
			    <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoDevolverTarea}" data-tituloventana="Retroceder tarea"><span class="glyphicon glyphicon-facetime-video"></span> Devolver tarea</a>
			</li>         
	       </ul>
	     </li>
      	<li><a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoVerFlujosEnCursoYReasignarUnaTarea}" data-tituloventana="Ver flujos en curso y reasignar tarea"><span class="glyphicon glyphicon-facetime-video"></span> Ver flujos en curso y reasignar tarea</a></li>   
   		<li class="dropdown-submenu">
       		<a class="sub-menu-ayuda" tabindex="-1" href="#">Visar y firmar un documento <span class="caret"></span></a>
       		<ul class="dropdown-menu">
			  <li>
			      <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoVisarUnDocumento}" data-tituloventana="Visar un documento"><span class="glyphicon glyphicon-facetime-video"></span> Visar un documento</a>
			  </li>
			  <li>
			      <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoFirmarUnDocumentoConFirmaCentralizada}" data-tituloventana="Firmar un documento con firma centralizada"><span class="glyphicon glyphicon-facetime-video"></span> Firmar un documento con firma centralizada</a>
			  </li>
			  <li>
			      <a tabindex="-1" href="#" class="link-carga-video" data-urlvideoiniciosesion="${urlVideoFirmarUnDocumentoConFirmaToken}" data-tituloventana="Firmar un documento con firma token"><span class="glyphicon glyphicon-facetime-video"></span> Firmar un documento con firma token</a>
			  </li>		
			</ul>	
       	</li>
	</ul>
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>
	var incializaDropDownSubmenu = function() {
	  $('.dropdown-submenu a.sub-menu-ayuda').on("click", function(e){
	    $(this).next('ul').toggle();
	    e.stopPropagation();
	    e.preventDefault();
	  });
	};
	var incializaLinkCargaVideo = function() {
		$(".link-carga-video").off('click').on('click', function () {
			var urlIdVideo = $(this).attr("data-urlvideoiniciosesion");
			var titulo = $(this).attr("data-tituloventana");
			$(".link-carga-video").removeClass("sub-menu-apply");
			$.get("${sessionURL}", function(haySession){
                console.log("haySession: " + haySession);
                if(haySession){
                     window.open(urlIdVideo, titulo, 'width=1080, height=600');
                } else {
                      bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                                    , function(){
                                          window.open('${raizURL}', '_blank');
                                    }
                       );
                }
         	});
			$(this).addClass("sub-menu-apply");
		});
	};
	var remueveClassDeLiLinkManual = function() {
		$("#liLinkManual").removeClass("active");	
	};	
	function actualizaActive(){
		$(".liMenu").removeClass("active");
		$("#liLinkManual").addClass("active");
	}
	function cargaVideo(urlIdVideo, titulo) {		
		window.open(urlIdVideo, titulo, 'width=1080, height=600');		
	}
	$(document).ready(function() {
		$(remueveClassDeLiLinkManual);		
		$(incializaLinkCargaVideo);	
		$(incializaDropDownSubmenu);	
	});
</script>
  
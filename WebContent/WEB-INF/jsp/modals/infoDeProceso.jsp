<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />
<c:url value="http://sgdocb/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}&idInsProc=${instanciaDeTareaDTO.idInstanciaDeProceso}&t=s" var="urlDiagramaSubProceso" />
<c:url value="http://sgdocb/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}&idInsProc=${instanciaDeTareaDTO.idInstanciaDeProceso}" var="urlDiagramaSubProcesoBoton" />

<div class="modal fade" id="infoDeProcesoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">

	<div class="modal-dialog modal-lg">
		<div class="modal-content">		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				
				<h3 class="modal-title">Informaci&oacute;n del SubProceso</h3>
			
			</div>
			
			<div class="modal-body" id="informacionDelProcesoDiv">			
			
				<div class="row">
					<div class="col-sm-12"><c:import url="/WEB-INF/jsp/div/informacionDelProceso.jsp"></c:import></div>
				</div>
				
				<div class="row">				
					<div class="col-sm-12"><strong>SubProceso:</strong></div>
					<br>
					<br>
					<div class="col-sm-12">
						<%-- <c:import url="http://sgdocb/proceso/bpm/this_task.php?idTask=${instanciaDeTareaDTO.idDiagrama}&idProc=${instanciaDeTareaDTO.idProceso}"></c:import>--%>
						<c:import url="${urlDiagramaSubProceso}"></c:import>
					</div>
				</div>
				
				<br>
				
				<div class="row">			
					<div class="col-sm-12">
						<button type="button" class="btn btn-primary" id="botonVerDiagramaEnNuevaVentana">
							Ampliar Diagrama
						</button>
					</div>				
				</div>
				
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var inicializaBotonVerDiagramaEnNuevaVentana = function (){
  $("#botonVerDiagramaEnNuevaVentana").off('click').on('click', function () {
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
$(document).ready(function() {
	$(inicializaBotonVerDiagramaEnNuevaVentana);
});
</script>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />


	<c:choose>
		<c:when test = "${flagBuscarDiagrama eq 0}">
			<c:url value="http://sgdocb/proceso/bpm/this_task.php?idTask=${idDiagrama}&idProc=${idProceso}&idInsProc=${idInstanciaDeProceso}&t=s" var="urlDiagramaSubProceso2" />
			<c:url value="http://sgdocb/proceso/bpm/this_task.php?idTask=${idDiagrama}&idProc=${idProceso}&idInsProc=${idInstanciaDeProceso}" var="urlDiagramaSubProcesoBoton2" />
		</c:when>
		<c:when test = "${flagBuscarDiagrama eq 1}">
				<c:url value="http://sgdocb/proceso/bpm/this_task.php?nomExp=${instanciaDeProcesoDTO.nombreExpediente}&t=s" var="urlDiagramaSubProceso2" />
				<c:url value="http://sgdocb/proceso/bpm/this_task.php?nomExp=${instanciaDeProcesoDTO.nombreExpediente}" var="urlDiagramaSubProcesoBoton2" />
		</c:when>
	</c:choose>
	
		
				<div class="row">
					<div class="col-sm-12"><c:import url="/WEB-INF/jsp/div/informacionDelProceso.jsp"></c:import></div>
				</div>
		
				
				<div class="row">				
					<div class="col-sm-12"><strong>SubProceso:</strong></div>
					<br>
					<br>
					<div class="col-sm-12">
						<c:import url="${urlDiagramaSubProceso2}"></c:import>
					</div>
				</div>
				
				<br>
				
				<div class="row">			
					<div class="col-sm-12">
						<button type="button" class="btn btn-primary" id="botonVerDiagramaEnNuevaVentana2">
							Ampliar Diagrama
						</button>
					</div>				
				</div>



<script type="text/javascript">
var inicializaBotonVerDiagramaEnNuevaVentana2 = function (){
  $("#botonVerDiagramaEnNuevaVentana2").off('click').on('click', function () {
    var urlDiagramaSubProcesoBoton = "${urlDiagramaSubProcesoBoton2}";    
    $.get("${sessionURL}", function(haySession) {          
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
	$(inicializaBotonVerDiagramaEnNuevaVentana2);
});

</script>
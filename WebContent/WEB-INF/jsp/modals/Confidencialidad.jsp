<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import= "cl.gob.scj.sgdp.control.AppContextControl" %> 

<div class="modal fade modal-lg" id="asignarConfidencialidadModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 class="modal-title" id="lineModalLabel">Asignaci&oacute;n de confidencialidad a documento</h3>
			</div>
			<div class="modal-body" id="contenedorFormulario" style="padding: 30px">	
				<div id="app"></div>
					<script type="text/javascript"	src='<c:url value="https://cdn.jsdelivr.net/npm/vue@2.6.14" />'></script>
					<script type="text/javascript"	src='<c:url value="https://unpkg.com/vuex@3.6.2/dist/vuex.js" />'></script>
					<script type="text/javascript"	src='<c:url value="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js" />'></script>
					
					<script type="text/javascript"	src='<c:url value="js/confidencialidadDocumento/services/dataService.js" />'></script>
					
					<script type="text/javascript"	src='<c:url value="js/confidencialidadDocumento/components/Container.js" />'></script>
					<script type="text/javascript"	src='<c:url value="js/confidencialidadDocumento/components/Form.js" />'></script>
					
					<script type="text/javascript"	src='<c:url value="js/confidencialidadDocumento/main.js" />'></script>
			</div>
		</div>
	</div>
</div>

<script>
$(function() {
    
});
</script>

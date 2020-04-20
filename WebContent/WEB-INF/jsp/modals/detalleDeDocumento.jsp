<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="detalleDeDocumentoModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	
	<input type="hidden" id="linkDescargaNavegador" name="linkDescargaNavegador" />	
	
	<input type="hidden" id="idExpedienteDetalleDeDocumentoModal" />
	<input type="hidden" id="nombreArchivoDetalleDeDocumentoModal" />
	<input type="hidden" id="mimeTypeDetalleDeDocumentoModal" />
	<input type="hidden" id="idDocumentoDetalleDeDocumentoModal" />
	<input type="hidden" id="tipoDeDocumentoDetalleDeDocumentoModal" />	
	<input type="hidden" id="cdrDetalleDeDocumentoModal" />
	<input type="hidden" id="numeroDocumentoDetalleDeDocumentoModal" />
	<input type="hidden" id="emisorDetalleDeDocumentoModal" />
	<input type="hidden" id="cartaRelacionadaDetalleDeDocumentoModal" />
	
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title" id="detalleDeDocumentoModalTitulo"></h3>
			</div>
			
			<div class="modal-body">
			
				<div id="detalleDeDocumentoDiv">
				
				</div>			
			
			</div>
			
			<div class="modal-footer"></div>			
		
		</div>
	</div>
</div>

<script>
function recargaDetalleDocumentoModal() {  	
     var urlGetDetalleDeDocumento = $('#urlGetDetalleDeDocumento').val()
     +"/"+$("#linkRecargaDetalleDocumento").attr("data-idarchivo")
     +"/"+$("#linkRecargaDetalleDocumento").attr("data-esvisable")
     +"/"+$("#linkRecargaDetalleDocumento").attr("data-aplicafea")
     +"/"+$("#linkRecargaDetalleDocumento").attr("data-aplicafirmaapplet")
     +"/"+$("#linkRecargaDetalleDocumento").attr("data-idexpediente") 
      +"/"+$("#linkRecargaDetalleDocumento").attr("data-idinstanciadetarea");
     console.log("urlGetDetalleDeDocumento: " + urlGetDetalleDeDocumento);
     $('#detalleDeDocumentoDiv').each(function() {        	 
		$(this).fadeOut("slow").load(urlGetDetalleDeDocumento ).fadeIn('slow');	
	 });   		
}; 
</script>
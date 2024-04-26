<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="historialCondDeSatisfaccionModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="historialCondicionesDeSatisfaccionH4" class="modal-title"></h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="historialDeCondicionesDeSatisfaccionDiv" class="panel-body">				
					
					</div>  
				</div>	
	    	</div>
	    </div>    
  	</div>
</div> 

<script>

	function cargaHistorialDeCondicionesDeSatisfaccionPorIdExpediente(nombreExpediente, idExpediente) {
	
		var urlSessionValida = $("#urlSessionValida").val();
		var raizURL = $("#raizURL").val();
	
		$.get(urlSessionValida, function(haySession) {
		      console.log("haySession: " + haySession);
		      if(haySession) {
		   			var urlGetHistorialDeCondicionesDeSatisfaccionPorIdExpediente = $("#urlGetHistorialDeCondicionesDeSatisfaccionPorIdExpediente").val();
		    		$("#historialCondicionesDeSatisfaccionH4").empty();
		    		$("#historialCondicionesDeSatisfaccionH4").append('Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes: ( ' + nombreExpediente + ' ) ');
		    		$('#historialDeCondicionesDeSatisfaccionDiv').load(urlGetHistorialDeCondicionesDeSatisfaccionPorIdExpediente + "/" + idExpediente );
		    		$('#historialCondDeSatisfaccionModal').modal({backdrop: 'static', keyboard: false});
		      }	else {
		            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
		                          , function(){
		                                window.open(raizURL, '_self');
		                          }
		             );
		      }
		}); 
		
	}
	
	function cargaHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea(idHistoricoDeInstDeTarea, nombreExpediente, nombreTarea) {

		var urlSessionValida = $("#urlSessionValida").val();
		var raizURL = $("#raizURL").val();

		$.get(urlSessionValida, function(haySession) {
		      console.log("haySession: " + haySession);
		      if(haySession) {
		    	  	var urlGetHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea = $("#urlGetHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea").val();
		    		console.log("urlGetHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea: " + urlGetHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea);
		    		console.log("idHistoricoDeInstDeTarea: " + idHistoricoDeInstDeTarea);
		    		$("#historialCondicionesDeSatisfaccionH4").empty();
		    		$("#historialCondicionesDeSatisfaccionH4").append('Historial de Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes: ' + nombreTarea + " ( " + nombreExpediente + " ) ");
		    		$('#historialCondDeSatisfaccionModal').modal({backdrop: 'static', keyboard: false});
		    		$('#historialDeCondicionesDeSatisfaccionDiv').load(urlGetHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea + "/" + idHistoricoDeInstDeTarea );
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


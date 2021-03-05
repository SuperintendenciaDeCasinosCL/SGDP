<div class="modal fade" id="condicionesDeSatisfaccionParaMostrarModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="condicionesDeSatisfaccionParaMostrarH4" class="modal-title"></h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="condicionesDeSatisfaccionParaMostrarDiv" class="panel-body">				
					
					</div>  
				</div>	
	    	</div>
	    	<div class="modal-footer">	    		          
	        </div>
      	</div>
      </div>    
</div>

<script>

	function cargaCondicionesDeSatisfaccionParaMostrar(idInstanciaDeTarea, nombreDeTarea, nombreDeProceso) {
	
		var urlSessionValida = $("#urlSessionValida").val();
		var raizURL = $("#raizURL").val();
	
		$.get(urlSessionValida, function(haySession) {
		      console.log("haySession: " + haySession);
		      if(haySession) {
		    	  	$('#condicionesDeSatisfaccionParaMostrarDiv').empty();
		   			var urlGetCondDeSatisParaMostrarPorIdInstanciaDeTarea = $("#urlGetCondDeSatisParaMostrarPorIdInstanciaDeTarea").val();
		    		$("#condicionesDeSatisfaccionParaMostrarH4").empty();
		    		$("#condicionesDeSatisfaccionParaMostrarH4").append('Requisitos de Satisfacci&oacute;n y/o Salidas no Conformes: ' + nombreDeTarea + ' ( ' + nombreDeProceso + '  ) ');
		    		$('#condicionesDeSatisfaccionParaMostrarDiv').load(urlGetCondDeSatisParaMostrarPorIdInstanciaDeTarea + "/" + idInstanciaDeTarea );
		    		$('#condicionesDeSatisfaccionParaMostrarModal').modal({backdrop: 'static', keyboard: false});
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


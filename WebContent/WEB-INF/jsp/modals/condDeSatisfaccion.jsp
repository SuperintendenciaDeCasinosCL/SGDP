<div class="modal fade" id="condicionesDeSatisfaccionModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="condicionesDeSatisfaccionH4" class="modal-title"></h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="condicionesDeSatisfaccionDiv" class="panel-body">				
					
					</div>  
				</div>	
	    	</div>
	    	<div class="modal-footer">
	    		<div class="col-sm-3"></div>
	    		<div class="col-sm-6"><button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal"> Evaluar</button></div>
	    		<div class="col-sm-3"></div>	          
	        </div>
      	</div>
      </div>    
</div>

<script>

	var cargoRDSSNC = false;
	
	function resetCondicioDeSatisFaccion() {
		if ($('#condicionesDeSatisfaccionDiv').length>=1) {
			$('#condicionesDeSatisfaccionDiv').empty();
		}		
		cargoRDSSNC = false;
	}

</script>
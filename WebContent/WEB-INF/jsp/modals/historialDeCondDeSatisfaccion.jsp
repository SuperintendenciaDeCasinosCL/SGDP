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
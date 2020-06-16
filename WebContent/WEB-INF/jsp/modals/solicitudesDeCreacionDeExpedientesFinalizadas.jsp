<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="solicitudesCreacExpFinalizadasModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">	
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="solicitudesCreacExpFinalizadasH4" class="modal-title">Solicitudes de creaci&oacute;n de expedientes finalizadas</h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="solicitudesCreacExpFinalizadasDiv" class="panel-body">					
						<div class="table-responsive col-sm-12">				  		
							<table id="tablaSolicitudesDeCreacionDeExpedienteFinalizadas" class="table table-striped table-bordered table-fixed tabla_espacio_total">											
							    <thead>
							        <tr>
							            <th>ID</th>
							            <th>Nombre Subproceso</th>
							            <th>Usuario que solicita crear expediente</th>
							            <th>Usuario creador de expediente</th>
							            <th>Fecha solicitud</th>
							            <th>Fecha atenci&oacute;n</th>
							            <th>Comentario</th>
							            <th>Expediente</th>
							            <th>Estado</th>									          	       
							        </tr>
							    </thead>							    
							</table>			    
			   		 	</div>			    
					</div>  
				</div>	
	    	</div>
	    </div>    
  	</div>
</div>
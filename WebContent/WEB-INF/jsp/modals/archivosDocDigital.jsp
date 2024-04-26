<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="archivosDocDigitalModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="archivosDocDigitlaModalH4" class="modal-title">Archivos Doc Digital</h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="archivosDocDigitalModalDiv" class="panel-body">
						<div class="table-responsive col-sm-12">
							<table id="tablaArchivosDocDigital" class="table table-striped table-bordered table-fixed tabla_espacio_total">
							    <thead>
							        <tr>
							            <th>Nombre Archivo</th>
							            <th>Fecha Creaci&oacute;n</th>
							            <th>Folio Origen</th>
							            <th>Tipo Origen</th>
							            <th>Reservado</th>
							            <th>Usuarios Destinatarios</th>
							            <th>Descargar</th>
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

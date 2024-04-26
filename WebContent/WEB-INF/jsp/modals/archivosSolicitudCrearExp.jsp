<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="archivosSolicitudCrearExpModal" role="dialog" style="display: none;">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">X</button>
	        	<h4 id="solicitudesCreacExpFinalizadasH4" class="modal-title">Archivos de solicitudes para creaci&oacute;n de expedientes</h4>
	       	</div>
	        <div class="modal-body">
				<div class="row">
					<div id="archivosSolicitudCrearExpDiv" class="panel-body">
						<div class="table-responsive col-sm-12">
							<table id="tablaObtenerArchivos" class="table table-striped table-bordered table-fixed tabla_espacio_total">
							    <thead>
							        <tr>
							            <th>Nombre Archivo</th>
							            <th>Fecha Creaci&oacute;n</th>
							            <th>Folio Origen</th>
							            <th>Tipo Origen</th>
							            <th>Reservado</th>
							            <th>Usuarios Reservados</th>
							            <th>Descargar</th>
							        </tr>
							    </thead>
							</table>
			   		 	</div>
			   		 <div class="col-sm-2">
			   		 	<button type="button" class="btn btn-primary" id="crearExpArc" class="col-sm-4" style="margin-top: 16px;"><i class="glyphicon glyphicon-pencil"></i>Crear Expediente</button>
			   		 </div>
					</div>
				</div>
	    	</div>
	    </div>
  	</div>
</div>

<script>

var inicializaNombreBoton = function() {
	$('#crearExpArc').click(function() {
		var botonAdjuntaDocumento = $(this);
		$.get("${sessionURL}", function(haySession) {
			if (haySession) {
				var idExpediente = botonAdjuntaDocumento.attr("data-solcrear");
				creaExp(idExpediente);
				//cerrar model
				$('#archivosSolicitudCrearExpModal').modal('hide');
			} else {
				bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>", function() {
					window.open('${raizURL}', '_self');
				});
			}
		});
	});
};

$(document).ready(function() {
	$(inicializaNombreBoton);
});


</script>

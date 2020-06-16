<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA_FORM_HH_MM" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<c:set var = "enviar" value="enviar"/>
<c:set var = "finalizar" value="finalizar"/>


<form class="form-horizontal" id="formCorreoDeDistribucionModal">

	<div class="form-group">
	  	<label class="control-label col-sm-3" for="correosDeDistribucion">Correos de distribuci&oacute;n:</label>
	  	<div class="col-sm-9">	
	    	<select id="correosDeDistribucion" class="form-control select2-correos-de-distribucion validate[required]"" multiple="multiple">
				<c:forEach items="${listaDistribucion}" var="listaDeDistribucionDTO">								
			    	<option value="${listaDeDistribucionDTO.email}">${listaDeDistribucionDTO.email} (${listaDeDistribucionDTO.organizacion} - ${listaDeDistribucionDTO.cargo})</option>
				</c:forEach>    
	    	</select>
	  	</div>
  	</div>
  	<div class="form-group">  		
  		<label class="control-label col-sm-3" id="asuntoCorreoDistribucionLabel" for="asuntoCorreoDistribucion">Asunto: </label>
  		<div class="col-sm-9">  			
  			<!--  <input type="text" class="form-control validate[required]" id="asuntoCorreoDistribucion" 
  			readonly value="${instanciaDeProcesoDTO.nombreDeProceso} - ${instanciaDeProcesoDTO.asunto} - ${instanciaDeProcesoDTO.nombreExpediente}">-->
  			
  			<textarea class="form-control validate[required]" id="asuntoCorreoDistribucion" readonly>${instanciaDeProcesoDTO.nombreDeProceso} - ${instanciaDeProcesoDTO.asunto} - ${instanciaDeProcesoDTO.nombreExpediente}</textarea>
  		</div>
  		<div id="seleccionarArchivosDistribuicion">  			
  		</div>	  	
  	</div>  			     
</form>

<div class="table-responsive col-sm-12">
				  		
 		<table id="tablaHistorialDelDocumentoEnDistribucion" class="table table-striped table-bordered table-fixed">											
	    <thead>
	        <tr>	             	 
	            <th>Documento (Tipo)</th>	          
	            <th></th>
	            <th>Seleccionar</th>      						       
	        </tr>
	    </thead>
	    <tbody>
	    
	    	<c:forEach var="archivoInstDeTareaDTO" items="${todosLosDocSubidos}">	    	
	    		
	    		<tr>	    			
			    	<td>${archivoInstDeTareaDTO.nombreArchivo} (${archivoInstDeTareaDTO.nombreDeTipoDeDocumento})</td>
			    	<td align="center">		    				
						<button type="button" title="Descarga documento"
							class="btn btn-primary btn-xs"
							onclick='descargaArchivo("<c:url value='getArchivoPorId/${archivoInstDeTareaDTO.idArchivoCms}'/>")'
							data-iddocumento="${archivoInstDeTareaDTO.idArchivoCms}">
							<span class="fa fa-download font-icon-2 "></span>
						</button>	
			    	</td>
			    	<td>
			    		<div class="checkbox" id="checkBoxesADistribuir">
  							<label>
  								<input id="idArchivoADistribuir${archivoInstDeTareaDTO.idArchivoCms}" onclick="cargaIdArchivosADistribuir('idArchivoADistribuir${archivoInstDeTareaDTO.idArchivoCms}')" type="checkbox" class="id-archivos-a-distribuir" name="idArchivosADistribuir" value="${archivoInstDeTareaDTO.idArchivoCms}">
  							</label>
						</div>
			    	</td>
			    </tr>
	    	
	    	</c:forEach>
	  	       
	    </tbody>
	</table> 	
 	
</div>

<div class="col-sm-12">	  	

	<div class="pull-left">
	
		<button id="botonGuardaDatosDeDistribucion" onclick="guardaDatosDeDistribucion()"						       									       								       			
						type="button" 
						class="btn btn-labeled btn-primary">
					Guardar <span class="btn-label-default"><i class="glyphicon glyphicon-saved"></i></span>	               						
					</button> 
	
	</div>
	
</div>

<script>

function guardaDatosDeDistribucion() {
	$('#correosDeDistribucionHiden').empty();
    $('#correosDeDistribucion option:selected').each(function() {      
        $('#correosDeDistribucionHiden').append('<option value="'+$(this).val()+'">'+$(this).val()+'</option>');
        $('#correosDeDistribucion').append($(this));
    });   
	var validaForm = $("#formCorreoDeDistribucionModal").validationEngine('validate');
	var idArchivosADistribuirArray = [];
	$('input[name="idArchivosADistribuirHiden"]').each(function() {   
	    idArchivosADistribuirArray.push(this.value);
	});
	if (idArchivosADistribuirArray.length == 0) {
    	$("#seleccionarArchivosDistribuicion").validationEngine('showPrompt', 'Por favor seleccione al menos un archivo para distribuir', 'error'); 
	} else if (validaForm == true) {
		$("#validoFormDistribucion").val("true");	
		$("#botonAbrirCorreoDeDistribucionModal").removeClass("btn-warning");
		$("#botonAbrirCorreoDeDistribucionModal").addClass("btn-primary");
		$("#estadoFormCorreoDeDistribucionModal").removeClass("glyphicon-ban-circle");
		$("#estadoFormCorreoDeDistribucionModal").addClass("glyphicon-ok-circle");
		if ($(".botonAbrirCorreoDeDistribucionModalformError").length) {
			$(".botonAbrirCorreoDeDistribucionModalformError").remove();
		}	
		$('#correoDeDistribucionModal').modal('hide');	
	}
}

var formatTablaHistorialDelDocumentoEnDistribucion = function () {
	
	var tablaHistorialDelDocumentoEnDistribucion = $('#tablaHistorialDelDocumentoEnDistribucion')
	.DataTable(
			{				
				"language" : languajeDataTableDocumentos,
				"pageLength": 7,
				"order": [[ 0, "desc" ]],
				"searching": false	
			});
	
	tablaHistorialDelDocumentoEnDistribucion.buttons().container().appendTo(
	'#tablaHistorialDelDocumentoEnDistribucion_wrapper .row:eq(0)');

	$('#tablaHistorialDelDocumentoEnDistribucion_length').addClass("hide");
};

var inicializaSelect2CorreosDeDistribucion = function(){
	$(".select2-correos-de-distribucion").select2({
		    theme: "bootstrap",
 		    dropdownParent: $("#formCorreoDeDistribucionModal"),
 		    language: "es"
 		});
};

/*var inicializaChangeIdArchivosADistribuir = function(){
	$(".id-archivos-a-distribuir").change(function () {
		console.log($(this).val());
		console.log($(this).is(":checked"));
		if ($(this).is(":checked") == true) {
			$('#idsArchivosADistribuir').append('<input type="checkbox" id="idArchivoADistribuirHiden'+$(this).val()+'" name="idArchivosADistribuirHiden" value="'+$(this).val()+'">');
		} else {
			$("#idArchivoADistribuirHiden"+$(this).val()).remove();
		}
	});
};*/

function cargaIdArchivosADistribuir(id) {
	var obj = $("#"+id);
	console.log(obj.val());
	console.log(obj.is(":checked"));
	if (obj.is(":checked") == true) {
		$('#idsArchivosADistribuir').append('<input type="checkbox" id="idArchivoADistribuirHiden'+obj.val()+'" name="idArchivosADistribuirHiden" value="'+obj.val()+'">');
	} else {
		$("#idArchivoADistribuirHiden"+obj.val()).remove();
	}
}

$(document).ready(function(){
	$(formatTablaHistorialDelDocumentoEnDistribucion);
	$(inicializaSelect2CorreosDeDistribucion);
	//$(inicializaChangeIdArchivosADistribuir);	
});


</script>
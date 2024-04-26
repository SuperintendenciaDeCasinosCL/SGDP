<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="cargaListaDistribucionMasivaModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
					<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
				</button>
				<h3 class="modal-title" id="cargaListaDistribucionMasivaTitulo">Carga Masiva Lista Destinatarios</h3>
			</div>				
			<div class="modal-body">
				<form class="form-horizontal" id="formCargaListaDistribucionMasivaModal" method="POST" enctype="multipart/form-data">
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="spanListaDistribucionMasivaModal">Archivo (*):</label>
				      <div class="col-sm-4">	
						<span class="btn btn-success fileinput-button btn-file btn-verde-tarea" id="spanListaDistribucionMasivaModal"> 
                    		<i class="glyphicon glyphicon-plus"></i>
                    		<span id="nombreArchivoSpanListaDistribucionMasivaModal">Seleccionar archivo</span>
                    		<input class="documento-adicional-file" 
                    			type="file" name="archivo"
                    			id="archivoListaDistribucionMasiva" >
                		</span>
				      </div>
				      <div class="col-sm-5">
				      	<span id="mensajesDeErrorListaDistribucionMasivaModal"></span>
				      </div>
				    </div>
				   	<div class="form-group"> 
						<div class="col-sm-12 text-center"> 
							<button id="botonCargaListaDistribucionMasivaModal" type="button" class="btn btn-primary">
								<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir
							</button> 
						</div>
					</div>
					
				</form>			
			</div>		
		</div>	
	</div>
</div>

<script>
var inicializaCargaListaDistribucionMasivaModal = function(){
	var sacar = 0
	var urlCargaListaDistribucionMasiva = $("#urlCargaListaDistribucionMasiva").val();
	var urlCargaListaDeDistribucion = $("#urlCargaListaDeDistribucion").val();
	console.log("urlCargaListaDistribucionMasiva: " + urlCargaListaDistribucionMasiva);	
  	var notify = "";
  	var tamanoDeArchivo;
  	var extension;
  	var allowedExtensionsRegx = /(\.xls)$/i;
  	$('#cargaListaDistribucionMasivaModal').fileupload({
  		dataType: 'json',
        url: urlCargaListaDistribucionMasiva,
        autoUpload: false,
        add: function (e, data) {    
            $( "#nombreArchivoSpanListaDistribucionMasivaModal" ).empty();       
        	$.each(data.files, function (index, file) {
                $('#nombreArchivoSpanListaDistribucionMasivaModal').text(file.name);
                extension = file.name.substr(file.name.lastIndexOf("."));
                tamanoDeArchivo = file.size;
                console.log("tamanoDeArchivo: " + tamanoDeArchivo);
                console.log("extension: " + extension);   
            });        	
        	$("#botonCargaListaDistribucionMasivaModal").off('click').on('click', function () {
            	var validaTamanoDeArchivo = true;
            	var validaExtension = true;
        		if ($('#nombreArchivoSpanDocumentoAdicionalModal').text() == "Seleccionar archivo") {
        			$("#mensajesDeErrorListaDistribucionMasivaModal").validationEngine('showPrompt', '* Este campo es obligatorio', 'error');			
        		}  
        		if (tamanoDeArchivo<=0) {
	            	validaTamanoDeArchivo = false;
	            	$("#mensajesDeErrorListaDistribucionMasivaModal").validationEngine('showPrompt', '* El tamao del archivo debe ser mayor a 0 bytes', 'error');
		        }   
        		if (!allowedExtensionsRegx.test(extension)) {
        			validaExtension = false;
	            	$("#mensajesDeErrorListaDistribucionMasivaModal").validationEngine('showPrompt', '* Solo se permiten archivos con extensi&oacute;n xls', 'error');
		        } 
        		if (validaTamanoDeArchivo && validaExtension
        				&& $('#nombreArchivoSpanListaDistribucionMasivaModal').text() != "Seleccionar archivo"
                			&& $('#nombreArchivoSpanListaDistribucionMasivaModal').text() != "") {
        			data.submit();
            	}  
            });
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);             
            if (sacar == 0){
		             notify = $.notify('<strong>Guardando</strong> No cerrar esta pagina...', {
		            	type: 'success',
		            	allow_dismiss: false,
		            	showProgressbar: true
		            });
		       sacar = 1;      
            }
            if (progress == 100){
                notify.update({'type': 'success', 'message': ' Ha terminado la carga!', 'progress': progress});
                sacar = 0;
            }else{
                notify.update({'type': 'success', 'message': ' <strong>Guardando</strong> No cerrar esta pagina ... ', 'progress': progress});
            }            
        },
        error : function(e) {
			console.log("ERROR: ", e);			
		},
		complete : function(data){
        	console.log("data" + data);
       	 	console.log("data.toString()" + data.toString());
       	 	console.log("data.responseJSON: ", data.responseJSON);  
       	 	if (data.responseJSON.cssStatus == "alert alert-warning") {
				$('#cargaListaDistribucionMasivaModal').modal('hide');
				$.notify({
		   			message: data.responseJSON.mensaje
		   		},{
		   			type: 'warning'
		   		});
			} else if (data.responseJSON.cssStatus == "alert alert-danger") {
         		 setTimeout(function() {
         	        notify.close();
         	    }, 4500);
        		$.notify({
         			message: data.responseJSON.mensaje
         		},{
         			type: 'danger'
         		});	
		   	} else {
				document.getElementById('formCargaListaDistribucionMasivaModal').reset();
				$.notify({
		   			message: 'Se han cargado correctamente los datos!'
		   		},{
		   			type: 'success'
		   		});	
				$("#listaDeDistribucionCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
				$("#listaDeDistribucionCuerpo").load(urlCargaListaDeDistribucion,
                         function() {
                             $("#listaDeDistribucionCuerpo").find(".cargando").remove();                			                       
                });	
				const resultados = data.responseJSON.listaDeDistribucionDTOResultadoCargaMasiva;
				resultados.forEach(resultado => {
				  console.log(resultado);
				});
				const keys = Object.keys(resultados[0]);
				const excludedFields = ["idListaDeDistribucion", "respuesta", "tipoDeDestinatarioDTO", "idTipoDestinatario", 
					"motivo", "fechaInicioVigencia", "fechaFinVigencia"];
				const filteredKeys = keys.filter(function(key) {
				  return !excludedFields.includes(key);
				});
				const header = filteredKeys.join(',');
				const rows = resultados.map(function(resultado) {
				  const filteredValues = Object.values(resultado).filter(function(value, index) {
				    return !excludedFields.includes(keys[index]);
				  });
				  return filteredValues.join(',');
				});
				const csvContent = [header, ...rows].join('\n');
				const csvData = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csvContent);
				const linkElement = document.createElement('a');
				linkElement.setAttribute('href', csvData);
				linkElement.setAttribute('download', 'resultado_carga.csv');
				linkElement.click();
		   	}
			$('#cargaListaDistribucionMasivaModal').modal('hide');	
        }
  	});
};

$(document).ready(function() {
	$(inicializaCargaListaDistribucionMasivaModal);	
});

</script>
/**
 * 
 */
function subirArhivoModal() {	
	
	var validaForm = $("#formSubirArhivo").validationEngine('validate');
	console.log("validaForm: " + validaForm);
	
	if (!validaForm) {
		return;
	}
	
	$('#subirArchivoModal').modal('hide');
	
	var dialog = bootbox.dialog({	   
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Archivo...</p>'	   
	});	
	
	$('#botonSubirArchivo').addClass('disabled');
	
	console.log("En subirArhivoModal");
	
	var form = $('#formSubirArhivo')[0]; 
	var formData = new FormData(form);
	
	var idInstanciaDeTareaSubirArchivo = $("#idInstanciaDeTareaSubirArchivo").val();
	console.log("idInstanciaDeTareaSubirArchivo: " + idInstanciaDeTareaSubirArchivo);	
	
	var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTareaSubirArchivo+"&muestraSoloDocumentosDeSalida=true";
	var urlSubirArchivo = $("#urlSubirArchivo").val();
	
	console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
	
	console.log("urlSubirArchivo: " + urlSubirArchivo);
	
	  $.ajax({
	    url: urlSubirArchivo,
	    type: 'POST',
	    data: formData,
	    async: true,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returnData) {
	    	console.log("SUCCESS -- subirArhivoModal: ", returnData);
	    	 
	    },
	    error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData){
			console.log("COMPLETE -- subirArhivoModal: ", returnData.responseJSON);			
			$('#divDetalleDeTarea').each(function(){        	 
		        $(this).fadeOut("slow").load(urlGetDetalleDeTarea, function(){
		        		//formatTablaDocumentos();	
		        		//formatTablaHistorialDelProceso();
		        }).fadeIn('slow');
		    }); 
			dialog.find('.bootbox-body').html(returnData.responseJSON.resultadoSubirArchivo);	
			$('#botonSubirArchivo').removeClass('disabled');
		}
	  });
}
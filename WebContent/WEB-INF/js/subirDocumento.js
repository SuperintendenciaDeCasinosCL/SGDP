/**
 * 
 */
function subirDocumentoModal() {
	
	var validaForm = $("#formSubirDocumento").validationEngine('validate');
	console.log("validaForm: " + validaForm);
	
	if (!validaForm) {
		return;
	}
	
	var dialog = bootbox.dialog({	   
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Documentos...</p>',
	    onEscape: function () {
            $('#subirDocumentoModal').modal('hide');
        }
	});	
	
	console.log("En subirDocumentoModal");
	
	var urlSubirDocumento = $('#urlSubirDocumento').val();
	var urlGetInstanciasDeTarea = $("#urlGetInstanciasDeTarea").val();
	
	console.log("urlSubirDocumento: " + urlSubirDocumento);
	console.log("urlGetInstanciasDeTarea: " + urlGetInstanciasDeTarea);
	
	var form = $('#formSubirDocumento')[0]; 
	var formData = new FormData(form);
		 
	  $.ajax({
	    url: urlSubirDocumento,
	    type: 'POST',
	    data: formData,
	    async: true,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returnData) {
	    	console.log("SUCCESS -- subirDocumentoModal: ", returnData);	    	
	    },
	    error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData) {
			console.log("COMPLETE -- subirDocumentoModal: ", returnData.responseJSON);
			//Cargar Tareas
			$('#tareasBandejaDeEntrada').each(function(){        	 
			        $(this).fadeOut("slow").load(urlGetInstanciasDeTarea).fadeIn('slow');
			    });
			dialog.find('.bootbox-body').html(returnData.responseJSON.resultadoSubirDocumento);		
		}
	  });
}
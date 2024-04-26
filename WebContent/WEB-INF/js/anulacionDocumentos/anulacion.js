const idFieldFilterText = 'txt-code-expedient';
const idBtnSearch = 'btnSearch';

const idFieldMessageFilterText = 'error-code-expedient';

const idTableReport = 'tableReport';

const idDivModal = 'divModal';
const idTxtMotivo = "txtMotivo";
const idTxtId = "txtId";
const idErrorCode="error-code";
const idTxtGeneralError = "txt-general-error";

var table = null;

$(document).ready(function(){
    hideMesageErrorInit();
    table = configTableAnulacion();
    execLoadTableAnulacion();
    $('#'+idBtnSearch).on('click',function(){
        if(validateFieldsLoadTable()){
        	execLoadTableAnulacion();
        }
    });
});

function hideMesageErrorInit(){
	$('#'+idErrorCode).hide();
}

function configTableAnulacion(){
    var table = $('#'+idTableReport).DataTable({
         serverSide: true,
         lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
         ajax: {
              url: "getListDocumentoDataTable",
              type: "POST",
              data: function(d){ 
                  //d.dateFrom = $('#'+fieldDateFrom).val();
                  //d.dateTo = $('#'+fieldDateTo).val();
              }
         },
         columns:[
        	 {data:"codeExpediente"},
             {data:"nombreArchivo"},
             {data:"nombreTipoDocumento"},
             {data:"nombreTarea"},
             {data:"fechaSubida"},
             {
            	 data:"anulado",
            	 render: function(data, type){
            		var contenedor = $('<div/>');
            		var span = $('<span/>');
            		span.addClass('glyphicon font-icon-2');
            		if(data){
            			span.addClass('glyphicon-ban-circle font-icon-2 text-danger');
            		}else{
            			span.addClass('glyphicon-ok-circle text-success');
            		}
            		contenedor.append(span);
            		var text = contenedor.html()
                    return text;
            	} 
             },
             {
                 data:"idArchivo",
                 render: function(data, type){
                     var contenedor = $('<div/>');
                     var button = $('<button/>').html('ver');
                     button.attr('onClick', 'openModalAnulDocument("'+ data +'")');
                     contenedor.append(button);
                     var text = contenedor.html();
                     return text;
                 }
             }
         ]
    });
    
    $('.dataTables_filter').hide();
    return table;
}

function openModalAnulDocument(id) {
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  	$('#'+idTxtId).val(id);
	    		$('#'+idTxtMotivo).val('');
	    		$('#'+idTxtGeneralError).text('');
	    		$("#buttonAnularDocumento").removeClass("disabled");
	    		$("#spanAnularDocumento").removeClass("fa fa-spinner fa-spin");
	    		$("#spanAnularDocumento").removeClass("glyphicon glyphicon-floppy-disk text-lg-left");
	    		$("#spanAnularDocumento").addClass("glyphicon glyphicon-floppy-disk text-lg-left");
	    		$('#'+idDivModal).modal('show');
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_self');
	                          }
	             );
	      }
	});  
	
}

function anulDocument() {
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  var opt = null;
	    		if(validateFieldsAnul()){
	    			$("#buttonAnularDocumento").addClass("disabled");
	    			$("#spanAnularDocumento").removeClass("glyphicon glyphicon-floppy-disk text-lg-left");
	    			$("#spanAnularDocumento").addClass("fa fa-spinner fa-spin");
	    			console.log('anulando documento');
	    			var jsonData = {
	    				idArchivo: $('#'+idTxtId).val(),
	    				motivo: $('#'+idTxtMotivo).val()
	    			};
	    			opt = $.ajax({
	    		        url: "anular",
	    		        method: 'POST',
	    		        processData: false,
	    		        data: JSON.stringify(jsonData),
	    		        contentType: 'application/json'
	    		    });
	    			opt.done(function(data){
	    				console.log(data);
	    				$("#buttonAnularDocumento").removeClass("disabled");
	    				$("#spanAnularDocumento").removeClass("fa fa-spinner fa-spin");
	    				$("#spanAnularDocumento").removeClass("glyphicon glyphicon-floppy-disk text-lg-left");
	    				$("#spanAnularDocumento").addClass("glyphicon glyphicon-floppy-disk text-lg-left");
	    				if(data.code == 0){
	    					$('#'+idTxtGeneralError).text('');
	    					$('#'+idDivModal).modal('hide');
	    					execLoadTableAnulacion();
	    					bootbox.dialog({
	    	                    title: '',
	    	                    message: "<h5 style='text-align: center;'>" + data.message + "</h5>",
	    	                    size: 'Small'
	    	                    });
	    				}else{
	    					$('#'+idTxtGeneralError).text(data.code+' - '+data.message);
	    				}
	    			});
	    		}
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_self');
	                          }
	             );
	      }
	}); 
}

function execLoadTableAnulacion(){
	var text = $('#'+idFieldFilterText).val();
    table.search(text);
    table.draw();
}
function validateFieldsLoadTable(){
	return true;
}

function validateFieldsAnul(){
	var error = 0;
	var idArchivo = $('#'+idTxtId).val();
	if(idArchivo == null || idArchivo.length == 0){
		error++;
		$('#'+idTxtGeneralError).text('Id de archivo no encontrado');
	}else{
		$('#'+idTxtGeneralError).text('');
	}
	var motivo = $('#'+idTxtMotivo).val();
	if(motivo !=null && motivo.length > 0){
		$('#'+idErrorCode).hide();
	} else {
		error++;
		$('#'+idErrorCode).show();
	}
	if(error == 0){
		return true;
	}else{
		return false;
	}
}
/**
 * 
 */

/*$(function(){
	$("#botonEditaDocumento").click(editarDocumento);
});*/

/*$(document).ready(function() {
	$("#botonEditarDocumento").click(editarDocumento);
});*/
function editarDocumento(){

	var codigoMimeType = $("#botonEditarDocumento").attr("data-codigomimetype"); 
	var linkSharpoint = $("#botonEditarDocumento").attr("data-linksharpoint");
	
	console.log("codigoMimeType: " + codigoMimeType);
	console.log("linkSharpoint: " + linkSharpoint);
	
	if(!(window.ActiveXObject)) {			
		if(codigoMimeType==1) { //word
			var miwindow = window.open("ms-word:ofe|u|"+linkSharpoint,"_self");
			miwindow.locate;
		}
		else if (codigoMimeType==2) { //excel
			var miwindow = window.open("ms-excel:ofe|u|"+linkSharpoint,"_self");
			miwindow.locate;
		}
   } else {
	   if(codigoMimeType==1) { //word
			/*var miwindow = window.open("ms-word:ofe|u|"+linkSharpoint,"_self");
			miwindow.locate;*/
			var wdApp = new ActiveXObject("Word.Application");
			wdApp.Visible = true;
			var wdDoc = wdApp.Documents;
			wdDoc.Open(linkSharpoint);
		}
		else if (codigoMimeType==2) { //excel
			/*var miwindow = window.open("ms-excel:ofe|u|"+linkSharpoint,"_self");
			miwindow.locate;*/
			var wdApp = new ActiveXObject("Excel.Application");
			wdApp.Visible = true;
			var wb = wdApp.Workbooks;
			wb.Open(linkSharpoint);
		}
   }
	
}

function muestraEjecucionDeTareaDesdeApp() {	
	$("#divDetalleDeTareaDesdeAppContenido").removeClass('hide');	
}

function cargaDetalleDeTarea(nombreExpediente, idInstanciaDeTarea, muestraSoloDocumentosDeSalida, idExpediente, urlControl) {
	
	var sessionURL = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	
	console.log("sessionURL: " + sessionURL);
	console.log("raizURL: " + raizURL);
	
	$('#correosDeDistribucionHiden').empty();
	$('#idsArchivosADistribuir').empty();
	$("#validoFormDistribucion").val("");
	$('#idsArchivosADistribuir').empty();
	
	$.get(sessionURL, function(haySession){
    	if (haySession) {
    		
    		console.log("urlControl: " + urlControl);
    		
    		var urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda = $("#urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda").val()+"/"+idExpediente;
    		
    		var urlGetTablaHistorialDeDocumentoPorIdExpediente = $("#urlGetTablaHistorialDeDocumentoPorIdExpediente").val();
    		
    		console.log("urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda: " + urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda);
    		
    		 $("#divTabsDetalleDeTareaForm").addClass("hide");	 
    		 $("#divTablaHistorialDeDocumentosForm").empty();
    		 $("#divTablaHistorialDeInstanciaDeProcesoForm").empty();

    		 $("#divDetalleDeTarea").empty();
    		 $("#divTablaHistorialDeInstanciaDeProceso").empty();
    		 $("#divTablaHistorialDeDocumentos").empty();	

    		 $("#liTabDivDetalleDeTarea").addClass("active");
    		 $("#liTabHistorialDeTareas").removeClass("active");   
    		 $("#liTabHistorialDeDocumentos").removeClass("active");

    		 $("#tabDivDetalleDeTarea").addClass("active");
    		 $("#tabDivDetalleDeTarea").addClass("in");

    		 $("#tabDivHistorialDeTareas").removeClass("active");
    		 $("#tabDivHistorialDeTareas").removeClass("in");

    		 $("#tabDivHistorialDeDocumentos").removeClass("active");
    		 $("#tabDivHistorialDeDocumentos").removeClass("in");
    		
    		if (urlControl!=null && urlControl!="") {
    					
    			$('#tablaTareas').DataTable().rows('.selected').nodes().to$().removeClass('selected');			
    			$("#"+idInstanciaDeTarea+"LinkProceso").closest('tr').toggleClass('selected');	
    			
    			urlControl = urlControl + "/" + idExpediente;
    			
    			var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTarea
    			+"&muestraSoloDocumentosDeSalida="+muestraSoloDocumentosDeSalida
    			+"&desdeApp="+true + "&urlControl=" + urlControl;

    			console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);		
    			
    			$("#divTabsDetalleDeTareaForm").removeClass("hide");

    	        $('#divDetalleDeTareaDesdeApp').each(function(){        	 
    		        $(this).fadeOut("slow").load(urlGetDetalleDeTarea, function() {
    		        }).fadeIn('slow');
    		    });
    			        
    		    var dialogForm = bootbox.dialog({
    			    message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Detalle De Tarea...</p>',
    			    closeButton: false
    			}); 	    
    		    
    		    $('#divTablaHistorialDeDocumentosForm').load(urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + idExpediente + "/" 
    					+ idInstanciaDeTarea + "/" + nombreExpediente , function(){
    		    	dialogForm.modal('hide');
    		    });
    		    
    		    $('#divTablaHistorialDeInstanciaDeProcesoForm').load(urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda, function(){
    		    	dialogForm.modal('hide');
    		    });
    			
    		} else {
    			
    			$('#tablaTareas').DataTable().rows('.selected').nodes().to$().removeClass('selected');	
    			
    			$("#"+idInstanciaDeTarea+"LinkProceso").closest('tr').toggleClass('selected');	
    			
    			var urlGetDetalleDeTarea = $("#urlGetDetalleDeTarea").val()+"?idInstanciaDeTarea="+idInstanciaDeTarea
    			+"&muestraSoloDocumentosDeSalida="+muestraSoloDocumentosDeSalida
    			+"&desdeApp="+false;

    			console.log("urlGetDetalleDeTarea: " + urlGetDetalleDeTarea);
    			
    		    var dialog = bootbox.dialog({
    			    message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Detalle De Tarea...</p>',
    			    closeButton: false
    			});
    		    
    		    $("#divTabsDetalleDeTarea").removeClass('hide');
    			
    		    $('#divDetalleDeTarea').fadeOut("slow").load(urlGetDetalleDeTarea, function(){
    	    		dialog.modal('hide');	        	
    	    		if ($("#divTabsDetalleDeTarea").length>0) {
    	    			$('html, body').animate({scrollTop: $("#divTabsDetalleDeTarea").offset().top}, 2000);
    	    		}
    		    }).fadeIn('slow');    		 
    		    
    		    $('#divTablaHistorialDeDocumentos').load(urlGetTablaHistorialDeDocumentoPorIdExpediente + "/" + idExpediente + "/" 
    					+ idInstanciaDeTarea + "/" + nombreExpediente, function(){
    		    	$( "#tabHistorialDeTareas" ).prepend( "<br>" );
    		    });
    		    
    		    $('#divTablaHistorialDeInstanciaDeProceso').load(urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda, function(){
    		    	$( "#tabHistorialDeDocumentos" ).prepend( "<br>" );
    		    });
    		    
    		}    		
    		
       	} else {
             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
             	, function(){ window.open(raizURL, '_blank');}
             );
       	}
	});

}

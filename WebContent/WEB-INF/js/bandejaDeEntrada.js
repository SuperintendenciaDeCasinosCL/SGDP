/**
 * 
 */

function cerrarElemento(idElemento) {
	$("#"+idElemento).remove();
}




$( function() {
    var availableTags = [
      "jriffo",
      "fmolins",
      "jpena"
    ];
    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    $( "#usuariosAsignados" )
      // don't navigate away from the field on tab when selecting an item
      .on( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( ", " );
          return false;
        }
      });
  } );


$(document).on('click', '#close-preview', function(){ 
    $('.image-preview').popover('hide');
    // Hover befor close the preview
    $('.image-preview').hover(
        function () {
           $('.image-preview').popover('show');
        }, 
         function () {
           $('.image-preview').popover('hide');
        }
    );    
});

$(function() {
    // Create the close button
    var closebtn = $('<button/>', {
        type:"button",
        text: 'x',
        id: 'close-preview',
        style: 'font-size: initial;',
    });
    closebtn.attr("class","close pull-right");
    // Set the popover default content
    $('.image-preview').popover({
        trigger:'manual',
        html:true,
        title: "<strong>Preview</strong>"+$(closebtn)[0].outerHTML,
        content: "There's no image",
        placement:'bottom'
    });
    // Clear event
    $('.image-preview-clear').click(function(){
        $('.image-preview').attr("data-content","").popover('hide');
        $('.image-preview-filename').val("");
        $('.image-preview-clear').hide();
        $('.image-preview-input input:file').val("");
        $(".image-preview-input-title").text("Browse"); 
    }); 
    // Create the preview image
    $(".image-preview-input input:file").change(function (){     
        var img = $('<img/>', {
            id: 'dynamic',
            width:250,
            height:200
        });      
        var file = this.files[0];
        var reader = new FileReader();
        // Set preview image into the popover data-content
        reader.onload = function (e) {
            $(".image-preview-input-title").text("Change");
            $(".image-preview-clear").show();
            $(".image-preview-filename").val(file.name);            
            img.attr('src', e.target.result);
            $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
        }        
        reader.readAsDataURL(file);
    });
    
      
});

$(document).ready(function() {

    	 $("#idAutor").select2({
			    theme: "bootstrap",
			    dropdownParent: $("#crearExpedienteModal"),
			    language: "es"
		  });
    	 
    	
 
    	 $("#idAutorSubirDocumentoExpediente").select2({
			    theme: "bootstrap",
			    dropdownParent: $("#subirDocumentoExpdienteModal"),
			    language: "es"
		  });
    	 
       	 $("#idTipoDeDocumentoSubirExpediente").select2({
			    theme: "bootstrap",
			    dropdownParent: $("#subirDocumentoExpdienteModal"),
			    language: "es"
		  });
       	 
       	 $("#idTipoDeDocumentoSubirAntecedente").select2({
			    theme: "bootstrap",
			    dropdownParent: $("#subirDocumentoAntecedenteModal"),
			    language: "es"
		  });
       	 
       	 $("#idAutorSubirDocumentoAntecedente").select2({
			    theme: "bootstrap",
			    dropdownParent: $("#subirDocumentoAntecedenteModal"),
			    language: "es"
		  });

});

/*Valida si fecha1 es menor que fecha2. Considera que las fecha tienen el formato DD/mm/YYYY*/
function validaFechaMenor(idFecha1, idFecha2, mensajeError) {
	var fecha1 = $("#"+idFecha1).val();
	var fecha2 = $("#"+idFecha2).val();
	console.log("validaFechaMayor; fecha1: , fecha2: " + fecha1 + ", " + fecha2);
	if(fecha1=='null' || fecha1=='' || fecha1=='undefined' ) {
		//$("#"+idFecha1).validationEngine('showPrompt', 'Fecha invalida', 'error');
		return false;
	} if (fecha2=='null' || fecha2=='' || fecha2=='undefined') {
		//$("#"+idFecha2).validationEngine('showPrompt', 'Fecha invalida', 'error');
		return false;
	} else {
		var fecha1Array = fecha1.split("/");
		var fecha2Array = fecha2.split("/");
		if (fecha1Array.length != 3 || fecha2Array.length != 3) {
			return false;
		} else {
			var nvaFecha1 = fecha1Array[1] + "/" + fecha1Array[0] + "/" + fecha1Array[2];
			var nvaFecha2 = fecha2Array[1] + "/" + fecha2Array[0] + "/" + fecha2Array[2];
			console.log("nvaFecha1: " + nvaFecha1);
			console.log("nvaFecha2: " + nvaFecha2);
			var nvaFecha1D = new Date(nvaFecha1);
			var nvaFecha2D = new Date(nvaFecha2);
			if (nvaFecha1D <= nvaFecha2D) {
				//$("#"+idFecha1).validationEngine('hide');
				return true;
			} else {
				$("#"+idFecha1).validationEngine('showPrompt', mensajeError, 'error');
				return false;
			}
		}
	}
}

function descargaArchivo(urlDescarga) {
	console.log("urlDescarga: " + urlDescarga);
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	console.log("urlSessionValida: " + urlSessionValida);
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  window.location.href = urlDescarga;
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_blank');
	                          }
	             );
	      }
	});  
}

function descargaArchivoPorIdYVersion(urlDescargaPorIdYVersion, idArchivo, versionLabel, versionMimeType) {
	console.log("urlDescargaPorIdYVersion: " + urlDescargaPorIdYVersion);	
	console.log("idArchivo: " + idArchivo);
	console.log("versionLabel: " + versionLabel);
	console.log("versionMimeType: " + versionMimeType);
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	var urlDescargaPorIdYVersionV = urlDescargaPorIdYVersion
									+"?idArchivo="+encodeURIComponent(idArchivo)
									+"&versionLabel="+encodeURIComponent(versionLabel)
									+"&versionMimeType="+encodeURIComponent(versionMimeType);	
	console.log("urlDescargaPorIdYVersionV: " + urlDescargaPorIdYVersionV);
	console.log("urlSessionValida: " + urlSessionValida);
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  window.location.href = urlDescargaPorIdYVersionV;
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_blank');
	                          }
	             );
	      }
	});  
}

function levenshteinDistance (s, t) {
    if (!s.length) return t.length;
    if (!t.length) return s.length;

    return Math.min(
        levenshteinDistance(s.substr(1), t) + 1,
        levenshteinDistance(t.substr(1), s) + 1,
        levenshteinDistance(s.substr(1), t.substr(1)) + (s[0] !== t[0] ? 1 : 0)
    ) + 1;
}

function cargaTareasEnEjecucion() {	
	var urlSessionValida = $("#urlSessionValida").val();
	var raizURL = $("#raizURL").val();
	$.get(urlSessionValida, function(haySession) {
	      console.log("haySession: " + haySession);
	      if(haySession) {
	    	  var urlGetTareasEnEjecucion = $("#urlGetTareasEnEjecucion").val();	    		
	    	  $("#tareasEnEjecucion").css("position",	"relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));	    		
	    	  $("#tareasEnEjecucion").load(urlGetTareasEnEjecucion, function() { $("#tareasEnEjecucion").find(".cargando").remove(); });
	      }	else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                          , function(){
	                                window.open(raizURL, '_blank');
	                          }
	             );
	      }
	}); 
}

function seteaBotonAbrirCorreoDeDistribucionModal() {
	if ($("#validoFormDistribucion").val()=="true" && $("#botonAbrirCorreoDeDistribucionModal").length) {
		$("#botonAbrirCorreoDeDistribucionModal").removeClass("btn-warning");
		$("#botonAbrirCorreoDeDistribucionModal").addClass("btn-primary");
		$("#estadoFormCorreoDeDistribucionModal").removeClass("glyphicon-ban-circle");
		$("#estadoFormCorreoDeDistribucionModal").addClass("glyphicon-ok-circle");
		if ($(".botonAbrirCorreoDeDistribucionModalformError").length) {
			$(".botonAbrirCorreoDeDistribucionModalformError").remove();
		}
	}
}	
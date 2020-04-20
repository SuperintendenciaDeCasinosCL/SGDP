<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="anadirAntecedenteTodaEtapaModal" role="dialog" style="display: none;">
    <div class="modal-dialog modal-lg">
     
      <!-- Modal content-->
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">X</button>
          <h4 class="modal-title">A&ntilde;adir Antecedentes</h4>
         </div>
           <div class="modal-body">
             <form id="formAdjuntarAnteTodaEtapa" name="formAdjuntarAnteTodaEtapa">
		          <div class="row">	 
						 <div class="col-sm-12"> 	  
							 <div class="table-responsive">
										<table 
											class="table table-striped table-bordered  AdjuntarAntecedentesTodaTabla tamanio-datatable ">
											<thead>
												<tr>
													<th>Nombre Documento</th>
													<th>Tipo Documento</th>
													<th>Eliminar</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
								</div>
			       		</div>	
						<br>
						
						<div class="col-sm-12"> 	
						   <div class="col-sm-6">		
							<span id="spanAdjuntarAntecedentesTodaEtapaFile" class="btn btn-success fileinput-button btn-file btn-verde-tarea">
				                    <i class="glyphicon glyphicon-plus"></i>
				                    <span>Seleccionar Documento ...</span>				                     
				                    <input id="adjuntarAntecedentesTodaEtapaFile" name="listaArchivoFile"
				                    	type="file" name="files[]" multiple="">
				             </span>
						   </div>  	
				
							<div class="col-sm-6 pull-right" style="text-align: right;">	
								<button type="button" id="botonAdjuntarAntecedentesTodaEtapa" disabled="disabled" class="btn btn-primary">
									Ingresar Documentos
							   </button>
							 </div>	  
						</div>	   
				 </div> 
		 </form>                
        </div>
      </div>
      
    </div>
  </div>
  
<script>

function trasformarTablaEnArreglo(){
	
	var anadirAntecedentesAdicionalesTablas = new Array();   
	$(".trTipoDocumentoTrasversal").each(function (colIndex, c) {
			 anadirAntecedentesAdicionalesTablas.push({					
				nombreDocumento : encodeURIComponent($(this).find(".nombreDocumentoTrasversal").text()),
				idTipoDocumento : $(this).find(".tipoDocumentoTrasversal").val(),	
				nombreTipoDocumento : encodeURIComponent($(this).find(".tipoDocumentoTrasversal>option:selected").text()),			
		});		
    }); 
    
     console.log(anadirAntecedentesAdicionalesTablas);   

     return anadirAntecedentesAdicionalesTablas;
}	


function validaQueNoTodosLosDocSeanAntecedentes() {
	var name = 'tipoDocumentoTrasversal';
	var mySelect = $('select[name=' + name + ']');
	var cantidadDeDocAntecedentes = 0;
	console.log("mySelect.length: " + mySelect.length);
	for (i=0; i<mySelect.length; i++) {
		console.log("mySelect[i].selectedOptions[0].text: " + mySelect[i].selectedOptions[0].text);
		if (mySelect[i].selectedOptions[0].text == 'Antecedente') {
			cantidadDeDocAntecedentes = cantidadDeDocAntecedentes + 1;
		}
	}
	if (cantidadDeDocAntecedentes == mySelect.length) {
		return false;
	} else {
		return true;
	}
}

function validaQueNoseRepitanLosTiposDeDoc() {
	let mySet = new Set();
	var name = 'tipoDocumentoTrasversal';
	var mySelect = $('select[name=' + name + ']');
	var cantidadDeDocQueNoSonAntecedentes = 0;
	console.log("mySelect.length: " + mySelect.length);
	//cuenta todos los tipos de documentos que no son Antecedentes
	for (i=0; i<mySelect.length; i++) {
		console.log("mySelect[i].selectedOptions[0].text: " + mySelect[i].selectedOptions[0].text);
		if (mySelect[i].selectedOptions[0].text != 'Antecedente') {
			cantidadDeDocQueNoSonAntecedentes = cantidadDeDocQueNoSonAntecedentes + 1;
		}
	}
	console.log("cantidadDeDocQueNoSonAntecedentes: " + cantidadDeDocQueNoSonAntecedentes);
	//Agregamos documentos al set
	for (i=0; i<mySelect.length; i++) {
		console.log("mySelect[i].selectedOptions[0].text: " + mySelect[i].selectedOptions[0].text);
		if (mySelect[i].selectedOptions[0].text != 'Antecedente') {
			mySet.add(mySelect[i].selectedOptions[0].text);
		}
	}
	console.log("mySet.size: " + mySet.size);
	if (cantidadDeDocQueNoSonAntecedentes!=mySet.size) {
		return false;
	} else {
		return true;
	}
}

var inicializaFileUploadAdjuntarAntecedenteEtapaModal = function(){

	var dialog = "";
	var nombreExpediente;
	var comboboxTipoDocumento;
	var contador = 0;  
    var formDataAdjuntarAntecedenteEtapa = new FormData(); 
	var notify = "";
	var sacar = 0;
	var flagRestablecerDocumento = 0;
	var flagRestablecerDocumentoFijo = 0;
	var contextPath = "${pageContext.request.contextPath}";
	var cantidadDeArchivosAnadidos = 0;
	var comboboxTipoDocumentoVariable;

	// Eliminar filas
	$('.AdjuntarAntecedentesTodaTabla').on('click', '.adjuntarAntecedentesTD', function () {
	     $(this).parent().parent().remove();
		 var validacion = trasformarTablaEnArreglo();
		 if (validacion.length == 0){
			 $("#botonAdjuntarAntecedentesTodaEtapa").attr('disabled', 'disabled');
              console.log("Entro a Validaci&oacute;n");
			} 
	});
		
 	$('#adjuntarAntecedentesTodaEtapaFile').fileupload({
  		dataType: 'json',
        url: contextPath + "/guardarAntecedenteTodaEtapa",
        autoUpload: false,    
        maxChunkSize: 50000000, // 50 MB    
        add: function (e, data) {  
            
        	$("#botonAdjuntarAntecedentesTodaEtapa").removeAttr("disabled");

           // Reinicializa los contadores cuando el contador llega a 0
        	$("#anadirAntecedenteTodaEtapaModal").on("hidden.bs.modal", function () {
        		flagRestablecerDocumentoFijo = 0;
        		contador = 0;
        		console.log("entro");
        	});


        	 if (flagRestablecerDocumentoFijo == 0){
        		 formDataAdjuntarAntecedenteEtapa.delete("listaArchivoFile2");
        		 formDataAdjuntarAntecedenteEtapa.delete("anadirAntecedentesTablaString");
        		 formDataAdjuntarAntecedenteEtapa.delete("anadirAntecedenteDTOString");
      			
      			 flagRestablecerDocumentoFijo = 1;
         	 }
           	 
        	
        	/*
        	var idExpediente = $(this).data("idexpediente");
     		var idInstanciaDeTarea = $(this).data("idinstanciatarea");     		
     		var emisor = $(this).data("emisor");
     		nombreExpediente = $(this).data("nombreexpediente");
     		flagRestablecerDocumento = $(this).data("flagrestablecerdocumento");
    		*/
    		
    		var idExpediente = $(this).attr("data-idexpediente");
     		var idInstanciaDeTarea = $(this).attr("data-idinstanciatarea");     		
     		var emisor = $(this).attr("data-emisor");
     		nombreExpediente = $(this).attr("data-nombreexpediente");
     		
     		flagRestablecerDocumento = $(this).attr("data-flagrestablecerdocumento");
     		
        	var anadirAntecedentesAdicionalesTablas = new Array();   	

        	anadirAntecedentesAdicionalesTablas = trasformarTablaEnArreglo();
       
            $.each(data.files, function (index, file) {
               var seEncuentraDocumento = false;
               var listaDocumentoRepetido = "";               
               console.log("cantidadDeArchivosAnadidos: " + cantidadDeArchivosAnadidos);
             	// ----------------------------------------------------
             	// Se valida que no se repita el nombre del documento
	             for (i = 0; i < anadirAntecedentesAdicionalesTablas.length; i++) {
	           		 console.log("anadirAntecedentesAdicionalesTablas : " +
	    	           		  anadirAntecedentesAdicionalesTablas[i].nombreDocumento);     

	           		 if (anadirAntecedentesAdicionalesTablas[i].nombreDocumento == file.name){
	           			 seEncuentraDocumento = true;

	           			// if (listaDocumentoRepetido == ""){
	           				listaDocumentoRepetido = file.name;
		           		// }else{
		           		//	listaDocumentoRepetido = listaDocumentoRepetido + " <br> " + file.name;
		 			    // }	           			 
		           	
			         }	           		      			
	           	 } 


                 // ----------------------------------------------------
 	             // Se agregan un arreglo de file  y todos los atributos que se mandan al control
 	             // ------------------------------------------------------
 	             if (seEncuentraDocumento == false) {

 	            		var create = '<select class="form-control m-b tipoDocumentoTrasversal validate[required]" name="tipoDocumentoTrasversal">';
		 	           	create += '<option value="">Seleccione Tipo Documento</option>';
		
		 	           	if (cantidadDeArchivosAnadidos > 0 ) {
		 	           		create += '<option value="46">Antecedente</option>';
		 	           	}
		
		 	           	const urlGetTiposDeDocumentosDTOPorNombreExpediente = $("#urlGetTiposDeDocumentosDTOPorNombreExpediente").val()+"/"+nombreExpediente;
		 	           	//console.log("urlGetTiposDeDocumentosDTOPorNombreExpediente: " + urlGetTiposDeDocumentosDTOPorNombreExpediente);
		 	           	$.get(urlGetTiposDeDocumentosDTOPorNombreExpediente, function(listaTipoDoc, status) {
		 	           	    console.log("listaTipoDoc: " + listaTipoDoc + "\nStatus: " + status);
		 	           	    listaTipoDoc.forEach(function (tipoDoc) {	        
		 	           	        //console.log(tipoDoc.nombreDeTipoDeDocumento);
		 	           	        create += '<option value="'+tipoDoc.idTipoDeDocumento +'">'
		 	           			 + tipoDoc.nombreDeTipoDeDocumento +'</option>';
		 	           	    
		 	           			 
		 	           		});
			 	           	$(".AdjuntarAntecedentesTodaTabla > tbody").
	            			append("<tr class='trTipoDocumentoTrasversal'><td><p class='nombreDocumentoTrasversal'>"+file.name+"</p></td>"
	                    			+ "<td>"+create+"</td>"
	                    			+ "<td><a href='#' class='btn btn-danger btn-sm boton-cerrar-expediente adjuntarAntecedentesTD' "
	                    			+ "title='Eliminar Fila'>"
									+ "<span class='glyphicon glyphicon-remove'></span>"
									+ "</a></td>"
	                    			+ "</tr>");   
	
	 		    			
	 	 	             	formDataAdjuntarAntecedenteEtapa.append("listaArchivoFile2", file , encodeURIComponent(file.name)); 
		 	           	});	

 	 	             	
						/*
 	 	             	if (cantidadDeArchivosAnadidos==0) {
 	 	             		comboboxTipoDocumentoVariable = generarComboBoxTipoDocumentoPorNombreExpediente(nombreExpediente, false);
 	 	 	             } else {
 	 	 	            	comboboxTipoDocumentoVariable = generarComboBoxTipoDocumentoPorNombreExpediente(nombreExpediente, true);
 	 	 	 	 	     }
	         
 	 	             	$(".AdjuntarAntecedentesTodaTabla > tbody").
            			append("<tr class='trTipoDocumentoTrasversal'><td><p class='nombreDocumentoTrasversal'>"+file.name+"</p></td>"
                    			+ "<td>"+comboboxTipoDocumentoVariable+"</td>"
                    			+ "<td><a href='#' class='btn btn-danger btn-sm boton-cerrar-expediente adjuntarAntecedentesTD' "
                    			+ "title='Eliminar Fila'>"
								+ "<span class='glyphicon glyphicon-remove'></span>"
								+ "</a></td>"
                    			+ "</tr>");   

 		    			
 	 	             	formDataAdjuntarAntecedenteEtapa.append("listaArchivoFile2", file , encodeURIComponent(file.name));  

 	 	             	*/
 	 	 	         
 	             }      
 	             // ------------------------------------------------------
 	      		 // Validaciones
 				 // ------------------------------------------------------
 	             if (listaDocumentoRepetido != ""){
 	            //	bootbox.alert("Los nombres de los siguientes documentos se encuentran repetidos : " + listaDocumentoRepetido);
	 	            	$.notify({
	 	            		title: '<strong>Archivo se encuentra repetido</strong>',
	 	            		message: listaDocumentoRepetido
	 	            	},{
	 	            		type: 'danger'
	 	            	});
 		            }
 	            // -------------------------------------------------  
 	            cantidadDeArchivosAnadidos = cantidadDeArchivosAnadidos + 1;
  	            
            });

      	         	   
          
       	 $("#botonAdjuntarAntecedentesTodaEtapa").off('click').on('click', function () {    
      
        	var validaFormAdjuntarAnteTodaEtapa = $("#formAdjuntarAnteTodaEtapa").validationEngine('validate');
    	    console.log("validaFormAdjuntarAnteTodaEtapa: " + validaFormAdjuntarAnteTodaEtapa);
    	
	    	if (!validaFormAdjuntarAnteTodaEtapa) {
	    		return;
	    	}

	    	var validaQueNoTodosLosDocSeanAnt = validaQueNoTodosLosDocSeanAntecedentes();
	    	console.log("validaQueNoTodosLosDocSeanAnt: " + validaQueNoTodosLosDocSeanAnt);
	    	if (validaQueNoTodosLosDocSeanAnt == false) {
	    		$.notify({
	    			message: 'Al menos un documento debe ser distinto a Antecedente'
				},{
					type: 'danger'
				});
		    	return;
		    }

			var validaQueNoseRepLosTiposDeDoc = validaQueNoseRepitanLosTiposDeDoc();
			console.log("validaQueNoseRepLosTiposDeDoc: " + validaQueNoseRepLosTiposDeDoc);
			if (validaQueNoseRepLosTiposDeDoc == false) {
	    		$.notify({
					message: 'Ya existe un documento del mismo tipo'
				},{
					type: 'danger'
				});
		    	return;
		    }

	    	bootbox.confirm({
	    	    message: "Est&aacute; seguro que desea Guardar?",
	    	    buttons: {
	    	        confirm: {
	    	            label: 'Si',
	    	            className: 'btn-success'
	    	        },
	    	        cancel: {
	    	            label: 'No',
	    	            className: 'btn-danger'
	    	        }
	    	    },
	    	    callback: function (result) {

	    	        if (result == true){
	 	         	   
	    	     		/*console.log("idExpediente: " + idExpediente);
	    	    		console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	    	    		console.log("emisor: " + emisor);
	    	    		console.log("nombreExpediente: " + nombreExpediente);*/

	    	            if (contador == 0){   		
	    		    		var anadirAntecedenteDTO = {};
	    		    		anadirAntecedenteDTO["idExpediente"] = idExpediente;
	    		    		anadirAntecedenteDTO["idInstanciaDeTarea"] = idInstanciaDeTarea;
	    		    		anadirAntecedenteDTO["emisor"] = emisor;
	    		    		
	    		    		formDataAdjuntarAntecedenteEtapa.append("anadirAntecedenteDTOString" , JSON.stringify(anadirAntecedenteDTO));  
	    		    		contador = 1;
	    	            }
	    	    		    		
	    	            formDataAdjuntarAntecedenteEtapa.append("anadirAntecedentesTablaString" , JSON.stringify(trasformarTablaEnArreglo()));      		
	    	            data.formData = formDataAdjuntarAntecedenteEtapa;
	    	            
	    	       		data.submit();
		    	     }
	    	        
	    	    }
	    	});
	    	

       	 });	
          
        	 
        },
        progressall: function (e, data) {
            /*
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
            */        
        },
        error : function(e) {
			console.log("ERROR: ", e);
			//$("#contenedorBEPrincipal").find(".cargando").remove();			
		},
		beforeSend: function(xhr) {
			//$("#contenedorBEPrincipal").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
			$('#subirDocumentoRequeridoModal').modal('hide');

			  dialog = bootbox.dialog({
                  message: '<p><i class="fa fa-spin fa-spinner"></i> Guardando Informaci&oacute;n</p>',
                    closeButton: false
              });
			
		}, 
		success: function (data) {
			
			$('#anadirAntecedenteTodaEtapaModal').modal('hide');
			
            var mensajeSalida = "";

            var cuerpo  = "";
			var Cabecera = "<h4>Expediente : " + nombreExpediente + "</h4> "
			+"	 <table class='table'>"
			+" 	<thead>                         "
			+" 	  <tr>                          "
			+" 		<th>Estado</th>          "
			+" 		<th>Descripci&oacute;n</th>           "
			+" 		<th>Nombre Archivo</th>              "
			+" 	  </tr>                         "
			+" 	</thead>                        "
			+" 	<tbody>                         ";


			var footer = " 	</tbody>        "
			+"   </table>                       ";
			
            
			for (i = 0; i < data.length; i++) {
			
				cuerpo = cuerpo 
				+" 	  <tr> "
				+" 		<td>"+ data[i].respuesta+ "</td>"
				+" 		<td>"+ data[i].codigoError + " </td>"
				+" 		<td>"+ data[i].nombreArchivo + "</td>"
				+" 	  </tr>                         ";			     				      
			}
			
			mensajeSalida = Cabecera + cuerpo + footer;
			bootbox.alert(mensajeSalida);

			   
		},
		complete : function(data){
        	console.log(data);
       	 	console.log("data.toString()" + data.toString());
       	 	console.log("data.responseJSON: ", data.responseJSON);
       	 	dialog.modal('hide');      	    
			// Setear variables
	       	contador = 0; 
	        flagRestablecerDocumentoFijo = 0;
	        formDataAdjuntarAntecedenteEtapa.delete("listaArchivoFile2");
   		 	formDataAdjuntarAntecedenteEtapa.delete("anadirAntecedentesTablaString");
   		 	formDataAdjuntarAntecedenteEtapa.delete("anadirAntecedenteDTOString");	   		
	   		nombreExpediente = "";	   		
	   		contador = 0; 
	   		notify = "";
	   		sacar = 0;
	   		flagRestablecerDocumento = 0;
	   		flagRestablecerDocumentoFijo = 0;	   	
	   		cantidadDeArchivosAnadidos = 0;	
	   		$("#adjuntarAntecedentesTodaEtapaFile").remove(); 
	   		$("#spanAdjuntarAntecedentesTodaEtapaFile").append('<input id="adjuntarAntecedentesTodaEtapaFile" name="listaArchivoFile" type="file" name="files[]" multiple="">');
	    	dialog.modal('hide'); 		       
        }
  	});
	
};

$(document).ready(function() {
	$(inicializaFileUploadAdjuntarAntecedenteEtapaModal);
});

 
 </script>
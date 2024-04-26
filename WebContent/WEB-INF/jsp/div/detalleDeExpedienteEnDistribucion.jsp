<%@ page import="cl.gob.scj.sgdp.config.Constantes"%>
<%@ page import= "cl.gob.scj.sgdp.tipos.ModuloType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="FORMATO_FECHA_FORM_HH_MM"
	value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM%>" />

<c:set var="enviar" value="enviar" />
<c:set var="finalizar" value="finalizar" />
<c:url value="/verificarSession" var="sessionURL" />
<!-- MIG -->
<html>
<head>		
			
<!-- Modal Subir Archivo-->

<c:import url="/WEB-INF/jsp/modals/subirDocumentoAdicional.jsp"></c:import>

</head>
<body>

	<div id="contenedorApiDoc">


		<div class="row">

			<div class="col-sm-12">

				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" onclick="reiniciarListaDist()" href="#tabCorreoDeDistribucion">Correo de Distribucion</a></li>
					<li>
					<li><a data-toggle="tab"  onclick = "getEntidad()"  href="#tabApiDoc" >Api DocDigital</a></li> 
					<li>
					<li><a data-toggle="tab"
						href="#">Oficina Virtual</a></li>
					<li>
				</ul>

			</div>

		</div>

		<div class="tab-content">

			<div id="tabCorreoDeDistribucion" class="tab-pane fade in active" >
			<br>
				<!-- MIG -->


				<form class="form-horizontal" id="formCorreoDeDistribucionModal">
					<!--<div class="form-group">	
		<label class="control-label col-sm-3" for="tipoDestinatario">Tipos de destinatarios:</label>		
		<div class="col-sm-9">		
			<select id="tipoDestinatario" class="form-control select2-tipo-destinatario" multiple="multiple" >
				<c:forEach items="${todosLostiposDeDestinatarios}" var="tipoDestinatario">								
			    	<option value="${tipoDestinatario.idTipoDestinatario}">${tipoDestinatario.nombreTipoDestinatario}</option>
				</c:forEach>    
	    	</select>		
		</div>	
	</div>-->

					<div class="form-group">
						<label class="control-label col-sm-3" for="correosDeDistribucion">Correos
							de distribuci&oacute;n:</label>
						<div id="correosDist" class="col-sm-9">
							<select id="correosDeDistribucion"
								class="form-control select2-correos-de-distribucion validate[required]"
								multiple="multiple">
								<c:forEach items="${listaDistribucion}"
									var="listaDeDistribucionDTO">
									<option value="${listaDeDistribucionDTO.email}">${listaDeDistribucionDTO.email}
										(${listaDeDistribucionDTO.organizacion} -
										${listaDeDistribucionDTO.cargo})</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3"
							id="asuntoCorreoDistribucionLabel" for="asuntoCorreoDistribucion">Asunto:
						</label>
						<div class="col-sm-9">
							<textarea class="form-control validate[required]"
								id="asuntoCorreoDistribucion" readonly>${instanciaDeProcesoDTO.nombreDeProceso} - ${instanciaDeProcesoDTO.asunto} - ${instanciaDeProcesoDTO.nombreExpediente}</textarea>
						</div>
						<div id="seleccionarArchivosDistribuicion"></div>
					</div>
				</form>				

				<div class="col-sm-12" style="padding-bottom: 10px;">

					<div class="pull-left">

						<button id="botonGuardaDatosDeDistribucion"
							onclick="guardaDatosDeDistribucion()" type="button"
							class="btn btn-labeled btn-primary">
							Guardar <span class="btn-label-default"><i
								class="glyphicon glyphicon-saved"></i></span>
						</button>

					</div>
					
					<div class="text-right">

						<button type="button" class="btn btn-labeled btn-primary" onclick="abreModalCreaRegistroListaDeDistribucionDetExp()">Nuevo Destinatario</button>

					</div>
					
				</div>

			</div>

			<div id="tabApiDoc" class="tab-pane fade">
				<div class="form-group" id="apiDocDistribucion">					
					<div id = "listaEntidades" class="col-sm-9"></div>
				</div>
			</div>

		</div>
			
			
		<div class="table-responsive col-sm-12">
		<div class="row">						
					<div class="col-sm-12">	
						<div class="pull-right">
							<button type="button" title="Subir Archivo"
								data-idexpediente="${instanciaDeTareaDTO.idExpediente}" 
								data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
								data-nombreexpediente="${instanciaDeTareaDTO.nombreExpediente}"
								id="botonSubirArchivoSolicitud" class="btn btn-success">
								<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir Archivo
							</button>
						</div>										
					</div>
				</div>
			
				<div id="documentos">
					<table id="tablaHistorialDelDocumentoEnDistribucion"
						class="table table-striped table-bordered table-fixed">
						<thead>
							<tr>
								<th>Documento (Tipo)</th>
								<th></th>
								<th>Seleccionar</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="archivoInstDeTareaDTO"
								items="${todosLosDocSubidos}">

								<tr>
									<td>${archivoInstDeTareaDTO.nombreArchivo}
										(${archivoInstDeTareaDTO.nombreDeTipoDeDocumento})</td>
									<td align="center">
										<button type="button" title="Descarga documento"
											class="btn btn-primary btn-xs"
											onclick='descargaArchivo("<c:url value='getArchivoPorId/${archivoInstDeTareaDTO.idArchivoCms}'/>"
														, "<%=ModuloType.DETALLE_DE_EXPEDIENTE_EN_DISTRIBUCION.getNombreModulo()%>" )'
											data-iddocumento="${archivoInstDeTareaDTO.idArchivoCms}">
											<span class="fa fa-download font-icon-2 "></span>
										</button>
									</td>
									<td>
										<div class="checkbox" id="checkBoxesADistribuir">
											<label> <input
												id="idArchivoADistribuir${archivoInstDeTareaDTO.idArchivoCms}"
												onclick="cargaIdArchivosADistribuir('idArchivoADistribuir${archivoInstDeTareaDTO.idArchivoCms}')"
												type="checkbox" class="id-archivos-a-distribuir"
												name="idArchivosADistribuir"
												value="${archivoInstDeTareaDTO.idArchivoCms}">
											</label>
										</div>
									</td>
								</tr>

							</c:forEach>

						</tbody>
					</table>
				</div>
		</div>
				
				
				
	</div>
	
</body>
</html>

<script>

function cargaTiposDeDestinatarios() {
	var urlGetAllTipoDeDestinatario = $('#urlGetAllTipoDeDestinatario').val();
	$('#tipoDestinatario').empty();
	$.ajax({
  	    url: urlGetAllTipoDeDestinatario,
  	    type: 'GET',
  	    async: true,
  	    cache: false,
  	    contentType: false,
  	    processData: false,
  	    success: function (returnData) {
  	    },
  	    error : function(e) {
  			$.notify({
     			message: 'Error al obtener los tipos de destinatarios'
     		},{
     			type: 'danger'
     		});			
  		},
  		done : function(e) {
  		},
  		complete : function(returnData) {
  			if (returnData.responseJSON.length > 0) {
  				$('#tipoDestinatario').append($('<option>').text('Seleccionar').attr('value', 'Seleccionar'));	
  				$.each(returnData.responseJSON, function(i, obj){
  					$('#tipoDestinatario').append($('<option>').text(obj.nombreTipoDestinatario).attr('value', obj.idTipoDestinatario));
	            });
	  		} 
  		}  		
  	});
}

function abreModalCreaRegistroListaDeDistribucionDetExp() {
	$.get("${sessionURL}", function(haySession){       
        if(haySession) {
        	$('#formCreaRegistroListaDeDistribucion').trigger("reset");
        	$('#creaRegistroListaDeDistribucionModal').modal( {backdrop: 'static', keyboard: false});
        	cargaTiposDeDestinatarios();
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

function getEntidad() {	
	$("#detalleDeExpedienteEnDistribucionModal").css("position", "relative").prepend($("<div />").addClass("cargando"));
	$.get("${sessionURL}", function(haySession){
	if (haySession) {
		console.log("getEntidades");
		$("#listaEntidades").load("${pageContext.request.contextPath}"+"/getEntidades", function() {
			$("#detalleDeExpedienteEnDistribucionModal").find(".cargando").remove();
			});
	} else {
        bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
        	, function(){ window.open('${raizURL}', '_self');
        	}
        );
  	}
	});
}


var inicializaBotonSubirArchivoSolicitud = function() {	
	$("#subirArchivoDist").val("distribucion");
	$('#botonSubirArchivoSolicitud').click(function() {
		$.get("${sessionURL}", function(haySession){
	    	if (haySession) {	    
	    		console.log("Boton subir archivo");
		    	var idExpediente = $("#botonAnadirDocumentoOpcional").attr("data-idexpediente");
		   		var idInstanciaDeTarea = $("#botonAnadirDocumentoOpcional").attr("data-idinstanciadetarea");
		   		var nombreExpediente = $("#botonAnadirDocumentoOpcional").attr("data-nombreexpediente");
		   		$("#botonSubirDocumentoAdicionalModal").attr('data-idexpediente', idExpediente);
		   		$("#botonSubirDocumentoAdicionalModal").attr('data-idinstanciadetarea', idInstanciaDeTarea);
		   		$("#botonSubirDocumentoAdicionalModal").attr('data-nombreexpediente', nombreExpediente);
		   		$("#nombreArchivoSpanDocumentoAdicionalModal" ).empty();       
		       	$('#nombreArchivoSpanDocumentoAdicionalModal').text("Seleccionar archivo");
		       	document.getElementById('formSubirDocumentoAdicionalModal').reset();
		       	$('.select2-subir-doc-adicional-modal').val('').change();
		       	$('.select2-subir-doc-adicional-modal-multiple').val('').change();
		       	$("#idTipoDeDocumentoAdicionalModal").val($('#idTipoDeDocumentoAdicionalModal option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();
		       	$('#idTipoDeDocumentoAdicionalModal').prop('disabled', 'disabled');
	    		$("#fechaDocumentoAdicionalModal").val(moment().format("DD/MM/YYYY"));
	    		$("#fechaRecepcionDocumentoAdicionalModal").val(moment().format("DD/MM/YYYY"));
		   		$("#subirDocumentoAdicionalModal").modal({backdrop: "static"});
		   		
// 		   		$("#tablaHistorialDelDocumentoEnDistribucion").load(urlGetDetalleDeExpedienteEnDistribucion + "/" + idExpediente + "/" 
// 	        			+ idInstanciaDeTarea + "/" + nombreExpediente);
	       } else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_self');}
	             );
	       }
		});
	});
	
	
};

function guardaDatosDeDistribucion() {
	$("#correosHiden").val("distCorreo");
	console.log("guardar distCorreo: "+$("#correosHiden").val());
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
		//$('#correoDeDistribucionModal').modal('hide');	
		$("#correoDeDistribucionModal .close").click();
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

/*var inicializaSelect2TipoDestinatario = function(){
	$(".select2-tipo-destinatario").select2({
		    theme: "bootstrap",
 		    dropdownParent: $("#formCorreoDeDistribucionModal"),
 		    language: "es"
 	});	
	$('#tipoDestinatario ').on('select2:select', function (e) {
	    var idTipoDestinatario = e.params.data.id;
	    console.log(idTipoDestinatario);
	    $.get( "${pageContext.request.contextPath}" + "/getListaDistribucionPorIdTipoDestinatario/" + idTipoDestinatario , function(res) {
	    	console.log(res);
	    	const emails = [];
	    	if ($('#correosDeDistribucion').val()!=null) {
	    		emails.push(...$('#correosDeDistribucion').val());
	    	}	    	
	    	for (i = 0; i < res.length; i++) {
	    		console.log(res[i]);
	    		console.log(res[i].email); 
	    		emails.push(res[i].email);   
	    	}
	    	console.log("emails: " + emails);
    		$('#correosDeDistribucion').val(emails);
    		$('#correosDeDistribucion').trigger('change');		    
	    } );    
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


function reiniciarListaDist() {
	$("#detalleDeExpedienteEnDistribucionModal").css("position", "relative").prepend($("<div />").addClass("cargando"));
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
        	
        	$('#correosDist').load("${pageContext.request.contextPath}"+ "/getListaDistribucionCorreo", function() {
				$("#detalleDeExpedienteEnDistribucionModal").find(".cargando").remove();
				});	
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  });		
}


$(document).ready(function(){
	$(formatTablaHistorialDelDocumentoEnDistribucion);
	$(inicializaSelect2CorreosDeDistribucion);	
	$(inicializaBotonSubirArchivoSolicitud);
	
});

</script>


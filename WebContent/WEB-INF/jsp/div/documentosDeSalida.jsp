<%@ page import="cl.gob.scj.sgdp.tipos.PermisoType"%>
<%@ page import= "cl.gob.scj.sgdp.tipos.ModuloType" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="permisoModificaArchivos"
	value="<%=PermisoType.MODIFICA_ARCHIVOS.getNombrePermiso()%>" />

<c:set var="permisoPuedeVisarDocumento"
	value="<%=PermisoType.PUEDE_VISAR_DOCUMENTO.getNombrePermiso()%>" />

<c:set var="permisoPuedeFirmarConFEA"
	value="<%=PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso()%>" />

<c:set var="permisoPuedeFirmarConApplet"
	value="<%=PermisoType.PUEDE_FIRMAR_CON_APPLET.getNombrePermiso()%>" />

<div class="table-responsive">
	<table id="tablaDocumentosDeSalida"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Documentos</th>
				<th>Acci&oacute;n</th>
				<th>Documento Subido</th>
				<th>Opci&oacute;n</th>
				<c:if
					test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento 
                   		and instanciaDeTareaDTO.puedeVisarDocumentos eq true}">
					<th>Status Visaci&oacute;n</th>
				</c:if>
				<c:if
					test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                   		and instanciaDeTareaDTO.puedeAplicarFEA eq true}">
					<th>Status Firma Avanzada</th>
				</c:if>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${archivosInfoDTODeSalidaPorIdInstanciaDeTarea}"
				var="archivoInfoDTO">
				<tr>
					<td>${archivoInfoDTO.tipoDeDocumento}</td>
					<td align="center"><c:if
							test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento 
                              		and instanciaDeTareaDTO.puedeVisarDocumentos eq true
                              		and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                              		and archivoInfoDTO.esVisable eq true}">
							<a id="botonVisarDocumento" type="button"
								class="btn btn-success btn-sm" data-toggle="tooltip"
								title="Visar documento"
								onclick="visarDocumento('${archivoInfoDTO.idArchivo}', ${archivoInfoDTO.esVisable}, 
       								${archivoInfoDTO.aplicaFEA}, ${archivoInfoDTO.aplicaFirmaApplet}, '${archivoInfoDTO.idExpediente}', 
       								'${archivoInfoDTO.nombre}', '${archivoInfoDTO.mimeType}', '${instanciaDeTareaDTO.idInstanciaDeTarea}',
       								'${instanciaDeTareaDTO.nombreExpediente}',
       								${archivoInfoDTO.idTipoDeDocumento})">
								<span class="glyphicon glyphicon-thumbs-up font-icon-2"></span>
							</a>
						</c:if> <c:if
							test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                              		and instanciaDeTareaDTO.puedeAplicarFEA eq true
                        			and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                        			and archivoInfoDTO.aplicaFEA eq true}">
							<a id="botonFEA" type="button" class="btn btn-primary btn-sm"
								data-toggle="tooltip" title="Firma centralizada"
								onclick="cargaModalFEA(
                                    	 '${archivoInfoDTO.idExpediente}',
                                    	 '${archivoInfoDTO.idArchivo}',
                                    	 '${archivoInfoDTO.nombre}',
                                    	 '${archivoInfoDTO.mimeType}',
                                    	 '${archivoInfoDTO.idTipoDeDocumento}',
                                    	 '${archivoInfoDTO.tipoDeDocumento}',
                                    	 '${archivoInfoDTO.cdr}',
                                    	 '${archivoInfoDTO.numeroDocumento}',
                                    	 '${archivoInfoDTO.cartaRelacionada}',
                                    	 '${archivoInfoDTO.emisor}',
                                    	 '${instanciaDeTareaDTO.idInstanciaDeTarea}',
                                    	 '${instanciaDeTareaDTO.nombreExpediente}'
                                         )">
								<span class="glyphicon glyphicon-qrcode font-icon-2"></span>
							</a>
							
						</c:if> <c:if
							test="${permisos[permisoPuedeFirmarConApplet] eq permisoPuedeFirmarConApplet 
                        			and instanciaDeTareaDTO.puedeAplicarFEA eq true
                        			and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                        			and archivoInfoDTO.aplicaFirmaApplet eq true}">

							<%-- <a	data-toggle="tooltip" title="Firma con token"
	                        		onclick="firmarConToken('<c:url value="/firmaApplets/${archivoInfoDTO.idArchivo}/${archivoInfoDTO.nombre}/${archivoInfoDTO.idExpediente}/${archivoInfoDTO.idTipoDeDocumento}/${instanciaDeTareaDTO.idInstanciaDeTarea}"/>',
	                        			'${archivoInfoDTO.idArchivo}')"
	                              href='<c:url value="/firmaApplets/${archivoInfoDTO.idArchivo}/${archivoInfoDTO.nombre}/${archivoInfoDTO.idExpediente}/${archivoInfoDTO.idTipoDeDocumento}/${instanciaDeTareaDTO.idInstanciaDeTarea}"/>'
	                              class="btn btn-primary btn-sm"> 
	                              <span class="glyphicon glyphicon-modal-window font-icon-2"></span>
	                        </a>

							<a data-toggle="tooltip" title="Firma con token"
								onclick="firmarConToken('<c:url value="/firmaApplets/${archivoInfoDTO.idArchivo}/${archivoInfoDTO.nombre}/${archivoInfoDTO.idExpediente}/${archivoInfoDTO.idTipoDeDocumento}/${instanciaDeTareaDTO.idInstanciaDeTarea}"/>',
	                        			'${archivoInfoDTO.idArchivo}', ${instanciaDeTareaDTO.idInstanciaDeTarea}, ${archivoInfoDTO.idTipoDeDocumento})"
								class="btn btn-primary btn-sm"> <span
								class="glyphicon glyphicon-modal-window font-icon-2"></span>
							</a> --%>

							<a id="linkFirmarConTokenHidden" href="#" style="display: none;"></a>

						</c:if> <a href="#"
						class="btn btn-primary btn-sm link-subir-documento-requerido"
						id="linkSubirDocumentoRequerido" data-toggle="tooltip"
						title="Subir documento requerido"
						data-nombredocumentorequerido="${archivoInfoDTO.nombre}"
						data-idexpediente="${archivoInfoDTO.idExpediente}"
						data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
						data-idarchivo="${archivoInfoDTO.idArchivo}"
						data-fechaderecepcion="${archivoInfoDTO.fechaDeRecepcion}"
						data-idtipodedocumento="${archivoInfoDTO.idTipoDeDocumento}"
						data-nombretipodocumento="${archivoInfoDTO.tipoDeDocumento}"
						data-nombreexpediente="${instanciaDeTareaDTO.nombreExpediente}"
						data-puedevisardocumentos="${instanciaDeTareaDTO.puedeVisarDocumentos}"
						data-puedeaplicarfea="${instanciaDeTareaDTO.puedeAplicarFEA}"
						data-permisoPuedeFirmarConFEAV1="${permisos[permisoPuedeFirmarConFEA]}"
						data-permisoPuedeFirmarConFEAv2="${permisoPuedeFirmarConFEA}"
						data-aplicaFEA="${archivoInfoDTO.aplicaFEAPorTipoDeDocumento}"
						data-aplicavisacionportipodedocumento="${archivoInfoDTO.aplicaVisacionPorTipoDeDocumento}"
						data-permisopuedevisardocumento="${permisos[permisoPuedeVisarDocumento]}"
						data-nombrepermisopuedevisardocumento="${permisoPuedeVisarDocumento}"
						data-asignarNumeroDocumento="${instanciaDeTareaDTO.asignaNumDoc}">
							<span class="glyphicon glyphicon-upload font-icon-2"></span>
					</a>
					<c:if test="${!empty archivoInfoDTO.nombre and archivoInfoDTO.puedeVerPorRol eq true and archivoInfoDTO.puedeVerPorUsuario eq true and archivoInfoDTO.subidoALaTareaPorElUsuario eq true}">
						<a href="#" class="btn btn-warning btn-sm" id="btnConfidencialida" 
							onclick="abrirModalConfidencial('${archivoInfoDTO.idArchivo}')"
							data-toggle="tooltip" title="Modificar confidencialidad del documento">
							<span class="glyphicon glyphicon-lock font-icon-2"></span>
						</a>
					</c:if>
					</td>
					<td><c:choose>
							<c:when
								test="${!empty archivoInfoDTO.nombre 
									and (archivoInfoDTO.puedeVerPorRol eq false 
									or archivoInfoDTO.puedeVerPorUsuario eq false)}">
									${archivoInfoDTO.nombre} 
							</c:when>
							<c:when
								test="${!empty archivoInfoDTO.nombre and archivoInfoDTO.subidoALaTareaPorElUsuario eq true}">
								<a href='#'
									onclick='descargaArchivo("<c:url value='getArchivoPorId/${archivoInfoDTO.idArchivo}'/>", 
											"<%=ModuloType.EJECUCION_DE_TAREA.getNombreModulo()%>")'>
									${archivoInfoDTO.nombre} </a>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info">
									<strong>sin documento!</strong>
								</div>
							</c:otherwise>
						</c:choose></td>

					<td><c:if
							test="${archivoInfoDTO.puedeVerPorUsuario eq true 
									or archivoInfoDTO.puedeVerPorRol eq true 
									or archivoInfoDTO.subidoALaTareaPorElUsuario eq true}">
							<a href="#"
								class="btn btn-primary btn-sm link-adjuntar-documento"
								id="linkAdjuntarDocumento" data-toggle="tooltip"
								title="Añadir adjunto"
								data-nombredocumentodesalida="${archivoInfoDTO.nombre}"
								data-idexpediente="${archivoInfoDTO.idExpediente}"
								data-idinstanciadetarea="${instanciaDeTareaDTO.idInstanciaDeTarea}"
								data-idarchivo="${archivoInfoDTO.idArchivo}"
								data-fechaderecepcion="${archivoInfoDTO.fechaDeRecepcion}"
								data-nombreexpediente="${instanciaDeTareaDTO.nombreExpediente}"
								data-emisor="${instanciaDeProcesoDTO.emisor}"> <span
								class="glyphicon glyphicon-paperclip font-icon-2"></span>
							</a>
						</c:if> <c:if 
						test="${(archivoInfoDTO.esEditable eq true)	and (archivoInfoDTO.puedeVerPorUsuario eq true or archivoInfoDTO.puedeVerPorRol eq true
									or archivoInfoDTO.subidoALaTareaPorElUsuario eq true) }">

							<a href="#" class="btn btn-primary btn-sm boton-editar-documento"
								id="botonEditarDocumentoEnEjecucionTarea"
								title="Editar documento"
								data-idarchivo="${archivoInfoDTO.idArchivo}"
								data-codigomimetype="${archivoInfoDTO.codigoMimeType}"
								data-linksharpoint="${archivoInfoDTO.linkSharpoint}"> <span
								class="glyphicon glyphicon-edit font-icon-2"></span>
							</a>

						</c:if></td>

					<%-- Visacion --%>
					<c:choose>
						<c:when
							test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento 
                             		and instanciaDeTareaDTO.puedeVisarDocumentos eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                             		and archivoInfoDTO.esVisable eq true
									and archivoInfoDTO.estaVisado eq true}">
							<td><span
								class="glyphicon glyphicon-ok-circle font-icon-2 text-success"
								title="Documento visado"></span></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento 
                             		and instanciaDeTareaDTO.puedeVisarDocumentos eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                             		and archivoInfoDTO.esVisable eq true
									and archivoInfoDTO.estaVisado eq false}">
							<td><span
								class="glyphicon glyphicon-ban-circle font-icon-2 text-danger"
								title="El documento no esta visado"></span></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento 
                             		and instanciaDeTareaDTO.puedeVisarDocumentos eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq false
                             		and archivoInfoDTO.esVisable eq true
									and archivoInfoDTO.estaVisado eq false}">
							<td><span
								class="glyphicon glyphicon-ban-circle font-icon-2 text-danger"
								title="El documento no esta visado"></span></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento 
                             		and instanciaDeTareaDTO.puedeVisarDocumentos eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq false
                             		and archivoInfoDTO.esVisable eq false
									and archivoInfoDTO.estaVisado eq false}">
							<td></td>
						</c:when>
					</c:choose>

					<%-- FEA Web Start FEA Centralizada --%>
					<c:choose>
						<c:when
							test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                             		and instanciaDeTareaDTO.puedeAplicarFEA eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                             		and archivoInfoDTO.aplicaFEA eq true
									and (archivoInfoDTO.estaFirmadoConFEAWebStart eq true or archivoInfoDTO.estaFirmadoConFEACentralizada eq true)}">
							<td><span
								class="glyphicon glyphicon-ok-circle font-icon-2 text-success"
								title="Documento firmado"></span></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                             		and instanciaDeTareaDTO.puedeAplicarFEA eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                             		and archivoInfoDTO.aplicaFEA eq true
									and archivoInfoDTO.estaFirmadoConFEAWebStart eq false
									and archivoInfoDTO.estaFirmadoConFEACentralizada eq false}">
							<td><span
								class="glyphicon glyphicon-ban-circle font-icon-2 text-danger"
								title="El documento no esta firmado"></span></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                             		and instanciaDeTareaDTO.puedeAplicarFEA eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq false
                             		and archivoInfoDTO.aplicaFEA eq true
									and archivoInfoDTO.estaFirmadoConFEAWebStart eq false
									and archivoInfoDTO.estaFirmadoConFEACentralizada eq false}">
							<td><span
								class="glyphicon glyphicon-ban-circle font-icon-2 text-danger"
								title="El documento no esta firmado"></span></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                             		and instanciaDeTareaDTO.puedeAplicarFEA eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq false
                             		and archivoInfoDTO.aplicaFEA eq false
									and archivoInfoDTO.estaFirmadoConFEAWebStart eq false
									and archivoInfoDTO.estaFirmadoConFEACentralizada eq false}">
							<td></td>
						</c:when>
						<c:when
							test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA 
                             		and instanciaDeTareaDTO.puedeAplicarFEA eq true
                             		and archivoInfoDTO.subidoALaTareaPorElUsuario eq true
                             		and archivoInfoDTO.aplicaFEA eq false
									and archivoInfoDTO.estaFirmadoConFEAWebStart eq false
									and archivoInfoDTO.estaFirmadoConFEACentralizada eq false}">
							<td></td>
						</c:when>
					</c:choose>

					<td align="center"><c:choose>
							<c:when
								test="${archivoInfoDTO.puedeVerPorRol eq false 
									and archivoInfoDTO.puedeVerPorUsuario eq false
									and archivoInfoDTO.subidoALaTareaPorElUsuario eq false}">
								<span
									class="glyphicon glyphicon-lock font-icon-2 text-success"></span>
							</c:when>
							<c:when
								test="${archivoInfoDTO.subidoALaTareaPorElUsuario eq true}">
								<span
									class="glyphicon glyphicon-ok-circle font-icon-2 text-success"></span>
							</c:when>
							<c:otherwise>
								<span
									class="glyphicon glyphicon-ban-circle font-icon-2 text-danger"></span>
							</c:otherwise>
						</c:choose></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>
var plantillaDeDocumento = null;

const subirArhivoTest = (pl) => {	


	var dialog = bootbox.dialog({	   
	    message: '<p><i class="fa fa-spin fa-spinner"></i> Cargando Archivo...</p>'	   
	});	

	$('#subirPlantilla').addClass('disabled');
	

	
	var botonSubirDocumentoRequerido = $('.link-subir-documento-requerido');
	
	var formData = new FormData();
	var idExpediente = botonSubirDocumentoRequerido.attr("data-idexpediente");
	var idInstanciaDeTarea = botonSubirDocumentoRequerido.attr("data-idinstanciadetarea");
	var idTipoDeDocumento = botonSubirDocumentoRequerido.attr("data-idtipodedocumento");
	var nombreTipoDocumento = botonSubirDocumentoRequerido.attr("data-nombretipodocumento");
	var nombreExpediente = botonSubirDocumentoRequerido.attr("data-nombreexpediente");
	var puedeVisarDocumentos = botonSubirDocumentoRequerido.attr("data-puedevisardocumentos");
	var puedeAplicarFEA = botonSubirDocumentoRequerido.attr("data-puedeaplicarfea");	
	var permisoPuedeFirmarConFEAV1 = botonSubirDocumentoRequerido.attr("data-permisoPuedeFirmarConFEAV1");	
	var permisoPuedeFirmarConFEAv2 = botonSubirDocumentoRequerido.attr("data-permisoPuedeFirmarConFEAv2");	
	var aplicaFEA = botonSubirDocumentoRequerido.attr("data-aplicaFEA");	
	var puedeVisarDocumentos  = botonSubirDocumentoRequerido.attr("data-puedevisardocumentos");
	var aplicaVisacionPorTipoDeDocumento  = botonSubirDocumentoRequerido.attr("data-aplicavisacionportipodedocumento");
	var permisoPuedeVisarDocumento = botonSubirDocumentoRequerido.attr("data-permisopuedevisardocumento");
	var nombrePermisoPuedeVisarDocumento = botonSubirDocumentoRequerido.attr("data-nombrepermisopuedevisardocumento");
	var asignarNumeroDocumento = botonSubirDocumentoRequerido.attr("data-asignarNumeroDocumento");	   
	
	var dateObj = new Date();
	var month = dateObj.getUTCMonth() + 1; //months from 1-12
	var day = dateObj.getUTCDate();
	var year = dateObj.getUTCFullYear();

	const hoy = day + "/" + month + "/" + year;
	    		 			    	
	formData.append("idExpedienteSubirArchivo", idExpediente);
	formData.append("idTipoDeDocumentoSubir", idTipoDeDocumento);
	formData.append("idInstanciaDeTareaSubirArchivo", idInstanciaDeTarea);
	formData.append("cdr", "");
	formData.append("fechaCreacionArchivo", hoy);
	formData.append("fechaRecepcionArchivo", hoy);
	formData.append("idTags", null);
	formData.append("comentario", "");
	formData.append("tieneFirma", aplicaFEA);
	formData.append("convertirAPDF", false);
	formData.append("tipoDeDocumento", nombreTipoDocumento);
	formData.append("esRequerido",true);
	formData.append("asignarnumerodocumento", asignarNumeroDocumento);
	formData.append("validaInstanciaDeTareaEnBE", true);
	
	var blob = new Blob([pl], { type: "text/plain"});
	var file = new File([blob], 'plantilla-' + new Date().getTime() + '.txt', {type: "text/plain" });
	formData.append("archivo", file);
	
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
			console.log("ddDddddddDdddddddd ");			
			
			dialog.find('.bootbox-body').html(returnData.responseJSON.resultadoSubirArchivo);	
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(returnData){	
			console.log("COMPLETE -- subirArhivoModal: ", returnData.responseJSON);	
			
			$('#divDetalleDeTarea').each(function(){        	 
    		        $(this).fadeOut("slow").load(urlGetDetalleDeTarea, function() {
    		        }).fadeIn('slow');
    		    });
    		    
    		    		
		
		    
		    $('#subirDocumentoRequeridoModal').fadeOut('slow');
		    dialog.find('.bootbox-body').html(returnData.responseJSON.resultadoSubirArchivo);	
		    $('.modal-backdrop').remove();

			$('#subirPlantilla').removeClass('disabled');
			
		}
	  });
}

var dibujarPlantillaDeDocumento = () => {
	
	console.log({plantillaDeDocumento});
	
	const plantilla = JSON.parse(plantillaDeDocumento.plantilla);
	
	let html = plantilla.reduce((acc, field) => {
		const id = field.name.replaceAll(' ', '');
		acc = acc + '<div class="form-group">';
		acc = acc + '<label for="plantilla-'+id+'" class="control-label col-sm-3">' + field.name + '</label>';
		acc = acc + '<div class="col-sm-9">';
		if (field.type === 'number' || field.type === 'text' || field.type === 'date') {
			acc = acc + `<input form="formPlantilla" id="plantilla-`+id+`" name="plantilla-`+id+`" type="`+field.type+`" placeholder="`+field.desc+`"/>`;
		} else
		if (field.type === 'textArea') {
			acc = acc + `<textarea form="formPlantilla" id="plantilla-`+id+`" name="plantilla-`+id+`" rows="10" cols="50" placeholder="`+field.desc+`"></textarea>`;
		} else
		if (field.type === 'select') {
			const options = field.options.reduce((bcc, opt) => bcc + `<option value="`+opt+`">`+opt+`</option>`, '');
			acc = acc + `<select form="formPlantilla" id="plantilla-`+id+`" name="plantilla-`+id+`" >`+options+`</select>`;
		} else
		if (field.type === 'radio') {
			acc = acc + field.options.reduce((bcc, opt) => bcc + `<input form="formPlantilla" type="radio" id="plantilla-`+id+`" name="plantilla-`+id+`" value="`+opt+`"><label for="plantilla-`+id+`">`+opt+`</label><br>`, '');
		} else
		if (field.type === 'checkbox') {
			acc = acc + field.options.reduce((bcc, opt) => bcc + `<input form="formPlantilla" type="checkbox" id="plantilla-`+id+`" name="plantilla-`+id+`" value="`+opt+`"><label for="plantilla-`+id+`">`+opt+`</label><br>`, '');
		} else
		if (field.type === 'switch') {
			acc = acc + `<label for="plantilla-`+id+`" class="switch">`+field.name+`<input form="formPlantilla" type="checkbox" id="plantilla-`+id+`" name="plantilla-`+id+`"><span class="slider round"></span></label>`;
		}
		acc = acc + '</div></div>';
		return acc;
	}, '');
	
	if (html !== '') {
		html = html + '<div class="form-group">';
		html = html + '<div class="col-sm-12 text-center">'
		html = html + '<button id="subirPlantilla" class="btn btn-primary">Subir</button>';
		html = html + '</div>'
		html = html + '</div>'
		/*$("#divPlantillaDeDocumento").html(
		'<form id="formPlantillaDeDocumento">' +
			html +
		'</form>'
		);
		);*/
		$("#divPlantillaDeDocumento").html(html);

	}  else {
		html = '<span>Plantilla invalida</span>';
	}
	
	
	
	$("#subirPlantilla").click(function( event ) {
  		event.preventDefault();
  		const plantilla = JSON.stringify($("#formPlantilla").serializeArray());
		console.log({plantilla});
		subirArhivoTest(plantilla);

	})
}


var inicializaLinkAdjuntarDocumento = function(){
	$('.link-adjuntar-documento').click(function(){
		var botonAdjuntaDocumento = $(this);
		$.get("${sessionURL}", function(haySession){
	    	if (haySession) {
	    		var nombreDocumentoDeSalida = botonAdjuntaDocumento.attr("data-nombredocumentodesalida");
	    		var idExpediente = botonAdjuntaDocumento.attr("data-idexpediente");
	    		var idInstanciaDeTarea = botonAdjuntaDocumento.attr("data-idinstanciadetarea");	
	    		var fechaDeRecepcion = botonAdjuntaDocumento.attr("data-fechaderecepcion");		
	    		var emisor = botonAdjuntaDocumento.attr("data-emisor");	 
	    		$("#subirAdjuntoModalTitulo").empty();		
	    		$("#subirAdjuntoModalTitulo").append('Subir adjunto de ' + nombreDocumentoDeSalida);
	    		$("#botonSubirDocumentoAdjuntoModal").attr('data-idexpediente', idExpediente);
	    		$("#botonSubirDocumentoAdjuntoModal").attr('data-idinstanciadetarea', idInstanciaDeTarea);
	    		$("#botonSubirDocumentoAdjuntoModal").attr('data-cartarelacionada', nombreDocumentoDeSalida);
	    		$("#botonSubirDocumentoAdjuntoModal").attr('data-fechaderecepcion', fechaDeRecepcion);		
	    		$("#botonSubirDocumentoAdjuntoModalMas").attr('data-idexpediente', idExpediente);
	    		$("#botonSubirDocumentoAdjuntoModalMas").attr('data-idinstanciadetarea', idInstanciaDeTarea);
	    		$("#botonSubirDocumentoAdjuntoModalMas").attr('data-cartarelacionada', nombreDocumentoDeSalida);
	    		$("#botonSubirDocumentoAdjuntoModalMas").attr('data-fechaderecepcion', fechaDeRecepcion);
	    		document.getElementById('formSubirAdjuntoModal').reset();
	    		$('.select2-subir-adjunto').val('').change();
	    		$("#idTipoDeDocumentoSubirAdjuntoModal").val($('#idTipoDeDocumentoSubirAdjuntoModal option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();	    		
	    		$('#idTipoDeDocumentoSubirAdjuntoModal').prop('disabled', 'disabled');
	    		$('#nombreArchivoSubirAdjuntoModal').text("Seleccionar archivo");	

	    		
	    		$("#fechaDocumentoAdjuntoModal").val(moment().format("DD/MM/YYYY"));
               //Seleccione el combobox por el nombre del archivo
	    		$("#idAutorAdjuntoModal").val($('#idAutorAdjuntoModal option').filter(function () { return $(this).html() == emisor; }).val()).change();
	    		$(".select2-subir-adjunto").prop("disabled", true);
	    			
	    		$("#subirAdjuntoModal").modal({backdrop: "static"});	
	    		$('.select2-subir-adjunto-modal-multiple').val('').change();
	       	} else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci?n y hemos caducado tu sesi?n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_self');}
	             );
	       	}
		});
	});
};

var usuarios = [];
var roles = [];
var usuariosAsignados = [];
var rolesAsignados = [];

var abrirModalConfidencial = function (idArchivo) {

	$.get("${sessionURL}", function(haySession){
			if (haySession) {
				$("#asignarConfidencialidadModal").modal({backdrop: "static"});
				
				$("#idArchivoCmsInput").val(idArchivo);
				
				$.get('/sgdp/confidencialidadDocumento/' + idArchivo, function(data){
					usuarios = data.usuarios;
					roles = data.roles;
					usuariosAsignados = data.usuariosAsignados;
					rolesAsignados = data.rolesAsignados;
					
					$("#asignadorUsuarios").select2({
						theme: "bootstrap",
						dropdownParent: $("#contenedorFormulario"),
						language: "es",
						data: usuarios
					});
					
					$('#asignadorUsuarios').val(usuariosAsignados.map(it => it.usuario));
					$('#asignadorUsuarios').trigger('change'); 

					$("#asignadorRoles").select2({
						theme: "bootstrap",
						dropdownParent: $("#contenedorFormulario"),
						language: "es",
						data: roles.map(it => ({
							id: it.idRol,
							text: it.nombreRol
						}))
					});
					const rr = rolesAsignados.map(it => it.id);
					$('#asignadorRoles').val(rr);
					$('#asignadorRoles').trigger('change'); 
					

				});
			}	
		})
}

var inicializaLinkConfidencialidad = function () {
	$('#btnConfidencialidad').click(function () {
		console.log('abriendo modal confidevcial');
		$.get("${sessionURL}", function(haySession){
			console.log({haySession});
			if (haySession) {
				console.log('levantando el modal');
				$("#asignarConfidencialidadModal").modal({backdrop: "static"});
				
			}	
		})
	});
}

var inicializaLinkSubirDocumentoRequerido = function(){
	$('.link-subir-documento-requerido').click(function() {

		var botonSubirDocumentoRequerido = $(this);
		$.get("${sessionURL}", function(haySession){

	    	if (haySession) {		    	
	    		$("#divInputsSubirDocumentoRequeridoModal").addClass("hide");
	    		$("#divInputsSubirDocumentoRequeridoConAsignacionModal").addClass("hide");
	    		
	    		$('#seleccioneDocumentoRequeridoModal').empty();
	    		$('#seleccioneDocumentoRequeridoModal').append($('<option>').text('Seleccionar').attr('value', 'Seleccionar'));	    		
	    		var idExpediente = botonSubirDocumentoRequerido.attr("data-idexpediente");
	    		var idInstanciaDeTarea = botonSubirDocumentoRequerido.attr("data-idinstanciadetarea");
	    		var idTipoDeDocumento = botonSubirDocumentoRequerido.attr("data-idtipodedocumento");
	    		var nombreTipoDocumento = botonSubirDocumentoRequerido.attr("data-nombretipodocumento");
	    		var nombreExpediente = botonSubirDocumentoRequerido.attr("data-nombreexpediente");
	    		var puedeVisarDocumentos = botonSubirDocumentoRequerido.attr("data-puedevisardocumentos");
	    		var puedeAplicarFEA = botonSubirDocumentoRequerido.attr("data-puedeaplicarfea");	
	    		var permisoPuedeFirmarConFEAV1 = botonSubirDocumentoRequerido.attr("data-permisoPuedeFirmarConFEAV1");	
	    		var permisoPuedeFirmarConFEAv2 = botonSubirDocumentoRequerido.attr("data-permisoPuedeFirmarConFEAv2");	
	    		var aplicaFEA = botonSubirDocumentoRequerido.attr("data-aplicaFEA");	
	    		var puedeVisarDocumentos  = botonSubirDocumentoRequerido.attr("data-puedevisardocumentos");
	    		var aplicaVisacionPorTipoDeDocumento  = botonSubirDocumentoRequerido.attr("data-aplicavisacionportipodedocumento");
	    		var permisoPuedeVisarDocumento = botonSubirDocumentoRequerido.attr("data-permisopuedevisardocumento");
	    		var nombrePermisoPuedeVisarDocumento = botonSubirDocumentoRequerido.attr("data-nombrepermisopuedevisardocumento");
	    		var asignarNumeroDocumento = botonSubirDocumentoRequerido.attr("data-asignarNumeroDocumento");	    			    			
	    		    		
	            if (puedeAplicarFEA == "true" && permisoPuedeFirmarConFEAV1 == permisoPuedeFirmarConFEAv2 && aplicaFEA == "true" ) {
	            	  $(".checkboxFirma").removeClass("hidden");       	  
	            } else {      	
	            	   $(".checkboxFirma").addClass("hidden");   	    
	            }	

	    		if (asignarNumeroDocumento == "true"){
					$("#numeroDocumentoRequeridoModal").addClass("validate[required]");   	    
					$("#numeroDocumentoRequeridoLabel").text("N? Documento (*):");
					
				}else{
					$("#numeroDocumentoRequeridoModal").removeClass("validate[required]");   	    
					$("#numeroDocumentoRequeridoLabel").text("N? Documento:");
				}
	            
	                	
	    		$("#subirDocumentoRequeridoModalTitulo").empty();
	    		$("#subirDocumentoRequeridoModalTitulo").html("Subir " + nombreTipoDocumento);

	    		
	    		// Boton documento requerido nuevo
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-idexpediente', idExpediente);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-idinstanciadetarea', idInstanciaDeTarea);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-idTipoDeDocumentoRequeridoModal', idTipoDeDocumento);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-nombreexpediente', nombreExpediente);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-puedevisardocumentos', puedeVisarDocumentos);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-aplicavisacionportipodedocumento', aplicaVisacionPorTipoDeDocumento);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-permisopuedevisardocumento', permisoPuedeVisarDocumento);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-nombrepermisopuedevisardocumento', nombrePermisoPuedeVisarDocumento);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-puedeaplicarfea', puedeAplicarFEA);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-aplicafeaportipodedocumento', aplicaFEA);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-permisopuedefirmarconfea', permisoPuedeFirmarConFEAV1);
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-nombrepermisopuedefirmarconfea', permisoPuedeFirmarConFEAv2);	
	    		$("#botonSubirDocumentoRequeridoModal").attr('data-asignarNumeroDocumento', asignarNumeroDocumento);	

	    		// Boton documento requerido reutilizable

	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-idexpediente', idExpediente);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-idinstanciadetarea', idInstanciaDeTarea);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-idTipoDeDocumentoRequeridoModal', idTipoDeDocumento);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-nombreexpediente', nombreExpediente);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-puedevisardocumentos', puedeVisarDocumentos);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-aplicavisacionportipodedocumento', aplicaVisacionPorTipoDeDocumento);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-permisopuedevisardocumento', permisoPuedeVisarDocumento);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-nombrepermisopuedevisardocumento', nombrePermisoPuedeVisarDocumento);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-puedeaplicarfea', puedeAplicarFEA);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-aplicafeaportipodedocumento', aplicaFEA);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-permisopuedefirmarconfea', permisoPuedeFirmarConFEAV1);
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-nombrepermisopuedefirmarconfea', permisoPuedeFirmarConFEAv2);	
	    		$("#botonSubirDocumentoRequeridoConAsignacionModal").attr('data-asignarNumeroDocumento', asignarNumeroDocumento);		    		
	    					
	    		$("#botonSoloSubirDocumentoRequeridoModal").attr('data-idexpediente', idExpediente);
	    		$("#botonSoloSubirDocumentoRequeridoModal").attr('data-idinstanciadetarea', idInstanciaDeTarea);
	    		$("#botonSoloSubirDocumentoRequeridoModal").attr('data-idTipoDeDocumentoRequeridoModal', idTipoDeDocumento);	
	    		$("#botonSoloSubirDocumentoRequeridoModal").attr('data-nombreexpediente', nombreExpediente);	
	    		$("#botonSoloSubirDocumentoRequeridoModal").attr('data-nombretipodocumento', nombreTipoDocumento);

	    		$("#nombreArchivoSpanDocumentoRequeridoModal" ).empty();       
	        	$('#nombreArchivoSpanDocumentoRequeridoModal').text("Seleccionar archivo");

	        	// LLena la modificaci?n tipo documento nuevo
	        	$('#idTipoDeDocumentoRequeridoModal').val(idTipoDeDocumento).change();
	        	$('#idTipoDeDocumentoRequeridoModal').prop('disabled', 'disabled');
	        	
                // LLena la modificacion tipo de documento reutilizable
                $('#idTipoDeDocumentoRequeridoConAsignacionModal').val(idTipoDeDocumento).change();
	        	$('#idTipoDeDocumentoRequeridoConAsignacionModal').prop('disabled', 'disabled');
                	        	
	        	$("#tagsDocumentoRequeridoModal").val(null).trigger("change"); 
	        	$("#tagsDocumentoRequeridoConAsignacionModal").val(null).trigger("change");    
	        	   
	        	$('#tieneFirmaDocumentoRequeridoModal').attr('checked', false);	
	        	$("#documentoRequeridoModal").removeAttr("accept");
	    		var urlGetDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento = $("#urlGetDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento").val() 
	    			+ "/" + idExpediente 
	    			+ "/" + idTipoDeDocumento
	    			+ "/" + puedeVisarDocumentos
	    			+ "/" + puedeAplicarFEA
	    			+ "/" + idInstanciaDeTarea;
	    		console.log("urlGetDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento: " + urlGetDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento);
	    		
	    		$.ajax({
	    	  	    url: urlGetDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
	    	  	    type: 'GET',
	    	  	    async: true,
	    	  	    cache: false,
	    	  	    contentType: false,
	    	  	    processData: false,
	    	  	    success: function (returnData) {
	    	  	    	console.log("SUCCESS -- getDetalleDeArchivoDTO: ", returnData);	    	
	    	  	    },
	    	  	    error : function(e) {
	    	  			console.log("ERROR: ", e);
	    	  			$.notify({
	             			message: 'Error al obtener documentos requeridos: ' + returnData.responseJSON.resultadoRetrocedeProceso
	             		},{
	             			type: 'danger'
	             		});			
	    	  		},
	    	  		done : function(e) {
	    	  			console.log("DONE");
	    	  		},
	    	  		complete : function(returnData) {
	    	  			console.log("COMPLETE -- getDetalleDeArchivoDTO: ", returnData.responseJSON );
	    	  			console.log("returnData.responseJSON.length: " + returnData.responseJSON.length);	
	    	  			if (returnData.responseJSON.length > 0) {
	    	  				$.each(returnData.responseJSON, function(i, obj){
	    	  					$('#seleccioneDocumentoRequeridoModal').append($('<option>').text(obj.nombre + ' - versi?n ' + obj.labelUltimaVersion)
	    	    	  					.attr('title', '?ltima modificaci?n el ' + obj.fechaUltimaModificacionCompleta + ' por ' + obj.usuarioUltimaModificacion)
	    	    	  					.attr('value', obj.idArchivo).attr('data-convertirapdf', obj.convertirAPDF)
	    	    	  					.attr('data-asignarNumeroDocumento', asignarNumeroDocumento)
	    	    	  					.attr('data-nombre', obj.nombre))
	    		            });
	    		  		} 
	    	  			$('#seleccioneDocumentoRequeridoModal').append($('<option>').text('Subir nuevo...').attr('value', 'Subir nuevo...'));  
	    	  			$("#subirDocumentoRequeridoModal").modal({backdrop: "static"});	 
	    	  		}  		
	    	  	});	
	    	  	
	    	  	$.ajax({
						    	  	    url: '/sgdp/plantilla/' + idTipoDeDocumento,
						    	  	    type: 'GET',
						    	  	    async: true,
						    	  	    cache: false,
						    	  	    contentType: false,
						    	  	    processData: false,
						    	  	    success: function (returnData) {
						    	  	    	console.log("SUCCESS -- getPlantilla: ", returnData);	    	
						    	  	    },
						    	  	    error : function(e) {
						    	  			console.log("ERROR: getPlantilla ", e);
						    	  			$.notify({
						             			message: 'Error al obtener documentos requeridos: ' + returnData.responseJSON.resultadoRetrocedeProceso
						             		},{
						             			type: 'danger'
						             		});		
						             		plantillaDeDocumento = null;	
						    	  		},
						    	  		done : function(e) {
						    	  			//console.log("DONE");
						    	  		},
						    	  		complete : function(returnData) {
						    	  			if (returnData !== null && returnData.responseJSON && returnData.responseJSON.nombre) {
						    	  				$('#seleccioneDocumentoRequeridoModal').append($('<option>').text('PLANTILLA | ' + returnData.responseJSON.nombre + ' | ' + returnData.responseJSON.codigo).attr('value', 'plantilla'));  
						    	  				$("#subirDocumentoRequeridoModal").modal({backdrop: "static"});	
						    	  				plantillaDeDocumento = returnData.responseJSON;
						    	  			} else {
						    	  				plantillaDeDocumento = null;
						    	  			}
						    	  			
						    	  			
						    	  		}  		
						    	  	});	
	       	} else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci?n y hemos caducado tu sesi?n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_self');}
	             );
	       }
		});
	});
};
var inicializaChangeSeleccioneDocumentoRequeridoModal = function(){
	$( "#seleccioneDocumentoRequeridoModal" ).change(function() {

		var asignarNumeroDocumento = $("#seleccioneDocumentoRequeridoModal option:selected").data("asignarnumerodocumento");
		
    	 if ($('#seleccioneDocumentoRequeridoModal option:selected').val() == 'Subir nuevo...') {
			 $("#divInputsSubirDocumentoRequeridoModal").removeClass("hide");
			 $("#divBotonSoloSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divInputsSubirDocumentoRequeridoConAsignacionModal").addClass("hide");
			 $("#divPlantillaDeDocumento").addClass("hide");
			 $("#fechaDocumentoRequeridoModal").val(moment().format("DD/MM/YYYY"));
			 $("#fechaRecepcionDocumentoRequeridoModal").val(moment().format("DD/MM/YYYY"));	
			 
		 } else if ($('#seleccioneDocumentoRequeridoModal option:selected').val() != 'Seleccionar' && asignarNumeroDocumento == false) { // Este siempre tiene que ir en false
			 $("#divInputsSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divBotonSoloSubirDocumentoRequeridoModal").removeClass("hide");
			 $("#divInputsSubirDocumentoRequeridoConAsignacionModal").addClass("hide");
			 $("#divPlantillaDeDocumento").addClass("hide");
			 
		 } else if ($('#seleccioneDocumentoRequeridoModal option:selected').val() != 'Seleccionar' && asignarNumeroDocumento == true) { // Este Siempre tiene que ir en true
			 $("#divInputsSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divBotonSoloSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divInputsSubirDocumentoRequeridoConAsignacionModal").removeClass("hide");
			 $("#divPlantillaDeDocumento").addClass("hide");

			 $("#fechaDocumentoRequeridoConAsignacionModal").val(moment().format("DD/MM/YYYY"));
			 $("#fechaRecepcionDocumentoRequeridoConAsignacionModal").val(moment().format("DD/MM/YYYY"));	
		
		} else if ($('#seleccioneDocumentoRequeridoModal option:selected').val() == 'plantilla') { // para subir plantilla
		console.log("es plantilla")
			 $("#divInputsSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divBotonSoloSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divInputsSubirDocumentoRequeridoConAsignacionModal").addClass("hide");
			 
			 $("#divPlantillaDeDocumento").removeClass("hide");
			 
			 dibujarPlantillaDeDocumento();
			 
		 } else {
			 $("#divInputsSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divBotonSoloSubirDocumentoRequeridoModal").addClass("hide");
			 $("#divInputsSubirDocumentoRequeridoConAsignacionModal").addClass("hide");
			 $("#divPlantillaDeDocumento").addClass("hide");
		 }
   	});
};

var inicializaBotonEditarDocumentoEnEjecucionTarea = function(){
	$('.boton-editar-documento').click(function() {
		var codigoMimeType = $(this).attr("data-codigomimetype"); 
		var linkSharpoint = $(this).attr("data-linksharpoint");
		var idArchivo = $(this).attr("data-idarchivo");
		var raizURL = $("#raizURL").val();
		var urlPuedeVerDocumento = raizURL + "confidencialidadDocumento/puedeVerDocumento/" + idArchivo;
		var modulo = "<%=ModuloType.EJECUCION_DE_TAREA.getNombreModulo()%>";
		console.log("codigoMimeType: " + codigoMimeType);
		console.log("linkSharpoint: " + linkSharpoint);
		$.get("${sessionURL}", function(haySession) {
	    	if (haySession) {
	    		$.get(urlPuedeVerDocumento, function(puedeVerDocumento) {
		    		  console.log("puedeVerDocumento: " + puedeVerDocumento);
		    		  if (puedeVerDocumento == true) {
		    			  if(!(window.ActiveXObject)) {			
			  					if(codigoMimeType==1) { //word
			  						var miwindow = window.open("ms-word:ofe|u|"+linkSharpoint,"_self");
			  						miwindow.locate;
			  						insertaLogDocumento(idArchivo, "EDITA", modulo);
			  					}
			  					else if (codigoMimeType==2) { //excel
			  						var miwindow = window.open("ms-excel:ofe|u|"+linkSharpoint,"_self");
			  						miwindow.locate;
			  						insertaLogDocumento(idArchivo, "EDITA", modulo);
			  					}
			  			   } else {
			  				   if(codigoMimeType==1) { //word								
			  						var wdApp = new ActiveXObject("Word.Application");
			  						wdApp.Visible = true;
			  						var wdDoc = wdApp.Documents;
			  						wdDoc.Open(linkSharpoint);
			  						insertaLogDocumento(idArchivo, "EDITA", modulo);
			  					}
			  					else if (codigoMimeType==2) { //excel							
			  						var wdApp = new ActiveXObject("Excel.Application");
			  						wdApp.Visible = true;
			  						var wb = wdApp.Workbooks;
			  						wb.Open(linkSharpoint);
			  						insertaLogDocumento(idArchivo, "EDITA", modulo);
			  					}
			  			   }
		    		  } else {
		    			  bootbox.alert("Lo sentimos, no tiene acceso al documento.");
		    		  }
		    	  });
	       } else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_blank');}
	             );
	       }
		});
	});
};
function firmarConToken(url, idArchivo, idInstanciaDeTarea, idTipoDeDocumento) {
	/*console.log("url: " + url);
	console.log("idArchivo: " + idArchivo);
	console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);*/
    $.get("${sessionURL}", function(haySession) {
        if(haySession) {
        	var urlEstaDocumentoFirmado = "${pageContext.request.contextPath}" + "/estaDocumentoFirmado/" + idArchivo + "/" + idInstanciaDeTarea;
        	$.get(urlEstaDocumentoFirmado, function(estaDocumentoFirmado) {
            	if (estaDocumentoFirmado == true) {
            		bootbox.confirm({
        		    	title: "Firmar documento",
        		        message: "El documento ya est&aacute; firmado por usted. ?Desea firmarlo nuevamente?",
        		        buttons: {
        		            confirm: {
        		                label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
        		                className: 'btn-success'
        		            },
        		            cancel: {
        		                label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
        		                className: 'btn-danger'
        		            }
        		        },
        		        callback: function (result) {        		            
        		            if (result == true) {
        		            	$("#linkFirmarConTokenHidden").attr('href', url);            	    		
        	                	document.getElementById("linkFirmarConTokenHidden").click();
        	                	consultaEstadoDocumentoFirmado(idInstanciaDeTarea);
        				    }
        		        }
        		    });
                } else {                	
					var urlValidaSiHayFirmaHoy = "${pageContext.request.contextPath}" + "/validaSiHayFirmaHoy/" + idTipoDeDocumento + "/" + idInstanciaDeTarea;					
	        		$.get(urlValidaSiHayFirmaHoy, function(urlValidaSiHayFirmaHoy) {
	        			if (urlValidaSiHayFirmaHoy == true) {	        			
	        				bootbox.confirm({
		        		    	title: "Firmar documento",

		        		        message: "En esta tarea HOY ya se firm&oacute; un documentom ¿desea firmar nuevamente?",
		        		        buttons: {
		        		            confirm: {
		        		                label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
		        		                className: 'btn-success'
		        		            },
		        		            cancel: {
		        		                label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
		        		                className: 'btn-danger'
		        		            }
		        		        },
		        		        callback: function (result) {        		            
		        		            if (result == true) {
		        		            	$("#linkFirmarConTokenHidden").attr('href', url);            	    		
		    	                        document.getElementById("linkFirmarConTokenHidden").click();
		    	                        consultaEstadoDocumentoFirmado(idInstanciaDeTarea);
		        				    }
		        		        }
	        		    	});	        				
	        			} else {	        				
	        				$("#linkFirmarConTokenHidden").attr('href', url);            	    		
	                        document.getElementById("linkFirmarConTokenHidden").click();
	                        consultaEstadoDocumentoFirmado(idInstanciaDeTarea);	                        
	        			}	        			
	        		});
                }
            });            
        } else {
            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ?ltima acci?n y hemos caducado tu sesi?n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                     , function(){
                           window.open('${raizURL}', '_blank');
                     }
            );
        }
    });	
}
$(document).ready(function() {		

	$(inicializaLinkConfidencialidad);
	$(inicializaLinkSubirDocumentoRequerido);
	$(inicializaLinkAdjuntarDocumento);
	$(inicializaChangeSeleccioneDocumentoRequeridoModal);	
	$(inicializaBotonEditarDocumentoEnEjecucionTarea);
});
</script>
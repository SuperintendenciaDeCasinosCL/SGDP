<%@ page import="cl.gob.scj.sgdp.tipos.PermisoType"%>
<%@ page import="cl.gob.scj.sgdp.config.Constantes"%>
<%@ page import= "cl.gob.scj.sgdp.tipos.ModuloType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="permisoPuedeVisarDocumento"
	value="<%=PermisoType.PUEDE_VISAR_DOCUMENTO.getNombrePermiso()%>" />

<c:set var="permisoPuedeFirmarConFEA"
	value="<%=PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso()%>" />

<c:set var="permisoPuedeFirmarConApplet"
	value="<%=PermisoType.PUEDE_FIRMAR_CON_APPLET.getNombrePermiso()%>" />

<c:set var="permisoPuedeModificarDatosDeDocumento"
	value="<%=PermisoType.PUEDE_MODIFICAR_DATOS_DE_DOCUMENTO
					.getNombrePermiso()%>" />

<div class="row">

	<div class="col-sm-12">

		<div class="col-sm-12 well border-radius">

			<div class="col-sm-9">

				<div class="row">
					<div class="col-sm-6">
						<label for="autorDetalleDeDocumento">Autor:</label>
					</div>
					<div class="col-sm-6">
						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">

							<select class="form-control"
								name="idAutorActualizaMetadataDocumento"
								id="idAutorActualizaMetadataDocumento">
								<option value="">Seleccione Autor</option>
								<c:forEach items="${autoresDTO}" var="autorDTO">
									<c:choose>
										<c:when
											test="${detalleDeArchivoDTO.emisor eq autorDTO.nombreAutor}">
											<option selected="selected" value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
										</c:when>
										<c:otherwise>
											<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>

						</c:if>

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] ne permisoPuedeModificarDatosDeDocumento}">


							<c:forEach items="${autoresDTO}" var="autorDTO">

								<c:if
									test="${detalleDeArchivoDTO.emisor eq autorDTO.nombreAutor}">
									<span class="negrita" id="idAutorActualizaMetadataDocumento">${autorDTO.nombreAutor}</span>
								</c:if>
							</c:forEach>
							<!-- 
							<span id="idAutorActualizaMetadataDocumento">${detalleDeArchivoDTO.autor}</span>
 						-->
						</c:if>

					</div>
				</div>

				<br>

				<div class="row">
					<div class="col-sm-6">
						<label for="fechaDetalleDeDocumento">Fecha del documento:</label>
					</div>
					<div class="col-sm-6">
						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">

							<div class='input-group date'
								id='fechaCreacionActualizaMetadataDoc'>
								<input type='text' class="form-control validate[required]"
									id="fechaCreacionActualizaMetadataDocumento"
									name="fechaCreacionActualizaMetadataDocumento"
									placeholder='__/__/____'
									value="${detalleDeArchivoDTO.fechaDeCreacion}" /> <span
									class="input-group-addon"> <span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>

						</c:if>
						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] ne permisoPuedeModificarDatosDeDocumento}">
							<span class="negrita"
								id="fechaCreacionActualizaMetadataDocumento">${detalleDeArchivoDTO.fechaDeCreacion}</span>
						</c:if>

					</div>
				</div>

				<br>

				<div class="row">
					<div class="col-sm-6">
						<label for="ultimaModificacionDetalleDeDocumento">&Uacute;ltima
							modificaci&oacute;n:</label>
					</div>
					<div class="col-sm-6">
						<span class="negrita" id="ultimaModificacionDetalleDeDocumento">${detalleDeArchivoDTO.fechaUltimaModificacion}</span>
					</div>
				</div>

				<br>

				<div class="row">
					<div class="col-sm-6">
						<label for="cdrDetalleDeDocumento">CDR:</label>
					</div>
					<div class="col-sm-6">

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">
							<input class="form-control validate[required]" type="text"
								id="cdrDetalleDeDocumento" name="cdrDetalleDeDocumento"
								value="${detalleDeArchivoDTO.cdr}" />
						</c:if>

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] ne permisoPuedeModificarDatosDeDocumento}">
							<span class="negrita" id="cdrDetalleDeDocumento">${detalleDeArchivoDTO.cdr}</span>
						</c:if>

					</div>
				</div>

				<br>

				<div class="row">
					<div class="col-sm-6">
						<label for="numeroDocumentoDetalleDeDocumento">N&ring;
							documento:</label>
					</div>
					<div class="col-sm-6">

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">
							<input class="form-control validate[required]" type="text"
								id="numeroDocumentoDetalleDeDocumento"
								name="numeroDocumentoDetalleDeDocumento"
								value="${detalleDeArchivoDTO.numeroDocumento}" />
						</c:if>

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] ne permisoPuedeModificarDatosDeDocumento}">
							<span class="negrita" id="numeroDocumentoDetalleDeDocumento">${detalleDeArchivoDTO.numeroDocumento}</span>
						</c:if>

					</div>
				</div>

				<br>

				<div class="row">
					<div class="col-sm-6">
						<label for="MateriaDeDocumento"> Materia:</label>
					</div>
					<div class="col-sm-6">

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">
							<input class="form-control validate[required]" type="text"
								id="materiaDeDocumento" name="materiaDetalleDeDocumento"
								value="${detalleDeArchivoDTO.descripcion}" />
						</c:if>

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] ne permisoPuedeModificarDatosDeDocumento}">
							<span class="negrita" id="MateriaDeDocumento">${detalleDeArchivoDTO.descripcion}</span>
						</c:if>

					</div>
				</div>

				<br>

				<div class="row">
					<div class="col-sm-6">
						<label for="tipoDeDocumentoDetalleDeDocumento">Tipo de
							documento:</label>
					</div>
					<div class="col-sm-6">

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">

							<select class="form-control validate[required]"
								name="idTipoDeDocumentoDetalleDeDocumento"
								id="idTipoDeDocumentoDetalleDeDocumento">
								<option value="">Seleccione Tipo De Documento</option>
								<c:forEach items="${tiposDeDocumentosDTO}"
									var="tipoDeDocumentosDTO">
									<c:choose>
										<c:when
											test="${detalleDeArchivoDTO.tipoDeDocumento eq tipoDeDocumentosDTO.nombreDeTipoDeDocumento}">
											<option selected="selected"
												value="${tipoDeDocumentosDTO.idTipoDeDocumento}">${tipoDeDocumentosDTO.nombreDeTipoDeDocumento}</option>
										</c:when>
										<c:otherwise>
											<option value="${tipoDeDocumentosDTO.idTipoDeDocumento}">${tipoDeDocumentosDTO.nombreDeTipoDeDocumento}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>

						</c:if>

						<c:if
							test="${permisos[permisoPuedeModificarDatosDeDocumento] ne permisoPuedeModificarDatosDeDocumento}">

							<span class="negrita" id="tipoDeDocumentoDetalleDeDocumento">${detalleDeArchivoDTO.tipoDeDocumento}</span>

						</c:if>


					</div>
				</div>
                <br> 
				<div class="col-sm-12">		
			
					<button type="button"
						class="btn btn-primary"
						onclick='descargaArchivo("<c:url value='getArchivoPorId/${detalleDeArchivoDTO.idArchivo}'/>"
						, "<%=ModuloType.DETALLE_DE_DOCUMENTO.getNombreModulo()%>" )'
						data-iddocumento="${detalleDeArchivoDTO.idArchivo}">
						<span class="fa fa-download font-icon-2 "></span>
					</button>&nbsp;

			
					<%--
				 	<a href='<c:url value="${detalleDeArchivoDTO.linkDescargaNavegador}"/>?ticket=${usuario.alfTicket}' target="_blank" class="btn btn-primary btn-sm">
							<span class="fa fa-download font-icon-2 "></span>
					</a>
					--%>
					
					<c:if test = "${detalleDeArchivoDTO.esEditable eq true}">
					
						<a href="#" class="btn btn-primary btn-sm" id="botonEditarDocumento" 
								onclick='editarDocumento("<%=ModuloType.DETALLE_DE_DOCUMENTO.getNombreModulo()%>");'
										data-idarchivo="${detalleDeArchivoDTO.idArchivo}"
		                                data-codigomimetype="${detalleDeArchivoDTO.codigoMimeType}"
		                                data-linksharpoint="${detalleDeArchivoDTO.linkSharpoint}">
		                              <span class="glyphicon glyphicon-edit font-icon-2"></span>
		                 </a>
					
					</c:if>
					
				</div>
  
              <!--  
				<c:if
					test="${permisos[permisoPuedeModificarDatosDeDocumento] eq permisoPuedeModificarDatosDeDocumento}">
					<br>
					<div class="row">
						<div class="col-sm-4">
							<button id="botonModificarDatosDeDocumento" type="button"
								class="btn btn-primary"
								onclick="modificarDatosDeDocumento('${idArchivo}')">
								<i class="glyphicon glyphicon-saved"></i> Grabar
							</button>
						</div>
					</div>
				</c:if>
				-->
				
				 <!--  
				<c:if
					test="${permisos[permisoPuedeVisarDocumento] eq permisoPuedeVisarDocumento and esVisable}">
					<br>
					<div class="row">
						<div class="col-sm-4">
							<button id="botonVisarDocumento" type="button"
								class="btn btn-info"
								onclick="visarDocumento('${idArchivo}', ${esVisable}, 
	    		${aplicaFEA}, ${aplicaFirmaApplet}, '${detalleDeArchivoDTO.idExpedienteQueLoContiene}', 
	    		'${detalleDeArchivoDTO.nombre}', '${detalleDeArchivoDTO.mimeType}')">
								<i class="glyphicon glyphicon-ok"></i> Visar Documento
							</button>
						</div>
					</div>
				</c:if>
				-->
				<!--  
				<c:if
					test="${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA and aplicaFEA}">
					<br>
					<div class="row">
						<div class="col-sm-4">
							<button id="botonFEA" type="button" class="btn btn-info"
								onclick="cargaModalFEA()">
								<i class="glyphicon glyphicon-qrcode"></i> Firma Avanzada
							</button>
						</div>
					</div>
				</c:if>
				-->
				<!--  
				<c:if
					test="${permisos[permisoPuedeFirmarConApplet] eq permisoPuedeFirmarConApplet and aplicaFirmaApplet}">
					<br>
					<div class="row">
						<div class="col-sm-4">
							<a
								href='<c:url value="/firmaApplets/${idArchivo}/${detalleDeArchivoDTO.nombre}/${detalleDeArchivoDTO.idExpedienteQueLoContiene}/${detalleDeArchivoDTO.tipoDeDocumento}"/>'
								class="btn btn-info" role="button"> <i
								class="glyphicon glyphicon-modal-window"></i> Firma Applet
							</a>

						</div>
					</div>
				</c:if>
				-->

			</div>

			<div class="col-sm-3">

				<label>Versiones:</label>
				<div>

					<ul id="versionesDetalleDeDocumento">

						<c:forEach items="${detalleDeArchivoDTO.versiones}"
							var="versionArchivoDTO">

							<li>
								<%--  
								<a
								href='<c:url value="${versionArchivoDTO.linkAversion}"/>?ticket=${ticket}'
								target="_blank" role="button"> <i
									class="glyphicon glyphicon-download" aria-hidden="true"></i>
									${versionArchivoDTO.versionLabel} por
									${versionArchivoDTO.creador}
								</a>--%>
								<a href='#' onclick='descargaArchivoPorIdYVersion("<c:url value='getContenidoArchivoPorIdYVersion'/>"
								,"${detalleDeArchivoDTO.idArchivo}"
								,"${versionArchivoDTO.versionLabel}"
								,"${versionArchivoDTO.versionMimeType}"
								,"<%=ModuloType.DETALLE_DE_DOCUMENTO.getNombreModulo()%>"
								)'
									data-idarchivo='${detalleDeArchivoDTO.idArchivo}'
									data-versionlabel='${versionArchivoDTO.versionLabel}'
									data-versionmimetype='${versionArchivoDTO.versionMimeType}'>
									<i class="glyphicon glyphicon-download" aria-hidden="true"></i>
									${versionArchivoDTO.versionLabel} por
									${versionArchivoDTO.creador}
								</a>

							</li>

						</c:forEach>

					</ul>

				</div>

			</div>

		</div>
	</div>
	<!-- Poner Div -->
	<div class="col-sm-12">
		<div class="col-sm-9">
			<br>

			<div class="row">
				<div class="col-sm-12">
					<label for="tipoDeDocumentoDetalleDeDocumento">A&ntilde;adir
						observaci&oacute;n:</label>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<textarea class="form-control validate[required]"
						id="comentarioDetalleDeDocumento"
						name="comentarioDetalleDeDocumento" rows="5"></textarea>
				</div>
			</div>

			<br>

			<div class="row">
				<div class="col-sm-12">
					<button id="agregarComentarioDocumento"
						onclick="agregarComentarioDocumento('${idArchivo}')" type="button"
						class="btn btn-primary">A&ntilde;adir</button>
				</div>
			</div>

		</div>
	</div>
	<div class="col-sm-12">
		<div class="col-sm-9">

			<br>

			<div class="row">
				<div class="col-sm-12">
					<label for="tipoDeDocumentoDetalleDeDocumento">Observaciones:</label>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<table id="comentariosDetalleDeDocumento"
						class="table table-striped table-bordered" cellspacing="0"
						width="100%">
						<thead>
							<tr>
								<th>observaci&oacute;n</th>
								<th>Fecha</th>
								<th>Usuario</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${detalleDeArchivoDTO.comentarios}"
								var="comentarioArchivoDTO">
								<tr>
									<td>${comentarioArchivoDTO.comentario}</td>
									<td>${comentarioArchivoDTO.fecha}</td>
									<td>${comentarioArchivoDTO.usuario}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>


	<div class="col-sm-12">
		<div class="col-sm-9">

			<br>

			<div class="row">
				<div class="col-sm-12">
					<label for="adjuntosDetalleDeDocumento">Adjuntos:</label>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<table id="adjuntosDetalleDeDocumento"
						class="table table-striped table-bordered" cellspacing="0"
						width="100%">
						<thead>
							<tr>
								<th>Nombre Adjunto</th>
								<th></th>
							</tr>
						</thead>
						<tbody>					
							<c:forEach items="${listaArchivoInfoDTOAdjuntos}" var="listaArchivoInfoDTOAdjunto">
								<%-- <c:if test="${not empty listaArchivoInfoDTOAdjunto.cartaRelacionada}">--%>
									<tr>
										<td>${listaArchivoInfoDTOAdjunto.nombre}</td>
										<td>
									<a href='<c:url value="${listaArchivoInfoDTOAdjunto.linkDescargaNavegador}"/>?ticket=${usuario.alfTicket}'
										target="_blank" 
									class="btn btn-primary btn-sm btn-file ">
									<span class="glyphicon glyphicon-eye-open "></span></a>									
										</td>	

									</tr>
								<%--</c:if>--%>

							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>

</div>


<script>
$(function() {
    $('#fechaCreacionActualizaMetadataDoc').datetimepicker({
          locale : 'es',
          format : 'DD/MM/YYYY'
    });
});


function formatTablaAdjuntos() {

	var adjuntosDetalleDeDocumento = $('#adjuntosDetalleDeDocumento')
	.DataTable(
			{
				buttons : [/* {
					extend : 'copyHtml5',
					exportOptions : {
						columns : [ 0, ':visible' ]
					}
				},*/ {
					extend : 'excelHtml5',
					filename : 'Comentarios.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : {

					"sProcessing" : "Procesando...",
					"sLengthMenu" : "Mostrar _MENU_ registros",
					"sZeroRecords" : "No se encontraron resultados",
					"sEmptyTable" : "Ning\u00fan dato disponible en esta tabla",
					"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
					"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
					"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
					"sInfoPostFix" : "",
					"sSearch" : "Buscar:",
					"sUrl" : "",
					"sInfoThousands" : ",",
					"sLoadingRecords" : "Cargando...",
					"oPaginate" : {
						"sFirst" : "Primero",
						"sLast" : "\u00faltimo",
						"sNext" : "Siguiente",
						"sPrevious" : "Anterior"
					},
					"oAria" : {
						"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
						"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
					},
					"buttons" : {
						"excel" : 'Exportar a Excel',
						"colvis" : 'Cambiar columnas',
						"copy" : 'Copiar a Portapapeles',
						"copyTitle" : 'A&ntilde;adido al portapapeles',
						"copyKeys" : 'Pulse <i> Ctrl </i> o <i>\ u2318 </i> + <i> C </i> para copiar datos de la tabla en el portapapeles. <br> cancelar, haga clic en este mensaje o pulse Esc . ',
						"copySuccess" : {
							_ : 'Copiados %d filas',
							1 : 'Copiado 1 fila'
						}
					}
				},
				"pageLength": 3,
				bFilter: false,
				bLengthChange: false,
				bInfo : false,
				"emptyTable":     "No hay comentarios"	
			});

	/*comentariosDetalleDeDocumento.buttons().container().appendTo(
	'#comentariosDetalleDeDocumento_wrapper .row:eq(0)');*/
}


function formatTablaComentariosDetalleDeDocumento() {

	var comentariosDetalleDeDocumento = $('#comentariosDetalleDeDocumento')
	.DataTable(
			{
				buttons : [/* {
					extend : 'copyHtml5',
					exportOptions : {
						columns : [ 0, ':visible' ]
					}
				},*/ {
					extend : 'excelHtml5',
					filename : 'Comentarios.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : {

					"sProcessing" : "Procesando...",
					"sLengthMenu" : "Mostrar _MENU_ registros",
					"sZeroRecords" : "No se encontraron resultados",
					"sEmptyTable" : "Ning\u00fan dato disponible en esta tabla",
					"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
					"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
					"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
					"sInfoPostFix" : "",
					"sSearch" : "Buscar:",
					"sUrl" : "",
					"sInfoThousands" : ",",
					"sLoadingRecords" : "Cargando...",
					"oPaginate" : {
						"sFirst" : "Primero",
						"sLast" : "\u00faltimo",
						"sNext" : "Siguiente",
						"sPrevious" : "Anterior"
					},
					"oAria" : {
						"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
						"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
					},
					"buttons" : {
						"excel" : 'Exportar a Excel',
						"colvis" : 'Cambiar columnas',
						"copy" : 'Copiar a Portapapeles',
						"copyTitle" : 'A&ntilde;adido al portapapeles',
						"copyKeys" : 'Pulse <i> Ctrl </i> o <i>\ u2318 </i> + <i> C </i> para copiar datos de la tabla en el portapapeles. <br> cancelar, haga clic en este mensaje o pulse Esc . ',
						"copySuccess" : {
							_ : 'Copiados %d filas',
							1 : 'Copiado 1 fila'
						}
					}
				},
				"pageLength": 3,
				bFilter: false,
				bLengthChange: false,
				bInfo : false,
				"order": [[ 1, "desc" ]],
				"emptyTable":     "No hay comentarios"	
			});

	/*comentariosDetalleDeDocumento.buttons().container().appendTo(
	'#comentariosDetalleDeDocumento_wrapper .row:eq(0)');*/
};

$(document).ready(function() {
	formatTablaComentariosDetalleDeDocumento();	
	formatTablaAdjuntos();	 
	$("#comentarioDetalleDeDocumento").val("");
});

</script>
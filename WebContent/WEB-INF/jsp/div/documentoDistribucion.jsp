<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.ModuloType" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<div id=divDocs>
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
											, "<%=ModuloType.DOCUMENTO_EN_DISTRIBUCION.getNombreModulo()%>")'
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

<script>
	$(document).ready(function() {
		formatTablaHistorialDelDocumentoEnDistribucion();
	});
</script>
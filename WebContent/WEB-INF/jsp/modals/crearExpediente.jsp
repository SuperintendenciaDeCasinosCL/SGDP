<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<!-- Modal Crear Expediente -->
<div class="modal fade" id="crearExpedienteModal" tabindex="-1"
	role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="formCrearExpediente"
				action="${pageContext.request.contextPath}/crearExpediente"
				method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message
								code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
								code="boton.cerrar.nombre" /></span>
					</button>
					<h3 class="modal-title" id="lineModalLabel">
						<spring:message
							code="bandejaDeEntrada.modal.crearExpediente.titulo" />
					</h3>

				</div>
				<div class="modal-body">

					<div class="col-sm-12 text-center">
						<span class="btn btn-danger btn-sm">Crear Expediente</span> <span
							class="glyphicon glyphicon-arrow-right"></span> <span
							class="btn btn-default btn-sm">Subir documento conductor</span> <span
							class="glyphicon glyphicon-arrow-right"></span> <span
							class="btn btn-default btn-sm">Subir adjunto</span>
					</div>

					<div class="form-group">
						<label for="materia"><spring:message
								code="bandejaDeEntrada.modal.crearExpediente.form.input.materia.label" /></label>
						<input type="text" class="form-control validate[required]"
							id="materia" name="materia"
							placeholder='<spring:message code="bandejaDeEntrada.modal.crearExpediente.form.input.materia.placeholder"/>'>
					</div>
					<div class="form-group">
					  <div>
						<spring:message
							code="bandejaDeEntrada.modal.crearExpediente.form.select.autor.seleccione"
							var="autorSeleccione" />
						<label for="autor"><spring:message
								code="bandejaDeEntrada.modal.crearExpediente.form.input.autor.label" /></label>
						<select class="form-control validate[required]"
							style="width: 100%" name="idAutor" id="idAutor">
							<!-- <option value="" label='${autorSeleccione}' /> -->
							<option value="">${autorSeleccione}</option>
							<%--<c:forEach items="${autoresDTO}" var="autorDTO">--%>
							<c:forEach items="${autoresDTO}" var="autorDTO">
								<!--	<option value="${autorDTO.idAutor}" label="${autorDTO.nombreAutor}" /> -->
								<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
							</c:forEach>
						</select>
					</div>
					</div>

				   <div class="row">
	                <div class="col-sm-10">	                
						<div class="form-group">
							<label for="proceso"><spring:message
									code="bandejaDeEntrada.modal.crearExpediente.form.input.proceso.label" /></label>
							<select class="form-control validate[required]"
								style="width: 100%" id="idProcesosVigente" name="idProceso"
								onchange="buscarNumeroTareas(0)">
								<option value="">Seleccione SubProceso</option>
								<c:forEach items="${listaProcesoDto}" var="listaProceso">
									<option value="${listaProceso.idProceso}">${listaProceso.nombreProceso} (${listaProceso.nombreMacroproceso})</option>
								</c:forEach>
							</select>										
						</div>
	                </div>
	                <div class="col-sm-1">
	                	<span class="glyphicon glyphicon-question-sign cursorPuntero info-sgdp"  
	                	data-toggle="tooltip" data-placement="top" title="Buscar Proceso"
	                	 style="margin: 30px 0;" onclick="buscarAyudaProceso()"></span>
	                </div>
	                <div class="col-sm-1">
	                	<span class="glyphicon glyphicon-info-sign cursorPuntero info-sgdp"  
	                	data-toggle="tooltip" data-placement="top" title="Ver Proceso"
	                	 style="margin: 30px 0;" onclick="dibujarProcesoModal()"></span>
	                </div>
	              </div>

				</div>
				<div class="modal-footer">
					<div class="form-group">
						<div class=" col-sm-offset-8 col-sm-4">
							<button type="button" id="buttonSubirCrearExpediente"
								onclick="crearExpedienteSubirArchivo()"
								class="btn btn-link btn-lg btn-block">
								<spring:message
									code="bandejaDeEntrada.modal.crearExpediente.form.boton.Siguiente.value" />
								<span class="glyphicon glyphicon-chevron-right"></span>
							</button>

						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">

$("#idProcesosVigente").select2({
    theme: "bootstrap",
    dropdownParent: $("#formCrearExpediente"),
    language: "es"
});

</script>


<!-- ------------------------------------ Modal subir documentos conductor al expediente -------------------------------------- -->



<div class="modal fade" id="subirDocumentoExpdienteModal" tabindex="-1"
	role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<sf:form id="formSubirDocumentoExpediente" class="form-horizontal"
				method="POST" enctype="multipart/form-data"
				commandName="subirDocumentoDTO">
				<input type="hidden" name="tipoDeDocumentoSubirExpediente"
					id="tipoDeDocumentoSubirExpediente" />
				<input type="hidden" name="idExpedienteSubirDocumento"
					id="idExpedienteSubirDocumentoExpediente" />
				<input type="hidden" name="nombreExpedienteSubirDocumentoExpediente"
					id="nombreExpedienteSubirDocumentoExpediente" />
				<input type="hidden" name="idInstanciaDeTareaOrigenSubirDocumento"
					id="idInstanciaDeTareaOrigenSubirDocumentoExpediente">
				<input type="hidden" id="idTipoDeDocumentoSubirExpedienteHidden" name="idTipoDeDocumentoSubir" />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message
								code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
								code="boton.cerrar.nombre" /></span>
					</button>
					<!--  
					<h3 id="cabeceraSubirDocumentoExpdienteModal" class="modal-title" id="lineModalLabel"></h3>
					-->
					<h3 id="cabeceraSubirDocumentoConductor" class="modal-title"
						id="lineModalLabel">
						<spring:message
							code="bandejaDeEntrada.modal.crearDocumentoConductor.titulo" />
					</h3>
				</div>
				<div class="modal-body">

					<div class="col-sm-12 text-center">
						<span class="btn btn-default btn-sm">Crear Expediente</span> <span
							class="glyphicon glyphicon-arrow-right"></span> <span
							class="btn btn-danger btn-sm">Subir documento conductor</span> <span
							class="glyphicon glyphicon-arrow-right"></span> <span
							class="btn btn-default btn-sm">Subir adjunto</span> <br> <br>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-4" for="documentoExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.documentoConductor" /></label>
						<div class="col-sm-8">
							<input class="validate[required, minFileSize]" type="file" name="documento"
								id="documentoConductor" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="idTipoDeDocumentoSubirExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento" /></label>
						<div class="col-sm-8">
							<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione"
								var="tipoDeDocumentoSeleccione" />
							<select class="form-control validate[required]"
								style="width: 100%" id="idTipoDeDocumentoSubirExpediente">								
								<%--<c:forEach items="${tiposDeDocumentosDTO}"
									var="tipoDeDocumentoDTO">
									<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
								</c:forEach>--%>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="cdrExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.cdr" /></label>
						<div class="col-sm-8">
							<input type="text" class="form-control validate[required]"
								id="cdrExpediente" name="cdr"
								placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.cdr.placeholder"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="numeroDocumentoExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.numeroDocumento" /></label>
						<div class="col-sm-8">
							<input type="text" class="form-control validate[required]"
								id="numeroDocumentoExpediente" name="numeroDocumento"
								placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="fechaDeCreacionDocumentoExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.fechaDeCreacion" /></label>
						<div class="col-sm-8">
							<div class='input-group date'
								id='fechaCreacionDocumentoExpedienteDiv'>
								<input type='text' class="form-control validate[required]"
									id="fechaDeCreacionDocumentoExpediente"
									name="fechaDeCreacionDocumento"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="fechaRecepcionDocumentoExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.fechaDeRecepcion" /></label>
						<div class="col-sm-8">
							<div class='input-group date'
								id='fechaRecepcionDocumentoExpedienteDiv'>
								<input type='text' class="form-control validate[required]"
									id="fechaRecepcionDocumentoExpediente"
									name="fechaRecepcionDocumento"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeRecepcion.placeholder"/>' />
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="idAutorSubirDocumentoExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.autor" /></label>
						<spring:message
							code="bandejaDeEntrada.modal.subirDocumento.form.select.autor.seleccione"
							var="autorSubirDocumentoSeleccione" />
						<div class="col-sm-8">
							<select class="form-control validate[required]"
								style="width: 100%" name="idAutorSubirDocumento"
								id="idAutorSubirDocumentoExpediente">
								<option value="">${autorSubirDocumentoSeleccione}</option>
								<c:forEach items="${autoresDTO}"
									var="autorDTO">
									<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="descripcionExpediente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.label" /></label>
						<div class="col-sm-8">
							<textarea class="form-control validate[required]"
								id="descripcionExpediente" name="descripcion"
								placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.placeholder"/>'
								rows="10"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="col-sm-offset-8 col-sm-4">
						<button id="botonSubirDocumentoConductor" type="button" class="btn btn-link btn-lg btn-block"
							onclick="subirDocumentoConductor()">
							<spring:message
								code="bandejaDeEntrada.modal.crearExpediente.form.boton.Siguiente.value" />
							<span class="glyphicon glyphicon-chevron-right"></span>
						</button>
					</div>

					<!--   
				    <div class="col-sm-offset-4 col-sm-8">
				       <br>					    
					     <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" id="subirDocumentoAntecedenteModalB" data-target="#subirDocumentoAntecedenteModal">
							<span class="glyphicon glyphicon-upload"></span>  <spring:message code="bandejaDeEntrada.modal.subirDocumento.form.boton.AgregarAntecedente.value"/>
						 </button>					    				    				    					    
				    </div>
				 	 -->
					<!--  	    
				    <div class="col-sm-offset-4 col-sm-8">
				        <br>
						<button type="button" class="btn btn-primary btn-lg btn-block" >
							<span class="glyphicon glyphicon-upload"></span>  
	                       	<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.boton.OtroDocConductor.value"/>
					    </button>
				    </div>
						  -->
				</div>
			</sf:form>
		</div>
	</div>
</div>

<script>
	$(function() {
		$('#fechaDeCreacionDocumentoExpediente').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		jQuery("#formSubirDocumento").validationEngine();
	});

	$(function() {
		$('#fechaCreacionDocumentoExpedienteDiv').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		jQuery("#formSubirDocumento").validationEngine();
	});

	$(function() {
		$('#fechaRecepcionDocumentoExpedienteDiv').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		// jQuery("#formSubirDocumento").validationEngine();
	});

	$(function() {
		$('#fechaRecepcionDocumentoExpediente').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		// jQuery("#formSubirDocumento").validationEngine();
	});

	 $('#fechaDeCreacionDocumentoExpediente').val(moment(new Date().getTime()).format('DD/MM/YYYY'));
	 $('#fechaRecepcionDocumentoExpediente').val(moment(new Date().getTime()).format('DD/MM/YYYY'));
</script>

<!-- ------------------------------------ Subir mas documentos -------------------------------------- -->




<div class="modal fade" id="subirMasDocumentoExpdienteModal"
	tabindex="-1" role="dialog" aria-labelledby="modalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- <input type="hidden" name="idInstanciaDeTareaOrigenSubirDocumentoExpediente" id="idInstanciaDeTareaOrigenSubirDocumentoExpediente" />  -->
			<!--	<input type="hidden" name="idExpedienteSubirDocumento" id="idExpedienteSubirDocumento" />  -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"><spring:message
							code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
							code="boton.cerrar.nombre" /></span>
				</button>
				<h3 id="cabeceraSubirMasDocumentoBotonesModal" class="modal-title"
					id="lineModalLabel"></h3>
			</div>

			<div class="modal-footer">

				<div class="col-sm-12 form-group">

					<button type="button" class="btn btn-primary btn-lg btn-block"
						data-toggle="modal" id="DocumentoAntecedenteModalB"
						data-target="#subirDocumentoAntecedenteModal">
						<span class="glyphicon glyphicon-upload"></span>
						<spring:message
							code="bandejaDeEntrada.modal.subirDocumento.form.boton.AgregarAntecedente.value" />
					</button>
				</div>

				<div class="col-sm-12 form-group">
					<!-- 
				        <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" id="DocumentoAgregarDocConductor" data-target="#subirDocumentoExpdienteModal">
							<span class="glyphicon glyphicon-upload"></span>  <spring:message code="bandejaDeEntrada.modal.subirDocumento.form.boton.OtroDocConductor.value"/>
						 </button>	
						-->

					<button type="button" class="btn btn-primary btn-lg btn-block"
						onclick="adjuntarOtroDocConductor()">
						<span class="glyphicon glyphicon-upload"></span>
						<spring:message
							code="bandejaDeEntrada.modal.subirDocumento.form.boton.OtroDocConductor.value" />
					</button>

				</div>

				<div class="col-sm-12 form-group">
					<button type="button" class="btn btn-primary btn-lg btn-block"
						onclick="cerrarModalBotones()">
						<span class="glyphicon glyphicon-upload"></span>
						<spring:message
							code="bandejaDeEntrada.modal.subirDocumento.form.boton.Terminar.value" />
					</button>
				</div>




			</div>
		</div>
	</div>
</div>

<!-- ------------------------------------ Modal subir Antecedente -------------------------------------- -->

<div class="modal fade" id="subirDocumentoAntecedenteModal"
	tabindex="-1" role="dialog" aria-labelledby="modalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<sf:form id="formSubirDocumentoAntecedente" class="form-horizontal"
				method="POST" enctype="multipart/form-data"
				commandName="subirDocumentoDTO">

				<input type="hidden" name="idExpedienteSubirArchivo"
					id="idExpedienteSubirArchivoAntecedente" />
				<input type="hidden" name="idInstanciaDeTareaSubirArchivo"
					id="idInstanciaDeTareaSubirArchivoAntecedente" />
				<input type="hidden" name="cartaRelacionada"
					id="cartaRelacionadaAntecedentes">
				<!-- Nombre Documento conductor -->


				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message
								code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
								code="boton.cerrar.nombre" /></span>
					</button>
					<h3 id="cabeceraSubirDocumentoModalAntecedente" class="modal-title"
						id="lineModalLabel"></h3>

				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label col-sm-4" for="documentoAntecedente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.documentoAdjunto" /></label>
						<div class="col-sm-8">
							<input class="validate[required]" type="file"
								id="archivoAntecedente" name="archivo" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="idTipoDeDocumentoSubirAntecedenteL"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.tipoDeDocumento" /></label>
						<div class="col-sm-8">
							<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.select.tipoDeDocumento.seleccione"
								var="tipoDeDocumentoSeleccione" />
							<select class="form-control validate[required]"
								style="width: 100%" name="idTipoDeDocumentoSubir"
								id="idTipoDeDocumentoSubirAntecedente">
								<option value="">${tipoDeDocumentoSeleccione}</option>
								<c:forEach items="${tiposDeDocumentosDTO}"
									var="tipoDeDocumentoDTO">
									<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="numeroDocumentoAntecedente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.numeroDocumento" /></label>
						<div class="col-sm-8">
							<input type="text" class="form-control validate[required]"
								id="numeroDocumentoAntecedente" name="numeroDocumento"
								placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="fechaCreacionArchivoAntecedenteDiv"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.fechaDeCreacion" /></label>
						<div class="col-sm-8">
							<div class='input-group date'
								id='fechaCreacionArchivoAntecedenteDiv'>
								<input type='text' class="form-control validate[required]"
									id="fechaCreacionArchivoAntecedente"
									name="fechaCreacionArchivo"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="fechaRecepcionArchivoAntecedenteDiv"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.fechaDeRecepcion" /></label>
						<div class="col-sm-8">
							<div class='input-group date'
								id='fechaRecepcionArchivoAntecedenteDiv'>
								<input type='text' class="form-control validate[required]"
									id="fechaRecepcionArchivoAntecedente"
									name="fechaRecepcionArchivo"
									placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeRecepcion.placeholder"/>' />
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4"
							for="idAutorSubirDocumentoAntecedente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.label.autor" /></label>
						<spring:message
							code="bandejaDeEntrada.modal.subirDocumento.form.select.autor.seleccione"
							var="autorSubirDocumentoSeleccione" />
						<div class="col-sm-8">
							<select class="form-control validate[required]"
								style="width: 100%" name="idAutorSubirDocumento"
								id="idAutorSubirDocumentoAntecedente">
								<option value="">${autorSubirDocumentoSeleccione}</option>
								<c:forEach items="${autoresDTO}"
									var="autorDTO">
									<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="descripcionAntecedente"><spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.label" /></label>
						<div class="col-sm-8">
							<textarea class="form-control validate[required]"
								id="descripcionAntecedente" name="descripcion"
								placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.placeholder"/>'
								rows="10"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="col-sm-offset-4 col-sm-8">
						<button type="button" class="btn btn-primary btn-lg btn-block"
							onclick=" subirDocumentoAdjuntoModal(1)">
							<span class="glyphicon glyphicon-upload"></span>
							<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.boton.subir.value" />
						</button>
					</div>
					<div class="col-sm-offset-4 col-sm-8">
						<br>
						<button type="button" class="btn btn-primary btn-lg btn-block"
							onclick="subirDocumentoAdjuntoModal(2)">
							<span class="glyphicon glyphicon-upload"></span>
							<spring:message
								code="bandejaDeEntrada.modal.subirDocumento.form.boton.subirAdjuntarNuevo.value" />
						</button>
					</div>
				</div>
			</sf:form>
		</div>
	</div>
</div>


<script>
	$(function() {
		$('#fechaCreacionArchivoAntecedenteDiv').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		jQuery("#formSubirDocumento").validationEngine();
	});

	$(function() {
		$('#fechaCreacionArchivoAntecedente').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		jQuery("#formSubirDocumento").validationEngine();
	});

	$(function() {
		$('#fechaRecepcionArchivoAntecedenteDiv').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		//  jQuery("#formSubirDocumento").validationEngine();
	});

	$(function() {
		$('#fechaRecepcionArchivoAntecedente').datetimepicker({
			locale : 'es',
			format : 'DD/MM/YYYY'
		});
		//  jQuery("#formSubirDocumento").validationEngine();
	});
</script>




<div class="modal fade" id="subirDocumentoAntecedenteTablaModal"
	tabindex="-1" role="dialog" aria-labelledby="modalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<sf:form id="formSubirDocumentoAntecedenteTabla"
				class="form-horizontal" method="POST" enctype="multipart/form-data"
				commandName="subirDocumentoDTO">

				<input type="hidden" name="idExpedienteSubirArchivo"
					id="idExpedienteSubirArchivoAntecedenteTabla" />
				<input type="hidden" name="idInstanciaDeTareaSubirArchivo"
					id="idInstanciaDeTareaSubirArchivoAntecedenteTabla" />
				<input type="hidden" name="cartaRelacionada"
					id="cartaRelacionadaAntecedentesTabla">
				<!-- Nombre Documento conductor -->


				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message
								code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
								code="boton.cerrar.nombre" /></span>
					</button>

					<h3 id="cabeceraSubirDocumentoModalAntecedenteTabla"
						class="modal-title" id="lineModalLabel">
						<spring:message code="dejaDeEntrada.modal.subirAdjuntos.titulo" />
					</h3>

				</div>
				<div class="modal-body">


					<div class="text-center">
						<span class="btn btn-default btn-sm">Crear Expediente</span> <span
							class="glyphicon glyphicon-arrow-right"></span> <span
							class="btn btn-default btn-sm">Subir documento conductor</span> <span
							class="glyphicon glyphicon-arrow-right"></span> <span
							class="btn btn-danger btn-sm">Subir adjunto</span> <br> <br>
					</div>



					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="tablaDocumentosAdjuntosLista">
							<thead>
								<tr>
									<th>Descripcion</th>
									<th>Tipo</th>
									<th>N° Documento</th>
									<th>Fecha Documento</th>
									<th>Autor</th>
									<th>Documento Adjunto</th>
									<!-- 
									<th>
										 
										    <span class="btn btn-primary btn-file" data-toggle="tooltip" data-placement="top" title="Agregar Antecedente" onclick="agregarDocumento()">    		
										    <span class="glyphicon glyphicon-plus"></span>                                                                                        		
										    </span>  
										  
									</th>
									  -->
								</tr>
							</thead>
							<tbody id="tablaDocumentosAdjuntoTbody">

								<tr class="documentoAdjuntoReco">

									<td><input type="text"
										class="form-control validate[required] descripcionAntecedenteTabla "
										id="descripcionAntecedenteTabla" name="descripcion"
										placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.placeholder"/>'>
									</td>

									<td><select
										class="form-control validate[required] idTipoDeDocumentoSubirAntecedenteTabla"
										style="width: 100%" name="idTipoDeDocumentoSubir"
										id="idTipoDeDocumentoSubirAntecedenteTabla">
											<option value="">Seleccione</option>
											<c:forEach items="${tiposDeDocumentosDTO}"
												var="tipoDeDocumentoDTO">
												<option value="${tipoDeDocumentoDTO.idTipoDeDocumento}">${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}</option>
											</c:forEach>
									</select></td>
									<td><input type="text"
										class="form-control validate[required] numeroDocumentoAntecedenteTabla "
										id="numeroDocumentoAntecedenteTabla" name="numeroDocumento"
										placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder"/>'>
									</td>

									<td>
										<div
											class='input-group date fechaCreacionArchivoAntecedenteTablaDiv '
											id='fechaCreacionArchivoAntecedenteTablaDiv'>
											<input type='text'
												class="form-control validate[required] clsDatePicker fechaCreacionArchivoAntecedenteTabla"
												id="fechaCreacionArchivoAntecedenteTabla"
												name="fechaCreacionArchivo"
												placeholder='<spring:message code="bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder"/>' />
											<span class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</td>

									<td><select
										class="form-control validate[required] idAutorSubirDocumentoAntecedenteTabla"
										style="width: 100%" name="idAutorSubirDocumento"
										id="idAutorSubirDocumentoAntecedenteTabla">
											<!--  	<option value="">${autorSubirDocumentoSeleccione}</option>-->
											<option value="">Seleccione</option>
											<c:forEach items="${autoresDTO}"
												var="autorDTO">
												<option value="${autorDTO.idAutor}">${autorDTO.nombreAutor}</option>
											</c:forEach>
									</select></td>
									<td>
										<!-- <input class="validate[required]" type="file" id="archivoAntecedente" name="archivo"/>	 -->

										<span id="spanSubirDocumentoAdjuntoExp"
										class="btn btn-success fileinput-button  btn-file btn-verde"
										data-toggle="tooltip" data-placement="top"
										title="Subir Documento Adjunto"> <span
											class="fa fa-upload "></span> <input
											class="validate[required] archivoAntecedenteTabla"
											type="file" id="archivoAntecedenteTabla" name="archivo" />
									</span>
									</td>
										<!--  
									<td>									
								    <span class="btn btn-danger btn-file eliminarDocumento" data-toggle="tooltip" data-placement="top" title="Eliminar Antecedente">  		
									      <span class="glyphicon glyphicon-minus"></span>                                                                                       		
									</span>      				
									</td>
										   -->

								</tr>



							</tbody>
						</table>

					</div>

				</div>
				<div class="modal-footer">

					<div class="col-sm-offset-9 col-sm-3">
						<br>

						<button type="button" id="buttonSubirDocumentoAdjuntoTable" onclick="cerrarModalSubirAdjunto()"
							class="btn btn-link btn-lg btn-block">
							<spring:message
								code="bandejaDeEntrada.modal.crearExpediente.form.boton.finalizar.value" />
							<span class="glyphicon glyphicon-ok"></span>
						</button>
					</div>

				</div>
			</sf:form>
		</div>
	</div>
</div>



<script type="text/javascript">

var fila = 1;

function agregarDocumentoAdjunto(){

    

	 //Desabilitar campos
	 $(".descripcionAntecedenteTabla ").attr('disabled','disabled');
	 $(".idTipoDeDocumentoSubirAntecedenteTabla ").attr('disabled','disabled'); 
	 $(".numeroDocumentoAntecedenteTabla ").attr('disabled','disabled');
	 $(".fechaCreacionArchivoAntecedenteTabla ").attr('disabled','disabled');
	 $(".idAutorSubirDocumentoAntecedenteTabla  ").attr('disabled','disabled');
	 $(".archivoAntecedenteTabla").attr('disabled','disabled');
     $(".btn-verde").css("background-color", "#afacac");


 	// Generar Fila nueva
 	 
	 
 	 
	var strVar="";
	strVar += "<tr class=\"documentoAdjuntoReco\">";
	strVar += "";
	strVar += "									<td><input type=\"text\"";
	strVar += "										class=\"form-control validate[required] descripcionAntecedenteTabla \"";
	strVar += "										id=\"descripcionAntecedenteTabla"+fila +"\" name=\"descripcion\"";
	strVar += "										placeholder='<spring:message code='bandejaDeEntrada.modal.subirDocumento.form.input.descripcion.placeholder' />'>";
	strVar += "									<\/td>";
	strVar += "									<td><select";
	strVar += "										class=\"form-control validate[required] idTipoDeDocumentoSubirAntecedenteTabla\"";
	strVar += "										style=\"width: 100%\" name=\"idTipoDeDocumentoSubir\"";
	strVar += "										id=\"idTipoDeDocumentoSubirAntecedenteTabla"+fila +"\">";
	strVar += "											<option value=\"\">Seleccione<\/option>";

	
										<c:forEach items="${tiposDeDocumentosDTO}" var="tipoDeDocumentoDTO" >
	strVar +=  "									 <option value='${tipoDeDocumentoDTO.idTipoDeDocumento}'>${tipoDeDocumentoDTO.nombreDeTipoDeDocumento}<\/option>";
										</c:forEach>
	
	strVar += "									<\/select><\/td>";
	strVar += "									<td><input type=\"text\"";
	strVar += "										class=\"form-control validate[required] numeroDocumentoAntecedenteTabla \"";
	strVar += "										id=\"numeroDocumentoAntecedenteTabla"+fila +"\" name=\"numeroDocumento\"";
	strVar += "										placeholder='<spring:message code='bandejaDeEntrada.modal.subirDocumento.form.input.numeroDocumento.placeholder'/>'>";
	strVar += "									<\/td>";
	strVar += "";
	strVar += "									<td>";
	strVar += "										<div";
	strVar += "											class='input-group date fechaCreacionArchivoAntecedenteTablaDiv '";
	strVar += "											id='fechaCreacionArchivoAntecedenteTablaDiv'>";
	strVar += "											<input type='text'";
	strVar += "												class=\"form-control validate[required] clsDatePicker fechaCreacionArchivoAntecedenteTabla\"";
	strVar += "												id=\"fechaCreacionArchivoAntecedenteTabla"+fila +"\"";
	strVar += "												name=\"fechaCreacionArchivo\"";
	strVar += "												placeholder='<spring:message code= 'bandejaDeEntrada.modal.subirDocumento.form.input.fechaDeCreacion.placeholder' />' \/>";
	strVar += "											<span class=\"input-group-addon\"> <span";
	strVar += "												class=\"glyphicon glyphicon-calendar\"><\/span>";
	strVar += "											<\/span>";
	strVar += "										<\/div>";
	strVar += "									<\/td>";
	strVar += "";
	strVar += "									<td><select";
	strVar += "										class=\"form-control validate[required] idAutorSubirDocumentoAntecedenteTabla\"";
	strVar += "										style=\"width: 100%\" name=\"idAutorSubirDocumento\"";
	strVar += "										id=\"idAutorSubirDocumentoAntecedenteTabla"+fila +"\">";
	strVar += "											<option value=\"\">Seleccione<\/option>";



							    	<c:forEach  items="${autoresDTO}"  var="autorDTO">

	strVar +=  "									 <option value='${autorDTO.idAutor}'>${autorDTO.nombreAutor}<\/option>";
									</c:forEach>

	strVar += "									<\/select><\/td>";	
	strVar += "									<td>";
	strVar += "";
	strVar += "										<span";
	strVar += "										id=\"spanSubirDocumentoAdjuntoExp"+fila+"\"";
	strVar += "										class=\"btn btn-success fileinput-button  btn-file btn-verde\"";
	strVar += "										data-toggle=\"tooltip\" data-placement=\"top\"";
	strVar += "										title=\"Subir Documento Adjunto\"> <span";
	strVar += "											class=\"fa fa-upload \"><\/span> <input";
	strVar += "											class=\"validate[required] archivoAntecedenteTabla\"";
	strVar += "											type=\"file\" id=\"archivoAntecedenteTabla"+fila +"\" name=\"archivo\" \/>";
	strVar += "									<\/span>";
	strVar += "									<\/td>";
	// strVar += "									<td>";
	// strVar += "									<\/td>";
	strVar += "								<\/tr>";

	 $("#tablaDocumentosAdjuntosLista").append(strVar);



	 // Metodos para recargar los valores de la tabla


	 $(function() {
	     $('.fechaCreacionArchivoAntecedenteTablaDiv').datetimepicker({
	           locale : 'es',
	           format : 'DD/MM/YYYY'
	     }).on("dp.show", function (e) {
	     	$(".table-responsive").css("padding-bottom", "207px");
	     })
	     .on("dp.hide", function (e) {
	     	$(".table-responsive").css("padding-bottom", "0px");
	     });
	     jQuery("#formSubirDocumento").validationEngine();
	 });

	 $(function() {
	     $('.fechaCreacionArchivoAntecedenteTabla').datetimepicker({
	           locale : 'es',
	           format : 'DD/MM/YYYY'
	     }).on("dp.show", function (e) {
	     	$(".table-responsive").css("padding-bottom", "175px");
	     })
	     .on("dp.hide", function (e) {
	     	$(".table-responsive").css("padding-bottom", "0px");
	     });
	     jQuery("#formSubirDocumento").validationEngine();
	 });

	 $('#fechaCreacionArchivoAntecedenteTabla'+fila).val(moment(new Date().getTime()).format('DD/MM/YYYY'));


	 
	 $(document).ready(function() {

	 	$(".idTipoDeDocumentoSubirAntecedenteTabla").select2({
	 	    theme: "bootstrap",
	 	    dropdownParent: $("#subirDocumentoAntecedenteTablaModal"),
	 	    language: "es"
	 	});
	 	  
	 	$(".idAutorSubirDocumentoAntecedenteTabla").select2({
	 		    theme: "bootstrap",
	 		    dropdownParent: $("#subirDocumentoAntecedenteTablaModal"),
	 		    language: "es"
	 		});
	 });



	 $(function () {
	 	var sacar = 0
	 	var notify = "";
	 	var tamanoDeArchivo;
	 	
	     $('.archivoAntecedenteTabla').fileupload({
	        dataType: 'json',
	        url: '/sgdp/guardarAdjuntoTabla',
	        add: function (e, data) {
				var descripcion = "#descripcionAntecedenteTabla" + (fila -1);	        	
	         	if ($("#descripcionAntecedenteTabla" + (fila -1)).validationEngine('validate') == false ||
	         		$("#idTipoDeDocumentoSubirAntecedenteTabla" + (fila -1)).validationEngine('validate') == false ||
	         	   	$("#numeroDocumentoAntecedenteTabla" + (fila -1)).validationEngine('validate') == false ||
	         	   	$("#fechaCreacionArchivoAntecedenteTabla" + (fila -1)).validationEngine('validate') == false ||
	         	   	$("#idAutorSubirDocumentoAntecedenteTabla" + (fila -1)).validationEngine('validate') == false )	{
	         		
		         		$("#descripcionAntecedenteTabla" + (fila -1)).validationEngine('validate');
		         		$("#idTipoDeDocumentoSubirAntecedenteTabla" + (fila -1)).validationEngine('validate');
		         		$("#numeroDocumentoAntecedenteTabla" + (fila -1)).validationEngine('validate');
		         		$("#fechaCreacionArchivoAntecedenteTabla" + (fila -1)).validationEngine('validate');
		         		$("#idAutorSubirDocumentoAntecedenteTabla" + (fila -1)).validationEngine('validate');
						return;
	         	}

	         	$.each(data.files, function (index, file) {	               
	                tamanoDeArchivo = file.size;            
	            });	

	         	if (tamanoDeArchivo<=0) {
	            	$("#spanSubirDocumentoAdjuntoExp"+ (fila -1)).validationEngine('showPrompt', '* El tamaño del archivo debe ser mayor a 0 bytes', 'error');
	            	return;
		        }         	
	         	
	         	var SubirAntecedenteDTO = new Array();   
	     		$($(this).parent().parent().parent()).each(function (colIndex, c) {
	            	console.log($(this).find("#descripcionAntecedenteTabla"+ (fila -1)).val());
    				console.log($(this).find("#idTipoDeDocumentoSubirAntecedenteTabla"+ (fila -1)).val());
    				console.log($(this).find("#numeroDocumentoAntecedenteTabla"+ (fila -1)).val()); 
    				console.log($(this).find("#fechaCreacionArchivoAntecedenteTabla"+ (fila -1)).val());
    				console.log($(this).find("#idAutorSubirDocumentoAntecedenteTabla"+ (fila -1)).val());
    				console.log(encodeURIComponent($(this).find('#idTipoDeDocumentoSubirAntecedenteTabla' + (fila -1) + ' :selected').text()));
	     				 SubirAntecedenteDTO.push({					
	     					descripcion : $(this).find("#descripcionAntecedenteTabla"+ (fila -1)).val(),
	     					idTipoDeDocumentoSubir :$(this).find("#idTipoDeDocumentoSubirAntecedenteTabla"+ (fila -1)).val(),	
	     					numeroDocumento : $(this).find("#numeroDocumentoAntecedenteTabla"+ (fila -1)).val(),
	     					fechaCreacionArchivo :$(this).find("#fechaCreacionArchivoAntecedenteTabla"+ (fila -1)).val(),	
	     					idAutorSubirDocumento : $(this).find("#idAutorSubirDocumentoAntecedenteTabla"+ (fila -1)).val(),
	     					idExpedienteSubirArchivo : $("#idExpedienteSubirArchivoAntecedenteTabla").val(),
	     					idInstanciaDeTareaSubirArchivo : $("#idInstanciaDeTareaSubirArchivoAntecedenteTabla").val(),
	     					cartaRelacionada : encodeURIComponent($("#cartaRelacionadaAntecedentesTabla").val()),
	     					tipoDeDocumento : encodeURIComponent($(this).find('#idTipoDeDocumentoSubirAntecedenteTabla' + (fila -1) + ' :selected').text())	     					
	     				});		
	             });  
     		 	console.log(SubirAntecedenteDTO); 
     			data.formData = {"subirArhivoDTO" : JSON.stringify(SubirAntecedenteDTO)};
             	data.submit();
	         },
	           progressall: function (e, data) {
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
	                 notify.update({'type': 'success', 'message': ' Se ha guardado correctamente el antecedente!', 'progress': progress});
	                 sacar = 0;
	             }else{
	                 notify.update({'type': 'success', 'message': ' <strong>Guardando</strong> No cerrar esta pagina ... ', 'progress': progress});
	             }
	             
	         },
	         complete : function(data){


	        	 console.log("data" + data);
	        	 console.log("data.toString()" + data.toString());
	        	 console.log("data.responseJSON: ", data.responseJSON);
	        	 
	          	if (data.responseJSON.cssStatus == "alert alert-danger"){
	          		
	          		 notify.update({'type': 'danger', 'message': ' Error al guardar !'});
	          		 setTimeout(function() {
	          	        notify.close();
	          	    }, 4500);

	         		$.notify({
	          			message: data.responseJSON.resultadoSubirArchivo
	          		},{
	          			type: 'danger'
	          		});

	 	
	 		   	}else{

	          		$.notify({
	          			message: 'Se ha guardado correctamente el documento Adjunto!'
	          		},{
	          			type: 'success'
	          		});
	 		   		/*
	 		   		$.notify({
	 				//	message: returnData.responseJSON.setResultadoSubirArchivo
	 				    message :"Se ingreso el antecedente correctamente"
	 				},{
	 					type: 'success'
	 				});
	 				*/
	 		      	 agregarDocumentoAdjunto ();
	 		      	 //$('.idTipoDeDocumentoSubirAntecedenteTabla').val($('.idTipoDeDocumentoSubirAntecedenteTabla > option:contains("Antecedente")').val()).change();
	 		      	 $(".idTipoDeDocumentoSubirAntecedenteTabla").val($('.idTipoDeDocumentoSubirAntecedenteTabla option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();
	 		      	 $('.idTipoDeDocumentoSubirAntecedenteTabla').attr('disabled', 'disabled');
	 		   	}	

	         	
	            // data.context.text('Upload finished.');
	            //$.notify("Upload finished");
	         }
	     });
	 });
	 	 
	 fila = fila + 1;
}


</script>

<!-- ------------------------------------ AYUDA PROCESO Y SUBPROCESO -------------------------------------- -->
	
	
<div class="modal fade" id="ayudaProcesoSubproceso"
	tabindex="-1" role="dialog" aria-labelledby="modalLabel"
	aria-hidden="true">
		
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="formAyudaProcesoSubproceso"
				action="${pageContext.request.contextPath}/crearExpediente"
				method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message
								code="boton.cerrar.signo" /></span> <span class="sr-only"><spring:message
								code="boton.cerrar.nombre" /></span>
					</button>
					<h3 class="modal-title" id="lineModalLabel">
						 Buscar Proceso
					</h3>

				</div>
				<div class="modal-body">				
					<div class="form-group">
						<spring:message
							code="bandejaDeEntrada.modal.crearExpediente.form.select.macroProceso.seleccione"
							var="macroProcesoSeleccione" />
						<label for="macroProceso"><spring:message
								code="bandejaDeEntrada.modal.crearExpediente.form.input.macroProceso.label" /></label>
						<select class="form-control validate[required]"
							style="width: 100%" name="idMacroProceso" id="idMacroProceso"
							onchange="cargarProcesos()">
							<!--  	<option value="" label='${macroProcesoSeleccione}' />	-->
							<option value="">${macroProcesoSeleccione}</option>
							<%-- <c:forEach items="<%=AppContextControl.getMacroProcesosDTO()%>"--%>
							<c:forEach items="${listaMacroProcesosDTO}" var="macroProcesoDTO">
								<!-- <option value="${macroProcesoDTO.idMacroProceso}" label="${macroProcesoDTO.nombreMacroProceso}" />  -->
								<option value="${macroProcesoDTO.idMacroProceso}">
									${macroProcesoDTO.nombreMacroProceso}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="proceso"><spring:message
								code="bandejaDeEntrada.modal.crearExpediente.form.input.proceso.label" /></label>
						<select class="form-control validate[required]"
							style="width: 100%" id="idProceso" name="idProceso"
							onchange="buscarNumeroTareas(1)">
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<div class="form-group">

						<div class=" col-sm-offset-6 col-sm-6">
							<button type="button" id="buttonAyudaProcesoSubproceso"
								onclick="asignarProceso()"
								class="btn btn-primary btn-lg btn-block">
								 Asignar
								<span class="glyphicon glyphicon-chevron-right"></span>
							</button>

						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script type="text/javascript">

$(document).ready(function(){
	$('#subirDocumentoAntecedenteTablaModal').on('show.bs.modal', function (e) {
		console.log("En show modal de subirDocumentoAntecedenteTablaModal");
		//$('.idTipoDeDocumentoSubirAntecedenteTabla').val($('.idTipoDeDocumentoSubirAntecedenteTabla > option:contains("Antecedente")').val()).change();
	   	$(".idTipoDeDocumentoSubirAntecedenteTabla").val($('.idTipoDeDocumentoSubirAntecedenteTabla option').filter(function () { return $(this).html() == "Antecedente"; }).val()).change();
	   	$('.idTipoDeDocumentoSubirAntecedenteTabla').attr('disabled', 'disabled');
	});
});

$("#idProceso").select2({
    theme: "bootstrap",
    dropdownParent: $("#ayudaProcesoSubproceso"),
    language: "es"
});

$("#idMacroProceso").select2({
    theme: "bootstrap",
    dropdownParent: $("#ayudaProcesoSubproceso"),
    language: "es"
});

function dibujarProcesoModal(){
	console.log('$("#idProcesosVigente").val(): ' + $("#idProcesosVigente").val())
	if ($("#idProcesosVigente").val() <= 0) {
		bootbox.alert("Por favor seleccione un SubProceso... ");
	} else {
		var urlDibujaProceso = "http://${urlFuncPhp}/proceso/indexp.php?idproc="+$("#idProcesosVigente").val();
		console.log("urlDibujaProceso: " + urlDibujaProceso);
        $.get("${sessionURL}", function(haySession){
            console.log("haySession: " + haySession);
            if(haySession){
                 window.open(urlDibujaProceso, "Proceso", 'width=1080, height=600');
            }else{
                  bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                                , function(){
                                      window.open('${raizURL}', '_blank');
                                }
                   );
            }
     	});			 
	}
 }
 
</script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>
<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>


<form class="form-horizontal" id="formApiDocDeDistribucion">
	<div id="divUsuaDocDig"  style="margin-top:49px;">
		<label class="control-label col-sm-3" for="usuariFor">Usuario:</label>
		<div class="col-sm-9" style="padding-bottom: 10px;" >
			<select class="form-control select2-usuario-doc validate[required]"
				multiple="multiple" name="usuarioDocDigSelect2"
				id="usuarioDocDigSelect2">
				<option value="">Seleccione Usuarios</option>
				<c:forEach items="${listaUsuariosEntidad}" var="usuario">
					<option value="${usuario.idUsuario}">${usuario.emailUsuario}</option>
				</c:forEach>
			</select>
		</div>
		<label class="control-label col-sm-3" for="usuariFor">Reservado:</label>
		<div class="col-sm-9" style="padding-bottom: 10px;" >
		    <input class="form-control"
                id="condifencialDocdigital"
                type="checkbox"
                name="condifencialDocdigital">
		</div>
		<div id="seleccionarArchivosApiDoc"></div>
	</div>

</form>

		<div class="form-group col-sm-12">
			<div class="col-sm-12">
				<div class="pull-rigth">
					<button id="botonGuardaDatosDeDistribucion"
						onclick="guardaDatosDeDistribucionApiDoc()" type="button"
						class="btn btn-labeled btn-primary">
						Guardar selecci&oacute;n <span class="btn-label-default"><i
							class="glyphicon glyphicon-saved"></i></span>
					</button>
				</div>
			</div>

		</div>






	<script>
		var inicializaSelect2UsuariosDocDig = function() {
			$(".select2-usuario-doc").select2({
				theme : "bootstrap",
				dropdownParent : $("#divUsuaDocDig"),
				language : "es"
			});
		};

		function guardaDatosDeDistribucionApiDoc() {

			$("#correosHiden").val("apiDoc");
			console.log("guardar apiDoc correosHiden: "+$("#correosHiden").val());
			var validaForm = $("#formApiDocDeDistribucion")
					.validationEngine('validate');
			console.log("valida apiDoc validaForm: "+validaForm);

			var idArchivosADistribuirArray = [];
			$('input[name="idArchivosADistribuirHiden"]').each(function() {
				idArchivosADistribuirArray.push(this.value);
			});
			if (idArchivosADistribuirArray.length == 0) {
				$("#seleccionarArchivosApiDoc")
						.validationEngine(
								'showPrompt',
								'Por favor seleccione al menos un archivo para distribuir',
								'error');
			} else if (validaForm == true) {
				console.log("apiDoc ingresa a valida Form : "+validaForm);
				$("#validoFormDistribucionApi").val("true");
				$("#botonAbrirCorreoDeDistribucionModal").removeClass(
						"btn-warning");
				$("#botonAbrirCorreoDeDistribucionModal").addClass(
						"btn-primary");
				$("#estadoFormCorreoDeDistribucionModal").removeClass(
						"glyphicon-ban-circle");
				$("#estadoFormCorreoDeDistribucionModal").addClass(
						"glyphicon-ok-circle");
				if ($(".botonAbrirCorreoDeDistribucionModalformError").length) {
					$(".botonAbrirCorreoDeDistribucionModalformError").remove();
				}
				//$('#correoDeDistribucionModal').modal('hide');
				$("#correoDeDistribucionModal .close").click();
			}
		}

		$(document).ready(function() {
			$(inicializaSelect2UsuariosDocDig);
		});
	</script>

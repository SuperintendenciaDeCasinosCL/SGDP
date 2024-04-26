<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<form class="form-horizontal" id="formApiDocDeDistribucionEntidad">
<div id="divEntidadDocDig">

	<label class="control-label col-sm-3" for="entidadFor">Entidad:</label>
	<div class="col-sm-9">
	<select class="form-control select2-entidad-doc validate[required]"
		name="entidadesDocDigSelect2" id="entidadesDocDigSelect2">
		<option value="">Seleccione Entidad</option>
		<c:forEach items="${listaEntidades}" var="entidad">
			<option value="${entidad.idEntidad}">${entidad.nombreEntidad}</option>
		</c:forEach>
	</select>
	</div>
</div>
</form>


<div id=divUauriosEntidades></div>



<script>
	var inicializaSelect2EntidadesDocDig = function() {
		$(".select2-entidad-doc").select2({
			theme : "bootstrap",
			dropdownParent : $("#divEntidadDocDig"),
			language : "es"
		});
	};

	$(document).ready(function() {
		$(inicializaSelect2EntidadesDocDig);
	});

	$('.select2-entidad-doc').on('change', function() {
		$("#detalleDeExpedienteEnDistribucionModal").css("position", "relative").prepend($("<div />").addClass("cargando"));
		  var idEntidad = this.value;
		  console.log("idEntidad: "+ idEntidad)

		  $("#divUauriosEntidades").load("${pageContext.request.contextPath}"+"/getUsuariosEntidades/"+idEntidad, function() {
			$("#detalleDeExpedienteEnDistribucionModal").find(".cargando").remove();
			});

		 // $("#divUauriosEntidades").load("${pageContext.request.contextPath}"+"/getUsuariosEntidades/"+idEntidad);

	});

 </script>

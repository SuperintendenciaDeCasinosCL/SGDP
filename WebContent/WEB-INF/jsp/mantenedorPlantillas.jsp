<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>

</head>

<body>

	<div class="container-fluid container-sgdp">

		<div class="row content">

			<div class="col-sm-2 sidenav">
				<c:import url="/WEB-INF/jsp/objetos/menu.jsp"></c:import>
			</div>

			<div class="col-sm-10">


				<c:set var="colorBackHeaderIndicador" value="#41CAC0" />
				<c:set var="mensajeIndicador" value="Plantilla de documentos" />


				<div id="divBackHeaderIndicador" class="row div-area-trabajo-cab"
					style="background-color: ${colorBackHeaderIndicador}; color: #fff;">

					<div class="col-sm-1"></div>

					<div class="col-sm-9">
						<h2 id="h2MensajeIndicador">${mensajeIndicador}</h2>
					</div>

					<div class="col-sm-2">
						<c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import>
					</div>

				</div>
				
				<div class="col-sm-12" id="IndicadorCuerpo">

					<div id="app"></div>
					<script type="text/javascript"	src='<c:url value="https://cdn.jsdelivr.net/npm/vue@2.6.14" />'></script>
					<script type="text/javascript"	src='<c:url value="https://unpkg.com/vuex@3.6.2/dist/vuex.js" />'></script>
					<script type="text/javascript"	src='<c:url value="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js" />'></script>
					
 					<script type="text/javascript"	src='<c:url value="js/plantillas/services/dataService.js" />'></script>

					
					<script type="text/javascript"	src='<c:url value="js/plantillas/components/Container.js" />'></script>
					<script type="text/javascript"	src='<c:url value="js/plantillas/components/Formulario.js" />'></script>
					<script type="text/javascript"	src='<c:url value="js/plantillas/components/PreForm.js" />'></script>

					<script type="text/javascript"	src='<c:url value="js/plantillas/main.js" />'></script>

				</div>
			</div>

		</div>

	</div>


</body>

<script>

</script>

</html>


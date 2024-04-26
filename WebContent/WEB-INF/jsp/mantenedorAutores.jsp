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
					<c:choose>
						<c:when test="${usuario.fueraDeOficina eq true}">
							<c:set var="colorBackHeaderMantenedorAutores" value="#FFD51D" />
							<c:set var="mensajeMantenedorAutores"
								value="Mantenedor de Autores (Fuera de Oficina)" />
						</c:when>
						<c:otherwise>
							<c:set var="colorBackHeaderMantenedorAutores" value="#41CAC0" />
							<c:set var="mensajeMantenedorAutores" value="Mantenedor de Autores" />
						</c:otherwise>
					</c:choose>
					<div id="divBackHeaderMantenedorAutores"
						class="row div-area-trabajo-cab"
						style="background-color: ${colorBackHeaderMantenedorAutores}; color: #fff;">
						<div class="col-sm-1"></div>
						<div class="col-sm-9">
							<h2 id="h2MensajeMantenedorAutores">${mensajeMantenedorAutores}</h2>
						</div>
						<div class="col-sm-2">
							<c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import>
						</div>
					</div>
					<hr>
					<div class="col-sm-12" id="mantenedorAutoresCuerpo">
						<c:import url="/WEB-INF/jsp/div/mantenedorAutoresCuerpo.jsp"></c:import>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>


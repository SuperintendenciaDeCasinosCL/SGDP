<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<div class="col-sm-16">
	<select class="form-control usuarios-asignados" name="idUsuario${instanciaTarea}" 	id="${instanciaTarea}">
		<option selected="selected" value="">Seleccione Usuario</option>
		<c:forEach
			items="${listaPosiblesUsuariosUnidad}" var="posibleUsuario">
			<option value="${posibleUsuario}">${posibleUsuario}</option>
		</c:forEach>
		<c:forEach
 			items="${listaPosiblesUsuariosUnidadFueraOf}" var="posibleUsuarioFO"> 
 			<option value="${posibleUsuarioFO}" disabled>${posibleUsuarioFO} 
				(Fuera de oficina)</option>
		</c:forEach>
	</select>
</div>
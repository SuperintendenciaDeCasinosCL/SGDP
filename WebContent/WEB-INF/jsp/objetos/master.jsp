<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="permisoSubirCarta" value="<%=PermisoType.SUBIR_CARTA.getNombrePermiso()%>"/>

<html>

	<head>

		<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>
	
	</head>

	<body>
		
		<div class="container-sgdp">
		
			<div id="wrapper">
				
				<c:import url="/WEB-INF/jsp/objetos/menu.jsp"></c:import>
			
				<c:choose>
				    <c:when test="${param.pagina eq 'bandejaEntrada'}">
				        <c:import url="/WEB-INF/jsp/bandejaEntrada.jsp"></c:import>
				    </c:when>					   
				</c:choose>
			
			</div>
			
		</div>		
		
	</body>
	
</html>
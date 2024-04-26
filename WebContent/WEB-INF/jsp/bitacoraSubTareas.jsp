<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="idInstTarea" value="idInstTarea"/>
<c:set var="idEstadoTarea" value="idEstadoTarea"/>

<html>

<head>

</head>

<body>
<input type="hidden" id="idInstTarea" value="<%= request.getAttribute("idInstTarea")%>" />
<input type="hidden" id="idEstadoTarea" value="<%= request.getAttribute("idEstadoTarea")%>" />

	
				<div class="col-sm-12" id="IndicadorCuerpo">
					<div id="bitacora">
					</div>
					
					<script type="text/javascript"	src='<c:url value="https://cdn.jsdelivr.net/npm/vue@2.6.14" />'></script>
					<script type="text/javascript"	src='<c:url value="https://unpkg.com/vuex@3.6.2/dist/vuex.js" />'></script>
					<script type="text/javascript"	src='<c:url value="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js" />'></script>
					
					<script type="text/javascript" src='<c:url value="/js/bitacora/components/ListaBitacora.js" />'></script>
					<script type="text/javascript" src='<c:url value="/js/bitacora/components/ContenedorBitacora.js" />'></script>
					<script type="text/javascript" src='<c:url value="/js/bitacora/components/FormularioBitacora.js" />'></script>			
					<script type="text/javascript" src='<c:url value="/js/bitacora/mainBitacora.js" />'></script>
				</div>

</body>

<script>

</script>

</html>


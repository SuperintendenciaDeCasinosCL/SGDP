<%@ page import= "cl.gob.scj.sgdp.tipos.EstadoDeTareaType" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="codigoEstadoDeTareaTypeAsignada" value="<%=EstadoDeTareaType.ASIGNADA.getCodigoEstadoDeTarea()%>"/>

<c:forEach items="${etapasDeInstanciaDeProcesoDTO}" var="etapaDeInstanciaDeProcesoDTO" varStatus="status">

	<c:choose>
		<c:when test="${etapaDeInstanciaDeProcesoDTO.codigoEstadoDeTarea eq codigoEstadoDeTareaTypeAsignada}">			
			<span class="btn btn-danger btn-xs">${etapaDeInstanciaDeProcesoDTO.nombreEtapa}</span>						
		</c:when>
		<c:otherwise>		
			<span class="btn btn-default btn-xs">${etapaDeInstanciaDeProcesoDTO.nombreEtapa}</span>  				
		</c:otherwise>	
	</c:choose>	  

	<c:if test="${not status.last}">
		<span class="glyphicon glyphicon-arrow-right"></span>
	</c:if>  			

</c:forEach>
  
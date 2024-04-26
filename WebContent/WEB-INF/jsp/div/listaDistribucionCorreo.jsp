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
	<div id="divCorresDistribuir" >
		<select id="correosDeDistribucion"
								class="form-control select2-correos-de-distribucion validate[required]"
								multiple="multiple">
								<c:forEach items="${listaDistribucion}"
									var="listaDeDistribucionDTO">
									<option value="${listaDeDistribucionDTO.email}">${listaDeDistribucionDTO.email}
										(${listaDeDistribucionDTO.organizacion} -
										${listaDeDistribucionDTO.cargo})</option>
								</c:forEach>
							</select>
	</div>		
</form>






	<script>
	

		var inicializaSelect2CorreosDeDistribucion = function(){
			$(".select2-correos-de-distribucion").select2({
				    theme: "bootstrap",
		 		    dropdownParent: $("#divCorresDistribuir"),
		 		    language: "es"
		 		});
		};
		

		$(document).ready(function() {
			$(inicializaSelect2CorreosDeDistribucion);
		});
	</script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<form class="form-horizontal" id="formUnidadesporOperativa">	
	<div class="form-group">  		  		
  		<div id="divUnidades" class="col-sm-12">  			
  			<select class="form-control select2-crea-unidad validate[required]" name="creaUnidad"
				id="creaUnidad">
				<option value="">Seleccione Unidad</option>
				<c:forEach items="${listaUnidades}" var="unidadDTO">
					<option value="${unidadDTO.idUnidad}">${unidadDTO.nombreCompletoUnidad}</option>
				</c:forEach>
			</select>
  		</div>  		
  	</div>	  	
</form>
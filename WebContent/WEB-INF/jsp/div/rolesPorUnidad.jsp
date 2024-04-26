<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<form class="form-horizontal" id="formRolesPorUnidad">	
	<div class="form-group">  		  		
  		<div id="divCargos" class="col-sm-12">  			
  			<select class="form-control select2-cargo validate[required]" name="creaRol"
				id="creaRol">
				<option value="">Seleccione Cargo</option>
				<c:forEach items="${rolesDTOUnidad}" var="rolDTO">
					<option value="${rolDTO.idRol}">${rolDTO.nombreRol}</option>
				</c:forEach>
			</select>
  		</div>  		
  	</div>	  	
</form>

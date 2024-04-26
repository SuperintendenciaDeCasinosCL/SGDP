<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<form class="form-horizontal" id="formUnidadesporOperativaUsuario">	
	<div class="form-group" id="divFormUnidadEdita">  
		
		<label class="control-label col-sm-3" id="editaUnidadLabel" for="editaUnidad">Unidad (*): </label>	  		
  		<div id=divUnidadesUsuEdita class="col-sm-9">  			
  			<select class="form-control select2-unidad-edita validate[required]" name="editaUnidad"
				id="editaUnidad">
				<option value="">Seleccione Unidad</option>
				<c:forEach items="${listaUnidades}" var="unidadDTO">
					<option value="${unidadDTO.idUnidad}">${unidadDTO.nombreCompletoUnidad}</option>
				</c:forEach>
			</select>
  		</div>  		
    	</div> 
    	
    	<div class="form-group" id="divFormCargo"> 
  		<label class="control-label col-sm-3" id="creaCargoLabel" for="editaCargoForm">Cargo (*): </label>
  		<div class="col-sm-9" id="divCargosEdita">  			
			<select class="form-control select2-crea-cargo-a" name="editaCargo"
				id="editaCargo">
				<option value="">Seleccione Cargo</option>
			</select>
  		</div>  		
  	</div>	  
</form>




<script>

$(document).ready(function() {
	$(inicializaSelect2UnidadUsu);
});

var inicializaSelect2UnidadUsu = function() {
	$(".select2-unidad-edita").select2({
		theme : "bootstrap",
		dropdownParent : $("#divUnidadesUsuEdita"),
		language : "es"
	});
};


$('.select2-unidad-edita').on('change', function() {	
	  var idUnidad = this.value;	  
	   console.log("idUnidadOperativa: "+ idUnidad)
	  $("#divCargosEdita").load("${pageContext.request.contextPath}"+"/getRolesPorUnidadEdita/"+ idUnidad);
	  
});

</script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<form class="form-horizontal" id="formUnidadesporOperativaUsuario">	
	<div class="form-group" id="divFormUnidad">  
		
		<label class="control-label col-sm-3" id="creaCargoLabel" for="creaUnidad">Unidad (*): </label>	  		
  		<div id=divUnidadesUsu class="col-sm-9">  			
  			<select class="form-control select2-unidad-cargo validate[required]" name="creaUnidad"
				id="creaUnidad">
				<option value="">Seleccione Unidad</option>				
			</select>
  		</div>  		
    	</div> 


	<div class="form-group" id="divFormCargo"> 
  		<label class="control-label col-sm-3" id="creaCargoLabel" for="creaCargo">Cargo (*): </label>
  		<div class="col-sm-9" id="divCargos">  			
			<select class="form-control select2-crea-cargo-a" name="creaRol"
				id="creaRol">
				<option value="">Seleccione Cargo</option>
			</select>
  		</div>  		
  	</div>	  

  </form>
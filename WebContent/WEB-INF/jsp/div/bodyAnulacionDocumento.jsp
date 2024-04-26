<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<div class="container">

	<div class="row">
		<div class="col-sm-6">
			<form action="#">
				<div class="col-sm-12">
					<div id="form-group-txt-code-expedient" class="form-group">
						<label for="txt-code-expedient" class="col-sm-3">Codigo Expediente</label>
						<input id="txt-code-expedient" type="text" class="form-control" name="code" placeholder="Ej: EXP-202-054">
						<span id="error-code-expedient" class="help-block text-danger">Debe ingresar un codigo de expediente valido.</span>
					</div>
				</div>
				<div class="col-sm-12">
					<div id="form-group-btn-search" class="form-group">
						<button id="btnSearch" type="button" class="btn btn-primary btn-lg">Buscar</button>
					</div>
				</div>
				
			</form>
		</div>
	</div>
	<br>
	
	<div class="row">
		<div class="table-responsive col-sm-12">
			<table id="tableReport" class="table table-striped table-bordered table-hover table-responsive" style="width: 100%;">
				<thead>
					<tr>
						<th>Expediente</th>
						<th>Documento</th>
						<th>Nombre Tipo Documento</th>					
						<th>Nombre Tarea</th>
						<th>Fecha Subida</th>
						<th>Activo</th>
						<th></th>
					</tr>
				</thead>
				
				<tfoot>
					<tr>
						<th>Expediente</th>
						<th>Documento</th>
						<th>Nombre Tipo Documento</th>					
						<th>Nombre Tarea</th>
						<th>Fecha Subida</th>
						<th>Activo</th>
						<th></th>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</div>
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
					<div id="form-group-dateFrom" class="form-group">
						<label for="div-date-from">Fecha Desde</label>
						<div id="div-date-from" class="input-group date">
							<input id="txt-date-from" type="text" class="form-control" placeholder="Ej: 12/03/2022" value="12/03/2022" />
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
						<span id="error-date-from" class="help-block text-danger">Debe ingresar una Fecha Desde valida.</span>
						<span id="error-date-from-major" class="help-block text-danger">La fecha desde debe ser menor que la fecha hasta.</span> 
					</div>
				</div>
				<div class="col-sm-12">
					<div id="form-group-date-to" class="form-group">
						<label for="txt-date-to">Fecha Hasta</label>
						<div id="div-date-to" class="input-group date">
							<input id="txt-date-to" type="text" class="form-control" name="code" placeholder="Ej: 12/03/2022" value="12/03/2022" />
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
						<span id="error-date-to" class="help-block text-danger">Debe ingresar una Fecha Hasta valida.</span>
					</div>
				</div>
				<div class="col-sm-12">
					<div id="form-group-dateFrom" class="form-group">
						<label for="txt-filter-text" class="col-sm-3">Texto de Filtro</label>
						<input id="txt-filter-text" type="text" class="form-control" name="code" placeholder="Ingrese un texto para filtrar el reporte">
						<span id="error-filter-text" class="help-block text-danger">Debe ingresar un texto de filtro valido.</span>
					</div>
				</div>
				<div class="col-sm-12">
					<div id="form-group-dateFrom" class="form-group">
						<button id="btnSearch" type="button" class="btn btn-primary btn-lg">Buscar</button>
					</div>
				</div>
			</form>
		</div>
			
	</div>
	<br>
	
	<div class="row">
		<div class="table-responsive col-sm-12">
			<table id="tablaReportLogDocument" class="table table-striped table-bordered table-hover table-responsive" style="width: 100%;">
				<thead>
					<tr>
						<th>ID</th>
						<th>Tipo Operacion</th>
						<th>Modulo</th>	
						<th>Fecha</th>					
						<th>Usuario</th>
						<th>IP</th>
						<th>Expediente</th>
						<th>Documento</th>
						<th></th>
					</tr>
				</thead>
				
				<tfoot>
					<tr>
						<th>ID</th>
						<th>Tipo Operacion</th>
						<th>Modulo</th>	
						<th>Fecha</th>					
						<th>Usuario</th>
						<th>IP</th>
						<th>Expediente</th>
						<th>Documento</th>
						<th></th>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />
<script type="text/javascript" src="js/reportLogDocument/logDocument.js" ></script>


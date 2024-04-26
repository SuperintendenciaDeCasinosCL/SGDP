<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="col-sm-5 col-sm-offset-4 col-md-4 col-md-offset-4">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><spring:message code="login.mensajeBienvenida"/></h3>
		</div>
		<div class="panel-body">
			<form name='f' action="j_spring_security_check" method='POST'>
				<fieldset>
					<div class="form-group">
						<input class="form-control" placeholder='<spring:message code="login.form.nombreUsuario.placeholder"/>'
							name="idUsuario" id="idUsuario" onblur="cargarRolesUsuario()" />
					</div>
					<div class="form-group">					
						<select class="form-control" id="idRolUsuarioSeleccionado" name="idRolUsuarioSeleccionado">
						</select>						
					</div>
					<div class="form-group">
						<input class="form-control claveInput" placeholder='<spring:message code="login.form.clave.placeholder"/>'
							name="clave"
							type="password" value="" />
					</div>					
					<input name="submit" type="submit" value='<spring:message code="login.form.botonIngresar.value"/>'
						class="btn btn-lg btn-primary btn-block" />
				</fieldset>
			</form>
			<c:if test="${param.error == 'errorCredenciales'}">
				<span style="color: red;"><spring:message code="login.error.credenciales"/></span>
			</c:if>
			<c:if test="${param.error == 'accesoDenegado'}">
				<span style="color: red;"><spring:message code="login.error.acceso"/></span>
			</c:if>
		</div>
		<!-- Cierre Panel -->
	</div>
	<!-- Cierre Panel -->
</div>

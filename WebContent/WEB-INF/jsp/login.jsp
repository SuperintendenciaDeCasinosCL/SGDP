<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>

<script type="text/javascript" src='<c:url value="/js/login.js" />'>	
</script>

<link href='<c:url value="css/loginForm.css"/>' rel="stylesheet">

</head>

<body>

	<p hidden>[16.12][20.18]</p>

	<div class="container">
	    <div class="row">
	        <div class="col-md-4 col-md-offset-8">
	            <div class="panel panel-default">
	                <div class="panel-heading">	                		                     
	                </div>
	                <div class="panel-body">
	                    <form class="form-horizontal" id="loginForm" accept-charset="UTF-8" role="form" name="f" action="j_spring_security_check" method="POST">
		                    <div class="form-group">
		                        <label for="idUsuario" class="col-sm-3 control-label">
		                            <spring:message code="login.form.input.nombreUsuario.nombre"/></label>
		                        <div class="col-sm-9">
		                            <input class="form-control validate[required]" placeholder='<spring:message code="login.form.input.nombreUsuario.placeholder"/>'
								name="idUsuario" id="idUsuario" onblur="cargarRolesUsuario()" />
		                        </div>
		                    </div>		                   
		                    <div class="form-group">
		                        <label for="clave" class="col-sm-3 control-label">
		                            <spring:message code="login.form.input.clave.nombre"/></label>
		                        <div class="col-sm-9">
		                           <input class="form-control validate[required]" placeholder='<spring:message code="login.form.input.clave.placeholder"/>'
								name="clave"
								type="password" value="" />
		                        </div>
		                    </div>
		                    <div class="form-group hide" id="divRolUsuarioSeleccionado">
		                    	 <label for="idRolUsuarioSeleccionado" class="col-sm-3 control-label">
		                            <spring:message code="login.form.input.rol.nombre"/></label>
		                        <div class="col-sm-9">
		                           <select class="form-control validate[required]" id="idRolUsuarioSeleccionado" name="idRolUsuarioSeleccionado">
									</select>
		                        </div>
		                    </div>	                    
		                    <div class="form-group last">
		                        <div class="col-sm-offset-3 col-sm-9">
		                            <button type="button" onclick="enviarFormLogin()" class="btn btn-primary btn-md">
		                                <spring:message code="login.form.botonIngresar.value"/></button>
		                            <button type="reset" class="btn btn-default btn-md">
                                		<spring:message code="login.form.botonReset.value"/></button>		                                 
		                        </div>
		                        
		                        <br>		                        
		                      	
		                      	<div class="col-sm-offset-3 col-sm-9 text-danger">							    
							    <c:choose>
								    <c:when test="${not empty param.error and param.error eq 'errorCredenciales'}">								    	
								        	<spring:message code="login.error.credenciales"/>								  
								    </c:when>
								    <c:when test="${not empty param.error and param.error eq 'accesoDenegado'}">								        
								        	<spring:message code="login.error.acceso"/>								        
								    </c:when>
								    <c:when test="${not empty param.error and param.error eq 'errorArchivoQR'}">								        
								        	<spring:message code="login.error.archivoQR"/>								        
								    </c:when>
								    <c:otherwise>
								    	<c:if test="${not empty param.error}">
								    		${param.error}
								    	</c:if>
								    </c:otherwise>
								</c:choose>		                            		                                 
		                        </div>
		                    </div>
	                    </form>
	                </div>	              
	            </div>
	        </div>
	    </div>
	</div>

</body>
</html>
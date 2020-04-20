<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="bandejaDeEntrada" var="bandejaDeEntradaURL">
   <c:param name="linkActivo" value="bandejaDeEntrada" />
</c:url>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERROR al cargar detalle de la tarea</title>
<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                    Oops!</h1>
                <h2>
                    ERROR al cargar detalle de la tarea</h2>
                <div class="error-details">
                   La tarea ya no est&aacute; en su bandeja
                </div>
                <div class="error-actions">
                  <a href="${bandejaDeEntradaURL}" class="btn btn-primary btn-lg">
                  <i class="glyphicon glyphicon-inbox"></i> <spring:message code="menu.principal.link.nombre.bandejaDeEntrada"/></a>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
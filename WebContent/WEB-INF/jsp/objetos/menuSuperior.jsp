<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

 <ul class="nav navbar-top-links navbar-right">
      <!-- /.dropdown -->
      <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <%-- <i class="fa fa-user fa-fw"></i>--%>${usuario.idUsuario} <i class="fa fa-caret-down"></i>
          </a>
          <ul class="dropdown-menu dropdown-user">                       
              <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i><spring:message code="menu.principal.salir.nombre"/></a>
              </li>
          </ul>
          <!-- /.dropdown-user -->
      </li>
      <!-- /.dropdown -->
  </ul>
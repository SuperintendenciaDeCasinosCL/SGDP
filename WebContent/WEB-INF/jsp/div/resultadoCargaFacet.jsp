<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	
	<c:if test="${mensajeVistaDTO.mensajes ne null and !empty mensajeVistaDTO.mensajes}">
		<div class="col-sm-12" id="mensajeria">
			<c:import url="/WEB-INF/jsp/div/mensajeria.jsp"></c:import>
		</div>
	</c:if>
	
	<c:if test="${respuestaCargaFacetDTO ne null}">
	
		<div class="col-sm-3 menuBusquedaOrden">

		    <ul class="list-group">
		
		        <c:if test="${not empty respuestaCargaFacetDTO.facetSubProcesos}">
		
		            <li class="list-group-item">SubProceso
		                <ul class="list-group tagsProceso">
		                    <c:forEach var="entry"
		                        items="${respuestaCargaFacetDTO.facetSubProcesos}">                      
		                        <li
		                            class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
		                            onclick="busquedaInformacionConFiltro('${entry.key}',1);">
		                            ${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
		                        </li>
		                    </c:forEach>
		                </ul> <a class="cursorPuntero hidden" id="procesoVerMenos"
		                onclick="OcultarProceso()">Ver menos</a> <a
		                class="pull-right cursorPuntero hidden" id="procesoVerMas"
		                onclick="verProceso()">Ver mas</a>
		
		            </li>
		
		        </c:if>
		
		        <c:if test="${not empty respuestaCargaFacetDTO.facetMaterias}">
		
		            <li class="list-group-item">Materia
		                <ul class="list-group tagMateria">
		                    <c:forEach var="entry"
		                        items="${respuestaCargaFacetDTO.facetMaterias}">
		                        <li
		                            class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
		                            onclick="busquedaInformacionConFiltro('${entry.key}',2);">
		                            ${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
		                        </li>
		                    </c:forEach>
		
		                </ul> <a class="cursorPuntero hidden" id="materiaVerMenos"
		                onclick="OcultarMaterias()">Ver menos</a> <a
		                class="pull-right cursorPuntero hidden" id="materiaVerMas"
		                onclick="verMaterias()">Ver mas</a>
		            </li>
		
		        </c:if>
		
		        <c:if test="${not empty respuestaCargaFacetDTO.facetAutores}">
		
		            <li class="list-group-item">Autor
		                <ul class="list-group tagAutor">
		                    <c:forEach var="entry"
		                        items="${respuestaCargaFacetDTO.facetAutores}">
		                        <li
		                            class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
		                            onclick="busquedaInformacionConFiltro('${entry.key}',3);">
		                            ${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
		                        </li>
		                    </c:forEach>
		                </ul> <a class="cursorPuntero hidden" id="autorVerMenos"
		                onclick="OcultarAutor()">Ver menos</a> <a
		                class="pull-right cursorPuntero hidden" id="autorVerMas"
		                onclick="verAutor()">Ver mas</a>
		
		            </li>
		
		        </c:if>
		
		        <c:if test="${not empty respuestaCargaFacetDTO.facetCreadores}">
		
		            <li class="list-group-item">Creador
		                <ul class="list-group tagCreador">
		                    <c:forEach var="entry"
		                        items="${respuestaCargaFacetDTO.facetCreadores}">
		                        <li
		                            class="list-group-item cursorPuntero menubusquedaEspacioContenedores"
		                            onclick="busquedaInformacionConFiltro( '${entry.key}',4);">
		                            ${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
		                        </li>
		                    </c:forEach>
		                </ul> <a class="cursorPuntero hidden" id="creadorVerMenos"
		                onclick="OcultarCreador()">Ver menos</a> <a
		                class="pull-right cursorPuntero hidden" id="creadorVerMas"
		                onclick="verCreador()">Ver mas</a>
		
		            </li>
		
		        </c:if>
		
		        <c:if test="${not empty respuestaCargaFacetDTO.facetFechasCreacion}">
		
		            <li class="list-group-item">Fecha Creaci&oacute;n
		                <ul class="list-group tagFechaCreacion">
		                    <c:forEach var="entry"
		                        items="${respuestaCargaFacetDTO.facetFechasCreacion}">
		                        <li class="list-group-item cursorPuntero"
		                            onclick="filterColumn(${indiceColumnaFechaCreacion}, '${entry.key}');">
		                            ${entry.key}<span class="badge menubusquedatamaño">${entry.value}</span>
		                        </li>
		                    </c:forEach>
		                </ul> <a class="cursorPuntero hidden" id="creacionVerMenos"
		                onclick="OcultarCreacion()">Ver menos</a> <a
		                class="pull-right cursorPuntero hidden" id="creacionVerMas"
		                onclick="verCreacion()">Ver mas</a>
		
		            </li>
		
		        </c:if>
		
		    </ul>

		</div>
	
	</c:if>

</div>
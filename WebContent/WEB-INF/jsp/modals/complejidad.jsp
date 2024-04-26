<%@ page contentType="text/html;encoding=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cl.gob.scj.sgdp.control.AppContextControl"%>

<div class="modal fade" id="complejidadModal" role="dialog" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">	
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <spring:message code="boton.cerrar.signo"/>
                </button>
                <h4 id="complejidadH4" class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="formComplejidadModal">
                    <div id="complejidadDiv" class="panel-body">				
                        <div class="form-group">
                            <spring:message
                                code="bandejaDeEntrada.modal.crearExpediente.form.select.complejidad.seleccione"
                                var="complejidadSeleccione" />
                            <label for="complejidad">
                            <spring:message 
                                code="bandejaDeEntrada.modal.crearExpediente.form.input.complejidad.label" /></label>
                                <select class="form-control" style="width: 100%" name="idComplejidad" id="idComplejidad">
									
								</select>
                        </div>

                        <div class="form-group">
                            <label for="motivoComplejidad"><spring:message
                                    code="bandejaDeEntrada.modal.crearExpediente.form.input.motivoComplejidad.label" /></label>
                            <input type="text" class="form-control validate[required]" maxlength="1000"
                                   id="motivoComplejidad" name="motivoComplejidad"
                                   placeholder='<spring:message code="bandejaDeEntrada.modal.crearExpediente.form.input.motivoComplejidad.placeholder"/>'>
                        </div>

                        <input type="hidden" id="nombreExpedienteDesdeComplejidad" />
                        <input type="hidden" id="recargaBusquedaDesdeComplejidad" />

                        <div class="form-group">							
                            <button id="guardarComplejidad" type="button" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok font-icon-1"></span> Actualizar complejidad
                            </button>
                        </div>
                    </div>  

                </form>
            </div>

        </div>    
    </div>
</div> 

<script type="text/javascript" charset="utf-8"	src='<c:url value="js/complejidad.js" />'></script>
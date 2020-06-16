<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM_HH_MM_SS%>" />

				<div class="row">      
					<label class="control-label col-sm-3">
  <strong>Fecha de Inicio:</strong></label>
					<div class="col-sm-9">             
						<span>
							<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeProcesoDTO.fechaInicio}" />
						</span>           
					</div>					
				</div> 
				
				<div class="row">      
					<label class="control-label col-sm-3">
  <strong>Iniciador:</strong></label>
					<div class="col-sm-9">
						<span>${instanciaDeProcesoDTO.idUsuarioInicia}</span>             
					</div>					
				</div>
				
				<div class="row">
					<label class="control-label col-sm-3">
  <strong>Plazo:</strong></label>
					<div class="col-sm-9">
						<span>
							<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeProcesoDTO.fechaVencimiento}" />									
						</span>             
					</div>
				</div>

				<div class="row">      
					<label class="control-label col-sm-3">
  <strong>SubProceso:</strong></label>
					<div class="col-sm-9">
						<span>${instanciaDeProcesoDTO.nombreDeProceso}</span>            
					</div>					
				</div>

				<div class="row">      
					<label class="control-label col-sm-3">
  <strong>Autor:</strong></label>
					<div class="col-sm-9">
						<span>${instanciaDeProcesoDTO.emisor}</span>             
					</div>  
				</div>
				
				<br>
				
		

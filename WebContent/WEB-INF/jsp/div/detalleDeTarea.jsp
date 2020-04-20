<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="permisoModificaArchivos" value="<%=PermisoType.MODIFICA_ARCHIVOS.getNombrePermiso()%>"/>
<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />
	
	<div class="col-sm-12">	
		<br>	
		De: ${idUsuarioUltimoComentario}<br>
		Comentario: ${ultimoComentario}
		<br>
		<br>
		<span>
			<button type="button" class="btn btn-labeled btn-primary" data-toggle="modal" data-target="#infoDeProcesoModal">
   				<span class="btn-label-default"><i class="glyphicon glyphicon-list-alt"></i></span>
   				Ver m&aacute;s Info
   			</button>
  			</span>
  			<br>
   	</div>   	
   	
   	<div class="col-sm-12">		
   		<br>	
		<fieldset>    
	 
			<legend>Documentos ${instanciaDeTareaDTO.nombreExpediente}</legend>
												
			<c:import url="/WEB-INF/jsp/div/tablaDetalleDeExpediente.jsp"></c:import>						
		
			<br>
		
			<div class="row">
			
				<div class="col-sm-12">
				
					<label class="radio-inline">
				      <input type="radio" onclick="cargaDetalleDeTarea(${instanciaDeTareaDTO.idInstanciaDeTarea}, 'true', '${instanciaDeTareaDTO.idExpediente}', '${instanciaDeTareaDTO.urlControl}') " id="muestraDocumentosObligatorios" name="muestraTodosLosDocumento" ${instanciaDeTareaDTO.muestraDocumentosObligatoriosChecked}>Solo los documentos obligatorios
				    </label>
				    <label class="radio-inline">
				      <input type="radio" onclick="cargaDetalleDeTarea(${instanciaDeTareaDTO.idInstanciaDeTarea}, 'false', '${instanciaDeTareaDTO.idExpediente}', '${instanciaDeTareaDTO.urlControl}')" id="muestraTodosDocumentos" name="muestraTodosLosDocumento" ${instanciaDeTareaDTO.muestraTodosDocumentosChecked}>Todos los documentos
				    </label>
				
				</div>	
			
			</div>
						
			<br>
			
			<div class="row">
			
				<div class="col-sm-2">
			    	<button type="button" class="btn btn-labeled btn-primary" data-toggle="modal" data-target="#subirArchivoModal">
			    	<span class="btn-label-default"><i class="glyphicon glyphicon-upload"></i></span>
	                   Añadir Archivo
					</button>
			    </div>
			
			</div>
			
			<br>
			
			<div class="row">
			
			    <div class="col-sm-2">
					
					<c:if test = "${instanciaDeTareaDTO.puedeDevolver eq true}">
					
						<button type="button" class="btn btn-labeled btn-primary" data-toggle="modal" data-target="#retrocedeProcesoModal">
	             				<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-left"></i></span>
	             				Retroceder
	             			</button>
					
					</c:if>								
					
	
			    </div>		
			    
			    <div class="col-sm-2 col-md-offset-8">
	             			
	             			<c:choose>
					    <c:when test = "${instanciaDeTareaDTO.esUltimaTarea eq false}">
					       <button onclick="cargarDatosContinuarProcesoModal(${instanciaDeTareaDTO.idInstanciaDeTarea}, 
																			'${instanciaDeTareaDTO.idExpediente}', 
																			${instanciaDeTareaDTO.puedeDevolver}, 
																			${instanciaDeTareaDTO.esUltimaTarea},
																			'${instanciaDeTareaDTO.nombreDeTarea} - ${instanciaDeTareaDTO.nombreDeProceso} - ${instanciaDeTareaDTO.nombreExpediente}',
																			${instanciaDeTareaDTO.tieneDocumentosEnCMS}, 
																			false, 
																			'${instanciaDeTareaDTO.tipoDeBifurcacion}',
																			'${instanciaDeTareaDTO.nombreDeTarea}'
																			)" 
																			type="button" 
								class="btn btn-labeled btn-primary">
	               				<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>
	               				Ejecutar
	               			</button>
					    </c:when>
					    <c:when test = "${instanciaDeTareaDTO.esUltimaTarea eq true}">
					        <button onclick="finalizarProceso(${instanciaDeTareaDTO.idInstanciaDeTarea}, 
																			'${instanciaDeTareaDTO.idExpediente}'
																			)" 
																			type="button" 
								class="btn btn-labeled btn-primary">
	               				<span class="btn-label-default"><i class="glyphicon glyphicon-triangle-right"></i></span>
	               				Despachar
	               			</button>
					    </c:when>									   
					</c:choose>
					
			    </div>						
			
			</div>		
	
		</fieldset> 
	
	</div>
	
	<!-- Modal Subir Archivo-->
	
	<%--<c:import url="/WEB-INF/jsp/modals/subirArchivo.jsp"></c:import>--%>	
	
	<!-- Modal Retrocede Proceso-->
	
	<%--<c:import url="/WEB-INF/jsp/modals/retrocedeProceso.jsp"></c:import>--%>
	
	<!-- Modal Detalle Documento-->
	
	<%-- 
	<c:import url="/WEB-INF/jsp/modals/detalleDeDocumento.jsp"></c:import>
	--%>
	
	<!-- Firma Avanzada-->
	
	<%--
	<c:import url="/WEB-INF/jsp/modals/firmaAvanzada.jsp"></c:import>
	
	<c:import url="/WEB-INF/jsp/modals/infoDeProceso.jsp"></c:import>	
	--%>

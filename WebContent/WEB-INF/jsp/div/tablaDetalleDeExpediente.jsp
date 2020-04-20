<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="cl.gob.scj.sgdp.util.FechaUtil" %>

<input type="hidden" id="idInstanciaDeTareaTablaDetalleDeExpediente" value="${instanciaDeTareaDTO.idInstanciaDeTarea}" />

<div class="table-responsive">
							
	<table id="tablaDocumentos" class="table table-striped table-bordered table-hover">											
	    <thead>
	        <tr>
	            <th>Nombre</th>
	            <th>CDR</th>
	            <th>Fecha Documento</th>
	            <th>Creador</th>
	            <th>Descripci&oacute;n</th>
	            
	            <c:choose>
					<c:when test="${filtraFirmas eq true}">					
						 <th></th>
	            		<th></th>
					</c:when>
				</c:choose>           
	            
	        </tr>
	    </thead>
	    <tbody>								    
	    	<c:forEach var="archivoInfoDTO" items="${archivosExpedienteDTO}">
	            <tr>	                
	                <c:choose>
						<c:when test="${linkDescargaEnNombre eq true}">					
							 <td class="view-message">							 
							 	<a href='#' onclick='descargaArchivo("<c:url value='getArchivoPorId/${archivoInfoDTO.idArchivo}'/>")'>
									${archivoInfoDTO.nombre}
								</a>							 
							 </td>
						</c:when>
						<c:otherwise>						
							<td class="view-message">${archivoInfoDTO.nombre}</td>
						</c:otherwise>
					</c:choose>                                 
	                <td class="view-message">${archivoInfoDTO.cdr}</td>
	                <td class="view-message">	    		
		    			<c:if test="${archivoInfoDTO.fechaDocumento ne null}">			    			
		    				<span style="display: none;" >
				    		  <fmt:formatDate pattern="yyyy-MM-dd" value="${archivoInfoDTO.fechaDocumento}" />  
				    		</span>	
				    		<fmt:formatDate pattern="dd/MM/yyyy" value="${archivoInfoDTO.fechaDocumento}" />
		    			</c:if>
	                </td>
	                <td class="view-message">${archivoInfoDTO.creador}</td>
	                <td class="view-message">${archivoInfoDTO.descripcion}</td>
	                
	                <c:choose>
						<c:when test="${filtraFirmas eq true}">					
						
							<td class="view-message">							               
			                	<c:if test = "${archivoInfoDTO.esEditable eq true and permisos[permisoModificaArchivos] eq permisoModificaArchivos}">
			                					            	
					            	<a href="#" class="btn btn-info btn-sm" id="botonEditarDocumento" onclick="editarDocumento();" 
				                		data-codigomimetype=${archivoInfoDTO.codigoMimeType}
				                		data-linksharpoint="${archivoInfoDTO.linkSharpoint}">		
			                			<span class="glyphicon glyphicon-pencil"></span>
					            	</a>
			                	
			                	</c:if>								                	
			                </td>
			                <td class="view-message">								                	
			                	 <a href="#" class="btn btn-info btn-sm" id="${archivoInfoDTO.idArchivo}"
			                	onclick="cargaDetalleDeDocumento('${archivoInfoDTO.idArchivo}', 
			                	${archivoInfoDTO.esVisable}, 
			                	${archivoInfoDTO.aplicaFEA}, 
			                	${archivoInfoDTO.aplicaFirmaApplet},
			                	'${instanciaDeTareaDTO.idExpediente}',								                	
			                	'${archivoInfoDTO.nombre}',
			                	'${archivoInfoDTO.mimeType}',
			                	'${instanciaDeTareaDTO.idInstanciaDeTarea}');" data-toggle="modal" data-target="#detalleDeDocumentoModal">
		   												<span class="glyphicon glyphicon-zoom-in"></span>
		 											</a>        											
		 												                	
			                </td>
						
						</c:when>
					</c:choose>
					
	            </tr>
	        </c:forEach>						           						     															  
	    </tbody>
	</table>
</div>	

<script>

function formatTablaDocumentos() {

	var tablaDocumentos = $('#tablaDocumentos')
	.DataTable(
			{
				"language" : languajeDataTableDocumentos,
				"pageLength": 5,
				bFilter: false,
				bLengthChange: false,
				bInfo : false,
				"dom": '<"toolbarTablaDocumentos">frtip'
				/*,
				sDom: '<"top"flp>rt<"bottom"i><"clear">'*/				
			});

	$("div.toolbarTablaDocumentos").html('<b>Documentos</b>');
	
	tablaDocumentos.buttons().container().appendTo(
	'#tablaDocumentos_wrapper .row:eq(0)');
}

$(document).ready(function() {
	formatTablaDocumentos();	
});

</script>
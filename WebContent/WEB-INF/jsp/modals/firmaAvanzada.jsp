<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="permisoPuedeFirmarConFEA" value="<%=PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso()%>"/>

<div class="modal fade" id="firmaAvanzadaModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="formFirmaAvanzada" class="form-horizontal" method="POST">			
				
				<input type="hidden" id="propositoFirmaAvanzada" name="propositoFirmaAvanzada"/>
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"><spring:message code="boton.cerrar.signo"/></span>
						<span class="sr-only"><spring:message code="boton.cerrar.nombre"/></span>
					</button>
					<h3 id="cabeceraFirmaAvanzadaModal" class="modal-title" id="lineModalLabel">Firma Avanzada</h3>
				</div>
				
				<div class="modal-body">
								
					<div class="form-group">						
						<label class="control-label col-sm-3" for="rut">
							RUT:
						</label>
					    <div class="col-sm-6">		    
					    	<input type="text" class="form-control validate[required, validaRut]" id="rutFirmaAvanzada" placeholder="Ej:12345678-K" />	    			    	
					    </div>
					</div>	
					
					<div class="form-group hidden">						
						<label class="control-label col-sm-3" for="proposito">
							Prop&oacute;sito:
						</label>
					    <div class="col-sm-6">					    	
					    	<select onchange="cargaTipoDeFirma()" id="propositoFirmaAvanzadaSelect" class="form-control validate[required]">
					    		
					    	</select>	    			    	
					    </div>
					</div>	
					
					<div class="form-group hidden" id="divOPT">						
						<label class="control-label col-sm-3" for="otpFirmaAvanzada">
							OTP:
						</label>
					    <div class="col-sm-6"> 		    
					    	<input type="text" class="form-control validate[required]" id="otpFirmaAvanzada" />	    			    	
					    </div>
					    <div class="col-sm-3 hidden"> 		    
					    	<button type="button" onclick="muestraCodigoQR()" class="btn btn-info btn-sm" id="botonVerQR">
					    		<span class="glyphicon glyphicon-qrcode"></span> Codigo QR
					    	</button>	    			    	
					    </div>
					</div>
					
					 	
					
					<div class="form-group hidden" id="divImagenQR">
						<label class="control-label col-sm-3">
							Codigo QR:
						</label>
						 <div class="col-sm-6"> 
						 	<%-- <c:if test = "${permisos[permisoPuedeFirmarConFEA] eq permisoPuedeFirmarConFEA}">			    
					    		<img src='<c:url value="/getImagenCodigoQRUsuario"/>' class="img-responsive img-thumbnail">	
					    	</c:if> --%>    			    	

					    </div>
					</div>
					
					
				
				</div>
				
				<div class="modal-footer">
					<button type="button" id="botonAplicarFirmaAvanzada" class="btn btn-primary btn-lg btn-block" onclick="aplicarFirmaAvanzada()">
						<span class="glyphicon glyphicon-qrcode"></span> Firma Avanzada
				    </button>
				</div>
			
			</form>
		</div>
	</div>
</div>
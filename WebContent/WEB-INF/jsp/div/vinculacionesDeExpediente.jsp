<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<c:set var="permisoPuedeDesVincularExpedientes" value="<%=PermisoType.PUEDE_DES_VINCULAR_EXPEDIENTES.getNombrePermiso()%>"/>

<div class="table-responsive col-sm-12">

	<table id="tablaVinculacionesDeExpediente" class="table table-striped table-bordered table-fixed">											
	    <thead class="cabecera-vinc-thead">
	        <tr>	        		            	 
	            <th>Expediente(s) antecesor(es)</th>
	            <th>Expediente actual</th>  
	            <th>Expediente(s) sucesor(es)</th>     						       
	        </tr>
	    </thead>
	    <tbody>			    
	   		<tr>		    		
	   			<td align="left">	   			
	   				<ul class="list-group">	   						   				
	   					<c:forEach items="${vinculacionExpedienteDTO.expedientesAntecesores}" var="vinculacionExpedienteDTOAntecesor">
							<li class="list-group-item li-vinc-ex" data-nombre-expediente="${vinculacionExpedienteDTOAntecesor.nombreExpediente}">
								<c:if test="${permisos[permisoPuedeDesVincularExpedientes] eq permisoPuedeDesVincularExpedientes}">
									<a href="#" title="Desvincular expedientes" onclick="event.stopPropagation(); desVincularExpedientes('${vinculacionExpedienteDTO.nombreExpediente}', '${vinculacionExpedienteDTOAntecesor.nombreExpediente}');" class="close" data-dismiss="alert" aria-label="close">×</a>
								</c:if>
								<div>${vinculacionExpedienteDTOAntecesor.nombreExpediente}</div>
								<div>(${vinculacionExpedienteDTOAntecesor.nombreProceso})</div>
								<span>Comentario(s) sobre la vinculaci&oacute;n:</span>
								<div class="corner">${vinculacionExpedienteDTOAntecesor.comentario}</div>
							</li>
						</c:forEach>							   					   					
	   				</ul>
	   			</td>
				<td align="center">
					<div>${vinculacionExpedienteDTO.nombreExpediente}</div>
					<div>(${vinculacionExpedienteDTO.nombreProceso})</div>
					<!--  
						<br>
						<div class="corner">
							<span>Comentario(s) sobre la vinculaci&oacute;n:</span>
							<ul>
								<c:forEach items="${vinculacionExpedienteDTO.comentarios}" var="comentario">
									<li>&period; ${comentario}</li>
								</c:forEach>
							</ul>
						</div>
					-->						   			
	   			</td>
				<td align="left">
					<ul class="list-group">					
						<c:forEach items="${vinculacionExpedienteDTO.expedientesSucesores}" var="vinculacionExpedienteDTOSucesor">
							<li class="list-group-item li-vinc-ex" data-nombre-expediente="${vinculacionExpedienteDTOSucesor.nombreExpediente}">
								<div>${vinculacionExpedienteDTOSucesor.nombreExpediente}</div>
								<div>(${vinculacionExpedienteDTOSucesor.nombreProceso})</div>
								<span>Comentario(s) sobre la vinculaci&oacute;n:</span>							
								<div class="corner">${vinculacionExpedienteDTOSucesor.comentario}</div>
							</li>
						</c:forEach>					
					</ul>
				</td>	    					    		
	   		</tr>	
	    </tbody>	    
	</table>

</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script type="text/javascript">	
	var inicializaVerExpEnBusc = function() {
		$(".li-vinc-ex").off('click').on('click', function () {
			var nombreExpB = $(this).attr('data-nombre-expediente');
			var urlBuscador = '${pageContext.request.contextPath}/' + "buscador?linkActivo=buscador";
			$.get("${sessionURL}", function(haySession) {
                if(haySession) {
                	var n= window.open(urlBuscador);
                	n.focus(); 
                	n.addEventListener('load', function(){
                		n.document.getElementById('palabraClave').value=nombreExpB;
                		var checkBuscar = n.document.getElementsByName("tipoDeObjeto");
                	    for (i = 0; i < checkBuscar.length; i++) {
                	        if (checkBuscar[i].type == "checkbox" && checkBuscar[i].value == 'CARPETA') {
                	            checkBuscar[i].checked = true;
                	        }
                	    }
                	    n.callOpenerCargaResultadoBusqueda();
                	  }, true);                	 		
                } else {
                	bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                             , function(){
                                   window.open('${raizURL}', '_blank');
                             }
                    );
                }
            });	
		});	
	};
	$(document).ready(function() {	
		$(inicializaVerExpEnBusc);	
	});
	function desVincularExpedientes(nombreExpediente, nombreExpedienteAntecesor) {
		console.log("nombreExpediente: " + nombreExpediente + "; nombreExpedienteAntecesor: " + nombreExpedienteAntecesor);
		var urlBuscador = '${pageContext.request.contextPath}/' + "buscador?linkActivo=buscador";
		$.get("${sessionURL}", function(haySession) {
            if(haySession) {
            	bootbox.prompt({	    	
    	        	title: "Desvincular expedientes",
    	        	message: "<p>Desvincular expediente " + nombreExpedienteAntecesor + " de expediente " + nombreExpedienteAntecesor + "</p>",
    	        	placeholder: "Ingrese motivo de desvinculación",
    	        	size: 'medium',
    	            buttons: {
    	                confirm: {
    	                    label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
    	                    className: 'btn-success'
    		            },
    	                cancel: {
    	                    label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
    	                    className: 'btn-danger'
    	                }
    	            },
    	            callback: function (motivoDesvinculacion) {
    	            	var vinculacionExpedienteDTO = {};
    	    			var urlDesVinculacionDeExpedientes = '${pageContext.request.contextPath}/' + "desVincularExp";
    	    	       	vinculacionExpedienteDTO["nombreExpediente"] = nombreExpediente;
    	    	       	vinculacionExpedienteDTO["nombreExpAntecesor"] =  nombreExpedienteAntecesor;
    	    	       	vinculacionExpedienteDTO["comentario"] =  motivoDesvinculacion;
    	    			if (motivoDesvinculacion!=null && motivoDesvinculacion!="") {
    	    				$.ajax({
	    						type : "POST",
	    						contentType : "application/json",
	    					    url: urlDesVinculacionDeExpedientes,
	    						data : JSON.stringify(vinculacionExpedienteDTO),
	    						dataType : 'json',
	    						timeout : 100000,
	    						success : function(data) {
	    	                        console.log("SUCESS: ", data);           							 
	    						},
	    						error : function(e) {
	    							 console.log("ERROR: ", e);
	    						},
	    						complete: function(data){							
	    							console.log("COMPLETE: ", data);
	    							$('#respuestaVinculacion').text(data.responseJSON.respuestaVinculacion);
	    							$("#respuestaVinculacion").removeClass();
	    							$('#respuestaVinculacion').addClass(data.responseJSON.cssStatus);
	    							var urlGetVinculacionesDeExpediente = '${pageContext.request.contextPath}/' + "getVinculacionesDeExpediente/" + $("#vincularExpedienteBoton").attr('data-id-expediente');						
	    							$("#vinculacionExpedienteDiv").load(urlGetVinculacionesDeExpediente, function() {
	    								formatTablaVinculacionesDeExpediente();
	    								$("#vinculacionExpedienteDiv").find(".cargando").remove(); 
	    								$("#vinculacionExpedienteDiv").css("position","");
	    								$("#vinculacionExpedienteDiv").css("min-height","");                			                       
	    			                } );
	    						},		
	    					});    	    								
    	    			} else if (motivoDesvinculacion==null) {
    						return true;
                		} else {									            	    	
                	    	$(".bootbox-input-text").validationEngine('showPrompt', 'Por favor ingrese un motivo', 'error');
                	    	return false;
    	            	}										
    	            }
    	        });	               	 		
            } else {
            	bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                         , function(){
                               window.open('${raizURL}', '_blank');
                         }
                );
            }
        });	
	}
</script>


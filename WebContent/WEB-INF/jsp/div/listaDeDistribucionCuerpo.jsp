<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%>
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />

<div class="container">

	<div class="row">
	
		<div class="col-sm-4">
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalCreaRegistroListaDeDistribucion()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
		<div class="col-sm-4">
		</div>
		
		<div class="col-sm-4">
			<button type="button" title="Subir Archivo"
				id="botonSubirArchivoCargaMasivaListaDistribucion" class="btn btn-success btn-lg btn-block">
				<span class="glyphicon glyphicon-upload font-icon-1"></span> Subir Archivo
			</button>
		</div>	
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaListaDeDistribucion"
				class="table table-striped table-bordered table-fixed" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>Nombre Completo</th>
						<th>Organizaci&oacute;n</th>
						<th>Cargo</th>
						<th>Email</th>
						<th>Tipo de Destinatario</th>
						<th>N&uacute;meros telef&oacute;nicos</th>
						<th>Fechas de Vigencia</th>
						<th>Usuario de Creaci&oacute;n</th>
						<th>Fecha de Creaci&oacute;n</th>
						<th>Usuario &Uacute;ltima Modificaci&oacute;n</th>
						<th>Fecha &Uacute;ltima Modificaci&oacute;n</th>
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${listaTotalDeDistribucionDTO}" var="listaDeDistribucionDTO">
						<tr>
							<td>${listaDeDistribucionDTO.nombreCompleto}</td>							
							<td>${listaDeDistribucionDTO.organizacion}</td>
							<td>${listaDeDistribucionDTO.cargo}</td>							
							<td>${listaDeDistribucionDTO.email}</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.tipoDeDestinatarioDTO ne null and listaDeDistribucionDTO.tipoDeDestinatarioDTO.nombreTipoDestinatario ne null}">
				    				${listaDeDistribucionDTO.tipoDeDestinatarioDTO.nombreTipoDestinatario}
				    			</c:if>							
							</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.numeroTelefono1 ne null}">${listaDeDistribucionDTO.numeroTelefono1}</c:if>
								<c:if test = "${listaDeDistribucionDTO.numeroTelefono2 ne null}">- ${listaDeDistribucionDTO.numeroTelefono2}</c:if>
							</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.fechaInicioVigencia ne null and listaDeDistribucionDTO.fechaFinVigencia ne null}">
									<span style="display: none;" >
					    		  		<fmt:formatDate value="${listaDeDistribucionDTO.fechaInicioVigencia}" pattern="yyyy-MM-dd" /> 
					    		  		<fmt:formatDate value="${listaDeDistribucionDTO.fechaFinVigencia}" pattern="yyyy-MM-dd" /> 
					    			</span>								
									<fmt:formatDate pattern="${FORMATO_FECHA}" value="${listaDeDistribucionDTO.fechaInicioVigencia}" /> -
									<fmt:formatDate pattern="${FORMATO_FECHA}" value="${listaDeDistribucionDTO.fechaFinVigencia}" />
								</c:if>
							</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.idUsuarioCreacion ne null}">${listaDeDistribucionDTO.idUsuarioCreacion}</c:if>
							</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.fechaCreacion ne null}">
									<span style="display: none;" >
					    		  		<fmt:formatDate value="${listaDeDistribucionDTO.fechaCreacion}" pattern="yyyy-MM-dd" /> 
					    			</span>	
									<fmt:formatDate pattern="${FORMATO_FECHA}" value="${listaDeDistribucionDTO.fechaCreacion}" />
								</c:if>
							</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.idUsuarioUltimaModificacion ne null}">${listaDeDistribucionDTO.idUsuarioUltimaModificacion}</c:if>
							</td>
							<td>
								<c:if test = "${listaDeDistribucionDTO.fechaUltimaModificacion ne null}">
									<span style="display: none;" >
					    		  		<fmt:formatDate value="${listaDeDistribucionDTO.fechaUltimaModificacion}" pattern="yyyy-MM-dd" /> 
					    			</span>	
									<fmt:formatDate pattern="${FORMATO_FECHA}" value="${listaDeDistribucionDTO.fechaUltimaModificacion}" />
								</c:if>
							</td>
							<td>
								<p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalActualizaRegistroListaDeDistribucion" data-toggle="modal"										
										onclick="abreModalActualizaRegistroListaDeDistribucion(${listaDeDistribucionDTO.idListaDeDistribucion})">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
								</p>
								<p data-placement="top" data-toggle="tooltip"
									title="Borrar">
									<button class="btn btn-danger btn-sm"
										data-title="borrarRegistroListaDeDistribucion"
										onclick="borrarRegistroListaDeDistribucion(${listaDeDistribucionDTO.idListaDeDistribucion})">
										<span class="glyphicon glyphicon-trash"></span>
									</button>
								</p>
							</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
		</div>
		
	
	</div>
	
</div>
	
<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />
	
<script>

var inicializaBotonSubirArchivoCargaMasivaListaDistribucion = function() {	
	$('#botonSubirArchivoCargaMasivaListaDistribucion').click(function() {
		$.get("${sessionURL}", function(haySession){
	    	if (haySession) {	    
	    		console.log("Boton subir archivo");
	    		$("#cargaListaDistribucionMasivaModal").modal({backdrop: "static"});	
	       	} else {
	             bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	             	, function(){ window.open('${raizURL}', '_self');}
	             );
	       }
		});
	});
};

function abreModalCreaRegistroListaDeDistribucion() {
	$.get("${sessionURL}", function(haySession){       
        if(haySession) {
        	$('#formCreaRegistroListaDeDistribucion').trigger("reset");
        	$('#creaRegistroListaDeDistribucionModal').modal( {backdrop: 'static', keyboard: false});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

function abreModalActualizaRegistroListaDeDistribucion(idListaDeDistribucion) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaRegistroListaDeDistribucionModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaRegistroListaDeDistribucionModal = "${pageContext.request.contextPath}" + "/editaRegistroListaDeDistribucion/" + idListaDeDistribucion;        	
        	$("#editaRegistroListaDeDistribucionDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaRegistroListaDeDistribucionDiv").load(urlEditaRegistroListaDeDistribucionModal, function() {
                $("#editaRegistroListaDeDistribucionDiv").find(
                        ".cargando").remove();
     		});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

function borrarRegistroListaDeDistribucion(idListaDeDistribucion) {
	$.get("${sessionURL}", function(haySession){      
        if(haySession) {
        	var urlBorrarRegistroDeListaDeDistribucion = $("#urlBorrarRegistroDeListaDeDistribucion").val()+"/"+idListaDeDistribucion;
        	var urlCargaListaDeDistribucion = $("#urlCargaListaDeDistribucion").val();
        	bootbox.prompt({	    	
	        	title: "Borrar registro",
	        	message: "<p>Borrar registro.</p>",
	        	placeholder: "Ingrese motivo",
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
	            callback: function (motivo) {	  
	    			if (motivo!=null && motivo!="") {
                        urlBorrarRegistroDeListaDeDistribucion = urlBorrarRegistroDeListaDeDistribucion + "/" + motivo;
	    				$.ajax( {
                    	    url: urlBorrarRegistroDeListaDeDistribucion,
                    	    type: 'DELETE',
                    	    async: true,
                    	    cache: false,
                    	    contentType: false,
                    	    processData: false,
                    	    beforeSend: function(xhr) {
                                $("#listaDeDistribucionCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
                    	    },
                    	    success: function (returnData) {
                    	    	console.log("SUCCESS -- elimina registro: ", returnData);	    	
                    	    },
                    	    error : function(e) {
                    			console.log("ERROR: ", e);
                    			$("#listaDeDistribucionCuerpo").find(".cargando").remove();			
                    		},
                    		done : function(e) {
                    			console.log("DONE");
                    		},
                    		complete : function(returnData) {
                    			console.log("COMPLETE -- elimina registro: ", returnData);
                    			if (returnData.responseText=="OK") {
                    				$.notify({
                             			message: "Registro eliminado"
                             		},{
                             			type: 'success'
                             		});
                        		} else {
                        			$.notify({
                             			message: "Ocurrio un error al eliminar el registro"
                             		},{
                             			type: 'danger'
                             		});
                                }                    			
                    			$("#listaDeDistribucionCuerpo").load(urlCargaListaDeDistribucion,
                    			        function() {
                    			            $("#listaDeDistribucionCuerpo").find(".cargando").remove();            
                    			});			            					
                    		}                    		
                    	});					
	    			} else {									            	    	
            	    	$(".bootbox-input-text").validationEngine('showPrompt', 'Por favor ingrese un motivo', 'error');
            	    	return false;
	            	}										
	            }
	        });	
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

function formatTablaListaDeDistribucion() {

	var tablaListaDeDistribucion = $('#tablaListaDeDistribucion')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'ListaDeDistribucion.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 8
				
			});

	tablaListaDeDistribucion.buttons().container().appendTo(
	'#tablaListaDeDistribucion_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaListaDeDistribucion();	
	inicializaBotonSubirArchivoCargaMasivaListaDistribucion();
})

</script>
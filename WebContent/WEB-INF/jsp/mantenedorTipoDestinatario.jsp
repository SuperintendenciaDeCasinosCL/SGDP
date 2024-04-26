<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>

		<c:import url="/WEB-INF/jsp/objetos/cabeceraImportacion.jsp"></c:import>
	 
	</head>

	<body>
		
		<div class="container-fluid container-sgdp">
		
			<div class="row content">
			
				<div class="col-sm-2 sidenav">		      
			      <c:import url="/WEB-INF/jsp/objetos/menu.jsp"></c:import>		      
			    </div>
			    
			    <div class="col-sm-10">	
			    
			    	<c:choose>
						<c:when test = "${usuario.fueraDeOficina eq true}">
							<c:set var="colorBackHeaderMantenedorTipoDestinatario" value="#FFD51D" />
							<c:set var="mensajeMantenedorTipoDestinatario" value="Mantenedor de Tipos de Destinatario (Fuera de Oficina)" />
						</c:when>
						<c:otherwise>
							<c:set var="colorBackHeaderMantenedorTipoDestinatario" value="#41CAC0" />
							<c:set var="mensajeMantenedorTipoDestinatario" value="Mantenedor de Tipos de Destinatario" />							
						</c:otherwise>						
					</c:choose> 
					
					<div id="divBackHeaderMantenedorParametros" class="row div-area-trabajo-cab" style="background-color: ${colorBackHeaderMantenedorTipoDestinatario}; color: #fff;">
			    	
			    		<div class="col-sm-1"></div>
			    	
			    		<div class="col-sm-9"><h2 id="h2MensajeMantenedorTipoDestinatario">${mensajeMantenedorTipoDestinatario}</h2></div>
			    		
			    		<div class="col-sm-2"><c:import url="/WEB-INF/jsp/objetos/menuAyuda.jsp"></c:import></div> 
			    	
			    	</div>   	
		      
		      		<hr>
			    
   					<div class="col-sm-12" id="mantenedorTipoDesTinatarioCuerpo">
   						
				    	<c:import url="/WEB-INF/jsp/div/mantenedorTipoDestinatarioCuerpo.jsp"></c:import>
				    	   	 		
					</div>  
			    
			    
			    </div>
			
			</div>
		
		</div>	
	
		
	</body>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function borrarTipoDestinatario(idTipoDestinatario) {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {   
	        var urlBorrarTipoDestinatario = "${pageContext.request.contextPath}" + "/borraTipoDestinatario/" + idTipoDestinatario;
    	    var urlMantenedorTipoDestinatarioCuerpo = "${pageContext.request.contextPath}" + "/mantenedorTipoDestinatarioCuerpo";	        
            bootbox.confirm({
                title: "Borrar Tipo de destinatario",
                message: "¿Desea eliminar el tipo de destinatario?",
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
                callback: function (result) {	                  
                    if (result == true) {	                         			      			       
                        $.ajax( {
                        	url: urlBorrarTipoDestinatario,
                    	    type: 'DELETE',
                    	    async: true,
                    	    cache: false,
                    	    contentType: false,
                    	    processData: false,                	    
                            beforeSend: function(xhr) {
                                $("#mantenedorTipoDesTinatarioCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
                            },
                            success: function (returnData) {
                                console.log("SUCCESS -- elimina registro: ", returnData);	    	
                            },
                            error : function(e) {
                                console.log("ERROR: ", e);
                                $("#mantenedorTipoDesTinatarioCuerpo").find(".cargando").remove();			
                            },
                            done : function(e) {
                                console.log("DONE");
                            },
                            complete : function(returnData) {
                                if (returnData.responseJSON.respuesta=="OK") {
                                    $.notify({
                                        message: "Tipo de destinatario eliminado"
                                    },{
                                        type: 'success'
                                    });
                                } else {
                                    $.notify({
                                        message: returnData.responseJSON.respuesta
                                    },{
                                        type: 'danger'
                                    });
                                }                			                       	
                                $("#mantenedorTipoDesTinatarioCuerpo").load(urlMantenedorTipoDestinatarioCuerpo,
    	                            function() {                            			
    	                                $("#mantenedorTipoDesTinatarioCuerpo").find(".cargando").remove();                			                       
                                });		            					
                            }                		
                        });
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

function creaTipoDestinatario() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
        	bootbox.prompt("Ingrese el nombre del nuevo tipo de destinatario", function(result) {         		
        	    console.log(result);        	   
        	    var urlCreaTipoDestinatario = "${pageContext.request.contextPath}" + "/creaTipoDestinatario";
        	    var urlMantenedorTipoDestinatarioCuerpo = "${pageContext.request.contextPath}" + "/mantenedorTipoDestinatarioCuerpo";
        	    var tipoDestinatarioDTO  = {}
        	    tipoDestinatarioDTO["nombreTipoDestinatario"] = result;        	    
        	    respuesta = jQuery.ajax({
       	                'type': 'PUT',
       	                'url': urlCreaTipoDestinatario,
       	                'contentType': 'application/json',
       	                'data': JSON.stringify(tipoDestinatarioDTO),
       	                'dataType': 'json',
       	                'complete': function(returnData) {
       	                	console.log(returnData.responseJSON);
       	                	console.log(returnData.responseJSON.respuesta);
       	                	if (returnData.responseJSON.respuesta=="OK") {
                                $.notify({
                                    message: "Registro creado"
                                },{
                                    type: 'success'
                                });
                            } else {
                                $.notify({
                                    message: returnData.responseJSON.respuesta
                                },{
                                    type: 'danger'
                                });
                            }
       	                	$("#mantenedorTipoDesTinatarioCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));                        	
                            $("#mantenedorTipoDesTinatarioCuerpo").load(urlMantenedorTipoDestinatarioCuerpo,
	                            function() {                            			
	                                $("#mantenedorTipoDesTinatarioCuerpo").find(".cargando").remove();                			                       
                            });       	                	
       	                },
       	                'error' : function(xhr, ajaxOptions, error) {                
       	                	$.notify({
                                message: "Ocurrio un error al crear el registro"
                            },{
                                type: 'danger'
                            });
       	                }
       	            }    
       	        );        	    
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

function actualizaTipoDestinatario(idTipoDestinatario) {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
        	bootbox.prompt("Ingrese el nombre del tipo de destinatario", function(result) {         		
        	    console.log(result);        	   
        	    var urlActualizaTipoDestinatario = "${pageContext.request.contextPath}" + "/actualizaTipoDestinatario";
        	    var urlMantenedorTipoDestinatarioCuerpo = "${pageContext.request.contextPath}" + "/mantenedorTipoDestinatarioCuerpo";
        	    var tipoDestinatarioDTO  = {}
        	    tipoDestinatarioDTO["idTipoDestinatario"] = idTipoDestinatario;
        	    tipoDestinatarioDTO["nombreTipoDestinatario"] = result;        	    
        	    respuesta = jQuery.ajax({
       	                'type': 'PUT',
       	                'url': urlActualizaTipoDestinatario,
       	                'contentType': 'application/json',
       	                'data': JSON.stringify(tipoDestinatarioDTO),
       	                'dataType': 'json',
       	                'complete': function(returnData) {
       	                	console.log(returnData.responseJSON);
       	                	console.log(returnData.responseJSON.respuesta);
       	                	if (returnData.responseJSON.respuesta=="OK") {
                                $.notify({
                                    message: "Registro actualizado"
                                },{
                                    type: 'success'
                                });
                            } else {
                                $.notify({
                                    message: returnData.responseJSON.respuesta
                                },{
                                    type: 'danger'
                                });
                            }
       	                	$("#mantenedorTipoDesTinatarioCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));                        	
                            $("#mantenedorTipoDesTinatarioCuerpo").load(urlMantenedorTipoDestinatarioCuerpo,
	                            function() {                            			
	                                $("#mantenedorTipoDesTinatarioCuerpo").find(".cargando").remove();                			                       
                            });       	                	
       	                },
       	                'error' : function(xhr, ajaxOptions, error) {                
       	                	$.notify({
                                message: "Ocurrio un error al crear el registro"
                            },{
                                type: 'danger'
                            });
       	                }
       	            }    
       	        );        	    
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
	
</html>


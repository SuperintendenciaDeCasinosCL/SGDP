<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container">

	<div class="row">
	
		<div class="col-sm-4">
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalNuevoParametro()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
		</div>
		
	
	</div>
	
	<div class="row">
	
		<div class="table-responsive col-sm-12">
			<table id="tablaMantenedorParametros"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Valor Texto</th>
						<th>Valor n&uacute;merico</th>						
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${parametrosMap}" var="entryParametrosMap">
						<tr>
							<td>${entryParametrosMap.value.idParametro}</td>							
							<td>${entryParametrosMap.value.nombreParametro}</td>
							<td><c:out value="${entryParametrosMap.value.valorParametroChar}"/></td>							
							<td>${entryParametrosMap.value.valorParametroNumerico}</td>
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaParametro" data-toggle="modal"										
										onclick="abreModalEditaParametro(${entryParametrosMap.value.idParametro})">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
								</p>
							</td>
							<td><p data-placement="top" data-toggle="tooltip"
										title="Borrar">
										<button class="btn btn-danger btn-sm"
											data-title="borrarParametro"
											onclick="borrarParametro(${entryParametrosMap.value.idParametro})">
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

function borrarParametro(idParametro) {	
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {	        
	        var urlBorraParametro = "${pageContext.request.contextPath}" + "/borraParametro/" + idParametro;
	        var urlCargaParametros = "${pageContext.request.contextPath}" + "/cargaParametros";
            bootbox.confirm({
                title: "Borrar par&aacute;metro",
                message: "¿Desea eliminar el par&aacute;metro?",
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
                        	url: urlBorraParametro,
                    	    type: 'POST',
                    	    async: true,
                    	    cache: false,
                    	    contentType: false,
                    	    processData: false,                	    
                            beforeSend: function(xhr) {
                                $("#mantenedorParametrosCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
                            },
                            success: function (returnData) {
                                console.log("SUCCESS -- elimina registro: ", returnData);	    	
                            },
                            error : function(e) {
                                console.log("ERROR: ", e);
                                $("#mantenedorParametrosCuerpo").find(".cargando").remove();			
                            },
                            done : function(e) {
                                console.log("DONE");
                            },
                            complete : function(returnData) {
                                if (returnData.responseText=="OK") {
                                    $.notify({
                                        message: "Par&aacute;metro eliminado"
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
                                $("#mantenedorParametrosCuerpo").load(urlCargaParametros,
                                        function() {
                                            $("#mantenedorParametrosCuerpo").find(".cargando").remove();                			                       
                                });		            					
                            }                		
                        });
                    } 
                }
            });	        
	    } else {
	          bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                        , function(){
	                              window.open('${raizURL}', '_self');
	                        }
	           );
	    }
	});
}

function abreModalNuevoParametro() {
	$.get("${sessionURL}", function(haySession){
        console.log("haySession: " + haySession);
        if(haySession) {
        	$('#formCreaParametro').trigger("reset");
        	$('#creaParametroModal').modal( {backdrop: 'static', keyboard: false});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}

function abreModalEditaParametro(idParametro) {
	$.get("${sessionURL}", function(haySession){
        if(haySession) {
        	$('#editaParametroModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaParametro = "${pageContext.request.contextPath}" + "/editaParametro/" + idParametro;        	
        	$("#editaParametroDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaParametroDiv").load(urlEditaParametro, function() {
                $("#editaParametroDiv").find(
                        ".cargando").remove();
     		});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_self');
                            }
               );
        }
  	});	
}

function formatTablaMantenedorParametros() {

	var tablaMantenedorParametros = $('#tablaMantenedorParametros')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'Parametros.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 10
				
			});

	tablaMantenedorParametros.buttons().container().appendTo(
	'#tablaMantenedorParametros_wrapper .row:eq(0)');
};

$(document).ready(function() {
	formatTablaMantenedorParametros();	
})

</script>
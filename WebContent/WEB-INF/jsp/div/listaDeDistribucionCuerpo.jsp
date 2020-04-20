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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="abreModalCreaRegistroListaDeDistribucion()">Nuevo</button>
				<br>				
		</div>
	
		<div class="col-sm-8">
			
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
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>	
				
					<c:forEach items="${listaTotalDeDistribucionDTO}" var="ListaDeDistribucionDTO">
						<tr>
							<td>${ListaDeDistribucionDTO.nombreCompleto}</td>							
							<td>${ListaDeDistribucionDTO.organizacion}</td>
							<td>${ListaDeDistribucionDTO.cargo}</td>							
							<td>${ListaDeDistribucionDTO.email}</td>
							<td><p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalActualizaRegistroListaDeDistribucion" data-toggle="modal"										
										onclick="abreModalActualizaRegistroListaDeDistribucion(${ListaDeDistribucionDTO.idListaDeDistribucion})">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
								</p>
							</td>
							<td><p data-placement="top" data-toggle="tooltip"
										title="Borrar">
										<button class="btn btn-danger btn-sm"
											data-title="borrarRegistroListaDeDistribucion"
											onclick="borrarRegistroListaDeDistribucion(${ListaDeDistribucionDTO.idListaDeDistribucion})">
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

function abreModalCreaRegistroListaDeDistribucion() {
	$.get("${sessionURL}", function(haySession){       
        if(haySession) {
        	$('#formCreaRegistroListaDeDistribucion').trigger("reset");
        	$('#creaRegistroListaDeDistribucionModal').modal( {backdrop: 'static', keyboard: false});
        } else {
              bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                            , function(){
                                  window.open('${raizURL}', '_blank');
                            }
               );
        }
  	});	
}

function abreModalActualizaRegistroListaDeDistribucion(idListaDeDistribucion) {
	$.get("${sessionURL}", function(haySession){
        //console.log("haySession: " + haySession);
        if(haySession) {
        	//$("#editaRegistroCorreoDeDistribucionModalH4").empty();
        	//$("#editaRegistroCorreoDeDistribucionModalH4").append('Edici&oacute;n de registro');
        	$('#editaRegistroListaDeDistribucionModal').modal( {backdrop: 'static', keyboard: false});
        	var urlEditaRegistroListaDeDistribucionModal = "${pageContext.request.contextPath}" + "/editaRegistroListaDeDistribucion/" + idListaDeDistribucion;        	
        	$("#editaRegistroListaDeDistribucionDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
        	$("#editaRegistroListaDeDistribucionDiv").load(urlEditaRegistroListaDeDistribucionModal, function() {
                $("#editaRegistroListaDeDistribucionDiv").find(
                        ".cargando").remove();
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

function borrarRegistroListaDeDistribucion(idListaDeDistribucion) {
	$.get("${sessionURL}", function(haySession){      
        if(haySession) {
        	var urlBorrarRegistroDeListaDeDistribucion = $("#urlBorrarRegistroDeListaDeDistribucion").val()+"/"+idListaDeDistribucion;
        	var urlCargaListaDeDistribucion = $("#urlCargaListaDeDistribucion").val();        	
        	bootbox.confirm({
            	title: "Borrar registro",
                message: "Desea eliminar el registro",
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
                    console.log('El usuario selecciono: ' + result);
                    if (result == true) {
                    	$.ajax( {
                    	    url: urlBorrarRegistroDeListaDeDistribucion,
                    	    type: 'POST',
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
})

</script>
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
			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="modalNuevoAutor()">Nuevo</button>
			<br>				
		</div>
		<div class="col-sm-8"></div>
	</div>
	<div class="row">
		<div class="table-responsive col-sm-12">
			<table id="tablaMantenedorAutores"
				class="table table-striped table-bordered" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre de Autores</th>				
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>	
					<c:forEach items="${listaAutoresDTO}" var="autorDTO">
						<tr>
							<td>${autorDTO.idAutor}</td>							
							<td style="text-transform: uppercase">${autorDTO.nombreAutor}</td>
							<td>
								<p data-placement="top" data-toggle="tooltip"
									title="Editar">
									<button class="btn btn-primary btn-sm"
										data-title="abreModalEditaAutor" data-toggle="modal"										
										onclick="modalEditarAutor(${autorDTO.idAutor})">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
								</p>
							</td>
							<td>
								<p data-placement="top" data-toggle="tooltip"
											title="Borrar">
									<button class="btn btn-danger btn-sm"
										data-title="borrarAutor"
										onclick="borrarAutor(${autorDTO.idAutor}, '${autorDTO.nombreAutor}')">
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

	$(document).ready(function() {
		formatTablaMantenedorAutores();	
	});
	
	function formatTablaMantenedorAutores() {
	
		var tablaMantenedorAutores = $('#tablaMantenedorAutores')
		.DataTable({
			buttons : [{
				extend : 'excelHtml5',
				filename : 'Parametros.*',
				exportOptions : {
					columns : ':visible'
					}
				}, 'colvis'
			],
			"language" : lenguaje_es,
			"pageLength": 10
		});
	
		tablaMantenedorAutores.buttons().container().appendTo(
		'#tablaMantenedorAutores_wrapper .row:eq(0)');
	};

	function modalNuevoAutor() {
		$.get("${sessionURL}", function(haySession){
	        console.log("haySession: " + haySession);
	        if(haySession) {
	        	$('#formCreaAutor').trigger("reset");
	        	$('#creaAutorModal').modal({backdrop: 'static', keyboard: false});
	        } else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	            	, function(){
	                   	window.open('${raizURL}', '_self');
	            	}
	           	);
	        }
	  	});
	}
	
	function modalEditarAutor(idAutor) {
		$.get("${sessionURL}", function(haySession){
	        if(haySession) {
	        	$('#editarAutorModal').modal({backdrop: 'static', keyboard: false});
	        	var urlEditarAutor = "${pageContext.request.contextPath}" + "/editarAutor/" + idAutor;        	
	        	$("#editarAutorDiv").css("position", "relative").css("min-height", "150px").prepend($("<div />").addClass("cargando"));			
	        	$("#editarAutorDiv").load(urlEditarAutor, function() {
	                $("#editarAutorDiv").find(
	                        ".cargando").remove();
	     		});
	        } else {
	            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
					, function(){
	                		window.open('${raizURL}', '_self');
	               	}
	           	);
	        }
	  	});	
	}
	
	function borrarAutor(idAutor, nombreAutor) {	
		$.get("${sessionURL}", function(haySession){
		    console.log("haySession: " + haySession);
		    if(haySession) {	        
		        var urlBorrarAutor = "${pageContext.request.contextPath}" + "/borrarAutor/" + idAutor;
		        var urlCargaAutores = "${pageContext.request.contextPath}" + "/cargarAutores";
		        console.log("urlBorrarAutor " + urlBorrarAutor);
	            bootbox.confirm({
	                title: "Borrar Autor",
	                message: "Desea eliminar el autor " + nombreAutor + "?",
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
	                        $.ajax({
	                        	url: urlBorrarAutor,
	                    	    type: 'GET',
	                    	    async: true,
	                    	    cache: false,
	                    	    contentType: false,
	                    	    processData: false,                	    
	                            beforeSend: function(xhr) {
	                                $("#mantenedorAutoresCuerpo").css("position", "relative").css("min-height", "200px").prepend($("<div />").addClass("cargando"));
	                            },
	                            success: function (returnData) {
	                                console.log("SUCCESS -- elimina registro: ", returnData);	    	
	                            },
	                            error : function(e) {
	                                console.log("ERROR: ", e);
	                                $("#mantenedorAutoresCuerpo").find(".cargando").remove();			
	                            },
	                            done : function(e) {
	                                console.log("DONE");
	                            },
	                            complete : function(returnData) {
	                            	console.log(returnData);
	                                if (returnData.responseText=="OK") {
	                                    $.notify({
	                                        message: nombreAutor + " eliminado"
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
	                                $("#mantenedorAutoresCuerpo").load(urlCargaAutores,
	                                        function() {
	                                            $("#mantenedorAutoresCuerpo").find(".cargando").remove();                			                       
	                                });		            					
	                            }                		
	                        });
	                    } 
	                }
	            });	        
		    } else {
		          bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ltima accin y hemos caducado tu sesin por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
		                        , function(){
		                              window.open('${raizURL}', '_self');
		                        }
		           );
		    }
		});
	}

</script>
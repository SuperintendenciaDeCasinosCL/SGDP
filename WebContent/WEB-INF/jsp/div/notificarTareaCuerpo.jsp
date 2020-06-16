<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:url value="${urlFuncPhp}" var="urlFuncPhp" />

<div class="container">
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#tabProcesosVigentes">Proceso Vigentes</a></li>
    <li><a data-toggle="tab" href="#tabProcesoNoVigentes">Procesos No Vigentes</a></li>
  </ul>

  <%-- Primer DIV --%>

  <div class="tab-content">
    <div id="tabProcesosVigentes" class="tab-pane fade in active">
    <br>
     <form name="procesoVigentesForm" id="procesoVigentesForm">
	      <div class="col-sm-10 form-horizontal">
			<h3 class="centrar-botones" >Asignar usuarios a subproceso vigente</h3>
	      
	           <br>
	        
	      
				      <div class="form-group">
				      
					     <label class="control-label col-sm-4">Seleccione SubProceso :</label>
	
					       
					       <div class="col-sm-6">
						       <select class="form-control select2-filtros-notificador validate[required]" id="procesoVigentesNotificador"
						        name="procesoVigentesNotificador" onchange="buscarTareaPorIdProceso('vigente')">
						        	 <option value="">Seleccione SubProceso</option>
								    <c:forEach items="${listaProcesosVigentes}" var="procesosVigentes">
										   <option value="${procesosVigentes.idProceso}">${procesosVigentes.nombreProceso} (${procesosVigentes.nombreMacroproceso})</option>
								   </c:forEach>	
							   </select>	       
					       </div>	
					             	
					     <%--        
					    <div class="col-sm-1">
	                		<span class="glyphicon glyphicon-info-sign cursorPuntero info-sgdp" data-toggle="tooltip" data-placement="top" 
	                		  title="Ver Proceso"  style="cursor: pointer"  onclick="dibujarProcesoModal('vigente')"></span>
	                    </div>--%>    	
					             		      
				      </div>
				      
				      <div class="form-group">
				      
		
					     	  <label class="control-label col-sm-4 ">Seleccione Tarea :</label>
			
					       
					       <div class="col-sm-6">
						       <select class="form-control select2-filtros-notificador validate[required]"  id="tareaVigenteNotificador" 
						       name="tareaVigenteNotificador"
						       	onchange="buscarUsuariosPorIdTarea('vigente')">
								
							   </select>	       
					       </div>	
					       
					        <div class="col-sm-1">
	                		
	                		<%-- <span class="glyphicon glyphicon-info-sign cursorPuntero info-sgdp" data-toggle="tooltip" data-placement="top" 
	                		  title="Ver Proceso"  style="cursor: pointer"  onclick="dibujarProcesoModal('vigente')"></span>--%>
	                		  
	                		  <a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
												onclick="dibujarProcesoModal('vigente')">
												<span class="fa fa-gears"></span>
								        	</a>
	                    </div> 
					             		      
				      </div>
				     
			 <br>
			 <br> 	     
			 <div class="col-sm-4">
				<select name="libres[]" class="form-control" id="libres" multiple="multiple" size="15">
				</select>
			 </div>
			 
			 <div class="col-sm-4 centrar-botones" >
			    <br><br><br>
				<input type="button" class="pasar izq btn btn-primary" onclick="pasar()" value="Pasar »">
				<input type="button" class="quitar der btn btn-primary" onclick="quitar()" value="« Quitar">
				<br><br>
				<input type="button" class="pasartodos izq btn btn-primary" onclick="pasartodos()" value="Todos »">
				<input type="button" class="quitartodos der btn btn-primary" onclick="quitartodos()" value="« Todos">
			</div>
			
			<div class="col-sm-4">
				<select name="asignados[]" class="form-control" id="asignados" multiple="multiple" size="15">
				</select>
			</div>
			
		
			
			<div class="col-sm-12 centrar-botones ">
				<br></br>
				<input type="button" class="btn btn-primary"  onclick="guardarAsignacion()" value="Guardar Asignación">
			</div>      
			
	      </div>

	 </form>
    </div>
    
    <%--Segundo DIV --%>
    
    <div id="tabProcesoNoVigentes" class="tab-pane fade">
      
   <br>
     <form name="procesoNoVigentesForm" id="procesoNoVigentesForm">
	      <div class="col-sm-10 form-horizontal">
			<h3 class="centrar-botones" >Asignar usuarios a subproceso no vigente</h3>
	      
	           <br>
	        
	      
				      <div class="form-group">
				      
					     <label class="control-label col-sm-4">Seleccione SubProceso :</label>
	
					       
					       <div class="col-sm-6">
						       <select class="form-control select2-filtros-notificador validate[required]" id="procesoNoVigentesNotificador"
						        name="procesoNoVigentesNotificador"  onchange="buscarTareaPorIdProceso('noVigente')">
						        	 	<option value="">Seleccione SubProceso</option>
								   		<c:forEach items="${listaProcesosNoVigentes}" var="procesosNoVigentes">
										   <option value="${procesosNoVigentes.idProceso}">[${procesosNoVigentes.idProceso}] ${procesosNoVigentes.nombreProceso} (${procesosNoVigentes.nombreMacroproceso} , no vigente)</option>
								   		</c:forEach>	
							   </select>	       
					       </div>	
					    <%--   
					    <div class="col-sm-1">
	                		<span class="glyphicon glyphicon-info-sign cursorPuntero info-sgdp" data-toggle="tooltip" data-placement="top" 
	                		  title="Ver Proceso" style="cursor: pointer" onclick="dibujarProcesoModal('noVigente')"></span>
	                    </div>--%> 
					       
					             		      
				      </div>
				      
				      <div class="form-group">
				      
		
					 		<label class="control-label col-sm-4 ">Seleccione Tarea :</label>
			
					       
					       	<div class="col-sm-6">
						       <select class="form-control select2-filtros-notificador validate[required]"  id="tareaNoVigenteNotificador" 
						       name="tareaNoVigenteNotificador"
						       	onchange="buscarUsuariosPorIdTarea('noVigente')">
								
							   </select>	       
					       	</div>	
					       
					        <div class="col-sm-1">
		                		
		                		<%-- <span class="glyphicon glyphicon-info-sign cursorPuntero info-sgdp" data-toggle="tooltip" data-placement="top" 
		                		  title="Ver Proceso" style="cursor: pointer" onclick="dibujarProcesoModal('noVigente')"></span>--%>
		                		 
		                		 <a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Diagrama del Subproceso" 																										
												onclick="dibujarProcesoModal('noVigente')">
												<span class="fa fa-gears"></span>
								        	</a>
		                		  
		                    </div>
					             		      
				      </div>
				     
			 <br>
			 <br> 	     
			 <div class="col-sm-4">
				<select name="libresNoVigente[]" class="form-control" id="libresNoVigente" multiple="multiple" size="15">
				</select>
			 </div>
			 
			 <div class="col-sm-4 centrar-botones" >
			    <br><br><br>
				<input type="button" class="pasar izq btn btn-primary" onclick="pasarNoVigente()" value="Pasar »">
				<input type="button" class="quitar der btn btn-primary" onclick="quitarNoVigente()" value="« Quitar">
				<br><br>
				<input type="button" class="pasartodos izq btn btn-primary" onclick="pasartodosNoVigente()" value="Todos »">
				<input type="button" class="quitartodos der btn btn-primary" onclick="quitartodosNoVigente()" value="« Todos">
			</div>
			
			<div class="col-sm-4">
				<select name="asignadosNoVigente[]" class="form-control" id="asignadosNoVigente" multiple="multiple" size="15">
				</select>
			</div>
			
		
			
			<div class="col-sm-12 centrar-botones ">
				<br></br>
				<input type="button" class="btn btn-primary"  onclick="guardarAsignacionNoVigentes()" value="Guardar Asignación">
			</div>      
			
	      </div>

	 </form>


    </div>
  </div>
</div>


<script>
	
	var inicializaSelect2FiltrosNotificador = function(){
		$(".select2-filtros-notificador").select2({
		    dropdownAutoHeight : 'true',
		    height: 'auto',
		    theme: "bootstrap"
		});
	};

       function buscarTareaPorIdProceso(estadoProceso){

    	  var contextPath = "${pageContext.request.contextPath}"
    	   
        var proceso = {};

        if (estadoProceso == 'vigente'){  
       	 	proceso["idProceso"] = $("#procesoVigentesNotificador").val();
        }else if ('noVigente'){
        	proceso["idProceso"] = $("#procesoNoVigentesNotificador").val();
        }else{
			consolo.log("Error al detectar el estado del proceso : " + estadoProceso)
         }   
	       
	    	$.ajax({
				type : "POST",
				contentType : "application/json",
				url : contextPath + "/buscarTareasPorIdProcesoParaNotificar",
				data : JSON.stringify(proceso),
				dataType : 'json',
				timeout : 100000,
				success : function(data) {

				if (estadoProceso == 'vigente') {	
					$("#tareaVigenteNotificador").html("");
					
					 $('<option>').val("").text("Seleccione Tarea").appendTo('#tareaVigenteNotificador');
					$.each(data, function(k, v) {
					    $('<option>').val(v.idTarea).text("(" + v.idTarea + ") " + v.nombreTarea).attr("data-iddiagramatarea",v.idDiagrama).appendTo('#tareaVigenteNotificador');
					});
				}else if ('noVigente'){
					
					$("#tareaNoVigenteNotificador").html("");
					
					 $('<option>').val("").text("Seleccione Tarea").appendTo('#tareaNoVigenteNotificador');
					$.each(data, function(k, v) {
					    $('<option>').val(v.idTarea).text("[" + v.idTarea + "] " + v.nombreTarea).attr("data-iddiagramatarea",v.idDiagrama).appendTo('#tareaNoVigenteNotificador');
					});
						
				}else{
						consolo.log("Error al detectar el estado del proceso : " + estadoProceso)
			     } 
								
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
	       	   			
	   }


	  function buscarUsuariosPorIdTarea(estadoProceso){
		  
		  var tareaDTO = {};
		  var contextPath = "${pageContext.request.contextPath}"
		  if (estadoProceso == 'vigente'){  
		 	

		  	 	if ($("#tareaVigenteNotificador").val() == ""){
			 		return;
			    }
		    	   
		       	tareaDTO["idTarea"] = $("#tareaVigenteNotificador").val();
		 	    $("#libres").html("");
		 	    $("#asignados").html("");
		 	    
		  }else if ('noVigente'){
			  
			  if ($("#tareaNoVigenteNotificador").val() == ""){
			 		return;
			    }
		    	   
		       	tareaDTO["idTarea"] = $("#tareaNoVigenteNotificador").val();
		 	    $("#libresNoVigente").html("");
		 	    $("#asignadosNoVigente").html("");
		 	    
		  }else{
				consolo.log("Error al detectar el estado del proceso : " + estadoProceso)
	      } 
			        
			    	$.ajax({
						type : "POST",
						contentType : "application/json",
						url : contextPath + "/buscarUsuariosPorIdTarea",
						data : JSON.stringify(tareaDTO),
						dataType : 'json',
						timeout : 100000,
						success : function(data) {
							console.log("success: ", data);

						if (estadoProceso == 'vigente'){  
								if (data.resultado == "OK"){	
										for (i = 0; i < data.usuariosNotificacionTareaDTO.length; ++i) {
											if (data.usuariosNotificacionTareaDTO[i].idTarea == null){
												 $('<option>').val(data.usuariosNotificacionTareaDTO[i].idUsuario).text(data.usuariosNotificacionTareaDTO[i].idUsuario).appendTo('#libres');
											}else{
												 $('<option>').val(data.usuariosNotificacionTareaDTO[i].idUsuario).text(data.usuariosNotificacionTareaDTO[i].idUsuario).appendTo('#asignados');
											}
										}
								}else{
									bootbox.alert("Error al buscar los usuarios Asignados a la tarea, favor contactar con  UTDP");
								}
						}else if ('noVigente'){	
								if (data.resultado == "OK"){	
									for (i = 0; i < data.usuariosNotificacionTareaDTO.length; ++i) {
										if (data.usuariosNotificacionTareaDTO[i].idTarea == null){
											 $('<option>').val(data.usuariosNotificacionTareaDTO[i].idUsuario).text(data.usuariosNotificacionTareaDTO[i].idUsuario).appendTo('#libresNoVigente');
										}else{
											 $('<option>').val(data.usuariosNotificacionTareaDTO[i].idUsuario).text(data.usuariosNotificacionTareaDTO[i].idUsuario).appendTo('#asignadosNoVigente');
										}
									}
								}else{
									bootbox.alert("Error al buscar los usuarios Asignados a la tarea, favor contactar con  UTDP");
								}
						}else{
								consolo.log("Error al detectar el estado del proceso : " + estadoProceso)
					    } 
							
						},
						error : function(e) {
							console.log("ERROR: ", e);
						}
					});
			       	   			
		  
		  } 

	  function pasartodos(){
		  $('#libres option').each(function() { $(this).remove().appendTo('#asignados'); });
	  }	  

	  function quitartodos(){
		  $('#asignados option').each(function() { $(this).remove().appendTo('#libres'); });
	  }

	  function pasar(){
		   return !$('#libres option:selected').remove().appendTo('#asignados');
	  }

	  function quitar(){
		  return !$('#asignados option:selected').remove().appendTo('#libres'); 
	  }

	  //* ------------------------------------------------------------------------------

	  function pasartodosNoVigente(){
		  $('#libresNoVigente option').each(function() { $(this).remove().appendTo('#asignadosNoVigente'); });
	  }	  

	  function quitartodosNoVigente(){
		  $('#asignadosNoVigente option').each(function() { $(this).remove().appendTo('#libresNoVigente'); });
	  }

	  function pasarNoVigente(){
		   return !$('#libresNoVigente option:selected').remove().appendTo('#asignadosNoVigente');
	  }

	  function quitarNoVigente(){
		  return !$('#asignadosNoVigente option:selected').remove().appendTo('#libresNoVigente'); 
	  }
	  
	  

	  function guardarAsignacion(){

		  var contextPath = "${pageContext.request.contextPath}"
		  
          var validaFormProcesoVigente = $("#procesoVigentesForm").validationEngine('validate');
  	
	    	if (!validaFormProcesoVigente) {
	    		return;
	    	}


	    	/*
	    	var cont= $('#asignados option').length;
			if(cont<1){

				$.notify({
					message: 'No has seleccionado ningún usuario, favor seleccione al menos 1'
				},{
					type: 'warning'
				});
				
				return false;
			}  */

			 
		 	var usuariosAsignadosDTO = new Array();
		        $("#libres option").each(function (colIndex, c) {
		        			usuariosAsignadosDTO.push({    		
					        		idUsuario : $(this).val() ,
					        		idTarea :  $("#tareaVigenteNotificador").val(),
					        		asignado : "no",
					            }); 
				   });   	    
		      


	        $("#asignados option").each(function (colIndex, c) {
	        			usuariosAsignadosDTO.push({    		
				        		idUsuario : $(this).val() ,
				        		idTarea :  $("#tareaVigenteNotificador").val(),
				        		asignado : "si",
				            }); 
			   }); 
			     	    
		   console.log(usuariosAsignadosDTO); 
	
   		   
		   var formData = new FormData(); 
		   
		   formData.append("usuariosAsignadosDTOString" , JSON.stringify(usuariosAsignadosDTO));
		   
			
		      $.ajax({
		         url:  contextPath + "/guardarUsuariosAsignadosNotificacion",
		         type: 'POST',
		         data: formData,
		         dataType: 'text',
		         processData: false,
		         contentType: false,
		         dataType : 'json',
				 success : function(data) {
					console.log("data: ", data);

				 if (data.mensaje == 'OK'){

				//	 $("#libres").html("");
				// 	 $("#asignados").html("");
				// 	 $("#tareaVigenteNotificador").html("");				 	 
				// 	 $('#procesoVigentesNotificador').val('').trigger('change');
					 $.notify({
							message: 'Se han asignado los usuarios correctamente'
						},{
							type: 'success'
						});
				 }else{
					 $.notify({
							message: 'Error al asignar los usuarios'
						},{
							type: 'danger'
					});
				 }
					
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		
    	 }



	  function guardarAsignacionNoVigentes(){

		  var contextPath = "${pageContext.request.contextPath}"
		  
          var validaFormProcesoVigente = $("#procesoNoVigentesForm").validationEngine('validate');
  	
	    	if (!validaFormProcesoVigente) {
	    		return;
	    	}

	    	/*
	    	var cont= $('#asignadosNoVigente option').length;
			if(cont<1){

				$.notify({
					message: 'No has seleccionado ningún usuario, favor seleccione al menos 1'
				},{
					type: 'warning'
				});
				
				return false;
			} */ 

			 
		 	var usuariosAsignadosDTO = new Array();
		        $("#libresNoVigente option").each(function (colIndex, c) {
		        			usuariosAsignadosDTO.push({    		
					        		idUsuario : $(this).val() ,
					        		idTarea :  $("#tareaNoVigenteNotificador").val(),
					        		asignado : "no",
					            }); 
				   });   	    
		      


	        $("#asignadosNoVigente option").each(function (colIndex, c) {
	        			usuariosAsignadosDTO.push({    		
				        		idUsuario : $(this).val() ,
				        		idTarea :  $("#tareaNoVigenteNotificador").val(),
				        		asignado : "si",
				            }); 
			   }); 
			     	    
		   console.log(usuariosAsignadosDTO); 
	
   		   
		   var formData = new FormData(); 
		   
		   formData.append("usuariosAsignadosDTOString" , JSON.stringify(usuariosAsignadosDTO));
		   
			
		      $.ajax({
		         url:  contextPath + "/guardarUsuariosAsignadosNotificacion",
		         type: 'POST',
		         data: formData,
		         dataType: 'text',
		         processData: false,
		         contentType: false,
		         dataType : 'json',
				 success : function(data) {
					console.log("data: ", data);

				 if (data.mensaje == 'OK'){

				//	 $("#libres").html("");
				// 	 $("#asignados").html("");
				// 	 $("#tareaVigenteNotificador").html("");				 	 
				// 	 $('#procesoVigentesNotificador').val('').trigger('change');
					 $.notify({
							message: 'Se han asignado los usuarios correctamente'
						},{
							type: 'success'
						});
				 }else{
					 $.notify({
							message: 'Error al asignar los usuarios'
						},{
							type: 'danger'
					});
				 }
					
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		
    	 }
	 	 

	  function dibujarProcesoModal(estadoProceso){	 
		  
          var idProc = "";
          var idTarea = "";
          var idDiagramaTarea = "";
          var urlFuncPhp = "${urlFuncPhp}";

          console.log("estadoProceso: " + estadoProceso);
		  console.log("urlFuncPhp: "+ urlFuncPhp);
		  
		    if (estadoProceso == 'vigente'){  //tareaNoVigenteNotificador
		    	idProc =  $("#procesoVigentesNotificador").val();
		    	idTarea = $("#tareaVigenteNotificador").val();
		    	
	        }else if ('noVigente'){
	        	idProc =  $("#procesoNoVigentesNotificador").val();
	        	idTarea = $("#tareaNoVigenteNotificador").val();
	        }else{
				consolo.log("Error al detectar el estado del proceso : " + estadoProceso)
				
				$.notify({
					message: 'No has seleccionado ningún usuario, favor seleccione al menos 1'
				},{
					type: 'error'
				});
				
				return;
	        }   
		  
		
			if (idProc <= 0) {
				bootbox.alert("Por favor seleccione un SubProceso... ");
				return;
			} 

			if (idTarea <= 0) {
				bootbox.alert("Por favor seleccione una Tarea... ");
				return;
			} else {
				 if (estadoProceso == 'vigente') {
					 idDiagramaTarea = $('#tareaVigenteNotificador option:selected').attr('data-iddiagramatarea');
				 } else if ('noVigente') {
					 idDiagramaTarea = $('#tareaNoVigenteNotificador option:selected').attr('data-iddiagramatarea');
				 } else {
					consolo.log("Error al detectar el estado del proceso : " + estadoProceso);
					$.notify({
						message: 'No has seleccionado ningún usuario, favor seleccione al menos 1'
					},{
						type: 'error'
					});
					
					return;
		        } 
				console.log("idDiagramaTarea: " + idDiagramaTarea);
			}
			
			var urlDibujaProceso = "http://"+urlFuncPhp+"/proceso/bpm/this_task.php?idTask="+idDiagramaTarea+"&idProc="+idProc;
			console.log("urlDibujaProceso: " + urlDibujaProceso);
	        $.get("/sgdp/verificarSession", function(haySession){
	            console.log("haySession: " + haySession);
	            if(haySession){			            
	                 window.open(urlDibujaProceso, "Proceso", 'width=1080, height=600');
	            }else{
	                  bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                                , function(){
	                                      window.open('/sgdp/', '_blank');
	                                }
	                   );
	            }
	     	});			 
			
		 }
		 

		
	$(document).ready(function() {
		$(inicializaSelect2FiltrosNotificador);
		//$(checkeaTipoDeElemento);	
	});

</script>

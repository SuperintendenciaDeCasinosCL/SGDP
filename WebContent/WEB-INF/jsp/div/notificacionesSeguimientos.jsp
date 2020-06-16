<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %> 
<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="permisoModificaArchivos" value="<%=PermisoType.MODIFICA_ARCHIVOS.getNombrePermiso()%>"/>
<c:set var="FORMATO_FECHA" value="<%=Constantes.FORMATO_FECHA_FORM%>" />

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

  <br>
  <br>
  
	<div class="table-responsive">	
			<table id="tablaNotificacionesSeguimientos" class="table table-striped table-bordered table-hover">											
			    <thead>
			        <tr>
			            <th>SubProceso</th>
						<th>Tarea</th>
						<th>Expediente</th>
						<th>Responsable</th>
						<th>Plazo Tarea</th>
			        	<th>Plazo Total</th>
			        	<th>Acciones</th>
			    	</tr>
				</thead>
				
				<tbody>																		  
				 <c:forEach var="instanciaDeTareaDTO" items="${instanciasDeTareasDTOEnSeguimiento}">
						<tr>
						    <td class="view-message">${instanciaDeTareaDTO.nombreDeProceso}</td>
							<td class="view-message">${instanciaDeTareaDTO.nombreDeTarea}</td>
							<td class="view-message">${instanciaDeTareaDTO.nombreExpediente}</td>
							<td class="view-message">
							
							<c:choose>
							  <c:when test="${instanciaDeTareaDTO.idEstadoTarea == 3}">
							         Finalizada
							  </c:when>
							  <c:when test="${instanciaDeTareaDTO.idEstadoTarea == 4}">
									 Anulada
							  </c:when>
							  <c:otherwise>
							  	   ${instanciaDeTareaDTO.primerUsuarioAsignado}
								 <c:if test = "${instanciaDeTareaDTO.cantidadDeMasUsuariosAsignados gt 0}">								
								  	y ${instanciaDeTareaDTO.cantidadDeMasUsuariosAsignados} <i onclick="muestraMasUsuariosAsignados('${instanciaDeTareaDTO.usuariosAsignadosString}', '${instanciaDeTareaDTO.idInstanciaDeTarea}')" id="plusUsuarios${instanciaDeTareaDTO.idInstanciaDeTarea}" class="glyphicon glyphicon-plus cursorPuntero" ></i>
								 </c:if>							  
							  </c:otherwise>
							</c:choose>		
																	
							</td>
							<td class="view-message">
								<span style="display: none;" >
					    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" pattern="yyyy-MM-dd" /> 
					    		</span>								
								<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeTarea}" />
							</td>
							<td class="view-message">
								<span style="display: none;" >
					    		  <fmt:formatDate value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeProceso}" pattern="yyyy-MM-dd" /> 
					    		</span>									
								<fmt:formatDate pattern="${FORMATO_FECHA}" value="${instanciaDeTareaDTO.fechaVencimientoInstanciaDeProceso}" />
							</td>
							<td class="inbox-small-cells">							
								<a href="#" class="btn btn-success btn-sm" title="Ver Historial Proceso" 
								  onclick="verHistorialProceso('${instanciaDeTareaDTO.idExpediente}')">
									<span class="glyphicon glyphicon-log-in"></span>
								</a>
								
								<a href="#" class="btn btn-success btn-sm" title="Ver Todos Documentos" 
								  onclick="verHistorialTodosDocumentos('${instanciaDeTareaDTO.idExpediente}')">
									<span class="glyphicon glyphicon-folder-open"></span>
								</a>
								
								<!--  
								<a href="#" class="btn btn-success btn-sm" title="Ver Documentos Finales" 
								  onclick="verHistorialDocumentosFinales('${instanciaDeTareaDTO.idExpediente}')">
									<span class="glyphicon glyphicon-folder-open"></span>
								</a>
								-->
								<a href="#" class="btn btn-danger btn-sm boton-cerrar-expediente" title="Dejar seguir" 
								  onclick="dejarSeguirNotificacion(${instanciaDeTareaDTO.idInstanciaDeProceso})" >
									<span class="glyphicon glyphicon-remove"></span>
								</a>
								
								<a href="#" class="btn btn-primary btn-sm boton-cerrar-expediente" title="Vinculaciones" 																										
													onclick="vinculaciones('${instanciaDeTareaDTO.idExpediente}', '${instanciaDeTareaDTO.nombreExpediente}')">
													<span class="glyphicon glyphicon-link font-icon-1"></span>
									        </a>
								
							</td>
						</tr>
					</c:forEach>		
				 
				 </tbody>
			</table>
		</div>
		
		

		
 <!-- Modal -->
 
 <div class="modal fade" id="historialProcesoSeguimiento" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Historial De Procesos</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
					
				<div id="historialProcesoSeguimientooModal" class="panel-body">					
				
				</div>              	
                   
			</div>				         
         
        </div>
      </div>
      
    </div>
  </div>
  
   <div class="modal fade" id="historialTodosDocumentosSeguimiento" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Todos los Documentos</h4>
        </div>
        <div class="modal-body">
			<div class="row">
					
				<div id="historialTodosDocumentosSeguimientoModal" class="panel-body">					
				
				</div>              	
                   
			</div>
        </div>
      </div>
    </div>
  </div>
 
 <!-- Historial de procesos -->
 
  <div class="modal fade" id="historialProceso" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Historial De Procesos</h4>
        </div>
        <div class="modal-body">
         
			<div class="table-responsive">
							  		
			 		<table id="tablaHistorialDelProceso" class="table table-striped table-bordered tabla_espacio_total" >											
				    <thead>
				        <tr>
							<th>Tarea</th>
	          				<th>Fecha</th>
				            <th>Comentario</th>	
				            <th>Acci&oacute;n</th> 
				            <th>Usuario</th>          						       
				        </tr>
				    </thead>
				    <tbody>
				    

				    
				    </tbody>
				</table>
			 	
			 	
			</div>
			         
         
        </div>
      </div>
      
    </div>
  </div>
		
		
<!-- Historial de todos los documentos  -->

  <div class="modal fade" id="historialTodosDocumentos" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Todos los Documentos</h4>
        </div>
        <div class="modal-body">

			<div class="table-responsive">
										
				<table id="tablaTodosDocumentos" class="table table-striped table-bordered table-hover tabla_espacio_total">											
				    <thead>
				        <tr>
				            <th>Nombre</th>
				            <th>CDR</th>
				            <th>Fecha Creaci&oacute;n</th>
				            <th>Creador</th>
				            <th>Descripci&oacute;n</th>         				            
				        </tr>
				    </thead>
				    <tbody>								    
				    					           						     															  
				   </tbody>
				</table>
			</div>	
         
        </div>
      </div>
      
    </div>
  </div>

		
<!-- Historial de documentos finales  -->		

  <div class="modal fade" id="historialDocumentosFinales" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Documentos Finales</h4>
        </div>
        <div class="modal-body">

			<div class="table-responsive">
										
				<table id="tablaTodosDocumentosFinales" class="table table-striped table-bordered table-hover tabla_espacio_total">											
				    <thead>
				        <tr>
				            <th>Nombre</th>
				            <th>CDR</th>
				            <th>Fecha Creaci&oacute;n</th>
				            <th>Creador</th>
				            <th>Descripci&oacute;n</th>				                     				            
				        </tr>
				    </thead>
				    <tbody>								    
				    					           						     															  
				   </tbody>
				</table>
			</div>	
         
        </div>
      </div>
      
    </div>
  </div>		
  
  
		<script type="text/javascript">
  jQuery(document).ready(
    			function($) { 
    				formatTablaNotificacionesSeguimientos();      
    				//formatTablaHistorialDelProceso();
    				//formatTablaTodosDocumentos();
    				formatTablaTodosDocumentosFinales();                				   
   		     });

	function formatTablaNotificacionesSeguimientos() {

		var tablaNotificacionesSeguimientos = $('#tablaNotificacionesSeguimientos')
		.DataTable(
				{
					buttons : [ {
						extend : 'excelHtml5',
						filename : 'TareasEnEjecucion.*',
						exportOptions : {
							columns : ':visible'
						}
					}, 'colvis' ],

					"language" : lenguaje_es,
					"pageLength": 8
					
				});

		tablaNotificacionesSeguimientos.buttons().container().appendTo(
		'#tablaNotificacionesSeguimientos_wrapper .row:eq(0)');
	};


	
	function dejarSeguirNotificacion(idInstanciaDeProceso){
		$.get("${sessionURL}", function(haySession){
	          console.log("haySession: " + haySession);
	          if(haySession) {
							bootbox.confirm({
							message: "¿Dejar de seguir el proceso?",
							buttons: {
									confirm: {
											label: 'Si',
											className: 'btn-success'
									},
									cancel: {
											label: 'No',
											className: 'btn-danger'
									}
							},
							callback: function (result) {
									if (result){

								var contextPath = "${pageContext.request.contextPath}"
									
									 var InstanciaDeProceso = {}
									 InstanciaDeProceso["idInstanciaDeProceso"] =  idInstanciaDeProceso;

											$.ajax({
											type : "POST",
											contentType : "application/json",
												url: contextPath + "/" + "dejarDeSeguimiento",
											data : JSON.stringify(InstanciaDeProceso),
											dataType : 'json',
											timeout : 100000,
											success : function(data) {

																		if (data.mensaje == "ERROR"){
																			$.notify({
														message: 'Error al dejar de seguir'
													},{
														type: 'danger'
													}); 
																		}	           							 
											},
											error : function(e) {
												 console.log("ERROR: ", e);
											},
											complete: function(e){
											 /*	cargaDetalleDeTarea("${instanciaDeTareaDTO.nombreExpediente}"
														,"${instanciaDeTareaDTO.idInstanciaDeTarea}"
														,"true"
														,"${instanciaDeTareaDTO.idExpediente}"
														,"${instanciaDeTareaDTO.urlControl}");
											 */
											 $("#divTabsDetalleDeTarea").addClass('hide');					
												notificacionSeguimiento();
												
											},
							
										});
									}
							}
					});	
	          } else {
	                bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
	                              , function(){
	                                    window.open('${raizURL}', '_blank');
	                              }
	                 );
	          }
	    });		
	}


      function verHistorialProceso(idExpediente) {  

    	  var urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda = $("#urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda").val()+"2/"+idExpediente;

    	  $('#historialProcesoSeguimientooModal').load(urlGetHistoricoDeInstDeTareaPorIdExpedienteBusqueda, function(){
    		  $("#historialProcesoSeguimiento").modal('show');
		    });                 
  	  	
         }



       function CrearTablaDatosHistorialDelProceso(data) {
   	
	var tableHistorialProceso = $('#tablaHistorialDelProceso').DataTable();	
	
	tableHistorialProceso.destroy();
   	
       var tableHistorialProceso =  $('#tablaHistorialDelProceso').DataTable({
           "language" : lenguaje_es,
           columns: [

                     {
                         title: 'Tarea',
                         data:'nombreTareaDeOrigen',
		                 render: function ( data, type, row ) {
		                     return row.nombreTareaDeOrigen ;
		                 }
                    },
                    
                     {
                         title: 'Fecha',                         
                         data:'fechaMovimiento',
		                 render: function ( data, type, row ) {
		                     return  "<span style='display: none;' >"+moment(row.fechaMovimiento).format("yyyy-MM-dd")+"</span>" + moment(row.fechaMovimiento).format("DD/MM/YYYY HH:mm:ss");
		                 }
		          },

                     {
                         title: 'Comentario',
                         data:'comentario',
		                 render: function ( data, type, row ) {
		                     return row.comentario ;
		                 }
                    }, 
                     {
                         title: 'Acci&oacute;n',
                         data:'nombreAccion',
		                 render: function ( data, type, row ) {
		                     return row.nombreAccion ;
		                 }
                    }, 
                     {
                         title: 'Usuario',
                         data:'idUsuarioOrigen',
		                 render: function ( data, type, row ) {
		                     return row.idUsuarioOrigen ;
		                 }
                    },  
                 ],
                 
                 data: data,              
           buttons: [
               {extend: 'excel', title: 'HistorialProceso'},
              {extend: 'colvis'}, 
           ],
           pageLength: '4',
           order : [[ 1, "asc" ]], 
           lengthMenu: [ [4, 8, 12, -1], [4, 8, 12, "Todos"] ],

       });

       tableHistorialProceso.buttons().container().appendTo(
      	'#tablaHistorialDelProceso_wrapper .row:eq(0)');
   	
   }


      function formatTablaHistorialDelProceso() {

      	var tablaHistorialDelProceso = $('#tablaHistorialDelProceso')
      	.DataTable(
      			{
      				buttons : [/* {
      					extend : 'copyHtml5',
      					exportOptions : {
      						columns : [ 0, ':visible' ]
      					}
      				},*/ {
      					extend : 'excelHtml5',
      					filename : 'HistorialProceso.*',
      					exportOptions : {
      						columns : ':visible'
      					}
      				}, 'colvis' ],

      				"language" : languajeDataTableHistorialDelProceso,
      				"pageLength": 4,
      				"order": [[ 4, "desc" ]]
      				
      			});
      	
      	tablaHistorialDelProceso.buttons().container().appendTo(
      	'#tablaHistorialDelProceso_wrapper .row:eq(0)');
      }



function formatTablaTodosDocumentos() {
	
	var tablaTodosDocumentos = $('#tablaTodosDocumentos')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'TareasEnEjecucion.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 8
				
			});

	tablaTodosDocumentos.buttons().container().appendTo(
	'#tablaTodosDocumentos_wrapper .row:eq(0)');
};

function formatTablaTodosDocumentosFinales() {
	
	var tablaTodosDocumentosFinales = $('#tablaTodosDocumentosFinales')
	.DataTable(
			{
				buttons : [ {
					extend : 'excelHtml5',
					filename : 'TareasEnEjecucion.*',
					exportOptions : {
						columns : ':visible'
					}
				}, 'colvis' ],

				"language" : lenguaje_es,
				"pageLength": 8
				
			});

	tablaTodosDocumentosFinales.buttons().container().appendTo(
	'#tablaTodosDocumentosFinales_wrapper .row:eq(0)');
};


	function verHistorialTodosDocumentos(idExpediente) {
		
		var urlGetTablaDetalleDeExpedientePorIdExpediente = $("#urlGetTablaDetalleDeExpedientePorIdExpediente").val()+'2?idExpediente='+idExpediente;

		$('#historialTodosDocumentosSeguimientoModal').load(urlGetTablaDetalleDeExpedientePorIdExpediente, function(){
   			$("#historialTodosDocumentosSeguimiento").modal('show');
		});
                 	  	
	}  

       function CrearHistorialTodosDocumentos(data) {


      	// var contextPath = "${pageContext.request.contextPath}";
	    	var alticket  = '<c:out value="${ticket}"/>';

	    	
	var tablaTodosDocumentos = $('#tablaTodosDocumentos').DataTable();	
	
	tablaTodosDocumentos.destroy();
   	
       var tablaTodosDocumentos =  $('#tablaTodosDocumentos').DataTable({
           "language" : lenguaje_es,
           columns: [

                     {
                         title: 'Nombre',
                         data:'nombre',
		                 render: function ( data, type, row ) {
		                	 return  '<a href="#" onclick="descargaArchivoNotSeg(\''+row.idArchivo+'\')" >' +row.nombre + '</a>' ;
		                     //return  "<a href='"+ row.linkDescargaNavegador+"?ticket="+alticket+"' target='_blank'>" +row.nombre + "</a>" ;
		                 }
                    },
                    
                     {
                         title: 'CDR',                         
                         data:'cdr',
		                 render: function ( data, type, row ) {
		                     return row.cdr;
		                 }
		          },

                     {
                         title: 'Fecha Creaci&oacute;n',
                         data:'fechaCreacion',
		                 render: function ( data, type, row ) {
		                     return row.fechaCreacion ;
		                 }
                    }, 
                     {
                         title: 'Creador',
                         data:'creador',
		                 render: function ( data, type, row ) {
		                     return row.creador ;
		                 }
                    }, 
                     {
                         title: 'Descripci&oacute;n',
                         data:'descripcion',
		                 render: function ( data, type, row ) {
		                     return row.descripcion ;
		                 }
                    },  
                 ],
                 
                 data: data,
                /* rowsGroup: [
                   'first:name',
                 ],*/
           buttons: [
               {extend: 'excel', title: 'HistorialProceso'},
              {extend: 'colvis'}, 
           ],
           pageLength: '4',
           lengthMenu: [ [4, 8, 12, -1], [4, 8, 12, "Todos"] ],

       });

       tablaTodosDocumentos.buttons().container().appendTo(
      	'#tablaTodosDocumentos_wrapper .row:eq(0)');
   	
   }




       function verHistorialDocumentosFinales(idExpediente){           

       	var contextPath = "${pageContext.request.contextPath}";

		var instanciaDeProceso  = {};
		instanciaDeProceso["idExpediente"] = idExpediente;
				
       	 
		$.ajax({
			type : "POST",
			contentType : "application/json",
		    url: contextPath + "/" + "buscarTodosDocumentosFinalesSeguimiento" ,
			data : JSON.stringify(instanciaDeProceso),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("Success: " , data);
			    CrearHistorialDocumentosFinales(data);					
			},
			error : function(e) {
				 console.log("ERROR: ", e);
			},
			complete: function(e){
				 $("#historialDocumentosFinales").modal('show');	
			},

		});      	  
          }   


       function CrearHistorialDocumentosFinales(data) {


       	// var contextPath = "${pageContext.request.contextPath}";
 	    	var alticket  = '<c:out value="${ticket}"/>';

 	    	
		var tablaTodosDocumentosFinales = $('#tablaTodosDocumentosFinales').DataTable();	
 	
		tablaTodosDocumentosFinales.destroy();
    	
        var tablaTodosDocumentosFinales =  $('#tablaTodosDocumentosFinales').DataTable({
            "language" : lenguaje_es,
            columns: [

                      {
                          title: 'Nombre',
                          data:'nombre',
			                 render: function ( data, type, row ) {
			                     return  "<a href='"+ row.linkDescargaNavegador+"?ticket="+alticket+"' target='_blank'>" +row.nombre + "</a>" ;
			                 }
                     },
                     
                      {
                          title: 'CDR',                         
                          data:'cdr',
			                 render: function ( data, type, row ) {
			                     return row.cdr;
			                 }
			          },

                      {
                          title: 'Fecha Creaci&oacute;n',
                          data:'fechaCreacion',
			                 render: function ( data, type, row ) {
			                     return row.fechaCreacion ;
			                 }
                     }, 
                      {
                          title: 'Creador',
                          data:'creador',
			                 render: function ( data, type, row ) {
			                     return row.creador ;
			                 }
                     }, 
                      {
                          title: 'Descripci&oacute;n',
                          data:'descripcion',
			                 render: function ( data, type, row ) {
			                     return row.descripcion ;
			                 }
                     },  
                  ],
                  
                  data: data,
                 /* rowsGroup: [
                    'first:name',
                  ],*/
            buttons: [
                {extend: 'excel', title: 'HistorialProceso'},
               {extend: 'colvis'}, 
            ],
            pageLength: '4',
            lengthMenu: [ [4, 8, 12, -1], [4, 8, 12, "Todos"] ],

        });

       tablaTodosDocumentosFinales.buttons().container().appendTo(
       	'#tablaTodosDocumentosFinales_wrapper .row:eq(0)');
    	
    }

    function descargaArchivoNotSeg(idArchivo) {
    	var contextPath = "${pageContext.request.contextPath}";
    	var urlDescarga = contextPath + "/getArchivoPorId/" + idArchivo;
 		console.log("urlDescarga: " + urlDescarga);
 		var urlSessionValida = $("#urlSessionValida").val();
 		var raizURL = $("#raizURL").val();
 		console.log("urlSessionValida: " + urlSessionValida);
 		$.get(urlSessionValida, function(haySession) {
 		      console.log("haySession: " + haySession);
 		      if(haySession) {
 		    	  window.location.href = urlDescarga;
 		      }	else {
 		            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
 		                          , function(){
 		                                window.open(raizURL, '_blank');
 		                          }
 		             );
 		      }
 		});  
 	}

</script>
		
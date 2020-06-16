<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container">

	<div class="row">

		<div id="divFormIndicadores">

			<form id="formBuscarIndicadorSubproceso" class="form-horizontal">

				<div class="form-group">
					<label class="control-label col-sm-3" for="filtrosBusqueda">Indicador
						:</label>
					<div class="col-sm-5">
						<select class="form-control validate[required]" id="nombreIndicador" name="nombreIndicador">
							<option value="">Seleccione el indicador</option>
							     <c:forEach items="${listaIndicadores}" var="indicador">
										<option value="${indicador.indicadorId}">(${indicador.indicadorId}) ${indicador.indicadorNombre}</option>
								  </c:forEach>	
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-3" for="fechaDesde">Fecha
						Desde:</label>
					<div class="col-sm-5">
						<div class="input-group date" id="fechaDesdeDate">
							<input type="text" class="form-control validate[validaRangoFecha[fechaDesde,fechaHasta],required] "
								id="fechaDesde" name="fechaDesde" placeholder="__/__/____">
							<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="fechaHasta">Fecha
						Hasta:</label>
					<div class="col-sm-5">
						<div class="input-group date" id="fechaHastaDate">
							<input type="text"
								class="form-control  validate[validaRangoFecha[fechaDesde,fechaHasta,required]]" id="fechaHasta"
								 name="fechaHasta" placeholder="__/__/____">
							<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			  <div class="form-group">
					<label class="control-label col-sm-3"> Fecha Inicio Subproceso
						:</label>
					 <div class="col-sm-1 div-radio-tarea">
							<input class="zoom-sgdp fechaIndicador" type="radio" name="fechaIndicador" value="inicio" checked>					
					</div>
					<label class="control-label col-sm-3"> Fecha Finalización Subproceso
						:</label>
					 <div class="col-sm-5 div-radio-tarea">
							<input class="zoom-sgdp fechaIndicador" type="radio" name="fechaIndicador"  value="fin">					
					</div>
					
				</div>
				
				
				<!-- 
				<div class="form-group">
					<label class="control-label col-sm-3" > Valor meta días
						:</label>
					<div class="col-sm-5">
                         <input type="text" class="form-control validate[required,custom[integer]]" id="valorMeta" name="ValorMeta">
					</div>
				</div>
 				-->

				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-5">
						<button type="button" class="btn btn-default btn-block" onclick="buscarIndicadoresSubproceso()">
							<span class="glyphicon glyphicon-search"></span> Buscar
						</button>
					</div>
				</div>
			</form>

		</div>

	</div>
	
	 <br>

    <!--  
	<div class="table-responsive col-sm-3">
		 		<table id="tablaIndicadorTotales"
					class="table table-striped table-bordered">
						<tr>
							<td>Número de elementos dentro del plazo</td>
							<th>100</th>
						</tr>
						<tr>
							<td>Número de elementos fuera del plazo</td>
							<th>10</th>
						</tr>
						<tr>
							<td>Total</td>
							<th>110</th>
						</tr>
		
				</table>
		</div>
	-->
	
	
	  <div class="table-responsive col-sm-12">
			<table id="tablaIndicador"
				class="table table-striped table-bordered">
				<thead>
					<tr>
               			 <td rowspan="1" >Número de elementos dentro del plazo</td>
              		     <th colspan="7" id="numeroElementosDentroDelPlazo" ></th>
          		    </tr>
					<tr>
               			 <td rowspan="1" >Número de elementos totales</td>
              		     <th colspan="7" id="numeroElementosFueraDelPlazo"  ></th>
          		    </tr>
					<tr>
               			 <td rowspan="1">Total</td>
              		     <th colspan="7" id="totalSolicitudes"></th>
          		    </tr>
					<tr>
						<th>NombreSubproceso</th>
						<th>Número Expediente</th>
						<th>Estado</th>
						<th>Tipo inicio</th>
						<th>Fecha inicio elemento medido</th>
						<th>Tipo fin</th>
						<th>Fecha fin elemento medido</th>
						<th>Duración</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	
	
	
	<script type="text/javascript">
	
		$(function() {
		    $('#fechaDesdeDate').datetimepicker({
		          locale : 'es',
		          format : 'DD/MM/YYYY'
		    });
		    $('#fechaHastaDate').datetimepicker({
		        locale : 'es',
		        format : 'DD/MM/YYYY'
		  });

		    crearTablaIndicador();
		    $("#nombreIndicador").select2({
	 		    theme: "bootstrap",
	 		    dropdownParent: $("#divFormIndicadores"),
	 		    language: "es"
	 		}); 
		});


		function buscarIndicadoresSubproceso(){
			var valid = $("#formBuscarIndicadorSubproceso").validationEngine('validate');

	    	if (valid == false) {
	    		$("#formBuscarIndicadorSubproceso").validationEngine();
	    		return
	    	}


	    	var contextPath = "${pageContext.request.contextPath}"
	    	
	    	
	    	entradaIndicadoresDTO = {}
	    	entradaIndicadoresDTO['idIndicador'] = $("#nombreIndicador").val(); 
	    	entradaIndicadoresDTO['fechaDesde'] =  	$("#fechaDesde").val();
	    	entradaIndicadoresDTO['fechaHasta'] =  $("#fechaHasta").val();
	    	entradaIndicadoresDTO['flagFechaSubprocesoCombo'] = $("input[name=fechaIndicador]:checked").val(); 

	    	var dialog = "";
	    	
	     	$.ajax({
				type : "POST",
				contentType : "application/json",
				url: contextPath + "/buscarSubprocesoPorIdIndicador", 
				data : JSON.stringify(entradaIndicadoresDTO),
				dataType : 'json',
				timeout : 100000,

			    beforeSend: function () {
			        // $('#loading').show();
			    	 dialog = bootbox.dialog({
			    	    message: '<p><i class="fa fa-spin fa-spinner"></i> Buscando Datos ... </p>',
			    		closeButton: false
			    	});
			    },
				
				success : function(data) {
	               console.log("SUCCESS: ", data);	

	               if (data.respuesta == "OK"){


	            	   $("#numeroElementosDentroDelPlazo").text(data.numeroElementosDentroDelPlazo)
	            	   $("#numeroElementosFueraDelPlazo").text(data.total)
	            	   
	            	   var porcentaje = 0;
	            	   
                     	if (data.numeroElementosDentroDelPlazo == 0 && data.total == 0 ){                         	
                     		porcentaje = 0;
                         	
                        }else{
                        	porcentaje = parseFloat((data.numeroElementosDentroDelPlazo * 100) / data.total).toFixed(1);
                        } 
	            	   
		 
	            	   
	            	   $("#totalSolicitudes").text(porcentaje + "%")
		               
		              
		               
	            	   CrearTablaIndicadorConDatos(data.subprocesoIndicadoresSalidaDTO);
		           }else{
		        	   bootbox.alert("Error al buscar la información, favor intentar nuevamente");
			       }
	                       	               
				},
				error : function(e) {
					 console.log("ERROR: ", e);
				},
			    complete: function () {				     
				    	dialog.modal('hide');
				}
			});
	    	
			
			}


		function CrearTablaIndicadorConDatos(data) {

			
			var tablaIndicador = $('#tablaIndicador').DataTable();	
			tablaIndicador.destroy();
			
			var tablaIndicador = $('#tablaIndicador')
					.DataTable(
							{ data: data,
								columns:[
							                 {data:'nombreSubproceso'},
							                 {data:'numeroExpediente'} ,							             
							                 {data:'estado'},
							                 {data:'tipoDeInicio'},
							                 {data:'fechaInicioElementoMedido'},
							                 {data:'tipoFin'},
							                 {data:'fechaFinElementoMedido',
							                	 createdCell: function (td, cellData, rowData, row, col) {

						                              
								                	 if (rowData.marcaFechaHasta == "A"){
								                		  $(td).addClass("estilo-amarillo");
								                		  $(td).prop('title', 'Este elemento aún no ha finalizado');
								                		  								                	 
									                 }
									                 return data;
							                	 }

								                 },
							                 {data:'duracion',
								                	 createdCell: function (td, cellData, rowData, row, col) {
								                	 if (rowData.marcaDuracion == "R"){

								                		 var diasdesfase;
									                	 if (rowData.duracion == "No se puede definir" ){
									                		 diasdesfase = 0;
												         }else {
												        	 var diasdesfase = rowData.duracion - rowData.duracionProgramadaProceso;
														  }

									                	 
							                        	 $(td).addClass("estilo-rojo");	
							                        	 $(td).prop('title', 'Este elemento tiene un desfase de ' + diasdesfase + ' días' );				
								                	 }else if (rowData.marcaDuracion == "V"){
								                		 $(td).addClass("estilo-verde");				
									                 }else if(rowData.marcaDuracion == "A"){
									                	 $(td).addClass("estilo-amarillo");		
										              }
									                 return data;
							                	 }

								                 }
							             ],
								buttons : [
								    {
									extend : 'excelHtml5',
									filename : 'indicador.*',
									exportOptions : {
										columns : ':visible'
									}
								}, 'colvis' ],

								"language" : lenguaje_es
							});
			tablaIndicador.buttons().container().appendTo(
			'#tablaIndicador_wrapper .row:eq(0)');

		}
		
		

		function crearTablaIndicador() {
			var TablaIndicador = $('#tablaIndicador')
					.DataTable(
							{
								buttons : [ 
								    /* {
									extend : 'copyHtml5',
									exportOptions : {
										columns : [ 0, ':visible' ]
									}
								}, */{
									extend : 'excelHtml5',
									filename : 'indicador.*',
									exportOptions : {
										columns : ':visible'
									}/*,
		                              action : function( e, dt, button, config ) {
		                                  exportTableToCSV.apply(this, [$('#tablaIndicador'), 'export.csv']);
		 
		                              }*/
								}, 'colvis' ],

								"language" : lenguaje_es
							});

			TablaIndicador.buttons().container().appendTo(
					'#tablaIndicador_wrapper .row:eq(0)');

		}

		/*

		function exportTableToCSV($table, filename) {
			 
		     //rescato los títulos y las filas
		     var $Tabla_Nueva = $table.find('tr:has(td,th)');
		     // elimino la tabla interior.
		     var Tabla_Nueva2= $Tabla_Nueva.filter(function() {
		          return (this.childElementCount != 1 );
		     });
		 
		     var $rows = Tabla_Nueva2,
		         // Temporary delimiter characters unlikely to be typed by keyboard
		         // This is to avoid accidentally splitting the actual contents
		         tmpColDelim = String.fromCharCode(11), // vertical tab character
		         tmpRowDelim = String.fromCharCode(0), // null character
		 
		         // Solo Dios Sabe por que puse esta linea
		         colDelim = (filename.indexOf("xls") !=-1)? '"\t"': '","',
		         rowDelim = '"\r\n"',
		 
		 
		         // Grab text from table into CSV formatted string
		         csv = '"' + $rows.map(function (i, row) {
		             var $row = $(row);
		             var   $cols = $row.find('td:not(.hidden),th:not(.hidden)');
		 
		             return $cols.map(function (j, col) {
		                 var $col = $(col);
		                 var text = $col.text().replace(/\./g, '');
		                 return text.replace('"', '""'); // escape double quotes
		 
		             }).get().join(tmpColDelim);
		             csv =csv +'"\r\n"' +'fin '+'"\r\n"';
		         }).get().join(tmpRowDelim)
		             .split(tmpRowDelim).join(rowDelim)
		             .split(tmpColDelim).join(colDelim) + '"';
		 
		 
		      download_csv(csv, filename);
		 
		 
		 }
		 
		 
		 
		function download_csv(csv, filename) {
		     var csvFile;
		     var downloadLink;
		 
		     // CSV FILE
		     csvFile = new Blob([csv], {type: "text/csv"});
		 
		     // Download link
		     downloadLink = document.createElement("a");
		 
		     // File name
		     downloadLink.download = filename;
		 
		     // We have to create a link to the file
		     downloadLink.href = window.URL.createObjectURL(csvFile);
		 
		     // Make sure that the link is not displayed
		     downloadLink.style.display = "none";
		 
		     // Add the link to your DOM
		     document.body.appendChild(downloadLink);
		 
		     // Lanzamos
		     downloadLink.click();
		 }

		*/
	</script>

</div>
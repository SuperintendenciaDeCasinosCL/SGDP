async function cargaBitacora(id){
	console.log(id);
	$.get('/sgdp/bitacoraSubTareas/' + id, function(data) {
		console.log("retront", data);
		data = data.map(acc => ({
			...acc,
			hora: moment(new Date(acc.fecha)).format("DD-MM-YYYY HH:mm"),
			duracion: Math.floor(acc.duracion / 60) + ' h:' + Math.floor(acc.duracion % 60) + ' m'
		}));
		

		if ( $.fn.DataTable.isDataTable('#bitacoraTable') ) {
 			$('#bitacoraTable').DataTable().destroy();
		}
		$("#bitacoraList").empty();
		
		
		
		data.forEach(it => {
			const usuariosRelacionados = it.usuarios ? ("<ul>" + it.usuarios.reduce((acc, us) => acc + "<li>- " + us + "</li>","") + "</ul>") : "";
			$("#bitacoraList").append("<tr><td>"+it.hora+"</td><td>"+it.nombreTarea+"</td><td>"+it.idUsuario+"</td><td>"+it.nombreActividad+"</td><td>"+it.accion+"</td><td>"+it.duracion+"</td><td>"+usuariosRelacionados+"</td></tr>")
		});
		//$('#modalBitacora').modal('show'); 	
		$('#modalBitacora').modal({backdrop: 'static', keyboard: false});
		
		let bitacoraTable = $("#bitacoraTable").DataTable(
			{
				
				"language" : languajeDataTableDocumentos,
				"pageLength": 7,
				"order": [[ 0, "desc" ]],
				buttons : [ {
							extend : 'excelHtml5',
							filename : 'Bitacora.*',
							exportOptions : {
								columns : ':visible'
							}
						}, 'colvis' ],
					});
					
		bitacoraTable.buttons().container().appendTo('#bitacoraTable_wrapper .row:eq(0)');
	});

}

function cerrarBitacora() {
	$('#modalBitacora').modal('hide');
} 
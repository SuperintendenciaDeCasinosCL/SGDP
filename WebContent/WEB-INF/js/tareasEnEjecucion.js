function cargarUsuarioAsignadosModal(usuariosAsignadosString, nombreDeTarea) {

	console.log("usuariosAsignadosString: " + usuariosAsignadosString);
	
	$("#ulUsuariosAsignadosATarea").html('');
	
	$("#cabeceraUsuariosAsigandosATareaModa").html('');
	
	$("#cabeceraUsuariosAsigandosATareaModa").append("Usuarios Asignados a Tarea " + nombreDeTarea);
	
	if (usuariosAsignadosString.indexOf(",") >= 0) {
		var arr = usuariosAsignadosString.split(',');		
		for(i=0; i < arr.length; i++) {
			$("#ulUsuariosAsignadosATarea").append('<li class="list-group-item">' + arr[i] + '</li>');
		}
	} else {
		$("#ulUsuariosAsignadosATarea").append('<li class="list-group-item">' + usuariosAsignadosString + '</li>');
	}	
	$('#usuariosAsigandosATareaModal').modal('show');
}

function muestraMasUsuariosAsignados(usuariosAsignadosString, idInstanciaDeTarea) {
	
	var tablaTareasEnEjecucion = $('#tablaTareasEnEjecucion').DataTable();
	
	console.log("usuariosAsignadosString: " + usuariosAsignadosString);
	console.log("idInstanciaDeTarea: " + idInstanciaDeTarea);
	
	var tr = $("#plusUsuarios"+idInstanciaDeTarea).closest('tr');
    var row = tablaTareasEnEjecucion.row( tr );
    
    if ( row.child.isShown() ) {
        // This row is already open - close it
        /*row.child.hide();
        tr.removeClass('shown');*/
        
        $('div.slider', row.child()).slideUp( function () {
            row.child.hide();
            tr.removeClass('shown');
        } );
        
        $("#plusUsuarios"+idInstanciaDeTarea).addClass('glyphicon-plus');
        $("#plusUsuarios"+idInstanciaDeTarea).removeClass('glyphicon-minus');
                
    }
    else {

		dataRowTable = '<div class="slider"><table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
		
		if (usuariosAsignadosString.indexOf(",") >= 0) {
			var arr = usuariosAsignadosString.split(',');		
			for(i=0; i < arr.length; i++) {				
				dataRowTable = dataRowTable + '<tr>'+
									            '<td colspan="3"></td>'+
									            '<td colspan="3">'+arr[i]+'</td>'+
									          '</tr>';
			}
		} else {			
			dataRowTable = dataRowTable + '<tr>'+
								            '<td colspan="3"></td>'+
								            '<td colspan="3">'+usuariosAsignadosString+'</td>'+
								          '</tr>';
		}
		
		dataRowTable = dataRowTable + '</table></div>';
		
        console.log("dataRowTable: " + dataRowTable);
    	
    	/*row.child( dataRowTable ).show();
        tr.addClass('shown');*/
        
        row.child( dataRowTable, 'no-padding' ).show();
        tr.addClass('shown');
        $('div.slider', row.child()).slideDown();
        
        $("#plusUsuarios"+idInstanciaDeTarea).removeClass('glyphicon-plus');
        $("#plusUsuarios"+idInstanciaDeTarea).addClass('glyphicon-minus');
    }
	
}
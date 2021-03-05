/**
 * 
 */

/*function cargarRolesUsuario() {	
	var idUsuario = $('#idUsuario').val();
	console.log("idUsuario: ", idUsuario);	
	$('#idRolUsuarioSeleccionado').empty();
	$('#idRolUsuarioSeleccionado').addClass('disabled');
    $('#idRolUsuarioSeleccionado').append($('<option>').text("Seleccione Rol").attr('value', ''));
    var cantidadDeRoles;
    var nonbreRol;
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/sgdp/login/getRolesUsuario?idUsuario="+idUsuario,		
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);					
		},
		error : function(e) {
			console.log("ERROR: ", e);			
		},
		done : function(e) {
			console.log("DONE");
		},
		complete : function(data) {			
            console.log("COMPLETE: ", data.responseJSON);
            cantidadDeRoles = data.responseJSON.length;
            console.log("cantidadDeRoles: " + cantidadDeRoles);
            $.each(data.responseJSON, function(i, obj){
                $('#idRolUsuarioSeleccionado').append($('<option>').text(obj.nombreRol).attr('value', obj.idRol));
                if (cantidadDeRoles == 1) {
                	nombreRol = obj.nombreRol;
                }
            });
            $('#idRolUsuarioSeleccionado').removeClass('disabled');
            if (cantidadDeRoles == 1) {
            	console.log("nombreRol: " + nombreRol);            	
            	$("#idRolUsuarioSeleccionado").val($('#idRolUsuarioSeleccionado option').filter(function () { return $(this).html() == nombreRol; }).val()).change();
            	$('#divRolUsuarioSeleccionado').addClass('hide');
            } else {
            	$('#divRolUsuarioSeleccionado').removeClass('hide');
            }            
		}
	}); 
}*/

function enviarFormLogin() {
	/*if ($( "#idRolUsuarioSeleccionado" ).val() == '' || $( "#idRolUsuarioSeleccionado" ).val() == undefined || $( "#idRolUsuarioSeleccionado" ).val() == null || $( "#idRolUsuarioSeleccionado" ).val() == "null") {
		if ($('#idRolUsuarioSeleccionado').has('option').length <= 0) {
			cargarRolesUsuario();	
		}
		$("#loginForm").validationEngine('validate');
	} else {
		$( "#loginForm" ).submit();
	}*/
	$( "#loginForm" ).submit();
}

$( document ).ready(
		function() {
				jQuery("#loginForm").validationEngine();
		}
);
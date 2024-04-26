function cargarComplejidadExpediente(nombreExpediente) {
    const urlSessionValida = $("#urlSessionValida").val();
    const urlComplejidadExpediente = $("#urlComplejidadExpediente").val();

    $.get(urlSessionValida, function (haySession) {
        if (haySession) {
            $.get(urlComplejidadExpediente + nombreExpediente, function (data) {
                if (data) {
                    $('#idComplejidad').empty();
                    $("#motivoComplejidad").val(data.motivoComplejidad);
                    $.each(data.complejidades, function(i, obj) {
                    	console.log(obj);
                    	if (obj.complejidad == data.complejidad) {
                    		$('#idComplejidad').append($('<option selected="selected">').text(obj.complejidad).attr('value', obj.complejidad));
                    	} else {
                    		$('#idComplejidad').append($('<option>').text(obj.complejidad).attr('value', obj.complejidad));
                    	}
                        
                    });
                }
            });
        } else {
            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                    , function () {
                        window.open(raizURL, '_self');
                    }
            );
        }
    });
}


$("#guardarComplejidad").off('click').click(() => {
    var validaForm = $("#formComplejidadModal").validationEngine('validate');
    if (validaForm == false) {
        return;
    }
    console.log('guardar complejidad');
    const idComplejidad = $("#idComplejidad").val();
    const motivo = $("#motivoComplejidad").val();
    const nombreExpediente = $("#nombreExpedienteDesdeComplejidad").val();
    const urlSessionValida = $("#urlSessionValida").val();
    const urlComplejidadExpediente = $("#urlComplejidadExpediente").val();
    console.log(idComplejidad, motivo, nombreExpediente);
    $.get(urlSessionValida, function (haySession) {
        console.log("haySession: " + haySession);
        if (haySession) {
            $.post(
                    urlComplejidadExpediente + nombreExpediente + '/' + idComplejidad ,
                    {motivo: motivo},
                    function (data) {
                        console.log("data.message: " + data.message);
                        console.log("data.type: " + data.type);
                        $.notify({message: data.message}, {type: data.type});                        
                    }).done(function() {                        
                        $('#complejidadModal').modal('hide');
                        if ($('#recargaBusquedaDesdeComplejidad').val() == "true") {
                        	cargaResultadoBusqueda();
                        }
                    }).fail(function () {
                        notify.update({'type': 'danger', 'message': ' Error al guardar !'});
                        setTimeout(function () {
                            notify.close();
                        }, 4500);
                    });
        } else {
            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                    , function () {
                        window.open(raizURL, '_self');
                    }
            );
        }
    });
});

function cargarModalComplejidad(nombreExpediente, recargaBusquedaDesdeComplejidad) {
	$("#recargaBusquedaDesdeComplejidad").val(recargaBusquedaDesdeComplejidad);
	$("#nombreExpedienteDesdeComplejidad").val(nombreExpediente);
    var urlSessionValida = $("#urlSessionValida").val();
    var raizURL = $("#raizURL").val();
    $.get(urlSessionValida, function (haySession) {
        console.log("haySession: " + haySession);
        if (haySession) {
            $("#complejidadH4").empty();
            $("#complejidadH4").append('Cambiar complejidad de Expediente ' + nombreExpediente);
            $('#complejidadModal').modal({backdrop: 'static', keyboard: false});
            cargarComplejidadExpediente(nombreExpediente);
        } else {
            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu ultima acci&oacute;n y hemos caducado tu sesi&oacute;n por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                    , function () {
                        window.open(raizURL, '_self');
                    }
            );
        }
    });

}
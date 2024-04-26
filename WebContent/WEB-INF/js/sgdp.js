function insertaLogDocumento(idDocumentoLogDocumento, tipoOperacionLogDocumento, moduloLogDocumento) {
	var urlLogDocumento = $("#urlLogDocumento").val();
	var logDocumentoDTO = {};	
	logDocumentoDTO["idDocumentoLogDocumento"] = idDocumentoLogDocumento;
	logDocumentoDTO["tipoOperacionLogDocumento"] = tipoOperacionLogDocumento;
	logDocumentoDTO["moduloLogDocumento"] = moduloLogDocumento;
	console.log("logDocumentoDTO: " + logDocumentoDTO);
	$.ajax( {
        url: urlLogDocumento,
        type: 'POST',
        data: JSON.stringify(logDocumentoDTO),
        dataType: 'json',
        contentType: "application/json",                	    
        success: function (returnData) {
            console.log("SUCCESS -- actualiza registro: ", returnData);	    	
        },
        error : function(e) {
            console.log("ERROR: ", e);	
        },
        done : function(e) {
            console.log("DONE", e);
        },
        complete : function(returnData) {
        	console.log("COMPLETE -- actualiza registro: ", returnData);	
            if (returnData.status==200) {
                console.log("Registro de log documento realizado");
            } else {
                console.log("Registro de log documento con error");
            }                			            					
        }                		
    });
}


function insertaLogDocumentoSolicitudCreacionExpediente(idDocumentoLogDocumento, tipoOperacionLogDocumento, moduloLogDocumento, idSolicitudCreacionExp) {
	var urlLogDocumentoSolicitudCreacionExpediente = $("#urllogDocumentoSolicitudCreacionExpediente").val();
	var logDocumentoDTO = {};
	logDocumentoDTO["idDocumentoLogDocumento"] = idDocumentoLogDocumento;
	logDocumentoDTO["tipoOperacionLogDocumento"] = tipoOperacionLogDocumento;
	logDocumentoDTO["moduloLogDocumento"] = moduloLogDocumento;
	logDocumentoDTO["idSolicitudCreacionExp"] = idSolicitudCreacionExp;
	
	/*var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
	xmlhttp.open("POST", urlLogDocumentoSolicitudCreacionExpediente);
	xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xmlhttp.send(JSON.stringify(logDocumentoDTO));*/
	
	$.ajax( {
        url: urlLogDocumentoSolicitudCreacionExpediente,
        type: 'POST',
        data: JSON.stringify(logDocumentoDTO),
        dataType: 'json',
        contentType: "application/json",                	    
        success: function (returnData) {
            console.log("SUCCESS -- actualiza registro: ", returnData);	    	
        },
        error : function(e) {
            console.log("ERROR: ", e);		
        },
        done : function(e) {
            console.log("DONE", e);
        },
        complete : function(returnData) {
        	console.log("COMPLETE -- actualiza registro: ", returnData);	
            if (returnData.status==200) {
                console.log("Registro de log documento realizado");
            } else {
            	console.log("Registro de log documento con error");
            }                			            					
        }                		
    });
}

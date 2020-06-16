<%@ page import= "cl.gob.scj.sgdp.config.Constantes" %> 
<%@ page import= "cl.gob.scj.sgdp.tipos.PermisoType" %>  
<%@ page import= "cl.gob.scj.sgdp.tipos.EstadoSolicitudCreacionExpType" %>  

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="container">

	<div class="row">	
	
		<div class="col-sm-5">
			<div class="row div-h-mant-lat"><h3>SubProcesos Vigentes</h3></div>		
			<div class="row">
				<select class="form-control" id="todosLosProcVig" name="todosLosProcVig" multiple="multiple" size="30">		    	
			    	<c:forEach items="${listaProcesosVigentes}" var="procesoDTO">
						<option value="${procesoDTO.codigoProceso}">${procesoDTO.nombreProceso} - [${procesoDTO.codigoProceso}]</option>
			   		</c:forEach>	
				</select>
			</div>	
		 </div>
		 
		 <div class="col-sm-2 centrar-botones div-botones-mant-lat" >
		    <div class="row">
				<input type="button" class="pasar izq btn btn-primary" onclick="pasarProcCreaExp()" value="Pasar »">
				<input type="button" class="quitar der btn btn-primary" onclick="quitarProcCreaExp()" value="« Quitar">
				<br><br>
				<input type="button" class="pasartodos izq btn btn-primary" onclick="pasarTodosProcCreaExp()" value="Todos »">
				<input type="button" class="quitartodos der btn btn-primary" onclick="quitarTodosProcCreaExp()" value="« Todos">
				<br><br><br><br>
				<a id="linkProcSolCreaExpExcelHidden" href="#" style="display: none;"></a>
				<a id="linkProcSolCreaExpExcel" onclick="procSolCreaExpExcel()" href="#" class="btn btn-default" role="button">Exportar a Excel</a>
			</div>
		</div>
		
		<div class="col-sm-5">
			<div class="row div-h-mant-lat"><h3>SubProcesos para solicitud de creaci&oacute;n de expediente</h3></div>
			<div class="row">
				<select name="todosLosProcCreacExp" class="form-control" id="todosLosProcCreacExp" multiple="multiple" size="30">
					<c:forEach items="${todosLosProcFormCreaExp}" var="procesoFormCreaExpDTO">
						<option value="${procesoFormCreaExpDTO.codigoProceso}">${procesoFormCreaExpDTO.nombreProceso} - [${procesoFormCreaExpDTO.codigoProceso}]</option>
			   		</c:forEach>
				</select>
			</div>
		</div>
	
	</div>
	
	<div class="row">
	
		<div class="col-sm-12 centrar-botones ">
			<br></br>
			<input type="button" class="btn btn-primary"  onclick="guardarAsignacionProcCreaExp()" value="Guardar Asignación">
		</div>  
	
	</div>

</div>

<c:url value="/verificarSession" var="sessionURL" />
<c:url value="/" var="raizURL" />

<script>

function pasarTodosProcCreaExp(){
	  $('#todosLosProcVig option').each(function() { $(this).remove().appendTo('#todosLosProcCreacExp'); });
}	  

function quitarTodosProcCreaExp(){
	  $('#todosLosProcCreacExp option').each(function() { $(this).remove().appendTo('#todosLosProcVig'); });
}

function pasarProcCreaExp(){
	   return !$('#todosLosProcVig option:selected').remove().appendTo('#todosLosProcCreacExp');
}

function quitarProcCreaExp(){
	  return !$('#todosLosProcCreacExp option:selected').remove().appendTo('#todosLosProcVig'); 
}

function guardarAsignacionProcCreaExp() {
	$.get("${sessionURL}", function(haySession){
	    console.log("haySession: " + haySession);
	    if(haySession) {   			 
    		var procesoFormCreaExpDTO = new Array();
    		$("#todosLosProcCreacExp option").each(function (colIndex, c) {
    			procesoFormCreaExpDTO.push({    		
    				codigoProceso : $(this).val()
    			}); 
    		});
    		console.log(JSON.stringify(procesoFormCreaExpDTO));
    		var urlGuardarAsignacionProcCreaExp = "${pageContext.request.contextPath}/guardarAsignacionProcCreaExp";    
        	var urlMantenedorProcSolCreaExp = "${pageContext.request.contextPath}"+"/mantenedorProcSolCreaExp?vistaCompleta=false";
            $.ajax( {
                url: urlGuardarAsignacionProcCreaExp,
                type: 'POST',
                data: JSON.stringify(procesoFormCreaExpDTO),
                dataType: 'json',
                contentType: "application/json",                	    
                beforeSend: function(xhr) {
                    $("#mantProcSolCreaExpDiv").addClass("cargando2");
                },
                success: function (returnData) {
                    console.log("SUCCESS -- : ", returnData);	    	
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                    $("#mantProcSolCreaExpDiv").removeClass("cargando2");	
                },
                done : function(e) {
                    console.log("DONE");
                },
                complete : function(returnData) {
                    if (returnData.responseJSON.glosa=="OK") {
                        $.notify({
                            message: 'Datos guardados correctamente'
                        },{
                            type: 'success'
                        });
                    } else {
                        $.notify({
                            message: 'Ocurrio un error al guardar los datos'
                        },{
                            type: 'danger'
                        });
                    }     			
                    $("#mantProcSolCreaExpDiv").load(urlMantenedorProcSolCreaExp,	    	                                
                            function() {
                                $("#mantProcSolCreaExpDiv").removeClass("cargando2");                			                       
                    });	
                  	            					
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

function procSolCreaExpExcel() {
    $.get("${sessionURL}", function(haySession) {
        if(haySession) {
            $("#linkProcSolCreaExpExcelHidden").attr('href', '${pageContext.request.contextPath}/procSolCreaExpExcel');	    		
            document.getElementById("linkProcSolCreaExpExcelHidden").click();
        } else {
            bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Ha pasado algo de tiempo desde tu última acción y hemos caducado tu sesión por seguridad, por favor presiona aceptar y vuelve a hacer login. </p></div>"
                     , function(){
                           window.open('${raizURL}', '_blank');
                     }
            );
        }
    });	
}

</script>
 const fieldDateFrom = 'txt-date-from';
 const fieldDateTo = 'txt-date-to';
 const fieldFilterText = 'txt-filter-text';
 const btnSearch = 'btnSearch';
 
 const divDateFrom = 'div-date-from';
 const divDateTo = 'div-date-to';
 const tableReport = 'tablaReportLogDocument';
 const bodyTableModal = 'bodyModalExtraData';
 const modalLogDocumento = 'viewLogDocumentoModal';
 
 const type_success = 'success';
 const type_danger = 'danger';
 const type_warning = 'warning';

 var table = null;
 $(document).ready(function(){
     configurePopover();
     loadDefaultValues();
     table = configTable();
     configureControls(table);
 });

 function printMessage(message,type){
	$.notify(
		{ message : message }, 
		{ type : type }
	);
}
function configurePopover(){
	var arrow = $('<div/>').addClass('arrow');
	var title = $('<h3/>').addClass('popover-title').css('display', 'none');
	var content = $('<div/>').addClass('popover-content');
	var popover = $('<div/>').addClass('popover bg-danger').attr('role','tooltip');
	popover.append(arrow);
	popover.append(title);
	popover.append(content);
	var content = $('<div/>').append(popover);
	
	var options={
		trigger: 'manual',
		template: content.html(),
		placement: 'bottom'
	};
	$('[data-toggle="popover"]').popover(options);
}

 function loadDefaultValues(){
    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var dateText = day+'/'+month+'/'+year;
    $('#'+fieldDateFrom).val(dateText);
    $('#'+fieldDateTo).val(dateText);
 }
 
 function configureControls(table){
     $('#'+divDateFrom).datetimepicker({
           locale : 'es',
           format : 'DD/MM/YYYY'
     });
     $('#'+divDateTo).datetimepicker({ 
         locale : 'es',
         format : 'DD/MM/YYYY'
     });
     $('#'+btnSearch).on('click',function(){
        loadTable();
     });
 }
 
 function loadTable(){
    if(validateFields()){
        var text = $('#'+fieldFilterText).val();
        table.search(text);
        table.draw();         
    }
 }
 
 function configTable(){
     var table = $('#'+tableReport).DataTable({
          serverSide: true,
          lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
          ajax: {
                url: "getListLogDocumentoDataTable",
               type: "POST",
               data: function(d){ 
                   d.dateFrom = parseDateFormat($('#'+fieldDateFrom).val());
                   d.dateTo = parseDateFormat($('#'+fieldDateTo).val());
               }
          },
          language:{
			"decimal":        "",
			"emptyTable":     "Sin datos para mostrar",
			"info":           "Mostrando registros del _START_ al _END_ de _TOTAL_ registros totales",
			"infoEmpty":      "Mostrando registros del 0 al 0 de 0 registros totales",
			"infoFiltered":   "(filtrado desde _MAX_ registros totales)",
			"infoPostFix":    "",
			"thousands":      ",",
			"lengthMenu":     "Mostrar _MENU_ registros",
			"loadingRecords": "Cargando...",
			"processing":     "",
			"search":         "Buscar:",
			"zeroRecords":    "No hay coincidencia de registros",
			"paginate": {
				"first":      "Primero",
				"last":       "\u00DAltimo",
				"next":       "Siguiente",
				"previous":   "Anterior"
			},
			"aria": {
				"sortAscending":  ": activando el ordenamiento de columna acendente",
				"sortDescending": ": activando el ordenamiento de columna decendente"
			}
		 },
          columns:[
              {data:"idLogDocumento"},
              {data:"tipoOperacionLogDocumento"},
              {data:"moduloLogDocumento"},
              {data:"fechaLogDocumento"},
              {data:"nombreUsuarioLogDocumento"},
              {data:"ipLogDocumento"},
              {data:"codigoExpedienteLogDocumento"},
              {data:"nombreDocumento"},
              {
                  data:"idLogDocumento",
                  render: function(data, type){
                      var contenedor = $('<div/>');
                      var button = $('<button/>');
                      button.addClass('btn btn-primary btn-sm');
                      var span = $('<span/>');
                      span.addClass('glyphicon glyphicon-eye-open');
                      button.append(span);
                      button.attr('onClick', 'openViewExtraData('+ data +')');
                      contenedor.append(button);
                      var text = contenedor.html()
                      return text;
                  }
              },
          ]
     });
     $('.dataTables_filter').hide();
     return table;
 }
 
function parseDateFormat(text){
    var array = text.split("/");
    return completeCharacter(array[0],2,'0')+'/'+completeCharacter(array[1],2,'0')+'/'+array[2];
}
function completeCharacter(text, size, fillCharacter){
    if(text != null && text.length>0){
        for(var index = text.length; index < size; index++){
            text = fillCharacter + text;
        }
    }
    return text;
}
 function validateFields(){ 
     var dateFrom = $('#'+fieldDateFrom).val();
     var dateTo = $('#'+fieldDateTo).val();
     var fieldText = $('#'+fieldFilterText).val();
     var expregDate = /^([0-2][0-9]|3[0-1])(\/)(0[1-9]|1[0-2])(\/)(\d{4})$/;
     var expregText = /[^A-Za-z0-9\_\-\:\.\s]+/;
     var error = 0;
     
     if(expregDate.test(dateFrom)) {
        $('#'+fieldDateFrom).popover('hide');
     } else {
        $('#'+fieldDateFrom).popover('show');
         error++;	
     }
     
     if(expregDate.test(dateTo)) {
        $('#'+fieldDateTo).popover('hide');
     } else {
        $('#'+fieldDateTo).popover('show');
         error++;
     }
     
     var millisDateFrom = getMillis(dateFrom);
     var millisDateTo = getMillis(dateTo);
     
     if( millisDateFrom > millisDateTo ) {
        printMessage('La fecha desde debe ser menor que la fecha hasta.', type_danger);
        error++;
     }
     
     if(!expregText.test(fieldText)) {
        $('#'+fieldFilterText).popover('hide');
     } else {
        $('#'+fieldFilterText).popover('show');
         error++;
     }
     
     if(error == 0) {
         return true;
     }
     return false;
 }
 
 function getMillis(textDate){
     var array = textDate.split('/');
     var date = new Date(array[2]+'-'+array[1]+'-'+array[0]);
     var time = date.getTime();
     return time;
 }
 
 function viewErrorMessage(field, status){
     if(status){
         $('#'+field).show();
     }else{
         $('#'+field).hide();
     }
 }
 
 function openViewExtraData(id){
     var opt = $.ajax({
         url: "getLogDocumentobyId/"+id,
         method: 'GET',
         contentType: 'application/json'
     });
     opt.done(function(data){
         if(data.code == 0){
             fillTableExtraData(data);
             $('#'+modalLogDocumento).modal('show');
         }else{
            printMessage(data.message, type_danger);
         }
     });
 }
 
 function fillTableExtraData(response){
     var dto = response.data;
     var body = $('#'+bodyTableModal).html('');
     var tr = $('<tr/>');
     tr = addTextRow('Id', dto.idLogDocumento);
     body.append(tr);
     tr = addTextRow('Tipo Operacion', dto.tipoOperacionLogDocumento);
     body.append(tr);
     tr = addTextRow('Modulo', dto.moduloLogDocumento);
     body.append(tr);
     tr = addTextRow('IP', dto.ipLogDocumento);
     body.append(tr);
     tr = addTextRow('Fecha', dto.fechaLogDocumento);
     body.append(tr);
     tr = addTextRow('Id Usuario', dto.idUsuarioLogDocumento);
     body.append(tr);
     tr = addTextRow('Nombre Usuario', dto.nombreUsuarioLogDocumento);
     body.append(tr);
     tr = addTextRow('Id Expediente', dto.idExpedienteLogDocumento);
     body.append(tr);
     tr = addTextRow('Codigo Expediente', dto.codigoExpedienteLogDocumento);
     body.append(tr);
     tr = addTextRow('Id Docuemnto', dto.idDocumentoLogDocumento);
     body.append(tr);
     tr = addTextRow('Nombre Docuemnto', dto.nombreDocumento);
     body.append(tr);
 }
 function closeViewLogDocument(){
    $('#'+modalLogDocumento).modal('hide');
 }

 function addTextRow(key, value){
     var tr = $('<tr/>');
     tr = addTextCell(tr, key);
     tr = addTextCell(tr, value);
     return tr;
 }
 
 function addTextCell(tr, text){
     var td = $('<td/>').html(text);
     tr.append(td);
     return tr;
 }

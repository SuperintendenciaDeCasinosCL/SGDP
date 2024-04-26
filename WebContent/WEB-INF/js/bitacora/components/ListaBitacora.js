Vue.component('ListaBitacora', {
    props: ['reload','tiposAcciones'],
    template: //html
        `
<div class="row">
				<table class="table" id="bit">
					<thead>
						<tr>
							<th>Fecha</th><th>Tarea</th><th>Usuario</th><th>Tipo Subtarea</th><th>Subtarea</th><th>Tiempo dedicado</th><th>Usuarios relacionados</th><th>Eliminar</th>
						</tr>
					</thead>
					<tbody>
						<tr  v-for="acc in acciones">
							<td>{{moment(new Date(acc.fecha)).format("DD-MM-YYYY HH:mm")}}</td>
							<td>{{acc.nombreTarea}}</td>
							<td>{{acc.idUsuario}}</td>
							<td>{{tiposAcciones.filter(it => it.id === acc.idActividad)[0].nombre}}</td>
							<td>{{acc.accion}}</td>
							<td>{{getTime(acc.duracion)}}</td>
							<td>
								<ul>
									<li v-for="us in acc.usuarios">
										- {{us}}
									</li>
								</ul>
							</td>
							<td>
								<div v-if="estadoInstanciaDeTarea === '2' && acc.canDelete" >
								<button class="btn btn-danger btn-small" @click="elimina(acc)" >X</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
</div>
        `,
    mounted: function () {
		this.instanciaDeTarea = $("#idInstTarea").val();
		this.estadoInstanciaDeTarea = $("#idEstadoTarea").val();
        this.loadBitacora();

		},
	watch: {
		reload: function() {
			this.loadBitacora();
		},
		usuariosFiltro: function() {
			this.doFilter();
		},
		acciones: function() {
			this.click();
		}
	},
    methods: {
		loadBitacora: async function() {
			if(this.instanciaDeTarea !== 0) {
				const lista = await this.getBitacora(this.instanciaDeTarea);
				this.acciones = lista.sort((a, b) => b.fecha - a.fecha);
			}
		},
		getTime: function (t) {
			const hours = Math.floor(t / 60);
			const mins = Math.floor(t % 60);
			
			const str = [];
			
			if(hours > 0) {
				str.push(hours + ' hora' + (hours > 1 ? 's' : ''));				
			}
			
			if(mins > 0) {
				str.push(mins + ' minuto' + (mins > 1 ? 's' : ''));
			}
			
			if(str.length === 2) {
				return str.join(' y ');
			} else {
				return str.join('');
			}

		},
		click: function () {
			if(this.tabla) {
				$('#bit').DataTable().destroy();
			} 
			
			setTimeout(() => {
				this.tabla = $('#bit').DataTable({
						order: [0, 'desc'],
						"language" : languajeDataTableDocumentos,
						"pageLength": 10,
						buttons : [ {
							extend : 'excelHtml5',
							filename : 'Bitacora.*',
							exportOptions : {
								columns : ':visible'
							}
						}, 'colvis' ],
					});
					
					this.tabla.buttons().container().appendTo('#bit_wrapper .row:eq(0)');
				},100)
		},
		elimina: async function(accion) {
			const that = this;
			bootbox.confirm({
    			title: "Eliminar registro de bit&aacute;cora",
        		message: "Desea eliminar el registro de la bit&aacute;cora?",
        		buttons: {
            		confirm: {
                	label: '<span class="glyphicon glyphicon-ok-circle font-icon-3"></span>',
                	className: 'btn-success'
            	},
            	cancel: {
                	label: '<span class="glyphicon glyphicon-remove-circle font-icon-3"></span>',
                	className: 'btn-danger'
            	}
        	},
        	callback: function (result) {
            	if (result == true) {
					that.doRemove(accion)
					return true;
		    	}
				return true;
        	}
    	}); 
			
		},
		doRemove: async function(accion) {
			await this.remove(accion);
			this.loadBitacora();
		},
		remove: async function(bitacora) {
			try {
				data = await axios({
					method: 'delete',
					url: '/sgdp/bitacoraSubTareas',
					data: bitacora
				});
				data = data.data;
			} catch (error) {
				data = []
			}

			return data;
		},
		getBitacora: async function(idInstTarea) {
    		try {
        		data = await axios({
            		method: 'get',
            		url: '/sgdp/bitacoraSubTareas/'+idInstTarea,
        		});
        		data = data.data;
    		} catch (error) {
        		data = []
    		}

    		return data;
		}
		
    },
    data() {
        return {
			acciones: [],
			instanciaDeTarea: $("#idInstTarea").val(),
			estadoInstanciaDeTarea: $("#idEstadoTarea").val(),
			tabla: null,
			filtered: []
        }
    }
})
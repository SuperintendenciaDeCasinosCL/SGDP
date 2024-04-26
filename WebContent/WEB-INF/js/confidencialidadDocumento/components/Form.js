Vue.component('Form', {
	props: ['idDocumento'],
	template: //html
		`
        <div id="container" style="padding: 0.5em;" >
				<div class="row">
					Usuarios
					<select v-model="usuariosAsignados" id="asignadorUsuarios" class="form-control select2" multiple="" tabindex="-1" aria-hidden="true">
					</select>
				</div>
				<hr></hr>
				<div class="row">
					Roles
					<select v-model="rolesAsignados" id="asignadorRoles" class="form-control select2" multiple="" tabindex="-1" aria-hidden="true">
					</select>
				</div>
				<hr></hr>
                <div class="row">
                	<input type="hidden" id="idArchivoCmsInput"/>
                    <button class="btn btn-primary" @click="guardarAsignacion()">Guardar asignacion</button>
                </div>
		</div>
        `,
	mounted: function() {
		
	},
	methods: {
		guardarAsignacion: async function() {
			console.log("Guardando asignacion")
			
			const rolesAsig = $("#asignadorRoles").val() ? $("#asignadorRoles").val() : [0];
			const usuariosAsig = $("#asignadorUsuarios").val() ? $("#asignadorUsuarios").val() : [0];
			console.log({rolesAsig, usuariosAsig})

			const req = {
				usuarios: usuariosAsig,
				roles: rolesAsig,
				id: $("#idArchivoCmsInput").val()
			};
			
			const res = await this.sendAsignacion(req);
			
			console.log({res});
			
			if(res.status === 200) {
				$.notify({
					message: 'Asignacion realizada.'
				},{
					type: 'success'
				});
			} else {
				$.notify({
					message: 'La asignacion no pudo ser realizada.'
				},{
					type: 'error'
				});
			}
			
			
		},
		sendAsignacion: async function(req) {
		    try {
        		data = await axios({
            		method: 'post',
            		url: '/sgdp/confidencialidadDocumento/' + req.id + '/' + req.usuarios + '/' + req.roles,
            		data: req
        		});
        		console.log(data.data)
    		} catch (error) {
        		data = []
    		}

    		return data;
		}
	},
	watch: {
		idDocemnto: function() {
			this.loadData();
		}
	},
	data() {
		return {
			usuarios: [],
			roles: [],
			usuariosAsignados: [],
			rolesAsignados: [],
			idDocemnto: 0
		}
	}
})
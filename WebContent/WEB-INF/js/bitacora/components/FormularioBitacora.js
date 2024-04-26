Vue.component('FormularioBitacora', {
	template: //html
		`
        <template>
            <div class="container-fluid">
				<div class="row">
					&nbsp;
				</div>
                
				<div class="row">
                    <h2>
						Agregar Subtarea en bit&aacute;cora
					</h2>
				</div>
						
   
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
                            <label for="control">Tipo de Subtarea</label>

                            <select v-model="accionSeleccionada" id="accionSeleccionada" class="form-control">
								<option :value="0">Seleccione</option>
                                <option v-for="acc in acciones" :value="acc.id">{{acc.nombre}}</option>
                            </select>
						</div>
					</div>
					<div class="col-sm-4" id="select">
						<div class="form-group">
                            <label for="usuarios">Usuarios relacionados</label>

                            <select v-model="usuariosSeleccionados" id="usuariosSeleccionados" class="form-control select2" multiple>
                                <option v-for="us in usuarios" :value="us">{{us}}</option>
                            </select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group" id="tiempoDedicadoDiv">
							<label class="control-label">Tiempo dedicado:</label>
							<br>
							<div class="durationpicker-container form-control">
								<div class="durationpicker-innercontainer" style="display: inline-block;">
									<input v-model="duracionHoras" min="0" max="undefined" placeholder="0" type="number" id="duration-hours" class="durationpicker-duration" oninput="validity.valid||(value='');">
									<span class="durationpicker-label">h</span>
								</div>
								<div class="durationpicker-innercontainer" style="display: inline-block;">
									<input v-model="duracionMinutos" min="0" max="59" placeholder="0" type="number" id="duration-minutes" class="durationpicker-duration" oninput="validity.valid||(value='');">
									<span class="durationpicker-label">m</span>
								</div>
							</div>
							<input type="text" id="tiempoDedicado" name="tiempoDedicado" style="display: none;">					
						</div>
					</div>
				</div>
				<div class="row justify-content-between">
					<div class="col-sm-10">
						<div class="form-group">
                        	<label for="accion">Subtarea a registrar</label>
                            <textarea class="form-control" v-model="accion" id="accion" placeholder="Ingrese el detalle de la subtarea realizada" maxlength="1000"/>
                        </div>
					</div>
				</div>
				<div class="row justify-content-between">
					<div class="col-sm-8">
					 <button class="btn btn-primary btn-small" @click="insertar($event)" :disabled="disableButton">Guardar</button>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-sm-6">
						<h2>Hist&oacute;rico</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<ListaBitacora :reload="reload" :tiposAcciones="acciones" />
					</div>
				</div>
			</div>
		</template>
        `,
	mounted: function() {
		this.instanciaDeTarea = $("#idInstTarea").val();
		this.cargarTiposAcciones();
		this.select = $("#usuariosSeleccionados").select2({
			    theme: "bootstrap",
			    dropdownParent: $("#select"),
			    language: "es"
		  }).on('select2:select', (e) => {
      			this.usuariosSeleccionados.push(e.params.data.id);
   			}).on('select2:unselect', (e) => {
      			this.usuariosSeleccionados = this.usuariosSeleccionados.filter(it=> it !== e.params.data.id);
   			});

	},
	methods: {
		cargarTiposAcciones: async function() {
			this.acciones = await this.getTiposAcciones();
			this.usuarios = await this.getUsuarios();
		},
		esValido: function() {
			let valido = true;
			if(this.instanciaDeTarea === 0) {
				valido = false;
			}
			if(this.accion.length < 1) {
				valido = false;
			}
			if( this.accionSeleccionada === 0) {
				valido = false;
			}
			if(Number(this.duracionHoras) * 60 + Number(this.duracionMinutos) === 0) {
				valido = false;
			}
			
			if(!valido) {
				 $.notify({ message: "Todos los campos son obligatorios." }, { type: 'danger' });
			}
			
			return valido;
		},
		insertar: async function(event) {
			if(!this.esValido()) {
				return;
			}

			const res = await this.save({
				idBitacora: 0,
				idInstanciaDeTarea: this.instanciaDeTarea,
				idActividad: this.accionSeleccionada,
				accion: this.accion,
				usuarios: this.usuariosSeleccionados,
				duracion: Number(this.duracionHoras) * 60 + Number(this.duracionMinutos)
			});
			this.reload = !this.reload;
		},
		save: async function(bitacora) {

			try {
				data = await axios({
					method: 'post',
					url: '/sgdp/bitacoraSubTareas',
					data: bitacora
				});
				this.clear();
			} catch (error) {
				data = []
			}

			return data;
		},
		clear: function() {
			this.accionSeleccionada = 0;
			this.accion = '';
			this.duracionHoras = 0;
			this.duracionMinutos = 0;
			this.duracion = 0;
			this.usuariosSeleccionados = [];
			this.select.val(null).trigger("change");
		},
		getTiposAcciones: async function() {
			try {
				data = await axios({
					method: 'get',
					url: '/sgdp/bitacoraSubTareas/tiposActividad',
				});
				data = data.data.filter(it => it.activo);
			} catch (error) {
				data = []
			}

			return data;
		},
		getUsuarios: async function(){
			try {
				data = await axios({
					method: 'get',
					url: '/sgdp/getUsuarios',
				});
				data = Array.isArray(data.data) ? data.data.map(it => it.idUsuario).filter((v, i, a) => a.indexOf(v) === i) : [];
			} catch (error) {
				data = []
			}
			return data;
		}
	},
	watch: {

	},
	data() {
		return {
			acciones: [],
			usuarios: [],
			tiposAcciones: [],
			accionSeleccionada: 0,
			usuariosSeleccionados: [],
			accion: '',
			disableButton: false,
			instanciaDeTarea: $("#idInstTarea").val(),
			reload: true,
			duracionHoras: 0,
			duracionMinutos: 0,
			duracion: 0,
			select: null,
			selectFiltro: null
		}
	}
})
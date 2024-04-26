Vue.component('Formulario', {
	template: //html
		`
        <template>
            <div class="container">
				<div class="row">
					&nbsp;
				</div>
                <div class="row">
                    <div class="card">
                        <div class="card-body">
							<div class="form-group">
                            	<label for="control">Seleccione proceso</label>

                            	<select v-model="procesoSeleccionado" id="procesoSeleccionado" class="form-control">
                                	<option v-for="p in procesos" :value="p.idProceso">{{p.nombreProceso}}</option>
                            	</select>
							</div>
							
                        </div>
                    </div>
                </div>
				<div class="row">
                    <div class="card">
						<div class="card-header">
							<h2>
								Documentos del proceso seleccionado
							</h2>
							<div class="pull-right">
								<button type="button" class="btn btn-primary" @click="guardarAsignaciones()">Guardar asignaciones</button>
								<button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalPlantilla">
  									Crear/Editar plantilla
								</button>
							</div>
						</div>
						
                        <div class="card-body">
							<div class="row">
								&nbsp;
							</div>
							<table class="table">
								<thead>
									<tr>
										<th>ID tarea</th><th>Tarea</th><th>Documento</th><th>Plantilla asignada</th><th>Plantilla a Asignar</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="doc in documentos">
										<td>{{doc.codigoTarea}}</td>
										<td>{{doc.nombreTarea}}</td>
										<td>{{doc.nombreDeTipoDeDocumento}}
										<td>{{doc.nombrePlantilla != null ? doc.nombrePlantilla : '-'}}</td>
										<td>
											<select @change="cambiaPlantilla(doc.idTipoDeDocumento, doc.idTarea, $event)">
												<option value="0" :selected="!doc.plantilla">Sin plantilla asignada</option>
												<option v-for="pl in plantillas" :value="pl.idPlantilla" :selected="doc.idPlantilla == pl.idPlantilla">{{pl.codigo}} | {{pl.nombre}}</option>
											</select>
										</td>
									</tr>
								</tbody>
							</table>
														
                        </div>
                    </div>
                </div>



				<div class="modal fade" id="modalPlantilla" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" >
  					<div class="modal-dialog modal-dialog-centered modal-lg">
    					<div class="modal-content">
      						<div class="modal-header">
        						<h5 class="modal-title" id="exampleModalLongTitle">Creaci&oacute;n y edici&oacute;n de plantillas</h5>
        						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          							<span aria-hidden="true">&times;</span>
        						</button>
      						</div>
      						<div class="modal-body container-fluid">
								<div class="row">
									<div class="col-sm-4">
										<select v-model="plantillaSeleccionada" @change="cambiaPlantillaEdicion($event)" class="form-control">
											<option value="0">Nueva plantilla</option>
											<option v-for="pl in plantillas" :value="pl.idPlantilla" >{{pl.codigo}} | {{pl.nombre}}</option>
										</select>
									</div>
								</div>
								<hr>
								<div class="row">
									<div class="col-sm-4">
										<div class="form-group">
                                			<label for="nombrePlantilla">Nombre de la plantilla</label>
                                    		<input class="form-control" v-model="form.nombrePlantilla" id="nombrePlantilla"/>
                                		</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
                                			<label for="descripcionPlantilla">Descripci&oacute;n de la plantilla</label>
                                    		<input class="form-control" v-model="form.descripcionPlantilla" id="descripcionPlantilla"/>
                                		</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
                                			<label for="codigoPlantilla">C&oacute;digo de la plantilla</label>
                                    		<input class="form-control" v-model="form.codigoPlantilla" id="codigoPlantilla"/>
                                		</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
                                			<label for="control">Tipo de plantilla</label>
											<input type="radio" v-model="tipoPlantilla" name="tipoPlantilla" value="formulario" checked>
											Formulario en pantalla
											&nbsp;&nbsp;&nbsp;
											<input type="radio" v-model="tipoPlantilla" name="tipoPlantilla" value="documento">
											Documento adjunto
										</div>
									</div>
								</div>
								<hr>
								<div v-if="tipoPlantilla === 'formulario'">
								<div class="row">
									<div class="col-sm-4">
										<div class="form-group">
                                			<label for="control">Tipo de campo</label>
                                    		<select v-model="form.control" id="control" class="form-control">
                                        		<option v-for="ctrl in controls" :value="ctrl.id">{{ctrl.desc}}</option>
                                    		</select>
                                		</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
                                    		<label for="name">Nombre del campo</label>
                                    		<input class="form-control" v-model="form.name" id="name"/>
                                		</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
                                    		<label for="description">Descripci&oacute;n del campo</label>
                                    		<input class="form-control" v-model="form.desc" id="description"/>
                                		</div>
									</div>
								</div>
								
								

								<div class="container-fluid">
                            		<div class="form-group" v-if="form.control === 'select' || form.control === 'radio' || form.control === 'checkbox'">
                                    	<label for="option">Ingrese opcion</label>
                                    	<input class="form-control" v-model="form.option" id="option"/>
                                    	<br>
                                    	<button class="btn btn-info btn-sm" @click="addOption($event)">Agregar opcion</button>
                                    	<br><br>
                                    	<div class="row">
                                        	<div class="col-6 d-flex align-items-stretch">
                                            	<ul class="list-group">
                                                	<li v-for="option in form.options" class="list-group-item">
                                                        {{option}}
                                                        <button class="btn btn-warning btn-sm" @click="removeOption($event, option)">Remover opcion</button>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
									<div class="form-group">
										<button class="btn btn-warning btn-sm" @click="addPield($event)">Agregar campo</button>
									</div>
									<div class="form-group">
										<PreForm :form="fields" :removeField="removeField"/>
									</div>
								</div>
								</div>

								<div v-if="tipoPlantilla === 'documento'">
									<div class="row">
									<div class="col-sm-4">
										<div class="form-group">
											<label for="archivo">Seleccione archivo</label>
											<input class="form-control" type="file"/>
										</div>
									</div>
								</div>
								</div>

	                        </div>
                            <div class="modal-footer">
                            	<button @click="guardarPlantilla($event)" :disabled="tipoPlantilla === 'formulario' && fields.length === 0" class="btn btn-primary">Guardar plantilla</button>
                            </div>
                        </div>
                    </div>
                </div>
			</div>
		</template>
        `,
	mounted: function() {
		this.loadProcesos();
		this.loadPlantillas();
	},
	methods: {
		cambiaPlantillaEdicion: function(event) {

			let found = false;

			for(let pl of this.plantillas) {
				if(pl.idPlantilla === Number(event.target.value)) {
					console.log(pl);
					this.fields = JSON.parse(pl.plantilla);
					this.plantilla = pl;
					this.form = {
						...this.form,
						nombrePlantilla: pl.nombre,
						descripcionPlantilla: pl.nombre,
						codigoPlantilla: pl.codigo
					}
					found = true;
					break;
				}
			}

			if(!found) {
				this.plantilla = null;
				this.fields = [];
				this.resetFull(); 
			}
			
			
		},
		showNuevaPlantilla: function() {

		},
		guardarAsignaciones: async function() {
			const res = await updateAsignaciones(this.relacionPlantillaDocumento);
			console.log(res);
			const p = this.procesoSeleccionado;
			this.loadDocumentos(p);

		},
		cambiaPlantilla: function(idTipoDeDocumento, idTarea, event) {
			const idPlantilla = event.target.value;
			const removedList = this.relacionPlantillaDocumento.filter(it => it.idTipoDocto !== idTipoDeDocumento && it.idTarea !== idTarea);
			removedList.push({ idTipoDeDocumento, idPlantilla, idTarea });

			this.relacionPlantillaDocumento = removedList;

		},
		loadProcesos: async function() {
			const data = await getProcesos();
			if (data) {
				this.procesos = data;
			}
		},
		loadDocumentos: async function(idProceso) {
			const data = await getDocumentos(idProceso);
			if (data) {
				this.documentos = data;
			}
		},
		loadPlantillas: async function() {
			const data = await getPlantillas();
			if (data) {
				this.plantillas = data;
			}
		},
		addOption: function(event) {
			event.preventDefault();
			this.form.options.push(this.form.option);
			this.form.option = '';
		},
		removeOption: function(event, opt) {
			event.preventDefault();
			this.form.options = this.form.options.filter(it => it !== opt);
		},
		addPield: function(event) {
			event.preventDefault();
			if (this.form.name === '' || this.form.desc === '' || this.form.control === '') {
				alert('Todos los campos son requeridos');
				return;
			}

			if ((this.form.control === 'select' || this.form.control === 'radio' ) && this.form.options.length < 2) {
				alert('Al seleccionar "SELECTOR", "OPCION UNICA" debe agregar dos o mas opciones');
				return;
			}

			const f = {
				...this.form,
				name: this.form.name,
				desc: this.form.desc,
				type: this.form.control,
				options: this.form.options
			}

			this.fields.push(f);
			this.reset();
		},
		removeField: function(field) {
			this.fields = this.fields.filter(it => it.name !== field.name);
		},
		resetFull: function() {
			this.form = {
				nombrePlantilla: '',
				descripcionPlantilla: '',
				codigoPlantilla: '',
				control: '',
				name: '',
				option: '',
				options: [],
				desc: ''
			};
			this.tipoPlantilla = 'formulario';
			this.plantillaSeleccionada = 0;
		},
		reset: function() {
			this.form = {
				...this.form,
				control: '',
				name: '',
				option: '',
				options: [],
				desc: ''
			}
		},
		guardarPlantilla: async function(event) {
			event.preventDefault();
			if (this.tipoPlantilla === 'formulario' && this.fields.length === 0) {
				return
			}

			const data = {
				nombre: this.form.nombrePlantilla,
				descripcion: this.form.descripcionPlantilla,
				codigo: this.form.codigoPlantilla,
				vigente: true,
				plantilla: JSON.stringify(this.fields)
			}

			const res = await savePlantilla(data);
			
			if (res.status === 200) {
				this.resetFull();
				this.fields = [];
				this.loadPlantillas();
				$('#modalPlantilla').modal('hide');
			}
		}
	},
	watch: {
		procesoSeleccionado: function() {
			const p = this.procesoSeleccionado;
			console.log('cambio en el proceso', this.procesoSeleccionado);

			if (p !== '0') {
				this.documentos = [];
				this.loadDocumentos(p);
				this.documentos = [];
			} else {
				this.documentos = [];
			}
		}
	},
	data() {
		return {
			procesos: [],
			procesoSeleccionado: '0',
			documentos: [],
			documentosSeleccionados: [],
			plantillas: [],
			relacionPlantillaDocumento: [],
			plantillaSeleccionada: 0,
			tipoPlantilla: 'formulario',
			form: {
				nombrePlantilla: '',
				descripcionPlantilla: '',
				codigoPlantilla: '',
				control: '',
				name: '',
				option: '',
				options: [],
				desc: '',
			},
			fields: [],
			controls: [
				{ id: 'text', desc: 'Texto' },
				{ id: 'number', desc: 'Numero' },
				{ id: 'textArea', desc: 'Area de texto' },
				{ id: 'date', desc: 'Fecha' },
				{ id: 'select', desc: 'Selector' },
				{ id: 'radio', desc: 'Opcion unica' },
				{ id: 'checkbox', desc: 'Checkbox' },
				{ id: 'switch', desc: 'Switch' },
			],
		}
	}
})

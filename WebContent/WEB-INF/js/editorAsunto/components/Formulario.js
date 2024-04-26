Vue.component('Formulario', {
	template: //html
		`
            <div class="card">
				<div>
					&nbsp;
				</div>
                <div class="row">
                    <div class="card">
                        <div class="card-body">
							<div class='col-sm-3'>
							<div class="form-group">
                            	<label for="control">Ingrese nombre del expediente</label>
                            	<input class="form-control" v-model="busqueda" id="busqueda" placeholder="EXP-0000-0000"/>
							</div>
							<div class="form-group">
                            	<button @click="buscarExpediente()" class="btn btn-primary btn-sm">Buscar</button>
							</div>
							</div>
                        </div>
                    </div>
                </div>
				<div class="row" v-if="expediente.idExpediente">
				<div class="col-sm-8">
                    <div class="card">
						<div class="card-header">
							<h2>
								Expediente seleccionado
							</h2>
							
						</div>
						
                        <div class="card-body">
							<div class="row">
								&nbsp;
							</div>
							<table class="table">
								<tbody>
									<tr>
										<th>Nombre expediente</th><td>{{expediente.nombreExpediente}}</td>
									</tr>
									<tr>
										<th>Nombre de proceso</th><td>{{expediente.nombreDeProceso}}</td>
									</tr>
									<tr>
										<th>Unidad/Divisi&oacute;n responsable</th><td>{{expediente.nombreUnidad}}</td>
									</tr>
									<tr>
										<th>Asunto (actual)</th><td>{{expediente.asunto}}</td>
									</tr>
									<tr>
										<th>Fecha de inicio</th><td>{{moment(new Date(expediente.fechaInicio)).format(formatoFecha)}}</td>
									</tr>
									<tr>
										<th>Fecha de fin</th><td>{{moment(new Date(expediente.fechaFin)).format(formatoFecha)}}</td>
									</tr>
									<tr>
										<th>Nuevo asunto</th>
										<td span="2">
											<input class="form-control" v-model="nuevoAsunto" id="nuevoAsunto"/>
										</td>
									</tr>

									<tr>
										<td colspan="2" align="center">
											<button @click="actualirarExpediente()" class="btn btn-primary btn-sm">Guardar asunto</button>
										</td>
									</tr>
								</tbody>
							</table>
														
                        </div>
                    </div>
				</div>
                </div>

			</div>

        `,
	mounted: function() {

	},
	methods: {
		buscarExpediente: async function() {
			this.expediente = await getEkpediente(this.busqueda);
		},
		actualirarExpediente: async function() {

			const asuntoAnterior = this.expediente.asunto;
			this.expediente.asunto = this.nuevoAsunto;
			const res = await actualizarExpediente(this.expediente);
			if(res) {
				$.notify({ message: "Asunto actualizado." }, { type: 'success' });
				this.busqueda="";
				this.expediente = res;
			} else {
				$.notify({ message: "No se pudo actualizar el asunto." }, { type: 'error' });
				this.expediente.asunto = asuntoAnterior;
			}
		}
	},
	watch: {
		expediente: function() {
		}
	},
	data() {
		return {
			formatoFecha: "DD-MM-YYYY HH:mm",
			busqueda: "",
			nuevoAsunto: "",
			expediente: {}
		}
	}
})
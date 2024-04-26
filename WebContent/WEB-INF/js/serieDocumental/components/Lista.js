Vue.component('Lista', {
    props: ['series', 'reloadList', 'setProcesoSeleccionado', 'setSerie'],
    template: //html
        `
        <template>
            <div class="container">
                 <table class='table table-striped table-hover table-responsive"'>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Funci&oacute;n</th>
                            <th>SubFunci&oacute;n</th>
                            <th>Serie documental</th>
                            <th>ID serie documental</th>
                            <th>ID subserie documental</th>
                            <th>Editar</th>
                            <th>Eliminar</th>
                            <th>Tabla Retenci&oacute;n</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="serie in series">
                            <td>{{serie.idSerieDocumental}}</td>
                            <td>{{serie.nombreFuncion}}</td>
                            <td>{{serie.nombreSubFuncion}}</td>
                            <td>{{serie.nombreProceso}}</td>
                            <td>
                                <span :id="'valueSerie' + serie.idSerieDocumental">{{serie.serieDocumental}}</span>
                                <input maxlength="30" class="form-control edit" style="display:none" :id="'editSerie' + serie.idSerieDocumental" :value="serie.serieDocumental"/>
                            </td>
                            <td>
                                <span :id="'valueSubSerie' + serie.idSerieDocumental">{{serie.subSerieDocumental}}</span>
                                <input maxlength="30" class="form-control edit" style="display:none" :id="'editSubSerie' + serie.idSerieDocumental" :value="serie.subSerieDocumental"/>
                            </td>
                            <td>
                                <button class="btn btn-warning btn-small btn-edit" @click="edit($event, serie)" >
									<i class="glyphicon glyphicon-edit"></i>
								</button>
                                <button :id="'btnGuardar' + serie.idSerieDocumental" class="btn btn-success btn-small edit" style="display:none" @click="save($event, serie)">Guardar</button>
                                <button :id="'btnCancelar' + serie.idSerieDocumental" class="btn btn-light btn-small edit" style="display:none" @click="cancel(serie)">Cancelar</button>
                            </td>
                            <td>
                            <button class="btn btn-danger btn-small" @click="del($event, serie)">
								<i class="glyphicon glyphicon-remove"></i>
							</button>
                            </td>
                            <td>
                                <button class="btn btn-primary btn-small btn-edit" @click="verDocumentos($event, serie)" >
									<i class="glyphicon glyphicon-list"></i>
								</button>
                            </td>
                        </tr>
                    </tbody>
                 </table>
            </div>
        </template>
        `,
    mounted: function () {

    },
    methods: {
        verDocumentos: function(event, serie) {
            event.preventDefault();
            this.setProcesoSeleccionado(serie.codigoProceso);
			this.setSerie(serie);
        },
        edit: function(event, serie) {
            event.preventDefault();
            $(".btn-edit").hide();
            $('#valueSerie' + serie.idSerieDocumental).hide();
            $('#valueSubSerie' + serie.idSerieDocumental).hide();
            $("#editSerie" + serie.idSerieDocumental).show();
            $("#editSubSerie" + serie.idSerieDocumental).show();
            $('#btnGuardar' + serie.idSerieDocumental).show();
            $('#btnCancelar' + serie.idSerieDocumental).show();
        },
        cancel: function (serie) {
            $(".edit").hide();
            $('#valueSerie' + serie.idSerieDocumental).show();
            $('#valueSubSerie' + serie.idSerieDocumental).show();
            $(".btn-edit").show();
            this.reloadList()
        },
        save: async function(event, serie) {
            event.preventDefault();
            const nvSerieDocumental = $('#editSerie' + serie.idSerieDocumental).val();
            const nvSubSerieDocumental = $('#editSubSerie' + serie.idSerieDocumental).val();
            serie = {
                ...serie,
                serieDocumental: nvSerieDocumental,
                subSerieDocumental: nvSubSerieDocumental
            }

            const res = await actualizarSerieDocumental(serie);
            this.cancel(serie);
            this.reloadList();
			if(res) {
				$.notify({ message: "Serie actualizada." }, { type: 'success' });
			} else {
				$.notify({ message: "No se pudo actualizar la serie documental." }, { type: 'danger' });
			}
        },
		doDel: async function(serie) {
			const res = await eliminarSerieDocumental(serie);
			
			if(res) {
				$.notify({ message: "Serie eliminada." }, { type: 'success' });
			} else {
				$.notify({ message: "No se pudo eliminar la serie documental." }, { type: 'danger' });
			}
            this.reloadList();
		},
        del: async function(event, serie) {
            event.preventDefault();
			const that = this;
			bootbox.confirm({
    			title: "Confirmar eliminacion",
        		message: "Esta seguro de eliminar la serie documental?",
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
            		console.log('El usuario selecciono: ' + result);
            		if (result == true) {
            			that.doDel(serie);
					}
				}});
        }
    },
    watch: {
    },      
    data() {
        return {
            editSerie: {},
            editSubSerie: {},
        }
    }
})
Vue.component('Container', {
    template: //html
        `
        <template>
            <div class="container">
                <nav class="navbar navbar-light bg-light">
                    <form class="form-inline">
                        <button class="btn btn-outline-success" @click="cambiaReporte('area')" type="button">Reporte por &aacute;rea</button>
                        <button class="btn btn-outline-secondary" @click="cambiaReporte('finalizados')" type="button">Reporte de tareas finalizadas</button>
                        <button class="btn btn-outline-secondary" @click="cambiaReporte('pendientes')" type="button">Reporte de tareas pendientes</button>
                        <button class="btn btn-outline-secondary" @click="cambiaReporte('subprocesos')" type="button">Reporte de subprocesos</button>
                    </form>
                </nav>
                <div class="card">
                    <div class="card-header">
                        <div class="card-title">
                            <p><h3>{{titulo}}</h3></p>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group row">
                                    <label for="desde">Desde</label>
                                    <input type='date' id="desde" v-model="desde"/> 
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group row">
                                    <label for="hasta">Hasta</label>
                                    <input type='date' id="hasta" v-model="hasta"/> 
                                </div>
                            </div>
                            <div class="col-md-4">
                                <button class="btn btn-primary" @click="loadData()">Buscar</button>
                            </div>
                        </div>
                        <hr></hr>
                        
                        <ReporteTareas :headers="headers" :repoData="repoData"/>
                    </div>
                </div>

                
            </div>
        </template>
        `,
        mounted: function () {
            this.cambiaReporte();
        },
        vuetify: new Vuetify({}),
        methods: {
            cambiaReporte: function (repo) {
	debugger
				this.repoData = [];
                switch(repo) {
                    case this.reporteArea: 
						this.headers = headerPorArea; 
						this.titulo = this.tituloArea; 
						break;
                    case this.reporteFinalizados: 
						this.headers = headerTareasFinalizadas; 
						this.titulo = this.tituloFinalizadas; 
						break;
                    case this.reportePendientes: 
						this.headers = headerTareasPendientes; 
						this.titulo = this.tituloPendiente; 
						break;
                    case this.reporteSubprocesos: 
						this.headers = headerDeSubprocesos; 
						this.titulo = this.tituloSubprocesos; 
						break;
                    default: 
						this.headers = headerPorArea; 
						this.titulo = this.tituloArea;  
						break;
                }
				this.reporteSeleccionado = repo;
                this.loadData(repo);
            },
          loadData: async function (repo) {
			if (!repo) {
				repo = this.reporteSeleccionado;
			} 
             this.repoData = await getRepoData(repo, this.desde, this.hasta, this.filtro);
          },
        },
        data() {
          return {
            headers: [],
            repoData: [],
            reporteArea: 'area',
            reporteFinalizados: 'finalizados',
            reportePendientes: 'pendientes',
            reporteSubprocesos: 'subprocesos',
            reporteSeleccionado: 'area',
            tituloArea: 'Reporte por area',
            tituloPendiente: 'Reporte de tareas pendientes',
            tituloFinalizadas: 'Reporte de tareas finalizadas',
            tituloSubprocesos: 'Reporte de subprocesos',
            titulo: '',
            desde: '2000-01-01',
            hasta: '2100-12-31',
            filtro: ''
          };
        },
})
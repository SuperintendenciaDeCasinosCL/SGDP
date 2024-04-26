Vue.component('Container', {
    template: //html
        `
        <template>
            <div class="container">
 				<div class="row">
					&nbsp;
                </div>
                <div class="row">
					&nbsp;
                </div>
                <div class="row">
                        <h4>Series Documentales</h4>

                        
                        <div class="modal fade" id="modalSerie" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" >
  					        <div class="modal-dialog modal-dialog-centered modal-lg">
    					        <div class="modal-content">
      						        <div class="modal-header">
        						        <h5 class="modal-title" id="exampleModalLongTitle">Agregar serie documental</h5>
        						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          							        <span aria-hidden="true">&times;</span>
        						        </button>
      						        </div>
      						        <div class="modal-body">
                                        <SerieForm :reloadList='listar'></SerieForm>
                                    </div>  
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="modalDocumentos" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" >
  					        <div class="modal-dialog modal-dialog-centered modal-lg">
    					        <div class="modal-content">
      						        <div class="modal-header">
        						        <h2 class="modal-title" id="exampleModalLongTitle">Tabla de retenci&oacute;n</h2>
        						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          							        <span aria-hidden="true">&times;</span>
        						        </button>
      						        </div>
      						        <div class="modal-body">
                                        <ListaDocumentos :documentos='documentos' :serie="serieSeleccionada" :idSerie="idSerieSeleccionada" :reload="reloadListarTiposDocumento"></ListaDocumentos>
                                    </div>  
                                </div>
                            </div>
                        </div>

                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modalSerie">
  							Crear serie documental
						</button>
						<Lista :series="series" :reloadList='listar' :setProcesoSeleccionado="setProcesoSeleccionado" :setSerie="setSerieSeleccionada"></Lista>
                </div>
            </div>
        </template>
        `,
    mounted: function () {
        this.listar();
    },
    methods: {
        listar: async function () {
            const lista = await listarSeriesDocumentales();
            this.series = lista.sort((it1, it2) => it1.idSerieDocumental - it2.idSerieDocumental);
        },
        setProcesoSeleccionado: async function(codigo) {
            this.load(codigo);
            $("#modalDocumentos").modal('show');
        },
		setSerieSeleccionada: function(serie) {
			this.serieSeleccionada = serie.idSerieDocumental;
			this.idSerieSeleccionada = serie.serieDocumental;
		},
		reloadListarTiposDocumento: async function(codigo) {
			this.load(codigo);
		},
		load:  async function(codigo) {
			const lista = await listarTiposDocumento(codigo);
			this.documentos = lista.sort((it1, it2) => it2.nombreDeTipoDeDocumento > it1.nombreDeTipoDeDocumento ? -1 : 1);
		}
    },
    data() {
        return {
            series: [],
            documentos: [],
            procesoSeleccionado: null,
			serieSeleccionada: null,
			idSerieSeleccionada: null
        }
    }
})
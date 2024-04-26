Vue.component('ListaDocumentos', {
    props: ['documentos', 'serie', 'idSerie', "reload"],
    template: //html
        `
        <template>
            <div class="container">
                 <table class="table table-striped table-hover table-responsive" style="max-width: 74% !important;">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre tipo de documento</th>
                            <th>C&oacute;digo</th>
                            <th>Serie documental</th>
                            <th>A&ntilde;os de retenci&oacute;n</th>
                            <th>Transferible a archivo nacional</th>
                        </tr>
                    </thead>
                    <tbody>
						<tr v-if="documentos.length === 0">
							<td colspan="6"> No hay documentos para mostrar</td>
						</tr>
                        <tr v-for="doc in documentos">
                            <td>{{doc.idTipoDeDocumento}}</td>
                            <td>{{doc.nombreDeTipoDeDocumento}}</td>
                            <td>{{doc.codigoTipoDocumonto}}</td>
                            <td>{{idSerie}}</td>
                            <td>
                            <input type="number" min="1" max="1000" style="max-width: 4em !important;" @change="handleAnios($event, doc)" oninput="this.value = Math.abs(this.value)" :value="doc.aniosDeRetencion"/>
                            </td>
                            <td>
                                <input type="checkbox" @change="onChange($event, doc)" :checked="doc.transferibleAarchivo" >
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
       onChange: async function(event, doc) {
			const checked = event.target.checked;
			doc = {
				...doc,
				transferibleAarchivo: checked,
				serieDocumental: this.serie,
				aniosDeRetencion: doc.aniosDeRetencion
			};
			const res = await actualizarTablaRetencion(doc);
			this.reload(this.idSerie);
		},
		handleAnios: async function(event, doc) {
			const anios = event.target.value;
			
			doc = {
				...doc,
				aniosDeRetencion: anios,
				serieDocumental: this.serie,
				transferibleAarchivo: doc.transferibleAarchivo
			};

			const res = await actualizarTablaRetencion(doc);

			this.reload(this.idSerie);
			if (res) {
        		$.notify({ message: "Documentos actualizados." }, { type: 'success' });
      		} else {
        		$.notify({ message: "No fue posible guardar los documentos." }, { type: 'error' });
      		}
		},
		
    },
    watch: {

    },      
    data() {
        return {
            savedDocuments: []
        }
    }
})	
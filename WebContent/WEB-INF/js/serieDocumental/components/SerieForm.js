Vue.component("SerieForm", {
	props: ['reloadList'],
  template: //html
  `
    <div class="container">
    <form accept-charset="utf-8">
      <div class="row">
        <div class="col-sm-5">
     
          <div class="form-group row">
            <label for="macroProceso">Funci&oacute;n (*)</label>
            <select v-model="macroProceso" id="macroProceso" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="p in macroProcesos" :value="p.idMacroProceso">{{p.nombreMacroProceso}}</option>
            </select>
          </div>

          <div class="form-group row">
            <label for="superProceso">Subfunci&oacute;n (*)</label>
            <select v-model="superProceso" id="superProceso" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="p in superProcesos" :value="p.id">{{p.nombre}}</option>
            </select>
          </div>

		  <div class="form-group row">
            <label for="proceso">Serie documental (*)</label>
            <select v-model="proceso" id="proceso" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="p in procesos" :value="p.idProceso">{{p.nombreProceso}}</option>
            </select>
          </div>

		  <div class="form-group row">
		  <label for="idSerieDocumental">ID Serie documental (*)</label>
		  <input v-model="idSerieDocumental" class="form-control" id="idSerieDocumental" maxlength="30" />
		</div>
	
		<div class="form-group row">
		  <label for="idSubSerieDocumental">ID Subserie documental</label>
		  <input v-model="idSubSerieDocumental" class="form-control" id="idSubSerieDocumental" maxlength="30"/>
		</div>
          
		<div class="form-group row">
			<button class="btn btn-primary btn-small" @click="save($event)" :disabled="disableButton">Guardar</button>
		</div>
        </div>
        
      </div>

      
      </form>
      </div>
  `,
  mounted: function () {
    this.loadMacroProcesos();
  },
  computed: {
    disableButton: function () {
      return false; //processData === {};
    },
  },
  data() {
    return {
      macroProceso: "0",
      superProceso: "0",
      proceso: "0",
      idSubSerieDocumental: "",
      idSerieDocumental: "",
      macroProcesos: [],
      superProcesos: [],
	  procesos: []
    };
  },
  watch: {
    macroProceso: function () {
      if (this.macroProceso !== "0") {
        this.loadSuperProcesos(this.macroProceso);
      } else {
        this.superProcesos = [];
        this.superProceso = "0";
      }
    },
	superProceso: function () {
		if (this.superProceso !== "0") {
		  this.loadProcesos(this.superProceso);
		} else {
		  this.procesos = [];
		  this.proceso = "0";
		}
	},
	proceso: function() {
		this.idSerieDocumental = this.procesos.filter(it => it.idProceso === this.proceso)[0]?.codigoProceso;
	}
  },
  methods: {
	loadMacroProcesos: async function(){
		this.macroProcesos = await getMacroProcesos(0);
	},
    loadSuperProcesos: async function () {
      this.superProcesos = await getSuperProcesos(this.macroProceso);
    },
    loadProcesos: async function () {
      this.procesos = await getProcesos(this.superProceso);
    },
	validate: function(value) {
		return value !== null && value !== "" && value !== "0"; 
	},
	save: async function (event) {
		event.preventDefault();
		const proceso = this.procesos.filter(it => it.idProceso === this.proceso)[0];
		
		let res = null;
		
		if(this.validate(this.macroProceso) && this.validate(this.superProceso) && this.validate(this.proceso) && this.validate(this.idSerieDocumental) && this.idSerieDocumental.length < 31 && this.idSubSerieDocumental.length < 31) {
		
			res = await guardarSerieDocumental({
    	  			idFuncion: this.macroProceso,
					nombreFuncion: '',
      				idSubFuncion: this.superProceso,
      				nombreSubFuncion: '',
      				idProceso: this.proceso,
      				nombreProceso: proceso?.nombreProceso,
					codigoProceso: proceso?.codigoProceso,
      				serieDocumental: this.idSerieDocumental,
      				subSerieDocumental: this.idSubSerieDocumental
			})

			if(res) {
				$('#modalSerie').modal('hide');
				this.macroProceso + "0";
				this.superProceso = "0";
				this.proceso = "0";
				this.idSubSerieDocumental = "";
				this.idSerieDocumental = "";
				this.superProcesos = [];
				this.procesos = [];
				this.reloadList();
			
				$.notify({ message: "Serie guardada." }, { type: 'success' });
			} else {
				$.notify({ message: "No se pudo guardar la serie documental." }, { type: 'danger' });
			}
		
		} else {
			$.notify({ message: "Debe llenar los campos obligatorios (*)." }, { type: 'danger' });
			if(this.idSerieDocumental.length > 30 || this.idSubSerieDocumental.length > 30) {
				$.notify({ message: 'Los campos "ID Serie documental" y "D Subserie documental" deben ser de maximo 30 carateres' }, { type: 'danger' });
			}
			
			return;
		}
		

	}
  },
});


Vue.component('LoadForm', {
  template: //html
    `
    <div class="container">
    <form accept-charset="utf-8">
      <div class="row">
        <div class="col-sm-5">
          <div class="form-group row">
            <label for="perspectiva">Perspectiva</label>
            <select v-model="perspectiva" id="perspectiva" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="p in perspectivas" :value="p.id">{{p.nombre}}</option>
            </select>
          </div>
      
          <div class="form-group row">
            <label for="macroProceso">Macroproceso</label>
            <select v-model="macroProceso" id="macroProceso" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="p in macroProcesos" :value="p.idMacroProceso">{{p.nombreMacroProceso}}</option>
            </select>
          </div>

          <div class="form-group row">
            <label for="superProceso">Superproceso</label>
            <select v-model="superProceso" id="superProceso" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="p in superProcesos" :value="p.id">{{p.nombre}}</option>
            </select>
          </div>

          <div class="form-group row">
            <label for="unidad">Divisi&oacute;n / Unidad responsable</label>
            <select v-model="unidad" id="unidad" class="form-control">
              <option :value="0">Seleccione...</option>
              <option v-for="u in unidades" :value="u.idUnidad">{{u.nombreCompletoUnidad}}</option>
            </select>
          </div>
        </div>
        <div class="col-sm-1">

        </div>
        <div class="col-sm-5">
      
          <div class="form-group row">
            <label for="nombreProceso">Nombre subproceso</label>
            <input v-model="nombreProceso" class="form-control" id="nombreProceso"/>
          </div>
      
          <div class="form-group row">
            <label for="codigoProceso">C&oacute;digo subproceso</label>
            <input v-model="codigoProceso" class="form-control" id="codigoProceso"/>
          </div>
      
          <div class="form-group row">
            <label for="duracionProceso">Duraci&oacute;n (d&iacute;as h&aacute;biles)</label>
            <input v-model="duracionProceso" class="form-control" id="duracionProceso" @keypress="isNumber($event)"/>
          </div>
          <div class="form-group row">
            <input class="form-check-input" v-model="privado" type="checkbox" value="" id="privado" checked>
            <label class="form-check-label" for="privado">
              Privado
            </label>
          </div>
      
        </div>
      </div>
      <div class="row">
        <div class="col-md-5">
          <div class="row">
            <div class="form-group row">
              <label for="inputfile">Seleccione el archivo bpmn (evtensi&oacute;n xml, bpmn)</label>
              <input type="file" v-model="bpmnFile" name="inputfile" id="inputfile" @change="readFile(event)"  class="form-control-file" accept=".bpm, .bpmn, .xml">
            </div>
            <div class="form-group row">
              <label for="image">Seleccione el diagrama (svg, png, jpg)</label>
              <input type="file" v-model="image" name="image" id="image" class="form-control-file" accept="image/png, image/svg, image/jpeg, image/jpg">
            </div>
          </div>
        </div>
        <div class="col-sm-1">

        </div>
        <div class="col-md-5">
          <div class="row">
            <button class="btn btn-primary" @click="doProcess($event)" :disabled="disabledButton" >Guardar proceso</button>
          </div>
        </div>
      </div>
      
      </form>
      </div>
  `,
  mounted: function () {
    this.loadData();
  },
  computed: {
    disableButton: function () {
      return false; //processData === {};
    }
  },
  data() {
    return {
      image: '',
      perspectiva: '0',
      macroProceso: '0',
      superProceso: '0',
      proceso: '0',
      nombreProceso: '',
      codigoProceso: '',
      duracionProceso: '',
      privado: false,
      unidad: '0',
      bpmnFile: '',
      svgFile: '',
      disableButton: true,
      processData: {},
      perspectivas: [],
      unidades: [],
      macroProcesos: [],
      superProcesos: [],
      procesos: [],
      disabledButton: false,
      afirmativos: ['sí','sï', 'si', 'SI', 'Si', 'yes']
    }
  },
  watch: {
    perspectiva: function () {
      if (this.perspectiva !== '0') {
        this.loadMacroProcesos(this.perspectiva);
        this.superProcesos = [];
        this.superProceso = '0';
      } else {
        this.macroProcesos = [];
        this.macroProceso = '0';
      }
      this.superProcesos = [];
    },
    macroProceso: function () {
      if (this.macroProceso !== '0') {
        this.loadSuperProcesos(this.macroProceso);
      } else {
        this.superProcesos = [];
        this.superProceso = '0';
      }
    },
  },
  methods: {
    loadMacroProcesos: async function () {
      this.macroProcesos = await getMacroProcesos(this.perspectiva);
    },
    loadSuperProcesos: async function () {
      this.superProcesos = await getSuperProcesos(this.macroProceso);
    },
    loadProcesos: async function () {
      const data = await getProcesos(this.superProceso);
    },
    getProp: function (doc, propName) {

      let value = '';
      const docprop = doc.prop;
      if (!docprop || docprop === undefined) {
        return value;
      }

      if (!Array.isArray(docprop)) {
        value = docprop[propName] ? docprop[propName] : '';
      } else {
        value = docprop.reduce((acc, prop) => prop['@name'] === propName ? prop['@value'] : acc, '');
      }

      return value;

    },
    doProcess: async function (e) {
      e.preventDefault();
      this.disabledButton = true;

      if (this.perspectiva === '0' ||
        this.macroProceso === '0' ||
        this.superProceso === '0' ||
        this.nombreProceso === '' ||
        this.codigoProceso === '' ||
        this.duracionProceso === '0' ||
        store.state.processData === null) {
        bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Todos los campos son obligatorios.</p></div>")
        this.disabledButton = false;
        return
      }


      const processdata = store.state.processData


      let proceso = {
        idProceso: '0',
        descripcionProceso: '',
        nombreProceso: this.nombreProceso,
        vigente: true,
        diasHabilesMaxDuracion: this.duracionProceso,
        nombreMacroproceso: '',
        codigoProceso: this.codigoProceso,
        idMacroproceso: this.macroProceso,
        idSuperProceso: this.superProceso,
        privado: this.privado,
        xml: processdata.xml,
        idUnidad: this.unidad,
        fechaCreacion: new Date(),
        tareas: processdata.parsed.map((it, idx) => ({
          idTarea: 0,
          descripcionTarea: '',
          nombreTarea: it.taskName,
          diasHabilesMaxDuracion: Number(it.plazo),
          orden: idx + 1,
          vigente: true,
          idDiagrama: it.taskId,
          visa: it.visa,
          fea: it.fea,
          numOc: it.numOc,
          esperarRespuesta: it.esperar,
          soloInformar: !it.soloInformar || this.esSi(it.soloInformar),
          idEtapa: it.stage,
          obligatoria: it.obligatoria,
          ulitimaTarea: it.type.includes('FIN') || it.finAlaSiguiente,
          tipoBifurcacion: it.type.includes('FIN') || it.type.includes('INICIO') ? '' : it.type.join(','),
          tipoReseteo: it.tiporesteo,
          numeroDiasReseteo: it.diasresteo,
          conformaExpediente: it.conformaExpediente,
          distribuye: it.distribuye,
          codigotipodoc: it.codigotipodoc,
          numeracionAuto: it.numeracionAuto,
          tareaSiguiente: it.nextTasks,
          roles: it.roles,
          docs: it.docsRef,
          salidaNoConforme: null,
          parametros: it.output
        })),
        docs: processdata.docs.map(d => ({
          idTipoDeDocumento: 0,
          nombreDeTipoDeDocumento: d.name,
          codigoTipoDocumonto: this.getProp(d, 'codtipodoc'),
          conformaExpediente: this.esSi(this.getProp(d, 'expediente')),
          aplicaVisacion: this.esSi(this.getProp(d, 'visa')),
          aplicaFEA: this.esSi(this.getProp(d, 'fea')),
          esDocumentoConductor: this.esSi(this.getProp(d, 'conductor')),
          numeracionAuto: this.esSi(this.getProp(d, 'numauto')),
          nombreEnDiagrama: d.id
        })),
        roles: processdata.roles.map(rol => ({
          id: null,
          nombre: rol.name,
          idProceso: null
        })),

      };

      proceso = {
        ...proceso,
        tieneParametrosPorTarea: proceso.tareas.reduce((acc, it) => Array.isArray(it.parametros) ? true : acc, false)
      }

      let formData = new FormData();
      const imagefile = document.querySelector('#image');
      const file = imagefile.files[0]
      formData.append("image", file);
      formData.append("tmp", JSON.stringify(proceso));

      const res = await sendProcessAssMultipart(formData);

      if (res && res.data && res.status === 200) {
        $.notify({ message: "Proceso guardado correctamente" }, { type: 'success' });
        this.perspectiva = '0';
        this.macroProceso = '0';
        this.proceso = '0';
        this.nombreProceso = '';
        this.codigoProceso = '';
        this.duracionProceso = '';
        this.unidad = '0';
        this.bpmnFile = '';
        this.image = '';
        store.commit('setProcessData', null);
      } else {
        $.notify({ message: "No se pudo guardar el proceso" }, { type: 'error' });
      }
      this.disabledButton = false;
    },
    sendImage: (processId) => {
      var formData = new FormData();
      var imagefile = document.querySelector('#image');

      formData.append("image", imagefile.files[0]);
      sendProcessImage(processId, formData);
    },
    esSi: function (r) { return this.afirmativos.includes(r.trim().toLowerCase()) },
    loadData: async function () {
      const { perspectivas, unidades } = await getInitialData();
      this.perspectivas = perspectivas;
      this.unidades = unidades;
    },

    readFile: (event) => {
      var fr = new FileReader();
      fr.onload = function () {
        var xmlDoc = this.responseXML;
        var xml = fr.result;
        dom = parseXml(xml);
        json = JSON.parse(xml2json(dom)
          .replace('undefined', '')
          .replaceAll('bpmn:', '')
          .replaceAll('camunda:', ''));

        const paralellGateways = getParallelGateway(json);
        const exclusiveGateway = getExclusiveGateways(json);

        const startEvent = getStartEvent(json);
        const endEvent = getEndEvent(json);
        const roles = getRoles(json);
        const tasks = getTasks(json);
        const docs = getDocuments(json);
        const gateways = getGateways(json);
        const relations = getGRelations(json);

        const mappedRoles = mapRoles(roles);
        const mappedDocs = mapDocs(docs);
        const mappedTypes = taskTypes(relations, startEvent, endEvent, paralellGateways, exclusiveGateway);
        const mappedDestinys = mapDestinys(relations, tasks);

        const parsed = parse(tasks, mappedRoles, mappedDocs, mappedTypes, mappedDestinys);

        store.commit('setProcessData', {
          startEvent, endEvent, roles, tasks, docs, gateways, relations,
          parsed, xml
        });

      }

      fr.readAsText(event.target.files[0]);
    }
  },

})

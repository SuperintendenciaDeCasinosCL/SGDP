
Vue.component('UserForm', {
  template: //html
    `
    <div class="container">
    <form>
    <div class="row">
      <div class="col-sm-6">
        <div class="form-group row">
          <label for="subProceso">Seleccione subproceso</label>
          <select v-model="subProceso" id="subProceso" class="form-control" required>
            <option v-for="p in procesos" :value="p.idProceso">{{p.nombreProceso}}</option>
          </select>
        </div>
    
        <div class="form-group row">
          <label for="rol">Seleccione Rol</label>
          <select v-model="rol" id="rol" class="form-control" required>
            <option v-if="subProceso !== undefined" v-for="p in (roles ? roles : [] )" :value="p.id">{{p.nombre}}</option>
          </select>
        </div>
        
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3">
        <div class="form-group row">
          <label for="usuariosSinAsignar">Usuarios  sin asignar</label>

          <select id="usuariosSinAsignar" class="form-control" multiple size="20">
            <optgroup :label="g" v-for="g in Object.keys(usuariosSinAsignar)" :value="g" size="15" @click="addGroup(g)">

              <option v-for="u of usuariosSinAsignar[g]" :value="u.id" @click="addUser(g, u)">
                {{u.name}}
              </option>
            </optgroup>
          </select>
        </div>
      </div>
      <div class="col-sm-3">
        <div class="form-group row">
          <label for="usuariosAsignados">Usuarios asignados 
          </label>
          <select id="usuariosAsignados" class="form-control" multiple size="20">
            <optgroup :label="g" v-for="g in Object.keys(usuariosAsignados)" :value="g" size="15" @click="removeGroup(g)">

            <option v-for="u of usuariosAsignados[g]" :value="u.id" @click="removeUser(g, u)">
            {{u.name}}
            </option>
            </optgroup>
            
          </select>
        </div>
      </div>


    </div>
    
    <div class="row">
      <button class="btn btn-secondary btn-small" @click="restore($event)">Restaurar</button>
      <button class="btn btn-primary btn-small" @click="saveUsers($event)" :disabled="disableButton">Guardar Usuarios</button>
    </div>
    </form>
    
      </div>
  `,
  mounted: function () {
    console.log('usuarios montado a cargar')
    this.loadProcesos();
  },
  computed: {
    disableButton: function () {
      return this.processData === {};
    },
  },
  data() {
    return {
      subProceso: null,
      rol: null,
      procesos: [],
      roles: [],
      usuariosAsignados: {},
      usuariosSinAsignar: {},
      usuariosAsignadosBack: {},
      usuariosSinAsignarBack: {},
      processData: {},
      blocked: false
    }
  },
  watch: {
    subProceso: function (val) {
      this.loadRoles(val);
    },
    rol: function (val) {
      console.log("cambio rol");
      this.loadUsuarios(val);
    }
  },
  methods: {
    doProcess: function () {
      console.log('procesando');
      console.log(
        this.subProceso,
        this.rol,
        this.procesos,
        this.roles,
        this.usuariosAsignados,
        this.usuariosSinAsignar,
      )
    },
    addGroup: function (group) {
      if (this.blocked) {
        this.blocked = false;
        return
      }

      const grupo = this.usuariosSinAsignar[group];

      delete this.usuariosSinAsignar[group];

      if (this.usuariosAsignados[group]) {
        const temp = this.usuariosAsignados[group].concat(grupo);
        const temp2 = this.usuariosAsignados;
        console.log(temp2)
        this.usuariosAsignados = {};
        console.log(temp2);
        this.usuariosAsignados = temp2;
        this.usuariosAsignados[group] = [];
        this.usuariosAsignados[group] = temp;
      } else {
        this.usuariosAsignados[group] = grupo;
      }
      this.$forceUpdate();

    },
    addUser: function (group, user) {
      if (this.blocked) {
        this.blocked = false;
        return
      }
      this.blocked = true;

      const filtered = this.usuariosSinAsignar[group].filter(it => it.name !== user.name);
      this.usuariosSinAsignar[group] = filtered;

      if (this.usuariosSinAsignar[group].length === 0) {
        delete this.usuariosSinAsignar[group];
      }

      if (this.usuariosAsignados[group]) {
        const array = this.usuariosAsignados[group];
        array.push(user);
        this.usuariosAsignados[group] = array;

      } else {
        this.usuariosAsignados[group] = [];
        this.usuariosAsignados[group].push(user);
      }

    },
    removeGroup: function (group) {
      if (this.blocked) {
        this.blocked = false;
        return
      }

      const grupo = this.usuariosAsignados[group];

      delete this.usuariosAsignados[group];

      if (this.usuariosSinAsignar[group]) {
        const temp = this.usuariosSinAsignar[group].concat(grupo);
        this.usuariosSinAsignar[group] = [];
        this.usuariosSinAsignar[group] = temp;
      } else {
        this.usuariosSinAsignar[group] = grupo;
      }

      this.$forceUpdate();

    },
    removeUser: function (group, user) {
      if (this.blocked) {
        this.blocked = false;
        return
      }
      this.blocked = true;

      const filtered = this.usuariosAsignados[group].filter(it => it.name !== user.name);
      this.usuariosAsignados[group] = filtered;

      if (this.usuariosAsignados[group].length === 0) {
        delete this.usuariosAsignados[group];
      }

      if (this.usuariosSinAsignar[group]) {
        const array = this.usuariosSinAsignar[group];
        array.push(user);
        this.usuariosSinAsignar[group] = array;

      } else {
        this.usuariosSinAsignar[group] = [];
        this.usuariosSinAsignar[group].push(user);
      }

    },
    restore: function (event) {
      event.preventDefault();
      this.usuariosAsignados = this.groupUsers(this.usuariosAsignadosBack);
      this.usuariosSinAsignar = this.groupUsers(this.usuariosSinAsignarBack);
    },
    loadProcesos: async function () {
      console.log('cargando procesos')
      const data = await getProcesos();
      console.log(' procesos ', data.data)
      this.procesos = data.data
    },
    loadRoles: async function (idProceso) {
      const data = await getRolesService(idProceso);
      console.log(' roles   ', data)
      this.roles = data
    },
    saveUsers: async function (event) {
      event.preventDefault();
      if (this.rol === null) {
        bootbox.alert("<div style=\"text-align:center;\"><i class=\"icon-emo-sleep don_sshi\"></i><p style=\"margin-top: 15px;\">Deme seleccionar ROL.</p></div>")
        return
      }

      const us = Object.values(this.usuariosAsignados).reduce((acc, it) => acc.concat(it), []);

      const usuarios = us.map((it, idx) => ({
        idUsuarioResponsabilidad: this.rol,
        idUsuario: it.name,
        orden: idx + 1
      }));
      const res = await postUsuarios(usuarios, this.rol);
      console.log("guardados", res);
      if (res) {
        $.notify({ message: "Usuarios guardados" }, { type: 'success' });
      } else {
        $.notify({ message: "No se pudieron guardar los usuarios" }, { type: 'error' });
      }
    },
    loadUsuarios: async function (idRol) {
      this.usuariosAsignados = [];
      this.usuariosSinAsignar = [];
      this.usuariosAsignadosBack = [];
      this.usuariosSinAsignarBack = [];

      const data = await getUsuarios(idRol);
      this.usuariosAsignados = this.groupUsers(data.asignados);
      this.usuariosSinAsignar = this.groupUsers(data.noAsignados);
      this.usuariosAsignadosBack = data.asignados
      this.usuariosSinAsignarBack = data.noAsignados
    },
    groupUsers: function (list) {
      const mapa = {};

      list.forEach(el => {
        const nombre = el.nombreRol;
        if (mapa[nombre] === undefined) {
          mapa[nombre] = [];
        }
        const contenido = mapa[nombre];
        contenido.push({ id: el.idUsuario, name: el.idUsuario });
        mapa[nombre] = contenido;
      });

      return mapa;
    }

  },

})
